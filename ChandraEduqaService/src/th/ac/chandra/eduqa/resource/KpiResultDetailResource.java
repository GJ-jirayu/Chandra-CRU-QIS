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
import th.ac.chandra.eduqa.domain.CdsResultDetail;
import th.ac.chandra.eduqa.domain.KpiResultDetail;
import th.ac.chandra.eduqa.model.KpiResultDetailModel;
import th.ac.chandra.eduqa.model.KpiStrucModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeMessage;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class KpiResultDetailResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public KpiResultDetailResource() {
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
			xstream.processAnnotations(KpiResultDetailModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			KpiResultDetailModel xsource = new KpiResultDetailModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (KpiResultDetailModel) xtarget;
				if (xsource != null) {
					KpiResultDetail domain = new KpiResultDetail();
					BeanUtils.copyProperties(xsource,domain); 
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.KPI_RESULT_DETAIL_FIND_BY_ID)){
								ImakeResultMessage imakeMessage = new ImakeResultMessage();
								List<KpiResultDetailModel> models = new ArrayList<KpiResultDetailModel>();
								KpiResultDetail krd = new KpiResultDetail();
								krd = service.findKpiResultDetailById(xsource);
								if(domain!=null){
									KpiResultDetailModel model = new KpiResultDetailModel();
									BeanUtils.copyProperties(krd, model);
									models.add(model);
									imakeMessage.setResultListObj(models);
								}
								return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.KPI_RESULT_DETAIL_FIND_BY_IDENTIFY)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List<KpiResultDetailModel> models = new ArrayList<KpiResultDetailModel>();
							KpiResultDetailModel model = new KpiResultDetailModel();
							model = service.findKpiResultDetailByIdentify(xsource);
							if(model!=null){
								models.add(model);
								imakeMessage.setResultListObj(models);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.CRITERIA_EXIST_RESULT_QUALITY)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List<KpiResultDetailModel> models = new ArrayList<KpiResultDetailModel>();
							models = service.findCriteriaResult(xsource);
							if(models!=null){
								imakeMessage.setResultListObj(models);
							}
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.	RESULT_DETAIL_QUALITY_SAVE)){
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							xsource.setCreatedDate(now);
							xsource.setUpdatedDate(now);
							int updateRecord=service.saveKpiResultDetail(xsource);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
						else if(serviceName.equals(ServiceConstant.	RESULT_DETAIL_QUALITY_UPDATE_EVIDENCE)){
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							//xsource.setCreatedDate(now);
							xsource.setUpdatedDate(now);
							int updateRecord=service.updateKpiResultDetailEvidence(xsource);
							return returnUpdateRecord(entity,xsource,updateRecord);
						}
					} 
					else {
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
	private List<KpiResultDetailModel> getModels(	java.util.ArrayList<CdsResultDetail> domains) {
		List<KpiResultDetailModel> models = new ArrayList<KpiResultDetailModel>(domains.size());
		for (CdsResultDetail domain : domains) {
			KpiResultDetailModel model =new KpiResultDetailModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,KpiResultDetailModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<KpiResultDetailModel> xsources = new ArrayList<KpiResultDetailModel>(1);
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
