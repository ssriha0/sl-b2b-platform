/**
 *
 */
package com.newco.marketplace.web.action.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceOfferings.AvailabilityDTO;
import com.newco.marketplace.dto.vo.serviceOfferings.AvailabilityVO;
import com.newco.marketplace.dto.vo.serviceOfferings.PricingVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ServiceOfferingHistoryVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ServiceOfferingsDTO;
import com.newco.marketplace.dto.vo.serviceOfferings.ServiceOfferingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.service.provider.serviceofferings.ManageServiceOfferingsService;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.log4j.Logger;


public class ManageServiceOfferingsAction extends SLBaseAction implements ModelDriven<ServiceOfferingsDTO>{

	private static final long serialVersionUID = 123509443231332602L;	
	Logger logger = Logger.getLogger(ManageServiceOfferingsAction.class);	
	private ManageServiceOfferingsService manageServiceOfferingsService;
	private ServiceOfferingsDTO model = new ServiceOfferingsDTO();
	private ServiceOfferingsVO	serviceOfferingsVO=new ServiceOfferingsVO(); 
	private Integer vendorId=null;
    
    // display the service offering page
	public String display() throws Exception {
		HttpServletRequest request = getRequest();
		String skuId = request.getParameter("skuId");
		getRequest().setAttribute("skuId", skuId);
		String action=request.getParameter("action");
		getRequest().setAttribute("action", action);
		if (StringUtils.isNotBlank(skuId)){
		try {
		Integer serviceOfferingSkuId = new Integer(skuId);
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		vendorId= soContxt.getCompanyId();
		serviceOfferingsVO=manageServiceOfferingsService.fetchServiceOfferingDetails(vendorId, serviceOfferingSkuId);
		
		ServiceOfferingsVO skuDetails=manageServiceOfferingsService.fetchServiceOfferingSkuDetails(serviceOfferingSkuId);
		getRequest().setAttribute("skuDetails", skuDetails);

		populateModel();
		return SUCCESS;
		} catch (Exception e) {
			logger.error("Exception in prepare method of ManageServiceOfferingsAction" , e);
		return ERROR;
		}
	   }
		return ERROR;
	}
	
	// populate the existing data for the service offerings
	private void populateModel()
	{
		if (model == null) {
			model = new ServiceOfferingsDTO();
		}
		if (serviceOfferingsVO != null)
		{
			try {
				model.setCreatedBy(serviceOfferingsVO.getCreatedBy());
				model.setOfferingId(serviceOfferingsVO.getOfferingId());
				if(null!=serviceOfferingsVO.getDailyLimit()){
					model.setDailyLimit(serviceOfferingsVO.getDailyLimit().toString());
				}
				if(null!=serviceOfferingsVO.getServiceRadius()){
					model.setServiceRadius(serviceOfferingsVO.getServiceRadius().toString());  
				}
				if(null!=serviceOfferingsVO.getOfferingStatus()){
					model.setOfferingStatus(serviceOfferingsVO.getOfferingStatus());
				}
				if(null!=serviceOfferingsVO.getPriceList() && !serviceOfferingsVO.getPriceList().isEmpty())
				{
					List<PricingVO> pricingList=serviceOfferingsVO.getPriceList();
					for(PricingVO pricingVO:pricingList){
						if(null!=pricingVO.getIsCenter() && pricingVO.getIsCenter()){
							model.setPrice(pricingVO.getPrice().toString());
							model.setZipcode(pricingVO.getZipcode());
						}
				}
				if(null!=serviceOfferingsVO.getAvailabilityList()){
					List<AvailabilityDTO> availabilityList=new ArrayList<AvailabilityDTO>();
					for(int i=0;i<7;i++){
						AvailabilityDTO availabilityDTO=new AvailabilityDTO();
						setDaysIntheList(availabilityDTO,i);						
						availabilityList.add(availabilityDTO);
					}
					for(AvailabilityVO availabilityVO: serviceOfferingsVO.getAvailabilityList()){
						 setTotalAvailability(availabilityVO,availabilityList);					 	 
					}
					model.setAvailabilityList(availabilityList);
				}
				}
			} catch (Exception e) {
				logger.error("Unable to populate model for ManageServiceOfferingsAction: " + model, e);
			}
			getSession().setAttribute("serviceOfferingDto", model);
		}else{
			getSession().setAttribute("serviceOfferingDto", model);
		}
	}

	

	// save the service offering details
	public String save() throws Exception {	
		try{
		ServiceOfferingsDTO existingData =new ServiceOfferingsDTO();
		if(null!=getSession().getAttribute("serviceOfferingDto")){
		  existingData = (ServiceOfferingsDTO)getSession().getAttribute("serviceOfferingDto");
		}
		ServiceOfferingsDTO serviceOfferingsDTO=getModel();
		ServiceOfferingsVO serviceOfferingsVO=new ServiceOfferingsVO();
		serviceOfferingsVO = saveServiceOfferingDetails(existingData,
				serviceOfferingsDTO, serviceOfferingsVO);
		// save the pricing details with zipcodes	
		saveServiceOfferingPrice(existingData, serviceOfferingsDTO,
				serviceOfferingsVO);
		// save the availability information
		saveServiceOfferingAvailability(existingData, serviceOfferingsDTO,
				serviceOfferingsVO);
		return "dashboard";
		}catch(Exception e){
			logger.error("Exception in save of the service offerings",e);
			return "error";
		}
	}

	/**
	 * @param existingData
	 * @param serviceOfferingsDTO
	 * @param serviceOfferingsVO
	 * @throws BusinessServiceException
	 */
	public void saveServiceOfferingAvailability(
			ServiceOfferingsDTO existingData,
			ServiceOfferingsDTO serviceOfferingsDTO,
			ServiceOfferingsVO serviceOfferingsVO)
			throws BusinessServiceException {
		List<AvailabilityDTO> availabilityList=new ArrayList<AvailabilityDTO>();  
		List<AvailabilityVO> offeringsAvailability=new ArrayList<AvailabilityVO>();
		availabilityList=serviceOfferingsDTO.getAvailabilityList();
		if(null!=availabilityList && !availabilityList.isEmpty()){
			for(AvailabilityDTO availabilityDTO:availabilityList){ 
				if(null!=availabilityDTO){
				if("on".equals(availabilityDTO.getAllInd())){
					AvailabilityVO availabilityVO=setAvailibilty(serviceOfferingsVO, availabilityDTO);
					availabilityVO.setTimeWindow(1);
					offeringsAvailability.add(availabilityVO);
				}
				if("on".equals(availabilityDTO.getEarlyMorningInd())){
					AvailabilityVO availabilityVO=setAvailibilty(serviceOfferingsVO, availabilityDTO);
					availabilityVO.setTimeWindow(2);
					offeringsAvailability.add(availabilityVO);

				}
				if("on".equals(availabilityDTO.getMorningInd())){
					AvailabilityVO availabilityVO=setAvailibilty(serviceOfferingsVO, availabilityDTO);
					availabilityVO.setTimeWindow(3);
					offeringsAvailability.add(availabilityVO);

				}
				if("on".equals(availabilityDTO.getAfterNoonInd())){
					AvailabilityVO availabilityVO=setAvailibilty(serviceOfferingsVO, availabilityDTO);
					availabilityVO.setTimeWindow(4);
					offeringsAvailability.add(availabilityVO);

				}
				if("on".equals(availabilityDTO.getEveningInd())){
					AvailabilityVO availabilityVO=setAvailibilty(serviceOfferingsVO, availabilityDTO);
					availabilityVO.setTimeWindow(5);
					offeringsAvailability.add(availabilityVO);

				}
			  }
			}		
			List<AvailabilityDTO> availabilityListInModel=new ArrayList<AvailabilityDTO>();
			List<AvailabilityDTO> existingAvailabilityList=new ArrayList<AvailabilityDTO>();
			availabilityListInModel=serviceOfferingsDTO.getAvailabilityList();
			existingAvailabilityList=existingData.getAvailabilityList();
			int countOfAvailability=0;
			if(null!=existingAvailabilityList && !existingAvailabilityList.isEmpty()){
				for(AvailabilityDTO availabilityDTO:availabilityListInModel){
					for(AvailabilityDTO existingAvailibility:existingAvailabilityList){ 
						if(null!=availabilityDTO && null!=availabilityDTO.getDay() && availabilityDTO.getDay().equals(existingAvailibility.getDay())){
							
							if(  (availabilityDTO.getEarlyMorningInd()==existingAvailibility.getEarlyMorningInd() ||  (null!=availabilityDTO.getEarlyMorningInd() && availabilityDTO.getEarlyMorningInd().equals(existingAvailibility.getEarlyMorningInd())))
								&&	(availabilityDTO.getAfterNoonInd()==existingAvailibility.getAfterNoonInd() || (null!=availabilityDTO.getAfterNoonInd() && availabilityDTO.getAfterNoonInd().equals(existingAvailibility.getAfterNoonInd())))
								&& (availabilityDTO.getMorningInd()==existingAvailibility.getMorningInd() ||  (null!=availabilityDTO.getMorningInd() && availabilityDTO.getMorningInd().equals(existingAvailibility.getMorningInd())))
								&& (availabilityDTO.getEveningInd()==existingAvailibility.getEveningInd() || (null!=availabilityDTO.getEveningInd() && availabilityDTO.getEveningInd().equals(existingAvailibility.getEveningInd())))
								&& (availabilityDTO.getAllInd()==existingAvailibility.getAllInd() ||  (null!=availabilityDTO.getAllInd() && availabilityDTO.getAllInd().equals(existingAvailibility.getAllInd())))
									){
								countOfAvailability=countOfAvailability+1;
							}		
						}
						
					}
				}
				
			} 
			if(!(countOfAvailability==7)){
				if(existingData.getOfferingId()!=null){
				manageServiceOfferingsService.deleteServiceOfferingAvailability(existingData.getOfferingId());
				}
				manageServiceOfferingsService.saveAvailabilityDetails(offeringsAvailability);
			}						
		}
	}

	/**
	 * @param existingData
	 * @param serviceOfferingsDTO
	 * @param serviceOfferingsVO
	 * @throws BusinessServiceException
	 */
	public void saveServiceOfferingPrice(ServiceOfferingsDTO existingData,
			ServiceOfferingsDTO serviceOfferingsDTO,
			ServiceOfferingsVO serviceOfferingsVO)
			throws BusinessServiceException {
		//List<Integer> zipCodeList=manageServiceOfferingsService.getzipcodeList(serviceOfferingsDTO.getZipcode(), serviceOfferingsDTO.getServiceRadius());
		
		List<String> zipCodeList=manageServiceOfferingsService.fetchAvailableZipCodes(serviceOfferingsDTO.getZipcode(), serviceOfferingsDTO.getServiceRadius());
		
	  if(null!=zipCodeList && !zipCodeList.isEmpty()){
		  List<PricingVO> pricingList=new ArrayList<PricingVO>(); 
		  for(String zipCode:zipCodeList){  
		   PricingVO pricingVO=new PricingVO();
			pricingVO.setOfferingId(serviceOfferingsVO.getOfferingId());
			pricingVO.setPrice(new Double(Double.parseDouble(serviceOfferingsDTO.getPrice())));
			pricingVO.setZipcode(zipCode);
			pricingVO.setCreatedBy(serviceOfferingsVO.getCreatedBy());
			pricingVO.setModifiedBy(serviceOfferingsVO.getModifiedBy());
			if(zipCode.equals(serviceOfferingsDTO.getZipcode())){
				pricingVO.setIsCenter(true);	
			}
			pricingList.add(pricingVO); 
		  }
		  if(existingData.getOfferingId()!=null){
		  if(!(serviceOfferingsDTO.getZipcode().equals(existingData.getZipcode())) || !(serviceOfferingsDTO.getServiceRadius().equals(existingData.getServiceRadius())) ){
			  manageServiceOfferingsService.deleteServiceOfferingPrice(existingData.getOfferingId());
			  manageServiceOfferingsService.saveOfferingPriceDetails(pricingList);
		   }
		  else if(!(serviceOfferingsDTO.getPrice().equals(existingData.getPrice()))){
			  PricingVO pricingVO=pricingList.get(0);
			  manageServiceOfferingsService.updateOfferingPriceDetails(pricingVO);
		  }
		  }else{
		  manageServiceOfferingsService.saveOfferingPriceDetails(pricingList);
		  }
	   }
	}

	/**
	 * @param existingData
	 * @param serviceOfferingsDTO
	 * @param serviceOfferingsVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public ServiceOfferingsVO saveServiceOfferingDetails(
			ServiceOfferingsDTO existingData,
			ServiceOfferingsDTO serviceOfferingsDTO,
			ServiceOfferingsVO serviceOfferingsVO)
			throws BusinessServiceException {
		serviceOfferingsVO.setSkuId(new Integer(Integer.parseInt(serviceOfferingsDTO.getSkuId()))); 
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		vendorId= soContxt.getCompanyId();
		serviceOfferingsVO.setCreatedBy(soContxt.getUsername());
		serviceOfferingsVO.setModifiedBy(soContxt.getUsername());
		serviceOfferingsVO.setVendorId(vendorId);
		serviceOfferingsVO.setOfferingStatus(serviceOfferingsDTO.getOfferingStatus());
		serviceOfferingsVO.setServiceRadius(new Integer(Integer.parseInt(serviceOfferingsDTO.getServiceRadius())));
		serviceOfferingsVO.setOfferingStatus(serviceOfferingsDTO.getOfferingStatus());
		serviceOfferingsVO.setDailyLimit(new Integer(Integer.parseInt(serviceOfferingsDTO.getDailyLimit()))); 
	    if(existingData.getOfferingId()!=null){
	    	serviceOfferingsVO.setOfferingId(existingData.getOfferingId());
	    	if(!(serviceOfferingsDTO.getDailyLimit().equals(existingData.getDailyLimit())) ||  !(serviceOfferingsDTO.getServiceRadius().equals(existingData.getServiceRadius()))
	    		|| (serviceOfferingsDTO.getOfferingStatus().equals(existingData.getOfferingStatus()))){
	    	manageServiceOfferingsService.updateOfferingDetails(serviceOfferingsVO);
			}
	    }else{
		serviceOfferingsVO= manageServiceOfferingsService.saveOfferingDetails(serviceOfferingsVO);
	    }
		return serviceOfferingsVO;
	}

	
	/**@Description: This method will fetch all the history details associated with a service offering.
	 * It includes data from service_offering_history,service_offering_price_history and 
	 * service_offering_availability_history
	 * @return
	 */
	public String getHistory(){
		String offeringId = null;
		ServiceOfferingHistoryVO historyVO=null;
		offeringId = getRequest().getParameter("offeringId");
		if(StringUtils.isBlank(offeringId) || !StringUtils.isNumeric(offeringId)){
			return ERROR;
		}else{
			try {
				historyVO = manageServiceOfferingsService.fetchServiceOfferingHistory(Integer.parseInt(offeringId));
			} catch (Exception e) {
				logger.error("Exception in fetching Service Offering History for the service offering id"+ offeringId);
				return ERROR;
			}
		}
		getSession().setAttribute("serviceOfferingHistory", historyVO);
		return SUCCESS;
		
	}
	
	//set the availabilityVo
	public AvailabilityVO setAvailibilty(ServiceOfferingsVO serviceOfferingsVO,
			AvailabilityDTO availabilityDTO) {
		AvailabilityVO availabilityVO=new AvailabilityVO();
		availabilityVO.setOfferingId(serviceOfferingsVO.getOfferingId());
		availabilityVO.setCreatedBy(serviceOfferingsVO.getCreatedBy());
		availabilityVO.setModifiedBy(serviceOfferingsVO.getModifiedBy());
		availabilityVO.setDayOfTheWeek(availabilityDTO.getDay());
		return availabilityVO;
	}
	
	
	// set the availability information
	public void setTotalAvailability(AvailabilityVO availabilityVO,List<AvailabilityDTO>  availabilityList) {
		if("Monday".equals(availabilityVO.getDayOfTheWeek())){ 
			 setAvailbilityOfTheDay(availabilityList,
					availabilityVO,0);
			 
		 }else if("Tuesday".equals(availabilityVO.getDayOfTheWeek())){
			 setAvailbilityOfTheDay(availabilityList,
						availabilityVO,1);

		  }else if("Wednesday".equals(availabilityVO.getDayOfTheWeek())){
				 setAvailbilityOfTheDay(availabilityList,
							availabilityVO,2);
		  }else if("Thursday".equals(availabilityVO.getDayOfTheWeek())){
				 setAvailbilityOfTheDay(availabilityList,
							availabilityVO,3);
		  }
		  else if("Friday".equals(availabilityVO.getDayOfTheWeek())){
				 setAvailbilityOfTheDay(availabilityList,
							availabilityVO,4);

		  }else if("Saturday".equals(availabilityVO.getDayOfTheWeek())){
					 setAvailbilityOfTheDay(availabilityList,
								availabilityVO,5);

		  }else if("Sunday".equals(availabilityVO.getDayOfTheWeek())){
						 setAvailbilityOfTheDay(availabilityList,
									availabilityVO,6);

		  }
	}
   
	// populate the days in the availability List
	public void setDaysIntheList(AvailabilityDTO availabilityDTO,int count) {
		if(count==0){
			availabilityDTO.setDay("Monday");	
		}else if(count==1){
			availabilityDTO.setDay("Tuesday");	
		}else if(count==2){
			availabilityDTO.setDay("Wednesday");	
		}else if(count==3){
			availabilityDTO.setDay("Thursday");	
		}else if(count==4){
			availabilityDTO.setDay("Friday");	
		}else if(count==5){
			availabilityDTO.setDay("Saturday");	
		}else if(count==6){
			availabilityDTO.setDay("Sunday");	
		}
	}

	public void setAvailbilityOfTheDay(List<AvailabilityDTO> availabilityList,
			AvailabilityVO availabilityVO,int dayCount) {
	 
		 if(new Integer(1).equals(availabilityVO.getTimeWindow())){
			 availabilityList.get(dayCount).setAllInd("on");
		 }else if(new Integer(2).equals(availabilityVO.getTimeWindow())){
			 availabilityList.get(dayCount).setEarlyMorningInd("on");
		 }else if(new Integer(3).equals(availabilityVO.getTimeWindow())){
			 availabilityList.get(dayCount).setMorningInd("on");
		 }else if(new Integer(4).equals(availabilityVO.getTimeWindow())){
			 availabilityList.get(dayCount).setAfterNoonInd("on");
		 }else if(new Integer(5).equals(availabilityVO.getTimeWindow())){
			 availabilityList.get(dayCount).setEveningInd("on");
		 }
		 
	}

	public ManageServiceOfferingsService getManageServiceOfferingsService() {
		return manageServiceOfferingsService;
	}

	public void setManageServiceOfferingsService(
			ManageServiceOfferingsService manageServiceOfferingsService) {
		this.manageServiceOfferingsService = manageServiceOfferingsService;
	}

	
	public ServiceOfferingsVO getServiceOfferingsVO() {
		return serviceOfferingsVO;
	}

	public void setServiceOfferingsVO(ServiceOfferingsVO serviceOfferingsVO) {
		this.serviceOfferingsVO = serviceOfferingsVO;
	}

	public ServiceOfferingsDTO getModel() {
		return model;
	}

	public void setModel(ServiceOfferingsDTO model) {
		this.model = model; 
	}

	public Integer getVendorId() {
		return vendorId;
	}
	
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
    
}
