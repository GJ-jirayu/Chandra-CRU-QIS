package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import th.ac.chandra.eduqa.model.KpiModel;

public class KpiForm extends CommonForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private KpiModel kpiModel;
	private String radioCriteriaScore;
	
	public KpiForm(KpiModel kpiModel) {
		super();
		this.kpiModel = kpiModel;
	}
	public KpiForm() {
		super();
		kpiModel=new KpiModel();
	}
	public KpiModel getKpiModel() {
		return kpiModel;
	}
	public void setKpiModel(KpiModel kpiModel) {
		this.kpiModel = kpiModel;
	}
	public String getRadioCriteriaScore() {
		return radioCriteriaScore;
	}
	public void setRadioCriteriaScore(String radioCriteriaScore) {
		this.radioCriteriaScore = radioCriteriaScore;
	}
	
}
