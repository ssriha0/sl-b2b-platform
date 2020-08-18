package com.newco.marketplace.criteria;

public class FilterCriteria implements ICriteria{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 4234449010345325456L;
	private Integer status[];
	private String subStatus;
	private Integer pbfilterId;
	private String pbfilterOpt;
	private boolean isSet = false;
	private String serviceProName;
	private Integer buyerRoleId;	
	private String marketName;
	private Integer buyerRefTypeId;
	private String buyerRefValue;
	private String priceModel;
	private String wfm_sodFlag;
	private String soId;
	private boolean manageSOFlag;
	private String resourceId;
	private String searchByBuyerId;
	
	public String getSearchByBuyerId() {
		return searchByBuyerId;
	}

	public void setSearchByBuyerId(String searchByBuyerId) {
		this.searchByBuyerId = searchByBuyerId;
	}

	public FilterCriteria(){}
	
	public FilterCriteria(Integer status[], String subStatus,String serviceProName,String marketName, Integer buyerRoleId){
		this.status =status;
		this.subStatus = subStatus;
		this.serviceProName = serviceProName;
		this.marketName = marketName;
		this.isSet = true;
		this.buyerRoleId = buyerRoleId;
	}
	
	public FilterCriteria(Integer status[], String subStatus,String serviceProName,String marketName, Integer buyerRoleId, String priceModel){
		this.status =status;
		this.subStatus = subStatus;
		this.serviceProName = serviceProName;
		this.marketName = marketName;
		this.isSet = true;
		this.buyerRoleId = buyerRoleId;
		this.priceModel = priceModel;
	}
	
	
	public boolean isSet() {
		// TODO Auto-generated method stub
		return isSet;
	}

	public void reset() {
		// TODO Auto-generated method stub
		setStatus(null);
		setSubStatus(null);
	}


	public Integer[] getStatus() {
		return status;
	}


	public void setStatus(Integer[] status) {
		this.status = status;
	}


	public String getSubStatus() {
		return subStatus;
	}


	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}


	public Integer getPbfilterId() {
		return pbfilterId;
	}


	public void setPbfilterId(Integer pbfilterId) {
		this.pbfilterId = pbfilterId;
	}

	public String getPbfilterOpt() {
		return pbfilterOpt;
	}

	public void setPbfilterOpt(String pbfilterOpt) {
		this.pbfilterOpt = pbfilterOpt;
	}

	public String getServiceProName() {
		return serviceProName;
	}

	public void setServiceProName(String serviceProName) {
		this.serviceProName = serviceProName;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Integer getBuyerRefTypeId() {
		return buyerRefTypeId;
	}

	public void setBuyerRefTypeId(Integer buyerRefTypeId) {
		this.buyerRefTypeId = buyerRefTypeId;
	}

	public String getBuyerRefValue() {
		return buyerRefValue;
	}

	public void setBuyerRefValue(String buyerRefValue) {
		this.buyerRefValue = buyerRefValue;
	}

	public Integer getBuyerRoleId()
	{
		return buyerRoleId;
	}

	public void setBuyerRoleId(Integer buyerRoleId)
	{
		this.buyerRoleId = buyerRoleId;
	}

	public void setPriceModel(String priceModel) {
		this.priceModel = priceModel;
	}

	public String getPriceModel() {
		return priceModel;
	}

	public String getWfm_sodFlag() {
		return wfm_sodFlag;
	}

	public void setWfm_sodFlag(String wfm_sodFlag) {
		this.wfm_sodFlag = wfm_sodFlag;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public boolean isManageSOFlag() {
		return manageSOFlag;
	}

	public void setManageSOFlag(boolean manageSOFlag) {
		this.manageSOFlag = manageSOFlag;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

}
