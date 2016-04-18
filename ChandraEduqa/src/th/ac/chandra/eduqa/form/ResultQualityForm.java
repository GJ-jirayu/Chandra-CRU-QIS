package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import th.ac.chandra.eduqa.model.CdsResultModel;

public class ResultQualityForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private Integer kpiId;
	private Integer orgId;
	private Integer monthId;
	private Integer monthNo;
	private String kpiName;
	private Double targetValue;
	private String uom;
	private List<ResultQualityTable> resultList;
	private BigDecimal resultValue;
	private Integer selectResultId; // selected 
	private Integer selectCdsId; // selected
	private Integer selectStdId;
	private String message;
	private Integer criteriaMethodId;
	
	public ResultQualityForm() {
		super();
		this.resultList=new ArrayList<ResultQualityTable>();
	}

	public Integer getKpiId() {
		return kpiId;
	}

	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getMonthId() {
		return monthId;
	}

	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}

	public Integer getMonthNo() {
		return monthNo;
	}

	public void setMonthNo(Integer monthNo) {
		this.monthNo = monthNo;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public List<ResultQualityTable> getResultList() {
		return resultList;
	}
	
	public void setResultList(List<ResultQualityTable> resultList) {
		this.resultList = resultList;
	}
	
	public BigDecimal getResultValue() {
		return resultValue;
	}

	public void setResultValue(BigDecimal resultValue) {
		this.resultValue = resultValue;
	}

	public Double getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(Double targetValue) {
		this.targetValue = targetValue;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}
	public Integer getSelectResultId() {
		return this.selectResultId;
	}
	public void setSelectResultId(Integer selectResultId) {
		this.selectResultId = selectResultId;
	}

	public Integer getSelectCdsId() {
		return selectCdsId;
	}

	public void setSelectCdsId(Integer selectCdsId) {
		this.selectCdsId = selectCdsId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getSelectStdId() {
		return selectStdId;
	}

	public void setSelectStdId(Integer selectStdId) {
		this.selectStdId = selectStdId;
	}

	public Integer getCriteriaMethodId() {
		return criteriaMethodId;
	}

	public void setCriteriaMethodId(Integer criteriaMethodId) {
		this.criteriaMethodId = criteriaMethodId;
	}
}
