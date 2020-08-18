/*
** TeamMemberInformationForm.java  1.0     2007/06/06
*/
package com.newco.marketplace.dto.vo.provider;

import com.sears.os.vo.SerializableBaseVO;



/**
 * Struts Form bean for Team Member actions
 * 
 * @version
 * @author blars04
 *
 */
public class TMBackgroundCheckVO extends SerializableBaseVO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8067402207168577813L;
	String userName = null;
    String resourceId = null; // team member id 
    String resourceEmail = null; // team member's email 
    String backgroundCheckStatus = null;
    String vendorEmail = null;
    String wfEntity = null;
    
    public TMBackgroundCheckVO(String userName, String resourceId,
            String resourceEmail, String backgroundCheckStatus,
            String vendorEmail) {

        super();
        this.userName = userName;
        this.resourceId = resourceId;
        this.resourceEmail = resourceEmail;
        this.backgroundCheckStatus = backgroundCheckStatus;
        this.vendorEmail = vendorEmail;
    }

    public TMBackgroundCheckVO() {

        super();
    }
  
    public String getResourceId() {
    
        return resourceId;
    }
    public void setResourceId(String resourceID) {
    
        this.resourceId = resourceID;
    }
    public String getResourceEmail() {
    
        return resourceEmail;
    }
    public void setResourceEmail(String resourceIDEmail) {
    
        this.resourceEmail = resourceIDEmail;
    }
    public String getUserName() {
    
        return userName;
    }
    public void setUserName(String userName) {
    
        this.userName = userName;
    }
    public String getBackgroundCheckStatus() {
    
        return this.backgroundCheckStatus;
    }
    public void setBackgroundCheckStatus(String backgroundCheckStatus) {
    
        this.backgroundCheckStatus = backgroundCheckStatus;
    }
    public String getVendorEmail() {
    
        return vendorEmail;
    }
    public void setVendorEmail(String vendorEmail) {
    
        this.vendorEmail = vendorEmail;
    }

    @Override
	public String toString() {
        return ("<TMBCVO>" + " " + userName + " " + resourceId + " " + backgroundCheckStatus + " " + vendorEmail + " "+ resourceEmail+"</TMBCVO>");
    }

    public String getWfEntity() {
    
        return wfEntity;
    }

    public void setWfEntity(String wfEntity) {
    
        this.wfEntity = wfEntity;
    }

}//end class