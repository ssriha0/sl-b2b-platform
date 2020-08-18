package com.newco.marketplace.translator.business.impl;

import com.newco.marketplace.translator.business.IBuyerService;
import com.newco.marketplace.translator.dao.BuyerMT;
import com.newco.marketplace.translator.dao.IBuyerDAO;
import com.newco.marketplace.translator.dao.IUserProfileDAO;
import com.newco.marketplace.translator.dao.UserProfile;
import com.newco.marketplace.translator.dto.BuyerCredentials;
import com.newco.marketplace.translator.dto.BuyerCredentialsMapper;

public class BuyerService implements IBuyerService {
	
	private static final Integer BUYER_ROLE = new Integer(3);
	
	private IBuyerDAO buyerDAO;
	private IUserProfileDAO userProfileDAO;

	public BuyerCredentials getBuyerCredentials(String userName) {
		BuyerCredentials buyerCred = new BuyerCredentials();
		BuyerMT buyer = getBuyerDAO().findByUserName(userName).get(0);
		UserProfile profile = getUserProfileDAO().findByUserNameAndRoleID(userName, BUYER_ROLE);
		buyerCred = BuyerCredentialsMapper.mapDomainToDTO(buyer, profile);
		return buyerCred;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.IBuyerService#getBuyerCredentials(java.lang.Integer)
	 */
	public BuyerCredentials getBuyerCredentials(Integer buyerId) {
		BuyerCredentials buyerCred = new BuyerCredentials();
		BuyerMT buyer = getBuyerDAO().findById(buyerId);
		UserProfile profile = getUserProfileDAO().findByUserNameAndRoleID(buyer.getUserName(), BUYER_ROLE);
		buyerCred = BuyerCredentialsMapper.mapDomainToDTO(buyer, profile);
		return buyerCred;
	}

	public IBuyerDAO getBuyerDAO() {
		return buyerDAO;
	}

	public void setBuyerDAO(IBuyerDAO buyerDAO) {
		this.buyerDAO = buyerDAO;
	}

	public IUserProfileDAO getUserProfileDAO() {
		return userProfileDAO;
	}

	public void setUserProfileDAO(IUserProfileDAO userProfileDAO) {
		this.userProfileDAO = userProfileDAO;
	}
	
}
