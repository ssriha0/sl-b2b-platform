package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory;

import java.util.List;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("precallHistoryDetails")
public class PreCallHistoryDetails {
	
	@XStreamAlias("historyList")
	private List<PreCallHistory> historyList;

	public List<PreCallHistory> getHistoryList() {
		return historyList;
	}
	public void setHistoryList(List<PreCallHistory> historyList) {
		this.historyList = historyList;
	}
}
