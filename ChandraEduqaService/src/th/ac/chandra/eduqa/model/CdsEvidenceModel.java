package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CdsEvidenceModel")
public class CdsEvidenceModel extends ImakeXML implements Serializable{
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
	private String evidenceUrlPath;

	public CdsEvidenceModel() {
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

	public String getEvidenceUrlPath() {
		return evidenceUrlPath;
	}

	public void setEvidenceUrlPath(String evidenceUrlPath) {
		this.evidenceUrlPath = evidenceUrlPath;
	}
	
}
