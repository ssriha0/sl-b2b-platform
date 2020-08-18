/**
 * 
 */
package com.servicelive.orderfulfillment.domain.type;

public enum SOScheduleType {
	SINGLEDAY(1),
	DATERANGE(2),
	PREFERENCE(3);
	
	Integer id;
	
	private SOScheduleType(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public static SOScheduleType fromId(int id){
	    if(id > 3) throw new IllegalArgumentException("Invalid ID provided for SOScheduleType: " + id);
	    if(1 == id){
	        return SINGLEDAY;
	    }else if(3==id){
	        return PREFERENCE;
	    }else {
	    	 return DATERANGE;
	    }
	}
}