package com.newco.marketplace.dto.vo.buyerUploadScheduler;

import com.sears.os.vo.SerializableBaseVO;

public class OrgDocumentVO extends SerializableBaseVO{
int document_id;
String descr;
/**
 * @return the document_id
 */
public int getDocument_id() {
	return document_id;
}
/**
 * @param document_id the document_id to set
 */
public void setDocument_id(int document_id) {
	this.document_id = document_id;
}
/**
 * @return the descr
 */
public String getDescr() {
	return descr;
}
/**
 * @param descr the descr to set
 */
public void setDescr(String descr) {
	this.descr = descr;
}

}
