package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import th.ac.chandra.eduqa.model.KpiResultModel;
import th.ac.chandra.eduqa.model.CdsResultModel;

public class ResultQuantityTable extends CommonForm implements Serializable{

	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private Integer cdsId;
	private String cdsName;
	private String cdsValue;
	private List<String> evidences;
	private Integer hasEvidence;
	
	public ResultQuantityTable() {
		super();
	}

	public Integer getCdsId() {
		return cdsId;
	}

	public void setCdsId(Integer cdsId) {
		this.cdsId = cdsId;
	}

	public String getCdsName() {
		return cdsName;
	}

	public void setCdsName(String cdsName) {
		this.cdsName = cdsName;
	}

	public String getCdsValue() {
		return cdsValue;
	}

	public void setCdsValue(String cdsValue) {
		this.cdsValue = cdsValue;
	}

	public List<String> getEvidences() {
		return evidences;
	}

	public void setEvidences(List<String> evidences) {
		this.evidences = evidences;
	}

	public Integer getHasEvidence() {
		return hasEvidence;
	}

	public void setHasEvidence(Integer hasEvidence) {
		this.hasEvidence = hasEvidence;
	}
	
}
