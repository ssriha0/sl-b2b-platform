package com.newco.marketplace.web.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.inhomeOutBoundNotification.NotificationServiceImpl;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;



/**
 * @author zizrale
 *
 */
public class RescheduleDTO extends SerializedBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2918858684583469746L;
	private static final Logger logger = Logger.getLogger(RescheduleDTO.class);
	private String soId;
	private Integer resourceId;
	private String newStartDate; 
	private String newEndDate; 
	private String newStartTime; 
	private String newEndTime;
	private Integer subStatus; 
	private Integer requestorRole;
	private String rangeOfDates;
	private boolean rescheduleAccepted;
	private Integer companyId;
	private String rescheduleComments;
	private String reasonCode;
	
	/*SL 15642: */
	private boolean specificDate;
	private String timeZone;

	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getRescheduleComments() {
		return rescheduleComments;
	}
	public void setRescheduleComments(String rescheduleComments) {
		this.rescheduleComments = rescheduleComments;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public boolean isRescheduleAccepted() {
		return rescheduleAccepted;
	}
	public void setRescheduleAccepted(boolean rescheduleAccepted) {
		this.rescheduleAccepted = rescheduleAccepted;
	}
	public String getNewEndDate() {
		return newEndDate;
	}
	public void setNewEndDate(String newEndDate) {
		this.newEndDate = newEndDate;
	}
	public String getNewEndTime() {
		return newEndTime;
	}
	public void setNewEndTime(String newEndTime) {
		this.newEndTime = newEndTime;
	}
	public String getNewStartDate() {
		return newStartDate;
	}
	public void setNewStartDate(String newStartDate) {
		this.newStartDate = newStartDate;
	}
	public String getNewStartTime() {
		return newStartTime;
	}
	public void setNewStartTime(String newStartTime) {
		this.newStartTime = newStartTime;
	}
	public Integer getRequestorRole() {
		return requestorRole;
	}
	public void setRequestorRole(Integer requestorRole) {
		this.requestorRole = requestorRole;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(Integer subStatus) {
		this.subStatus = subStatus;
	}
	public String getRangeOfDates() {
		return rangeOfDates;
	}
	public void setRangeOfDates(String rangeOfDates) {
		this.rangeOfDates = rangeOfDates;
	}	

	public ProcessResponse validate(ServiceOrderDTO serviceOrder,String rangeOfDates) {
	
		  java.util.Date today = new java.util.Date();
		  java.sql.Date now = new java.sql.Date(today.getTime());
		
		  ProcessResponse processResp = new ProcessResponse();
		  List<String> messageArray = new ArrayList<String>();
		  Timestamp newStartDate1 = Timestamp.valueOf(newStartDate + " 00:00:00");
		  Timestamp newStartTimeCombined = new Timestamp(TimeUtils.combineDateAndTime(newStartDate1, newStartTime, serviceOrder.getSoLocationTimeZone()).getTime());
		  if(null != newStartTimeCombined ){
		    logger.info("Class type of newStartTimeCombined:"+ newStartTimeCombined.getClass());
		  }
		  if(null!= now){
			logger.info("Class type of now :"+ now.getClass());
		  }
		  try{
		     if (newStartTimeCombined.compareTo(now) < 0) {
		         processResp.setCode(ServiceConstants.USER_ERROR_RC);
		         processResp.setSubCode(ServiceConstants.USER_ERROR_RC);
		         messageArray.add("Start Date must be in the future.");
		         processResp.setMessages(messageArray);
		        }
		     }catch(Exception e){
		    	 logger.error("Exception in validating reschedule Request:"+ e);
		     }
		 
		  if (newEndDate != null && "1".equalsIgnoreCase(rangeOfDates)) {
		   // check if start < end
		  Timestamp newEndDate1 = Timestamp.valueOf(newEndDate + " 00:00:00");
		  Timestamp newEndTimeCombined = new Timestamp(TimeUtils.combineDateAndTime(newEndDate1, newEndTime, serviceOrder.getSoLocationTimeZone()).getTime());
		   if (newStartTimeCombined.compareTo(newEndTimeCombined) >= 0) {
		    processResp.setCode(ServiceConstants.USER_ERROR_RC);
		    processResp.setSubCode(ServiceConstants.USER_ERROR_RC);
		    messageArray.add("Start Date must be prior to End Date.");
		    processResp.setMessages(messageArray);
		    
		   }
		 }
		  if(processResp.getMessages().size()==0){
			  processResp.setCode("00");
		  }
		  return processResp;
		 }
	public String getReasonCode() {
		return reasonCode;
		}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;

	}
	public boolean isSpecificDate() {
		return specificDate;
	}
	public void setSpecificDate(boolean specificDate) {
		this.specificDate = specificDate;
	}
		}


