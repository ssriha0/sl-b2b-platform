package com.newco.marketplace.buyeroutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("HEADER")
public class RequestHeader {
	
	@XStreamAlias("noOfOrders")
	private String noOfOrders;
	
	@XStreamAlias("seqNo")
	private String  seqNo;

	public String getNoOfOrders() {
		return noOfOrders;
	}

	public void setNoOfOrders(String noOfOrders) {
		this.noOfOrders = noOfOrders;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	
	
	

}
