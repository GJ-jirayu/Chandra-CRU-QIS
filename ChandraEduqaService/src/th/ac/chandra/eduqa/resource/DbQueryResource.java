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
import th.ac.chandra.eduqa.model.DbQueryModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeMessage;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class DbQueryResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);  
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public DbQueryResource() {
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
			xstream.processAnnotations(DbQueryModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			DbQueryModel xsource = new DbQueryModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (DbQueryModel) xtarget;
				if (xsource != null) {
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.query_preview)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							ImakeMessage msg = new ImakeMessage();
							// get connection detail
							DbConn conn = service.findConnById(xsource.getConn().getConnId());
							if(conn!=null){
								xsource.setConn(getConnModel(conn));
								// do query
								List result = null;
								if(conn.getDbType().equals("mysql")){
									result = service.previewMysqlQueryResult(xsource);
								}
								else if(conn.getDbType().equals("oracle")){
									result = service.previewOracleQueryResult(xsource);
								}
								msg.setMsgDesc((String) result.get(0));
								if( ((ArrayList) result.get(1)).size() != 0 ){
									imakeMessage.setResultListObj( (List)result.get(1) );
								}else{
									imakeMessage.setResultListObj(new ArrayList());
								}
							}else{
								msg.setError(true);
								msg.setMsgDesc("database connection failed");
							}
							imakeMessage.setResultMessage(msg);
							return getRepresentation(entity, imakeMessage, xstream);
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
	private DbConnModel getConnModel(	DbConn domain) {
			DbConnModel model =new DbConnModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
		return model;
	}
	private Representation returnUpdateRecord(Representation entity,DbQueryModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<DbQueryModel> xsources = new ArrayList<DbQueryModel>(1);
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
