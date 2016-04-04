package th.ac.chandra.eduqa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("DbConnModel")
public class DbConnModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer academicYear;
	private Integer connId;
	private String connName;
	private String hostName;
	private String dbType;
	private String dbName;
	private Integer port;
	private String username;
	private String password;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	
	private String createdBy;
	private String updatedBy;
	
	public DbConnModel() {
	}
	
	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public Integer getConnId() {
		return connId;
	}

	public void setConnId(Integer connId) {
		this.connId = connId;
	}

	public String getConnName() {
		return connName;
	}

	public void setConnName(String conenctionName) {
		this.connName = conenctionName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String host) {
		this.hostName = host;
	}
	
	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String pass) {
		this.password = pass;
	}
	public Timestamp getCreatedDate() {
		return this.createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getUpdatedDate() {
		return this.updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
