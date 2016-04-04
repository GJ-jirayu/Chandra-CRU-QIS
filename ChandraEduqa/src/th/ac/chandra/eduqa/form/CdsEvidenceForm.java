package th.ac.chandra.eduqa.form;

import java.io.Serializable;

import th.ac.chandra.eduqa.model.CdsEvidenceModel;

public class CdsEvidenceForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private CdsEvidenceModel cdsEvidenceModel;
	private String createDate;
	private String pageSize = "10";
	
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public CdsEvidenceForm(CdsEvidenceModel cdsEvidenceModel) {
		super();
		this.cdsEvidenceModel = cdsEvidenceModel;
	}
	public CdsEvidenceForm() {
		super();
		cdsEvidenceModel=new CdsEvidenceModel();
	}
	public CdsEvidenceModel getCdsEvidenceModel() {
		return cdsEvidenceModel;
	}
	public void setCdsEvidenceModel(CdsEvidenceModel cdsEvidenceModel) {
		this.cdsEvidenceModel = cdsEvidenceModel;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
