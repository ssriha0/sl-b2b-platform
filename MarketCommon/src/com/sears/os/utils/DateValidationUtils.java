package com.sears.os.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.newco.marketplace.utils.TimeUtils;

public class DateValidationUtils {
	
	 public static boolean fromDateLesserToDate(String fromDate, String toDate){
			Date dateFrom = validDate(fromDate);
			Date dateTo = validDate(toDate);
			if(dateFrom.before(dateTo) || dateFrom.compareTo(dateTo)==0){
				return true;
			}
			else{
				return false;
			}
		}
		public static boolean fromDateGreaterCurrentDate(String fromDate){
			Date currentDate = new Date();
			currentDate.setDate(currentDate.getDate()-1);
			Date dateFrom = validDate(fromDate);
			if(dateFrom.after(currentDate)){
				return true;
			}
			else{
				return false;
			}
		}		
		
		public static boolean fromDateGreaterThanCurrentDate(String fromDate){
			Date currentDate = new Date();		
			Date dateFrom = validDate(fromDate);			
			if(dateFrom.after(currentDate))
			{				
				return true;
			}
			else
			{
				return false;
			}
		}
		
		public static boolean fromDateTimeGreaterCurrentDateTime(String fromDate,String fromTime){
			Timestamp dateFrom = new Timestamp(validDate(fromDate).getTime());
			return TimeUtils.isPastCurrentTime(dateFrom, fromTime);
		}
		
		public static boolean fromDateTimeGreaterCurrentDateTime(String fromDate
				,String fromTime, String timeZone){
			Timestamp dateFrom = new Timestamp(validDate(fromDate).getTime());
			return TimeUtils.isPastCurrentTime(dateFrom, fromTime, timeZone);
		}
		
		public static Date validDate(String date){
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		   Date actualDate = null;
		   try{
			   actualDate = sdf.parse(date);
		   }
		   catch (ParseException e){
		   }
		   return actualDate;

		}
		public static String isValidTimestamp(String date){
			String validDate = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date actualDate = null;
			   try{
				   actualDate = sdf.parse(date);
			   }

			   catch (ParseException e){
				   return "invalid_date";
			   }			
			return "valid_date";
		}
		public static String isValidDate(String date){
			String validDate = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date actualDate = null;
			   try{
				   actualDate = sdf.parse(date);
			   }

			   catch (ParseException e){
				   return "invalid_date";
			   }
			   if (!sdf.format(actualDate).equals(date)){
				   return "invalid_format";
			   }

			return "valid_date";
		}
		public static boolean timeValidation(String time1, String time2){
			DateFormat formatter = new SimpleDateFormat("hh:mm a");
			Date date1 = null;
			Date date2 = null;
			try{
				 date1 = (Date)formatter.parse(time1);
			     date2 = (Date)formatter.parse(time2);
			} catch (ParseException e){}
	        if(date1.before(date2)){
				return true;
			}
			else{
				return false;
			}
		}		
		
		public static boolean timeValidationFor24HrFormat(String time1, String time2){
			DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
			Date date1 = null;
			Date date2 = null;
			try{
				 date1 = (Date)formatter.parse(time1);
			     date2 = (Date)formatter.parse(time2);
			} catch (ParseException e){}
	        if(null != date1 && date1.before(date2)){
				return true;
			}
			else{
				return false;
			}
		}		
		
		
		public static boolean emailValidation(String email){
	    	Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)");
			;
			//if(null != email || !email.equals(""))
			Matcher m = p.matcher(email);
			boolean valResult = m.matches();
	    	return valResult;
	    }
}
