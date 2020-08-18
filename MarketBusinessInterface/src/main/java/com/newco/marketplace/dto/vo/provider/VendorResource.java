package com.newco.marketplace.dto.vo.provider;
import java.sql.Timestamp;


import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.sears.os.vo.SerializableBaseVO;

public class VendorResource extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -15585171563509510L;
	private String soId;
	private Integer resourceId = null;
	private Integer vendorId = -1;
	private Integer contactId = -1;
	private Integer dispatchId = -1;
	private String ssn = null;
	private Integer priContactMthd = null;
	private Integer altContactMthd = -1;
	private Integer yrsOfExperience = -1;
	private Integer locnId = new Integer(-1);
	private Integer insured = -1;
	private String insuranceProvider = null;
	private String pictureUrl = null;
	private Integer pictureDate = 0;
	private Integer laborTypeId = null;
	private Timestamp createdDate = null;
	private Timestamp modifiedDate= null;
	private Integer serviceAreaRadiusId = null;
	private Integer mktPlaceInd = -1;
	private Integer backgroundStateId = -1;
	private Integer wfStateId = -1;
    private Boolean noCredInd =false;	
	private Contact resourceContact= null;
	private SoLocation resourceLocation = null;
	
	
	
	public Boolean getNoCredInd() {
		return noCredInd;
	}
	public void setNoCredInd(Boolean noCredInd) {
		this.noCredInd = noCredInd;
	}
	/**
	 * @return the wfStateId
	 */
	public Integer getWfStateId() {
		return wfStateId;
	}
	/**
	 * @param wfStateId the wfStateId to set
	 */
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	/**
	 * @return the altContactMthd
	 */
	public Integer getAltContactMthd() {
		return altContactMthd;
	}
	/**
	 * @param altContactMthd the altContactMthd to set
	 */
	public void setAltContactMthd(Integer altContactMthd) {
		this.altContactMthd = altContactMthd;
	}
	/**
	 * @return the contactId
	 */
	public Integer getContactId() {
		return contactId;
	}
	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the dispatchId
	 */
	public Integer getDispatchId() {
		return dispatchId;
	}
	/**
	 * @param dispatchId the dispatchId to set
	 */
	public void setDispatchId(Integer dispatchId) {
		this.dispatchId = dispatchId;
	}
	/**
	 * @return the insuranceProvider
	 */
	public String getInsuranceProvider() {
		return insuranceProvider;
	}
	/**
	 * @param insuranceProvider the insuranceProvider to set
	 */
	public void setInsuranceProvider(String insuranceProvider) {
		this.insuranceProvider = insuranceProvider;
	}
	/**
	 * @return the insured
	 */
	public Integer getInsured() {
		return insured;
	}
	/**
	 * @param insured the insured to set
	 */
	public void setInsured(Integer insured) {
		this.insured = insured;
	}
	/**
	 * @return the laborTypeId
	 */
	public Integer getLaborTypeId() {
		return laborTypeId;
	}
	/**
	 * @param laborTypeId the laborTypeId to set
	 */
	public void setLaborTypeId(Integer laborTypeId) {
		this.laborTypeId = laborTypeId;
	}
	/**
	 * @return the locnId
	 */
	public Integer getLocnId() {
		return locnId;
	}
	/**
	 * @param locnId the locnId to set
	 */
	public void setLocnId(Integer locnId) {
		this.locnId = locnId;
	}
	/**
	 * @return the mktPlaceInd
	 */
	public Integer getMktPlaceInd() {
		return mktPlaceInd;
	}
	/**
	 * @param mktPlaceInd the mktPlaceInd to set
	 */
	public void setMktPlaceInd(Integer mktPlaceInd) {
		this.mktPlaceInd = mktPlaceInd;
	}
	/**
	 * @return the modifiedDate
	 */
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the pictureDate
	 */
	public Integer getPictureDate() {
		return pictureDate;
	}
	/**
	 * @param pictureDate the pictureDate to set
	 */
	public void setPictureDate(Integer pictureDate) {
		this.pictureDate = pictureDate;
	}
	/**
	 * @return the pictureUrl
	 */
	public String getPictureUrl() {
		return pictureUrl;
	}
	/**
	 * @param pictureUrl the pictureUrl to set
	 */
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	/**
	 * @return the priContactMthd
	 */
	public Integer getPriContactMthd() {
		return priContactMthd;
	}
	/**
	 * @param priContactMthd the priContactMthd to set
	 */
	public void setPriContactMthd(Integer priContactMthd) {
		this.priContactMthd = priContactMthd;
	}
	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the serviceAreaRadiusId
	 */
	public Integer getServiceAreaRadiusId() {
		return serviceAreaRadiusId;
	}
	/**
	 * @param serviceAreaRadiusId the serviceAreaRadiusId to set
	 */
	public void setServiceAreaRadiusId(Integer serviceAreaRadiusId) {
		this.serviceAreaRadiusId = serviceAreaRadiusId;
	}
	/**
	 * @return the ssn
	 */
	public String getSsn() {
		return ssn;
	}
	/**
	 * @param ssn the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}
	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	/**
	 * @return the yrsOfExperience
	 */
	public Integer getYrsOfExperience() {
		return yrsOfExperience;
	}
	/**
	 * @param yrsOfExperience the yrsOfExperience to set
	 */
	public void setYrsOfExperience(Integer yrsOfExperience) {
		this.yrsOfExperience = yrsOfExperience;
	}
	/**
	 * @return the backgroundStateId
	 */
	public Integer getBackgroundStateId() {
		return backgroundStateId;
	}
	/**
	 * @param backgroundStateId the backgroundStateId to set
	 */
	public void setBackgroundStateId(Integer backgroundStateId) {
		this.backgroundStateId = backgroundStateId;
	}
	
	public String getSoId() {
		return soId;
	}
	
	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	public Contact getResourceContact() {
		return resourceContact;
	}
	
	public void setResourceContact(Contact resourceContact) {
		this.resourceContact = resourceContact;
	}

	
	/**
	 * @return the resourceLocation
	 */
	public SoLocation getResourceLocation() {
		return resourceLocation;
	}
	/**
	 * @param resourceLocation the resourceLocation to set
	 */
	public void setResourceLocation(SoLocation resourceLocation) {
		this.resourceLocation = resourceLocation;
	}
	




}
