/*
** TeamMemberVO.java    1.0     2007/06/06
*/
package com.newco.marketplace.dto.vo.provider;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author blars04
 *
 */
public class TeamMemberVO extends SerializableBaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7629970011437336644L;
	private String lastName = null;
    private String firstName = null;
    private String title = null;
    private String phoneNumber = null;
    private Integer vendorContactId = new Integer(-1);
    private Integer resourceStateId = new Integer(-1);
    private Integer backgroundCheckStateId = new Integer(-1);
    private Boolean owner = null;
    private String resourceState = null;
    private String backgroundCheckState = null;
    private String wfEntity = null;
    private Integer resourceId = new Integer(-1);
    private String editURL = null;
    private String fullName = null;
    private String ownerInd = null;
    private Integer totalSoCompleted = new Integer(0);
    
    /**
     * @return the backgroundCheckState
     */
    public String getBackgroundCheckState() {
        return backgroundCheckState;
    }
    /**
     * @param backgroundCheckState the backgroundCheckState to set
     */
    public void setBackgroundCheckState(String backgroundCheckState) {
        this.backgroundCheckState = backgroundCheckState;
    }
    /**
     * @return the backgroundCheckStateId
     */
    public Integer getBackgroundCheckStateId() {
        return backgroundCheckStateId;
    }
    /**
     * @param backgroundCheckStateId the backgroundCheckStateId to set
     */
    public void setBackgroundCheckStateId(Integer backgroundCheckStateId) {
        this.backgroundCheckStateId = backgroundCheckStateId;
    }
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return the owner
     */
    public Boolean getOwner() {
        return owner;
    }
    /**
     * @param owner the owner to set
     */
    public void setOwner(Boolean owner) {
        this.owner = owner;
    }
    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * @return the resourceState
     */
    public String getResourceState() {
        return resourceState;
    }
    /**
     * @param resourceState the resourceState to set
     */
    public void setResourceState(String resourceState) {
        this.resourceState = resourceState;
    }
    /**
     * @return the resourceStateId
     */
    public Integer getResourceStateId() {
        return resourceStateId;
    }
    /**
     * @param resourceStateId the resourceStateId to set
     */
    public void setResourceStateId(Integer resourceStateId) {
        this.resourceStateId = resourceStateId;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
    /**
     * @return the vendorResourceId
     */
    public Integer getResourceId() {
        return resourceId;
    }
    /**
     * @param vendorResourceId the vendorResourceId to set
     */
    public void setResourceId(Integer vendorResourceId) {
        this.resourceId = vendorResourceId;
    }
    
    public String getEditURL() {
        return this.editURL;
    }
    public void setEditURL(String editURL) {
        this.editURL = editURL;
    }
    
    public String getFullName() {
        return this.fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getOwnerInd() {
        return this.ownerInd;
    }
    public void setOwnerInd(String ownerInd) {
        this.ownerInd = ownerInd;
    }
    public String getWfEntity() {
    
        return wfEntity;
    }
    public void setWfEntity(String wfEntity) {
    
        this.wfEntity = wfEntity;
    }
	/**
	 * @return the totalSoCompleted
	 */
	public Integer getTotalSoCompleted() {
		return totalSoCompleted;
	}
	/**
	 * @param totalSoCompleted the totalSoCompleted to set
	 */
	public void setTotalSoCompleted(Integer totalSoCompleted) {
		this.totalSoCompleted = totalSoCompleted;
	}
	
}//end class