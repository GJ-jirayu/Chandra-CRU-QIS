package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import th.ac.chandra.eduqa.model.KpiModel;
import th.ac.chandra.eduqa.model.KpiResultModel;

public class KpiListForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private Integer orgId;
	private Integer kpiId;
	private String structureName;
	private List<KpiModel> Kpis;
	private List<KpiResultModel> resultKpis;
	private Integer level;
	private String university;
	private String faculty;
	private String course;
	
	public KpiListForm() {
		super();
		Kpis=new ArrayList<KpiModel>();
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

	public List<KpiModel> getKpis() {
		return Kpis;
	}
	public void setKpis(List<KpiModel> kpis) {
		Kpis = kpis;
	}
	public List<KpiResultModel> getResultKpis() {
		return resultKpis;
	}
	public void setResultKpis(List<KpiResultModel> resultKpis) {
		this.resultKpis = resultKpis;
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
}
