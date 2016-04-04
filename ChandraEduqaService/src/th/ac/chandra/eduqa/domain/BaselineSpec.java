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
@Table(name="specified_baseline")
@NamedQuery(name="spec.findAll", query="SELECT p FROM BaselineSpec p")
public class BaselineSpec implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BASELINE_ID")
	private Integer baselineId;

	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;

	@Column(name="KPI_ID")
	private Integer kpiId;

	@Column(name="CRITERIA_GROUP_DETAIL_ID")
	private Integer groupId;

	@Column(name="SCORE")
	private Integer score;
	
	@Column(name="CRITERIA_ID")
	private Integer criteriaId;
	
	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	public BaselineSpec() {
	
	}
	
	public Integer getBaselineId() {
		return baselineId;
	}

	public void setBaselineId(Integer baselineId) {
		this.baselineId = baselineId;
	}

	public Integer getAcademicYear() {
		return this.academicYear;
	}
	public void setAcademicYear(Integer y) {
		this.academicYear = y;
	}
	
	public Integer getKpiId() {
		return kpiId;
	}

	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getCriteriaId() {
		return this.criteriaId;
	}

	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}

	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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