package com.servicelive.scmaudit;

import java.util.Set;

public class EmailBean {
	
	private String fromAddress;
	private Set<String> toAddress;
	private String ccAddress;
	private String subject;
	private String body;

	public EmailBean(String fromAddress, Set<String> toAddress, String ccAddress, String subject, String body) {
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.ccAddress = ccAddress;
		this.subject = subject;
		this.body = body;
	}

	public String getFromAddress() {
		return this.fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public Set<String> getToAddress() {
		return this.toAddress;
	}

	public void setToAddress(Set<String> toAddress) {
		this.toAddress = toAddress;
	}

	public String getCcAddress() {
		return this.ccAddress;
	}

	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
