package com.newco.marketplace.vo.apiSearch;

public class SearchProviderRequestCriteria {
	
	private String id;
	private String phone;
	private String email;
	private String name;
	private String zipCode;
	private String city;
	private String state;
	
	private boolean invalidCriteria=false;
	private String message;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isInvalidCriteria() {
		return invalidCriteria;
	}
	public void setInvalidCriteria(boolean invalidCriteria) {
		this.invalidCriteria = invalidCriteria;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}