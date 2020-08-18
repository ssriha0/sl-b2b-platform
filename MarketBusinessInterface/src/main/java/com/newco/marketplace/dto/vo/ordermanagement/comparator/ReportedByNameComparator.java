package com.newco.marketplace.dto.vo.ordermanagement.comparator;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;

public class ReportedByNameComparator implements Comparator<OMServiceOrder>{
	public ReportedByNameComparator() {
		super();
	}
	
	//TODO Need review
	/**
	 * Compares two OMServiceOrder based on the Problem Reported By Column. 
	 * **/
	public int compare(OMServiceOrder object, OMServiceOrder compareObject) {
		String objValue =  StringUtils.isBlank(object.getProblemReportedBy())?"zzzz": object.getProblemReportedBy().toLowerCase();
		String compareObjValue =  StringUtils.isBlank(compareObject.getProblemReportedBy())?"zzzz": compareObject.getProblemReportedBy().toLowerCase();
		return objValue.compareTo(compareObjValue);
	}
	
}
