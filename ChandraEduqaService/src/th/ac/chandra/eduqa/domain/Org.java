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
@Table(name="org")
@NamedQuery(name="Org.findAll", query="SELECT k FROM Org k")
public class Org implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ORG_ID")
	private Integer orgId;
	
	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;
	
	@Column(name="ORG_CODE")
	private String orgCode;
	
	@Column(name="ORG_NAME")
	private String orgName;
	
	@Column(name="KPI_LEVEL_ID")
	private Integer levelId;
	
	@Column(name="PARENT_ORG_ID")
	private Integer parentOrgId;
	
	@Column(name="PATH")
	private String path;
	
	@Column(name="CRITERIA_GROUP_DETAIL_ID")
	private Integer criteraiGruopDetail;
	
	@Column(name="UNIVERSITY_CODE")
	private String universityCode;
	
	@Column(name="UNIVERSITY_NAME")
	private String universityName;
	
	@Column(name="FACULTY_CODE")
	private String facultyCode;
	
	@Column(name="FACULTY_NAME")
	private String facultyName;
	
	@Column(name="COURSE_CODE")
	private String courseCode;
	
	@Column(name="COURSE_NAME")
	private String courseName;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;

	
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
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

	public Integer getCriteraiGruopDetail() {
		return criteraiGruopDetail;
	}

	public void setCriteraiGruopDetail(Integer criteraiGruopDetail) {
		this.criteraiGruopDetail = criteraiGruopDetail;
	}

	public String getUniversityCode() {
		return universityCode;
	}

	public void setUniversityCode(String universityCode) {
		this.universityCode = universityCode;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getFacultyCode() {
		return facultyCode;
	}

	public void setFacultyCode(String facultyCode) {
		this.facultyCode = facultyCode;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Integer getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Integer parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}