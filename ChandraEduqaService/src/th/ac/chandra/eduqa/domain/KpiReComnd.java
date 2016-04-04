package th.ac.chandra.eduqa.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name="kpi_recommend")
@NamedQuery(name="KpiReComnd.findAll", query="SELECT k FROM KpiReComnd k")
public class KpiReComnd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RECOMMEND_ID")
	private Integer reComndId;
	
	@Column(name="ORG_ID")
	private Integer orgId;

	@Column(name="KPI_GROUP_ID")
	private Integer groupId;
	
	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;
	
	@Column(name="RECOMMEND_FLAG")
	private String reComndFlag;
	
	@Column(name="RECOMMEND")
	private String reComndDesc;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;

	public KpiReComnd() {
	}

	public Integer getReComndId() {
		return reComndId;
	}

	public void setReComndId(Integer reComndId) {
		this.reComndId = reComndId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}


	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public String getReComndFlag() {
		return reComndFlag;
	}

	public void setReComndFlag(String reComndFlag) {
		this.reComndFlag = reComndFlag;
	}

	public String getReComndDesc() {
		return reComndDesc;
	}

	public void setReComndDesc(String reComndDesc) {
		this.reComndDesc = reComndDesc;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
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

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

}