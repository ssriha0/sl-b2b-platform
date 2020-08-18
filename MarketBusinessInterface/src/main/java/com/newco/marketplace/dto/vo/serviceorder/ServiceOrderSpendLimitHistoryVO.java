package com.newco.marketplace.dto.vo.serviceorder;

import java.util.List;
public class ServiceOrderSpendLimitHistoryVO {
private String soId;
private double currentPrice;
private Double spendLimitLabor;
private Double spendLimitParts;
private Integer buyerId;
private List<ServiceOrderSpendLimitHistoryListVO> historyVOlist;
public String getSoId() {
	return soId;
}
public void setSoId(String soId) {
	this.soId = soId;
}
public double getOriginalPrice() {
	return currentPrice;
}
public void setOriginalPrice(double originalPrice) {
	this.currentPrice = originalPrice;
}
public List<ServiceOrderSpendLimitHistoryListVO> getHistoryVOlist() {
	return historyVOlist;
}
public void setHistoryVOlist(
		List<ServiceOrderSpendLimitHistoryListVO> historyVOlist) {
	this.historyVOlist = historyVOlist;
}

public Double getSpendLimitLabor() {
	return spendLimitLabor;
}
public void setSpendLimitLabor(Double spendLimitLabor) {
	this.spendLimitLabor = spendLimitLabor;
}
public Double getSpendLimitParts() {
	return spendLimitParts;
}
public void setSpendLimitParts(Double spendLimitParts) {
	this.spendLimitParts = spendLimitParts;
}
public Integer getBuyerId() {
	return buyerId;
}
public void setBuyerId(Integer buyerId) {
	this.buyerId = buyerId;
}


}
