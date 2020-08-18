package com.servicelive.wallet.serviceinterface.requestbuilder;

import com.servicelive.common.CommonConstants;
import com.servicelive.wallet.serviceinterface.vo.AchVO;

// TODO: Auto-generated Javadoc
/**
 * Class AchRequestBuilder.
 */
public class AchRequestBuilder extends ABaseRequestBuilder {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IAchRequestBuilder#createBuyerAchRequest(long, long, long, double)
	 */
	public AchVO createBuyerAchRequest(Long fundingTypeId, Long buyerId, Long accountId, double amount) {

		AchVO request = new AchVO();
		setRequest(request, fundingTypeId, null, buyerId, null, null);
		setAchRequest(request, accountId, amount);
		return request;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.client.IAchRequestBuilder#createProviderAchRequest(long, long, double, long)
	 */
	public AchVO createProviderAchRequest(Long accountId, Long providerId, double amount, long fundingTypeId) {

		AchVO request = new AchVO();
		setRequest(request, fundingTypeId, null, null, providerId, null);
		setAchRequest(request, accountId, amount);
		return request;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.service.requestbuilder.IAchRequestBuilder#createAdminAchRequest(long, double)
	 */
	public AchVO createAdminAchRequest(Long accountId, double amount){
		AchVO request = new AchVO();
		setRequest(request, CommonConstants.FUNDING_TYPE_NON_FUNDED, null, null, null, null);
		setAchRequest(request, accountId, amount);
		return request;
		
	}
	
	/**
	 * setAchRequest.
	 * 
	 * @param request 
	 * @param accountId 
	 * @param amount 
	 * 
	 * @return void
	 */
	private void setAchRequest(AchVO request, Long accountId, double amount) {

		request.setAccountId(accountId);
		request.setAmount(amount);
	}

}
