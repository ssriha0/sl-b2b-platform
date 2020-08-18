package com.newco.marketplace.dto.vo.buyerUploadScheduler;

import com.sears.os.vo.SerializableBaseVO;

public class LookUpPartsSuppliedVO extends SerializableBaseVO{
int parts_supplied_by_Id;
String descr;
String type;
public int getParts_supplied_by_Id() {
	return parts_supplied_by_Id;
}
public void setParts_supplied_by_Id(int parts_supplied_by_Id) {
	this.parts_supplied_by_Id = parts_supplied_by_Id;
}
public String getDescr() {
	return descr;
}
public void setDescr(String descr) {
	this.descr = descr;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
}
