package th.ac.chandra.eduqa.form;

import java.io.Serializable;

import th.ac.chandra.eduqa.model.KpiStrucModel;

public class KpiStrucForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private KpiStrucModel kpiStrucModel;
	private String createDate;
	private String pageSize = "10";
	
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public KpiStrucForm(KpiStrucModel KpiStrucModel) {
		super();
		this.kpiStrucModel = KpiStrucModel;
	}
	public KpiStrucForm() {
		super();
		kpiStrucModel=new KpiStrucModel();
	}
	public KpiStrucModel getKpiStrucModel() {
		return kpiStrucModel;
	}
	public void setKpiStrucModel(KpiStrucModel KpiStrucModel) {
		this.kpiStrucModel = KpiStrucModel;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
