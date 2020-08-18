package com.newco.marketplace.buyeroutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("RespondServiceOrder")
public class ResponseMsgBody {
	
	@XStreamAlias("xsi:noNamespaceSchemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance;
	
	@XStreamAlias("HEADER")
	private RequestHeader header;
	
	@XStreamAlias("ORDERS")
	private ResponseOrders orders;

	public RequestHeader getHeader() {
		return header;
	}

	public void setHeader(RequestHeader header) {
		this.header = header;
	}

	public ResponseOrders getOrders() {
		return orders;
	}

	public void setOrders(ResponseOrders orders) {
		this.orders = orders;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getSchemaInstance() {
		return schemaInstance;
	}

	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}
}
