package com.newco.marketplace.translator.dto;

import com.newco.marketplace.translator.dao.BuyerMT;
import com.newco.marketplace.translator.dao.UserProfile;

public class BuyerCredentialsMapper {
	
	public static BuyerCredentials mapDomainToDTO(BuyerMT buyer, UserProfile profile) {
		BuyerCredentials buyerCred = new BuyerCredentials();
		buyerCred.setBuyerID(buyer.getBuyerId());
		buyerCred.setUsername(buyer.getUserName());
		buyerCred.setPassword(profile.getPassword());
		return buyerCred;
	}

}
