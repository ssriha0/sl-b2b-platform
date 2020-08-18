/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 12-Mar-2010	KMSTRSUP   Infosys				1.0
 *
 *
 */
package com.newco.marketplace.api.utils.mappers.so.v1_4;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.BusinessPhone;
import com.newco.marketplace.api.beans.so.Estimate;
import com.newco.marketplace.api.beans.so.EstimateDetails;
import com.newco.marketplace.api.beans.so.EstimateItemsHistory;
import com.newco.marketplace.api.beans.so.EstimateFirmDetails;
import com.newco.marketplace.api.beans.so.EstimateHistory;
import com.newco.marketplace.api.beans.so.EstimateHistoryDetails;
import com.newco.marketplace.api.beans.so.EstimateLocation;
import com.newco.marketplace.api.beans.so.EstimatePart;
import com.newco.marketplace.api.beans.so.EstimatePartHistory;
import com.newco.marketplace.api.beans.so.EstimateParts;
import com.newco.marketplace.api.beans.so.EstimatePartsHistory;
import com.newco.marketplace.api.beans.so.EstimatePricingDetails;
import com.newco.marketplace.api.beans.so.EstimateProviderDetails;
import com.newco.marketplace.api.beans.so.Estimates;
import com.newco.marketplace.api.beans.so.FirmContact;
import com.newco.marketplace.api.beans.so.LaborTaskHistory;
import com.newco.marketplace.api.beans.so.LaborTasks;
import com.newco.marketplace.api.beans.so.LaborTasksHistory;
import com.newco.marketplace.api.beans.so.Labortask;
import com.newco.marketplace.api.beans.so.Location;
import com.newco.marketplace.api.beans.so.Reschedule;
import com.newco.marketplace.api.beans.so.Schedule;
import com.newco.marketplace.api.beans.so.ScopeOfWork;
import com.newco.marketplace.api.beans.so.ServiceOrderReview;
import com.newco.marketplace.dto.vo.addons.*;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_2.SORetrieveMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.business.businessImpl.so.pdf.SOPDFUtils;
import com.newco.marketplace.dto.vo.EstimateHistoryTaskVO;
import com.newco.marketplace.dto.vo.EstimateHistoryVO;
import com.newco.marketplace.dto.vo.EstimateLocationVO;
import com.newco.marketplace.dto.vo.EstimateTaskVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.dateTimeSlots.ScheduleServiceSlot;
import com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDateTimeSlots;
import com.newco.marketplace.dto.vo.leadsmanagement.ReviewVO;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.api.beans.so.EstimateOtherServiceHistory;
import com.newco.marketplace.api.beans.so.EstimateOtherServicesHistory;
import com.newco.marketplace.api.beans.so.OtherService;
import com.newco.marketplace.api.beans.so.OtherServices;
import com.newco.marketplace.api.beans.so.retrieve.v1_2.RetrieveServiceOrder;
import com.newco.marketplace.api.beans.so.Skus;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;


/**
 * This class would act as a Mapper class for Mapping Service Object to
 * SORetrieveResponse Object.
 *
 * @author Infosys
 * @version 1.3
 */
public class SORetrieveMapperV1_4 extends  SORetrieveMapper{
private Logger logger = Logger.getLogger(SORetrieveMapperV1_4.class);

	
	/**@Description:This will add the Provider Firm details if response 
	 * 				filter Routed Providers is present 
	 * @param response
	 * @param serviceOrder
	 * @param responseFilters
	 * @return
	 */
	public RetrieveServiceOrder mapAcceptedFirmDetails(RetrieveServiceOrder response, ServiceOrder serviceOrder) {
		if(null != serviceOrder.getFirmContact() && 
			null!= serviceOrder.getFirmContact().getVendorId()){
    			FirmContact providerFirmDetails =new FirmContact();
    			com.newco.marketplace.dto.vo.provider.FirmContact firmContact = serviceOrder.getFirmContact();
    			//SL-21529
    			Location firmLocation = null;
    			providerFirmDetails = mapProviderFirmDetails(providerFirmDetails,firmContact);
    			if(null != serviceOrder.getAcceptedResource()&& 
    			   null != serviceOrder.getAcceptedResource().getResourceId()){
    				providerFirmDetails.setResponse(PublicAPIConstant.PROVIDER_ASSIGNED);
    			}else{
    				providerFirmDetails.setResponse(PublicAPIConstant.PROVIDER_NOT_ASSIGNED);
    			}
    			
    			// For buyer 3333, set the accepted firmId
    			if(PublicAPIConstant.RELAY_SERVICES_BUYER_ID.equals(serviceOrder.getBuyer().getBuyerId()) || 
    					PublicAPIConstant.TECHTALK_SERVICES_BUYER_ID.equals(serviceOrder.getBuyer().getBuyerId())){
    				providerFirmDetails.setFirmId(firmContact.getVendorId());
    				//SL-21529
    				if(null != firmContact.getFirmLocation()){
    					firmLocation = new Location();
        				firmLocation.setAddress1(firmContact.getFirmLocation().getAddress1());
        				firmLocation.setAddress2(firmContact.getFirmLocation().getAddress2());
        				firmLocation.setCity(firmContact.getFirmLocation().getCity());
        				firmLocation.setState(firmContact.getFirmLocation().getState());
        				firmLocation.setZip(firmContact.getFirmLocation().getZip());
    				}
    				
    			}
    			//SL-21529
    			providerFirmDetails.setFirmLocation(firmLocation);
    			response.setAcceptedProviderFirmContact(providerFirmDetails);
			}
		return response;
	}
	/**@Description:mapping provider firm contact details to the response object
	 * @param providerFirmDetails
	 * @param firmContact
	 * @return
	 */
	private FirmContact mapProviderFirmDetails(FirmContact providerFirmDetails,
			com.newco.marketplace.dto.vo.provider.FirmContact firmContact) {
		providerFirmDetails.setFirmName(firmContact.getFirmName());
		/**Trimming the admin name as it is a combination of first name+ last name.
		 * leading/Trailing white space can be present in admin name*/
		if(StringUtils.isNotBlank(firmContact.getAdminName())){
		   providerFirmDetails.setAdminName(firmContact.getAdminName().trim());
		}
		providerFirmDetails.setEmail(firmContact.getEmail());
		/**Formatting the phone no in xxx-xxx-xxxx format*/
		String primaryPhoneNo = SOPDFUtils.formatPhoneNumber(firmContact.getAdminPrimaryPhone());
		String alternatePhoneNo =SOPDFUtils.formatPhoneNumber(firmContact.getAdminAlternatePhone());
		String businessPhoneNo = SOPDFUtils.formatPhoneNumber(firmContact.getNumber());
		BusinessPhone businessPhone = new BusinessPhone();
		businessPhone.setNumber(businessPhoneNo);
		businessPhone.setExtension(firmContact.getExtension());
		providerFirmDetails.setBusinessPhone(businessPhone);
		providerFirmDetails.setAdminPrimaryPhone(primaryPhoneNo);
		providerFirmDetails.setAdminAlternatePhone(alternatePhoneNo);
		return providerFirmDetails;
	}
	
	/**B2C Changes
	 * @Description:This will map Estimate Details
	 * @param response
	 * @param serviceOrder
	 * @return
	 */
	public RetrieveServiceOrder mapEstimateDetails(RetrieveServiceOrder response, ServiceOrder serviceOrder) {
		
		if(null != serviceOrder.getEstimateList() && 
			!(serviceOrder.getEstimateList().isEmpty())){
			
			Iterator<EstimateVO> estimateList = serviceOrder.getEstimateList()
					.iterator();
			
			logger.info("inside mapEstimateDetails :::"+serviceOrder.getEstimateList().size());
			Estimates estimates = new Estimates();
			List<Estimate> estimateListResponse = new ArrayList<Estimate>();
			while (estimateList.hasNext()) {
				
				Estimate estimate = new Estimate();
				EstimateVO estimateVO = estimateList.next();
				if (null!=estimateVO){
					estimate.setEstimationId(estimateVO.getEstimationId());
					estimate.setEstimationRefNo(estimateVO.getEstimationRefNo());
					if(null!=estimateVO.getEstimationDate()){
						estimate.setEstimateDate(DateUtils.formatDate(estimateVO.getEstimationDate()));
					}
					if(null!=estimateVO.getEstimationExpiryDate()){
						estimate.setEstimationExpiryDate(DateUtils.formatDate(estimateVO.getEstimationExpiryDate()));
					}
					if(StringUtils.isNotBlank(estimateVO.getStatus()) &&
							(estimateVO.getStatus().equals(MPConstants.ACCEPTED_STATUS) || estimateVO.getStatus().equals(MPConstants.DECLINED_STATUS))){
						Date responseDate = estimateVO.getResponseDate();
						if(null!= responseDate){
							//Convert response Date to service location timezone  and then to  yyyy-MM-dd'T'HH:mm:ss format
							Timestamp ts = new Timestamp(responseDate.getTime());
							responseDate =TimeUtils.convertTimeFromOneTimeZoneToOther(ts,MPConstants.SERVER_TIMEZONE,serviceOrder.getServiceLocationTimeZone());
							estimate.setResponseDate(DateUtils.formatDate(responseDate));
							logger.info("Accepted/Declined Date"+ estimate.getResponseDate());
						}
					}
					//mapping vendor and provider details
					estimate.setFirmDetails(mapFirmDetails(estimateVO.getVendorId(),estimateVO.getFirmLocation()));
					estimate.setProviderDetails(mapProviderDetails(estimateVO.getResourceId(),estimateVO.getProviderLocation()));
					
					/*estimate.setDescription(estimateVO.getDescription());
					estimate.setAcceptSource(estimateVO.getAcceptSource());
					estimate.setTripCharge(estimateVO.getTripCharge());*/
					estimate.setStatus(estimateVO.getStatus());
					/*estimate.setRespondedCustomerName(estimateVO.getRespondedCustomerName());
					 * if(null!=estimateVO.getResponseDate()){
					 * estimate.setResponseDate(DateUtils.formatDate(estimateVO.getResponseDate()));
					 * }
					*/
					EstimateDetails estimateDetails = new EstimateDetails();
					
					LaborTasks laborTasks = new LaborTasks();
					EstimateParts estimateParts = new EstimateParts();
					OtherServices otherServices =new OtherServices();
					List<Labortask> laborTaskList = new ArrayList<Labortask>();
					List<EstimatePart> estimatePartList = new ArrayList<EstimatePart>();
					//SL-21387
					List<OtherService> otherServicelist=new ArrayList<OtherService>();
					
					List<EstimateTaskVO> estimateItems = estimateVO.getEstimateTasks();
				
					// mapping labor tasks
					if(null!=estimateItems && !(estimateItems.isEmpty())){
						logger.info("inside mapEstimateDetails :::"+estimateItems.size());
						
						for (EstimateTaskVO estimateTaskVO : estimateItems) {
								
								if (null!= estimateTaskVO){
									Labortask labortask = new Labortask();
									labortask.setItemId(estimateTaskVO.getItemId());
									labortask.setTaskSeqNo(estimateTaskVO.getTaskSeqNumber());
									labortask.setTaskName(estimateTaskVO.getTaskName());
									labortask.setDescription(estimateTaskVO
											.getDescription());
								//	twoDecimalFormat.format(serviceOrder.getSpendLimitLabor())
									if(null!=estimateTaskVO.getUnitPrice())
									labortask.setUnitPrice(String.format("%.2f",estimateTaskVO.getUnitPrice()));
									labortask.setQuantity(estimateTaskVO.getQuantity());
									if(null!=estimateTaskVO.getTotalPrice())
									labortask.setTotalPrice(String.format("%.2f",estimateTaskVO.getTotalPrice()));
									labortask.setAdditionalDetails(estimateTaskVO
											.getAdditionalDetails());
									laborTaskList.add(labortask); 
								}
						}
					}
					
					
					//mapping part items
					estimateItems = estimateVO.getEstimateParts();
					if(null!=estimateItems && !(estimateItems.isEmpty())){
						for(EstimateTaskVO estimateTaskVO : estimateItems){
							
							if(null!= estimateTaskVO){
								EstimatePart estimatePart = new EstimatePart();
								estimatePart.setItemId(estimateTaskVO.getItemId());
								estimatePart.setPartSeqNo(estimateTaskVO
										.getPartSeqNumber());
								estimatePart.setPartNo(estimateTaskVO.getPartNumber());
								estimatePart.setPartName(estimateTaskVO.getPartName());
								estimatePart.setDescription(estimateTaskVO
										.getDescription());
								if(null!=estimateTaskVO.getUnitPrice())
								estimatePart
										.setUnitPrice(String.format("%.2f",estimateTaskVO.getUnitPrice()));
								estimatePart.setQuantity(estimateTaskVO.getQuantity());
								if(null!=estimateTaskVO
										.getTotalPrice())
								estimatePart.setTotalPrice(String.format("%.2f",estimateTaskVO
										.getTotalPrice()));
								estimatePart.setAdditionalDetails(estimateTaskVO
										.getAdditionalDetails());
								estimatePartList.add(estimatePart);
							}	
						}
					}
					
					
					//mapping other estimate services items
					 estimateItems=estimateVO.getEstimateOtherEstimateServices();
					if(null!=estimateItems && !(estimateItems.isEmpty())){
					for(EstimateTaskVO estimateTaskVO : estimateItems){
					if(null!= estimateTaskVO){
					OtherService otherService =new OtherService();
				    otherService.setItemId(estimateTaskVO.getItemId());					
					otherService.setOtherServiceSeqNumber(estimateTaskVO.getOtherServiceSeqNumber());
					otherService.setOtherServiceType(estimateTaskVO.getOtherServiceType());
					otherService.setOtherServiceName(estimateTaskVO.getOtherServiceName());
					otherService.setDescription(estimateTaskVO.getDescription());
					if(null!=estimateTaskVO.getUnitPrice())
					otherService.setUnitPrice(String.format("%.2f",estimateTaskVO.getUnitPrice()));
					otherService.setQuantity(estimateTaskVO.getQuantity());
					if(null!=estimateTaskVO.getTotalPrice())
					otherService.setTotalPrice(String.format("%.2f",estimateTaskVO.getTotalPrice()));
					otherService.setAdditionalDetails(estimateTaskVO.getAdditionalDetails());
					otherServicelist.add(otherService);
						  	                                                         }
					 	  	                                                 }
						  	                                       }
					
					if(null!=laborTaskList && !(laborTaskList.isEmpty())){
						laborTasks.setLabortask(laborTaskList);
						estimateDetails.setLaborTasks(laborTasks);	
					}	
					
					if(null!=estimatePartList && !(estimatePartList.isEmpty())){
						estimateParts.setPart(estimatePartList);
						estimateDetails.setParts(estimateParts);
					}
					//SL-21387
					if(null!=otherServicelist && !(otherServicelist.isEmpty())){
						otherServices.setOtherService(otherServicelist);
						estimateDetails.setOtherServices(otherServices);
					}					
					
					if(null!=estimateDetails && (null!=estimateDetails.getLaborTasks() || null!=estimateDetails.getParts() || null!=estimateDetails.getOtherServices())){
						estimate.setEstimateDetails(estimateDetails);
					}
					EstimatePricingDetails estimatePricingDetails = new EstimatePricingDetails();
					Double subTotal=0.0d;
					if(null!=estimateVO.getTotalLaborPrice())
					estimatePricingDetails.setTotalLaborPrice(String.format("%.2f",estimateVO.getTotalLaborPrice()));
					if(null!=estimateVO.getTotalPartsPrice())
					estimatePricingDetails.setTotalPartsPrice(String.format("%.2f",estimateVO.getTotalPartsPrice()));
					//SL-21387
					if(null!=estimateVO.getTotalOtherServicePrice())
					estimatePricingDetails.setTotalOtherServicePrice(String.format("%.2f",estimateVO.getTotalOtherServicePrice()));
					DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL);
			        if(null!=estimateVO.getTotalLaborPrice()){
			            subTotal=subTotal+estimateVO.getTotalLaborPrice().doubleValue();   
			        }
			        if(null!=estimateVO.getTotalPartsPrice()){
			            subTotal=subTotal+estimateVO.getTotalPartsPrice().doubleValue();   
			        }
			        logger.info("subtotal before formatting:"+subTotal);
			        if(null !=subTotal){
			        	estimatePricingDetails.setSubTotal(String.format("%.2f",subTotal));
			        }
			        //SL-21387
			        if(null!=estimateVO.getTotalOtherServicePrice()){
			            subTotal=subTotal+estimateVO.getTotalOtherServicePrice().doubleValue();   
			        }			        
			        logger.info("subtotal after formatting:"+subTotal);
					estimatePricingDetails.setDiscountType(estimateVO.getDiscountType());
					if(null!=estimateVO.getDiscountedPercentage()){
					estimatePricingDetails.setDiscountedPercentage(String.format("%.2f",estimateVO.getDiscountedPercentage()));
					}
					if(null!=estimateVO.getDiscountedAmount()){
					estimatePricingDetails.setDiscountedAmount(String.format("%.2f",estimateVO.getDiscountedAmount()));
					}
					if(null!=estimateVO.getTaxRate()){
					estimatePricingDetails.setTaxRate(String.format("%.2f",estimateVO.getTaxRate()));
					}
					estimatePricingDetails.setTaxType(estimateVO.getTaxType());
					if(null!=estimateVO.getTaxPrice()){
					estimatePricingDetails.setTaxPrice(String.format("%.2f",estimateVO.getTaxPrice()));
					}
					if(null!=estimateVO.getTotalPrice()){
					estimatePricingDetails.setTotalPrice(String.format("%.2f",estimateVO.getTotalPrice()));
					}
					
					//SL-21939
					
						estimatePricingDetails.setLaborDiscountType(estimateVO.getLaborDiscountType());
						estimatePricingDetails.setPartsDiscountType(estimateVO.getPartsDiscountType());
						
					if(null!=estimateVO.getLaborDiscountedPercentage()){
						estimatePricingDetails.setLaborDiscountedPercentage(String.format("%.2f",estimateVO.getLaborDiscountedPercentage()));
						
					}
					if(null!=estimateVO.getLaborDiscountedAmount()){
						estimatePricingDetails.setLaborDiscountedAmount(String.format("%.2f",estimateVO.getLaborDiscountedAmount()));
					}
					if(null!=estimateVO.getLaborTaxRate()){
						estimatePricingDetails.setLaborTaxRate(String.format("%.2f",estimateVO.getLaborTaxRate()));
					}
					if(null!=estimateVO.getLaborTaxPrice()){
						estimatePricingDetails.setLaborTaxPrice(String.format("%.2f",estimateVO.getLaborTaxPrice()));
					}
					
					if(null!=estimateVO.getPartsDiscountedPercentage()){
						estimatePricingDetails.setPartsDiscountedPercentage(String.format("%.2f",estimateVO.getPartsDiscountedPercentage()));
					}
					if(null!=estimateVO.getPartsDiscountedAmount()){
						estimatePricingDetails.setPartsDiscountedAmount(String.format("%.2f",estimateVO.getPartsDiscountedAmount()));
					}
					if(null!=estimateVO.getPartsTaxRate()){
						estimatePricingDetails.setPartsTaxRate(String.format("%.2f",estimateVO.getPartsTaxRate()));
					}
					if(null!=estimateVO.getPartsTaxPrice()){
						estimatePricingDetails.setPartsTaxPrice(String.format("%.2f",estimateVO.getPartsTaxPrice()));
					}
					
					estimate.setPricing(estimatePricingDetails);
					estimate.setComments(estimateVO.getComments());
					estimate.setLogoDocumentId(estimateVO.getLogoDocumentId());
					//mapping history details for the estimate
					estimate.setEstimateHistoryDetails(mapHistoryDetails(estimateVO.getEstimateHistoryList(),estimateVO.getEstimateTasksHistory(),
							estimateVO.getEstimatePartsHistory(),estimateVO.getEstimateOtherServicesHistory(),serviceOrder.getServiceLocationTimeZone()));
					
					estimateListResponse.add(estimate);
					}
			}
		estimates.setEstimates(estimateListResponse);
		
		response.setEstimateFlag(true);
	    response.setEstimates(estimates);
	}
	else{
		response.setEstimateFlag(false);
		}
		return response;
	}
	
	/**mapping provider details
	 * @param resourceId
	 * @param providerLocation
	 * @return
	 */
	private EstimateProviderDetails mapProviderDetails(Integer resourceId,
			EstimateLocationVO providerLocation) {
		EstimateProviderDetails estimateProviderDetails = null;
		if(null!=resourceId || null!=providerLocation){
			estimateProviderDetails = new EstimateProviderDetails();
			estimateProviderDetails.setResourceId(resourceId);
			estimateProviderDetails.setLocation(mapLocationDetails(providerLocation));
		}
		return estimateProviderDetails;
	}
	
	
	/**mapping vendor details
	 * @param vendorId
	 * @param firmLocation
	 * @return
	 */
	private EstimateFirmDetails mapFirmDetails(Integer vendorId,
			EstimateLocationVO firmLocation) {
		EstimateFirmDetails estimateFirmDetails = null;
		if(null!=vendorId || null!=firmLocation){
			estimateFirmDetails = new EstimateFirmDetails();
			estimateFirmDetails.setVendorId(vendorId);
			estimateFirmDetails.setLocation(mapLocationDetails(firmLocation));
		}
		return estimateFirmDetails;
	}
	
	/** mapping location details of vendor and provider
	 * @param location
	 * @return
	 */
	private EstimateLocation mapLocationDetails(EstimateLocationVO location) {
		EstimateLocation estimateLocation = null;
		if(null!=location){
			estimateLocation = new EstimateLocation();
			estimateLocation.setAddress1(location.getStreet_1());
			estimateLocation.setAddress2(location.getStreet_2());
			estimateLocation.setCity(location.getCity());
			estimateLocation.setState(location.getState_cd());
			estimateLocation.setZip(location.getZip());
			if(StringUtils.isNotBlank(location.getZip4())){
				estimateLocation.setZip4(location.getZip4());
			}
		}
		return estimateLocation;
	}
	
	/** mapping review details
	 * @param response
	 * @param serviceOrder
	 * @return
	 */
	public RetrieveServiceOrder mapReviewDetails(RetrieveServiceOrder response,
			List<ReviewVO> reviewVOList) {
		ServiceOrderReview review = null;
		if(null!=reviewVOList && !reviewVOList.isEmpty()){
			review = new ServiceOrderReview();
			for (ReviewVO reviewVO:reviewVOList){
				if(null!=reviewVO){
					if(reviewVO.getQuestion().equalsIgnoreCase(PublicAPIConstant.ProviderReviews.CLEANLINESS)){
						review.setCleanliness(reviewVO.getScore());
					}
					else if(reviewVO.getQuestion().equalsIgnoreCase(PublicAPIConstant.ProviderReviews.TIMELINESS)){
						review.setTimeliness(reviewVO.getScore());
					}
					else if(reviewVO.getQuestion().equalsIgnoreCase(PublicAPIConstant.ProviderReviews.COMMUNICATION)){
						review.setCommunication(reviewVO.getScore());
					}
					else if(reviewVO.getQuestion().equalsIgnoreCase(PublicAPIConstant.ProviderReviews.PROFESSIONALISM)){
						review.setProfessionalism(reviewVO.getScore());
					}
					else if(reviewVO.getQuestion().equalsIgnoreCase(PublicAPIConstant.ProviderReviews.QUALITY)){
						review.setQuality(reviewVO.getScore());
					}
					else if(reviewVO.getQuestion().equalsIgnoreCase(PublicAPIConstant.ProviderReviews.VALUE)){
						review.setValue(reviewVO.getScore());
					}
				}
			}
			ReviewVO reviewElement = reviewVOList.get(PublicAPIConstant.INTEGER_ZERO);
			if(null!=reviewElement){
				DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_THREE_DECIMAL);
				if(null !=reviewElement.getReviewRatingObj()){
					review.setOverallRating(df.format(reviewElement.getReviewRatingObj()));
		        }
				review.setComment(reviewElement.getReviewComment());
				if(null!=reviewElement.getReviewDateObj()){
					review.setDate(DateUtils.formatDate(reviewElement.getReviewDateObj()));
				}
			}
		}
		response.setReview(review);
		return response;
	}	
	
	/** mapping estimate history details
	 * @param estimateHistoryList
	 * @param estimatePartList 
	 * @param estimateTaskList
	 * @param otherServicelist  
	 * @param timezone 
	 * @return
	 */
	private EstimateHistoryDetails mapHistoryDetails(
			List<EstimateHistoryVO> estimateHistoryList, List<EstimateHistoryTaskVO> estimateTaskList, List<EstimateHistoryTaskVO> estimatePartList,
			List<EstimateHistoryTaskVO> estimateOtherServiceList, String timezone) {
		 
		Map<Integer,List<EstimateHistoryTaskVO>> laborTaskMap=new HashMap<Integer,List<EstimateHistoryTaskVO>>();
		Map<Integer,List<EstimateHistoryTaskVO>> partTaskMap=new HashMap<Integer,List<EstimateHistoryTaskVO>>();
		Map<Integer,List<EstimateHistoryTaskVO>> otherTaskMap=new HashMap<Integer,List<EstimateHistoryTaskVO>>();

		if(null!=estimateTaskList && estimateTaskList.size()>0){
		for(EstimateHistoryTaskVO estimateHistoryTaskVO: estimateTaskList){		
			if(laborTaskMap.containsKey(estimateHistoryTaskVO.getEstimationHistoryId())){
				List<EstimateHistoryTaskVO> history=laborTaskMap.get(estimateHistoryTaskVO.getEstimationHistoryId());
				history.add(estimateHistoryTaskVO);
				laborTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);
			}else{
				List<EstimateHistoryTaskVO> history=new ArrayList<EstimateHistoryTaskVO>();
				history.add(estimateHistoryTaskVO);
				laborTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

			}			
		}
		}
		
		if(null!=estimatePartList && estimatePartList.size()>0){
		for(EstimateHistoryTaskVO estimateHistoryTaskVO: estimatePartList){		
			if(partTaskMap.containsKey(estimateHistoryTaskVO.getEstimationHistoryId())){
				List<EstimateHistoryTaskVO> history=partTaskMap.get(estimateHistoryTaskVO.getEstimationHistoryId());
				history.add(estimateHistoryTaskVO);
				partTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

			}else{
				List<EstimateHistoryTaskVO> history=new ArrayList<EstimateHistoryTaskVO>();
				history.add(estimateHistoryTaskVO);
				partTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

			}			
		}
		}
		
		if(null!=estimateOtherServiceList && estimateOtherServiceList.size()>0){
		for(EstimateHistoryTaskVO estimateHistoryTaskVO: estimateOtherServiceList){		
			if(otherTaskMap.containsKey(estimateHistoryTaskVO.getEstimationHistoryId())){
				List<EstimateHistoryTaskVO> history=otherTaskMap.get(estimateHistoryTaskVO.getEstimationHistoryId());
				history.add(estimateHistoryTaskVO);
				otherTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

			}else{
				List<EstimateHistoryTaskVO> history=new ArrayList<EstimateHistoryTaskVO>();
				history.add(estimateHistoryTaskVO);
				otherTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

			}			
		}
		}
		
		
		EstimateHistoryDetails estimateHistoryDetails = null;
		if (null!=estimateHistoryList && !estimateHistoryList.isEmpty()){
			estimateHistoryDetails = new EstimateHistoryDetails();
			List<EstimateHistory> historyList = new ArrayList<EstimateHistory>();
			for (EstimateHistoryVO estimateHistoryVO:estimateHistoryList){
				if(null!=estimateHistoryVO){
					EstimateHistory estimateHistory = new EstimateHistory();
					estimateHistory.setEstimationHistoryId(estimateHistoryVO.getEstimationHistoryId());
					estimateHistory.setEstimationId(estimateHistoryVO.getEstimationId());
					estimateHistory.setEstimationRefNo(estimateHistoryVO.getEstimationRefNo());
					if(null!=estimateHistoryVO.getEstimationDate()){
						estimateHistory.setEstimateDate(DateUtils.formatDate(estimateHistoryVO.getEstimationDate()));
					}
					if(StringUtils.isNotBlank(estimateHistoryVO.getStatus()) &&
							(estimateHistoryVO.getStatus().equals(MPConstants.ACCEPTED_STATUS) || estimateHistoryVO.getStatus().equals(MPConstants.DECLINED_STATUS))){
						Date responseDate = estimateHistoryVO.getResponseDate();
						if(null!= responseDate){
							//Convert response Date to service location timezone  and then to  yyyy-MM-dd'T'HH:mm:ss format
							Timestamp ts = new Timestamp(responseDate.getTime());
							responseDate =TimeUtils.convertTimeFromOneTimeZoneToOther(ts,MPConstants.SERVER_TIMEZONE,timezone);
							estimateHistory.setResponseDate(DateUtils.formatDate(responseDate));
							logger.info("Accepted/Declined Date"+ estimateHistory.getResponseDate());
						}
					}
					
					if(null!=estimateHistoryVO.getEstimationExpiryDate()){
						estimateHistory.setEstimationExpiryDate(DateUtils.formatDate(estimateHistoryVO.getEstimationExpiryDate())); 
				    }
					
					//mapping vendor and provider details
					estimateHistory.setFirmDetails(mapFirmDetails(estimateHistoryVO.getVendorId(),estimateHistoryVO.getFirmLocation()));
					estimateHistory.setProviderDetails(mapProviderDetails(estimateHistoryVO.getResourceId(),estimateHistoryVO.getProviderLocation()));
					
					estimateHistory.setStatus(estimateHistoryVO.getStatus());
					
					//mapping pricing details
					estimateHistory.setPricing(mapPricingDetails(estimateHistoryVO));
					
					estimateHistory.setComments(estimateHistoryVO.getComments());
					estimateHistory.setLogoDocumentId(estimateHistoryVO.getLogoDocumentId());
					estimateHistory.setAction(estimateHistoryVO.getAction());
					
					mapItemsHistory(laborTaskMap.get(estimateHistory.getEstimationHistoryId()),
							partTaskMap.get(estimateHistory.getEstimationHistoryId()),otherTaskMap.get(estimateHistory.getEstimationHistoryId()),estimateHistory);
					
					
					historyList.add(estimateHistory);
				}
			}
			estimateHistoryDetails.setEstimateHistory(historyList);
			//mapping Item details
			//estimateHistoryDetails.setEstimateItemsHistory(mapItemsHistory(estimateTaskList,estimatePartList));
		}
		
		return estimateHistoryDetails;
	}
	
	
	
	/** mapping pricing details
	 * @param estimateHistoryVO
	 * @return
	 */
	private EstimatePricingDetails mapPricingDetails(
			EstimateHistoryVO estimateHistoryVO) {
		EstimatePricingDetails estimatePricingDetails = new EstimatePricingDetails();
		Double subTotal=0.0d;
		if(null!=estimateHistoryVO.getTotalLaborPrice())
		estimatePricingDetails.setTotalLaborPrice(String.format("%.2f",estimateHistoryVO.getTotalLaborPrice()));
		if(null!=estimateHistoryVO.getTotalPartsPrice())
		estimatePricingDetails.setTotalPartsPrice(String.format("%.2f",estimateHistoryVO.getTotalPartsPrice()));
		//SL-21387
		if(null!=estimateHistoryVO.getTotalOtherServicePrice())
		estimatePricingDetails.setTotalOtherServicePrice(String.format("%.2f",estimateHistoryVO.getTotalOtherServicePrice()));
		
		DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL);
        if(null!=estimateHistoryVO.getTotalLaborPrice()){
            subTotal=subTotal+estimateHistoryVO.getTotalLaborPrice().doubleValue();   
        }
        if(null!=estimateHistoryVO.getTotalPartsPrice()){
            subTotal=subTotal+estimateHistoryVO.getTotalPartsPrice().doubleValue();   
        }
        //SL-21387
        if(null!=estimateHistoryVO.getTotalOtherServicePrice()){
            subTotal=subTotal+estimateHistoryVO.getTotalOtherServicePrice().doubleValue();   
        }
  
        if(null !=subTotal){
        	estimatePricingDetails.setSubTotal(String.format("%.2f",subTotal));
        }
		estimatePricingDetails.setDiscountType(estimateHistoryVO.getDiscountType());
		if(null!=estimateHistoryVO.getDiscountedPercentage())
		estimatePricingDetails.setDiscountedPercentage(String.format("%.2f",estimateHistoryVO.getDiscountedPercentage()));
		if(null!=estimateHistoryVO.getDiscountedAmount())
		estimatePricingDetails.setDiscountedAmount(String.format("%.2f",estimateHistoryVO.getDiscountedAmount()));
		if(null!=estimateHistoryVO.getTaxRate())
		estimatePricingDetails.setTaxRate(String.format("%.2f",estimateHistoryVO.getTaxRate()));
		estimatePricingDetails.setTaxType(estimateHistoryVO.getTaxType());
		if(null!=estimateHistoryVO.getTaxPrice())
		estimatePricingDetails.setTaxPrice(String.format("%.2f",estimateHistoryVO.getTaxPrice()));
		if(null!=estimateHistoryVO.getTotalPrice())
		estimatePricingDetails.setTotalPrice(String.format("%.2f",estimateHistoryVO.getTotalPrice()));
		//SL-21939
		
		estimatePricingDetails.setLaborDiscountType(estimateHistoryVO.getLaborDiscountType());
		estimatePricingDetails.setPartsDiscountType(estimateHistoryVO.getPartsDiscountType());
		
		if(null!=estimateHistoryVO.getLaborDiscountedPercentage()){
			estimatePricingDetails.setLaborDiscountedPercentage(String.format("%.2f",estimateHistoryVO.getLaborDiscountedPercentage()));
			
		}
		if(null!=estimateHistoryVO.getLaborDiscountedAmount()){
			estimatePricingDetails.setLaborDiscountedAmount(String.format("%.2f",estimateHistoryVO.getLaborDiscountedAmount()));
		}
		if(null!=estimateHistoryVO.getLaborTaxRate()){
			estimatePricingDetails.setLaborTaxRate(String.format("%.2f",estimateHistoryVO.getLaborTaxRate()));
		}
		if(null!=estimateHistoryVO.getLaborTaxPrice()){
			estimatePricingDetails.setLaborTaxPrice(String.format("%.2f",estimateHistoryVO.getLaborTaxPrice()));
		}
		
		if(null!=estimateHistoryVO.getPartsDiscountedPercentage()){
			estimatePricingDetails.setPartsDiscountedPercentage(String.format("%.2f",estimateHistoryVO.getPartsDiscountedPercentage()));
		}
		if(null!=estimateHistoryVO.getPartsDiscountedAmount()){
			estimatePricingDetails.setPartsDiscountedAmount(String.format("%.2f",estimateHistoryVO.getPartsDiscountedAmount()));
		}
		if(null!=estimateHistoryVO.getPartsTaxRate()){
			estimatePricingDetails.setPartsTaxRate(String.format("%.2f",estimateHistoryVO.getPartsTaxRate()));
		}
		if(null!=estimateHistoryVO.getPartsTaxPrice()){
			estimatePricingDetails.setPartsTaxPrice(String.format("%.2f",estimateHistoryVO.getPartsTaxPrice()));
		}
	
		return estimatePricingDetails;
	}
	
	/** mapping labor and part items
	 * @param estimateTaskList
	 * @param estimatePartList
	 * @return
	 */
	private void mapItemsHistory(
			List<EstimateHistoryTaskVO> estimateTaskList,
			List<EstimateHistoryTaskVO> estimatePartList,List<EstimateHistoryTaskVO> otherServicelist,EstimateHistory estimateHistory) {
		EstimateItemsHistory estimateItems = null;
		List<LaborTaskHistory> laborTaskList = null;
		List<EstimatePartHistory> partList = null;
		List<EstimateOtherServiceHistory> estimateOtherServiceHistoryList =null;

		if((null!=estimateTaskList && !estimateTaskList.isEmpty()) || (null!=estimatePartList && !estimatePartList.isEmpty())){
			estimateItems = new EstimateItemsHistory();
			// mapping labor tasks
			if(null!=estimateTaskList && !(estimateTaskList.isEmpty())){
				laborTaskList = new ArrayList<LaborTaskHistory>();
				for (EstimateHistoryTaskVO estimateTaskVO : estimateTaskList) {
						
						if (null!= estimateTaskVO){
							LaborTaskHistory labortask = new LaborTaskHistory();
							labortask.setItemId(estimateTaskVO.getItemId());
							labortask.setTaskSeqNo(estimateTaskVO.getTaskSeqNumber());
							labortask.setTaskName(estimateTaskVO.getTaskName());
							labortask.setDescription(estimateTaskVO
									.getDescription());
							if(null!=estimateTaskVO.getUnitPrice())
							labortask.setUnitPrice(String.format("%.2f",estimateTaskVO.getUnitPrice()));
							labortask.setQuantity(estimateTaskVO.getQuantity());
							if(null!=estimateTaskVO.getTotalPrice())
							labortask.setTotalPrice(String.format("%.2f",estimateTaskVO.getTotalPrice()));
							labortask.setAdditionalDetails(estimateTaskVO
									.getAdditionalDetails());
							labortask.setAction(estimateTaskVO.getAction());
							laborTaskList.add(labortask);
						}
				}
			}
			
			//mapping part items
			if(null!=estimatePartList && !(estimatePartList.isEmpty())){
				partList = new ArrayList<EstimatePartHistory>();
				for(EstimateHistoryTaskVO estimateTaskVO : estimatePartList){
					
					if(null!= estimateTaskVO){
						EstimatePartHistory estimatePart = new EstimatePartHistory();
						estimatePart.setItemId(estimateTaskVO.getItemId());
						estimatePart.setPartSeqNo(estimateTaskVO
								.getPartSeqNumber());
						estimatePart.setPartNo(estimateTaskVO.getPartNumber());
						estimatePart.setPartName(estimateTaskVO.getPartName());
						estimatePart.setDescription(estimateTaskVO
								.getDescription());
						if(null!=estimateTaskVO.getUnitPrice()){
						estimatePart
								.setUnitPrice(String.format("%.2f",estimateTaskVO.getUnitPrice()));
						}
						estimatePart.setQuantity(estimateTaskVO.getQuantity());
						if(null!=estimateTaskVO
								.getTotalPrice()){
						estimatePart.setTotalPrice(String.format("%.2f",estimateTaskVO
								.getTotalPrice()));
						}
						estimatePart.setAdditionalDetails(estimateTaskVO
								.getAdditionalDetails());
						estimatePart.setAction(estimateTaskVO.getAction());
						partList.add(estimatePart);
					}	
				}
			}
			//SL-21387 mapping other estimate service details
			if(null!=otherServicelist && !(otherServicelist.isEmpty())){
				estimateOtherServiceHistoryList = new ArrayList<EstimateOtherServiceHistory>();
				for(EstimateHistoryTaskVO estimateTaskVO : otherServicelist){
					
					if(null!= estimateTaskVO){
						EstimateOtherServiceHistory estimateOtherService = new EstimateOtherServiceHistory();
						estimateOtherService.setItemId(estimateTaskVO.getItemId());
						estimateOtherService.setOtherServiceSeqNumber(estimateTaskVO.getOtherServiceSeqNumber());
						estimateOtherService.setOtherServiceType(estimateTaskVO.getOtherServiceType());
						estimateOtherService.setOtherServiceName(estimateTaskVO.getOtherServiceName());
						estimateOtherService.setDescription(estimateTaskVO.getDescription());
						if(null!=estimateTaskVO.getUnitPrice()){
						estimateOtherService.setUnitPrice(String.format("%.2f",estimateTaskVO.getUnitPrice()));
						}
						estimateOtherService.setQuantity(estimateTaskVO.getQuantity());
						if(null!=estimateTaskVO.getTotalPrice()){
						estimateOtherService.setTotalPrice(String.format("%.2f",estimateTaskVO.getTotalPrice()));
						}
						estimateOtherService.setAdditionalDetails(estimateTaskVO.getAdditionalDetails());
						estimateOtherService.setAction(estimateTaskVO.getAction());
						estimateOtherServiceHistoryList.add(estimateOtherService);
					}	
				}
			}			 
			if(null!=laborTaskList && !(laborTaskList.isEmpty())){
				LaborTasksHistory laborTasks = new LaborTasksHistory();
				laborTasks.setLabortask(laborTaskList);
				estimateItems.setLaborTasks(laborTasks);	
			}	
			
			if(null!=estimatePartList && !(estimatePartList.isEmpty())){
				EstimatePartsHistory estimateParts = new EstimatePartsHistory();
				estimateParts.setPart(partList);
				estimateItems.setParts(estimateParts);
			//SL-21387
			if(null!=estimateOtherServiceHistoryList && !(estimateOtherServiceHistoryList.isEmpty())){
				EstimateOtherServicesHistory estimateOtherServices = new EstimateOtherServicesHistory();
				estimateOtherServices.setOtherService(estimateOtherServiceHistoryList);
				estimateItems.setOtherServices(estimateOtherServices);
			}				
			}		
			estimateHistory.setEstimateItemsHistory(estimateItems);
		}
	}
	
	public RetrieveServiceOrder adaptRequest(ServiceOrder serviceOrder,
			List<String> responseFilter) throws DataServiceException {
		
		
		ScheduleServiceSlot scheduleServiceSlot = null;
		RetrieveServiceOrder retrieveSOResponse = super.adaptRequest(serviceOrder, responseFilter);
		if (responseFilter.contains(PublicAPIConstant.SCHEDULE) && null != serviceOrder.getServiceDatetimeSlots() && serviceOrder.getServiceDatetimeSlots().size()>0) {
			scheduleServiceSlot = new ScheduleServiceSlot();
			mapServiceOrderTimeSlots(serviceOrder, scheduleServiceSlot);
			retrieveSOResponse.setScheduleServiceSlot(scheduleServiceSlot);
			
		}
		
		
		return retrieveSOResponse;
	}
	private void mapServiceOrderTimeSlots(ServiceOrder serviceOrder,
			ScheduleServiceSlot scheduleServiceSlot) {
		logger.info("Entering into mapServiceOrderTimeSlots of  SORetrieveMapperV1_4");
		try{
			ServiceDateTimeSlots serviceDateTimeSlotsForXML = new ServiceDateTimeSlots();
			
			List<com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot> serviceDatetimeSlots = new ArrayList<com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot>() ;
			for(com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot serviceDatetimeSlt:serviceOrder.getServiceDatetimeSlots()){
				com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot serviceDatetimeSlot= new com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot();
				if(null !=serviceDatetimeSlt && null != serviceDatetimeSlt.getServiceStartDate()){
					java.util.Date serviceStartDate = (java.util.Date) TimeUtils.combineDateTime(
							serviceDatetimeSlt.getServiceStartDate(), serviceDatetimeSlt
							.getServiceStartTime());
					serviceDatetimeSlot.setServiceStartDate(CommonUtility.sdfToDate
							.format(new Timestamp(serviceStartDate.getTime())));
				}
				if(null !=serviceDatetimeSlt && null != serviceDatetimeSlt.getServiceEndDate()){
					java.util.Date serviceEndDate = (java.util.Date) TimeUtils.combineDateTime(
							serviceDatetimeSlt.getServiceEndDate(), serviceDatetimeSlt
							.getServiceEndTime());
					serviceDatetimeSlot.setServiceEndDate(CommonUtility.sdfToDate
							.format(new Timestamp(serviceEndDate.getTime())));
				}
				serviceDatetimeSlot.setSlotSeleted("1".equals(serviceDatetimeSlt.getSlotSelected()) ? PublicAPIConstant.SLOT_SELETED_TRUE 
						: PublicAPIConstant.SLOT_SELETED_FALSE );
				serviceDatetimeSlot.setPreferenceInd(serviceDatetimeSlt.getPreferenceInd());
				serviceDatetimeSlots.add(serviceDatetimeSlot);
			}
			
			serviceDateTimeSlotsForXML.setServiceDatetimeSlot(serviceDatetimeSlots);
			scheduleServiceSlot.setServiceDatetimeSlots(serviceDateTimeSlotsForXML);
			scheduleServiceSlot.setConfirmWithCustomer(("1".equals(serviceOrder
					.getProviderServiceConfirmInd())) ? PublicAPIConstant.CONFIRM_CUSTOMER_TRUE
					: PublicAPIConstant.CONFIRM_CUSTOMER_FALSE);
			
			if(1 == serviceOrder.getServiceDateTypeId()){
				scheduleServiceSlot.setScheduleType(PublicAPIConstant.DATETYPE_FIXED);
			}else if(3 == serviceOrder.getServiceDateTypeId()){
				scheduleServiceSlot.setScheduleType(PublicAPIConstant.DATETYPE_PREFERENCES);
			}else{
				scheduleServiceSlot.setScheduleType(PublicAPIConstant.DATETYPE_FIXED);	
			}
			
			logger.info("Exiting mapServiceOrderTimeSlots of  SORetrieveMapperV1_4");
			
		}catch(Exception e){
			e.printStackTrace();		
			logger.error("Exception Occurred" + e.getMessage());
			
		}
		
		
	}
	
	public RetrieveServiceOrder mapUpsellInfo(RetrieveServiceOrder response,
			List<ServiceOrderAddonVO> AddonVOList) {
		logger.info("Entering into mapUpsellInfo of  SORetrieveMapperV1_4");

		AddOns addOnList = new AddOns();
		List<AddOn> addons = new ArrayList<AddOn>();
		try {

			
			if (null != AddonVOList && !AddonVOList.isEmpty()) {
				
				for (ServiceOrderAddonVO serviceOrderAddonVO : AddonVOList) {
					AddOn addOn = new AddOn();
					
					if (null != serviceOrderAddonVO && serviceOrderAddonVO.getQuantity()>0) {
						
						addOn.setAddonSKU(serviceOrderAddonVO.getSku());
						//addOn.setCoverageType(serviceOrderAddonVO.getCoverage());
						
						addOn.setCustomerCharge(serviceOrderAddonVO.getRetailPrice()*serviceOrderAddonVO.getQuantity());
						
						addOn.setDescription(serviceOrderAddonVO
								.getDescription());
						addOn.setMargin(String.valueOf(serviceOrderAddonVO.getMargin()));
						
						addOn.setQty(serviceOrderAddonVO.getQuantity());
						if(serviceOrderAddonVO.getAddonId()!=null){
							addOn.setSoAddonId(serviceOrderAddonVO.getAddonId()
									.toString());
							}
						addOn.setTaxPercentage(serviceOrderAddonVO.getTaxPercentage());
						addons.add(addOn);
					}

				}
				addOnList.setAddon(addons);
				response.setAddOns(addOnList);
			}

			logger.info("Exiting mapUpsellInfo of  SORetrieveMapperV1_4");

		} catch (Exception e) {
			logger.error("Exception Occurred" + e.getMessage());
		}
		
		return response;
	}
		
	public RetrieveServiceOrder mapScopeOfWorkWithSku(RetrieveServiceOrder response, ServiceOrder serviceOrder) {
		logger.info("Entering into Mapping: Scope Of Work of SORetrieveMapperV1_4");
		ScopeOfWork scopeOfWork = response.getScopeOfWork();
		List<String> skus = new ArrayList<String>();
		Skus allSkus = new Skus();
		Iterator<ServiceOrderTask> soTaskList = serviceOrder.getTasks().iterator();
		while(soTaskList.hasNext()) {
			ServiceOrderTask soTask = (ServiceOrderTask)soTaskList.next();			
			skus.add(StringUtils.isEmpty(soTask.getSku()) ? "" : soTask.getSku());			
		}
		allSkus.setSkuList(skus);
		scopeOfWork.setSkus(allSkus);
		response.setScopeOfWork(scopeOfWork);
		logger.info("Exiting mapScopeOfWorkWithSku of  SORetrieveMapperV1_4");
		return response;
	}

}


