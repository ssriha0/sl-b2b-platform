package com.newco.marketplace.buyeroutboundnotification.beans;

import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("UpdateServiceOrder")
public class RequestMsgBody {
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance;
	
	@XStreamAlias("HEADER")
	private RequestHeader header;
	
	@XStreamAlias("ORDERS")
	private RequestOrders orders;

	
	public RequestOrders getOrders() {
		return orders;
	}

	public void setOrders(RequestOrders orders) {
		this.orders = orders;
	}

	public RequestHeader getHeader() {
		return header;
	}

	public void setHeader(RequestHeader header) {
		this.header = header;
	}

	public RequestMsgBody(){
		this.namespace = BuyerOutBoundConstants.NPS_XLMNS;
		this.schemaInstance = BuyerOutBoundConstants.NPS_XLMNS_XSI;
		this.schemaLocation = BuyerOutBoundConstants.NPS_SCHEMA_LOCATION;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getSchemaInstance() {
		return schemaInstance;
	}

	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}
	
	

}
