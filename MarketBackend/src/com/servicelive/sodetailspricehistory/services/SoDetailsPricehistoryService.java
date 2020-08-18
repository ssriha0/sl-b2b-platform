package com.servicelive.sodetailspricehistory.services;

import com.newco.marketplace.api.beans.so.price.SOPriceHistoryResponse;
import com.newco.marketplace.dto.priceHistory.SoPriceHistoryDTO;


/**
 * 
 * @author root
 *
 */
public interface SoDetailsPricehistoryService {
	/**
	 * Call API
	 * @param buyerId
	 * @param soIds
	 * @param infoLevel
	 * @return
	 */
	public SOPriceHistoryResponse getpriceHistoryList(String buyerId,String soIds, String infoLevel);
	
	/**
	 * Map to DTO
	 * @param responseAsObject
	 * @param priceHistorytabDto
	 */
	public void mapResponseObjectToDto(SOPriceHistoryResponse responseAsObject,SoPriceHistoryDTO priceHistorytabDto);

	
}
