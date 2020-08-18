import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import junit.framework.TestCase;


public class DateFormatTest extends TestCase {

	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z"); //XML Gregorian Cal
	SimpleDateFormat fmtTime = new SimpleDateFormat("hh:mm a");
	SimpleDateFormat fmtTimeMilitary = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss z" );

	final long t1 = 1217386800000l;	// 2008-07-29T22:00:00.000-0500
	final Date t1d = new Date( t1 );
	final String d = "P2008-07-31T18:00:00 CDT";
	final String d1 = "2008-07-29T18:00:00 CDT";
	final String dd = "D2008-07-29T16:09:00 CDT";
	final String d2 = "2008-07-29T16:09:00 CDT";
	final String timeString = "10:00:00";
	
	Date today = new Date();
	
	static final long ONE_HOUR = 60 * 60 * 1000;
	static final int one_hour = 60 * 60 * 1000;
	static final long ONE_DAY = 60 * 60 * 1000 * 24;
	static final int one_day = 60 * 60 * 1000 * 24;
	static final long ONE_YEAR = 60 * 60 * 1000 * 365;
	static final int one_year = 60 * 60 * 1000 * 365;
	public void testFormat() 
	{
		try
		{
			System.out.println( " 1) " + fmt.parse(d1) );
			System.out.println( " 2) " + fmt.parse(d1) );
			System.out.println( " 3) " + fmtDate.parse(d1) );
			System.out.println( " 4) " + fmtDate.format(today) );

			System.out.println( " 5) " + 
					fmtTime.format(today) );

			System.out.println( " 6) " + 
					d.substring( 1, 11 ));

			System.out.println( " 7) " + 
					(new Date(fmtDate.parse(d.substring( 1, 11 )).getTime() + ONE_DAY)).toString() );

			System.out.println( " 8) " + 
					fmtDate.format(new Date(fmtDate.parse(d.substring( 1, 11 )).getTime() + ONE_DAY)) );

			System.out.println( " 9) " + 
					fmt.format(new Date(fmt.parse(d1).getTime() + ONE_HOUR * 2)) );

			// CURRENT TIME + 2 HOURS
			System.out.println( "10) " + 
					fmt.format(new Date(today.getTime() + ONE_HOUR * 2)).substring( 11, 20 ) );

			String timeToChange = fmt.format(new Date(fmt.parse(d1).getTime() + ONE_HOUR * 2));
			String changedTime = timeToChange.substring( 0, timeToChange.indexOf('T') + 1 ) + fmt.format(new Date(today.getTime() + ONE_HOUR * 2)).substring( 11, 20 ) ;
			System.out.println( "11) " + changedTime );
			
			System.out.println( "12) " + d.substring( 12, 20 ));
			System.out.println( "13) " + today );
			System.out.println( "14) " + t1d );
			System.out.println( "15) " + dateFormat.parse(d2).getClass().getName() );

			System.out.println( "16) " + new Date() );
			System.out.println( "17) " + new Date( 0L + 6 * ONE_HOUR ) );
			
			String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
			SimpleTimeZone simpleZone = new SimpleTimeZone( 0, ids[0] );
			for( int i = 0; i < ids.length; i++ )
			{
				System.out.print( ids[i] + ", " );
			}
			print( Integer.valueOf(simpleZone.getRawOffset() / 1000) );
		}
		catch( Exception e )
		{
			System.out.println( "EXCEPTION: " + e.toString() );
		}
		return;
	}
	public void newline()
	{
		System.out.println();
	}
	public void print( Object s )
	{
		System.out.println( s.toString() );
	}
}
