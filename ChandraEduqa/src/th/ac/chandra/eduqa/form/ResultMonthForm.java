package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import th.ac.chandra.eduqa.model.KpiModel;

public class ResultMonthForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private Integer kpiId;
	private Integer frequency;
	private Integer startMonthNo;
	private List<Integer> resultMonth;
	

	public ResultMonthForm() {
		super();
	}
	public Integer getKpiId() {
		return kpiId;
	}
	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public Integer getStartMonthNo() {
		return startMonthNo;
	}
	public void setStartMonthNo(Integer startMonthNo) {
		this.startMonthNo = startMonthNo;
	}
	public List<Integer> getResultMonth() {
		return resultMonth;
	}
	public void setResultMonth(List<Integer> resultMonth) {
		this.resultMonth = resultMonth;
	}
}
