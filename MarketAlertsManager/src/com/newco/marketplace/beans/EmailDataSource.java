package com.newco.marketplace.beans;

public class EmailDataSource {
	
	private String dataSourceSQL;
	private String postProcessorSQL;
	private String dataSource;
	private String folder;
	private String parameters;
	private String schedulerName;
	private int priority;
	
	public String getDataSourceSQL() {
		return dataSourceSQL;
	}
	public void setDataSourceSQL(String dataSourceSQL) {
		this.dataSourceSQL = dataSourceSQL;
	}
	public String getPostProcessorSQL() {
		return postProcessorSQL;
	}
	public void setPostProcessorSQL(String postProcessorSQL) {
		this.postProcessorSQL = postProcessorSQL;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getSchedulerName() {
		return schedulerName;
	}
	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
