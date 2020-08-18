package com.newco.marketplace.vo.provider;
import java.sql.Timestamp;

public class VendorResource extends BaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3678922393226365980L;
	private Integer resourceId = null;
	private Integer vendorId = -1;
	private Integer contactId = -1;
	private Integer dispatchId = -1;
	private String ssn = null;
	private Integer priContactMthd = null;
	private Integer altContactMthd = 0;
	private Integer yrsOfExperience = 0;
	private Integer locnId = null;
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
    private String hourlyRate="";
    private String userName = "";
    private Integer dispatchInd=0;
    private Integer ownerInd=0;
    private Integer adminInd=0;
    private Integer primaryInd=0;
    private Integer resourceInd=0;
    
    
    
    private Integer managerInd;
    private Integer otherInd;
    private Integer sproInd;
    
    
    /**
     * @return the dispatchInd
     */
    public int getDispatchInd() {
        return dispatchInd;
    }
    
    /**
     * @return the dispatchInd
     */
    public void setDispatchInd(Integer dispatchInd) {
        this.dispatchInd=dispatchInd;
    }
    
    /**
     * @return the ownerInd
     */
    public Integer getOwnerInd() {
        return ownerInd;
    }
    
    /**
     * @return the ownerInd
     */
    public void setOwnerInd(Integer ownerInd) {
        this.ownerInd=ownerInd;
    }
    
    /**
     * @return the adminInd
     */
    public Integer getAdminInd() {
        return adminInd;
    }
    
    /**
     * @return the adminInd
     */
    public void setAdminInd(Integer adminInd) {
        this.adminInd=adminInd;
    }
    
    
    /**
     * @return the primaryInd
     */
    public Integer getPrimaryInd() {
        return primaryInd;
    }
    
    /**
     * @return the primaryInd
     */
    public void setPrimaryInd(Integer primaryInd) {
        this.primaryInd=primaryInd;
    }
    
    /**
     * @return the resourceInd
     */
    public Integer getResourceInd() {
        return resourceInd;
    }
    
    /**
     * @return the resourceInd
     */
    public void setResourceInd(Integer resourceInd) {
        this.resourceInd=resourceInd;
    }
	
	/**
	 * @return the hourlyRate
	 */
	public String getHourlyRate() {
		return hourlyRate;
	}
	/**
	 * @param the hourlyRate to set
	 */
	public void setHourlyRate(String hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
	
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString()
	{
		return "vendorResourceID: "+getResourceId().toString()
		+"\nvendorID: "+getVendorId()
		+"\ncontactID: "+getContactId()
		+"\nwfStateID: "+getWfStateId()
		+"\nuserName: "+getUserName().toString();
	}

	public Integer getManagerInd() {
		return managerInd;
	}

	public void setManagerInd(Integer managerInd) {
		this.managerInd = managerInd;
	}

	public Integer getOtherInd() {
		return otherInd;
	}

	public void setOtherInd(Integer otherInd) {
		this.otherInd = otherInd;
	}

	public Integer getSproInd() {
		return sproInd;
	}

	public void setSproInd(Integer sproInd) {
		this.sproInd = sproInd;
	}


	
	

}
