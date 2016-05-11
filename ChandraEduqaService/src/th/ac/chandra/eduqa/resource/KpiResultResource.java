package th.ac.chandra.eduqa.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import th.ac.chandra.eduqa.constant.ServiceConstant;
import th.ac.chandra.eduqa.domain.KpiResult;
import th.ac.chandra.eduqa.domain.KpiXCds;
import th.ac.chandra.eduqa.domain.SysYear;
import th.ac.chandra.eduqa.model.KpiResultModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeMessage;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class KpiResultResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public KpiResultResource() {
		super();
	}

	@Override
	protected void doInit() throws ResourceException {
		// TODO Auto-generated method stub
		super.doInit();
		logger.debug("into doInit");
	}
	
	@Override
	protected Representation post(Representation entity, Variant variant)
			throws ResourceException {
		//logger.debug("into Post ConsultantReportResource 2");
		InputStream in = null;
		try {
			in = entity.getStream();
			xstream.processAnnotations(KpiResultModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			KpiResultModel xsource = new KpiResultModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (KpiResultModel) xtarget;
				if (xsource != null) {
					KpiResult domain = new KpiResult();
					BeanUtils.copyProperties(xsource,domain); 
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						
						if(serviceName.equals(ServiceConstant.RESULT_FIND_BY_ID)){
							domain = service.findKpiResultById(xsource.getResultId());
							if(domain!=null){
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<KpiResultModel> models = new ArrayList<KpiResultModel>(1);
								KpiResultModel model = new KpiResultModel();
								BeanUtils.copyProperties(domain,model);	
								model.setPagging(null);
								models.add(model);
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.RESULT_SAVE)){
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							domain.setCreatedDate(now);
							domain.setUpdatedDate(now);
							int updateRecord=service.saveKpiResult(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.RESULT_UPDATE)){
							java.sql.Timestamp updatedDate = new java.sql.Timestamp(new Date().getTime());
							domain.setUpdatedDate(updatedDate);
							int updateRecord=service.updateKpiResult(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						
						else if(serviceName.equals(ServiceConstant.RESULT_DELETE)){
							//int updateRecord=service.deleteKpiResult(domain);
							//return returnUpdateRecord(entity,xsource,updateRecord);
							int updateRecord=0;
							try{
								updateRecord=service.deleteKpiResult(domain);								
							}catch(Exception e){
								Throwable t = e.getCause();
							    while ((t != null) && !(t instanceof ConstraintViolationException)) {
							        t = t.getCause();
							    }							  
							    if (t instanceof ConstraintViolationException) {
							    	updateRecord=-9;
							    }
							}
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.RESULT_SEARCH)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchKpiResult(domain, page,xsource.getKeySearch(),xsource.getOtherKeySearch());
							if (result != null && result.size() == 2) {
								List<KpiResultModel> models = (List<KpiResultModel>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								imakeMessage.setMaxRow(domains_size);
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.RESULT_FIND_BY_KPI)){
							KpiResult result = service.findKpiResultByKpi(domain);
								List<KpiResultModel> models = new ArrayList<KpiResultModel>();
								KpiResultModel kpiResultM = new KpiResultModel();
								BeanUtils.copyProperties(result,kpiResultM); 
								models.add(kpiResultM);
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.RESULT_INSERTS)){
							//for assign
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							xsource.setCreatedDate(now);
							xsource.setUpdatedDate(now);
							SysYear sy = service.getSysYear();
							xsource.setAcademicYear(sy.getAppraisalAcademicYear());
							ImakeResultMessage resultMessage = new ImakeResultMessage();
							ImakeMessage msg = new ImakeMessage();
							Integer updateRecord = 0;
							try{
							updateRecord=service.insertsKpiResult(xsource);
							msg.setMsgCode("100");
							msg.setMsgDesc(ServiceConstant.MESSAGE_SAVE_SUCCESS);
							}catch(PersistenceException e){
								Throwable t = e.getCause();
								msg.setError(true);
								msg.setMsgCode("200");
								msg.setMsgDesc( t.getCause().getMessage());
							}
							List<KpiResultModel> models = new ArrayList<KpiResultModel>();
							KpiResultModel kr = new KpiResultModel();
							kr.setUpdateRecord(updateRecord);
							models.add(kr);
							resultMessage.setResultListObj(models);
							resultMessage.setResultMessage(msg);
							return getRepresentation(entity, resultMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.RESULT_DELETE_BY_ORG)){
							//for reassign
							ImakeResultMessage resultMessage = new ImakeResultMessage();
							ImakeMessage msg = new ImakeMessage();
							Integer updateRecord = 0;
							try{
							SysYear config = service.getSysYear();
							xsource.setAcademicYear(config.getAppraisalAcademicYear());
							updateRecord=service.deleteKpiResultByOrgId(xsource);
							msg.setMsgCode("100");
							msg.setMsgDesc(ServiceConstant.MESSAGE_DELETE_SUCCESS);
							}catch(PersistenceException e){
								Throwable t = e.getCause();
								msg.setError(true);
								msg.setMsgCode("200");
								msg.setMsgDesc( t.getCause().getMessage());
							}
							List<KpiResultModel> models = new ArrayList<KpiResultModel>();
							KpiResultModel kr = new KpiResultModel();
							kr.setUpdateRecord(updateRecord);
							models.add(kr);
							resultMessage.setResultListObj(models);
							resultMessage.setResultMessage(msg);
							return getRepresentation(entity, resultMessage, xstream);
						}
						// 20-10-2558
						else if(serviceName.equals(ServiceConstant.RESULT_FIND_BY_IDENTIFY)){
								KpiResultModel result = service.findKpiResultByIdentify(xsource);
								List<KpiResultModel> models = new ArrayList<KpiResultModel>();
								models.add(result);
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.RESULT_GET_KPI_WITH_ACTIVE)){
							SysYear config = service.getSysYear();
							xsource.setAcademicYear(config.getAppraisalAcademicYear());
							List<KpiResultModel> models = service.searchKpiResultWithActiveKpi(xsource);
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							imakeMessage.setResultListObj(models);
							return getRepresentation(entity, imakeMessage, xstream);
						}
						
						else if(serviceName.equals(ServiceConstant.KPI_RESULT_DELETE_BY_KPIID)){
							KpiResultModel kpiResultModel = new KpiResultModel();
							kpiResultModel.setKpiId(xsource.getKpiId());
							Integer updateRecord = service.deleteKpiResultByKpiId(kpiResultModel);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						
						else if(serviceName.equals(ServiceConstant.RANGE_BASELINE_DELETE_BY_KPIID)){
							KpiResultModel kpiResultModel = new KpiResultModel();
							kpiResultModel.setKpiId(xsource.getKpiId());
							Integer updateRecord = service.deleteRangeBaselineByKpiId(kpiResultModel);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						
					} else {
					}
					// end serviceName case handle
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			logger.debug(" into Finally Call");
			try {
				if (in != null)
					in.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	
	}
	private List<KpiResultModel> getModels(	java.util.ArrayList<KpiResult> domains) {
		List<KpiResultModel> models = new ArrayList<KpiResultModel>(domains.size());
		for (KpiResult domain : domains) {
			KpiResultModel model =new KpiResultModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,KpiResultModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<KpiResultModel> xsources = new ArrayList<KpiResultModel>(1);
		model.setUpdateRecord(updateRecord);
		xsources.add(model);
		imakeMessage.setResultListObj(xsources);
		return getRepresentation(entity, imakeMessage, xstream);
	}
 
	@Override
	protected Representation get(Variant variant) throws ResourceException {
		//System.out.println("aoe->"+service.listJournalPaperAll());
		 return null;
	} 
	
	
	public com.thoughtworks.xstream.XStream getXstream() {
		return xstream;
	}

	public void setXstream(com.thoughtworks.xstream.XStream xstream) {
		this.xstream = xstream;
	}

}
