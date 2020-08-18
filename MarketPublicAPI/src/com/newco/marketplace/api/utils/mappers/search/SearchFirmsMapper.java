package com.newco.marketplace.api.utils.mappers.search;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.firms.AvailableTimeSlot;
import com.newco.marketplace.api.beans.search.firms.AvailableTimeSlotsList;
import com.newco.marketplace.api.beans.search.firms.Firm;
import com.newco.marketplace.api.beans.search.firms.PriceDetails;
import com.newco.marketplace.api.beans.search.firms.PriceList;
import com.newco.marketplace.api.beans.search.firms.SearchFirmsRequest;
import com.newco.marketplace.api.beans.search.firms.SearchFirmsResponse;
import com.newco.marketplace.api.beans.search.firms.SearchFirmsResults;
import com.newco.marketplace.api.beans.search.firms.ServiceOffering;
import com.newco.marketplace.api.beans.search.firms.ServiceOfferingsList;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.dto.vo.provider.AvailableTimeSlotVO;
import com.newco.marketplace.dto.vo.provider.SearchFirmsResponseVO;
import com.newco.marketplace.dto.vo.provider.SearchFirmsVO;
import com.newco.marketplace.dto.vo.provider.ServiceOfferingPriceVO;
import com.newco.marketplace.dto.vo.provider.ServiceOfferingsVO;
import com.newco.marketplace.mobile.constants.MPConstants;

public class SearchFirmsMapper {
	private static Logger logger = Logger.getLogger(SearchFirmsMapper.class);

	/**
	 * This method is for mapping the response of search firms
	 * 
	 */
	public SearchFirmsResults mapSearchFirms(SearchFirmsResponseVO searchFirmsResponseVO) {
		
		SearchFirmsResults searchFirmsResults =new SearchFirmsResults();
		SearchFirmsResponse searchFirmsResponse =new SearchFirmsResponse();
		List<Firm> firmList = new ArrayList<Firm>();
		List<ServiceOffering> serviceOffering=null;
		ServiceOffering serviceOffer =null;
		Firm firm =null;
		int successCount=0;
		Results results = null;
		ServiceOfferingsList serviceOfferingsList = null;
		if(null!=searchFirmsResponseVO && null!=searchFirmsResponseVO.getFirmOfferingMap())
		{
			for (Map.Entry<Integer, List<Integer>> entry : searchFirmsResponseVO.getFirmOfferingMap().entrySet()) {
				serviceOfferingsList = new ServiceOfferingsList();
				if(null!=entry.getKey()){
				firm = new Firm();
				serviceOffering=new ArrayList<ServiceOffering>();
				//Mapping firm id to Search Firm Response
				firm.setFirmId(entry.getKey());
				List<Integer> serviceOfferingId= entry.getValue();
				if(null!=serviceOfferingId && !serviceOfferingId.isEmpty())
				{
					for(Integer offeringId :serviceOfferingId)
					{
						if(null!=searchFirmsResponseVO.getFirmPriceMap())
						{
								serviceOffer = mappingPriceDetails(
										searchFirmsResponseVO, serviceOffer, offeringId);	
						}
						
						if(null!=searchFirmsResponseVO.getOfferAvailablityMap())
						{
								mapOfferAvailability(searchFirmsResponseVO,
										serviceOffer, offeringId);
									
						}
						serviceOffering.add(serviceOffer);
					}

					if(null!=searchFirmsResponseVO.getFirmNames())
					{
						String firmName=searchFirmsResponseVO.getFirmNames().get(entry.getKey().longValue());
						//Mapping Firm Name to Search Firm Response
						if(StringUtils.isNotBlank(firmName))
						{
							firm.setFirmName(firmName);
						}
					}
					
					if(null!=searchFirmsResponseVO.getAggregateRatingMap())
					{
						BigDecimal firmRating=searchFirmsResponseVO.getAggregateRatingMap().get(entry.getKey());
						//Mapping Aggregate Firm Rating to Search Firm Response
						if(null!=firmRating)
						{
							DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_THREE_DECIMAL);
							firm.setFirmRating(df.format(firmRating).toString());
						}
					}
				}	
				
				if(null!=serviceOffering && !serviceOffering.isEmpty())
				{
					serviceOfferingsList.setServiceOffering(serviceOffering);
					firm.setServiceOfferingList(serviceOfferingsList);
					firmList.add(firm);
				}
			  }
				 successCount=successCount+1;
			}
			if(null!=firmList && !firmList.isEmpty())
			{
				searchFirmsResponse.setFirm(firmList);
				searchFirmsResults.setFirms(searchFirmsResponse);
				if(successCount>0){
					results=Results.getSuccess(successCount+" "+ResultsCode.SEARCH_FIRMS_SUCCESS.getMessage());
				}else{
					results=Results.getError(ResultsCode.SEARCH_NO_RECORDS.getMessage(), 
						ResultsCode.SEARCH_NO_RECORDS.getCode());
				}
				searchFirmsResults.setResults(results);
			}	
			else {
				results=Results.getError(ResultsCode.SEARCH_NO_RECORDS.getMessage(), 
						ResultsCode.SEARCH_NO_RECORDS.getCode());
				searchFirmsResults.setResults(results);
			}	
		}
		
		return searchFirmsResults;
	}

	/**
	 * @param searchFirmsResponseVO
	 * @param serviceOffer
	 * @param priceDetailsList
	 * @param offeringId
	 * @return
	 */
	private ServiceOffering mappingPriceDetails(
			SearchFirmsResponseVO searchFirmsResponseVO,
			ServiceOffering serviceOffer, 
			Integer offeringId) {
		PriceList priceDetailsList = new PriceList();
		List<PriceDetails> priceList =null;
		PriceDetails priceDetails =null;
		ServiceOfferingsVO serviceOfferingsVO=searchFirmsResponseVO.getFirmPriceMap().get(offeringId);
		if(null!=serviceOfferingsVO)
		{
			priceList=new ArrayList<PriceDetails>();
			serviceOffer =new ServiceOffering();
			
			//Mapping SKU to Search Firm Response
			serviceOffer.setSku(serviceOfferingsVO.getSku());
			List<ServiceOfferingPriceVO> serviceOfferingPrice=serviceOfferingsVO.getServiceOfferingPrice();
			
			if(null!=serviceOfferingPrice && !serviceOfferingPrice.isEmpty())
			{
				for(ServiceOfferingPriceVO serviceOfferings: serviceOfferingPrice)
				{
					priceDetails =new PriceDetails();
					if(null!=serviceOfferings.getPrice())
					{
						priceDetails.setPrice(serviceOfferings.getPrice().toString());
					}
					priceDetails.setZip(serviceOfferings.getZip());
					priceList.add(priceDetails);
				}
			}
			//Mapping Price for each SKU to Search Firm Response
			if (null!=priceList && !(priceList.isEmpty())){
				priceDetailsList.setPriceDetails(priceList);
				serviceOffer.setPriceList(priceDetailsList);	
			}				
		}
		return serviceOffer;
	}

	/**
	 * @param searchFirmsResponseVO
	 * @param serviceOffer
	 * @param availableTimeSlotsList
	 * @param offeringId
	 */
	private void mapOfferAvailability(
			SearchFirmsResponseVO searchFirmsResponseVO,
			ServiceOffering serviceOffer, Integer offeringId) {
		AvailableTimeSlotsList availableTimeSlotsList = new AvailableTimeSlotsList();
		List<AvailableTimeSlot> availableTimeList =null;
		AvailableTimeSlot availableTimeSlots =null;
		List<AvailableTimeSlotVO> availableTimeSlotVO=searchFirmsResponseVO.getOfferAvailablityMap().get(offeringId);
		if(null!=availableTimeSlotVO)
		{
			availableTimeList=new ArrayList<AvailableTimeSlot>();
			for(AvailableTimeSlotVO availableTimeSlot: availableTimeSlotVO)
			{				
				availableTimeSlots=new AvailableTimeSlot();
				availableTimeSlots.setDay(availableTimeSlot.getDay());
				// setting the serviceTimeWindow
				if (null!=availableTimeSlot.getTimeWindow()){
					int timeWindow = availableTimeSlot.getTimeWindow().intValue();
					if (timeWindow == PublicAPIConstant.INTEGER_ONE){
						availableTimeSlots.setTimeWindow(PublicAPIConstant.SERVICETIME_ALL);
					}
					else if (timeWindow == PublicAPIConstant.INTEGER_TWO){
						availableTimeSlots.setTimeWindow(PublicAPIConstant.SERVICETIME_EARLY_MORNING);
					}
					else if (timeWindow == PublicAPIConstant.THREE){
						availableTimeSlots.setTimeWindow(PublicAPIConstant.SERVICETIME_MORNING);
					}
					else if (timeWindow == PublicAPIConstant.INTEGER_FOUR){
						availableTimeSlots.setTimeWindow(PublicAPIConstant.SERVICETIME_AFTERNOON);
					}
					else if (timeWindow == PublicAPIConstant.FIVE){
						availableTimeSlots.setTimeWindow(PublicAPIConstant.SERVICETIME_EVENING);
					}
				}
				availableTimeList.add(availableTimeSlots);
			}
			
			//Mapping Available Time Slot for each SKU to Search Firm Response
			if (null!=availableTimeList && !(availableTimeList.isEmpty())){
				availableTimeSlotsList.setAvailableTimeSlot(availableTimeList);
				serviceOffer.setAvailableTimeSlotsList(availableTimeSlotsList);
			}
		}
	}
	
	/** mapping search Firms request
	 * @param searchFirmsRequest
	 * @param buyerId 
	 * @return
	 */
	public SearchFirmsVO mapRequest(SearchFirmsRequest searchFirmsRequest, int buyerId) {
		SearchFirmsVO searchFirmsVO = null;
		if(null!=searchFirmsRequest){
			searchFirmsVO = new SearchFirmsVO();
			searchFirmsVO.setBuyerId(buyerId);
			searchFirmsVO.setZip(searchFirmsRequest.getZip());
			searchFirmsVO.setMainCategory(searchFirmsRequest.getMainCategory());
	
			if (StringUtils.isNotBlank(searchFirmsRequest.getServiceDate1())){
				searchFirmsVO.setServiceDate1(parseDate(searchFirmsRequest.getServiceDate1()));
				if (StringUtils.isNotBlank(searchFirmsRequest.getServiceDate2())){
					searchFirmsVO.setServiceDate2(parseDate(searchFirmsRequest.getServiceDate2()));
				}
				else{
					//if servicedate2 is not present, setting servicedate1 as service date2
					searchFirmsVO.setServiceDate2(parseDate(searchFirmsRequest.getServiceDate1()));
				}
				searchFirmsVO.setServiceDays(getDays(searchFirmsVO.getServiceDate1(), searchFirmsVO.getServiceDate2()));
			}
			// setting the serviceTimeWindow
			if (StringUtils.isNotBlank(searchFirmsRequest.getServiceTimeWindow())){
				String timeWindow = searchFirmsRequest.getServiceTimeWindow();
				if (timeWindow.equalsIgnoreCase(PublicAPIConstant.SERVICETIME_ALL)){
					searchFirmsVO.setTimeWindow(PublicAPIConstant.INTEGER_ONE);
				}
				else if (timeWindow.equalsIgnoreCase(PublicAPIConstant.SERVICETIME_EARLY_MORNING)){
					searchFirmsVO.setTimeWindow(PublicAPIConstant.INTEGER_TWO);
				}
				else if (timeWindow.equalsIgnoreCase(PublicAPIConstant.SERVICETIME_MORNING)){
					searchFirmsVO.setTimeWindow(PublicAPIConstant.THREE);
				}
				else if (timeWindow.equalsIgnoreCase(PublicAPIConstant.SERVICETIME_AFTERNOON)){
					searchFirmsVO.setTimeWindow(PublicAPIConstant.INTEGER_FOUR);
				}
				else if (timeWindow.equalsIgnoreCase(PublicAPIConstant.SERVICETIME_EVENING)){
					searchFirmsVO.setTimeWindow(PublicAPIConstant.FIVE);
				}
			}
			//mapping radius
			if(StringUtils.isNotBlank(searchFirmsRequest.getRadius())){
				searchFirmsVO.setRadius(searchFirmsRequest.getRadius());
			}
		}
		return searchFirmsVO;
	}
	
	/**@Description: Convert String Date  to Date Object
	 * @param DateString
	 * @return
	 * @throws ParseException 
	 */
	private Date parseDate(String DateString){
		Date DateTime =null;
		SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
		try {
			DateTime = input.parse(DateString);
			
		} catch (ParseException e) {
			logger.error("Exception in parsing Date"+e);
		}
		return DateTime;
		
	}
	
	private List<String> getDays(Date startDate, Date endDate){
		List<String> days = new ArrayList<String>();
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.setTime(startDate);
		endCal.setTime(endDate);
		while (!startCal.after(endCal)) {
			
		   int day = startCal.get(Calendar.DAY_OF_WEEK);
		   switch (day){
		   case 1 : days.add(PublicAPIConstant.SUNDAY_STR);
		   			break;
		   case 2 : days.add(PublicAPIConstant.MONDAY_STR);
		   			break;
		   case 3 : days.add(PublicAPIConstant.TUESDAY_STR);
		   			break;
		   case 4 : days.add(PublicAPIConstant.WEDNESDAY_STR);
		   			break;
		   case 5 : days.add(PublicAPIConstant.THURSDAY_STR);
		   			break;
		   case 6 : days.add(PublicAPIConstant.FRIDAY_STR);
		   			break;
		   default : days.add(PublicAPIConstant.SATURDAY_STR);
		   }
		  

		   startCal.add(Calendar.DATE, 1);
		}
		return days;
	}
}
