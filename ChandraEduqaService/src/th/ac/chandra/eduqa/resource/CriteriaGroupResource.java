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
import th.ac.chandra.eduqa.domain.CriteriaGroup;
import th.ac.chandra.eduqa.domain.CriteriaGroupDetail;
import th.ac.chandra.eduqa.domain.CriteriaStandard;
import th.ac.chandra.eduqa.model.CdsModel;
import th.ac.chandra.eduqa.model.CriteriaGroupModel;
import th.ac.chandra.eduqa.service.EduqaService;
import th.ac.chandra.eduqa.xstream.common.ImakeResultMessage;
import th.ac.chandra.eduqa.xstream.common.Paging;

public class CriteriaGroupResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ServiceConstant.LOG_APPENDER);  
	@Autowired
	@Qualifier("eduqaServiceJpaImpl")
	private EduqaService service;
	//private ConsultantReportService consultantReportService;
	private com.thoughtworks.xstream.XStream xstream; 
	public CriteriaGroupResource() {
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
			xstream.processAnnotations(CriteriaGroupModel.class);// or xstream.autodetectAnnotations(true); (Auto-detect  Annotations)
			CriteriaGroupModel xsource = new CriteriaGroupModel();
			Object xtarget = xstream.fromXML(in);
			if (xtarget != null) {
				xsource = (CriteriaGroupModel) xtarget;
				if (xsource != null) {
					
					if (xsource.getServiceName() != null && xsource.getServiceName().length()!=0) {	
						String serviceName = xsource.getServiceName();
						if(serviceName.equals(ServiceConstant.CRITERIA_GROUP_SEARCH)){
							xsource.getPaging();
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List<CriteriaGroupModel> models = new ArrayList<CriteriaGroupModel>();
							
							List result = service.searchCriteriaGroup(xsource.getPaging(), xsource.getKeySearch());
							String totalRow = (String) result.get(1);
							imakeMessage.setMaxRow(totalRow);
							List<CriteriaGroup> groups = (List<CriteriaGroup>) result.get(0);
							for (CriteriaGroup group: groups) {
								CriteriaGroupModel groupModel = new CriteriaGroupModel();
								groupModel.setGroupId( group.getGroupId() );
								groupModel.setGroupName(group.getGroupName() );
								models.add(groupModel);
							}
							imakeMessage.setResultListObj(models);
							return getRepresentation(entity, imakeMessage, xstream);
						}
						else if(serviceName.equals(ServiceConstant.CRITERIA_DETAIL_BY_GROUP)){
							ImakeResultMessage imakeMessage = new ImakeResultMessage();
							List<CriteriaGroupModel> models = new ArrayList<CriteriaGroupModel>();
							
						List result = service.searchCriteriaGroupDetail(xsource.getGroupId(),xsource.getPaging());
							String totalRow = (String) result.get(1);
							imakeMessage.setMaxRow(totalRow);
							List<CriteriaGroupDetail> groups = (List<CriteriaGroupDetail>) result.get(0);
							for (CriteriaGroupDetail group: groups) {
								CriteriaGroupModel groupModel = new CriteriaGroupModel();
								groupModel.setDetailId( group.getDetailId() );
								groupModel.setDetailName(group.getDetailName() );
								models.add(groupModel);
							}
							imakeMessage.setResultListObj(models);
							return getRepresentation(entity, imakeMessage, xstream);
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

}
