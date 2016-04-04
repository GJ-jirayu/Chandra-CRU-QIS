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
import th.ac.chandra.eduqa.domain.KpiGroup;
import th.ac.chandra.eduqa.form.KpiGroupForm;
import th.ac.chandra.eduqa.form.KpiStrucForm;
import th.ac.chandra.eduqa.mapper.CustomObjectMapper;
import th.ac.chandra.eduqa.mapper.ResultService;
import th.ac.chandra.eduqa.model.KpiGroupModel;
import th.ac.chandra.eduqa.model.KpiStrucModel;
import th.ac.chandra.eduqa.model.StrucTypeModel;
import th.ac.chandra.eduqa.model.SysYearModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.Paging;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

@Controller("kpiStrucController")
@RequestMapping("VIEW")
@SessionAttributes({ "kpiStrucForm" })
public class KpiStrucController {
	private static final Logger logger = Logger
			.getLogger(KpiStrucController.class);
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
		KpiStrucForm kpiStrucForm = null;
		if (!model.containsAttribute("kpiStrucForm")) {
			kpiStrucForm = new KpiStrucForm();
			model.addAttribute("kpiStrucForm", kpiStrucForm);
		} else {
			kpiStrucForm = (KpiStrucForm) model.asMap().get("kpiStrucForm");
		}
		KpiStrucModel kpiStrucModel = new KpiStrucModel();
		KpiGroupModel kpiGroupModel = new KpiGroupModel();
		StrucTypeModel strucTypeModel = new StrucTypeModel();
		Paging page = new Paging(); //default pageNo = 1
		String keySearch = kpiStrucForm.getKeySearch();
		kpiStrucModel.setKeySearch(keySearch);
		kpiStrucModel.setPaging(page);
		
		List<KpiGroupModel> groups = service.searchKpiGroup(kpiGroupModel);
		List<StrucTypeModel> strucTypes = service.searchStrucType(strucTypeModel);
		List<KpiStrucModel> strucs = service.searchKpiStruc(kpiStrucModel);		
		model.addAttribute("strucs", strucs);
		model.addAttribute("groups", groups);
		model.addAttribute("strucTypes", strucTypes);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", "1");
		return "master/KpiStruc";
	}

	@RequestMapping(params = "action=doInsert")
	public void actionInsert(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("kpiStrucForm") KpiStrucForm kpiStrucForm,
			BindingResult result, Model model) {
		User user = (User) request.getAttribute(WebKeys.USER);
		kpiStrucForm.getKpiStrucModel().setStrucId(null);
		kpiStrucForm.getKpiStrucModel().setAcademicYear(getCurrentYear());
		kpiStrucForm.getKpiStrucModel().setCreatedBy(user.getFullName());
		kpiStrucForm.getKpiStrucModel().setUpdatedBy(user.getFullName());
		ResultService rs = service.saveKpiStruc(kpiStrucForm.getKpiStrucModel());
		String pageNo = kpiStrucForm.getPageNo().toString();
		String pageSize = kpiStrucForm.getPageSize();
		String keySearch = kpiStrucForm.getKeySearch();
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
			@ModelAttribute("kpiStrucForm") KpiStrucForm kpiStrucForm,
			BindingResult result, Model model) throws ParseException {
		User user = (User) request.getAttribute(WebKeys.USER);
		kpiStrucForm.getKpiStrucModel().setAcademicYear(getCurrentYear());
		kpiStrucForm.getKpiStrucModel().setUpdatedBy(user.getFullName());
		String createStr = kpiStrucForm.getCreateDate();
		Timestamp timestamp = Timestamp.valueOf(createStr);
		kpiStrucForm.getKpiStrucModel().setCreatedDate(timestamp);
		ResultService rs = service.updateKpiStruc(kpiStrucForm.getKpiStrucModel());
		String pageNo = kpiStrucForm.getPageNo().toString();
		String pageSize = kpiStrucForm.getPageSize();
		String keySearch = kpiStrucForm.getKeySearch();
		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", pageNo); 
		response.setRenderParameter("pageSize", pageSize);
		response.setRenderParameter("messageCode", rs.getMsgCode());
		response.setRenderParameter("messageDesc", rs.getMsgDesc());
	}
	
	@RequestMapping(params = "action=doDelete")
	public void actionDelete(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("kpiStrucForm") KpiStrucForm kpiStrucForm,
			BindingResult result, Model model) {
		User user = (User) request.getAttribute(WebKeys.USER);
		KpiStrucModel kpiStrucModel = new KpiStrucModel();
		kpiStrucModel.setStrucId(kpiStrucForm.getKpiStrucModel().getStrucId());		
		//service.deleteKpiStruc(kpiStrucModel);
		String messageDesc ="";
		String messageCode ="";
		int recoedCount=service.deleteKpiStruc(kpiStrucModel);
		if(recoedCount == -9){
			//model.addAttribute(ServiceConstant.ERROR_MESSAGE_KEY, ServiceConstant.ERROR_CONSTRAINT_VIOLATION_MESSAGE_CODE);
			messageDesc = ServiceConstant.ERROR_CONSTRAINT_VIOLATION_MESSAGE_CODE;
			messageCode = "0";
		}
		//Render to list page.
		String pageNo = kpiStrucForm.getPageNo().toString();
		String pageSize = kpiStrucForm.getPageSize();
		String keySearch = kpiStrucForm.getKeySearch();
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
			@ModelAttribute("kpiStrucForm") KpiStrucForm kpiStrucForm,
			BindingResult result, Model model) {
		/*
		 * User user = (User) request.getAttribute(WebKeys.USER);
		 * kpiStrucForm.getKpiStrucModel().setAcademicYear(sysYear); keySearch =
		 * "aa"; KpiStrucModel kpiStrucModel =new KpiStrucModel ();
		 * kpiStrucModel.setKeySearch(keySearch); List<KpiStrucModel> strucs =
		 * service.searchKpiStruc(kpiStrucModel);
		 * //model.addAttribute("strucs",strucs); //return n;
		 */
		String keySearch = kpiStrucForm.getKeySearch();
		String pageSize = kpiStrucForm.getPageSize();
		response.setRenderParameter("render", "listSearch");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageSize", pageSize);
	}
	@RequestMapping(params = "action=doFilter")
	public void actionFilterGroup(javax.portlet.ActionRequest request,javax.portlet.ActionResponse response,
			@ModelAttribute("kpiStrucForm") KpiStrucForm kpiStrucForm,BindingResult result, Model model){
		response.setRenderParameter("render", "filter");
		request.setAttribute("get", kpiStrucForm );
	}
	@RequestMapping("VIEW")
	@RenderMapping(params = "render=filter")
	public String RenderFilterGroup(javax.portlet.PortletRequest request, Model model) {
		KpiStrucForm form = (KpiStrucForm) request.getAttribute("get");
		KpiStrucModel kpiStrucModel = new KpiStrucModel();
		KpiGroupModel kpiGroupModel = new KpiGroupModel();
		StrucTypeModel strucTypeModel = new StrucTypeModel();		
		Paging page = new Paging(); //default pageNo = 1
		kpiStrucModel.setPaging(page);
		kpiStrucModel.getPaging().setPageSize( Integer.parseInt(form.getPageSize()) );		
		kpiStrucModel.setKeySearch(form.getKeySearch());
		kpiStrucModel.setGroupId( form.getKpiStrucModel().getGroupId()  );
		List<KpiGroupModel> groups = service.searchKpiGroup(kpiGroupModel);
		List<StrucTypeModel> strucTypes = service.searchStrucType(strucTypeModel);
		List<KpiStrucModel> strucs = service.searchKpiStruc(kpiStrucModel);
		
		model.addAttribute("strucs", strucs);
		model.addAttribute("groups", groups);
		model.addAttribute("strucTypes", strucTypes);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", 1);
		model.addAttribute("pageSize", form.getPageSize() );
		model.addAttribute("keySearch", form.getKeySearch() );
		model.addAttribute("groupId", form.getKpiStrucModel().getGroupId() );
		return "master/KpiStruc";
	}
	@RequestMapping(params = "action=doListPage")
	public void actionListPage(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("kpiStrucForm") KpiStrucForm kpiStrucForm,
			BindingResult result, Model model) {
		String pageNo = kpiStrucForm.getPageNo().toString();
		String pageSize = kpiStrucForm.getPageSize();
		String keySearch = kpiStrucForm.getKeySearch();
		String messageCode = "";
		String messageDesc = "";
		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", pageNo);
		response.setRenderParameter("pageSize", pageSize);
		response.setRenderParameter("messageDesc", messageDesc);
		response.setRenderParameter("messageCode", messageCode);
	}
	
	@RequestMapping(params = "action=doPageSize")
	public void actionPageSize(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("kpiStrucForm") KpiStrucForm kpiStrucForm,
			BindingResult result, Model model) {
		String pageSize = kpiStrucForm.getPageSize();
		String keySearch = kpiStrucForm.getKeySearch();
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
		KpiStrucModel kpiStrucModel = new KpiStrucModel();
		KpiGroupModel kpiGroupModel = new KpiGroupModel();
		StrucTypeModel strucTypeModel = new StrucTypeModel();		
		kpiStrucModel.setKeySearch(keySearch);
		Paging page = new Paging(); //default pageNo = 1
		kpiStrucModel.setPaging(page);
		kpiStrucModel.getPaging().setPageSize(pageSize);				
		List<KpiGroupModel> groups = service.searchKpiGroup(kpiGroupModel);
		List<StrucTypeModel> strucTypes = service.searchStrucType(strucTypeModel);
		List<KpiStrucModel> strucs = service.searchKpiStruc(kpiStrucModel);
		
		model.addAttribute("strucs", strucs);
		model.addAttribute("groups", groups);
		model.addAttribute("strucTypes", strucTypes);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", 1);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("keySearch", keySearch);
		return "master/KpiStruc";
	}
	
	@RequestMapping("VIEW")
	@RenderMapping(params = "render=listPage")
	public String RenderListPage(@RequestParam("keySearch") String keySearch,
			@RequestParam("pageNoStr") String pageNoStr,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("messageDesc") String messageDesc,
			@RequestParam("messageCode") String messageCode, 
			Model model) {
		KpiStrucModel kpiStrucModel = new KpiStrucModel();
		KpiGroupModel kpiGroupModel = new KpiGroupModel();
		StrucTypeModel strucTypeModel = new StrucTypeModel();
		kpiStrucModel.setKeySearch(keySearch);
		Paging page = new Paging(Integer.parseInt(pageNoStr), 10, "ASC");
		kpiStrucModel.setPaging(page);
		kpiStrucModel.getPaging().setPageSize(pageSize);
		
		List<KpiGroupModel> groups = service.searchKpiGroup(kpiGroupModel);
		List<StrucTypeModel> strucTypes = service.searchStrucType(strucTypeModel);
		List<KpiStrucModel> strucs = service.searchKpiStruc(kpiStrucModel);	
		
		model.addAttribute("strucs", strucs);
		model.addAttribute("groups", groups);
		model.addAttribute("strucTypes", strucTypes);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", pageNoStr);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("messageCode", messageCode);
		model.addAttribute("messageDesc", messageDesc);
		model.addAttribute("keySearch", keySearch);
		return "master/KpiStruc";
	}
	
	@RequestMapping("VIEW")
	@RenderMapping(params = "render=actionList")
	public String RenderInsert(@RequestParam("messageCode") String messageCode, 
			@RequestParam("messageDesc") String messageDesc, Model model) {
		KpiStrucModel kpiStrucModel = new KpiStrucModel();
		KpiGroupModel kpiGroupModel = new KpiGroupModel();
		StrucTypeModel strucTypeModel = new StrucTypeModel();
		Paging page = new Paging(); //default pageNo=1
		kpiStrucModel.setPaging(page);
		kpiStrucModel.getPaging().setPageSize(10);
		
		List<KpiGroupModel> groups = service.searchKpiGroup(kpiGroupModel);
		List<StrucTypeModel> strucTypes = service.searchStrucType(strucTypeModel);
		List<KpiStrucModel> strucs = service.searchKpiStruc(kpiStrucModel);		
		model.addAttribute("strucs", strucs);
		model.addAttribute("groups", groups);
		model.addAttribute("strucTypes", strucTypes);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", 1);
		model.addAttribute("messageCode", messageCode);
		model.addAttribute("messageDesc", messageDesc);
		return "master/KpiStruc";
	}
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
