

package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "mailingAddress")
@XStreamAlias("mailingAddress")
@XmlAccessorType(XmlAccessType.FIELD)
public class MailingAddress {

	@XStreamAlias("mailingStreet1")
    private String mailingStreet1;
	
	@XStreamAlias("mailingStreet2")
    private String mailingStreet2;
	
	@XStreamAlias("mailingCity")
    private String mailingCity;
	
	@XStreamAlias("mailingState")
    private String mailingState;
	
	@XStreamAlias("mailingZip")
    private String mailingZip;
	
	@XStreamAlias("mailingAprt")
    private String mailingAprt;


    public String getmailingStreet1() {
        return mailingStreet1;
    }


    public void setmailingStreet1(String value) {
        this.mailingStreet1 = value;
    }

    public String getmailingStreet2() {
        return mailingStreet2;
    }


    public void setmailingStreet2(String value) {
        this.mailingStreet2 = value;
    }


    public String getmailingCity() {
        return mailingCity;
    }


    public void setmailingCity(String value) {
        this.mailingCity = value;
    }


    public String getmailingState() {
        return mailingState;
    }


    public void setmailingState(String value) {
        this.mailingState = value;
    }

    public String getmailingZip() {
        return mailingZip;
    }


    public void setmailingZip(String value) {
        this.mailingZip = value;
    }


    public String getmailingAprt() {
        return mailingAprt;
    }

    public void setmailingAprt(String value) {
        this.mailingAprt = value;
    }

}
