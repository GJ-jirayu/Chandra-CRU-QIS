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
import th.ac.chandra.eduqa.model.CdsModel;
import th.ac.chandra.eduqa.model.CdsResultModel;
import th.ac.chandra.eduqa.model.KpiStrucModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class CdsResultResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public CdsResultResource() {
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
			xstream.processAnnotations(CdsResultModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			CdsResultModel xsource = new CdsResultModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (CdsResultModel) xtarget;
				if (xsource != null) {
					CdsResult domain = new CdsResult();
					BeanUtils.copyProperties(xsource,domain); 
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.CDS_FIND_BY_ID)){
							domain = service.findCdsResultById(xsource.getCdsId());
							if(domain!=null){
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<CdsResultModel> models = new ArrayList<CdsResultModel>(1);
								CdsResultModel model = new CdsResultModel();
								BeanUtils.copyProperties(domain,model);	
								model.setPagging(null);
								models.add(model);
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.CDS_RESULT_SAVE)){
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							domain.setCreatedDate(now);
							domain.setUpdatedDate(now);
							domain.setAcademicYear(2558);
							int updateRecord=service.saveCdsResult(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.CDS_USED_SEARCH_BY_KPI_ID)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchCdsUsedByKpiId(domain, page,xsource.getKeySearch(), xsource.getOtherKeySearch());
							if (result != null && result.size() == 2) {
								/*java.util.ArrayList<CdsResult> domains = (java.util.ArrayList<CdsResult>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsResultModel> models = new ArrayList<CdsResultModel>();
								if (domains_size != null && domains_size.length()!=0)
									imakeMessage.setMaxRow(domains_size);
								if (domains != null && domains.size() > 0) {
									models = getModels(domains);
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
								*/
								String meta = (String) result.get(1);  
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<CdsResultModel> models = (ArrayList<CdsResultModel>) result.get(0); //get dataList
								imakeMessage.setMaxRow(meta);
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.CDS_RESULT_SEARCH)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchCdsResult(domain, page,xsource.getKeySearch(),xsource.getOtherKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsResult> domains = (java.util.ArrayList<CdsResult>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsResultModel> models = new ArrayList<CdsResultModel>();
								if (domains_size != null && domains_size.length()!=0)
									imakeMessage.setMaxRow(domains_size);
								if (domains != null && domains.size() > 0) {
									models = getModels(domains);
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.KPI_CDS_MAP_SEACH)){
							@SuppressWarnings("rawtypes")
							List result = (List) service.getCdsMapWithKpi(xsource);
							if (result != null) {
								List<CdsResultModel> cdsList = (ArrayList<CdsResultModel>) result.get(0); //get dataList
								String meta = (String) result.get(1);  
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								imakeMessage.setMaxRow((String) result.get(1));
								imakeMessage.setResultListObj(cdsList);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.CDS_RESULT_FIND_BY_CDS)){
							@SuppressWarnings("rawtypes")
							CdsResult cdsResult = service.findCdsResultByCds(domain);
							CdsResultModel cdsResultModel = new CdsResultModel();
								BeanUtils.copyProperties(cdsResult,cdsResultModel);	
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<CdsResultModel> cdsResultList = new ArrayList<CdsResultModel>();
								cdsResultList.add(cdsResultModel);
								imakeMessage.setResultListObj(cdsResultList);
								return getRepresentation(entity, imakeMessage, xstream);
						}
						/*else if(serviceName.equals(ServiceConstant.ORG_SEARCH_GROUPBY_COURSE)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchCdsResultGroupByCourseCode(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsResult> domains = (java.util.ArrayList<CdsResult>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsResultModel> models = new ArrayList<CdsResultModel>();
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
							List result = (List) service.searchCdsResultByFacultyCode(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsResult> domains = (java.util.ArrayList<CdsResult>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsResultModel> models = new ArrayList<CdsResultModel>();
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
							List result = (List) service.searchCdsResultIdByOthersCode(domain, page,
									xsource.getKeySearch(), xsource.getOtherKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<CdsResult> domains = (java.util.ArrayList<CdsResult>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<CdsResultModel> models = new ArrayList<CdsResultModel>();
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
	private List<CdsResultModel> getModels(	java.util.ArrayList<CdsResult> domains) {
		List<CdsResultModel> models = new ArrayList<CdsResultModel>(domains.size());
		for (CdsResult domain : domains) {
			CdsResultModel model =new CdsResultModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,CdsResultModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<CdsResultModel> xsources = new ArrayList<CdsResultModel>(1);
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
