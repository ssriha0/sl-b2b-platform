package com.newco.marketplace.api.utils.mappers.so.v1_1;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.closedOrders.ClosedOrderContact;
import com.newco.marketplace.api.beans.closedOrders.ClosedOrderContacts;
import com.newco.marketplace.api.beans.closedOrders.ClosedOrderGeneralSection;
import com.newco.marketplace.api.beans.closedOrders.ClosedOrderLocation;
import com.newco.marketplace.api.beans.closedOrders.ClosedOrderPhone;
import com.newco.marketplace.api.beans.closedOrders.ClosedOrderPricing;
import com.newco.marketplace.api.beans.closedOrders.ClosedOrderReview;
import com.newco.marketplace.api.beans.closedOrders.ClosedOrderSchedule;
import com.newco.marketplace.api.beans.closedOrders.ClosedServiceOrder;
import com.newco.marketplace.api.beans.closedOrders.ClosedServiceOrders;
import com.newco.marketplace.api.beans.closedOrders.RetrieveClosedOrdersResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.dto.vo.serviceorder.ClosedOrdersRequestVO;
import com.newco.marketplace.dto.vo.serviceorder.ClosedServiceOrderVO;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.UIUtils;

/**
 * This class would act as a Mapper class for Mapping Request Object to
 * ClosedOrdersRequestVO Object.and also for mapping service order list
 * to RetrieveClosedOrders object
 * 
 * @author Infosys
 * @version 1.0
 */
public class ClosedSOByProviderMapper {
	private Logger logger = Logger.getLogger(ClosedSOByProviderMapper.class);

	
	/**This method is for mapping Mapping Request Object to
	 * ClosedOrdersRequestVO Object
	 * @param requestMap
	 * @param providerId
	 * @return
	 */
	public ClosedOrdersRequestVO mapClosedSoRequest(Map<String,String> requestMap, String providerId){
		logger.info("Entering SOSearchByProviderMapper.mapSoSearchRequest()");
		ClosedOrdersRequestVO closedOrdersRequestVO = new ClosedOrdersRequestVO();
		closedOrdersRequestVO.setProviderId(providerId);
		if(StringUtils.isNotBlank(requestMap
				.get(PublicAPIConstant.ZIP))){
			closedOrdersRequestVO.setServiceLocZipcode(requestMap
					.get(PublicAPIConstant.ZIP));
		}
		if(StringUtils.isNotBlank(requestMap
				.get(PublicAPIConstant.COMPLETED_IN))){
			closedOrdersRequestVO.setCompletedIn(Integer.parseInt(requestMap
					.get(PublicAPIConstant.COMPLETED_IN)));
			closedOrdersRequestVO.setStartDate(DateUtils.addMonth(-1*closedOrdersRequestVO.getCompletedIn()));
		}
		if(StringUtils.isNotBlank(requestMap
				.get(PublicAPIConstant.NO_OF_ORDERS))){
			closedOrdersRequestVO.setNoOfOrders(Integer.parseInt(requestMap
					.get(PublicAPIConstant.NO_OF_ORDERS)));
		}

		logger.info("Leaving ClosedSOByProviderMapper.mapClosedSoRequest()");
		return closedOrdersRequestVO;
	}
	
	/**
	 * This method is for mapping list of ClosedServiceOrderVO to
	 * RetrieveClosedOrders Object.
	 * @param closedSOList
	 *            List<ClosedServiceOrderVO>
	 * @throws DataException
	 * @return RetrieveClosedOrders
	 */
	public RetrieveClosedOrdersResponse mapResponse(
			List<ClosedServiceOrderVO> closedSOList) {
		logger.info("Entering ClosedSOByProviderMapper.mapResponse()");
		RetrieveClosedOrdersResponse soResponse = new RetrieveClosedOrdersResponse();
		Results results = new Results();
		int successCount=0;
		List<ClosedServiceOrder> closedServiceOrdersList = null;
		ClosedServiceOrders closedServiceOrders = null;
		if(null!=closedSOList && !closedSOList.isEmpty()){
				Iterator<ClosedServiceOrderVO> soList = closedSOList.iterator();
				closedServiceOrdersList = new ArrayList<ClosedServiceOrder>();
				while (soList.hasNext()) {
					ClosedServiceOrderVO closedServiceOrderVO = (ClosedServiceOrderVO) soList.next();
					ClosedServiceOrder closedServiceOrder = new ClosedServiceOrder();
					closedServiceOrder.setSoId(closedServiceOrderVO.getSoId());
					if(null!=closedServiceOrderVO.getCreatedDate()){
						closedServiceOrder.setCreatedDate(formatDate(closedServiceOrderVO.getCreatedDate()));
					}
					if(null!=closedServiceOrderVO.getClosedDate()){
						closedServiceOrder.setClosedDate(formatDate(closedServiceOrderVO.getClosedDate()));
					}
					closedServiceOrder.setBuyerId(closedServiceOrderVO.getBuyerId());
					// mapping sectionGeneral
					closedServiceOrder.setSectionGeneral(mapGeneralSection(closedServiceOrderVO));
					// mapping serviceLocation
					closedServiceOrder.setServiceLocation(mapServiceLocation(closedServiceOrderVO));
					//mapping schedule
					closedServiceOrder.setSchedule(mapSchedule(closedServiceOrderVO));
					//mapping pricing details
					closedServiceOrder.setPricing(mapPricing(closedServiceOrderVO));
					//mapping contacts details
					closedServiceOrder.setContacts(mapContacts(closedServiceOrderVO));
					//mapping review details
					closedServiceOrder.setReview(mapReview(closedServiceOrderVO));
					
					successCount=successCount+1;
					closedServiceOrdersList.add(closedServiceOrder);
				
		}
				if(null!=closedServiceOrdersList && !(closedServiceOrdersList.isEmpty())){
					closedServiceOrders = new ClosedServiceOrders();
					closedServiceOrders.setServiceorder(closedServiceOrdersList);
					if(successCount>0){
						results=Results.getSuccess(successCount+" "+ResultsCode.SO_RETRIEVE_SOS_FOUND.getMessage());
					}else{
						results=Results.getError(ResultsCode.SEARCH_NO_RECORDS.getMessage(), 
								ResultsCode.SEARCH_NO_RECORDS.getCode());
					}
					soResponse.setClosedServiceOrders(closedServiceOrders);
					soResponse.setResults(results);
				}		
		}
		else{
				logger.info("Setting result and message as Failure when the Get closed orders" +
						" result list is empty");
						results = Results.getError(
								ResultsCode.SEARCH_NO_RECORDS.getMessage(), 
								ResultsCode.SEARCH_NO_RECORDS.getCode());
						soResponse.setResults(results);
			}
		
		logger.info("Leaving ClosedSOByProviderMapper.mapResponse()");
		return soResponse;

	}
	

	/** mapping general section
	 * @param closedServiceOrderVO
	 * @return
	 */
	private ClosedOrderGeneralSection mapGeneralSection(ClosedServiceOrderVO closedServiceOrderVO){
		ClosedOrderGeneralSection closedOrderGeneralSection = new ClosedOrderGeneralSection();
		closedOrderGeneralSection.setTitle(closedServiceOrderVO.getSowTitle());
		if(StringUtils.isNotBlank(closedServiceOrderVO.getSowDs())){
			closedOrderGeneralSection.setOverview(ServiceLiveStringUtils.removeHTML(closedServiceOrderVO.getSowDs()));
		}
		if(StringUtils.isNotBlank(closedServiceOrderVO.getBuyerTerms())){
			closedOrderGeneralSection.setBuyerTerms(ServiceLiveStringUtils.removeHTML(closedServiceOrderVO.getBuyerTerms()));
		}
		if(StringUtils.isNotBlank(closedServiceOrderVO.getProviderInstructions())){
			closedOrderGeneralSection.setSpecialInstructions(ServiceLiveStringUtils.removeHTML(closedServiceOrderVO.getProviderInstructions()));
		}
		
		return closedOrderGeneralSection;
	}
	
	/** mapping service location details
	 * @param closedServiceOrderVO
	 * @return
	 */
	private ClosedOrderLocation mapServiceLocation(ClosedServiceOrderVO closedServiceOrderVO){
		ClosedOrderLocation closedOrderLocation = new ClosedOrderLocation();
		if(null!=closedServiceOrderVO.getLocTypeId()){
			int locTypeId = closedServiceOrderVO.getLocTypeId().intValue();
			if(locTypeId==PublicAPIConstant.INTEGER_ONE){
				closedOrderLocation.setLocationType(PublicAPIConstant.LOCATION_TYPE_COMMERCIAL);
			}
			else if (locTypeId==PublicAPIConstant.INTEGER_TWO){
				closedOrderLocation.setLocationType(PublicAPIConstant.LOCATION_TYPE_RESIDENTIAL);
			}
		}
		closedOrderLocation.setLocationName(closedServiceOrderVO.getLocnName());
		closedOrderLocation.setAddress1(closedServiceOrderVO.getStreet1());
		closedOrderLocation.setAddress2(closedServiceOrderVO.getStreet2());
		closedOrderLocation.setCity(closedServiceOrderVO.getCity());
		closedOrderLocation.setState(closedServiceOrderVO.getState());
		closedOrderLocation.setZip(closedServiceOrderVO.getZip());
		if(StringUtils.isNotBlank(closedServiceOrderVO.getZip4())){
			closedOrderLocation.setZip4(closedServiceOrderVO.getZip4());
		}
		
		return closedOrderLocation;
	}
	
	/** Mapping schedule details of SO
	 * @param closedServiceOrderVO
	 * @return
	 */
	private ClosedOrderSchedule mapSchedule(ClosedServiceOrderVO closedServiceOrderVO){
		ClosedOrderSchedule closedOrderSchedule = new ClosedOrderSchedule();
		//mapping schedule type 
		if (null!=closedServiceOrderVO.getServiceDateTypeId()){
			int serviceDateType = closedServiceOrderVO.getServiceDateTypeId().intValue();
			if(serviceDateType==PublicAPIConstant.INTEGER_ONE){
				closedOrderSchedule.setScheduleType(PublicAPIConstant.DATETYPE_FIXED);
			}
			else if (serviceDateType == PublicAPIConstant.INTEGER_TWO){
				closedOrderSchedule.setScheduleType(PublicAPIConstant.DATETYPE_RANGE);
			}
		}
		HashMap<String, Object> startDate = null;
		HashMap<String, Object> endDate = null;
		
		if(null!= closedServiceOrderVO.getServiceDate1() && StringUtils.isNotBlank(closedServiceOrderVO.getServiceTimeStart())){
			startDate = TimeUtils.convertGMTToGivenTimeZone(closedServiceOrderVO.getServiceDate1(), 
					closedServiceOrderVO.getServiceTimeStart(), closedServiceOrderVO.getServiceLocationTimeZone());
			
			if(null!=startDate){
				closedOrderSchedule.setServiceStartDate(formatDate((Timestamp)startDate.get(OrderConstants.GMT_DATE)));
				closedOrderSchedule.setServiceWindowStartTime((String) startDate.get(OrderConstants.GMT_TIME));
			}
			
		}
		if(null!= closedServiceOrderVO.getServiceDate2() &&StringUtils.isNotBlank(closedServiceOrderVO.getServiceTimeEnd())){
			endDate = TimeUtils.convertGMTToGivenTimeZone(closedServiceOrderVO.getServiceDate2(), 
					closedServiceOrderVO.getServiceTimeEnd(), closedServiceOrderVO.getServiceLocationTimeZone());
			if(null!=endDate){
				closedOrderSchedule.setServiceEndDate(formatDate((Timestamp) endDate.get(OrderConstants.GMT_DATE)));
				closedOrderSchedule.setServiceWindowEndTime((String) endDate.get(OrderConstants.GMT_TIME));
			}
		
		}
		closedOrderSchedule.setServiceLocationTimezone(closedServiceOrderVO.getServiceLocationTimeZone());	
		return closedOrderSchedule;
	}
	
	/** mapping pricing details
	 * @param closedServiceOrderVO
	 * @return
	 */
	private ClosedOrderPricing mapPricing(ClosedServiceOrderVO closedServiceOrderVO){
		ClosedOrderPricing closedOrderPricing = new ClosedOrderPricing();
		closedOrderPricing.setPriceModel(closedServiceOrderVO.getPriceModel());
		DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL_WITH_ZEROS);
		if(null !=closedServiceOrderVO.getSpendLimitLabor()){
			closedOrderPricing.setLaborSpendLimit(df.format(closedServiceOrderVO.getSpendLimitLabor()));
        }
		if(null !=closedServiceOrderVO.getSpendLimitParts()){
			closedOrderPricing.setPartsSpendLimit(df.format(closedServiceOrderVO.getSpendLimitParts()));
        }
		if(null !=closedServiceOrderVO.getLaborFinalPrice()){
			closedOrderPricing.setFinalPriceForLabor(df.format(closedServiceOrderVO.getLaborFinalPrice()));
        }
		if(null !=closedServiceOrderVO.getPartsFinalPrice()){
			closedOrderPricing.setFinalPriceForParts(df.format(closedServiceOrderVO.getPartsFinalPrice()));
        }
		return closedOrderPricing;
	}
	
	/** Mapping Service and Buyer Support contact
	 * @param closedServiceOrderVO
	 * @return
	 */
	private ClosedOrderContacts mapContacts(ClosedServiceOrderVO closedServiceOrderVO){
		ClosedOrderContacts closedOrderContacts = new ClosedOrderContacts();
		List<ClosedOrderContact> contactList = new ArrayList<ClosedOrderContact>();
		//mapping end Customer contact details
		ClosedOrderContact contact = new ClosedOrderContact();
		contact.setContactLocnType(PublicAPIConstant.SERVICE);
		contact.setFirstName(closedServiceOrderVO.getServiceFirstName());
		contact.setLastName(closedServiceOrderVO.getServiceLastName());
		ClosedOrderPhone phone = null;
		if(StringUtils.isNotBlank(closedServiceOrderVO.getServicePhoneNo()) || 
				StringUtils.isNotBlank(closedServiceOrderVO.getServicePhoneNoExt())){
			phone = new ClosedOrderPhone();
			if (null!=closedServiceOrderVO.getServicePhoneClassId()){
				mapPhoneType(closedServiceOrderVO.getServicePhoneClassId().intValue(),phone);
			}
			phone.setNumber(UIUtils.formatPhoneNumber(closedServiceOrderVO.getServicePhoneNo()));
			phone.setExtension(closedServiceOrderVO.getServicePhoneNoExt());
			contact.setPrimaryPhone(phone);
		}
		ClosedOrderPhone altPhone = null;
		if(StringUtils.isNotBlank(closedServiceOrderVO.getServiceAltphoneNo()) || 
				StringUtils.isNotBlank(closedServiceOrderVO.getServiceAltphoneNoExt())){
			altPhone = new ClosedOrderPhone();
			if (null!=closedServiceOrderVO.getServiceAltphoneClassId()){
				mapPhoneType(closedServiceOrderVO.getServiceAltphoneClassId().intValue(),altPhone);
			}
			altPhone.setNumber(UIUtils.formatPhoneNumber(closedServiceOrderVO.getServiceAltphoneNo()));
			altPhone.setExtension(closedServiceOrderVO.getServiceAltphoneNoExt());
			contact.setAltPhone(altPhone);
			}
		contact.setEmail(closedServiceOrderVO.getServiceEmail());
		contactList.add(contact);
		
		//mapping buyer contact details
		ClosedOrderContact buyerContact = null;
		if (StringUtils.isNotBlank(closedServiceOrderVO.getBuyerSupportFirstName())||
				StringUtils.isNotBlank(closedServiceOrderVO.getBuyerSupportLastName())){
			buyerContact = new ClosedOrderContact();
			buyerContact.setContactLocnType(PublicAPIConstant.BUYER_SUPPORT);
			buyerContact.setFirstName(closedServiceOrderVO.getBuyerSupportFirstName());
			buyerContact.setLastName(closedServiceOrderVO.getBuyerSupportLastName());
			ClosedOrderPhone buyerPhone = null;
			if(StringUtils.isNotBlank(closedServiceOrderVO.getBuyerSupportPhoneNo()) || 
					StringUtils.isNotBlank(closedServiceOrderVO.getBuyerSupportPhoneNoExt())){
				buyerPhone = new ClosedOrderPhone();
				buyerPhone.setPhoneType(PublicAPIConstant.WORK);
				buyerPhone.setNumber(UIUtils.formatPhoneNumber(closedServiceOrderVO.getBuyerSupportPhoneNo()));
				buyerPhone.setExtension(closedServiceOrderVO.getBuyerSupportPhoneNoExt());
				buyerContact.setPrimaryPhone(buyerPhone);
			}
			ClosedOrderPhone buyerAltPhone = null;
			if(StringUtils.isNotBlank(closedServiceOrderVO.getBuyerSupportCellNo())){
				buyerAltPhone = new ClosedOrderPhone();
				buyerAltPhone.setPhoneType(PublicAPIConstant.MOBILE);
				buyerAltPhone.setNumber(UIUtils.formatPhoneNumber(closedServiceOrderVO.getBuyerSupportCellNo()));
				buyerContact.setAltPhone(buyerAltPhone);
				}
			buyerContact.setEmail(closedServiceOrderVO.getBuyerSupportEmail());
			contactList.add(buyerContact);
		}
		closedOrderContacts.setContact(contactList);
		return closedOrderContacts;
	}

	/** Mapping phone type 
	 * @param phoneClassId
	 * @param phone
	 */
	private void mapPhoneType(int phoneClassId, ClosedOrderPhone phone) {
		if(phoneClassId == PublicAPIConstant.INTEGER_ZERO){
			phone.setPhoneType(PublicAPIConstant.HOME);
		}
		else if(phoneClassId == PublicAPIConstant.INTEGER_ONE){
			phone.setPhoneType(PublicAPIConstant.WORK);
		}
		else if(phoneClassId == PublicAPIConstant.INTEGER_TWO){
			phone.setPhoneType(PublicAPIConstant.MOBILE);
		}
		else if(phoneClassId == PublicAPIConstant.INTEGER_FOUR){
			phone.setPhoneType(PublicAPIConstant.PAGER);
		}
		else if(phoneClassId == PublicAPIConstant.FIVE){
			phone.setPhoneType(PublicAPIConstant.OTHER);
		}
		else if(phoneClassId == PublicAPIConstant.INTEGER_SIX){
			phone.setPhoneType(PublicAPIConstant.FAX);
		}
		
	}
	
	/** Mapping reviews submitted by buyer for that SO
	 * @param closedServiceOrderVO
	 * @return
	 */
	private ClosedOrderReview mapReview(ClosedServiceOrderVO closedServiceOrderVO){
		ClosedOrderReview closedOrderReview = null;
		if (null!= closedServiceOrderVO.getOverallRatingScore() || 
				StringUtils.isNotBlank(closedServiceOrderVO.getComments()) ||
				null!= closedServiceOrderVO.getReviewDate()){
			closedOrderReview = new ClosedOrderReview();
			DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_THREE_DECIMAL);
			if(null !=closedServiceOrderVO.getOverallRatingScore()){
				closedOrderReview.setRating(df.format(closedServiceOrderVO.getOverallRatingScore()));
	        }
			closedOrderReview.setComment(closedServiceOrderVO.getComments());
			if(null!=closedServiceOrderVO.getReviewDate()){
				closedOrderReview.setDate(formatDate(closedServiceOrderVO.getReviewDate()));
			}
		}
		return closedOrderReview;
	}
	
	/** formatting Date to yyyy-MM-dd
	 * @param createdDate
	 * @return
	 */
	private String formatDate(Timestamp createdDate) {
		// TODO Auto-generated method stub
		String DateTime =null;
		String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
		SimpleDateFormat outPut = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		try {
			DateTime = outPut.format(createdDate);	
		} catch (Exception e) {
			logger.error("Exception in formatting Date"+e);
		}
		return DateTime;
	}
	
	

}
