package th.ac.chandra.eduqa.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import th.ac.chandra.eduqa.domain.CriteriaStandard;
import th.ac.chandra.eduqa.form.CdsForm;
import th.ac.chandra.eduqa.form.KpiForm;
import th.ac.chandra.eduqa.form.KpiListForm;
import th.ac.chandra.eduqa.mapper.CustomObjectMapper;
import th.ac.chandra.eduqa.mapper.ResultService;
import th.ac.chandra.eduqa.model.*;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.Paging;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

@Controller("kpiController")
@RequestMapping("VIEW")
public class KpiController {
	private static final Logger logger = Logger.getLogger(KpiController.class);
	@Autowired
	@Qualifier("eduqaServiceWSImpl")
	private EduqaService service;
	
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

	private String msg = "start";
	@InitBinder
	public void initBinder(PortletRequestDataBinder binder, PortletPreferences preferences) {
		logger.debug("initBinder");
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
/*
		final String[] ALLOWED_FIELDS={"researchGroupM.researchGroupId","researchGroupM.createdBy","researchGroupM.createdDate",
				"researchGroupM.groupCode","researchGroupM.permissions","researchGroupM.updatedBy",
				"researchGroupM.updatedDate","researchGroupM.groupTh","researchGroupM.groupEng","mode", "command","keySearch"};
			*/
		//	binder.setAllowedFields(ALLOWED_FIELDS);		
	}
	@RequestMapping("VIEW") 
	public String listRows(PortletRequest request,Model model){
		Map<Integer,String> levelList = new HashMap<Integer,String>();
		List<KpiLevelModel> levels = service.searchKpiLevel(new KpiLevelModel());
		for(KpiLevelModel level: levels){
			levelList.put(level.getLevelId(),level.getDesc());
		}
		model.addAttribute("levels", levelList);
		
		KpiListForm form = new KpiListForm();
		form.setKeySearch(null);
		if(levelList.size()>0){
			form.setLevel(levelList.entrySet().iterator().next().getKey());  
		}
		model.addAttribute("kpiListForm",form);
		
		KpiModel kpiModel = new KpiModel();
		kpiModel.setKeySearch(form.getKeySearch());
		kpiModel.setLevelId(form.getLevel());
		Paging page = new Paging();
		page.setPageNo(1);
		page.setPageSize(1000);
		kpiModel.setPaging(page);
		List<KpiModel> kpis = service.searchKpi(kpiModel);
		List<KpiListForm> lists = convertAccordion(kpis);
		model.addAttribute("accordions",lists);
		if(request.getParameter("pageMessage")!=null){
			model.addAttribute("pageMessage",request.getParameter("pageMessage"));
		}
		return "master/kpi";
	}

	// kpiList 
	@RequestMapping(params="action=doNew") 
	public void newDetail(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("kpiListForm") KpiListForm kpiListForm,BindingResult result,Model model){
		response.setRenderParameter("render", "showDetail");
	}
	@RequestMapping(params="action=doEdit") 
	public void editDetail(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("kpiListForm") KpiListForm kpiListForm,BindingResult result,Model model){

		response.setRenderParameter("render", "showDetail");
		response.setRenderParameter("kpiId",String.valueOf( kpiListForm.getKpiId()) );
	}
	@RequestMapping("VIEW")
	@RenderMapping(params="render=showDetail")
	public String showDetail(PortletRequest request,Model model){
	//	if(request.getParameter("message")!=null){
			model.addAttribute("actionMessage",request.getParameter("message"));
			model.addAttribute("actionMessageCode",request.getParameter("messageCode"));
		//}
		Paging pageSetting = new Paging();
		pageSetting.setPageSize(50);	// set to 50 item
		//
		Map<Integer,String> levelList = new HashMap<Integer,String>();
		List<KpiLevelModel> levels = service.searchKpiLevel(new KpiLevelModel());
		for(KpiLevelModel level: levels){
			levelList.put(level.getLevelId(),level.getDesc());
		}
		model.addAttribute("levelList", levelList);
		
		Map<Integer,String> typeList = new HashMap<Integer,String>();
		List<KpiTypeModel> types = service.searchKpiType(new KpiTypeModel());
		for(KpiTypeModel type: types){
			typeList.put(type.getTypeId(),type.getTypeName());
		}
		model.addAttribute("typeList", typeList);
		
		Map<Integer,String> groupList = new HashMap<Integer,String>();
		List<KpiGroupModel> groups = service.searchKpiGroup(new KpiGroupModel());
		for(KpiGroupModel group: groups){
			groupList.put(group.getGroupId(),group.getGroupName());
		}
		model.addAttribute("groupList", groupList);
	
		Map<Integer,String> strucList = new HashMap<Integer,String>();
		KpiStrucModel kst = new KpiStrucModel();
		kst.setPaging(pageSetting);
		List<KpiStrucModel> strucs = service.searchKpiStruc(kst);
		for(KpiStrucModel struc: strucs){
			strucList.put(struc.getStrucId(),struc.getStrucName());
		}
		model.addAttribute("structureList", strucList);
		
		Map<String,String> calendarTypeList = new LinkedHashMap<String,String>();
		List<DescriptionModel> canlendars = service.getCalendarTypes(new DescriptionModel());
		for(DescriptionModel calendar: canlendars){
			calendarTypeList.put(calendar.getDescCode(), calendar.getDescription());
		}
		model.addAttribute("calendarTypeList", calendarTypeList);
		
		Map<String,String> periodList = new LinkedHashMap<String,String>();
		List<DescriptionModel> periods = service.getPeriods(new DescriptionModel());
		//for(KpiStrucModel struc: strucs){
		for(DescriptionModel period : periods){
			periodList.put(period.getDescCode(), period.getDescription());
		}
		model.addAttribute("periodList", periodList);
		
		Map<Integer,String> uomList = new HashMap<Integer,String>();
		List<KpiUomModel> uoms = service.searchKpiUom(new KpiUomModel());
		for(KpiUomModel uom: uoms){
			uomList.put(uom.getUomId(),uom.getUomName());
		}
		model.addAttribute("uomList", uomList);
		
		Map<String,String> parentList = new HashMap<String,String>();
		List<DescriptionModel> parents = service.getKpiNameAll(new DescriptionModel());
		parentList.put(null,"none");
		for(DescriptionModel parent: parents){
			parentList.put(parent.getDescCode(),parent.getDescription());
		}
		model.addAttribute("parentList",parentList);
		
		Map<String,String> criteriaTypeList = new HashMap<String,String>();
		List<DescriptionModel> crTypes = service.getCriTypes(new DescriptionModel());
		for(DescriptionModel crType: crTypes){
			criteriaTypeList.put(crType.getDescCode(),crType.getDescription());
		}
		//	criteriaTypeList.put(1, "เชิงปริมาณ");
		//	criteriaTypeList.put(2, "เชิงคุณภาพ");
		model.addAttribute("criteriaTypeList", criteriaTypeList);
		
		Map<String,String> criteriaMethodList = new HashMap<String,String>();
		List<DescriptionModel> crMthods = service.getCriMethods(new DescriptionModel());
		for(DescriptionModel crMethod: crMthods){
			criteriaMethodList.put(crMethod.getDescCode(),crMethod.getDescription());
		}
		//criteriaMethodList.put(1,"แปลงค่าจากสูตร");
		//criteriaMethodList.put(2,"ใช้ผลลัพท์จากสูตร");
		//criteriaMethodList.put(3,"เกณฑ์มาตราฐาน");
		//criteriaMethodList.put(4,"เกณฑ์มาตราฐานแยกข้อ");
		model.addAttribute("criteriaMethodList",criteriaMethodList);
		// criteria group
		Map<Integer,String> criteriaGroupList = new HashMap<Integer,String>();
		List<CriteriaGroupModel> criteriaGroups = service.searchCriteriaGroup(new CriteriaGroupModel());
		for(CriteriaGroupModel cgroup: criteriaGroups){
			criteriaGroupList.put(cgroup.getGroupId(),cgroup.getGroupName());
		}
		model.addAttribute("criteriaGroupList", criteriaGroupList);
		
		Map<Integer,String> criteriaGroupDetailList = new HashMap<Integer,String>();
		// maunal map with key all 
		criteriaGroupDetailList.put(1,"รวม");
		model.addAttribute("criteriaGroupDetailList", criteriaGroupDetailList);
		//kpiForm
		KpiForm kpiForm=null;
		if (!model.containsAttribute("kpiForm")) {
			kpiForm = new KpiForm();
			//model.addAttribute("kpiForm",	kpiForm);
		}else{
			kpiForm = (KpiForm)model.asMap().get("kpiForm");
		}
		// check mode
		if(request.getParameter("kpiId")!=null && !request.getParameter("kpiId").equals("") ){
			Integer kpiId = Integer.parseInt(request.getParameter("kpiId"));
			KpiModel kpi = service.findKpiById(kpiId);
			kpiForm.setKpiModel(kpi);
			if(kpi.getPassFlag().equals("1")){
				kpiForm.setRadioCriteriaScore("pass");
			}else if(kpi.getScoreFlag().equals("1")){
				kpiForm.setRadioCriteriaScore("integer");
			}else{
				kpiForm.setRadioCriteriaScore("integer");
			}
			model.addAttribute("kpiForm",kpiForm);
		}
		else{ // new detail with initial value
			KpiModel kpiM = new KpiModel();
			kpiM.setCriteriaScore(5);
			kpiForm.setRadioCriteriaScore("integer");
			kpiForm.setKpiModel(kpiM);
			model.addAttribute("kpiForm",kpiForm);
		}
		return "master/kpiDetail";
	}
	
	@RequestMapping(params = "action=doDelete")
	public void actionDelete(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("kpiListForm") KpiListForm kpiListForm,BindingResult result,Model model) { 
			User user = (User) request.getAttribute(WebKeys.USER);
			KpiModel kpi = new KpiModel();
			kpi.setKpiId(kpiListForm.getKpiId());
			service.deleteKpi(kpi);
			response.setRenderParameter("pageMessage", validParamString(service.getResultMessage()));
	}
	@RequestMapping(params = "action=doFilter") 
	public void actionKeySearch(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("kpiListForm") KpiListForm kpiListForm,BindingResult result,Model model) { 
		response.setRenderParameter("render", "listSearch");
		response.setRenderParameter("keySearch", kpiListForm.getKeySearch());
		response.setRenderParameter("level", String.valueOf(kpiListForm.getLevel()));
	}
	@RequestMapping("VIEW")
	@RenderMapping(params = "render=listSearch")
	public String RenderKeySearch(@RequestParam("keySearch") String keySearch
			,@RequestParam("level") String levelId,Model model){
			Map<Integer,String> levelList = new HashMap<Integer,String>();
			List<KpiLevelModel> levels = service.searchKpiLevel(new KpiLevelModel());
			for(KpiLevelModel level: levels){
				levelList.put(level.getLevelId(),level.getDesc());
			}
			model.addAttribute("levels", levelList);
		
			KpiListForm form = new KpiListForm();
			form.setKeySearch(keySearch);
			form.setLevel(Integer.parseInt(levelId));
			model.addAttribute("kpiListForm",form);
			
			KpiModel KpiModel =new KpiModel();
			KpiModel.setLevelId(Integer.parseInt(levelId));
			KpiModel.setKeySearch(keySearch);
			Paging page = new Paging();  // default pageNo = 1
			page.setPageSize(1000);	//limit return result  1000 row
			KpiModel.setPaging(page);
			// convert Model -> form 
			List<KpiModel> kpis = service.searchKpi(KpiModel);
			List<KpiListForm> lists = convertAccordion(kpis);
			model.addAttribute("accordions",lists);
			model.addAttribute("lastPage",service.getResultPage());
			return "master/kpi";
	}

	// detail request
	@RequestMapping(params = "action=doInsertKpi") 
	public void saveKpi(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("kpiForm") KpiForm kpiForm,BindingResult result,Model model) { 
		User user = (User) request.getAttribute(WebKeys.USER);
		KpiModel kpi = kpiForm.getKpiModel();
		if(kpiForm.getRadioCriteriaScore().equals("integer")){
			kpi.setPassFlag("0");
			kpi.setScoreFlag("1");
		}
		else if (kpiForm.getRadioCriteriaScore().equals("pass")){
			kpi.setPassFlag("1");
			kpi.setScoreFlag("0");
		}
		else{
			kpi.setPassFlag(null);
			kpi.setScoreFlag(null);
		}
		
		kpi.setAcademicYear(getCurrentYear());
		kpi.setCreatedBy(user.getFullName());
		kpi.setUpdatedBy(user.getFullName());
		Integer kpiId = service.saveKpi(kpi);
		String message = "";
		String messageCode = "";
		if(kpiId==null || kpiId==0){
			message = "บันทึกไม่สำเร็จ";
			messageCode  = "1";
			
		}else{
			message = "บันทึกสำเร็จ";
			messageCode  = "0";
		}
		response.setRenderParameter("render", "showDetail");
		response.setRenderParameter("kpiId", String.valueOf(kpiId) );
		response.setRenderParameter("message", message);
		response.setRenderParameter("messageCode", messageCode);
	}
	@RequestMapping(params = "action=doUpdateKpi") 
	public void UpdateKpi(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("kpiForm") KpiForm kpiForm,BindingResult result,Model model) { 
		User user = (User) request.getAttribute(WebKeys.USER);
		KpiModel kpi = kpiForm.getKpiModel();
		if(kpiForm.getRadioCriteriaScore().equals("integer")){
			kpi.setPassFlag("0");
			kpi.setScoreFlag("1");
		}
		else if (kpiForm.getRadioCriteriaScore().equals("pass")){
			kpi.setPassFlag("1");
			kpi.setScoreFlag("0");
		}
		else{
			kpi.setPassFlag(null);
			kpi.setScoreFlag(null);
		}
		kpi.setAcademicYear(getCurrentYear());
		kpi.setUpdatedBy(user.getFullName());
		service.updateKpi(kpi);
		response.setRenderParameter("render", "showDetail");
		response.setRenderParameter("kpiId", String.valueOf(kpiForm.getKpiModel().getKpiId()) );
		response.setRenderParameter("message", "บันทึกสำเร็จ");
		response.setRenderParameter("messageCode", "0");
	}
	@RequestMapping(params = "action=doBack2List") 
	public void back2List(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("kpiForm") KpiForm kpiForm,BindingResult result,Model model) { 
			User user = (User) request.getAttribute(WebKeys.USER);
			
	}
	/* #####  function */
	private List<KpiListForm> convertAccordion(List<KpiModel> kpis){
		ListIterator<KpiModel> kpisIter = kpis.listIterator();
		//required sort data from service;
		List<KpiModel> listKpi = new ArrayList<KpiModel>();
		KpiListForm form = new KpiListForm();
		List<KpiListForm> forms = new ArrayList<KpiListForm>();
		Integer previousId = null;
		while(kpisIter.hasNext()){
			KpiModel kpi = (KpiModel) kpisIter.next();
			
			if(!kpi.getStructureId().equals(previousId) || previousId==null){ // if not
				form = new KpiListForm();
				form.setStructureName(kpi.getStructureName());
				listKpi = new ArrayList<KpiModel>();
				listKpi.add(kpi);
			}
			else{ // equals previousId
				listKpi.add(kpi);
			}
			//
			if(kpisIter.hasNext()){
				Integer current = kpi.getStructureId();
				KpiModel next = (KpiModel) kpisIter.next();
				if(!next.getStructureId().equals(current)){
					form.setKpis(listKpi);
					forms.add(form);
				}
				kpisIter.previous();
			}else{ // last
				form.setKpis(listKpi);
				forms.add(form);
			}
			// last thing 
			previousId = kpi.getStructureId();
		}	// end while iterator 
		return forms;
	}
	// ajax 
	@ResourceMapping(value="doSearchKpiName")
	@ResponseBody 
	public void getKpiNames(ResourceRequest request,ResourceResponse response) throws IOException{
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		Integer groupId = Integer.parseInt(normalRequest.getParameter("group"));
		Integer levelId = Integer.parseInt(normalRequest.getParameter("level"));
		Integer structureId = Integer.parseInt(normalRequest.getParameter("structure"));
		Paging page = new Paging();
		KpiModel kpiM = new KpiModel();
		kpiM.setPaging(page);
		kpiM.setGroupId(groupId);
		kpiM.setLevelId(levelId);
		kpiM.setStructureId(structureId);
		List<KpiModel> kpis = service.searchKpi(kpiM);
		 JSONArray lists = 	JSONFactoryUtil.createJSONArray();
		 Integer size = 0;
		 if(kpis!=null){
			 size = kpis.size();
			 for(KpiModel kpi:kpis ){
					JSONObject connJSON = JSONFactoryUtil.createJSONObject();
		         	connJSON.put("id", kpi.getKpiId() );
		         	connJSON.put("name", kpi.getKpiName() );
		         	lists.put(connJSON);
		      }
		 }
		header.put("size",size);
		 content.put("lists", lists);
		json.put("header",header);
		json.put("content",content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	@ResourceMapping(value="requestCriteriaGroupDetail")
	@ResponseBody 
	public void getCriteriaGroupDetail(ResourceRequest request,ResourceResponse response) throws IOException{
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		
		//Integer groupId = ParamUtil.getInteger(request, "groupId");
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		Integer groupId = Integer.parseInt(normalRequest.getParameter("groupId"));
		Paging page = new Paging();
		CriteriaGroupModel groupModel = new CriteriaGroupModel();
		groupModel.setPaging(page);
		groupModel.setGroupId(groupId);
		List<CriteriaGroupModel> details = service.searchCriteriaGroupDetail(groupModel);
		 JSONArray lists = 	JSONFactoryUtil.createJSONArray();
		 Integer size = 0;
		 if(details!=null){
			 size = details.size();
			 for(CriteriaGroupModel detail:details ){
					JSONObject connJSON = JSONFactoryUtil.createJSONObject();
		         	connJSON.put("id", detail.getDetailId());
		         	connJSON.put("name", detail.getDetailName() );
		         	lists.put(connJSON);
		      }
		 }
		header.put("size",size);
		 content.put("lists", lists);
		json.put("header",header);
		json.put("content",content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	//
	@ResourceMapping(value="listStandardCriteria")
	@ResponseBody 
	public void listStandardCriteria(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		
		header.put("type","list");
		 JSONArray lists = 	JSONFactoryUtil.createJSONArray();
		 CriteriaModel crModel = new CriteriaModel();
		 if(normalRequest.getParameter("kpiId")!=""){
			 crModel.setKpiId(Integer.parseInt(normalRequest.getParameter("kpiId")));
		 }
		List<CriteriaModel> criterias=service.searchCriteriaStandard(crModel);
		for(CriteriaModel criteria:criterias ){
			JSONObject connJSON = JSONFactoryUtil.createJSONObject();
         	connJSON.put("groupId", criteria.getGroupId());
         	connJSON.put("groupName", criteria.getGroupName() );
         	connJSON.put("stdId", criteria.getStandardId() );
         	connJSON.put("runNo",criteria.getRunningNo());
         	connJSON.put("stdName", criteria.getDesc() );
         	connJSON.put("cds", validParamString(criteria.getCdsId()) );
         	lists.put(connJSON);
         }
		content.put("data",lists);
		header.put("size",criterias.size() );
		json.put("header",header);
		json.put("content",content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	@ResourceMapping(value="doSaveCriteria")
	@ResponseBody 
	public void saveCriteria(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		/*4 type 
		1. ปริมาณ แปลงคะแนน -> qualitative_baseline 
		2. ปริมาณจากสูตร ?   -> none
		3. ปริมาณ กำหนดเอง (ไม่มีแล้ว) -> none  
		4. คุณภาพ เกณพ์รวม -> range_baseline 
		5 คุณภาพเกณฑ์แยก  ->specified_baseline ,specified_baseline_detailm
		*/
		// dat { "criteria" :{ type:1,data:{ row:[{groupName:"", item:[{ name,cds  },{}] }]  }} }
		User user = (User) request.getAttribute(WebKeys.USER);
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		CriteriaModel cr = new CriteriaModel();
		cr.setDesc(normalRequest.getParameter("desc"));
		cr.setCreatedBy(user.getFullName());
		cr.setUpdatedBy(user.getFullName());
		
		if(normalRequest.getParameter("kpiId")!=""){
			cr.setKpiId(Integer.parseInt(normalRequest.getParameter("kpiId")));	
		}
		if(normalRequest.getParameter("cdsId")!=""){
			cr.setCdsId(Integer.parseInt(normalRequest.getParameter("cdsId")));
		}
		if(normalRequest.getParameter("groupId")!=""){
			cr.setGroupId(Integer.parseInt(normalRequest.getParameter("groupId")));	
		}
		if(normalRequest.getParameter("runNo")!=""){
			cr.setRunningNo(Integer.parseInt(normalRequest.getParameter("runNo")));	
		}
		if( normalRequest.getParameter("stdId")==null || normalRequest.getParameter("stdId")==""){
			header.put("type","insert");
			Integer rowId=service.saveCriteriaStandard(cr);
			content.put("stdId",rowId);
		}
		else if( normalRequest.getParameter("stdId")!=null && normalRequest.getParameter("stdId")!="" ){
			cr.setStandardId( Integer.parseInt(normalRequest.getParameter("stdId")) );
			header.put("type","update");
			Integer rowId=service.saveCriteriaStandard(cr);
			content.put("total",rowId);
		}
		else{
			header.put("type","none");
		}
		json.put("header",header);
		json.put("content",content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	//doDeleteCriteria
	@ResourceMapping(value="doDeleteCriteria")
	@ResponseBody 
	public void deleteCriteria(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		CriteriaModel cr = new CriteriaModel();
		
		if( normalRequest.getParameter("stdId")!=null && normalRequest.getParameter("stdId")!="" ){
			cr.setStandardId( Integer.parseInt(normalRequest.getParameter("stdId")) );
			header.put("type","delete");
			Integer rowId=service.deleteCriteriaStandard(cr);
			content.put("data",rowId);
		}
		else{
			header.put("type","none");
			content.put("data","none");
		}
		json.put("header",header);
		json.put("content",content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	} // end doDelete
	@ResourceMapping(value="doSaveFormula")
	@ResponseBody 
	public void saveFormula(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		KpiModel kpi = new KpiModel();
		kpi.setKpiId( Integer.parseInt(normalRequest.getParameter("kpiId")));
		kpi.setFormula(normalRequest.getParameter("formula"));
		kpi.setFormulaDesc(normalRequest.getParameter("desc"));
		kpi.setPercentFlag(normalRequest.getParameter("percent"));
		User user = (User) request.getAttribute(WebKeys.USER);
		kpi.setCreatedBy(user.getFullName());
		kpi.setUpdatedBy(user.getFullName());
		Integer code = service.saveKpiFormula(kpi);
		content.put("content", code);
		json.put("header",header);
		json.put("content",content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	@ResourceMapping(value="doSaveQuanBaseline")
	@ResponseBody 
	public void saveQuanBaseline(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		User user = (User) request.getAttribute(WebKeys.USER);
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		BaselineModel baseline = new BaselineModel();
		if(normalRequest.getParameter("baselineId")!=null && normalRequest.getParameter("baselineId")!="" ){
			baseline.setBaselineId(Integer.parseInt(normalRequest.getParameter("baselineId")));
		}
		baseline.setResultType(normalRequest.getParameter("type"));
		baseline.setKpiId( Integer.parseInt(normalRequest.getParameter("kpiId")));
		baseline.setGroupId( Integer.parseInt(normalRequest.getParameter("groupId")) );
		baseline.setSubtractValue( validParam2int( normalRequest.getParameter("subVal") ) );
	//baseline.setSubtractValue( Integer.parseInt( normalRequest.getParameter("subVal") ));
		baseline.setConversionValue( Integer.parseInt(normalRequest.getParameter("percentVal")) );
		baseline.setCreatedBy(user.getFullName());
		baseline.setUpdatedBy(user.getFullName());
		
		Integer code = service.saveQuanBaseline(baseline);
		if(code==null){
			code = 0;
			header.put("size",0);
		}else{
			header.put("size",1);
		}
		content.put("baselineId", code);
		json.put("header",header);
		json.put("content",content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	
	@ResourceMapping(value="listBaseline")
	@ResponseBody 
	public void listBaseline(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		 JSONArray lists = 	JSONFactoryUtil.createJSONArray();
		 BaselineModel baseline = new BaselineModel();
		 Integer size = 0;
		 String errorMessage = "";
		 
		 header.put("type",normalRequest.getParameter("type"));
		 baseline.setResultType(normalRequest.getParameter("type"));
	if(!normalRequest.getParameter("kpiId").equals("")){
		baseline.setKpiId(Integer.parseInt(normalRequest.getParameter("kpiId")));
		
		 if(normalRequest.getParameter("type").equals("quan")){
				List<BaselineModel> results=service.listBaseline(baseline);
				if(results!=null){
					size = results.size();
					for(BaselineModel result:results ){
						JSONObject connJSON = JSONFactoryUtil.createJSONObject();
			         	connJSON.put("baselineId", result.getBaselineId());
			         	connJSON.put("subVal", validParamString(result.getSubtractValue() ));
			         	connJSON.put("conVal", result.getConversionValue());
			         	connJSON.put("groupId", result.getGroupId());
			         	connJSON.put("groupName", result.getGroupName());
			         	lists.put(connJSON);
			         }
				}
		 }
		 else if(normalRequest.getParameter("type").equals("range")){
			 
				List<BaselineModel> results=service.listBaseline(baseline);
				if(results!=null){
					size = results.size();
					for(BaselineModel result:results ){
						JSONObject connJSON = JSONFactoryUtil.createJSONObject();
			         	connJSON.put("baselineId", result.getBaselineId());
			         	connJSON.put("groupId", result.getGroupId());
			         	connJSON.put("groupName", result.getGroupName());
			         	connJSON.put("score", result.getScore());
			         	connJSON.put("begin", result.getBeginValue());
			         	connJSON.put("end", result.getEndValue());
			         	connJSON.put("desc", result.getDesc());
			         	lists.put(connJSON);
			         }
				}
		 }else if(normalRequest.getParameter("type").equals("spec")){
			 if(normalRequest.getParameter("score").equals("") ){
				 errorMessage="parameter Invalid";
			 }
			 else{
				 baseline.setScore( Integer.parseInt(normalRequest.getParameter("score")));
				List<BaselineModel> results=service.listBaseline(baseline);
				if(results!=null){
					size=results.size();
					for(BaselineModel result:results ){
						JSONObject connJSON = JSONFactoryUtil.createJSONObject();
			         	connJSON.put("groupId", result.getGroupId());
			         	connJSON.put("groupDesc", result.getGroupName());
			         	connJSON.put("baselineId", result.getBaselineId());
			         	connJSON.put("desc", result.getDesc());
			         	lists.put(connJSON);
			         }
				}
			 }
		 }else if(normalRequest.getParameter("type").equals("specGroup")){
				List<BaselineModel> results=service.listBaseline(baseline);
				if(results!=null){
					size=results.size();
					for(BaselineModel result:results ){
						JSONObject connJSON = JSONFactoryUtil.createJSONObject();
			         	connJSON.put("groupId", result.getGroupId());
			         	connJSON.put("groupDesc", result.getGroupName());
			         	lists.put(connJSON);
			         }
				}
		 }else if(normalRequest.getParameter("type").equals("specDetail")){
			 if(normalRequest.getParameter("score").equals("") ){
				 errorMessage="parameter Invalid";
			 }
			 else{
			 	baseline.setScore( Integer.parseInt(normalRequest.getParameter("score")));
			 	baseline.setGroupId(Integer.parseInt(normalRequest.getParameter("groupId")));
				List<BaselineModel> results=service.listBaseline(baseline);
				if(results!=null){
					size=results.size();
					for(BaselineModel result:results ){
						JSONObject connJSON = JSONFactoryUtil.createJSONObject();
			         	connJSON.put("baselineId", result.getBaselineId());
			         	connJSON.put("desc", result.getDesc());
			         	lists.put(connJSON);
			         }
				}
			 }
		 }
	}
	else{  errorMessage="missing kpiId"; }
		header.put("size", size );
		header.put("errorMsg",errorMessage);
		content.put("lists",lists);
		json.put("header",header);
		json.put("content",content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	@ResourceMapping(value="doSaveRangeBaseline")
	@ResponseBody 
	public void saveRangeBaseline(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		User user = (User) request.getAttribute(WebKeys.USER);
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		BaselineModel baseline = new BaselineModel();
		if(normalRequest.getParameter("baselineId")!=null && !normalRequest.getParameter("baselineId").equals("")){
			baseline.setBaselineId(Integer.parseInt(normalRequest.getParameter("baselineId")));	
		}
		baseline.setResultType(normalRequest.getParameter("type"));
		baseline.setKpiId( Integer.parseInt(normalRequest.getParameter("kpiId")));
		baseline.setGroupId( Integer.parseInt(normalRequest.getParameter("groupId")) );
		baseline.setScore( Integer.parseInt(normalRequest.getParameter("score")) );
		baseline.setBeginValue( Integer.parseInt(normalRequest.getParameter("begin") ) );
		baseline.setEndValue( Integer.parseInt(normalRequest.getParameter("end")) );
		baseline.setDesc( normalRequest.getParameter("desc") );
		//baseline.set( Integer.parseInt(normalRequest.getParameter("end")) );
		baseline.setCreatedBy(user.getFullName());
		baseline.setUpdatedBy(user.getFullName());
	
		Integer baselineId = service.saveRangeBaseline(baseline);

		JSONObject data = JSONFactoryUtil.createJSONObject();
		data.put("baselineId",baselineId);
		content.put("data", data);
		json.put("content",content);
		json.put("header",header);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	@ResourceMapping(value="doDeleteBaseline")
	@ResponseBody 
	public void deleteRangeBaseline(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		BaselineModel baseline = new BaselineModel();
		header.put("type","delete");
		header.put("resultType", normalRequest.getParameter("type"));
		Integer total = 0;
		if( normalRequest.getParameter("baselineId")!=null && normalRequest.getParameter("baselineId")!="" ){
			baseline.setBaselineId( Integer.parseInt(normalRequest.getParameter("baselineId")) );
			baseline.setResultType( normalRequest.getParameter("type") );
			total=service.deleteBaseline(baseline);
			if(total!=null && total>0){
				header.put("success","1" );
				content.put("total",total);
			}
		}
		content.put("total",total);
		json.put("header",header);
		json.put("content",content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	} // end doDelete
	// spec baseline
	@ResourceMapping(value="doSaveSpecBaseline")
	@ResponseBody 
	public void saveSpecBaseline(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		// no case edit 
		User user = (User) request.getAttribute(WebKeys.USER);
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		BaselineModel baseline = new BaselineModel();
		baseline.setResultType(normalRequest.getParameter("type"));
		baseline.setKpiId( Integer.parseInt(normalRequest.getParameter("kpiId")));
		baseline.setGroupId( Integer.parseInt(normalRequest.getParameter("groupId")) );
		baseline.setScore( Integer.parseInt(normalRequest.getParameter("score") ));
		baseline.setCriteriaId( Integer.parseInt(normalRequest.getParameter("criteriaId")) );
		baseline.setCreatedBy(user.getFullName());
		baseline.setUpdatedBy(user.getFullName());
		
		Integer code = service.saveSpecBaseline(baseline);
		if(code!=null){
			
		}
		content.put("baselineId", code);
		json.put("header",header);
		json.put("content",content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	@ResourceMapping(value="listCds")
	@ResponseBody 
	public void listCds(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		 JSONArray dataList = 	JSONFactoryUtil.createJSONArray();
		 Integer size = 0;
		 String pageTotal = "0";
		if( !normalRequest.getParameter("pageNo").equals("") ){
			CdsModel cds = new CdsModel();
			cds.setKeySearch(normalRequest.getParameter("searchStr"));
			Paging page = new Paging();
			///page.setPageSize(1);
			page.setPageNo(Integer.parseInt(normalRequest.getParameter("pageNo")));
			cds.setPaging(page);
		//	List<CdsModel> results = service.searchCds(cds);
			ResultService rs = service.searchCds(cds);
			List<CdsModel> results = (List<CdsModel>) rs.getResultObjList();
			if(  results!=null ){
				size = results.size();
				pageTotal = rs.getResultPage();
			}
             for (CdsModel result : results) {
             	JSONObject col = JSONFactoryUtil.createJSONObject();
             	col.put("cdsId", result.getCdsId());
             	col.put("cdsName",result.getCdsName());
             	col.put("cdsLevel", result.getLevelDesc());
             	dataList.put(col);
             }
		}
		header.put("size", size);
		header.put("currentPage", normalRequest.getParameter("pageNo"));
		header.put("pageTotal", pageTotal);
		content.put("lists",dataList);
		json.put("header",header);
		json.put("content",content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	
	
	@ResourceMapping(value="doGetCriteraiMethod")
	@ResponseBody 
	public void doGetCriteraiMethod(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);		
		JSONObject json = JSONFactoryUtil.createJSONObject();
		Integer criteraiTypeId = Integer.parseInt(normalRequest.getParameter("criteraiTypeId"));
		
		DescriptionModel descriptionModel = new DescriptionModel();
		descriptionModel.setGroupId(criteraiTypeId);
		List<DescriptionModel> crMthods = service.getCriMethods(descriptionModel);
		JSONArray lists = JSONFactoryUtil.createJSONArray();
		for(DescriptionModel crMethod: crMthods){
			JSONObject connJSON = JSONFactoryUtil.createJSONObject();
         	connJSON.put("id", crMethod.getDescCode());
         	connJSON.put("name", crMethod.getDescription());
         	lists.put(connJSON);
        }
		json.put("lists", lists);
		response.getWriter().write(json.toString());
	}
	
	
	@ResourceMapping(value="doDeleteKpiChildTable")
	@ResponseBody 
	public void doDeleteKpiChildTable(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest = PortalUtil.getOriginalServletRequest(httpReq);		
		Integer kpiId = Integer.parseInt(normalRequest.getParameter("kpiId"));
		
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONArray lists = JSONFactoryUtil.createJSONArray();
		
		/* Delete kpi_result before other table */
		KpiResultModel kpiResultModel = new KpiResultModel();
		kpiResultModel.setKpiId(kpiId);
		Integer delCountKpiResult = service.deleteKpiResultByKpiId(kpiResultModel);
		Integer delCountRange = service.deleteRangeBaselineByKpiId(kpiResultModel);
		
		if(delCountKpiResult != -1){
			BaselineModel baselineModel = new BaselineModel();
			baselineModel.setKpiId(kpiId);
			baselineModel.setResultType("spec");
			Integer delCountBaseSpec = service.deleteBaselineSpecDetailByKpiId(baselineModel);
			
			baselineModel.setResultType("quan");
			Integer delCountBaseQuan = service.deleteBaselineQuanByKpiId(baselineModel);
			
			CriteriaModel criteriaModel = new CriteriaModel();
			criteriaModel.setKpiId(kpiId);
			Integer delCountCriQuan = service.deleteCriteriaStandardByKpiI(criteriaModel);
			
			KpiModel kpiModel = new KpiModel();
			kpiModel.setKpiId(kpiId);
			Integer delCountKpiCds = service.deleteKpiXCds(kpiModel);		
			
			JSONObject connJSON = JSONFactoryUtil.createJSONObject();
			if(delCountKpiResult != -1
					&& delCountRange != -1
					&& delCountBaseSpec != -1
					&& delCountBaseQuan != -1
					&& delCountCriQuan != -1
					&& delCountKpiCds != -1){
				connJSON.put("statusCode", "1");
				connJSON.put("statusDesc", "Processe Success.");
			}else{
				connJSON.put("statusCode", "0");
				connJSON.put("statusDesc", "Processe Error!! --> "
						+"delCountKpiResult:"+delCountKpiResult
						+", delCountRange:"+delCountRange
						+", delCountBaseSpec:"+delCountBaseSpec
						+", delCountBaseQuan:"+delCountBaseQuan
						+", delCountCriQuan:"+delCountCriQuan
						+", delCountKpiCds:"+delCountKpiCds);
			}
			lists.put(connJSON);
		}
		
		json.put("lists", lists);
		response.getWriter().write(json.toString());
	}
	
	
	private Integer validParam2int(String paramValue){
		Integer ret=null;
			if(  paramValue!=null && !paramValue.trim().equals("") ){
				ret = Integer.parseInt(paramValue);
			}else{
				ret = null;
			}
		return ret;
	}
	private String validParamString(Object param){
		if(param instanceof Integer){
			if(param==null){
				return "";
			}else{
				return String.valueOf(param);
			}
		}else if(param instanceof String){
			if(param==null){
				return "";
			}else{
				return (String) param;
			}
		}else{
			return "";
		}
	}
}
