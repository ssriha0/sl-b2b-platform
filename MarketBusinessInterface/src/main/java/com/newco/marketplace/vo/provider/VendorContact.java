/**
 * 
 */
package com.newco.marketplace.vo.provider;

/**
 * @author sahmad7
 */
public class VendorContact extends BaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8235606246595184065L;
	private Integer vendorContactId = new Integer(-1);
    private Integer vendorId = new Integer(-1);
    private Integer contactId = new Integer(-1);
//    private boolean  dispatchInd = false;
//    private boolean  ownerInd = false;
//    private boolean  adminInd = false;
//    private boolean primaryInd = false;
//    private boolean resourceInd = false;
    private Integer resourceId = new Integer(-1);
    
    private Integer dispatchInd=0;
    private Integer ownerInd=0;
    private Integer adminInd=0;
    private Integer primaryInd=0;
    private Integer resourceInd=0;
    
    
    /**
     * @return the dispatchInd
     */
    public int getDispatchInd() {
        return dispatchInd;
    }
    
    /**
     * @return the dispatchInd
     */
    public void setDispatchInd(Integer dispatchInd) {
        this.dispatchInd=dispatchInd;
    }
    
    /**
     * @return the ownerInd
     */
    public Integer getOwnerInd() {
        return ownerInd;
    }
    
    /**
     * @return the ownerInd
     */
    public void setOwnerInd(Integer ownerInd) {
        this.ownerInd=ownerInd;
    }
    
    /**
     * @return the adminInd
     */
    public Integer getAdminInd() {
        return adminInd;
    }
    
    /**
     * @return the adminInd
     */
    public void setAdminInd(Integer adminInd) {
        this.adminInd=adminInd;
    }
    
    
    /**
     * @return the primaryInd
     */
    public Integer getPrimaryInd() {
        return primaryInd;
    }
    
    /**
     * @return the primaryInd
     */
    public void setPrimaryInd(Integer primaryInd) {
        this.primaryInd=primaryInd;
    }
    
    /**
     * @return the resourceInd
     */
    public Integer getResourceInd() {
        return resourceInd;
    }
    
    /**
     * @return the resourceInd
     */
    public void setResourceInd(Integer resourceInd) {
        this.resourceInd=resourceInd;
    }
    
    
    
    
    
    
    
    
    
    
    
//    /**
//     * @return the adminInd
//     */
//    public boolean getAdminInd() {
//        return adminInd;
//    }
//    /**
//     * @return the adminInd
//     */
//    public boolean isAdminInd() {
//        return adminInd;
//    }
//    /**
//     * @param adminInd the adminInd to set
//     */
//    public void setAdminInd(boolean adminInd) {
//        this.adminInd = adminInd;
//    }
    /**
     * @return the contactId
     */
    public Integer getContactId() {
        return contactId;
    }
    /**
     * @param contactId the contactId to set
     */
    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

//    /**
//     * @return the dispatchInd
//     */
//    public boolean getDispatchInd() {
//        return dispatchInd;
//    }
//    /**
//     * @return the dispatchInd
//     */
//    public boolean isDispatchInd() {
//        return dispatchInd;
//    }
//    /**
//     * @param dispatchInd the dispatchInd to set
//     */
//    public void setDispatchInd(boolean dispatchInd) {
//        this.dispatchInd = dispatchInd;
//    }
    
//    /**
//     * @return the ownerInd
//     */
//    public boolean getOwnerInd() {
//        return ownerInd;
//    }
//    /**
//     * @return the ownerInd
//     */
//    public boolean isOwnerInd() {
//        return ownerInd;
//    }
//    /**
//     * @param ownerInd the ownerInd to set
//     */
//    public void setOwnerInd(boolean ownerInd) {
//        this.ownerInd = ownerInd;
//    }

//    /**
//     * @return the primaryInd
//     */
//    public boolean getPrimaryInd() {
//        return primaryInd;
//    }
//    /**
//     * @return the primaryInd
//     */
//    public boolean isPrimaryInd() {
//        return primaryInd;
//    }
//    /**
//     * @param primaryInd the primaryInd to set
//     */
//    public void setPrimaryInd(boolean primaryInd) {
//        this.primaryInd = primaryInd;
//    }
    /**
     * @return the resourceId
     */
    public Integer getResourceId() {
        return resourceId;
    }
    /**
     * @param resourceId the resourceId to set
     */
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

//    /**
//     * @return the resourceInd
//     */
//    public boolean getResourceInd() {
//        return resourceInd;
//    }
//    /**
//     * @return the resourceInd
//     */
//    public boolean isResourceInd() {
//        return resourceInd;
//    }
//    /**
//     * @param resourceInd the resourceInd to set
//     */
//    public void setResourceInd(boolean resourceInd) {
//        this.resourceInd = resourceInd;
//    }
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
    /**
     * @return the vendorId
     */
    public Integer getVendorId() {
        return vendorId;
    }
    /**
     * @param vendorId the vendorId to set
     */
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

}//end class