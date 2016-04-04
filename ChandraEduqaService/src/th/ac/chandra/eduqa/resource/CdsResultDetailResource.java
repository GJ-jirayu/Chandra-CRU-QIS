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
import th.ac.chandra.eduqa.domain.CdsResult;
import th.ac.chandra.eduqa.domain.CdsResultDetail;
import th.ac.chandra.eduqa.model.CdsResultDetailModel;
import th.ac.chandra.eduqa.model.KpiStrucModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeMessage;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class CdsResultDetailResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public CdsResultDetailResource() {
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
			xstream.processAnnotations(CdsResultDetailModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			CdsResultDetailModel xsource = new CdsResultDetailModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (CdsResultDetailModel) xtarget;
				if (xsource != null) {
					CdsResultDetail domain = new CdsResultDetail();
					BeanUtils.copyProperties(xsource,domain); 
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.CDS_RESULT_DETAIL_FIND_BY_ID)){
						System.out.print("id:"+xsource.getResultDetailId());
						domain = service.findCdsResultDetailById(xsource.getResultDetailId());
							if(domain!=null){
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<CdsResultDetailModel> models = new ArrayList<CdsResultDetailModel>(1);
								CdsResultDetailModel model = new CdsResultDetailModel();
								BeanUtils.copyProperties(domain,model);	
								model.setPagging(null);
								models.add(model);
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else 	if(serviceName.equals(ServiceConstant.CDS_RESULT_DETAIL_FIND_BY_MODEL)){
							domain = service.findCdsResultDetail(xsource);
							if(domain!=null){
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<CdsResultDetailModel> models = new ArrayList<CdsResultDetailModel>(1);
								CdsResultDetailModel model = new CdsResultDetailModel();
								BeanUtils.copyProperties(domain,model);	
								model.setPagging(null);
								models.add(model);
								imakeMessage.setResultListObj(models);
								imakeMessage.setMaxRow("1");
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.CDS_RESULT_DETAIL_SAVE)){
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							domain.setCreatedDate(now);
							domain.setUpdatedDate(now);
							domain.setAcademicYear(2558);
							int updateRecord=service.saveCdsResultDetail(domain);
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							ImakeMessage msg = new ImakeMessage();
							if(updateRecord>0){
								msg.setMsgDesc("บันทึกสำเร็จ");
								imakeMessage.setResultMessage(msg);
							}
							List<CdsResultDetailModel> xsources = new ArrayList<CdsResultDetailModel>(1);
							xsource.setUpdateRecord(updateRecord);
							xsources.add(xsource);
							imakeMessage.setResultListObj(xsources);
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.CDS_RESULT_DETAIL_UPDATE)){
							java.sql.Timestamp updatedDate = new java.sql.Timestamp(new Date().getTime());
							domain.setUpdatedDate(updatedDate);
							int updateRecord=service.updateCdsResultDetail(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						
						else if(serviceName.equals(ServiceConstant.CDS_RESULT_DETAIL_SEARCH)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchCdsResultDetail(domain, page,xsource.getKeySearch(), xsource.getOtherKeySearch());
							if (result != null && result.size() == 2) {
								/*java.util.ArrayList<CdsResultDetail> domains = (java.util.ArrayList<CdsResultDetail>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsResultDetailModel> models = new ArrayList<CdsResultDetailModel>();
								if (domains_size != null && domains_size.length()!=0)
									imakeMessage.setMaxRow(domains_size);
								if (domains != null && domains.size() > 0) {
									models = getModels(domains);
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);*/		
								String meta = (String) result.get(1);  
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<CdsResultDetailModel> models = (ArrayList<CdsResultDetailModel>) result.get(0); //get dataList
								imakeMessage.setMaxRow(meta);
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						/*else if(serviceName.equals(ServiceConstant.ORG_SEARCH_BY_LEVEL)){
							Paging page = xsource.getPaging(); 
							d@SuppressWarnings("rawtypes")
							List result = (List) service.searchCdsResultDetailtByLevelId(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsResultDetailt> domains = (java.util.ArrayList<CdsResultDetailt>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsResultDetailModel> models = new ArrayList<CdsResultDetailModel>();
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
							List result = (List) service.searchCdsResultDetailtGroupByCourseCode(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsResultDetailt> domains = (java.util.ArrayList<CdsResultDetailt>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsResultDetailModel> models = new ArrayList<CdsResultDetailModel>();
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
							List result = (List) service.searchCdsResultDetailtByFacultyCode(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsResultDetailt> domains = (java.util.ArrayList<CdsResultDetailt>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsResultDetailModel> models = new ArrayList<CdsResultDetailModel>();
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
							List result = (List) service.searchCdsResultDetailtIdByOthersCode(domain, page,
									xsource.getKeySearch(), xsource.getOtherKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsResultDetailt> domains = (java.util.ArrayList<CdsResultDetailt>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsResultDetailModel> models = new ArrayList<CdsResultDetailModel>();
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
	private List<CdsResultDetailModel> getModels(	java.util.ArrayList<CdsResultDetail> domains) {
		List<CdsResultDetailModel> models = new ArrayList<CdsResultDetailModel>(domains.size());
		for (CdsResultDetail domain : domains) {
			CdsResultDetailModel model =new CdsResultDetailModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,CdsResultDetailModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<CdsResultDetailModel> xsources = new ArrayList<CdsResultDetailModel>(1);
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
