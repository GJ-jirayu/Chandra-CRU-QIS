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
@Table(name="kpi")
@NamedQuery(name="Kpi.findAll", query="SELECT k FROM Kpi k")
public class Kpi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="KPI_ID")
	private Integer kpiId;

	@Column(name="KPI_NAME")
	private String kpiName;

	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;

	@Column(name="KPI_LEVEL_ID")
	private Integer levelId;

	@Column(name="KPI_GROUP_ID")
	private Integer groupId;

	@Column(name="KPI_STRUCTURE_ID")
	private Integer structureId;

	@Column(name="KPI_TYPE_ID")
	private Integer typeId;

	@Column(name="KPI_UOM_ID")
	private Integer uomId;
	
	@Column(name="CALENDAR_TYPE_ID")
	private Integer calendarTypeId;

	@Column(name="PERIOD_ID")
	private Integer periodId;

	@Column(name="PARENT_KPI_ID")
	private Integer parentId;

	@Column(name="BENCHMARK_VALUE")
	private double benchmark;
	
	@Column(name="MIN_SCORE")
	private double minScore;
	
	@Column(name="CRITERIA_TYPE_ID")
	private Integer criteriaTypeId;

	@Column(name="SCORE_CRITERIA_FLAG")
	private String scoreFlag;

	@Column(name="PASS_CRITERIA_FLAG")
	private String passFlag;

	@Column(name="CRITERIA_METHOD_ID")
	private Integer criteriaMethodId;
	
	@Column(name="FORMULA_DESC")
	private String formulaDesc;

	@Column(name="FORMULA")
	private String formula;
	
	@Column(name="CRITERIA_SCORE")
	private Integer criteriaScore;
	
	@Column(name="PERCENTAGE_FLAG")
	private String percentFlag;
	
	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	
	public Kpi() {
	
	}
	public Integer getAcademicYear() {
		return this.academicYear;
	}
	public void setAcademicYear(Integer y) {
		this.academicYear = y;
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

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getStructureId() {
		return structureId;
	}

	public void setStructureId(Integer structureId) {
		this.structureId = structureId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getUomId() {
		return uomId;
	}

	public void setUomId(Integer uomId) {
		this.uomId = uomId;
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
	public Integer getCriteriaTypeId() {
		return criteriaTypeId;
	}

	public void setCriteriaTypeId(Integer criteriaTypeId) {
		this.criteriaTypeId = criteriaTypeId;
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

	public Integer getCriteriaMethodId() {
		return criteriaMethodId;
	}

	public void setCriteriaMethodId(Integer method) {
		this.criteriaMethodId = method;
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

	public Integer getCriteriaScore() {
		return criteriaScore;
	}

	public void setCriteriaScore(Integer criteriaScore) {
		this.criteriaScore = criteriaScore;
	}

	public String getPercentFlag() {
		return percentFlag;
	}

	public void setPercentFlag(String percentFlag) {
		this.percentFlag = percentFlag;
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