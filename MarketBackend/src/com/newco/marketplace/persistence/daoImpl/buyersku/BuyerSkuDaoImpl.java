package com.newco.marketplace.persistence.daoImpl.buyersku;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.buyersku.IBuyerSkuDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class BuyerSkuDaoImpl extends ABaseImplDao implements IBuyerSkuDao {
	private Logger LOGGER = Logger.getLogger(BuyerSkuDaoImpl.class);

	/**
	 * @Description : check sku category is available for the buyer
	 * @param buyerId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean getAvailableSkuCatCount(Integer buyerId)throws DataServiceException {
		Integer count = null;
		boolean isAvailable =false;
		try{
			count = (Integer) queryForObject("getCountOfBuyerSkuCategory.query", buyerId);
			if(null!= count){
				isAvailable =true;
			}
		}catch (Exception e) {
			LOGGER.error("Excaption in fetching avalable buyer sku category for the buyer");
			e.printStackTrace();
			throw new DataServiceException(e.getMessage());
		}
		return isAvailable;
	}
	
	/**@Description : Fetching all the SKU details added by the user.
	 * @param buyerId
	 * @return
	 * @throws DataServiceException
	 */
	public List<SkuDetailsVO> getAvailableBuyerSkus(Integer buyerId)throws DataServiceException {
		List<SkuDetailsVO> skuDetailsVOs = null;
		try{
			skuDetailsVOs = (List<SkuDetailsVO>)queryForList("getbuyerSkuDetails.query",buyerId);
		}catch (Exception e) {
			LOGGER.error("Exception in fetching sku details available for the buyer");
			throw new DataServiceException(e.getMessage());
		}
		return skuDetailsVOs;
	}

	public boolean existBuyerAndSkus(String sku, Integer buyerId) throws DataServiceException {
		boolean skuExist = false;

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("buyerId", buyerId);
			params.put("sku", sku);

			Integer count = (Integer) queryForObject("getCountOfBuyerAndSku.query", params);
			skuExist = null != count && count.intValue() > 0 ? true : false;
		} catch (Exception e) {
			LOGGER.error("Exception in fetching sku for the buyer");
			throw new DataServiceException(e.getMessage());
		}
		return skuExist;
	}
	
	

}
