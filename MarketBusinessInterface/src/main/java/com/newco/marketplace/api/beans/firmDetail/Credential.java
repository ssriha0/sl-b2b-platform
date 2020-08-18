package com.newco.marketplace.api.beans.firmDetail;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("credential")
@XmlAccessorType(XmlAccessType.FIELD)
public class Credential {

	@XStreamAlias("credentialType")
	private String credentialType;
	
	@XStreamAlias("type")
	private String type;
		
	@XStreamAlias("category")
	private String category;
	
	@XStreamAlias("source")
	private String source;
	
	@XStreamAlias("name")
	private String name;
	
	@XStreamAlias("status")
	private String status;
	
	@XStreamAlias("no")
	private String no;
	
	@XStreamAlias("issueDate")
	private String issueDate;
	
	@XStreamAlias("expiryDate")
	private String expiryDate;

	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	

}
