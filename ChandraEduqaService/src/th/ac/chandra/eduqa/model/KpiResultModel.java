package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("kpiResultModel")
public class KpiResultModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer resultId;
	private Integer academicYear;
	private Integer monthID;
	private Integer orgId;
	private String orgName;
	private Integer kpiLevelId;
	private String kpiLevelName;
	private Integer kpiGroupId;
	private String kpiGroupShortName;
	private String kpiGroupName;
	private Integer kpiStructureId;
	private String kpiStructureName;
	private Integer kpiTypeId;
	private String kpiTypeShortName;
	private String kpiTypeName;
	private Integer kpiId;
	private String kpiName;
	private Integer criteriaTypeId;
	private String formulaDesc;
	private String calendarTypeName;
	private String periodName;
	private Integer kpiUomId;
	private String kpiUomName;
	private String multiplicand;
	private String denominator;
	private Integer benchmarkValue;
	private Double targetValue;
	private Double actualValue;
	private Integer percentActualVsTarget;
	private Integer percentActualChange;
	private Integer score;
	private String createdBy;
	private String updatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private List<Integer> kpiIds;
	private String hasResult;
	private Integer active;
	
	public KpiResultModel() {
	}

	public Integer getResultId() {
		return resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public Integer getMonthID() {
		return monthID;
	}

	public void setMonthID(Integer monthID) {
		this.monthID = monthID;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getKpiLevelId() {
		return kpiLevelId;
	}

	public void setKpiLevelId(Integer kpiLevelId) {
		this.kpiLevelId = kpiLevelId;
	}

	public String getKpiLevelName() {
		return kpiLevelName;
	}

	public void setKpiLevelName(String kpiLevelName) {
		this.kpiLevelName = kpiLevelName;
	}

	public Integer getKpiGroupId() {
		return kpiGroupId;
	}

	public void setKpiGroupId(Integer kpiGroupId) {
		this.kpiGroupId = kpiGroupId;
	}

	public String getKpiGroupShortName() {
		return kpiGroupShortName;
	}

	public void setKpiGroupShortName(String kpiGroupShortName) {
		this.kpiGroupShortName = kpiGroupShortName;
	}

	public String getKpiGroupName() {
		return kpiGroupName;
	}

	public void setKpiGroupName(String kpiGroupName) {
		this.kpiGroupName = kpiGroupName;
	}

	public Integer getKpiStructureId() {
		return kpiStructureId;
	}

	public void setKpiStructureId(Integer kpiStructureId) {
		this.kpiStructureId = kpiStructureId;
	}

	public String getKpiStructureName() {
		return kpiStructureName;
	}

	public void setKpiStructureName(String kpiStructureName) {
		this.kpiStructureName = kpiStructureName;
	}

	public Integer getKpiTypeId() {
		return kpiTypeId;
	}

	public void setKpiTypeId(Integer kpiTypeId) {
		this.kpiTypeId = kpiTypeId;
	}

	public String getKpiTypeShortName() {
		return kpiTypeShortName;
	}

	public void setKpiTypeShortName(String kpiTypeShortName) {
		this.kpiTypeShortName = kpiTypeShortName;
	}

	public String getKpiTypeName() {
		return kpiTypeName;
	}

	public void setKpiTypeName(String kpiTypeName) {
		this.kpiTypeName = kpiTypeName;
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

	public Integer getCriteriaTypeId() {
		return criteriaTypeId;
	}

	public void setCriteriaTypeId(Integer criteriaTypeId) {
		this.criteriaTypeId = criteriaTypeId;
	}

	public String getFormulaDesc() {
		return formulaDesc;
	}

	public void setFormulaDesc(String formulaDesc) {
		this.formulaDesc = formulaDesc;
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

	public Integer getKpiUomId() {
		return kpiUomId;
	}

	public void setKpiUomId(Integer kpiUomId) {
		this.kpiUomId = kpiUomId;
	}

	public String getKpiUomName() {
		return kpiUomName;
	}

	public void setKpiUomName(String kpiUomName) {
		this.kpiUomName = kpiUomName;
	}

	public String getMultiplicand() {
		return multiplicand;
	}

	public void setMultiplicand(String multiplicand) {
		this.multiplicand = multiplicand;
	}

	public String getDenominator() {
		return denominator;
	}

	public void setDenominator(String denominator) {
		this.denominator = denominator;
	}

	public Integer getBenchmarkValue() {
		return benchmarkValue;
	}

	public void setBenchmarkValue(Integer benchmarkValue) {
		this.benchmarkValue = benchmarkValue;
	}

	public Double getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(Double targetValue) {
		this.targetValue = targetValue;
	}

	public Double getActualValue() {
		return actualValue;
	}

	public void setActualValue(Double actualValue) {
		this.actualValue = actualValue;
	}

	public Integer getPercentActualVsTarget() {
		return percentActualVsTarget;
	}

	public void setPercentActualVsTarget(Integer percentActualVsTarget) {
		this.percentActualVsTarget = percentActualVsTarget;
	}

	public Integer getPercentActualChange() {
		return percentActualChange;
	}

	public void setPercentActualChange(Integer percentActualChange) {
		this.percentActualChange = percentActualChange;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	public List<Integer> getKpiIds() {
		return kpiIds;
	}

	public void setKpiIds(List<Integer> kpiIds) {
		this.kpiIds = kpiIds;
	}

	public String getHasResult() {
		return hasResult;
	}

	public void setHasResult(String hasResult) {
		this.hasResult = hasResult;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

}
