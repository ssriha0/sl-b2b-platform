package com.servicelive.wallet.serviceinterface.requestbuilder;

import com.servicelive.common.vo.ABaseVO;

// TODO: Auto-generated Javadoc
/**
 * Class ABaseRequestBuilder.
 */
public abstract class ABaseRequestBuilder {

	/**
	 * setRequest.
	 * 
	 * @param request 
	 * @param fundingTypeId 
	 * @param serviceOrderId 
	 * 
	 * @return void
	 */
	protected void setRequest(ABaseVO request, Long fundingTypeId, String serviceOrderId) {

		setRequest(request, fundingTypeId, serviceOrderId, null, null, null);
	}

	/**
	 * setRequest.
	 * 
	 * @param request 
	 * @param fundingTypeId 
	 * @param serviceOrderId 
	 * @param buyerId 
	 * @param providerId 
	 * @param userName 
	 * 
	 * @return void
	 */
	protected void setRequest(ABaseVO request, Long fundingTypeId, String serviceOrderId, Long buyerId, Long providerId, String userName) {

		request.setFundingTypeId(fundingTypeId);
		request.setServiceOrderId(serviceOrderId);
		request.setBuyerId(buyerId);
		request.setProviderId(providerId);
		request.setUserName(userName);

	}
}
