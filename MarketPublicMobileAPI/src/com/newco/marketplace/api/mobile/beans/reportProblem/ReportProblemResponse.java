package com.newco.marketplace.api.mobile.beans.reportProblem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XSD(name = "reportProblemResponse.xsd", path = "/resources/schemas/mobile/v3_1/")
@XmlRootElement(name = "reportProblemResponse")
@XStreamAlias("reportProblemResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportProblemResponse  implements IAPIResponse{
    
    @XStreamAlias("results")
	private Results results;
    
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results=results;
	}
    public void setVersion(String version) {}
    public void setSchemaLocation(String schemaLocation) {}
    public void setNamespace(String namespace) {}
    public void setSchemaInstance(String schemaInstance) {}

}
