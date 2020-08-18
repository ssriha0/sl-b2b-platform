package com.newco.marketplace.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class HolidayUtils
    {

	public static String getAllHolidaysforthisYear()
	{
		String Holidays= null;
		Calendar calendar= new GregorianCalendar();
		
		int currentYear=calendar.get(Calendar.YEAR);
		Date date = NewYearsDayObserved(currentYear).getTime();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strTempDate = formatter.format(date);
        Holidays=strTempDate;
        
        date = MartinLutherKingObserved(currentYear).getTime();
        strTempDate = formatter.format(date);
        Holidays=Holidays + " ;" +strTempDate;
        
        date = PresidentsDayObserved(currentYear).getTime();
        strTempDate = formatter.format(date);
        Holidays=Holidays + " ;" +strTempDate;
        
        date = MemorialDayObserved(currentYear).getTime();
        strTempDate = formatter.format(date);
        Holidays=Holidays + " ;" +strTempDate;
        
        date = IndependenceDayObserved(currentYear).getTime();
        strTempDate = formatter.format(date);
        Holidays=Holidays + " ;" +strTempDate;
        
        date = LaborDayObserved(currentYear).getTime();
        strTempDate = formatter.format(date);
        Holidays=Holidays + " ;" +strTempDate;
        
        date = ColumbusDayObserved(currentYear).getTime();
        strTempDate = formatter.format(date);
        Holidays=Holidays + " ;" +strTempDate;
        
        date = VeteransDayObserved(currentYear).getTime();
        strTempDate = formatter.format(date);
        Holidays=Holidays + " ;" +strTempDate;
        
        date = ThanksgivingObserved(currentYear).getTime();
        strTempDate = formatter.format(date);
        Holidays=Holidays + " ;" +strTempDate;
        
        date = ChristmasDayObserved(currentYear).getTime();
        strTempDate = formatter.format(date);
        Holidays=Holidays + " ;" +strTempDate;
	
        return Holidays;
	
	}
	
    public static GregorianCalendar NewYearsDayObserved (int nYear)
	{
	int nX;
	int nMonth = 0;			// January
	int nMonthDecember = 11;	// December
	
	Calendar calendar = new GregorianCalendar();
	calendar.set(nYear, nMonth, 1);
	nX=calendar.get(Calendar.DAY_OF_WEEK);
	switch(nX)
	    {
	    case 1 : // Sunday
	    	return new GregorianCalendar(nYear, nMonth, 2);
	    case 2 : // Monday
	    case 3 : // Tuesday
	    case 4 : // Wednesday
	    case 5 : // Thursday
	    case 6 : // Friday
	    default :	
	    	return new GregorianCalendar(nYear, nMonth, 1);
	    }
	}

    public static GregorianCalendar MartinLutherKingObserved (int nYear)
	{
	// Third Monday in January
	int nX;
	int nMonth = 0; // January
	//Date dtD;
	Calendar calendar = new GregorianCalendar();
	calendar.set(nYear, nMonth, 1);
	//dtD = new Date(nYear, nMonth, 1);
	//nX = dtD.getDay();
	nX=calendar.get(Calendar.DAY_OF_WEEK);
	switch(nX)
	    {
	    case 1 : // Sunday
		//return new Date(nYear, nMonth, 16);
	    	return new GregorianCalendar(nYear, nMonth, 16);	
	    case 2 : // Monday
		//return new Date(nYear, nMonth, 15);
	    	return new GregorianCalendar(nYear, nMonth, 15);
	    case 3 : // Tuesday
	    	return new GregorianCalendar(nYear, nMonth, 21);	
		//return new Date(nYear, nMonth, 21);
	    case 4 : // Wednesday
	    	return new GregorianCalendar(nYear, nMonth, 20);
		//return new Date(nYear, nMonth, 20);
	    case 5 : // Thursday
	    	return new GregorianCalendar(nYear, nMonth, 19);
		//return new Date(nYear, nMonth, 19);
	    case 6 : // Friday
	    	return new GregorianCalendar(nYear, nMonth, 18);
		//return new Date(nYear, nMonth, 18);
	    default : // Saturday
	    	return new GregorianCalendar(nYear, nMonth, 17);
		//return new Date(nYear, nMonth, 17);
	    }
	}

    
    public static GregorianCalendar PresidentsDayObserved (int nYear)
	{
	// Third Monday in February
	int nX;
	int nMonth = 1; // February
	Calendar calendar = new GregorianCalendar();
	calendar.set(nYear, nMonth, 1);
	nX=calendar.get(Calendar.DAY_OF_WEEK);
	switch(nX)
	    {
	    case 1 : // Sunday
	    	return new GregorianCalendar(nYear, nMonth, 16);
	    case 2 : // Monday
	    	return new GregorianCalendar(nYear, nMonth, 15);
	    case 3 : // Tuesday
	    	return new GregorianCalendar(nYear, nMonth, 21);
	    case 4 : // Wednesday
	    	return new GregorianCalendar(nYear, nMonth, 20);
	    case 5 : // Thursday
	    	return new GregorianCalendar(nYear, nMonth, 19);
	    case 6 : // Friday
	    	return new GregorianCalendar(nYear, nMonth, 18);
	    default : // Saturday
	    	return new GregorianCalendar(nYear, nMonth, 17);
	    }
	}


    public static GregorianCalendar MemorialDayObserved (int nYear)
	{
	// Last Monday in May
	int nX;
	int nMonth = 4; //May

	Calendar calendar = new GregorianCalendar();
	calendar.set(nYear, nMonth, 1);
	nX=calendar.get(Calendar.DAY_OF_WEEK);
	switch(nX)
	    {
	    case 1 : // Sunday
	    	return new GregorianCalendar(nYear, nMonth, 30);
	    case 2 : // Monday
	    	return new GregorianCalendar(nYear, nMonth, 29);
	    case 3 : // Tuesday
	    	return new GregorianCalendar(nYear, nMonth, 28);
	    case 4 : // Wednesday
	    	return new GregorianCalendar(nYear, nMonth, 27);
	    case 5 : // Thursday
	    	return new GregorianCalendar(nYear, nMonth, 26);
	    case 6 : // Friday
	    	return new GregorianCalendar(nYear, nMonth, 25);	
	    default : // Saturday
	    	return new GregorianCalendar(nYear, nMonth, 24);
	    }
	}

    public static GregorianCalendar IndependenceDayObserved (int nYear)
	{
	int nX;
	int nMonth = 6; // July
	Calendar calendar = new GregorianCalendar();
	calendar.set(nYear, nMonth, 4);
	nX=calendar.get(Calendar.DAY_OF_WEEK);
	switch(nX)
	    {
	    case 1 : // Sunday
		//if Independence day falls on a Sunday return Monday as the bank holiday date.
	    	return new GregorianCalendar(nYear, nMonth, 5);
	    case 2 : // Monday
	    case 3 : // Tuesday
	    case 4 : // Wednesday
	    case 5 : // Thursday
	    case 6 : // Friday
	    	return new GregorianCalendar(nYear, nMonth, 4);
	    default :
		// Saturday
	    	return new GregorianCalendar(nYear, nMonth, 4);
	    }
	}

    public static GregorianCalendar LaborDayObserved (int nYear)
	{
	// The first Monday in September
	int nX;
	int nMonth = 8; // September
	
	Calendar calendar = new GregorianCalendar();
	calendar.set(nYear, nMonth, 1);
	nX=calendar.get(Calendar.DAY_OF_WEEK);

	switch(nX)
	    {
	    case 1 : // Sunday
	    	return new GregorianCalendar(nYear, nMonth, 2);
	    case 2 : // Monday
	    	return new GregorianCalendar(nYear, nMonth, 1);
	    case 3 : // Tuesday
	    	return new GregorianCalendar(nYear, nMonth, 7);
	    case 4 : // Wednesday
	    	return new GregorianCalendar(nYear, nMonth, 6);
	    case 5 : // Thursday
	    	return new GregorianCalendar(nYear, nMonth, 5);
	    case 6 : // Friday
	    	return new GregorianCalendar(nYear, nMonth, 4);
	    default : // Saturday
	    	return new GregorianCalendar(nYear, nMonth, 3);	
	    }
	}

    public static GregorianCalendar ColumbusDayObserved (int nYear)
	{
	// Second Monday in October
	int nX;
	int nMonth = 9; // October 

	Calendar calendar = new GregorianCalendar();
	calendar.set(nYear, nMonth, 1);
	nX=calendar.get(Calendar.DAY_OF_WEEK);
	
	switch(nX)
	    {
	    case 1 : // Sunday
	    	return new GregorianCalendar(nYear, nMonth, 9);
	    case 2 : // Monday
	    	return new GregorianCalendar(nYear, nMonth, 8);
	    case 3 : // Tuesday
	    	return new GregorianCalendar(nYear, nMonth, 14);
	    case 4 : // Wednesday
	    	return new GregorianCalendar(nYear, nMonth, 13);
	    case 5 : // Thursday
	    	return new GregorianCalendar(nYear, nMonth, 12);
	    case 6 : // Friday
	    	return new GregorianCalendar(nYear, nMonth, 11);	
	    default : // Saturday
	    	return new GregorianCalendar(nYear, nMonth, 10);	
	    }

	}

    public static GregorianCalendar VeteransDayObserved (int nYear)
	{
	//November 11th
	int nMonth = 10; // November
	int nX;
	Calendar calendar = new GregorianCalendar();
	calendar.set(nYear, nMonth, 11);
	nX=calendar.get(Calendar.DAY_OF_WEEK);
	switch(nX)
	    {
	    case 1 : // Sunday
	    	//if Veterans day falls on a Sunday return Monday as the bank holiday date.
	    	return new GregorianCalendar(nYear, nMonth, 12);
	    case 2 : // Monday
	    case 3 : // Tuesday
	    case 4 : // Wednesday
	    case 5 : // Thursday
	    case 6 : // Friday
	    	return new GregorianCalendar(nYear, nMonth, 11);
	    default :
		// Saturday
	    	return new GregorianCalendar(nYear, nMonth, 11);
	    }
	}

    
    public static GregorianCalendar ThanksgivingObserved(int nYear)
	{
	int nX;
	int nMonth = 10; // November
	
	Calendar calendar = new GregorianCalendar();
	calendar.set(nYear, nMonth, 1);
	nX=calendar.get(Calendar.DAY_OF_WEEK);
	
	switch(nX)
	    {
	    case 1 : // Sunday
	    	return new GregorianCalendar(nYear, nMonth, 26);
	    case 2 : // Monday
	    	return new GregorianCalendar(nYear, nMonth, 25);
	    case 3 : // Tuesday
	    	return new GregorianCalendar(nYear, nMonth, 24);
	    case 4 : // Wednesday
	    	return new GregorianCalendar(nYear, nMonth, 23);
	    case 5 : // Thursday
	    	return new GregorianCalendar(nYear, nMonth, 22);
	    case 6 : // Friday
	    	return new GregorianCalendar(nYear, nMonth, 28);
	    default : // Saturday
	    	return new GregorianCalendar(nYear, nMonth, 27);
	    }
	} 

    public static GregorianCalendar ChristmasDayObserved (int nYear)
	{
	int nX;
	int nMonth = 11; // December
	Calendar calendar = new GregorianCalendar();
	calendar.set(nYear, nMonth, 25);
	nX=calendar.get(Calendar.DAY_OF_WEEK);
	switch(nX)
	    {
	    case 1 : // Sunday
	    	//if Christmas day falls on a Sunday return Monday as the bank holiday date.
	    	return new GregorianCalendar(nYear, nMonth, 26);
	    case 2 : // Monday
	    case 3 : // Tuesday
	    case 4 : // Wednesday
	    case 5 : // Thursday
	    case 6 : // Friday
	    	return new GregorianCalendar(nYear, nMonth, 25);	
	    default :
		// Saturday
	    	return new GregorianCalendar(nYear, nMonth, 25);
	    }
	}
    }


