package com.newco.marketplace.api.mobile.beans.viewDashboard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/10
* for fetching response of view dashboard providers forSO for mobile
*
*/
@XSD(name="viewDashboardResponse.xsd", path="/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "viewDashboardResponse")
@XStreamAlias("viewDashboardResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ViewDashboardResponse implements IAPIResponse {

	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("tabs")
	private Tabs tabs;
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}
    public void setVersion(String version) {}

	public void setSchemaLocation(String schemaLocation) {}

	public void setNamespace(String namespace) {}

	public void setSchemaInstance(String schemaInstance) {}

	public Tabs getTabs() {
		return tabs;
	}

	public void setTabs(Tabs tabs) {
		this.tabs = tabs;
	}

}
