package com.newco.marketplace.business.iBusiness.serviceorder;


public interface IServiceOrderFeatureSetBO {
	/**
	 * See if a service order has a feature by passing the feature name and soId. If the feature is
	 * present this will return true.
	 * @param buyerID
	 * @param feature
	 * @return
	 */
	public Boolean validateFeature(String soID, String feature);
	public void setFeature(String soID, String feature);

}
