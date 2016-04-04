package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class AssignResultQualityForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private Integer resultDetailId;
	private Integer kpiId;
	private String kpiName;
	private String cdsName;
	private String yearName;
	private String yearNo;
	private String monthName;
	private Double cdsValue;
	private boolean actionFlag;
	private Integer resultId;
	private Integer orgId;
	private Integer cdsId;
	private Integer MonthId;
	private String message;
	private Integer criteriaId;
	private String criteriaDesc;
	
	public AssignResultQualityForm() {
		super();
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
	public String getCdsName() {
		return cdsName;
	}
	public void setCdsName(String cdsName) {
		this.cdsName = cdsName;
	}
	public String getYearName() {
		return yearName;
	}
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}
	public String getYearNo() {
		return yearNo;
	}
	public void setYearNo(String yearNo) {
		this.yearNo = yearNo;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public Double getCdsValue() {
		return cdsValue;
	}
	public void setCdsValue(Double cdsValue) {
		this.cdsValue = cdsValue;
	}
	public Integer getResultId() {
		return resultId;
	}
	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getCdsId() {
		return cdsId;
	}
	public void setCdsId(Integer cdsId) {
		this.cdsId = cdsId;
	}
	public Integer getMonthId() {
		return MonthId;
	}
	public void setMonthId(Integer monthId) {
		MonthId = monthId;
	}
	public Integer getResultDetailId() {
		return resultDetailId;
	}
	public void setResultDetailId(Integer resultDetailId) {
		this.resultDetailId = resultDetailId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getCriteriaId() {
		return criteriaId;
	}
	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}
	public String getCriteriaDesc() {
		return criteriaDesc;
	}
	public void setCriteriaDesc(String criteriaDesc) {
		this.criteriaDesc = criteriaDesc;
	}
	public boolean getActionFlag() {
		return actionFlag;
	}
	public void setActionFlag(boolean actionFlag) {
		this.actionFlag = actionFlag;
	}
}
