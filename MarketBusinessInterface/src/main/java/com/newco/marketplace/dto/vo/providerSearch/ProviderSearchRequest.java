package com.newco.marketplace.dto.vo.providerSearch;

import com.sears.os.vo.SerializableBaseVO;

public class ProviderSearchRequest extends SerializableBaseVO{
	
	private static final long serialVersionUID = 418590600482056596L;
	private Integer id;
	private String providerEmail;
	private String providerPhone;
	private String providerName;
	private String providerZip;
	private String providerState;
	private String providerCity;
	
	public String getProviderEmail() {
		return providerEmail;
	}
	public void setProviderEmail(String providerEmail) {
		this.providerEmail = providerEmail;
	}
	public String getProviderPhone() {
		return providerPhone;
	}
	public void setProviderPhone(String providerPhone) {
		this.providerPhone = providerPhone;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getProviderZip() {
		return providerZip;
	}
	public void setProviderZip(String providerZip) {
		this.providerZip = providerZip;
	}
	public String getProviderState() {
		return providerState;
	}
	public void setProviderState(String providerState) {
		this.providerState = providerState;
	}
	public String getProviderCity() {
		return providerCity;
	}
	public void setProviderCity(String providerCity) {
		this.providerCity = providerCity;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
}