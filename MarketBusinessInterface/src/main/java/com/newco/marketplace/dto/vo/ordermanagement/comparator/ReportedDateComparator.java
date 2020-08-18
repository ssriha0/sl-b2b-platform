package com.newco.marketplace.dto.vo.ordermanagement.comparator;

import java.util.Comparator;
import java.util.Date;

import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;

public class ReportedDateComparator implements Comparator<OMServiceOrder>{
	public ReportedDateComparator() {
		super();
	}
	
	/**
	 * Compares two OMServiceOrder based on Problem Reported Date
	 * **/
	public int compare(OMServiceOrder object, OMServiceOrder compareObject) {
		Date objValue = object.getProblemReportedDate();
		Date compareObjValue = compareObject.getProblemReportedDate();
		int retVal = 0;
		if(compareObjValue == null){
			retVal = 0;
		}else{
			retVal = objValue.compareTo(compareObjValue);
		}
		return retVal;
	}
}
