package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import th.ac.chandra.eduqa.xstream.common.ImakeXML;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("DescriptionModel")
public class DescriptionModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private String descCode;
	private String description;
	private Integer groupId;
	private Integer acadYear;
	
	public DescriptionModel() {
		 this.descCode = "0";
		 this.description = "";
	}

	public String getDescCode() {
		return descCode;
	}

	public void setDescCode(String descId) {
		this.descCode = descId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getAcadYear() {
		return acadYear;
	}

	public void setAcadYear(Integer acadYear) {
		this.acadYear = acadYear;
	}

}
