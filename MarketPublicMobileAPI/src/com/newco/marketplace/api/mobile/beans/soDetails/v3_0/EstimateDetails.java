package com.newco.marketplace.api.mobile.beans.soDetails.v3_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing Estimate information.
 * @author Infosys
 *
 */

@XStreamAlias("estimateDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstimateDetails {

	
	@XStreamImplicit(itemFieldName="estimate")
	private List<EstimateDetail> estimate;

	public List<EstimateDetail> getEstimate() {
		return estimate;
	}

	public void setEstimate(List<EstimateDetail> estimate) {
		this.estimate = estimate;
	}

}
