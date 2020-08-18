


package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("generalLiabilityDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class GeneralLiabilityDetails {


	
	@XStreamAlias("policyNumber")
    private String policyNumber;
	
	@XStreamAlias("carrierName")
    private String carrierName;
	
	@XStreamAlias("agencyName")
    private String agencyName;
	
	@XStreamAlias("agencyState")
    private String agencyState;
	
	@XStreamAlias("agencyCounty")
    private String agencyCounty;
	
	@XStreamAlias("issueDate")
    private String issueDate;
	
	@XStreamAlias("expirationDate")
    private String expirationDate;


 
   


    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String value) {
        this.policyNumber = value;
    }

 
    public String getCarrierName() {
        return carrierName;
    }

 
    public void setCarrierName(String value) {
        this.carrierName = value;
    }


    public String getAgencyName() {
        return agencyName;
    }


    public void setAgencyName(String value) {
        this.agencyName = value;
    }

    public String getAgencyState() {
        return agencyState;
    }


    public void setAgencyState(String value) {
        this.agencyState = value;
    }


    public String getAgencyCounty() {
        return agencyCounty;
    }


    public void setAgencyCounty(String value) {
        this.agencyCounty = value;
    }


    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String value) {
        this.issueDate = value;
    }


    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String value) {
        this.expirationDate = value;
    }

}
