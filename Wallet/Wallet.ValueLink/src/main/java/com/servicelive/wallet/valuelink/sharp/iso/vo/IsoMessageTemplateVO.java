package com.servicelive.wallet.valuelink.sharp.iso.vo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.servicelive.wallet.valuelink.sharp.iso.IsoBitmapSupport;

// TODO: Auto-generated Javadoc
/**
 * Class IsoMessageTemplateVO.
 */
public class IsoMessageTemplateVO implements Comparator<IsoMessageGenericRecordVO>, Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** isoMessageGenericRecordList. */
	private List<IsoMessageGenericRecordVO> isoMessageGenericRecordList;

	/** messageTypeIdentifier. */
	private String messageTypeIdentifier = "";
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(IsoMessageGenericRecordVO f1, IsoMessageGenericRecordVO f2) {

		if (f1.getIsoMessageSortOrder() > f2.getIsoMessageSortOrder()) {
			return 1;
		} else {
			return -1;
		}

	}

	/**
	 * format.
	 * 
	 * @return String
	 */
	public String format() {

		removeEmptyFields();
		Collections.sort(isoMessageGenericRecordList, this);
		Iterator<IsoMessageGenericRecordVO> itr = isoMessageGenericRecordList.iterator();
		String formatString = "";
		while (itr.hasNext()) {
			IsoMessageGenericRecordVO imgr = (IsoMessageGenericRecordVO) itr.next();
			String str = formatValue(imgr);
			formatString = formatString + str;
		}
		return formatString;
	}

	/**
	 * formatAlphaNumeric.
	 * 
	 * @param inputStr 
	 * @param toPadLength 
	 * 
	 * @return String
	 */
	public String formatAlphaNumeric(String inputStr, int toPadLength) {

		String paddedString = "";
		// int remainingPadLength= toPadLength;
		for (int t = 0; t < toPadLength; t++) {
			paddedString = paddedString + " ";
		}
		return (inputStr + paddedString).toUpperCase();

	}

	/**
	 * formatNumeric.
	 * 
	 * @param inputStr 
	 * @param toPadLength 
	 * 
	 * @return String
	 */
	public String formatNumeric(String inputStr, int toPadLength) {

		String paddedString = "";
		for (int t = 0; t < toPadLength; t++) {
			paddedString = paddedString + "0";
		}
		return paddedString + inputStr;

	}

	/**
	 * formatValue.
	 * 
	 * @return byte[]
	 * 
	 * @throws StringIndexOutOfBoundsException 
	 * @throws IOException 
	 */
	public byte[] formatValue() throws StringIndexOutOfBoundsException, IOException {

		removeEmptyFields();
		ByteArrayOutputStream baus = new ByteArrayOutputStream();
		Collections.sort(isoMessageGenericRecordList, this);

		String formatString = "";
		for (int r = 0; r < isoMessageGenericRecordList.size(); r++) {
			IsoMessageGenericRecordVO imgr = (IsoMessageGenericRecordVO) isoMessageGenericRecordList.get(r);
			if (r < 5 || r == isoMessageGenericRecordList.size() - 1) {
				// baus.write(imgr.getIsoByteValue()) ;
			} else {
				String str = formatValue(imgr);
				baus.write(str.getBytes());
				formatString = formatString + str;
			}

		}

		return baus.toByteArray();
	}

	/**
	 * formatValue.
	 * 
	 * @param isoMessageGenericRecord 
	 * 
	 * @return String
	 * 
	 * @throws StringIndexOutOfBoundsException 
	 */
	public String formatValue(IsoMessageGenericRecordVO isoMessageGenericRecord) throws StringIndexOutOfBoundsException {

		String str = isoMessageGenericRecord.getIsoMessageValue();
		if (str == null) str = "";
		String numberStr = "";

		if (isoMessageGenericRecord.getIsoFormatTypeDescr().equals("FIXED")) {
			int definedLength = isoMessageGenericRecord.getDataLength();
			int stringLength = str.length();
			int toPadLength = 0;
			if (stringLength <= definedLength) {
				toPadLength = definedLength - stringLength;
			} else {
				throw new StringIndexOutOfBoundsException("Length of the String " + str + " exceeds the defined length of " + definedLength);
			}
			if ((isoMessageGenericRecord.getMessageDataType().equals("A")) || (isoMessageGenericRecord.getMessageDataType().equals("ANS"))) {
				str = formatAlphaNumeric(str, toPadLength);
			}
			if (isoMessageGenericRecord.getMessageDataType().equals("N")) {
				str = formatNumeric(str, toPadLength);
			}
		}
		if ((isoMessageGenericRecord.getIsoFormatTypeDescr().equals("LLVAR")) || (isoMessageGenericRecord.getIsoFormatTypeDescr().equals("LLLVAR"))) {
			if (isoMessageGenericRecord.getIsoFormatTypeDescr().equals("LLVAR")) {
				numberStr = prefixZeroForCount(str, 2);
			}
			if (isoMessageGenericRecord.getIsoFormatTypeDescr().equals("LLLVAR")) {
				numberStr = prefixZeroForCount(str, 3);
			}
			str = numberStr + str;
		}
		return str;
	}

	
	protected List<String> getUsedPCodes() {
		List<String> usedPCodes = new ArrayList<String>();
		for (int j = 0; j < isoMessageGenericRecordList.size(); j++) {
			IsoMessageGenericRecordVO isoMessageGenericRecord = (IsoMessageGenericRecordVO) isoMessageGenericRecordList.get(j);
			String code = isoMessageGenericRecord.getIsoDataElement().trim();

			if( IsoBitmapSupport.isBitmapped(code) ) {
				usedPCodes.add(code);
			}
		}
		return usedPCodes;
	}

	/**
	 * constructBitmap.
	 * 
	 * @return Creates the bitmap for the ISO message, which is based on the presence/absence of fields in the message
	 */	
	protected long constructBitmap() {
		return IsoBitmapSupport.getBitmapFromCodes(getUsedPCodes());
	}
	
	public byte[] constructBitmapByteArray() {
		return IsoBitmapSupport.convertBitmapToByteArray(constructBitmap());
	}
	
	/**
	 * getHash.
	 * 
	 * @return HashMap<String,IsoMessageGenericRecordVO>
	 */
	public HashMap<String, IsoMessageGenericRecordVO> getHash() {

		HashMap<String, IsoMessageGenericRecordVO> hashMap = new HashMap<String, IsoMessageGenericRecordVO>();
		for (int r = 0; r < isoMessageGenericRecordList.size(); r++) {
			IsoMessageGenericRecordVO mgr = (IsoMessageGenericRecordVO) isoMessageGenericRecordList.get(r);
			hashMap.put(mgr.getIsoDataElement(), mgr);
		}
		return hashMap;
	}


	/**
	 * getIsoMessageGenericRecordList.
	 * 
	 * @return List<IsoMessageGenericRecordVO>
	 */
	public List<IsoMessageGenericRecordVO> getIsoMessageGenericRecordList() {

		return isoMessageGenericRecordList;
	}

	/**
	 * getMessageTypeIdentifier.
	 * 
	 * @return String
	 */
	public String getMessageTypeIdentifier() {

		return messageTypeIdentifier;
	}

	/**
	 * prefixZeroForCount.
	 * 
	 * @param str 
	 * @param totalLength 
	 * 
	 * @return String
	 */
	private String prefixZeroForCount(String str, int totalLength) {

		String stringLengthStr = str;
		if (str != null) {
			int stringLength = str.length();
			stringLengthStr = String.valueOf(stringLength);
			int stringLengthStrLength = stringLengthStr.length();
			if ("0".equals(stringLengthStr)) {
				stringLengthStrLength = 0;
			}
			if (stringLengthStrLength < totalLength + 1) {
				while (stringLengthStrLength <= totalLength - 1) {
					stringLengthStr = "0" + stringLengthStr;
					stringLengthStrLength = stringLengthStrLength + 1;
				}

			}
		}
		return stringLengthStr;
	}

	/**
	 * removeEmptyFields.
	 * 
	 * @return void
	 */
	private void removeEmptyFields() {

		for (int i = 0; i < isoMessageGenericRecordList.size(); i++) {
			IsoMessageGenericRecordVO isoMessageGenericRecord = (IsoMessageGenericRecordVO) isoMessageGenericRecordList.get(i);
			String temp = isoMessageGenericRecord.getIsoMessageValue();
			if (!(isoMessageGenericRecord.getIsoDataElement().equals("BITMAP"))) {
				if ((temp == null) || (temp.equals("")) || (temp.equals("00"))) {
					isoMessageGenericRecordList.remove(i);
					i--;
				}
			}
		}
	}

	/**
	 * setIsoMessageGenericRecord.
	 * 
	 * @param isoMessageGenericRecordList 
	 * 
	 * @return void
	 */
	public void setIsoMessageGenericRecord(List<IsoMessageGenericRecordVO> isoMessageGenericRecordList) {

		this.isoMessageGenericRecordList = isoMessageGenericRecordList;
	}

	/**
	 * setMessageTypeIdentifier.
	 * 
	 * @param messageTypeIdentifier 
	 * 
	 * @return void
	 */
	public void setMessageTypeIdentifier(String messageTypeIdentifier) {

		this.messageTypeIdentifier = messageTypeIdentifier;
	}

	/**
	 * sort.
	 * 
	 * @return void
	 */
	public void sort() {

		Collections.sort(isoMessageGenericRecordList, this);

	}
}
