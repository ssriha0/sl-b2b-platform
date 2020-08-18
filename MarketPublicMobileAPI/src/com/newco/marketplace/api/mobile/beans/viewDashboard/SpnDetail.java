package com.newco.marketplace.api.mobile.beans.viewDashboard;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("spnDetail")
public class SpnDetail {

	    @XStreamAlias("spnId")
		private Integer spnId;
	    
	    @XStreamAlias("spnName")
		private String spnName;
	    
	    @XStreamAlias("spnMembershipStatus")
		private String spnMembershipStatus;
		
		public Integer getSpnId() {
			return spnId;
		}
		public void setSpnId(Integer spnId) {
			this.spnId = spnId;
		}
		public String getSpnName() {
			return spnName;
		}
		public void setSpnName(String spnName) {
			this.spnName = spnName;
		}
		public String getSpnMembershipStatus() {
			return spnMembershipStatus;
		}
		public void setSpnMembershipStatus(String spnMembershipStatus) {
			this.spnMembershipStatus = spnMembershipStatus;
		}

}
