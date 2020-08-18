package com.newco.marketplace.dto.vo.buyerUploadScheduler;

import com.sears.os.vo.SerializableBaseVO;

public class LookupShippngCarrierVO extends SerializableBaseVO{
private int shipping_carrier_id;
private String descr;
private int sort_order	;
public int getShipping_carrier_id() {
	return shipping_carrier_id;
}
public void setShipping_carrier_id(int shipping_carrier_id) {
	this.shipping_carrier_id = shipping_carrier_id;
}
public String getDescr() {
	return descr;
}
public void setDescr(String descr) {
	this.descr = descr;
}
public int getSort_order() {
	return sort_order;
}
public void setSort_order(int sort_order) {
	this.sort_order = sort_order;
}
}
