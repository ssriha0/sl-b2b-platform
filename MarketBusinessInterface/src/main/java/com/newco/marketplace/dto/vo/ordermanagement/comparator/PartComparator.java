package com.newco.marketplace.dto.vo.ordermanagement.comparator;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders.InvoicePartVO;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.Task;
import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;

/**
 * Comparator class for Sorting {@link OMServiceOrder} List based on the Trip ON date.
 * 
 * **/
public class PartComparator implements Comparator<OMServiceOrder>{
	
	public PartComparator(){
		super();
	}
	
	public int compare(OMServiceOrder object, OMServiceOrder compareObject) {/*
		StringBuilder partStatus1 = new StringBuilder();
		List<InvoicePartVO> partList = object.getParts();
		if(null != partList && partList.size() >0){
			for(InvoicePartVO invoicePartVO : partList){
				partStatus1.append(invoicePartVO.getPartDesc()).append(" ");
			}
		}
		StringBuilder partStatus2 = new StringBuilder();
		partList = compareObject.getParts();
		if(null != partList && partList.size() >0){
			for(InvoicePartVO invoicePartVO : partList){
				partStatus2.append(invoicePartVO.getPartDesc()).append(" ");
			}
		}
		String objValue = partStatus1.toString().toLowerCase();
		String compareObjValue = partStatus2.toString().toLowerCase();
		return objValue.compareTo(compareObjValue);
		
	*/
		return 1;
	}
}
