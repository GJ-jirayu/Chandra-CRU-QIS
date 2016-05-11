package th.ac.chandra.eduqa.resource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
import th.ac.chandra.eduqa.domain.CriteriaGroupDetail;
import th.ac.chandra.eduqa.domain.CriteriaStandard;
import th.ac.chandra.eduqa.model.CriteriaGroupModel;
import th.ac.chandra.eduqa.model.CriteriaModel;
import th.ac.chandra.eduqa.model.KpiGroupModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class CriteriaResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);  
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public CriteriaResource() {
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
			xstream.processAnnotations(CriteriaModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			CriteriaModel xsource = new CriteriaModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (CriteriaModel) xtarget;
				if (xsource != null) {
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						if(  serviceName.equals(ServiceConstant.CRITERIA_STD_SEARCH) ){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();

							List result = service.searchCriteriaStandard(xsource.getKpiId());
							String totalRow = (String) result.get(1);
							imakeMessage.setMaxRow(totalRow);
							List<CriteriaModel> models = (List<CriteriaModel>) result.get(0);
							imakeMessage.setResultListObj(models);
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.CRITERIA_STD_SAVE)){
							Timestamp now = new Timestamp(System.currentTimeMillis());
							long timestamp = now.getTime();
							Calendar cal = Calendar.getInstance();
							cal.setTimeInMillis(timestamp);
							Integer y = cal.get(Calendar.YEAR);
							
							CriteriaStandard std = new CriteriaStandard();
							std.setRunningNo(xsource.getRunningNo());
							std.setKpiId(xsource.getKpiId());
							std.setCdsId(xsource.getCdsId());
							std.setDesc(xsource.getDesc());
							std.setDetailId(xsource.getGroupId());
							std.setAcademicYear(y); 
							std.setCreatedDate(now);
							std.setUpdatedDate(now);
							std.setCreatedBy("");
							std.setUpdatedBy("");
							Integer updateRecord = 0;
							System.out.print("xxx:"+xsource.getStandardId());
							if(xsource.getStandardId()==null){
								updateRecord = service.saveCriteriaStandard(std);	
							}
							else{
								std.setStandardId(xsource.getStandardId());
								updateRecord = service.updateCriteriaStandard(std);	
							}
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.CRITERIA_STD_DELETE)){
							
							CriteriaStandard std = new CriteriaStandard();
							std.setStandardId(xsource.getStandardId());
							Integer updateRecord = service.deleteCriteriaStandard(std);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.CRITERIA_STD_DELETE_By_KPIID)){	
							CriteriaStandard std = new CriteriaStandard();
							std.setKpiId(xsource.getKpiId());
							Integer updateRecord = service.deleteCriteriaStandardByKpiI(std);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
					} // end serviceName case handle
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
	private Representation returnUpdateRecord(Representation entity,CriteriaModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<CriteriaModel> xsources = new ArrayList<CriteriaModel>(1);
		model.setUpdateRecord(updateRecord);
		xsources.add(model);
		imakeMessage.setResultListObj(xsources);
		return getRepresentation(entity, imakeMessage, xstream);
	}
}
