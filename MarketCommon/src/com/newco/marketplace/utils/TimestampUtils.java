/**
 * 
 */
package com.newco.marketplace.utils;
import java.sql.Timestamp;
import java.text.ParseException;

/**
 * @author ksharm6
 * 
 */

public class TimestampUtils 
{

	public Timestamp stringToTimestamp(String timeformatString) throws ParseException
	{
		
		return Timestamp.valueOf(timeformatString);
	}
	public String timestampToString(Timestamp timestampValue)
	{
		return timestampValue.toString();
	}
	
}
