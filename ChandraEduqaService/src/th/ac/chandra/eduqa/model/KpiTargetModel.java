package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.util.HashMap;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("KpiTargetModel")
public class KpiTargetModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer kpiId;
	private Integer orgId;
	private String orgName;
	private HashMap<Integer,Double> listMonth;
	private Integer totalSuccess;
	private Integer totalFailure;
	private Integer targetOfYear;
	private String createdBy;
	private String updatedBy;
	
	public KpiTargetModel() {
		this.totalSuccess = null;
		this.totalFailure = null;
	}
	public HashMap<Integer, Double> getListMonth() {
		return listMonth;
	}
	public void setListMonth(HashMap<Integer, Double> listMonth) {
		this.listMonth = listMonth;
	}
	public Integer getTotalSuccess() {
		return totalSuccess;
	}
	public void setTotalSuccess(Integer totalSuccess) {
		this.totalSuccess = totalSuccess;
	}
	public Integer getTotalFailure() {
		return totalFailure;
	}
	public void setTotalFailure(Integer totalFailure) {
		this.totalFailure = totalFailure;
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
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public Integer getTargetOfYear() {
		return targetOfYear;
	}
	public void setTargetOfYear(Integer targetOfYear) {
		this.targetOfYear = targetOfYear;
	}
	
}
