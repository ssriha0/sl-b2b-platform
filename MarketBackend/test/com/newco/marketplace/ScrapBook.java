package com.newco.marketplace;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang.StringUtils;

public class ScrapBook {

	public static void main(String... args) {
		ScrapBook sb = new ScrapBook();
		//sb.beepFor30sec(); // java.unit.concurrent (Threads Scheduling Service) Test
		//System.out.println("*************************************************");
		sb.removeLeading0sFromUnitNumber();
		System.out.println("*************************************************");
		sb.testSQLStringAfterPercentEscaping();
		System.out.println("*************************************************");
		sb.testMillisToDateTime();
		System.out.println("*************************************************");
	}

	@SuppressWarnings("unused")
	private void beepFor30sec() {
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		final Runnable beeper = new Runnable() { public void run() { System.out.println("beep"); } };
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 1, 5, SECONDS); // 1,6,11,16,21,26
		scheduler.schedule(new Runnable() { public void run() { beeperHandle.cancel(true); } }, 30, SECONDS);
	}

	private void removeLeading0sFromUnitNumber() {
		String rawUnit = "0004031";
		String unit;
		try {
			Long unitLong = Long.parseLong(rawUnit);
			unit = unitLong.toString();
		} catch (NumberFormatException nfe) {
			System.err.println("IGNORING NumberFormatException while trying to parse unit number " + rawUnit + " to a Long.");
			unit = rawUnit;
		}
		System.out.println("Unit = [" + unit + "]");
	}

	private void testSQLStringAfterPercentEscaping() {
		String str = "ABC X & YZ";
		boolean endsWithPercent = str.endsWith("%");
		str = StringUtils.join(str.split("%"), "\\%");
		if (endsWithPercent) {
			str += "\\%";
		}
		System.out.println(str);
	}

	private void testMillisToDateTime() {
		long longMillis = 1290457920000l;
		System.out.println(new Date(longMillis));
	}
}
