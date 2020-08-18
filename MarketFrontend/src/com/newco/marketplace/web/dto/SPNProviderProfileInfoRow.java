package com.newco.marketplace.web.dto;

import java.util.Date;

public class SPNProviderProfileInfoRow extends SerializedBaseDTO{

		private String spnName;
		private Integer status;
		private Date membershipDate;
		private Integer spnId;
		private Integer spnNetworkId;
		
		public String getSpnName() {
			return spnName;
		}
		public void setSpnName(String spnName) {
			this.spnName = spnName;
		}
		public Date getMembershipDate() {
			return membershipDate;
		}
		public void setMembershipDate(Date membershipDate) {
			this.membershipDate = membershipDate;
		}
		public Integer getSpnId() {
			return spnId;
		}
		public void setSpnId(Integer spnId) {
			this.spnId = spnId;
		}
		public Integer getSpnNetworkId() {
			return spnNetworkId;
		}
		public void setSpnNetworkId(Integer spnNetworkId) {
			this.spnNetworkId = spnNetworkId;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}

}
