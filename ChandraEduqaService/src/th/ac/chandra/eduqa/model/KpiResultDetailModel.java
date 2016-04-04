package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("KpiResultDetailModel")
public class KpiResultDetailModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer resultDetailId;
	private Integer academicYear;
	private Integer resultId;
	private Integer criteriaId;
	private String actionFlag;
	private String evidenceFlag;
	private String createdBy;
	private String updatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	
	private Integer evidenceId;
	private String evidencePath;
	private String criteriaDesc;
	private Integer cdsId;
	private Integer kpiId;
	private Integer orgId;
	private Integer monthId;
	private String cdsName;
	private Double cdsValue;
	
	public KpiResultDetailModel() {
	}

	public Integer getResultDetailId() {
		return resultDetailId;
	}

	public void setResultDetailId(Integer resultDetailId) {
		this.resultDetailId = resultDetailId;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public Integer getResultId() {
		return resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public String getEvidenceFlag() {
		return evidenceFlag;
	}

	public void setEvidenceFlag(String evidenceFlag) {
		this.evidenceFlag = evidenceFlag;
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

	public String getEvidencePath() {
		return evidencePath;
	}

	public void setEvidencePath(String evidencePath) {
		this.evidencePath = evidencePath;
	}

	public Integer getEvidenceId() {
		return evidenceId;
	}

	public void setEvidenceId(Integer evidenceId) {
		this.evidenceId = evidenceId;
	}

	public Integer getCriteriaId() {
		return criteriaId;
	}

	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	public String getCriteriaDesc() {
		return criteriaDesc;
	}

	public void setCriteriaDesc(String criteriaDesc) {
		this.criteriaDesc = criteriaDesc;
	}

	public Integer getCdsId() {
		return cdsId;
	}

	public void setCdsId(Integer cdsId) {
		this.cdsId = cdsId;
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

	public String getCdsName() {
		return cdsName;
	}

	public void setCdsName(String cdsName) {
		this.cdsName = cdsName;
	}

	public Double getCdsValue() {
		return cdsValue;
	}
	
	public void setCdsValue(Double cdsValue) {
		this.cdsValue = cdsValue;
	}
	
}
