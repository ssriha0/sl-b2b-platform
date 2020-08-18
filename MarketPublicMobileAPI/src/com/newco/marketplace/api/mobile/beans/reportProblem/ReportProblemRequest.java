package com.newco.marketplace.api.mobile.beans.reportProblem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "reportProblemRequest.xsd", path = "/resources/schemas/mobile/v3_1/")
@XmlRootElement(name = "reportProblemRequest")
@XStreamAlias("reportProblemRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportProblemRequest {

	@XStreamAlias("problemCode")
	private Integer problemCode;
	
	@XStreamAlias("problemDescription")
	private String problemDescription;
	
	public Integer getProblemCode() {
		return problemCode;
	}
	public void setProblemCode(Integer problemCode) {
		this.problemCode = problemCode;
	}
	public String getProblemDescription() {
		return problemDescription;
	}
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
}
