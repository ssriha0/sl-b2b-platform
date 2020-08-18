package com.newco.marketplace.dto.vo.buyerUploadScheduler;

import com.sears.os.vo.SerializableBaseVO;

public class PhoneClassVO extends SerializableBaseVO{
int so_phone_class_id;
String descr;
public int getSo_phone_class_id() {
	return so_phone_class_id;
}
public void setSo_phone_class_id(int so_phone_class_id) {
	this.so_phone_class_id = so_phone_class_id;
}
public String getDescr() {
	return descr;
}
public void setDescr(String descr) {
	this.descr = descr;
}
}
