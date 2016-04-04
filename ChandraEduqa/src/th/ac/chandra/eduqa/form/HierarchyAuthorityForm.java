package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import th.ac.chandra.eduqa.model.KpiModel;

public class HierarchyAuthorityForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private Integer orgId;
	private Integer level;
	private String university;
	private String faculty;
	private String course;
	
	public HierarchyAuthorityForm() {
		super();
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
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
	public String findoutOrg(){
		String ret = null;
		if(this.getLevel().equals(1)){
			ret = this.getUniversity();
		}else if(this.getLevel().equals(2)){
			ret = this.getFaculty();
		}else if(this.getLevel().equals(3)){
			ret= this.getCourse();
		}
		return ret;
	}
}
