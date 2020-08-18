package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("revisitSubStatus")

	public class RevisitSubStatusFilterVO {
		
		private Integer id;
		private String descr;
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getDescr() {
			return descr;
		}
		public void setDescr(String descr) {
			this.descr = descr;
		}
}
