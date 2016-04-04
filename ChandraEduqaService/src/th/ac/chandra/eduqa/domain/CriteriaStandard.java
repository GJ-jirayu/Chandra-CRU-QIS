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
@Table(name="qualitative_criteria")
@NamedQuery(name="CriteriaStandard.findAll", query="SELECT g FROM CriteriaGroup g")
public class CriteriaStandard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="criteria_id")
	private Integer standardId;

	@Column(name="academic_year")
	private Integer academicYear;

	@Column(name="criteria_desc")
	private String desc;
	
	@Column(name="running_no")
	private Integer runningNo;
	
	@Column(name="kpi_id")
	private Integer kpiId;
	
	@Column(name="cds_id")
	private Integer cdsId;
	
	@Column(name="criteria_group_detail_id")
	private Integer detailId;
	
	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	
	public CriteriaStandard() {
	
	}
	
	public Integer getStandardId() {
		return standardId;
	}
	public void setStandardId(Integer standardId) {
		this.standardId = standardId;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getRunningNo() {
		return runningNo;
	}

	public void setRunningNo(Integer runningNo) {
		this.runningNo = runningNo;
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

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getUpdatedDate() {
		return this.updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
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
}