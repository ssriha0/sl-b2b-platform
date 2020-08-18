package com.newco.marketplace.criteria;

public class OrderCriteria implements ICriteria {

	private static final long serialVersionUID = 6375351247242281121L;
	
	private Integer companyId;
	private Integer statusId[];
	private Integer subStatusId;	
	private Integer vendBuyerResId;
	private String roleType;
	private Integer roleId;
	private String serviceProName;
	private String marketName;
	private Integer adoptedRoleId;
	private String priceModel;
	
	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public boolean isSet() {
		// TODO Auto-generated method stub
		return false;
	}
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Integer[] getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer[] statusId) {
		this.statusId = statusId;
	}
	public Integer getSubStatusId() {
		return subStatusId;
	}
	public void setSubStatusId(Integer subStatusId) {
		this.subStatusId = subStatusId;
	}
	public Integer getVendBuyerResId() {
		return vendBuyerResId;
	}
	public void setVendBuyerResId(Integer vendBuyerResId) {
		this.vendBuyerResId = vendBuyerResId;
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
	public Integer getAdoptedRoleId() {
		return adoptedRoleId;
	}
	public void setAdoptedRoleId(Integer adoptedRoleId) {
		this.adoptedRoleId = adoptedRoleId;
	}
	public String getPriceModel()
	{
		return priceModel;
	}
	public void setPriceModel(String priceModel)
	{
		this.priceModel = priceModel;
	}
}
