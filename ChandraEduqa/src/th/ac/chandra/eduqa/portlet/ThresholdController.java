package th.ac.chandra.eduqa.portlet;

//import java.sql.Date;
import java.math.BigInteger;
import java.sql.Timestamp;
//import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
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
import th.ac.chandra.eduqa.form.ThresholdForm;
import th.ac.chandra.eduqa.mapper.CustomObjectMapper;
import th.ac.chandra.eduqa.mapper.ResultService;
import th.ac.chandra.eduqa.model.KpiLevelModel;
import th.ac.chandra.eduqa.model.SysYearModel;
import th.ac.chandra.eduqa.model.ThresholdModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.Paging;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;

@Controller("thresholdController")
@RequestMapping("VIEW")
@SessionAttributes({ "thresholdForm" })
public class ThresholdController {
	private static final Logger logger = Logger
			.getLogger(ThresholdController.class);
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
		ThresholdForm thresholdForm = null;
		if (!model.containsAttribute("thresholdForm")) {
			thresholdForm = new ThresholdForm();
			model.addAttribute("thresholdForm", thresholdForm);
		} else {
			thresholdForm = (ThresholdForm) model.asMap().get("thresholdForm");
		}
		ThresholdModel thresholdModel = new ThresholdModel();
		KpiLevelModel kpiLevelModel = new KpiLevelModel();
		String keySearch = thresholdForm.getKeySearch();
		Paging page = new Paging(); //default pageNo = 1
		
		List<KpiLevelModel> kpiLevels = service.searchKpiLevel(kpiLevelModel);
		KpiLevelModel fLevel = new KpiLevelModel();
		fLevel = kpiLevels.get(0);
		String fristLevel = fLevel.getLevelId().toString();
		if(keySearch == null){
			thresholdModel.setKeySearch(fristLevel);
		}else{
			thresholdModel.setKeySearch(keySearch);
		}
		thresholdModel.setPaging(page);

		List<ThresholdModel> thresholds = service.searchThreshold(thresholdModel);
		model.addAttribute("thresholds", thresholds);
		model.addAttribute("levels", kpiLevels);
		model.addAttribute("firstLevel", fristLevel);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("keySearch", keySearch);
		model.addAttribute("PageCur", "1");
		return "master/Threshold";
	}

	@RequestMapping(params = "action=doInsert")
	public void actionInsert(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("thresholdForm") ThresholdForm thresholdForm,
			BindingResult result, Model model) {
		User user = (User) request.getAttribute(WebKeys.USER);
		thresholdForm.getThresholdModel().setThresholdId(null);
		thresholdForm.getThresholdModel().setAcademicYear(getCurrentYear());
		thresholdForm.getThresholdModel().setCreatedBy(user.getFullName());
		thresholdForm.getThresholdModel().setUpdatedBy(user.getFullName());
		String keySearch = thresholdForm.getKeySearch();
		//String pageNo = thresholdForm.getPageNo().toString();
		//String pageSize = thresholdForm.getPageSize();
		ResultService rs = service.saveThreshold(thresholdForm.getThresholdModel());
		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", "1");
		response.setRenderParameter("pageSize", "20");
		response.setRenderParameter("messageCode", rs.getMsgCode());
		response.setRenderParameter("messageDesc", rs.getMsgDesc());
		//Render to "VIEW"
	}
	
	@RequestMapping(params = "action=doEdit")
	public void actionUpdate(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("thresholdForm") ThresholdForm thresholdForm,
			BindingResult result, Model model) throws ParseException {
		User user = (User) request.getAttribute(WebKeys.USER);
		thresholdForm.getThresholdModel().setAcademicYear(getCurrentYear());
		thresholdForm.getThresholdModel().setUpdatedBy(user.getFullName());
		String keySearch = thresholdForm.getKeySearch();
		//String pageNo = thresholdForm.getPageNo().toString();
		//String pageSize = thresholdForm.getPageSize();
		String createStr = thresholdForm.getCreateDate();
		Timestamp timestamp = Timestamp.valueOf(createStr);
		thresholdForm.getThresholdModel().setCreatedDate(timestamp);
		ResultService  rs = service.updateThreshold(thresholdForm.getThresholdModel());
		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", "1");
		response.setRenderParameter("pageSize", "20");		
		response.setRenderParameter("messageCode", rs.getMsgCode());
		response.setRenderParameter("messageDesc", rs.getMsgDesc());
	}
	
	@RequestMapping(params = "action=doDelete")
	public void actionDelete(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("thresholdForm") ThresholdForm thresholdForm,
			BindingResult result, Model model) {
		User user = (User) request.getAttribute(WebKeys.USER);
		ThresholdModel thresholdModel = new ThresholdModel();
		thresholdModel.setThresholdId(thresholdForm.getThresholdModel().getThresholdId());		
		//service.deleteThreshold(thresholdModel);
		String messageDesc ="";
		String messageCode ="";
		int recoedCount=service.deleteThreshold(thresholdModel);
		if(recoedCount == -9){
			//model.addAttribute(ServiceConstant.ERROR_MESSAGE_KEY, ServiceConstant.ERROR_CONSTRAINT_VIOLATION_MESSAGE_CODE);
			messageDesc = ServiceConstant.ERROR_CONSTRAINT_VIOLATION_MESSAGE_CODE;
			messageCode = "0";
		}
		//Render to list page.
		//String pageNo = thresholdForm.getPageNo().toString();
		String pageNo = "1";
		String pageSize = thresholdForm.getPageSize();
		String keySearch = thresholdForm.getKeySearch();
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
			@ModelAttribute("thresholdForm") ThresholdForm thresholdForm,
			BindingResult result, Model model) {
		/*
		 * User user = (User) request.getAttribute(WebKeys.USER);
		 * thresholdForm.getThresholdModel().setAcademicYear(sysYear); keySearch =
		 * "aa"; ThresholdModel thresholdModel =new ThresholdModel ();
		 * thresholdModel.setKeySearch(keySearch); List<ThresholdModel> thresholds =
		 * service.searchThreshold(thresholdModel);
		 * //model.addAttribute("thresholds",thresholds); //return n;
		 */
		String keySearch = thresholdForm.getKeySearch();
		String pageSize = thresholdForm.getPageSize();
		response.setRenderParameter("render", "listSearch");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageSize", pageSize);
	}
	
	@RequestMapping(params = "action=doListPage")
	public void actionListPage(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("thresholdForm") ThresholdForm thresholdForm,
			BindingResult result, Model model) {
		String pageNo = thresholdForm.getPageNo().toString();
		String pageSize = thresholdForm.getPageSize();
		String keySearch = thresholdForm.getKeySearch();
		String messageCode = "";
		String messageDesc = "";
		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", pageNo);
		response.setRenderParameter("pageSize", pageSize);
		response.setRenderParameter("messageDesc", messageDesc);
		response.setRenderParameter("messageCode", messageCode);
	}

	@RequestMapping("VIEW")
	@RenderMapping(params = "render=listSearch")
	public String RenderSearch(@RequestParam("keySearch") String keySearch,
			@RequestParam("pageSize") int pageSize, Model model) {
		ThresholdModel thresholdModel = new ThresholdModel();
		KpiLevelModel kpiLevelModel = new KpiLevelModel();
		Paging page = new Paging(); //default pageNo = 1
		thresholdModel.setKeySearch(keySearch);		
		thresholdModel.setPaging(page);
		thresholdModel.getPaging().setPageSize(pageSize);
		
		List<KpiLevelModel> levels = service.searchKpiLevel(kpiLevelModel);
		List<ThresholdModel> thresholds = service.searchThreshold(thresholdModel);
		model.addAttribute("thresholds", thresholds);
		model.addAttribute("levels", levels);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", 1);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("keySearch", keySearch);
		return "master/Threshold";
	}
	
	@RequestMapping("VIEW")
	@RenderMapping(params = "render=listPage")
	public String RenderListPage(@RequestParam("keySearch") String keySearch,
			@RequestParam("pageNoStr") String pageNoStr,
			@RequestParam("pageSize") String pageSize,
			@RequestParam("messageDesc") String messageDesc,
			@RequestParam("messageCode") String messageCode, 
			Model model) {
		ThresholdModel thresholdModel = new ThresholdModel();
		KpiLevelModel kpiLevelModel = new KpiLevelModel();
		Paging page = new Paging(Integer.parseInt(pageNoStr), 10, "ASC");
		thresholdModel.setKeySearch(keySearch);		
		thresholdModel.setPaging(page);
		thresholdModel.getPaging().setPageSize(Integer.parseInt(pageSize));
		
		List<KpiLevelModel> levels = service.searchKpiLevel(kpiLevelModel);
		List<ThresholdModel> thresholds = service.searchThreshold(thresholdModel);
		model.addAttribute("thresholds", thresholds);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("levels", levels);
		model.addAttribute("keySearch", keySearch);
		model.addAttribute("PageCur", pageNoStr);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("messageCode", messageCode);
		model.addAttribute("messageDesc", messageDesc);
		return "master/Threshold";
	}
	
	/*@RequestMapping("VIEW")
	@RenderMapping(params = "render=actionList")
	public String RenderInsert(@RequestParam("messageCode") String messageCode, 
			@RequestParam("messageDesc") String messageDesc, Model model) {
		ThresholdModel thresholdModel = new ThresholdModel();
		Paging page = new Paging(); //default pageNo=1
		thresholdModel.setPaging(page);
		thresholdModel.getPaging().setPageSize(10);
		List<ThresholdModel> thresholds = service.searchThreshold(thresholdModel);
		model.addAttribute("thresholds", thresholds);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", 1);
		model.addAttribute("messageCode", messageCode);
		model.addAttribute("messageDesc", messageDesc);
		return "master/Threshold";
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
