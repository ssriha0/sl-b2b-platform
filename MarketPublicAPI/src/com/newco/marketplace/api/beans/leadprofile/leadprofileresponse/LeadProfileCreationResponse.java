package com.newco.marketplace.api.beans.leadprofile.leadprofileresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.beans.Results;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XmlRootElement(name = "leadProfileCreationResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class LeadProfileCreationResponse {

		@XStreamAlias("messege")
		private String messege;
	
		@XStreamAlias("results")
		private Results results;
		
		@XStreamAlias("partnerId")
		private String partnerId;
		
		public Results getResults() {
			return results;
		}

		public void setResults(Results results) {
			this.results = results;
		}
		
		public String getMessege() {
			return messege;
		}

		public void setMessege(String messege) {
			this.messege = messege;
		}

		public String getPartnerId() {
			return partnerId;
		}

		public void setPartnerId(String partnerId) {
			this.partnerId = partnerId;
		}

		public void setVersion(String version) {
			// TODO Auto-generated method stub
			
		}
	

}
