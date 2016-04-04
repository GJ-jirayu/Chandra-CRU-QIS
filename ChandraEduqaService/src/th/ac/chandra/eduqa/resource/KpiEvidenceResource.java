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
import th.ac.chandra.eduqa.domain.CdsEvidence;
import th.ac.chandra.eduqa.domain.KpiEvidence;
import th.ac.chandra.eduqa.model.CdsEvidenceModel;
import th.ac.chandra.eduqa.model.KpiEvidenceModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class KpiEvidenceResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);
	
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public KpiEvidenceResource() {
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
			xstream.processAnnotations(KpiEvidenceModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			KpiEvidenceModel xsource = new KpiEvidenceModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (KpiEvidenceModel) xtarget;
				if (xsource != null) {
					KpiEvidence domain = new KpiEvidence();
					BeanUtils.copyProperties(xsource,domain); 
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.KPI_EVIDENCE_SAVE)){
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							xsource.setCreatedDate(now);
							xsource.setUpdatedDate(now);
							Integer success = service.saveKpiEvidence(xsource);
							return returnUpdateRecord(entity,xsource,success);
						}
						else if(serviceName.equals(ServiceConstant.KPI_EVIDENCE_DELETE)){
							Integer totalDelete = service.deleteKpiEvidence(xsource);
							return returnUpdateRecord(entity,xsource,totalDelete);
						}
						else if(serviceName.equals(ServiceConstant.KPI_EVIDENCE_SEARCH)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List<KpiEvidenceModel> models = service.searchKpiEvidence(xsource);
							if(models!=null){
								imakeMessage.setResultListObj(models);
							}
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
	private List<CdsEvidenceModel> getModels(	java.util.ArrayList<CdsEvidence> domains) {
		List<CdsEvidenceModel> models = new ArrayList<CdsEvidenceModel>(domains.size());
		for (CdsEvidence domain : domains) {
			CdsEvidenceModel model =new CdsEvidenceModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,KpiEvidenceModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<KpiEvidenceModel> xsources = new ArrayList<KpiEvidenceModel>(1);
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
