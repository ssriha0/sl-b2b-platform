/**
 * 
 */
package com.newco.marketplace.util;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ledger.FiscalCalendarVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.ledger.IFiscalCalendarDAO;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.StackTraceHelper;
/**
 * @author ksharm6
 * 
 */
public class TimestampUtils 
{
	private static final Logger logger = Logger.getLogger(TimestampUtils.class.getName());
	
	public Timestamp stringToTimestamp(String timeformatString) throws ParseException
	{
		
		return Timestamp.valueOf(timeformatString);
	}
	public String timestampToString(Timestamp timestampValue)
	{
		return timestampValue.toString();
	}
	
	public static FiscalCalendarVO getSearsFiscalCalendarWeek(IFiscalCalendarDAO fiscalCalendarDAO)  throws DataServiceException
	{
		
		FiscalCalendarVO fiscalCalendarVO = new FiscalCalendarVO();
		
		Calendar calendar = Calendar.getInstance();		
		String currentDate = new Timestamp(calendar.getTimeInMillis()).toString();
		
		StringTokenizer token = new StringTokenizer(currentDate,"-");
		
		String year = token.nextToken();
		String month = token.nextToken();
		String dayAndTime = token.nextToken();		
		String day = dayAndTime.substring(0, 2);
		
		String fiscalDate = year + month + day;
		
		Integer fiscalDateInt = Integer.parseInt(fiscalDate);
		
		logger.info("The CheckFiscalDate str "+fiscalDate+" fiscalDateInt "+fiscalDateInt);
		
		fiscalCalendarVO.setCheckFiscalWeek(fiscalDateInt);
		try {
			fiscalCalendarVO = fiscalCalendarDAO.getFiscalCalendar(fiscalCalendarVO);
        }
        catch (Exception ex) {
        	logger.info("[TimestampUtils - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("Error", ex);
        }	
		
		return fiscalCalendarVO;
	}
	
	/**
	 * Returns Timestamp object after parsing time string with the specified format
	 * 
	 * @param dateTimeStr
	 * @param formatStr
	 * @return
	 */
	public static Timestamp getTimestampFromString(String dateTimeStr, String formatStr) {
		
		return new Timestamp(DateUtils.getDateFromString(dateTimeStr, formatStr).getTime());
	}
	
	/**
	 * Returns A string from the timestamp using the specified format
	 * 
	 * @param timestamp
	 * @param formatStr
	 * @return
	 */
	public static String getStringFromTimestamp(Timestamp timestamp, String formatStr){
		Date date = new Date(timestamp.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}
	
	/**
	 * This method calculated the number of days between two dates
	 * @param startTime
	 * @param endTime
	 * @return the difference of days
	 * @throws ParseException 
	 */
	public static int getDayDifference(Timestamp startTime, Timestamp endTime) throws ParseException {
		int daysDiff = 0;
		Calendar startDate = Calendar.getInstance();
		startDate.setTimeInMillis(startTime.getTime());
		 
		Calendar endDate = Calendar.getInstance();
		endDate.setTimeInMillis(endTime.getTime());
		
		//If the first time is after the second time return -1
		if (startDate.after(endDate)) {
			return -1;
		}
		
		//Strip off the time portion and compare the days
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String startDateStr = sdf.format(new Date(startTime.getTime()));
		String endDateStr = sdf.format(new Date(endTime.getTime()));
		
		startDate.setTimeInMillis(sdf.parse(startDateStr).getTime());
		endDate.setTimeInMillis(sdf.parse(endDateStr).getTime());
				
		while (startDate.before(endDate)) {
			startDate.add(Calendar.DAY_OF_MONTH, 1);
			daysDiff++;
		}
		return daysDiff;
	}

}
