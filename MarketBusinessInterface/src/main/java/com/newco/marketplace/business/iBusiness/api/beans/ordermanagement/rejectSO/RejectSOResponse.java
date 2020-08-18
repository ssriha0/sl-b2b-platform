/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 03-Nov-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.rejectSO;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This class serves as POJO object used for sending response 
 *
 */
@XSD(name = "rejectSOResponse.xsd", path = "/resources/schemas/so/")
@XStreamAlias("rejectSOResponse")
public class RejectSOResponse implements IAPIResponse {

		@XStreamAlias("xsi:schemaLocation")
		@XStreamAsAttribute()
		private String schemaLocation;

		@XStreamAlias("xmlns")
		@XStreamAsAttribute()
		private String namespace;

		@XStreamAlias("xmlns:xsi")
		@XStreamAsAttribute()
		private String schemaInstance;

		@XStreamAlias("results")
		private Results results;
		
		public String toString() {
			return ReflectionToStringBuilder.toString(this);
		}
		public Results getResults() {
			return results;
		}
		public void setResults(Results results) {
			this.results = results;
		}

		public String getSchemaLocation() {
			return schemaLocation;
		}
		public void setSchemaLocation(String schemaLocation) {
			this.schemaLocation = schemaLocation;
		}
		public String getNamespace() {
			return namespace;
		}
		public void setNamespace(String namespace) {
			this.namespace = namespace;
		}
		public String getSchemaInstance() {
			return schemaInstance;
		}
		public void setSchemaInstance(String schemaInstance) {
			this.schemaInstance = schemaInstance;
		}
		public void setVersion(String version) {
			// TODO Auto-generated method stub
		}
		
	}
