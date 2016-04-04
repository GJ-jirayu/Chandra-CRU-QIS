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
@Table(name="kpi_type")
@NamedQuery(name="KpiType.findAll", query="SELECT k FROM KpiType k")
public class KpiType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="KPI_TYPE_ID")
	private Integer typeId;
	
	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;

	@Column(name="KPI_TYPE_NAME")
	private String typeName;

	@Column(name="KPI_TYPE_SHORT_NAME")
	private String typeShortName;
	
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

	public KpiType() {
	}

	
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeShortName() {
		return typeShortName;
	}
	public void setTypeShortName(String typeShortName) {
		this.typeShortName = typeShortName;
	}

	public Integer getAcademicYear() {
		return this.academicYear;
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