package th.ac.chandra.eduqa.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import th.ac.chandra.eduqa.constant.ServiceConstant;
import th.ac.chandra.eduqa.domain.KpiReComnd;
import th.ac.chandra.eduqa.model.KpiReComndModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class KpiReComndResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public KpiReComndResource() {
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
			xstream.processAnnotations(KpiReComndModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			KpiReComndModel xsource = new KpiReComndModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (KpiReComndModel) xtarget;
				if (xsource != null) {
					KpiReComnd domain = new KpiReComnd();
					BeanUtils.copyProperties(xsource,domain); 
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.RECOMND_FIND_BY_ID)){
							domain = service.findKpiReComndById(xsource.getReComndId());
							if(domain!=null){
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<KpiReComndModel> models = new ArrayList<KpiReComndModel>(1);
								KpiReComndModel model = new KpiReComndModel();
								BeanUtils.copyProperties(domain,model);	
								model.setPagging(null);
								models.add(model);
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.RECOMND_SAVE)){
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							domain.setCreatedDate(now);
							domain.setUpdatedDate(now);
							int updateRecord=service.saveKpiReComnd(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.RECOMND_UPDATE)){
							java.sql.Timestamp updatedDate = new java.sql.Timestamp(new Date().getTime());
							domain.setUpdatedDate(updatedDate);
							int updateRecord=service.updateKpiReComnd(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						
						else if(serviceName.equals(ServiceConstant.RECOMND_DELETE)){
							//int updateRecord=service.deleteKpiReComnd(domain);
							//return returnUpdateRecord(entity,xsource,updateRecord);
							int updateRecord=0;
							try{
								updateRecord=service.deleteKpiReComnd(domain);								
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
						
						else if(serviceName.equals(ServiceConstant.RECOMND_SEARCH)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchKpiReComnd(domain, page,xsource.getKeySearch(),xsource.getOtherKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<KpiReComnd> domains = (java.util.ArrayList<KpiReComnd>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<KpiReComndModel> models = new ArrayList<KpiReComndModel>();
								if (domains_size != null && domains_size.length()!=0)
									imakeMessage.setMaxRow(domains_size);
								if (domains != null && domains.size() > 0) {
									models = getModels(domains);
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
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
	private List<KpiReComndModel> getModels(	java.util.ArrayList<KpiReComnd> domains) {
		List<KpiReComndModel> models = new ArrayList<KpiReComndModel>(domains.size());
		for (KpiReComnd domain : domains) {
			KpiReComndModel model =new KpiReComndModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,KpiReComndModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<KpiReComndModel> xsources = new ArrayList<KpiReComndModel>(1);
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
