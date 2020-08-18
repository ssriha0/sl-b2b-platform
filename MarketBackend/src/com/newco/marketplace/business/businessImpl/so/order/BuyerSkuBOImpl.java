package com.newco.marketplace.business.businessImpl.so.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSkuBO;
import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.buyersku.IBuyerSkuDao;

public class BuyerSkuBOImpl implements IBuyerSkuBO{
	
	private Logger LOGGER = Logger.getLogger(BuyerSkuBOImpl.class);
    private IBuyerSkuDao buyerSkuDao;
	
    /**@Description: check sku category is available for the buyer
	 * @param buyerIdInteger
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean checkSkuCatAvailable(Integer buyerId)throws BusinessServiceException {
		boolean available =false;
		try{
			available = buyerSkuDao.getAvailableSkuCatCount(buyerId);
		}catch (DataServiceException e) {
			LOGGER.error("Exception in checking sku category available for the buyer");
			throw new BusinessServiceException(e);
		}
		return available;
	}
    
	/**@Description : Fetching all the SKU details added by the user.
	 * @param buyerIdInteger
	 * @return
	 * @throws BusinessServiceException
	 */
	public Map<String, List<SkuDetailsVO>> getAvailableBuyerSkus(Integer buyerId)throws BusinessServiceException {
		Map<String, List<SkuDetailsVO>>  skuDetailsVOMap = null;
		try{
			List<SkuDetailsVO> skuDetailsVOs = buyerSkuDao.getAvailableBuyerSkus(buyerId);
			if(null!=skuDetailsVOs && !skuDetailsVOs.isEmpty() ){
				skuDetailsVOMap = formSkuMainSubCat(skuDetailsVOs);
			}
		}catch (Exception e) {
			LOGGER.error("Exception in fetching sku details available for the buyer");
			throw new BusinessServiceException(e);
		}
		return skuDetailsVOMap;
	}
	
	/**
	 * checks if particular sku exists for a buyer
	 */
	public boolean isSkuExistForBuyer(String sku, Integer buyerId) throws BusinessServiceException {
		boolean skuDetailsVOs = false;
		try {
			skuDetailsVOs = buyerSkuDao.existBuyerAndSkus(sku, buyerId);
		} catch (DataServiceException e) {
			LOGGER.error("Exception in fetching sku for the buyer");
			throw new BusinessServiceException(e);
		}

		return skuDetailsVOs;
		
	}

	/**
	 * @param skuDetailsVOs
	 * @return
	 */
	private Map<String, List<SkuDetailsVO>> formSkuMainSubCat(List<SkuDetailsVO> skuDetailsVOs) {
		Map<String, List<SkuDetailsVO>>  skuDetailsVOMap = new HashMap<String, List<SkuDetailsVO>>();
		if(null!=skuDetailsVOs && !skuDetailsVOs.isEmpty()){
		  for(SkuDetailsVO skuVo : skuDetailsVOs){
			   if(skuDetailsVOMap.containsKey(skuVo.getSkuCategory())){
				   List<SkuDetailsVO> skuDetailList=skuDetailsVOMap.get(skuVo.getSkuCategory());
				   skuDetailList.add(skuVo);
				   skuDetailsVOMap.put(skuVo.getSkuCategory(), skuDetailList);	
			    }else{
				   List<SkuDetailsVO> skuDetailList=new ArrayList<SkuDetailsVO>();
				   skuDetailList.add(skuVo);
				   skuDetailsVOMap.put(skuVo.getSkuCategory(), skuDetailList);					
			}
		  }
		}
		return skuDetailsVOMap;
	}

	public IBuyerSkuDao getBuyerSkuDao() {
		return buyerSkuDao;
	}

	public void setBuyerSkuDao(IBuyerSkuDao buyerSkuDao) {
		this.buyerSkuDao = buyerSkuDao;
	}

}
