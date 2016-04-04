package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import th.ac.chandra.eduqa.xstream.common.ImakeXML;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("KpiGroupTypeModel")
public class KpiGroupTypeModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer groupTypeId;
	private Integer academicYear;
	private String groupTypeName;
	private String createdBy;
	private Timestamp createdDate;
	private String UpdatedBy;	
	private Timestamp updatedDate;
	
//####################################################################//
	
	public Integer getGroupTypeId() {
		return groupTypeId;
	}
	public void setGroupTypeId(Integer groupTypeId) {
		this.groupTypeId = groupTypeId;
	}
	public Integer getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}
	public String getGroupTypeName() {
		return groupTypeName;
	}
	public void setGroupTypeName(String groupTypeName) {
		this.groupTypeName = groupTypeName;
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
}
