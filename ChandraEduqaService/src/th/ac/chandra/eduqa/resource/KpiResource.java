package th.ac.chandra.eduqa.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;

import th.ac.chandra.eduqa.constant.ServiceConstant;
import th.ac.chandra.eduqa.domain.CriteriaStandard;
import th.ac.chandra.eduqa.domain.Kpi;
import th.ac.chandra.eduqa.domain.KpiXCds;
import th.ac.chandra.eduqa.domain.SysYear;
import th.ac.chandra.eduqa.model.CdsModel;
import th.ac.chandra.eduqa.model.KpiModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeMessage;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class KpiResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);  
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public KpiResource() {
		super();
	}

	@Override
	protected void doInit() throws ResourceException {
		// TODO Auto-generated method stub
		super.doInit();
		logger.debug("into doInit");
	}
	
	@SuppressWarnings("finally")
	@Override
	protected Representation post(Representation entity, Variant variant)
			throws ResourceException {
		//logger.debug("into Post ConsultantReportResource 2");
		InputStream in = null;
		try {
			in = entity.getStream();
			xstream.processAnnotations(KpiModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			KpiModel xsource = new KpiModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (KpiModel) xtarget;
				if (xsource != null) {
					Kpi domain = new Kpi();
					BeanUtils.copyProperties(xsource,domain); 
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.KPI_FIND_BY_ID)){
							domain = service.findKpiById(xsource.getKpiId());
							if(domain!=null){
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<KpiModel> models = new ArrayList<KpiModel>(1);
								KpiModel model = new KpiModel();
								BeanUtils.copyProperties(domain,model);	
								model.setPagging(null);
								models.add(model);
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.KPI_FIND_DETAIL_BY_ID)){
							KpiModel kpiModel = service.findKpiWithDescById(xsource);
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List<KpiModel> models = new ArrayList<KpiModel>();
							models.add(kpiModel);
							imakeMessage.setResultListObj(models);
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.KPI_SAVE)){
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							domain.setCreatedDate(now);
							domain.setUpdatedDate(now);
							int updateRecord=service.saveKpi(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.KPI_SAVE_FORMULA)){
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							domain.setCreatedDate(now);
							domain.setUpdatedDate(now);
							SysYear sy = service.getSysYear();
							domain.setAcademicYear(sy.getMasterAcademicYear());
							Integer updateRecord = service.saveKpiFormula(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.KPI_UPDATE)){
							java.sql.Timestamp updatedDate = new java.sql.Timestamp(new Date().getTime());
							domain.setCreatedDate(updatedDate);
							domain.setUpdatedDate(updatedDate);
							int updateRecord=service.updateKpi(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.KPI_DELETE)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							ImakeMessage msg = new ImakeMessage();
							Integer updateRecord = null;
							try{
								updateRecord=service.deleteKpi(domain);
								msg.setMsgDesc("ลบสำเร็จ");
							} catch(PersistenceException e){
								Throwable t = e.getCause();
								msg.setMsgDesc(t.getCause().getMessage());
							}
							imakeMessage.setResultMessage(msg);
							KpiModel model = new KpiModel();
							model.setUpdateRecord(updateRecord);
							List<KpiModel> models = new ArrayList<KpiModel>();
							models.add(model);
							imakeMessage.setResultListObj(models);
							return getRepresentation(entity, imakeMessage, xstream);		
							
						}
						else if(serviceName.equals(ServiceConstant.KPI_SEARCH)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List result = (List) service.searchKpi(domain, page,xsource.getKeySearch());
							if (result != null) {
								ArrayList<KpiModel> kpis = (ArrayList<KpiModel>) result.get(0); //get dataList
								String meta = (String) result.get(1);  
								imakeMessage.setMaxRow((String) result.get(1));
								imakeMessage.setResultListObj(kpis);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.KPI_CDS_MAPPING_DELETE)){
							KpiXCds std = new KpiXCds();
							std.setKpiId(xsource.getKpiId());
							Integer updateRecord = service.deleteKpiXCds(std);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
					} 
					else {
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
	private List<KpiModel> getModels(	java.util.ArrayList<Kpi> domains) {
		List<KpiModel> models = new ArrayList<KpiModel>(domains.size());
		for (Kpi domain : domains) {
			KpiModel model =new KpiModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,KpiModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<KpiModel> xsources = new ArrayList<KpiModel>(1);
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
