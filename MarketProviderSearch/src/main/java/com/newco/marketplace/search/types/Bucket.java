package com.newco.marketplace.search.types;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Bucket {
	public static final int RATING_TYPE=0;
	public static final int DISTANCE_TYPE=1;
	public static final int LANGUAGE_TYPE=2;
	
	private int type;
	private String startRange;
	private String endRange;
	private Integer sortOrder;	
	private Integer count;
	
	public Bucket(int type) {
		this.type = type;
	}
	
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getStartRange() {
		return startRange;
	}
	
	public void setStartRange(String startRange) {
		this.startRange = startRange;
	}
	
	public String getEndRange() {
		return endRange;
	}
	
	public void setEndRange(String endRange) {
		this.endRange = endRange;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
