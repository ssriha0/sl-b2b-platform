package com.newco.marketplace.dto.vo.cache.event;

import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.interfaces.AOPConstants;
import com.sears.os.vo.SerializableBaseVO;

public abstract class CacheEvent extends SerializableBaseVO{
	
	int buyerId;
	int buyerResourceId;
	int vendorId;
	int vendorResourceId;
	String soId;
	List<RoutedProvider> providerList;
	
	// elements for dynamic updates of Posted 
	boolean incrementCondAcceptCount = false;
	boolean incrementRejectCount = false;
	
	// elements to indicate actions on buyer tree
	boolean clearBuyerSummary = false;
	boolean clearBuyerDetails = false;
	boolean clearBuyerDashboardAmounts = false;

	// elements to indicate actions on provider tree	
	boolean clearAllProvidersSummary = false;
	boolean clearAllProvidersDetails = false;
	boolean clearAllProvidersDashboardAmount = false;
	
	// elements to indicate actions on provider tree	
	boolean clearProvidersSummary = false;
	boolean clearProvidersDetails = false;
	boolean clearProvidersDashboardAmount = false;
	
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public int getBuyerResourceId() {
		return buyerResourceId;
	}
	public void setBuyerResourceId(int buyerResourceId) {
		this.buyerResourceId = buyerResourceId;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public int getVendorResourceId() {
		return vendorResourceId;
	}
	public void setVendorResourceId(int vendorResourceId) {
		this.vendorResourceId = vendorResourceId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public boolean isClearBuyerSummary() {
		return clearBuyerSummary;
	}
	public void setClearBuyerSummary(boolean clearBuyerSummary) {
		this.clearBuyerSummary = clearBuyerSummary;
	}
	public boolean isClearBuyerDetails() {
		return clearBuyerDetails;
	}
	public void setClearBuyerDetails(boolean clearBuyerDetails) {
		this.clearBuyerDetails = clearBuyerDetails;
	}
	public boolean isClearBuyerDashboardAmounts() {
		return clearBuyerDashboardAmounts;
	}
	public void setClearBuyerDashboard(boolean clearBuyerDashboardAmounts) {
		this.clearBuyerDashboardAmounts = clearBuyerDashboardAmounts;
	}
	public boolean isClearAllProvidersSummary() {
		return clearAllProvidersSummary;
	}
	public void setClearAllProvidersSummary(boolean clearAllProvidersSummary) {
		this.clearAllProvidersSummary = clearAllProvidersSummary;
	}
	public boolean isClearAllProvidersDetails() {
		return clearAllProvidersDetails;
	}
	public void setClearAllProvidersDetails(boolean clearAllProvidersDetails) {
		this.clearAllProvidersDetails = clearAllProvidersDetails;
	}
	public boolean isClearAllProvidersDashboardAmount() {
		return clearAllProvidersDashboardAmount;
	}
	public void setClearAllProvidersDashboard(boolean clearAllProvidersDashboardAmount) {
		this.clearAllProvidersDashboardAmount = clearAllProvidersDashboardAmount;
	}
	public boolean isClearProvidersSummary() {
		return clearProvidersSummary;
	}
	public void setClearProvidersSummary(boolean clearProvidersSummary) {
		this.clearProvidersSummary = clearProvidersSummary;
	}
	public boolean isClearProvidersDetails() {
		return clearProvidersDetails;
	}
	public void setClearProvidersDetails(boolean clearProvidersDetails) {
		this.clearProvidersDetails = clearProvidersDetails;
	}
	public boolean isClearProvidersDashboardAmount() {
		return clearProvidersDashboardAmount;
	}
	public void setClearProvidersDashboard(boolean clearProvidersDashboardAmount) {
		this.clearProvidersDashboardAmount = clearProvidersDashboardAmount;
	}
	public List<RoutedProvider> getProviderList() {
		return providerList;
	}
	public void setProviderList(List<RoutedProvider> providerList) {
		this.providerList = providerList;
	}
	public boolean isIncrementCondAcceptCount() {
		return incrementCondAcceptCount;
	}
	public void setIncrementCondAcceptCount(boolean incrementCondAcceptCount) {
		this.incrementCondAcceptCount = incrementCondAcceptCount;
	}
	public boolean isIncrementRejectCount() {
		return incrementRejectCount;
	}
	public void setIncrementRejectCount(boolean incrementRejectCount) {
		this.incrementRejectCount = incrementRejectCount;
	}
	
	public boolean isUpdateBuyer() {
		return clearBuyerDetails || clearBuyerDashboardAmounts || clearBuyerSummary || 
			incrementCondAcceptCount || incrementRejectCount;
	}
	
	public boolean  isUpdateProvider() {
		return clearProvidersDashboardAmount || 
			clearProvidersDetails || clearProvidersSummary;
	}
	
	public boolean isUpdateAllProviders() {
		return clearAllProvidersDashboardAmount || clearAllProvidersDetails || 
				clearAllProvidersSummary;
	}
	
	public CacheEvent(){}
	
	public CacheEvent(HashMap<String, Object> params) {
		super();
		buyerId = getIntValue(params,AOPConstants.AOP_BUYER_ID);
		buyerResourceId = getIntValue(params,AOPConstants.AOP_BUYER_RESOURCE_ID);
		soId = getStringValue(params,AOPConstants.AOP_SO_ID);
		vendorId = getIntValue(params,AOPConstants.AOP_PROVIDER_ID);
		vendorResourceId = getIntValue(params,AOPConstants.AOP_VENDOR_RESOURCE_ID);
		providerList = (List<RoutedProvider>)params.get(AOPConstants.AOP_PROVIDER_LIST);

	}
	
	private int getIntValue(HashMap<String, Object> params,String key){
		Integer v=null;
		return ( v=(Integer) params.get(key))==null?-1:v.intValue();
	}
	
	private String getStringValue(HashMap<String, Object> params,String key){
		return (String) params.get(key);
	}
	
	@Override
	public String toString()
	{
		return "CacheEvent [buyerId=" + buyerId + ", buyerResourceId=" + buyerResourceId + ", clearAllProvidersDashboardAmount="
				+ clearAllProvidersDashboardAmount + ", clearAllProvidersDetails=" + clearAllProvidersDetails + ", clearAllProvidersSummary="
				+ clearAllProvidersSummary + ", clearBuyerDashboardAmounts=" + clearBuyerDashboardAmounts + ", clearBuyerDetails="
				+ clearBuyerDetails + ", clearBuyerSummary=" + clearBuyerSummary + ", clearProvidersDashboardAmount=" + clearProvidersDashboardAmount
				+ ", clearProvidersDetails=" + clearProvidersDetails + ", clearProvidersSummary=" + clearProvidersSummary
				+ ", incrementCondAcceptCount=" + incrementCondAcceptCount + ", incrementRejectCount=" + incrementRejectCount + ", "
				+ (providerList != null ? "providerList=" + providerList + ", " : "") + (soId != null ? "soId=" + soId + ", " : "") + "vendorId="
				+ vendorId + ", vendorResourceId=" + vendorResourceId + "]";
	}
}
