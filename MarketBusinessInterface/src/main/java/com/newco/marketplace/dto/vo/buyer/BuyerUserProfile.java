/**
 * 
 */
package com.newco.marketplace.dto.vo.buyer;

import com.newco.marketplace.vo.provider.BaseVO;
import com.newco.marketplace.vo.common.*;

/**
 * @author paugus2
 *
 */
public class BuyerUserProfile extends BaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2181850327294054057L;
	private String userName;
    private int contactId = -1;
    private int questionId = -1;
    private String password;
    private String answerTxt;
    private String createdDate;
    private String modifiedDate;
    private String firstName;
    private String lastName;
    private String roleName = null;
    private int roleId = -1;
    private int passwordFlag = 1;
    private Integer vendorId = new Integer(-1);
    private String email;
    //To cc alternate email of resource
    private String altEmail;
    private String modifiedBy = null;
    private int activeInd = 0;
    private InterimPasswordVO interimPassword;
    private int promotionalMailInd;
    
    //Added to get the Business name and resource id for a user
    private String businessName = null;
    private Integer resourceId = new Integer(-1);
    
    public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getAnswerTxt()
    {
        return answerTxt;
    }
    public void setAnswerTxt(String answerTxt)
    {
        this.answerTxt = answerTxt;
    }
    public int getContactId()
    {
        return contactId;
    }
    public void setContactId(int contactId)
    {
        this.contactId = contactId;
    }
    public String getCreatedDate()
    {
        return createdDate;
    }
    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }
    public String getModifiedDate()
    {
        return modifiedDate;
    }
    public void setModifiedDate(String modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public int getQuestionId()
    {
        return questionId;
    }
    public void setQuestionId(int questionId)
    {
        this.questionId = questionId;
    }
    public String getUserName()
    {
        return userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
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
    public String getRoleName() {
        return this.roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public int getRoleId() {
        return this.roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
	public int getPasswordFlag() {
		return passwordFlag;
	}
	public void setPasswordFlag(int passwordFlag) {
		this.passwordFlag = passwordFlag;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAltEmail() {
		return altEmail;
	}
	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public int getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(int activeInd) {
		this.activeInd = activeInd;
	}
	public InterimPasswordVO getInterimPassword() {
		return interimPassword;
	}
	public void setInterimPassword(InterimPasswordVO interimPassword) {
		this.interimPassword = interimPassword;
	}
	public int getPromotionalMailInd() {
		return promotionalMailInd;
	}
	public void setPromotionalMailInd(int promotionalMailInd) {
		this.promotionalMailInd = promotionalMailInd;
	}
}