package com.newco.marketplace.dto.vo.financemanger;
import java.util.Comparator;
import java.util.Date;


public class DateComparator implements Comparator<BuyerSOReportVO>{

	public DateComparator() {
		super();
	}

	public int compare(BuyerSOReportVO object, BuyerSOReportVO compareObject) {
		Date objValue = object.getPaymentDate();
		Date compareObjValue = compareObject.getPaymentDate();
		int retVal = 0;
		if(compareObjValue == null){
			retVal = 0;
		}else{
			retVal = compareObjValue.compareTo(objValue);
		}
			
		return retVal;
	}
}
