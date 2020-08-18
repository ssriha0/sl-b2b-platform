

package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("w9Information")
@XmlRootElement(name = "w9Information")
@XmlAccessorType(XmlAccessType.FIELD)
public class W9Information {

	@XStreamAlias("legalBusinessName")
    private String legalBusinessName;
	
	@XStreamAlias("businessName")
    private String businessName;
	
	@XStreamAlias("taxStatus")
    private String taxStatus;
	
	@XStreamAlias("address1")
    private String address1;
	
	@XStreamAlias("address2")
    private String address2;
	
	@XStreamAlias("apartNo")
    private String apartNo;
	
	@XStreamAlias("city")
    private String city;
	
	@XStreamAlias("state")
    private String state;
	
	@XStreamAlias("zip")
    private String zip;
	
	@XStreamAlias("securityNoType")
    private String securityNoType;
	
	@XStreamAlias("taxPayerNo")
    private String taxPayerNo;
	
	@XStreamAlias("taxExemptInd")
    private Integer taxExemptInd;
	
	@XStreamAlias("legalTaxCheck")
    private Integer legalTaxCheck;
	
	@XStreamAlias("dateOfBirth")
    private String dateOfBirth;


    public String getLegalBusinessName() {
        return legalBusinessName;
    }


    public void setLegalBusinessName(String value) {
        this.legalBusinessName = value;
    }

    public String getBusinessName() {
        return businessName;
    }


    public void setBusinessName(String value) {
        this.businessName = value;
    }


    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String value) {
        this.taxStatus = value;
    }


    public String getAddress1() {
        return address1;
    }


    public void setAddress1(String value) {
        this.address1 = value;
    }


    public String getAddress2() {
        return address2;
    }


    public void setAddress2(String value) {
        this.address2 = value;
    }


    public String getApartNo() {
        return apartNo;
    }


    public void setApartNo(String value) {
        this.apartNo = value;
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

    public String getSecurityNoType() {
        return securityNoType;
    }

  
    public void setSecurityNoType(String value) {
        this.securityNoType = value;
    }

    
    public String getTaxPayerNo() {
        return taxPayerNo;
    }

   
    public void setTaxPayerNo(String value) {
        this.taxPayerNo = value;
    }

   
    public Integer getTaxExemptInd() {
        return taxExemptInd;
    }

    
    public void setTaxExemptInd(Integer value) {
        this.taxExemptInd = value;
    }

   
    public Integer getLegalTaxCheck() {
        return legalTaxCheck;
    }

    
    public void setLegalTaxCheck(Integer value) {
        this.legalTaxCheck = value;
    }

    
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    
    public void setDateOfBirth(String value) {
        this.dateOfBirth = value;
    }

}
