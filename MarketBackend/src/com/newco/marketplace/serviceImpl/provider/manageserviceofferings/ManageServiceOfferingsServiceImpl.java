package com.newco.marketplace.serviceImpl.provider.manageserviceofferings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dao.provider.serviceofferings.ManageServiceOfferingsDao;
import com.newco.marketplace.dto.vo.serviceOfferings.AvailabilityVO;
import com.newco.marketplace.dto.vo.serviceOfferings.CodeDetails;
import com.newco.marketplace.dto.vo.serviceOfferings.ManageServiceOfferingsDTO;
import com.newco.marketplace.dto.vo.serviceOfferings.PricingVO;
import com.newco.marketplace.dto.vo.serviceOfferings.Result;
import com.newco.marketplace.dto.vo.serviceOfferings.ServiceOfferingHistoryVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ServiceOfferingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.service.provider.serviceofferings.ManageServiceOfferingsService;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientHandlerException;
//import com.sun.jersey.api.client.WebResource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class ManageServiceOfferingsServiceImpl implements ManageServiceOfferingsService{

	private static final Logger LOGGER = Logger.getLogger(ManageServiceOfferingsServiceImpl.class);
	private ManageServiceOfferingsDao manageServiceOfferingsDao;
	
	/**fetch new & updated pending CAR rules
	 * @param vendorId
	 * @return
	 */
	public List<ManageServiceOfferingsDTO> fetchServiceOfferingList(Integer vendorId) throws BusinessServiceException{
		
		List<ManageServiceOfferingsDTO> serviceOfferinglist = new ArrayList<ManageServiceOfferingsDTO>();
		try{			
			serviceOfferinglist = (List<ManageServiceOfferingsDTO>) manageServiceOfferingsDao.fetchServiceOfferingList(vendorId);
			
		}
		catch(DataServiceException e){
			LOGGER.debug("Exception in ManageServiceOfferingsServiceImpl.fetchServiceOfferingList due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}		
		return serviceOfferinglist;
	}
   
	public ServiceOfferingsVO fetchServiceOfferingDetails(Integer vendorId,Integer skuId) throws BusinessServiceException{
		ServiceOfferingsVO serviceOffering = new ServiceOfferingsVO();
		
		try{			
			serviceOffering = manageServiceOfferingsDao.fetchServiceOfferingDetails(vendorId,skuId);
			return serviceOffering;
		}
		catch(DataServiceException e){
			LOGGER.debug("Exception in ManageServiceOfferingsServiceImpl.fetchServiceOfferingList due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}		
		
	}
	
	
	
	
	public List<String> getzipcodeList(String zipCode,String radius) throws BusinessServiceException{
		List<String> zipcodes = new ArrayList<String>();
		try{			
			zipcodes = manageServiceOfferingsDao.getzipcodeList(zipCode,radius);
			return zipcodes;
		}
		catch(DataServiceException e){
			LOGGER.debug("Exception in ManageServiceOfferingsServiceImpl.getzipcodeList due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}	
	}


	public ServiceOfferingsVO saveOfferingDetails(ServiceOfferingsVO serviceOfferingsVO) throws BusinessServiceException{
     ServiceOfferingsVO serviceOffering = new ServiceOfferingsVO();
		
		try{			
			serviceOffering = manageServiceOfferingsDao.saveOfferingDetails(serviceOfferingsVO);
			return serviceOffering;
		}
		catch(DataServiceException e){
			LOGGER.debug("Exception in ManageServiceOfferingsServiceImpl.fetchServiceOfferingList due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
	}

	public void saveOfferingPrice(PricingVO pricingVO) throws BusinessServiceException{
		
		try{			
			manageServiceOfferingsDao.saveOfferingPrice(pricingVO);
		}
		catch(DataServiceException e){
			LOGGER.debug("Exception in ManageServiceOfferingsServiceImpl.fetchServiceOfferingList due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		}

	
	public void saveAvailabilityDetails( List<AvailabilityVO> availabilityList) throws BusinessServiceException{
		try{			
		 manageServiceOfferingsDao.splitInsertAvailabilityDetails(availabilityList);
	}
	catch(DataServiceException e){
		LOGGER.debug("Exception in ManageServiceOfferingsServiceImpl.insertAvailabilityDetails due to "+e.getMessage());
		throw new BusinessServiceException(e.getMessage(),e);
	}}
	
	public void saveOfferingPriceDetails(List<PricingVO> pricingList) throws BusinessServiceException{
		try{			
			 manageServiceOfferingsDao.splitInsertPricingDetails(pricingList);
		}
		catch(DataServiceException e){
			LOGGER.debug("Exception in ManageServiceOfferingsServiceImpl.insertAvailabilityDetails due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}}
	
	
	public void updateOfferingDetails(ServiceOfferingsVO serviceOfferingsVO) throws BusinessServiceException{
		try{			
			 manageServiceOfferingsDao.updateOfferingDetails(serviceOfferingsVO);
		}
		catch(DataServiceException e){
			LOGGER.debug("Exception in ManageServiceOfferingsServiceImpl.insertAvailabilityDetails due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
	}
	public void updateOfferingPriceDetails(PricingVO pricingVO) throws BusinessServiceException{
		try{			
			 manageServiceOfferingsDao.updateOfferingPriceDetails(pricingVO);
		}
		catch(DataServiceException e){
			LOGGER.debug("Exception in ManageServiceOfferingsServiceImpl.insertAvailabilityDetails due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
	}
	public void deleteServiceOfferingPrice(Integer offeringId) throws BusinessServiceException{
		try{			
			 manageServiceOfferingsDao.deleteServiceOfferingPrice(offeringId);
		}
		catch(DataServiceException e){
			LOGGER.debug("Exception in ManageServiceOfferingsServiceImpl.insertAvailabilityDetails due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
	}
	
	public void deleteServiceOfferingAvailability(Integer offeringId) throws BusinessServiceException{
		try{			
			 manageServiceOfferingsDao.deleteServiceOfferingAvailability(offeringId);
		}
		catch(DataServiceException e){
			LOGGER.debug("Exception in ManageServiceOfferingsServiceImpl.insertAvailabilityDetails due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
	} 
	/**@Description: This method will fetch all the history details associated with a service offering.
	 * It includes data from service_offering_history,service_offering_price_history and 
	 * service_offering_availability_history
	 * @param offeringId
	 * @return
	 * @throws BusinessServiceException
	 */
    public ServiceOfferingHistoryVO fetchServiceOfferingHistory(Integer offeringId) throws BusinessServiceException {
    	ServiceOfferingHistoryVO historyVO =null;
    	try{
    		historyVO = manageServiceOfferingsDao.fetchServiceOfferingHistory(offeringId);
    	}catch (DataServiceException e) {
			LOGGER.error("Exception in fetching Service Offering History for the service offering id from fetchServiceOfferingHistory()"+ offeringId+ e);
			throw new BusinessServiceException(e);
		}
		return historyVO;
	}
    
    
	public List<AvailabilityVO>  fetchServiceOfferingAvailability(Integer vendorId) throws BusinessServiceException{
		List<AvailabilityVO> availabilityList=null; 
    	try{ 
    		availabilityList = manageServiceOfferingsDao.fetchServiceOfferingAvailability(vendorId); 
    	}catch (DataServiceException e) {
			LOGGER.error("Exception in fetching Service Offering Availability for the vendor id from fetchServiceOfferingAvailability()"+ vendorId+ e);
			throw new BusinessServiceException(e);
		}
		return availabilityList;
	}
	
	public ServiceOfferingsVO fetchServiceOfferingSkuDetails(Integer skuId) throws BusinessServiceException{
		ServiceOfferingsVO serviceOfferingsVO=null; 
    	try{ 
    		serviceOfferingsVO = manageServiceOfferingsDao.fetchServiceOfferingSkuDetails(skuId); 
    	}catch (DataServiceException e) {
			LOGGER.error("Exception in fetching Service Offering  for the sku id from fetchServiceOfferingSkuDeatils()"+ skuId+ e);
			throw new BusinessServiceException(e);
		}
		return serviceOfferingsVO;
	}

	
    public List<String> fetchAvailableZipCodes(String zipCode,String radius) throws BusinessServiceException{
    	List<String> zipCodeList =null;
    	try{
    		zipCodeList = getAvailableZipCodes(zipCode,"0",radius);
    	}catch (Exception e) {
    		LOGGER.error("Exception in fetching available zipcode  around the zip"+zipCode+" within the radius"+radius );
			throw new BusinessServiceException(e);
		}
		return zipCodeList; 
    }
    
    public List<String> getAvailableZipCodes(String zipCode, String minRadius,String maxRadius) {
		List<String> zipCodeList =null;
		Result response = invokeZipCodefinder(zipCode,minRadius,maxRadius);
		if(null!= response && null!= response.getDataList() && null!= response.getDataList().getCodeDetails()){
			List<CodeDetails> zipCodeResponse = response.getDataList().getCodeDetails();
			if(!zipCodeResponse.isEmpty()){
				zipCodeList = new ArrayList<String>();
				for(CodeDetails zip:zipCodeResponse){
					zipCodeList.add(zip.getZipCode());
				}
			}
		}
		return zipCodeList;
	}
	
    public Result invokeZipCodefinder(String zipCode, String minRadius,String maxRadius) {
		Result response =null;
		String responseXml = "";//getZipCodes(zipCode,minRadius,maxRadius);
		LOGGER.info("Response xml"+ responseXml);
		if(StringUtils.isNotBlank(responseXml)){
			if(StringUtils.contains(responseXml, "<Error>")){
				LOGGER.info("Error Response for the zip code finder");
			}else{
				LOGGER.info("Success Response for the zip code finder");
				response = (Result) deserializeLisResponse(responseXml,Result.class);
			}
		}
		return response;
	}
	/*public String getZipCodes(String zipCode,String minRadius, String maxRadius){
		WebResource resource;
		Client client = Client.create();
		String zipCodeResponse =null;
		try {
            client.setConnectTimeout(30000);
			client.setReadTimeout(30000);
			Map <String, String> apiDetails=manageServiceOfferingsDao.getZipCodeAPIDetails();
			String url ="";
			String apiKey="";
			if(null!=apiDetails){ 
				url=apiDetails.get("zipCodeAPIURL");
				apiKey=apiDetails.get("zipCodeAPIKey");
			}
			
					//url="http://api.zip-codes.com/ZipCodesAPI.svc/1.0/xml/FindZipCodesInRadius?";
			if(null!=zipCode){
				url=url.concat("?zipcode="+zipCode);
			}else{
				return "<Error>";
			}
            if(null!=maxRadius){
            	url=url.concat("&maximumradius="+maxRadius);
			}
            if(null!= minRadius){
            	url=url.concat("&minimumradius="+minRadius);
            }
			//String key ="DEMOAPIKEY";
			String completeUrl= url.concat("&key="+apiKey);
			resource = client.resource(completeUrl);
			zipCodeResponse =resource.accept("application/xml").get(String.class);
		}
		catch(ClientHandlerException ce){
			ce.printStackTrace();
		}
		catch (Exception e) {
			client.destroy();
			e.printStackTrace();
		}				
		return zipCodeResponse;
	}*/
	
	private Object deserializeLisResponse(String responseXml, Class<?> classz){
		XStream xstreamResponse = new XStream(new DomDriver());
		xstreamResponse.processAnnotations(classz);
		Result response = (Result) xstreamResponse.fromXML(responseXml);
		return response;
	}
	
	public ManageServiceOfferingsDao getManageServiceOfferingsDao() {
		return manageServiceOfferingsDao;
	}

	public void setManageServiceOfferingsDao(
			ManageServiceOfferingsDao manageServiceOfferingsDao) {
		this.manageServiceOfferingsDao = manageServiceOfferingsDao;
	}

	
	
	
	
	
}
