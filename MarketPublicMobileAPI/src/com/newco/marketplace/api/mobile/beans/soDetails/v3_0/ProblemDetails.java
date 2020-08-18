package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * This is a generic bean class for storing service order details.
 * @author Infosys
 *
 */
@XStreamAlias("problemDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProblemDetails {
	
	@XStreamAlias("problemType")
	private String problemType;
	
	@XStreamAlias("problemComments")
	private String problemComments;

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public String getProblemComments() {
		return problemComments;
	}

	public void setProblemComments(String problemComments) {
		this.problemComments = problemComments;
	}

}
