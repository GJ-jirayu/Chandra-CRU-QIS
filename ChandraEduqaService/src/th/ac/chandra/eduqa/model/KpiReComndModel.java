package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import th.ac.chandra.eduqa.xstream.common.ImakeXML;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("kpiReComndModel")
public class KpiReComndModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer reComndId;
	private Integer orgId;
	private Integer groupId;
	private Integer academicYear;
	private String reComndFlag;
	private String reComndDesc;
	private String createdBy;
	private String UpdatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;

	public KpiReComndModel() {
	}

	public Integer getReComndId() {
		return reComndId;
	}

	public void setReComndId(Integer reComndId) {
		this.reComndId = reComndId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public String getReComndFlag() {
		return reComndFlag;
	}

	public void setReComndFlag(String reComndFlag) {
		this.reComndFlag = reComndFlag;
	}

	public String getReComndDesc() {
		return reComndDesc;
	}

	public void setReComndDesc(String reComndDesc) {
		this.reComndDesc = reComndDesc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
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
