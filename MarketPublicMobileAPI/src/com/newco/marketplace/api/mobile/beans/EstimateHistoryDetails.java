package com.newco.marketplace.api.mobile.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of estimates.
 * @author Infosys
 *
 */
@XStreamAlias("estimateHistoryDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class EstimateHistoryDetails {
	@XStreamImplicit(itemFieldName="estimateHistory")
	private List<EstimateHistory> estimateHistory;
	

	public List<EstimateHistory> getEstimateHistory() {
		return estimateHistory;
	}

	public void setEstimateHistory(List<EstimateHistory> estimateHistory) {
		this.estimateHistory = estimateHistory;
	}



	


	

}
