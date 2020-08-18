package com.newco.marketplace.business.businessImpl.buyer;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerFeatureSetDAO;

public class BuyerFeatureSetBOImpl implements IBuyerFeatureSetBO {
	
	private static final Logger logger = Logger.getLogger(BuyerFeatureSetBOImpl.class);
	
	private IBuyerFeatureSetDAO buyerFeatureSetDAO;
	
	public Boolean validateFeature(Integer buyerID, String feature) {
		Boolean foundFeature = null;
		
		try {
			String strfeature = getBuyerFeatureSetDAO().getFeature(buyerID, feature);
			if (strfeature != null) {
				foundFeature = new Boolean(true);
			}
			else {
				foundFeature = new Boolean(false);
			}
		} catch (DataServiceException e) {
			logger.error(e.getMessage(), e);
		}
		return foundFeature;
	}

	public List<String> getFeatures(Integer buyerID) {
		List<String> features = null;
		try {
			features = getBuyerFeatureSetDAO().getFeatures(buyerID);
		} catch (DataServiceException e) {
			logger.error(e.getMessage(), e);
		}
		return features;
	}

	public IBuyerFeatureSetDAO getBuyerFeatureSetDAO() {
		return buyerFeatureSetDAO;
	}

	public void setBuyerFeatureSetDAO(IBuyerFeatureSetDAO buyerFeatureSetDAO) {
		this.buyerFeatureSetDAO = buyerFeatureSetDAO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO#getBuyerIdsForFeature(java.lang.String)
	 */
	public List<Integer> getBuyerIdsForFeature(String feature) {
		List<Integer> buyerIds = null;
		try {
			buyerIds = getBuyerFeatureSetDAO().getSHCBuyerIds(feature);
		} catch (DataServiceException e) {
			logger.error("Error finding Buyer IDs for the feature " + feature, e);
		}
		return buyerIds;
	}
	
	public Double getCancelFeeForBuyer(Integer buyerId) {
		Double cancelFee = null;
		try {
			cancelFee = getBuyerFeatureSetDAO().getCancelFeeForBuyer(buyerId);
		} catch (Exception e) {
			logger.error("Error finding cancelFee for buyer " + buyerId, e);
		}
		return cancelFee;
	}
}
