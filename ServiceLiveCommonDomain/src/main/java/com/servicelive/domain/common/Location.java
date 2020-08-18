package com.servicelive.domain.common;
// default package


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.domain.lookup.LookupLocationTypes;
import com.servicelive.domain.lookup.LookupStates;


/**
 * Location entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="location" )
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class Location  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="locn_id", unique=true, nullable=false)
    private Integer locnId;
     
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="state_cd")
    private LookupStates lookupStates;

	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="locn_type_id")
	private LookupLocationTypes lookupLocationTypes;
     
    @Column(name="street_1", length=40)
	private String street1;

    @Column(name="street_2", length=40)
    private String street2;

    @Column(name="city", length=30)
    private String city;

    @Column(name="zip", length=10)
    private String zip;

    @Column(name="zip4", length=10)
    private String zip4;
    
    @Column(name="country", length=30)
    private String country;
    
    @Column(name="locn_name")
    private String locnName;
    
    @Column(name="mailing_addr_ind")
    private Byte mailingAddrInd;
    
    @Column(name="gis_latitude", precision=22, scale=0)
    private Double gisLatitude;

    @Column(name="gis_longitude", precision=22, scale=0)
    private Double gisLongitude;
    
    @Temporal(TemporalType.DATE)
    @Column(name="created_date", nullable=false, length=19)
    private Date createdDate;
     
    @Temporal(TemporalType.DATE)
    @Column(name="modified_date", nullable=false, length=19)
    private Date modifiedDate;

    @Column(name="modified_by", length=30)
    private String modifiedBy;

	@Column(name="apt_no", length=20)
	private String aptNo;

    // Constructors

    /** default constructor */
    public Location() {
    	super();
    }

	/** 
	 * 
	 * @param createdDate
	 * @param modifiedDate
	 */
    public Location(Date createdDate, Date modifiedDate) {
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    /**
     * 
     * @return String
     */
    public String getAptNo() {
        return this.aptNo;
    }
    /**
     * 
     * @return String
     */
    public String getCity() {
        return this.city;
    }
    /**
     * 
     * @return String
     */
    public String getCountry() {
        return this.country;
    }
    /**
     * 
     * @return Date
     */
    public Date getCreatedDate() {
        return this.createdDate;
    }
    /**
     * 
     * @return Double
     */
    public Double getGisLatitude() {
        return this.gisLatitude;
    }
    /**
     * 
     * @return Double
     */
    public Double getGisLongitude() {
        return this.gisLongitude;
    }
    
    // Property accessors
    /**
     * 
     * @return Integer
     */
    public Integer getLocnId() {
        return this.locnId;
    }
    /**
     * 
     * @return String
     */
    public String getLocnName() {
        return this.locnName;
    }
    
    /**
     * 
     * @return LookupLocationTypes
     */
    public LookupLocationTypes getLookupLocationTypes() {
        return this.lookupLocationTypes;
    }
    /**
     * 
     * @return LookupStates
     */
    public LookupStates getLookupStates() {
        return this.lookupStates;
    }
    /**
     * 
     * @return Byte
     */
    public Byte getMailingAddrInd() {
        return this.mailingAddrInd;
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
     * @return Date
     */
    public Date getModifiedDate() {
        return this.modifiedDate;
    }
    /**
     * 
     * @return String
     */
    public String getStreet1() {
        return this.street1;
    }
    /**
     * 
     * @return String
     */
    public String getStreet2() {
        return this.street2;
    }
    /**
     * 
     * @return String
     */
    public String getZip() {
        return this.zip;
    }
    
    /**
     * 
     * @return String
     */
    public String getZip4() {
        return this.zip4;
    }
    /**
     * 
     * @param aptNo
     */
    public void setAptNo(String aptNo) {
        this.aptNo = aptNo;
    }
    /**
     * 
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * 
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * 
     * @param createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    /**
     * 
     * @param gisLatitude
     */
    public void setGisLatitude(Double gisLatitude) {
        this.gisLatitude = gisLatitude;
    }
    
    /**
     * 
     * @param gisLongitude
     */
    public void setGisLongitude(Double gisLongitude) {
        this.gisLongitude = gisLongitude;
    }
    /**
     * 
     * @param locnId
     */
    public void setLocnId(Integer locnId) {
        this.locnId = locnId;
    }
    
    /**
     * 
     * @param locnName
     */
    public void setLocnName(String locnName) {
        this.locnName = locnName;
    }
    /**
     * 
     * @param lookupLocationTypes
     */
    public void setLookupLocationTypes(LookupLocationTypes lookupLocationTypes) {
        this.lookupLocationTypes = lookupLocationTypes;
    }
    /**
     * 
     * @param lookupStates
     */
    public void setLookupStates(LookupStates lookupStates) {
        this.lookupStates = lookupStates;
    }
    /**
     * 
     * @param mailingAddrInd
     */
    public void setMailingAddrInd(Byte mailingAddrInd) {
        this.mailingAddrInd = mailingAddrInd;
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
     * @param modifiedDate
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    /**
     * 
     * @param street1
     */
    public void setStreet1(String street1) {
        this.street1 = street1;
    }
    /**
     * 
     * @param street2
     */
    public void setStreet2(String street2) {
        this.street2 = street2;
    }
    /**
     * 
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
    /**
     * 
     * @param zip4
     */
    public void setZip4(String zip4) {
        this.zip4 = zip4;
    }

}