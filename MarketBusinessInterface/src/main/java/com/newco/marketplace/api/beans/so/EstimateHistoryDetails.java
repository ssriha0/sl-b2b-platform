package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of estimates.
 * @author Infosys
 *
 */
@XStreamAlias("estimateHistoryDetails")
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
