package com.newco.marketplace.api.beans.account.provider;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "testRequest")
public class TestRequest {
	
	private String id;
	private String idValue;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdValue() {
		return idValue;
	}
	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}
	

}
