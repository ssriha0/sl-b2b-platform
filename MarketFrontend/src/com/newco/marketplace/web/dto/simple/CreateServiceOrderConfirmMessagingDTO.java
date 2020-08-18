/*
 * @(#)CreateServiceOrderConfirmMessagingDTO.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.web.dto.simple;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

/**
 * @author Mahmud Khair
 *
 */
public class CreateServiceOrderConfirmMessagingDTO extends SerializedBaseDTO{
	
	private String email;
	private String messageType;
	
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
	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}
	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

}
