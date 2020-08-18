package com.newco.marketplace.api.mobile.beans;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("reasonCodes")
public class ReasonCodes {

		@XStreamImplicit(itemFieldName="reasonCode")
		private List<ReasonCode> reasonList;

		public List<ReasonCode> getReasonList() {
			return reasonList;
		}

		public void setReasonList(List<ReasonCode> reasonList) {
			this.reasonList = reasonList;
		}
	

}
