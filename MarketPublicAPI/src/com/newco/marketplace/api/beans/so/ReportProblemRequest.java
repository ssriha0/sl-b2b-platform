/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 19-Oct-2009	pgangra   SHC				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Clas representing request object for the request 
 * @author pgangra
 *
 */

@XSD(name = "ReportProblemRequest.xsd", path = "/resources/schemas/so/")
@XStreamAlias("reportProblemRequest")
public class ReportProblemRequest {
	
	@XStreamAlias("problemCode")
	private String problemCode;
	
	@XStreamAlias("problemDescription")
	private String problemDescription;
	
	@XStreamAlias("problemCodeEnum")
	private String problemCodeEnum;	

	public String getProblemCode() {
		return problemCode;
	}

	public void setProblemCode(String problemCode) {
		this.problemCode = problemCode;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	public String getProblemCodeEnum() {
		return problemCodeEnum;
	}

	public void setProblemCodeEnum(String problemCodeEnum) {
		this.problemCodeEnum = problemCodeEnum;
	}
	
}
