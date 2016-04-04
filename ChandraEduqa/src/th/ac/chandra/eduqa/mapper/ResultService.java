package th.ac.chandra.eduqa.mapper;

import java.util.List;

public class ResultService{
	@SuppressWarnings("rawtypes")
	public List resultObjList;
	public Object resultObj;
	public Integer resultInt;
	public String resultString;
	public Integer resultRow;
	public String resultPage;
	public String msgCode;
	public String msgDesc;
	public boolean error;
	
	public ResultService(){
		this.error = false;
		this.msgCode = "";
		this.msgDesc = "";
	}
	@SuppressWarnings("rawtypes")
	public List getResultObjList() {
		return resultObjList;
	}
	@SuppressWarnings("rawtypes")
	public void setResultObjList(List resultObjList) {
		this.resultObjList = resultObjList;
	}
	
	public Object getResultObj() {
		return resultObj;
	}
	public void setResultObj(Object resultObj) {
		this.resultObj = resultObj;
	}
	public Integer getResultInt() {
		return resultInt;
	}
	public void setResultInt(Integer resultInt) {
		this.resultInt = resultInt;
	}
	public String getResultString() {
		return resultString;
	}
	public void setResultString(String resultString) {
		this.resultString = resultString;
	}
	public Integer getResultRow() {
		return resultRow;
	}
	public void setResultRow(Integer resultRow) {
		this.resultRow = resultRow;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getMsgDesc() {
		return msgDesc;
	}
	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getResultPage() {
		return resultPage;
	}
	public void setResultPage(String resultPage) {
		this.resultPage = resultPage;
	}
	
}
