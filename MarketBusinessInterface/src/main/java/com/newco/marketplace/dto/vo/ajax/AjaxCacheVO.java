package com.newco.marketplace.dto.vo.ajax;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;


public class AjaxCacheVO  extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 855130086622187009L;
	private String userName = "";
	private String roleType = "";
	private Integer companyId = -1; //Provider Admin or Buyer Admin
	private Integer vendBuyerResId = -1; //Provider Resource or Buyer Resource
	private Integer routedTabCount = -1;
	private boolean resubmitGrid = false;
	private boolean dataChanged = false;
	private ArrayList<SOToken> soTokenList;
	private boolean manageSoFlag;
	//This is created for Order Management SL-15642
	private boolean viewOrderPricing;

	
	
	
	public boolean getDataChanged() {
		return dataChanged;
	}
	public void setDataChanged(boolean dataChanged) {
		this.dataChanged = dataChanged;
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
	public Integer getRoutedTabCount() {
		return routedTabCount;
	}
	public void setRoutedTabCount(Integer routedTabCount) {
		this.routedTabCount = routedTabCount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getVendBuyerResId() {
		return vendBuyerResId;
	}
	public void setVendBuyerResId(Integer vendBuyerResId) {
		this.vendBuyerResId = vendBuyerResId;
	}
	public ArrayList<SOToken> getSoTokenList() {
		return soTokenList;
	}
	public void setSoTokenList(ArrayList<SOToken> soTokenList) {
		this.soTokenList = soTokenList;
	}
	public boolean getResubmitGrid() {
		return resubmitGrid;
	}
	public void setResubmitGrid(boolean resubmitGrid) {
		this.resubmitGrid = resubmitGrid;
	}
	public boolean isManageSoFlag() {
		return manageSoFlag;
	}
	public void setManageSoFlag(boolean manageSoFlag) {
		this.manageSoFlag = manageSoFlag;
	}
	public boolean isViewOrderPricing() {
		return viewOrderPricing;
	}
	public void setViewOrderPricing(boolean viewOrderPricing) {
		this.viewOrderPricing = viewOrderPricing;
	}
	
	
	
	
}