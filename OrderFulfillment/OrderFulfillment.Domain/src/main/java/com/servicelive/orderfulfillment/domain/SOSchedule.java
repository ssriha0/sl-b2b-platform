package com.servicelive.orderfulfillment.domain;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.domain.type.SOScheduleType;


@Embeddable()
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class SOSchedule extends SOScheduleDate implements Comparable<SOSchedule> {

	private static final Logger logger = Logger.getLogger(SOSchedule.class);
	private static final long serialVersionUID = 1L;
		
	@Column(name = "service_date_type_id")
	private Integer serviceDateTypeId;

	@Transient
	private String reason;
	
	//SL-21230 : Transient variable to hold comments for reschedule
	@Transient
	private String comments="";
		
	@Transient
	private boolean isCreatedViaAPI;

	public SOScheduleType getServiceDateTypeId() {
		if(serviceDateTypeId == null) return null;
	    return SOScheduleType.fromId(serviceDateTypeId);
	}

	public int compareTo(SOSchedule o){
		return new CompareToBuilder().appendSuper(super.compareTo(o)).append(serviceDateTypeId, o.serviceDateTypeId).toComparison();
	}
	
	public boolean equals(Object aThat){
	    if ( this == aThat ) return true;
	    if ( !(aThat instanceof SOSchedule) ) return false;
	
	    SOSchedule o = (SOSchedule)aThat;
	    return new EqualsBuilder().appendSuper(super.equals(aThat))
					.append(serviceDateTypeId, o.serviceDateTypeId).isEquals();
	}
	
	public int hashCode(){
		return new HashCodeBuilder().appendSuper(super.hashCode())
						.append(serviceDateTypeId).toHashCode();
	}

	public boolean isRequestedServiceTimeTypeDateRange() {
		return SOScheduleType.DATERANGE.getId().equals(serviceDateTypeId);
	}

	public boolean isRequestedServiceTimeTypeDatePreference() {
		return SOScheduleType.PREFERENCE.getId().equals(serviceDateTypeId);
	}

	
	public boolean isRequestedServiceTimeTypeSingleDay() {
		return SOScheduleType.SINGLEDAY.getId().equals(serviceDateTypeId);
	}

	public void setServiceDateTypeId(Integer serviceDateTypeId) {
		this.serviceDateTypeId = serviceDateTypeId;
	}

	public void setServiceDateTypeId(SOScheduleType type) {
		this.serviceDateTypeId = type.getId();
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public List<String> validate(){
	    List<String> returnVal = new ArrayList<String>();
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a");
        java.util.Date today = new java.util.Date();
		java.sql.Date now = new java.sql.Date(today.getTime());
        /*
         * From PublicAPI we get the end time for the given day too. From MFE it is null!
         */
        if(isRequestedServiceTimeTypeSingleDay()){
            if(StringUtils.isNotBlank(getServiceTimeEnd())){
                try{
                	
                    Date start = sdfTime.parse(getServiceTimeStart());
                    Date end = sdfTime.parse(getServiceTimeEnd());
                    if(start.after(end)){
                        returnVal.add("Service can not end before it has even started. Make sure the start and end time values are specified correctly.");
                    }
                }catch(ParseException pe){
                    returnVal.add("Could not parse start/end time for the given day. Make sure the time values are specified correctly.");
                }
            }
        }else if(isRequestedServiceTimeTypeDateRange()){
        	if(getServiceDate1().after(getServiceDate2())){
        		returnVal.add("Start date of Service can not be after the completion/end date.");
        	}   
        }else if(isRequestedServiceTimeTypeDatePreference()){
        	  logger.info("Schedule Type::: PREFERENCE");
        }else{
            returnVal.add("Could not determine schedule type! Please specify whether it is Range based or on a single day?");
        }
        return returnVal;
	}
	
	public List<String> validateSheduleForAPI(String timeZone){
		List<String> returnVal = new ArrayList<String>();
		java.util.Date today = new java.util.Date();
		Timestamp now = new Timestamp(today.getTime());
		  Timestamp newStartDate1 = new Timestamp(getServiceDate1().getTime());
		  logger.info("rrrdate1:::" + newStartDate1);
		  logger.info("rrrTimeStart:::"+ getServiceTimeStart());
		  logger.info("rrrTimeEnd:::" + getServiceTimeEnd());
		  logger.info("rrrzone::"+timeZone);
		  Timestamp newStartTimeCombined = new Timestamp(combineDateAndTime(newStartDate1, getServiceTimeStart(),timeZone).getTime());
		  
		  logger.info("rrrnewStartTimeCombined1::"+newStartTimeCombined);
		  if (newStartTimeCombined.compareTo(now) < 0) {
			  returnVal.add("Start Date must be in the future.");
		  }
		  if (getServiceDate2() != null && isRequestedServiceTimeTypeDateRange()) {
		   // check if start < end
		  Timestamp newEndDate1 = new Timestamp(getServiceDate2().getTime());
		  logger.info("rrrdate2:::" + newEndDate1);
		  Timestamp newEndTimeCombined = new Timestamp(combineDateAndTime(newEndDate1, getServiceTimeEnd(), timeZone).getTime());
		  logger.info("rrrnewStartTimeCombined2::"+newEndTimeCombined);
		   if (newStartTimeCombined.compareTo(newEndTimeCombined) >= 0) {
			   returnVal.add("Start Date must be prior to End Date.");
		    
		   }
		 }
		return returnVal;
	}
	
	public static Date combineDateAndTime(Timestamp ts, String tm, String zone){
		if(zone == null){
			zone = "EST";
		}
		Calendar cal = null;
		int year = 0;
		int month = 0;
		int dayOfMonth = 0;
		int hours = 0;
		int min = 0;
		int sec = 0;
		String timeType = null;
		DateFormat sdf_yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
		String dt = sdf_yyyymmdd.format(ts);
		if (dt != null && tm != null && tm.length()==8 && zone != null) {
			try {
				year = Integer.parseInt(dt.substring(0, 4));
				month = Integer.parseInt(dt.substring(5, 7));
				dayOfMonth = Integer.parseInt(dt.substring(8, 10));
	
				hours = Integer.parseInt(tm.substring(0, 2));
				min = Integer.parseInt(tm.substring(3, 5));
				sec = Integer.parseInt("00");
				timeType = tm.substring(6, 8);
				
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			TimeZone tz = TimeZone.getTimeZone(zone);
	
			cal = new GregorianCalendar(year, month-1, dayOfMonth,
					hours, min, sec);
	
			cal.setTimeZone(tz);
			if(timeType.equalsIgnoreCase("AM")){
				cal.set(Calendar.AM_PM,Calendar.AM);
			}else if(timeType.equalsIgnoreCase("PM")){
				cal.set(Calendar.AM_PM, Calendar.PM);
			}
		}
		return cal.getTime();
	
	}
	
	public String getReason() {
		return reason;
}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public boolean isCreatedViaAPI() {
		return isCreatedViaAPI;
	}

	public void setCreatedViaAPI(boolean isCreatedViaAPI) {
		this.isCreatedViaAPI = isCreatedViaAPI;
	}
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
