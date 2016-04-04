package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("KpiModel")
public class KpiModel extends ImakeXML implements Serializable{

	private static final long serialVersionUID = 8333761541272929831L;
	private Integer kpiId;
	private String kpiName; 
	private Integer academicYear;
	private Integer levelId;
	private Integer groupId;
	private Integer structureId;
	private Integer typeId;
	private Integer uomId;
	private Integer calendarTypeId;
	private Integer periodId;
	private Integer parentId;
	private double benchmark;
	private double minScore;
	private String scoreFlag;
	private String passFlag;
	private Integer criteriaScore;
	private Integer criteriaTypeId;
	private Integer criteriaMethodId;
	private String formulaDesc;
	private String formula;
	private String percentFlag;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private String createdBy;
	private String updatedBy;
	//  name attribute
	private String groupName;
	private String structureName;
	private String typeName;
	private String criteriaTypeName;
	private String calendarTypeName;
	private String periodName;
	private String uomName;

	public KpiModel() {
	}

	public Integer getKpiId() {
		return kpiId;
	}
	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	public Integer getAcademicYear() {
		return this.academicYear;
	}
	public void setAcademicYear(Integer y) {
		this.academicYear = y;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer kpiLevelId) {
		this.levelId = kpiLevelId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer kpiGroupId) {
		this.groupId = kpiGroupId;
	}
	public Integer getStructureId() {
		return structureId;
	}
	public void setStructureId(Integer kpiStructureId) {
		this.structureId = kpiStructureId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer kpiTypeId) {
		this.typeId = kpiTypeId;
	}
	public Integer getKpiUomId() {
		return uomId;
	}
	public void setUomId(Integer kpiUomId) {
		this.uomId = kpiUomId;
	}
	public Integer getCalendarTypeId() {
		return calendarTypeId;
	}
	public void setCalendarTypeId(Integer calendarTypeId) {
		this.calendarTypeId = calendarTypeId;
	}
	public Integer getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Integer periodId) {
		this.periodId = periodId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public double getBenchmark() {
		return benchmark;
	}
	public void setBenchmark(double benchmark) {
		this.benchmark = benchmark;
	}
	public double getMinScore() {
		return minScore;
	}
	public void setMinScore(double minScore) {
		this.minScore = minScore;
	}
	public String getScoreFlag() {
		return scoreFlag;
	}
	public void setScoreFlag(String scoreFlag) {
		this.scoreFlag = scoreFlag;
	}
	public String getPassFlag() {
		return passFlag;
	}
	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}
	
	public Integer getCriteriaScore() {
		return criteriaScore;
	}

	public void setCriteriaScore(Integer criteriaScore) {
		this.criteriaScore = criteriaScore;
	}

	public Integer getCriteriaTypeId() {
		return criteriaTypeId;
	}
	public void setCriteriaTypeId(Integer criteriaTypeId) {
		this.criteriaTypeId = criteriaTypeId;
	}
	public Integer getCriteriaMethodId() {
		return criteriaMethodId;
	}
	public void setCriteriaMethodId(Integer criteriaMethodId) {
		this.criteriaMethodId = criteriaMethodId;
	}
	public String getFormulaDesc() {
		return formulaDesc;
	}
	public void setFormulaDesc(String formulaDesc) {
		this.formulaDesc = formulaDesc;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getPercentFlag() {
		return percentFlag;
	}
	public void setPercentFlag(String percentFlag) {
		this.percentFlag = percentFlag;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCriteriaTypeName() {
		return criteriaTypeName;
	}

	public void setCriteriaTypeName(String criteriaTypeName) {
		this.criteriaTypeName = criteriaTypeName;
	}

	public Integer getUomId() {
		return uomId;
	}

	public String getCalendarTypeName() {
		return calendarTypeName;
	}

	public void setCalendarTypeName(String calendarTypeName) {
		this.calendarTypeName = calendarTypeName;
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
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
