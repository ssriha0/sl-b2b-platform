package com.newco.marketplace.web.dto;

import java.util.Comparator;

public class ResponseStatusDTOComparator implements Comparator<ResponseStatusDTO> {

	public int compare(ResponseStatusDTO r1, ResponseStatusDTO r2) {
		int compareResult = 0;
		
		compareResult = compareVendorIds(r1.getVendorId(), r2.getVendorId());
		if (compareResult != 0) {
			return compareResult;
		}
		
		compareResult = compareDistance(r1.getDistanceFromBuyer(), r2.getDistanceFromBuyer());
		if (compareResult != 0) {
			return compareResult;
		}
		
		compareResult = compareStrings(r1.getFirstName(), r2.getFirstName());
		if (compareResult != 0) {
			return compareResult;
		}
		
		compareResult = compareStrings(r1.getLastName(), r2.getLastName());		
		return compareResult;
	}
	
	private int compareStrings(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return 0;
		}
		if (s1 == null) {
			return -1;
		}
		if (s2 == null) {
			return 1;
		}
		return s1.compareTo(s2);
	}
	
	private int compareVendorIds(Integer v1, Integer v2) {
		if (v1 == null && v2 == null) {
			return 0;
		}
		if (v1 == null) {
			return -1;
		}
		if (v2 == null) {
			return 1;
		}
		return v1.compareTo(v2);
	}
	
	private int compareDistance(Long d1, Long d2) {
		if (d1 == null && d2 == null) {
			return 0;
		}
		if (d1 == null) {
			return -1;
		}
		if (d2 == null) {
			return 1;
		}
		return d1.compareTo(d2);
	}

}
