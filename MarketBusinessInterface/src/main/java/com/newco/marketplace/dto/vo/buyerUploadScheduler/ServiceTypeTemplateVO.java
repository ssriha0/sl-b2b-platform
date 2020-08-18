package com.newco.marketplace.dto.vo.buyerUploadScheduler;

import com.sears.os.vo.SerializableBaseVO;

public class ServiceTypeTemplateVO extends SerializableBaseVO{
private int service_type_template_id;
private int node_id;
private String descr;
public int getService_type_template_id() {
	return service_type_template_id;
}
public void setService_type_template_id(int service_type_template_id) {
	this.service_type_template_id = service_type_template_id;
}
public int getNode_id() {
	return node_id;
}
public void setNode_id(int node_id) {
	this.node_id = node_id;
}
public String getDescr() {
	return descr;
}
public void setDescr(String descr) {
	this.descr = descr;
}

}
