package th.ac.chandra.eduqa.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import th.ac.chandra.eduqa.constant.ServiceConstant;
import th.ac.chandra.eduqa.domain.Cds;
import th.ac.chandra.eduqa.model.CdsModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeMessage;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class CdsResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);  
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public CdsResource() {
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
			xstream.processAnnotations(CdsModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			CdsModel xsource = new CdsModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (CdsModel) xtarget;
				if (xsource != null) {
					Cds domain = new Cds();
					BeanUtils.copyProperties(xsource,domain); 
					System.out.print("cds:"+xsource.getCdsId());
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.CDS_FIND_BY_ID)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							ImakeMessage msg = new ImakeMessage();
							List<CdsModel> models = new ArrayList<CdsModel>(1);
							CdsModel model = new CdsModel();
							try{
								domain = service.findCdsById(xsource.getCdsId());
								msg.setMsgCode(ServiceConstant.RESULT_OK);
							    msg.setMsgDesc("");
							}catch(PersistenceException e){
								Throwable t = e.getCause();
								msg.setMsgCode(ServiceConstant.RESULT_ERROR);
							    msg.setMsgDesc(	t.getCause().getMessage() );
							}
							BeanUtils.copyProperties(domain,model);	
							model.setPagging(null);;
							models.add(model);
							imakeMessage.setResultMessage(msg);
							imakeMessage.setResultListObj(models);
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.CDS_SAVE)){
							ImakeResultMessage resultMessage = new ImakeResultMessage();
							ImakeMessage msg = new ImakeMessage();
							List<CdsModel> cdss = new ArrayList<CdsModel>();
							CdsModel cds = new CdsModel();
							java.sql.Timestamp now= new java.sql.Timestamp(new Date().getTime());
							domain.setCreatedDate(now);
							domain.setUpdatedDate(now);
							Integer  updateRecord;
							try{
								updateRecord=service.saveCds(domain);
								if(updateRecord<0){
									msg.setMsgCode(ServiceConstant.RESULT_ERROR);
								    msg.setMsgDesc(ServiceConstant.MESSAGE_DUPLICATE );
								}else{
									cds.setUpdateRecord(updateRecord);
									msg.setMsgCode(ServiceConstant.RESULT_OK);
								    msg.setMsgDesc(ServiceConstant.MESSAGE_SAVE_SUCCESS );
								}
							} catch(PersistenceException e){
								Throwable t = e.getCause();
								msg.setMsgCode(ServiceConstant.RESULT_ERROR);
							    msg.setMsgDesc(	t.getCause().getMessage() );
							}
							resultMessage.setResultMessage(msg);
							resultMessage.setMaxRow("1");
							cdss.add(cds);
							resultMessage.setResultListObj(cdss);
							return getRepresentation(entity, resultMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.CDS_UPDATE)){
							ImakeResultMessage resultMessage = new ImakeResultMessage();
							ImakeMessage msg = new ImakeMessage();
							List<CdsModel> cdss = new ArrayList<CdsModel>();
							CdsModel cds = new CdsModel();
							java.sql.Timestamp updatedDate = new java.sql.Timestamp(new Date().getTime());
							domain.setUpdatedDate(updatedDate);
							Integer updateRecord=service.updateCds(domain);
							if(updateRecord>=0){
								cds.setUpdateRecord(updateRecord);
								msg.setMsgCode(ServiceConstant.RESULT_OK);
							    msg.setMsgDesc(ServiceConstant.MESSAGE_SAVE_SUCCESS );
							}else{
								msg.setMsgCode(ServiceConstant.RESULT_ERROR);
								msg.setMsgDesc(ServiceConstant.MESSAGE_DUPLICATE);
							}
							resultMessage.setResultMessage(msg);
							resultMessage.setMaxRow("1");
							cdss.add(cds);
							resultMessage.setResultListObj(cdss);
							return getRepresentation(entity, resultMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.CDS_DELETE)){
							ImakeResultMessage resultMessage = new ImakeResultMessage();
							ImakeMessage msg = new ImakeMessage();
							List<CdsModel> cdss = new ArrayList<CdsModel>();
							Integer updateRecord = null;
							try{
								updateRecord=service.deleteCds(domain);
								msg.setMsgCode("100");
								msg.setMsgDesc(ServiceConstant.MESSAGE_DELETE_SUCCESS);
							} catch(PersistenceException e){
								Throwable t = e.getCause();
								msg.setError(true);
								msg.setMsgCode("200");
								msg.setMsgDesc( t.getCause().getMessage());
							}
							resultMessage.setResultMessage(msg);
							resultMessage.setMaxRow("1");
							CdsModel cds = new CdsModel();
							cds.setUpdateRecord(updateRecord);
							cdss.add(cds);
							resultMessage.setResultListObj(cdss);
							return getRepresentation(entity, resultMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.CDS_SEARCH)){
							Paging page = xsource.getPaging(); 
							@SuppressWarnings("rawtypes")
							List result = (List) service.searchCds(domain, page,xsource.getKeySearch());
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							ImakeMessage msg = new ImakeMessage();
							if (result != null) {
								ArrayList<CdsModel> kpis = (ArrayList<CdsModel>) result.get(0); //get dataList
								imakeMessage.setMaxRow((String) result.get(1));
								imakeMessage.setResultListObj(kpis);
							}
							msg.setMsgCode(ServiceConstant.RESULT_OK);
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
	private List<CdsModel> getModels(	java.util.ArrayList<Cds> domains) {
		List<CdsModel> models = new ArrayList<CdsModel>(domains.size());
		for (Cds domain : domains) {
			CdsModel model =new CdsModel ();
			BeanUtils.copyProperties(domain, model);
			model.setPagging(null);
			models.add(model);
		}
		return models;
	}
	private Representation returnUpdateRecord(Representation entity,CdsModel model,int updateRecord){
		ImakeResultMessage imakeMessage = new ImakeResultMessage();
		List<CdsModel> xsources = new ArrayList<CdsModel>(1);
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
