package com.newco.marketplace.api.beans.hi.account.create.provider;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("address")
public class Address {

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

    /**
     * Gets the value of the streetName1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetName1() {
        return streetName1;
    }

    /**
     * Sets the value of the streetName1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetName1(String value) {
        this.streetName1 = value;
    }

    /**
     * Gets the value of the streetName2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetName2() {
        return streetName2;
    }

    /**
     * Sets the value of the streetName2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetName2(String value) {
        this.streetName2 = value;
    }

    /**
     * Gets the value of the aptNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAptNo() {
        return aptNo;
    }

    /**
     * Sets the value of the aptNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAptNo(String value) {
        this.aptNo = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the zip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the value of the zip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZip(String value) {
        this.zip = value;
    }

}
