package com.newco.marketplace.web.action.details;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitResultVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.TimeChangeUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.TimeOnSiteDTO;
import com.newco.marketplace.web.dto.TimeOnSiteResultDTO;
import com.newco.marketplace.web.dto.TimeOnSiteResultRowDTO;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.domain.spn.detached.LabelValueBean;

public class SODetailsTimeOnSiteAction extends SLDetailsBaseAction  implements ModelDriven<TimeOnSiteDTO>, Preparable {
	
	private static final long serialVersionUID = 10002;// arbitrary number to get rid
	
	private static final Logger logger = Logger
	.getLogger(SODetailsTimeOnSiteAction.class.getName());
	
	private TimeOnSiteDTO timeOnSiteDTO=new TimeOnSiteDTO();
	private boolean  AddEditEntry;
	private String serviceorderZipcode;
	private static final String REASON_CODES = "reasonCodes";
	private static final String ROLE_ID = "roleid";
	private static final String RESULTS_LIST = "resultsList";
	//SL-19820
	String soId;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public SODetailsTimeOnSiteAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
		
		
	}

	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception {
		
		
		createCommonServiceOrderCriteria();
		//SL-19820
		String soId = getParameter("soId");
		setAttribute(SO_ID, soId);
		this.soId = soId;
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		if(getSession().getAttribute("timeOnSiteDTO_"+soId) != null)
		{
		  setModel((TimeOnSiteDTO)getSession().getAttribute("timeOnSiteDTO_"+soId));
		  getSession().removeAttribute("timeOnSiteDTO_"+soId);
		}
		ServiceOrderDTO dto = null;
		try{
			dto = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
			setAttribute(THE_SERVICE_ORDER, dto);
		}
		catch(Exception e){
			logger.error("Error while fetching service order");
		}
		
	}
	
	
	public String execute() throws Exception {
		populateTimeOnSiteRecords();
		return SUCCESS;
	}
	
	// Method to populate time on site data to TimeOnSiteResultDTO
	public void populateTimeOnSiteRecords()throws Exception{
		ArrayList<TimeOnSiteResultDTO> results = new ArrayList<TimeOnSiteResultDTO>();
		List<LabelValueBean> reasonOptions = new ArrayList<LabelValueBean>();
		List<String> reasons = null;
		ArrayList<SOOnsiteVisitResultVO> soOnsiteVisitResults = null;
		String soId = (String) getAttribute(SO_ID);
		String timeZone = getTimeZoneForServiceOrder();
		
		if(null != soId){
			// Fetching all trip details for the service order.
			try{
				soOnsiteVisitResults = new ArrayList<SOOnsiteVisitResultVO>(detailsDelegate.getTimeOnSiteRecords(soId));
			}catch(BusinessServiceException e){
				logger.error("Exception in fetching trip details - populateTimeOnSiteRecords :"+e);
			}
			if(null != soOnsiteVisitResults && !soOnsiteVisitResults.isEmpty()){
				for(SOOnsiteVisitResultVO soOnsiteVisitResult : soOnsiteVisitResults){
					TimeOnSiteResultDTO timeOnSiteResultDTO = new TimeOnSiteResultDTO();
					StringBuffer apptDateTimeEntry = new StringBuffer();
					StringBuffer arrivalEntry = new StringBuffer();
					StringBuffer departureEntry = new StringBuffer();
					HashMap<String, Object> apptStartDate = null;
					HashMap<String, Object> apptEndDate = null;
					String startDate = "";
					timeOnSiteResultDTO.setSoId(soId);
					timeOnSiteResultDTO.setTripNum(soOnsiteVisitResult.getTripNum());
					timeOnSiteResultDTO.setVisitId(soOnsiteVisitResult.getCheckInVisitId());
					
					// Converting the appointment date in GMT to corresponding time-zone.
					if(null != soOnsiteVisitResult.getApptStartDate() && null != soOnsiteVisitResult.getApptStartTime()){
						apptStartDate = TimeUtils.convertGMTToGivenTimeZone(soOnsiteVisitResult.getApptStartDate(), soOnsiteVisitResult.getApptStartTime(), timeZone);
					}
					if(null != soOnsiteVisitResult.getApptEndDate() && null != soOnsiteVisitResult.getApptEndTime()){
						apptEndDate = TimeUtils.convertGMTToGivenTimeZone(soOnsiteVisitResult.getApptEndDate(), soOnsiteVisitResult.getApptEndTime(), timeZone);
					}
					if(null != apptStartDate){
						startDate = formatDate(OrderConstants.DATE_FORMAT_1, (Date) apptStartDate.get(OrderConstants.GMT_DATE));
						apptDateTimeEntry.append(startDate);
					}
					if(null != apptEndDate){
						String endDate = formatDate(OrderConstants.DATE_FORMAT_1, (Date) apptEndDate.get(OrderConstants.GMT_DATE));
						if(!startDate.equals(endDate)){
							apptDateTimeEntry.append(" - ");
							apptDateTimeEntry.append(endDate);
						}
					}
					if(null != apptStartDate){
						apptDateTimeEntry.append("</br>");
						apptDateTimeEntry.append((String) apptStartDate.get(OrderConstants.GMT_TIME));
					}
					if(null != apptEndDate){
						apptDateTimeEntry.append(" - ");
						apptDateTimeEntry.append((String) apptEndDate.get(OrderConstants.GMT_TIME));
					}
					if(null != apptStartDate){
						apptDateTimeEntry.append(" " + detailsDelegate.getTimeZone((Date) apptStartDate.get(OrderConstants.GMT_DATE),timeZone));
					}
					// Setting appointment date and time to apptDateTimeEntry variable.
					timeOnSiteResultDTO.setApptDateTimeEntry(apptDateTimeEntry.toString());
					String arrivalDateString=null;
					if(null != soOnsiteVisitResult.getArrivalDateTime() && null != getDateOnZipcode(soOnsiteVisitResult.getArrivalDateTime(),timeZone)){
						// Converting check-in date in GMT to given time-zone.
						arrivalDateString=getDateStringOnZipcode(soOnsiteVisitResult.getArrivalDateTime(),timeZone);
						arrivalEntry.append(arrivalDateString.substring(0, 5));
						arrivalEntry.append(" at ");
						arrivalEntry.append(arrivalDateString.substring(11, 19));
						arrivalEntry.append(" "+detailsDelegate.getTimeZone(arrivalDateString.substring(0, 19), OrderConstants.DATE_FORMAT_2, timeZone)+"</br>");
						//Contact result= (Contact) detailsDelegate.getVisitResourceName(soOnsiteVisitResult.getArrivalResourceId());
						if(OrderConstants.MOBILE.equals(soOnsiteVisitResult.getTripStartSource())){
							arrivalEntry.append(OrderConstants.MOBILE_1+"</br>");
							arrivalEntry.append(soOnsiteVisitResult.getResName());
						} else if(OrderConstants.IVR.equals(soOnsiteVisitResult.getTripStartSource())){
							arrivalEntry.append(OrderConstants.IVR+"</br>");							
							arrivalEntry.append(soOnsiteVisitResult.getResName());
						} else if(OrderConstants.WEB.equals(soOnsiteVisitResult.getTripStartSource())){
							arrivalEntry.append(OrderConstants.WEB_1+"</br>");
							arrivalEntry.append(soOnsiteVisitResult.getResName());
						}
					}
					// Setting check in date and time to arrivalEntry variable.
					timeOnSiteResultDTO.setArrivalEntry(arrivalEntry.toString());
					timeOnSiteResultDTO.setArrivalDate(arrivalDateString);
					// Setting indicator to show 'add departure' button when check-in is present while check-out is not.
					if(null == soOnsiteVisitResult.getDepartureDateTime() && null != soOnsiteVisitResult.getArrivalDateTime()){
						timeOnSiteResultDTO.setAddDepartureInd(true);
					}  
					if(null != soOnsiteVisitResult.getDepartureDateTime() && null != getDateOnZipcode(soOnsiteVisitResult.getDepartureDateTime(),timeZone)){
						// Converting check-out date in GMT to given time-zone.
						String departureDate=getDateStringOnZipcode(soOnsiteVisitResult.getDepartureDateTime(),timeZone);
						departureEntry.append(departureDate.substring(0, 5));
						departureEntry.append(" at ");
						departureEntry.append(departureDate.substring(11, 19));
						departureEntry.append(" "+detailsDelegate.getTimeZone(departureDate.substring(0, 19), OrderConstants.DATE_FORMAT_2, timeZone)+"</br>");
						//Contact result= (Contact) detailsDelegate.getVisitResourceName(soOnsiteVisitResult.getArrivalResourceId());
						if(OrderConstants.MOBILE.equals(soOnsiteVisitResult.getTripEndSource())){
							departureEntry.append(OrderConstants.MOBILE_1+"</br>");
						} else if(OrderConstants.IVR.equals(soOnsiteVisitResult.getTripEndSource())){
							departureEntry.append(OrderConstants.IVR+"</br>");							
							departureEntry.append(soOnsiteVisitResult.getResName());
						} else if(OrderConstants.WEB.equals(soOnsiteVisitResult.getTripEndSource())){
							departureEntry.append(OrderConstants.WEB_1+"</br>");
							departureEntry.append(soOnsiteVisitResult.getResName());
						}
					}
					// Setting check out date and time to departureEntry variable.
					timeOnSiteResultDTO.setDepartureEntry(departureEntry.toString());
					if(OrderConstants.MOBILE.equals(soOnsiteVisitResult.getTripEndSource()) && null != soOnsiteVisitResult.getTripNum()){
						// Trip information is not required to be shown in a pop-up for Mobile - Cancellation Requested
						if(null != soOnsiteVisitResult.getDepartureReason()){
							if((OrderConstants.CANCELLATION_REQUESTED).equals(soOnsiteVisitResult.getDepartureReason())){
								departureEntry.append(soOnsiteVisitResult.getDepartureReason());
								timeOnSiteResultDTO.setDepartureEntry(departureEntry.toString());
								timeOnSiteResultDTO.setDepartureReason("");
							}else{
								// For Revisit Needed and Service Completed,trip information should be displayed in a pop-up
								// The value set in departureReason will be displayed as a hyper-link for pop-up. 
								timeOnSiteResultDTO.setDepartureReason(soOnsiteVisitResult.getDepartureReason());
							}
						}else{
							departureEntry.append(soOnsiteVisitResult.getResName());
							timeOnSiteResultDTO.setDepartureEntry(departureEntry.toString());
							timeOnSiteResultDTO.setDepartureReason("");
						}
					}else if(OrderConstants.MOBILE.equals(soOnsiteVisitResult.getTripEndSource()) && null == soOnsiteVisitResult.getTripNum()){
						departureEntry.append(soOnsiteVisitResult.getResName());
						timeOnSiteResultDTO.setDepartureEntry(departureEntry.toString());
						timeOnSiteResultDTO.setDepartureReason("");
					}else{
						timeOnSiteResultDTO.setDepartureReason("");
					}
					results.add(timeOnSiteResultDTO);
				}
			}
		}
		try{
			reasons = detailsDelegate.fetchTimeOnSiteReasons();
		} catch(BusinessServiceException e){
			logger.error("Exception in fetching reason codes for time on site"+e);
		}
		/*reasonOptions.add(new LabelValueBean("IVR not working", "1"));
		reasonOptions.add(new LabelValueBean("Mobile App not working", "2"));
		reasonOptions.add(new LabelValueBean("Forgot to call IVR", "3"));*/
		setAttribute(REASON_CODES,reasons);
		setAttribute(ROLE_ID,_commonCriteria.getRoleId());
		setAttribute(RESULTS_LIST, results);
	}
	
	public void populatewithrecords()throws Exception
	{
		
		ArrayList<TimeOnSiteResultRowDTO> results = new ArrayList<TimeOnSiteResultRowDTO>();
		ArrayList<SOOnsiteVisitVO> Onsiterecords;
		//SL-19820
		//String soId = getCurrentServiceOrderId();
		String soId = (String) getAttribute(SO_ID);
		String TimeZone = getTimeZoneForServiceOrder();
		//SL-19820
	    //Integer status=(Integer) getSession().getAttribute(THE_SERVICE_ORDER_STATUS_CODE);
		Integer status = getCurrentServiceOrderFromRequest().getStatus();
		
		if(soId != null)
		{
	    	Onsiterecords= new ArrayList<SOOnsiteVisitVO>(detailsDelegate.getTimeOnSiteResults(soId));
	    	//SL-19820
	    	//getSession().setAttribute("resultsList",Onsiterecords);
	    	getSession().setAttribute("resultsList_"+soId,Onsiterecords);
	    	setAttribute("resultsList",Onsiterecords);
	    	String SystemIVR="System/IVR";
		
		    if(Onsiterecords != null)
		    {	
		
		             for(SOOnsiteVisitVO SOOnsiteVisitRecord :Onsiterecords)
		                {
		
		            	     TimeOnSiteResultRowDTO timeOnSiteResult = new TimeOnSiteResultRowDTO();		            	            	     
		            	     
		            	      if(SOOnsiteVisitRecord.getVisitId()!=null)
		            	      timeOnSiteResult.setVisitId(SOOnsiteVisitRecord.getVisitId());
		            	      if(SOOnsiteVisitRecord.getSoId() != null)
		            	    	 timeOnSiteResult.setSoId(SOOnsiteVisitRecord.getSoId());	
		            	      
		            	  
		            	       if(SOOnsiteVisitRecord.getArrivalDate() !=null)
		            	       {
		            	             
		            	    	     
		            	    	       if( getDateOnZipcode(SOOnsiteVisitRecord.getArrivalDate(),TimeZone) != null)
		            	    	       {
		            	    	    	   String arrivalDate=getDateStringOnZipcode(SOOnsiteVisitRecord.getArrivalDate(),TimeZone);
		            	    	    	   timeOnSiteResult.setArrivalDate(arrivalDate.substring(0, 10));
		            	    	    	   timeOnSiteResult.setArrivalTime(arrivalDate.substring(11));		            	    	    	
				            	           timeOnSiteResult.setArrivalDateCal(getDateOnZipcode(SOOnsiteVisitRecord.getArrivalDate(),TimeZone));
				            	           
		            	    	    	   
		            	    	       } 
		            	    	      
		            	       }
		            	       if(SOOnsiteVisitRecord.getDepartureDate() != null )
		            	       {
		            	    	   logger.info("Departure date" + SOOnsiteVisitRecord.getDepartureDate());
		            	    	   
		            	    	   if( getDateOnZipcode(SOOnsiteVisitRecord.getDepartureDate(),TimeZone) != null)
	            	    	       {
		            	    		   String departureDate=getDateStringOnZipcode(SOOnsiteVisitRecord.getDepartureDate(),TimeZone);
		            	    		   timeOnSiteResult.setDepartureDate(departureDate.substring(0, 10));
		            	    		   timeOnSiteResult.setDepartureTime(departureDate.substring(11));	            	    	    	 
			            	           timeOnSiteResult.setDepartureDateCal(getDateOnZipcode(SOOnsiteVisitRecord.getDepartureDate(),TimeZone));
	            	    	    	   
	            	    	       } 
	            	    	       
		            	         
		            	       }	
		            	          
		            	        if(SOOnsiteVisitRecord.getArrivalInputMethod()!= null && SOOnsiteVisitRecord.getArrivalInputMethod()==1)
		            	 		 timeOnSiteResult.setArrivalEnteredType(SystemIVR);
		            	 	    else 
		            	 	    {
		            	 	      if(SOOnsiteVisitRecord.getArrivalInputMethod()!= null)
		            	 	      {
		            	 	    	 //SL-20071
		            	 	    	 Integer resId = (null != SOOnsiteVisitRecord.getResourceId() && 0 != SOOnsiteVisitRecord.getResourceId())?
		            	 	    	 					SOOnsiteVisitRecord.getResourceId():SOOnsiteVisitRecord.getArrivalInputMethod();
		            	 	    	 Contact result= (Contact) detailsDelegate.getVisitResourceName(resId);
		            	 	    	 timeOnSiteResult.setArrivalEnteredType(result.getLastName() +" "+ result.getFirstName());
		            	 	      }
		            	 		
		            	 	    }  
		            	 	   if(SOOnsiteVisitRecord.getDepartureInputMethod()!= null && SOOnsiteVisitRecord.getDepartureInputMethod()==1)
		            	 		timeOnSiteResult.setDepartureEnteredType(SystemIVR);
		            	 	   else 
		            	 	   {
		            	 		  if(SOOnsiteVisitRecord.getDepartureInputMethod()!= null)
		            	 	      {
		            	 			  //SL-20071
		            	 			  Integer depResId = (null != SOOnsiteVisitRecord.getDepartureResourceId() && 0 != SOOnsiteVisitRecord.getDepartureResourceId())?
		            	 					  				SOOnsiteVisitRecord.getDepartureResourceId():SOOnsiteVisitRecord.getDepartureInputMethod();
		            	 	    	 Contact result= (Contact) detailsDelegate.getVisitResourceName(depResId);
		            	 	    	 timeOnSiteResult.setDepartureEnteredType(result.getLastName()+" " + result.getFirstName());
		            	 	      }  
		            	 		
		            	 		
		            	 	   } 
		            	 	   
		            	 	
		            	 	 if(status != null && status.intValue() == OrderConstants.COMPLETED_STATUS)
		            	 	 {
		            	 		timeOnSiteResult.setRoleId(OrderConstants.TIME_ON_SITE_COMPLETED_STATUS); 
		            	 	 }
		            	 	 else
		            	 	 {	 
		            	 	  timeOnSiteResult.setRoleId(_commonCriteria.getRoleId());
		            	 	 }
			                   
		            	 	results.add(timeOnSiteResult);
		                }
		
		           }
		
		}
		
		 if(status != null && status.intValue() == OrderConstants.COMPLETED_STATUS)
	 	 {
	 		
	 		setAttribute("roleid", OrderConstants.TIME_ON_SITE_COMPLETED_STATUS);
	 	 }
	 	 else
	 	 {	
	 	  setAttribute("roleid",_commonCriteria.getRoleId());	 
	 	 
	 	 }
		 
		    setAttribute("resultsList", results);
		   
		
	}


public String getTimeZoneForServiceOrder()
{
	String TimeZone=null;
	Integer EndIndex;
	Integer StartIndex;
	
	try{
		//SL-19820
		ServiceOrderDTO dto = getCurrentServiceOrderFromRequest();
	
		if(dto != null){
			int lenghtOfCityStateZip = dto.getLocationContact().getCityStateZip().length();
			//If there is zip4, set the start and end index to get the zip
			if (dto.getLocationContact().getCityStateZip().charAt(lenghtOfCityStateZip - 5) == '-') {
				EndIndex = dto.getLocationContact().getCityStateZip().length() - 5;
				StartIndex= EndIndex - 5;
			}
			//No zip4, set the start and end index to get the zip
			else{
				EndIndex= lenghtOfCityStateZip;
				StartIndex= EndIndex-5;
			}
			if(dto.getLocationContact().getCityStateZip().substring(StartIndex, EndIndex)!= null){
				setServiceorderZipcode(dto.getLocationContact().getCityStateZip().substring(StartIndex, EndIndex));
				TimeZone= getTimeZone (getServiceorderZipcode());
			}
		}
	}catch(Exception e){
		logger.error("Exception while trying to fetch SO Details");
	}
	return TimeZone;
	
}


public  static Date getDateOnZipcode(Date gmtDate,String TimeZone){
		
		Date timezoneDate= new Date();
		Timestamp ts = new Timestamp(gmtDate.getTime());
		String TimeinGMT= TimeUtils.getTimePartforTimeonsite(gmtDate.toString());
		
		Date gmtDate1; 
		 if(ts!= null && TimeinGMT!=null && TimeZone!=null )
		 { 
		gmtDate1 = TimeUtils.convertGMTToGivenTimeZoneforTimeonsite(ts,TimeinGMT,TimeZone);
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
/* method to get the Date as String based on the Zip code*/
public  static String getDateStringOnZipcode(Date gmtDate,String TimeZone){
	
	String timezoneDate="";
	Timestamp ts = new Timestamp(gmtDate.getTime());
	String TimeinGMT= TimeUtils.getTimePartforTimeonsite(gmtDate.toString());
	
	String gmtDate1; 
	 if(ts!= null && TimeinGMT!=null && TimeZone!=null )
	 { 
	gmtDate1 = TimeUtils.convertGMTToGivenTimeZoneInString(ts,TimeinGMT,TimeZone);
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

	
	public static Date getDateinGMT(Date timeZoneDate,String TimeZone){
		Timestamp ts = new Timestamp( timeZoneDate.getTime());
		Date gmtDate= TimeUtils.convertTimeToGMT(ts,TimeZone);
		return gmtDate;
	}
	
	public static Date getDateInServiceLocationTimeZone(Date timeZoneDate,String fromTimeZone, String toTimeZone){
		Timestamp ts = new Timestamp( timeZoneDate.getTime());
		Date gmtDate= TimeUtils.convertTimeFromOneTimeZoneToOther(ts,fromTimeZone,toTimeZone);
		return gmtDate;
	}
	public String deletentry() throws Exception
	{
		
		
		// need to update so that the row values doesnt get overrided
		
		populatewithrecords();
		SOOnsiteVisitVO soOnsiteVisitVO= new SOOnsiteVisitVO();
		soOnsiteVisitVO.setVisitId(timeOnSiteDTO.getVisitId());
		soOnsiteVisitVO.setSoId(timeOnSiteDTO.getSoId());
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar cal = Calendar.getInstance();
		format.format(cal.getTime());
		System.out.println(cal.getTime());
	
		Date modifiedDate = cal.getTime();
		soOnsiteVisitVO.setModifiedDate(modifiedDate);
		soOnsiteVisitVO.setDeleteInd(1);
		detailsDelegate.UpdateTimeOnSiteRow(soOnsiteVisitVO);
		
		setDefaultTab(SODetailsUtils.ID_TIME_ON_SITE);		
		return GOTO_COMMON_DETAILS_CONTROLLER;
	}
	
	
	public String saveinsert() throws Exception
	{
		//SL-19820
		String soId = (String) getAttribute(SO_ID);
		
		//Getting the current SO details
		ServiceOrderDTO serviceOrderDTO = (ServiceOrderDTO)getAttribute(THE_SERVICE_ORDER);
		
		
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar cal = Calendar.getInstance();
		String timeZone = getTimeZoneForServiceOrder();
		timeOnSiteDTO = getModel();
		timeOnSiteDTO.setTimeZone(timeZone);
		timeOnSiteDTO.validate();
		validateOnsiteDatesWithSODates(timeZone);
		//Sl-19820
		//checkIfProviderAssigned(getCurrentServiceOrderId());
		checkIfProviderAssigned(soId);
		if(timeOnSiteDTO.getErrors().size() > 0) {
			timeOnSiteDTO.setAddEditEntryPanel(true);
			timeOnSiteDTO.setAddMode(true);
			//SL-19820
			getSession().setAttribute("timeOnSiteDTO_"+soId, timeOnSiteDTO);
			getSession().setAttribute("timeOnSiteDTO", timeOnSiteDTO);
		} else {
			SOOnsiteVisitVO soOnsiteVisitVO = new SOOnsiteVisitVO();
			//SL-19820
			/*if(getCurrentServiceOrderId() != null){
				soOnsiteVisitVO.setSoId(getCurrentServiceOrderId());
			}*/
			if(soId != null){
				soOnsiteVisitVO.setSoId(soId);
			}
			soOnsiteVisitVO.setResourceId(_commonCriteria.getVendBuyerResId());
			Date createdDate = cal.getTime();
			soOnsiteVisitVO.setCreatedDate(createdDate);
			soOnsiteVisitVO.setModifiedDate(createdDate);
			soOnsiteVisitVO.setDeleteInd(0);
			soOnsiteVisitVO.setBuyerId(serviceOrderDTO.getBuyerID());
			Integer tripNo = null;
			Long visitId=null;
			
			if(timeOnSiteDTO.getArrivalTimestamp()!= null && timeZone!= null ){
				if((OrderConstants.WEB_ARRIVAL).equals(timeOnSiteDTO.getArrivalDeparture())){
					logger.info("SODetailsTimeOnSiteAction saveinsert() arrival date prior to GMT conversion :"+timeOnSiteDTO.getArrivalTimestamp());
					if(getDateinGMT(timeOnSiteDTO.getArrivalTimestamp(),timeZone)!=null )
						soOnsiteVisitVO.setArrivalDate(getDateinGMT(timeOnSiteDTO.getArrivalTimestamp(),timeZone));
					if(_commonCriteria.getVendBuyerResId()!= null)
						soOnsiteVisitVO.setArrivalInputMethod(_commonCriteria.getVendBuyerResId());
					logger.info("SODetailsTimeOnSiteAction saveinsert() arrival date after GMT conversion :"+soOnsiteVisitVO.getArrivalDate());
					soOnsiteVisitVO.setArrivalReason(timeOnSiteDTO.getTimeOnSiteReason());
					detailsDelegate.InsertVisitResult(soOnsiteVisitVO);
					
					//Get the latest visit Id from onsite visit table
					visitId=detailsDelegate.findLatestOnsiteVisitEntry(soId);
				} else if((OrderConstants.WEB_DEPARTURE).equals(timeOnSiteDTO.getArrivalDeparture())){	
					
					soOnsiteVisitVO.setVisitId(timeOnSiteDTO.getVisitId());
					soOnsiteVisitVO.setDepartureResourceId(_commonCriteria.getVendBuyerResId());
					if(getDateinGMT(timeOnSiteDTO.getDepartureTimestamp(),timeZone)!=null )
						soOnsiteVisitVO.setDepartureDate(getDateinGMT(timeOnSiteDTO.getDepartureTimestamp(),timeZone));
					if(_commonCriteria.getVendBuyerResId() != null)
						soOnsiteVisitVO.setDepartureInputMethod(_commonCriteria.getVendBuyerResId());
					soOnsiteVisitVO.setDepartureReason(timeOnSiteDTO.getTimeOnSiteReason());
					detailsDelegate.UpdateTimeOnSiteRow(soOnsiteVisitVO);
					
					//R12_0 If substatus is 'Provider On-site' or 'Work Started' make the substatus empty
					if(StringUtils.isNotBlank(soId) && null!= serviceOrderDTO && null!= serviceOrderDTO.getSubStatus() 
							&& ((serviceOrderDTO.getSubStatus().intValue() == OrderConstants.PROVIDER_ON_SITE_SUBSTATUS_ID)||(serviceOrderDTO.getSubStatus().intValue() == OrderConstants.WORK_STARTED_SUBSTATUS_ID))){
						//update the substatus to empty
						Integer substatusValue = null;
						detailsDelegate.updateSubStatusOfSO(soId,substatusValue);
					}
					
					//Get the latest visit Id from onsite visit table
					visitId=timeOnSiteDTO.getVisitId(); 
					tripNo = timeOnSiteDTO.getTripNumber();
				}
			}
			//R12_0: Insert an entry into so_trip in case of arrival
			createOrUpdateTrip(serviceOrderDTO,visitId,timeOnSiteDTO.getArrivalDeparture(), tripNo, _commonCriteria.getVendBuyerResId());
		}
		setDefaultTab(SODetailsUtils.ID_TIME_ON_SITE);		
		return GOTO_COMMON_DETAILS_CONTROLLER;	
	}
	
	private void checkIfProviderAssigned(String soId)throws BusinessServiceException{
		
		logger.info("SO_ASSIGNMENT_TYPE_CHECK:SOID"+soId);
		String  assignmentType = getDetailsDelegate().getAssignmentType(soId);
		logger.info("SO_ASSIGNMENT_TYPE_CHECK1:"+assignmentType);
		if(null != assignmentType && assignmentType.equals(OrderConstants.SO_ASSIGNMENT_TYPE_FIRM)){
			SOWError err = new SOWError(timeOnSiteDTO.getTheResourceBundle().getString("Assign_Provider"),
					timeOnSiteDTO.getTheResourceBundle().getString("Assign_Provider_Validation"), OrderConstants.SOD_TAB_ERROR);
			timeOnSiteDTO.getErrors().add(err);
		}
	}
	
	private void validateOnsiteDatesWithSODates(String toTimeZone) {
		if(timeOnSiteDTO.getErrors().isEmpty()) { // Onsite Dates are correctly entered
			//SL-19820
			ServiceOrderDTO so = getCurrentServiceOrderFromRequest();
			// so.getAcceptedDate() should never be null at this stage; so no need to check for null here
			// R12.0 SL-20403 fix, this fix is made in the assumption that accepted_date coming from so_hdr is always in CST timezone, as existing
			Date acceptedDate = so.getAcceptedDate();
			String fromTimeZone= Calendar.getInstance().getTimeZone().getID();//this is always CST
			//get the acceptedDate in Service location time zone, currently which is always in CST
			Date acceptedDateInDesiredTimeZone = getDateInServiceLocationTimeZone(acceptedDate,fromTimeZone, toTimeZone);
			
			//compare arrival date (in service location TZ) against accepted date (in service location TZ)
			if (!timeOnSiteDTO.getArrivalTimestamp().after(acceptedDateInDesiredTimeZone)) {
				SOWError err = new SOWError(timeOnSiteDTO.getTheResourceBundle().getString("Arrival_Time"),
						timeOnSiteDTO.getTheResourceBundle().getString("Arrival_time_Accepted_time_validation"), OrderConstants.SOD_TAB_ERROR);
				timeOnSiteDTO.getErrors().add(err);
			}
			
			// Extra validations for Time On Site Arrival Date versus SO Service Dates:
			// can be done here later; after business rules are defined/documented and confirmed by business owners/analysts
			// These fields can be used then so.getServiceDate1(), so.getServiceDate2(), so.getServiceTimeStart() and so.getServiceTimeEnd()
		}
	}
	
	public String saveupdate() throws Exception
	{	
		//SL-19820
		String soId = (String)getAttribute(SO_ID);
		//TODO:Not sure of this code
		//SL-19820
		/*if(timeOnSiteDTO.getVisitId()!= null){                          
            getSession().setAttribute("visitIdEdit",timeOnSiteDTO.getVisitId());                              
		}else{                           
            if(getSession().getAttribute("visitIdEdit")!=null){
                        String visitIdEdit= getSession().getAttribute("visitIdEdit").toString();
                        Long vIdEdit=Long.parseLong(visitIdEdit);
                        timeOnSiteDTO.setVisitId(vIdEdit);                                          
            }
		}*/
		
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar cal = Calendar.getInstance();
		String TimeZone=getTimeZoneForServiceOrder();
		timeOnSiteDTO = getModel();
		//SL-19820
		if(timeOnSiteDTO.getVisitId()== null && StringUtils.isNotBlank(getParameter("visitId"))){
			Long vIdEdit=Long.parseLong(getParameter("visitId"));
			timeOnSiteDTO.setVisitId(vIdEdit);
		}
		timeOnSiteDTO.validate();
		validateOnsiteDatesWithSODates(TimeZone);
		logger.info("TimeOnsite SOID:"+timeOnSiteDTO.getSoId());
		//SL-19820
		//checkIfProviderAssigned(getCurrentServiceOrderId());
		checkIfProviderAssigned(soId);
		if(timeOnSiteDTO.getErrors().size() > 0) {	
			timeOnSiteDTO.setAddEditEntryPanel(true);
			timeOnSiteDTO.setAddMode(false);
			//SL-19820
			getSession().setAttribute("timeOnSiteDTO_"+soId, timeOnSiteDTO);
			getSession().setAttribute("timeOnSiteDTO", timeOnSiteDTO);
			setModel(timeOnSiteDTO);
	    } else {
			timeOnSiteDTO.setAddEditEntryPanel(true);
			SOOnsiteVisitVO soOnsiteVisitVO= new SOOnsiteVisitVO();	
			SOOnsiteVisitVO soOnsiteInitValue =new SOOnsiteVisitVO();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String initArrivalDate="";
			String initDepartureDate="";
			String editedArrivalDate="";
			String editedDepartureDate="";
			//Sl-19820
		    //ArrayList<SOOnsiteVisitVO>  results=  (ArrayList<SOOnsiteVisitVO>) getSession().getAttribute("resultsList");
		    ArrayList<SOOnsiteVisitVO>  results =  (ArrayList<SOOnsiteVisitVO>) getSession().getAttribute("resultsList_"+soId);
	  	    for(SOOnsiteVisitVO tosInitValues :results) {
			    if(tosInitValues.getVisitId().equals(timeOnSiteDTO.getVisitId()))				  
			    {
				  soOnsiteInitValue = tosInitValues;	
				  if(soOnsiteInitValue.getArrivalDate()!=null)
				  {
					  initArrivalDate= dateFormat.format(soOnsiteInitValue.getArrivalDate());
				  }
				  if(soOnsiteInitValue.getDepartureDate()!=null)
				  {
					  initDepartureDate=dateFormat.format(soOnsiteInitValue.getDepartureDate());			
				  }			    
			   }
		    }	
		    if(timeOnSiteDTO.getArrivalTimestamp()!=null)
		    { 
		      if(getDateinGMT(timeOnSiteDTO.getArrivalTimestamp(),TimeZone)!=null )
		      {	
		    	  editedArrivalDate  =dateFormat.format(getDateinGMT(timeOnSiteDTO.getArrivalTimestamp(),TimeZone));
		      }
		    }
		    if(timeOnSiteDTO.getDepartureTimestamp()!=null)
		    {  
		      if(getDateinGMT(timeOnSiteDTO.getDepartureTimestamp(),TimeZone)!=null )
		      {
			    editedDepartureDate=dateFormat.format(getDateinGMT(timeOnSiteDTO.getDepartureTimestamp(),TimeZone));
		      }
		    }
			soOnsiteVisitVO.setVisitId(timeOnSiteDTO.getVisitId());
			//Sl-19820
			//soOnsiteVisitVO.setSoId(getCurrentServiceOrderId());
			soOnsiteVisitVO.setSoId(soId);
			if(getDateinGMT(timeOnSiteDTO.getArrivalTimestamp(),TimeZone)!=null )
			soOnsiteVisitVO.setArrivalDate(getDateinGMT(timeOnSiteDTO.getArrivalTimestamp(),TimeZone));
			if(_commonCriteria.getVendBuyerResId()!= null)
			{   
				if(!initArrivalDate.equals(editedArrivalDate))
				{    
			        soOnsiteVisitVO.setArrivalInputMethod(_commonCriteria.getVendBuyerResId());
				}
				else{
					soOnsiteVisitVO.setArrivalInputMethod(soOnsiteInitValue.getArrivalInputMethod());
				}
			}
			if(timeOnSiteDTO.getDepartureTimestamp() != null)
			{  	
			  if(getDateinGMT(timeOnSiteDTO.getDepartureTimestamp(),TimeZone)!=null )
			  soOnsiteVisitVO.setDepartureDate(getDateinGMT(timeOnSiteDTO.getDepartureTimestamp(),TimeZone));

			  if(_commonCriteria.getVendBuyerResId()  != null)
			  {       
				  if(!initDepartureDate.equals(editedDepartureDate))
				  {       
			          soOnsiteVisitVO.setDepartureInputMethod(_commonCriteria.getVendBuyerResId());
				  }
				  else
				  {      
					  soOnsiteVisitVO.setDepartureInputMethod(soOnsiteInitValue.getDepartureInputMethod());
				  }
			  }
				 
			}  
			
			Date createdDate = cal.getTime();
			soOnsiteVisitVO.setCreatedDate(createdDate);

			if(!initArrivalDate.equals(editedArrivalDate)||!initDepartureDate.equals(editedDepartureDate))
			{  
		      detailsDelegate.UpdateTimeOnSiteRow(soOnsiteVisitVO);
			}
		}
		setTimeOnSiteDTO(timeOnSiteDTO);
		setDefaultTab(SODetailsUtils.ID_TIME_ON_SITE);		
		return GOTO_COMMON_DETAILS_CONTROLLER;		
	
	}
	
	private String formatDate(String format, Date date){
		DateFormat formatter = new SimpleDateFormat(format);
		String formattedDate = null;
		try {
			formattedDate = formatter.format(date);
		} catch (Exception e) {
			logger.error("exception in formatDate()"+ e);
		}
		return formattedDate;
	}	

	public String cancel() throws Exception
	{
		setDefaultTab(SODetailsUtils.ID_TIME_ON_SITE);		
		return GOTO_COMMON_DETAILS_CONTROLLER;	
	}

	public TimeOnSiteDTO getModel() {
		return timeOnSiteDTO;
	}


	public  void setModel(TimeOnSiteDTO  timeOnSiteDTO){
		this.timeOnSiteDTO=timeOnSiteDTO;
		
	}
  	
	public TimeOnSiteDTO getTimeOnSiteDTO() {
		return timeOnSiteDTO;
	}

	public void setTimeOnSiteDTO(TimeOnSiteDTO timeOnSiteDTO) {
		this.timeOnSiteDTO = timeOnSiteDTO;
	}

	

	public String getServiceorderZipcode() {
		return serviceorderZipcode;
	}

	public void setServiceorderZipcode(String serviceorderZipcode) {
		this.serviceorderZipcode = serviceorderZipcode;
	}

	public boolean isAddEditEntry() {
		return AddEditEntry;
	}

	public void setAddEditEntry(boolean addEditEntry) {
		AddEditEntry = addEditEntry;
	}
	
	
	/**
	 * Method create/update the trip
	 * 
	 * Trip logic as below
	 * Check In from web
	 		1. if the latest trip is an open trip without check in entry(Revisit Needed trip), update the trip with check in entry
	 		2. else Create New Trip with tripNo=currentTripNo+1 with only arrival entries and trip status OPEN
	 * Check Out from web
	 		update the existing trip's departure entries and trip status ENDED.
	 */
	private void createOrUpdateTrip(ServiceOrderDTO so,
			Long visitId, String mode, Integer tripNo, Integer resourceId) throws BusinessServiceException {
		SOTripVO trip = new SOTripVO();
		mapSOTrip(so,visitId, mode, trip, tripNo, resourceId);
		// Insert to so_onsite_visit flow on Arrival and departure only entry
		// while departure
		if (OrderConstants.WEB_ARRIVAL.equals(mode)) {
			//for trip without checkin and checkout, update the check in.
			if(isRevisitNeededTrip(soId,trip.getTripNo())){
				detailsDelegate.updateTripWeb(trip);
			}else{
				trip.setTripNo(trip.getTripNo()+1);
				detailsDelegate.insertNewTripWeb(trip);
			}
			
		} else if (OrderConstants.WEB_DEPARTURE.equals(mode) && null != tripNo) {

			// latest trip status is open, update the trip status to ENDED
			detailsDelegate.updateTripWeb(trip);

		}
	}
	
	
	/**
	 * Create the SOTripVO object
	 * @param so
	 * @param event
	 * @param visitId
	 * @param mode
	 * @param trip
	 * @return SOTripVO
	 * @throws BusinessServiceException
	 */
	private SOTripVO mapSOTrip(ServiceOrderDTO so,Long visitId,String mode, SOTripVO trip, Integer tripNo, Integer resourceId) throws BusinessServiceException{
		
		Integer latestTrip = null;
		String userName = null;
		//common Trip attributes --START
		if(OrderConstants.WEB_ARRIVAL.equals(mode)){
			latestTrip = detailsDelegate.fetchLatestTripSO(soId);
			if(null == latestTrip){
				trip.setTripNo(0);
			}else{
				trip.setTripNo(latestTrip.intValue());
			}
		}else if(OrderConstants.WEB_DEPARTURE.equals(mode)){
			if(null != tripNo){
				trip.setTripNo(tripNo.intValue());
			}
		}
		trip.setSoId(so.getId());
		if(null != resourceId){
			userName = detailsDelegate.fetchUserName(resourceId.toString());
		}
		trip.setCurrentApptStartDate(so.getServiceDateGMT1());
		trip.setCurrentApptEndDate(so.getServiceDateGMT2());
		trip.setCurrentApptStartTime(so.getServiceTimeStartGMT());
		trip.setCurrentApptEndTime(so.getServiceTimeEndGMT());
		
		trip.setCreatedDate(new Date());
		trip.setModifiedDate(new Date());
		trip.setCreatedBy(userName);
		trip.setModifiedBy(userName);
		//common Trip attributes --END
		
		//Create new trip on arrival --START
		if(OrderConstants.WEB_ARRIVAL.equals(mode)){
			trip.setTripStatus(OrderConstants.TRIP_STATUS_OPEN);
			trip.setTripStartVisitId(visitId);//TODO visit ID
			trip.setTripStartSource(OrderConstants.WEB);////Source = WEB
		}
		//Create new trip on arrival --END
		
		//Create or update trip on departure with only departure entry OR Update departure --START
		else if(OrderConstants.WEB_DEPARTURE.equals(mode) ){
			trip.setTripStatus(OrderConstants.TRIP_STATUS_CLOSED);
			trip.setTripEndVisitId(visitId);//TODO visit ID
			trip.setTripEndSource(OrderConstants.WEB);//Source = WEB
		}
		//Create or update trip on departure with only departure entry OR Update departure --END
		return trip;
	}
	
	private boolean isRevisitNeededTrip(String soId, Integer currentTripNo) throws BusinessServiceException{
		if(0== currentTripNo){
			return false;
		}
		return detailsDelegate.isRevisitNeededTrip(soId,currentTripNo);
	}
}