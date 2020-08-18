package com.newco.marketplace.search.types;


public enum ResultMode {		
	MINIMUN,
	MEDIUM,
	LARGE,
	ALL;

	public static ResultMode getObj(String str) {
		if (str == null) return ResultMode.MINIMUN; //default
		
		if (str.equalsIgnoreCase("Minimum")) {
			return ResultMode.MINIMUN;
		} else if (str.equalsIgnoreCase("Medium")) {
			return ResultMode.MEDIUM;
		} else if (str.equalsIgnoreCase("Large")) {
			return ResultMode.LARGE;
		} else {
			return ResultMode.ALL;		
		}
	}
}
