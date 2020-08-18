package com.newco.marketplace.persistence.daoImpl.leadprofile;
import java.util.Comparator;
import java.util.Date;

import com.newco.marketplace.vo.leadprofile.GetLeadProjectTypeResponseVO;


public class PriceComparator implements Comparator<GetLeadProjectTypeResponseVO>{

	public PriceComparator() {
		super();
	}

	public int compare(GetLeadProjectTypeResponseVO object, GetLeadProjectTypeResponseVO compareObject) {
		Double objValue = compareObject.getExclusivePrice();
		Double compareObjValue = object.getExclusivePrice();
		int retVal = 0;
		if(compareObjValue == null){
			retVal = 0;
		}else{
			retVal = compareObjValue.compareTo(objValue);
		}
		return retVal;
	}
}
