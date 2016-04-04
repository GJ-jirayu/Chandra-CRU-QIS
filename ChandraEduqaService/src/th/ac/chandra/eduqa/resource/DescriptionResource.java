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
import th.ac.chandra.eduqa.model.DescriptionModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class DescriptionResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);  
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public DescriptionResource() {
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
			xstream.processAnnotations(DescriptionModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			DescriptionModel xsource = new DescriptionModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (DescriptionModel) xtarget;
				if (xsource != null) {
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.DESC_USER_ORG)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List<DescriptionModel> org = service.getOrgOfUser(xsource);
							if(org!=null){
								imakeMessage.setResultListObj(org);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.DESC_PERIOD)){

							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List periods = service.getPeriodAll(xsource);
							if(periods!=null){
								imakeMessage.setResultListObj(periods);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.DESC_UOM)){

							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List uoms = service.getUomAll(xsource);
							if(uoms!=null){
								imakeMessage.setResultListObj(uoms);
							}
							return getRepresentation(entity, imakeMessage, xstream);
							
						} 
						else if(serviceName.equals(ServiceConstant.DESC_CALENDAR)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List calen = service.getCalendarAll(xsource);
							if(calen!=null){
								imakeMessage.setResultListObj(calen);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.DESC_CRITERIA_TYPE)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List crType = service.getCriteriaTypeAll(xsource);
							if(crType!=null){
								imakeMessage.setResultListObj(crType);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.DESC_CRITERIA_METHOD)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List crMethod = service.getCriteriaMethodAll(xsource);
							if(crMethod!=null){
								imakeMessage.setResultListObj(crMethod);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.DESC_KPI)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List kpis = service.getKpiNameAll();
							if(kpis!=null){
								imakeMessage.setResultListObj(kpis);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
						// oraganization
						else if(serviceName.equals(ServiceConstant.DESC_UNIVERSITY)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List unis = service.getUniversityAll();
							System.out.print(unis.size());
							if(unis!=null){
								imakeMessage.setResultListObj(unis);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.DESC_FACULTY)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List kpis = service.getFacultyAll();
							if(kpis!=null){
								imakeMessage.setResultListObj(kpis);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.DESC_COURSE)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List kpis = service.getCourseAll();
							if(kpis!=null){
								imakeMessage.setResultListObj(kpis);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.DESC_MONTH)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							Integer yearNo = xsource.getAcadYear()!=null? xsource.getAcadYear():0;
							List kpis = service.getMonthAll(yearNo);
							if(kpis!=null){
								imakeMessage.setResultListObj(kpis);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
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
	@SuppressWarnings("unused")
	private Representation returnUpdateRecord(Representation entity,DescriptionModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<DescriptionModel> xsources = new ArrayList<DescriptionModel>();
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
