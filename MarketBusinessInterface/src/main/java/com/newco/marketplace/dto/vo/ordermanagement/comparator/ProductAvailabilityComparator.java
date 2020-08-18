package com.newco.marketplace.dto.vo.ordermanagement.comparator;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;

public class ProductAvailabilityComparator implements Comparator<OMServiceOrder>{
	Logger LOGGER = Logger.getLogger(ProductAvailabilityComparator.class);
	public ProductAvailabilityComparator() {
		super();
	}
	
	/**
	 * Compares two OMServiceOrder based on Product Availability Column.
	 * **/
	public int compare(OMServiceOrder object, OMServiceOrder compareObject) {
		StringBuilder objValue;
		String street1 = StringUtils.isBlank(object.getPartStreet1())?"zzz":object.getPartStreet1().toLowerCase();
		objValue = new StringBuilder(street1);
		String street2 = StringUtils.isBlank(object.getPartStreet2())?"zzz":object.getPartStreet2().toLowerCase();
		objValue = objValue.append(street2);
		
		street1 = StringUtils.isBlank(compareObject.getPartStreet1())?"zzz":compareObject.getPartStreet1().toLowerCase();
		StringBuilder compareObjValue = new StringBuilder(street1);
		street2 = StringUtils.isBlank(compareObject.getPartStreet2())?"zzz":compareObject.getPartStreet2().toLowerCase();
		compareObjValue = compareObjValue.append(street2);
		return objValue.toString().compareTo(compareObjValue.toString());
	}
}
