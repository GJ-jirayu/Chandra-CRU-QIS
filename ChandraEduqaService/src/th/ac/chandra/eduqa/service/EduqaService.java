package th.ac.chandra.eduqa.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import th.ac.chandra.eduqa.domain.*;
import th.ac.chandra.eduqa.model.*;
import th.ac.chandra.eduqa.xstream.common.Paging;;

public interface EduqaService { 
	// ####master ####
	public Integer saveKpiLevel(KpiLevel transientInstance) throws DataAccessException;
	public Integer updateKpiLevel(KpiLevel transientInstance) throws DataAccessException ;
	public Integer deleteKpiLevel(KpiLevel persistentInstance) throws DataAccessException ;	
	public KpiLevel findKpiLevelById(Integer KpiLevelId)throws DataAccessException  ;
	@SuppressWarnings("rawtypes")
	public  List searchKpiLevel(KpiLevel persistentInstance,Paging pagging,String keySearch)throws DataAccessException  ;
	
	//KPI Group
		public Integer saveKpiGroup(KpiGroup transientInstance) throws DataAccessException;
		public Integer updateKpiGroup(KpiGroup transientInstance) throws DataAccessException;
		public Integer deleteKpiGroup(KpiGroup persistentInstance) throws DataAccessException;	
		public KpiGroup findKpiGroupById(Integer KpiGroupId)throws DataAccessException;
		@SuppressWarnings("rawtypes")
		public  List searchKpiGroup(KpiGroup persistentInstance,Paging pagging,String keySearch)throws DataAccessException;
		
		//KPI Group Type 
		public Integer saveKpiGroupType(KpiGroupType transientInstance) throws DataAccessException;
		public Integer updateKpiGroupType(KpiGroupType transientInstance) throws DataAccessException;
		public Integer deleteKpiGroupType(KpiGroupType persistentInstance) throws DataAccessException;	
		public KpiGroupType findKpiGroupTypeById(Integer KpiGroupId)throws DataAccessException;
		@SuppressWarnings("rawtypes")
		public  List searchKpiGroupType(KpiGroupType persistentInstance,Paging pagging,String keySearch)throws DataAccessException;
		
		//KPI Type
		public Integer saveKpiType(KpiType transientInstance) throws DataAccessException;
		public Integer updateKpiType(KpiType transientInstance) throws DataAccessException;
		public Integer deleteKpiType(KpiType persistentInstance) throws DataAccessException;	
		public KpiType findKpiTypeById(Integer KpiTypeId)throws DataAccessException;
		@SuppressWarnings("rawtypes")
		public  List searchKpiType(KpiType persistentInstance,Paging pagging,String keySearch)throws DataAccessException;
		
		//KPI Uom
		public Integer saveKpiUom(KpiUom transientInstance) throws DataAccessException;
		public Integer updateKpiUom(KpiUom transientInstance) throws DataAccessException;
		public Integer deleteKpiUom(KpiUom persistentInstance) throws DataAccessException;	
		public KpiUom findKpiUomById(Integer KpiTypeId)throws DataAccessException;
		@SuppressWarnings("rawtypes")
		public  List searchKpiUom(KpiUom persistentInstance,Paging pagging,String keySearch)throws DataAccessException;
		
		//KPI Structure
		public Integer saveKpiStruc(KpiStruc transientInstance) throws DataAccessException;
		public Integer updateKpiStruc(KpiStruc transientInstance) throws DataAccessException;
		public Integer deleteKpiStruc(KpiStruc persistentInstance) throws DataAccessException;	
		public KpiStruc findKpiStrucById(Integer KpiTypeId)throws DataAccessException;
		@SuppressWarnings("rawtypes")
		public  List searchKpiStruc(KpiStruc persistentInstance,Paging pagging,String keySearch)throws DataAccessException;
		
		//ORG
		public Org findOrgById(Integer orgId);
		@SuppressWarnings("rawtypes")
		public Org searchOrg(Org persistentInstance, Paging pagging, String keySearch) throws DataAccessException;
		public List searchOrgByLevelId(Org persistentInstance, Paging pagging, String keySearch) throws DataAccessException;
		public List searchOrgGroupByCourseCode(Org persistentInstance, Paging pagging, String keySearch) throws DataAccessException;
		public List searchOrgByFacultyCode(Org persistentInstance, Paging pagging, String keySearch) throws DataAccessException;
		public List searchOrgIdByOthersCode(Org persistentInstance, Paging pagging,String keySearch, String[] otherKeySearch) throws DataAccessException;
		public List getAllOrgUniversity(Org org,Paging page);
		public List getOrgFacultyOfUniversity(Org org,Paging page);
		public List getOrgCourseOfFaculty(Org org,Paging page);
		public List getOrgIdByOrgDetailFilter(Org org,Paging page);
		
		//Org Type
		public OrgType findOrgTypeById(Integer orgTypeId);
		@SuppressWarnings("rawtypes")
		public List searchOrgType(OrgType persistentInstance, Paging pagging, String keySearch) throws DataAccessException;
		
		//Structure Type
		public StrucType findStrucTypeById(Integer StrucTypeId);
		@SuppressWarnings("rawtypes")
		public List searchStrucType(StrucType persistentInstance, Paging pagging, String keySearch) throws DataAccessException;

	
	//db connection
	public DbConn findConnById(Integer connId)throws DataAccessException  ;
	public Integer saveConn(DbConn persistentInstance) throws DataAccessException;
	public Integer deleteConn(DbConn persistentInstance) throws DataAccessException;
	public Integer updateConn(DbConn transientInstance) throws DataAccessException ;
	@SuppressWarnings("rawtypes")
	public  List searchConn(DbConn persistentInstance,Paging pagging,String keySearch)throws DataAccessException  ;
	
	// query
	public boolean checkConnection(DbConnModel conn);
	public List previewMysqlQueryResult(DbQueryModel q);
	public List previewOracleQueryResult(DbQueryModel q);
	
	//cds
	public Cds findCdsById(Integer CdsId)throws DataAccessException  ;
	public Integer saveCds(Cds persistentInstance) throws DataAccessException;
	public Integer deleteCds(Cds persistentInstance) throws DataAccessException;
	public Integer updateCds(Cds transientInstance) throws DataAccessException ;
	@SuppressWarnings("rawtypes")
	public  List searchCds(Cds persistentInstance,Paging pagging,String keySearch)throws DataAccessException  ;
	
	//kpi
	public Kpi findKpiById(Integer KpiId)throws DataAccessException  ;
	public KpiModel findKpiWithDescById(KpiModel kpiM)throws DataAccessException  ;
	public Integer saveKpi(Kpi persistentInstance) throws DataAccessException;
	public Integer saveKpiFormula(Kpi persistentInstance) throws DataAccessException;
	public Integer deleteKpi(Kpi persistentInstance) throws DataAccessException;
	public Integer updateKpi(Kpi transientInstance) throws DataAccessException ;
	@SuppressWarnings("rawtypes")
	public  List searchKpi(Kpi persistentInstance,Paging pagging,String keySearch)throws DataAccessException  ;
	
	// criteria 
	public List searchCriteriaGroup(Paging pagging,String keySearch) throws DataAccessException;
	public List searchCriteriaGroupDetail(Integer groupId,Paging pagging) throws DataAccessException; 
	public List searchCriteriaStandard(Integer kpiId)  throws DataAccessException; 
	public Integer saveCriteriaStandard(CriteriaStandard std)	throws DataAccessException;
	public Integer updateCriteriaStandard(CriteriaStandard std)	throws DataAccessException;
	public Integer deleteCriteriaStandard(CriteriaStandard std)	throws DataAccessException;

	//baseline
	public Integer saveQuanBaseline(BaselineQuan baseline)  throws DataAccessException;
	public Integer updateQuanBaseline(BaselineQuan baseline)  throws DataAccessException;
	public Integer deleteQuanBaseline(BaselineQuan baseline)  throws DataAccessException;
	public Integer saveRangeBaseline(BaselineRange baseline)  throws DataAccessException;
	public Integer updateRangeBaseline(BaselineRange baseline)  throws DataAccessException;
	public Integer deleteRangeBaseline(BaselineRange baseline)  throws DataAccessException;
	public Integer saveSpecBaseline(BaselineSpec baseline)  throws DataAccessException;
	public Integer updateSpecBaseline(BaselineSpec baseline)  throws DataAccessException;
	public Integer deleteSpecBaseline(BaselineSpec baseline)  throws DataAccessException;
	public List listBaselineQuan(BaselineQuan baseline) throws DataAccessException;
	public List listBaselineRange(BaselineRange baseline) throws DataAccessException;
	public List listBaselineSpec(BaselineSpec spec) throws DataAccessException;
	public List listBaselineSpecGroup(BaselineSpec baseline) throws DataAccessException;
	public List listBaselineSpecDetail(BaselineSpec baseline) throws DataAccessException;

	// Threshold
	public Integer saveThreshold(Threshold transientInstance) throws DataAccessException;
	public Integer updateThreshold(Threshold transientInstance) throws DataAccessException;
	public Integer deleteThreshold(Threshold persistentInstance) throws DataAccessException;
	public Threshold findThresholdById(Integer ThresholdId) throws DataAccessException;
	@SuppressWarnings("rawtypes") 
	public List searchThreshold(Threshold persistentInstance, Paging pagging, String keySearch) throws DataAccessException;
	
	//SYS YEAR
	public SysYear getSysYear()  throws DataAccessException;
	public Integer saveSysYear(SysYear transientInstance) throws DataAccessException;
	public Integer updateSysYear(SysYear transientInstance) throws DataAccessException;
	@SuppressWarnings("rawtypes") 
	public List searchSysYear(SysYear persistentInstance, Paging pagging, String keySearch) throws DataAccessException;
	
	//SYS MONTH
	public SysMonthModel findSysMonthById(Integer monthId) throws DataAccessException;
	public Integer saveSysMonth(SysMonth transientInstance) throws DataAccessException;
	public Integer updateSysMonth(SysMonth transientInstance) throws DataAccessException;
	@SuppressWarnings("rawtypes") 
	public List searchSysMonth(SysMonth persistentInstance, Paging pagging, String keySearch) throws DataAccessException;
	public List getCalendarYearsByAcad(SysMonth months,Paging pagging,String otherSearch) throws DataAccessException;
	public List getMonthsByCalendar(SysMonth months,Paging pagging,String otherSearch) throws DataAccessException;
	public  List getMonthId(SysMonth persistentInstance,Paging pagging,String keySearch, String[] otherKeySearch)throws DataAccessException;
	
	
	//KPI RECOMMEND
	public Integer saveKpiReComnd(KpiReComnd transientInstance) throws DataAccessException;
	public Integer updateKpiReComnd(KpiReComnd transientInstance) throws DataAccessException;
	public Integer deleteKpiReComnd(KpiReComnd persistentInstance) throws DataAccessException;	
	public KpiReComnd findKpiReComndById(Integer KpiTypeId)throws DataAccessException;
	@SuppressWarnings("rawtypes")
	public  List searchKpiReComnd(KpiReComnd persistentInstance,Paging pagging,String keySearch, String[] otherKeySearch)throws DataAccessException;
	
	//KPI RESULT
	public Integer saveKpiResult(KpiResult transientInstance) throws DataAccessException;
	public Integer updateKpiResult(KpiResult transientInstance) throws DataAccessException;
	public Integer deleteKpiResult(KpiResult persistentInstance) throws DataAccessException;	
	public KpiResult findKpiResultById(Integer KpiTypeId)throws DataAccessException;
	@SuppressWarnings("rawtypes")
	public  List searchKpiResult(KpiResult persistentInstance,Paging pagging,String keySearch, String[] otherKeySearch)throws DataAccessException;
	public  KpiResult findKpiResultByKpi(KpiResult domain) throws DataAccessException;
	public Integer insertsKpiResult(KpiResultModel model) throws DataAccessException;
	public Integer deleteKpiResultByOrgId(KpiResultModel model) throws DataAccessException;
	public KpiResultModel findKpiResultByIdentify(KpiResultModel model) throws DataAccessException;
	@SuppressWarnings("rawtypes")
	public List searchKpiResultWithActiveKpi(KpiResultModel model) throws DataAccessException;
	
	//CDS RESULT
	public Integer saveCdsResult(CdsResult transientInstance) throws DataAccessException;
	public CdsResult findCdsResultById(Integer cdsResultId) throws DataAccessException;
	public  List searchCdsResult(CdsResult persistentInstance,Paging pagging,
			String keySearch, String[] otherKeySearch)throws DataAccessException;
	@SuppressWarnings("rawtypes")
	public List searchCdsUsedByKpiId(CdsResult persistentInstance,Paging pagging,
			String keySearch, String[] otherKeySearch)throws DataAccessException;
		
	//CDS RESULT DETAIL 
	public Integer saveCdsResultDetail(CdsResultDetail transientInstance) throws DataAccessException;
	public Integer updateCdsResultDetail(CdsResultDetail transientInstance) throws DataAccessException;
	public CdsResultDetail findCdsResultDetailById(Integer cdsResultDetail) throws DataAccessException;
	@SuppressWarnings("rawtypes")
	public List searchCdsResultDetail(CdsResultDetail persistentInstance,Paging pagging,
			String keySearch, String[] otherKeySearch)throws DataAccessException;
	public CdsResultDetail findCdsResultDetail(CdsResultDetailModel model) throws DataAccessException;
		
	//CDS EVIDENCE 
	public Integer saveCdsEvidence(CdsEvidence transientInstance) throws DataAccessException;
	public Integer deleteCdsEvidence(CdsEvidence transientInstance) throws DataAccessException;
	public CdsEvidence findCdsEvidenceById(Integer cdsEvidence) throws DataAccessException;
	@SuppressWarnings("rawtypes")
	public List searchCdsEvidence(CdsEvidence evidence,Paging pagging)throws DataAccessException;
	
	//kpi evidence
	public Integer saveKpiEvidence(KpiEvidenceModel model) throws DataAccessException;
	public Integer deleteKpiEvidence(KpiEvidenceModel model) throws DataAccessException;
	public List<KpiEvidenceModel> searchKpiEvidence(KpiEvidenceModel model) throws DataAccessException;
		
	// kpi target 
	public KpiTargetModel getKpiTarget(KpiTargetModel model) throws DataAccessException;
	public Integer saveKpiTarget(KpiResult result,KpiTargetModel target) throws DataAccessException;
	public KpiResult resultDescByKpiId(KpiResult result) throws DataAccessException;
	
	//decription detail   > period,uom,calendar
	public List<DescriptionModel> getOrgOfUser(DescriptionModel model);
	public List getPeriodAll(DescriptionModel model);
	public List getUomAll(DescriptionModel model);
	public List getCalendarAll(DescriptionModel model);
	public List getCriteriaTypeAll(DescriptionModel model);
	public List getCriteriaMethodAll(DescriptionModel model);
	public List getKpiNameAll();
	public List getUniversityAll();
	public List getFacultyAll();
	public List getCourseAll();
	public List getMonthAll(Integer yearNo);
	
	//???????? add by p @20/10/2558
	public List getCdsMapWithKpi(CdsResultModel model) throws DataAccessException ;
	public CdsResult findCdsResultByCds(CdsResult cds);
	public List<KpiResultDetailModel> findCriteriaResult(KpiResultDetailModel model)  throws DataAccessException;
	public KpiResultDetailModel findKpiResultDetailByIdentify(KpiResultDetailModel model)  throws DataAccessException;
	public Integer saveKpiResultDetail(KpiResultDetailModel model ) throws DataAccessException;
	public Integer updateKpiResultDetailEvidence(KpiResultDetailModel model) throws DataAccessException;
	public KpiResultDetail findKpiResultDetailById(KpiResultDetailModel model)  throws DataAccessException;

	public Integer generateSysMonth(SysYearModel model) throws DataAccessException ;
	
	public Integer deleteKpiXCds(KpiXCds model) throws DataAccessException;
	public Integer deleteBaselineSpecDetailByKpiId(Integer kpiId) throws DataAccessException;
	public Integer deleteBaselineQuanByKpiId(BaselineQuan model) throws DataAccessException;
	public Integer deleteCriteriaStandardByKpiI(CriteriaStandard model) throws DataAccessException;
	public Integer deleteKpiResultByKpiId(KpiResultModel model) throws DataAccessException;
	public Integer deleteRangeBaselineByKpiId(KpiResultModel model) throws DataAccessException;
	
}