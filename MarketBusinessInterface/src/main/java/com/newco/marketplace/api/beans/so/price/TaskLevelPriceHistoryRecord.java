package com.newco.marketplace.api.beans.so.price;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("taskPriceHistoryRecord")
public class TaskLevelPriceHistoryRecord {
	
	@XStreamAlias("taskName")
	private String taskName;

	@XStreamAlias("sku")
	private String sku;

	@XStreamImplicit(itemFieldName="priceHistory")
	private List<TaskPriceHistory> taskPricehistory;
	
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public List<TaskPriceHistory> getTaskPricehistory() {
		return taskPricehistory;
	}

	public void setTaskPricehistory(List<TaskPriceHistory> taskPricehistory) {
		this.taskPricehistory = taskPricehistory;
	}
	
	
}
