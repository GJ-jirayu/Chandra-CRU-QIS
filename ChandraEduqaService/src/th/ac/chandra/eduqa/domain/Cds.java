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
@Table(name="cds")
@NamedQuery(name="cds.findAll", query="SELECT k FROM Cds k")
public class Cds implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CDS_ID")
	private Integer cdsId;

	@Column(name="CDS_NAME")
	private String cdsName;

	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;

	@Column(name="CDS_DESC")
	private String cdsDesc;

	@Column(name="KPI_LEVEL_ID")
	private Integer levelId;

	@Column(name="CHE_CDS_CODE")
	private String cheCode;

	@Column(name="CONNECTION_ID")
	private Integer connId ;
	
	@Column(name="SQL_FLAG")
	private Integer sqlFlag;

	@Column(name="CDS_SQL")
	private String query ;
	
	@Column(name="VALUE_FIELD_NAME")
	private String valueField ;

	@Column(name="UNIVERSITy_FIELD_NAME")
	private String universityField;

	@Column(name="FACULTY_FIELD_NAME")
	private String facultyField;

	@Column(name="COURSE_FIELD_NAME")
	private String courseField;
	
	@Column(name="SUPPORT_DATA_FIELD_NAME")
	private String detailField;
	
	@Column(name="YEAR_FIELD_NAME")
	private String yearField;

	@Column(name="MONTH_FIELD_NAME")
	private String monthField;

	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	
	public Cds() {
	
	}
	public Integer getAcademicYear() {
		return this.academicYear;
	}
	public void setAcademicYear(Integer y) {
		this.academicYear = y;
	}
	public Integer getCdsId() {
		return cdsId;
	}
	public void setCdsId(Integer cdsId) {
		this.cdsId = cdsId;
	}
	public String getCdsName() {
		return cdsName;
	}
	public void setCdsName(String cdsName) {
		this.cdsName = cdsName;
	}
	public String getCdsDesc() {
		return cdsDesc;
	}
	public void setCdsDesc(String cdsDesc) {
		this.cdsDesc = cdsDesc;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public String getCheCode() {
		return cheCode;
	}
	public void setCheCode(String cheCode) {
		this.cheCode = cheCode;
	}
	public Integer getConnId() {
		return connId;
	}
	public void setConnId(Integer connectionId) {
		this.connId = connectionId;
	}
	public Integer getSqlFlag() {
		return sqlFlag;
	}
	public void setSqlFlag(Integer sqlFlag) {
		this.sqlFlag = sqlFlag;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String value) {
		this.valueField = value;
	}
	public String getUniversityField() {
		return this.universityField;
	}
	public void setUniversityField(String university) {
		this.universityField = university;
	}
	public String getFacultyField() {
		return facultyField;
	}
	public void setFacultyField(String faculty) {
		this.facultyField = faculty;
	}
	public String getCourseField() {
		return courseField;
	}
	public void setCourseField(String course) {
		this.courseField = course;
	}
	public String getDetailField() {
		return detailField;
	}
	public void setDetailField(String detailField) {
		this.detailField = detailField;
	}
	public String getYearField() {
		return yearField;
	}
	public void setYearField(String yearField) {
		this.yearField = yearField;
	}
	public String getMonthField() {
		return monthField;
	}
	public void setMonthField(String monthField) {
		this.monthField = monthField;
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