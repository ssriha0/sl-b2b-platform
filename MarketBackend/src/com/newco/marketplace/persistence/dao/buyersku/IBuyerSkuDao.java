package com.newco.marketplace.persistence.dao.buyersku;

import java.util.List;

import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.exception.core.DataServiceException;


public interface IBuyerSkuDao {

	/**@Description : check sku category is available for the buyer
	 * @param buyerId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean getAvailableSkuCatCount(Integer buyerId) throws DataServiceException;

	/**@Description : Fetching all the SKU details added by the user.
	 * @param buyerId
	 * @return
	 * @throws DataServiceException
	 */
	public List<SkuDetailsVO> getAvailableBuyerSkus(Integer buyerId)throws DataServiceException;

	public boolean existBuyerAndSkus(String sku, Integer buyerId) throws DataServiceException;
	
}
