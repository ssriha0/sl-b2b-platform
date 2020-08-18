/*
** TeamMemberInformationForm.java  1.0     2007/06/06
*/
package com.newco.marketplace.vo.provider;

import java.sql.Date;

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
	private static final long serialVersionUID = 3637996615801301443L;

	String userName = null;
    String resourceId = null; // team member id 
    String resourceEmail = null; // team member's email
    String resourceAltEmail = null; // bnatara - team member's alternate email
    String backgroundCheckStatus = null;
    String vendorEmail = null;
    String wfEntity = null;
    String firstName = null;
    String lastName = null;
    String encryptedSSN = null;
    String encryptedPlusOneKey = null;
    String ccArr[] = null;
    String plusOneKey = null;
    String backgroundVerificationDate = null;
    String backgroundRecertificationDate = null;
    String backgroundRequestDate = null;
    
    String ssn = null;
    Integer backgroundStateId;
    
    
    // add additional ratings.
    
    String overall;
    String criminal;
    String driving;
    String drug;
    String civil;
    
   //plusOne recertification Ind
    Boolean plusOneRecertificationInd;
  // background approve date
    Boolean recertificationInd;

    Date backgroundStatusUpdateDate;
    
    Boolean backgroundConfirmInd;
    Boolean backgroundNoConfirmInd;
    Integer bcDoneForvendorId;

    
    Integer techId;
    Integer firmId;
    String changedComment;
    String requestType;
    
    boolean change;
    String displayInd="Y";
    
    String screeningStatus;
    
    String recertificationStatus;
    Date requestDate;
    
  //R11_0 To set fourth parameter in Team Background Check Email
    private String recertificationIndParam;
    private Integer bgCheckId;
   
    private String recertBeforeExpiry;
    
    private Boolean updateRecertBeforeExpiry;
    
    
    //R11_1
  	//Jira SL-20434
      private String encryptedResourceIdSsn;

      
  	public String getBackgroundRequestDate() {
		return backgroundRequestDate;
	}
	public void setBackgroundRequestDate(String backgroundRequestDate) {
		this.backgroundRequestDate = backgroundRequestDate;
	}
	public String getEncryptedResourceIdSsn() {
  		return encryptedResourceIdSsn;
  	}
  	public void setEncryptedResourceIdSsn(String encryptedResourceIdSsn) {
  		this.encryptedResourceIdSsn = encryptedResourceIdSsn;
  	}
    
    

	public Boolean getUpdateRecertBeforeExpiry() {
		return updateRecertBeforeExpiry;
	}

	public void setUpdateRecertBeforeExpiry(Boolean updateRecertBeforeExpiry) {
		this.updateRecertBeforeExpiry = updateRecertBeforeExpiry;
	}

	public String getRecertBeforeExpiry() {
		return recertBeforeExpiry;
	}

	public void setRecertBeforeExpiry(String recertBeforeExpiry) {
		this.recertBeforeExpiry = recertBeforeExpiry;
	}

	public Integer getBgCheckId() {
		return bgCheckId;
	}

	public void setBgCheckId(Integer bgCheckId) {
		this.bgCheckId = bgCheckId;
	}

	public String getRecertificationIndParam() {
		return recertificationIndParam;
	}

	public void setRecertificationIndParam(String recertificationIndParam) {
		this.recertificationIndParam = recertificationIndParam;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getRecertificationStatus() {
		return recertificationStatus;
	}

	public void setRecertificationStatus(String recertificationStatus) {
		this.recertificationStatus = recertificationStatus;
	}

	public String getScreeningStatus() {
		return screeningStatus;
	}

	public void setScreeningStatus(String screeningStatus) {
		this.screeningStatus = screeningStatus;
	}

	public String getDisplayInd() {
		return displayInd;
	}

	public void setDisplayInd(String displayInd) {
		this.displayInd = displayInd;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getChangedComment() {
		return changedComment;
	}

	public void setChangedComment(String changedComment) {
		this.changedComment = changedComment;
	}

	public Boolean getRecertificationInd() {
		return recertificationInd;
	}

	public void setRecertificationInd(Boolean recertificationInd) {
		this.recertificationInd = recertificationInd;
	}

	public Integer getTechId() {
		return techId;
	}

	public void setTechId(Integer techId) {
		this.techId = techId;
	}

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public Boolean getBackgroundConfirmInd() {
		return backgroundConfirmInd;
	}

	public void setBackgroundConfirmInd(Boolean backgroundConfirmInd) {
		this.backgroundConfirmInd = backgroundConfirmInd;
	}

	public Boolean getBackgroundNoConfirmInd() {
		return backgroundNoConfirmInd;
	}

	public void setBackgroundNoConfirmInd(Boolean backgroundNoConfirmInd) {
		this.backgroundNoConfirmInd = backgroundNoConfirmInd;
	}

	public Integer getBcDoneForvendorId() {
		return bcDoneForvendorId;
	}

	public void setBcDoneForvendorId(Integer bcDoneForvendorId) {
		this.bcDoneForvendorId = bcDoneForvendorId;
	}

	

	public Date getBackgroundStatusUpdateDate() {
		return backgroundStatusUpdateDate;
	}

	public void setBackgroundStatusUpdateDate(Date backgroundStatusUpdateDate) {
		this.backgroundStatusUpdateDate = backgroundStatusUpdateDate;
	}

	public Boolean getPlusOneRecertificationInd() {
		return plusOneRecertificationInd;
	}

	public void setPlusOneRecertificationInd(Boolean plusOneRecertificationInd) {
		this.plusOneRecertificationInd = plusOneRecertificationInd;
	}

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEncryptedSSN() {
		return encryptedSSN;
	}

	public void setEncryptedSSN(String encryptedSSN) {
		this.encryptedSSN = encryptedSSN;
	}

	/**
	 * @return the encryptedPlusOneKey
	 */
	public String getEncryptedPlusOneKey() {
		return encryptedPlusOneKey;
	}

	/**
	 * @param encryptedPlusOneKey the encryptedPlusOneKey to set
	 */
	public void setEncryptedPlusOneKey(String encryptedPlusOneKey) {
		this.encryptedPlusOneKey = encryptedPlusOneKey;
	}

	public String getResourceAltEmail() {
		return resourceAltEmail;
	}

	public void setResourceAltEmail(String resourceAltEmail) {
		this.resourceAltEmail = resourceAltEmail;
	}

	public String[] getCcArr() {
		return ccArr;
	}

	public void setCcArr(String[] ccArr) {
		this.ccArr = ccArr;
	}

	public String getPlusOneKey() {
		return plusOneKey;
	}

	public void setPlusOneKey(String plusOneKey) {
		this.plusOneKey = plusOneKey;
	}

	public String getBackgroundVerificationDate() {
		return backgroundVerificationDate;
	}

	public void setBackgroundVerificationDate(String backgroundVerificationDate) {
		this.backgroundVerificationDate = backgroundVerificationDate;
	}

	public String getBackgroundRecertificationDate() {
		return backgroundRecertificationDate;
	}

	public void setBackgroundRecertificationDate(
			String backgroundRecertificationDate) {
		this.backgroundRecertificationDate = backgroundRecertificationDate;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Integer getBackgroundStateId() {
		return backgroundStateId;
	}

	public void setBackgroundStateId(Integer backgroundStateId) {
		this.backgroundStateId = backgroundStateId;
	}

	public String getOverall() {
		return overall;
	}

	public void setOverall(String overall) {
		this.overall = overall;
	}

	public String getCriminal() {
		return criminal;
	}

	public void setCriminal(String criminal) {
		this.criminal = criminal;
	}

	public String getDriving() {
		return driving;
	}

	public void setDriving(String driving) {
		this.driving = driving;
	}

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public String getCivil() {
		return civil;
	}

	public void setCivil(String civil) {
		this.civil = civil;
	}

	
	
	
	

	
	
}//end class