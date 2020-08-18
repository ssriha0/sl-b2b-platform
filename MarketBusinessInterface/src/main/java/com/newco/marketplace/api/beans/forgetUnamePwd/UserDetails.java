package com.newco.marketplace.api.beans.forgetUnamePwd;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("userDetails")
public class UserDetails {

	public UserDetails(List<UserDetail> userList){
		this.userList = userList;
	}
	
	public UserDetails(){
		
	}
	@XStreamImplicit(itemFieldName="userDetail")
	private List<UserDetail> userList;

	/**
	 * @return the userList
	 */
	public List<UserDetail> getUserList() {
		return userList;
	}

	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<UserDetail> userList) {
		this.userList = userList;
	}

	
	
}
