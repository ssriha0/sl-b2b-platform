package com.sears.os.vo.request;

import com.sears.os.vo.ABaseVO;

public abstract class ABaseServiceRequestVO extends ABaseVO{

	protected String clientId;
	protected String userId;
	protected String password;

	public abstract String toString();
	
	/**
	 * @return
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param string
	 */
	public void setClientId(String string) {
		clientId = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}
	/**
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param string
	 */
	public void setUserId(String string) {
		userId = string;
	}

}
