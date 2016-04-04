package th.ac.chandra.eduqa.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import th.ac.chandra.eduqa.constant.ServiceConstant;
import th.ac.chandra.eduqa.domain.DbConn;
import th.ac.chandra.eduqa.model.DbConnModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class DbConnResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);  
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public DbConnResource() {
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
			xstream.processAnnotations(DbConnModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			DbConnModel xsource = new DbConnModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (DbConnModel) xtarget;
				if (xsource != null) {
					DbConn domain = new DbConn();
					BeanUtils.copyProperties(xsource,domain); 
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.CONN_FIND_BY_ID)){
							domain = service.findConnById(xsource.getConnId());
							if(domain!=null){
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<DbConnModel> models = new ArrayList<DbConnModel>(1);
								DbConnModel model = new DbConnModel();
								BeanUtils.copyProperties(domain,model);	
								model.setPagging(null);
								models.add(model);
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.CONN_SAVE)){
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							domain.setCreatedDate(now);
							domain.setUpdatedDate(now);
							int updateRecord=service.saveConn(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.CONN_UPDATE)){
							java.sql.Timestamp updatedDate = new java.sql.Timestamp(new Date().getTime());
							domain.setCreatedDate(updatedDate);
							domain.setUpdatedDate(updatedDate);
							int updateRecord=service.updateConn(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.CONN_DELETE)){
							int updateRecord=service.deleteConn(domain);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.CONN_SEARCH)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchConn(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								@SuppressWarnings("unchecked")
								java.util.ArrayList<DbConn> domains = (java.util.ArrayList<DbConn>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<DbConnModel> models = new ArrayList<DbConnModel>();
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
	private List<DbConnModel> getModels(	java.util.ArrayList<DbConn> domains) {
		List<DbConnModel> models = new ArrayList<DbConnModel>(domains.size());
		for (DbConn domain : domains) {
			DbConnModel model =new DbConnModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,DbConnModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<DbConnModel> xsources = new ArrayList<DbConnModel>(1);
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
