package com.newco.marketplace.translator.util;

import java.util.*;

public class HolidayUtils
    {

	public static String getHolidaysForYear( Integer currentYear )
	{
		StringBuffer holidays = new StringBuffer();
//		String holidaty = "";
//		Calendar c = new GregorianCalendar();
//        DateFormat formatter = new SimpleDateFormat("MMddyyyy");
//        
//		Date date = NewYearsDayObserved(c, currentYear);
//        String strTempDate = formatter.format(date);
//        holidays = strTempDate;
//        holidays += ";0101" + currentYear;
//        holidays += ";12312010";
//        
//        date = MartinLutherKingObserved(c, currentYear);
//        strTempDate = formatter.format(date);
//        holidays += ";" + strTempDate;
//        
//        date = PresidentsDayObserved(c, currentYear);
//        strTempDate = formatter.format(date);
//        holidays += ";" + strTempDate;
//        
//        date = MemorialDayObserved(c, currentYear);
//        strTempDate = formatter.format(date);
//        holidays += ";" + strTempDate;
//        
//        date = IndependenceDayObserved(c, currentYear);
//        strTempDate = formatter.format(date);
//        holidays += ";" + strTempDate;
//        holidays += ";0704" + currentYear.toString();
//        
//        date = LaborDayObserved(c, currentYear);
//        strTempDate = formatter.format(date);
//        holidays += ";" + strTempDate;
//        
//        date = ColumbusDayObserved(c, currentYear);
//        strTempDate = formatter.format(date);
//        holidays += ";" + strTempDate;
//        
//        date = VeteransDayObserved(c, currentYear);
//        strTempDate = formatter.format(date);
//        holidays += ";" + strTempDate;
//        
//        date = ThanksgivingObserved(c, currentYear);
//        strTempDate = formatter.format(date);
//        holidays += ";" + strTempDate;
//        
//        date = ChristmasDayObserved(c, currentYear);
//        strTempDate = formatter.format(date);
//        holidays += ";" + strTempDate;
//        holidays += ";1225" + currentYear.toString();
	
		holidays.append( "11272008" )
			.append( ";" )
			.append( "12252008" )
			.append( ";" )
			.append( "01012009" )
			.append( ";" )
			.append( "05252009" )
			.append( ";" )
			.append( "07032009" )
			.append( ";" )
			.append( "09072009" )
			.append( ";" )
			.append( "11262009" )
			.append( ";" )
			.append( "12252009" )
			.append( ";" )
			.append( "01012010" )
			.append( ";" )
			.append( "05312010" )
			.append( ";" )
			.append( "07052010" )
			.append( ";" )
			.append( "09062010" )
			.append( ";" )
			.append( "11252010" )
			.append( ";" )
			.append( "12242010" )
			.append( ";" )
			.append( "05302011" )
			.append( ";" )
			.append( "07042011" )
			.append( ";" )
			.append( "09052011" )
			.append( ";" )
			.append( "11242011" )
			.append( ";" )
			.append( "12262011" )
			.append( ";" )
			.append( "01022012" )
			.append( ";" )
			.append( "05282012" )
			.append( ";" )
			.append( "07042012" )
			.append( ";" )
			.append( "09032012" )
			.append( ";" )
			.append( "11222012" )
			.append( ";" )
			.append( "12252012" )
			.append( ";" );
        return holidays.toString();
	
	}
	
    public static final Date NewYearsDayObserved (Calendar c, int nYear)
	{
		int nX;
		int nMonth = Calendar.JANUARY;
		
		c.set(nYear, nMonth, 1);
		nX = c.get(Calendar.DAY_OF_WEEK);
		switch(nX)
		{
	    case Calendar.SUNDAY:
	    	c.set(nYear, nMonth, 2);
	    	break;
	    case Calendar.SATURDAY:
	    	c.set(nYear - 1, Calendar.DECEMBER, 31);
	    	break;
		}
	    return c.getTime();
	}

    public static final Date MartinLutherKingObserved (Calendar c, int nYear)
	{
		// Third Monday in January
		int nX;
		int nMonth = Calendar.JANUARY;
		
		c.set(nYear, nMonth, 1);
		nX=c.get(Calendar.DAY_OF_WEEK);
		switch(nX)
		{
		    case Calendar.SUNDAY:
		    	c.set(nYear, nMonth, 16);	
		    	break;
		    case Calendar.MONDAY:
		    	c.set(nYear, nMonth, 15);
		    	break;
		    case Calendar.TUESDAY:
		    	c.set(nYear, nMonth, 21);	
		    	break;
		    case Calendar.WEDNESDAY:
		    	c.set(nYear, nMonth, 20);
		    	break;
		    case Calendar.THURSDAY:
		    	c.set(nYear, nMonth, 19);
		    	break;
		    case Calendar.FRIDAY:
		    	c.set(nYear, nMonth, 18);
		    	break;
		    default:
		    	c.set(nYear, nMonth, 17);
	    		break;
		}
	    return c.getTime();
	}

    
    public static final Date PresidentsDayObserved (Calendar c, int nYear)
	{
	// Third Monday in February
	int nX;
	int nMonth = Calendar.FEBRUARY;
	
	c.set(nYear, nMonth, 1);
	nX=c.get(Calendar.DAY_OF_WEEK);
	switch(nX)
	    {
	    case Calendar.SUNDAY:
	    	c.set(nYear, nMonth, 16);
	    	break;
	    case Calendar.MONDAY:
	    	c.set(nYear, nMonth, 15);
	    	break;
	    case Calendar.TUESDAY:
	    	c.set(nYear, nMonth, 21);
	    	break;
	    case Calendar.WEDNESDAY:
	    	c.set(nYear, nMonth, 20);
	    	break;
	    case Calendar.THURSDAY:
	    	c.set(nYear, nMonth, 19);
	    	break;
	    case Calendar.FRIDAY:
	    	c.set(nYear, nMonth, 18);
	    	break;
	    default:
	    	c.set(nYear, nMonth, 17);
    		break;
	    }
    return c.getTime();
	}


    public static final Date MemorialDayObserved (Calendar c, int nYear)
	{
		// Last Monday in May
		int nX;
		int nMonth = Calendar.MAY;
	
		c.set(nYear, nMonth, 1);
		nX = c.get(Calendar.DAY_OF_WEEK);
		switch(nX)
		{
	    case Calendar.SUNDAY:
	    	c.set(nYear, nMonth, 30);
	    	break;
	    case Calendar.MONDAY:
	    	c.set(nYear, nMonth, 29);
	    	break;
	    case Calendar.TUESDAY:
	    	c.set(nYear, nMonth, 28);
	    	break;
	    case Calendar.WEDNESDAY:
	    	c.set(nYear, nMonth, 27);
	    	break;
	    case Calendar.THURSDAY:
	    	c.set(nYear, nMonth, 26);
	    	break;
	    case Calendar.FRIDAY:
	    	c.set(nYear, nMonth, 25);	
	    	break;
	    default:
	    	c.set(nYear, nMonth, 31);
    		break;
		}
	    return c.getTime();
	}

    public static final Date IndependenceDayObserved (Calendar c, int nYear)
	{
		int nX;
		int nMonth = Calendar.JULY;
		
		c.set(nYear, nMonth, 4);
		nX = c.get(Calendar.DAY_OF_WEEK);
		switch(nX)
		{
	    case Calendar.SUNDAY :
	    	c.set(nYear, nMonth, 5);
	    	break;
	    case Calendar.SATURDAY:
	    	c.set(nYear, nMonth, 3);
	    	break;
	    default :
//	    	c.set(nYear, nMonth, 4);
    		break;
		}
	    return c.getTime();
	}

    public static final Date LaborDayObserved (Calendar c, int nYear)
	{
		// The first Monday in September
		int nX;
		int nMonth = Calendar.SEPTEMBER;
		
		c.set(nYear, nMonth, 1);
		nX = c.get(Calendar.DAY_OF_WEEK);
	
		switch(nX)
		{
	    case Calendar.SUNDAY:
	    	c.set(nYear, nMonth, 2);
	    	break;
	    case Calendar.MONDAY:
	    	c.set(nYear, nMonth, 1);
	    	break;
	    case Calendar.TUESDAY:
	    	c.set(nYear, nMonth, 7);
	    	break;
	    case Calendar.WEDNESDAY:
	    	c.set(nYear, nMonth, 6);
	    	break;
	    case Calendar.THURSDAY:
	    	c.set(nYear, nMonth, 5);
	    	break;
	    case Calendar.FRIDAY:
	    	c.set(nYear, nMonth, 4);
	    	break;
	    default:
	    	c.set(nYear, nMonth, 3);	
    		break;
		}
	    return c.getTime();
	}

    public static final Date ColumbusDayObserved (Calendar c, int nYear)
	{
	// Second Monday in October
	int nX;
	int nMonth = Calendar.OCTOBER;

	
	c.set(nYear, nMonth, 1);
	nX = c.get(Calendar.DAY_OF_WEEK);
	
	switch(nX)
	    {
	    case Calendar.SUNDAY:
	    	c.set(nYear, nMonth, 9);
	    	break;
	    case Calendar.MONDAY:
	    	c.set(nYear, nMonth, 8);
	    	break;
	    case Calendar.TUESDAY:
	    	c.set(nYear, nMonth, 14);
	    	break;
	    case Calendar.WEDNESDAY:
	    	c.set(nYear, nMonth, 13);
	    	break;
	    case Calendar.THURSDAY:
	    	c.set(nYear, nMonth, 12);
	    	break;
	    case Calendar.FRIDAY:
	    	c.set(nYear, nMonth, 11);	
	    	break;
	    default:
	    	c.set(nYear, nMonth, 10);	
    		break;
	    }
    return c.getTime();
	}

    public static final Date VeteransDayObserved (Calendar c, int nYear)
	{
	//November 11th
	int nMonth = Calendar.NOVEMBER;
	int nX;
	
	c.set(nYear, nMonth, 11);
	nX=c.get(Calendar.DAY_OF_WEEK);
	switch(nX)
	    {
	    case Calendar.SUNDAY:
	    	//if Veterans day falls on a Sunday return Monday as the bank holiday date.
	    	c.set(nYear, nMonth, 12);
	    	break;
	    case Calendar.MONDAY:
	    case Calendar.TUESDAY:
	    case Calendar.WEDNESDAY:
	    case Calendar.THURSDAY:
	    case Calendar.FRIDAY:
	    	c.set(nYear, nMonth, 11);
	    	break;
	    default :
	    	c.set(nYear, nMonth, 11);
    		break;
	    }
    return c.getTime();
	}

    
    public static final Date ThanksgivingObserved(Calendar c, int nYear)
	{
		int nX;
		int nMonth = Calendar.NOVEMBER;
		
		c.set(nYear, nMonth, 1);
		nX = c.get(Calendar.DAY_OF_WEEK);
		
		switch(nX)
		{
	    case Calendar.SUNDAY:
	    	c.set(nYear, nMonth, 26);
	    	break;
	    case Calendar.MONDAY:
	    	c.set(nYear, nMonth, 25);
	    	break;
	    case Calendar.TUESDAY:
	    	c.set(nYear, nMonth, 24);
	    	break;
	    case Calendar.WEDNESDAY:
	    	c.set(nYear, nMonth, 23);
	    	break;
	    case Calendar.THURSDAY:
	    	c.set(nYear, nMonth, 22);
	    	break;
	    case Calendar.FRIDAY:
	    	c.set(nYear, nMonth, 28);
	    	break;
	    default:
	    	c.set(nYear, nMonth, 27);
    		break;
		}
	    return c.getTime();
	} 

    public static final Date ChristmasDayObserved (Calendar c, int nYear)
	{
		int nX;
		int nMonth = Calendar.DECEMBER;
		
		c.set(nYear, nMonth, 25);
		nX = c.get(Calendar.DAY_OF_WEEK);
		switch(nX)
		{
	    case Calendar.SUNDAY:
	    	//if Christmas day falls on a Sunday return Monday as the bank holiday date.
	    	c.set(nYear, nMonth, 26);
	    	break;
	    case Calendar.SATURDAY:
	    	c.set(nYear, nMonth, 24);
	    	break;
	    default:
//	    	c.set(nYear, nMonth, 25);
    		break;
		}
	    return c.getTime();
	}
}


