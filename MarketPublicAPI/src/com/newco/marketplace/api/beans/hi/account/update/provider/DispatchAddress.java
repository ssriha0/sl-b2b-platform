


package com.newco.marketplace.api.beans.hi.account.update.provider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("address")
@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
public class DispatchAddress {

	@XStreamAlias("streetName1")
    private String streetName1;
    
	@XStreamAlias("streetName2")
    private String streetName2;
    
	@XStreamAlias("aptNo")
    private String aptNo;
    
	@XStreamAlias("city")
    private String city;
    
	@XStreamAlias("state")
    private String state;
    
	@XStreamAlias("zip")
    private String zip;

   
    public String getStreetName1() {
        return streetName1;
    }

    
    public void setStreetName1(String value) {
        this.streetName1 = value;
    }

   
    public String getStreetName2() {
        return streetName2;
    }

   
    public void setStreetName2(String value) {
        this.streetName2 = value;
    }

    
    public String getAptNo() {
        return aptNo;
    }

    
    public void setAptNo(String value) {
        this.aptNo = value;
    }

 
    public String getCity() {
        return city;
    }

   
    public void setCity(String value) {
        this.city = value;
    }

    
    public String getState() {
        return state;
    }

   
    public void setState(String value) {
        this.state = value;
    }

    
    public String getZip() {
        return zip;
    }

 
    public void setZip(String value) {
        this.zip = value;
    }

}
