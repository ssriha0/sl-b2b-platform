package com.servicelive.wallet.valuelink.sharp.iso.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Class IsoMessageGenericRecordVO.
 */
public class IsoMessageGenericRecordVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** dataLength. */
	private int dataLength = 0;

	/** isoByteValue. */
	private byte[] isoByteValue;

	/** isoDataElement. */
	private String isoDataElement = "";

	/** isoFormatTypeDescr. */
	private String isoFormatTypeDescr = "";

	/** isoMessageSortOrder. */
	private int isoMessageSortOrder = 0;

	/** isoMessageValue. */
	private String isoMessageValue = "";

	/** messageDataType. */
	private String messageDataType = "";

	/** messageShortAlias. */
	private String messageShortAlias = "";

	/**
	 * getDataLength.
	 * 
	 * @return int
	 */
	public int getDataLength() {

		return dataLength;
	}

	/**
	 * getIsoByteValue.
	 * 
	 * @return byte[]
	 */
	public byte[] getIsoByteValue() {

		return isoByteValue;
	}

	/**
	 * getIsoDataElement.
	 * 
	 * @return String
	 */
	public String getIsoDataElement() {

		return isoDataElement;
	}

	/**
	 * getIsoFormatTypeDescr.
	 * 
	 * @return String
	 */
	public String getIsoFormatTypeDescr() {

		return isoFormatTypeDescr;
	}

	/**
	 * getIsoMessageSortOrder.
	 * 
	 * @return int
	 */
	public int getIsoMessageSortOrder() {

		return isoMessageSortOrder;
	}

	/**
	 * getIsoMessageValue.
	 * 
	 * @return String
	 */
	public String getIsoMessageValue() {

		return isoMessageValue;
	}

	/**
	 * getMessageDataType.
	 * 
	 * @return String
	 */
	public String getMessageDataType() {

		return messageDataType;
	}

	/**
	 * getMessageShortAlias.
	 * 
	 * @return String
	 */
	public String getMessageShortAlias() {

		return messageShortAlias;
	}

	/**
	 * setDataLength.
	 * 
	 * @param dataLength 
	 * 
	 * @return void
	 */
	public void setDataLength(int dataLength) {

		this.dataLength = dataLength;
	}

	/**
	 * setIsoByteValue.
	 * 
	 * @param isoByteValue 
	 * 
	 * @return void
	 */
	public void setIsoByteValue(byte[] isoByteValue) {

		this.isoByteValue = isoByteValue;
	}

	/**
	 * setIsoDataElement.
	 * 
	 * @param isoDataElement 
	 * 
	 * @return void
	 */
	public void setIsoDataElement(String isoDataElement) {

		this.isoDataElement = isoDataElement;
	}

	/**
	 * setIsoFormatTypeDescr.
	 * 
	 * @param isoFormatTypeDescr 
	 * 
	 * @return void
	 */
	public void setIsoFormatTypeDescr(String isoFormatTypeDescr) {

		this.isoFormatTypeDescr = isoFormatTypeDescr;
	}

	/**
	 * setIsoMessageSortOrder.
	 * 
	 * @param isoMessageSortOrder 
	 * 
	 * @return void
	 */
	public void setIsoMessageSortOrder(int isoMessageSortOrder) {

		this.isoMessageSortOrder = isoMessageSortOrder;
	}

	/**
	 * setIsoMessageValue.
	 * 
	 * @param isoMessageValue 
	 * 
	 * @return void
	 */
	public void setIsoMessageValue(String isoMessageValue) {

		this.isoMessageValue = isoMessageValue;
	}

	/**
	 * setMessageDataType.
	 * 
	 * @param messageDataType 
	 * 
	 * @return void
	 */
	public void setMessageDataType(String messageDataType) {

		this.messageDataType = messageDataType;
	}

	/**
	 * setMessageShortAlias.
	 * 
	 * @param messageShortAlias 
	 * 
	 * @return void
	 */
	public void setMessageShortAlias(String messageShortAlias) {

		this.messageShortAlias = messageShortAlias;
	}

}
