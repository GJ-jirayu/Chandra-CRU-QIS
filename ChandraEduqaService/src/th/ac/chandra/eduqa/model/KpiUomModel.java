package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import th.ac.chandra.eduqa.xstream.common.ImakeXML;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("KpiUomModel")
public class KpiUomModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer uomId;
	private Integer academicYear;
	private String uomName;
	private String createdBy;
	private String UpdatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;

	public KpiUomModel() {
	}
	public Integer getUomId() {
		return this.uomId;
	}
	public void setUomId(Integer identify){
		this.uomId = identify;
	}
	public Integer getAcademicYear(){
		return this.academicYear;
	}
	public void setAcademicYear(Integer y){
		this.academicYear = y;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
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
