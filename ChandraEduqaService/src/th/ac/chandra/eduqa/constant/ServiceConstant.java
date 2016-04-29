package th.ac.chandra.eduqa.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ServiceConstant {
	//public static final String hostReference = "http://10.2.0.76:10000/BPSService/RestletServlet/";
	
	public static final String LOG_APPENDER = "FinWizLog";
	
	public static final String INTERFACE_RETURN_TYPE = "java.util.List";
	public static final String VOID_RETURN_TYPE = "void";
	public static final ResourceBundle bundle;
	public static String SCHEMA="";
	static{
		bundle =  ResourceBundle.getBundle( "jdbc" );	
		SCHEMA=bundle.getString("schema");
	}
	// USER_PROFILE
	public static final String USER_PROFILE_LOGIN = "loginUserProfile";
	public static final String USER_PROFILE_LOOUT = "logoutUserProfile";
	public static final String USER_PROFILE_SEARCH = "SearchUserProfile";
	public static final String USER_PROFILE_SAVE= "saveUserProfile";	
	public static final String MISS_ACCOUNT_UPDATE = "updateUserProfile";
	public static final String MISS_ACCOUNT_ITEMS_DELETE = "deleteUserProfileItems";
	public static final String MISS_ACCOUNT_DELETE = "deleteUserProfile";

	//UserPortal
	public static final String USER_PORTAL_SEARCH = "searchUserPortal";
	public static final String USER_PORTAL_FIND_BY_ID = "findByIdUserPortal";	
	
	
	// ## master
	public static final String LEVEL_SEARCH = "searchLevel";
	public static final String LEVEL_SAVE= "saveLevel";	
	public static final String LEVEL_UPDATE = "updateLevel";
	public static final String LEVEL_DELETE = "deleteLevel";
	public static final String LEVEL_FIND_BY_ID = "findLevelById";
	
		//KPI Group
		public static final String GROUP_SEARCH = "searchGroup";
		public static final String GROUP_SAVE= "saveGroup";	
		public static final String GROUP_UPDATE = "updateGroup";
		public static final String GROUP_DELETE = "deleteGroup";
		public static final String GROUP_FIND_BY_ID = "findGroupById";
		
		//KPI Group Type
		public static final String GROUP_TYPE_SEARCH = "searchGroupType";
		public static final String GROUP_TYPE_SAVE= "saveGroupType";	
		public static final String GROUP_TYPE_UPDATE = "updateGroupType";
		public static final String GROUP_TYPE_DELETE = "deleteGroupType";
		public static final String GROUP_TYPE_FIND_BY_ID = "findGroupTypeById";
		
		//KPI Type
		public static final String TYPE_SEARCH = "searchType";
		public static final String TYPE_SAVE= "saveType";	
		public static final String TYPE_UPDATE = "updateType";
		public static final String TYPE_DELETE = "deleteType";
		public static final String TYPE_FIND_BY_ID = "findTypeById";
		
		//KPI Uom
		public static final String UOM_SEARCH = "searchUom";
		public static final String UOM_SAVE= "saveUom";	
		public static final String UOM_UPDATE = "updateUom";
		public static final String UOM_DELETE = "deleteUom";
		public static final String UOM_FIND_BY_ID = "findUomById";
		
		//KPI Structure
		public static final String STRUC_SEARCH = "searchStruc";
		public static final String STRUC_SAVE= "saveStruc";	
		public static final String STRUC_UPDATE = "updateStruc";
		public static final String STRUC_DELETE = "deleteStruc";
		public static final String STRUC_FIND_BY_ID = "findStrucById";
		
		//Org
		public static final String ORG_SEARCH = "searchOrg";
		public static final String ORG_FIND_BY_ID = "findOrgById";
		public static final String ORG_SEARCH_BY_LEVEL = "searchOrgByLevelId";
		public static final String ORG_SEARCH_GROUPBY_COURSE = "searchOrgGroupByCourseCode";
		public static final String ORG_SEARCH_BY_FACULTY= "searchOrgByFacultyCode";
		public static final String ORG_SEARCH_BY_COURSE ="searchOrgByCourseCode";
		// addtional by pun
		public static final String ORG_GET_UNIVERSITY = "searchOrgUniversity";
		public static final String ORG_GET_FACULTY_OF_UNIVERSITY = "searchFacultyByUniversity";
		public static final String ORG_GET_COURSE_OF_FACULTY = "searchCourseByFaculty";
		public static final String ORG_GET_ORGID_OF_ORG_DETAIL = "getOrgIdByOrgDetailFilter";
		
		
		//Org Type
		public static final String ORG_TYPE_SEARCH = "searchOrgType";
		public static final String ORG_TYPE_FIND_BY_ID = "findOrgTypeById";
		
		//Structure Type
		public static final String STRUC_TYPE_SEARCH = "searchStrucType";
		public static final String STRUC_TYPE_FIND_BY_ID = "findStrucTypeById";
	
	//###### entry
		public static final String CDS_SEARCH = "searchCds";
		public static final String CDS_SAVE = "saveCds";
		public static final String CDS_UPDATE = "updateCds";
		public static final String CDS_DELETE = "deleteCds";
		public static final String CDS_FIND_BY_ID = "findCds";
			
		public static final String CONN_SEARCH = "searchConn";
		public static final String CONN_SAVE = "saveConn";
		public static final String CONN_UPDATE = "updateConn";
		public static final String CONN_DELETE = "deleteConn";
		public static final String CONN_FIND_BY_ID = "findConn";
		
		public static final String query_preview = "queryPreview";
		
		public static final String KPI_SEARCH = "searchKpi";
		public static final String KPI_SAVE= "saveKpi";	
		public static final String KPI_UPDATE = "updateKpi";
		public static final String KPI_DELETE = "deleteKpi";
		public static final String KPI_FIND_BY_ID = "findKpi";
		public static final String KPI_FIND_DETAIL_BY_ID = "findKpiWithDetail";
		public static final String KPI_SAVE_FORMULA = "saveKpiFormula";
		
		
		public static final String CRITERIA_GROUP_SEARCH = "searchCriteriaGroup";
		public static final String CRITERIA_DETAIL_BY_GROUP = "searchCriteriaGroupDetail";
		
		public static final String CRITERIA_STD_SEARCH = "searchStandardCriteria";
		public static final String CRITERIA_STD_SAVE = "saveStandardCriteria";
		public static final String CRITERIA_STD_DELETE = "deleteStandardCriteria";
		
		public static final String BASELINE_LIST = "listBaseline";
		public static final String BASELINE_DELETE = "deleteBaseline";
		public static final String BASELINE_QUAN_SAVE = "saveQuanBaseline";
		public static final String BASELINE_RANGE_SAVE = "saveRangeBaseline";
		public static final String BASELINE_SPEC_SAVE = "saveSpecBaseline";
		
		//KPI Threshold
		public static final String THRESHOLD_SEARCH = "searchThreshold";
		public static final String THRESHOLD_SAVE= "saveThreshold";	
		public static final String THRESHOLD_UPDATE = "updateThreshold";
		public static final String THRESHOLD_DELETE = "deleteThreshold";
		public static final String THRESHOLD_FIND_BY_ID = "findThresholdById";
		
		//Sys Year
		public static final String SYSYEAR_GET = "getSysYear";
		public static final String SYSYEAR_SEARCH = "searchSysYear";
		public static final String SYSYEAR_SAVE= "saveSysYear";	
		public static final String SYSYEAR_UPDATE = "updateSysYear";
		public static final String SYSYEAR_CREATE_SYSMONTH = "sysyearGenMonth";
		
		//Sys Month
		public static final String SYSMONTH_FIND_BY_ID = "findSysMonthById";
		public static final String SYSMONTH_SEARCH = "searchSysMonth";
		public static final String SYSMONTH_SAVE= "saveSysMonth";	
		public static final String SYSMONTH_UPDATE = "updateSysMonth";
		public static final String SYSMONTH_GET_MONTH_ID = "getMonthId";
		public static final String SYSMONTH_GET_MONTH_BY_CALENDAR = "getMonthsByCalendar";
		public static final String SYSMONTH_GET_CALYEAR_BY_ACAD = "getCalendarYearByAcadYear";
		
		//KPI ReComndture
		public static final String RECOMND_SEARCH = "searchReComnd";
		public static final String RECOMND_SAVE= "saveReComnd";	
		public static final String RECOMND_UPDATE = "updateReComnd";
		public static final String RECOMND_DELETE = "deleteReComnd";
		public static final String RECOMND_FIND_BY_ID = "findReComndById";
		
		//KPI result
		public static final String RESULT_FIND_BY_ID = "findResultById";
		public static final String RESULT_SEARCH = "searchResult";
		public static final String RESULT_SAVE= "saveResult";	
		public static final String RESULT_UPDATE = "updateResult";
		public static final String RESULT_DELETE = "deleteResult";
		public static final String RESULT_FIND_BY_KPI = "findResultByKpi";
		public static final String RESULT_INSERTS = "resultInserts";
		public static final String RESULT_DELETE_BY_ORG  = "deleteResultKpi";
		public static final String RESULT_FIND_BY_IDENTIFY = "findResultKpiByIdentify";
		public static final String RESULT_GET_KPI_WITH_ACTIVE = "searchResultWithActiveKpi";
		
		
		//CDS RESULT
		public static final String CDS_RESULT_FIND_BY_ID = "findCdsResultById";
		public static final String CDS_USED_SEARCH_BY_KPI_ID = "searchCdsUsedByKpiId";
		public static final String CDS_RESULT_SAVE = "saveCdsResult";
		public static final String CDS_RESULT_SEARCH = "searchCdsResult";
		public static final String CDS_RESULT_FIND_BY_CDS = "findCdsResultByCds";
		
		//CDS RESULT DETAIL
		public static final String CDS_RESULT_DETAIL_FIND_BY_ID = "findCdsResultDetailById";
		public static final String CDS_RESULT_DETAIL_SEARCH = "searchCdsResultDetail";
		public static final String CDS_RESULT_DETAIL_SAVE = "saveCdsResultDetail";
		public static final String CDS_RESULT_DETAIL_UPDATE = "updateCdsResultDetail";
		public static final String CDS_RESULT_DETAIL_FIND_BY_MODEL = "findCdsResultDetailByModel";
		
		//CDS EVIDENCE
		public static final String CDS_EVIDENCE_SAVE = "saveCdsEvidence";
		public static final String CDS_EVIDENCE_DELETE = "deleteCdsEvidence";
		public static final String CDS_EVIDENCE_FIND_BY_ID = "findCdsEvidenceById";
		public static final String CDS_EVIDENCE_SEARCH = "searchCdsEvidence";
		

		//KPI EVIDENCE
		public static final String KPI_EVIDENCE_SAVE = "saveKpiEvidence";
		public static final String KPI_EVIDENCE_DELETE = "deleteKpiEvidence";
		public static final String KPI_EVIDENCE_SEARCH = "searchKpiEvidence";

		
		// kpi target
		public static final String TARGET_SAVE_LIST = "saveTargetList";
		public static final String TARGET_GET_LIST = "getTargetList";
		
		// mapping
		public static final String KPI_CDS_MAP_SEACH = "getAllKpiMapCds";
	/// message
	// repo query message
		public static final ArrayList<String> stat= new ArrayList<String>(Arrays.asList("มีข้อมูลอยู่ในระบบแล้ว","บันทึกข้อมูลเรียบร้อย"));
		//public static final List<String> stat = Arrays.asList("error", "ok");
		
		public static final String RESULT_OK = "100";
		public static final String RESULT_ERROR = "200";
		public static final String RESULT_WARNING = "300";
		public static final String MESSAGE_DUPLICATE = "ข้อมูลซ้ำ"; 
		public static final String MESSAGE_SAVE_SUCCESS = "บันทึกสำเร็จ";
		public static final String MESSAGE_UPDATE_SUCCESS = "บันทึกสำเร็จ";
		public static final String MESSAGE_DELETE_SUCCESS = "ลบสำเร็จ";
		
		//Error message
		public static final String ERROR_MESSAGE_KEY="errorMessage";
		public static final String SUCCESS_MESSAGE_KEY="successMessage";
		public static final String WARNING_MESSAGE_KEY="warningMessage";
		public static final String ERROR_CONSTRAINT_VIOLATION_MESSAGE_CODE="ไม่สามารถลบข้อมูลได้ ข้อมูลถูกใช้งานอยู่ในขณะนี้";
		
		//desc
		public static final String DESC_USER_ORG = "getUserOrg";
		public static final String DESC_PERIOD = "getDescPeriodAll";
		public static final String DESC_UOM = "getDescUomAll";
		public static final String DESC_CALENDAR = "getDescCalendarAll";
		public static final String DESC_CRITERIA_TYPE = "getCriteriaTypeAll";
		public static final String DESC_CRITERIA_METHOD = "getCriteriaMethodAll";
		public static final String DESC_KPI = "getKpiAll";
		public static final String DESC_UNIVERSITY = "getUniversityAll";
		public static final String DESC_FACULTY = "getFacultyAll";
		public static final String DESC_COURSE = "getCourseAll";
		public static final String DESC_MONTH = "getMonthAll";
		


		public static final String SAVE_RESULT_QUANTITY = "saveResultQuantity"; // table cds_result_detail
		public static final String SAVE_EVIDENCE_QUANTITY = "saveEvidenceQuantity"; // table cds_evidence
			
		public static final String CRITERIA_EXIST_RESULT_QUALITY = "criteriaExistResult"; //	 KPI_RESULT_DETAIL x CRITER_STANDARD use kpi_result_detail_model
		public static final String KPI_RESULT_DETAIL_FIND_BY_ID = "kpiResultDetailFindById";
		public static final String KPI_RESULT_DETAIL_FIND_BY_IDENTIFY = "kpiResultDetailFindByIdentify";
		public static final String RESULT_DETAIL_QUALITY_SAVE = "saveKpiResultDetail";		// kpi_result_detail
		public static final String RESULT_DETAIL_QUALITY_UPDATE_EVIDENCE = "updateKpiResultDetailEvidence";		// kpi_result_detail
		
}
