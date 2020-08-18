package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.InsuranceRatingsLookupVO;

public class InsuranceRatingParameterBean extends RatingParameterBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2820389619982815728L;
	private Integer generalLiabilityRating = 0;
	private Integer vehicleLiabilityRating = 0;
	private Integer workersCompensationRating = 0;
	private boolean glRatingCheckBox = false;
	private boolean vlRatingCheckBox = false;
	private boolean wcRatingCheckBox = false;
	Map<Integer, InsuranceRatingsLookupVO> selectedInsuranceMap = null;	
	List<InsuranceRatingsLookupVO> selectedInsuranceList = null;
	
	
	private Integer additionalInsuranceRating = 0;
	private boolean addRatingCheckBox = false;
	
	Map<Integer, InsuranceRatingsLookupVO> addnSelectedInsuranceMap = null;	
	List<InsuranceRatingsLookupVO> addnSelectedInsuranceList = null;
	
	public Integer getGeneralLiabilityRating() {
		return generalLiabilityRating;
	}
	public void setGeneralLiabilityRating(Integer generalLiabilityRating) {
		this.generalLiabilityRating = generalLiabilityRating;
	}
	public Integer getVehicleLiabilityRating() {
		return vehicleLiabilityRating;
	}
	public void setVehicleLiabilityRating(Integer vehicleLiabilityRating) {
		this.vehicleLiabilityRating = vehicleLiabilityRating;
	}
	public Integer getWorkersCompensationRating() {
		return workersCompensationRating;
	}
	public void setWorkersCompensationRating(Integer workersCompensationRating) {
		this.workersCompensationRating = workersCompensationRating;
	}
	public boolean isGlRatingCheckBox() {
		return glRatingCheckBox;
	}
	public void setGlRatingCheckBox(boolean glRatingCheckBox) {
		this.glRatingCheckBox = glRatingCheckBox;
	}
	public boolean isVlRatingCheckBox() {
		return vlRatingCheckBox;
	}
	public void setVlRatingCheckBox(boolean vlRatingCheckBox) {
		this.vlRatingCheckBox = vlRatingCheckBox;
	}
	public boolean isWcRatingCheckBox() {
		return wcRatingCheckBox;
	}
	public void setWcRatingCheckBox(boolean wcRatingCheckBox) {
		this.wcRatingCheckBox = wcRatingCheckBox;
	}
	public List<InsuranceRatingsLookupVO> getSelectedInsuranceList() {
		return selectedInsuranceList;
	}
	public void setSelectedInsuranceList(
			List<InsuranceRatingsLookupVO> selectedInsuranceList) {
		this.selectedInsuranceList = selectedInsuranceList;
	}
	public Map<Integer, InsuranceRatingsLookupVO> getSelectedInsuranceMap() {
		return selectedInsuranceMap;
	}
	public void setSelectedInsuranceMap(
			Map<Integer, InsuranceRatingsLookupVO> selectedInsuranceMap) {
		this.selectedInsuranceMap = selectedInsuranceMap;
	}
	public Integer getAdditionalInsuranceRating() {
		return additionalInsuranceRating;
	}
	public void setAdditionalInsuranceRating(Integer additionalInsuranceRating) {
		this.additionalInsuranceRating = additionalInsuranceRating;
	}
	public boolean isAddRatingCheckBox() {
		return addRatingCheckBox;
	}
	public void setAddRatingCheckBox(boolean addRatingCheckBox) {
		this.addRatingCheckBox = addRatingCheckBox;
	}
	public Map<Integer, InsuranceRatingsLookupVO> getAddnSelectedInsuranceMap() {
		return addnSelectedInsuranceMap;
	}
	public void setAddnSelectedInsuranceMap(
			Map<Integer, InsuranceRatingsLookupVO> addnSelectedInsuranceMap) {
		this.addnSelectedInsuranceMap = addnSelectedInsuranceMap;
	}
	public List<InsuranceRatingsLookupVO> getAddnSelectedInsuranceList() {
		return addnSelectedInsuranceList;
	}
	public void setAddnSelectedInsuranceList(
			List<InsuranceRatingsLookupVO> addnSelectedInsuranceList) {
		this.addnSelectedInsuranceList = addnSelectedInsuranceList;
	}	
	
	
	
}