package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SysMonthModel")
public class SysMonthModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer monthId;
	private Integer calendarYear;
	private Integer academicYear;
	private Integer fiscalYear;
	private Integer calendarMonthNo;
	private Integer academicMonthNo;
	private Integer fiscalMonthNo;
	private String thMonthName;
	private String cal2Month;
	private String cal3Month;
	private String cal4Month;
	private String cal5Month;
	private String cal6Month;
	private String cal7Month;
	private String cal8Month;
	private String cal9Month;
	private String cal10Month;
	private String cal11Month;
	private String cal12Month;
	private String aca2Month;
	private String aca3Month;
	private String aca4Month;
	private String aca5Month;
	private String aca6Month;
	private String aca7Month;
	private String aca8Month;
	private String aca9Month;
	private String aca10Month;
	private String aca11Month;
	private String aca12Month;
	private String fis2Month;
	private String fis3Month;
	private String fis4Month;
	private String fis5Month;
	private String fis6Month;
	private String fis7Month;
	private String fis8Month;
	private String fis9Month;
	private String fis10Month;
	private String fis11Month;
	private String fis12Month;
	private String createdBy;
	private String updatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;

	public SysMonthModel() {
	}

	public Integer getMonthId() {
		return monthId;
	}

	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}

	public Integer getCalendarYear() {
		return calendarYear;
	}

	public void setCalendarYear(Integer calendarYear) {
		this.calendarYear = calendarYear;
	}

	public Integer getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(Integer fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public Integer getCalendarMonthNo() {
		return calendarMonthNo;
	}

	public void setCalendarMonthNo(Integer calendarMonthNo) {
		this.calendarMonthNo = calendarMonthNo;
	}

	public Integer getAcademicMonthNo() {
		return academicMonthNo;
	}

	public void setAcademicMonthNo(Integer academicMonthNo) {
		this.academicMonthNo = academicMonthNo;
	}

	public Integer getFiscalMonthNo() {
		return fiscalMonthNo;
	}

	public void setFiscalMonthNo(Integer fiscalMonthNo) {
		this.fiscalMonthNo = fiscalMonthNo;
	}

	public String getThMonthName() {
		return thMonthName;
	}

	public void setThMonthName(String thMonthName) {
		this.thMonthName = thMonthName;
	}

	public String getCal2Month() {
		return cal2Month;
	}

	public void setCal2Month(String cal2Month) {
		this.cal2Month = cal2Month;
	}

	public String getCal3Month() {
		return cal3Month;
	}

	public void setCal3Month(String cal3Month) {
		this.cal3Month = cal3Month;
	}

	public String getCal4Month() {
		return cal4Month;
	}

	public void setCal4Month(String cal4Month) {
		this.cal4Month = cal4Month;
	}

	public String getCal5Month() {
		return cal5Month;
	}

	public void setCal5Month(String cal5Month) {
		this.cal5Month = cal5Month;
	}

	public String getCal6Month() {
		return cal6Month;
	}

	public void setCal6Month(String cal6Month) {
		this.cal6Month = cal6Month;
	}

	public String getCal7Month() {
		return cal7Month;
	}

	public void setCal7Month(String cal7Month) {
		this.cal7Month = cal7Month;
	}

	public String getCal8Month() {
		return cal8Month;
	}

	public void setCal8Month(String cal8Month) {
		this.cal8Month = cal8Month;
	}

	public String getCal9Month() {
		return cal9Month;
	}

	public void setCal9Month(String cal9Month) {
		this.cal9Month = cal9Month;
	}

	public String getCal10Month() {
		return cal10Month;
	}

	public void setCal10Month(String cal10Month) {
		this.cal10Month = cal10Month;
	}

	public String getCal11Month() {
		return cal11Month;
	}

	public void setCal11Month(String cal11Month) {
		this.cal11Month = cal11Month;
	}

	public String getCal12Month() {
		return cal12Month;
	}

	public void setCal12Month(String cal12Month) {
		this.cal12Month = cal12Month;
	}

	public String getAca2Month() {
		return aca2Month;
	}

	public void setAca2Month(String aca2Month) {
		this.aca2Month = aca2Month;
	}

	public String getAca3Month() {
		return aca3Month;
	}

	public void setAca3Month(String aca3Month) {
		this.aca3Month = aca3Month;
	}

	public String getAca4Month() {
		return aca4Month;
	}

	public void setAca4Month(String aca4Month) {
		this.aca4Month = aca4Month;
	}

	public String getAca5Month() {
		return aca5Month;
	}

	public void setAca5Month(String aca5Month) {
		this.aca5Month = aca5Month;
	}

	public String getAca6Month() {
		return aca6Month;
	}

	public void setAca6Month(String aca6Month) {
		this.aca6Month = aca6Month;
	}

	public String getAca7Month() {
		return aca7Month;
	}

	public void setAca7Month(String aca7Month) {
		this.aca7Month = aca7Month;
	}

	public String getAca8Month() {
		return aca8Month;
	}

	public void setAca8Month(String aca8Month) {
		this.aca8Month = aca8Month;
	}

	public String getAca9Month() {
		return aca9Month;
	}

	public void setAca9Month(String aca9Month) {
		this.aca9Month = aca9Month;
	}

	public String getAca10Month() {
		return aca10Month;
	}

	public void setAca10Month(String aca10Month) {
		this.aca10Month = aca10Month;
	}

	public String getAca11Month() {
		return aca11Month;
	}

	public void setAca11Month(String aca11Month) {
		this.aca11Month = aca11Month;
	}

	public String getAca12Month() {
		return aca12Month;
	}

	public void setAca12Month(String aca12Month) {
		this.aca12Month = aca12Month;
	}

	public String getFis2Month() {
		return fis2Month;
	}

	public void setFis2Month(String fis2Month) {
		this.fis2Month = fis2Month;
	}

	public String getFis3Month() {
		return fis3Month;
	}

	public void setFis3Month(String fis3Month) {
		this.fis3Month = fis3Month;
	}

	public String getFis4Month() {
		return fis4Month;
	}

	public void setFis4Month(String fis4Month) {
		this.fis4Month = fis4Month;
	}

	public String getFis5Month() {
		return fis5Month;
	}

	public void setFis5Month(String fis5Month) {
		this.fis5Month = fis5Month;
	}

	public String getFis6Month() {
		return fis6Month;
	}

	public void setFis6Month(String fis6Month) {
		this.fis6Month = fis6Month;
	}

	public String getFis7Month() {
		return fis7Month;
	}

	public void setFis7Month(String fis7Month) {
		this.fis7Month = fis7Month;
	}

	public String getFis8Month() {
		return fis8Month;
	}

	public void setFis8Month(String fis8Month) {
		this.fis8Month = fis8Month;
	}

	public String getFis9Month() {
		return fis9Month;
	}

	public void setFis9Month(String fis9Month) {
		this.fis9Month = fis9Month;
	}

	public String getFis10Month() {
		return fis10Month;
	}

	public void setFis10Month(String fis10Month) {
		this.fis10Month = fis10Month;
	}

	public String getFis11Month() {
		return fis11Month;
	}

	public void setFis11Month(String fis11Month) {
		this.fis11Month = fis11Month;
	}

	public String getFis12Month() {
		return fis12Month;
	}

	public void setFis12Month(String fis12Month) {
		this.fis12Month = fis12Month;
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

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}	
	
}
