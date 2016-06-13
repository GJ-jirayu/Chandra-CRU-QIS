package th.ac.chandra.eduqa.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name="cds_evidence")
@NamedQuery(name="CdsEvidence.findAll", query="SELECT k FROM CdsEvidence k")
public class CdsEvidence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EVIDENCE_ID")
	private Integer evidenceId;
	
	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;
	
	@Column(name="RESULT_DETAIL_ID")
	private Integer resultDetailId;
	
	@Column(name="EVIDENCE_PATH")
	private String evidencePath;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;
	
	@Column(name="EVIDENCE_URL_PATH")
	private String evidenceUrlPath;

	
	public CdsEvidence() {
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