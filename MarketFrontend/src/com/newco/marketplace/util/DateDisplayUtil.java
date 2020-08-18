package com.newco.marketplace.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.lookup.LookupBO;
import com.newco.marketplace.utils.TimezoneMapper;

public class DateDisplayUtil {
	
	private static final Logger logger = Logger.getLogger(DateDisplayUtil.class);
	private static LookupBO lookupBO;
	static DateFormat sdfStatic = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	
	public static String getDisplayDateWithTimeZone(String serviceOrderDateString, String zip) {
		if (zip == null) {
			return null;
		}
		//LookupBO lookupBO = (LookupBO) MPSpringLoaderPlugIn.getCtx().getBean("lookupBO");
		LookupBO lookupBO = getLookupBO();
		String timezone = lookupBO.getZipTimezone(zip);
		return getDisplayDateWithTimeZone(serviceOrderDateString, timezone, zip);
	}
	
	public static LookupBO getLookupBO() {
		if (lookupBO == null)
		  lookupBO = (LookupBO) MPSpringLoaderPlugIn.getCtx().getBean("lookupBO");
		return lookupBO;
	}

	public static String getDisplayDateWithTimeZone(String serviceOrderDateString, String systemTimeZone, String zip) {		
		String displayDate = null;
		if (serviceOrderDateString != null && !serviceOrderDateString.equals("No Data")) {
			TimeZone tz = null;
			if (systemTimeZone != null) {
				tz = TimeZone.getTimeZone(TimezoneMapper.getStandardTimezone(systemTimeZone));
			}
			String dlsFlag = null;
			try{
				LookupBO lookupBO = getLookupBO();
				dlsFlag = lookupBO.getDaylightSavingsFlg(zip);
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
			//this is the same date formatter used in the object mapper
			DateFormat sdf = sdfStatic;
			Date dt = null;
			try {
				dt = sdf.parse(serviceOrderDateString);
			}catch(ParseException pe){
				logger.error(pe.getMessage(), pe);
			}	
			if("Y".equals(dlsFlag) && tz != null && dt != null){
				if (dt != null) {
					boolean isDLSActive=tz.inDaylightTime(new Timestamp(dt.getTime()));
					if(isDLSActive){
						displayDate = sdf.format(dt) + " ("+tz.getID()+")";
					}else{
						displayDate = sdf.format(dt) + " ("+tz.getID()+")";
					}
				}
			}
			else if (dt != null) {
				displayDate = sdf.format(dt);
				if(tz != null) {
					displayDate +=  " ("+tz.getID()+")";
				}
			}
		}		
		return displayDate;
	}
	
	public static Timestamp convertDateStringToTimestamp(String format, String date)
	{
		if(format == null)
			return null;
		
		if(date == null)
			return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d=null;
		try
		{
			d = sdf.parse(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		if(d != null)
			return new Timestamp(d.getTime());		
		else
			return null;
	}
}
