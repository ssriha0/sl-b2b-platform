package com.newco.marketplace.utils;

public class TimezoneMapper {
	
	public static String getStandardTimezone(String timezone){
		String tz = "";
		if("EST5EDT".equals(timezone)){
			tz="EST";
		}
		if("AST4ADT".equals(timezone)){  
			tz="AST";
		}
		if("CST6CDT".equals(timezone)){
			tz="CST";
		}
		if("MST7MDT".equals(timezone)){
			tz="MST";
		}
		if("PST8PDT".equals(timezone)){
			tz="PST";
		}
		if("HST".equals(timezone)){
			tz="HST";
		}
		if("Etc/GMT+1".equals(timezone)){
			tz="CET";
		}
		if("AST".equals(timezone)){
			tz="AST";
		}		
		if("Etc/GMT-9".equals(timezone)){
			tz="PST-7";
		}
		if("MIT".equals(timezone)){
			tz="PST-3";
		}
		if("NST".equals(timezone)){
			tz="PST-4";
		}
		if("Etc/GMT-10".equals(timezone)){
			tz="PST-6";
		}
		if("Etc/GMT-11".equals(timezone)){
			tz="PST-5";
		}		
 		return tz;
 	}
	
	public static String getDSTTimezone(String tz) {
		String timezone = "";
		if ("EST5EDT".equals(tz)) {
			timezone="EDT";
		}
		if ("AST4ADT".equals(tz)) {
			timezone="ADT";
		}
		if ("CST6CDT".equals(tz)) {
			timezone="CDT";
		}
		if ("MST7MDT".equals(tz)) {
			timezone ="MDT";
		}
		if ("PST8PDT".equals(tz)) {
			timezone ="PDT";
		}
		if ("HST".equals(tz)) {
			timezone ="HADT";
		}
		if ("Etc/GMT+1".equals(tz)) {
			timezone ="CEDT";
		}
		if ("AST".equals(tz)) {
			timezone="AKDT";
		}
		return timezone;
	}

}
