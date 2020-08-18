package com.newco.marketplace.api.beans.so.cancel.v1_2;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("cancelSku")
public class CancelSKU {
	
	@XStreamImplicit(itemFieldName="sku")
	private List<WorkOrderSKU> workOrderSKUs;	

	public List<WorkOrderSKU> getWorkOrderSKUs() {
		return workOrderSKUs;
	}

	public void setWorkOrderSKUs(List<WorkOrderSKU> workOrderSKUs) {
		this.workOrderSKUs = workOrderSKUs;
	}
	

}
