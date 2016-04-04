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
import th.ac.chandra.eduqa.domain.CdsEvidence;
import th.ac.chandra.eduqa.model.CdsEvidenceModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class CdsEvidenceResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public CdsEvidenceResource() {
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
			xstream.processAnnotations(CdsEvidenceModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			CdsEvidenceModel xsource = new CdsEvidenceModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (CdsEvidenceModel) xtarget;
				if (xsource != null) {
					CdsEvidence domain = new CdsEvidence();
					BeanUtils.copyProperties(xsource,domain); 
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.CDS_EVIDENCE_FIND_BY_ID)){
						domain = service.findCdsEvidenceById(xsource.getEvidenceId());
							if(domain!=null){
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<CdsEvidenceModel> models = new ArrayList<CdsEvidenceModel>(1);
								CdsEvidenceModel model = new CdsEvidenceModel();
								BeanUtils.copyProperties(domain,model);	
								model.setPagging(null);
								models.add(model);
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.CDS_EVIDENCE_SAVE)){
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							domain.setCreatedDate(now);
							domain.setUpdatedDate(now);
							domain.setAcademicYear(2558);
							int updateRecord=service.saveCdsEvidence(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.CDS_EVIDENCE_SEARCH)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchCdsEvidence(domain, page);
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsEvidence> domains = (java.util.ArrayList<CdsEvidence>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsEvidenceModel> models = new ArrayList<CdsEvidenceModel>();
								if (domains_size != null && domains_size.length()!=0)
									imakeMessage.setMaxRow(domains_size);
								if (domains != null && domains.size() > 0) {
									models = getModels(domains);
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
								
								/*String meta = (String) result.get(1);  
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<CdsEvidenceModel> models = (ArrayList<CdsEvidenceModel>) result.get(0); //get dataList
								imakeMessage.setMaxRow(meta);
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);*/
							}
						}
						else if(serviceName.equals(ServiceConstant.CDS_EVIDENCE_DELETE)){
							//int updateRecord=service.deleteKpiReComnd(domain);
							//return returnUpdateRecord(entity,xsource,updateRecord);
							int updateRecord=0;
							try{
								updateRecord=service.deleteCdsEvidence(domain);								
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
						/*else if(serviceName.equals(ServiceConstant.ORG_SEARCH_BY_LEVEL)){
							Paging page = xsource.getPaging(); 
							d@SuppressWarnings("rawtypes")
							List result = (List) service.searchCdsEvidencetByLevelId(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsEvidencet> domains = (java.util.ArrayList<CdsEvidencet>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsEvidenceModel> models = new ArrayList<CdsEvidenceModel>();
								if (domains_size != null && domains_size.length()!=0)
									imakeMessage.setMaxRow(domains_size);
								if (domains != null && domains.size() > 0) {
									models = getModels(domains);
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.ORG_SEARCH_GROUPBY_COURSE)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchCdsEvidencetGroupByCourseCode(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsEvidencet> domains = (java.util.ArrayList<CdsEvidencet>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsEvidenceModel> models = new ArrayList<CdsEvidenceModel>();
								if (domains_size != null && domains_size.length()!=0)
									imakeMessage.setMaxRow(domains_size);
								if (domains != null && domains.size() > 0) {
									models = getModels(domains);
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.ORG_SEARCH_BY_FACULTY)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchCdsEvidencetByFacultyCode(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsEvidencet> domains = (java.util.ArrayList<CdsEvidencet>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsEvidenceModel> models = new ArrayList<CdsEvidenceModel>();
								if (domains_size != null && domains_size.length()!=0)
									imakeMessage.setMaxRow(domains_size);
								if (domains != null && domains.size() > 0) {
									models = getModels(domains);
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.ORG_SEARCH_BY_COURSE)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchCdsEvidencetIdByOthersCode(domain, page,
									xsource.getKeySearch(), xsource.getOtherKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsEvidencet> domains = (java.util.ArrayList<CdsEvidencet>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsEvidenceModel> models = new ArrayList<CdsEvidenceModel>();
								if (domains_size != null && domains_size.length()!=0)
									imakeMessage.setMaxRow(domains_size);
								if (domains != null && domains.size() > 0) {
									models = getModels(domains);
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}*/
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
	private List<CdsEvidenceModel> getModels(	java.util.ArrayList<CdsEvidence> domains) {
		List<CdsEvidenceModel> models = new ArrayList<CdsEvidenceModel>(domains.size());
		for (CdsEvidence domain : domains) {
			CdsEvidenceModel model =new CdsEvidenceModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,CdsEvidenceModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<CdsEvidenceModel> xsources = new ArrayList<CdsEvidenceModel>(1);
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
