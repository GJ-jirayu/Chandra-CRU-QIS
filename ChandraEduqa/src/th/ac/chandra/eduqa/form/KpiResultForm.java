package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import java.util.List;

import th.ac.chandra.eduqa.model.KpiResultModel;

public class KpiResultForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 728205922357937092L;
	/**
	 * 
	 */
	/**
	 * 
	 */
	private String createDate;
	private String pageSize = "10";
	private HierarchyAuthorityForm identify;
	private Integer kpiId;
	private Integer orgId;
	private Integer calendarYear;
	private Integer academicYear;
	private Integer monthId;
	private Integer monthNo;
	private Integer groupId;
	private List<KpiResultListForm> resultList;
	private Integer criteriaType; // 	1 = quantity ,2= quality
	
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public List<KpiResultListForm> getResultList() {
		return resultList;
	}
	public void setResultList(List<KpiResultListForm> resultList) {
		this.resultList = resultList;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public HierarchyAuthorityForm getIdentify() {
		return identify;
	}
	public void setIdentify(HierarchyAuthorityForm identify) {
		this.identify = identify;
	}
	public Integer getCalendarYear() {
		return calendarYear;
	}
	public void setCalendarYear(Integer calendarYear) {
		this.calendarYear = calendarYear;
	}
	public Integer getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
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
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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
	public Integer getCriteriaType() {
		return criteriaType;
	}
	public void setCriteriaType(Integer criteriaType) {
		this.criteriaType = criteriaType;
	}
}
