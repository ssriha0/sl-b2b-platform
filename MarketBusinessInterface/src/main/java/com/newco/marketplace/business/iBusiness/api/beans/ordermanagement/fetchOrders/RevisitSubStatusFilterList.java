package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("revisitFilterList")
public class RevisitSubStatusFilterList {
	
			@XStreamImplicit(itemFieldName="revisitSubStatus")
			private List<RevisitSubStatusFilterVO> revisitSubStatus;

			public List<RevisitSubStatusFilterVO> getRevisitSubStatus() {
				return revisitSubStatus;
			}

			public void setRevisitSubStatus(List<RevisitSubStatusFilterVO> revisitSubStatus) {
				this.revisitSubStatus = revisitSubStatus;
			}

}
