package com.newco.marketplace.dto.priceHistory;

import java.util.List;

import com.newco.marketplace.dto.vo.SoPriceHistoryVO;
import com.newco.marketplace.dto.vo.TaskLevelHistoryVO;

public class SoPriceHistoryDTO {
	private String timeZone;
	private String pricingType;
	private List<SoPriceHistoryVO> soLevelHistoryVo;

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public List<SoPriceHistoryVO> getSoLevelHistoryVo() {
		return soLevelHistoryVo;
	}

	public void setSoLevelHistoryVo(List<SoPriceHistoryVO> soLevelHistoryVo) {
		this.soLevelHistoryVo = soLevelHistoryVo;
	}
	
	public String getPricingType() {
		return pricingType;
	}

	public void setPricingType(String pricingType) {
		this.pricingType = pricingType;
	}

}
