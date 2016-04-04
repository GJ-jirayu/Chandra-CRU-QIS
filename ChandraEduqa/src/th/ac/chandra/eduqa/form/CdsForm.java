package th.ac.chandra.eduqa.form;

import java.io.Serializable;

import th.ac.chandra.eduqa.model.CdsModel;

public class CdsForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private CdsModel cdsModel;
	private String createdDate;
	private String pageSize = "10";
	
	public CdsForm(CdsModel cdsModel) {
		super();
		this.cdsModel = cdsModel;
	}
	public CdsForm() {
		super();
		cdsModel=new CdsModel();
	}
	public CdsModel getCdsModel() {
		return cdsModel;
	}
	public void setCdsModel(CdsModel cdsModel) {
		this.cdsModel = cdsModel;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

}
