package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import th.ac.chandra.eduqa.model.CdsEvidenceModel;
import th.ac.chandra.eduqa.model.KpiEvidenceModel;

public class EvidenceQualityForm  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private List<KpiEvidenceModel> evidences;
	private CommonsMultipartFile fileData;
	private Integer orgId;
	private Integer kpiId;
	private Integer criteriaId;
	private Integer monthId;
	private Integer cdsId;
	private String message;
	private String evidenceType;
	private String evidenceCheck;
	private String urlPath;
	//desc
	private String kpiName;
	private String criteriaName;
	private String cdsName;
	
	public List<KpiEvidenceModel> getEvidences() {
		return evidences;
	}
	public void setEvidences(List<KpiEvidenceModel> evidences) {
		this.evidences = evidences;
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
	public Integer getCriteriaId() {
		return criteriaId;
	}
	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}
	public Integer getMonthId() {
		return monthId;
	}
	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}
	public String getEvidenceType() {
		return evidenceType;
	}
	public void setEvidenceType(String evidenceType) {
		this.evidenceType = evidenceType;
	}
	public String getEvidenceCheck() {
		return evidenceCheck;
	}
	public void setEvidenceCheck(String evidenceCheck) {
		this.evidenceCheck = evidenceCheck;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	public Integer getCdsId() {
		return cdsId;
	}
	public void setCdsId(Integer cdsId) {
		this.cdsId = cdsId;
	}
	public String getCriteriaName() {
		return criteriaName;
	}
	public void setCriteriaName(String criteriaName) {
		this.criteriaName = criteriaName;
	}
	public String getCdsName() {
		return cdsName;
	}
	public void setCdsName(String cdsName) {
		this.cdsName = cdsName;
	}
	
}
