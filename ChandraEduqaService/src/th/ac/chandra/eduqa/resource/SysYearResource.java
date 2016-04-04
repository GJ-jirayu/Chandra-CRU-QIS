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
import th.ac.chandra.eduqa.domain.SysYear;
import th.ac.chandra.eduqa.model.SysYearModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class SysYearResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public SysYearResource() {
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
			xstream.processAnnotations(SysYearModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			SysYearModel xsource = new SysYearModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (SysYearModel) xtarget;
				if (xsource != null) {
					SysYear domain = new SysYear();
					BeanUtils.copyProperties(xsource,domain); 
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.SYSYEAR_GET)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							SysYear y=service.getSysYear();
							SysYearModel ym = new SysYearModel();
							BeanUtils.copyProperties(y,ym);
							List<SysYearModel> yms = new ArrayList<SysYearModel>();
							yms.add(ym);
							imakeMessage.setResultListObj(yms);
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.SYSYEAR_SAVE)){
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							domain.setCreatedDate(now);
							domain.setUpdatedDate(now);
							int updateRecord=service.saveSysYear(domain);
							if(updateRecord>0){
								service.generateSysMonth(xsource);
							}
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.SYSYEAR_UPDATE)){
							java.sql.Timestamp updatedDate = new java.sql.Timestamp(new Date().getTime());
							domain.setUpdatedDate(updatedDate);
							int updateRecord=service.updateSysYear(domain);

							if(updateRecord>0){
								service.generateSysMonth(xsource);
							}
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.SYSYEAR_SEARCH)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchSysYear(domain, page,xsource.getKeySearch());
							if (result != null && result.size() == 2) {
								java.util.ArrayList<SysYear> domains = (java.util.ArrayList<SysYear>) result.get(0); //get dataList
								String domains_size = (String) result.get(1);  // get meta
								ImakeResultMessage imakeMessage = new ImakeResultMessage();

								List<SysYearModel> models = new ArrayList<SysYearModel>();
								if (domains_size != null && domains_size.length()!=0)
									imakeMessage.setMaxRow(domains_size);
								if (domains != null && domains.size() > 0) {
									models = getModels(domains);
								}
								imakeMessage.setResultListObj(models);
								return getRepresentation(entity, imakeMessage, xstream);
							}
						}
						else if(serviceName.equals(ServiceConstant.SYSYEAR_CREATE_SYSMONTH)){
						//	java.sql.Timestamp updatedDate = new java.sql.Timestamp(new Date().getTime());
						//	domain.setUpdatedDate(updatedDate);
							int updateRecord=service.generateSysMonth(xsource);
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
	private List<SysYearModel> getModels(	java.util.ArrayList<SysYear> domains) {
		List<SysYearModel> models = new ArrayList<SysYearModel>(domains.size());
		for (SysYear domain : domains) {
			SysYearModel model =new SysYearModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,SysYearModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<SysYearModel> xsources = new ArrayList<SysYearModel>(1);
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
