package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CdsResultDetailModel")
public class CdsResultDetailModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer resultDetailId;
	private Integer academicYear;
	private Integer resultId;
	private String evidenceFlag;
	private String createdBy;
	private String updatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	
	private Integer evidenceId;
	private String evidencePath;
	
	public CdsResultDetailModel() {
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

}
