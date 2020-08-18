/**
 * 
 */
package com.newco.marketplace.vo.provider;

import java.util.Date;


/**
 * @author KSudhanshu
 *
 */
public class LoginVO extends BaseVO {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8395466455994129928L;
	private String username;
    private String isTempPassword;
    private String lockedInd;
    private String unsuccessLoginInd;
    private Integer role = -1;
    private Date modDate = new Date();
    
   	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	/**
	 * @return the isTempPassword
	 */
	public String getIsTempPassword() {
		return isTempPassword;
	}

	/**
	 * @param isTempPassword the isTempPassword to set
	 */
	public void setIsTempPassword(String isTempPassword) {
		this.isTempPassword = isTempPassword;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
	public String getLockedInd() {
		return lockedInd;
	}

	public void setLockedInd(String lockedInd) {
		this.lockedInd = lockedInd;
	}

	public String getUnsuccessLoginInd() {
		return unsuccessLoginInd;
	}

	public void setUnsuccessLoginInd(String unsuccessLoginInd) {
		this.unsuccessLoginInd = unsuccessLoginInd;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}    
}