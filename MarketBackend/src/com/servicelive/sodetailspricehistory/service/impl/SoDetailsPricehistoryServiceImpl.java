package com.servicelive.sodetailspricehistory.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.price.SOLevelpriceHistoryRecord;
import com.newco.marketplace.api.beans.so.price.SOPriceHistoryResponse;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.dto.priceHistory.SoPriceHistoryDTO;
import com.newco.marketplace.dto.vo.SoPriceHistoryVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.sodetailspricehistory.services.SoDetailsPriceHistoryRestClient;
import com.servicelive.sodetailspricehistory.services.SoDetailsPricehistoryService;

/**
 * 
 * @author root
 *
 */
public class SoDetailsPricehistoryServiceImpl implements SoDetailsPricehistoryService {
	private Logger logger = Logger.getLogger(SoDetailsPricehistoryServiceImpl.class);
   
   private SoDetailsPriceHistoryRestClient soDetailsPriceHistoryRestClient;
   
   /**
    * Call API
    */
   public SOPriceHistoryResponse getpriceHistoryList(String buyerId,String soIds, String infoLevel) {
		SOPriceHistoryResponse ResponseAsObject = null;
		if (StringUtils.isNotBlank(soIds) && StringUtils.isNotBlank(buyerId)&& StringUtils.isNotBlank(infoLevel)) {
			ResponseAsObject = soDetailsPriceHistoryRestClient.getSoDetailsPriceHistory(buyerId, soIds, infoLevel);
		}
		if (null != ResponseAsObject) {
			return ResponseAsObject;
		}
		return ResponseAsObject;
	}
	
   /**
    * Map DTO
    */
	public void mapResponseObjectToDto(SOPriceHistoryResponse responseAsObject,SoPriceHistoryDTO priceHistorytabDto) {
		List<SOLevelpriceHistoryRecord> soLevelPriceHistory=null;
		if(null!=responseAsObject && null != priceHistorytabDto){
			if(null!= responseAsObject.getServiceOrdersList() 
					&& null!=  responseAsObject.getServiceOrdersList().getOrderPriceHistories()
					&& null!=responseAsObject.getServiceOrdersList().getOrderPriceHistories().get(0)
					&& null!=responseAsObject.getServiceOrdersList().getOrderPriceHistories().get(0).getSoLevelPriceHistory()
					&&null!=responseAsObject.getServiceOrdersList().getOrderPriceHistories().get(0).getSoLevelPriceHistory().getSoLevelPriceHistoryRecord()){
			    soLevelPriceHistory = responseAsObject.getServiceOrdersList().getOrderPriceHistories().get(0).getSoLevelPriceHistory().getSoLevelPriceHistoryRecord();
			    
			}
			if(priceHistorytabDto.getPricingType().equalsIgnoreCase(OrderConstants.SO_LEVEL_PRICING) ){
				 List<SoPriceHistoryVO> soPriceHistoryVOs = new ArrayList<SoPriceHistoryVO>();
				
				 if(null!=soLevelPriceHistory && !soLevelPriceHistory.isEmpty()){
					
				      for(SOLevelpriceHistoryRecord soLevelFromXml:soLevelPriceHistory ){
				    	  SoPriceHistoryVO soLevelDto=new SoPriceHistoryVO();
				    	  soLevelDto.setAction(soLevelFromXml.getAction());
				    	  soLevelDto.setModifiedDate(setDateAndTimeZone(soLevelFromXml.getChangedDate()));
				    	  soLevelDto.setAction(soLevelFromXml.getAction());
				    	  
				    	  // Set the user name 
				    	  String user = soLevelFromXml.getChangedByUserName();
				    	  String userId = null;
				    	  if(!StringUtils.isEmpty(user) &&  !user.equalsIgnoreCase(soLevelFromXml.getChangedByUserId())){
				    		  userId = "(User Id# "+soLevelFromXml.getChangedByUserId()+")";
				    	  }
				    	  if(userId == null){
				    		  userId = "";
				    	  }
				    	  //set reason comments
				    	  String reasonComments=null;
				    	  if(StringUtils.isNotBlank(soLevelFromXml.getReasonCode())){
				    		  reasonComments=soLevelFromXml.getReasonCode();
				    	  }
				    	  soLevelDto.setReasonComments(reasonComments);
				    	  soLevelDto.setModifiedBy(userId);
				    	  soLevelDto.setModifiedByName(user);
				    	  soLevelDto.setLaborPriceChange(setCreditOrdebitindicator(soLevelFromXml.getLaborPriceChange()));
				    	  soLevelDto.setPartPriceChange(setCreditOrdebitindicator(soLevelFromXml.getPartPriceChange()));
				    	  soLevelDto.setAddonPriceChange(setCreditOrdebitindicator(soLevelFromXml.getAddonPriceChange()));
				    	  soLevelDto.setInvoicePartPriceChange(setCreditOrdebitindicator(soLevelFromXml.getInvoicePartPriceChange()));
				    	  soLevelDto.setPermitPriceChange(setCreditOrdebitindicator(soLevelFromXml.getPermitPriceChange()));
				    	  soLevelDto.setTotalPrice(soLevelFromXml.getTotalPrice().toString());
				    	//soLevelDto.setTimeZone(priceHistorytabDto.getTimeZone());
				    	  soPriceHistoryVOs.add(soLevelDto);
				     }
				     
				   // Collections.reverse(soPriceHistoryVOs);
			    	  priceHistorytabDto.setSoLevelHistoryVo(soPriceHistoryVOs);

				 }else{
					 priceHistorytabDto.setSoLevelHistoryVo(soPriceHistoryVOs);					 
				 }
			} 
		}
	}

	private String setCreditOrdebitindicator(BigDecimal bigDecimalValue) {
		String bigDecimalToString="0";
		if(bigDecimalValue !=  null){
		bigDecimalToString=bigDecimalValue.toString();
		if(StringUtils.contains(bigDecimalToString, OrderConstants.DEBIT_CHAR)){
			bigDecimalToString=OrderConstants.DEBIT_INDICATOR+" "+"$"+StringUtils.remove(bigDecimalToString, OrderConstants.DEBIT_CHAR);
			return bigDecimalToString;
		}else if(StringUtils.contains(bigDecimalToString, OrderConstants.CREDIT_CHAR)){
			bigDecimalToString=OrderConstants.CREDIT_INDICATOR+" "+"$"+StringUtils.remove(bigDecimalToString, OrderConstants.CREDIT_CHAR);
			return bigDecimalToString;
		}
		else{
			////assume that amount is zero 
			if(bigDecimalToString.equals("0.00") || bigDecimalToString.equals("0")){
				//we are not displaying anything for the amount zero
				//return "$"+ bigDecimalToString;
				return null;
			}
			//assume that amount is credited
			bigDecimalToString=OrderConstants.CREDIT_INDICATOR+" "+"$"+bigDecimalToString;
			return bigDecimalToString;
		    }
		  }else{
			  return null;
		  }
		
		}
	public SoDetailsPriceHistoryRestClient getSoDetailsPriceHistoryRestClient() {
		return soDetailsPriceHistoryRestClient;
	}

	public void setSoDetailsPriceHistoryRestClient(
			SoDetailsPriceHistoryRestClient soDetailsPriceHistoryRestClient) {
		this.soDetailsPriceHistoryRestClient = soDetailsPriceHistoryRestClient;
	}
    /*This method will parse the string to date object,format it  for frond end display and 
	  convert back to string*/
	private String setDateAndTimeZone(String finalDate) {
		if (StringUtils.isNotBlank(finalDate)) {
			/*
			Date dateObject;
			try {
				logger.info("18007: Final date passed  - " + finalDate);
				dateObject = CommonUtility.sdfToDate.parse(finalDate);
				TimeZone timeZone=TimeZone.getTimeZone(OrderConstants.DEFAULT_SERVICE_LOCATION_TIMEZONE);
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy HH:MM zzz");
				formatter.setTimeZone(timeZone);
				finalDate = formatter.format(dateObject);
				logger.info("18007: Final date after parsing   - " + finalDate);
				//Daylight saving for CST
				if(timeZone.inDaylightTime(dateObject)){
					finalDate =StringUtils.replaceOnce(finalDate,"CST", "CDT");
					}
				    finalDate = StringUtils.replaceOnce(finalDate,"CDT", "(CDT)");
				    finalDate = StringUtils.replaceOnce(finalDate,"CST", "(CST)");
			} catch (ParseException p) {
				return finalDate;
			}*/
			String finalDateTemp = finalDate;
			Date dateObject;
			try {
				logger.info("18007: Date passed  - " + finalDate);
				dateObject = CommonUtility.sdfToDate.parse(finalDate);
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy HH:mm");
				finalDate = formatter.format(dateObject);
				logger.info("18007: Date after parsing   - " + finalDate);
				//
				dateObject = CommonUtility.sdfToDate.parse(finalDateTemp);
				TimeZone timeZone=TimeZone.getTimeZone(OrderConstants.DEFAULT_SERVICE_LOCATION_TIMEZONE);
				formatter = new SimpleDateFormat("MM/dd/yy HH:mm zzz");
				formatter.setTimeZone(timeZone);
				//Daylight saving for CST
				if(timeZone.inDaylightTime(dateObject)){
					logger.info("18007: inDaylightTime  - YES ");
					finalDate =finalDate + " " + "CDT";
				}else{
					logger.info("18007: inDaylightTime  - NO ");
					finalDate =finalDate + " " + "CST";
				}
				finalDate = StringUtils.replaceOnce(finalDate,"CDT", "(CDT)");
				finalDate = StringUtils.replaceOnce(finalDate,"CST", "(CST)");
				logger.info("18007: Date after parsing   - " + finalDate);
			} catch (ParseException p) {
				return finalDate;
			}
			
		} else {
			return finalDate;
		}
		return finalDate;

	}
}
