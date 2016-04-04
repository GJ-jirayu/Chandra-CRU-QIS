package th.ac.chandra.eduqa.form;

import java.io.Serializable;

import th.ac.chandra.eduqa.model.KpiUomModel;

public class KpiUomForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private KpiUomModel kpiUomModel;
	private String createDate;
	private String pageSize = "10";
	
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public KpiUomForm(KpiUomModel KpiUomModel) {
		super();
		this.kpiUomModel = KpiUomModel;
	}
	public KpiUomForm() {
		super();
		kpiUomModel=new KpiUomModel();
	}
	public KpiUomModel getKpiUomModel() {
		return kpiUomModel;
	}
	public void setKpiUomModel(KpiUomModel KpiUomModel) {
		this.kpiUomModel = KpiUomModel;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
