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
@Table(name="sys_year")
@NamedQuery(name="SysYear.findAll", query="SELECT k FROM SysYear k")
public class SysYear implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="YEAR_ID")
	private Integer yearId;

	@Column(name="MASTER_SETUP_ACADEMIC_YEAR")
	private Integer masterAcademicYear;
	
	@Column(name="APPRAISAL_ACADEMIC_YEAR")
	private Integer appraisalAcademicYear;

	@Column(name="FIRST_MONTH_FISCAL_YEAR")
	private String firstMonthFiscal;
	
	@Column(name="FIRST_MONTH_ACADEMIC_YEAR")
	private String firstMonthAcademic;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;

	public Integer getYearId() {
		return yearId;
	}

	public void setYearId(Integer yearId) {
		this.yearId = yearId;
	}

	public Integer getMasterAcademicYear() {
		return masterAcademicYear;
	}

	public void setMasterAcademicYear(Integer masterAcademicYear) {
		this.masterAcademicYear = masterAcademicYear;
	}

	public Integer getAppraisalAcademicYear() {
		return appraisalAcademicYear;
	}

	public void setAppraisalAcademicYear(Integer appraisalAcademicYear) {
		this.appraisalAcademicYear = appraisalAcademicYear;
	}

	public String getFirstMonthFiscal() {
		return firstMonthFiscal;
	}

	public void setFirstMonthFiscal(String firstMonthFiscal) {
		this.firstMonthFiscal = firstMonthFiscal;
	}

	public String getFirstMonthAcademic() {
		return firstMonthAcademic;
	}

	public void setFirstMonthAcademic(String firstMonthAcademic) {
		this.firstMonthAcademic = firstMonthAcademic;
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