package com.newco.marketplace.api.mobile.beans.feedback;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="feedbackRequest.xsd", path="/resources/schemas/mobile/v2_0/")
@XmlRootElement(name="feedbackRequest")
@XStreamAlias("feedbackRequest")
public class FeedbackRequest {

	@XStreamAlias("userName")
	private String userName;
	
	@XStreamAlias("email")
	private String email;
	
	@XStreamAlias("comments")   
	private String comments;
	
	@XStreamAlias("deviceVersion")   
	private String deviceVersion;

	@XStreamAlias("sourceVersion")   
	private String sourceVersion;

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getSourceVersion() {
		return sourceVersion;
	}

	public void setSourceVersion(String sourceVersion) {
		this.sourceVersion = sourceVersion;
	}

	
	
}
