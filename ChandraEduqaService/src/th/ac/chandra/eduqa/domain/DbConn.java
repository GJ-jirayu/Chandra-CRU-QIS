package th.ac.chandra.eduqa.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name="database_connection")
@NamedQuery(name="DbConn.findAll", query="SELECT dc FROM DbConn dc")
public class DbConn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CONNECTION_ID")
	private Integer connId;

	@Column(name="CONNECTION_NAME")
	private String connName;

	@Column(name="ACADEMIC_YEAR")
	private Integer academicYear;

	@Column(name="DATABASE_TYPE")
	private String dbType;
	
	@Column(name="HOST_NAME")
	private String hostName;

	@Column(name="PORT")
	private Integer port;

	@Column(name="DATABASE_NAME")
	private String dbName;

	@Column(name="USER_NAME")
	private String username ;
	
	@Column(name="PASSWORD")
	private String password;

	@Column(name="CREATED_DTTM")
	private Timestamp createdDate;
	
	@Column(name="UPDATED_DTTM")
	private Timestamp updatedDate;

	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	public DbConn() {
	
	}
	public Integer getAcademicYear() {
		return this.academicYear;
	}
	public void setAcademicYear(Integer y) {
		this.academicYear = y;
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
	public void setConnName(String connName) {
		this.connName = connName;
	}
	
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String host) {
		this.hostName = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String user) {
		this.username = user;
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