package com.newco.marketplace.dto.vo;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;
import com.newco.marketplace.dto.vo.LookupVO;

public class InsuranceRatingsLookupVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7920328318061541661L;
	List<LookupVO> generalLiabilityRatingList = null;
	List<LookupVO> vehicleLiabilityRatingList = null;
	List<LookupVO> workersCompensationRatingList = null;
	List<LookupVO> additionalInsuranceRatingList = null;
	
	Integer id = null;
    String type = null;
    String descr = null;
    Double limitValue = 0.0;
    private boolean checkBoxValue = false;
  
	public List<LookupVO> getGeneralLiabilityRatingList() {
		return generalLiabilityRatingList;
	}
	public void setGeneralLiabilityRatingList(
			List<LookupVO> generalLiabilityRatingList) {
		this.generalLiabilityRatingList = generalLiabilityRatingList;
	}
	public List<LookupVO> getVehicleLiabilityRatingList() {
		return vehicleLiabilityRatingList;
	}
	public void setVehicleLiabilityRatingList(
			List<LookupVO> vehicleLiabilityRatingList) {
		this.vehicleLiabilityRatingList = vehicleLiabilityRatingList;
	}
	public List<LookupVO> getWorkersCompensationRatingList() {
		return workersCompensationRatingList;
	}
	public void setWorkersCompensationRatingList(
			List<LookupVO> workersCompensationRatingList) {
		this.workersCompensationRatingList = workersCompensationRatingList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Double getLimitValue() {
		return limitValue;
	}
	public void setLimitValue(Double limitValue) {
		this.limitValue = limitValue;
	}
	public boolean isCheckBoxValue() {
		return checkBoxValue;
	}
	public void setCheckBoxValue(boolean checkBoxValue) {
		this.checkBoxValue = checkBoxValue;
	}
	public List<LookupVO> getAdditionalInsuranceRatingList() {
		return additionalInsuranceRatingList;
	}
	public void setAdditionalInsuranceRatingList(
			List<LookupVO> additionalInsuranceRatingList) {
		this.additionalInsuranceRatingList = additionalInsuranceRatingList;
	}
	
	
	
}