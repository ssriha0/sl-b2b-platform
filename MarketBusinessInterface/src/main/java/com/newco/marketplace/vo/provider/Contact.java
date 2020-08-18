package com.newco.marketplace.vo.provider;

import java.sql.Timestamp;


public class Contact extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8031022382803513731L;
	private Integer contactId= -1;
	private String lastName= "";
	private String firstName="";
	private String mi="";
	private String suffix="";
	private String title="";
	private String role = null;
	private String phoneNo="";
	private String faxNo="";
	private String cellNo="";
	private String pagerText="";
	private String email="";
	private String altEmail="";
	private Integer contactGroup=-1;
	private Integer contactMethodId=-1;
	private Timestamp createdDate = null;
	private Timestamp modifiedDate = null;
    private String honorific = null;
    private String userName = null;
    private String ext = "";
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCellNo() {
		return cellNo;
	}
	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}
	public Integer getContactGroup() {
		return contactGroup;
	}
	public void setContactGroup(Integer contactGroup) {
		this.contactGroup = contactGroup;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public Integer getContactMethodId() {
		return contactMethodId;
	}
	public void setContactMethodId(Integer contactMethodId) {
		this.contactMethodId = contactMethodId;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMi() {
		return mi;
	}
	public void setMi(String mi) {
		this.mi = mi;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getPagerText() {
		return pagerText;
	}
	public void setPagerText(String pagerText) {
		this.pagerText = pagerText;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
    /**
     * @return the honorific
     */
    public String getHonorific() {
        return honorific;
    }
    /**
     * @param honorific the honorific to set
     */
    public void setHonorific(String honorific) {
        this.honorific = honorific;
    }
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAltEmail() {
		return altEmail;
	}
	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	
		
	}
