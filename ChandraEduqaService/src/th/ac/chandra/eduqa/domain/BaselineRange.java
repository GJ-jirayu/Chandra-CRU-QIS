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
@Table(name="range_baseline")
@NamedQuery(name="range.findAll", query="SELECT p FROM BaselineRange p")
public class BaselineRange implements Serializable {
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
	
	@Column(name="BEGIN_BASELINE")
	private Integer begin;

	@Column(name="END_BASELINE")
	private Integer end;
	
	
	@Column(name="BASELINE_DESC")
	private String desc;
	
	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	public BaselineRange() {
	
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

	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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