/**
 * 
 */
package com.newco.marketplace.web.dto.provider;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.dto.vo.provider.TeamMemberVO;

/**
 * @author MTedder
 *
 */
public class BackgroundCheckDTO extends BaseDto{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List <TeamMemberVO>teamMemberList= null;
    private String lastName = "";
    private String firstName = "";
    private String userName="";
    private int count=0;
    private String email="";
    private String confirmEmail="";
    private String emailAlt="";
    private String confirmEmailAlt="";
    private String sendResultsPrimaryCoContact="";
    private Integer resourceId = 0;
    private String backgroundConfirmInd ="";//MTedder - String here but Integer in VO (mapper occurs in TeamMemberMapper)
    private int bgCheckStatus = 0;
    private String recertify="";
    String encryptedPlusOneKey = null;
    String backgroundCheckStatus = null;
    String plusOneUrl = null;
    String action;
    
    boolean isBackgroundCheckShared;
    boolean isBackgroundCheckRecertify;
    
    boolean backgroundCheckRecertifyShared;
    
    //R11_1
  	//Jira SL-20434
    private String encryptedResourceIdSsn;
 
    //R12_2
    //SL-20553
    private Integer originalResourceId = 0;
	 
	public String getEncryptedResourceIdSsn() {
		return encryptedResourceIdSsn;
	}
	public void setEncryptedResourceIdSsn(String encryptedResourceIdSsn) {
		this.encryptedResourceIdSsn = encryptedResourceIdSsn;
	}
	public boolean isBackgroundCheckRecertifyShared() {
		return backgroundCheckRecertifyShared;
	}
	public void setBackgroundCheckRecertifyShared(
			boolean backgroundCheckRecertifyShared) {
		this.backgroundCheckRecertifyShared = backgroundCheckRecertifyShared;
	}
	

    String bgdCheckStatus;
    Date certificationDate;
    Date recertificationDate;
    Integer bcCheckId;
    
    public String getRecertify() {
		return recertify;
	}

	public void setRecertify(String recertify) {
		this.recertify = recertify;
	}

	//BNatara - Added Provider UserName
    private String provUserName = null;

    public String getProvUserName() {
		return provUserName;
	}

	public void setProvUserName(String provUserName) {
		this.provUserName = provUserName;
	}

	public int getCount() {
    	
    	if(teamMemberList!=null){
    		count=teamMemberList.size();
    	}
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
     * @return the teamMembers
     */
    public List <TeamMemberVO> getTeamMemberList() {
        return teamMemberList;
    }

    /**
     * @param teamMembers the teamMembers to set
     */
    public void setTeamMemberList(List <TeamMemberVO> teamMembers) {
        this.teamMemberList = teamMembers;
    }

	/**
	 * @return the email
	 */
	public String getEmail() {
		if (this.email !=null)
			return email;
		else return "";
		
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the emailAlt
	 */
	public String getEmailAlt() {
		return emailAlt;
	}

	/**
	 * @param emailAlt the emailAlt to set
	 */
	public void setEmailAlt(String emailAlt) {
		this.emailAlt = emailAlt;
	}

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

	/**
	 * @return the confirmEmail
	 */
	public String getConfirmEmail() {
		return confirmEmail;
	}

	/**
	 * @param confirmEmail the confirmEmail to set
	 */
	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	/**
	 * @return the confirmEmailAlt
	 */
	public String getConfirmEmailAlt() {
		return confirmEmailAlt;
	}

	/**
	 * @param confirmEmailAlt the confirmEmailAlt to set
	 */
	public void setConfirmEmailAlt(String confirmEmailAlt) {
		this.confirmEmailAlt = confirmEmailAlt;
	}

	/**
	 * @return the sendResultsPrimaryCoContact
	 */
	public String getSendResultsPrimaryCoContact() {
		return sendResultsPrimaryCoContact;
	}

	/**
	 * @param sendResultsPrimaryCoContact the sendResultsPrimaryCoContact to set
	 */
	public void setSendResultsPrimaryCoContact(String sendResultsPrimaryCoContact) {
		this.sendResultsPrimaryCoContact = sendResultsPrimaryCoContact;
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
	 * @return the backgroundConfirmInd
	 */
	public String getBackgroundConfirmInd() {
		return backgroundConfirmInd;
	}

	/**
	 * @param backgroundConfirmInd the backgroundConfirmInd to set
	 */
	public void setBackgroundConfirmInd(String backgroundConfirmInd) {
		this.backgroundConfirmInd = backgroundConfirmInd;
	}

	public int getBgCheckStatus() {
		return bgCheckStatus;
	}

	public void setBgCheckStatus(int bgCheckStatus) {
		this.bgCheckStatus = bgCheckStatus;
	}

	public String getEncryptedPlusOneKey() {
		return encryptedPlusOneKey;
	}

	public void setEncryptedPlusOneKey(String encryptedPlusOneKey) {
		this.encryptedPlusOneKey = encryptedPlusOneKey;
	}

	public String getBackgroundCheckStatus() {
		return backgroundCheckStatus;
	}

	public void setBackgroundCheckStatus(String backgroundCheckStatus) {
		this.backgroundCheckStatus = backgroundCheckStatus;
	}

	public String getPlusOneUrl() {
		return plusOneUrl;
	}

	public void setPlusOneUrl(String plusOneUrl) {
		this.plusOneUrl = plusOneUrl;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getBgdCheckStatus() {
		return bgdCheckStatus;
	}

	public void setBgdCheckStatus(String bgdCheckStatus) {
		this.bgdCheckStatus = bgdCheckStatus;
	}

	public Date getCertificationDate() {
		return certificationDate;
	}

	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}

	public Date getRecertificationDate() {
		return recertificationDate;
	}

	public void setRecertificationDate(Date recertificationDate) {
		this.recertificationDate = recertificationDate;
	}

	

	public boolean isBackgroundCheckShared() {
		return isBackgroundCheckShared;
	}

	public void setBackgroundCheckShared(boolean isBackgroundCheckShared) {
		this.isBackgroundCheckShared = isBackgroundCheckShared;
	}

	public Integer getBcCheckId() {
		return bcCheckId;
	}

	public void setBcCheckId(Integer bcCheckId) {
		this.bcCheckId = bcCheckId;
	}

	public boolean isBackgroundCheckRecertify() {
		return isBackgroundCheckRecertify;
	}

	public void setBackgroundCheckRecertify(boolean isBackgroundCheckRecertify) {
		this.isBackgroundCheckRecertify = isBackgroundCheckRecertify;
	}
	public Integer getOriginalResourceId() {
		return originalResourceId;
	}
	public void setOriginalResourceId(Integer originalResourceId) {
		this.originalResourceId = originalResourceId;
	}
	
	
	
}
