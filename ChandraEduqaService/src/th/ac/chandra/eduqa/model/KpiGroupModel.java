package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import th.ac.chandra.eduqa.xstream.common.ImakeXML;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("KpiGroupModel")
public class KpiGroupModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer groupId;
	private Integer academicYear;
	private String groupName;
	private String groupShortName;
	private Integer orgTypeId;
	private Integer groupTypeId;	
	private String createdBy;
	private Timestamp createdDate;
	private String UpdatedBy;	
	private Timestamp updatedDate;
	private String orgTypeName;
	private String groupTypeName;
	
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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupShortName() {
		return groupShortName;
	}
	public void setGroupShortName(String groupShortName) {
		this.groupShortName = groupShortName;
	}
	public Integer getOrgTypeId() {
		return orgTypeId;
	}
	public void setOrgTypeId(Integer orgTypeId) {
		this.orgTypeId = orgTypeId;
	}
	public Integer getGroupTypeId() {
		return groupTypeId;
	}
	public void setGroupTypeId(Integer groupTypeId) {
		this.groupTypeId = groupTypeId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedBy() {
		return UpdatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getOrgTypeName() {
		return orgTypeName;
	}
	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}
	public String getGroupTypeName() {
		return groupTypeName;
	}
	public void setGroupTypeName(String groupTypeName) {
		this.groupTypeName = groupTypeName;
	}
}
