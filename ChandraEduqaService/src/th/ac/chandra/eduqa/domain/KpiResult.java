package th.ac.chandra.eduqa.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name="kpi_result")
@NamedQuery(name="KpiResult.findAll", query="SELECT k FROM KpiResult k")
public class KpiResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RESULT_ID")
	private Integer resultId;
	
	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;
	
	@Column(name="MONTH_ID")
	private Integer monthID;
	
	@Column(name="ORG_ID")
	private Integer orgId;
	
	@Column(name="ORG_NAME")
	private String orgName;
	
	@Column(name="KPI_LEVEL_ID")
	private Integer kpiLevelId;
	
	@Column(name="KPI_LEVEL_NAME")
	private String kpiLevelName;
	
	@Column(name="KPI_GROUP_ID")
	private Integer kpiGroupId;
	
	@Column(name="KPI_GROUP_SHORT_NAME")
	private String kpiGroupShortName;
	
	@Column(name="KPI_GROUP_NAME")
	private String kpiGroupName;
	
	@Column(name="KPI_STRUCTURE_ID")
	private Integer kpiStructureId;
	
	@Column(name="KPI_STRUCTURE_NAME")
	private String kpiStructureName;
	
	@Column(name="KPI_TYPE_ID")
	private Integer kpiTypeId;
	
	@Column(name="KPI_TYPE_SHORT_NAME")
	private String kpiTypeShortName;
	
	@Column(name="KPI_TYPE_NAME")
	private String kpiTypeName;
	
	@Column(name="KPI_ID")
	private Integer kpiId;
	
	@Column(name="KPI_NAME")
	private String kpiName;
	
	@Column(name="CRITERIA_TYPE_ID")
	private Integer criteriaTypeId;
	
	@Column(name="FORMULA_DESC")
	private String formulaDesc;
	
	
	@Column(name="CALENDAR_TYPE_NAME")
	private String calendarTypeName;
	
	@Column(name="PERIOD_NAME")
	private String periodName;
	
	@Column(name="KPI_UOM_ID")
	private Integer kpiUomId;
	
	@Column(name="KPI_UOM_NAME")
	private String kpiUomName;
	
	@Column(name="MULTIPLICAND")
	private String multiplicand;
	
	@Column(name="DENOMINATOR")
	private String denominator;
	
	@Column(name="BENCHMARK_VALUE")
	private Double benchmarkValue;
	
	@Column(name="TARGET_VALUE")
	private Double targetValue;
	
	@Column(name="ACTUAL_VALUE")
	private Double actualValue;
	
	@Column(name="PERCENT_ACTUAL_VS_TARGET")
	private Integer percentActualVsTarget;
	
	@Column(name="PERCENT_ACTUAL_CHANGE")
	private Integer percentActualChange;
	
	@Column(name="SCORE")
	private Integer score;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;

	/*//bi-directional many-to-one association to ResearcherGroup
	@OneToMany(mappedBy="researchGroup")
	private List<ResearcherGroup> researcherGroups;*/

	public KpiResult() {
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

	public Double getBenchmarkValue() {
		return benchmarkValue;
	}

	public void setBenchmarkValue(Double benchmarkValue) {
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
}