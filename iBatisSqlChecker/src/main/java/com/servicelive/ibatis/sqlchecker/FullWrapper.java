package com.servicelive.ibatis.sqlchecker;

public class FullWrapper {
	private Query query;
	private String schema;
	private Exception e;
	private ExplainPlan explainPlan;

	public FullWrapper(Query query, String schema, Exception e){
		this.query = query;
		this.schema = schema;
		this.e = e;
		this.explainPlan = null;
	}

	public FullWrapper(Query query, String schema, ExplainPlan explainPlan){
		this.query = query;
		this.schema = schema;
		this.explainPlan = explainPlan;
		this.e = null;
	}

	/**
	 * @return the query
	 */
	public Query getQuery() {
		return query;
	}

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @return the e
	 */
	public Exception getE() {
		return e;
	}

	/**
	 * @return the explainPlan
	 */
	public ExplainPlan getExplainPlan() {
		return explainPlan;
	}
}
