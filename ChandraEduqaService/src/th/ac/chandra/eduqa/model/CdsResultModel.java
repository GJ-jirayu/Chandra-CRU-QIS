package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CdsResultModel")
public class CdsResultModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer resultId;
	private Integer academicYear;
	private Integer MonthId;
	private Integer orgId;
	private Integer cdsId;
	private String cdsName;
	private String universityName;
	private String facultyName;
	private String courseName;
	private String supportDataName;
	private String cdsValue;
	private String createdBy;
	private String updatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private Integer kpiId;

	private String evidenceFlag;
	private String sqlFlag;
	private Integer cdsResultDetailId;
	private String evidencePathList;
	
	public CdsResultModel() {
	}

	public Integer getResultId() {
		return resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public Integer getMonthId() {
		return MonthId;
	}

	public void setMonthId(Integer monthId) {
		MonthId = monthId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
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

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSupportDataName() {
		return supportDataName;
	}

	public void setSupportDataName(String supportDataName) {
		this.supportDataName = supportDataName;
	}

	public String getCdsValue() {
		return cdsValue;
	}

	public void setCdsValue(String cdsValue) {
		this.cdsValue = cdsValue;
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

	public String getEvidenceFlag() {
		return evidenceFlag;
	}

	public void setEvidenceFlag(String evidenceFlag) {
		this.evidenceFlag = evidenceFlag;
	}

	public String getSqlFlag() {
		return sqlFlag;
	}

	public void setSqlFlag(String sqlFlag) {
		this.sqlFlag = sqlFlag;
	}

	public Integer getCdsResultDetailId() {
		return cdsResultDetailId;
	}

	public void setCdsResultDetailId(Integer cdsResultDetailId) {
		this.cdsResultDetailId = cdsResultDetailId;
	}

	public Integer getKpiId() {
		return kpiId;
	}
	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}

	public String getEvidencePathList() {
		return evidencePathList;
	}

	public void setEvidencePathList(String evidencePathList) {
		this.evidencePathList = evidencePathList;
	}
	
}
