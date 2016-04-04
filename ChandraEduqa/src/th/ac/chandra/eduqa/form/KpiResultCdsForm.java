package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import th.ac.chandra.eduqa.model.KpiResultModel;
import th.ac.chandra.eduqa.model.CdsResultModel;

public class KpiResultCdsForm extends CommonForm implements Serializable{

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
	private List<CdsResultModel> cdsList;
	private BigDecimal resultValue;
	
	public KpiResultCdsForm() {
		super();
		this.cdsList=new ArrayList<CdsResultModel>();
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

	public List<CdsResultModel> getCdsList() {
		return cdsList;
	}

	public void setCdsList(List<CdsResultModel> cdsUsed) {
		this.cdsList = cdsUsed;
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
	
}
