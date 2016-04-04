package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import th.ac.chandra.eduqa.xstream.common.ImakeXML;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("KpiTypeModel")
public class KpiTypeModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer typeId;
	private Integer academicYear;
	private String typeName;
	private String typeShortName;
	private String createdBy;
	private String UpdatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;

	public KpiTypeModel() {
	}
	public Integer getTypeId() {
		return this.typeId;
	}
	public void setTypeId(Integer identify){
		this.typeId = identify;
	}
	public Integer getAcademicYear(){
		return this.academicYear;
	}
	public void setAcademicYear(Integer y){
		this.academicYear = y;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeShortName() {
		return typeShortName;
	}
	public void setTypeShortName(String typeShortName) {
		this.typeShortName = typeShortName;
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
}
