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
@Table(name="cds_result_detail")
@NamedQuery(name="CdsResultDetail.findAll", query="SELECT k FROM CdsResultDetail k")
public class CdsResultDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RESULT_DETAIL_ID")
	private Integer resultDetailId;
	
	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;
	
	@Column(name="RESULT_ID")
	private Integer resultId;
	
	@Column(name="EVIDENCE_FLAG")
	private String evidenceFlag;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;

	
	public CdsResultDetail() {
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

}