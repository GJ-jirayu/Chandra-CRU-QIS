package th.ac.chandra.eduqa.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import th.ac.chandra.eduqa.mapper.MessageConstant;
import th.ac.chandra.eduqa.mapper.ResultService;
import th.ac.chandra.eduqa.model.*;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.constant.ServiceConstant;
import th.ac.chandra.eduqa.domain.SysMonth;
import th.ac.chandra.eduqa.xstream.common.ImakeMessage;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

@Component
@Service("eduqaServiceWSImpl")
public class EduqaServiceWSImpl extends PostCommon  implements EduqaService{

	private Integer resultPage=0;
	private String resultMessage="";
	private String resultCode = "";
	public Integer getResultPage(){
		return this.resultPage;
	}
	public String getResultMessage(){
		return this.resultMessage;
	}
	public String getResultCode(){
		return this.resultCode;
	}
	@Override
	public ResultService saveKpiLevel(KpiLevelModel kpiLevelModel) {
		kpiLevelModel.setServiceName(ServiceConstant.LEVEL_SAVE);
		ResultService rs = new ResultService();
		try{
			ImakeResultMessage imakeMessage =postMessage(kpiLevelModel,kpiLevelModel.getClass().getName(),"kpiLevelModel",true);
			rs.setError(imakeMessage.getResultMessage().isError());
			rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
			rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
		}catch(Exception e){
			rs.setError(true);
			rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
			rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
		}
		return rs;
	}

	@Override
	public ResultService updateKpiLevel(KpiLevelModel kpiLevelModel) {
		kpiLevelModel.setServiceName(ServiceConstant.LEVEL_UPDATE);
		ResultService rs = new ResultService();
		try{
		ImakeResultMessage imakeMessage =postMessage(kpiLevelModel,kpiLevelModel.getClass().getName(),"kpiLevelModel",true);
		rs.setError(imakeMessage.getResultMessage().isError());
		rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
		rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
		}catch(Exception e){
			rs.setError(true);
			rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
			rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
		}
		return rs;
	}
	@Override
	public Integer deleteKpiLevel(KpiLevelModel kpiLevelModel) {
		kpiLevelModel.setServiceName(ServiceConstant.LEVEL_DELETE);
		ImakeResultMessage imakeMessage =postMessage(kpiLevelModel,kpiLevelModel.getClass().getName(),"kpiLevelModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((KpiLevelModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public KpiLevelModel findKpiLevelById(Integer KpiLevelId) {
		return null;
	}
	@Override
	public List<KpiLevelModel> searchKpiLevel(KpiLevelModel kpiLevelModel) {
		kpiLevelModel.setServiceName(ServiceConstant.LEVEL_SEARCH);
		ImakeResultMessage imakeMessage =postMessage(kpiLevelModel,kpiLevelModel.getClass().getName(),"kpiLevelModel",true);
		this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
		return imakeMessage.getResultListObj();
	}
	
	
	//#####[ START: KPI GROUP ]########################################################################################//
		@Override
		public ResultService saveKpiGroup(KpiGroupModel kpiGroupModel) {
			kpiGroupModel.setServiceName(ServiceConstant.GROUP_SAVE);
			ResultService rs = new ResultService();
			try{
				ImakeResultMessage imakeMessage =postMessage(kpiGroupModel,kpiGroupModel.getClass().getName(),"kpiGroupModel",true);
				rs.setError(imakeMessage.getResultMessage().isError());
				rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
				rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
			}catch(Exception e){
				rs.setError(true);
				rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
				rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
			}
			return rs;
			/*kpiGroupModel.setServiceName(ServiceConstant.GROUP_SAVE);
			ImakeResultMessage imakeMessage =postMessage(kpiGroupModel,kpiGroupModel.getClass().getName(),"kpiGroupModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((KpiGroupModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;
				*/
		}

		@Override
		public ResultService updateKpiGroup(KpiGroupModel kpiGroupModel) {
			kpiGroupModel.setServiceName(ServiceConstant.GROUP_UPDATE);
			ResultService rs = new ResultService();
			try{
			ImakeResultMessage imakeMessage =postMessage(kpiGroupModel,kpiGroupModel.getClass().getName(),"kpiGroupModel",true);
			rs.setError(imakeMessage.getResultMessage().isError());
			rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
			rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
			}catch(Exception e){
				rs.setError(true);
				rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
				rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
			}
			return rs;
			/*kpiGroupModel.setServiceName(ServiceConstant.GROUP_UPDATE);
			ImakeResultMessage imakeMessage =postMessage(kpiGroupModel,kpiGroupModel.getClass().getName(),"kpiGroupModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((KpiGroupModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;
				*/
		}
		@Override
		public Integer deleteKpiGroup(KpiGroupModel kpiGroupModel) {
			kpiGroupModel.setServiceName(ServiceConstant.GROUP_DELETE);
			ImakeResultMessage imakeMessage =postMessage(kpiGroupModel,kpiGroupModel.getClass().getName(),"kpiGroupModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((KpiGroupModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;
		}
		@Override
		public KpiGroupModel findKpiGroupById(Integer KpiGroupId) {
			return null;
		}
		@Override
		public List<KpiGroupModel> searchKpiGroup(KpiGroupModel kpiGroupModel) {
			kpiGroupModel.setServiceName(ServiceConstant.GROUP_SEARCH);
			ImakeResultMessage imakeMessage =postMessage(kpiGroupModel,kpiGroupModel.getClass().getName(),"kpiGroupModel",true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
	//#####[ END: KPI GROUP ]########################################################################################//


		
	//#####[ START: KPI GROUP TYPE ]########################################################################################//
		@Override
		public ResultService saveKpiGroupType(KpiGroupTypeModel kpiGroupTypeModel) {
			kpiGroupTypeModel.setServiceName(ServiceConstant.GROUP_TYPE_SAVE);
			ResultService rs = new ResultService();
			try{
				ImakeResultMessage imakeMessage =postMessage(kpiGroupTypeModel,kpiGroupTypeModel.getClass().getName(),"kpiGroupTypeModel",true);
				rs.setError(imakeMessage.getResultMessage().isError());
				rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
				rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
			}catch(Exception e){
				rs.setError(true);
				rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
				rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
			}
			return rs;
			/*kpiGroupTypeModel.setServiceName(ServiceConstant.GROUP_TYPE_SAVE);
			ImakeResultMessage imakeMessage = postMessage(kpiGroupTypeModel,
					kpiGroupTypeModel.getClass().getName(), "kpiGroupTypeModel", true);
			if (imakeMessage.getResultListObj() != null
					&& imakeMessage.getResultListObj().size() > 0)
				return ((KpiGroupTypeModel) imakeMessage.getResultListObj().get(0))
						.getUpdateRecord();
			else
				return null;*/
		}

		@Override
		public ResultService updateKpiGroupType(KpiGroupTypeModel kpiGroupTypeModel) {
			kpiGroupTypeModel.setServiceName(ServiceConstant.GROUP_TYPE_UPDATE);
			ResultService rs = new ResultService();
			try{
				ImakeResultMessage imakeMessage =postMessage(kpiGroupTypeModel,kpiGroupTypeModel.getClass().getName(),"kpiGroupTypeModel",true);
				rs.setError(imakeMessage.getResultMessage().isError());
				rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
				rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
			}catch(Exception e){
				rs.setError(true);
				rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
				rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
			}
			return rs;
			/*kpiGroupTypeModel.setServiceName(ServiceConstant.GROUP_TYPE_UPDATE);
			ImakeResultMessage imakeMessage = postMessage(kpiGroupTypeModel,
					kpiGroupTypeModel.getClass().getName(), "kpiGroupTypeModel", true);
			if (imakeMessage.getResultListObj() != null
					&& imakeMessage.getResultListObj().size() > 0)
				return ((KpiGroupTypeModel) imakeMessage.getResultListObj().get(0))
						.getUpdateRecord();
			else
				return null;*/
		}

		@Override
		public Integer deleteKpiGroupType(KpiGroupTypeModel kpiGroupTypeModel) {
			kpiGroupTypeModel.setServiceName(ServiceConstant.GROUP_TYPE_DELETE);
			ImakeResultMessage imakeMessage = postMessage(kpiGroupTypeModel,
					kpiGroupTypeModel.getClass().getName(), "kpiGroupTypeModel", true);
			if (imakeMessage.getResultListObj() != null
					&& imakeMessage.getResultListObj().size() > 0)
				return ((KpiGroupTypeModel) imakeMessage.getResultListObj().get(0))
						.getUpdateRecord();
			else
				return null;
		}

		@Override
		public KpiGroupTypeModel findKpiGroupTypeById(Integer KpiGroupTypeId) {
			return null;
		}

		@Override
		public List<KpiGroupTypeModel> searchKpiGroupType(KpiGroupTypeModel kpiGroupTypeModel) {
			kpiGroupTypeModel.setServiceName(ServiceConstant.GROUP_TYPE_SEARCH);
			ImakeResultMessage imakeMessage = postMessage(kpiGroupTypeModel,
					kpiGroupTypeModel.getClass().getName(), "kpiGroupTypeModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
	//#####[ END: KPI GROUP TYPE ]########################################################################################//
		
		
		
	//#####[ START: KPI TYPE ]########################################################################################//
		@Override
		public ResultService saveKpiType(KpiTypeModel kpiTypeModel) {
			kpiTypeModel.setServiceName(ServiceConstant.TYPE_SAVE);
			ResultService rs = new ResultService();
			try{
				ImakeResultMessage imakeMessage =postMessage(kpiTypeModel,kpiTypeModel.getClass().getName(),"kpiTypeModel",true);
				rs.setError(imakeMessage.getResultMessage().isError());
				rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
				rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
			}catch(Exception e){
				rs.setError(true);
				rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
				rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
			}
			return rs;
			/*kpiTypeModel.setServiceName(ServiceConstant.TYPE_SAVE);
			ImakeResultMessage imakeMessage =postMessage(kpiTypeModel,kpiTypeModel.getClass().getName(),"kpiTypeModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((KpiTypeModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;*/
		}

		@Override
		public ResultService updateKpiType(KpiTypeModel kpiTypeModel) {
			kpiTypeModel.setServiceName(ServiceConstant.TYPE_UPDATE);
			ResultService rs = new ResultService();
			try{
				ImakeResultMessage imakeMessage =postMessage(kpiTypeModel,kpiTypeModel.getClass().getName(),"kpiTypeModel",true);
				rs.setError(imakeMessage.getResultMessage().isError());
				rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
				rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
			}catch(Exception e){
				rs.setError(true);
				rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
				rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
			}
			return rs;
			/*
			kpiTypeModel.setServiceName(ServiceConstant.TYPE_UPDATE);
			ImakeResultMessage imakeMessage =postMessage(kpiTypeModel,kpiTypeModel.getClass().getName(),"kpiTypeModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((KpiTypeModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;*/
		}
		@Override
		public Integer deleteKpiType(KpiTypeModel kpiTypeModel) {
			kpiTypeModel.setServiceName(ServiceConstant.TYPE_DELETE);
			ImakeResultMessage imakeMessage =postMessage(kpiTypeModel,kpiTypeModel.getClass().getName(),"kpiTypeModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((KpiTypeModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;
		}
		@Override
		public KpiTypeModel findKpiTypeById(Integer KpiTypeId) {
			return null;
		}
		@Override
		public List<KpiTypeModel> searchKpiType(KpiTypeModel kpiTypeModel) {
			kpiTypeModel.setServiceName(ServiceConstant.TYPE_SEARCH);
			ImakeResultMessage imakeMessage =postMessage(kpiTypeModel,kpiTypeModel.getClass().getName(),"kpiTypeModel",true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
	//#####[ END: KPI TYPE ]########################################################################################//
		
		
		
	//#####[ START: KPI UOM ]########################################################################################//
		@Override
		public ResultService saveKpiUom(KpiUomModel kpiUomModel) {
			kpiUomModel.setServiceName(ServiceConstant.UOM_SAVE);
			ResultService rs = new ResultService();
			try{
				ImakeResultMessage imakeMessage =postMessage(kpiUomModel,kpiUomModel.getClass().getName(),"kpiUomModel",true);
				rs.setError(imakeMessage.getResultMessage().isError());
				rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
				rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
			}catch(Exception e){
				rs.setError(true);
				rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
				rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
			}
			return rs;
			/*
			kpiUomModel.setServiceName(ServiceConstant.UOM_SAVE);
			ImakeResultMessage imakeMessage =postMessage(kpiUomModel,kpiUomModel.getClass().getName(),"kpiUomModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((KpiUomModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;
				*/
		}
		@Override
		public ResultService updateKpiUom(KpiUomModel kpiUomModel) {
			kpiUomModel.setServiceName(ServiceConstant.UOM_UPDATE);
			ResultService rs = new ResultService();
			try{
				ImakeResultMessage imakeMessage =postMessage(kpiUomModel,kpiUomModel.getClass().getName(),"kpiUomModel",true);
				rs.setError(imakeMessage.getResultMessage().isError());
				rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
				rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
			}catch(Exception e){
				rs.setError(true);
				rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
				rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
			}
			return rs;
			/*kpiUomModel.setServiceName(ServiceConstant.UOM_UPDATE);
			ImakeResultMessage imakeMessage =postMessage(kpiUomModel,kpiUomModel.getClass().getName(),"kpiUomModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((KpiUomModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;*/
		}
		@Override
		public Integer deleteKpiUom(KpiUomModel kpiUomModel) {
			kpiUomModel.setServiceName(ServiceConstant.UOM_DELETE);
			ImakeResultMessage imakeMessage =postMessage(kpiUomModel,kpiUomModel.getClass().getName(),"kpiUomModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((KpiUomModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;
		}
		@Override
		public KpiUomModel findKpiUomById(Integer KpiUomId) {
			return null;
		}
		@Override
		public List<KpiUomModel> searchKpiUom(KpiUomModel kpiUomModel) {
			kpiUomModel.setServiceName(ServiceConstant.UOM_SEARCH);
			ImakeResultMessage imakeMessage =postMessage(kpiUomModel,kpiUomModel.getClass().getName(),"kpiUomModel",true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
	//#####[ END: KPI UOM ]########################################################################################//

		
		
		
	//#####[ START: KPI Structure ]########################################################################################//
		@Override
		public ResultService saveKpiStruc(KpiStrucModel kpiStrucModel) {
			kpiStrucModel.setServiceName(ServiceConstant.STRUC_SAVE);
			ResultService rs = new ResultService();
			try{
				ImakeResultMessage imakeMessage =postMessage(kpiStrucModel,kpiStrucModel.getClass().getName(),"kpiStrucModel",true);
				rs.setError(imakeMessage.getResultMessage().isError());
				rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
				rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
			}catch(Exception e){
				rs.setError(true);
				rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
				rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
			}
			return rs;
			/*kpiStrucModel.setServiceName(ServiceConstant.STRUC_SAVE);
			ImakeResultMessage imakeMessage =postMessage(kpiStrucModel,kpiStrucModel.getClass().getName(),"kpiStrucModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((KpiStrucModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;*/
		}
		@Override
		public ResultService updateKpiStruc(KpiStrucModel kpiStrucModel) {
			kpiStrucModel.setServiceName(ServiceConstant.STRUC_UPDATE);
			ResultService rs = new ResultService();
			try{
				ImakeResultMessage imakeMessage =postMessage(kpiStrucModel,kpiStrucModel.getClass().getName(),"kpiStrucModel",true);
				rs.setError(imakeMessage.getResultMessage().isError());
				rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
				rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
			}catch(Exception e){
				rs.setError(true);
				rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
				rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
			}
			return rs;
			/*kpiStrucModel.setServiceName(ServiceConstant.STRUC_UPDATE);
			ImakeResultMessage imakeMessage =postMessage(kpiStrucModel,kpiStrucModel.getClass().getName(),"kpiStrucModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((KpiStrucModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;*/
		}
		@Override
		public Integer deleteKpiStruc(KpiStrucModel kpiStrucModel) {
			kpiStrucModel.setServiceName(ServiceConstant.STRUC_DELETE);
			ImakeResultMessage imakeMessage =postMessage(kpiStrucModel,kpiStrucModel.getClass().getName(),"kpiStrucModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((KpiStrucModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;
		}
		@Override
		public KpiStrucModel findKpiStrucById(Integer KpiStrucId) {
			return null;
		}
		@Override
		public List<KpiStrucModel> searchKpiStruc(KpiStrucModel kpiStrucModel) {
			kpiStrucModel.setServiceName(ServiceConstant.STRUC_SEARCH);
			ImakeResultMessage imakeMessage =postMessage(kpiStrucModel,kpiStrucModel.getClass().getName(),"kpiStrucModel",true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
	//#####[ END: KPI Structure ]########################################################################################//

		//=====[ START: ORG |========================================================================================//
		@Override
		public OrgModel findOrgById(OrgModel orgModel){
			orgModel.setServiceName(ServiceConstant.ORG_FIND_BY_ID);
			ImakeResultMessage imakeMessage = postMessage(orgModel,orgModel.getClass().getName(), "orgModel", true);
			@SuppressWarnings("unchecked")
			List<OrgModel> orgs =  imakeMessage.getResultListObj();
			OrgModel org = new OrgModel();
			if(orgs!=null){
				if(orgs.size()>0){
					org = orgs.get(0);
				}
			}
			return org;
		}
		
		public OrgModel searchOrg(OrgModel orgModel) {
			orgModel.setServiceName(ServiceConstant.ORG_SEARCH);
			ImakeResultMessage imakeMessage = postMessage(orgModel,
					orgModel.getClass().getName(), "orgModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return (OrgModel) imakeMessage.getResultListObj();
		}
		@Override
		public List<OrgModel> searchOrgByLevelId(OrgModel orgModel) {
			orgModel.setServiceName(ServiceConstant.ORG_SEARCH_BY_LEVEL);
			ImakeResultMessage imakeMessage = postMessage(orgModel,
					orgModel.getClass().getName(), "orgModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		
		@Override
		public List<OrgModel> searchOrgGroupByCourseCode(OrgModel orgModel) {
			orgModel.setServiceName(ServiceConstant.ORG_SEARCH_GROUPBY_COURSE);
			ImakeResultMessage imakeMessage = postMessage(orgModel,
					orgModel.getClass().getName(), "orgModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		
		@Override
		public List<OrgModel> searchOrgByFacultyCode(OrgModel orgModel) {
			orgModel.setServiceName(ServiceConstant.ORG_SEARCH_BY_FACULTY);
			ImakeResultMessage imakeMessage = postMessage(orgModel,
					orgModel.getClass().getName(), "orgModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		
		@Override
		public List<OrgModel> searchOrgIdByOthersCode(OrgModel orgModel) {
			orgModel.setServiceName(ServiceConstant.ORG_SEARCH_BY_COURSE);
			ImakeResultMessage imakeMessage = postMessage(orgModel,
					orgModel.getClass().getName(), "orgModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		// add by pun
		@Override
		public List getAllUniversity(OrgModel orgModel) {
			orgModel.setServiceName(ServiceConstant.ORG_GET_UNIVERSITY);
			ImakeResultMessage imakeMessage = postMessage(orgModel,
					orgModel.getClass().getName(), "orgModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		@Override
		public List getOrgFacultyOfUniversity(OrgModel orgModel) {
			orgModel.setServiceName(ServiceConstant.ORG_GET_FACULTY_OF_UNIVERSITY);
			ImakeResultMessage imakeMessage = postMessage(orgModel,
					orgModel.getClass().getName(), "orgModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		@Override
		public List getOrgCourseOfFaculty(OrgModel orgModel) {
			orgModel.setServiceName(ServiceConstant.ORG_GET_COURSE_OF_FACULTY);
			ImakeResultMessage imakeMessage = postMessage(orgModel,
					orgModel.getClass().getName(), "orgModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		
		@Override
		public List getOrgIdByOrgDetailFilter(OrgModel orgModel){
			orgModel.setServiceName(ServiceConstant.ORG_GET_ORGID_OF_ORG_DETAIL);
			ImakeResultMessage imakeMessage = postMessage(orgModel,
				orgModel.getClass().getName(), "orgModel", true);
		this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
		return imakeMessage.getResultListObj();
			
		}
	//=====[ END: ORG |========================================================================================//	
		
	//#####[ START: ORG TYPE ]########################################################################################//
		@Override
		public List<KpiGroupTypeModel> searchOrgType(OrgTypeModel orgTypeModel) {
			orgTypeModel.setServiceName(ServiceConstant.ORG_TYPE_SEARCH);
			ImakeResultMessage imakeMessage = postMessage(orgTypeModel,
					orgTypeModel.getClass().getName(), "orgTypeModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
	//#####[ END: ORG TYPE ]########################################################################################//
		
		
		
	//#####[ START: STRUCTURE TYPE ]########################################################################################//
		@Override
		public List<StrucTypeModel> searchStrucType(StrucTypeModel strucTypeModel) {
			strucTypeModel.setServiceName(ServiceConstant.STRUC_TYPE_SEARCH);
			ImakeResultMessage imakeMessage = postMessage(strucTypeModel,
					strucTypeModel.getClass().getName(), "strucTypeModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
	//#####[ END: STRUCTURE TYPE ]########################################################################################//
	
	//################ entry ############
	@Override
	public Integer saveKpi(KpiModel kpiModel) {
		kpiModel.setServiceName(ServiceConstant.KPI_SAVE);
		ImakeResultMessage imakeMessage =postMessage(kpiModel,kpiModel.getClass().getName(),"kpiModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((KpiModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	
	@Override
	public Integer saveKpiFormula(KpiModel kpiModel){
		kpiModel.setServiceName(ServiceConstant.KPI_SAVE_FORMULA);
		ImakeResultMessage imakeMessage =postMessage(kpiModel,kpiModel.getClass().getName(),"kpiModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((KpiModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
		return null;
	}
	@Override
	public Integer updateKpi(KpiModel kpiModel) {
		kpiModel.setServiceName(ServiceConstant.KPI_UPDATE);
		ImakeResultMessage imakeMessage =postMessage(kpiModel,kpiModel.getClass().getName(),"kpiModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((KpiModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public Integer deleteKpi(KpiModel kpiModel) {
		kpiModel.setServiceName(ServiceConstant.KPI_DELETE);
		ImakeResultMessage imakeMessage =postMessage(kpiModel,kpiModel.getClass().getName(),"kpiModel",true);
		ImakeMessage msg = imakeMessage.getResultMessage();
		this.resultMessage = msg.getMsgDesc();	
		if(imakeMessage.getResultMessage().isError()){
			return null;
		}
		else{
			return ((KpiModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		}
	}
	@Override
	public KpiModel findKpiById(Integer kpiId) {
		KpiModel KpiModel=new KpiModel();
		KpiModel.setKpiId(kpiId);
		KpiModel.setServiceName(ServiceConstant.KPI_FIND_BY_ID);
		ImakeResultMessage imakeMessage =postMessage(KpiModel,KpiModel.getClass().getName(),"kpiModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return (KpiModel)imakeMessage.getResultListObj().get(0);
		else
			return null;
	}
	@Override
	public KpiModel findKpiWithDescById(Integer kpiId) {
		KpiModel KpiModel=new KpiModel();
		KpiModel.setKpiId(kpiId);
		KpiModel.setServiceName(ServiceConstant.KPI_FIND_DETAIL_BY_ID);
		ImakeResultMessage imakeMessage =postMessage(KpiModel,KpiModel.getClass().getName(),"kpiModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return (KpiModel)imakeMessage.getResultListObj().get(0);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<KpiModel> searchKpi(KpiModel kpiModel) {
		kpiModel.setServiceName(ServiceConstant.KPI_SEARCH);
		ImakeResultMessage imakeMessage =postMessage(kpiModel,kpiModel.getClass().getName(),"kpiModel",true);
		this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
		return imakeMessage.getResultListObj();
	}
	@Override
	public ResultService saveCds(CdsModel cdsModel) {
		cdsModel.setServiceName(ServiceConstant.CDS_SAVE);
		ImakeResultMessage imakeMessage =postMessage(cdsModel,cdsModel.getClass().getName(),"cdsModel",true);
		ImakeMessage msg = imakeMessage.getResultMessage();
		ResultService rs = new ResultService();
		rs.setMsgCode(msg.getMsgCode());
		rs.setMsgDesc(msg.getMsgDesc());
		if(!msg.getMsgCode().equals("100")){	rs.setError(true); }else{
			rs.setResultInt( ((CdsModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord());
		}
		return rs;
	}
	@Override
	public ResultService updateCds(CdsModel cdsModel) {
		cdsModel.setServiceName(ServiceConstant.CDS_UPDATE);
		ImakeResultMessage imakeMessage =postMessage(cdsModel,cdsModel.getClass().getName(),"cdsModel",true);
		ResultService rs = new ResultService();
		rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
		rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
		if(!imakeMessage.getResultMessage().getMsgCode().equals("100")){	
			rs.setError(true); 
			rs.setResultInt(-1);
		}else{
			rs.setResultInt( ((CdsModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord());
		}
		return rs;
	}
	@Override
	public ResultService deleteCds(CdsModel cdsModel) {
		cdsModel.setServiceName(ServiceConstant.CDS_DELETE);
		ImakeResultMessage imakeMessage =postMessage(cdsModel,cdsModel.getClass().getName(),"cdsModel",true);
		ResultService rs = new ResultService();
		rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
		rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
		if(!imakeMessage.getResultMessage().getMsgCode().equals("100")){	
			rs.setError(true); 
			rs.setResultInt(-1);
		}else{
			rs.setResultInt( ((CdsModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord());
		}
		return rs;
	}
	@Override
	public ResultService findCdsById(Integer cdsId) {
		CdsModel cdsModel = new CdsModel();
		cdsModel.setCdsId(cdsId);
		cdsModel.setServiceName(ServiceConstant.CDS_FIND_BY_ID);
		ImakeResultMessage imakeMessage =postMessage(cdsModel,cdsModel.getClass().getName(),"cdsModel",true);
		ResultService rs = new ResultService();
		rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
		rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
		if(!imakeMessage.getResultMessage().getMsgCode().equals("100")){ 
			rs.setError(true);
		}
	//	if(imakeMessage.getResultListObj().get(0).getClass().toString().equals("CdsModel")){
			rs.setResultObj( imakeMessage.getResultListObj().get(0) );
	//	}else{
	//		rs.setError(true);
	//		rs.setResultObj(new CdsModel());
	//	}
		return rs;
	}
	@Override
	public ResultService searchCds(CdsModel cdsModel) {
		cdsModel.setServiceName(ServiceConstant.CDS_SEARCH);
		ImakeResultMessage imakeMessage =postMessage(cdsModel,cdsModel.getClass().getName(),"cdsModel",true);
		ResultService rs = new ResultService();
		rs.setResultRow(Integer.parseInt(imakeMessage.getLastpage()));
		rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
		rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
		rs.setResultObjList(imakeMessage.getResultListObj());
		rs.setResultPage(imakeMessage.getLastpage());
		return rs;
	}
	
	@Override
	public Integer saveConn(DbConnModel connModel) {
		connModel.setServiceName(ServiceConstant.CONN_SAVE);
		ImakeResultMessage imakeMessage =postMessage(connModel,connModel.getClass().getName(),"DbConnModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((DbConnModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public Integer updateConn(DbConnModel connModel) {
		connModel.setServiceName(ServiceConstant.CONN_UPDATE);
		ImakeResultMessage imakeMessage =postMessage(connModel,connModel.getClass().getName(),"DbConnModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((DbConnModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public Integer deleteConn(DbConnModel connModel) {
		connModel.setServiceName(ServiceConstant.CONN_DELETE);
		ImakeResultMessage imakeMessage =postMessage(connModel,connModel.getClass().getName(),"DbConnModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((DbConnModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public DbConnModel findConnById(Integer connId) {
		DbConnModel connModel = new DbConnModel();
		connModel.setServiceName(ServiceConstant.CONN_FIND_BY_ID);
		connModel.setConnId(connId);
		ImakeResultMessage imakeMessage =postMessage(connModel,connModel.getClass().getName(),"DbConnModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return (DbConnModel)imakeMessage.getResultListObj().get(0);
		else
			return null;
	}
	@Override
	public List searchConn(DbConnModel connModel) {
		connModel.setServiceName(ServiceConstant.CONN_SEARCH);
		ImakeResultMessage imakeMessage =postMessage(connModel,connModel.getClass().getName(),"DbConnModel",true);
	
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0){
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();	
		}
			else
		return null;
	}
	// ############# query
	@Override
	public List resultPreviewQuery(DbQueryModel model){
		model.setServiceName(ServiceConstant.query_preview);
		ImakeResultMessage imakeMessage =postMessage(model,model.getClass().getName(),"DbQueryModel",true);
		ImakeMessage msg = imakeMessage.getResultMessage();
		this.resultMessage = msg.getMsgDesc();
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0){
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();	
		}
			else
		return null;
	}
	// ########## criteria #########
	@Override
	public List searchCriteriaGroup(CriteriaGroupModel groupModel) {
		groupModel.setServiceName(ServiceConstant.CRITERIA_GROUP_SEARCH);
		ImakeResultMessage imakeMessage =postMessage(groupModel,groupModel.getClass().getName(),"CriteriaGroupModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0){
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();	
		}
			else
		return null;
	}
	@Override
	public List searchCriteriaGroupDetail(CriteriaGroupModel groupDetail) {
		groupDetail.setServiceName(ServiceConstant.CRITERIA_DETAIL_BY_GROUP);
		ImakeResultMessage imakeMessage =postMessage(groupDetail,groupDetail.getClass().getName(),"CriteriaGroupModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0){
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();	
		}
			else
		return null;
	}
	@Override
	public List searchCriteriaStandard(CriteriaModel std) {
		std.setServiceName(ServiceConstant.CRITERIA_STD_SEARCH);
		ImakeResultMessage imakeMessage =postMessage(std,std.getClass().getName(),"CriteriaModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0){
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();	
		}
			else
		return null;
	}
	
	@Override
	public Integer saveCriteriaStandard(CriteriaModel criteria) {
		criteria.setServiceName(ServiceConstant.CRITERIA_STD_SAVE);
		ImakeResultMessage imakeMessage =postMessage(criteria,criteria.getClass().getName(),"CriteriaModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((CriteriaModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public Integer deleteCriteriaStandard(CriteriaModel criteria) {
		criteria.setServiceName(ServiceConstant.CRITERIA_STD_DELETE);
		ImakeResultMessage imakeMessage =postMessage(criteria,criteria.getClass().getName(),"CriteriaModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((CriteriaModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public Integer saveQuanBaseline(BaselineModel baseline) {
		baseline.setServiceName(ServiceConstant.BASELINE_QUAN_SAVE);
		ImakeResultMessage imakeMessage =postMessage(baseline,baseline.getClass().getName(),"BaselineModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((BaselineModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public List listBaseline(BaselineModel baseline) {
		baseline.setServiceName(ServiceConstant.BASELINE_LIST);
		ImakeResultMessage imakeMessage =postMessage(baseline,baseline.getClass().getName(),"BaselineModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return imakeMessage.getResultListObj();
		else
			return null;
	}
	@Override
	public Integer saveRangeBaseline(BaselineModel baseline) {
		baseline.setServiceName(ServiceConstant.BASELINE_RANGE_SAVE);
		ImakeResultMessage imakeMessage =postMessage(baseline,baseline.getClass().getName(),"BaselineModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((BaselineModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override

	public Integer deleteBaseline(BaselineModel baseline) {
		baseline.setServiceName(ServiceConstant.BASELINE_DELETE);
		ImakeResultMessage imakeMessage =postMessage(baseline,baseline.getClass().getName(),"BaselineModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((BaselineModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public Integer saveSpecBaseline(BaselineModel baseline) {
		baseline.setServiceName(ServiceConstant.BASELINE_SPEC_SAVE);
		ImakeResultMessage imakeMessage =postMessage(baseline,baseline.getClass().getName(),"BaselineModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((BaselineModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public ResultService saveThreshold(ThresholdModel thresholdModel) {
		thresholdModel.setServiceName(ServiceConstant.THRESHOLD_SAVE);
		ResultService rs = new ResultService();
		try{
			ImakeResultMessage imakeMessage =postMessage(thresholdModel,thresholdModel.getClass().getName(),"thresholdModel",true);
			rs.setError(imakeMessage.getResultMessage().isError());
			rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
			rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
		}catch(Exception e){
			rs.setError(true);
			rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
			rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
		}
		return rs;
		/*thresholdModel.setServiceName(ServiceConstant.THRESHOLD_SAVE);
		ImakeResultMessage imakeMessage =postMessage(thresholdModel,thresholdModel.getClass().getName(),"thresholdModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((ThresholdModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;*/
	}

	@Override
	public ResultService updateThreshold(ThresholdModel thresholdModel) {
		thresholdModel.setServiceName(ServiceConstant.THRESHOLD_UPDATE);
		ResultService rs = new ResultService();
		try{
			ImakeResultMessage imakeMessage =postMessage(thresholdModel,thresholdModel.getClass().getName(),"thresholdModel",true);
			rs.setError(imakeMessage.getResultMessage().isError());
			rs.setMsgCode(imakeMessage.getResultMessage().getMsgCode());
			rs.setMsgDesc(imakeMessage.getResultMessage().getMsgDesc());
		}catch(Exception e){
			rs.setError(true);
			rs.setMsgCode(MessageConstant.RESULT_ERROR_CODE);
			rs.setMsgDesc(MessageConstant.RESULT_SERVICE_NONE_RESPONSE);
		}
		return rs;
		/*thresholdModel.setServiceName(ServiceConstant.THRESHOLD_UPDATE);
		ImakeResultMessage imakeMessage =postMessage(thresholdModel,thresholdModel.getClass().getName(),"thresholdModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((ThresholdModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;*/
	}
	@Override
	public Integer deleteThreshold(ThresholdModel thresholdModel) {
		thresholdModel.setServiceName(ServiceConstant.THRESHOLD_DELETE);
		ImakeResultMessage imakeMessage =postMessage(thresholdModel,thresholdModel.getClass().getName(),"thresholdModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((ThresholdModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public ThresholdModel findThresholdById(Integer ThresholdId) {
		return null;
	}
	@Override
	public List<ThresholdModel> searchThreshold(ThresholdModel thresholdModel) {
		thresholdModel.setServiceName(ServiceConstant.THRESHOLD_SEARCH);
		ImakeResultMessage imakeMessage =postMessage(thresholdModel,thresholdModel.getClass().getName(),"thresholdModel",true);
		this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
		return imakeMessage.getResultListObj();
	}
//=====[ END: THRESHOLD |========================================================================================//

	
	
	
//=====[ START: SYS_YEAR (Setup Master) |========================================================================================//
	
	@SuppressWarnings("unchecked")
	@Override
	public SysYearModel getSysYear(){
		SysYearModel ym = new SysYearModel();
		ym.setServiceName(ServiceConstant.SYSYEAR_GET);
		ImakeResultMessage imakeMessage =postMessage(ym,ym.getClass().getName(),"sysYearModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
		{ List<SysYearModel> sy = imakeMessage.getResultListObj(); 
			ym=sy.get(0);
		}	 
		return ym;
	}
	@Override
	public Integer saveSysYear(SysYearModel sysYearModel) {
		sysYearModel.setServiceName(ServiceConstant.SYSYEAR_SAVE);
		ImakeResultMessage imakeMessage =postMessage(sysYearModel,sysYearModel.getClass().getName(),"sysYearModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((SysYearModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}

	@Override
	public Integer updateSysYear(SysYearModel sysYearModel) {
		sysYearModel.setServiceName(ServiceConstant.SYSYEAR_UPDATE);
		ImakeResultMessage imakeMessage =postMessage(sysYearModel,sysYearModel.getClass().getName(),"sysYearModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((SysYearModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public List<SysYearModel> searchSysYear(SysYearModel sysYearModel) {
		sysYearModel.setServiceName(ServiceConstant.SYSYEAR_SEARCH);
		ImakeResultMessage imakeMessage =postMessage(sysYearModel,sysYearModel.getClass().getName(),"sysYearModel",true);
		this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
		return imakeMessage.getResultListObj();
	}
//=====[ END: SYS_YEAR |========================================================================================//
	
	
	
//=====[ START: SYS_MONTH |========================================================================================//
	@Override
	public SysMonthModel findSysMonthById(Integer monthId) {
		SysMonthModel monthM = new SysMonthModel();
		monthM.setMonthId(monthId);
		monthM.setServiceName(ServiceConstant.SYSMONTH_FIND_BY_ID);
		ImakeResultMessage imakeMessage =postMessage(monthM,monthM.getClass().getName(),"sysMonthModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return (SysMonthModel)imakeMessage.getResultListObj().get(0);
		else
			return null;
	}
	@Override
	public Integer saveSysMonth(SysMonthModel sysMonthModel) {
		sysMonthModel.setServiceName(ServiceConstant.SYSMONTH_SAVE);
		ImakeResultMessage imakeMessage =postMessage(sysMonthModel,sysMonthModel.getClass().getName(),"sysMonthModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((SysMonthModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}

	@Override
	public Integer updateSysMonth(SysMonthModel sysMonthModel) {
		sysMonthModel.setServiceName(ServiceConstant.SYSMONTH_UPDATE);
		ImakeResultMessage imakeMessage =postMessage(sysMonthModel,sysMonthModel.getClass().getName(),"sysMonthModel",true);
		if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
			return ((SysMonthModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
		else
			return null;
	}
	@Override
	public List<SysMonthModel> searchSysMonth(SysMonthModel sysMonthModel) {
		sysMonthModel.setServiceName(ServiceConstant.SYSMONTH_SEARCH);
		ImakeResultMessage imakeMessage =postMessage(sysMonthModel,sysMonthModel.getClass().getName(),"sysMonthModel",true);
		this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
		return imakeMessage.getResultListObj();
	}
	@Override
	public List<SysMonthModel> getMonthsByCalendar(SysMonthModel months) {
		months.setServiceName(ServiceConstant.SYSMONTH_GET_MONTH_BY_CALENDAR);
		ImakeResultMessage imakeMessage =postMessage(months,months.getClass().getName(),"sysMonthModel",true);
		this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
		return imakeMessage.getResultListObj();
	}
	@Override
	public List<SysMonthModel> getYearsByAcad(SysMonthModel months){
		months.setServiceName(ServiceConstant.SYSMONTH_GET_CALYEAR_BY_ACAD);
		ImakeResultMessage imakeMessage =postMessage(months,months.getClass().getName(),"sysMonthModel",true);
		this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
		return imakeMessage.getResultListObj();
	}
	@Override
	public List<SysMonthModel> getMonthId(SysMonthModel sysMonthModel) {
		sysMonthModel.setServiceName(ServiceConstant.SYSMONTH_GET_MONTH_ID);
		ImakeResultMessage imakeMessage =postMessage(sysMonthModel,sysMonthModel.getClass().getName(),"sysMonthModel",true);
		this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
		return imakeMessage.getResultListObj();
	}
//=====[ END: SYS_MONTH |========================================================================================//



	
//=====[ START: KPI RECOMND |========================================================================================//
	@Override
	public Integer saveKpiReComnd(KpiReComndModel kpiReComndModel) {
		kpiReComndModel.setServiceName(ServiceConstant.RECOMND_SAVE);
		ImakeResultMessage imakeMessage = postMessage(kpiReComndModel,
				kpiReComndModel.getClass().getName(), "kpiReComndModel", true);
		if (imakeMessage.getResultListObj() != null
				&& imakeMessage.getResultListObj().size() > 0)
			return ((KpiReComndModel) imakeMessage.getResultListObj().get(0))
					.getUpdateRecord();
		else
			return null;
	}

	@Override
	public Integer updateKpiReComnd(KpiReComndModel kpiReComndModel) {
		kpiReComndModel.setServiceName(ServiceConstant.RECOMND_UPDATE);
		ImakeResultMessage imakeMessage = postMessage(kpiReComndModel,
				kpiReComndModel.getClass().getName(), "kpiReComndModel", true);
		if (imakeMessage.getResultListObj() != null
				&& imakeMessage.getResultListObj().size() > 0)
			return ((KpiReComndModel) imakeMessage.getResultListObj().get(0))
					.getUpdateRecord();
		else
			return null;
	}

	@Override
	public Integer deleteKpiReComnd(KpiReComndModel kpiReComndModel) {
		kpiReComndModel.setServiceName(ServiceConstant.RECOMND_DELETE);
		ImakeResultMessage imakeMessage = postMessage(kpiReComndModel,
				kpiReComndModel.getClass().getName(), "kpiReComndModel", true);
		if (imakeMessage.getResultListObj() != null
				&& imakeMessage.getResultListObj().size() > 0)
			return ((KpiReComndModel) imakeMessage.getResultListObj().get(0))
					.getUpdateRecord();
		else
			return null;
	}

	@Override
	public KpiReComndModel findKpiReComndById(Integer KpiReComndId) {
		return null;
	}

	@Override
	public List<KpiReComndModel> searchKpiReComnd(
			KpiReComndModel kpiReComndModel) {
		kpiReComndModel.setServiceName(ServiceConstant.RECOMND_SEARCH);
		ImakeResultMessage imakeMessage = postMessage(kpiReComndModel,
				kpiReComndModel.getClass().getName(), "kpiReComndModel", true);
		this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
		return imakeMessage.getResultListObj();
	}
//=====[ END: KPI RECOMND |========================================================================================//

	
	
	
//=====[ START: KPI RESULT |========================================================================================//
	@Override
	public Integer saveKpiResult(KpiResultModel kpiResultModel) {
		kpiResultModel.setServiceName(ServiceConstant.RESULT_SAVE);
		ImakeResultMessage imakeMessage = postMessage(kpiResultModel,
				kpiResultModel.getClass().getName(), "kpiResultModel", true);
		if (imakeMessage.getResultListObj() != null
				&& imakeMessage.getResultListObj().size() > 0)
			return ((KpiResultModel) imakeMessage.getResultListObj().get(0))
					.getUpdateRecord();
		else
			return null;
	}
		@Override
	public Integer updateKpiResult(KpiResultModel kpiResultModel) {
		kpiResultModel.setServiceName(ServiceConstant.RESULT_UPDATE);
		ImakeResultMessage imakeMessage = postMessage(kpiResultModel,
				kpiResultModel.getClass().getName(), "kpiResultModel", true);
		if (imakeMessage.getResultListObj() != null
				&& imakeMessage.getResultListObj().size() > 0)
			return ((KpiResultModel) imakeMessage.getResultListObj().get(0))
					.getUpdateRecord();
		else
			return null;
	}
		@Override
	public Integer deleteKpiResult(KpiResultModel kpiResultModel) {
		kpiResultModel.setServiceName(ServiceConstant.RESULT_DELETE);
		ImakeResultMessage imakeMessage = postMessage(kpiResultModel,
				kpiResultModel.getClass().getName(), "kpiResultModel", true);
		if (imakeMessage.getResultListObj() != null
				&& imakeMessage.getResultListObj().size() > 0)
			return ((KpiResultModel) imakeMessage.getResultListObj().get(0))
					.getUpdateRecord();
		else
			return null;
	}
		@Override
	public KpiResultModel findKpiResultById(Integer KpiResultId) {
		return null;
	}
		@Override
	public List<KpiResultModel> searchKpiResult(KpiResultModel kpiResultModel) {
		kpiResultModel.setServiceName(ServiceConstant.RESULT_SEARCH);
		ImakeResultMessage imakeMessage = postMessage(kpiResultModel,kpiResultModel.getClass().getName(), "kpiResultModel", true);
		//this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
		return imakeMessage.getResultListObj();
	}
		@Override
		public KpiResultModel findKpiResultByKpi(KpiResultModel model) {
			model.setServiceName(ServiceConstant.RESULT_FIND_BY_KPI);
			ImakeResultMessage imakeMessage = postMessage(model,model.getClass().getName(), "kpiResultModel", true);
			//this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			List result =  imakeMessage.getResultListObj();
			KpiResultModel kpiResultM = (KpiResultModel) result.get(0);
			return kpiResultM;
		}
		@Override
		public KpiResultModel findKpiResultByIden(KpiResultModel model) {
			model.setServiceName(ServiceConstant.RESULT_FIND_BY_IDENTIFY);
			ImakeResultMessage imakeMessage = postMessage(model,model.getClass().getName(), "kpiResultModel", true);
			List result =  imakeMessage.getResultListObj();
			KpiResultModel kpiResultM = (KpiResultModel) result.get(0);
			return kpiResultM; 
		}
		@Override
		public List<KpiResultModel> searchKpiResultWithActiveKpi(KpiResultModel model) {
			model.setServiceName(ServiceConstant.RESULT_GET_KPI_WITH_ACTIVE);
			ImakeResultMessage imakeMessage = postMessage(model,model.getClass().getName(), "kpiResultModel", true);
			List<KpiResultModel> result = new ArrayList<KpiResultModel>();
			if( imakeMessage.getResultListObj().size()>0){
				result =  imakeMessage.getResultListObj();
			}
			return result;
		}
		
		//=====[ END: KPI RESULT |========================================================================================//
		
		
		//=====[ START: CDS RESULT |========================================================================================//		
			@Override
			public Integer saveCdsResult(CdsResultModel cdsResultModel) {
				cdsResultModel.setServiceName(ServiceConstant.CDS_RESULT_SAVE);
				ImakeResultMessage imakeMessage = postMessage(cdsResultModel,
						cdsResultModel.getClass().getName(), "cdsResultModel", true);
				if (imakeMessage.getResultListObj() != null
						&& imakeMessage.getResultListObj().size() > 0)
					return ((CdsResultModel) imakeMessage.getResultListObj().get(0))
							.getUpdateRecord();
				else
					return null;
			}
			
			public CdsResultModel findCdsResultById(Integer KpiResultId) {
				return null;
			}

			@Override
			public List<CdsResultModel> searchCdsByKpiId(CdsResultModel cdsResultModel) {
				cdsResultModel.setServiceName(ServiceConstant.CDS_USED_SEARCH_BY_KPI_ID);
				ImakeResultMessage imakeMessage = postMessage(cdsResultModel,
						cdsResultModel.getClass().getName(), "cdsResultModel", true);
				this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
				return imakeMessage.getResultListObj();
			}
			
			@Override
			public List<CdsResultModel> searchCdsResult(CdsResultModel cdsResultModel) {
				cdsResultModel.setServiceName(ServiceConstant.CDS_RESULT_SEARCH);
				ImakeResultMessage imakeMessage = postMessage(cdsResultModel,
						cdsResultModel.getClass().getName(),
						"cdsResultModel", true);
				this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
				return imakeMessage.getResultListObj();
			}
			
			@Override
			public List<CdsResultModel> getCdsWithKpi(CdsResultModel cdsResultModel) {
				cdsResultModel.setServiceName(ServiceConstant.KPI_CDS_MAP_SEACH);
				ImakeResultMessage imakeMessage = postMessage(cdsResultModel,
						cdsResultModel.getClass().getName(),
						"cdsResultModel", true);
				this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
				return imakeMessage.getResultListObj();
			}
			@Override
			public CdsResultModel findCdsResultByCds(CdsResultModel model) {
				model.setServiceName(ServiceConstant.CDS_RESULT_FIND_BY_CDS);
				ImakeResultMessage imakeMessage = postMessage(model,
						model.getClass().getName(),
						"cdsResultModel", true);
				CdsResultModel result = new CdsResultModel();
				result = (CdsResultModel) imakeMessage.getResultListObj().get(0);
				return result;
			}
		//=====[ END: CDS RESULT |========================================================================================//
			
			
			
		//=====[ START: CDS RESULT DETAIL |========================================================================================//		
			@Override
			public Integer saveCdsResultDetail(CdsResultDetailModel cdsResultDetailModel) {
				cdsResultDetailModel.setServiceName(ServiceConstant.CDS_RESULT_DETAIL_SAVE);
				ImakeResultMessage imakeMessage = postMessage(cdsResultDetailModel,
						cdsResultDetailModel.getClass().getName(), "cdsResultDetailModel", true);
				ImakeMessage msg = imakeMessage.getResultMessage();
					this.resultMessage = msg.getMsgDesc();
				if (imakeMessage.getResultListObj() != null
						&& imakeMessage.getResultListObj().size() > 0)
					return ((CdsResultDetailModel) imakeMessage.getResultListObj().get(0))
							.getUpdateRecord();
				else
					return null;
			}
			
			@Override
			public Integer updateCdsResultDetail(CdsResultDetailModel cdsResultDetailModel){
				cdsResultDetailModel.setServiceName(ServiceConstant.CDS_RESULT_DETAIL_UPDATE);
				ImakeResultMessage imakeMessage = postMessage(cdsResultDetailModel,
						cdsResultDetailModel.getClass().getName(), "cdsResultDetailModel", true);
				if (imakeMessage.getResultListObj() != null
						&& imakeMessage.getResultListObj().size() > 0)
					return ((CdsResultDetailModel) imakeMessage.getResultListObj().get(0))
							.getUpdateRecord();
				else
					return null;
			}
			
			@Override
			public CdsResultDetailModel findCdsResultDetailById(Integer CdsResultDetailId) {
				CdsResultDetailModel cdsResultDetailModel = new CdsResultDetailModel();
				cdsResultDetailModel.setResultDetailId(CdsResultDetailId);
				cdsResultDetailModel.setServiceName(ServiceConstant.CDS_RESULT_DETAIL_FIND_BY_ID);
				ImakeResultMessage imakeMessage = postMessage(cdsResultDetailModel,cdsResultDetailModel.getClass().getName(),"cdsResultDetailModel", true);
				this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
				return (CdsResultDetailModel) imakeMessage.getResultListObj().get(0);
			}

			@Override
			public CdsResultDetailModel findCdsResultDetail(CdsResultDetailModel cdsResultDetailModel ) {
				cdsResultDetailModel.setServiceName(ServiceConstant.CDS_RESULT_DETAIL_FIND_BY_MODEL);
				ImakeResultMessage imakeMessage = postMessage(cdsResultDetailModel,cdsResultDetailModel.getClass().getName(),"cdsResultDetailModel", true);
				this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
				return (CdsResultDetailModel) imakeMessage.getResultListObj().get(0);
			}
			
			@Override
			public List<CdsResultDetailModel> searchCdsResultDetail(CdsResultDetailModel cdsResultDetailModel) {
				cdsResultDetailModel.setServiceName(ServiceConstant.CDS_RESULT_DETAIL_SEARCH);
				ImakeResultMessage imakeMessage = postMessage(cdsResultDetailModel,
						cdsResultDetailModel.getClass().getName(),
						"cdsResultDetailModel", true);
				this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
				return imakeMessage.getResultListObj();
			}
		//=====[ END: CDS RESULT DETAIL |========================================================================================//
			
			
			
		//=====[ START: CDS EVIDENCE |========================================================================================//		
			@Override
			public Integer saveCdsEvidence(CdsEvidenceModel cdsEvidenceModel) {
				cdsEvidenceModel.setServiceName(ServiceConstant.CDS_EVIDENCE_SAVE);
				ImakeResultMessage imakeMessage = postMessage(cdsEvidenceModel,
						cdsEvidenceModel.getClass().getName(), "cdsEvidenceModel", true);
				if (imakeMessage.getResultListObj() != null
						&& imakeMessage.getResultListObj().size() > 0)
					return ((CdsEvidenceModel) imakeMessage.getResultListObj().get(0))
							.getUpdateRecord();
				else
					return null;
			}
			
			@Override
			public Integer deleteCdsEvidence(CdsEvidenceModel cdsEvidenceModel){
				cdsEvidenceModel.setServiceName(ServiceConstant.CDS_EVIDENCE_DELETE);
				ImakeResultMessage imakeMessage = postMessage(cdsEvidenceModel,
						cdsEvidenceModel.getClass().getName(), "cdsEvidenceModel", true);
				if (imakeMessage.getResultListObj() != null
						&& imakeMessage.getResultListObj().size() > 0)
					return ((CdsEvidenceModel) imakeMessage.getResultListObj().get(0))
							.getUpdateRecord();
				else
					return null;
			}
			
			@Override
			public CdsEvidenceModel findCdsEvidenceById(Integer evidenceId) {
				CdsEvidenceModel result =  new CdsEvidenceModel();
				CdsEvidenceModel evidence = new CdsEvidenceModel();
				evidence.setEvidenceId(evidenceId);
				evidence.setServiceName(ServiceConstant.CDS_EVIDENCE_FIND_BY_ID);
				ImakeResultMessage imakeMessage = postMessage(evidence,evidence.getClass().getName(), "cdsEvidenceModel", true);
				if(imakeMessage!=null){
					 result = (CdsEvidenceModel) imakeMessage.getResultListObj().get(0);
				}
				return result;
			}

			@Override
			public List<CdsEvidenceModel> searchCdsEvidence(
					CdsEvidenceModel cdsEvidenceModel) {
				cdsEvidenceModel.setServiceName(ServiceConstant.CDS_EVIDENCE_SEARCH);
				ImakeResultMessage imakeMessage = postMessage(cdsEvidenceModel,
						cdsEvidenceModel.getClass().getName(), "cdsEvidenceModel", true);
				this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
				return imakeMessage.getResultListObj();
			}
		//=====[ END: CDS EVIDENCE |========================================================================================//
	
			@Override
			public Integer saveResultQuantity(CdsResultModel model) {
				model.setServiceName(ServiceConstant.CDS_EVIDENCE_SAVE);
				ImakeResultMessage imakeMessage = postMessage(model,model.getClass().getName(), "cdsResultModel", true);
				if (imakeMessage.getResultListObj() != null
						&& imakeMessage.getResultListObj().size() > 0)
					return ((CdsEvidenceModel) imakeMessage.getResultListObj().get(0))
							.getUpdateRecord();
				else
					return 0;
			}
			
			// ######### ????????????????????   manual  depend on ER
			@SuppressWarnings("unchecked")
			@Override
			public List<KpiResultDetailModel> findCriteriaResult(KpiResultDetailModel model){
				model.setServiceName(ServiceConstant.CRITERIA_EXIST_RESULT_QUALITY);
				ImakeResultMessage imakeMessage = postMessage(model,model.getClass().getName(), "kpiResultDetailModel", true);
				List<KpiResultDetailModel> ret = new ArrayList<KpiResultDetailModel>();
				if (imakeMessage!= null ){
					ret =   imakeMessage.getResultListObj();
				}
				return ret;
			}
			@Override
			public KpiResultDetailModel findKpiResultDetailById(KpiResultDetailModel model) {
				model.setServiceName(ServiceConstant.KPI_RESULT_DETAIL_FIND_BY_ID);
				ImakeResultMessage imakeMessage = postMessage(model,model.getClass().getName(), "kpiResultDetailModel", true);
				KpiResultDetailModel ret = new KpiResultDetailModel();
				if (imakeMessage.getResultListObj().size()>0){
					ret =   (KpiResultDetailModel) imakeMessage.getResultListObj().get(0);
				}
				return ret;
			}
			@Override
			public KpiResultDetailModel findKpiResultDetailByIdentify(	KpiResultDetailModel model) {
				model.setServiceName(ServiceConstant.KPI_RESULT_DETAIL_FIND_BY_IDENTIFY);
				ImakeResultMessage imakeMessage = postMessage(model,model.getClass().getName(), "kpiResultDetailModel", true);
				KpiResultDetailModel ret = new KpiResultDetailModel();
				if (imakeMessage.getResultListObj().size()>0){
					ret =   (KpiResultDetailModel) imakeMessage.getResultListObj().get(0);
				}
				return ret;
			}
			@Override
			public Integer saveKpiResultDetail(KpiResultDetailModel model) {
				model.setServiceName(ServiceConstant.RESULT_DETAIL_QUALITY_SAVE);
				ImakeResultMessage imakeMessage = postMessage(model,
						model.getClass().getName(), "kpiResultDetailModel", true);
				if (imakeMessage.getResultListObj() != null
						&& imakeMessage.getResultListObj().size() > 0)
					return ((KpiResultDetailModel) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
				else
					return null;
			}
			@Override
			public Integer updateKpiResultDetailEvidence(KpiResultDetailModel model) {
				model.setServiceName(ServiceConstant.RESULT_DETAIL_QUALITY_UPDATE_EVIDENCE);
				ImakeResultMessage imakeMessage = postMessage(model,
						model.getClass().getName(), "kpiResultDetailModel", true);
				if (imakeMessage.getResultListObj() != null
						&& imakeMessage.getResultListObj().size() > 0)
					return ((KpiResultDetailModel) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
				else
					return null;
			}
			
			// ############ kpiEvidence #############
			@Override
			public Integer saveKpiEvidence(KpiEvidenceModel model) {
				model.setServiceName(ServiceConstant.KPI_EVIDENCE_SAVE);
				ImakeResultMessage imakeMessage = postMessage(model,
						model.getClass().getName(), "kpiEvidenceModel", true);
				if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0)
					return ((KpiEvidenceModel) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
				else
					return 0;
			}
			@Override
			public Integer deleteKpiEvidence(KpiEvidenceModel model) {
				model.setServiceName(ServiceConstant.KPI_EVIDENCE_DELETE);
				ImakeResultMessage imakeMessage = postMessage(model,
						model.getClass().getName(), "kpiEvidenceModel", true);
				if (imakeMessage.getResultListObj() != null
						&& imakeMessage.getResultListObj().size() > 0)
					return ((KpiEvidenceModel) imakeMessage.getResultListObj().get(0)).getUpdateRecord();
				else
					return 0;
			}
			@SuppressWarnings("unchecked")
			@Override
			public List<KpiEvidenceModel> searchKpiEvidence(KpiEvidenceModel model) {
				List<KpiEvidenceModel> rets = new ArrayList<KpiEvidenceModel>();
				model.setServiceName(ServiceConstant.KPI_EVIDENCE_SEARCH);
				ImakeResultMessage imakeMessage = postMessage(model,model.getClass().getName(), "kpiEvidenceModel", true);
				if (imakeMessage.getResultListObj() != null && imakeMessage.getResultListObj().size() > 0){
					rets = imakeMessage.getResultListObj();
				}
				return rets;
			}
			
			
			
			
		
		//############## target
		@Override
		public List getKpiTarget(KpiTargetModel targetModel){
			targetModel.setServiceName(ServiceConstant.TARGET_GET_LIST);
			ImakeResultMessage imakeMessage = postMessage(targetModel,targetModel.getClass().getName(), "kpiTargetModel", true);
			//this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		@Override
		public Integer saveKpiTarget(KpiTargetModel targetModel) {
			targetModel.setServiceName(ServiceConstant.TARGET_SAVE_LIST);
			ImakeResultMessage imakeMessage = postMessage(targetModel,targetModel.getClass().getName(), "kpiTargetModel", true);
			this.resultMessage = imakeMessage.getResultMessage().getMsgDesc();
			return ((KpiTargetModel) imakeMessage.getResultListObj().get(0)).getTotalSuccess();
		}
		//desc
		public DescriptionModel getOrgOfUser(DescriptionModel Model) {
			Model.setServiceName(ServiceConstant.DESC_USER_ORG);
			ImakeResultMessage imakeMessage = postMessage(Model,Model.getClass().getName(), "descriptionModel", true);
			DescriptionModel reDM = new DescriptionModel();
			try{
				reDM = (DescriptionModel)imakeMessage.getResultListObj().get(0);
			}catch(Exception ex){
				reDM = new DescriptionModel();
			}
			return reDM;
		}
		public List getPeriods(DescriptionModel Model){
			Model.setServiceName(ServiceConstant.DESC_PERIOD);
			ImakeResultMessage imakeMessage = postMessage(Model,Model.getClass().getName(), "descriptionModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		public List getUoms(DescriptionModel Model){
			Model.setServiceName(ServiceConstant.DESC_UOM);
			ImakeResultMessage imakeMessage = postMessage(Model,	Model.getClass().getName(), "descriptionModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		public List getCalendarTypes(DescriptionModel Model ){
			Model.setServiceName(ServiceConstant.DESC_CALENDAR);
			ImakeResultMessage imakeMessage = postMessage(Model,		Model.getClass().getName(), "descriptionModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		public List getCriTypes(DescriptionModel Model){
			Model.setServiceName(ServiceConstant.DESC_CRITERIA_TYPE);
			ImakeResultMessage imakeMessage = postMessage(Model,		Model.getClass().getName(), "descriptionModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		public List getCriMethods(DescriptionModel Model){
			Model.setServiceName(ServiceConstant.DESC_CRITERIA_METHOD);
			ImakeResultMessage imakeMessage = postMessage(Model,		Model.getClass().getName(), "descriptionModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		@Override
		public List getKpiNameAll(DescriptionModel Model) {
			Model.setServiceName(ServiceConstant.DESC_KPI);
			ImakeResultMessage imakeMessage = postMessage(Model,	Model.getClass().getName(), "descriptionModel", true);
			this.resultPage = Integer.parseInt(imakeMessage.getLastpage());
			return imakeMessage.getResultListObj();
		}
		@Override
		public List getUniversityAll(DescriptionModel Model) {
			Model.setServiceName(ServiceConstant.DESC_UNIVERSITY);
			ImakeResultMessage imakeMessage = postMessage(Model,	Model.getClass().getName(), "descriptionModel", true);
			
			return imakeMessage.getResultListObj();
		}
		@Override
		public List getFacultyAll(DescriptionModel Model) {
			Model.setServiceName(ServiceConstant.DESC_FACULTY);
			ImakeResultMessage imakeMessage = postMessage(Model,	Model.getClass().getName(), "descriptionModel", true);
			return imakeMessage.getResultListObj();
		}
		@Override
		public List getCourseAll(DescriptionModel Model) {
			Model.setServiceName(ServiceConstant.DESC_COURSE);
			ImakeResultMessage imakeMessage = postMessage(Model,	Model.getClass().getName(), "descriptionModel", true);
			return imakeMessage.getResultListObj();
		}
		@Override
		public List getMonthAll(DescriptionModel Model) {
			Model.setServiceName(ServiceConstant.DESC_MONTH);
			ImakeResultMessage imakeMessage = postMessage(Model,	Model.getClass().getName(), "descriptionModel", true);
			return imakeMessage.getResultListObj();
		}
		@Override
		public ResultService saveResultOfOrg(KpiResultModel kpiResultM) {
			kpiResultM.setServiceName(ServiceConstant.RESULT_INSERTS);
			ImakeResultMessage imakeMessage =postMessage(kpiResultM,kpiResultM.getClass().getName(),"kpiResultModel",true);
			ImakeMessage msg = imakeMessage.getResultMessage();
			ResultService rs = new ResultService();
			rs.setMsgCode(msg.getMsgCode());
			rs.setMsgDesc(msg.getMsgDesc());
			if(rs.getMsgCode().equals("100")){
				rs.setError(false);
				rs.setResultInt( ((KpiResultModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord() );
			}else{
				rs.setError(true);
			}
			return rs;
		}
		@Override
		public ResultService deleteResultByOrg(KpiResultModel kpiResultM) {
			kpiResultM.setServiceName(ServiceConstant.RESULT_DELETE_BY_ORG);
			ImakeResultMessage imakeMessage =postMessage(kpiResultM,kpiResultM.getClass().getName(),"kpiResultModel",true);
			ImakeMessage msg = imakeMessage.getResultMessage();
			ResultService rs = new ResultService();
			rs.setMsgCode(msg.getMsgCode());
			rs.setMsgDesc(msg.getMsgDesc());
			if(rs.getMsgCode().equals("100")){
				rs.setError(false);
				rs.setResultInt( ((KpiResultModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord() );
			}else{
				rs.setError(true);
			}
			return rs;
		}
		@Override
		public Integer generateSysMonthBySysYear(SysYearModel model) {
			model.setServiceName(ServiceConstant.SYSYEAR_CREATE_SYSMONTH);
			ImakeResultMessage imakeMessage =postMessage(model,model.getClass().getName(),"sysYearModel",true);
			if(imakeMessage.getResultListObj()!=null && imakeMessage.getResultListObj().size()>0)
				return ((SysYearModel)imakeMessage.getResultListObj().get(0)).getUpdateRecord();
			else
				return null;
		}
		
}
