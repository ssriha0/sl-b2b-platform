package com.newco.marketplace.api.beans.provider.capacity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XSD(name = "availableTimeSlotsResponse.xsd", path = "/resources/schemas/provider/capacity/v1_1/")
@XmlRootElement(name = "availableTimeSlotsResponse")
@XStreamAlias("availableTimeSlotsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvailableTimeSlotsResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("availableTimeSlotList")
	@XStreamImplicit(itemFieldName="availableTimeSlotList")
	private List<AvailableTimeSlotList> availableTimeSlotList;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}



	public List<AvailableTimeSlotList> getAvailableTimeSlotList() {
		return availableTimeSlotList;
	}

	public void setAvailableTimeSlotList(
			List<AvailableTimeSlotList> availableTimeSlotList) {
		this.availableTimeSlotList = availableTimeSlotList;
	}

	public void setVersion(String version) {
	}

	public void setSchemaLocation(String schemaLocation) {
	}

	public void setNamespace(String namespace) {
	}

	public void setSchemaInstance(String schemaInstance) {
	}
}
  
