package com.servicelive.spn.services.buyer;

import java.util.List;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.dao.buyer.BuyerDao;
import com.servicelive.spn.dao.buyer.BuyerResourceDao;
import com.servicelive.spn.services.BaseServices;

/**
 * @author hoza
 *
 */
public class BuyerServices extends BaseServices {
	private BuyerDao buyerDao;
	private BuyerResourceDao buyerResourceDao;

	/**
	 * @return the buyerDao
	 */
	public BuyerDao getBuyerDao() {
		return buyerDao;
	}

	/**
	 * @param buyerDao the buyerDao to set
	 */
	public void setBuyerDao(BuyerDao buyerDao) {
		this.buyerDao = buyerDao;
	}
	
	
	/**
	 * 
	 * @param id
	 * @return Buyer
	 * @throws Exception
	 */
	public Buyer getBuyerForId(Integer id) throws Exception {
		return buyerDao.findById(id);
	}
	/**
	 * 
	 * @param userName
	 * @return Buyer
	 * @throws Exception
	 */
	public Buyer getBuyerForUserName(String userName) throws Exception {
		User tmpUser = new User();
		tmpUser.setUsername(userName);
		List<Buyer> buyers = buyerDao.findByProperty("user", tmpUser); 
		return buyers != null && buyers.size() > 0 ? buyers.get(0) : null;
	}
	
	/**
	 * 
	 * @param userName
	 * @return BuyerResource
	 * @throws Exception
	 */
	public BuyerResource getBuyerResourceForUserName(String userName) throws Exception {
		User tmpUser = new User();
		tmpUser.setUsername(userName);
		
		List<BuyerResource> buyerResources = null;//
		buyerResources = buyerResourceDao.findByProperty("user", tmpUser);
			
		
		if(buyerResources != null && buyerResources.size() > 0) {
			return buyerResources.get(0);
		}

		return null;
	}
	// To retrieve the buyerResource by passing buyeresourceId
	/**
	 * 
	 * @param buyeresourceId
	 * @return BuyerResource
	 * @throws Exception
	 */
	public BuyerResource getBuyerResourceForBuyerResourceId(Integer buyerResoueceId)throws Exception{
	    BuyerResource buyerResource =new BuyerResource();
	    buyerResource=buyerResourceDao.findById(buyerResoueceId);
	    return buyerResource;
	    }

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}
	/**
	 * 
	 * @return BuyerResourceDao
	 */
	public BuyerResourceDao getBuyerResourceDao()
	{
		return buyerResourceDao;
	}
	/**
	 * 
	 * @param buyerResourceDao
	 */
	public void setBuyerResourceDao(BuyerResourceDao buyerResourceDao)
	{
		this.buyerResourceDao = buyerResourceDao;
	}
}
