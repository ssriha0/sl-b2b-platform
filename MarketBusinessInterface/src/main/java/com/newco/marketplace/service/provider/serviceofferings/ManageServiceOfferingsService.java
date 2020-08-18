package com.newco.marketplace.service.provider.serviceofferings;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.autoAcceptance.ManageAutoOrderAcceptanceDTO;
import com.newco.marketplace.dto.vo.serviceOfferings.AvailabilityVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ManageServiceOfferingsDTO;
import com.newco.marketplace.dto.vo.serviceOfferings.PricingVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ServiceOfferingHistoryVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ServiceOfferingsDTO;
import com.newco.marketplace.dto.vo.serviceOfferings.ServiceOfferingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface ManageServiceOfferingsService {

	// method to fetch the service offerings for the manage dashboard
	public List<ManageServiceOfferingsDTO> fetchServiceOfferingList(Integer vendorId) throws BusinessServiceException; 
	// fetch the service offering Details
	public ServiceOfferingsVO fetchServiceOfferingDetails(Integer vendorId,Integer skuId) throws BusinessServiceException;
	// save the service offering details
	public ServiceOfferingsVO saveOfferingDetails(ServiceOfferingsVO serviceOfferingsVO) throws BusinessServiceException;
	//save the service offering price details
	public void saveOfferingPrice(PricingVO pricingVO) throws BusinessServiceException;
	//save the service offering price details
	public void saveOfferingPriceDetails(List<PricingVO> pricingList) throws BusinessServiceException;
	//save the service offering availability details
	public void saveAvailabilityDetails( List<AvailabilityVO> availabilityList) throws BusinessServiceException;
	// get the zipcodes with in a raduius
	public List<String> getzipcodeList(String zipCode,String radius) throws BusinessServiceException;
	// update the service offering details
	public void updateOfferingDetails(ServiceOfferingsVO serviceOfferingsVO) throws BusinessServiceException;
	// update the service offering price details
	public void updateOfferingPriceDetails(PricingVO pricingVO) throws BusinessServiceException;
	// delete the service offering price details
	public void deleteServiceOfferingPrice(Integer offeringId) throws BusinessServiceException;
	// delete the service offering availability details
	public void deleteServiceOfferingAvailability(Integer offeringId) throws BusinessServiceException;
	
	/**@Description: This method will fetch all the history details associated with a service offering.
	 * It includes data from service_offering_history,service_offering_price_history and 
	 * service_offering_availability_history
	 * @param offeringId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ServiceOfferingHistoryVO fetchServiceOfferingHistory(Integer offeringId)throws BusinessServiceException;
	// fetch the service Offering availability details for a vendorId
	public List<AvailabilityVO> fetchServiceOfferingAvailability(Integer vendorId) throws BusinessServiceException;
	
	public ServiceOfferingsVO fetchServiceOfferingSkuDetails(Integer skuId) throws BusinessServiceException;
    
    public List<String> fetchAvailableZipCodes(String zipCode,String radius) throws BusinessServiceException;



}
