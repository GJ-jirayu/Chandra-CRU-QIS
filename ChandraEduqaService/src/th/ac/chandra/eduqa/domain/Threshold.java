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
@Table(name="threshold")
@NamedQuery(name="threshold.findAll", query="SELECT k FROM Threshold k")
public class Threshold implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="THRESHOLD_ID")
	private Integer thresholdId;
	
	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;
	
	@Column(name="KPI_LEVEL_ID")
	private Integer levelId;
	
	@Column(name="THRESHOLD_NAME")
	private String thresholdName;
	
	@Column(name="BEGIN_THRESHOLD")
	private String beginThreshold;
	
	@Column(name="END_THRESHOLD")
	private String endThreshold;
	
	@Column(name="COLOR_CODE")
	private String colorCode;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;

	
	public Integer getThresholdId() {
		return thresholdId;
	}

	public void setThresholdId(Integer thresholdId) {
		this.thresholdId = thresholdId;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public String getThresholdName() {
		return thresholdName;
	}

	public void setThresholdName(String threshold) {
		this.thresholdName = threshold;
	}

	public String getBeginThreshold() {
		return beginThreshold;
	}

	public void setBeginThreshold(String beginThreshold) {
		this.beginThreshold = beginThreshold;
	}

	public String getEndThreshold() {
		return endThreshold;
	}

	public void setEndThreshold(String endThreshold) {
		this.endThreshold = endThreshold;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
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