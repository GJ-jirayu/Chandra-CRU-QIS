package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import th.ac.chandra.eduqa.xstream.common.ImakeXML;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("KpiStrucModel")
public class KpiStrucModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer strucId;
	private Integer academicYear;
	private String strucName;
	private Integer groupId;
	private String groupName;
	private Integer structureType;
	private String strucTypeName;
	private String createdBy;
	private String UpdatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;

	public KpiStrucModel() {
	}
	public Integer getStrucId() {
		return this.strucId;
	}
	public void setStrucId(Integer identify){
		this.strucId = identify;
	}
	public Integer getAcademicYear(){
		return this.academicYear;
	}
	public void setAcademicYear(Integer y){
		this.academicYear = y;
	}
	public String getStrucName() {
		return strucName;
	}
	public void setStrucName(String strucName) {
		this.strucName = strucName;
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
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getStructureType() {
		return structureType;
	}
	public void setStructureType(Integer structureType) {
		this.structureType = structureType;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getStrucTypeName() {
		return strucTypeName;
	}
	public void setStrucTypeName(String strucTypeName) {
		this.strucTypeName = strucTypeName;
	}
}
