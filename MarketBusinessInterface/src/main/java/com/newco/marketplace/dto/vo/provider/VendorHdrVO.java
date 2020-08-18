package com.newco.marketplace.dto.vo.provider;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class VendorHdrVO extends SerializableBaseVO{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -1714550466902553636L;
	private Integer vendorID;
	private Boolean vehicalLiabilityInsurance;
	private Boolean generalLiabilityInsurance;
	private Boolean workersCompensationInsurance;
	private Integer credTypeId;
	private Integer wfStateId;
	private Double insVehAmt;
	private Double insWorkAmt;
	private Double insGenAmt;
	private Date insVerifiedDate;
	
	
	public Double getInsVehAmt() {
		return insVehAmt;
	}
	public void setInsVehAmt(Double insVehAmt) {
		this.insVehAmt = insVehAmt;
	}
	public Double getInsWorkAmt() {
		return insWorkAmt;
	}
	public void setInsWorkAmt(Double insWorkAmt) {
		this.insWorkAmt = insWorkAmt;
	}
	public Double getInsGenAmt() {
		return insGenAmt;
	}
	public void setInsGenAmt(Double insGenAmt) {
		this.insGenAmt = insGenAmt;
	}
	public Date getInsVerifiedDate() {
		return insVerifiedDate;
	}
	public void setInsVerifiedDate(Date insVerifiedDate) {
		this.insVerifiedDate = insVerifiedDate;
	}
	public Integer getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	public Boolean getGeneralLiabilityInsurance() {
		return generalLiabilityInsurance;
	}
	public void setGeneralLiabilityInsurance(Boolean generalLiabilityInsurance) {
		if(null == generalLiabilityInsurance){
			this.generalLiabilityInsurance = false;
		}else{
			this.generalLiabilityInsurance = generalLiabilityInsurance;
		}		
	}
	public Boolean getVehicalLiabilityInsurance() {
		return vehicalLiabilityInsurance;
	}
	public void setVehicalLiabilityInsurance(Boolean vehicalLiabilityInsurance) {
		if(null == vehicalLiabilityInsurance){
			this.vehicalLiabilityInsurance = false;
		}else{
			this.vehicalLiabilityInsurance = vehicalLiabilityInsurance;
		}		
	}
	public Integer getVendorID() {
		return vendorID;
	}
	public void setVendorID(Integer vendorID) {
		this.vendorID = vendorID;
	}
	public Boolean getWorkersCompensationInsurance() {
		return workersCompensationInsurance;
	}
	public void setWorkersCompensationInsurance(Boolean workersCompensationInsurance) {
		if(null == workersCompensationInsurance){
			this.workersCompensationInsurance = false;
		}else{
			this.workersCompensationInsurance = workersCompensationInsurance;
		}		
	}
	public Integer getCredTypeId() {
		return credTypeId;
	}
	public void setCredTypeId(Integer credTypeId) {
		this.credTypeId = credTypeId;
	}	
	
}
