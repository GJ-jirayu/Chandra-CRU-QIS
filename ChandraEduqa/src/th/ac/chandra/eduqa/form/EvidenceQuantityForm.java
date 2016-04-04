package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import th.ac.chandra.eduqa.model.CdsEvidenceModel;

public class EvidenceQuantityForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private Integer orgId;
	private Integer kpiId;
	private Integer cdsId;
	private Integer monthId;
	private String message;
	private String createDate;
	private String kpiName;
	private String cdsName;
	private String evidenceType;
	private String evidenceCheck;
	private String urlPath;
	private CommonsMultipartFile fileData;
	private List<CdsEvidenceModel> evidences;
	
	public List<CdsEvidenceModel> getEvidences() {
		return evidences;
	}
	public void setEvidences(List<CdsEvidenceModel> evidences) {
		this.evidences = evidences;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public CommonsMultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getKpiId() {
		return kpiId;
	}
	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}
	public Integer getCdsId() {
		return cdsId;
	}
	public void setCdsId(Integer cdsId) {
		this.cdsId = cdsId;
	}
	public Integer getMonthId() {
		return monthId;
	}
	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	public String getCdsName() {
		return cdsName;
	}
	public void setCdsName(String cdsName) {
		this.cdsName = cdsName;
	}
	public String getEvidenceType() {
		return evidenceType;
	}
	public void setEvidenceType(String evidenceType) {
		this.evidenceType = evidenceType;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public String getEvidenceCheck() {
		return evidenceCheck;
	}
	public void setEvidenceCheck(String evidenceCheck) {
		this.evidenceCheck = evidenceCheck;
	}
}
