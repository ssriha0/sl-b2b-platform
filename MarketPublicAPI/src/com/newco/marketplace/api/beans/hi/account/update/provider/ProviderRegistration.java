

package com.newco.marketplace.api.beans.hi.account.update.provider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.beans.hi.account.create.provider.Availability;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("provider")
@XmlRootElement(name = "provider")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderRegistration {

    @XStreamAlias("marketPlaceInd")
    private String marketPlaceInd;
    
    @XStreamAlias("marketPlaceStatus")
    private String marketPlaceStatus;
    
    @XStreamAlias("roles")
	@XmlElement(name="roles")
    private Roles roles;
    
    @XStreamAlias("jobTitle")
    private String jobTitle;
    
    @XStreamAlias("address")
	@XmlElement(name="address")
    private DispatchAddress address;
    
    @XStreamAlias("geographicalRange")
    private String geographicalRange;
    
    @XStreamAlias("preferredBillingRate")
    private String preferredBillingRate;
    
    @XStreamAlias("backgroundCheck")
	@XmlElement(name="backgroundCheck")
    private BackgroundCheck backgroundCheck;
    
    @XStreamAlias("availability")
	@XmlElement(name="availability")
    private Availability availability;
    
    @XStreamAlias("businessPhone")
    private String businessPhone;
    
    @XStreamAlias("mobileNo")
    private String mobileNo;
    
    @XStreamAlias("businessExtn")
    private String businessExtn;
    
    @XStreamAlias("primaryEmail")
    private String primaryEmail;
    
    @XStreamAlias("altEmail")
    private String altEmail;
    
    @XStreamAlias("smsAddress")
    private String smsAddress;
    
    @XStreamAlias("secondaryContact")
    private String secondaryContact;
    
    @XStreamAlias("languages")
	@XmlElement(name="languages")
    private Languages languages;
    
    @XStreamAlias("licenseAndCertifications")
	@XmlElement(name="licenseAndCertifications")
    private LicenseAndCertifications licenseAndCertifications;


	public String getMarketPlaceInd() {
		return marketPlaceInd;
	}


	public void setMarketPlaceInd(String marketPlaceInd) {
		this.marketPlaceInd = marketPlaceInd;
	}


	public String getMarketPlaceStatus() {
		return marketPlaceStatus;
	}


	public void setMarketPlaceStatus(String marketPlaceStatus) {
		this.marketPlaceStatus = marketPlaceStatus;
	}


	public Roles getRoles() {
		return roles;
	}


	public void setRoles(Roles roles) {
		this.roles = roles;
	}


	public String getJobTitle() {
        return jobTitle;
    }

   
    public void setJobTitle(String value) {
        this.jobTitle = value;
    }

    
    public DispatchAddress getAddress() {
        return address;
    }

    
    public void setAddress(DispatchAddress value) {
        this.address = value;
    }

    
    public String getGeographicalRange() {
        return geographicalRange;
    }

    public void setGeographicalRange(String value) {
        this.geographicalRange = value;
    }


	public String getPreferredBillingRate() {
		return preferredBillingRate;
	}


	public void setPreferredBillingRate(String preferredBillingRate) {
		this.preferredBillingRate = preferredBillingRate;
	}


	public String getBusinessPhone() {
        return businessPhone;
    }

   
    public void setBusinessPhone(String value) {
        this.businessPhone = value;
    }

    
    public String getMobileNo() {
        return mobileNo;
    }

    
    public void setMobileNo(String value) {
        this.mobileNo = value;
    }

   
    public String getBusinessExtn() {
        return businessExtn;
    }

   
    public void setBusinessExtn(String value) {
        this.businessExtn = value;
    }

   
    public String getPrimaryEmail() {
        return primaryEmail;
    }

 
    public void setPrimaryEmail(String value) {
        this.primaryEmail = value;
    }

    
    public String getAltEmail() {
        return altEmail;
    }

    
    public void setAltEmail(String value) {
        this.altEmail = value;
    }

    
    public String getSmsAddress() {
        return smsAddress;
    }

    
    public void setSmsAddress(String value) {
        this.smsAddress = value;
    }


    public String getSecondaryContact() {
		return secondaryContact;
	}


	public void setSecondaryContact(String secondaryContact) {
		this.secondaryContact = secondaryContact;
	}


	public void setBackgroundCheck(BackgroundCheck backgroundCheck) {
		this.backgroundCheck = backgroundCheck;
	}


	public Languages getLanguages() {
        return languages;
    }

   
    public void setLanguages(Languages value) {
        this.languages = value;
    }


	public Availability getAvailability() {
		return availability;
	}


	public void setAvailability(Availability availability) {
		this.availability = availability;
	}


	public LicenseAndCertifications getLicenseAndCertifications() {
		return licenseAndCertifications;
	}


	public void setLicenseAndCertifications(
			LicenseAndCertifications licenseAndCertifications) {
		this.licenseAndCertifications = licenseAndCertifications;
	}


	public BackgroundCheck getBackgroundCheck() {
		return backgroundCheck;
	}

	public boolean isEmpty()
	{
		if(null== marketPlaceInd && null == marketPlaceStatus 
				&& null== roles && null==jobTitle && null == address 
				&& null == geographicalRange && null == preferredBillingRate 
				&& null==backgroundCheck && null == availability 
				&& null == businessPhone && null== mobileNo 
				&& null ==businessExtn && null==primaryEmail 
				&& null ==altEmail && null==smsAddress 
				&& null ==secondaryContact 
				&& null== languages && null == licenseAndCertifications)
		{
			return true;
		}else
		{
			return false;
		}
	}
	
}
