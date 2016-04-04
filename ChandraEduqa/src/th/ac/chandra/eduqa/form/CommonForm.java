package th.ac.chandra.eduqa.form;

import java.io.Serializable;

public class CommonForm implements Serializable { 
	/**
	 * 
	 */
	private static final long serialVersionUID = -716798430048396259L;
	private String mode;
	private String command;
	private String keySearch;
	private Integer pageNo;
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getKeySearch() {
		return keySearch;
	}
	public void setKeySearch(String keySearch) {
		this.keySearch = keySearch;
	}
	public Integer getPageNo(){
		return this.pageNo;
	}
	public void setPageNo(Integer pageNo){
		this.pageNo = pageNo;
	}
}
