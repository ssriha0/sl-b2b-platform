package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("cancellationsSubStatusFilterList")
public class CancellationsSubStatusFilterList {
		
		@XStreamImplicit(itemFieldName="cancellationsSubStatus")
		private List<CancellationsSubStatusFilterVO> cancellationsSubStatus;

		public List<CancellationsSubStatusFilterVO> getCancellationsSubStatus() {
			return cancellationsSubStatus;
		}

		public void setCancellationsSubStatus(
				List<CancellationsSubStatusFilterVO> cancellationsSubStatus) {
			this.cancellationsSubStatus = cancellationsSubStatus;
		}

}
