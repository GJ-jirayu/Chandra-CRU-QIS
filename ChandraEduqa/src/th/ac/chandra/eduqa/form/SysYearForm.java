package th.ac.chandra.eduqa.form;

import java.io.Serializable;

import th.ac.chandra.eduqa.model.SysYearModel;

public class SysYearForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private SysYearModel sysYearModel;
	private String createDate;
	private String pageSize = "10";
	
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public SysYearForm(SysYearModel sysYearModel) {
		super();
		this.sysYearModel = sysYearModel;
	}
	public SysYearForm() {
		super();
		sysYearModel = new SysYearModel();
	}
	public SysYearModel getSysYearModel() {
		return sysYearModel;
	}
	public void setSysYearModel(SysYearModel sysYearModel) {
		this.sysYearModel = sysYearModel;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
