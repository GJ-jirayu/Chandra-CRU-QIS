package th.ac.chandra.eduqa.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import th.ac.chandra.eduqa.domain.BaselineQuan;
import th.ac.chandra.eduqa.domain.BaselineRange;
import th.ac.chandra.eduqa.domain.BaselineSpec;
import th.ac.chandra.eduqa.domain.Cds;
import th.ac.chandra.eduqa.domain.CdsEvidence;
import th.ac.chandra.eduqa.domain.CdsResult;
import th.ac.chandra.eduqa.domain.CdsResultDetail;
import th.ac.chandra.eduqa.domain.CriteriaGroup;
import th.ac.chandra.eduqa.domain.CriteriaGroupDetail;
import th.ac.chandra.eduqa.domain.CriteriaStandard;
import th.ac.chandra.eduqa.domain.DbConn;
import th.ac.chandra.eduqa.domain.Kpi;
import th.ac.chandra.eduqa.domain.KpiEvidence;
import th.ac.chandra.eduqa.domain.KpiGroup;
import th.ac.chandra.eduqa.domain.KpiGroupType;
import th.ac.chandra.eduqa.domain.KpiLevel;
import th.ac.chandra.eduqa.domain.KpiReComnd;
import th.ac.chandra.eduqa.domain.KpiResult;
import th.ac.chandra.eduqa.domain.KpiResultDetail;
import th.ac.chandra.eduqa.domain.KpiStruc;
import th.ac.chandra.eduqa.domain.KpiType;
import th.ac.chandra.eduqa.domain.KpiUom;
import th.ac.chandra.eduqa.domain.KpiXCds;
import th.ac.chandra.eduqa.domain.Org;
import th.ac.chandra.eduqa.domain.OrgType;
import th.ac.chandra.eduqa.domain.StrucType;
import th.ac.chandra.eduqa.domain.SysMonth;
import th.ac.chandra.eduqa.domain.SysYear;
import th.ac.chandra.eduqa.domain.Threshold;
import th.ac.chandra.eduqa.model.BaselineModel;
import th.ac.chandra.eduqa.model.CdsModel;
import th.ac.chandra.eduqa.model.CdsResultDetailModel;
import th.ac.chandra.eduqa.model.CdsResultModel;
import th.ac.chandra.eduqa.model.CriteriaModel;
import th.ac.chandra.eduqa.model.DbConnModel;
import th.ac.chandra.eduqa.model.DbQueryModel;
import th.ac.chandra.eduqa.model.DescriptionModel;
import th.ac.chandra.eduqa.model.KpiEvidenceModel;
import th.ac.chandra.eduqa.model.KpiGroupModel;
import th.ac.chandra.eduqa.model.KpiModel;
import th.ac.chandra.eduqa.model.KpiResultDetailModel;
import th.ac.chandra.eduqa.model.KpiResultModel;
import th.ac.chandra.eduqa.model.KpiStrucModel;
import th.ac.chandra.eduqa.model.KpiTargetModel;
import th.ac.chandra.eduqa.model.SysMonthModel;
import th.ac.chandra.eduqa.xstream.common.Paging;


@Repository("eduqaRepository")
@Transactional
public class EduqaRepository   {
	//private static final String SELECT_QUERY = "select p from JournalPaper p";

	@Autowired
	@PersistenceContext(unitName="HibernatePersistenceUnit") 
	private EntityManager entityManager;
	
	private Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	
	@Autowired
	@PersistenceContext(unitName="HibernatePersistenceLiferayUnit") 
	private EntityManager portalEntityManager;
	public Integer saveKpiLevel(KpiLevel transientInstance)
			throws DataAccessException {
		//entityManager.persist(transientInstance);
		//return transientInstance.getLevelId();
		String qryStr = "SELECT k FROM KpiLevel k WHERE KPI_LEVEL_NAME ='"+transientInstance.getDesc()+"'";
		List query = entityManager.createQuery(qryStr).getResultList();
		if(query.isEmpty()){ //0=Error, 1=OK
			entityManager.persist(transientInstance);
			//return transientInstance.getLevelId();
			return 1;
		}else{
			return 0;
		}
	}

	public Integer updateKpiLevel(KpiLevel transientInstance)
			throws DataAccessException {
		Integer status = 0;
		String qryStr = "SELECT k FROM KpiLevel k WHERE KPI_LEVEL_NAME ='"+transientInstance.getDesc()+"' "
				+ " and KPI_LEVEL_ID!="+transientInstance.getLevelId();
		List query = entityManager.createQuery(qryStr).getResultList();
		if(query.isEmpty()){
			entityManager.merge(transientInstance);
			status = transientInstance.getLevelId();
		}else{
			status = 0;
		}
		return status;
	}

	public Integer deleteKpiLevel(KpiLevel persistentInstance)
			throws DataAccessException {
		int deletedCount = entityManager.createQuery(
				"delete from KpiLevel where KPI_LEVEL_ID="
						+ persistentInstance.getLevelId()).executeUpdate();
		return deletedCount;
	}
	public KpiLevel findKpiLevelById(Integer kpiLevelId)
			throws DataAccessException {
		return entityManager.find(KpiLevel.class, kpiLevelId);
	}
	
	@SuppressWarnings("rawtypes")
	public List searchKpiLevel(KpiLevel persistentInstance,
			Paging pagging, String keySearch) throws DataAccessException {
		StringBuffer sb = new StringBuffer("");
		sb.append(" where p.levelNo>0 ");
		if (keySearch != null && keySearch.trim().length() > 0) {
			sb.append(" and  p.desc like '%" + keySearch.trim()
					+ "%' ");
		}
		ArrayList transList = new ArrayList();
		//entityManager.createNativeQuery(arg0, arg1);
		Query query = entityManager.createQuery(	
				" select p from KpiLevel p " + sb.toString() 
				,	KpiLevel.class);
		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
		query.setMaxResults(pagging.getPageSize());	
		transList.add(query.getResultList());
		query = entityManager
				.createQuery("select count(p) from KpiLevel p "
						+ sb.toString());
		long count = (Long) query.getSingleResult();
		transList.add(String.valueOf(count));
		return transList;
	}

	//#####[ START: Kpi Group ]###########################################################################//
		public Integer saveKpiGroup(KpiGroup transientInstance)
				throws DataAccessException {
			//entityManager.persist(transientInstance);
			//return transientInstance.getGroupId()		
			String qryStr = "SELECT k FROM KpiGroup k WHERE KPI_GROUP_NAME ='"+transientInstance.getGroupName()+"'";
			List query = entityManager.createQuery(qryStr).getResultList();
			if(query.isEmpty()){ //0=Error, 1=OK
				entityManager.persist(transientInstance);
				//return transientInstance.getGroupId();
				return 1;
			}else{
				return 0;
			}
		}

		public Integer updateKpiGroup(KpiGroup transientInstance)
				throws DataAccessException {
			String qryStr = "SELECT k FROM KpiGroup k WHERE KPI_GROUP_NAME ='"+transientInstance.getGroupName()+"'";
			List query = entityManager.createQuery(qryStr).getResultList();
			if(query.isEmpty()){ //0=Error, 1=OK
				entityManager.merge(transientInstance);
				//return transientInstance.getGroupId();
				return 1;
			}else{
				return 0;
			}
		}

		public Integer deleteKpiGroup(KpiGroup persistentInstance)
				throws DataAccessException {
			int deletedCount = entityManager.createQuery(
					"DELETE FROM KpiGroup WHERE KPI_GROUP_ID="
							+ persistentInstance.getGroupId()).executeUpdate();
			return deletedCount;		
		}
		public KpiGroup findKpiGroupById(Integer kpiGroupId)
				throws DataAccessException {
			return entityManager.find(KpiGroup.class, kpiGroupId);
		}
		public List searchKpiGroup(KpiGroup persistentInstance,
				Paging pagging, String keySearch) throws DataAccessException {
			StringBuffer sbGrp = new StringBuffer("");
			if (keySearch != null && keySearch.trim().length() > 0) {
				sbGrp.append(" and g.kpi_group_name like '%" + keySearch.trim() + "%' ");
			}		
			Query query = entityManager.createNativeQuery(
					" SELECT g.kpi_group_id,g.academic_year "+
					" ,g.kpi_group_name,g.kpi_group_short_name "+
					" ,o.org_type_id,o.org_type_name "+
					" ,t.kpi_group_type_id, t.kpi_group_type_name "+
					" ,g.created_by,g.created_dttm "+
					" FROM kpi_group g, org_type o, kpi_group_type t "+
					" where o.org_type_id = g.org_type_id "+
					" and t.kpi_group_type_id = g.kpi_group_type_id "+ 
					sbGrp.toString()+
					" order by g.kpi_group_id");
			query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
			query.setMaxResults(pagging.getPageSize());	
			
			List<Object[]> results = query.getResultList();
			List<KpiGroupModel> groups = new ArrayList<KpiGroupModel>();
			for(Object[] result: results){
				KpiGroupModel group = new KpiGroupModel();
				group.setGroupId((Integer) result[0]);
				group.setAcademicYear((Integer) result[1]);
				group.setGroupName((String) result[2]);
				group.setGroupShortName((String) result[3]);
				group.setOrgTypeId((Integer) result[4]);
				group.setOrgTypeName((String) result[5]);
				group.setGroupTypeId((Integer) result[6]);
				group.setGroupTypeName((String) result[7]);
				group.setCreatedBy((String) result[8]);
				group.setCreatedDate((Timestamp) result[9]);
				groups.add(group);
			}
			ArrayList<Object> transList = new ArrayList<Object>();
			transList.add(groups);
			
			query = entityManager.createNativeQuery(
					"SELECT count(*) "+
					"FROM kpi_group g, org_type o, kpi_group_type t "+
					"where o.org_type_id = g.org_type_id "+
					"and t.kpi_group_type_id = g.kpi_group_type_id "+ 
					sbGrp.toString());
			BigInteger count = (BigInteger) query.getSingleResult();
			transList.add(String.valueOf(count));
			return transList;
		}
	//#####[ END: Kpi Group ]###########################################################################//
		
		
		
	//#####[ START: KPI GROUP TYPE ]###########################################################################//
		public Integer saveKpiGroupType(KpiGroupType transientInstance)
				throws DataAccessException {
			String qryStr = "SELECT k FROM KpiGroupType k WHERE KPI_GROUP_TYPE_NAME ='"+transientInstance.getGroupTypeName()+"'";
			List query = entityManager.createQuery(qryStr).getResultList();
			if(query.isEmpty()){ //0=Error, 1=OK
				entityManager.persist(transientInstance);
				//return transientInstance.getGroupTypeId();
				return 1;
			}else{
				return 0;
			}
		}

		public Integer updateKpiGroupType(KpiGroupType transientInstance)
				throws DataAccessException {
			String qryStr = "SELECT k FROM KpiGroupType k WHERE KPI_GROUP_TYPE_NAME ='"+transientInstance.getGroupTypeName()+"'";
			List query = entityManager.createQuery(qryStr).getResultList();
			if(query.isEmpty()){ //0=Error, 1=OK
				entityManager.merge(transientInstance);
				return 1;
			}else{
				return 0;
			}
		}

		public Integer deleteKpiGroupType(KpiGroupType persistentInstance)
				throws DataAccessException {
			int deletedCount = entityManager.createQuery(
					"DELETE FROM KpiGroupType WHERE KPI_GROUP_TYPE_ID="
							+ persistentInstance.getGroupTypeId()).executeUpdate();
			return deletedCount;		
		}
		public KpiGroupType findKpiGroupTypeById(Integer kpiGroupTypeId)
				throws DataAccessException {
			return entityManager.find(KpiGroupType.class, kpiGroupTypeId);
		}
		public List searchKpiGroupType(KpiGroupType persistentInstance,
				Paging pagging, String keySearch) throws DataAccessException {
			StringBuffer sb = new StringBuffer("");
			if (keySearch != null && keySearch.trim().length() > 0) {
				sb.append(" where  p.groupTypeName like '%" + keySearch.trim()
						+ "%' ");
			}
			ArrayList transList = new ArrayList();
			Query query = entityManager.createQuery(	
					" select p from KpiGroupType p " + sb.toString() 
					,	KpiGroupType.class);
			query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
			query.setMaxResults(pagging.getPageSize());	
			transList.add(query.getResultList());

			query = entityManager
					.createQuery("select count(p) from KpiGroupType p "
							+ sb.toString());
			long count = (Long) query.getSingleResult();
			transList.add(String.valueOf(count));
			return transList;
		}
	//#####[ END: KPI GROUP TYPE ]###########################################################################//
		
		
		
	//#####[ START: KPI TYPE ]##############################################################################//
		public Integer saveKpiType(KpiType transientInstance)
				throws DataAccessException {
			String qryStr = "SELECT k FROM KpiType k "
					+ "WHERE (k.typeName ='"+transientInstance.getTypeName()+"' "
					+ "or k.typeShortName = '"+transientInstance.getTypeShortName()+"')";
			List query = entityManager.createQuery(qryStr).getResultList();
			if(query.isEmpty()){ //0=Error, 1=OK
				entityManager.persist(transientInstance);
				//return transientInstance.getTypeId();
				return 1;
			}else{
				return 0;
			}
		}

		public Integer updateKpiType(KpiType transientInstance)
				throws DataAccessException {
			String qryStr = "SELECT k FROM KpiType k "
					+ "WHERE k.typeName ='"+transientInstance.getTypeName()+"'";
			List query = entityManager.createQuery(qryStr).getResultList();
			if(query.isEmpty()){ //0=Error, 1=OK
				entityManager.merge(transientInstance);
				return 1;
			}else{
				return 0;
			}
			//entityManager.merge(transientInstance);
			//return 1;
		}

		public Integer deleteKpiType(KpiType persistentInstance)
				throws DataAccessException {
			int deletedCount = entityManager.createQuery(
					"DELETE FROM KpiType WHERE KPI_TYPE_ID="
							+ persistentInstance.getTypeId()).executeUpdate();
			return deletedCount;		
		}
		
		public KpiType findKpiTypeById(Integer kpiTypeId)
				throws DataAccessException {
			return entityManager.find(KpiType.class, kpiTypeId);
		}
		
		public List searchKpiType(KpiType persistentInstance,
				Paging pagging, String keySearch) throws DataAccessException {
			StringBuffer sb = new StringBuffer("");
			if (keySearch != null && keySearch.trim().length() > 0) {
				sb.append(" where  p.typeName like '%" + keySearch.trim()
						+ "%' ");
			}
			ArrayList transList = new ArrayList();
			Query query = entityManager.createQuery(	
					" select p from KpiType p " + sb.toString() 
					,	KpiType.class);
			query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
			query.setMaxResults(pagging.getPageSize());	
			transList.add(query.getResultList());

			query = entityManager
					.createQuery("select count(p) from KpiType p "
							+ sb.toString());
			long count = (Long) query.getSingleResult();
			transList.add(String.valueOf(count));
			return transList;
		}
	//#####[ END: KPI TYPE ]################################################################################//
		
		
		
	//#####[ START: KPI UOM ]##############################################################################//
	public Integer saveKpiUom(KpiUom transientInstance)
			throws DataAccessException {
		String qryStr = "SELECT k FROM KpiUom k WHERE KPI_UOM_NAME ='"
				+ transientInstance.getUomName() + "'";
		List query = entityManager.createQuery(qryStr).getResultList();
		if (query.isEmpty()) { // 0=Error, 1=OK
			entityManager.persist(transientInstance);
			// return transientInstance.getUomId();
			return 1;
		} else {
			return 0;
		}
	}

	public Integer updateKpiUom(KpiUom transientInstance)
			throws DataAccessException {
		String qryStr = "SELECT k FROM KpiUom k WHERE KPI_UOM_NAME ='"
				+ transientInstance.getUomName() + "'";
		List query = entityManager.createQuery(qryStr).getResultList();
		if (query.isEmpty()) { // 0=Error, 1=OK
			entityManager.merge(transientInstance);
			return 1;
		} else {
			return 0;
		}
		// entityManager.merge(transientInstance);
		// return 1;
	}

	public Integer deleteKpiUom(KpiUom persistentInstance)
			throws DataAccessException {
		int deletedCount = entityManager.createQuery(
				"DELETE FROM KpiUom WHERE KPI_UOM_ID="
						+ persistentInstance.getUomId()).executeUpdate();
		return deletedCount;
	}

	public KpiUom findKpiUomById(Integer kpiUomId) throws DataAccessException {
		return entityManager.find(KpiUom.class, kpiUomId);
	}

	public List searchKpiUom(KpiUom persistentInstance, Paging pagging,
			String keySearch) throws DataAccessException {
		StringBuffer sb = new StringBuffer("");
		if (keySearch != null && keySearch.trim().length() > 0) {
			sb.append(" where  p.uomName like '%" + keySearch.trim() + "%' ");
		}
		ArrayList transList = new ArrayList();
		Query query = entityManager.createQuery(
				" select p from KpiUom p " + sb.toString(), KpiUom.class);
		query.setFirstResult((pagging.getPageNo() - 1) * pagging.getPageSize());
		query.setMaxResults(pagging.getPageSize());
		transList.add(query.getResultList());
		query = entityManager.createQuery("select count(p) from KpiUom p "
				+ sb.toString());
		long count = (Long) query.getSingleResult();
		transList.add(String.valueOf(count));
		return transList;
	}
	//#####[ END: KPI UOM ]################################################################################//



	//#####[ START: KPI STRUCTURE ]##############################################################################//
		public Integer saveKpiStruc(KpiStruc transientInstance)
				throws DataAccessException {
			String qryStr = "SELECT k FROM KpiStruc k WHERE KPI_STRUCTURE_NAME ='"+transientInstance.getStrucName()+"'";
			List query = entityManager.createQuery(qryStr).getResultList();
			if(query.isEmpty()){ //0=Error, 1=OK
				entityManager.persist(transientInstance);
				//return transientInstance.getStrucId();
				return 1;
			}else{
				return 0;
			}
		}
		public Integer updateKpiStruc(KpiStruc transientInstance)
				throws DataAccessException {
				String qryStr = "SELECT k FROM KpiStruc k WHERE KPI_STRUCTURE_NAME ='"+transientInstance.getStrucName()+"'";
				List query = entityManager.createQuery(qryStr).getResultList();
				if(query.isEmpty()){ //0=Error, 1=OK
						entityManager.merge(transientInstance);
					return 1;
				}else{
					return 0;
				}
				//entityManager.merge(transientInstance);
				//return 1;
			}
		public Integer deleteKpiStruc(KpiStruc persistentInstance)
				throws DataAccessException {
			int deletedCount = entityManager.createQuery(
					"DELETE FROM KpiStruc WHERE KPI_STRUCTURE_ID="
							+ persistentInstance.getStrucId()).executeUpdate();
			return deletedCount;		
			}
		
		public KpiStruc findKpiStrucById(Integer kpiStrucId)
				throws DataAccessException {
			return entityManager.find(KpiStruc.class, kpiStrucId);
		}
		
		public List searchKpiStruc(KpiStruc persistentInstance,
				Paging pagging, String keySearch) throws DataAccessException {
			StringBuffer sbGrp = new StringBuffer("");
			if (keySearch != null && keySearch.trim().length() > 0) {
				sbGrp.append("and ks.kpi_structure_name like '%" + keySearch.trim() + "%' ");
			}		
			if( persistentInstance.getGroupId() != null ){
				sbGrp.append("and ks.kpi_group_id = " + persistentInstance.getGroupId()  );
			}
			Query query = entityManager.createNativeQuery(
					" select ks.academic_year,ks.kpi_structure_id, ks.kpi_structure_name "+
						" ,ks.kpi_group_id, kg.kpi_group_name  "+
						" ,ks.strucuture_type_id , st.structure_type_name "+
						" ,ks.created_by,ks.created_dttm "+
					"from kpi_structure ks, structure_type st, kpi_group kg "+
					"where st.strucuture_type_id = ks.strucuture_type_id "+
					"and kg.kpi_group_id = ks.kpi_group_id "+
					sbGrp.toString()+
					" order by ks.kpi_structure_id");
			
			query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
			query.setMaxResults(pagging.getPageSize());	
			
			List<Object[]> results = query.getResultList();
			List<KpiStrucModel> strucs = new ArrayList<KpiStrucModel>();
			for(Object[] result: results){
				KpiStrucModel struc = new KpiStrucModel();
				struc.setAcademicYear((Integer) result[0]);
				struc.setStrucId((Integer) result[1]);			
				struc.setStrucName((String) result[2]);
				struc.setGroupId( (Integer) result[3] );
				struc.setGroupName((String) result[4]);
				struc.setStructureType((Integer) result[5]);
				struc.setStrucTypeName((String) result[6]);
				struc.setCreatedBy((String) result[7]);
				struc.setCreatedDate((Timestamp) result[8]);
				strucs.add(struc);
			}
			ArrayList<Object> transList = new ArrayList<Object>();
			transList.add(strucs);
			
			query = entityManager.createNativeQuery(
					"SELECT count(*) "+
					"from kpi_structure ks, structure_type st, kpi_group kg "+
					"where st.strucuture_type_id = ks.strucuture_type_id "+
					"and kg.kpi_group_id = ks.kpi_group_id "+
					sbGrp.toString());
			BigInteger count = (BigInteger) query.getSingleResult();
			transList.add(String.valueOf(count));
			return transList;
		}
	//#####[ END: KPI STRUCTURE ]################################################################################//				
		
		//=====[ START: ORG ]========================================================================================//
		public Org findOrgById(Integer orgTypeTypeId) throws DataAccessException {
			return entityManager.find(Org.class, orgTypeTypeId);		
		}
		public List searchOrg(Org persistentInstance,
				Paging pagging, String keySearch) throws DataAccessException {
			/*StringBuffer sbOrg = new StringBuffer("");
			if (keySearch != null && keySearch.trim().length() > 0) {
				sbOrg.append(" where k.levelId = " + keySearch.trim());
			}
			ArrayList transList = new ArrayList();
			Query query = entityManager.createQuery(
					" select k from Org k " + sbOrg.toString() 
					,	Org.class);
			query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
			query.setMaxResults(pagging.getPageSize());	
			transList.add(query.getResultList());

			query = entityManager
					.createQuery("select count(k) from Org k "
							+ sbOrg.toString());
			long count = (Long) query.getSingleResult();
			transList.add(String.valueOf(count));
			return transList;*/
			return null;
		}
		
		public List searchOrgByLevelId(Org persistentInstance,
				Paging pagging, String keySearch) throws DataAccessException {
			ArrayList transList = new ArrayList();
			Query query = entityManager.createQuery(
					"select k from Org k where k.levelId="+persistentInstance.getLevelId()
					,Org.class);
			query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
			query.setMaxResults(pagging.getPageSize());	
			transList.add(query.getResultList());
			
			Integer count = query.getResultList().size();
			transList.add(String.valueOf(count));
			return transList;
		}
		
		public List searchOrgByFacultyCode(Org persistentInstance,
				Paging pagging, String keySearch) throws DataAccessException {
			StringBuffer sbOrg = new StringBuffer("");
			if (keySearch != null && keySearch.trim().length() > 0) {
				sbOrg.append("where k.facultyCode = '" + keySearch.trim()+"' ");
			}
			ArrayList transList = new ArrayList();
			Query query = entityManager.createQuery(
					"select k from Org k " 
					+ sbOrg.toString()
					+"group by k.courseCode ",	Org.class);
			query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
			query.setMaxResults(pagging.getPageSize());	
			transList.add(query.getResultList());
			Integer count = query.getResultList().size();
			transList.add(String.valueOf(count));
			return transList;
		}
		
		public List searchOrgGroupByCourseCode(Org persistentInstance,
				Paging pagging, String keySearch) throws DataAccessException {
			StringBuffer sbOrg = new StringBuffer("");
			if (keySearch != null && keySearch.trim().length() > 0) {
				sbOrg.append("where k.levelId = '" + keySearch.trim()+"' ");
			}
			ArrayList transList = new ArrayList();
			Query query = entityManager.createQuery(
					"select k from Org k " 
					+ sbOrg.toString()
					+"group by k.courseCode ",	Org.class);
			query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
			query.setMaxResults(pagging.getPageSize());	
			transList.add(query.getResultList());

			query = entityManager
					.createQuery("select count(k) from Org k "
							+ sbOrg.toString()
							+"group by k.courseCode");
			long count = (Long) query.getSingleResult();
			transList.add(String.valueOf(count));
			return transList;
		}
		
		@SuppressWarnings("unused")
		public List searchOrgIdByOthersCode(Org persistentInstance,
				Paging pagging, String keySearch, String[] otherKeySearch) throws DataAccessException {
			/**
			 * otherKeySearch Index[0]:Level Mode, Index[1]:Faculty Code, Index[2]: Course Code
			 * (Level Mode = U:University, F:Faculty, C:Couse)
			 */
			String levelMode = otherKeySearch[0];
			String facultyCode = otherKeySearch[1];
			String courseCode = otherKeySearch[2];
			StringBuffer sbOrg = new StringBuffer("");		
			if (keySearch != null && keySearch.trim().length() > 0) {			
				if(levelMode.equals("U")){
					sbOrg.append("where k.levelId = "+keySearch.trim()+" "
							+ "and k.facultyCode is null "
							+ "and k.courseCode is null ");
				}else if(levelMode.equals("F")){
					sbOrg.append("where k.facultyCode = '"+facultyCode.trim()+"' "
							+ "and k.courseCode = '"+courseCode.trim()+"' ");
				}else if(levelMode.equals("C")){
					sbOrg.append("where k.courseCode = '"+courseCode.trim()+"' ");
				}
			}
			ArrayList transList = new ArrayList();
			Query query = entityManager.createQuery(
					" select k from Org k " + sbOrg.toString() 
					,	Org.class);
			query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize());
			query.setMaxResults(pagging.getPageSize());
			transList.add(query.getResultList());

			/*query = entityManager
					.createQuery("select count(k) from Org k "
							+ sbOrg.toString());
			long count = (Long) query.getSingleResult();*/
			transList.add(String.valueOf(1));
			return transList;
		}
		@SuppressWarnings("rawtypes")
		public List getAllOrgUniversity(Org org, Paging page){
			// no limit size
			List transList = new ArrayList();
			String queryString = "select k from Org k where k.levelId = 1 group by k.universityCode";	
			Query query = entityManager.createQuery(queryString,	Org.class);
			List results = query.getResultList();
			transList.add(results);
			transList.add(results.size());
			return transList;
		}
		@SuppressWarnings("rawtypes")
		public List getOrgFacultyOfUniversity(Org org, Paging page){
			List transList = new ArrayList();
			List results = null;
			Integer size = 0;
			if(org.getUniversityCode()!=null && !org.getUniversityCode().equals("")){
				StringBuffer sb = new StringBuffer("");		
				sb.append(" and universityCode='"+org.getUniversityCode()+"' and path like '%["+org.getOrgId()+"]%'");
				String queryString = "select k from Org k where k.levelId = 2 "+sb.toString()+" group by k.facultyCode";
				
				Query query = entityManager.createQuery(queryString	,	Org.class);
				results = query.getResultList();
				size=results.size();
			}
			transList.add(results);
			transList.add(size);
			return transList;
		}
		@SuppressWarnings("rawtypes")
		public List getOrgCourseOfFaculty(Org org, Paging page){
			List transList = new ArrayList();
			List results = null;
			Integer size = 0;
			if(org.getUniversityCode()!=null && !org.getUniversityCode().equals("") 
					&& org.getFacultyCode()!=null && !org.getFacultyCode().equals("")){
				StringBuffer sb = new StringBuffer("");		
				sb.append(" and k.universityCode='"+org.getUniversityCode()+"'");
				sb.append(" and k.facultyCode='"+org.getFacultyCode()+"'");
				String queryString = "select k from Org k where k.levelId = 3 "+sb.toString()+" group by k.courseCode";
				Query query = entityManager.createQuery(queryString	,	Org.class);
				results = query.getResultList();
				size = results.size();
			}
			transList.add(results);
			transList.add(size);
			return transList;
		}
	//=====[ END: ORG ]=========================================================================================//	
		
	//#####[ START: ORG TYPE ]###############################################################################//
		public OrgType findOrgTypeById(Integer kpiGroupTypeId)
				throws DataAccessException {
			return entityManager.find(OrgType.class, kpiGroupTypeId);
		}
		public List searchOrgType(OrgType persistentInstance,
				Paging pagging, String keySearch) throws DataAccessException {
			StringBuffer sbOrg = new StringBuffer("");
			if (keySearch != null && keySearch.trim().length() > 0) {
				sbOrg.append(" where  k.orgTypeName like '%" + keySearch.trim()
						+ "%' ");
			}
			ArrayList transList = new ArrayList();
			Query query = entityManager.createQuery(
					" select k from OrgType k " + sbOrg.toString() 
					,	OrgType.class);
			query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
			query.setMaxResults(pagging.getPageSize());	
			transList.add(query.getResultList());

			query = entityManager
					.createQuery("select count(k) from OrgType k "
							+ sbOrg.toString());
			long count = (Long) query.getSingleResult();
			transList.add(String.valueOf(count));
			return transList;
		}
	//#####[ END: ORG TYPE ]#################################################################################//
		
		
		
	//#####[ START: STRUCTURE TYPE ]###############################################################################//
		public StrucType findStrucTypeById(Integer kpiStrucTypeId)
				throws DataAccessException {
			return entityManager.find(StrucType.class, kpiStrucTypeId);
		}
		public List searchStrucType(StrucType persistentInstance,
				Paging pagging, String keySearch) throws DataAccessException {
			StringBuffer sbStruc = new StringBuffer("");
			if (keySearch != null && keySearch.trim().length() > 0) {
				sbStruc.append(" where  k.strucTypeName like '%" + keySearch.trim()
						+ "%' ");
			}
			ArrayList transList = new ArrayList();
			Query query = entityManager.createQuery(
					" select k from StrucType k " + sbStruc.toString() 
					,	StrucType.class);
			query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
			query.setMaxResults(pagging.getPageSize());	
			transList.add(query.getResultList());

			query = entityManager
					.createQuery("select count(k) from StrucType k "
							+ sbStruc.toString());
			long count = (Long) query.getSingleResult();
			transList.add(String.valueOf(count));
			return transList;
		}
	//#####[ END: STRUCTURE TYPE ]#################################################################################//
	
	
	// ################ entry #############
	public Integer saveKpi(Kpi persistentInstance)
			throws DataAccessException {
		entityManager.persist(persistentInstance);
		return persistentInstance.getKpiId();
	}
	
	public Integer saveKpiFormula(Kpi kpi){
		int updatedCount =entityManager.createNativeQuery(
				" update kpi set formula = '"+kpi.getFormula()+"' "
				+" ,formula_desc = '"+kpi.getFormulaDesc()+"' "
				+" ,percentage_flag = '"+kpi.getPercentFlag()+"' "
				+ " where kpi_id = " + kpi.getKpiId()).executeUpdate();
		
		List<String> allFunc = new ArrayList<String>();
		List<String> allCds = new ArrayList<String>();
		List<String> allMatch = new ArrayList<String>();
		String mydata = kpi.getFormula();
		Pattern pattern = Pattern.compile("\\[(sum|max):cds([0-9]+)\\]");
		Matcher matcher = pattern.matcher(mydata);
		 while (matcher.find()) {
			 allMatch.add(matcher.group());
		   allCds.add(matcher.group(2));
		   if(matcher.group(1).equals("sum")){		  allFunc.add("1");  }
		   else if(matcher.group(1).equals("max")){ 	allFunc.add("0"); }
		   else{  	allFunc.add("0");		}
		 }
		 for(int i=0;i<allMatch.size();i++){
			 //query
			KpiXCds kc = new KpiXCds();
			BeanUtils.copyProperties(kpi, kc);
			kc.setCdsId( Integer.parseInt(allCds.get(i) ));
			kc.setAccum( Integer.parseInt(allFunc.get(i) )); 
			entityManager.persist(kc);
		 }
		return updatedCount;
	}

	public Integer updateKpi(Kpi transientInstance)
			throws DataAccessException {
		entityManager.merge(transientInstance);
		return transientInstance.getKpiId();
	}
	
	public Integer deleteKpi(Kpi persistentInstance)
			throws DataAccessException {
		int deletedCount =0;
		deletedCount = entityManager.createQuery(
				"delete from Kpi where KPI_ID="
						+ persistentInstance.getKpiId()).executeUpdate();

		return deletedCount;
	}

	public Kpi findKpiById(Integer kpiId)
			throws DataAccessException {
		return entityManager.find(Kpi.class, kpiId);
	}
	
	public KpiModel findKpiWithDetailById(KpiModel kpiM)
			throws DataAccessException {
		Kpi kpi = entityManager.find(Kpi.class, kpiM.getKpiId());
		BeanUtils.copyProperties(kpi, kpiM);
		
		String sqlKpiDesc = "select kpi.kpi_id ,ct.calendar_type_name,p.period_name,uom.kpi_uom_name "
				+ " from kpi  inner join calendar_type ct on kpi.calendar_type_id = ct.calendar_type_id "
				+ " inner join period p on kpi.period_id = p.period_id "
				+ " inner join kpi_uom uom on kpi.kpi_uom_id = uom.kpi_uom_id "
				+ " where kpi.kpi_id = "+kpiM.getKpiId();
		Query query = entityManager.createNativeQuery(sqlKpiDesc);
		query.setMaxResults(1);
		if(query.getResultList().size()>0){
			List<Object[]> results = query.getResultList();
			Object[] result= results.get(0);
			kpiM.setCalendarTypeName((String)result[1]);
			kpiM.setPeriodName((String)result[2]);
			kpiM.setUomName((String)result[3]);
		}
		return kpiM;
	}
	
	@SuppressWarnings("rawtypes")
	public List searchKpi(Kpi domain,
			Paging pagging, String keySearch) throws DataAccessException {
		StringBuffer sb = new StringBuffer("");
		if (keySearch == null || keySearch.trim().length() <= 0) {
			keySearch = "";
		}
		sb.append(" where  kpi.kpi_name like '%" + keySearch.trim()+ "%' and kpi.kpi_level_id="+domain.getLevelId());
		if(domain.getStructureId()!=null){ sb.append(" and kpi.kpi_structure_id="+domain.getStructureId()); }
		if(domain.getGroupId()!=null ){	 sb.append(" and kpi.kpi_group_id="+domain.getGroupId());		}
		String sql = "select s.kpi_structure_id "+
		" ,s.kpi_structure_name as structureName"+
		" ,kpi.kpi_id"+
		 ",kpi.kpi_name "+
		" ,ct.criteria_type_name "+
		" ,g.kpi_group_short_name "+
		" ,t.kpi_type_name "+
		" ,cd.calendar_type_name " +
		" ,p.period_name"+
		" ,um.kpi_uom_name "+
		" from kpi "+
		" left join kpi_group g on kpi.kpi_group_id = g.kpi_group_id "+
		" left join kpi_structure s on s.kpi_structure_id = kpi.kpi_structure_id "+
		" left join kpi_type t on kpi.kpi_type_id = t.kpi_type_id "+
		" left join criteria_type ct on ct.criteria_type_id = kpi.criteria_type_id "
		+ " left join calendar_type cd on kpi.calendar_type_id = cd.calendar_type_id"
		+ " left join period p on kpi.period_id = p.period_id"
		+ " left join kpi_uom um on kpi.kpi_uom_id = um.kpi_uom_id "  + sb.toString() + 
		" order by kpi.kpi_structure_id,kpi.kpi_name ";
		Query query =  entityManager.createNativeQuery(sql);
		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
		query.setMaxResults(pagging.getPageSize());	
		List<Object[]> results = query.getResultList();
		List<KpiModel>  kpis = new ArrayList<KpiModel>();
		for (Object[] result: results) {
			KpiModel kpi = new KpiModel();
		    kpi.setStructureId( (Integer) result[0]); // map st name
		    kpi.setStructureName( (String) result[1]); // map st name
		    kpi.setKpiId( (Integer)result[2] ); // map kpi id
		    kpi.setKpiName( (String) result[3] );
		    kpi.setCriteriaTypeName((String) result[4]);
		    kpi.setGroupName( (String)result[5]);
		    kpi.setTypeName( (String) result[6]);
		    kpi.setCalendarTypeName( (String)result[7]);
		    kpi.setPeriodName( (String)result[8]);
		    kpi.setUomName( (String)result[9]);
		    kpis.add(kpi);
		}
		ArrayList transList = new ArrayList();
		transList.add(kpis);
		transList.add(String.valueOf(results.size()));
		return transList;
	}
	public List<?> getCdsMapWithKpi(CdsResultModel model){
		String sql = "SELECT distinct kc.cds_id ,cds.cds_name"
				+ " ,(select cds_value from cds_result where org_id = "+model.getOrgId()+" and month_id = "+model.getMonthId()+" and cds_id = kc.cds_id and row_type = 'header') as cdsValue"
				+ " FROM kpi_cds_mapping kc"
				+ " inner join cds  on kc.cds_id = cds.cds_id "
				+ " where kpi_id = "+model.getKpiId();
		Query query =  entityManager.createNativeQuery(sql);
		
		List<Object[]> results = query.getResultList();
		List<CdsResultModel>  cdss = new ArrayList<CdsResultModel>();
		for (Object[] result: results) {
			CdsResultModel cds = new CdsResultModel();
			cds.setCdsId((Integer)result[0]);
			cds.setCdsName((String)result[1]);
			cds.setCdsValue( String.valueOf( (BigDecimal)result[2]));
			cdss.add(cds);
		}
		ArrayList transList = new ArrayList();
		transList.add(cdss);
		transList.add(String.valueOf(results.size()));
		return transList;
	}
	// ============== cds
	public Integer saveCds(Cds transientInstance){
		String qryStr = "select k from Cds k "
				+ "where k.academicYear ='"+transientInstance.getAcademicYear()+"' "
				+ "and k.cdsName = '"+transientInstance.getCdsName()+"' "
				+ "and k.levelId = '"+transientInstance.getLevelId()+"'";
		List query = entityManager.createQuery(qryStr).getResultList();
		if(query.isEmpty()){ //0=Error, 1=OK
			entityManager.persist(transientInstance);
			//return transientInstance.getCdsId();
			return 1;
		}else{
			return -1;
		}
	}
	public Integer updateCds(Cds transientInstance){
		String qryStr = "select k from Cds k "
				+ " where k.academicYear ='"+transientInstance.getAcademicYear()+"' "
				+ " and k.cdsName = '"+transientInstance.getCdsName()+"'"
				+ " and k.levelId ="+transientInstance.getLevelId() 
				+ " and k.cdsId!="+transientInstance.getCdsId();
		List query = entityManager.createQuery(qryStr).getResultList();
		if(query.isEmpty()){ //0=Error, 1=OK
			Cds cr = findCdsById(transientInstance.getCdsId());
			transientInstance.setCreatedDate(cr.getCreatedDate());
			entityManager.merge(transientInstance);
			//return transientInstance.getCdsId();
			return 1;
		}else{
			return -1;
		}		
	}
	public Integer deleteCds(Cds persistentInstance){
		int deletedCount = entityManager.createQuery(
				"delete from Cds where CDS_ID="
						+ persistentInstance.getCdsId()).executeUpdate();
		return deletedCount;
	}
	public Cds findCdsById(Integer cdsId){
		return entityManager.find(Cds.class, cdsId);
	}
	@SuppressWarnings("rawtypes")
	public List searchCds(Cds persistentInstance,
			Paging pagging, String keySearch){
		StringBuffer sb = new StringBuffer("");
		if (keySearch != null && keySearch.trim().length() > 0) {
			sb.append(" where cds.cds_name like '%" + keySearch.trim()
					+ "%' ");
		}
		Query query =  entityManager.createNativeQuery(
				" SELECT cds_id,cds_name,lv.kpi_level_name "+
				" FROM cds left join kpi_level lv "+
				" on cds.kpi_level_id = lv.kpi_level_id "  + sb.toString() + 
				" order by cds_id "
				);
		Query countQuery =  entityManager.createNativeQuery(
				" SELECT count(*) "+
				" FROM cds left join kpi_level lv "+
				" on cds.kpi_level_id = lv.kpi_level_id "  + sb.toString() 	);
		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
		query.setMaxResults(pagging.getPageSize());	
		List<Object[]> results = query.getResultList();
		List<CdsModel>  cdsList = new ArrayList<CdsModel>();
		for (Object[] result: results) {
			CdsModel cds = new CdsModel();
		    cds.setCdsId( (Integer) result[0]); 
		    cds.setCdsName( (String) result[1]); 
		    cds.setLevelDesc( (String)result[2] ); // map kpi id
		    cdsList.add(cds);
		}
		ArrayList transList = new ArrayList();
		transList.add(cdsList);
		
		BigInteger count = (BigInteger) countQuery.getSingleResult();
		
		transList.add(String.valueOf(count));
		return transList;
	}
	// ########### conection  ################
	
	public Integer saveConn(DbConn transientInstance){
		String qryStr = "SELECT k FROM DbConn k WHERE k.connName ='"+transientInstance.getConnName()+"'";
		List query = entityManager.createQuery(qryStr).getResultList();
		if(query.isEmpty()){ //0=Error, 1=OK
			entityManager.persist(transientInstance);
			return transientInstance.getConnId();
		}else{
			return 0;
		}		
	}
	public Integer updateConn(DbConn transientInstance){
		entityManager.merge(transientInstance);
		return transientInstance.getConnId();
	}
	public Integer deleteConn(DbConn persistentInstance){
		int deletedCount = entityManager.createQuery(
				"delete from DbConn where connId="
						+ persistentInstance.getConnId()).executeUpdate();
		return deletedCount;
	}
	public DbConn findConnById(Integer connId){
		return entityManager.find(DbConn.class, connId);
	}

	@SuppressWarnings("rawtypes")
	public List searchConn(DbConn persistentInstance,
			Paging pagging, String keySearch){
		StringBuffer sb = new StringBuffer("");
		if (keySearch != null && keySearch.trim().length() > 0) {
			sb.append(" where  p.connName like '%" + keySearch.trim()
					+ "%' ");
		}
		ArrayList transList = new ArrayList();
		//entityManager.createNativeQuery(arg0, arg1);
		Query query = entityManager.createQuery(	
				" select p from DbConn p " + sb.toString() 
				,	DbConn.class);
		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
		query.setMaxResults(pagging.getPageSize());	
		transList.add(query.getResultList());
		query = entityManager
				.createQuery("select count(p) from DbConn p "
						+ sb.toString());
		long count = (Long) query.getSingleResult();
		transList.add(String.valueOf(count));
		
		/*List<DbConn> lc = new ArrayList<DbConn>();
		DbConn c = new DbConn();
		c.setConnId(1);
		c.setConnName("111");
		c.setDbName("base");
		c.setUsername("root");
		lc.add(c);
		c = new DbConn();
		c.setConnId(2);
		c.setConnName("222");
		c.setDbName("test2");
		c.setUsername("root");
		lc.add(c);
		System.out.print(lc.size());
		transList.add(lc); */
		return transList;
	}
	//######### con & query ##########
	public boolean checkConnection(DbConnModel connM){
		Connection con = null;
  		try {
  			  String url = "jdbc:mysql://"+connM.getHostName()+"/"+connM.getDbName();
  		      con =   DriverManager.getConnection(url,connM.getUsername(),connM.getPassword());

  		      if(con!=null){
  	  		      con.close();
  	  		      return true;
  		      }else{
  		    	  return false;
  		      }
  		}catch(Exception ex){
  		    	 return false;
  		}
	}
	// ##############preview query  ######
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List previewMysqlQueryResult(DbQueryModel q){
		ArrayList transList = new ArrayList();
		ArrayList records = new ArrayList();
		String status  = "";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = null;
	  		Statement stmt = null;
	  		try {
	  			  String url = "jdbc:mysql://"+q.getConn().getHostName()+"/"+q.getConn().getDbName();
	  			  conn =   DriverManager.getConnection(url,q.getConn().getUsername(),q.getConn().getPassword());
	  		      try{
	  				  stmt = conn.createStatement();
	  				  stmt.setMaxRows(q.getMaxRow());
	  			      ResultSet rs = stmt.executeQuery(q.getQuery());
	  			      //get Meta
		  		      ResultSetMetaData metaData = rs.getMetaData();
				  		  int countColumn = metaData.getColumnCount(); //number of column
				  		// DbResultModel head = new DbResultModel();
	  			    	  List<String> headCol = new ArrayList();
	  			    	  for(Integer i = 1;i<=countColumn;i++){
	  			    		headCol.add( metaData.getColumnLabel(i)  );
	  			    	//	head.setResult(headCol);
	  			    	  }
	  			    	  records.add(headCol);
	  			      //get DataSet
	  			      while(rs.next()){
	  			    	  //ArrayList record = new ArrayList();
	  			    //	DbResultModel record = new DbResultModel();
	  			    	  List<String> cols = new ArrayList();
	  			    	  for(Integer i=1;i<=countColumn;i++){
	  			    		  cols.add( rs.getString(i) );
	  			    	//	  record.setResult(cols);
	  			    	  }
	  			    	  records.add(cols);
	  			      }
	  			      rs.close();
		  		      stmt.close();
		  		      conn.close();
	  			  }catch(SQLException ex){
	  				   status = ex.getMessage();
	  			  }catch(Exception ex){
	  				   status = ex.getMessage();
	  			  }
	  		} catch (SQLException ex) {
	  			status = "connection refused";
	  		     // System.out.println("SQLException: " + ex.getMessage());
	  		     // System.out.println("SQLState: " + ex.getSQLState());
	  		     // System.out.println("VendorError: " + ex.getErrorCode());
	  		}
		} catch (Exception ex) {
			status = ex.getMessage();
		}
		  
		transList.add(status);
		transList.add(records);
		return transList;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List previewOracleQueryResult(DbQueryModel q){
		/*maxColumnNo is deprecated*/
		ArrayList transList = new ArrayList();
		ArrayList records = new ArrayList();
		String status  = "";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			Connection conn = null;
	  		Statement stmt = null;
	  		try {
	  		     // conn =   DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.118:1521:xe","system","password");
	  		    String url = "jdbc:oracle:thin:@"+q.getConn().getHostName()+":"+q.getConn().getPort()+":"+q.getConn().getDbName();
	  			conn = DriverManager.getConnection(url,q.getConn().getUsername(),q.getConn().getPassword());  
	  			try{
	  				  stmt = conn.createStatement();
	  				  stmt.setMaxRows(q.getMaxRow());
	  			      ResultSet rs = stmt.executeQuery(q.getQuery());
	  			      //get Meta
		  		      ResultSetMetaData metaData = rs.getMetaData();
				  		  int countColumn = metaData.getColumnCount(); //number of column
	  			    	  List<String> headCol = new ArrayList();
	  			    	  for(Integer i = 1;i<=countColumn;i++){
	  			    		headCol.add( metaData.getColumnLabel(i)  );
	  			    	  }
	  			    	  records.add(headCol);
	  			      //get DataSet
	  			      while(rs.next()){
	  			    	  //ArrayList record = new ArrayList();
	  			    	  List<String> cols = new ArrayList();
	  			    	  for(Integer i=1;i<=countColumn;i++){
	  			    		  cols.add( rs.getString(i) );
	  			    	  }
	  			    	  records.add(cols);
	  			      }
	  			      rs.close();
		  		      stmt.close();
		  		      conn.close();
	  			  }catch(SQLException ex){
	  				   status = ex.getMessage();
	  			  }catch(Exception ex){
	  				   status = ex.getMessage();
	  			  }
	  		} catch (SQLException ex) {
	  			status = "connection refused";
	  		     // System.out.println("SQLException: " + ex.getMessage());
	  		     // System.out.println("SQLState: " + ex.getSQLState());
	  		     // System.out.println("VendorError: " + ex.getErrorCode());
	  		}
		} catch (Exception ex) {
			status = ex.getMessage();
		}
		  
		transList.add(status);
		transList.add(records);
		return transList;
	}
	// ######## kpi criteria ########/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List searchCriteriaGroup(Paging pagging, String keySearch){
			StringBuffer sb = new StringBuffer("");
			if (keySearch != null && keySearch.trim().length() > 0) {
				sb.append(" where  p.groupName like '%" + keySearch.trim()
						+ "%' ");
			}
			ArrayList transList = new ArrayList();
			//entityManager.createNativeQuery(arg0, arg1);
			Query query = entityManager.createQuery(	
					" select p from CriteriaGroup p " + sb.toString() + " order by p.levelId"
					,	CriteriaGroup.class);
			query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
			query.setMaxResults(pagging.getPageSize());	
			transList.add(query.getResultList());
			
			query = entityManager
					.createQuery("select count(p) from CriteriaGroup p "
							+ sb.toString());
			long count = (Long) query.getSingleResult();
			transList.add(String.valueOf(count));
		return transList;
	}
	
	public List searchCriteriaGroupDetail(Integer groupId,Paging pagging){
		StringBuffer sb = new StringBuffer("");
		if (groupId != null && groupId != 0) {
			sb.append(" where  p.groupId =" + groupId);
		}
		ArrayList transList = new ArrayList();
		Query query = entityManager.createQuery(	
				" select p from CriteriaGroupDetail p " + sb.toString() 
				,	CriteriaGroupDetail.class);
		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
		query.setMaxResults(pagging.getPageSize());	
		transList.add(query.getResultList());

		query = entityManager
				.createQuery("select count(p) from CriteriaGroupDetail p "
						+ sb.toString());
		long count = (Long) query.getSingleResult();
		transList.add(String.valueOf(count));
		return transList;
	}
	public List searchCriteriaStandard(Integer kpiId){
		String sql = " SELECT cr.criteria_id,cr.criteria_desc "+
				" ,cr.academic_year,cr.running_no "+
				" ,cr.criteria_group_detail_id as group_id,detail_name as group_name" +
				" ,cr.kpi_id ,cr.cds_id "+
				" FROM eduqa.qualitative_criteria cr "+  
				" left join criteria_group_detail gd "+
				" on cr.criteria_group_detail_id = gd.criteria_group_detail_id "+
				" where kpi_id = "+kpiId+
				" order by cr.criteria_id ";
		Query query =  entityManager.createNativeQuery(sql);
//		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
//		query.setMaxResults(pagging.getPageSize());	
		List<Object[]> results = query.getResultList();
		List<CriteriaModel>  crList = new ArrayList<CriteriaModel>();
		for (Object[] result: results) {
			CriteriaModel cr = new CriteriaModel();
			cr.setStandardId ( (Integer) result[0] ) ; 
			cr.setDesc( (String) result[1]); 
			cr.setAcademicYear( (Integer)result[2] ); // map kpi id
			cr.setRunningNo( (Integer)result[3]);
			cr.setGroupId( (Integer) result[4]);
			cr.setGroupName( (String)result[5]);
			cr.setKpiId((Integer) result[6]);
			cr.setCdsId( (Integer) result[7] );
			crList.add(cr);
		}
		
		ArrayList transList = new ArrayList();
		transList.add(crList);
		transList.add(String.valueOf(results.size()));
		return transList;
	}
	public Integer saveCriteriaStandard(CriteriaStandard std){
		entityManager.persist(std);
		return std.getStandardId();
	}
	public Integer updateCriteriaStandard(CriteriaStandard std){
		entityManager.merge(std);
		return std.getStandardId();
	}
	public Integer deleteCriteriaStandard(CriteriaStandard std){
		int deletedCount = entityManager.createQuery(
				"delete from CriteriaStandard where criteria_id="
						+ std.getStandardId()).executeUpdate();
		return deletedCount;
	}
	// ####################baseline
	public Integer saveQuanBaseline(BaselineQuan baseline){
		entityManager.persist(baseline);
		return baseline.getBaselineId();
	}
	public Integer updateQuanBaseline(BaselineQuan baseline){
		entityManager.merge(baseline);
		return baseline.getBaselineId();
	}
	public Integer saveRangeBaseline(BaselineRange baseline){
		System.out.print("test:"+baseline.getGroupId()+":"+baseline.getKpiId());
		entityManager.persist(baseline);
		return baseline.getBaselineId();
	}
	public Integer updateRangeBaseline(BaselineRange baseline){
		entityManager.merge(baseline);
		return baseline.getBaselineId();
	}
	public Integer deleteRangeBaseline(BaselineRange baseline){
		int deletedCount = entityManager.createQuery(
				"delete from BaselineRange where baseline_id="
						+ baseline.getBaselineId()).executeUpdate();
		return deletedCount;
	}
	public Integer saveSpecBaseline(BaselineSpec baseline){
		entityManager.persist(baseline);
		return baseline.getBaselineId();
	}
	public Integer updateSpecBaseline(BaselineSpec baseline){
		entityManager.merge(baseline);
		return baseline.getBaselineId();
	}
	public Integer deleteQuanBaseline(BaselineQuan baseline){
		int deletedCount = entityManager.createQuery(
				"delete from BaselineQuan where baseline_id="
						+ baseline.getBaselineId()).executeUpdate();
		return deletedCount;
	}
	public Integer deleteSpecBaseline(BaselineSpec baseline){
		int deletedCount = entityManager.createQuery(
				"delete from BaselineSpec where baseline_id="
						+ baseline.getBaselineId()).executeUpdate();
		return deletedCount;
	}
	public List listQuanBaseline(BaselineQuan quan){
		Query query =  entityManager.createNativeQuery(
				" SELECT q.baseline_id,subtract_value,percentage_conversion_value"
				+ " ,gd.criteria_group_detail_id  ,gd.detail_name"
				+ " FROM quantitative_baseline q "
				+ " left join criteria_group_detail gd "
				+ " on q.criteria_group_detail_id = gd.criteria_group_detail_id"
				+ " where q.kpi_id =  " + quan.getKpiId()
				);
//		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
//		query.setMaxResults(pagging.getPageSize());	
		List<Object[]> results = query.getResultList();
		List<BaselineModel>  lists = new ArrayList<BaselineModel>();
		for (Object[] result: results) {
			BaselineModel model = new BaselineModel();
			model.setBaselineId((Integer) result[0]);
			model.setSubtractValue( (Integer) result[1]);
			model.setConversionValue(  (Integer) result[2]);
			model.setGroupId( (Integer) result[3]);
			model.setGroupName((String) result[4]);
			lists.add(model);
		}
		
		ArrayList transList = new ArrayList();
		transList.add(lists);
		transList.add(String.valueOf(results.size()));
		return transList;
	}
	public List listRangeBaseline(BaselineRange range){
		Query query =  entityManager.createNativeQuery(
				" select rb.baseline_id,gd.criteria_group_detail_id as groupId , gd.detail_name as groupName "+
				" ,rb.score,rb.begin_baseline,rb.end_baseline,rb.baseline_desc "+
				" from range_baseline rb "+
				" left join criteria_group_detail gd "+ 
				" on rb.criteria_group_detail_id = gd.criteria_group_detail_id "+
				" where kpi_id = "+range.getKpiId()
				);
//		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
//		query.setMaxResults(pagging.getPageSize());	
		List<Object[]> results = query.getResultList();
		List<BaselineModel>  lists = new ArrayList<BaselineModel>();
		for (Object[] result: results) {
			BaselineModel model = new BaselineModel();
			model.setBaselineId((Integer) result[0]);
			model.setGroupId( (Integer)result[1]);
			model.setGroupName( (String)result[2]);
			model.setScore((Integer) result[3]);
			model.setBeginValue( (Integer) result[4]);
			model.setEndValue((Integer) result[5]);
			model.setDesc( (String)result[6] );
			lists.add(model);
		}
		
		ArrayList transList = new ArrayList();
		transList.add(lists);
		transList.add(String.valueOf(results.size()));
		return transList;
	}
	public List listSpecBaselineGroup(BaselineSpec spec){
		Query query =  entityManager.createNativeQuery(
				" select sb.criteria_group_detail_id ,cgd.detail_name "+
				" FROM eduqa.specified_baseline sb "+
				" left join eduqa.criteria_group_detail cgd "+
				" on cgd.criteria_group_detail_id = sb.criteria_group_detail_id "+
				" where kpi_id = " + spec.getKpiId() +
				" group by sb.criteria_group_detail_id " );
//		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
//		query.setMaxResults(pagging.getPageSize());	
		List<Object[]> results = query.getResultList();
		List<BaselineModel>  lists = new ArrayList<BaselineModel>();
		for (Object[] result: results) {
			BaselineModel model = new BaselineModel();
			model.setGroupId((Integer) result[0]);
			model.setGroupName( (String) result[1]);
			lists.add(model);
		}
		ArrayList transList = new ArrayList();
		transList.add(lists);
		transList.add(String.valueOf(results.size()));
		return transList;
	}
	public List listSpecBaselineDetail(BaselineSpec spec){
		Query query =  entityManager.createNativeQuery(
				" SELECT baseline_id,qc.criteria_desc"
				+ " FROM specified_baseline sb" 
				+ " left join qualitative_criteria qc on sb.criteria_id = qc.criteria_id"
				+ " where sb.kpi_id = " + spec.getKpiId()
				+ " and sb.criteria_group_detail_id = " + spec.getGroupId()
				+ " and sb.score = " + spec.getScore()
				);
//		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
//		query.setMaxResults(pagging.getPageSize());	
		List<Object[]> results = query.getResultList();
		List<BaselineModel>  lists = new ArrayList<BaselineModel>();
		for (Object[] result: results) {
			BaselineModel model = new BaselineModel();
			model.setBaselineId((Integer) result[0]);
			model.setDesc( (String) result[1]);
			lists.add(model);

		}
		
		ArrayList transList = new ArrayList();
		transList.add(lists);
		transList.add(String.valueOf(results.size()));
		return transList;
	}
	public List listSpecBaseline(BaselineSpec spec){
		Query query =  entityManager.createNativeQuery(
				 " SELECT  sb.criteria_group_detail_id , cgd.detail_name "+
				 " ,sb.baseline_id, qc.criteria_desc "+
				"	FROM specified_baseline sb "+
				" left join criteria_group_detail cgd on cgd.criteria_group_detail_id = sb.criteria_group_detail_id "+
				" left join qualitative_criteria qc on sb.criteria_id = qc.criteria_id "+
				" where sb.kpi_id =   "+ spec.getKpiId() +
				" and sb.score =  "+spec.getScore() +
				" order by sb.criteria_group_detail_id,sb.baseline_id "
				);
//		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
//		query.setMaxResults(pagging.getPageSize());	
		List<Object[]> results = query.getResultList();
		List<BaselineModel>  lists = new ArrayList<BaselineModel>();
		for (Object[] result: results) {
			BaselineModel model = new BaselineModel();
			model.setGroupId((Integer) result[0]);
			model.setGroupName( (String) result[1]);
			model.setBaselineId((Integer) result[2]);
			model.setDesc( (String) result[3]);
			lists.add(model);
		}
		ArrayList transList = new ArrayList();
		transList.add(lists);
		transList.add(String.valueOf(results.size()));
		return transList;
	}
	
	// threshold
	public Integer saveThreshold(Threshold transientInstance)
			throws DataAccessException {
		String qryStr = "" //เช็คว่า ID นีมีข้อมูลของช่วงคะแนน ทับซ้อนกันหรือมีชื่อเดียวกันหรือไม่ 
				+ "SELECT k FROM Threshold k "
				+ "WHERE KPI_LEVEL_ID ="+transientInstance.getLevelId()+" "
				+ "AND ( (THRESHOLD_NAME='"+transientInstance.getThresholdName()+"') "
				+ "OR (BEGIN_THRESHOLD BETWEEN "+transientInstance.getBeginThreshold()+" AND "+transientInstance.getEndThreshold()+") "
				+ "OR (END_THRESHOLD BETWEEN "+transientInstance.getBeginThreshold()+" AND "+transientInstance.getEndThreshold()+") "
				+ "OR ("+transientInstance.getBeginThreshold()+" BETWEEN BEGIN_THRESHOLD AND END_THRESHOLD) "
				+ "OR ("+transientInstance.getEndThreshold()+" BETWEEN BEGIN_THRESHOLD AND END_THRESHOLD) "
				+ ")";
		List query = entityManager.createQuery(qryStr).getResultList();
		if (query.isEmpty()) { 
			entityManager.persist(transientInstance);
			// return transientInstance.getThresholdId();
			return 1;
		} else {
			return 0;
		}
		// 0=Error, 1=OK
	}

	public Integer updateThreshold(Threshold transientInstance)
			throws DataAccessException {
		/*String qryStr = "SELECT k FROM Threshold k WHERE THRESHOLD_NAME ='"
				+ transientInstance.getThresholdName() + "'";
		List query = entityManager.createQuery(qryStr).getResultList();
		entityManager.merge(transientInstance);
		return 1;*/
		String qryStr = "" //เช็คว่า ID นีมีข้อมูลของช่วงคะแนน ทับซ้อนกันหรือมีชื่อเดียวกันหรือไม่ 
				+ "SELECT k FROM Threshold k "
				+ "WHERE KPI_LEVEL_ID ="+transientInstance.getLevelId()+" "
				+ "AND ("
				+ "(BEGIN_THRESHOLD BETWEEN "+transientInstance.getBeginThreshold()+" AND "+transientInstance.getEndThreshold()+") "
				+ "OR (END_THRESHOLD BETWEEN "+transientInstance.getBeginThreshold()+" AND "+transientInstance.getEndThreshold()+") "
				+ "OR ("+transientInstance.getBeginThreshold()+" BETWEEN BEGIN_THRESHOLD AND END_THRESHOLD) "
				+ "OR ("+transientInstance.getEndThreshold()+" BETWEEN BEGIN_THRESHOLD AND END_THRESHOLD) "
				+ ")  and k.thresholdId!="+transientInstance.getThresholdId();
		List query = entityManager.createQuery(qryStr).getResultList();
		if (query.isEmpty()) { 
			entityManager.merge(transientInstance);
			// return transientInstance.getThresholdId();
			return 1;
		} else {
			return 0;
		}
	}

	public Integer deleteThreshold(Threshold persistentInstance)
			throws DataAccessException {
		int deletedCount = entityManager.createQuery(
				"DELETE FROM Threshold WHERE THRESHOLD_ID="
						+ persistentInstance.getThresholdId()).executeUpdate();
		return deletedCount;
	}

	public Threshold findThresholdById(Integer kpiThresholdId)
			throws DataAccessException {
		return entityManager.find(Threshold.class, kpiThresholdId);
	}

	public List searchThreshold(Threshold persistentInstance, Paging pagging,
			String keySearch) throws DataAccessException {
		StringBuffer sb = new StringBuffer("");
		if (keySearch != null && keySearch.trim().length() > 0) {
			sb.append(" where  p.levelId =" + keySearch.trim() );
		}else if(keySearch == null){
			sb.append(" where  p.levelId = 0");
		}
		ArrayList transList = new ArrayList();
		Query queryTld = entityManager.createQuery(
				" select p from Threshold p " + sb.toString() + " order by p.beginThreshold", Threshold.class);
		queryTld.setFirstResult((pagging.getPageNo() - 1) * pagging.getPageSize());
		queryTld.setMaxResults(pagging.getPageSize());
		transList.add(queryTld.getResultList());
		queryTld = entityManager.createQuery("select count(p) from Threshold p "
				+ sb.toString());
		long count = (Long) queryTld.getSingleResult();
		transList.add(String.valueOf(count));
		return transList;
	}
//=====[ END: THRESHOLD ]================================================================================//	
	
	
	
//=====[ START: SYS_YEAR ]==============================================================================//
	
	public SysYear getSysYear( )		throws DataAccessException {
		//alway 1 rows
			SysYear sy  = new SysYear();
			String sql = "select y from SysYear y ";
			Query query = entityManager.createQuery(sql,SysYear.class);
			query.setMaxResults(1);
			if(query.getResultList().size()>0){
				sy = (SysYear) query.getResultList().get(0);
			}
			return sy;
	}
	public Integer saveSysYear(SysYear transientInstance)
		throws DataAccessException {
		entityManager.persist(transientInstance);
		return 1;
	}
	
	public Integer updateSysYear(SysYear transientInstance)
		throws DataAccessException {
		entityManager.merge(transientInstance);
		return 1;
	}
	
	public List searchSysYear(SysYear persistentInstance,
			Paging pagging, String keySearch) throws DataAccessException {
		StringBuffer sb = new StringBuffer("");
		if (keySearch != null && keySearch.trim().length() > 0) {
			sb.append("");
		}
		ArrayList transList = new ArrayList();
		Query query = entityManager.createQuery(	
				" select p from SysYear p " + sb.toString() 
				,	SysYear.class);
		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
		query.setMaxResults(pagging.getPageSize());	
		transList.add(query.getResultList());
			query = entityManager
				.createQuery("select count(p) from SysYear p "
						+ sb.toString());
		long count = (Long) query.getSingleResult();
		transList.add(String.valueOf(count));
		return transList;
	}
//=====[ END: SYS_YEAR ]================================================================================//	
	
	

//=====[ START: SYS_MONTH ]==============================================================================//
	public SysMonthModel findSysMonthById(Integer monthId){
		SysMonthModel monthM = new SysMonthModel();
		try{
		SysMonth domain = new SysMonth();
		domain = entityManager.find(SysMonth.class, monthId);
		BeanUtils.copyProperties(domain, monthM);
		}catch(Exception ex){
		}
		return monthM;
	}
	
	public Integer saveSysMonth(SysMonth transientInstance){
		
		return 0;
	}
	public Integer saveSysMonthProc(SysMonth transientInstance, String[] otherKeySearch)
		throws DataAccessException {
		/*
		 * otherKeySearch Description
		 * Index[0]:year, Index[1]:begin academic month, Index[2]:begin fiscal month 
		 */

		Integer saveStatus = 0;
		List query = entityManager.createQuery("select y from SysMonth y where y.academicYear = :paramYear")
				.setParameter("paramYear", Integer.parseInt(otherKeySearch[0]))
				.getResultList();
		
		if (query.isEmpty()){
			try {
				//Query procedureQry = entityManager.createNativeQuery("CALL generateSysYearBySystemSetup(2560, 8, 10);");
				Query procedureQry = entityManager.createNativeQuery("CALL generateSysYearBySystemSetup(:year, :beginAcaMonth, :beginFisMonth)")				
					.setParameter("year", otherKeySearch[0])
					.setParameter("beginAcaMonth", otherKeySearch[1])
					.setParameter("beginFisMonth", otherKeySearch[2]);
				List procedureResult = procedureQry.getResultList();
			//	System.out.println("Execute status: "+procedureResult.get(0));
				saveStatus = 1;
			} catch (Exception e) {
		//		System.out.println("Execute status: "+ e.getMessage());
				saveStatus = -9;
			}
		}
		//entityManager.persist(transientInstance);
		//System.out.println("saveStatus: "+saveStatus);
		return saveStatus;
	}
	
	public Integer updateSysMonth(SysMonth transientInstance)
		throws DataAccessException {
		entityManager.merge(transientInstance);
		return 1;
	}
	
	public List searchSysMonth(SysMonth persistentInstance,Paging pagging, String keySearch) throws DataAccessException {
		StringBuffer sb = new StringBuffer("");
		if (keySearch != null && keySearch.trim().length() > 0) {
			sb.append("");
		}
		ArrayList transList = new ArrayList();
		String sqlString = " select p from SysMonth p " + sb.toString() + " where p.academicYear is not null group by p.academicYear ";
		Query query = entityManager.createQuery( sqlString	,	SysMonth.class);
		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
		query.setMaxResults(pagging.getPageSize());	
		transList.add(query.getResultList());
		Integer count = query.getResultList().size();
		transList.add(String.valueOf(count));
		return transList;
	}
	
	public List getCalendarYearsByAcad(SysMonth months,Paging pagging, String keySearch) throws DataAccessException {
		StringBuffer sb = new StringBuffer("");
		if (keySearch != null && keySearch.trim().length() > 0) {
			sb.append("");
		}
		ArrayList transList = new ArrayList();
		String sqlString = " select p from SysMonth p " + sb.toString() + " where p.academicYear = "+months.getAcademicYear()+" group by p.calendarYear ";
		Query query = entityManager.createQuery( sqlString	,	SysMonth.class);
		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
		query.setMaxResults(pagging.getPageSize());	
		transList.add(query.getResultList());
		Integer count = query.getResultList().size();
		transList.add(String.valueOf(count));
		return transList;
	}
	
	public List getMonthsByCalendar(SysMonth months,Paging pagging, String otherSearch) throws DataAccessException {
		StringBuffer sb = new StringBuffer("");
		if (otherSearch != null && otherSearch.trim().length() > 0) {
			sb.append("");
		}
		pagging.setPageSize(12); // 12 month
		ArrayList transList = new ArrayList();
		String sqlString = " select p from SysMonth p " + sb.toString() + " where p.calendarYear =  "+months.getCalendarYear();
		Query query = entityManager.createQuery( sqlString	,	SysMonth.class);
		query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
		query.setMaxResults(pagging.getPageSize());	
		transList.add(query.getResultList());
		Integer count = query.getResultList().size();
		transList.add(String.valueOf(count));
		return transList;
	}
	
	public List getMonthId(SysMonth persistentInstance, Paging pagging,
			String keySearch, String[] otherKeySearch) throws DataAccessException {
		/**
		 * otherKeySearch Description
		 * Index[0]:paramYear, Index[1]:paramMonth
		 */
		StringBuffer sb = new StringBuffer("");
		sb.append("where  p.calendarYear ="+persistentInstance.getCalendarYear()+" "
					+ "and p.calendarMonthNo = "+persistentInstance.getCalendarMonthNo()+" ");
		ArrayList transList = new ArrayList();
		Query query = entityManager.createQuery("select p from SysMonth p "
				+ sb.toString(), SysMonth.class);
		query.setFirstResult((pagging.getPageNo() - 1) * pagging.getPageSize());
		query.setMaxResults(pagging.getPageSize());
		transList.add(query.getResultList());
		System.out.print("mid:"+ ((SysMonth)  query.getResultList().get(0)).getMonthId() );
		query = entityManager.createQuery("select count(p) from SysMonth p "
				+ sb.toString());
		long count = (Long) query.getSingleResult();
		transList.add(String.valueOf(count));
		return transList;
	}
//=====[ END: SYS_MONTH ]================================================================================//	

	
	
// =====[ START: KPI RECOMND ]==============================================================================//
	public Integer saveKpiReComnd(KpiReComnd transientInstance)
			throws DataAccessException {
		/*String qryStr = "SELECT k FROM KpiReComnd k "
				+ " WHERE RECOMMEND ='" + transientInstance.getReComndDesc()+ "'  "
				+ " and k.orgId = "+transientInstance.getOrgId()+ " and k.groupId="+transientInstance.getGroupId()
				+" and k.academicYear="+transientInstance.getAcademicYear();
		List query = entityManager.createQuery(qryStr).getResultList(); 
		if (query.isEmpty()) { // 0=Error, 1=OK
			entityManager.persist(transientInstance);
			 return transientInstance.getReComndId();
			return 1;
		} else {
			return 0;
		}*/
		Integer stat;
		try{
			entityManager.persist(transientInstance);
			stat=1;
		}catch(Exception ex){
			stat=0;
		}
		return stat;
	}

	public Integer updateKpiReComnd(KpiReComnd transientInstance)
			throws DataAccessException {
		/*
		String qryStr = "SELECT k FROM KpiReComnd k "
				+ " WHERE RECOMMEND ='" + transientInstance.getReComndDesc()+ "'  "
				+ " and k.orgId = "+transientInstance.getOrgId()+ " and k.groupId="+transientInstance.getGroupId()
				+ " and k.academicYear="+transientInstance.getAcademicYear()
				+ " and k.reComndId!="+transientInstance.getReComndId();
		List query = entityManager.createQuery(qryStr).getResultList();
		
		if(query.isEmpty()){ //0=Error, 1=OK
			entityManager.merge(transientInstance); return 1; }else{ return 0; }
		 */
		Integer stat;
		try{
			entityManager.merge(transientInstance); 
			stat = 1;
		}catch(Exception ex){
			stat = 0;
		}
		return stat;
	}

	public Integer deleteKpiReComnd(KpiReComnd persistentInstance)
			throws DataAccessException {
		int deletedCount = entityManager.createQuery(
				"DELETE FROM KpiReComnd WHERE RECOMMEND_ID="
						+ persistentInstance.getReComndId()).executeUpdate();
		return deletedCount;
	}

	public KpiReComnd findKpiReComndById(Integer kpiReComndId)
			throws DataAccessException {
		return entityManager.find(KpiReComnd.class, kpiReComndId);
	}

	public List searchKpiReComnd(KpiReComnd persistentInstance, Paging pagging,
			String keySearch, String[] otherKeySearch) throws DataAccessException {
		/**
		 * otherKeySearch Description
		 * Index[0]:paramYear, Index[1]:paramGroup, Index[2]:paramOrg
		 */
		StringBuffer sb = new StringBuffer("");
		if (otherKeySearch != null) {
			sb.append("where  p.academicYear ="+otherKeySearch[0]+" "
					+ "and p.groupId = "+otherKeySearch[1]+" "
					+ "and p.orgId = "+otherKeySearch[2]+" ");
		}
		ArrayList transList = new ArrayList();
		Query query = entityManager.createQuery("select p from KpiReComnd p "
				+ sb.toString(), KpiReComnd.class);
		query.setFirstResult((pagging.getPageNo() - 1) * pagging.getPageSize());
		query.setMaxResults(pagging.getPageSize());
		transList.add(query.getResultList());
		query = entityManager.createQuery("select count(p) from KpiReComnd p "
				+ sb.toString());
		long count = (Long) query.getSingleResult();
		transList.add(String.valueOf(count));
		return transList;
	}
// =====[ END: KPI RECOMND ]================================================================================//

// ===== TARGET ======
	public Integer saveKpiTarget(KpiResult resultD1, KpiTargetModel target){
		Integer size = 0;
		HashMap<Integer,Double> monthList = target.getListMonth();
		for( Integer monthNum : monthList.keySet() ) {
			KpiResult result = new KpiResult();
			BeanUtils.copyProperties(resultD1, result);
			result.setResultId(null);
			Double monthValue = monthList.get(monthNum);
			result.setTargetValue(monthValue);
			//find monthId from calendarMonth
			String sql = "select p from SysMonth p "
					+ " where p.academicYear = "+resultD1.getAcademicYear()+" and  p.calendarMonthNo = "+monthNum;
			Query query = entityManager.createQuery(sql, SysMonth.class);
			query.setMaxResults(1);
			List fmonth = query.getResultList();
			SysMonth sysMonth =  ((SysMonth)fmonth.get(0));
			result.setMonthID(sysMonth.getMonthId());
			String qCheck = "select result_id from kpi_result where org_id = "+result.getOrgId()+" and kpi_id = "+result.getKpiId()+" and month_id = "+result.getMonthID();
			Query queryCheck = entityManager.createNativeQuery(qCheck);
		//	queryCheck.setMaxResults(1);
			List resultCheck = queryCheck.getResultList();
			if(resultCheck.size()==0){
			}else{
				result.setResultId((Integer)resultCheck.get(0));
				entityManager.merge(result);
				size++;
			}
		}
		return size;
	}
	public KpiResult resultDescByKpiId(KpiResult result){
		String qryStr = "select  kpi.kpi_name "
				+ " ,lv.kpi_level_id, lv.kpi_level_name "
				+ " ,g.kpi_group_id,g.kpi_group_name ,g.kpi_group_short_name "
				+" ,s.kpi_structure_id ,s.kpi_structure_name  "
				+" ,t.kpi_type_id, t.kpi_type_name , t.kpi_type_short_name "
				+" ,um.kpi_uom_id, um.kpi_uom_name  "
				+" ,cd.calendar_type_name  , kpi.criteria_type_id ,kpi.benchmark_value "
				+" ,kpi.formula_desc  ,p.period_name"
				+" from kpi  "
				+" left join kpi_level lv on kpi.kpi_level_id = lv.kpi_level_id "
				+" left join kpi_group g on kpi.kpi_group_id = g.kpi_group_id " 
				+" left join kpi_structure s on s.kpi_structure_id = kpi.kpi_structure_id " 
				+" left join kpi_type t on kpi.kpi_type_id = t.kpi_type_id "
				+" left join calendar_type cd on kpi.calendar_type_id = cd.calendar_type_id "
				+" left join kpi_uom um on kpi.kpi_uom_id = um.kpi_uom_id    "
				+" left join period p on kpi.period_id = p.period_id "
				+ " where kpi.kpi_id = "+ result.getKpiId() + " and kpi.academic_year="+result.getAcademicYear();
		List<Object[]> rows = entityManager.createNativeQuery(qryStr).getResultList();
		if(rows.size() > 0 ) {
			Object[] row = rows.get(0);
			result.setKpiName( (String) row[0] );
			result.setKpiLevelId( (Integer) row[1] );
			result.setKpiLevelName( (String) row[2]);
			result.setKpiGroupId( (Integer) row[3]);
			result.setKpiGroupName( (String)row[4]);
			result.setKpiGroupShortName( (String)row[5]);
			result.setKpiStructureId( (Integer) row[6]);
			result.setKpiStructureName( (String) row[7] );
			result.setKpiTypeId( (Integer) row[8] );
			result.setKpiTypeName( (String) row[9]);
			result.setKpiTypeShortName( (String) row[10] );
			result.setKpiUomId(  (Integer) row[11] );
			result.setKpiUomName( (String) row[12]);
			result.setCalendarTypeName( (String) row[13]);
			result.setCriteriaTypeId( (Integer) row[14] );
			result.setBenchmarkValue( Double.parseDouble( ((BigDecimal) row[15]).toString() )  );
			result.setFormulaDesc( (String) row[16] );
			result.setPeriodName((String) row[17]);
			
	//		result.setBenchmarkValue( (float) row[15]   );
		}
		return result;
	}
	public KpiTargetModel getKpiTarget(KpiTargetModel target){
		KpiTargetModel model = new KpiTargetModel();
		HashMap<Integer,Double> targets =new HashMap<Integer, Double>();
	//	StringBuffer sb = new StringBuffer("");
	
		String qryStr = "select sm.calendar_month_no,coalesce(kr.target_value,0.00) as target_value"
				+ " from sys_month sm "
				+ " inner join (select month_id,target_value from kpi_result  where org_id = "+target.getOrgId()+" and kpi_id = "+target.getKpiId()
				+ "  and academic_year="+target.getTargetOfYear()
				+ " )kr on kr.month_id = sm.month_id "
				+ "order by calendar_month_no";
		Query query = entityManager.createNativeQuery(qryStr);
		List<Object[]> rows = query.getResultList();
		for(Object[] row : rows){
			targets.put((Integer)row[0], ( (BigDecimal)row[1]).doubleValue() );
		}
		model.setListMonth(targets);
		return model;
	}

	
	// =====[ END: KPI RESULT ]================================================================================//

	// =====[ START: KPI RESULT ]==============================================================================//
		public Integer saveKpiResult(KpiResult transientInstance)	throws DataAccessException {
			String qryStr = "SELECT k FROM KpiResult k WHERE result_id ='"
					+ transientInstance.getResultId()+ "'";
			List query = entityManager.createQuery(qryStr).getResultList();
			if (query.isEmpty()) { // 0=Error, 1=OK
				entityManager.persist(transientInstance);
				// return transientInstance.getResultId();
				return 1;
			} else {
				return 0;
			}
		}

		public Integer updateKpiResult(KpiResult transientInstance)
				throws DataAccessException {
			String qryStr = "SELECT k FROM KpiResult k WHERE RECOMMEND ='";
					//+ transientInstance.getResultDesc() + "'";
			List query = entityManager.createQuery(qryStr).getResultList();
			/*
			 * if(query.isEmpty()){ //0=Error, 1=OK
			 * entityManager.merge(transientInstance); return 1; }else{ return 0; }
			 */
			entityManager.merge(transientInstance);
			return 1;
		}

		public Integer deleteKpiResult(KpiResult persistentInstance)
				throws DataAccessException {
			int deletedCount = entityManager.createQuery(
					"DELETE FROM KpiResult WHERE RECOMMEND_ID="
							+ persistentInstance.getResultId()).executeUpdate();
			return deletedCount;
		}

		public KpiResult findKpiResultById(Integer kpiResultId)
				throws DataAccessException {
			return entityManager.find(KpiResult.class, kpiResultId);
		}
		public KpiResult findKpiResultByKpi(KpiResult kpi) 			throws DataAccessException {
			KpiResult result  = new KpiResult();
			String sql ="select p from KpiResult p where p.kpiId="+kpi.getKpiId()+" and p.orgId="+kpi.getOrgId()+" and p.monthID="+kpi.getMonthID();
			entityManager.flush();
			Query query = entityManager.createQuery(sql, KpiResult.class);
			entityManager.flush();
			query.setMaxResults(1);
			if( query.getResultList().size()>0){
				result = (KpiResult) query.getResultList().get(0);
			}
			if(result.getTargetValue()==null){
				result.setTargetValue(0.00);
			}
			return result;
		}

		public List searchKpiResult(KpiResult domain, Paging pagging,
				String keySearch, String[] otherKeySearch) throws DataAccessException {
			ArrayList transList = new ArrayList();
			/*String sql  = "  select kr.kpi_structure_id,kr.kpi_structure_name"
					+ " ,kr.kpi_id,kr.kpi_group_name,kr.kpi_name"
					+ " ,kr.calendar_type_name,kr.period_name,kpi_uom_name"
					+ " ,kr.target_value,kr.actual_value ,kr.criteria_type_id"
					+ " from (select kpi_id from kpi_org_mapping where org_id = "+domain.getOrgId()+" order by kpi_id) as map  "
					+ " left join kpi_result kr on map.kpi_id = kr.kpi_id "
					+ " where  kr.kpi_group_id = "+domain.getKpiGroupId()+" and kr.academic_year = "+domain.getAcademicYear()+" and kr.month_id = "+domain.getMonthID();
			*/
			String sql = "select kr.kpi_structure_id,kr.kpi_structure_name ,kr.kpi_id,kr.kpi_group_name,kr.kpi_name "
					+ "	,kr.calendar_type_name,kr.period_name,kpi_uom_name ,kr.target_value"
					+ " ,kr.actual_value "
					+ " ,(select count(*)from eduqa.kpi_result_detail where result_id = kr.result_id and action_flag = 1) as totalAction  "
					+ " ,kr.criteria_type_id "
					+ " from  kpi_result kr  "
					+ " where org_id = "+domain.getOrgId()+" and academic_year = "+domain.getAcademicYear()
					+ " and kpi_group_id = "+domain.getKpiGroupId()+" and month_id = "+domain.getMonthID() 
					+ " order by kr.kpi_structure_id,kr.kpi_name";
			Query query = entityManager.createNativeQuery(sql);
			//query.setFirstResult((pagging.getPageNo() - 1) * pagging.getPageSize());
			//query.setMaxResults(pagging.getPageSize());
			List<Object[]> results = query.getResultList();
			List<KpiResultModel> models = new ArrayList<KpiResultModel>();
			String sqlMonth = "select m from SysMonth m where  ";
			for(Object[] result: results){
				KpiResultModel model = new KpiResultModel();
				model.setKpiStructureId((Integer)result[0]);
				model.setKpiStructureName((String) result[1]);
				model.setKpiId((Integer) result[2]);
				model.setKpiGroupName((String) result[3]);
				model.setKpiName((String) result[4]);
				model.setCalendarTypeName((String) result[5]);
				model.setPeriodName((String) result[6]);
				model.setKpiUomName((String) result[7]);
				model.setCriteriaTypeId((Integer)result[11]);
				if(result[8]==null){
					model.setTargetValue(null);
				}else{
					model.setTargetValue( ((BigDecimal) result[8]).doubleValue());
				}
				if(result[9]==null){
					model.setActualValue(null);
				}else{
					model.setActualValue( ((BigDecimal) result[9]).doubleValue());
				}
				if(result[9]!=null || ((BigInteger)result[10]).intValue()!=0 ){
						model.setHasResult("1");
				}else{
					model.setHasResult("0");
				}
				models.add(model);
			}
			transList.add(models);
			transList.add(String.valueOf(query.getResultList().size()));
			return transList;
		}
		public List searchKpiResultWithActiveKpi(KpiResultModel model){
			List<KpiResultModel> kms = new ArrayList<KpiResultModel>();
			String sql = " select kpi.kpi_structure_id,ks.kpi_structure_name,kpi.kpi_id,kpi.kpi_name,grp.kpi_group_short_name "
					+ " ,ct.calendar_type_name,p.period_name,uom.kpi_uom_name "
					+ " ,(select if(count(result_id)>0,1,0) from kpi_result r where r.kpi_id = kpi.kpi_id  "
					+ " and r.academic_year="+model.getAcademicYear()+" and org_id = "+model.getOrgId()+"  ) as used "
					+ " from (select * from kpi where academic_year = "+model.getAcademicYear()+" and kpi_level_id="+model.getKpiLevelId()+" ) kpi "
					+ " left join kpi_structure ks on ks.kpi_structure_id = kpi.kpi_structure_id and ks.academic_year = kpi.academic_year "
					+ " left join kpi_group grp on kpi.kpi_group_id = grp.kpi_group_id and kpi.academic_year = grp.academic_year "
					+ " left join calendar_type ct on kpi.calendar_type_id = ct.calendar_type_id  "
					+ " left join period p on kpi.period_id = p.period_id "
					+ " left join kpi_uom uom on kpi.kpi_uom_id = uom.kpi_uom_id  and kpi.academic_year = uom.academic_year "
					+ " order by kpi_structure_id,kpi.kpi_name ";
			
			Query query = entityManager.createNativeQuery(sql);
			if(query.getResultList().size()>0){
				List<Object[]> results = query.getResultList();
				for(Object[] result : results){
					KpiResultModel km = new KpiResultModel();
					km.setKpiStructureId( (Integer)result[0]);
					km.setKpiStructureName( (String)result[1]);
					km.setKpiId( (Integer)result[2]);
					km.setKpiName( (String)result[3]);
					km.setKpiGroupShortName( (String)result[4]);
					km.setCalendarTypeName( (String)result[5]);
					km.setPeriodName( (String)result[6] );
					km.setKpiUomName(  (String)result[7] );
					km.setResultId(  ((BigInteger)result[8]).intValue() );  //  mean flag active field 
				//	km.setResultId(  (Integer)result[8] );  //  mean flag active field 
					kms.add(km);
				}
			}
			return kms;
		}
		
		// KPI RESULT INSERTS
		public Integer insertsKpiResult(KpiResultModel model){
			Integer success = 0; 
			Query qOrg = entityManager.createQuery("select o from Org o where o.orgId ="+model.getOrgId(),Org.class);
			Org org = (Org) qOrg.getResultList().get(0);
			entityManager.flush();
			
			try{
				List<Integer> kids = model.getKpiIds();
				for( Integer kid : kids){
					KpiResult domain = new KpiResult();
					domain.setResultId(null);
					domain.setOrgId(model.getOrgId());
					domain.setOrgName(org.getOrgName());
					domain.setKpiId(kid); 
					domain.setAcademicYear(model.getAcademicYear());
					domain.setCreatedBy(model.getCreatedBy());
					domain.setUpdatedBy(model.getUpdatedBy());
					domain.setCreatedDate(model.getCreatedDate());
					domain.setUpdatedDate(model.getUpdatedDate());
					String q1 = " SELECT "+
							" kpi.kpi_id , kpi.kpi_name,kpi.formula_desc "+
							" ,kpi.kpi_level_id,lv.kpi_level_name "+
							" ,kpi.kpi_group_id,grp.kpi_group_short_name,grp.kpi_group_name "+
							" ,kpi.kpi_structure_id,st.kpi_structure_name "+
							" ,kpi.kpi_type_id,t.kpi_type_name,t.kpi_type_short_name "+
							" ,kpi.calendar_type_id,c.calendar_type_name "+
							" ,kpi.period_id,p.period_name "+
							" ,kpi.kpi_uom_id,uom.kpi_uom_name " +
							" ,kpi.criteria_type_id " +
							" ,coalesce(kpi.benchmark_value,0.00) as bv "+
							" FROM  (select * from kpi where kpi_id = "+kid+" ) kpi  "+
							" left join kpi_level lv on kpi.kpi_level_id=lv.kpi_level_id "+
							" left join kpi_group grp on grp.kpi_group_id = kpi.kpi_group_id "+
							" left join kpi_structure st on st.kpi_structure_id=kpi.kpi_structure_id "+
							" left join kpi_type t on t.kpi_type_id=kpi.kpi_type_id "+
							" left join calendar_type c on c.calendar_type_id = kpi.calendar_type_id "+
							" left join period p on p.period_id = kpi.period_id "+
							" left join kpi_uom uom on uom.kpi_uom_id = kpi.kpi_uom_id ";
					Query query = entityManager.createNativeQuery(q1);
					query.setMaxResults(1);
					
					Object[] ob = (Object[]) query.getResultList().get(0);
					domain.setKpiName((String)ob[1]);
					domain.setFormulaDesc( (String) ob[2]);
					domain.setKpiLevelId((Integer)ob[3]);
					domain.setKpiLevelName((String)ob[4]);
					domain.setKpiGroupId((Integer)ob[5]);
					domain.setKpiGroupShortName((String)ob[6]);
					domain.setKpiGroupName((String)ob[7]);
					domain.setKpiStructureId((Integer)ob[8]);
					domain.setKpiStructureName((String)ob[9]);
					domain.setKpiTypeId((Integer)ob[10]);
					domain.setKpiTypeName((String)ob[11]);
					domain.setKpiTypeShortName((String)ob[12]);
				//	domain.setCalendarTypeId((Integer)ob[13]);
					domain.setCalendarTypeName((String)ob[14]);
				//	domain.setPeriodId((Integer)ob[15]);
					domain.setPeriodName((String)ob[16]);
					domain.setKpiUomId((Integer)ob[17]);
					domain.setKpiUomName((String)ob[18]);
					domain.setCriteriaTypeId((Integer)ob[19]);
					domain.setBenchmarkValue( ((BigDecimal)ob[20]).doubleValue());
					// distrubute month 1-21

					for(Integer i = 1;i<=12;i++){
						KpiResult mm = new KpiResult();
						BeanUtils.copyProperties(domain, mm);
						String sqlMonth = "select m from SysMonth m where m.academicYear="+mm.getAcademicYear();
						if(mm.getCalendarTypeName().equals("ปีปฏิทิน")){
							sqlMonth = sqlMonth + " and m.calendarMonthNo="+i;
						}else if(mm.getCalendarTypeName().equals("ปีการศึกษา")){
							sqlMonth = sqlMonth + " and m.academicMonthNo="+i;
						}else if(mm.getCalendarTypeName().equals("ปีงบประมาณ")){
							sqlMonth = sqlMonth + " and m.fiscalMonthNo="+i;
						}
					//	System.out.print("sql:"+mm.getCalendarTypeName()+":"+sqlMonth);
						query = entityManager.createQuery(sqlMonth,SysMonth.class);
						SysMonth month = (SysMonth) query.getResultList().get(0);
						mm.setMonthID(month.getMonthId());
						entityManager.persist(mm);
					}
				}
				success = 1;
				entityManager.flush();
			}catch(Exception ex){
				success = 0;
				System.out.print(ex);
			}
			return success ; //1,0
		} 
		// kpi result del 
		public Integer deleteKpiResultByOrgId(KpiResultModel model){
			//ต้องการลบข้อมูลที่ Child Table แต่ตอนนี้ทำที่ table ให้มัน on delete cascade
			/*ALTER TABLE `eduqa`.`kpi_evidence` 
			ADD CONSTRAINT `kpi_result_detail_kpi_evidence_fk`
			  FOREIGN KEY (`result_detail_id`)
			  REFERENCES `eduqa`.`kpi_result_detail` (`result_detail_id`)
			  ON DELETE CASCADE
			  ON UPDATE NO ACTION;
			ALTER TABLE `eduqa`.`kpi_result_detail` 
			ADD CONSTRAINT `kpi_result_kpi_result_detail_fk`
			  FOREIGN KEY (`result_id`)
			  REFERENCES `eduqa`.`kpi_result` (`result_id`)
			  ON DELETE CASCADE
			  ON UPDATE NO ACTION;*/


			String sql = "DELETE FROM KpiResult k WHERE k.orgId="+model.getOrgId()+" and k.academicYear="+model.getAcademicYear();
			int deletedCount = entityManager.createQuery(sql).executeUpdate();
			entityManager.flush();
			return deletedCount;
		}
		 public KpiResultModel findKpiResultByIdentify(KpiResultModel model){
			 KpiResultModel ret = new KpiResultModel();
			 String q1 = "select r from KpiResult r where r.kpiId="+model.getKpiId()+" and r.orgId="+model.getOrgId()+" and r.monthID="+model.getMonthID();
			 Query query =  entityManager.createQuery(q1,KpiResult.class);
			 KpiResult domain = new  KpiResult();
			 domain = (KpiResult) query.getResultList().get(0);
			 BeanUtils.copyProperties(domain,ret);	
			 return ret;
		 }
		// =====[ END: KPI RESULT ]================================================================================//


		// =====[ START: CDS RESULT ]================================================================================//
			public Integer saveCdsResult(CdsResult transientInstance)
					throws DataAccessException {
				// check header
				try{
					String qryStr = "SELECT k FROM CdsResult k "
							+ "WHERE k.academicYear = '"+ transientInstance.getAcademicYear()+"'"
							+ "AND k.monthId = '"+transientInstance.getMonthId()+"'"
							+ "AND k.orgId = '"+transientInstance.getOrgId()+"'"
							+ "AND k.cdsId = '"+transientInstance.getCdsId()+"' and k.typeRow = 'header' ";
					List<CdsResult> results = entityManager.createQuery(qryStr).getResultList();
					if (results.isEmpty()) { 
						// insert header
						CdsResult rsHead = new CdsResult();
						BeanUtils.copyProperties(transientInstance,rsHead);
						rsHead.setResultRowNo(0);
						rsHead.setTypeRow("header");
						entityManager.persist(rsHead);
						entityManager.flush();
					} else {
						CdsResult rsHead= results.get(0);
						rsHead.setCdsValue(transientInstance.getCdsValue());
						rsHead.setUpdatedBy(transientInstance.getUpdatedBy());
						rsHead.setUpdatedDate(transientInstance.getUpdatedDate());
						entityManager.merge(rsHead);
						entityManager.flush();
					}
					
					//check detail * 1 head --> n detail **
					String qryStrDetail = "SELECT k FROM CdsResult k "
							+ "WHERE k.academicYear = '"+ transientInstance.getAcademicYear()+"'"
							+ "AND k.monthId = '"+transientInstance.getMonthId()+"'"
							+ "AND k.orgId = '"+transientInstance.getOrgId()+"'"
							+ "AND k.cdsId = '"+transientInstance.getCdsId()+"' and k.typeRow = 'detail' ";
					List<CdsResult> resultsDetail = entityManager.createQuery(qryStrDetail).getResultList();
					if (results.isEmpty()) { 
						CdsResult rsDetail = new CdsResult();
						BeanUtils.copyProperties(transientInstance,rsDetail);
						rsDetail.setResultRowNo(1);
						rsDetail.setTypeRow("detail");
						entityManager.persist(rsDetail);
						entityManager.flush();
					} else {
						// nothing do.  support data case problem;
						/*CdsResult rsDetail= resultsDetail.get(0);
						rsDetail.setCdsValue(transientInstance.getCdsValue());
						rsDetail.setUpdatedBy(transientInstance.getUpdatedBy());
						rsDetail.setUpdatedDate(transientInstance.getUpdatedDate());
						entityManager.merge(rsDetail);
						entityManager.flush();*/
					}
					return 1;
				}catch(Exception ex){
					return 0;
				}
			}
			public CdsResult findCdsResultById(Integer csdId)
					throws DataAccessException {
				return entityManager.find(CdsResult.class, csdId);
			}
			public CdsResult findCdsResultByCds(CdsResult cds) throws DataAccessException{
				ArrayList transList = new ArrayList();
				String sqlString = "select k from CdsResult k "
						+ " where k.cdsId= "+cds.getCdsId()+" and k.orgId="+cds.getOrgId()+" and k.monthId="+cds.getMonthId();
				Query query = entityManager.createQuery(sqlString, CdsResult.class);
				query.setMaxResults(1);
				CdsResult cdsResult = new CdsResult();
				if(query.getResultList()!=null && query.getResultList().size()>0 ){
					cdsResult = (CdsResult) query.getResultList().get(0);
				}
				return cdsResult;
			}
			public List searchCdsResult(CdsResult persistentInstance, Paging pagging,
					String keySearch, String[] otherKeySearch) throws DataAccessException {
				/**
				 * otherKeySearch Description
				 * Index[0]:paramYear, Index[1]:paramMonth, Index[2]:paramOrg
				 * Index[3]:paramCdsId
				 */
				StringBuffer sb = new StringBuffer("");
				if (otherKeySearch != null) {
					sb.append("WHERE p.academicYear = "+otherKeySearch[0]+" "
							+ "AND p.MonthId = "+otherKeySearch[1]+" "
							+ "AND p.orgId = "+otherKeySearch[2]+" "
							+ "AND p.cdsId = "+otherKeySearch[3]);
				}
				ArrayList transList = new ArrayList();
				Query query = entityManager.createQuery("select p from CdsResult p "
						+ sb.toString(), CdsResult.class);
				query.setFirstResult((pagging.getPageNo() - 1) * pagging.getPageSize());
				query.setMaxResults(pagging.getPageSize());
				transList.add(query.getResultList());
				
				transList.add("1");
				return transList;
			}
			
			public List searchCdsUsedByKpiId(CdsResult persistentInstance,
					Paging pagging, String keySearch, String[] otherKeySearch) throws DataAccessException {
				/**
				 * Other Key Search Description
				 * otherKeySearch = NULL
				 */
				StringBuffer sb = new StringBuffer("");
				if (keySearch != null && keySearch.trim().length() > 0) {
					sb.append("where map.kpi_id = "+keySearch.trim());
				}
				//ArrayList transList = new ArrayList();
				Query query = entityManager.createNativeQuery(
					"select cds.cds_id, " 
					+	"cds.academic_year, " 
					+	"cds.cds_name, "
					+	"cast(cds.sql_flag as char(1)) as sql_flag, " 
					+	"ifnull(cr.result_id, 0) as result_id, "
					+	"cast(ifnull(cr.cds_value, '0') as char(6)) as cds_value, "
					+	"ifnull(crd.result_detail_id, 0) as result_detail_id, "
					+	"cast(ifnull(crd.evidence_flag, '') as char(1)) as evidence_flag "
					+"from cds "
					+"inner join kpi_cds_mapping map on map.cds_id = cds.cds_id " 
					+"left join cds_result cr on cr.cds_id = cds.cds_id "
					+"left join cds_result_detail crd on crd.result_id = cr.result_id " 
					+sb.toString());
				
				query.setFirstResult((pagging.getPageNo()-1) * pagging.getPageSize()); 
				query.setMaxResults(pagging.getPageSize());
				
				List<Object[]> results = query.getResultList();
				List<CdsResultModel> strucs = new ArrayList<CdsResultModel>();
				for(Object[] result: results){
					CdsResultModel cdsResultModel = new CdsResultModel();
					cdsResultModel.setCdsId((Integer) result[0]);
					cdsResultModel.setAcademicYear((Integer) result[1]);
					cdsResultModel.setCdsName((String) result[2]);
					cdsResultModel.setSqlFlag((String) result[3]);
					cdsResultModel.setResultId((Integer) ((BigInteger) result[4]).intValue());
					cdsResultModel.setCdsValue((String) result[5]);
					cdsResultModel.setCdsResultDetailId((Integer) ((BigInteger)result[6]).intValue());
					cdsResultModel.setEvidenceFlag((String) result[7]);
					strucs.add(cdsResultModel);
				}
				ArrayList<Object> transList = new ArrayList<Object>();
				transList.add(strucs);
				
				/*transList.add(query.getResultList());
					query = entityManager
						.createQuery("select count(p) from SysYear p "
								+ sb.toString());
				long count = (Long) query.getSingleResult();*/
				transList.add("1");
				return transList;
			}
		// =====[ END: CDS RESULT ]================================================================================//
			
			
			
		// =====[ START: CDS RESULT DETAIL ]================================================================================//
			public Integer saveCdsResultDetail(CdsResultDetail transientInstance)
					throws DataAccessException {
				String qryStr = "SELECT k FROM CdsResultDetail k "
						+ "WHERE k.resultId = '"+ transientInstance.getResultId()+"'";
				Query query = entityManager.createQuery(qryStr,CdsResultDetail.class);
				query.setMaxResults(1);
				CdsResultDetail result = new CdsResultDetail();
				if(query.getResultList().size()>0){
					result = (CdsResultDetail) query.getResultList().get(0);
				}
				if (result.getResultDetailId()==null) { // empty = insert , exist = update
					entityManager.persist(transientInstance);
					entityManager.flush();
					return transientInstance.getResultDetailId();
				} else {
					transientInstance.setResultDetailId(result.getResultDetailId());
					entityManager.merge(transientInstance);
					entityManager.flush();
					return transientInstance.getResultDetailId();
				}
			}
			
			public Integer updateCdsResultDetail(CdsResultDetail transientInstance)
					throws DataAccessException {
				entityManager.merge(transientInstance);		
				return 1;
			}
			
			public CdsResultDetail findCdsResultDetailById(Integer cdsResultDetail)
					throws DataAccessException {
				return entityManager.find(CdsResultDetail.class, cdsResultDetail);
			}
			
			public CdsResultDetail findCdsResultDetail(CdsResultDetailModel model)
					throws DataAccessException {
				Query query = entityManager.createQuery(
						"select p from CdsResultDetail p where resultId = "+model.getResultId(),
						CdsResultDetail.class);
				List<CdsResultDetail> domains= query.getResultList();
				CdsResultDetail ret = new CdsResultDetail();
				if(domains.size()>0){
					ret = domains.get(0);
				}
				return ret;
			}
			
			public List searchCdsResultDetail(CdsResultDetail persistentInstance, Paging pagging,
					String keySearch, String[] otherKeySearch) throws DataAccessException {
				/**
				 * otherKeySearch Description
				 * Index[0]:paramYear, Index[1]:resultId
				 */
				StringBuffer sb = new StringBuffer("");
				if (otherKeySearch != null) {
					sb.append("where crd.academic_year = "+otherKeySearch[0]+" "
							+"and crd.result_id = "+otherKeySearch[1]+" ");
				}
				//ArrayList transList = new ArrayList();
				Query query = entityManager.createNativeQuery(
						"select crd.result_id, crd.result_detail_id, cast(crd.evidence_flag as char(1)) as evidence_flag, "
						+ "ifnull(ce.evidence_id, 0), ifnull(ce.evidence_path, '') "
						+ "from cds_result_detail crd "
						+ "left join cds_evidence ce on  crd.result_detail_id = ce.result_detail_id "
						+ sb.toString());
				
				query.setFirstResult((pagging.getPageNo() - 1) * pagging.getPageSize());
				query.setMaxResults(pagging.getPageSize());
				
				List<Object[]> results = query.getResultList();
				List<CdsResultDetailModel> strucs = new ArrayList<CdsResultDetailModel>();
				for(Object[] result: results){
					CdsResultDetailModel cdsResultDetailModel = new CdsResultDetailModel();
					cdsResultDetailModel.setResultId((Integer) result[0]);			
					cdsResultDetailModel.setResultDetailId((Integer) result[1]);
					cdsResultDetailModel.setEvidenceFlag((String) result[2]);
					cdsResultDetailModel.setEvidenceId((Integer) ((BigInteger) result[3]).intValue());
					cdsResultDetailModel.setEvidencePath((String) result[4]);
					strucs.add(cdsResultDetailModel);
				}
				ArrayList<Object> transList = new ArrayList<Object>();
				transList.add(strucs);
				
				/*transList.add(query.getResultList());
				query = entityManager.createQuery("select count(p) from CdsResultDetail p "
						+ sb.toString());
				long count = (Long) query.getSingleResult();*/
				
				transList.add("1");
				return transList;
			}
		// =====[ END: CDS RESULT DETAIL ]================================================================================//
			
			
			
		// =====[ START: CDS EVIDENCE ]================================================================================//
			
			public Integer saveCdsEvidence(CdsEvidence transientInstance)
					throws DataAccessException {
				System.out.print("err:doo");
				try {
					entityManager.persist(transientInstance);
					entityManager.flush();
					return 1;
				} catch (Exception err) {
					System.out.print("err:"+err);
					return 0;
				}
			}
			
			public Integer deleteCdsEvidence(CdsEvidence persistentInstance)
					throws DataAccessException {
				int deletedCount = entityManager.createQuery(
						"DELETE FROM CdsEvidence WHERE EVIDENCE_ID="
								+ persistentInstance.getEvidenceId()).executeUpdate();
				return deletedCount;
			}
			
			public CdsEvidence findCdsEvidenceById(
					Integer cdsEvidence) throws DataAccessException {
				return entityManager.find(CdsEvidence.class, cdsEvidence);
			}

			public List searchCdsEvidence(CdsEvidence evidence,Paging pagging)
					throws DataAccessException {
				/**
				 * otherKeySearch Description Index[0]: paramYear, Index[1]: cdsResulDetailtId
				 */
				StringBuffer sb = new StringBuffer("");
				sb.append(" where resultDetailId="+evidence.getResultDetailId());
				ArrayList transList = new ArrayList();
				Query query = entityManager.createQuery(
						"select p from CdsEvidence p " + sb.toString(),
						CdsEvidence.class);
				query.setFirstResult((pagging.getPageNo() - 1) * pagging.getPageSize());
				query.setMaxResults(pagging.getPageSize());
				transList.add(query.getResultList());
				query = entityManager
						.createQuery("select count(p) from CdsEvidence p "
								+ sb.toString());
				long count = (Long) query.getSingleResult();
				transList.add(String.valueOf(count));
				return transList;
			}
		// =====[ END: CDS EVIDENCE ]================================================================================//
			public List<KpiResultDetailModel> findCriteriaResult(KpiResultDetailModel model){
				 List<KpiResultDetailModel> rets = new ArrayList<KpiResultDetailModel>();
			/*	String qStr = " select qc.criteria_id,qc.criteria_desc,coalesce(krd.action_flag,0) as action_flag "
						+ " from (select kpi_id from kpi_result where result_id="+model.getResultId()+")result "
						+ " inner join qualitative_criteria qc on qc.kpi_id = result.kpi_id "
						+ " left join kpi_result_detail krd on qc.criteria_id = krd.criteria_id "
						+ "order by qc.criteria_id";*/
				 String qStr = "select qc.criteria_id , qc.criteria_desc , qc.cds_id,cr.cds_value,result.action_flag"
				 		+ " from (select * from qualitative_criteria where kpi_id = "+model.getKpiId()+") qc "
				 		+ " left join (select cds_id,cds_value from cds_result where org_id = "+model.getOrgId()+" and month_id = "+model.getMonthId()+" ) cr on qc.cds_id = cr.cds_id "
				 		+ " left join (select krd.criteria_id,krd.action_flag from kpi_result kr inner join kpi_result_detail krd on kr.result_id = krd.result_id "
				 		+ " where kr.kpi_id = "+model.getKpiId()+" and org_id = "+model.getOrgId()+" and month_id = "+model.getMonthId()+") result "
				 		+ " on qc.criteria_id = result.criteria_id ";
				Query query = entityManager.createNativeQuery(qStr);
				query.setMaxResults(100); // Limiter
				List<Object[]> results = query.getResultList();
				for(Object[] result : results){
					KpiResultDetailModel m = new KpiResultDetailModel();
					m.setCriteriaId((Integer)result[0]);
					m.setCriteriaDesc((String)result[1]);
					m.setCdsId((Integer)result[2]);
					if(result[3]!=null){
						m.setCdsValue(((BigDecimal)result[3]).doubleValue());
					}
					m.setActionFlag( (String)result[4] );
					rets.add(m);
				}
				return rets;
			}
			public KpiResultDetail findKpiResultDetailById(KpiResultDetailModel model)
					throws DataAccessException {
				 KpiResultDetail domain =  entityManager.find(KpiResultDetail.class, model.getResultDetailId());
				 return domain;  
			}
			public KpiResultDetailModel findKpiResultDetailByIdentify(KpiResultDetailModel model){
				KpiResultDetailModel ret = new KpiResultDetailModel();
				 String str = "select qc.criteria_id , qc.criteria_desc , qc.cds_id,cr.cds_name,cr.cds_value,coalesce(result.action_flag,'0') as action_flag,result.evidence_flag "
					 		+ " from (select * from qualitative_criteria where kpi_id = "+model.getKpiId()+") qc "
					 		+ " left join (select cds_id,cds_name,cds_value from cds_result where org_id = "+model.getOrgId()+" and month_id = "+model.getMonthId()+" ) cr on qc.cds_id = cr.cds_id "
						 	+ " left join (select krd.criteria_id,krd.action_flag,krd.evidence_flag from kpi_result kr inner join kpi_result_detail krd on kr.result_id = krd.result_id "
					 		+ " where kr.kpi_id = "+model.getKpiId()+" and org_id = "+model.getOrgId()+" and month_id = "+model.getMonthId()+") result "
					 		+ " on qc.criteria_id = result.criteria_id "
					 		+ " where qc.criteria_id = "+model.getCriteriaId();		
				Query query = entityManager.createNativeQuery(str);
				if(query.getResultList().size()>0){
					Object[] result = (Object[]) query.getResultList().get(0);
					ret.setCriteriaId((Integer)result[0]);
					ret.setCriteriaDesc((String)result[1]);
					ret.setCdsId((Integer)result[2]);
					ret.setCdsName((String)result[3]);
					if(result[3]!=null){
						ret.setCdsValue( ((BigDecimal)result[4]).doubleValue());
					}
					ret.setActionFlag( (String)result[5] );
					ret.setEvidenceFlag((String)result[6]);
				}
				return ret;
			}
			
			// ###################  Kpi Evidence######################
			public Integer saveKpiEvidence(KpiEvidenceModel model){
				model.setAcademicYear(2558);
				try {
					//handle bug ??
					// entityManager.getTransaction().commit();
					KpiEvidence domain = new KpiEvidence();
					BeanUtils.copyProperties(model, domain);
					KpiResultDetailModel ret = new KpiResultDetailModel();

					 String str = "SELECT krd.result_detail_id,1 as tmp FROM kpi_result kr inner join kpi_result_detail krd on kr.result_id = krd.result_id "
					 		+ " where kr.org_id ="+model.getOrgId()+" and kr.kpi_id = "+model.getKpiId()+" and kr.month_id="+model.getMonthId()+" and krd.criteria_id="+model.getCriteriaId();
					 Query query = entityManager.createNativeQuery(str);
					 query.setMaxResults(1);
					 if(query.getResultList().size()>0){
						 Object[] result = (Object[]) query.getResultList().get(0);
					 	domain.setResultDetailId( (Integer)result[0] );
							 entityManager.persist(domain);
					 }
						return 1;
				} catch (Exception err) {
					return 0;
				}
			}
			public Integer deleteKpiEvidence(KpiEvidenceModel model){
				int deletedCount = entityManager.createQuery(
						"DELETE FROM KpiEvidence e WHERE e.evidenceId="+model.getEvidenceId()).executeUpdate();
				return deletedCount;
			}
			public List<KpiEvidenceModel> searchKpiEvidence(KpiEvidenceModel model){
				String q1 = "select e.evidence_id , e.evidence_path "
						+ " from ( select * from kpi_result where kpi_id = "+model.getKpiId()+" "
								+ " and org_id="+model.getOrgId()+" and month_id = "+model.getMonthId()+") r  "
						+ " inner join kpi_result_detail rd on r.result_id = rd.result_id "
						+ " inner join kpi_evidence e on rd.result_detail_id = e.result_detail_id "
						+ " where rd.criteria_id = "+model.getCriteriaId();
				Query query = entityManager.createNativeQuery(q1);
				List<Object[]> results = new ArrayList<Object[]>();
				results = query.getResultList();
				
				List<KpiEvidenceModel> rets = new ArrayList<KpiEvidenceModel>();
				for( Object[] result : results ){
					KpiEvidenceModel m = new KpiEvidenceModel();
					m.setEvidenceId( (Integer)result[0] );
					m.setEvidencePath( (String)result[1] );
					rets.add(m);
				}
				return rets;
			}
			public Integer saveKpiResultDetail(KpiResultDetailModel model){
				KpiResultDetail domain = new KpiResultDetail();
				try {
					BeanUtils.copyProperties(model, domain);
					String str = "select r from  KpiResult r where r.kpiId="+model.getKpiId()+" and r.orgId="+model.getOrgId()+" and r.monthID="+model.getMonthId();
					Query query = entityManager.createQuery(str,KpiResult.class);
					query.setMaxResults(1);
					if(query.getResultList().size()>0){
						KpiResult result = (KpiResult) query.getResultList().get(0);
						domain.setResultId(result.getResultId());
						// check exist

						String q2 = "select r from  KpiResultDetail r where r.resultId="+domain.getResultId()+" and r.criteriaId="+domain.getCriteriaId();
						Query query2 = entityManager.createQuery(q2,KpiResultDetail.class);
						query2.setMaxResults(1);
						//System.out.print("sqlsize:"+query2.getResultList().size());
						if(query2.getResultList().size()<=0){
							entityManager.persist(domain);
						}else{
							entityManager.createQuery(""
									+ "update KpiResultDetail r set "
									+ "r.actionFlag="+domain.getActionFlag()+" "
									//+ "r.updatedBy="+domain.getUpdatedBy()+" "
									//+ "r.updatedDate="+currentTimestamp+" "
									+ "where r.resultId="+domain.getResultId()+" "
									+ "and r.criteriaId="+domain.getCriteriaId()
								).executeUpdate();
						};
						entityManager.flush();
						return domain.getResultId();
					}else{

						entityManager.flush();
						return 0;
					}
				} catch (Exception err) {

					entityManager.flush();
					return 0;
				}
			}
			public Integer updateKpiResultDetailEvidence(KpiResultDetailModel model){
				KpiResultDetail domain = new KpiResultDetail();

				try {
					BeanUtils.copyProperties(model, domain);
					String str = "select r from  KpiResult r where r.kpiId="+model.getKpiId()+" and r.orgId="+model.getOrgId()+" and r.monthID="+model.getMonthId();
					Query query = entityManager.createQuery(str,KpiResult.class);
					query.setMaxResults(1);
					if(query.getResultList().size()>0){
						KpiResult result = (KpiResult) query.getResultList().get(0);
						domain.setResultId(result.getResultId());
						// check exist

						String q2 = "select r from  KpiResultDetail r where r.resultId="+domain.getResultId()+" and r.criteriaId="+domain.getCriteriaId();
						Query query2 = entityManager.createQuery(q2,KpiResultDetail.class);
						query2.setMaxResults(1);
						if(query2.getResultList().size()<=0){
							// invalid case 
						}else{
							entityManager.createQuery("update KpiResultDetail r set r.evidenceFlag='"+domain.getEvidenceFlag()+"' where r.resultId="+domain.getResultId()+" and r.criteriaId="+domain.getCriteriaId()).executeUpdate();
						};

						entityManager.flush();
						return domain.getResultId();
					}else{

						entityManager.flush();
						return 0;
					}
				} catch (Exception err) {

					entityManager.flush();
					return 0;
				}
			}
			
	
		
	// =====[ START: CDS EVIDENCE ]================================================================================//
		//####  description 
	public List<DescriptionModel> getOrgOfUser(DescriptionModel model)		throws DataAccessException {
			List<DescriptionModel> orgs = new ArrayList();
			String sql = "select Roles,UserName from user_roles where UserName like '"+model.getDescription()+"'";
			Query query = entityManager.createNativeQuery(sql);
			List<Object[]> results = query.getResultList();
			for(Object[] result: results){
				DescriptionModel org = new DescriptionModel();
				org.setDescCode( (String)result[0]);
				org.setDescription( (String)result[1]);
				orgs.add(org);
			}
			return orgs;
	}
	public List getPeriodAll(DescriptionModel model)		throws DataAccessException {
		List<DescriptionModel> periods = new ArrayList();
		Query query = entityManager.createNativeQuery("select period_id,period_name from period");
		List<Object[]> results = query.getResultList();
		for(Object[] result: results){
			DescriptionModel period = new DescriptionModel();
			period.setDescCode( String.valueOf((Integer)result[0]));
			period.setDescription( (String)result[1]);
			periods.add(period);
		}
		return periods;
	}
	public List getUomAll(DescriptionModel model)	throws DataAccessException {
		List uoms = new ArrayList();
		Query query = entityManager.createNativeQuery("SELECT kpi_uom_id,kpi_uom_name FROM eduqa.kpi_uom;");
		List<Object[]> results = query.getResultList();
		for(Object[] result: results){
			DescriptionModel uom = new DescriptionModel();
			uom.setDescCode( String.valueOf((Integer)result[0]));
			uom.setDescription( (String)result[1]);
			uoms.add(uom);
		}
		return uoms;
	}
	public List getCalendarAll(DescriptionModel model)	throws DataAccessException {
		List calendars = new ArrayList();
		Query query = entityManager.createNativeQuery("SELECT calendar_type_id,calendar_type_name FROM calendar_type order by calendar_type_name");
		List<Object[]> results = query.getResultList();
		for(Object[] result: results){
			DescriptionModel calendar = new DescriptionModel();
			calendar.setDescCode( String.valueOf((Integer)result[0]));
			calendar.setDescription( (String)result[1]);
			calendars.add(calendar);
		}
		return calendars;
	}
	public List getCriteriaTypeAll(DescriptionModel model)	throws DataAccessException {
		List returns = new ArrayList();
		Query query = entityManager.createNativeQuery("SELECT criteria_type_id,criteria_type_name FROM eduqa.criteria_type;");
		List<Object[]> results = query.getResultList();
		for(Object[] result: results){
			DescriptionModel mod = new DescriptionModel();
			mod.setDescCode( String.valueOf((Integer)result[0]));
			mod.setDescription( (String)result[1]);
			returns.add(mod);
		}
		return returns;
	}
	public List getCriteriaMethodAll(DescriptionModel model)	throws DataAccessException {
		List returns = new ArrayList();
		StringBuffer sb = new StringBuffer("");
		if( model.getGroupId()!=null){
			sb.append(" where criteria_group_id = "+ model.getGroupId());
		}
		String qStr = "SELECT criteria_method_id,criteria_method_name FROM eduqa.criteria_method "+sb.toString();
		
		Query query = entityManager.createNativeQuery(qStr);
		List<Object[]> results = query.getResultList();
		for(Object[] result: results){
			DescriptionModel mod = new DescriptionModel();
			mod.setDescCode( String.valueOf((Integer)result[0]));
			mod.setDescription( (String)result[1]);
			returns.add(mod);
		}
		return returns;
	}
	public List getKpiNameAll(){
		List returns = new ArrayList();
		String qStr = "SELECT kpi_id,kpi_name from kpi ";
		
		Query query = entityManager.createNativeQuery(qStr);
		List<Object[]> results = query.getResultList();
		for(Object[] result: results){
			DescriptionModel mod = new DescriptionModel();
			mod.setDescCode( String.valueOf((Integer)result[0]) );
			mod.setDescription( (String)result[1]);
			returns.add(mod);
		}
		return returns;
	}
	// organize
	public List getUniversityAll(){
		List returns = new ArrayList();
		String qStr = " SELECT university_code,min(university_name) as university_name "
				+ " FROM org group by university_code ";
		
		Query query = entityManager.createNativeQuery(qStr);
		List<Object[]> results = query.getResultList();
		for(Object[] result: results){
			DescriptionModel mod = new DescriptionModel();
			mod.setDescCode(   (String)result[0]  );
			mod.setDescription( (String)result[1]);
			returns.add(mod);
		}
		return returns;
	}
	public List getFacultyAll(){
		List returns = new ArrayList();
		String qStr = " SELECT faculty_code,min(faculty_name) as faculty_name "
				+ "  FROM org where faculty_name is not null and faculty_name != \"\""
				+ " group by faculty_code ";
		
		Query query = entityManager.createNativeQuery(qStr);
		List<Object[]> results = query.getResultList();
		for(Object[] result: results){
			DescriptionModel mod = new DescriptionModel();
			mod.setDescCode( (String)result[0] );
			mod.setDescription( (String)result[1]);
			returns.add(mod);
		}
		return returns;
	}
	public List getCourseAll(){
		List returns = new ArrayList();
		String qStr = "SELECT course_code,min(course_name) as course_name  "
				+ " FROM org "
				+ " where course_name is not null and course_name != \"\""
				+ " group by course_code ";
		Query query = entityManager.createNativeQuery(qStr);
		List<Object[]> results = query.getResultList();
		for(Object[] result: results){
			DescriptionModel mod = new DescriptionModel();
			mod.setDescCode(  (String)result[0]  );
			mod.setDescription( (String)result[1]);
			returns.add(mod);
		}
		return returns;
	}
	public List getMonthAll(Integer yearNo){
		List returns = new ArrayList();
		String qStr = " SELECT calendar_month_no,th_month_name FROM sys_month "
				+ " group by calendar_month_no ";
		Query query = entityManager.createNativeQuery(qStr);
		List<Object[]> results = query.getResultList();
		for(Object[] result: results){
			DescriptionModel mod = new DescriptionModel();
			mod.setDescCode( String.valueOf((Integer)result[0])  );
			mod.setDescription( (String)result[1]);
			returns.add(mod);
		}
		return returns;
	}
}
