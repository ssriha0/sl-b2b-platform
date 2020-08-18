

package com.newco.marketplace.api.beans.hi.account.update.provider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("backgroundCheck")
@XmlRootElement(name = "backgroundCheck")
@XmlAccessorType(XmlAccessType.FIELD)
public class BackgroundCheck {

   
	@XStreamAlias("backgroundCheckStatus")
    protected String backgroundCheckStatus;
   
	@XStreamAlias("verificationDate")
    protected String verificationDate;
   
	@XStreamAlias("reverificationDate")
    protected String reverificationDate;
	
	@XStreamAlias("requestDate")
    protected String requestDate;


	public String getBackgroundCheckStatus() {
		return backgroundCheckStatus;
	}


	public void setBackgroundCheckStatus(String backgroundCheckStatus) {
		this.backgroundCheckStatus = backgroundCheckStatus;
	}


	public String getVerificationDate() {
		return verificationDate;
	}


	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}


	public String getReverificationDate() {
		return reverificationDate;
	}


	public void setReverificationDate(String reverificationDate) {
		this.reverificationDate = reverificationDate;
	}


	public String getRequestDate() {
		return requestDate;
	}


	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

    

}
