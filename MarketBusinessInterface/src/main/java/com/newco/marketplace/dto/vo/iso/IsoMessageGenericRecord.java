package com.newco.marketplace.dto.vo.iso;


import com.sears.os.vo.SerializableBaseVO;

public class IsoMessageGenericRecord extends SerializableBaseVO {

	private static final long serialVersionUID = 1L;
	private String messageShortAlias="" ;
 	private String isoDataElement ="";
 	private String isoFormatTypeDescr ="";
 	private String isoMessageValue ="";
 	private int isoMessageSortOrder=0;
	private int dataLength=0;
	private String messageDataType="";
	private byte[] isoByteValue;
	
	public String getMessageShortAlias() {
		return messageShortAlias;
	}
	public void setMessageShortAlias(String messageShortAlias) {
		this.messageShortAlias = messageShortAlias;
	}
	public String getIsoDataElement() {
		return isoDataElement;
	}
	public void setIsoDataElement(String isoDataElement) {
		this.isoDataElement = isoDataElement;
	}
	public String getIsoFormatTypeDescr() {
		return isoFormatTypeDescr;
	}
	public void setIsoFormatTypeDescr(String isoFormatTypeDescr) {
		this.isoFormatTypeDescr = isoFormatTypeDescr;
	}
	public String getIsoMessageValue() {
		return isoMessageValue;
	}
	public void setIsoMessageValue(String isoMessageValue) {
		this.isoMessageValue = isoMessageValue;
	}
	public int getIsoMessageSortOrder() {
		return isoMessageSortOrder;
	}
	public void setIsoMessageSortOrder(int isoMessageSortOrder) {
		this.isoMessageSortOrder = isoMessageSortOrder;
	}
	public int getDataLength() {
		return dataLength;
	}
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	public String getMessageDataType() {
		return messageDataType;
	}
	public void setMessageDataType(String messageDataType) {
		this.messageDataType = messageDataType;
	}
	public byte[] getIsoByteValue() {
		return isoByteValue;
	}
	public void setIsoByteValue(byte[] isoByteValue) {
		this.isoByteValue = isoByteValue;
	}
	
}
