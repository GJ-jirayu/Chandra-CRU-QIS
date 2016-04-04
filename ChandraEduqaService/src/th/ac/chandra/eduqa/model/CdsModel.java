package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CdsModel")
public class CdsModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer academicYear;
	private Integer cdsId;
	private String cdsName;
	private String cdsDesc;
	private Integer levelId;
	private String cheCode;
	private Integer sqlFlag;
	private String query;
	private String valueField;
	private String universityField;
	private String facultyField;
	private String courseField;
	private String detailField;
	private String yearField;
	private String monthField;
	private Integer connId;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	// foreign key attribute name
	private String levelDesc;
	private String createdBy;
	private String updatedBy;

	public CdsModel() {
	}
	
	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
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

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public String getUniversityField() {
		return universityField;
	}

	public void setUniversityField(String universityField) {
		this.universityField = universityField;
	}

	public String getFacultyField() {
		return facultyField;
	}

	public void setFacultyField(String facultyField) {
		this.facultyField = facultyField;
	}

	public String getCourseField() {
		return courseField;
	}

	public void setCourseField(String courseField) {
		this.courseField = courseField;
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

	public Integer getConnId() {
		return connId;
	}

	public void setConnId(Integer connectionId) {
		this.connId = connectionId;
	}
	//
	public String getLevelDesc() {
		return this.levelDesc;
	}

	public void setLevelDesc(String desc) {
		this.levelDesc = desc;
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
