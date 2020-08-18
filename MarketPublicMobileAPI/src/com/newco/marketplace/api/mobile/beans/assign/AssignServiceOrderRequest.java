package com.newco.marketplace.api.mobile.beans.assign;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/23
* for fetching response 0f search SO for mobile
*
*/

	@XSD(name = "assignServiceOrderRequest.xsd", path = "/resources/schemas/mobile/v3_0/")
	@XmlRootElement(name = "assignOrderRequest")
	@XStreamAlias("assignOrderRequest")
	public class AssignServiceOrderRequest {
		
		@XStreamAlias("requestFor")
		private String requestFor;
		
		@XStreamAlias("resourceId")
		private Integer resourceId;
		
		@XStreamAlias("reassignComment")
		private String reassignComment;
		
		//for validation
		private String soId;
		private String firmId;
		private String firmResourceId;
		
		public String getSoId() {
			return soId;
		}

		public void setSoId(String soId) {
			this.soId = soId;
		}

		public String getFirmId() {
			return firmId;
		}

		public void setFirmId(String firmId) {
			this.firmId = firmId;
		}

		public String getFirmResourceId() {
			return firmResourceId;
		}

		public void setFirmResourceId(String firmResourceId) {
			this.firmResourceId = firmResourceId;
		}		

		public Integer getResourceId() {
			return resourceId;
		}

		public void setResourceId(Integer resourceId) {
			this.resourceId = resourceId;
		}

		public String getReassignComment() {
			return reassignComment;
		}

		public void setReassignComment(String reassignComment) {
			this.reassignComment = reassignComment;
		}

		public String getRequestFor() {
			return requestFor;
		}

		public void setRequestFor(String requestFor) {
			this.requestFor = requestFor;
		}

	
		
	}


