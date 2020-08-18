package com.newco.marketplace.business.iBusiness.serviceorder;


import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.price.ServiceOrderPriceVO;
import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface IServiceOrderCloseBO {
	
	/**
	 * This closes the service order and concludes the necessary financial transactions for final pricing/payment of service order
	 * 
	 * @param buyerId
	 * @param serviceOrderID
	 * @param finalPartsPrice
	 * @param finalLaborPrice
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProcessResponse processCloseSO(Integer buyerId, String serviceOrderID, double finalPartsPrice, double finalLaborPrice, SecurityContext securityContext)
		throws BusinessServiceException;
	
	/**
	 * This makes call to Staging Webservice to do Close updates for Upsell Payment and Skus
	 * @param soId
	 */
	public void stageUpsellPaymentAndSku(String soId);
	
	/**
	 * Updates the final price in staging
	 * 
	 * @param serviceOrderID
	 * @param finalLaborPrice
	 * @throws BusinessServiceException
	 */
	public void stageFinalPrice(String serviceOrderID, double finalLaborPrice) throws BusinessServiceException;

	/** R16_1_1: SL-21270:Fetching finalLaborPrice and finalPartsPrice from so_hdr
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ServiceOrderPriceVO getFinalPrice(String soId) throws BusinessServiceException;
	
	/** R16_1_1: SL-21270:Fetching addon details
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public AdditionalPaymentVO getAddonDetails(String soId) throws BusinessServiceException;

}
