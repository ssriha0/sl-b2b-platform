

package com.newco.marketplace.api.beans.hi.account.firm.update.v1_0;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import com.newco.marketplace.api.beans.hi.account.firm.v1_0.BusinessAddress;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.InsuranceDetails;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.LicenseAndCertifications;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.MailingAddress;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.W9Information;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.RoleWithinCom;


@XSD(name="updateFirmAccountRequest.xsd", path="/resources/schemas/ums/")
@XmlRootElement(name="updateFirmAccountRequest")
@XStreamAlias("updateFirmAccountRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateFirmAccountRequest {

	@XStreamAlias("primaryIndustry")
    private String primaryIndustry;
	
	@XStreamAlias("businessStartedDate")
    private String businessStartedDate;
	
	@XStreamAlias("businessStructure")
    private String businessStructure;
	
	@XStreamAlias("dunsNumber")
    private String dunsNumber;
	
	@XStreamAlias("foreignOwnedPercentage")
    private String foreignOwnedPercentage;
	
	@XStreamAlias("companySize")
    private String companySize;
	
	@XStreamAlias("annualSalesRevenue")
    private String annualSalesRevenue;
	
	@XStreamAlias("websiteAddress")
    private String websiteAddress;
	
	@XStreamAlias("businessDesc")
	private String businessDesc;
    
	@XStreamAlias("mainBusiPhoneNo")
    private String mainBusiPhoneNo;
    
	@XStreamAlias("mainBusinessExtn")
    private String mainBusinessExtn;
	
	@XStreamAlias("businessFax")
    private String businessFax;
	
	@XStreamAlias("businessAddress")
	@XmlElement(name="businessAddress")
    private BusinessAddress businessAddress;
	
	@XStreamAlias("mailingAddress")
	@XmlElement(name="mailingAddress")
    private MailingAddress mailingAddress;
	
	@XStreamAlias("rolesWithinCom")
	@XmlElement(name="rolesWithinCom")
    private RoleWithinCom rolesWithinCom;
	
	@XStreamAlias("jobTitle")
    private String jobTitle;

	@XStreamAlias("email")
    private String email;

	@XStreamAlias("altEmail")
    private String altEmail;
	
	@XStreamAlias("projectEstimatesChargeInd")
    private Integer projectEstimatesChargeInd;
	
	@XStreamAlias("warrantyOnLabor")
    private String warrantyOnLabor;
	
	@XStreamAlias("warrantyOnParts")
    private String warrantyOnParts;
	
	@XStreamAlias("drugTestingPolicyInd")
    private Integer drugTestingPolicyInd;
	
	@XStreamAlias("drugTestingPolicyRequired")
    private Integer drugTestingPolicyRequired;
	
	@XStreamAlias("workEnvironmentPolicyInd")
    private Integer workEnvironmentPolicyInd;
	
	@XStreamAlias("workEnvironmentPolicyRequired")
    private Integer workEnvironmentPolicyRequired;
	
	@XStreamAlias("employeeCitizenShipProofInd")
    private Integer employeeCitizenShipProofInd;
	
	@XStreamAlias("employeeCitizenShipProofRequired")
    private Integer employeeCitizenShipProofRequired;
	
	@XStreamAlias("crewWearBadgesInd")
    private Integer crewWearBadgesInd;
	
	@XStreamAlias("crewWearBadgesRequired")
    private Integer crewWearBadgesRequired;
	
	@XStreamAlias("licenseAndCertifications")
	@XmlElement(name="licenseAndCertifications")
    private LicenseAndCertifications licenseAndCertifications;
	
	@XStreamAlias("insuranceDetails")
	@XmlElement(name="insuranceDetails")
    private InsuranceDetails insuranceDetails;
	
	@XStreamAlias("w9Information")
	@XmlElement(name="w9Information")
    private W9Information w9Information;
	
	

  
    public String getPrimaryIndustry() {
        return primaryIndustry;
    }

   
    public void setPrimaryIndustry(String value) {
        this.primaryIndustry = value;
    }

 
    public String getBusinessStartedDate() {
        return businessStartedDate;
    }

   
    public void setBusinessStartedDate(String value) {
        this.businessStartedDate = value;
    }

   
    public String getBusinessStructure() {
        return businessStructure;
    }

    
    public void setBusinessStructure(String value) {
        this.businessStructure = value;
    }


    public String getDunsNumber() {
        return dunsNumber;
    }

 
    public void setDunsNumber(String value) {
        this.dunsNumber = value;
    }


    public String getForeignOwnedPercentage() {
        return foreignOwnedPercentage;
    }


    public void setForeignOwnedPercentage(String value) {
        this.foreignOwnedPercentage = value;
    }


    public String getCompanySize() {
        return companySize;
    }


    public void setCompanySize(String value) {
        this.companySize = value;
    }


    public String getAnnualSalesRevenue() {
        return annualSalesRevenue;
    }


    public void setAnnualSalesRevenue(String value) {
        this.annualSalesRevenue = value;
    }


    public String getWebsiteAddress() {
        return websiteAddress;
    }

    public void setWebsiteAddress(String value) {
        this.websiteAddress = value;
    }


    public String getMainBusiPhoneNo() {
        return mainBusiPhoneNo;
    }


    public void setMainBusiPhoneNo(String value) {
        this.mainBusiPhoneNo = value;
    }


    public String getMainBusinessExtn() {
        return mainBusinessExtn;
    }


    public void setMainBusinessExtn(String value) {
        this.mainBusinessExtn = value;
    }


    public String getBusinessFax() {
        return businessFax;
    }


    public void setBusinessFax(String value) {
        this.businessFax = value;
    }


    public BusinessAddress getBusinessAddress() {
        return businessAddress;
    }


    public void setBusinessAddress(BusinessAddress value) {
        this.businessAddress = value;
    }


    public MailingAddress getMailingAddress() {
        return mailingAddress;
    }


    public void setMailingAddress(MailingAddress value) {
        this.mailingAddress = value;
    }


    public RoleWithinCom getRoleWithinCom() {
        return rolesWithinCom;
    }


    public void setRoleWithinCom(RoleWithinCom value) {
        this.rolesWithinCom = value;
    }

  
    public String getJobTitle() {
        return jobTitle;
    }


    public void setJobTitle(String value) {
        this.jobTitle = value;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String value) {
        this.email = value;
    }


    public String getAltEmail() {
        return altEmail;
    }

    public void setAltEmail(String value) {
        this.altEmail = value;
    }


    public Integer getProjectEstimatesChargeInd() {
        return projectEstimatesChargeInd;
    }


    public void setProjectEstimatesChargeInd(Integer value) {
        this.projectEstimatesChargeInd = value;
    }




	public String getWarrantyOnLabor() {
		return warrantyOnLabor;
	}


	public void setWarrantyOnLabor(String warrantyOnLabor) {
		this.warrantyOnLabor = warrantyOnLabor;
	}


	public String getWarrantyOnParts() {
		return warrantyOnParts;
	}


	public void setWarrantyOnParts(String warrantyOnParts) {
		this.warrantyOnParts = warrantyOnParts;
	}



    
    public Integer getDrugTestingPolicyInd() {
		return drugTestingPolicyInd;
	}


	public void setDrugTestingPolicyInd(Integer drugTestingPolicyInd) {
		this.drugTestingPolicyInd = drugTestingPolicyInd;
	}


	public Integer getDrugTestingPolicyRequired() {
		return drugTestingPolicyRequired;
	}


	public void setDrugTestingPolicyRequired(Integer drugTestingPolicyRequired) {
		this.drugTestingPolicyRequired = drugTestingPolicyRequired;
	}




	public Integer getWorkEnvironmentPolicyInd() {
		return workEnvironmentPolicyInd;
	}


	public void setWorkEnvironmentPolicyInd(Integer workEnvironmentPolicyInd) {
		this.workEnvironmentPolicyInd = workEnvironmentPolicyInd;
	}


	public Integer getWorkEnvironmentPolicyRequired() {
		return workEnvironmentPolicyRequired;
	}


	public void setWorkEnvironmentPolicyRequired(
			Integer workEnvironmentPolicyRequired) {
		this.workEnvironmentPolicyRequired = workEnvironmentPolicyRequired;
	}


    
    public Integer getEmployeeCitizenShipProofInd() {
		return employeeCitizenShipProofInd;
	}


	public void setEmployeeCitizenShipProofInd(Integer employeeCitizenShipProofInd) {
		this.employeeCitizenShipProofInd = employeeCitizenShipProofInd;
	}


	public Integer getEmployeeCitizenShipProofRequired() {
		return employeeCitizenShipProofRequired;
	}


	public void setEmployeeCitizenShipProofRequired(
			Integer employeeCitizenShipProofRequired) {
		this.employeeCitizenShipProofRequired = employeeCitizenShipProofRequired;
	}


	public Integer getCrewWearBadgesInd() {
        return crewWearBadgesInd;
    }

   
    public void setCrewWearBadgesInd(Integer value) {
        this.crewWearBadgesInd = value;
    }

   
    public Integer getCrewWearBadgesRequired() {
        return crewWearBadgesRequired;
    }

    
    public void setCrewWearBadgesRequired(Integer value) {
        this.crewWearBadgesRequired = value;
    }

    
    public LicenseAndCertifications getLicenseAndCertifications() {
        return licenseAndCertifications;
    }

    
    public void setLicenseAndCertifications(LicenseAndCertifications value) {
        this.licenseAndCertifications = value;
    }

   
    public InsuranceDetails getInsuranceDetails() {
        return insuranceDetails;
    }

    
    public void setInsuranceDetails(InsuranceDetails value) {
        this.insuranceDetails = value;
    }

    
    public W9Information getW9Information() {
        return w9Information;
    }

   
    public void setW9Information(W9Information value) {
        this.w9Information = value;
    }

    
   


	public String getBusinessDesc() {
		return businessDesc;
	}


	public void setBusinessDesc(String businessDesc) {
		this.businessDesc = businessDesc;
	}

    
}
