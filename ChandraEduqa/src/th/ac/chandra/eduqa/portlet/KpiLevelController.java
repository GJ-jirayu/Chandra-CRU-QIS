package th.ac.chandra.eduqa.portlet;

//import java.sql.Date;
import java.sql.Timestamp;
//import java.text.DateFormat;
import java.text.ParseException;
///import java.text.SimpleDateFormat;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.apache.log4j.Logger;
//import org.joda.time.DateTime;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import th.ac.chandra.eduqa.constant.ServiceConstant;
import th.ac.chandra.eduqa.form.KpiLevelForm;
import th.ac.chandra.eduqa.mapper.CustomObjectMapper;
import th.ac.chandra.eduqa.mapper.ResultService;
import th.ac.chandra.eduqa.model.KpiLevelModel;
import th.ac.chandra.eduqa.model.SysYearModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.Paging;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

@Controller("kpiLevelController")
@RequestMapping("VIEW")
@SessionAttributes({ "kpiLevelForm" })
public class KpiLevelController {
	private static final Logger logger = Logger
			.getLogger(KpiLevelController.class);
	@Autowired
	@Qualifier("eduqaServiceWSImpl")
	private EduqaService service;
	
	// ชั่วคราว	
	//private Integer sysYear = 2558;
	private Integer getCurrentYear(){
		SysYearModel sysYearModel = new SysYearModel();
		List<SysYearModel> sysYears = service.searchSysYear(sysYearModel);
		try{
			sysYearModel = sysYears.get(0);
			return sysYearModel.getMasterAcademicYear();
		}catch(Exception err){
			return 9999;
		}
	}
	
	@Autowired
	private CustomObjectMapper customObjectMapper;

	@InitBinder
	public void initBinder(PortletRequestDataBinder binder,
			PortletPreferences preferences) {
		logger.debug("initBinder");
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@RequestMapping("VIEW")
	// first visit
	public String listDetail(PortletRequest request, Model model) {
		KpiLevelForm kpiLevelForm = null;
		if (!model.containsAttribute("kpiLevelForm")) {
			kpiLevelForm = new KpiLevelForm();
			model.addAttribute("kpiLevelForm", kpiLevelForm);
		} else {
			kpiLevelForm = (KpiLevelForm) model.asMap().get("kpiLevelForm");
		}
		KpiLevelModel kpiLevelModel = new KpiLevelModel();
		String keySearch = kpiLevelForm.getKeySearch();
		kpiLevelModel.setKeySearch(keySearch);

		Paging page = new Paging(); //default pageNo = 1
		kpiLevelModel.setPaging(page);
		List<KpiLevelModel> levels = service.searchKpiLevel(kpiLevelModel);
		model.addAttribute("levels", levels);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", "1");
		return "master/KpiLevel";
	}

	@RequestMapping(params = "action=doInsert")
	public void actionInsert(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("kpiLevelForm") KpiLevelForm kpiLevelForm,
			BindingResult result, Model model) {
		User user = (User) request.getAttribute(WebKeys.USER);
		kpiLevelForm.getKpiLevelModel().setLevelId(null);
		kpiLevelForm.getKpiLevelModel().setAcademicYear(getCurrentYear());
		kpiLevelForm.getKpiLevelModel().setCreatedBy(user.getFullName());
		kpiLevelForm.getKpiLevelModel().setUpdatedBy(user.getFullName());
		
		ResultService rs = service.saveKpiLevel(kpiLevelForm.getKpiLevelModel());
		String pageNo = kpiLevelForm.getPageNo().toString();
		String pageSize = kpiLevelForm.getPageSize();
		String keySearch = kpiLevelForm.getKeySearch();
		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", pageNo);
		response.setRenderParameter("pageSize", pageSize);
		response.setRenderParameter("messageCode", rs.getMsgCode());
		response.setRenderParameter("messageDesc", rs.getMsgDesc());
		//Render to "VIEW"
	}
	
	@RequestMapping(params = "action=doEdit")
	public void actionUpdate(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("kpiLevelForm") KpiLevelForm kpiLevelForm,
			BindingResult result, Model model) throws ParseException {
		User user = (User) request.getAttribute(WebKeys.USER);
		kpiLevelForm.getKpiLevelModel().setAcademicYear(getCurrentYear());
		kpiLevelForm.getKpiLevelModel().setUpdatedBy(user.getFullName());
		String createStr = kpiLevelForm.getCreateDate();
		Timestamp timestamp = Timestamp.valueOf(createStr);
		kpiLevelForm.getKpiLevelModel().setCreatedDate(timestamp);
		String pageNo = kpiLevelForm.getPageNo().toString();
		
		ResultService rs = service.updateKpiLevel(kpiLevelForm.getKpiLevelModel());

		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("keySearch", kpiLevelForm.getKeySearch());
		response.setRenderParameter("pageNoStr", pageNo);
		response.setRenderParameter("pageSize", kpiLevelForm.getPageSize());
		response.setRenderParameter("messageCode", rs.getMsgCode());
		response.setRenderParameter("messageDesc", rs.getMsgDesc());
	}
	
	@RequestMapping(params = "action=doDelete")
	public void actionDelete(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("kpiLevelForm") KpiLevelForm kpiLevelForm,
			BindingResult result, Model model) {
		User user = (User) request.getAttribute(WebKeys.USER);
		KpiLevelModel kpiLevelModel = new KpiLevelModel();
		kpiLevelModel.setLevelId(kpiLevelForm.getKpiLevelModel().getLevelId());		
		//service.deleteKpiLevel(kpiLevelModel);
		String messageDesc ="";
		String messageCode ="";
		int recoedCount=service.deleteKpiLevel(kpiLevelModel);
		if(recoedCount == -9){
			//model.addAttribute(ServiceConstant.ERROR_MESSAGE_KEY, ServiceConstant.ERROR_CONSTRAINT_VIOLATION_MESSAGE_CODE);
			messageDesc = ServiceConstant.ERROR_CONSTRAINT_VIOLATION_MESSAGE_CODE;
			messageCode = "0";
		}
		//Render to list page.
		String pageNo = kpiLevelForm.getPageNo().toString();
		String pageSize = kpiLevelForm.getPageSize();
		String keySearch = kpiLevelForm.getKeySearch();
		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", pageNo);
		response.setRenderParameter("pageSize", pageSize);
		response.setRenderParameter("messageCode", messageCode);
		response.setRenderParameter("messageDesc", messageDesc);
	}

	@RequestMapping(params = "action=doSearch")
	public void actionSearch(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("kpiLevelForm") KpiLevelForm kpiLevelForm,
			BindingResult result, Model model) {
		/*
		 * User user = (User) request.getAttribute(WebKeys.USER);
		 * kpiLevelForm.getKpiLevelModel().setAcademicYear(sysYear); keySearch =
		 * "aa"; KpiLevelModel kpiLevelModel =new KpiLevelModel ();
		 * kpiLevelModel.setKeySearch(keySearch); List<KpiLevelModel> levels =
		 * service.searchKpiLevel(kpiLevelModel);
		 * //model.addAttribute("levels",levels); //return n;
		 */
		String keySearch = kpiLevelForm.getKeySearch();
		String pageSize = kpiLevelForm.getPageSize();
		response.setRenderParameter("render", "listSearch");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageSize", pageSize);
	}
	
	@RequestMapping(params = "action=doListPage")
	public void actionListPage(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("kpiLevelForm") KpiLevelForm kpiLevelForm,
			BindingResult result, Model model) {
		String pageNo = kpiLevelForm.getPageNo().toString();
		String pageSize = kpiLevelForm.getPageSize();
		String keySearch = kpiLevelForm.getKeySearch();
		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", pageNo);
		response.setRenderParameter("pageSize", pageSize);
		response.setRenderParameter("messageDesc", "");
		response.setRenderParameter("messageCode", "");
	}
	
	@RequestMapping(params = "action=doPageSize")
	public void actionPageSize(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("kpiLevelForm") KpiLevelForm kpiLevelForm,
			BindingResult result, Model model) {
		String pageSize = kpiLevelForm.getPageSize();
		String keySearch = kpiLevelForm.getKeySearch();
		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", "1");
		response.setRenderParameter("pageSize", pageSize);
		response.setRenderParameter("messageDesc", "");
		response.setRenderParameter("messageCode", "");
	}

	@RequestMapping("VIEW")
	@RenderMapping(params = "render=listSearch")
	public String RenderSearch(@RequestParam("keySearch") String keySearch,
			@RequestParam("pageSize") int pageSize, Model model) {
		KpiLevelModel kpiLevelModel = new KpiLevelModel();
		kpiLevelModel.setKeySearch(keySearch);
		Paging page = new Paging(); //default pageNo = 1
		kpiLevelModel.setPaging(page);
		kpiLevelModel.getPaging().setPageSize(pageSize);
		List<KpiLevelModel> levels = service.searchKpiLevel(kpiLevelModel);
		model.addAttribute("levels", levels);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", 1);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("keySearch", keySearch);
		return "master/KpiLevel";
	}
	
	@RequestMapping("VIEW")
	@RenderMapping(params = "render=listPage")
	public String RenderListPage(@RequestParam("keySearch") String keySearch,
			@RequestParam("pageNoStr") String pageNoStr,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("messageDesc") String messageDesc,
			@RequestParam("messageCode") String messageCode, 
			Model model) {
		KpiLevelModel kpiLevelModel = new KpiLevelModel();
		kpiLevelModel.setKeySearch(keySearch);
		Paging page = new Paging(Integer.parseInt(pageNoStr), 10, "ASC");
		kpiLevelModel.setPaging(page);
		kpiLevelModel.getPaging().setPageSize(pageSize);
		List<KpiLevelModel> levels = service.searchKpiLevel(kpiLevelModel);
		model.addAttribute("levels", levels);
		model.addAttribute("keySearch", keySearch);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", pageNoStr);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("messageCode", messageCode);
		model.addAttribute("messageDesc", messageDesc);
		return "master/KpiLevel";
	}
	
	/*@RequestMapping("VIEW")
	@RenderMapping(params = "render=actionList")
	public String RenderInsert(@RequestParam("messageCode") String messageCode, 
			@RequestParam("messageDesc") String messageDesc,
			@RequestParam("pageNoStr") String pageNoStr, Model model) {
		KpiLevelModel kpiLevelModel = new KpiLevelModel();
		Paging page = new Paging(); //default pageNo=1
		kpiLevelModel.setPaging(page);
		kpiLevelModel.getPaging().setPageSize(10);
		List<KpiLevelModel> levels = service.searchKpiLevel(kpiLevelModel);
		model.addAttribute("levels", levels);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", pageNoStr);
		model.addAttribute("messageCode", messageCode);
		model.addAttribute("messageDesc", messageDesc);
		return "master/KpiLevel";
	}*/
	/*
	 * @ResourceMapping(value="getPlan")
	 * 
	 * @ResponseBody public void Echo(ResourceRequest request,ResourceResponse
	 * response) throws IOException{ String id=request.getParameter("p1");
	 * JSONObject json = JSONFactoryUtil.createJSONObject();
	 * json.put("description",id);
	 * 
	 * System.out.println(json.toString());
	 * response.getWriter().write(json.toString()); }
	 */
}
