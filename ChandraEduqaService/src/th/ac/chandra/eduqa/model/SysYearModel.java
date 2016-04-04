package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import th.ac.chandra.eduqa.xstream.common.ImakeXML;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SysYearModel")
public class SysYearModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer yearId;
	private Integer masterAcademicYear;
	private Integer appraisalAcademicYear;
	private String firstMonthFiscal;
	private String firstMonthAcademic;	
	private String createdBy;
	private String updatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;

	public SysYearModel() {
	}

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
