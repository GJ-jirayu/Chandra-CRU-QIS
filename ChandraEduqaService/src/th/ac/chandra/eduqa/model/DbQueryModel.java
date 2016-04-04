package th.ac.chandra.eduqa.model;

import java.io.Serializable;

import th.ac.chandra.eduqa.xstream.common.ImakeXML;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("DbQueryModel")
public class DbQueryModel extends ImakeXML implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Query;
	private Integer maxRow;
	private DbConnModel conn;
	
	public DbQueryModel() {
		this.maxRow=10;
	}
	public String getQuery() {
		return Query;
	}
	public void setQuery(String query) {
		Query = query;
	}
	public Integer getMaxRow() {
		return maxRow;
	}
	public void setMaxRow(Integer maxRow) {
		this.maxRow = maxRow;
	}
	public DbConnModel getConn() {
		return conn;
	}
	public void setConn(DbConnModel conn) {
		this.conn = conn;
	}
}
