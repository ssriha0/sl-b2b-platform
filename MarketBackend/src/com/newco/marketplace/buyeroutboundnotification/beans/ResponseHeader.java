package com.newco.marketplace.buyeroutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("HEADER")
public class ResponseHeader {
	
	@XStreamAlias("noOfOrders")
	private Integer noOfOrders;
	
	@XStreamAlias("seqNo")
	private Integer seqNo;

	public Integer getNoOfOrders() {
		return noOfOrders;
	}

	public void setNoOfOrders(Integer noOfOrders) {
		this.noOfOrders = noOfOrders;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	
	

}
