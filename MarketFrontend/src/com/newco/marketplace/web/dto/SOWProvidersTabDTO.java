package com.newco.marketplace.web.dto;

import java.util.ArrayList;

import com.newco.marketplace.interfaces.OrderConstants;

public class SOWProvidersTabDTO extends SOWBaseTabDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9216369887875860375L;
	private ArrayList<SOWProviderDTO> providers= new ArrayList<SOWProviderDTO>();
	private Boolean showSelectedProviders = true;
	private Integer selectTopProviders = 0;
	private Integer spnId;
	private Boolean routingPriorityAppliedInd;
	private Double performanceScore;
	private Double firmPerformanceScore;
	private String zip;

	public ArrayList<SOWProviderDTO> getProviders() {
		return providers;
	}

	public void setProviders(ArrayList<SOWProviderDTO> providers) {
		this.providers = providers;
	}

	public Boolean getShowSelectedProviders() {
		return showSelectedProviders;
	}

	public void setShowSelectedProviders(Boolean showSelectedProviders) {
		this.showSelectedProviders = showSelectedProviders;
	}

	public Integer getSelectTopProviders() {
		return selectTopProviders;
	}

	public void setSelectTopProviders(Integer selectTopProviders) {
		this.selectTopProviders = selectTopProviders;
	}

	@Override
	public void validate() {
		clearAllWarnings();
		boolean checked = false;
		_doWorkFlowValidation();
		SOWProviderDTO sowProviderDto = null;
		if(providers != null && providers.size() > 0){
			for(int i=0;i<providers.size();i++){
				sowProviderDto = providers.get(i);
				if(sowProviderDto.getIsChecked()){
					checked = true;
					break;
				}
			}
			if(!checked){
				addWarning("",getTheResourceBundle().getString("Provider_Not_Selected_Message")
						,OrderConstants.SOW_TAB_WARNING);
			}
		}else{
			addWarning("",getTheResourceBundle().getString("Providers_Not_Found_For_Category")
					,OrderConstants.SOW_TAB_WARNING);
		}
	}

	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return OrderConstants.SOW_PROVIDERS_TAB;
	}
	
	public void clearWarnings() {
		this.clearAllWarnings();
	}

	public String getZip()
	{
		return zip;
	}

	public void setZip(String zip)
	{
		this.zip = zip;
	}

	public Integer getSpnId() {
		return spnId;
	}

	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	public Boolean getRoutingPriorityAppliedInd() {
		return routingPriorityAppliedInd;
	}

	public void setRoutingPriorityAppliedInd(Boolean routingPriorityAppliedInd) {
		this.routingPriorityAppliedInd = routingPriorityAppliedInd;
	}

	public Double getPerformanceScore() {
		return performanceScore;
	}

	public void setPerformanceScore(Double performanceScore) {
		this.performanceScore = performanceScore;
	}

	public Double getFirmPerformanceScore() {
		return firmPerformanceScore;
	}

	public void setFirmPerformanceScore(Double firmPerformanceScore) {
		this.firmPerformanceScore = firmPerformanceScore;
	}

}
