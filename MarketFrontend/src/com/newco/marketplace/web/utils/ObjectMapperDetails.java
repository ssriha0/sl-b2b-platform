package com.newco.marketplace.web.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.util.DateFormatter;

import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.RevisitNeededInfoVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.web.dto.RevisitNeededInfoDTO;
import com.newco.marketplace.web.dto.SOContactDTO;
import com.newco.marketplace.web.dto.SOLoggingDTO;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;

public abstract class ObjectMapperDetails {

	private static final Logger logger = Logger.getLogger("ObjectMapperDetails");
	
	public static ServiceOrderNote convertServiceOrderNoteDTOtoVO(ServiceOrderNoteDTO noteDTO){
		//Error Handling or are the vars initialized?
		ServiceOrderNote orderNote = new ServiceOrderNote();
		
		// TODO : big mess here. groupId is treated as soId while storing notes.
		if(StringUtils.isBlank(noteDTO.getSoId())){
			orderNote.setSoId(noteDTO.getGroupId());
		}else{
			orderNote.setSoId(noteDTO.getSoId());
		}
		orderNote.setSubject(noteDTO.getSubject());
		orderNote.setNote(noteDTO.getMessage());
		orderNote.setNoteTypeId(noteDTO.getNoteTypeId());
		orderNote.setRoleId(noteDTO.getRoleId());
		orderNote.setNoteTypeIds(noteDTO.getNoteTypeIds());
		Integer privateID =  null; 
		if(noteDTO.getRadioSelection() != null && !noteDTO.getRadioSelection().equals(""))
		{
			try {
				privateID = new Integer(noteDTO.getRadioSelection());
			}
			catch(NumberFormatException nfe)
			{
				privateID = new Integer(-1);
			}
		}
		else
		{
			privateID = new Integer(-1);
		}
		orderNote.setPrivateId( privateID );
		return orderNote;
	}
	
	public static ArrayList<SOContactDTO> convertVOListtoContactDTOList(List contactList) {
		
		ArrayList<SOContactDTO> soContactDTOList = new ArrayList<SOContactDTO>();
		SOContactDTO sContactDTO = null;
		Contact contact = null;
		Iterator<Contact> listIter = contactList.iterator();
		while(listIter.hasNext()) {
			contact = (Contact)listIter.next();
			sContactDTO = new SOContactDTO();
			sContactDTO.setResourceId(contact.getResourceId());
			sContactDTO.setFirstName(contact.getFirstName());
			sContactDTO.setLastName(contact.getLastName());
			sContactDTO.setEmail(contact.getEmail());
			sContactDTO.setDisplayName(contact.getFirstName() + contact.getLastName()+ " ("+ contact.getResourceId() +")");
			soContactDTOList.add(sContactDTO);
		}
		return soContactDTOList;
	}
	
public static SoLoggingVo convertLoggingDTOtoLoggingVO(SOLoggingDTO soLoggingDTO) {
		
		SoLoggingVo soLoggingVO = new SoLoggingVo();
		//Setting values in VO from the DTO passed
		soLoggingVO.setServiceOrderNo(soLoggingDTO.getServiceOrderNo());
		soLoggingVO.setFirstName(soLoggingDTO.getFirstName());
		soLoggingVO.setLastName(soLoggingDTO.getLastName());
		soLoggingVO.setEmail(soLoggingDTO.getEmail());
		soLoggingVO.setContactId(soLoggingDTO.getContactId());
		soLoggingVO.setActionId(soLoggingDTO.getActionId());
		soLoggingVO.setOldValue(soLoggingDTO.getOldValue());
		soLoggingVO.setNewValue(soLoggingDTO.getNewValue());
		soLoggingVO.setComment(soLoggingDTO.getComment());
		soLoggingVO.setCreatedByName(soLoggingDTO.getCreatedByName());
		soLoggingVO.setCreatedDate(soLoggingDTO.getCreatedDate());
		soLoggingVO.setModifiedBy(soLoggingDTO.getModifiedBy());
		soLoggingVO.setModifiedDate(soLoggingDTO.getModifiedDate());
		soLoggingVO.setRoleId(soLoggingDTO.getRoleId());
		soLoggingVO.setEntityId(soLoggingDTO.getEntityId());
		return soLoggingVO;
	}

public static RevisitNeededInfoDTO convertRevisitTripVOtoRevisitTripDTO(RevisitNeededInfoVO soRevisitNeededVO){
	//Setting values in DTO from the VO passed
	
	RevisitNeededInfoDTO revisitDTO = new RevisitNeededInfoDTO();
	
	if(null != soRevisitNeededVO.getProvider()){
		if((OrderConstants.REVISIT_NEEDED).equals(soRevisitNeededVO.getDepartureReason())){
			revisitDTO.setProvider(soRevisitNeededVO.getRevisitCreatedBy());
		}else{
			revisitDTO.setProvider(soRevisitNeededVO.getProvider());
		}
	}
	
	if(null != soRevisitNeededVO.getCurrentApptStartDate() && null != soRevisitNeededVO.getCurrentApptStartTime()){
		try{
			HashMap<String, Object> apptStartDate = null;
			HashMap<String, Object> apptEndDate = null;
			apptStartDate = TimeUtils.convertGMTToGivenTimeZone(soRevisitNeededVO.getCurrentApptStartDate(), soRevisitNeededVO.getCurrentApptStartTime(), soRevisitNeededVO.getTimeZone());
			String apptDate = formatDate((Date) apptStartDate.get(OrderConstants.GMT_DATE), OrderConstants.DATE_FORMAT_3);
			String apptTime = (String)apptStartDate.get(OrderConstants.GMT_TIME);
			if(null!=soRevisitNeededVO.getCurrentApptEndDate() && null!=soRevisitNeededVO.getCurrentApptEndTime()){
				apptEndDate = TimeUtils.convertGMTToGivenTimeZone(soRevisitNeededVO.getCurrentApptEndDate(), soRevisitNeededVO.getCurrentApptEndTime(), soRevisitNeededVO.getTimeZone());
				String apptEndDateString = formatDate((Date) apptEndDate.get(OrderConstants.GMT_DATE), OrderConstants.DATE_FORMAT_3);
				String apptEndTimeString = (String)apptEndDate.get(OrderConstants.GMT_TIME);
				if(!apptDate.equals(apptEndDateString)){
					apptDate = apptDate +" - "+ apptEndDateString;
				}
				if(!apptTime.equals(apptEndTimeString)){
					apptTime = apptTime +" - "+ apptEndTimeString;
				}
			}
			apptTime = apptTime + " " + getTimeZone((Date)apptStartDate.get(OrderConstants.GMT_DATE), soRevisitNeededVO.getTimeZone());
			revisitDTO.setAppointmentDate(apptDate);
			revisitDTO.setServiceWindow(apptTime);
		}catch(Exception e){
			logger.error("Exception in mapping appt date in convertRevisitTripVOtoRevisitTripDTO()"+e);
		}
	}
	
	if(null != soRevisitNeededVO.getCheckedInDate()){
		try{
			revisitDTO.setCheckedIn(getDateStringOnZipcode(soRevisitNeededVO.getCheckedInDate(), soRevisitNeededVO.getTimeZone(), OrderConstants.DATE_FORMAT_4) + " "+ getTimeZone(soRevisitNeededVO.getCheckedInDate(), soRevisitNeededVO.getTimeZone()));
		}catch(Exception e){
			logger.error("Exception in mapping check in date in convertRevisitTripVOtoRevisitTripDTO()"+e);
		}
	}
	
	if(null != soRevisitNeededVO.getCheckedOutDate()){
		try{
			revisitDTO.setCheckedOut(getDateStringOnZipcode(soRevisitNeededVO.getCheckedOutDate(), soRevisitNeededVO.getTimeZone(), OrderConstants.DATE_FORMAT_4) + " "+ getTimeZone(soRevisitNeededVO.getCheckedOutDate(), soRevisitNeededVO.getTimeZone()));
		}catch(Exception e){
			logger.error("Exception in mapping check out date in convertRevisitTripVOtoRevisitTripDTO()"+e);
		}
	}
	
	if(null != soRevisitNeededVO.getNextAppointmentStartDate()){
		try{
			HashMap<String, Object> nextApptStartDate = null;
			HashMap<String, Object> nextApptEndDate = null;
			nextApptStartDate = TimeUtils.convertGMTToGivenTimeZone(soRevisitNeededVO.getNextAppointmentStartDate(), soRevisitNeededVO.getNextAppointmentStartTime(), soRevisitNeededVO.getTimeZone());
			String nextApptDate=formatDate((Date) nextApptStartDate.get(OrderConstants.GMT_DATE), OrderConstants.DATE_FORMAT_3);
			if(null!=soRevisitNeededVO.getNextAppointmentEndDate()){
				nextApptEndDate = TimeUtils.convertGMTToGivenTimeZone(soRevisitNeededVO.getNextAppointmentEndDate(), soRevisitNeededVO.getNextAppointmentEndTime(), soRevisitNeededVO.getTimeZone());
				String apptEndDateString = formatDate((Date) nextApptEndDate.get(OrderConstants.GMT_DATE), OrderConstants.DATE_FORMAT_3);
				if(!nextApptDate.equals(apptEndDateString)){
					nextApptDate = nextApptDate + " - " + apptEndDateString;
				}
			}
			if(null != soRevisitNeededVO.getNextAppointmentStartTime()){
				if(null != soRevisitNeededVO.getNextAppointmentEndTime()){
					revisitDTO.setNextAppointment(nextApptDate + "&nbsp;&nbsp;"+(String)nextApptStartDate.get(OrderConstants.GMT_TIME)+" - "+(String)nextApptEndDate.get(OrderConstants.GMT_TIME)+" "+getTimeZone((Date)nextApptStartDate.get(OrderConstants.GMT_DATE), soRevisitNeededVO.getTimeZone()));	
				}
				else{
					revisitDTO.setNextAppointment(nextApptDate + "&nbsp;&nbsp;"+(String)nextApptStartDate.get(OrderConstants.GMT_TIME));
				}
			}
		}catch(Exception e){
			logger.error("Exception in mapping next appt date in convertRevisitTripVOtoRevisitTripDTO()"+e);
		}
	}
	
	if(null != soRevisitNeededVO.getRevisitReason()){
		revisitDTO.setRevisitReasonCode(soRevisitNeededVO.getRevisitReason());
	}
	
	if(null != soRevisitNeededVO.getMerchDeliveredInd()){
		revisitDTO.setMerchDeliveredInd(soRevisitNeededVO.getMerchDeliveredInd());
	}
	
	if(null != soRevisitNeededVO.getWorkStartedInd()){
		revisitDTO.setWorkStartedInd(soRevisitNeededVO.getWorkStartedInd());
	}
	
	if(null != soRevisitNeededVO.getChangeTypes()){
		List<String> tripChanges = new ArrayList<String>();
		for(String changeType : soRevisitNeededVO.getChangeTypes()){
			tripChanges.add(OrderConstants.TRIP_CHANGE_TYPE.get(changeType));
		}
		revisitDTO.setChangeTypes(tripChanges);
	}
	
	if(null != soRevisitNeededVO.getComments()){
		revisitDTO.setComments(soRevisitNeededVO.getComments());
	}
	
	if(null != soRevisitNeededVO.getDepartureReason()){
		if((OrderConstants.REVISIT_NEEDED).equals(soRevisitNeededVO.getDepartureReason())){
			revisitDTO.setDepartureReason("Revisit ");
			revisitDTO.setDepartureReasonHeader(soRevisitNeededVO.getDepartureReason());
		}else if((OrderConstants.SERVICE_COMPLETED).equals(soRevisitNeededVO.getDepartureReason())){
			revisitDTO.setDepartureReason("Completion ");
			revisitDTO.setDepartureReasonHeader(soRevisitNeededVO.getDepartureReason());
		}else{
			revisitDTO.setDepartureReason("");
			revisitDTO.setDepartureReasonHeader("");
		}
	}
	
	return revisitDTO;
}

public static String formatDate(Date inDate, String dateformat){
	String  DateToStr ="";
	try {
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		DateToStr = format.format(inDate);
	}
	catch (Exception exc) {
		logger.error("Exception in RevisitInfoAction.formatDate()"+exc);
     // Rethrow the exception.
	}
	return DateToStr;
}

public  static String getDateStringOnZipcode(Date gmtDate,String TimeZone, String format){
	
	String timezoneDate="";
	Timestamp ts = new Timestamp(gmtDate.getTime());
	String TimeinGMT= TimeUtils.getTimePartforTimeonsite(gmtDate.toString());
	
	String gmtDate1; 
	 if(ts!= null && TimeinGMT!=null && TimeZone!=null )
	 { 
	gmtDate1 = TimeUtils.convertGMTToGivenTimeZoneInString(ts,TimeinGMT,TimeZone,format);
		if (gmtDate1 !=null ) 
		  {
  			timezoneDate= gmtDate1;
			
		  }
		else
		{
			logger.error("sodetailsTimeOnsiteAction: unable to convert the date to timezone date");
		}
	 
	 }
	 
	 return timezoneDate;
}

public static String getTimeZone(Date modifiedDate, String timeZone){
    TimeZone gmtTime = TimeZone.getTimeZone(timeZone);
    if(gmtTime.inDaylightTime(modifiedDate)){
    	return "("+getDSTTimezone(timeZone)+")";
    }
    return "("+getStandardTimezone(timeZone)+")";
}

public static String getDSTTimezone(String timeZone) {
	
	if ("EST5EDT".equals(timeZone)) {
		timeZone = "EDT";
	}
	if ("AST4ADT".equals(timeZone)) {
		timeZone = "ADT";
	}
	if ("CST6CDT".equals(timeZone)) {
		timeZone = "CDT";
	}
	if ("MST7MDT".equals(timeZone)) {
		timeZone = "MDT";
	}
	if ("PST8PDT".equals(timeZone)) {
		timeZone = "PDT";
	}
	if ("HST".equals(timeZone)) {
		timeZone = "HADT";
	}
	if ("Etc/GMT+1".equals(timeZone)) {
		timeZone = "CEDT";
	}
	if ("AST".equals(timeZone)) {
		timeZone = "AKDT";
	}
	return timeZone;
}

public static String getStandardTimezone(String timeZone) {
	if ("EST5EDT".equals(timeZone)) {
		timeZone = "EST";
	}
	if ("AST4ADT".equals(timeZone)) {
		timeZone = "AST";
	}
	if ("CST6CDT".equals(timeZone)) {
		timeZone = "CST";
	}
	if ("MST7MDT".equals(timeZone)) {
		timeZone = "MST";
	}
	if ("PST8PDT".equals(timeZone)) {
		timeZone = "PST";
	}
	if ("HST".equals(timeZone)) {
		timeZone = "HAST";
	}
	if ("Etc/GMT+1".equals(timeZone)) {
		timeZone = "CET";
	}
	if ("AST".equals(timeZone)) {
		timeZone = "AKST";
	}
	if ("Etc/GMT-9".equals(timeZone)) {
		timeZone = "PST-7";
	}
	if ("MIT".equals(timeZone)) {
		timeZone = "PST-3";
	}
	if ("NST".equals(timeZone)) {
		timeZone = "PST-4";
	}
	if ("Etc/GMT-10".equals(timeZone)) {
		timeZone = "PST-6";
	}
	if ("Etc/GMT-11".equals(timeZone)) {
		timeZone = "PST-5";
	}
	return timeZone;
}

}
