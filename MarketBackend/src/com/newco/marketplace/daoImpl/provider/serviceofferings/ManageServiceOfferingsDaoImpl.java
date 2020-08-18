package com.newco.marketplace.daoImpl.provider.serviceofferings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dao.provider.manageautoacceptance.ManageAutoOrderAcceptanceDao;
import com.newco.marketplace.dao.provider.serviceofferings.ManageServiceOfferingsDao;
import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.autoAcceptance.ManageAutoOrderAcceptanceDTO;
import com.newco.marketplace.dto.vo.serviceOfferings.AvailabilityVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ManageServiceOfferingsDTO;
import com.newco.marketplace.dto.vo.serviceOfferings.PricingVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ServiceOfferingHistoryVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ServiceOfferingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.sears.os.dao.impl.ABaseImplDao;


public class ManageServiceOfferingsDaoImpl extends ABaseImplDao implements 
ManageServiceOfferingsDao{
	
	
	public List<ManageServiceOfferingsDTO> fetchServiceOfferingList(Integer vendorId)throws DataServiceException{
		try{
			
			return (List<ManageServiceOfferingsDTO>)getSqlMapClient().queryForList("serviceOfferings.fetchServiceOfferingList", vendorId);
		} catch(Exception e) {
			logger.debug("Exception in ManageServiceOfferingsDaoImpl.fetchServiceOfferingList due to "+e.getMessage()); 
			throw new DataServiceException(e.getMessage(),e);
		}	
	} 
	
	public ServiceOfferingsVO fetchServiceOfferingDetails(Integer vendorId,Integer skuId)throws DataServiceException{
		try{
			Map<String,Integer> params = new HashMap<String, Integer>();
			params.put("skuId", skuId);
			params.put("vendorId", vendorId);
			return (ServiceOfferingsVO)getSqlMapClient().queryForObject("serviceOfferings.fetchServiceOfferingDetails", params);
		} catch(Exception e) { 
			logger.debug("Exception in ManageServiceOfferingsDaoImpl.fetchServiceOfferingList due to "+e.getMessage()); 
			throw new DataServiceException(e.getMessage(),e);
		}	
	} 
	
	public List<String> getzipcodeList(String zipCode,String radius) throws DataServiceException{
		try{
		Map<String,String> params = new HashMap<String, String>();
		params.put("zipCode", zipCode);
		params.put("radius", radius);
		List<String> zipcodeList=null;
		zipcodeList=(ArrayList<String>)getSqlMapClient().queryForList("serviceOfferings.zipcodeList",params);
		return zipcodeList;
		} catch(Exception e) { 
			logger.debug("Exception in ManageServiceOfferingsDaoImpl.zipcodeList due to "+e.getMessage()); 
			throw new DataServiceException(e.getMessage(),e);
		}	
	}

	
	public ServiceOfferingsVO saveOfferingDetails(ServiceOfferingsVO serviceOfferingsVO) throws DataServiceException{
		try{
			Integer offeringId=null;
			offeringId=(Integer) getSqlMapClient().insert("serviceOfferings.insertServiceOffering", serviceOfferingsVO);
			serviceOfferingsVO.setOfferingId(offeringId);
			return serviceOfferingsVO;
		} catch(Exception e) { 
			logger.debug("Exception in ManageServiceOfferingsDaoImpl.fetchServiceOfferingList due to "+e.getMessage()); 
			throw new DataServiceException(e.getMessage(),e);
		}	
	} 
	
	public void updateOfferingDetails(ServiceOfferingsVO serviceOfferingsVO) throws DataServiceException{
		try{
			 getSqlMapClient().update("serviceOfferings.updateOfferingDetails", serviceOfferingsVO);
			
		} catch(Exception e) { 
			logger.debug("Exception in ManageServiceOfferingsDaoImpl.updateOfferingDetails due to "+e.getMessage()); 
			throw new DataServiceException(e.getMessage(),e);
		}	
	}
	
	public void updateOfferingPriceDetails(PricingVO pricingVO) throws DataServiceException{
		try{
			 getSqlMapClient().update("serviceOfferings.updateOfferingPriceDetails", pricingVO);

		} catch(Exception e) { 
			logger.debug("Exception in ManageServiceOfferingsDaoImpl.updateOfferingPriceDetails due to "+e.getMessage()); 
			throw new DataServiceException(e.getMessage(),e);
		}	
	}
	
	public void deleteServiceOfferingPrice(Integer offeringId) throws DataServiceException{
		try{
			 getSqlMapClient().update("serviceOfferings.deleteServiceOfferingPrice", offeringId);

		} catch(Exception e) { 
			logger.debug("Exception in ManageServiceOfferingsDaoImpl.deleteServiceOfferingPrice due to "+e.getMessage()); 
			throw new DataServiceException(e.getMessage(),e);
		}	
	}
	
	public void deleteServiceOfferingAvailability(Integer offeringId) throws DataServiceException{
		try{
			 getSqlMapClient().update("serviceOfferings.deleteServiceOfferingAvailability", offeringId);

		} catch(Exception e) { 
			logger.debug("Exception in ManageServiceOfferingsDaoImpl.deleteServiceOfferingAvailability due to "+e.getMessage()); 
			throw new DataServiceException(e.getMessage(),e);
		}	
	}
	
	 
	public void splitInsertAvailabilityDetails(List<AvailabilityVO> availabilityList) throws DataServiceException{
		try{
			int noOfRecords = (availabilityList==null?0:availabilityList.size());
			int noOfIter = 0;
			int count =50;
			if(noOfRecords>0){
				noOfIter = noOfRecords/count;
				if(noOfIter==0){
					count = noOfRecords;
					noOfIter =1;
				}
			}
			int loopCount = 0;
			while(noOfIter!=0){
				int endIndex = (loopCount+1)*count;
				if(noOfIter==1){
					endIndex = availabilityList.size();
				}
				List<AvailabilityVO> availability = availabilityList.subList(loopCount*count, endIndex);
				insert("serviceOfferings.insertServiceOfferingAvailability", availability);
				
				loopCount++;
				noOfIter--;
			}
		}catch (Exception e){
			logger.info(e); 
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	public void splitInsertPricingDetails(List<PricingVO> pricingList) throws DataServiceException{
		try{
			int noOfRecords = (pricingList==null?0:pricingList.size());
			int noOfIter = 0;
			int count =500;
			if(noOfRecords>0){
				noOfIter = noOfRecords/count;
				if(noOfIter==0){
					count = noOfRecords;
					noOfIter =1;
				}
			}
			int loopCount = 0;
			while(noOfIter!=0){
				int endIndex = (loopCount+1)*count;
				if(noOfIter==1){
					endIndex = pricingList.size();
				}
				List<PricingVO> pricing = pricingList.subList(loopCount*count, endIndex);
				insert("serviceOfferings.insertServiceOfferingPricingList", pricing);
				loopCount++;
				noOfIter--;
			}
		}catch (Exception e){
			logger.info(e); 
			throw new DataServiceException(e.getMessage(),e);
		}
	}

	
	public void saveOfferingPrice(PricingVO pricingVO) throws DataServiceException{
		try{
			 getSqlMapClient().insert("serviceOfferings.insertServiceOfferingPricing",pricingVO);
			
		} catch(Exception e) { 
			logger.debug("Exception in ManageServiceOfferingsDaoImpl.saveOfferingPrice due to "+e.getMessage()); 
			throw new DataServiceException(e.getMessage(),e);
		}	
	}
	/**@Description: This method will fetch all the history details associated with a service offering.
	 * It includes data from service_offering_history,service_offering_price_history and 
	 * service_offering_availability_history
	 * @param offeringId
	 * @return
	 * @throws DataServiceException
	 */
	public ServiceOfferingHistoryVO fetchServiceOfferingHistory(Integer offeringId) throws DataServiceException {
		ServiceOfferingHistoryVO historyVO = null;
		List<ServiceOfferingsVO> serviceOfferingsHistoryList=null;
		List<PricingVO> serviceOfferingspricingHistoryList=null;
		List<AvailabilityVO> serviceOfferingsAvailabilityHistoryList=null;
		try{
			serviceOfferingsHistoryList = getSqlMapClient().queryForList("serviceOfferings.fetchServiceOfferingHistory", offeringId);
			serviceOfferingspricingHistoryList = getSqlMapClient().queryForList("serviceOfferings.fetchServiceOfferingPriceHistory", offeringId);
			serviceOfferingsAvailabilityHistoryList = getSqlMapClient().queryForList("serviceOfferings.fetchServiceOfferingAvailabilityHistory", offeringId);
		}catch (Exception e) {
			logger.error("Exception in fetching service offering history from dao layer", e);
			throw new DataServiceException(e.getMessage(),e);
		}
		if(null!= serviceOfferingsHistoryList || null!= serviceOfferingspricingHistoryList || null!= serviceOfferingsAvailabilityHistoryList){
			historyVO = new ServiceOfferingHistoryVO();
			historyVO.setOfferingId(offeringId);
			historyVO.setServiceOfferingsHistoryList(serviceOfferingsHistoryList);
			historyVO.setServiceOfferingspricingHistoryList(serviceOfferingspricingHistoryList);
			historyVO.setServiceOfferingsAvailabilityHistoryList(serviceOfferingsAvailabilityHistoryList);
		}
		return historyVO;
	} 
	
	public List<AvailabilityVO>  fetchServiceOfferingAvailability(Integer vendorId) throws DataServiceException{
		List<AvailabilityVO> serviceOfferingsAvailabilityList=null;
		try{
		
			serviceOfferingsAvailabilityList =(ArrayList<AvailabilityVO>) getSqlMapClient().queryForList("serviceOfferings.fetchServiceOfferingAvailabilityForSku", vendorId);
		return serviceOfferingsAvailabilityList;
		}catch (Exception e) {
			logger.error("Exception in fetching service offering history from dao layer", e);
			throw new DataServiceException(e.getMessage(),e);
		}
        
	}

	public ServiceOfferingsVO fetchServiceOfferingSkuDetails(Integer skuId) throws DataServiceException{
		ServiceOfferingsVO serviceOfferingsVO=null;
		try{
			serviceOfferingsVO =(ServiceOfferingsVO) getSqlMapClient().queryForObject("serviceOfferings.fetchServiceOfferingSkuDetails", skuId);
		return serviceOfferingsVO;
		}catch (Exception e) {
			logger.error("Exception in fetching fetchServiceOfferingSkuDetails from dao layer", e);
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	public Map <String, String> getZipCodeAPIDetails() throws DataServiceException{
		List<String> apiDetailsNeeded = new ArrayList<String>();
        HashMap <String, String> apiDetails = new HashMap <String, String>(); 
        apiDetailsNeeded.add("zipCodeAPIKey");
        apiDetailsNeeded.add("zipCodeAPIURL");
        try{
          apiDetails =(HashMap <String, String>) getSqlMapClient().queryForMap("serviceOfferings.getZipCodeAPIDetails", apiDetailsNeeded, "app_constant_key","app_constant_value");
        }
        catch(Exception e){
                logger.error("Exception occurred in getZipCodeAPIDetails: "+e.getMessage());
                throw new DataServiceException("Exception occurred in getZipCodeAPIDetails: "+e.getMessage(),e);
        }
        return apiDetails;
	}

	


}
