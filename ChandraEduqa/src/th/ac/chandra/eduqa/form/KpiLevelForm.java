package th.ac.chandra.eduqa.form;

import java.io.Serializable;

import th.ac.chandra.eduqa.model.KpiLevelModel;

public class KpiLevelForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private KpiLevelModel kpiLevelModel;
	private String createDate;
	private String pageSize = "10";
	
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public KpiLevelForm(KpiLevelModel KpiLevelModel) {
		super();
		this.kpiLevelModel = KpiLevelModel;
	}
	public KpiLevelForm() {
		super();
		kpiLevelModel=new KpiLevelModel();
	}
	public KpiLevelModel getKpiLevelModel() {
		return kpiLevelModel;
	}
	public void setKpiLevelModel(KpiLevelModel KpiLevelModel) {
		this.kpiLevelModel = KpiLevelModel;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
