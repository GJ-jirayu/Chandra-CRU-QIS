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
@Table(name="cds_result")
@NamedQuery(name="CdsResult.findAll", query="SELECT k FROM CdsResult k")
public class CdsResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RESULT_ID")
	private Integer resultId;
	
	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;
	
	@Column(name="MONTH_ID")
	private Integer monthId;
	
	@Column(name="ORG_ID")
	private Integer orgId;
	
	@Column(name="CDS_ID")
	private Integer cdsId;
	
	@Column(name="CDS_NAME")
	private String cdsName;
	
	@Column(name="UNIVERSITY_NAME")
	private String universityName;
	
	@Column(name="FACULTY_NAME")
	private String facultyName;
	
	@Column(name="COURSE_NAME")
	private String courseName;
	
	@Column(name="SUPPORT_DATA_NAME")
	private String supportDataName;
	
	@Column(name="CDS_VALUE")
	private String cdsValue;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;

	@Column(name="RESULT_ROW_NO")
	private Integer resultRowNo;

	@Column(name="ROW_TYPE")
	private String typeRow;
	
	public CdsResult() {
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
		return monthId;
	}

	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
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
	public Integer getResultRowNo() {
		return resultRowNo;
	}
	public void setResultRowNo(Integer resultRowNo) {
		this.resultRowNo = resultRowNo;
	}
	public String getTypeRow() {
		return typeRow;
	}
	public void setTypeRow(String typeRow) {
		this.typeRow = typeRow;
	}
}