package com.newco.marketplace.translator.business;

import com.newco.marketplace.translator.dto.BuyerCredentials;

public interface IBuyerService {
	
	public BuyerCredentials getBuyerCredentials(String userName);
	
	/**
	 * Get the buyer credentials by the buyer ID
	 * 
	 * @param buyerId
	 * @return
	 */
	public BuyerCredentials getBuyerCredentials(Integer buyerId);

}
