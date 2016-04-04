package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("KpiEvidenceModel")
public class KpiEvidenceModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer evidenceId;
	private Integer academicYear;
	private Integer resultDetailId;
	private String evidencePath;
	private String evidenceFileName;
	private String createdBy;
	private String updatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	
	private Integer kpiId;
	private Integer orgId;
	private Integer monthId;
	private Integer criteriaId;
	
	public KpiEvidenceModel() {
	}

	public Integer getEvidenceId() {
		return evidenceId;
	}

	public void setEvidenceId(Integer evidenceId) {
		this.evidenceId = evidenceId;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public Integer getResultDetailId() {
		return resultDetailId;
	}

	public void setResultDetailId(Integer resultDetailId) {
		this.resultDetailId = resultDetailId;
	}

	public String getEvidencePath() {
		return evidencePath;
	}

	public String getEvidenceFileName() {
		return evidenceFileName;
	}

	public void setEvidenceFileName(String evidenceFileName) {
		this.evidenceFileName = evidenceFileName;
	}

	public void setEvidencePath(String evidencePath) {
		this.evidencePath = evidencePath;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getKpiId() {
		return kpiId;
	}

	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getMonthId() {
		return monthId;
	}

	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}

	public Integer getCriteriaId() {
		return criteriaId;
	}

	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}
	
}
