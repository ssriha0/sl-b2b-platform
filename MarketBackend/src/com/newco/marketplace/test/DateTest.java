package com.newco.marketplace.test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTest {
	public static void  main(String arg[]) throws ParseException, IOException {
			Double a = 300.00;
			DecimalFormat df = new DecimalFormat("0.000");
			
			
			System.out.println("Double:"+df.format(a));
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_HHmmss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMdd");
			System.out.println(cal.get(Calendar.YEAR));
		    String dateStr = sdf.format(cal.getTime());
		    System.out.println("siva:"+sdf1.format(cal.getTime()));
		    System.out.println("String Date :"+dateStr);
		    java.util.Date now = sdf.parse(dateStr);
		   // java.util.Date now1 = new java.util.Date("Util Date:"+sdf.parse(dateStr).getTime());
		    System.out.println("Util Date :"+now);
		    System.out.println(new java.sql.Date(System.currentTimeMillis()));
		    //System.out.println(new java.sql.Date(sdf.parse(dateStr));
		    
		    java.util.Date today = new java.util.Date();
		    System.out.println("sql"+new java.sql.Timestamp(today.getTime()));
		
		/*FileWriter fstream = new FileWriter("GLFeed.txt");
        BufferedWriter out = new BufferedWriter(fstream);
        out.write("Hello Java");
        out.close();*/

		  
		    
	}
	
	private static double round(double val, int places) {
		long factor = (long)Math.pow(10,places);

		// Shift the decimal the correct number of places
		// to the right.
		val = val * factor;

		// Round to the nearest integer.
		long tmp = Math.round(val);

		// Shift the decimal the correct number of places
		// back to the left.
		return (double)tmp / factor;
	    }
}
