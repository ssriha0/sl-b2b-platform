package com.servicelive.ibatis.sqlchecker;

/**
 * 
 * @author svanloon
 *
 */
public class Query {
	private String id;
	private String sql;
	private String sqlWithSubstitution = null;
	
	public String getSqlWithSubstitutedParameters() {
		if(sqlWithSubstitution != null) {
			return sqlWithSubstitution;
		}
		String paramValue = "'1'";
		String result = sql.replaceAll("[#][^\\#]+[#]", paramValue);
		result = result.replaceAll("[?]", paramValue);
		sqlWithSubstitution = result;
		return sqlWithSubstitution;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}
	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}
}
