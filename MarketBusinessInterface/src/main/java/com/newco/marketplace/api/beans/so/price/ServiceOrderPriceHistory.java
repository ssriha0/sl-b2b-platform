package com.newco.marketplace.api.beans.so.price;

import com.newco.marketplace.api.beans.Results;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("serviceOrder")
public class ServiceOrderPriceHistory {
	
	
	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("orderstatus")
	private OrderStatus orderStatus;
	
	@XStreamAlias("currentPrice")
	private CurrentPrice currentPrice;
	
	@XStreamAlias("orderPriceHistory")
	private SOLevelPriceHistory soLevelPriceHistory;
	
	@XStreamAlias("taskPriceHistory")
	private TaskLevelPriceHistory taskLevelPriceHistory;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public CurrentPrice getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(CurrentPrice currentPrice) {
		this.currentPrice = currentPrice;
	}

	public SOLevelPriceHistory getSoLevelPriceHistory() {
		return soLevelPriceHistory;
	}

	public void setSoLevelPriceHistory(SOLevelPriceHistory soLevelPriceHistory) {
		this.soLevelPriceHistory = soLevelPriceHistory;
	}

	public TaskLevelPriceHistory getTaskLevelPriceHistory() {
		return taskLevelPriceHistory;
	}

	public void setTaskLevelPriceHistory(TaskLevelPriceHistory taskLevelPriceHistory) {
		this.taskLevelPriceHistory = taskLevelPriceHistory;
	}

	
	
	
		
		

		
	
	
	
	
}
