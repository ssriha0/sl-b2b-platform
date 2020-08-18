package com.newco.marketplace.web.dto;

import java.util.List;

import com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO;

public class AdminMarketAdjFormDTO extends SerializedBaseDTO
{
	List<BuyerMarketAdjVO> marketList;
	String sortColumn;
	String sortOrder;

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<BuyerMarketAdjVO> getMarketList() {
		return marketList;
	}

	public void setMarketList(List<BuyerMarketAdjVO> marketList) {
		this.marketList = marketList;
	}
	
	
	
	
}
