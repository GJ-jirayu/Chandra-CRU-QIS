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
import th.ac.chandra.eduqa.domain.OrgType;
import th.ac.chandra.eduqa.model.OrgTypeModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class OrgTypeResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public OrgTypeResource() {
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
			xstream.processAnnotations(OrgTypeModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			OrgTypeModel xsource = new OrgTypeModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (OrgTypeModel) xtarget;
				if (xsource != null) {
					OrgType domain = new OrgType();
					BeanUtils.copyProperties(xsource,domain); 
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.ORG_TYPE_FIND_BY_ID)){
							domain = service.findOrgTypeById(xsource.getOrgTypeId());
							if(domain!=null){
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<OrgTypeModel> models = new ArrayList<OrgTypeModel>(1);
								OrgTypeModel model = new OrgTypeModel();
								BeanUtils.copyProperties(domain,model);	
								model.setPagging(null);
								models.add(model);
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}else if(serviceName.equals(ServiceConstant.ORG_TYPE_SEARCH)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchOrgType(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<OrgType> domains = (java.util.ArrayList<OrgType>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<OrgTypeModel> models = new ArrayList<OrgTypeModel>();
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
	private List<OrgTypeModel> getModels(	java.util.ArrayList<OrgType> domains) {
		List<OrgTypeModel> models = new ArrayList<OrgTypeModel>(domains.size());
		for (OrgType domain : domains) {
			OrgTypeModel model =new OrgTypeModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,OrgTypeModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<OrgTypeModel> xsources = new ArrayList<OrgTypeModel>(1);
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
