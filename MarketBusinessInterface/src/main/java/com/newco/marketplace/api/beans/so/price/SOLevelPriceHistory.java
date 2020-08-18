package com.newco.marketplace.api.beans.so.price;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("orderPriceHistory")
public class SOLevelPriceHistory {
	
	@XStreamImplicit(itemFieldName="orderPriceHistoryRecord")
	private List<SOLevelpriceHistoryRecord> soLevelPriceHistoryRecord;

	public List<SOLevelpriceHistoryRecord> getSoLevelPriceHistoryRecord() {
		return soLevelPriceHistoryRecord;
	}

	public void setSoLevelPriceHistoryRecord(
			List<SOLevelpriceHistoryRecord> soLevelPriceHistoryRecord) {
		this.soLevelPriceHistoryRecord = soLevelPriceHistoryRecord;
	}
	
}
