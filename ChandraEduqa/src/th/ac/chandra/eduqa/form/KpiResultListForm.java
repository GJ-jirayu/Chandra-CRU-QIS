package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import th.ac.chandra.eduqa.model.KpiResultModel;

public class KpiResultListForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3366744870180486928L;
	/**
	 * 
	 */
	/**
	 * 
	 */
	private Integer kpiId;
	private Integer orgId;
	private String structureName;
	private List<KpiResultModel> KpiResults;
	private Integer monthId;
	private Integer calendaYearNo;
	private Integer level;
	private String university;
	private String faculty;
	private String course;
	
	public KpiResultListForm() {
		super();
		this.KpiResults=new ArrayList<KpiResultModel>();
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getKpiId() {
		return kpiId;
	}
	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}
	public String getStructureName() {
		return structureName;
	}
	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}
	public List<KpiResultModel> getKpiResults() {
		return KpiResults;
	}
	public void setKpiResults(List<KpiResultModel> KpiResults) {
		this.KpiResults = KpiResults;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public Integer getMonthId() {
		return monthId;
	}
	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}
	public Integer getCalendaYearNo() {
		return calendaYearNo;
	}
	public void setCalendaYearNo(Integer calendaYearNo) {
		this.calendaYearNo = calendaYearNo;
	}
}
