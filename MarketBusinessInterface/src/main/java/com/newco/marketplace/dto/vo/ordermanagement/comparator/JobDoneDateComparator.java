package com.newco.marketplace.dto.vo.ordermanagement.comparator;

import java.util.Comparator;
import java.util.Date;

import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;

/**
 * Comparator class for Sorting {@link OMServiceOrder} List based on the Job Done date.
 * 
 * **/
public class JobDoneDateComparator implements Comparator<OMServiceOrder>{
	
	public JobDoneDateComparator(){
		super();
	}
	
	public int compare(OMServiceOrder object, OMServiceOrder compareObject) {
		Date objValue = object.getJobDoneOn();
		Date compareObjValue = compareObject.getJobDoneOn();
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
