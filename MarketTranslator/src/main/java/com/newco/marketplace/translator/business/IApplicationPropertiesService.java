package com.newco.marketplace.translator.business;


public interface IApplicationPropertiesService {

	public String getServiceLiveEndpoint();
	
	/**
	 * Returns the OMSStaging webservice URL
	 * @return
	 */
	public String getStagingWebServiceEndPoint();
	
	/**
	 * Returns the Buyer ID
	 * @return
	 */
	public Integer getBuyerId(String buyerIdKey);
	
	
	/**
	 * Returns the Nps No Of orders to close in one run
	 * @return
	 */
	
	public int getNPSOrdersToProcess(String npsNumOrdersToCloseKey); 
	
	/*
	 * Returns the list of stores whose transactions can be sent to the new integration
	 */
	public String getRolloutUnits();

	/*
	 * Returns whether or not to use the new order fulfillment process for new orders
	 */
	public Boolean getUseNewOFProcess();

	/*
	 * Returns whether or order fulfillment is in test mode
	 */
	public Boolean getOFTestMode();

	/*
	 * Returns whether staging data should be stored in legacy tables even when it is handled by the new order fulfillment process.
	 */
	public Boolean getMaintainLegacyStagingData();
}
