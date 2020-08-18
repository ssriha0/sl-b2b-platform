package com.newco.marketplace.dto.vo.providerSearch;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.LocationVO;
import com.sears.os.vo.SerializableBaseVO;

public class ProviderSearchCriteriaVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2010237178934085436L;
	private ArrayList<Integer> skillNodeIds; 
	private long buyerZipCode;
	private int providerRating;
	private Integer buyerID;
	
	private Integer spnID;
	private Boolean isNewSpn;
	private Integer tierId;
		
	private LocationVO serviceLocation;
	private String serviceOrderID;
	private List<Integer> skillServiceTypeId;
	private int lockedResourceId;
	private String groupID;
	private String currentChildOrderId;
	private boolean marketProviderSearchOff;
	private boolean routingPriorityApplied;
	private Double perfScore;
	private Integer selectedDistance; 
	private List<Integer> firmId;
	
	public List<Integer> getFirmId() {
		return firmId;
	}
	public void setFirmId(List<Integer> firmIds) {
		this.firmId = firmIds;
	}
	public String getCurrentChildOrderId() {
		return currentChildOrderId;
	}
	public void setCurrentChildOrderId(String currentChildOrderId) {
		this.currentChildOrderId = currentChildOrderId;
	}
	public long getBuyerZipCode() {
		return buyerZipCode;
	}
	public void setBuyerZipCode(long buyerZipCode) {
		this.buyerZipCode = buyerZipCode;
	}
	public int getProviderRating() {
		return providerRating;
	}
	public void setProviderRating(int providerRating) {
		this.providerRating = providerRating;
	}
	public ArrayList<Integer> getSkillNodeIds() {
		return skillNodeIds;
	}
	public void setSkillNodeIds(ArrayList<Integer> skillNodeIds) {
		this.skillNodeIds = skillNodeIds;
	}	
	
	public Integer getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}
	
	public LocationVO getServiceLocation() {
		return serviceLocation;
	}
	public void setServiceLocation(LocationVO serviceLocation) {
		this.serviceLocation = serviceLocation;
	}
	
	public String getServiceOrderID() {
		return serviceOrderID;
	}
	public void setServiceOrderID(String serviceOrderID) {
		this.serviceOrderID = serviceOrderID;
	}
	
	
	public List<Integer> getSkillServiceTypeId() {
		return skillServiceTypeId;
	}
	public void setSkillServiceTypeId(List<Integer> skillServiceTypeId) {
		this.skillServiceTypeId = skillServiceTypeId;
	}
	/**
	 * @see com.sears.os.vo.request.ABaseServiceRequestVO#toString()
	 */
	@Override
	public String toString(){
		return "";
	}
	public Integer getSpnID() {
		return spnID;
	}
	public void setSpnID(Integer spnID) {
		this.spnID = spnID;
	}
	public int getLockedResourceId() {
		return lockedResourceId;
	}
	public void setLockedResourceId(int lockedResourceId) {
		this.lockedResourceId = lockedResourceId;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public Boolean getIsNewSpn() {
		return isNewSpn;
	}
	public void setIsNewSpn(Boolean isNewSpn) {
		this.isNewSpn = isNewSpn;
	}
		
	/**
	 * @return the tierId
	 */
	public Integer getTierId() {
		return tierId;
	}
	/**
	 * @param tierId the tierId to set
	 */
	public void setTierId(Integer tierId) {
		this.tierId = tierId;
	}
	public boolean isMarketProviderSearchOff() {
		return marketProviderSearchOff;
	}
	public void setMarketProviderSearchOff(boolean marketProviderSearchOff) {
		this.marketProviderSearchOff = marketProviderSearchOff;
	}
	public boolean isRoutingPriorityApplied() {
		return routingPriorityApplied;
	}
	public void setRoutingPriorityApplied(boolean routingPriorityApplied) {
		this.routingPriorityApplied = routingPriorityApplied;
	}
	public Double getPerfScore() {
		return perfScore;
	}
	public void setPerfScore(Double perfScore) {
		this.perfScore = perfScore;
	}	
	
	public Integer getSelectedDistance() {
		return selectedDistance;
	}
	public void setSelectedDistance(Integer selectedDistance) {
		this.selectedDistance = selectedDistance;
	}

}
