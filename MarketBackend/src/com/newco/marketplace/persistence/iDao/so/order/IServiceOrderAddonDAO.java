package com.newco.marketplace.persistence.iDao.so.order;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStagingVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * Interface to handle serviceorderaddons.
 * @author dpotlur
 *
 */
public interface IServiceOrderAddonDAO {
	
	public void createServiceOrderAddons(List<ServiceOrderAddonVO> addons) throws DataServiceException;
	
	/**
	 * get list of All Addon for given so Id
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public List<ServiceOrderAddonVO> getUpsellALLSkusForSO(String soId) throws DataServiceException;
	
	public List<ServiceOrderAddonVO> getUpsellSkuswithQtyForSO(String soId) throws DataServiceException;
		
	public void updateAddonQtys(List<ServiceOrderAddonVO> addons) throws DataServiceException;
	
	public void deleteUnpurchasedAddonsbySO(String soId) throws DataServiceException;

	public ServiceOrderStagingVO loadUpsellInfo(String soId)throws DataServiceException;
	
	/**
	 * Returns the total service fee for all the addon skus for the service order.
	 * 
	 * @param soId
	 * @return totalServiceFeeForAddOnSkus(
	 * @throws DataServiceException
	 */
	public Double getTotalServiceFeeForAddOnSkus(String soId)throws DataServiceException;
	
	public boolean isAddonSaved(ServiceOrderAddonVO addon);

	public Map<String, Boolean> isAddonsSaved(ServiceOrderAddonVO addon);
}
