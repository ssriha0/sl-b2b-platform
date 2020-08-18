package com.servicelive.esb.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Logistics")
public class Logistics implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = -7779309288818497547L;

	@XStreamAlias("Order")
	private LogisticsOrder logisticOrder;
	
	@XStreamAlias("LogisticsMerchandise")
	private LogisticsMerchandise logisticsMerchandise;
	
	public LogisticsOrder getLogisticOrder() {
		return logisticOrder;
	}

	public void setLogisticOrder(LogisticsOrder logisticOrder) {
		this.logisticOrder = logisticOrder;
	}

	public LogisticsMerchandise getLogisticsMerchandise() {
		return logisticsMerchandise;
	}

	public void setLogisticsMerchandise(
			LogisticsMerchandise logisticsMerchandise) {
		this.logisticsMerchandise = logisticsMerchandise;
	}
	
}
