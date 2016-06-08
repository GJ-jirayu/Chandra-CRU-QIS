package th.ac.chandra.eduqa.portlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

import th.ac.chandra.eduqa.domain.Kpi;
import th.ac.chandra.eduqa.form.AssignResultQualityForm;
import th.ac.chandra.eduqa.form.AssignResultQuantityForm;
import th.ac.chandra.eduqa.form.EvidenceQualityForm;
import th.ac.chandra.eduqa.form.EvidenceQuantityForm;
import th.ac.chandra.eduqa.form.HierarchyAuthorityForm;
import th.ac.chandra.eduqa.form.KpiResultForm;
import th.ac.chandra.eduqa.form.KpiResultListForm;
import th.ac.chandra.eduqa.form.ResultQualityForm;
import th.ac.chandra.eduqa.form.ResultQualityTable;
import th.ac.chandra.eduqa.form.ResultQuantityForm;
import th.ac.chandra.eduqa.form.ResultQuantityTable;
import th.ac.chandra.eduqa.mapper.CustomObjectMapper;
import th.ac.chandra.eduqa.mapper.ResultService;
import th.ac.chandra.eduqa.model.CdsEvidenceModel;
import th.ac.chandra.eduqa.model.CdsModel;
import th.ac.chandra.eduqa.model.CdsResultDetailModel;
import th.ac.chandra.eduqa.model.CdsResultModel;
import th.ac.chandra.eduqa.model.DescriptionModel;
import th.ac.chandra.eduqa.model.KpiEvidenceModel;
import th.ac.chandra.eduqa.model.KpiGroupModel;
import th.ac.chandra.eduqa.model.KpiLevelModel;
import th.ac.chandra.eduqa.model.KpiModel;
import th.ac.chandra.eduqa.model.KpiResultDetailModel;
import th.ac.chandra.eduqa.model.KpiResultModel;
import th.ac.chandra.eduqa.model.OrgModel;
import th.ac.chandra.eduqa.model.SysMonthModel;
import th.ac.chandra.eduqa.model.SysYearModel;
//import th.ac.chandra.eduqa.model.ReComndModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.Paging;

@Controller("resultController")
@RequestMapping("VIEW")
@SessionAttributes({ "resultForm" })
public class ResultController {
	private static final Logger logger = Logger.getLogger(ResultController.class);
	@Autowired
	@Qualifier("eduqaServiceWSImpl")
	private EduqaService service;
	//private String uploadDirectory = "/home/pwirun/app/Chandra/fileupload/";
	private String uploadDirectory = "C:\\Users\\Administrator\\My Servers\\QIS\\FileServer\\fileupload";
	private String directoryDelimitor;
	
	@Autowired
	private CustomObjectMapper customObjectMapper;
	
	private Integer tempGroupOptValue = 0;

	@InitBinder
	public void initBinder(PortletRequestDataBinder binder,
			PortletPreferences preferences) {
		logger.debug("initBinder");
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
		// find os
		if ( SystemUtils.IS_OS_LINUX ) {
			directoryDelimitor = "/";
		} else if ( 	SystemUtils.IS_OS_WINDOWS ) {
			directoryDelimitor  = "\\";
		} else {
			directoryDelimitor = "-??-";
		}
	}

	@RenderMapping
	public String initPage(PortletRequest request, Model model) {
		// ini Param
		String UserOrgId = "1";
		Integer monthId = 0;
		Integer groupId = null;
		//  initial param
		SysYearModel sy = service.getSysYear();
		if(request.getParameter("month")!=null){
			monthId =  Integer.parseInt(request.getParameter("month"));
		}
		if(request.getParameter("group")!=null){ 
			groupId =  Integer.parseInt(request.getParameter("group"));
		}
		if( request.getParameter("orgId")!=null){
			UserOrgId = request.getParameter("orgId");
		}else{
			User user = (User) request.getAttribute(WebKeys.USER);
			DescriptionModel userOrg = new DescriptionModel();
			userOrg.setDescription(user.getScreenName());
			userOrg = service.getOrgOfUser(userOrg);
			UserOrgId = userOrg.getDescCode();
		}
		//retrive SysMonth
		SysMonthModel CurrentCalendar = service.findSysMonthById(monthId);
		//retrive org
	
		OrgModel org = new OrgModel();
		org.setOrgId(Integer.parseInt(UserOrgId));
		org = service.findOrgById(org);
		HierarchyAuthorityForm HieAuth=new HierarchyAuthorityForm();
		HieAuth.setOrgId(org.getOrgId());
		HieAuth.setLevel(org.getLevelId());
		HieAuth.setUniversity(org.getUniversityCode());
		HieAuth.setFaculty(org.getFacultyCode());
		HieAuth.setCourse(org.getCourseCode());
		
		// creae filter list
		SysMonthModel monthM = new SysMonthModel();
		monthM.setAcademicYear( sy.getAppraisalAcademicYear() );
		Map<String,String> yearList = new LinkedHashMap<String,String>();
		List<SysMonthModel> sysMonths = service.getYearsByAcad(monthM);
		for(SysMonthModel month : sysMonths){
			yearList.put(month.getCalendarYear().toString() ,month.getCalendarYear().toString());
		}
		model.addAttribute("years", yearList);
		
		monthM.setCalendarYear(CurrentCalendar.getCalendarYear());
		Map<String,String> months = new LinkedHashMap<String,String>();
		List<DescriptionModel> resultMonths = service.getMonthAll(new DescriptionModel());
		for(DescriptionModel month : resultMonths){
			months.put(month.getDescCode() ,month.getDescription());
		}
		model.addAttribute("months", months);
		
		Map<Integer,String> groups = new HashMap<Integer,String>();
		KpiGroupModel groupM = new KpiGroupModel();
		List<KpiGroupModel> resultGroups = service.searchKpiGroup(groupM);
		for(KpiGroupModel group : resultGroups){
			groups.put(group.getGroupId(),group.getGroupShortName());
		}
		model.addAttribute("groups", groups);
		
		Map<Integer,String> levelList = new HashMap<Integer,String>();
		List<KpiLevelModel> levels = service.searchKpiLevel(new KpiLevelModel());
		for(KpiLevelModel level : levels){
			levelList.put(level.getLevelId(),level.getDesc());
		}
		model.addAttribute("levels",levelList);
		
		Map<String,String> uniList = new HashMap<String,String>();
		List<DescriptionModel> unis = service.getUniversityAll(new DescriptionModel());
		for(DescriptionModel uni : unis){
			uniList.put(uni.getDescCode(),uni.getDescription());
		}
		model.addAttribute("universitys",uniList);
		
		Map<String,String> facList = new HashMap<String,String>();
		/*List<DescriptionModel> facs = service.getFacultyAll(new DescriptionModel());
		for(DescriptionModel fac : facs){
			facList.put(fac.getDescCode(),fac.getDescription());
		}*/
		List<OrgModel> facs = service.getOrgFacultyOfUniversity(org);
		for(OrgModel fac : facs){
			facList.put(fac.getFacultyCode(),fac.getFacultyName());
		}
		model.addAttribute("facultys",facList);
		
		Map<String,String> corsList = new HashMap<String,String>();
		List<DescriptionModel> cors = service.getCourseAll(new DescriptionModel());
		for(DescriptionModel cor : cors){
			corsList.put(cor.getDescCode(),cor.getDescription());
		}
		model.addAttribute("courses",corsList);
		
		// retrive kpiResultList
		KpiResultModel kpiResultModel = new KpiResultModel();
		kpiResultModel.setOrgId(org.getOrgId());
		kpiResultModel.setMonthID(monthId);
		kpiResultModel.setAcademicYear( CurrentCalendar.getAcademicYear());
		kpiResultModel.setKpiGroupId(groupId);
		Paging page = new Paging();
		page.setPageNo(1);
		page.setPageSize(10000);
		kpiResultModel.setPaging(page);
		List<KpiResultModel> kpiResult = service.searchKpiResult(kpiResultModel);
		List<KpiResultListForm>resultLists = convertAccordion(kpiResult);
		
		//form
		KpiResultForm kpiResultForm=null;
		if (!model.containsAttribute("kpiResultForm")) {
			// initial value
			kpiResultForm = new KpiResultForm();
			kpiResultForm.setIdentify(HieAuth);
			kpiResultForm.setResultList(resultLists);	
			kpiResultForm.setCalendarYear( CurrentCalendar.getCalendarYear() ) ;
			kpiResultForm.setMonthNo( CurrentCalendar.getCalendarMonthNo() ) ;
			kpiResultForm.setMonthId(monthId);
			kpiResultForm.setGroupId(  groupId ) ;
			model.addAttribute("kpiResultForm",kpiResultForm);
		}else{
			kpiResultForm = (KpiResultForm)model.asMap().get("kpiResultForm");
		}
		
		model.addAttribute("size",kpiResult.size());
		model.addAttribute("lastPage",service.getResultPage());
		model.addAttribute("currentFaculty", (org.getFacultyCode() == null ? 0 : org.getFacultyCode()));
		model.addAttribute("currentCourse", (org.getCourseCode() == null ? 0 : org.getCourseCode()));
		
		return "dataEntry/resultList";
	}
	
	@RequestMapping(params="action=doShowResultQuantity") 
	public void quantityActionShowResultList(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("KpiResultForm") KpiResultForm kpiResultForm,BindingResult result,Model model){
		response.setRenderParameter("render", "showResultQuantity");
		response.setRenderParameter("orgId", String.valueOf(kpiResultForm.getIdentify().getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(kpiResultForm.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(kpiResultForm.getMonthId() ) );
	}
	@RequestMapping("VIEW")
	@RenderMapping(params="render=showResultQuantity")
	public String quantityRenderResultList(PortletRequest request,Model model){
		Integer orgId = Integer.parseInt(request.getParameter("orgId"));
		Integer kpiId = Integer.parseInt(request.getParameter("kpiId"));
		Integer monthId = Integer.parseInt(request.getParameter("monthId"));
		KpiResultModel sendModel = new KpiResultModel();
		sendModel.setOrgId(orgId);
		sendModel.setKpiId(kpiId);
		sendModel.setMonthID(monthId);
		KpiResultModel kpiResult = service.findKpiResultByKpi(sendModel);
		CdsResultModel cds = new CdsResultModel();
		cds.setKpiId(kpiId);
		cds.setOrgId(orgId);
		cds.setMonthId(monthId);
		List<CdsResultModel> cdsResultList = service.getCdsWithKpi(cds);
		List<ResultQuantityTable> quanTableList = new ArrayList<ResultQuantityTable>();
		for( CdsResultModel cdsResult : cdsResultList ){
			ResultQuantityTable quanTable = new ResultQuantityTable();
			quanTable.setCdsId( cdsResult.getCdsId() );
			quanTable.setCdsName( cdsResult.getCdsName() );
			quanTable.setCdsValue( cdsResult.getCdsValue() );
			if(cdsResult.getEvidencePathList()!=null){
				quanTable.setHasEvidence(1);
			}
			quanTableList.add(quanTable);
		}
		//bind form
		ResultQuantityForm form = new ResultQuantityForm();
		form.setKpiId(kpiResult.getKpiId());
		form.setKpiName(kpiResult.getKpiName());
		form.setTargetValue(kpiResult.getTargetValue());
		form.setCdsResultList(quanTableList);
		form.setUom(kpiResult.getKpiUomName());
		form.setOrgId(orgId);
		form.setMonthId(monthId);
		model.addAttribute("resultQuantityForm",form);
		return "dataEntry/result";
	}
	@RequestMapping(params="action=doAssignResultQuantity") 
	public void quantityActionAssignResult(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("ResultQuantityForm") ResultQuantityForm form,BindingResult result,Model model){
		response.setRenderParameter("render", "assignResultQuantity");
		response.setRenderParameter("orgId", String.valueOf(form.getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(form.getMonthId() ) );
		response.setRenderParameter("cdsId", String.valueOf(form.getSelectCdsId() ) );	
	}
	
	
	@RequestMapping("VIEW")
	@RenderMapping(params="render=assignResultQuantity")
	public String quantityRenderAssignResult(PortletRequest request,Model model){
		// get parameter
		Integer orgId = Integer.parseInt(request.getParameter("orgId"));
		Integer kpiId = Integer.parseInt(request.getParameter("kpiId"));
		Integer monthId = Integer.parseInt(request.getParameter("monthId"));
		Integer cdsId = Integer.parseInt(request.getParameter("cdsId"));
		String resultMessage  = request.getParameter("resultMessage");

		//##########   render Result quantity list table
		KpiResultModel sendModel = new KpiResultModel();
		sendModel.setOrgId(orgId);
		sendModel.setKpiId(kpiId);
		sendModel.setMonthID(monthId);
		KpiResultModel kpiResult = service.findKpiResultByKpi(sendModel);
		CdsResultModel cds = new CdsResultModel();
		cds.setKpiId(kpiId);
		cds.setOrgId(orgId);
		cds.setMonthId(monthId);
		List<CdsResultModel> cdsResultList = service.getCdsWithKpi(cds);	
		List<ResultQuantityTable> quanTableList = new ArrayList<ResultQuantityTable>();
		for( CdsResultModel cdsResult : cdsResultList ){
			ResultQuantityTable quanTable = new ResultQuantityTable();
			quanTable.setCdsId( cdsResult.getCdsId() );
			quanTable.setCdsName( cdsResult.getCdsName() );
			quanTable.setCdsValue( cdsResult.getCdsValue() );
			if( cdsResult.getEvidencePathList()!=null){
				quanTable.setHasEvidence(1);
			}
			quanTableList.add(quanTable);
		}
		//bind form
		ResultQuantityForm form = new ResultQuantityForm();
		form.setKpiId(kpiResult.getKpiId());
		form.setKpiName(kpiResult.getKpiName());
		form.setTargetValue(kpiResult.getTargetValue());
		form.setCdsResultList(quanTableList);
		form.setUom(kpiResult.getKpiUomName());
		form.setOrgId(orgId);
		form.setMonthId(monthId);
		model.addAttribute("resultQuantityForm",form);
		// ########### end copy
	
		AssignResultQuantityForm resultForm = new AssignResultQuantityForm();
		resultForm.setKpiName(kpiResult.getKpiName());
		resultForm.setOrgId(orgId);
		resultForm.setKpiId(kpiId);
		resultForm.setMonthId(monthId);
		//CdsModel detailCds = service.findCdsById(cdsId); // get Cds Detail
		ResultService rs = service.findCdsById(cdsId);
		CdsModel detailCds = (CdsModel) rs.getResultObj();
		resultForm.setCdsName(detailCds.getCdsName());
		resultForm.setCdsId(detailCds.getCdsId());
		CdsResultModel cdsResult = new CdsResultModel();
		 cdsResult.setCdsId(cdsId); 
		 cdsResult.setOrgId(orgId); 
		 cdsResult.setMonthId(monthId);
		cdsResult = service.findCdsResultByCds(cdsResult);	 // get Result if exist 
		if(cdsResult.getCdsValue()!=null){
			resultForm.setCdsValue( Double.parseDouble(cdsResult.getCdsValue()));
		}
		
		SysMonthModel monthM = new SysMonthModel();
		monthM.setMonthId(monthId);
		monthM = service.findSysMonthById(monthId);
		resultForm.setMonthId(monthId);
		resultForm.setMonthName( monthM.getThMonthName() );
		resultForm.setMessage(resultMessage);
		resultForm.setYearName("ปีปฎิทิน"); //ปีการศึกษา
		resultForm.setYearNo(monthM.getCalendarYear().toString()); //2558
		model.addAttribute("assignResultQuantityForm",resultForm);
		
		return "dataEntry/result";
	}
	
	
	@RequestMapping(params="action=doBackFromQuantity")
	public void doBackFromQuantity(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("ResultQuantityForm") ResultQuantityForm form,BindingResult result,Model model){
		response.setRenderParameter("orgId",String.valueOf(form.getOrgId()));
		response.setRenderParameter("month",String.valueOf(form.getMonthId()));
		response.setRenderParameter("group", String.valueOf(tempGroupOptValue));
	}
	@RequestMapping(params="action=doBackFromQuality")
	public void doBackFromQuality(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("ResultQualityForm") ResultQualityForm form,BindingResult result,Model model){
		response.setRenderParameter("orgId",String.valueOf(form.getOrgId()));
		response.setRenderParameter("month",String.valueOf(form.getMonthId()));
		response.setRenderParameter("group", String.valueOf(tempGroupOptValue));
	}
	@RequestMapping(params="action=doSaveResultQuantity") 
	public void quantityActionSaveResult(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("AssignResultQuantityForm") AssignResultQuantityForm form,BindingResult result,Model model){
		// declare attrbuite
		List<String> errorMessages = new ArrayList<String>();
		User user = (User) request.getAttribute(WebKeys.USER);
		
		//save cdsResult
		CdsResultModel cdsResult = new CdsResultModel();
		//cds = service.findCdsById(form.getCdsId());  // find cds by id
		ResultService rs = service.findCdsById(form.getCdsId());
		CdsModel cds = (CdsModel) rs.getResultObj();
		cdsResult.setCdsId( cds.getCdsId());
		cdsResult.setCdsName(cds.getCdsName());
		cdsResult.setMonthId( form.getMonthId() );
		cdsResult.setOrgId( form.getOrgId());
		OrgModel org = new OrgModel();
		org.setOrgId(form.getOrgId());
		org = service.findOrgById(org);	// find org by id
		cdsResult.setOrgId(org.getOrgId());
		cdsResult.setUniversityName(org.getUniversityName());
		cdsResult.setFacultyName(org.getFacultyName());
		cdsResult.setCourseName(org.getCourseName());
		cdsResult.setCdsValue( String.valueOf(form.getCdsValue() ));
		cdsResult.setCreatedBy(user.getScreenName());
		cdsResult.setUpdatedBy(user.getScreenName());
		Integer resultId = service.saveCdsResult(cdsResult);  // save cds result		
		if(resultId==null || resultId<=0){
			errorMessages.add("บันทึกผลดำเนินการไม่สำเร็จ");
		}
		
		String resultMessage = "";
		if(errorMessages.size()<=0){
			resultMessage = "บันทึกสำเร็จ";
		}
		else{
			resultMessage = StringUtils.join(" ",errorMessages);
		}
		response.setRenderParameter("resultMessage",  resultMessage); 
		response.setRenderParameter("render", "assignResultQuantity");
		response.setRenderParameter("orgId", String.valueOf(form.getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(form.getMonthId() ) );
		response.setRenderParameter("cdsId", String.valueOf(form.getCdsId() ) );	
	}
	
	@RequestMapping(params="action=doResultQuantityBackToList") 
	public void quantityResultBackToList(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("AssignResultQuantityForm") AssignResultQuantityForm form,BindingResult result,Model model){
		// back to original page initial
		response.setRenderParameter("render", "showResultQuantity");
		response.setRenderParameter("orgId", String.valueOf(form.getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(  form.getMonthId() ) );
		
	}

	@RequestMapping(params="action=doViewEvidenceQuantity") 
	public void quantityActionViewEvidence(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("ResultQuantityForm") ResultQuantityForm form,BindingResult result,Model model){
		// back to original page initial
		response.setRenderParameter("render", "showEvidenceQuantity");
		response.setRenderParameter("orgId", String.valueOf(form.getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(  form.getMonthId() ) );
		response.setRenderParameter("cdsId", String.valueOf(  form.getSelectCdsId() ) );
	}
	
	@RequestMapping("VIEW")
	@RenderMapping(params="render=showEvidenceQuantity")
	public String quantityRenderEvidence(PortletRequest request,Model model){
		// get parameter
		List<String> errorMessage = new ArrayList<String>();
		Integer orgId = Integer.parseInt(request.getParameter("orgId"));
		Integer kpiId = Integer.parseInt(request.getParameter("kpiId"));
		Integer monthId = Integer.parseInt(request.getParameter("monthId"));
		Integer cdsId = Integer.parseInt(request.getParameter("cdsId"));
		String evidenceMessage = request.getParameter("evidenceMessage");
		if(evidenceMessage!=null && !evidenceMessage.equals("")){
			errorMessage.add(evidenceMessage);
		}
		
		//########## copy
		KpiResultModel kpiResult = new KpiResultModel();
		kpiResult.setOrgId(orgId);
		kpiResult.setKpiId(kpiId);
		kpiResult.setMonthID(monthId);
		kpiResult = service.findKpiResultByKpi(kpiResult);
		CdsResultModel cds = new CdsResultModel();
		cds.setKpiId(kpiId);
		cds.setOrgId(orgId);
		cds.setMonthId(monthId);
		List<CdsResultModel> cdsResultList = service.getCdsWithKpi(cds);
		List<ResultQuantityTable> quanTableList = new ArrayList<ResultQuantityTable>();
		for( CdsResultModel cdsResult : cdsResultList ){
			ResultQuantityTable quanTable = new ResultQuantityTable();
			quanTable.setCdsId( cdsResult.getCdsId() );
			quanTable.setCdsName( cdsResult.getCdsName() );
			quanTable.setCdsValue( cdsResult.getCdsValue() );
			if( cdsResult.getEvidencePathList()!=null){
				quanTable.setHasEvidence(1);
			}
			quanTableList.add(quanTable);
		}
		
		//bind form
		ResultQuantityForm form = new ResultQuantityForm();
		form.setKpiId(kpiResult.getKpiId());
		form.setKpiName(kpiResult.getKpiName());
		form.setTargetValue(kpiResult.getTargetValue());
		form.setUom(kpiResult.getKpiUomName());
		form.setOrgId(orgId);
		form.setMonthId(monthId);
		form.setCdsResultList(quanTableList);
		model.addAttribute("resultQuantityForm",form);
		EvidenceQuantityForm eviQuan = new EvidenceQuantityForm();
		
		CdsResultModel cdsResult = new CdsResultModel();
		 cdsResult.setCdsId(cdsId); 
		 cdsResult.setOrgId(orgId); 
		 cdsResult.setMonthId(monthId);
		 cdsResult = service.findCdsResultByCds(cdsResult);	//	get cdsResult
		
		if( cdsResult.getResultId()==null){
			errorMessage.add("ไม่พบผลดำเนินงาน");
		}
		else{
			CdsResultDetailModel cdsResultDetail = new CdsResultDetailModel();
			cdsResultDetail.setResultId(cdsResult.getResultId());
			cdsResultDetail = service.findCdsResultDetail(cdsResultDetail);	// find ResultDetail
			if(cdsResultDetail.getResultDetailId()==null){
				errorMessage.add("ไม่พบประเภทหลักฐาน");
			}else{
				CdsEvidenceModel evidence = new CdsEvidenceModel();
				evidence.setResultDetailId(cdsResultDetail.getResultDetailId());
				List<CdsEvidenceModel> evidencesResult = service.searchCdsEvidence(evidence);
				List<CdsEvidenceModel> evidences = new ArrayList<CdsEvidenceModel>();
				String word = "";
				//if( !cdsResultDetail.getEvidenceFlag().isEmpty() ){
				//!Strings.isNullOrEmpty(acct)
				if( cdsResultDetail.getEvidenceFlag() != null && cdsResultDetail.getEvidenceFlag().length() != 0){
					if(cdsResultDetail.getEvidenceFlag().toUpperCase().equals("F")){
						String path = PortalUtil.getCurrentURL(request);
						int inx = path.indexOf("?");
						word  = path.substring(0, inx)+"?p_p_id=Result_WAR_ChandraEduqa&p_p_lifecycle=2&p_p_resource_id=fileDownload&filename=";	
					}
				}else{
					//กรณีที่ evidenceFlag = null กรณีนี้จะไม่เกิดขึ้นเพราะหน้าจอโปรแกรมได้ดัการทำงาน user ไว้แล้วว่าจำเป็นต้องระบุ evidence flag
					//Do something.
					logger.info("\n -- ResultContoller.java(537) --> cdsResultDetail.EvidenceFlag() = null \n");
				}
				for( CdsEvidenceModel evs : evidencesResult){
					evs.setEvidenceFileName(evs.getEvidencePath()); 
					evs.setEvidencePath( word +  evs.getEvidencePath() );  // renew
					evidences.add(evs);	
				}
				eviQuan.setEvidences(evidences);
				eviQuan.setEvidenceType(cdsResultDetail.getEvidenceFlag());
				eviQuan.setEvidenceCheck(cdsResultDetail.getEvidenceFlag());
			}
		}
			eviQuan.setOrgId(orgId);
			eviQuan.setKpiId(kpiId);
			eviQuan.setCdsId(cdsId);
			eviQuan.setMonthId(monthId);
			eviQuan.setKpiName(kpiResult.getKpiName());
			eviQuan.setCdsName(cdsResult.getCdsName());
			if(errorMessage.size()>0){
				eviQuan.setMessage(StringUtils.join(" ",errorMessage));
			}
			model.addAttribute("evidenceQuantityForm",eviQuan);
			return "dataEntry/result";
	}
	@RequestMapping(params="action=doSaveEvidenceQuantity") 
	public void quantityActionSaveEvidence(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("EvidenceQuantityForm") EvidenceQuantityForm form,BindingResult result,Model model){
		List<String> errorMessages = new ArrayList<String>();
		User user = (User) request.getAttribute(WebKeys.USER);
		
		CdsResultModel cdsResult = new CdsResultModel();
		cdsResult.setCdsId(form.getCdsId());
		cdsResult.setOrgId(form.getOrgId());
		cdsResult.setMonthId(form.getMonthId());
		cdsResult = service.findCdsResultByCds(cdsResult);

		Integer resultDetailId = 0;
		if(cdsResult.getResultId()==null){
			errorMessages.add("บันทึกหลักฐานไม่สำเร็จไม่มีผลดำเนินงาน");
		}else{
			CdsResultDetailModel resultDetail = new CdsResultDetailModel();
			resultDetail.setResultId(cdsResult.getResultId());
			resultDetail.setEvidenceFlag(form.getEvidenceType());
			resultDetail.setCreatedBy(user.getScreenName());
			resultDetail.setUpdatedBy(user.getScreenName());
			resultDetailId = service.saveCdsResultDetail(resultDetail);  // save cds result detail
		}
		if(resultDetailId==null || resultDetailId<=0){
			errorMessages.add("บันทึกประเภทหลักฐานไม่สำเร็จ");
		}
		else{
		//save evidence
		CdsEvidenceModel evidenceModel = new CdsEvidenceModel();
    	evidenceModel.setResultDetailId(resultDetailId);
     	evidenceModel.setCreatedBy(user.getScreenName());
    	evidenceModel.setUpdatedBy(user.getScreenName());
		if( form.getEvidenceType().equals("L")){
		 	evidenceModel.setEvidencePath(form.getUrlPath());
        	Integer evidenceId = service.saveCdsEvidence(evidenceModel);	//save Evidence
        	if(evidenceId<=0){
        		errorMessages.add("บันทึกหลักฐานไม่สำเร็จ");
        	}
		}else if( form.getEvidenceType().equals("F")){
			try{
				//create directory 
				File file = new File(uploadDirectory+user.getScreenName() );
				if (!file.exists()) {
					if (file.mkdir()) {
					//	errorMessages.add("สร้าง folder สำเร็จ");
					} else {
					//	errorMessages.add("สร้าง folder ไม่สำเร็จ");
					}
				}
				// copy file to server 
	        	FileCopyUtils.copy(form.getFileData().getBytes(), new File(  uploadDirectory + user.getScreenName() + directoryDelimitor + form.getFileData().getOriginalFilename()));       	
	        	evidenceModel.setEvidencePath(form.getFileData().getOriginalFilename());
	        	Integer evidenceId = service.saveCdsEvidence(evidenceModel);	//save Evidence
	        	if(evidenceId<=0){
		        	errorMessages.add("บึกทึกหลักฐานไม่สำเร็จ");
	        	}
	        }catch(Exception e){
	        	errorMessages.add("อัพโหลดหลักฐานไม่สำเร็จ:"+e.getMessage());
	        }
		}
		}//end check resultDetailId
		String resultMessage = "";
		if(errorMessages.size()<=0){
			resultMessage = "บันทึกสำเร็จ";
		}
		else{
			resultMessage = StringUtils.join(" ",errorMessages);
		}
		response.setRenderParameter("evidenceMessage",  resultMessage); 
		response.setRenderParameter("render", "showEvidenceQuantity");
		response.setRenderParameter("orgId", String.valueOf(form.getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(form.getMonthId() ) );
		response.setRenderParameter("cdsId", String.valueOf(form.getCdsId() ) );
		
	}
	@RequestMapping(params="action=doCloseEvidenceQuantity") 
	public void quantityActionCloseEvidence(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("EvidenceQuantityForm") EvidenceQuantityForm form,BindingResult result,Model model){
		// back to original page initial
		response.setRenderParameter("render", "showResultQuantity");
		response.setRenderParameter("orgId", String.valueOf(form.getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(  form.getMonthId() ) );
	}
	

	// ######################ajax hierachy ##################// 

	@RequestMapping(params = "action=doSubmitFilter")
	public void submitFilter(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response,
			@ModelAttribute("KpiResultForm") KpiResultForm form, BindingResult result, Model model) {
		// response.setRenderParameter(arg0, arg1);
		Integer level = form.getIdentify().getLevel();
		String orgId = null;
		if (level == 1) {
			orgId = form.getIdentify().getUniversity();
		} else if (level == 2) {
			orgId = form.getIdentify().getFaculty();
		} else if (level == 3) {
			orgId = form.getIdentify().getCourse();
		}
		if (orgId.isEmpty()) {
			orgId = "0";
		}
		// version 2 * academic > calendar , monthId > monthNo
		SysMonthModel dm = new SysMonthModel();
		dm.setCalendarYear(form.getCalendarYear());
		dm.setCalendarMonthNo(form.getMonthNo());
		try {
			List<SysMonthModel> dms = service.getMonthId(dm);
			dm = dms.get(0);
		} catch (Exception ex) {
		}
		tempGroupOptValue = form.getGroupId();
		response.setRenderParameter("orgId", orgId);
		response.setRenderParameter("month", String.valueOf(dm.getMonthId()));
		response.setRenderParameter("group", String.valueOf(form.getGroupId()));
	}
	
	// ########################### quality คุณภาพ ###################
	@RequestMapping(params="action=doShowResultQuality") 
	public void qualityActionShowResultList(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("KpiResultForm") KpiResultForm kpiResultForm,BindingResult result,Model model){
		response.setRenderParameter("render", "showResultQuality");
		response.setRenderParameter("orgId", String.valueOf(kpiResultForm.getIdentify().getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(kpiResultForm.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(kpiResultForm.getMonthId() ) );
	}
	@RequestMapping("VIEW")
	@RenderMapping(params="render=showResultQuality")
	public String qualityRenderResultList(PortletRequest request,Model model){
		Integer orgId = Integer.parseInt(request.getParameter("orgId"));
		Integer kpiId = Integer.parseInt(request.getParameter("kpiId"));
		Integer monthId = Integer.parseInt(request.getParameter("monthId"));
		KpiResultModel sendModel = new KpiResultModel();
		sendModel.setOrgId(orgId);
		sendModel.setKpiId(kpiId);
		sendModel.setMonthID(monthId);
		KpiResultModel kpiResult = service.findKpiResultByKpi(sendModel); //  find result 
		KpiResultDetailModel criteriaResult = new KpiResultDetailModel();
		criteriaResult.setOrgId(orgId);
		criteriaResult.setKpiId(kpiId);
		criteriaResult.setMonthId(monthId);
		List<KpiResultDetailModel> criteriaResults = service.findCriteriaResult(criteriaResult);
		List<ResultQualityTable> tableList = new ArrayList<ResultQualityTable>();
		for( KpiResultDetailModel result : criteriaResults ){
			ResultQualityTable table = new ResultQualityTable();
			table.setStandardId( result.getCriteriaId() );
			table.setStandardName( result.getCriteriaDesc() );
			if( result.getCdsValue()!=null){
				table.setCdsValue( String.valueOf( result.getCdsValue()) );
			}
			table.setHasResult( result.getActionFlag() );
			tableList.add(table);
		}
		
		//Get criteria_method_id 
		KpiModel kpiModel = service.findKpiById(kpiId);
		Integer criteriaMethodId = kpiModel.getCriteriaMethodId();
		
		//bind form
		ResultQualityForm form = new ResultQualityForm();
		form.setKpiId(kpiResult.getKpiId());
		form.setKpiName(kpiResult.getKpiName());
		form.setTargetValue(kpiResult.getTargetValue());
		form.setResultList(tableList);
		form.setUom(kpiResult.getKpiUomName());
		form.setOrgId(orgId);
		form.setMonthId(monthId);
		form.setCriteriaMethodId(criteriaMethodId);
		model.addAttribute("resultQualityForm",form);
		return "dataEntry/resultQuality";
	}
	
	@RequestMapping(params="action=doAssignResultQuality") 
	public void qualityActionAssignResult(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("ResultQualityForm") ResultQualityForm form,BindingResult result,Model model){
		response.setRenderParameter("render", "assignResultQuality");
		response.setRenderParameter("orgId", String.valueOf(form.getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(form.getMonthId() ) );
		response.setRenderParameter("criteriaId", String.valueOf(form.getSelectStdId() ) );	
	}
	@RequestMapping("VIEW")
	@RenderMapping(params="render=assignResultQuality")
	public String qualityRenderAssignResult(PortletRequest request,Model model){
		// get parameter
		Integer orgId = Integer.parseInt(request.getParameter("orgId"));
		Integer kpiId = Integer.parseInt(request.getParameter("kpiId"));
		Integer monthId = Integer.parseInt(request.getParameter("monthId"));
		Integer criteriaId = Integer.parseInt(request.getParameter("criteriaId"));
		String resultMessage  = request.getParameter("resultMessage");

		//##########   render Result quality list table
		KpiResultModel sendModel = new KpiResultModel();
		sendModel.setOrgId(orgId);
		sendModel.setKpiId(kpiId);
		sendModel.setMonthID(monthId);
		KpiResultModel kpiResult = service.findKpiResultByKpi(sendModel); //  find result 
		KpiResultDetailModel criteriaResult = new KpiResultDetailModel();
		criteriaResult.setOrgId(orgId);
		criteriaResult.setKpiId(kpiId);
		criteriaResult.setMonthId(monthId);
		List<KpiResultDetailModel> criteriaResults = service.findCriteriaResult(criteriaResult);
		List<ResultQualityTable> tableList = new ArrayList<ResultQualityTable>();
		for( KpiResultDetailModel result : criteriaResults ){
			ResultQualityTable table = new ResultQualityTable();
			table.setStandardId( result.getCriteriaId() );
			table.setStandardName( result.getCriteriaDesc() );
			if( result.getCdsValue()!=null){
				table.setCdsValue( String.valueOf( result.getCdsValue()) );
			}
			table.setHasResult( result.getActionFlag() );
			tableList.add(table);
		}
		
		//Get criteria_method_id 
		KpiModel kpiModel = service.findKpiById(kpiId);
		Integer criteriaMethodId = kpiModel.getCriteriaMethodId();

		//bind form
		ResultQualityForm form = new ResultQualityForm();
		form.setKpiId(kpiResult.getKpiId());
		form.setKpiName(kpiResult.getKpiName());
		form.setTargetValue(kpiResult.getTargetValue());
		form.setResultList(tableList);
		form.setUom(kpiResult.getKpiUomName());
		form.setOrgId(orgId);
		form.setMonthId(monthId);
		form.setCriteriaMethodId(criteriaMethodId);
		model.addAttribute("resultQualityForm",form);
		// ########### end copy
		
		AssignResultQualityForm resultForm = new AssignResultQualityForm();
		resultForm.setKpiName(kpiResult.getKpiName());
		resultForm.setOrgId(orgId);
		resultForm.setKpiId(kpiId);
		KpiModel kpi = service.findKpiById(kpiId);
		resultForm.setKpiName(kpi.getKpiName());
		resultForm.setYearName("ปีการศึกษา");
		SysYearModel sy = service.getSysYear();
		resultForm.setYearNo(String.valueOf(sy.getAppraisalAcademicYear()));
		SysMonthModel monthM = new SysMonthModel();
		monthM.setMonthId(monthId);
		monthM = service.findSysMonthById(monthId);
		resultForm.setMonthId(monthId);
		resultForm.setMonthName( monthM.getThMonthName() );
		
		KpiResultDetailModel kpiResultDetailM = new KpiResultDetailModel();
		kpiResultDetailM.setKpiId(kpiId);
		kpiResultDetailM.setOrgId(orgId);
		kpiResultDetailM.setMonthId(monthId);
		kpiResultDetailM.setCriteriaId(criteriaId);
		kpiResultDetailM = service.findKpiResultDetailByIdentify(kpiResultDetailM);	// find selected result detail 
		resultForm.setCdsId( kpiResultDetailM.getCdsId() );
		resultForm.setCdsValue(kpiResultDetailM.getCdsValue());
		resultForm.setCriteriaId( kpiResultDetailM.getCriteriaId() );
		resultForm.setCriteriaDesc( kpiResultDetailM.getCriteriaDesc() );
		if(kpiResultDetailM.getActionFlag().equals("1")){
			resultForm.setActionFlag(true);
		}else{
			resultForm.setActionFlag(false);	
		}
		resultForm.setMessage(resultMessage);
		resultForm.setCdsId(kpiResultDetailM.getCdsId());
		resultForm.setCdsName(kpiResultDetailM.getCdsName());

		model.addAttribute("assignResultQualityForm",resultForm);
		return "dataEntry/resultQuality";
	}
	
	@RequestMapping(params="action=doSaveResultQuality") 
	public void qualityActionSaveResult(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("AssignResultQualityForm") AssignResultQualityForm form,BindingResult result,Model model){
		// declare attrbuite
		List<String> errorMessages = new ArrayList<String>();
		User user = (User) request.getAttribute(WebKeys.USER);
		SysYearModel sysYear = service.getSysYear();
		//save kpiResultDetail
		KpiResultDetailModel kpiResultDetailM = new KpiResultDetailModel();
		//find resultId;
		kpiResultDetailM.setKpiId(form.getKpiId());
		kpiResultDetailM.setOrgId(form.getOrgId());
		kpiResultDetailM.setMonthId(form.getMonthId());
		kpiResultDetailM.setCriteriaId(form.getCriteriaId());
		if( form.getActionFlag() ){
			kpiResultDetailM.setActionFlag("1"); 
		}else{
			kpiResultDetailM.setActionFlag("0"); 
		}
		kpiResultDetailM.setCreatedBy(user.getScreenName());
		kpiResultDetailM.setUpdatedBy(user.getScreenName());
		kpiResultDetailM.setEvidenceFlag(null);  // update when click view evidence list
		kpiResultDetailM.setAcademicYear(sysYear.getAppraisalAcademicYear()); //SysYearModel sy = service.getSysYear();
		Integer success = service.saveKpiResultDetail(kpiResultDetailM); 
		if(success<=0){
			errorMessages.add("บันทึกไม่สำเร็จ");
		}
		
		String resultMessage = "";
		if(errorMessages.size()<=0){
			resultMessage = "บันทึกสำเร็จ";
		}
		else{
			resultMessage = StringUtils.join(" ",errorMessages);
		}
		response.setRenderParameter("resultMessage",  resultMessage); 
		response.setRenderParameter("render", "assignResultQuality");
		response.setRenderParameter("orgId", String.valueOf(form.getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(form.getMonthId() ) );
		response.setRenderParameter("criteriaId", String.valueOf(form.getCriteriaId() ) );	
	}
	
	@RequestMapping(params="action=doResultQualityBackToList") 
	public void qualityResultBackToList(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("AssignResultQualityForm") AssignResultQualityForm form,BindingResult result,Model model){
		// back to original page initial
		response.setRenderParameter("render", "showResultQuality");
		response.setRenderParameter("orgId", String.valueOf(form.getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(  form.getMonthId() ) );
		
	}

	@RequestMapping(params="action=doViewEvidenceQuality") 
	public void qualityActionViewEvidence(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("ResultQualityForm") ResultQualityForm form,BindingResult result,Model model){
		// back to original page initial
		response.setRenderParameter("render", "showEvidenceQuality");
		response.setRenderParameter("orgId", String.valueOf(form.getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(  form.getMonthId() ) );
		response.setRenderParameter("criteriaId", String.valueOf(  form.getSelectStdId() ) );	
	}
	
	@RequestMapping("VIEW")
	@RenderMapping(params="render=showEvidenceQuality")
	public String qualityRenderEvidence(PortletRequest request,Model model){
		// get parameter
		List<String> errorMessage = new ArrayList<String>();
		Integer orgId = Integer.parseInt(request.getParameter("orgId"));
		Integer kpiId = Integer.parseInt(request.getParameter("kpiId"));
		Integer monthId = Integer.parseInt(request.getParameter("monthId"));
		Integer criteriaId = Integer.parseInt(request.getParameter("criteriaId"));
		String evidenceMessage = request.getParameter("evidenceMessage");
		if(evidenceMessage!=null && !evidenceMessage.equals("")){
			errorMessage.add(evidenceMessage);
		}
		//########## copy
		KpiResultModel sendModel = new KpiResultModel();
		sendModel.setOrgId(orgId);
		sendModel.setKpiId(kpiId);
		sendModel.setMonthID(monthId);
		KpiResultModel kpiResult = service.findKpiResultByKpi(sendModel); //  find result 
		KpiResultDetailModel criteriaResult = new KpiResultDetailModel();
		criteriaResult.setOrgId(orgId);
		criteriaResult.setKpiId(kpiId);
		criteriaResult.setMonthId(monthId);
		List<KpiResultDetailModel> criteriaResults = service.findCriteriaResult(criteriaResult);
		List<ResultQualityTable> tableList = new ArrayList<ResultQualityTable>();
		for( KpiResultDetailModel result : criteriaResults ){
			ResultQualityTable table = new ResultQualityTable();
			table.setStandardId( result.getCriteriaId() );
			table.setStandardName( result.getCriteriaDesc() );
			if( result.getCdsValue()!=null){
				table.setCdsValue( String.valueOf( result.getCdsValue()) );
			}
			table.setHasResult( result.getActionFlag() );
			tableList.add(table);
		}
		
		//Get criteria_method_id 
		KpiModel kpiModel = service.findKpiById(kpiId);
		Integer criteriaMethodId = kpiModel.getCriteriaMethodId();
				
				
		//bind form
		ResultQualityForm form = new ResultQualityForm();
		form.setKpiId(kpiResult.getKpiId());
		form.setKpiName(kpiResult.getKpiName());
		form.setTargetValue(kpiResult.getTargetValue());
		form.setResultList(tableList);
		form.setUom(kpiResult.getKpiUomName());
		form.setOrgId(orgId);
		form.setMonthId(monthId);
		form.setCriteriaMethodId(criteriaMethodId);
		model.addAttribute("resultQualityForm",form);
		// end list
		KpiEvidenceModel eviModel = new KpiEvidenceModel();
		eviModel.setOrgId(orgId);
		eviModel.setKpiId(kpiId);
		eviModel.setMonthId(monthId);
		eviModel.setCriteriaId(criteriaId);
		
		EvidenceQualityForm eviQua = new EvidenceQualityForm();
		
		// kpiResultDetail 
		KpiResultDetailModel resultDetailM = new KpiResultDetailModel();
		resultDetailM.setKpiId(kpiId);
		resultDetailM.setOrgId(orgId);
		resultDetailM.setMonthId(monthId);
		resultDetailM.setCriteriaId(criteriaId);
		resultDetailM = service.findKpiResultDetailByIdentify(resultDetailM);
		eviQua.setEvidenceCheck(resultDetailM.getEvidenceFlag());
		eviQua.setEvidenceType(resultDetailM.getEvidenceFlag());
	
			//get evidence
			List<KpiEvidenceModel> evidencesResult =	  service.searchKpiEvidence(eviModel);
			List<KpiEvidenceModel> evidences =	  new ArrayList<KpiEvidenceModel>();
			String word = "";
			if(resultDetailM.getEvidenceFlag()==null){
					resultDetailM.setEvidenceFlag("");
			}
			if(resultDetailM.getEvidenceFlag().toUpperCase().equals("F")){
				String path = PortalUtil.getCurrentURL(request);
				int inx = path.indexOf("?");
				word  = path.substring(0, inx)+"?p_p_id=Result_WAR_ChandraEduqa&p_p_lifecycle=2&p_p_resource_id=fileDownload&filename=";	
			}
			for( KpiEvidenceModel evs : evidencesResult){
				evs.setEvidenceFileName(evs.getEvidencePath()); 
				evs.setEvidencePath( word +  evs.getEvidencePath() );  // renew
				evidences.add(evs);	
			}
			eviQua.setEvidences(evidences);
		
			eviQua.setKpiId(kpiId);
			eviQua.setMonthId(monthId);
			eviQua.setCriteriaId(criteriaId);
			eviQua.setOrgId(orgId);
			eviQua.setKpiName(kpiResult.getKpiName());
			eviQua.setCriteriaName(resultDetailM.getCriteriaDesc());
			
			if(errorMessage.size()>0){
				eviQua.setMessage(StringUtils.join(" ",errorMessage));
			}
			model.addAttribute("evidenceQualityForm",eviQua);
			return "dataEntry/resultQuality";
	}
	@RequestMapping(params="action=doSaveEvidenceQuality") 
	public void qualityActionSaveEvidence(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("EvidenceQualityForm") EvidenceQualityForm form,BindingResult result,Model model){
		List<String> errorMessages = new ArrayList<String>();
		User user = (User) request.getAttribute(WebKeys.USER);
		
		KpiResultDetailModel resultDetail = new KpiResultDetailModel();
		resultDetail.setCdsId(form.getCdsId());
		resultDetail.setKpiId( form.getKpiId());
		resultDetail.setOrgId( form.getOrgId());
		resultDetail.setMonthId( form.getMonthId() );
		resultDetail.setCriteriaId( form.getCriteriaId() );
		resultDetail.setEvidenceFlag(form.getEvidenceType());
		resultDetail.setCreatedBy(user.getScreenName());
		resultDetail.setUpdatedBy(user.getScreenName());
		Integer success = service.updateKpiResultDetailEvidence(resultDetail);  // save kpi result detail
		
		if(success==null || success<=0){
			errorMessages.add("บันทึกประเภทหลักฐานไม่สำเร็จ");
		}
		else{
		//save evidence
		KpiEvidenceModel evidenceModel = new KpiEvidenceModel();
		evidenceModel.setKpiId(form.getKpiId());
		evidenceModel.setOrgId(form.getOrgId());
		evidenceModel.setMonthId(form.getMonthId());
		evidenceModel.setCriteriaId(form.getCriteriaId());
     	evidenceModel.setCreatedBy(user.getScreenName());
    	evidenceModel.setUpdatedBy(user.getScreenName());
		if( form.getEvidenceType().equals("L")){
		 	evidenceModel.setEvidencePath(form.getUrlPath());
        	Integer evidenceId = service.saveKpiEvidence(evidenceModel);	//save Evidence
        	if(evidenceId<=0){
        		errorMessages.add("บันทึกหลักฐานไม่สำเร็จ");
        	}
		}else if( form.getEvidenceType().equals("F")){
			try{
				//create directory 
				File file = new File(uploadDirectory+user.getScreenName() );
				if (!file.exists()) {
					if (file.mkdir()) {
					//	errorMessages.add("สร้าง folder สำเร็จ");
					} else {
					//	errorMessages.add("สร้าง folder ไม่สำเร็จ");
					}
				}
				// copy file to server
	        	FileCopyUtils.copy(form.getFileData().getBytes(), new File( uploadDirectory + user.getScreenName() + directoryDelimitor + form.getFileData().getOriginalFilename()));       	
	        	evidenceModel.setEvidencePath(form.getFileData().getOriginalFilename());
	        	Integer evidenceId = service.saveKpiEvidence(evidenceModel);	//save Evidence
	        	if(evidenceId<=0){
		        	errorMessages.add("บึกทึกหลักฐานไม่สำเร็จ");
	        	}
	        }catch(Exception e){
	        	errorMessages.add("อัพโหลดหลักฐานไม่สำเร็จ");
	        }
		}
		}//end check resultDetailId
		String resultMessage = "";
		if(errorMessages.size()<=0){
			resultMessage = "บันทึกสำเร็จ";
		}
		else{
			resultMessage = StringUtils.join(" ",errorMessages);
		}
		response.setRenderParameter("evidenceMessage",  resultMessage); 
		response.setRenderParameter("render", "showEvidenceQuality");
		response.setRenderParameter("orgId", String.valueOf(form.getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(form.getMonthId() ) );
		response.setRenderParameter("criteriaId", String.valueOf(form.getCriteriaId() ) );
	}
	@RequestMapping(params="action=doCloseEvidenceQuality") 
	public void qualityActionCloseEvidence(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
			,@ModelAttribute("EvidenceQualityForm") EvidenceQualityForm form,BindingResult result,Model model){
		// back to original page initial
		response.setRenderParameter("render", "showResultQuality");
		response.setRenderParameter("orgId", String.valueOf(form.getOrgId()) );
		response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
		response.setRenderParameter("monthId", String.valueOf(  form.getMonthId() ) );
	}
	
	// ######################## ajax #######################//
	@ResourceMapping(value="requestSearchOrgId")
	@ResponseBody 
	public void requestSearchOrgId(ResourceRequest request,ResourceResponse response) throws IOException{
		JSONObject json = JSONFactoryUtil.createJSONObject();
		
		//Integer groupId = ParamUtil.getInteger(request, "groupId");
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest =PortalUtil.getOriginalServletRequest(httpReq);
		String levelId = normalRequest.getParameter("levelId");//Integer.parseInt(normalRequest.getParameter("levelId"));
		String facultyCode = normalRequest.getParameter("facultyCode");
		String couseCode = normalRequest.getParameter("couseCode");
		String levelMode = normalRequest.getParameter("levelMode");
		String[] otherKeySearch = {levelMode, facultyCode, couseCode};
		OrgModel orgModel = new OrgModel();
		orgModel.setKeySearch(levelId);
		orgModel.setOtherKeySearch(otherKeySearch);
				
		List<OrgModel> details = service.searchOrgIdByOthersCode(orgModel);
		JSONArray lists = JSONFactoryUtil.createJSONArray();
		for(OrgModel detail:details ){
			JSONObject connJSON = JSONFactoryUtil.createJSONObject();
         	connJSON.put("id", detail.getOrgId());
         	lists.put(connJSON);
         }
		json.put("orgIdLists", lists);
		//System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	@ResourceMapping(value="requestCriteriaCourse")
	@ResponseBody
	public void getCriteriaCourse(ResourceRequest request,ResourceResponse response) throws IOException{
		JSONObject json = JSONFactoryUtil.createJSONObject();
		
		//Integer groupId = ParamUtil.getInteger(request, "groupId");
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		String facultyCode = normalRequest.getParameter("facultyCode");
		OrgModel orgModel = new OrgModel();
		orgModel.setKeySearch(facultyCode);
		List<OrgModel> details = service.searchOrgByFacultyCode(orgModel);
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

	@ResourceMapping(value="doSearchOrg")
	@ResponseBody 
	public void searchOrgByLevel(ResourceRequest request,ResourceResponse response) 
			throws IOException{
		//WARNING**   service cann't be null
		// return <id,desc>
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject header = JSONFactoryUtil.createJSONObject();
		JSONObject content = JSONFactoryUtil.createJSONObject();
		 JSONArray orgList = 	JSONFactoryUtil.createJSONArray();
		String status = "";
		Integer size = 0;
		if( normalRequest.getParameter("level")!=null && normalRequest.getParameter("level")!=""){
			
			OrgModel org = new OrgModel();
			org.setLevelId( Integer.parseInt(normalRequest.getParameter("level")));
			org.setUniversityCode(validParamString(normalRequest.getParameter("university"))  );
			org.setFacultyCode( validParamString(normalRequest.getParameter("faculty")) );
			org.setCourseCode( validParamString(normalRequest.getParameter("course")));

			List<OrgModel> results = new ArrayList<OrgModel>();
			if(org.getLevelId().equals(0)){
				results = service.getAllUniversity(org);
				if(results!=null){
					size = results.size();
					if(size>0){
			             for (OrgModel result : results) {
			            	 JSONObject rec = JSONFactoryUtil.createJSONObject();
			            	 rec.put("orgCode",result.getUniversityCode());
			            	 rec.put("orgDesc",result.getUniversityName());
			            	 orgList.put(rec);
			             }
					}
				}//end result!=null
			}else if(org.getLevelId().equals(1)){
				results = service.getOrgFacultyOfUniversity(org);
				if(results!=null){
					size = results.size();
					if(size>0){
			             for (OrgModel result : results) {
			            	 JSONObject rec = JSONFactoryUtil.createJSONObject();
			            	 rec.put("orgCode",result.getFacultyCode());
			            	 rec.put("orgDesc",result.getFacultyName());
			            	 orgList.put(rec);
			             }
					}
				}//end result!=null
			}else if(org.getLevelId().equals(2)){
				results = service.getOrgCourseOfFaculty(org);
				if(results!=null){
					size = results.size();
					if(size>0){
			             for (OrgModel result : results) {
			            	 JSONObject rec = JSONFactoryUtil.createJSONObject();
			            	 rec.put("orgCode",result.getCourseCode());
			            	 rec.put("orgDesc",result.getCourseName());
			            	 orgList.put(rec);
			             }
					}
				}//end result!=null
			}
		}else{
			status = "invalid parameters";
		}
		content.put("lists",orgList);
		header.put("status", status);
		header.put("size", size);
		json.put("header",header);
		json.put("content", content);
		System.out.println(json.toString());
		response.getWriter().write(json.toString());
	}
	//################ helper  function  ###################//
		private String validParamString(Object param){
			String str = null;
			if(param!=null){
				if(param instanceof String ){
					str = (String) param;
				}else if(param instanceof Integer){
					str = String.valueOf( param );
				}
			}else{
				str = "";
			}
			return str;
		}
		private List<KpiResultListForm> convertAccordion(List<KpiResultModel> kpis){
			ListIterator<KpiResultModel> kpisIter = kpis.listIterator();
			//required sort data from service;
			List<KpiResultModel> listKpi = new ArrayList<KpiResultModel>();
			KpiResultListForm form = new KpiResultListForm();
			List<KpiResultListForm> forms = new ArrayList<KpiResultListForm>();
			Integer previousId = null;
			while(kpisIter.hasNext()){
				KpiResultModel kpi = (KpiResultModel) kpisIter.next();
				if(!kpi.getKpiStructureId().equals(previousId) || previousId==null){ // if not
					form = new KpiResultListForm();
					form.setStructureName(kpi.getKpiStructureName());
					listKpi = new ArrayList<KpiResultModel>();
					listKpi.add(kpi);
				}
				else{ // equals previousId
					listKpi.add(kpi);
				}
				//
				if(kpisIter.hasNext()){
					Integer current = kpi.getKpiStructureId();
					KpiResultModel next = (KpiResultModel) kpisIter.next();
					if(!next.getKpiStructureId().equals(current)){
						form.setKpiResults(listKpi);
						forms.add(form);
					}
					kpisIter.previous();
				}else{ // last
					form.setKpiResults(listKpi);
					forms.add(form);
				}
				// last thing 
				previousId = kpi.getKpiStructureId();
			}	// end while iterator 
			return forms;
		}
		
		// For file download in liferay portlet application
	    @ResourceMapping(value="fileDownload")
	    @ResponseBody 
	    public void fileDownload(ResourceRequest request, ResourceResponse response) throws IOException{
	        // required  filename , username(liferay screenname)
	    	//http://localhost:8080/web/guest/result?p_p_id=Result_WAR_ChandraEduqa&p_p_lifecycle=2&p_p_resource_id=fileDownload&filename=xxx

    		response.setContentType("application/xml");
    		response.addProperty("Content-disposition", "atachment; filename=error.txt");
    		
	    	try{	
	    		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
				HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
	    		String requestname = normalRequest.getParameter("filename");
	    	
	    		User user = (User) request.getAttribute(WebKeys.USER);
	    		String loginName = user.getScreenName();
	    	
		       	String fileName = uploadDirectory +  loginName + directoryDelimitor + requestname;

		        InputStream inputStream =  new FileInputStream(fileName);
		        IOUtils.copy(inputStream, response.getPortletOutputStream());
		        response.flushBuffer();
		        inputStream.close();

	    		response.setProperty("Content-disposition", "atachment; filename="+requestname);
	 		
	    	}catch(Exception ex){
	    	
	    		 String errorMessage = "ไม่พบไฟล์. The file you are looking for does not exist."+ex.getMessage();
	    	      // Convert String to bytes 
	 	        	byte[] bytes = errorMessage.getBytes();
	 	        	 OutputStream out = response.getPortletOutputStream();
	 	            out.write(bytes);
	 	            out.flush();
	 	            out.close();
	 	    
	    	}
	    }
	 // For file download in liferay portlet application
	    @ResourceMapping(value="fileDownloadExternal")
	    @ResponseBody 
	    public void fileDownloadExternal(ResourceRequest request, ResourceResponse response) throws IOException{
	        // required  filename , username(liferay screenname)
	    	//http://localhost:8080/web/guest/result?p_p_id=Result_WAR_ChandraEduqa&p_p_lifecycle=2&p_p_resource_id=fileDownload&user=xxxx&filename=xxx
	    	response.setContentType("application/xml");
    		response.addProperty("Content-disposition", "atachment; filename=error.txt");
	    	try{	
	    		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
				HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
	    		String requestname = normalRequest.getParameter("filename");
	    		String loginName = normalRequest.getParameter("user");
	    	
		       	String fileName = uploadDirectory +  loginName + directoryDelimitor + requestname;

		        InputStream inputStream =  new FileInputStream(fileName);
		        IOUtils.copy(inputStream, response.getPortletOutputStream());
		        response.flushBuffer();
		        inputStream.close();

	    		response.setProperty("Content-disposition", "atachment; filename="+requestname);
	 		
	    	}catch(Exception ex){
	    		 String errorMessage = "ไม่พบไฟล์. The file you are looking for does not exist.";
	    	      // Convert String to bytes 
	 	        	byte[] bytes = errorMessage.getBytes();
	 	        	 OutputStream out = response.getPortletOutputStream();
	 	            out.write(bytes);
	 	            out.flush();
	 	            out.close();
	 	    
	    	}
	    }
	    @ResourceMapping(value="evidenceFileDelete")
		@ResponseBody 
		public void quantityAjaxDeleteEvidence(ResourceRequest request,ResourceResponse response) throws IOException{
			//WARNING**   service cann't be null
			// return <id,desc>
			HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
			HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
			Integer evidenceId = Integer.parseInt( normalRequest.getParameter("evidenceId"));
			JSONObject json = JSONFactoryUtil.createJSONObject();
			JSONObject header = JSONFactoryUtil.createJSONObject();
			Integer success  = 0;
			User user = (User) request.getAttribute(WebKeys.USER);
	    	
			List<String> errorMessages = new ArrayList<String>();
			//find type  from evidenceId>result_detail_id 
			if(evidenceId==null || evidenceId<=0 ){
				errorMessages.add("ไม่พบหลักฐาน");
			}
			else{
			//	String filepath = "/home/gjserv/FileServerPath/";
			
				CdsEvidenceModel evidence = service.findCdsEvidenceById(evidenceId);
				CdsResultDetailModel cdsResultDetail = service.findCdsResultDetailById(evidence.getResultDetailId());
				if(cdsResultDetail.getEvidenceFlag().equals("F")){ // file
					// file delete  ***WARING *** to delete user FILED CREATE BY TO DETERMINE directory store file
					   String fileName = uploadDirectory + cdsResultDetail.getCreatedBy() + directoryDelimitor + evidence.getEvidencePath();
				        File file = new File(fileName);
				        if(file.delete()){
				        	success = service.deleteCdsEvidence(evidence);
				        }else{
				        	errorMessages.add("ลบไฟล์หลักฐานไม่สำเร็จ");
				        }
				}else if(cdsResultDetail.getEvidenceFlag().equals("L")){ // file
					success = service.deleteCdsEvidence(evidence);
				}
			}
			header.put("success",success );
			header.put("errorMessage", StringUtils.join(" ",errorMessages) );
			json.put("header",header);
			 System.out.println(json.toString());
			 response.getWriter().write(json.toString());
	    }
	    @ResourceMapping(value="kpiEvidenceFileDelete")
		@ResponseBody 
		public void qualityAjaxDeleteEvidence(ResourceRequest request,ResourceResponse response) throws IOException{
			//WARNING**   service cann't be null
			// return <id,desc>
			HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
			HttpServletRequest normalRequest	=	PortalUtil.getOriginalServletRequest(httpReq);
			Integer evidenceId = Integer.parseInt( normalRequest.getParameter("evidenceId"));
			Integer kpiId = Integer.parseInt( normalRequest.getParameter("kpiId"));
			Integer orgId = Integer.parseInt( normalRequest.getParameter("orgId"));
			Integer monthId = Integer.parseInt( normalRequest.getParameter("monthId"));
			Integer criteriaId = Integer.parseInt( normalRequest.getParameter("criteriaId"));
			JSONObject json = JSONFactoryUtil.createJSONObject();
			JSONObject header = JSONFactoryUtil.createJSONObject();
			User user = (User) request.getAttribute(WebKeys.USER);
	    	
			Integer success  = 0;
			List<String> errorMessages = new ArrayList<String>();
			//find type  from evidenceId>result_detail_id 
		
			//String filepath = "/home/gjserv/FileServerPath/";
			
				KpiResultDetailModel krd = new KpiResultDetailModel();
				krd.setKpiId(kpiId);
				krd.setOrgId(orgId);
				krd.setMonthId(monthId);
				krd.setCriteriaId(criteriaId);
				krd = service.findKpiResultDetailByIdentify(krd);
				if(krd.getEvidenceFlag().equals("F")){ // file
					String fileName = uploadDirectory + krd.getCreatedBy() + directoryDelimitor + krd.getEvidencePath();
			        File file = new File(fileName);
			        if(file.delete()){
					       
			        }else{
		//	        	errorMessages.add("ไม่สามารถลบไฟล์หลักฐานได้");
			        }
				}
					
				KpiEvidenceModel kpiEvi = new KpiEvidenceModel();
				kpiEvi.setEvidenceId(evidenceId);
				Integer deleteCount = service.deleteKpiEvidence(kpiEvi);
				if(deleteCount>0){
				}else{
					errorMessages.add("ไม่พบข้อมูลหลักฐาน");
				}
				if(errorMessages.size()<=0){
					success=1;
				}
			header.put("success",success );
			header.put("errorMessage", StringUtils.join(" ",errorMessages) );
			json.put("header",header);
			 System.out.println(json.toString());
			 response.getWriter().write(json.toString());
	    }
		// file upload & download
		   // For file upload 
		@RequestMapping(params="action=fileUpload") 
			public void fileUpload(javax.portlet.ActionRequest request, javax.portlet.ActionResponse response
					,@ModelAttribute("evidenceQuantityForm") EvidenceQuantityForm form,BindingResult result,Model model){
			User user = (User) request.getAttribute(WebKeys.USER);
			String message = "";
			//copy file
	        try{
	        	FileCopyUtils.copy(form.getFileData().getBytes(), new File( uploadDirectory + user.getScreenName() + directoryDelimitor + form.getFileData().getOriginalFilename()));
	        	
	        	// insert database
	        	CdsResultModel crm = new CdsResultModel();
	        	crm.setCdsId(form.getCdsId());
	        	crm.setOrgId(form.getOrgId());
	        	crm.setMonthId(form.getMonthId());
	        	crm = service.findCdsResultByCds(crm);
	        	CdsResultDetailModel crdm = new CdsResultDetailModel();
	        	crdm.setResultId(crm.getResultId());
	        	crdm = service.findCdsResultDetail(crdm);
	        	CdsEvidenceModel evidenceModel = new CdsEvidenceModel();
	        	evidenceModel.setResultDetailId(crdm.getResultDetailId());
	        	evidenceModel.setEvidencePath(form.getFileData().getOriginalFilename());
	        	evidenceModel.setCreatedBy(user.getScreenName());
	        	evidenceModel.setUpdatedBy(user.getScreenName());
	        	Integer evidenceId = service.saveCdsEvidence(evidenceModel);
	        	if(evidenceId<=0){
	        	}else{
	        		message = form.getFileData().getOriginalFilename()+" is upload successfully";
	        	}
	        }catch(Exception e){
	        	message = form.getFileData().getOriginalFilename()+" is upload fail";
	        }
	        
			response.setRenderParameter("render", "showResultDetailQuantity");
			response.setRenderParameter("orgId", String.valueOf(form.getOrgId() ) );
			response.setRenderParameter("kpiId", String.valueOf(form.getKpiId()) );
			response.setRenderParameter("monthId", String.valueOf(form.getMonthId() ) );
			response.setRenderParameter("cdsId", String.valueOf(form.getCdsId() ) );
			response.setRenderParameter("evidenceMessage", message);
	    }
		
		
	@ResourceMapping(value = "doSaveAutoSaveResult")
	@ResponseBody
	public void doSaveAutoSaveResult(ResourceRequest request, ResourceResponse resourceResponse) throws IOException {
		HttpServletRequest httpReq = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest normalRequest = PortalUtil.getOriginalServletRequest(httpReq);
	
		Integer orgId = Integer.parseInt( normalRequest.getParameter("orgId"));
		Integer kpiId = Integer.parseInt(normalRequest.getParameter("kpiId"));
		Integer monthId = Integer.parseInt(normalRequest.getParameter("monthId"));
		Integer criteriaId = Integer.parseInt(normalRequest.getParameter("selectStdId"));
		Boolean actionFlag = Boolean.valueOf(normalRequest.getParameter("actionFlag"));
		
		// declare attrbuite
		List<String> errorMessages = new ArrayList<String>();
		JSONObject jsonMessages = JSONFactoryUtil.createJSONObject();
		User user = (User) request.getAttribute(WebKeys.USER);
		SysYearModel sysYear = service.getSysYear();
		// save kpiResultDetail
		KpiResultDetailModel kpiResultDetailM = new KpiResultDetailModel();
		// find resultId;
		kpiResultDetailM.setKpiId(kpiId);
		kpiResultDetailM.setOrgId(orgId);
		kpiResultDetailM.setMonthId(monthId);
		kpiResultDetailM.setCriteriaId(criteriaId);
		if (actionFlag) {
			kpiResultDetailM.setActionFlag("1");
		} else {
			kpiResultDetailM.setActionFlag("0");
		}
		kpiResultDetailM.setCreatedBy(user.getFullName());
		kpiResultDetailM.setUpdatedBy(user.getFullName());
		kpiResultDetailM.setEvidenceFlag(null); // update when click view evidence list
		kpiResultDetailM.setAcademicYear(sysYear.getAppraisalAcademicYear());
		Integer success = service.saveKpiResultDetail(kpiResultDetailM);
		if (success <= 0) {
			errorMessages.add("บันทึกไม่สำเร็จ");
			jsonMessages.put("msgCode", "0").put("msgDesc", "บันทึกไม่สำเร็จ");
		}

		String resultMessage = "";
		if (errorMessages.size() <= 0) {
			resultMessage = "บันทึกสำเร็จ";
			jsonMessages.put("msgCode", "1").put("msgDesc", "บันทึกสำเร็จ");
		} else {
			resultMessage = StringUtils.join(" ", errorMessages);
		}
		resourceResponse.getWriter().write(jsonMessages.toString());
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
}
