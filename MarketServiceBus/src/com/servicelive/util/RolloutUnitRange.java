package com.servicelive.util;

public class RolloutUnitRange {
	private String lowerBound;
	private String upperBound;
	
	public RolloutUnitRange(String lower, String upper) {
		lowerBound = lower;
		upperBound = upper;
	}
	
	public void setLowerBound(String lowerBound) {
		this.lowerBound = lowerBound;
	}
	public String getLowerBound() {
		return lowerBound;
	}
	public void setUpperBound(String upperBound) {
		this.upperBound = upperBound;
	}
	public String getUpperBound() {
		return upperBound;
	}
}
