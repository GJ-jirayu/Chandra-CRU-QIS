package th.ac.chandra.eduqa.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import th.ac.chandra.eduqa.model.KpiResultModel;
import th.ac.chandra.eduqa.model.CdsResultModel;

public class ResultQualityTable extends CommonForm implements Serializable{

	private static final long serialVersionUID = -6498711139841794347L;
	/**
	 * 
	 */
	private Integer standardId;
	private String standardName;
	private String cdsValue;
	private String hasResult;
	
	public ResultQualityTable() {
		super();
	}

	public Integer getStandardId() {
		return standardId;
	}

	public void setStandardId(Integer standardId) {
		this.standardId = standardId;
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	public String getHasResult() {
		return hasResult;
	}

	public void setHasResult(String hasResult) {
		this.hasResult = hasResult;
	}
	public String getCdsValue() {
		return cdsValue;
	}
	public void setCdsValue(String cdsvalue) {
		this.cdsValue = cdsvalue;
	}
}
