package com.newco.marketplace.web.action.skuMaintenance;
import java.util.Comparator;

import com.newco.marketplace.dto.vo.LookupVO;


public class ListComparator implements Comparator<LookupVO>{

	public ListComparator() {
		super();
	}

	public int compare(LookupVO object, LookupVO compareObject) {
		String objValue = object.getDescr().toLowerCase();
		String compareObjValue = compareObject.getDescr().toLowerCase();
		return objValue.compareTo(compareObjValue);
	}
}
