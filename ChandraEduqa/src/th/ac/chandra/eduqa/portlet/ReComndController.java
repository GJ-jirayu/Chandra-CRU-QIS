package th.ac.chandra.eduqa.portlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import th.ac.chandra.eduqa.constant.ServiceConstant;
import th.ac.chandra.eduqa.form.HierarchyAuthorityForm;
import th.ac.chandra.eduqa.form.KpiReComndForm;
import th.ac.chandra.eduqa.mapper.CustomObjectMapper;
import th.ac.chandra.eduqa.model.DescriptionModel;
import th.ac.chandra.eduqa.model.KpiReComndModel;
import th.ac.chandra.eduqa.model.SysMonthModel;
import th.ac.chandra.eduqa.model.SysYearModel;
import th.ac.chandra.eduqa.model.KpiLevelModel;
import th.ac.chandra.eduqa.model.KpiGroupModel;
import th.ac.chandra.eduqa.model.OrgModel;
//import th.ac.chandra.eduqa.model.ReComndModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.Paging;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

@Controller("reComndController")
@RequestMapping("VIEW")
@SessionAttributes({ "reComndForm" })
public class ReComndController {
	private static final Logger logger = Logger
			.getLogger(ReComndController.class);
	@Autowired
	@Qualifier("eduqaServiceWSImpl")
	private EduqaService service;
	
	// ชั่วคราว	
	private Integer sysYear = 2558;
	
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
		KpiReComndForm reComndForm = null;
		if (!model.containsAttribute("reComndForm")) {
			reComndForm = new KpiReComndForm();
			model.addAttribute("reComndForm", reComndForm);
		} else {
			reComndForm = (KpiReComndForm) model.asMap().get("reComndForm");
		}
		
		//Get Org
		User user = (User) request.getAttribute(WebKeys.USER);
		DescriptionModel userOrg = new DescriptionModel();
		userOrg.setDescription(user.getScreenName());
		userOrg = service.getOrgOfUser(userOrg);
		
		OrgModel org = new OrgModel();
		org.setOrgId(Integer.parseInt(userOrg.getDescCode()));
		org = service.findOrgById(org);
		
		//Get calendar_year from SysMontModel 
		/*SysMonthModel sysMonthModel = new SysMonthModel();
		List<SysMonthModel> sysMonths = service.searchSysMonth(sysMonthModel);
		Map<Integer,Integer> acadYears = new HashMap<Integer,Integer>();
		for(SysMonthModel month : sysMonths){
			acadYears.put(month.getAcademicYear(),month.getAcademicYear());
		}*/
		SysYearModel sy = service.getSysYear();
		SysMonthModel monthM = new SysMonthModel();
		monthM.setAcademicYear( sy.getAppraisalAcademicYear() );
		Map<String,String> yearList = new LinkedHashMap<String,String>();
		List<SysMonthModel> sysMonths = service.getYearsByAcad(monthM);
		for(SysMonthModel month : sysMonths){
			yearList.put(month.getCalendarYear().toString() ,month.getCalendarYear().toString());
		}
				
		//Get KPI level
		KpiLevelModel kpiLevelModel = new KpiLevelModel();
		List<KpiLevelModel> resultlevels = service.searchKpiLevel(kpiLevelModel);
		Map<Integer,String> levels = new HashMap<Integer,String>();
		for(KpiLevelModel level : resultlevels){
			levels.put(level.getLevelId(),level.getDesc());
		}				
		//Get KPI Group
		KpiGroupModel kpiGroupModel = new KpiGroupModel();
		List<KpiGroupModel> resultgroups = service.searchKpiGroup(kpiGroupModel);
		Map<Integer,String> groups = new HashMap<Integer,String>();
		for(KpiGroupModel group : resultgroups){
			groups.put(group.getGroupId(), group.getGroupName());
		}
		//get univerisity list
		List<OrgModel> resultunis = service.getAllUniversity(org);
		Map<Integer,String> unis = new HashMap<Integer,String>();
		for(OrgModel uni : resultunis){
			unis.put(uni.getOrgId(), uni.getUniversityName());
		}
		//get faculty list
				List<OrgModel> resfacs = service.getOrgFacultyOfUniversity(org);
				Map<Integer,String> facs = new HashMap<Integer,String>();
				for(OrgModel fac : resfacs){
					facs.put(fac.getOrgId(), fac.getFacultyName());
				}		
				//get course list
				List<OrgModel> resCors = service.getOrgCourseOfFaculty(org);
				Map<Integer,String> cors = new HashMap<Integer,String>();
				for(OrgModel cor : resCors){
					cors.put(cor.getOrgId(), cor.getCourseName());
				}
		//form
		KpiReComndForm comndForm = new KpiReComndForm();
		KpiReComndModel comndModel = new KpiReComndModel();
		HierarchyAuthorityForm hieAuth  = new HierarchyAuthorityForm();
		
		//Get system config year from sysYear
		SysYearModel sysYear = new SysYearModel();
		sysYear = (SysYearModel) service.searchSysYear(sysYear).get(0);
		
		//add dropdown items
		//Get Recommend Model (deprecate)
		//KpiReComndModel kpiReComndModel = new KpiReComndModel();
		//String keySearch = kpiReComndModel.getKeySearch();
		//kpiReComndModel.setKeySearch(keySearch);
		//List<KpiReComndModel> reComnds = service.searchKpiReComnd(kpiReComndModel);
		//model.addAttribute("reComnds", reComnds);
		model.addAttribute("acadYears", yearList);
		model.addAttribute("groups", groups);
		model.addAttribute("levels", levels);
		model.addAttribute("unis",unis);
		model.addAttribute("facultys",facs);
		model.addAttribute("courses",cors);
		
		//add form with  default filter
		comndModel.setAcademicYear(sysYear.getAppraisalAcademicYear());
		comndModel.setOrgId(org.getOrgId());
		comndModel.setGroupId(resultgroups.get(0).getGroupId());
		hieAuth.setOrgId(org.getOrgId());
		hieAuth.setLevel(org.getLevelId());
		hieAuth.setUniversity(org.getUniversityCode());
		hieAuth.setFaculty(org.getFacultyCode());
		hieAuth.setCourse(org.getCourseCode());
		comndForm.setModel(comndModel);
		comndForm.setHieAuth(hieAuth);
		model.addAttribute("comndForm", comndForm);
		model.addAttribute("currentFaculty", (org.getFacultyCode() == null ? 0 : org.getFacultyCode()));
		model.addAttribute("currentCourse", (org.getCourseCode() == null ? 0 : org.getCourseCode()));
		return "master/ReComnd";
	}
	
	@SuppressWarnings("unchecked")
	@ResourceMapping(value="requestOrgList")
	@ResponseBody 
	public void requestOrgList(ResourceRequest request,ResourceResponse response) throws IOException{
		JSONObject json = JSONFactoryUtil.createJSONObject();
		
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest =PortalUtil.getOriginalServletRequest(httpReq);
		Integer orgId = Integer.parseInt(normalRequest.getParameter("orgId"));
		Integer level = Integer.parseInt(normalRequest.getParameter("level"));
		
		//paramOrg
		OrgModel paramOrg = new OrgModel();
		paramOrg.setOrgId(orgId);
		paramOrg = service.findOrgById(paramOrg);

		//userOrg
		User user = (User) request.getAttribute(WebKeys.USER);
		DescriptionModel userOrg = new DescriptionModel();
		userOrg.setDescription(user.getScreenName());
		userOrg = service.getOrgOfUser(userOrg);
		
		paramOrg.setOrgId( Integer.parseInt(userOrg.getDescCode() ));
		
		JSONArray lists = JSONFactoryUtil.createJSONArray();
		@SuppressWarnings("unchecked")
		List<OrgModel> details = new ArrayList<OrgModel>();

		if(level==1){
			for(OrgModel detail:details ){
				JSONObject connJSON = JSONFactoryUtil.createJSONObject();
	         	connJSON.put("id", detail.getUniversityCode());
	         	connJSON.put("name", detail.getUniversityName());
	         	lists.put(connJSON);
	         }
		}
		else if(level==2){
			details = service.getOrgFacultyOfUniversity(paramOrg);
			for(OrgModel detail:details ){
				JSONObject connJSON = JSONFactoryUtil.createJSONObject();
	         	connJSON.put("id", detail.getFacultyCode());
	         	connJSON.put("name", detail.getFacultyName());
	         	lists.put(connJSON);
	         }
		}
		else if(level==3){
			details = service.getOrgCourseOfFaculty(paramOrg);
			for(OrgModel detail:details ){
				JSONObject connJSON = JSONFactoryUtil.createJSONObject();
	         	connJSON.put("id", detail.getCourseCode());
	         	connJSON.put("name", detail.getCourseName());
	         	lists.put(connJSON);
	         }
		}
		
		json.put("lists", lists);
		
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	
	/** bind element event **
	 * @author Wirun [GJ] 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@ResourceMapping(value="requestRecomndList")
	@ResponseBody 
	public void requestRecomndList(ResourceRequest request,ResourceResponse response) throws IOException{
		JSONObject json = JSONFactoryUtil.createJSONObject();
		
		//Integer groupId = ParamUtil.getInteger(request, "groupId");
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest =PortalUtil.getOriginalServletRequest(httpReq);
		String paramYear = normalRequest.getParameter("paramYear");
		String paramGroup = normalRequest.getParameter("paramGroup");
		String patamOrg = normalRequest.getParameter("paramOrg");
		String[] otherKeySearch = {paramYear, paramGroup, patamOrg};
		KpiReComndModel kpiRecomndModel = new KpiReComndModel();
		kpiRecomndModel.setOtherKeySearch(otherKeySearch);
				
		List<KpiReComndModel> details = service.searchKpiReComnd(kpiRecomndModel);
		JSONArray lists = JSONFactoryUtil.createJSONArray();
		for(KpiReComndModel detail:details ){
			JSONObject connJSON = JSONFactoryUtil.createJSONObject();
         	connJSON.put("reComndId", detail.getReComndId());
         	connJSON.put("orgId", detail.getOrgId());
         	connJSON.put("groupId", detail.getGroupId());
         	connJSON.put("academicYear", detail.getAcademicYear());
         	connJSON.put("reComndFlag", detail.getReComndFlag());
         	connJSON.put("reComndDesc", detail.getReComndDesc());
         	connJSON.put("createdBy", detail.getCreatedBy());
         	connJSON.put("createdDate", detail.getCreatedDate());
         	lists.put(connJSON);
         }
		json.put("recomndLists", lists);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}

	@ResourceMapping(value="requestRecomndInsert")
	@ResponseBody 
	public void requestRecomndInsert(ResourceRequest request,ResourceResponse response,
			@ModelAttribute("reComndForm") KpiReComndForm reComndForm) throws IOException{
		
		JSONObject json = JSONFactoryUtil.createJSONObject();
		//Integer groupId = ParamUtil.getInteger(request, "groupId");
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest =PortalUtil.getOriginalServletRequest(httpReq);
		Integer valYear = Integer.parseInt(normalRequest.getParameter("valYear"));
		Integer valGroup = Integer.parseInt(normalRequest.getParameter("valGroup"));
		Integer valOrg = Integer.parseInt(normalRequest.getParameter("valOrg"));
		String valFlag = normalRequest.getParameter("valFlag");
		String valDesc = normalRequest.getParameter("valDesc");		
		User user = (User) request.getAttribute(WebKeys.USER);
		reComndForm.getModel().setReComndId(null);
		reComndForm.getModel().setAcademicYear(valYear);
		reComndForm.getModel().setGroupId(valGroup);
		reComndForm.getModel().setOrgId(valOrg);
		reComndForm.getModel().setReComndFlag(valFlag);
		reComndForm.getModel().setReComndDesc(valDesc);
		reComndForm.getModel().setCreatedBy(user.getFullName());
		reComndForm.getModel().setUpdatedBy(user.getFullName());
		
		Integer status = service.saveKpiReComnd(reComndForm.getModel());
		String msg = ServiceConstant.stat.get(status);
		json.put("insertStatus", status.toString());
		json.put("insertMsg", msg);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	
	@ResourceMapping(value="requestRecomndUpdate")
	@ResponseBody 
	public void requestRecomndUpdate(ResourceRequest request,ResourceResponse response,
			@ModelAttribute("reComndForm") KpiReComndForm reComndForm) throws IOException{
		User user = (User) request.getAttribute(WebKeys.USER);
		JSONObject json = JSONFactoryUtil.createJSONObject();
		//Integer groupId = ParamUtil.getInteger(request, "groupId");
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest =PortalUtil.getOriginalServletRequest(httpReq);
		Integer valId = Integer.parseInt(normalRequest.getParameter("valId"));
		Integer valYear = Integer.parseInt(normalRequest.getParameter("valYear"));
		Integer valGroup = Integer.parseInt(normalRequest.getParameter("valGroup"));
		Integer valOrg = Integer.parseInt(normalRequest.getParameter("valOrg"));
		String valFlag = normalRequest.getParameter("valFlag");
		String valDesc = normalRequest.getParameter("valDesc");
		String valCreateDate = normalRequest.getParameter("valCreateDate");
		String valCreateBy = normalRequest.getParameter("valCreateBy");
		Timestamp timestamp = Timestamp.valueOf(valCreateDate);
		
		reComndForm.getModel().setReComndId(valId);
		reComndForm.getModel().setAcademicYear(valYear);
		reComndForm.getModel().setGroupId(valGroup);
		reComndForm.getModel().setOrgId(valOrg);
		reComndForm.getModel().setReComndFlag(valFlag);
		reComndForm.getModel().setReComndDesc(valDesc);
		reComndForm.getModel().setCreatedBy(valCreateBy);
		reComndForm.getModel().setCreatedDate(timestamp);
		reComndForm.getModel().setUpdatedBy(user.getFullName());
		
		Integer status = service.updateKpiReComnd(reComndForm.getModel());
		String msg = ServiceConstant.stat.get(status);
		json.put("updateMsgCode", status.toString());
		json.put("updateMsgDesc", msg);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	
	@ResourceMapping(value="requestRecomndDelete")
	@ResponseBody 
	public void requestRecomndDelete(ResourceRequest request,ResourceResponse response,
			@ModelAttribute("reComndForm") KpiReComndForm reComndForm) throws IOException{
		
		JSONObject json = JSONFactoryUtil.createJSONObject();
		//Integer groupId = ParamUtil.getInteger(request, "groupId");
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest =PortalUtil.getOriginalServletRequest(httpReq);
		Integer recomndId = Integer.parseInt(normalRequest.getParameter("recomndId"));
		reComndForm.getModel().setReComndId(recomndId);
		String msgDesc = "";
		String msgCode = "";
		Integer status = service.deleteKpiReComnd(reComndForm.getModel());
		if(status == -9){
			//model.addAttribute(ServiceConstant.ERROR_MESSAGE_KEY, ServiceConstant.ERROR_CONSTRAINT_VIOLATION_MESSAGE_CODE);
			msgCode = "0";
			msgDesc = ServiceConstant.ERROR_CONSTRAINT_VIOLATION_MESSAGE_CODE;			
		}else{
			msgCode = "1";
			msgDesc = "Seve success";
		}
		
		json.put("deleteMsgCode", msgCode);
		json.put("deleteMsgDesc", msgDesc);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	
	@ResourceMapping(value = "dofindOrgByUserName")
	@ResponseBody
	public void dofindOrgByUserName(ResourceRequest request, ResourceResponse response) throws IOException {
		JSONObject json = JSONFactoryUtil.createJSONObject();
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest = PortalUtil.getOriginalServletRequest(httpReq);
	
		User user = (User) request.getAttribute(WebKeys.USER);
		DescriptionModel userOrg = new DescriptionModel();
		userOrg.setDescription(user.getScreenName());
		userOrg = service.getOrgOfUser(userOrg);
		String UserOrgId = userOrg.getDescCode();
		
		OrgModel org = new OrgModel();
		org.setOrgId(Integer.parseInt(UserOrgId));
		
		OrgModel orgs = service.findOrgById(org);
		JSONArray lists = JSONFactoryUtil.createJSONArray();
		JSONObject connJSON = JSONFactoryUtil.createJSONObject();
		connJSON.put("levelId", orgs.getLevelId());
		
        lists.put(connJSON);
		json.put("userRoleId", lists);
		
		//System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	
	@ResourceMapping(value = "dofindOrgIdByFilter")
	@ResponseBody
	public void dofindOrgIdByFilter(ResourceRequest request, ResourceResponse response) throws IOException {
		JSONObject json = JSONFactoryUtil.createJSONObject();
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest = PortalUtil.getOriginalServletRequest(httpReq);
		
		String valUniversity = normalRequest.getParameter("valUniversity");
		String valFaculty = normalRequest.getParameter("valFaculty");
		String valCourse = normalRequest.getParameter("valCourse");
	
		
		OrgModel org = new OrgModel();
		org.setUniversityCode(valUniversity);
		org.setFacultyCode(valFaculty);
		org.setCourseCode(valCourse);
		
		OrgModel orgModel = service.searchOrg(org);
		JSONArray lists = JSONFactoryUtil.createJSONArray();
		JSONObject connJSON = JSONFactoryUtil.createJSONObject();
		connJSON.put("orgId", orgModel.getLevelId());
		
        lists.put(connJSON);
		json.put("userRoleId", lists);
		
		response.getWriter().write(json.toString());
	}
	
	@ResourceMapping(value = "dofindOrgByOrgId")
	@ResponseBody
	public void dofindOrgByOrgId(ResourceRequest request, ResourceResponse response) throws IOException {
		JSONObject json = JSONFactoryUtil.createJSONObject();
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest = PortalUtil.getOriginalServletRequest(httpReq);
	
		Integer orgId = Integer.parseInt( normalRequest.getParameter("orgId"));
		OrgModel org = new OrgModel();
		org.setOrgId(orgId);
		
		OrgModel orgs = service.findOrgById(org);
		JSONArray lists = JSONFactoryUtil.createJSONArray();
		JSONObject connJSON = JSONFactoryUtil.createJSONObject();
		connJSON.put("facultyCode", orgs.getFacultyCode());
        connJSON.put("facultyName", orgs.getFacultyName());
        connJSON.put("courseCode", orgs.getCourseCode());
        connJSON.put("courseName", orgs.getCourseName());
        
        User user = (User) request.getAttribute(WebKeys.USER);
		DescriptionModel userOrg = new DescriptionModel();
		userOrg.setDescription(user.getScreenName());
		userOrg = service.getOrgOfUser(userOrg);
		String UserOrgId = userOrg.getDescCode();
		OrgModel orgByUser = service.findOrgById(org);
		connJSON.put("userRoleOrgId", UserOrgId);
		
        lists.put(connJSON);
		json.put("facultyCourseList", lists);
		
		//System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	
	@ResourceMapping(value="requestOrgFaculty")
	@ResponseBody 
	public void getOrgFaculty(ResourceRequest request,ResourceResponse response) throws IOException{
		JSONObject json = JSONFactoryUtil.createJSONObject();
		
		//Integer groupId = ParamUtil.getInteger(request, "groupId");
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest =PortalUtil.getOriginalServletRequest(httpReq);
		Integer levelId = Integer.parseInt(normalRequest.getParameter("levelId"));
		String university = normalRequest.getParameter("university");
		
		User user = (User) request.getAttribute(WebKeys.USER);
		DescriptionModel userOrg = new DescriptionModel();
		userOrg.setDescription(user.getScreenName());
		userOrg = service.getOrgOfUser(userOrg);
		
		OrgModel orgModel = new OrgModel();
		orgModel.setOrgId(Integer.parseInt(userOrg.getDescCode()));
		orgModel.setLevelId(levelId);
		orgModel.setUniversityCode(university);
		//orgModel.setOtherKeySearch(otherKeySearch);
				
		List<OrgModel> details = service.getOrgFacultyOfUniversity(orgModel);
		JSONArray lists = JSONFactoryUtil.createJSONArray();
		for(OrgModel detail:details ){
			JSONObject connJSON = JSONFactoryUtil.createJSONObject();
         	connJSON.put("id", detail.getFacultyCode());
         	connJSON.put("name", detail.getFacultyName());
         	lists.put(connJSON);
         }
		json.put("lists", lists);
		
		/*List<OrgModel> orgId = service.searchOrgIdByOthersCode(orgModel);		
		OrgModel orgIdModel = new OrgModel();
		orgIdModel = orgId.get(0);
		String orgIdStr = orgIdModel.getOrgId().toString();*/
		
		//System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	
	@ResourceMapping(value="requestOrgCourse")
	@ResponseBody
	public void getOrgCourse(ResourceRequest request,ResourceResponse response) throws IOException{
		JSONObject json = JSONFactoryUtil.createJSONObject();
		
		//Integer groupId = ParamUtil.getInteger(request, "groupId");
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		Integer levelId = Integer.parseInt( normalRequest.getParameter("levelId") );
		String uniCode  = normalRequest.getParameter("university");
		String facultyCode = normalRequest.getParameter("faculty");
		OrgModel orgModel = new OrgModel();
		orgModel.setLevelId(levelId);
		orgModel.setUniversityCode(uniCode);
		orgModel.setFacultyCode(facultyCode);
		
		List<OrgModel> details = service.getOrgCourseOfFaculty(orgModel);
		 JSONArray lists = 	JSONFactoryUtil.createJSONArray();
		for(OrgModel detail:details ){
			JSONObject connJSON = JSONFactoryUtil.createJSONObject();
         	connJSON.put("id", detail.getCourseCode());
         	connJSON.put("name", detail.getCourseName());
         	lists.put(connJSON);
         }     	
		json.put("lists", lists);
		//System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	
	@ResourceMapping(value="requestOrgIdByOrgDetailFilter")
	@ResponseBody
	public void getOrgIdByOrgDetailFilter(ResourceRequest request,ResourceResponse response) throws IOException{
		JSONObject json = JSONFactoryUtil.createJSONObject();
		
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		Integer paramLevel = Integer.parseInt(normalRequest.getParameter("paramLevel"));
		String paramUniversity = normalRequest.getParameter("paramUniversity");
		String paramFaculty  = normalRequest.getParameter("paramFaculty");
		String paramCourse = normalRequest.getParameter("paramCourse");		
		
		OrgModel orgModel = new OrgModel();
		orgModel.setLevelId(paramLevel);
		orgModel.setUniversityCode(paramUniversity);
		orgModel.setFacultyCode(paramFaculty);
		orgModel.setCourseCode(paramCourse);
		
		List<OrgModel> details = service.getOrgIdByOrgDetailFilter(orgModel);
		 JSONArray lists = 	JSONFactoryUtil.createJSONArray();
		for(OrgModel detail:details ){
			JSONObject connJSON = JSONFactoryUtil.createJSONObject();
         	connJSON.put("orgId", detail.getOrgId());
         	connJSON.put("levelId", detail.getLevelId());
         	connJSON.put("universityCode", detail.getUniversityCode());
         	connJSON.put("facultyCode", detail.getFacultyCode());
         	connJSON.put("courseCode", detail.getCourseCode());
         	lists.put(connJSON);
         }     	
		json.put("lists", lists);
		response.getWriter().write(json.toString());
	}
	
	
	/*@RequestMapping(params = "action=doInsert")
	public void actionInsert(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("reComndForm") KpiReComndForm reComndForm,
			BindingResult result, Model model) {
		User user = (User) request.getAttribute(WebKeys.USER);
		reComndForm.getKpiReComndModel().setReComndId(null);
		reComndForm.getKpiReComndModel().setAcademicYear(sysYear);
		reComndForm.getKpiReComndModel().setCreatedBy(user.getFullName());
		reComndForm.getKpiReComndModel().setUpdatedBy(user.getFullName());
		String keySearch = reComndForm.getKeySearch();
		//String pageNo = reComndForm.getPageNo().toString();
		//String pageSize = reComndForm.getPageSize();
		Integer msg = service.saveKpiReComnd(reComndForm.getKpiReComndModel());
		String statas = ServiceConstant.stat.get(msg);
		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", "1");
		response.setRenderParameter("pageSize", "20");
		response.setRenderParameter("messageCode", msg.toString());
		response.setRenderParameter("messageDesc", statas);
		//Render to "VIEW"
	}*/
	
	/*@RequestMapping(params = "action=doEdit")
	public void actionUpdate(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("reComndForm") KpiReComndForm reComndForm,
			BindingResult result, Model model) throws ParseException {
		User user = (User) request.getAttribute(WebKeys.USER);
		reComndForm.getReComndModel().setAcademicYear(sysYear);
		reComndForm.getReComndModel().setUpdatedBy(user.getFullName());
		String keySearch = reComndForm.getKeySearch();
		//String pageNo = reComndForm.getPageNo().toString();
		//String pageSize = reComndForm.getPageSize();
		String createStr = reComndForm.getCreateDate();
		Timestamp timestamp = Timestamp.valueOf(createStr);
		reComndForm.getReComndModel().setCreatedDate(timestamp);
		Integer msg = service.updateReComnd(reComndForm.getReComndModel());
		String statas = ServiceConstant.stat.get(msg);		
		response.setRenderParameter("render", "listPage");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageNoStr", "1");
		response.setRenderParameter("pageSize", "20");		
		response.setRenderParameter("messageCode", msg.toString());
		response.setRenderParameter("messageDesc", statas);
	}
	
	@RequestMapping(params = "action=doDelete")
	public void actionDelete(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("reComndForm") KpiReComndForm reComndForm,
			BindingResult result, Model model) {
		User user = (User) request.getAttribute(WebKeys.USER);
		ReComndModel reComndModel = new ReComndModel();
		reComndModel.setReComndId(reComndForm.getReComndModel().getReComndId());		
		//service.deleteReComnd(reComndModel);
		String messageDesc ="";
		String messageCode ="";
		int recoedCount=service.deleteReComnd(reComndModel);
		if(recoedCount == -9){
			//model.addAttribute(ServiceConstant.ERROR_MESSAGE_KEY, ServiceConstant.ERROR_CONSTRAINT_VIOLATION_MESSAGE_CODE);
			messageDesc = ServiceConstant.ERROR_CONSTRAINT_VIOLATION_MESSAGE_CODE;
			messageCode = "0";
		}
		//Render to list page.
		String pageNo = reComndForm.getPageNo().toString();
		String pageSize = reComndForm.getPageSize();
		String keySearch = reComndForm.getKeySearch();
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
			@ModelAttribute("reComndForm") KpiReComndForm reComndForm,
			BindingResult result, Model model) {
		
		 * User user = (User) request.getAttribute(WebKeys.USER);
		 * reComndForm.getReComndModel().setAcademicYear(sysYear); keySearch =
		 * "aa"; ReComndModel reComndModel =new ReComndModel ();
		 * reComndModel.setKeySearch(keySearch); List<ReComndModel> reComnds =
		 * service.searchReComnd(reComndModel);
		 * //model.addAttribute("reComnds",reComnds); //return n;
		 
		String keySearch = reComndForm.getKeySearch();
		String pageSize = reComndForm.getPageSize();
		response.setRenderParameter("render", "listSearch");
		response.setRenderParameter("keySearch", keySearch);
		response.setRenderParameter("pageSize", pageSize);
	}
	
	@RequestMapping(params = "action=doListPage")
	public void actionListPage(javax.portlet.ActionRequest request,
			javax.portlet.ActionResponse response,
			@ModelAttribute("reComndForm") KpiReComndForm reComndForm,
			BindingResult result, Model model) {
		String pageNo = reComndForm.getPageNo().toString();
		String pageSize = reComndForm.getPageSize();
		String keySearch = reComndForm.getKeySearch();
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
		ReComndModel reComndModel = new ReComndModel();
		KpiLevelModel kpiLevelModel = new KpiLevelModel();
		Paging page = new Paging(); //default pageNo = 1
		reComndModel.setKeySearch(keySearch);		
		reComndModel.setPaging(page);
		reComndModel.getPaging().setPageSize(pageSize);
		
		List<KpiLevelModel> levels = service.searchKpiLevel(kpiLevelModel);
		List<ReComndModel> reComnds = service.searchReComnd(reComndModel);
		model.addAttribute("reComnds", reComnds);
		model.addAttribute("levels", levels);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", 1);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("keySearch", keySearch);
		return "master/ReComnd";
	}
	
	@RequestMapping("VIEW")
	@RenderMapping(params = "render=listPage")
	public String RenderListPage(@RequestParam("keySearch") String keySearch,
			@RequestParam("pageNoStr") String pageNoStr,
			@RequestParam("pageSize") String pageSize,
			@RequestParam("messageDesc") String messageDesc,
			@RequestParam("messageCode") String messageCode, 
			Model model) {
		ReComndModel reComndModel = new ReComndModel();
		KpiLevelModel kpiLevelModel = new KpiLevelModel();
		Paging page = new Paging(Integer.parseInt(pageNoStr), 10, "ASC");
		reComndModel.setKeySearch(keySearch);		
		reComndModel.setPaging(page);
		reComndModel.getPaging().setPageSize(Integer.parseInt(pageSize));
		
		List<KpiLevelModel> levels = service.searchKpiLevel(kpiLevelModel);
		List<ReComndModel> reComnds = service.searchReComnd(reComndModel);
		model.addAttribute("reComnds", reComnds);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("levels", levels);
		model.addAttribute("keySearch", keySearch);
		model.addAttribute("PageCur", pageNoStr);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("messageCode", messageCode);
		model.addAttribute("messageDesc", messageDesc);
		return "master/ReComnd";
	}*/
	
	/*@RequestMapping("VIEW")
	@RenderMapping(params = "render=actionList")
	public String RenderInsert(@RequestParam("messageCode") String messageCode, 
			@RequestParam("messageDesc") String messageDesc, Model model) {
		ReComndModel reComndModel = new ReComndModel();
		Paging page = new Paging(); //default pageNo=1
		reComndModel.setPaging(page);
		reComndModel.getPaging().setPageSize(10);
		List<ReComndModel> reComnds = service.searchReComnd(reComndModel);
		model.addAttribute("reComnds", reComnds);
		model.addAttribute("lastPage", service.getResultPage());
		model.addAttribute("PageCur", 1);
		model.addAttribute("messageCode", messageCode);
		model.addAttribute("messageDesc", messageDesc);
		return "master/ReComnd";
	}*/
}
