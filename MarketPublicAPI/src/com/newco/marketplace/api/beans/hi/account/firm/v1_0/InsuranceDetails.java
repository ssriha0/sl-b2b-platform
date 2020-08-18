
package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("insuranceDetails")
@XmlRootElement(name = "insuranceDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class InsuranceDetails {

	@XStreamAlias("generalLiabilityInsuranceInd")
    private Integer generalLiabilityInsuranceInd;
	
	@XStreamAlias("generalLiabilityInsuranceAmount")
    private String generalLiabilityInsuranceAmount;
	
	@XStreamAlias("generalLiabilityDetails")
    private GeneralLiabilityDetails generalLiabilityDetails;
	
	@XStreamAlias("vehicleLiabilityInd")
    private Integer vehicleLiabilityInd;
	
	@XStreamAlias("vehicleLiabilityAmount")
    private String vehicleLiabilityAmount;
	
	@XStreamAlias("vehicleLiabilityDetails")
    private VehicleLiabilityDetails vehicleLiabilityDetails;
	
	@XStreamAlias("workmanCompensationInsuranceInd")
    private Integer workmanCompensationInsuranceInd;
	
	@XStreamAlias("workmanCompensationInsuranceAmount")
    private String workmanCompensationInsuranceAmount;
	
	@XStreamAlias("workmanCompensationDetails")
    private WorkmanCompensationDetails workmanCompensationDetails;


    public Integer getGeneralLiabilityInsuranceInd() {
        return generalLiabilityInsuranceInd;
    }

 
    public void setGeneralLiabilityInsuranceInd(Integer value) {
        this.generalLiabilityInsuranceInd = value;
    }

    
   
    public String getGeneralLiabilityInsuranceAmount() {
		return generalLiabilityInsuranceAmount;
	}


	public void setGeneralLiabilityInsuranceAmount(
			String generalLiabilityInsuranceAmount) {
		this.generalLiabilityInsuranceAmount = generalLiabilityInsuranceAmount;
	}


	public GeneralLiabilityDetails getGeneralLiabilityDetails() {
        return generalLiabilityDetails;
    }

   
    public void setGeneralLiabilityDetails(GeneralLiabilityDetails value) {
        this.generalLiabilityDetails = value;
    }

   
    public Integer getVehicleLiabilityInd() {
        return vehicleLiabilityInd;
    }

  
    public void setVehicleLiabilityInd(Integer value) {
        this.vehicleLiabilityInd = value;
    }

    
    
   
    public String getVehicleLiabilityAmount() {
		return vehicleLiabilityAmount;
	}


	public void setVehicleLiabilityAmount(String vehicleLiabilityAmount) {
		this.vehicleLiabilityAmount = vehicleLiabilityAmount;
	}


	public VehicleLiabilityDetails getVehicleLiabilityDetails() {
        return vehicleLiabilityDetails;
    }

  
    public void setVehicleLiabilityDetails(VehicleLiabilityDetails value) {
        this.vehicleLiabilityDetails = value;
    }

    
    public Integer getWorkmanCompensationInsuranceInd() {
        return workmanCompensationInsuranceInd;
    }

    
    public void setWorkmanCompensationInsuranceInd(Integer value) {
        this.workmanCompensationInsuranceInd = value;
    }

   
    public String getWorkmanCompensationInsuranceAmount() {
		return workmanCompensationInsuranceAmount;
	}


	public void setWorkmanCompensationInsuranceAmount(
			String workmanCompensationInsuranceAmount) {
		this.workmanCompensationInsuranceAmount = workmanCompensationInsuranceAmount;
	}


	public WorkmanCompensationDetails getWorkmanCompensationDetails() {
        return workmanCompensationDetails;
    }

 
    public void setWorkmanCompensationDetails(WorkmanCompensationDetails value) {
        this.workmanCompensationDetails = value;
    }

}
