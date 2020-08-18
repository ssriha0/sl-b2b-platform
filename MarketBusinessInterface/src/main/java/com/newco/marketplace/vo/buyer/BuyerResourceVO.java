package com.newco.marketplace.vo.buyer;


import java.util.ArrayList;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class BuyerResourceVO {

	/** firstName. */
	private String firstName;

	/** lastName. */
	private String lastName;
	
	  private Integer resourceId;
	  
	  private String userName;

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

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
