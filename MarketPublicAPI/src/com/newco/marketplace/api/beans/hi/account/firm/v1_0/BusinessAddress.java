
package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("businessAddress")
@XmlRootElement(name = "businessAddress")
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessAddress {

	@XStreamAlias("businessStreet1")
    private String businessStreet1;
	
	@XStreamAlias("businessStreet2")
    private String businessStreet2;
	
	@XStreamAlias("businessCity")
    private String businessCity;
	
	@XStreamAlias("businessState")
    private String businessState;
	
	@XStreamAlias("businessZip")
    private String businessZip;
	
	@XStreamAlias("businessAprt")
    private String businessAprt;


    public String getBusinessStreet1() {
        return businessStreet1;
    }


    public void setBusinessStreet1(String value) {
        this.businessStreet1 = value;
    }


    public String getBusinessStreet2() {
        return businessStreet2;
    }


    public void setBusinessStreet2(String value) {
        this.businessStreet2 = value;
    }


    public String getBusinessCity() {
        return businessCity;
    }


    public void setBusinessCity(String value) {
        this.businessCity = value;
    }


    public String getBusinessState() {
        return businessState;
    }


    public void setBusinessState(String value) {
        this.businessState = value;
    }


    public String getBusinessZip() {
        return businessZip;
    }


    public void setBusinessZip(String value) {
        this.businessZip = value;
    }


    public String getBusinessAprt() {
        return businessAprt;
    }

    public void setBusinessAprt(String value) {
        this.businessAprt = value;
    }

}
