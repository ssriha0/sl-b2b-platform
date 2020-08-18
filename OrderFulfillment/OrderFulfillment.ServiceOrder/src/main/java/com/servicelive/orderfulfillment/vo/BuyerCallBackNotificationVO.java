package com.servicelive.orderfulfillment.vo;

import java.util.Date;

public class BuyerCallBackNotificationVO {
	

		private String seqNo;
		private String soId;
		private Integer eventId;
		private String requestData;
		private Integer noOfRetries;
		private String status;
		private String response;
		private Date createdDate;
		private Date modifiedDate;
		/**
		 * @return the seqNo
		 */
		public String getSeqNo() {
			return seqNo;
		}
		/**
		 * @param seqNo the seqNo to set
		 */
		public void setSeqNo(String seqNo) {
			this.seqNo = seqNo;
		}
		/**
		 * @return the soId
		 */
		public String getSoId() {
			return soId;
		}
		/**
		 * @param soId the soId to set
		 */
		public void setSoId(String soId) {
			this.soId = soId;
		}
		/**
		 * @return the eventId
		 */
		public Integer getEventId() {
			return eventId;
		}
		/**
		 * @param eventId the eventId to set
		 */
		public void setEventId(Integer eventId) {
			this.eventId = eventId;
		}
		/**
		 * @return the requestData
		 */
		public String getRequestData() {
			return requestData;
		}
		/**
		 * @param requestData the requestData to set
		 */
		public void setRequestData(String requestData) {
			this.requestData = requestData;
		}
		/**
		 * @return the noOfRetries
		 */
		public Integer getNoOfRetries() {
			return noOfRetries;
		}
		/**
		 * @param noOfRetries the noOfRetries to set
		 */
		public void setNoOfRetries(Integer noOfRetries) {
			this.noOfRetries = noOfRetries;
		}
		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}
		/**
		 * @return the response
		 */
		public String getResponse() {
			return response;
		}
		/**
		 * @param response the response to set
		 */
		public void setResponse(String response) {
			this.response = response;
		}
		/**
		 * @return the createdDate
		 */
		public Date getCreatedDate() {
			return createdDate;
		}
		/**
		 * @param createdDate the createdDate to set
		 */
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		/**
		 * @return the modifiedDate
		 */
		public Date getModifiedDate() {
			return modifiedDate;
		}
		/**
		 * @param modifiedDate the modifiedDate to set
		 */
		public void setModifiedDate(Date modifiedDate) {
			this.modifiedDate = modifiedDate;
		}
		
}
