package com.newco.marketplace.business.iBusiness.serviceorder;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IBuyerSkuBO {

	/**@Description: check sku category is available for the buyer
	 * @param buyerIdInteger
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean checkSkuCatAvailable(Integer buyerId) throws BusinessServiceException;

	/**@Description : Fetching all the SKU details added by the user.
	 * @param buyerIdInteger
	 * @return
	 * @throws BusinessServiceException
	 */
	public Map<String, List<SkuDetailsVO>> getAvailableBuyerSkus(Integer buyerId)throws BusinessServiceException;
	
	/**
	 * checks if particular sku exists for a buyer
	 * 
	 * @param buyerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean isSkuExistForBuyer(String sku, Integer buyerId) throws BusinessServiceException;
	

}
