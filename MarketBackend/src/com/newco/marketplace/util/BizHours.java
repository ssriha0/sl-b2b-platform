package com.newco.marketplace.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BizHours {
	
	private static Map<Character, Integer> weekDays;
	static {
		weekDays = new HashMap<Character, Integer>();
		weekDays.put('S', Calendar.SUNDAY);
		weekDays.put('M', Calendar.MONDAY);
		weekDays.put('T', Calendar.TUESDAY);
		weekDays.put('W', Calendar.WEDNESDAY);
		weekDays.put('H', Calendar.THURSDAY);
		weekDays.put('F', Calendar.FRIDAY);
		weekDays.put('R', Calendar.SATURDAY);
	}
	private boolean[] bizHours;
	
	/**
	 * Examples of <code>weekHours</code>:
	 * <ul>
	 * <li>SMTWHFR:9-18</li>
	 * <li>MTWHF:9-18,SR:10-14</li>
	 * <li>MWF:8-14</li>
	 * </ul>
	 * 
	 * @param weekHours
	 */
	public BizHours(String weekHours) {
		bizHours = new boolean[168];
		for (int i = 0; i < 168; i++) {
			bizHours[i] = false;
		}

		for (String ss : weekHours.split(",")) {
			String[] tok = ss.split("[:\\-]");
			int start = Integer.parseInt(tok[1]);
			int end = Integer.parseInt(tok[2]);
			for (char dd : tok[0].toCharArray()) {
				int how = (weekDays.get(dd) - 1) * 24;
				for (int hh = start + 1; hh <= end; hh++) {
					bizHours[how + hh] = true;
				}
			}
		}
	}
	
	public void addAnHour(Calendar cal) {
		boolean done;
		do {
			cal.add(Calendar.HOUR_OF_DAY, 1);
			done = bizHours[(cal.get(Calendar.DAY_OF_WEEK) - 1) * 24 + cal.get(Calendar.HOUR_OF_DAY)];
		} 
		while (!done);
	}
	
	public void addHours(Calendar cal, int hours) {
		for (int i = 0; i < hours; i++) {
			addAnHour(cal);
		}
	}
	
	public static void main(String[] args) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(109, 1, 9, 19, 0));
		new BizHours("M:9-18").addHours(cal, 5);
				
		System.out.println(cal.getTime().toString());
	}

}
