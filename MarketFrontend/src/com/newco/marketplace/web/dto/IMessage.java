package com.newco.marketplace.web.dto;

import java.io.Serializable;

public interface IMessage extends Serializable{
	
	public String getMsg();
	
	public String getFieldId();
	
	public String getMsgType();

}
