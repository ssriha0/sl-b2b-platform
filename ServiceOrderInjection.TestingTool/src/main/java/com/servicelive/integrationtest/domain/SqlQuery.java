package com.servicelive.integrationtest.domain;

public class SqlQuery {
	
	private String queryName;
	private String querySql;
	
	public SqlQuery(String queryName, String querySql) {
		this.queryName = queryName;
		this.querySql = querySql;
	}
	
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public String getQuerySql() {
		return querySql;
	}

}
