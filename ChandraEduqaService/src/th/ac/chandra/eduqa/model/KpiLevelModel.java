package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import th.ac.chandra.eduqa.xstream.common.ImakeXML;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("KpiLevelModel")
public class KpiLevelModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer levelId;
	private Integer academicYear;
	private String desc;
	private Integer levelNo;
	private String createdBy;
	private String UpdatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;

	public KpiLevelModel() {
	}
	public Integer getLevelId() {
		return this.levelId;
	}
	public void setLevelId(Integer identify){
		this.levelId = identify;
	}
	public String getDesc() {
		return this.desc;
	}
	public void setDesc(String name){
		this.desc = name;
	}
	public Integer getAcademicYear(){
		return this.academicYear;
	}
	public void setAcademicYear(Integer y){
		this.academicYear = y;
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
	public Integer getLevelNo() {
		return levelNo;
	}
	public void setLevelNo(Integer levelNo) {
		this.levelNo = levelNo;
	}
}
