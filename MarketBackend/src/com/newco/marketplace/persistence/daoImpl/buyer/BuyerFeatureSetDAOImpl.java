package com.newco.marketplace.persistence.daoImpl.buyer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerFeatureSetDAO;

public class BuyerFeatureSetDAOImpl extends SqlMapClientDaoSupport implements IBuyerFeatureSetDAO {
	
	private static final Logger logger = Logger.getLogger(BuyerFeatureSetDAOImpl.class);

	public String getFeature(Integer buyerID, String feature) throws DataServiceException {
		String foundFeature = null;
		try{
			Map<String, String> params = new HashMap<String, String>();
			params.put("buyerID", String.valueOf(buyerID.intValue()));
			params.put("feature", feature);
			foundFeature = (String) getSqlMapClient(). queryForObject("buyerFeatuerSet.getFeature", params);
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @BuyerFeatureSetDAOImpl.getFeature() due to"+ex.getMessage());
		     throw new DataServiceException("SQL Exception @BuyerFeatureSetDAOImpl.getFeature() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     logger.info("General Exception @BuyerFeatureSetDAOImpl.getFeature() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @BuyerFeatureSetDAOImpl.getFeature() due to "+ex.getMessage());
      }
		return foundFeature;
	}

	public List<String> getFeatures(Integer buyerID) throws DataServiceException {
		List<String> features = null;
		try{
			features = (List) getSqlMapClient().queryForList("buyerFeatuerSet.getFeatures", buyerID);
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @BuyerFeatureSetDAOImpl.getFeatures() due to"+ex.getMessage());
		     throw new DataServiceException("SQL Exception @BuyerFeatureSetDAOImpl.getFeatures() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     logger.info("General Exception @BuyerFeatureSetDAOImpl.getFeatures() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @BuyerFeatureSetDAOImpl.getFeatures() due to "+ex.getMessage());
      }
		return features;
	}
	

	public List <Integer>  getSHCBuyerIds(String feature) throws DataServiceException {
		// we get an array of buyer ids of sears holding companies from this method for the purpose of gl feed
		List <Integer> buyerIds = null;
		try{
			buyerIds = (List<Integer>) getSqlMapClient().queryForList("buyerFeatuerSet.shc_buyerIds",feature);
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @BuyerFeatureSetDAOImpl.getSHCBuyerIds() due to"+ex.getMessage());
		     throw new DataServiceException("SQL Exception @BuyerFeatureSetDAOImpl.getSHCBuyerIds() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     logger.info("General Exception @BuyerFeatureSetDAOImpl.getSHCBuyerIds() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @BuyerFeatureSetDAOImpl.getSHCBuyerIds() due to "+ex.getMessage());
      }
		return buyerIds;
	}
	
	public Double getCancelFeeForBuyer(Integer buyerId){
		Double cancelFee = null;
		try{
			cancelFee = (Double) getSqlMapClient().queryForObject("buyerFeatuerSet.getCancelFee",buyerId);
    	}catch (SQLException ex) {
            ex.printStackTrace();
		    logger.info("SQL Exception @BuyerFeatureSetDAOImpl.getCancelFeeForBuyer() due to"+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		   logger.info("General Exception @BuyerFeatureSetDAOImpl.getCancelFeeForBuyer() due to"+ex.getMessage());
      }
		return cancelFee;
	}
}
