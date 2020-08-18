package com.servicelive.inhome.notification.mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.beans.JobCodeData;
import com.newco.marketplace.inhomeoutboundnotification.beans.OrderUpdateRequest;
import com.newco.marketplace.inhomeoutboundnotification.beans.RequestInHomeDetails;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.inhomeOutBoundNotification.NotificationDao;
import com.newco.marketplace.utils.TimeUtils;

/**
 * 
 * @author Infosys
 * Used for mapping In Home out-bound notification requests
 * for status changes and Closure  
 */
public class NotificationServiceMapper{
	   private static final Logger logger = Logger.getLogger(NotificationServiceMapper.class);
	   
	   //SL-21241
	   private NotificationDao notificationDao;
	    /**
		 * @param message
		 * @param orderNo
		 * @param unitNo
		 * @param empId
		 * @param soId
		 * @return
		 * @throws BusinessServiceException
		 */
		public RequestInHomeDetails mapDetails(String orderNo,String unitNo,String techId, String message) throws BusinessServiceException {
			RequestInHomeDetails requestDetails = new RequestInHomeDetails();
			requestDetails.setFromFunction(InHomeNPSConstants.INHOME_FROM_FUNCTION);
			requestDetails.setToFunction(InHomeNPSConstants.INHOME_TO_FUNCTION);
			requestDetails.setOrderType(InHomeNPSConstants.IN_HOME_ORDER_TYPE);
			if(StringUtils.isNotBlank(orderNo) && StringUtils.isNumeric(orderNo)){
				requestDetails.setOrderNum(orderNo);
			}else{
				requestDetails.setOrderNum(null);
			}
			if(StringUtils.isNotBlank(unitNo)&& StringUtils.isNumeric(unitNo)){
				requestDetails.setUnitNum(unitNo);
			}else{
				requestDetails.setUnitNum(null);
			}
			if(StringUtils.isNotBlank(techId)&& StringUtils.isNumeric(techId)){
				requestDetails.setEmpId(techId);
			}else{
				requestDetails.setEmpId(null);
			}
			requestDetails.setMessage(replaceSpecialCharacters(message));
			return requestDetails;

		}

		/*public String createMessageForReschedule(InHomeRescheduleVO homeRescheduleVO) {
			String message="";
			Date oldServiceStartDate = null;
			Date oldServiceEndDate = null;
			String oldServiceStartTime = null;
			String oldServiceEndTime = null;
			Date newServiceStartDate = null;
			Date newServiceEndDate = null;
			String newServiceStartTime = null;
			String newServiceEndTime = null;
			Calendar oldEndDateTime=null;
			Calendar newEndDateTime = null;
			Date oldServiceToDate = null;
			Date newServiceToDate = null;
			String oldServiceTimeEnd =null;
			String newServiceTimeEnd = null;
			*//**Getting all values and started mapping values*//*
			TimeZone timeZone=TimeZone.getTimeZone(homeRescheduleVO.getTimeZone());
			oldServiceStartDate = homeRescheduleVO.getServiceDate1();
			oldServiceStartTime = homeRescheduleVO.getStartTime();
			
			if(null!= homeRescheduleVO && null!= homeRescheduleVO.getServiceDate2()){
			   oldServiceEndDate = homeRescheduleVO.getServiceDate2();
			}
			
			if(null!= homeRescheduleVO && StringUtils.isNotBlank(homeRescheduleVO.getEndTime())){
			   oldServiceEndTime = homeRescheduleVO.getEndTime();
			}
			if(null!= homeRescheduleVO && InHomeNPSConstants.RELEASE_INDICATOR_TRUE == homeRescheduleVO.getReleasIndicator()){
				newServiceStartDate = homeRescheduleVO.getBuyerRescheduleServiceDate1();
				newServiceStartTime = homeRescheduleVO.getBuyerRescheduleStartTime();
				if(null != homeRescheduleVO.getBuyerRescheduleServiceDate2()){
				    newServiceEndDate = homeRescheduleVO.getBuyerRescheduleServiceDate2();
				}
				if(StringUtils.isNotBlank(homeRescheduleVO.getBuyerRescheduleEndTime())){
				   newServiceEndTime = homeRescheduleVO.getBuyerRescheduleEndTime();
				}
			}else if(null!= homeRescheduleVO &&InHomeNPSConstants.RELEASE_INDICATOR_FALSE == homeRescheduleVO.getReleasIndicator() ){
				   newServiceStartDate = homeRescheduleVO.getRescheduleServiceDate1();
				   newServiceStartTime = homeRescheduleVO.getRescheduleStartTime();
				if(null != homeRescheduleVO.getRescheduleServiceDate2()){
				   newServiceEndDate = homeRescheduleVO.getRescheduleServiceDate2();
				}
				if(StringUtils.isNotBlank(homeRescheduleVO.getRescheduleEndTime())){
				   newServiceEndTime = homeRescheduleVO.getRescheduleEndTime();
				}
			}
			*//**Converting all date time to service order location timezones *//*
			    logger.info("Old Start date :"+ oldServiceStartDate);
			    logger.info("Old Start time :" +oldServiceStartTime);
			    logger.info("Old End date :"+ oldServiceEndDate);
			    logger.info("Old End Time :"+ oldServiceEndTime);
			    logger.info("New Start date :"+ newServiceStartDate);
			    logger.info("New Start time :" +newServiceStartTime);
			    logger.info("New End date :"+ newServiceEndDate);
			    logger.info("New End Time :"+ newServiceEndTime);
				Calendar oldstartDateTime = TimeChangeUtil.getCalTimeFromParts(oldServiceStartDate, oldServiceStartTime,TimeZone.getTimeZone("GMT"));
				Calendar newstartDateTime = TimeChangeUtil.getCalTimeFromParts(newServiceStartDate, newServiceStartTime,TimeZone.getTimeZone("GMT"));
				if(null != oldServiceEndDate){
				   oldEndDateTime = TimeChangeUtil.getCalTimeFromParts(oldServiceEndDate,oldServiceEndTime,TimeZone.getTimeZone("GMT"));
		         }
				if(null!=newServiceEndDate ){
				   newEndDateTime = TimeChangeUtil.getCalTimeFromParts(newServiceEndDate,newServiceEndTime,TimeZone.getTimeZone("GMT"));
				}
				Date oldServiceFromDate = TimeChangeUtil.getDate(oldstartDateTime, timeZone);
				if(null!=oldEndDateTime ){
				   oldServiceToDate = TimeChangeUtil.getDate(oldEndDateTime, timeZone);
				}
				String oldServiceTimeStart = TimeChangeUtil.getTimeString(oldstartDateTime, timeZone);
				if(null!= oldEndDateTime ){
				 oldServiceTimeEnd = TimeChangeUtil.getTimeString(oldEndDateTime, timeZone);
				}
				Date newServiceFromDate = TimeChangeUtil.getDate(newstartDateTime, timeZone);
				if(null != newEndDateTime){
				  newServiceToDate = TimeChangeUtil.getDate(newEndDateTime, timeZone);
				}
				String newServiceTimeStart = TimeChangeUtil.getTimeString(newstartDateTime, timeZone);
				if(null!= newEndDateTime){
				  newServiceTimeEnd = TimeChangeUtil.getTimeString(newEndDateTime, timeZone);
				}
				*//**Reschedule From Range(Dates)--> Range(Dates) *//*
			if(null!= oldServiceEndDate && null!= newServiceEndDate){
				message = InHomeNPSConstants.RESCHEDULE_MESSAGE 
						  + InHomeNPSConstants.APPOINMENT_DATES
						  + formatDate(oldServiceFromDate) + InHomeNPSConstants.SEQ_HYP + formatDate(oldServiceToDate)
						  + InHomeNPSConstants.APPOINMENT_TIME_WINDOW 
						  + oldServiceTimeStart+ InHomeNPSConstants.SEQ_HYP + oldServiceTimeEnd
						  + InHomeNPSConstants.RESCHEDULE_DATES
						  + formatDate(newServiceFromDate) + InHomeNPSConstants.SEQ_HYP + formatDate(newServiceToDate)
						  + InHomeNPSConstants.RESCHEDULE__TIME_WINDOW
						  + newServiceTimeStart + InHomeNPSConstants.SEQ_HYP + newServiceTimeEnd;
			}*//**Reschedule From Range(Dates)--> Single(Date) *//*
			else if(null!= oldServiceEndDate && null == newServiceEndDate){
				message = InHomeNPSConstants.RESCHEDULE_MESSAGE 
						  + InHomeNPSConstants.APPOINMENT_DATES
						  + formatDate(oldServiceFromDate) + InHomeNPSConstants.SEQ_HYP + formatDate(oldServiceToDate)
						  + InHomeNPSConstants.APPOINMENT_TIME_WINDOW 
						  + oldServiceTimeStart+ InHomeNPSConstants.SEQ_HYP + oldServiceTimeEnd
						  + InHomeNPSConstants.RESCHEDULE_DATE
						  + formatDate(newServiceFromDate)
						  + InHomeNPSConstants.RESCHEDULE__TIME_WINDOW
						  + newServiceTimeStart;
			}*//**Reschedule From Single(Date)--> Single(Date) *//*
			else if(null == oldServiceEndDate && null == newServiceEndDate){
				message = InHomeNPSConstants.RESCHEDULE_MESSAGE 
						  + InHomeNPSConstants.APPOINMENT_DATE
						  + formatDate(oldServiceFromDate)
						  + InHomeNPSConstants.APPOINMENT_TIME_WINDOW 
						  + oldServiceTimeStart+ InHomeNPSConstants.SEQ_HYP
						  + InHomeNPSConstants.RESCHEDULE_DATE
						  + formatDate(newServiceFromDate)
						  + InHomeNPSConstants.RESCHEDULE__TIME_WINDOW
						  + newServiceTimeStart;
			}*//**Reschedule From Single(Date)--> Range(Dates) *//*
			else if(null == oldServiceEndDate && null != newServiceEndDate){
				message = InHomeNPSConstants.RESCHEDULE_MESSAGE 
						  + InHomeNPSConstants.APPOINMENT_DATE
						  + formatDate(oldServiceFromDate)
						  + InHomeNPSConstants.APPOINMENT_TIME_WINDOW 
						  + oldServiceTimeStart
						  + InHomeNPSConstants.RESCHEDULE_DATES
						  + formatDate(newServiceFromDate) + InHomeNPSConstants.SEQ_HYP + formatDate(newServiceToDate)
						  + InHomeNPSConstants.RESCHEDULE__TIME_WINDOW
						  + newServiceTimeStart + InHomeNPSConstants.SEQ_HYP + newServiceTimeEnd;
			}
			logger.info("Message formed: "+ message);
			return message;
		}*/
		public String formatDate(Date unformattedDate){
			String formatedDate="";
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
			if(null!= formatedDate){
				formatedDate=formatter.format(unformattedDate);
			}
			return formatedDate;
			
		}
		
		// Replace < &lt; > &gt;  & &amp; " &quot; ' &apos;
		private String replaceSpecialCharacters(String value) {
			if(StringUtils.isNotBlank(value)){
				value = value.replaceAll("&","&amp;");
				value = value.replaceAll("<","&lt;");
				value = value.replaceAll(">","&gt;");
				value = value.replaceAll("\"","&quot;");
				value = value.replaceAll("'", "&apos;");
			}
			return value;
		}
		
		
		/**R12_0  
		 * Description:map the request fields of Service Operations API for revisit needed
		 * @param inHomeDetails
		 * @param serviceOrder
		 * @return
		 */
		public OrderUpdateRequest mapInHomeDetails(OrderUpdateRequest inHomeDetails, String orderNo,String unitNo,String techId,String correlationId,InHomeRescheduleVO input,String coverageTypeLabor) {
			
			logger.info("Entering mapInHomeDetails");
			try{
				inHomeDetails.setSoId(input.getSoId());
				//mandatory fields
				//TODO Clarification on mandatory fields
				inHomeDetails.setCorrelationId(correlationId);
				inHomeDetails.setOrderType(InHomeNPSConstants.IN_HOME_ORDER_TYPE);
				if(null != unitNo){
					inHomeDetails.setUnitNum(unitNo);
				}else{
					inHomeDetails.setUnitNum(InHomeNPSConstants.NO_DATA);
				}
				if(null != orderNo){
					inHomeDetails.setOrderNum(orderNo);
				}else{
					inHomeDetails.setOrderNum(InHomeNPSConstants.NO_DATA);
				}
				inHomeDetails.setRouteDate(formatDates(new Date()));
				if(null != techId){
					inHomeDetails.setTechId(techId);
				}else{
					inHomeDetails.setTechId(InHomeNPSConstants.NO_DATA);
				}

				//changing call code based on the reason code in the SO Revisit needed API 
				if(null!=input.getRevisitReason())
				{
					//Setting Call Code as 13 for revisit reason :Customer Not Home
					if(InHomeNPSConstants.CUSTOMER_NOT_HOME_REASON.equals(input.getRevisitReason()))
					{
						inHomeDetails.setCallCd(InHomeNPSConstants.CUSTOMER_NOT_HOME_CALLCODE);
					}
					else
					{
						
						//Setting Call Code as 15 for revisit reason :Parts Needed	
						if(InHomeNPSConstants.PARTS_NEEDED_REASON.equalsIgnoreCase(input.getRevisitReason()))
						{
							inHomeDetails.setCallCd(InHomeNPSConstants.RESCHD_PARTS_CALLCODE);
						}
						else 
						{
							//Setting Call Code as 16 for other revisit reasons
							inHomeDetails.setCallCd(InHomeNPSConstants.RESCHD_CALLCODE);
						}
						
						//mapping job code details
						//SL-21241 : Adding soId as a parameter
						inHomeDetails.setJobCodeData(mapJobCodeData(coverageTypeLabor, input.getSoId()));			
						
						//mapping reschedule date and time
						if(null != input.getRescheduleDate1()){
							inHomeDetails.setReschdDate(formatStringtoDate(input.getRescheduleDate1()));
						}else{
							inHomeDetails.setReschdDate(InHomeNPSConstants.NO_DATA);
						}
						
						if(null != input.getRescheduleStartTime()){
							inHomeDetails.setReschdFromTime(formatTime(input.getRescheduleStartTime()));
						}else{
							inHomeDetails.setReschdFromTime(InHomeNPSConstants.NO_DATA);
						}
						
						if(null != input.getRescheduleEndTime()){
							inHomeDetails.setReschdToTime(formatTime(input.getRescheduleEndTime()));
						}else{
							inHomeDetails.setReschdToTime(InHomeNPSConstants.NO_DATA);
						}
					}
					
				}else if(input.isReleaseFlag()){
					//SL-20408 trigger Service Operations API for release SO after buyer reschedule
					inHomeDetails.setCallCd(InHomeNPSConstants.RESCHD_CALLCODE);
					//mapping job code details
					//SL-21241 : Adding soId as a parameter
					inHomeDetails.setJobCodeData(mapJobCodeData(coverageTypeLabor, input.getSoId()));			
					//mapping reschedule date and time
					HashMap<String, Object> rescheduleStartDate = null;
					HashMap<String, Object> rescheduleEndDate = null;
					if (null != input.getBuyerRescheduleServiceDate1() && null != input.getBuyerRescheduleStartTime()) {
	            		rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(input.getBuyerRescheduleServiceDate1(), input.getBuyerRescheduleStartTime(), input.getTimeZone());
	            		if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
	    						inHomeDetails.setReschdDate(formatDates((Date) rescheduleStartDate.get(OrderConstants.GMT_DATE)));
	            		}else{
    						inHomeDetails.setReschdDate(InHomeNPSConstants.NO_DATA);
    					}
	            		if(null != rescheduleStartDate.get(OrderConstants.GMT_TIME)){
							inHomeDetails.setReschdFromTime(formatTime((String) rescheduleStartDate.get(OrderConstants.GMT_TIME)));
						}else{
							inHomeDetails.setReschdFromTime(InHomeNPSConstants.NO_DATA);
						}
	    			}
					else
					{
						inHomeDetails.setReschdDate(InHomeNPSConstants.NO_DATA);
						inHomeDetails.setReschdFromTime(InHomeNPSConstants.NO_DATA);
					}
	            	if(null != input.getBuyerRescheduleServiceDate2() && null != input.getBuyerRescheduleEndTime()){
	            		rescheduleEndDate = TimeUtils.convertGMTToGivenTimeZone(input.getBuyerRescheduleServiceDate2(), input.getBuyerRescheduleEndTime(), input.getTimeZone());
	            		if (null != rescheduleEndDate && !rescheduleEndDate.isEmpty()) {
	            			if(null != rescheduleEndDate.get(OrderConstants.GMT_TIME)){
								inHomeDetails.setReschdToTime(formatTime((String) rescheduleEndDate.get(OrderConstants.GMT_TIME)));
							}   		
	            		}else{
							inHomeDetails.setReschdToTime(InHomeNPSConstants.NO_DATA);
						}
	            	}else if(null != input.getBuyerRescheduleServiceDate1() && null != input.getBuyerRescheduleStartTime() && null != rescheduleStartDate && !rescheduleStartDate.isEmpty() && null!=rescheduleStartDate.get(OrderConstants.GMT_TIME)){
						inHomeDetails.setReschdToTime(formatTime((String) rescheduleStartDate.get(OrderConstants.GMT_TIME)));
					} else
	            	{
	            		inHomeDetails.setReschdToTime(InHomeNPSConstants.NO_DATA);
	            	}
				}
				else
				{
					inHomeDetails.setCallCd(InHomeNPSConstants.NO_DATA);
				}
				
				
				inHomeDetails.setServiceFromTime(InHomeNPSConstants.DEFAULT_START_TIME);			
				inHomeDetails.setServiceToTime(InHomeNPSConstants.DEFAULT_END_TIME);
				
			
			

			}catch (Exception e){
				logger.error("Exception in NotificationServiceMapper.mapInHomeDetails() " + e);
			}
			logger.info("Leaving mapInHomeDetails");
			return inHomeDetails;
		}
		
		/**R12_0
		 * construct jobCodeData object
		 * SL-21241 : Adding soId as a parameter
		 * @param serviceOrder
		 * @soId
		 * @return
		 */
		private JobCodeData mapJobCodeData(String coverageTypeLabor, String soId) {
			
			logger.info("Mapping Job fields");
			try{
				//Assuming that there will be only one task for InHome order
				JobCodeData job = new JobCodeData();
				job.setJobCalcPrice(InHomeNPSConstants.JOB_CALC_PRICE);
				if(null != coverageTypeLabor){
					job.setJobCoverageCd(coverageTypeLabor);
				}else{
					job.setJobCoverageCd(InHomeNPSConstants.NO_DATA);
				}
				//SL-21241:
				//Setting the jobCode based on main category of the SO
				String jobCode = notificationDao.getJobCodeForMainCategory(soId);
				if(StringUtils.isNotBlank(jobCode)){
					job.setJobCode(jobCode);
				}
				return job;
			}
			catch(Exception e){
				logger.error("Exception in NotificationServiceMapper.mapJobCodeData() due to "+e);
			}
			return null;
		}
		
		/**R12_0
		 * Format the date as MMddyyyy
		 * @param format
		 * @param date
		 * @return
		 */
		private String formatDates(Date date){
			DateFormat formatter = new SimpleDateFormat(InHomeNPSConstants.TIME_FORMAT);
			String formattedDate = InHomeNPSConstants.NO_DATA;
			try {
				if(null != date){
					formattedDate = formatter.format(date);
					//to get the format as MMddyyyy
					formattedDate = formattedDate.substring(0,formattedDate.indexOf(","));
					String dates[] = formattedDate.split("-");
					formattedDate = dates[1] + dates[2] + dates[0];
				}
			} catch (Exception e) {
				logger.error("Exception in NotificationServiceMapper.formatDate() "+ e.getMessage(), e);
				formattedDate = InHomeNPSConstants.NO_DATA;
			}
			return formattedDate;
		}
		
		
		/**R12_0
		 * Format the date as MMddyyyy
		 * @param format
		 * @param date
		 * @return
		 */
		private String formatStringtoDate(String date){
			DateFormat formatter = new SimpleDateFormat(InHomeNPSConstants.TIME_FORMAT);
			SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");	
			Date formatDate = null;
			String formattedDate = InHomeNPSConstants.NO_DATA;
			try {
				if(null != date){
					formatDate = defaultDateFormat.parse(date);
					formattedDate = formatter.format(formatDate);
					//to get the format as MMddyyyy
					formattedDate = formattedDate.substring(0,formattedDate.indexOf(","));
					String dates[] = formattedDate.split("-");
					formattedDate = dates[1] + dates[2] + dates[0];
				}
			} catch (Exception e) {
				logger.error("Exception in NotificationServiceMapper.formatDate() "+ e.getMessage(), e);
				formattedDate = InHomeNPSConstants.NO_DATA;
			}
			return formattedDate;
		}
		
		/**R12_0
		 * Format the time as HHmm
		 * @param format
		 * @param date
		 * @return
		 */
		private String formatTime(String time){
			String formattedTime = InHomeNPSConstants.NO_DATA;
			try {
				if(null != time){
				
					String times[] = time.split(":");
					String[] timeFormatted=times[1].split(" ");
					formattedTime = times[0] + timeFormatted[0] + timeFormatted[1];
				}
			} catch (Exception e) {
				logger.error("Exception in NotificationServiceMapper.formatTime() "+ e.getMessage(), e);
				formattedTime = InHomeNPSConstants.NO_DATA;
			}
			return formattedTime;
		}

		public NotificationDao getNotificationDao() {
			return notificationDao;
		}

		public void setNotificationDao(NotificationDao notificationDao) {
			this.notificationDao = notificationDao;
		}
		

}