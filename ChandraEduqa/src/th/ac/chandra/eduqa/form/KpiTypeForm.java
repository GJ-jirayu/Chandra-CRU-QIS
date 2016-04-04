package th.ac.chandra.eduqa.form;

import java.io.Serializable;

import th.ac.chandra.eduqa.model.KpiTypeModel;

public class KpiTypeForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private KpiTypeModel kpiTypeModel;
	private String createDate;
	private String pageSize = "10";
	
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public KpiTypeForm(KpiTypeModel KpiTypeModel) {
		super();
		this.kpiTypeModel = KpiTypeModel;
	}
	public KpiTypeForm() {
		super();
		kpiTypeModel=new KpiTypeModel();
	}
	public KpiTypeModel getKpiTypeModel() {
		return kpiTypeModel;
	}
	public void setKpiTypeModel(KpiTypeModel KpiTypeModel) {
		this.kpiTypeModel = KpiTypeModel;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
