package th.ac.chandra.eduqa.service.impl;

import java.util.List;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.Paging;
import th.ac.chandra.eduqa.domain.*;
import th.ac.chandra.eduqa.model.CdsResultDetailModel;
import th.ac.chandra.eduqa.model.CdsResultModel;
import th.ac.chandra.eduqa.model.DbConnModel;
import th.ac.chandra.eduqa.model.DbQueryModel;
import th.ac.chandra.eduqa.model.DescriptionModel;
import th.ac.chandra.eduqa.model.KpiEvidenceModel;
import th.ac.chandra.eduqa.model.KpiModel;
import th.ac.chandra.eduqa.model.KpiResultDetailModel;
import th.ac.chandra.eduqa.model.KpiResultModel;
import th.ac.chandra.eduqa.model.KpiTargetModel;
import th.ac.chandra.eduqa.model.SysMonthModel;
import th.ac.chandra.eduqa.model.SysYearModel;
import th.ac.chandra.eduqa.repository.EduqaRepository;
import th.ac.chandra.eduqa.xstream.common.Paging;

@Service("eduqaServiceJpaImpl")
public class EduqaServiceJpaImpl  implements EduqaService {	
	@Autowired
	@Qualifier("eduqaRepository")
	private EduqaRepository repository;

	@Override
	public Integer saveKpiLevel(KpiLevel transientInstance)
			throws DataAccessException {
		return repository.saveKpiLevel(transientInstance);
	}

	@Override
	public Integer updateKpiLevel(KpiLevel transientInstance)
			throws DataAccessException {
		return  repository.updateKpiLevel(transientInstance);
	}
	@Override
	public Integer deleteKpiLevel(KpiLevel persistentInstance)
			throws DataAccessException {
		return repository.deleteKpiLevel(persistentInstance);
	}
	@Override
	public KpiLevel findKpiLevelById(Integer KpiLevelId)
			throws DataAccessException {
		return repository.findKpiLevelById(KpiLevelId);
	}
	@Override
	public List<?> searchKpiLevel(KpiLevel persistentInstance, Paging pagging,String keySearch)
			throws DataAccessException {
		return  repository.searchKpiLevel(persistentInstance,pagging,keySearch);
	}
	

	//#####[ START: Kpi Group ]##############################################################################//
		@Override
		public Integer saveKpiGroup(KpiGroup transientInstance)
				throws DataAccessException {
			return repository.saveKpiGroup(transientInstance);
		}

		@Override
		public Integer updateKpiGroup(KpiGroup transientInstance)
				throws DataAccessException {
			return  repository.updateKpiGroup(transientInstance);
		}
		@Override
		public Integer deleteKpiGroup(KpiGroup persistentInstance)
				throws DataAccessException {
			return repository.deleteKpiGroup(persistentInstance);
		}

		@Override
		public KpiGroup findKpiGroupById(Integer KpiGroupId)
				throws DataAccessException {
			return repository.findKpiGroupById(KpiGroupId);
		}

		@Override
		public List searchKpiGroup(KpiGroup persistentInstance, Paging pagging,String keySearch)
				throws DataAccessException {
			return  repository.searchKpiGroup(persistentInstance,pagging,keySearch);
		}
	//#####[ END: Kpi Group ]###############################################################################//

		
		
	//#####[ START: KPI GROUP TYPE ]##############################################################################//
		@Override
		public Integer saveKpiGroupType(KpiGroupType transientInstance)
				throws DataAccessException {
			return repository.saveKpiGroupType(transientInstance);
		}

		@Override
		public Integer updateKpiGroupType(KpiGroupType transientInstance)
				throws DataAccessException {
			return repository.updateKpiGroupType(transientInstance);
		}

		@Override
		public Integer deleteKpiGroupType(KpiGroupType persistentInstance)
				throws DataAccessException {
			return repository.deleteKpiGroupType(persistentInstance);
		}

		@Override
		public KpiGroupType findKpiGroupTypeById(Integer KpiGroupTypeId)
				throws DataAccessException {
			return repository.findKpiGroupTypeById(KpiGroupTypeId);
		}

		@Override
		public List searchKpiGroupType(KpiGroupType persistentInstance, Paging pagging,
				String keySearch) throws DataAccessException {
			return repository
					.searchKpiGroupType(persistentInstance, pagging, keySearch);
		}
	//#####[ END: KPI GROUP TYPE ]###############################################################################//

		

	//#####[ START:KPI TYPE ]###############################################################################//
		@Override
		public Integer saveKpiType(KpiType transientInstance)
				throws DataAccessException {
			return repository.saveKpiType(transientInstance);
		}

		@Override
		public Integer updateKpiType(KpiType transientInstance)
				throws DataAccessException {
			return  repository.updateKpiType(transientInstance);
		}
		@Override
		public Integer deleteKpiType(KpiType persistentInstance)
				throws DataAccessException {
			return repository.deleteKpiType(persistentInstance);
		}

		@Override
		public KpiType findKpiTypeById(Integer KpiLevelId)
				throws DataAccessException {
			return repository.findKpiTypeById(KpiLevelId);
		}

		@Override
		public List searchKpiType(KpiType persistentInstance, Paging pagging,String keySearch)
				throws DataAccessException {
			return  repository.searchKpiType(persistentInstance,pagging,keySearch);
		}
	//#####[ END:KPI TYPE ]###############################################################################//	

		
		
	//#####[ END:KPI UOM ]###############################################################################//
		@Override
		public Integer saveKpiUom(KpiUom transientInstance)
				throws DataAccessException {
			return repository.saveKpiUom(transientInstance);
		}

		@Override
		public Integer updateKpiUom(KpiUom transientInstance)
				throws DataAccessException {
			return  repository.updateKpiUom(transientInstance);
		}
		@Override
		public Integer deleteKpiUom(KpiUom persistentInstance)
				throws DataAccessException {
			return repository.deleteKpiUom(persistentInstance);
		}

		@Override
		public KpiUom findKpiUomById(Integer KpiUomId)
				throws DataAccessException {
			return repository.findKpiUomById(KpiUomId);
		}

		@Override
		public List searchKpiUom(KpiUom persistentInstance, Paging pagging,String keySearch)
				throws DataAccessException {
			return  repository.searchKpiUom(persistentInstance,pagging,keySearch);
		}
	//#####[ END:KPI UOM ]###############################################################################//	

		
		
	//#####[ END:KPI UOM ]###############################################################################//
		@Override
		public Integer saveKpiStruc(KpiStruc transientInstance)
				throws DataAccessException {
			return repository.saveKpiStruc(transientInstance);
		}

		@Override
		public Integer updateKpiStruc(KpiStruc transientInstance)
				throws DataAccessException {
			return  repository.updateKpiStruc(transientInstance);
		}
		@Override
		public Integer deleteKpiStruc(KpiStruc persistentInstance)
				throws DataAccessException {
			return repository.deleteKpiStruc(persistentInstance);
		}

		@Override
		public KpiStruc findKpiStrucById(Integer KpiStrucId)
				throws DataAccessException {
			return repository.findKpiStrucById(KpiStrucId);
		}

		@Override
		public List searchKpiStruc(KpiStruc persistentInstance, Paging pagging,String keySearch)
				throws DataAccessException {
			return  repository.searchKpiStruc(persistentInstance,pagging,keySearch);
		}
	//#####[ END:KPI UOM ]###############################################################################//	

		//=====| START:ORG |===============================================================================//
		@Override
		public Org findOrgById(Integer orgId)
				throws DataAccessException {
			return repository.findOrgById(orgId);
		}

		@Override
		public Org searchOrg(Org persistentInstance, Paging pagging,
				String keySearch) throws DataAccessException {
			return repository.searchOrg(persistentInstance, pagging, keySearch);
		}
		
		@Override
		public List searchOrgByLevelId(Org persistentInstance, Paging pagging,
				String keySearch) throws DataAccessException {
			return repository.searchOrgByLevelId(persistentInstance, pagging, keySearch);
		}
		
		@Override
		public List searchOrgGroupByCourseCode(Org persistentInstance, Paging pagging,
				String keySearch) throws DataAccessException {
			return repository.searchOrgGroupByCourseCode(persistentInstance, pagging, keySearch);
		}
		
		@Override
		public List searchOrgByFacultyCode(Org persistentInstance, Paging pagging,
				String keySearch) throws DataAccessException {
			return repository.searchOrgByFacultyCode(persistentInstance, pagging, keySearch);
		}
		
		@Override
		public List searchOrgIdByOthersCode(Org persistentInstance, Paging pagging,
				String keySearch, String[] otherKeySearch) throws DataAccessException {
			return repository.searchOrgIdByOthersCode(persistentInstance, pagging, keySearch, otherKeySearch);
		}
		// addtional by pun
		@Override
		public List getAllOrgUniversity(Org org, Paging page) {
			return repository.getAllOrgUniversity(org,page);
		}

		@Override
		public List getOrgFacultyOfUniversity(Org org, Paging page) {
			return repository.getOrgFacultyOfUniversity(org,page);
		}

		@Override
		public List getOrgCourseOfFaculty(Org org, Paging page) {
			return repository.getOrgCourseOfFaculty(org,page);
		}
		
		@Override
		public List getOrgIdByOrgDetailFilter(Org org, Paging page) {
			return repository.getOrgIdByOrgDetailFilter(org,page);
		}
	//=====| END:ORG |=================================================================================//
		
	//#####[ START:ORG TYPE ]###############################################################################//
		@Override
		public OrgType findOrgTypeById(Integer orgTypeId)
				throws DataAccessException {
			return repository.findOrgTypeById(orgTypeId);
		}

		@Override
		public List searchOrgType(OrgType persistentInstance, Paging pagging,
				String keySearch) throws DataAccessException {
			return repository
					.searchOrgType(persistentInstance, pagging, keySearch);
		}
	//#####[ END:ORG TYPE ]#################################################################################//
		
		
		
	//#####[ START:STRUCTURE TYPE ]###############################################################################//
		@Override
		public StrucType findStrucTypeById(Integer strucTypeId)
				throws DataAccessException {
			return repository.findStrucTypeById(strucTypeId);
		}

		@Override
		public List searchStrucType(StrucType persistentInstance, Paging pagging,
				String keySearch) throws DataAccessException {
			return repository
					.searchStrucType(persistentInstance, pagging, keySearch);
		}
	//#####[ END:STRUCTURE TYPE ]#################################################################################//
	
	//########## kpi section
	@Override
	public Integer saveKpi(Kpi persistentInstance) throws DataAccessException {
		return repository.saveKpi(persistentInstance);
	}

	@Override
	public Kpi findKpiById(Integer KpiId)
			throws DataAccessException { 
		return repository.findKpiById(KpiId);
	}

	@Override
	public KpiModel findKpiWithDescById(KpiModel kpiM)
			throws DataAccessException { 
		return repository.findKpiWithDetailById(kpiM);
	}

	@Override
	public Integer deleteKpi(Kpi persistentInstance) throws DataAccessException {
		return repository.deleteKpi(persistentInstance);
	}

	@Override
	public Integer updateKpi(Kpi transientInstance) throws DataAccessException {
		return repository.updateKpi(transientInstance);
	}

	@Override
	public List<?> searchKpi(Kpi persistentInstance, Paging pagging,
			String keySearch) throws DataAccessException {
		return  repository.searchKpi(persistentInstance,pagging,keySearch);
	}
	@Override
	public List<?> getCdsMapWithKpi(CdsResultModel model) throws DataAccessException {
		return  repository.getCdsMapWithKpi(model);
	}
	@Override
	public Cds findCdsById(Integer cdsId) throws DataAccessException {
		return repository.findCdsById(cdsId);
	}

	@Override
	public Integer saveCds(Cds persistentInstance) throws DataAccessException {
		return repository.saveCds(persistentInstance);
	}

	@Override
	public Integer deleteCds(Cds persistentInstance) throws DataAccessException {
		return repository.deleteCds(persistentInstance);
	}

	@Override
	public Integer updateCds(Cds transientInstance) throws DataAccessException {
		return repository.updateCds(transientInstance);
	}

	@Override
	public List<?> searchCds(Cds persistentInstance, Paging pagging,
			String keySearch) throws DataAccessException {
		return  repository.searchCds(persistentInstance,pagging,keySearch);
	}

	@Override
	public DbConn findConnById(Integer connId) throws DataAccessException {
		return repository.findConnById(connId);
	}

	@Override
	public Integer saveConn(DbConn persistentInstance)
			throws DataAccessException {
		return repository.saveConn(persistentInstance);
	}

	@Override
	public Integer deleteConn(DbConn persistentInstance)
			throws DataAccessException {
		return repository.deleteConn(persistentInstance);
	}

	@Override
	public Integer updateConn(DbConn transientInstance)
			throws DataAccessException {
		return repository.updateConn(transientInstance);
	}

	@Override
	public List<?> searchConn(DbConn persistentInstance, Paging pagging,
			String keySearch) throws DataAccessException {
		return repository.searchConn(persistentInstance, pagging, keySearch);
	}

	//###########  criteria group & detail #########

	@Override
	public List searchCriteriaGroup(Paging paging,String keysearch)
			throws DataAccessException {
		return repository.searchCriteriaGroup(paging,keysearch);
	}
	@Override
	public List searchCriteriaGroupDetail(Integer groupId,Paging pagging) 
			throws DataAccessException {
		return repository.searchCriteriaGroupDetail(groupId,pagging);
	}
	@Override
	public Integer saveCriteriaStandard(CriteriaStandard std) 
			throws DataAccessException {
		return repository.saveCriteriaStandard(std);
	}
	@Override
	public Integer updateCriteriaStandard(CriteriaStandard std) 
			throws DataAccessException {
		return repository.updateCriteriaStandard(std);
	}
	@Override
	public Integer deleteCriteriaStandard(CriteriaStandard std) 
			throws DataAccessException {
		return repository.deleteCriteriaStandard(std);
	}
	@Override
	public List searchCriteriaStandard(Integer kpiId)
			throws DataAccessException {
		return repository.searchCriteriaStandard(kpiId);
	}
	@Override
	public Integer saveKpiFormula(Kpi persistentInstance)
			throws DataAccessException {
		return repository.saveKpiFormula(persistentInstance);
	}
	@Override
	public Integer saveQuanBaseline(BaselineQuan baseline)
			throws DataAccessException {
		return repository.saveQuanBaseline(baseline);
	}
	@Override
	public Integer updateQuanBaseline(BaselineQuan baseline)
			throws DataAccessException {
		return repository.updateQuanBaseline(baseline);
	}
	@Override
	public List listBaselineQuan(BaselineQuan quan)
			throws DataAccessException {
		return repository.listQuanBaseline(quan);
	}

	@Override
	public List listBaselineRange(BaselineRange range)
			throws DataAccessException {
		return repository.listRangeBaseline(range);
	}
	@Override
	public List listBaselineSpec(BaselineSpec spec)
			throws DataAccessException {
		return repository.listSpecBaseline(spec);
	}
	@Override
	public List listBaselineSpecGroup(BaselineSpec spec)
			throws DataAccessException {
		return repository.listSpecBaselineGroup(spec);
	}
	@Override
	public List listBaselineSpecDetail(BaselineSpec spec)
			throws DataAccessException {
		return repository.listSpecBaselineDetail(spec);
	}
	@Override
	public Integer saveRangeBaseline(BaselineRange baseline)
			throws DataAccessException {
		return repository.saveRangeBaseline(baseline);
	}
	@Override
	public Integer updateRangeBaseline(BaselineRange baseline)
			throws DataAccessException {
		return repository.updateRangeBaseline(baseline);
	}
	@Override
	public Integer saveSpecBaseline(BaselineSpec baseline)
			throws DataAccessException {
		return repository.saveSpecBaseline(baseline);
	}
	@Override
	public Integer updateSpecBaseline(BaselineSpec baseline)
			throws DataAccessException {
		return repository.updateSpecBaseline(baseline);
	}

	@Override
	public Integer deleteRangeBaseline(BaselineRange baseline)
			throws DataAccessException {
		return repository.deleteRangeBaseline(baseline);
	}

	@Override
	public Integer deleteQuanBaseline(BaselineQuan baseline)
			throws DataAccessException {
		return repository.deleteQuanBaseline(baseline);
	}

	@Override
	public Integer deleteSpecBaseline(BaselineSpec baseline)
			throws DataAccessException {
		return repository.deleteSpecBaseline(baseline);
	}
	// threshold 
	@Override
	public Integer saveThreshold(Threshold transientInstance) throws DataAccessException {
		return repository.saveThreshold(transientInstance);
	}

	@Override
	public Integer updateThreshold(Threshold transientInstance) throws DataAccessException {
		return  repository.updateThreshold(transientInstance);
	}
	@Override
	public Integer deleteThreshold(Threshold persistentInstance)
			throws DataAccessException {
		return repository.deleteThreshold(persistentInstance);
	}

	@Override
	public Threshold findThresholdById(Integer ThresholdId)
			throws DataAccessException {
		return repository.findThresholdById(ThresholdId);
	}

	@Override
	public List searchThreshold(Threshold persistentInstance, Paging pagging,String keySearch)
			throws DataAccessException {
		return  repository.searchThreshold(persistentInstance,pagging,keySearch);
	}
//=====| END: THRESHOLD |=================================================================================//
	
	

//=====| STAR: SYS_YEAR |=================================================================================//

	@Override
	public SysYear getSysYear()  throws DataAccessException{
		return repository.getSysYear();
	}
	@Override
	public Integer saveSysYear(SysYear transientInstance) throws DataAccessException {
		return repository.saveSysYear(transientInstance);
	}

	@Override
	public Integer updateSysYear(SysYear transientInstance) throws DataAccessException {
		return  repository.updateSysYear(transientInstance);
	}
	@Override
	public List searchSysYear(SysYear persistentInstance, Paging pagging,String keySearch)
			throws DataAccessException {
		return  repository.searchSysYear(persistentInstance,pagging,keySearch);
	}
//=====| END: SYS_YEAR |=================================================================================//
	
	
	
//=====| STAR: SYS_MONTH|=================================================================================//
	
	@Override
	public SysMonthModel findSysMonthById(Integer monthId)
			throws DataAccessException {
		return  repository.findSysMonthById(monthId);
	}
	@Override
	public Integer saveSysMonth(SysMonth transientInstance) throws DataAccessException {
		return repository.saveSysMonth(transientInstance);
	}
	@Override
	public Integer updateSysMonth(SysMonth transientInstance) throws DataAccessException {
		return  repository.updateSysMonth(transientInstance);
	}
	@Override
	public List searchSysMonth(SysMonth persistentInstance, Paging pagging,String keySearch)
			throws DataAccessException {
		return  repository.searchSysMonth(persistentInstance,pagging,keySearch);
	}
	@Override
	public List getMonthsByCalendar(SysMonth months,Paging pagging,String otherSearch) throws DataAccessException{
		return  repository.getMonthsByCalendar(months,pagging,otherSearch);
	}
	@Override
	public List getCalendarYearsByAcad(SysMonth months,Paging pagging,String otherSearch) throws DataAccessException{
		return repository.getCalendarYearsByAcad(months,pagging,otherSearch);
	}
	@Override
	public List getMonthId(SysMonth persistentInstance,Paging pagging,
			String keySearch, String[] otherKeySearch)
			throws DataAccessException {
		return  repository.getMonthId(persistentInstance,pagging,keySearch,otherKeySearch);
	}
//=====| END: SYS_MONTH |=================================================================================//

	
	
//=====| END:KPI RECOMND |===============================================================================//
	@Override
	public Integer saveKpiReComnd(KpiReComnd transientInstance)
			throws DataAccessException {
		return repository.saveKpiReComnd(transientInstance);
	}

	@Override
	public Integer updateKpiReComnd(KpiReComnd transientInstance)
			throws DataAccessException {
		return repository.updateKpiReComnd(transientInstance);
	}

	@Override
	public Integer deleteKpiReComnd(KpiReComnd persistentInstance)
			throws DataAccessException {
		return repository.deleteKpiReComnd(persistentInstance);
	}

	@Override
	public KpiReComnd findKpiReComndById(Integer KpiReComndId)
			throws DataAccessException {
		return repository.findKpiReComndById(KpiReComndId);
	}

	@Override
	public List searchKpiReComnd(KpiReComnd persistentInstance, Paging pagging,
			String keySearch, String[] otherKeySearch) throws DataAccessException {
		return repository.searchKpiReComnd(persistentInstance, pagging,
				keySearch, otherKeySearch);
	}
//=====| END:KPI RECOMND |===============================================================================//	
	
	//=====| START:KPI RESULT |===============================================================================//
		@Override
		public Integer saveKpiResult(KpiResult transientInstance)
				throws DataAccessException {
			return repository.saveKpiResult(transientInstance);
		}

		@Override
		public Integer updateKpiResult(KpiResult transientInstance)
				throws DataAccessException {
			return repository.updateKpiResult(transientInstance);
		}

		@Override
		public Integer deleteKpiResult(KpiResult persistentInstance)
				throws DataAccessException {
			return repository.deleteKpiResult(persistentInstance);
		}

		@Override
		public KpiResult findKpiResultById(Integer KpiResultId)
				throws DataAccessException {
			return repository.findKpiResultById(KpiResultId);
		}

		@Override
		public List searchKpiResult(KpiResult persistentInstance, Paging pagging,
				String keySearch, String[] otherKeySearch) throws DataAccessException {
			return repository.searchKpiResult(persistentInstance, pagging,
			keySearch, otherKeySearch);
		}
		@Override
		public KpiResult findKpiResultByKpi(KpiResult domain)throws DataAccessException {
			return repository.findKpiResultByKpi(domain);
		}
		// add 20/10/2015
		@Override
		public Integer insertsKpiResult(KpiResultModel model) throws DataAccessException{
			return repository.insertsKpiResult(model);
		}
		@Override
		public Integer deleteKpiResultByOrgId(KpiResultModel model) throws DataAccessException{
			return repository.deleteKpiResultByOrgId(model);
		}
		@Override
		public KpiResultModel findKpiResultByIdentify(KpiResultModel model) throws DataAccessException{
			return repository.findKpiResultByIdentify(model);
		}

		@Override
		public List searchKpiResultWithActiveKpi(KpiResultModel model)
				throws DataAccessException {
			return repository.searchKpiResultWithActiveKpi(model);
		}		
		//=====| END:KPI RESULT |===============================================================================//

			//=====| START:CDS RESULT |===============================================================================//
			@Override
			public Integer saveCdsResult(CdsResult transientInstance)
					throws DataAccessException{
				return repository.saveCdsResult(transientInstance);
			}
			
			@Override
			public CdsResult findCdsResultById(Integer csdId)
					throws DataAccessException {
				return repository.findCdsResultById(csdId);
			}
			
			@Override
			public List searchCdsResult(CdsResult persistentInstance, Paging pagging,
					String keySearch, String[] otherKeySearch) throws DataAccessException {
				return repository.searchCdsResult(persistentInstance, pagging, keySearch, otherKeySearch);
			}
			
			@Override
			public List searchCdsUsedByKpiId(CdsResult persistentInstance, Paging pagging,
					String keySearch, String[] otherKeySearch) throws DataAccessException {
				return repository.searchCdsUsedByKpiId(persistentInstance, pagging,
				keySearch, otherKeySearch);
			}
		//=====| END:CDS RESULT |===============================================================================//
			
			
			
		//=====| START:CDS RESULT DETAIL |===============================================================================//
			@Override
			public Integer saveCdsResultDetail(CdsResultDetail transientInstance)
					throws DataAccessException{
				return repository.saveCdsResultDetail(transientInstance);
			}
			
			@Override
			public Integer updateCdsResultDetail(CdsResultDetail transientInstance)
					throws DataAccessException{
				return repository.updateCdsResultDetail(transientInstance);
			}
			
			@Override
			public CdsResultDetail findCdsResultDetailById(Integer csdResultDetailId) throws DataAccessException {
				return repository.findCdsResultDetailById(csdResultDetailId);
			}

			@Override
			public CdsResultDetail findCdsResultDetail(CdsResultDetailModel cdsResultDetailModel) throws DataAccessException {
				return repository.findCdsResultDetail(cdsResultDetailModel);
			}

			@Override
			public List searchCdsResultDetail(CdsResultDetail persistentInstance, Paging pagging,
					String keySearch, String[] otherKeySearch)
					throws DataAccessException {
				return repository.searchCdsResultDetail(persistentInstance, pagging,
						keySearch, otherKeySearch);
			}
		//=====| END:CDS RESULT DETAIL |===============================================================================//
			
			
			
			
		//=====| START:CDS EVIDENCE |===============================================================================//
			@Override
			public Integer saveCdsEvidence(CdsEvidence transientInstance)
					throws DataAccessException{
				return repository.saveCdsEvidence(transientInstance);
			}
			
			@Override
			public Integer deleteCdsEvidence(CdsEvidence transientInstance) throws DataAccessException{
				return repository.deleteCdsEvidence(transientInstance);
			}
			
			@Override
			public CdsEvidence findCdsEvidenceById(Integer cdsEvidence)
					throws DataAccessException {
				return repository.findCdsEvidenceById(cdsEvidence);
			}

			@Override
			public List searchCdsEvidence(CdsEvidence persistentInstance,Paging pagging)
					throws DataAccessException {
				return repository.searchCdsEvidence(persistentInstance, pagging);
			}
		//=====| END:CDS EVIDENCE |===============================================================================//	
	
			// ???????????
			@Override
			public CdsResult findCdsResultByCds(CdsResult cds){
				return repository.findCdsResultByCds(cds);
			}
			@Override
			public List<KpiResultDetailModel> findCriteriaResult(KpiResultDetailModel model)
					throws DataAccessException {
				return repository.findCriteriaResult(model);
			}
			@Override
			public KpiResultDetail findKpiResultDetailById(
					KpiResultDetailModel model) throws DataAccessException {
				return repository.findKpiResultDetailById(model);
			}
			@Override
			public KpiResultDetailModel findKpiResultDetailByIdentify(
					KpiResultDetailModel model) throws DataAccessException {
				return repository.findKpiResultDetailByIdentify(model);
			}
			
			// ############################### Kpi Evidence
			@Override
			public List<KpiEvidenceModel> searchKpiEvidence(KpiEvidenceModel model) throws DataAccessException{
				return repository.searchKpiEvidence(model);
			}
			@Override
			public Integer saveKpiEvidence(KpiEvidenceModel model)
					throws DataAccessException {
				return repository.saveKpiEvidence(model);
			}
			@Override
			public Integer deleteKpiEvidence(KpiEvidenceModel model)
					throws DataAccessException {
				return repository.deleteKpiEvidence(model);
			}
			@Override
			public Integer saveKpiResultDetail(KpiResultDetailModel model)
					throws DataAccessException {
				return repository.saveKpiResultDetail(model);
			}

			@Override
			public Integer updateKpiResultDetailEvidence(KpiResultDetailModel model)
					throws DataAccessException {
				return repository.updateKpiResultDetailEvidence(model);
			}

			@Override
			public Integer deleteKpiXCds(KpiXCds model)
				throws DataAccessException {
				return repository.deleteKpiXCds(model);
			}
			
			@Override
			public Integer deleteBaselineSpecDetailByKpiId(Integer kpiId) 
				throws DataAccessException{
				return repository.deleteBaselineSpecDetailByKpiId(kpiId);
			}
			
			@Override
			public Integer deleteBaselineQuanByKpiId(BaselineQuan model)
				throws DataAccessException{
				return repository.deleteBaselineQuanByKpiId(model);
			}
			
			@Override
			public Integer deleteCriteriaStandardByKpiI(CriteriaStandard model)
					throws DataAccessException{
				return repository.deleteCriteriaStandardByKpiId(model);
			}
			
			@Override
			public Integer deleteKpiResultByKpiId(KpiResultModel model) 
					throws DataAccessException{
				return repository.deleteKpiResultByKpiId(model);
			}
			
			@Override
			public Integer deleteRangeBaselineByKpiId(KpiResultModel model) 
					throws DataAccessException{
				return repository.deleteRangeBaselineByKpiId(model);
			}
			
	//##### target
	public KpiTargetModel getKpiTarget(KpiTargetModel model){
		return repository.getKpiTarget(model);
	}
	public Integer saveKpiTarget(KpiResult result,KpiTargetModel target){
		return repository.saveKpiTarget(result,target);
	}
	// ####### dbconnection
	@Override
	public boolean checkConnection(DbConnModel conn) {
		return repository.checkConnection(conn);
	}
	@Override
	public List previewMysqlQueryResult(DbQueryModel q) {
		return repository.previewMysqlQueryResult(q);
	}
	public List previewOracleQueryResult(DbQueryModel q) { 
		return repository.previewOracleQueryResult(q);
	}
	// ========================= START description ===================
	@Override
	public KpiResult resultDescByKpiId(KpiResult result)  {
		return repository.resultDescByKpiId(result);
	}
	@Override
	public List<DescriptionModel> getOrgOfUser(DescriptionModel model){
		return repository.getOrgOfUser(model);
	}
	@Override
	public List getPeriodAll(DescriptionModel model){
		return repository.getPeriodAll(model);
	}
	@Override
	public List getUomAll(DescriptionModel model){
		return repository.getUomAll(model);
	}
	@Override
	public List getCalendarAll(DescriptionModel model){
		return repository.getCalendarAll(model);
	}
	@Override
	public List getCriteriaTypeAll(DescriptionModel model){
		return repository.getCriteriaTypeAll(model);
	}
	@Override
	public List getCriteriaMethodAll(DescriptionModel model){
		return repository.getCriteriaMethodAll(model);
	}
	@Override
	public List getKpiNameAll(){
		return repository.getKpiNameAll();
	}

	@Override
	public List getUniversityAll() {
		return repository.getUniversityAll();
	}

	@Override
	public List getFacultyAll() {
		return repository.getFacultyAll();
	}

	@Override
	public List getCourseAll() {
		return repository.getCourseAll();
	}
	@Override
	public List getMonthAll(Integer yearNo) {
		return repository.getMonthAll(yearNo);
	}
	@Override
	public Integer generateSysMonth(SysYearModel model){
		Integer success = 0;
		//
		 try {
			 // for etl 5.4 required pentaho lib (core,engine,vfs,metaStore,js,mail,common-lang2.4)
			 	KettleEnvironment.init();
			    KettleDatabaseRepository repository = new KettleDatabaseRepository();
			    DatabaseMeta databaseMeta = new DatabaseMeta(
			    	      "MySql", "MySql", "", "localhost", "eduqa_etl_repo", "3306", "root", "password" );
			    KettleDatabaseRepositoryMeta kettleDatabaseMeta = new KettleDatabaseRepositoryMeta( );
			    kettleDatabaseMeta.setConnection(databaseMeta);
			    repository.init( kettleDatabaseMeta );
			    repository.connect( "admin", "admin" );
			    
			    RepositoryDirectoryInterface directory = repository.loadRepositoryDirectoryTree();
			    
			    JobMeta jobMeta = repository.loadJob("SysMonth", directory.findDirectory("/Master"), null, null);
			    
			    Job job = new Job(repository,jobMeta);
			    job.run();
			    job.waitUntilFinished(15000); // max wait 15 sec  becareful  high value  very slow application response time
			    if ( job.getErrors() > 0 )
			    {
			    	System.out.println("etlerror:");
			    }else{
			    	success = 1;
			    }
			  }
			  catch ( KettleException e ) {
				  System.out.println("etlerror:"+e);
			  }
		//
		return success;
	}

}