package com.servicelive.common;

import java.sql.Timestamp;
import java.util.Date;

public class CommonUtil {

	public static Timestamp getCurrentTimeStamp(){
		 return new Timestamp(new Date(System.currentTimeMillis()).getTime());
		 
	 }

}
