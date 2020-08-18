/*
** VendorContactLocation.java   1.0     2007/06/14
*/
package com.newco.marketplace.vo.provider;

/**
 * Value object for Vendor Contact Location
 * 
 * @version
 * @author blars04
 *
 */
public class VendorContactLocation extends BaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7433655486134954285L;
	private Integer vendorContactId = null;
    private Integer locationId = null;
    private Integer resourceId=null;
    

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
    
    
    /**
     * 
     */
    public VendorContactLocation() 
    {
        super();
    }

    /**
     * @param vendorContactId
     * @param locationId
     */
    public VendorContactLocation(Integer vendorContactId, Integer locationId) {
        super();
        this.vendorContactId = vendorContactId;
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
    /**
     * @return the vendorContactId
     */
    public Integer getVendorContactId() {
        return vendorContactId;
    }
    /**
     * @param vendorContactId the vendorContactId to set
     */
    public void setVendorContactId(Integer vendorContactId) {
        this.vendorContactId = vendorContactId;
    }
    
    
}//end class