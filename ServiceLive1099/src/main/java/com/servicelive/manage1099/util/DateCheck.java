package com.servicelive.manage1099.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateCheck {

	public static boolean isValidDate(String inDate) {

		if (inDate == null)
			return false;

		// set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if (inDate.trim().length() != dateFormat.toPattern().length())
			return false;

		dateFormat.setLenient(false);

		try {
			// parse the inDate parameter
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

		System.out.println(DateCheck.isValidDate("2004-02-29"));
		System.out.println(DateCheck.isValidDate("2005-02-29"));
	}
}