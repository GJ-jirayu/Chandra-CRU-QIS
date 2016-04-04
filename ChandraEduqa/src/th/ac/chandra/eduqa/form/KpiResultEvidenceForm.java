package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import th.ac.chandra.eduqa.model.KpiResultModel;
import th.ac.chandra.eduqa.model.CdsResultModel;

public class KpiResultEvidenceForm extends CommonForm implements Serializable{

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
	private Integer yearNo;
	private String kpiName;
	private String uom;
	private BigDecimal resultValue;
	private Integer cdsId;
	private Integer cdsName;
	
	public KpiResultEvidenceForm() {
		super();
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
	public Integer getYearNo() {
		return yearNo;
	}
	public void setYearNo(Integer yearNo) {
		this.yearNo = yearNo;
	}
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public BigDecimal getResultValue() {
		return resultValue;
	}
	public void setResultValue(BigDecimal resultValue) {
		this.resultValue = resultValue;
	}
	public Integer getCdsId() {
		return cdsId;
	}
	public void setCdsId(Integer cdsId) {
		this.cdsId = cdsId;
	}
	public Integer getCdsName() {
		return cdsName;
	}
	public void setCdsName(Integer cdsName) {
		this.cdsName = cdsName;
	}
	
}
