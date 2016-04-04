package th.ac.chandra.eduqa.form;

import java.io.Serializable;

import th.ac.chandra.eduqa.model.KpiReComndModel;

public class KpiReComndForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private KpiReComndModel model;
	private HierarchyAuthorityForm hieAuth;
	private String createDate;
	
	public KpiReComndForm(KpiReComndModel KpiReComndModel) {
		super();
		this.model = KpiReComndModel;
		this.hieAuth = new HierarchyAuthorityForm();
	}
	public KpiReComndForm() {
		super();
		this.model=new KpiReComndModel();
		this.hieAuth = new HierarchyAuthorityForm();
	}
	public KpiReComndModel getModel() {
		return model;
	}
	public void setModel(KpiReComndModel KpiReComndModel) {
		this.model = KpiReComndModel;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public HierarchyAuthorityForm getHieAuth() {
		return hieAuth;
	}
	public void setHieAuth(HierarchyAuthorityForm hieAuth) {
		this.hieAuth = hieAuth;
	}
	
}
