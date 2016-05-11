package th.ac.chandra.eduqa.service;

import java.util.List;

import th.ac.chandra.eduqa.mapper.ResultService;
import th.ac.chandra.eduqa.model.BaselineModel;
import th.ac.chandra.eduqa.model.CdsEvidenceModel;
import th.ac.chandra.eduqa.model.CdsModel;
import th.ac.chandra.eduqa.model.CdsResultDetailModel;
import th.ac.chandra.eduqa.model.CdsResultModel;
import th.ac.chandra.eduqa.model.CriteriaGroupModel;
import th.ac.chandra.eduqa.model.CriteriaModel;
import th.ac.chandra.eduqa.model.DbConnModel;
import th.ac.chandra.eduqa.model.DbQueryModel;
import th.ac.chandra.eduqa.model.DescriptionModel;
import th.ac.chandra.eduqa.model.KpiEvidenceModel;
import th.ac.chandra.eduqa.model.KpiGroupModel;
import th.ac.chandra.eduqa.model.KpiGroupTypeModel;
import th.ac.chandra.eduqa.model.KpiLevelModel;
import th.ac.chandra.eduqa.model.KpiModel;
import th.ac.chandra.eduqa.model.KpiReComndModel;
import th.ac.chandra.eduqa.model.KpiResultDetailModel;
import th.ac.chandra.eduqa.model.KpiResultModel;
import th.ac.chandra.eduqa.model.KpiStrucModel;
import th.ac.chandra.eduqa.model.KpiTargetModel;
import th.ac.chandra.eduqa.model.KpiTypeModel;
import th.ac.chandra.eduqa.model.KpiUomModel;
import th.ac.chandra.eduqa.model.OrgModel;
import th.ac.chandra.eduqa.model.OrgTypeModel;
import th.ac.chandra.eduqa.model.StrucTypeModel;
import th.ac.chandra.eduqa.model.SysMonthModel;
import th.ac.chandra.eduqa.model.SysYearModel;
import th.ac.chandra.eduqa.model.ThresholdModel;

public interface EduqaService { 
	public Integer getResultPage();
	public String getResultMessage();
	public String getResultCode();
	//kpi level
	public ResultService saveKpiLevel(KpiLevelModel kpiLevelModel);
	public ResultService updateKpiLevel(KpiLevelModel kpiLevelModel);
	public Integer deleteKpiLevel(KpiLevelModel kpiLevelModel);
	public KpiLevelModel findKpiLevelById(Integer kpiLevelId);
	@SuppressWarnings("rawtypes")
	public  List searchKpiLevel(KpiLevelModel  kpiLevelModel);
	
	//KPI Group
		public ResultService saveKpiGroup(KpiGroupModel kpiGroupModel);
		public ResultService updateKpiGroup(KpiGroupModel kpiGroupModel);
		public Integer deleteKpiGroup(KpiGroupModel kpiGroupModel);
		public KpiGroupModel findKpiGroupById(Integer kpiGroupModel);
		@SuppressWarnings("rawtypes")
		public  List searchKpiGroup(KpiGroupModel  persistentInstance);
		
		//KPI Group Type
		public ResultService saveKpiGroupType(KpiGroupTypeModel kpiGroupModelType);
		public ResultService updateKpiGroupType(KpiGroupTypeModel kpiGroupModelType);
		public Integer deleteKpiGroupType(KpiGroupTypeModel kpiGroupModelType);
		public KpiGroupTypeModel findKpiGroupTypeById(Integer kpiGroupModelType);
		@SuppressWarnings("rawtypes")
		public  List searchKpiGroupType(KpiGroupTypeModel  persistentInstance);
		
		//KPI Type
		public ResultService saveKpiType(KpiTypeModel kpiTypeModel);
		public ResultService updateKpiType(KpiTypeModel kpiTypeModel);
		public Integer deleteKpiType(KpiTypeModel kpiTypeModel);
		public KpiTypeModel findKpiTypeById(Integer KpiTypeId);
		@SuppressWarnings("rawtypes")
		public  List searchKpiType(KpiTypeModel  persistentInstance);
		
		//KPI Uom
		public ResultService saveKpiUom(KpiUomModel kpiUomModel);
		public ResultService updateKpiUom(KpiUomModel kpiUomModel);
		public Integer deleteKpiUom(KpiUomModel kpiUomModel);
		public KpiUomModel findKpiUomById(Integer KpiUomId);
		@SuppressWarnings("rawtypes")
		public  List searchKpiUom(KpiUomModel  persistentInstance);
		
		//KPI Structure
		public ResultService saveKpiStruc(KpiStrucModel kpiStrucModel);
		public ResultService updateKpiStruc(KpiStrucModel kpiStrucModel);
		public Integer deleteKpiStruc(KpiStrucModel kpiStrucModel);
		public KpiStrucModel findKpiStrucById(Integer KpiStrucId);
		@SuppressWarnings("rawtypes")
		public  List searchKpiStruc(KpiStrucModel  persistentInstance);
		
		//ORG
		public OrgModel findOrgById(OrgModel orgModel);
		public OrgModel searchOrg(OrgModel  persistentInstance);
		public List searchOrgByLevelId(OrgModel persistentInstance);
		public List searchOrgGroupByCourseCode(OrgModel persistentInstance);
		public List searchOrgByFacultyCode(OrgModel persistentInstance);
		public List searchOrgIdByOthersCode(OrgModel persistentInstance);
		public List getAllUniversity(OrgModel org);
		public List getOrgFacultyOfUniversity(OrgModel org);
		public List getOrgCourseOfFaculty(OrgModel org);
		public List getOrgIdByOrgDetailFilter(OrgModel org);
		
		//Org Type
		@SuppressWarnings("rawtypes")
		public  List searchOrgType(OrgTypeModel  persistentInstance);
		
		//Structure Type
		public List searchStrucType(StrucTypeModel persistentInstance);
	
	
	//cds
	public ResultService saveCds(CdsModel cdsModel);
	public ResultService updateCds(CdsModel cdsModel);
	public ResultService deleteCds(CdsModel cdsModel);
	public ResultService findCdsById(Integer cdsId);
	public ResultService searchCds(CdsModel  cdsModel);
	
	// database connection
	public Integer saveConn(DbConnModel connModel);
	public Integer updateConn(DbConnModel connModel);
	public Integer deleteConn(DbConnModel connModel);
	public DbConnModel findConnById(Integer connId);
	@SuppressWarnings("rawtypes")
	public  List searchConn(DbConnModel  connModel);
	// query
	public List resultPreviewQuery(DbQueryModel model);
	// kpi 
	public Integer saveKpi(KpiModel kpiModel);
	public Integer saveKpiFormula(KpiModel kpiModel);
	public Integer updateKpi(KpiModel kpiModel);
	public Integer deleteKpi(KpiModel kpiModel);
	public KpiModel findKpiById(Integer kpiId);
	public KpiModel findKpiWithDescById(Integer kpiId);
	@SuppressWarnings("rawtypes")
	public  List searchKpi(KpiModel  kpiModel);
	// criteria group
	public List searchCriteriaGroup(CriteriaGroupModel group);
	public List searchCriteriaGroupDetail(CriteriaGroupModel groupDetail);
	// criteria
	public Integer saveCriteriaStandard(CriteriaModel criteria);
	public Integer deleteCriteriaStandard(CriteriaModel criteria);
	public List searchCriteriaStandard(CriteriaModel std);
	// baseline 
	public List listBaseline(BaselineModel baseline);
	public Integer deleteBaseline(BaselineModel baseline);
	public Integer saveQuanBaseline(BaselineModel baseline);
	public Integer saveRangeBaseline(BaselineModel baseline);
	public Integer saveSpecBaseline(BaselineModel baseline);
	//KPI Structure
	public ResultService saveThreshold(ThresholdModel thresholdModel);
	public ResultService updateThreshold(ThresholdModel thresholdModel);
	public Integer deleteThreshold(ThresholdModel thresholdModel);
	public ThresholdModel findThresholdById(Integer ThresholdId);
	@SuppressWarnings("rawtypes")
	public  List searchThreshold(ThresholdModel  persistentInstance);
	
	//SYS YEAR
	public SysYearModel getSysYear();
	public Integer saveSysYear(SysYearModel thresholdModel);
	public Integer updateSysYear(SysYearModel thresholdModel);
	@SuppressWarnings("rawtypes")
	public  List searchSysYear(SysYearModel  persistentInstance);
	public Integer generateSysMonthBySysYear(SysYearModel model);
	
	//SYS MONTH
	public SysMonthModel findSysMonthById(Integer monthId);
	public Integer saveSysMonth(SysMonthModel thresholdModel);
	public Integer updateSysMonth(SysMonthModel thresholdModel);
	@SuppressWarnings("rawtypes")
	public  List searchSysMonth(SysMonthModel  persistentInstance);
	public List getMonthsByCalendar(SysMonthModel months);
	public List getYearsByAcad(SysMonthModel months);
	public List getMonthId(SysMonthModel perSysMonthModel);
	
	
	//KPI RECOMMEND
	public Integer saveKpiReComnd(KpiReComndModel kpiReComndModel);
	public Integer updateKpiReComnd(KpiReComndModel kpiReComndModel);
	public Integer deleteKpiReComnd(KpiReComndModel kpiReComndModel);
	public KpiReComndModel findKpiReComndById(Integer KpiReComndId);
	@SuppressWarnings("rawtypes")
	public  List searchKpiReComnd(KpiReComndModel  persistentInstance);
	
	//KPI RESULT
	public Integer saveKpiResult(KpiResultModel kpiResultModel);
	public Integer updateKpiResult(KpiResultModel kpiResultModel);
	public Integer deleteKpiResult(KpiResultModel kpiResultModel);
	public KpiResultModel findKpiResultById(Integer KpiResultId);
	@SuppressWarnings("rawtypes")
	public  List searchKpiResult(KpiResultModel  persistentInstance);
	public KpiResultModel findKpiResultByKpi(KpiResultModel model);
	public List searchKpiResultWithActiveKpi(KpiResultModel model);
	
	public ResultService saveResultOfOrg(KpiResultModel kpiResultM);
	public ResultService deleteResultByOrg(KpiResultModel kpiResultM);
	public KpiResultModel findKpiResultByIden(KpiResultModel model);
	
	//CDS RESULT
	public Integer saveCdsResult(CdsResultModel cdsResultModel);
	public CdsResultModel findCdsResultById(Integer cdsResultId);
	public List searchCdsByKpiId(CdsResultModel persistentInstance);
	public List searchCdsResult(CdsResultModel persistentInstance);
	public List getCdsWithKpi(CdsResultModel model);
	public CdsResultModel findCdsResultByCds(CdsResultModel model);
	
	//CDS RESULT DETAIL
	public Integer saveCdsResultDetail(CdsResultDetailModel cdsResultDetailModel); // saveResultQuantyity // can't use by ER
	public Integer updateCdsResultDetail(CdsResultDetailModel cdsResultDetailModel); // updateResultQuantity can't be exist by requirement
	public CdsResultDetailModel findCdsResultDetailById(Integer cdsResultDetailId); //  getResultDetail Quantity can't be exist by requirement
	public List searchCdsResultDetail(CdsResultDetailModel persistentInstance); // can't be exist by requirement
	public Integer saveResultQuantity(CdsResultModel cdsresult); // cds result have key(orgId,cdsId,monthId)
	public CdsResultDetailModel findCdsResultDetail(CdsResultDetailModel dsResultDetailModel);
	
	//CDS EVIDENCE
	public Integer deleteCdsEvidence(CdsEvidenceModel cdsEvidenceModel);
	public Integer saveCdsEvidence(CdsEvidenceModel cdsEvidenceModel);  
	public CdsEvidenceModel findCdsEvidenceById(Integer cdsEvidenceId);
	public List searchCdsEvidence(CdsEvidenceModel evidence);  // searchEvidenceQuantity() // retrieve  list evidenceId,filename
	
	
	//target 
	public List getKpiTarget(KpiTargetModel targetModel);
	public Integer saveKpiTarget(KpiTargetModel targetModel);
	
	// desctition (kpi)
	public DescriptionModel getOrgOfUser(DescriptionModel Model);
	public List getPeriods(DescriptionModel Model);
	public List getUoms(DescriptionModel Model);
	public List getCalendarTypes(DescriptionModel Model );
	public List getCriTypes(DescriptionModel Model);
	public List getCriMethods(DescriptionModel Model);
	public List getKpiNameAll(DescriptionModel Model);
	public List getUniversityAll(DescriptionModel Model);
	public List getFacultyAll(DescriptionModel Model);
	public List getCourseAll(DescriptionModel Model);
	public List getMonthAll(DescriptionModel Model);
	
	///???? add by p 20/10/2558
	public List<KpiResultDetailModel> findCriteriaResult(KpiResultDetailModel model);
	public KpiResultDetailModel findKpiResultDetailById(KpiResultDetailModel model);
	public KpiResultDetailModel findKpiResultDetailByIdentify(KpiResultDetailModel model);
	public Integer saveKpiResultDetail(KpiResultDetailModel model);
	public Integer updateKpiResultDetailEvidence(KpiResultDetailModel model);
	
	//evidence
	public Integer saveKpiEvidence(KpiEvidenceModel model);
	public Integer deleteKpiEvidence(KpiEvidenceModel model);
	public List<KpiEvidenceModel> searchKpiEvidence(KpiEvidenceModel model);

	public Integer deleteKpiXCds(KpiModel model);
	public Integer deleteBaselineSpecDetailByKpiId(BaselineModel kpiId);
	public Integer deleteBaselineQuanByKpiId(BaselineModel model);
	public Integer deleteCriteriaStandardByKpiI(CriteriaModel model);
	public Integer deleteKpiResultByKpiId(KpiResultModel model);
	public Integer deleteRangeBaselineByKpiId(KpiResultModel model);
}