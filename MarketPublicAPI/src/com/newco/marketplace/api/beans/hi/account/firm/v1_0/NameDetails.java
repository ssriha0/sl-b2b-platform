
package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("nameDetails")
@XmlRootElement(name = "nameDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class NameDetails {

	@XStreamAlias("firstName")
    private String firstName;
	
	@XStreamAlias("middleName")
    private String middleName;
	
	@XStreamAlias("lastName")
    private String lastName;
	
	@XStreamAlias("nameSuffix")
    private String nameSuffix;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}
	
	

}
