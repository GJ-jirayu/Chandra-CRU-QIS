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
@Table(name="kpi_structure")
@NamedQuery(name="KpiStruc.findAll", query="SELECT k FROM KpiStruc k")
public class KpiStruc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="KPI_STRUCTURE_ID")
	private Integer strucId;
	
	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;

	@Column(name="KPI_STRUCTURE_NAME")
	private String strucName;
	
	@Column(name="KPI_GROUP_ID")
	private Integer groupId;
	
	@Column(name="STRUCUTURE_TYPE_ID")
	private Integer structureType;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;

	/*//bi-directional many-to-one association to ResearcherGroup
	@OneToMany(mappedBy="researchGroup")
	private List<ResearcherGroup> researcherGroups;*/

	public KpiStruc() {
	}

	public Integer getStrucId() {
		return strucId;
	}

	public void setStrucId(Integer strucId) {
		this.strucId = strucId;
	}

	public String getStrucName() {
		return strucName;
	}

	public void setStrucName(String strucName) {
		this.strucName = strucName;
	}

	public Integer getAcademicYear() {
		return this.academicYear;
	}
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getStructureType() {
		return structureType;
	}

	public void setStructureType(Integer structureType) {
		this.structureType = structureType;
	}

	public void setAcademicYear(Integer y) {
		this.academicYear = y;
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

	
}