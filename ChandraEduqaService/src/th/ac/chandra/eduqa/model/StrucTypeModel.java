package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import th.ac.chandra.eduqa.xstream.common.ImakeXML;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("StrucTypeModel")
public class StrucTypeModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer strucTypeId;
	private String strucTypeName;
	private String createdBy;
	private String UpdatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;

	public StrucTypeModel() {
	}
	
	public Integer getStrucTypeId() {
		return strucTypeId;
	}

	public void setStrucTypeId(Integer strucTypeId) {
		this.strucTypeId = strucTypeId;
	}

	public String getStrucTypeName() {
		return strucTypeName;
	}

	public void setStrucTypeName(String strucTypeName) {
		this.strucTypeName = strucTypeName;
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
