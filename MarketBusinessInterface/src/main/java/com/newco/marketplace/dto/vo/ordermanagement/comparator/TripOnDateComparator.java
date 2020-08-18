package com.newco.marketplace.dto.vo.ordermanagement.comparator;

import java.util.Comparator;
import java.util.Date;

import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;

/**
 * Comparator class for Sorting {@link OMServiceOrder} List based on the Trip ON date.
 * 
 * **/
public class TripOnDateComparator implements Comparator<OMServiceOrder>{
	
	public TripOnDateComparator(){
		super();
	}
	//Method header
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 * method to compare job done date
	 */
	public int compare(OMServiceOrder object, OMServiceOrder compareObject) {
		Date objValue = object.getLastTripOn();
		Date compareObjValue = compareObject.getLastTripOn();
		int retVal = 0;
		
		if(compareObjValue == null){
			retVal = 0;
		}else if(objValue == null){
			retVal = 1;
		}else{
			retVal = objValue.compareTo(compareObjValue);
		}
		
		return retVal;
	}
}
