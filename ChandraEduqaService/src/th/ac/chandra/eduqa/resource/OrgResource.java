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
import th.ac.chandra.eduqa.domain.Org;
import th.ac.chandra.eduqa.model.OrgModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class OrgResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public OrgResource() {
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
			xstream.processAnnotations(OrgModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			OrgModel xsource = new OrgModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (OrgModel) xtarget;
				if (xsource != null) {
					Org domain = new Org();
					BeanUtils.copyProperties(xsource,domain); 
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.ORG_FIND_BY_ID)){
							domain = service.findOrgById(xsource.getOrgId());
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List<OrgModel> models = new ArrayList<OrgModel>(1);
							OrgModel model = new OrgModel();
							if(domain!=null){
								BeanUtils.copyProperties(domain,model);	
							}
							model.setPagging(null);
							models.add(model);
							imakeMessage.setResultListObj(models);
							return getRepresentation(entity, imakeMessage, xstream);
						}else if(serviceName.equals(ServiceConstant.ORG_SEARCH)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchOrg(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<Org> domains = (java.util.ArrayList<Org>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<OrgModel> models = new ArrayList<OrgModel>();
								if (domains_size != null && domains_size.length()!=0)
									imakeMessage.setMaxRow(domains_size);
								if (domains != null && domains.size() > 0) {
									models = getModels(domains);
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.ORG_SEARCH_BY_LEVEL)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchOrgByLevelId(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<Org> domains = (java.util.ArrayList<Org>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<OrgModel> models = new ArrayList<OrgModel>();
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
							List result = (List) service.searchOrgGroupByCourseCode(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<Org> domains = (java.util.ArrayList<Org>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<OrgModel> models = new ArrayList<OrgModel>();
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
							List result = (List) service.searchOrgByFacultyCode(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<Org> domains = (java.util.ArrayList<Org>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<OrgModel> models = new ArrayList<OrgModel>();
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
							List result = (List) service.searchOrgIdByOthersCode(domain, page,
									xsource.getKeySearch(), xsource.getOtherKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<Org> domains = (java.util.ArrayList<Org>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<OrgModel> models = new ArrayList<OrgModel>();
								if (domains_size != null && domains_size.length()!=0)
									imakeMessage.setMaxRow(domains_size);
								if (domains != null && domains.size() > 0) {
									models = getModels(domains);
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						} // add by pun
						else if(serviceName.equals(ServiceConstant.ORG_GET_UNIVERSITY)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.getAllOrgUniversity(domain, page);
							if (result != null && result.size() == 2) {
								java.util.ArrayList<Org> domains = (java.util.ArrayList<Org>) result.get(0); //get dataList
								Integer domains_size = (Integer) result.get(1);  // get meta
								List<OrgModel> models = new ArrayList<OrgModel>();
								if (domains != null && domains_size > 0) {
									models = getModels(domains);
									imakeMessage.setMaxRow(String.valueOf(domains_size));
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.ORG_GET_FACULTY_OF_UNIVERSITY)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							Paging page = xsource.getPaging();
							domain.setUniversityCode(xsource.getUniversityCode());
							@SuppressWarnings("rawtypes")
							List result = (List) service.getOrgFacultyOfUniversity(domain, page);
							if (result != null && result.size() == 2) {
								java.util.ArrayList<Org> domains = (java.util.ArrayList<Org>) result.get(0); //get dataList
								Integer domains_size = (Integer) result.get(1);  // get meta
								List<OrgModel> models = new ArrayList<OrgModel>();
								if (domains != null && domains_size > 0) {
									models = getModels(domains);
									imakeMessage.setMaxRow(String.valueOf(domains_size));
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.ORG_GET_COURSE_OF_FACULTY)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							Paging page = xsource.getPaging(); 
							domain.setUniversityCode(xsource.getUniversityCode());
							domain.setFacultyCode(xsource.getFacultyCode());
							@SuppressWarnings("rawtypes")
							List result = (List) service.getOrgCourseOfFaculty(domain, page);
							if (result != null && result.size() == 2) {
								java.util.ArrayList<Org> domains = (java.util.ArrayList<Org>) result.get(0); //get dataList
								Integer domains_size = (Integer) result.get(1);  // get meta
								List<OrgModel> models = new ArrayList<OrgModel>();
								if (domains != null && domains_size > 0) {
									models = getModels(domains);
									imakeMessage.setMaxRow(String.valueOf(domains_size));
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.ORG_GET_ORGID_OF_ORG_DETAIL)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							Paging page = xsource.getPaging(); 
							domain.setUniversityCode(xsource.getUniversityCode());
							domain.setFacultyCode(xsource.getFacultyCode());
							@SuppressWarnings("rawtypes")
							List result = (List) service.getOrgIdByOrgDetailFilter(domain, page);
							if (result != null && result.size() == 2) {
								java.util.ArrayList<Org> domains = (java.util.ArrayList<Org>) result.get(0); //get dataList
								Integer domains_size = (Integer) result.get(1);  // get meta
								List<OrgModel> models = new ArrayList<OrgModel>();
								if (domains != null && domains_size > 0) {
									models = getModels(domains);
									imakeMessage.setMaxRow(String.valueOf(domains_size));
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
	private List<OrgModel> getModels(	java.util.ArrayList<Org> domains) {
		List<OrgModel> models = new ArrayList<OrgModel>(domains.size());
		for (Org domain : domains) {
			OrgModel model =new OrgModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,OrgModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<OrgModel> xsources = new ArrayList<OrgModel>(1);
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
