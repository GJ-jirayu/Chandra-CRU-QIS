package th.ac.chandra.eduqa.form;

import java.io.Serializable;

import th.ac.chandra.eduqa.model.ThresholdModel;

public class ThresholdForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private ThresholdModel thresholdModel;
	private String createDate;
	private String pageSize = "10";
	
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public ThresholdForm(ThresholdModel ThresholdModel) {
		super();
		this.thresholdModel = ThresholdModel;
	}
	public ThresholdForm() {
		super();
		thresholdModel=new ThresholdModel();
	}
	public ThresholdModel getThresholdModel() {
		return thresholdModel;
	}
	public void setThresholdModel(ThresholdModel ThresholdModel) {
		this.thresholdModel = ThresholdModel;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
