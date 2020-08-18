package com.newco.marketplace.dto.vo.buyerUploadScheduler;

import com.sears.os.vo.SerializableBaseVO;

public class PhoneTypeVO extends SerializableBaseVO{
int phone_type_id;
String descr;
public int getPhone_type_id() {
	return phone_type_id;
}
public void setPhone_type_id(int phone_type_id) {
	this.phone_type_id = phone_type_id;
}
public String getDescr() {
	return descr;
}
public void setDescr(String descr) {
	this.descr = descr;
}
	
}
