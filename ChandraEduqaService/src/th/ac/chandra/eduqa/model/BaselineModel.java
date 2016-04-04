package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BaselineModel")
public class BaselineModel extends ImakeXML implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer baselineId;
	private Integer academicYear;
	private Integer kpiId;
	private Integer groupId;
	private String resultType;
	private Integer score;
	private Integer beginValue;
	private Integer endValue;
	private Integer subtractValue;
	private Integer conversionValue;
	private String desc;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	// spec
	private Integer criteriaId;
	// attribute description
	private String groupName;
	
	private String createdBy;
	private String updatedBy;
	
	public BaselineModel() {
	}
	
	public Integer getBaselineId() {
		return baselineId;
	}

	public void setBaselineId(Integer baselineId) {
		this.baselineId = baselineId;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public Integer getKpiId() {
		return kpiId;
	}

	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer detailId) {
		this.groupId = detailId;
	}
	
	public Integer getCriteriaId() {
		return criteriaId;
	}

	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}

	public Integer getSubtractValue() {
		return subtractValue;
	}

	public void setSubtractValue(Integer subtractValue) {
		this.subtractValue = subtractValue;
	}

	public Integer getConversionValue() {
		return conversionValue;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getBeginValue() {
		return beginValue;
	}

	public void setBeginValue(Integer beginValue) {
		this.beginValue = beginValue;
	}

	public Integer getEndValue() {
		return endValue;
	}

	public void setEndValue(Integer endValue) {
		this.endValue = endValue;
	}

	public void setConversionValue(Integer conversionValue) {
		this.conversionValue = conversionValue;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
