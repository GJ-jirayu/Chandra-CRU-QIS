package th.ac.chandra.eduqa.form;

import java.io.Serializable;

import th.ac.chandra.eduqa.model.CdsResultModel;

public class CdsResultForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private CdsResultModel cdsResultModel;
	private String createDate;
	private String pageSize = "10";
	
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public CdsResultForm(CdsResultModel cdsResultModel) {
		super();
		this.cdsResultModel = cdsResultModel;
	}
	public CdsResultForm() {
		super();
		cdsResultModel=new CdsResultModel();
	}
	public CdsResultModel getCdsResultModel() {
		return cdsResultModel;
	}
	public void setCdsResultModel(CdsResultModel cdsResultModel) {
		this.cdsResultModel = cdsResultModel;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
