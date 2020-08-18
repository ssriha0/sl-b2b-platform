/**
 * 
 */
package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;



/**
 * Value object for Service Order work flow  state transitions
 * 
 * @author langara
 *
 */
public class ServiceOrderWfStatesCountsVO extends SerializableBaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6639001226803039547L;
	private Integer buyerId = -1;
    private Integer vendorId = -1;
    private String  tabType = "";
    private Integer soCount = -1;
    private Integer roleSentTabCount = -1;
    private Integer leadCount = -1;
    private Integer count=0;
    
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getSoCount() {
		return soCount;
	}
	public void setSoCount(Integer soCount) {
		this.soCount = soCount;
	}

	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getTabType() {
		return tabType;
	}
	public void setTabType(String tabType) {
		this.tabType = tabType;
	}
	public Integer getRoleSentTabCount() {
		return roleSentTabCount;
	}
	public void setRoleSentTabCount(Integer roleSentTabCount) {
		this.roleSentTabCount = roleSentTabCount;
	}
	public Integer getLeadCount() {
		return leadCount;
	}
	public void setLeadCount(Integer leadCount) {
		this.leadCount = leadCount;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

    
    
    

	
    
	
	
	

}//end class