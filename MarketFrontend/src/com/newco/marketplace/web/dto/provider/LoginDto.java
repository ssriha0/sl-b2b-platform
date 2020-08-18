/**
 * 
 */
package com.newco.marketplace.web.dto.provider;



/**
 * @author KSudhanshu
 *
 */
public class LoginDto extends BaseDto {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6882182876085272906L;
	private String username;
	private String password;
    private String isTempPassword;
    private boolean rememberUserId;
    
   

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public boolean isRememberUserId() {
		return rememberUserId;
	}

	public void setRememberUserId(boolean rememberUserId) {
		this.rememberUserId = rememberUserId;
	}
}