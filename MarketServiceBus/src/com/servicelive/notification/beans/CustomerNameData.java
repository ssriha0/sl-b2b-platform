package com.servicelive.notification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("customerNameData")
public class CustomerNameData {

	@XStreamAlias("firstName")
	private String firstName;
	
	@XStreamAlias("lastName")
	private String lastName;
	
	@XStreamAlias("namePrefix")
	private String namePrefix;
	
	@XStreamAlias("nameSuffix")
	private String nameSuffix;
	
	@XStreamAlias("secondName")
	private String secondName;

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

	public String getNamePrefix() {
		return namePrefix;
	}

	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
	
}
