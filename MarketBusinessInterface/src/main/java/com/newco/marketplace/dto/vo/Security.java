package com.newco.marketplace.dto.vo;

import com.sears.os.vo.SerializableBaseVO;

public class Security extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2411441066180703268L;
	public String username ="";
	public String password ="";
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
}//Security
