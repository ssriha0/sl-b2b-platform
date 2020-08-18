package com.newco.marketplace.api.beans.serviceOrderDetail;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XSD(name = "getServiceOrdersResponse.xsd", path = "/resources/schemas/so/v1_1/")
@XmlRootElement(name = "getServiceOrdersResponse")
@XStreamAlias("getServiceOrdersResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetServiceOrdersResponse implements IAPIResponse {
		
		@XmlElement(name="results")
		private Results results;
		
		@XStreamAlias("serviceOrders")
		private ServiceOrders serviceOrders;
		
		
		public Results getResults() {
			return results;
		}

		public void setResults(Results results) {
			this.results = results;
		}
		
		public void setVersion(String version) {
			// TODO Auto-generated method stub
			
		}

		public void setSchemaLocation(String schemaLocation) {
			// TODO Auto-generated method stub
			
		}

		public void setNamespace(String namespace) {
			// TODO Auto-generated method stub
			
		}

		public void setSchemaInstance(String schemaInstance) {
			// TODO Auto-generated method stub
			
		}

		public ServiceOrders getServiceOrders() {
			return serviceOrders;
		}

		public void setServiceOrders(ServiceOrders serviceOrders) {
			this.serviceOrders = serviceOrders;
		}
			
}
