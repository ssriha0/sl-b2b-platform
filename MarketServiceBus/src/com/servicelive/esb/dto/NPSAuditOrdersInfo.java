package com.servicelive.esb.dto;

import java.io.Serializable;
import java.util.List;

public class NPSAuditOrdersInfo implements Serializable{

	private static final long serialVersionUID = 0L;
	
	private List<NPSAuditOrder> npsAuditOrders;
	
	private Integer successOrdersCnt;
	
	private Integer errorOrdersCnt;

	public List<NPSAuditOrder> getNpsAuditOrders() {
		return npsAuditOrders;
	}

	public void setNpsAuditOrders(List<NPSAuditOrder> npsAuditOrders) {
		this.npsAuditOrders = npsAuditOrders;
	}

	public Integer getSuccessOrdersCnt() {
		return successOrdersCnt;
	}

	public void setSuccessOrdersCnt(Integer successOrdersCnt) {
		this.successOrdersCnt = successOrdersCnt;
	}

	public Integer getErrorOrdersCnt() {
		return errorOrdersCnt;
	}

	public void setErrorOrdersCnt(Integer errorOrdersCnt) {
		this.errorOrdersCnt = errorOrdersCnt;
	}

}
