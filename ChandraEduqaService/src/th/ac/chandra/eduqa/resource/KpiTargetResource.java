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
import th.ac.chandra.eduqa.domain.KpiResult;
import th.ac.chandra.eduqa.domain.Org;
import th.ac.chandra.eduqa.domain.SysYear;
import th.ac.chandra.eduqa.model.KpiResultModel;
import th.ac.chandra.eduqa.model.KpiTargetModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeMessage;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;

public class KpiTargetResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public KpiTargetResource() {
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
			xstream.processAnnotations(KpiTargetModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			KpiTargetModel xsource = new KpiTargetModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (KpiTargetModel) xtarget;
				if (xsource != null) {
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.TARGET_SAVE_LIST)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							ImakeMessage msg = new ImakeMessage();
							Integer countSuccess = 0;
							// request kpi detail
							KpiResult result = new KpiResult();
							SysYear config = service.getSysYear();
						//	result.setAcademicYear(config.getAppraisalAcademicYear());
							result.setAcademicYear(config.getMasterAcademicYear());
							result.setKpiId(xsource.getKpiId());
							result = service.resultDescByKpiId(result);
						//	result.setAcademicYear(config.getAppraisalAcademicYear());
							result.setAcademicYear(config.getMasterAcademicYear());
							result.setCreatedBy(xsource.getCreatedBy());
							result.setUpdatedBy(xsource.getUpdatedBy());
							result.setOrgId(xsource.getOrgId());
							Org org = service.findOrgById(xsource.getOrgId());
							result.setOrgName(org.getOrgName());
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							result.setCreatedDate(now);
							result.setUpdatedDate(now);
							if(xsource.getListMonth().size()>0){
								countSuccess = service.saveKpiTarget(result,xsource);
								System.out.print("s:"+countSuccess+":"+ xsource.getListMonth().size());
								if(countSuccess == xsource.getListMonth().size()){
									msg.setMsgCode("1");
								}else{
									msg.setMsgCode("0");
									msg.setMsgDesc("ข้อมูลเข้าไม่ครบ");
								}
								xsource.setTotalSuccess(countSuccess);
								xsource.setTotalFailure(xsource.getListMonth().size()-countSuccess);
							}else{
								msg.setMsgCode("0");
								msg.setMsgDesc("ไม่มีข้อมูล");
							}
							List<KpiTargetModel> returnObjList = new ArrayList<KpiTargetModel>();
							returnObjList.add(xsource);
							imakeMessage.setResultMessage(msg);
							imakeMessage.setResultListObj(returnObjList);
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.TARGET_GET_LIST)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							ImakeMessage msg = new ImakeMessage();
							SysYear sy = new SysYear();
							sy = service.getSysYear();
							//xsource.setTargetOfYear(sy.getAppraisalAcademicYear());
							xsource.setTargetOfYear(sy.getMasterAcademicYear());
							KpiTargetModel target = service.getKpiTarget(xsource);
							imakeMessage.setResultMessage(msg);
							List<Double> list = new ArrayList<Double>(target.getListMonth().values());
							imakeMessage.setResultListObj(list);
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
	private Representation returnUpdateRecord(Representation entity,KpiTargetModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<KpiTargetModel> xsources = new ArrayList<KpiTargetModel>(1);
		model.setUpdateRecord(updateRecord);
		xsources.add(model);
		imakeMessage.setResultListObj(xsources);
		return getRepresentation(entity, imakeMessage, xstream);
	}
	private List<KpiResultModel> getResultModels(	java.util.ArrayList<KpiResult> domains) {
		List<KpiResultModel> models = new ArrayList<KpiResultModel>(domains.size());
		for (KpiResult domain : domains) {
			KpiResultModel model =new KpiResultModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
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
