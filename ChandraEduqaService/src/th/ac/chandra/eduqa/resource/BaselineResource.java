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
import th.ac.chandra.eduqa.model.BaselineModel;
import th.ac.chandra.eduqa.domain.BaselineQuan;
import th.ac.chandra.eduqa.domain.BaselineRange;
import th.ac.chandra.eduqa.domain.BaselineSpec;
import th.ac.chandra.eduqa.domain.SysYear;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class BaselineResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);  
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public BaselineResource() {
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
			xstream.processAnnotations(BaselineModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			BaselineModel xsource = new BaselineModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (BaselineModel) xtarget;
				if (xsource != null) {
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						//get config table
						SysYear config = service.getSysYear(); 
						if( serviceName.equals(ServiceConstant.BASELINE_LIST) ){
							
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List<BaselineModel> list = new ArrayList<BaselineModel>();
							String size = "";
							
							if(xsource.getResultType().equals("quan")){

								System.out.print(xsource.getResultType());
								BaselineQuan quan = new BaselineQuan();
								quan.setKpiId(xsource.getKpiId());
								List result = service.listBaselineQuan(quan);
								list = (List<BaselineModel>) result.get(0);
								size = (String) result.get(1);
							}
							else if(xsource.getResultType().equals("range")){
								BaselineRange range = new BaselineRange();
								range.setKpiId(xsource.getKpiId());
								List result = service.listBaselineRange(range);
								list = (List<BaselineModel>) result.get(0);
								size = (String) result.get(1);
							}
							else if(xsource.getResultType().equals("spec")){
								BaselineSpec spec = new BaselineSpec();
								spec.setKpiId(xsource.getKpiId());
								spec.setScore(xsource.getScore());
								List result = service.listBaselineSpec(spec);
								list = (List<BaselineModel>) result.get(0);
								size = (String) result.get(1);
							}
							else if(xsource.getResultType().equals("specGroup")){
								BaselineSpec spec = new BaselineSpec();
								spec.setKpiId(xsource.getKpiId());
								List result = service.listBaselineSpecGroup(spec);
								list = (List<BaselineModel>) result.get(0);
								size = (String) result.get(1);
							}
							else if(xsource.getResultType().equals("specDetail")){
								BaselineSpec spec = new BaselineSpec();
								spec.setKpiId(xsource.getKpiId());
								spec.setGroupId(xsource.getGroupId());
								spec.setScore(xsource.getScore());
								List result = service.listBaselineSpecDetail(spec);
								list = (List<BaselineModel>) result.get(0);
								size = (String) result.get(1);
							}
							
							imakeMessage.setMaxRow( size);
							imakeMessage.setResultListObj(list);
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if( serviceName.equals( ServiceConstant.BASELINE_DELETE)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List<BaselineModel> list = new ArrayList<BaselineModel>();
							int updateRecord = 0;
							if(xsource.getResultType().equals("quan")){
								BaselineQuan quan = new BaselineQuan();
								quan.setBaselineId(xsource.getBaselineId());
								updateRecord = service.deleteQuanBaseline(quan);
							}
							else if(xsource.getResultType().equals("range")){
								BaselineRange range = new BaselineRange();
								range.setBaselineId(xsource.getBaselineId());
								updateRecord = service.deleteRangeBaseline(range);
							}
							else if(xsource.getResultType().equals("spec")){
								BaselineSpec spec = new BaselineSpec();
								spec.setBaselineId(xsource.getBaselineId());
								updateRecord = service.deleteSpecBaseline(spec);
							}
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(  serviceName.equals(ServiceConstant.BASELINE_QUAN_SAVE) ){
							BaselineQuan quan = new BaselineQuan();
							quan.setBaselineId(xsource.getBaselineId());
							quan.setAcademicYear(config.getMasterAcademicYear());
							quan.setKpiId(xsource.getKpiId());
							quan.setGroupId(xsource.getGroupId());
							quan.setSubtractValue(xsource.getSubtractValue());
							quan.setConversionValue(xsource.getConversionValue());
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							quan.setCreatedDate(now);
							quan.setUpdatedDate(now);
							quan.setCreatedBy(xsource.getCreatedBy());
							quan.setUpdatedBy(xsource.getUpdatedBy());
							int updateRecord = 0;
							if(xsource.getBaselineId()==null ){
								updateRecord=service.saveQuanBaseline(quan);
							}
							else{
								System.out.print(quan.getBaselineId());
								updateRecord=service.updateQuanBaseline(quan);
							}
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(  serviceName.equals(ServiceConstant.BASELINE_RANGE_SAVE) ){
							BaselineRange range = new BaselineRange();
							range.setBaselineId(xsource.getBaselineId());
							range.setAcademicYear(config.getMasterAcademicYear());
							range.setKpiId(xsource.getKpiId());
							range.setGroupId(xsource.getGroupId());
							range.setBegin(xsource.getBeginValue());
							range.setEnd(xsource.getEndValue());
							range.setScore(xsource.getScore());
							range.setDesc(xsource.getDesc());
							range.setCreatedBy(xsource.getCreatedBy());
							range.setUpdatedBy(xsource.getUpdatedBy());
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							range.setCreatedDate(now);
							range.setUpdatedDate(now);
							int updateRecord = 0;
							if(xsource.getBaselineId()==null ){
								updateRecord=service.saveRangeBaseline(range);
							}
							else{
								System.out.print(range.getBaselineId());
								updateRecord=service.updateRangeBaseline(range);
							}
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(  serviceName.equals(ServiceConstant.BASELINE_SPEC_SAVE) ){
							BaselineSpec spec = new BaselineSpec();
							spec.setBaselineId(xsource.getBaselineId());
							spec.setAcademicYear(config.getMasterAcademicYear());
							spec.setKpiId(xsource.getKpiId());
							spec.setGroupId(xsource.getGroupId());
							spec.setScore(xsource.getScore());
							spec.setCriteriaId(xsource.getCriteriaId());
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							spec.setCreatedDate(now);
							spec.setUpdatedDate(now);
							spec.setCreatedBy(xsource.getCreatedBy());
							spec.setUpdatedBy(xsource.getUpdatedBy());
							int updateRecord = 0;
							updateRecord=service.saveSpecBaseline(spec);
							System.out.print(updateRecord);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						// end serviceName case handle
					}
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
	private Representation returnUpdateRecord(Representation entity,BaselineModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<BaselineModel> xsources = new ArrayList<BaselineModel>(1);
		model.setUpdateRecord(updateRecord);
		xsources.add(model);
		imakeMessage.setResultListObj(xsources);
		return getRepresentation(entity, imakeMessage, xstream);
	}
}
