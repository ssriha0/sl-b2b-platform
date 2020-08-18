/*
** VendorContactLocation.java   1.0     2007/06/14
*/
package com.newco.marketplace.vo.provider;

import java.sql.Date;

/**
 * Value object for Vendor Contact Location
 * 
 * @version
 * @author blars04
 *
 */
public class ResourceLocation extends BaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2231034678061175563L;
	private Integer locationId = null;
    private Integer resourceId=null;
    private Integer buyerId = null;
    private Integer defaultLocnInd = 0;
    private Date createdDate = null;
    private Date modifiedDate = null;
    private String modifiedBy = null;
    

    public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
     * @return the locationId
     */
    public Integer getResourceId() {
        return resourceId;
    }
    /**
     * @param locationId the locationId to set
     */
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
      
    public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	/**
     * 
     */
    public ResourceLocation() 
    {
        super();
    }

    /**
     * @param vendorContactId
     * @param locationId
     */
    public ResourceLocation(Integer resourceId, Integer locationId) {
        super();
        this.resourceId = resourceId;
        this.locationId = locationId;
    }
    
    /**
     * @return the locationId
     */
    public Integer getLocationId() {
        return locationId;
    }
    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getDefaultLocnInd() {
		return defaultLocnInd;
	}
	public void setDefaultLocnInd(Integer defaultLocnInd) {
		this.defaultLocnInd = defaultLocnInd;
	}
}//end class