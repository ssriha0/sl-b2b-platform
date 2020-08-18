package com.newco.marketplace.webservices.dto.serviceorder;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;
import org.codehaus.xfire.aegis.type.java5.XmlElement;

import com.sears.os.vo.request.ABaseServiceRequestVO;

public abstract class ABaseWebserviceRequest extends ABaseServiceRequestVO {
	
	private Integer buyerId;
	private String passwordFlag;
	
	@XmlElement(minOccurs="1", nillable=false)
	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	@Override
	@XmlElement(minOccurs="1", nillable=false)
	public String getPassword() {
		return super.getPassword();
	}
	
	
	@Override
	@XmlElement(minOccurs="1", nillable=false)
	public String getUserId(){
		return super.getUserId();
	}
	
	@Override
	@IgnoreProperty
	public String getClientId() {
		return super.clientId;
	}
	
	@Override
	public String toString() {
		return null;
	}

	public String getPasswordFlag() {
		return passwordFlag;
	}

	public void setPasswordFlag(String passwordFlag) {
		this.passwordFlag = passwordFlag;
	}

}
