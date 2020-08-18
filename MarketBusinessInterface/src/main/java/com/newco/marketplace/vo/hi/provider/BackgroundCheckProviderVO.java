package com.newco.marketplace.vo.hi.provider;

import java.util.Date;

public class BackgroundCheckProviderVO {
	
private Integer providerId;   
private Integer bgcheckId;
private Date verificationDate;
private Date reverificationDate;
private Date requestDate;
private Integer firmId;
private String bgRequestType;
private Integer bgStatus;


public Integer getBgcheckId() {
	return bgcheckId;
}
public void setBgcheckId(Integer bgcheckId) {
	this.bgcheckId = bgcheckId;
}
public Date getVerificationDate() {
	return verificationDate;
}
public void setVerificationDate(Date verificationDate) {
	this.verificationDate = verificationDate;
}
public Date getReverificationDate() {
	return reverificationDate;
}
public void setReverificationDate(Date reverificationDate) {
	this.reverificationDate = reverificationDate;
}
public Date getRequestDate() {
	return requestDate;
}
public void setRequestDate(Date requestDate) {
	this.requestDate = requestDate;
}

public Integer getProviderId() {
	return providerId;
}
public void setProviderId(Integer providerId) {
	this.providerId = providerId;
}
public Integer getFirmId() {
	return firmId;
}
public void setFirmId(Integer firmId) {
	this.firmId = firmId;
}
public String getBgRequestType() {
	return bgRequestType;
}
public void setBgRequestType(String bgRequestType) {
	this.bgRequestType = bgRequestType;
}
public Integer getBgStatus() {
	return bgStatus;
}
public void setBgStatus(Integer bgStatus) {
	this.bgStatus = bgStatus;
}


}
