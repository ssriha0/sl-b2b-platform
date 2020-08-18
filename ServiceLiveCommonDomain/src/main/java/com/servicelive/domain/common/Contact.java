package com.servicelive.domain.common;
// default package


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.domain.BaseDomain;


/**
 * Contact entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="contact" )
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class Contact  extends BaseDomain {

	private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="contact_id", unique=true, nullable=false)
	private Integer contactId;

     @Column(name="last_name", length=50)
     private String lastName;

     @Column(name="first_name", length=50)
     private String firstName;

     @Column(name="mi", length=50)
     private String mi;

     @Column(name="suffix", length=10)
     private String suffix;

     @Column(name="title", length=50)
     private String title;

     @Column(name="phone_no", length=30)
     private String phoneNo;

     @Column(name="fax_no", length=30)
     private String faxNo;

     @Column(name="mobile_no", length=30)
     private String mobileNo;

     @Column(name="pager_text", length=30)
     private String pagerText;

     @Column(name="email")
     private String email;

     @Column(name="contact_group")
     private Integer contactGroup;

     @Column(name="contact_method_id")
     private Integer contactMethodId;

     @Column(name="modified_by", length=30)
     private String modifiedBy;
     
     @Column(name="honorific", length=5)
     private String honorific;

     @Column(name="email_alt")
     private String emailAlt;

     @Column(name="phone_no_ext", length=6)
     private String phoneNoExt;


     @Column(name="sms_no", length=30)
     private String smsNo;

     @Temporal(TemporalType.DATE)
     @Column(name="dob", length=10)
     private Date dob;



    // Constructors

    /** default constructor */
    public Contact() {
    	super();
    }

    /**
     * 
     * @return Integer
     */
    public Integer getContactId() {
        return this.contactId;
    }
    /**
     * 
     * @param contactId
     */
    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }
    /**
     * 
     * @return String
     */
    public String getLastName() {
        return this.lastName;
    }
    /**
     * 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @return String
     */
    public String getFirstName() {
        return this.firstName;
    }
    /**
     * 
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return String
     */
    public String getMi() {
        return this.mi;
    }
    /**
     * 
     * @param mi
     */
    public void setMi(String mi) {
        this.mi = mi;
    }

    /**
     * 
     * @return String
     */
    public String getSuffix() {
        return this.suffix;
    }
    /**
     * 
     * @param suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 
     * @return String
     */
    public String getTitle() {
        return this.title;
    }
    /**
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return String
     */
    public String getPhoneNo() {
        return this.phoneNo;
    }
    /**
     * 
     * @param phoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * 
     * @return String
     */
    public String getFaxNo() {
        return this.faxNo;
    }
    /**
     * 
     * @param faxNo
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    /**
     * 
     * @return String
     */
    public String getMobileNo() {
        return this.mobileNo;
    }
    /**
     * 
     * @param mobileNo
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * 
     * @return String
     */
    public String getPagerText() {
        return this.pagerText;
    }
    /**
     * 
     * @param pagerText
     */
    public void setPagerText(String pagerText) {
        this.pagerText = pagerText;
    }
    /**
     * 
     * @return String
     */
    public String getEmail() {
        return this.email;
    }
    /**
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return Integer
     */
    public Integer getContactGroup() {
        return this.contactGroup;
    }
    /**
     * 
     * @param contactGroup
     */
    public void setContactGroup(Integer contactGroup) {
        this.contactGroup = contactGroup;
    }

    /**
     * 
     * @return Integer
     */
    public Integer getContactMethodId() {
        return this.contactMethodId;
    }
    /**
     * 
     * @param contactMethodId
     */
    public void setContactMethodId(Integer contactMethodId) {
        this.contactMethodId = contactMethodId;
    }
    /**
     * 
     * @return String
     */
    public String getModifiedBy() {
        return this.modifiedBy;
    }
    /**
     * 
     * @param modifiedBy
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    /**
     * 
     * @return String
     */
    public String getHonorific() {
        return this.honorific;
    }
    /**
     * 
     * @param honorific
     */
    public void setHonorific(String honorific) {
        this.honorific = honorific;
    }

    /**
     * 
     * @return String
     */
    public String getEmailAlt() {
        return this.emailAlt;
    }
    /**
     * 
     * @param emailAlt
     */
    public void setEmailAlt(String emailAlt) {
        this.emailAlt = emailAlt;
    }

    /**
     * 
     * @return String
     */
    public String getPhoneNoExt() {
        return this.phoneNoExt;
    }
    /**
     * 
     * @param phoneNoExt
     */
    public void setPhoneNoExt(String phoneNoExt) {
        this.phoneNoExt = phoneNoExt;
    }

    /**
     * 
     * @return String
     */
    public String getSmsNo() {
        return this.smsNo;
    }
    /**
     * 
     * @param smsNo
     */
    public void setSmsNo(String smsNo) {
        this.smsNo = smsNo;
    }


    /**
     * 
     * @return Date
     */
    public Date getDob() {
        return this.dob;
    }

    /**
     * 
     * @param dob
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }
}