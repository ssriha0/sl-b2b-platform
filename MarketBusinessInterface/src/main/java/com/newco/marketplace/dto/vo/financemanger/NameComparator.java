package com.newco.marketplace.dto.vo.financemanger;
import java.util.Comparator;


public class NameComparator implements Comparator<BuyerSOReportVO>{

	public NameComparator() {
		super();
	}

	public int compare(BuyerSOReportVO object, BuyerSOReportVO compareObject) {
		String objValue = object.getProviderFirmName().toLowerCase();
		String compareObjValue = compareObject.getProviderFirmName().toLowerCase();
		return objValue.compareTo(compareObjValue);
	}
}
