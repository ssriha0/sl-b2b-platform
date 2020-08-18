package com.newco.marketplace.dao.provider.serviceofferings;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dto.vo.autoAcceptance.ManageAutoOrderAcceptanceDTO;
import com.newco.marketplace.dto.vo.serviceOfferings.AvailabilityVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ManageServiceOfferingsDTO;
import com.newco.marketplace.dto.vo.serviceOfferings.PricingVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ServiceOfferingHistoryVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ServiceOfferingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;

public interface ManageServiceOfferingsDao {

	public List<ManageServiceOfferingsDTO> fetchServiceOfferingList(Integer vendorId)throws DataServiceException;

	public ServiceOfferingsVO fetchServiceOfferingDetails(Integer vendorId,Integer skuId)throws DataServiceException;

	public ServiceOfferingsVO saveOfferingDetails(ServiceOfferingsVO serviceOfferingsVO) throws DataServiceException;
	
	public void saveOfferingPrice(PricingVO pricingVO) throws DataServiceException;
	
	public void splitInsertPricingDetails(List<PricingVO> pricingList) throws DataServiceException;
	
	public void splitInsertAvailabilityDetails(List<AvailabilityVO> availabilityList) throws DataServiceException;
	
	public List<String> getzipcodeList(String zipCode,String radius) throws DataServiceException;
	
	public void updateOfferingDetails(ServiceOfferingsVO serviceOfferingsVO) throws DataServiceException;
	
	public void updateOfferingPriceDetails(PricingVO pricingVO) throws DataServiceException;
	
	public void deleteServiceOfferingPrice(Integer offeringId) throws DataServiceException;
	
	public void deleteServiceOfferingAvailability(Integer offeringId) throws DataServiceException;

	/**@Description: This method will fetch all the history details associated with a service offering.
	 * It includes data from service_offering_history,service_offering_price_history and 
	 * service_offering_availability_history
	 * @param offeringId
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOfferingHistoryVO fetchServiceOfferingHistory (Integer offeringId)throws DataServiceException;

	public List<AvailabilityVO>  fetchServiceOfferingAvailability(Integer vendorId) throws DataServiceException;
	
	public ServiceOfferingsVO fetchServiceOfferingSkuDetails(Integer skuId) throws DataServiceException;
	
	public Map <String, String> getZipCodeAPIDetails() throws DataServiceException;




}
