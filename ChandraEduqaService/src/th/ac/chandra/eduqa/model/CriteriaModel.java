package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CriteriaModel")
public class CriteriaModel extends ImakeXML implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer standardId;
	private Integer academicYear;
	private Integer runningNo;
	private String desc;
	private Integer kpiId;
	private Integer cdsId;
	private Integer groupId;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	//detail
	private String groupName;
	private String cdsName;
	
	private String createdBy;
	private String updatedBy;
	
	public CriteriaModel() {
	}
	
	public Integer getStandardId() {
		return standardId;
	}

	public void setStandardId(Integer standardId) {
		this.standardId = standardId;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public Integer getRunningNo() {
		return runningNo;
	}

	public void setRunningNo(Integer runningNo) {
		this.runningNo = runningNo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getKpiId() {
		return kpiId;
	}

	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}

	public Integer getCdsId() {
		return cdsId;
	}

	public void setCdsId(Integer cdsId) {
		this.cdsId = cdsId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer detailId) {
		this.groupId = detailId;
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCdsName() {
		return cdsName;
	}
	public void setCdsName(String cdsName) {
		this.cdsName = cdsName;
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
