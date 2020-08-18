package com.servicelive.wallet.valuelink.sharp.iso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageGenericRecordVO;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageTemplateVO;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageVO;

// TODO: Auto-generated Javadoc
/**
 * Class IsoMessageProcessor.
 */
public abstract class IsoMessageProcessor {

	/** END_DELIMITER. */
	private final int END_DELIMITER = 0x03;

	/** START_DELIMITER. */
	private final int START_DELIMITER = 0x02;

	/**
	 * checkTwoDigitFormat.
	 * 
	 * @param input 
	 * 
	 * @return String
	 */
	private String checkTwoDigitFormat(String input) {

		if (input.length() != 2) {
			input = "0" + input;
		}

		return input;
	}

	/**
	 * getCurrenyWithoutDecimal.
	 * 
	 * @param currencyWithDecimal 
	 * 
	 * @return String
	 */
	public String getCurrenyWithoutDecimal(double currencyWithDecimal) {

		double myDoubleRounded = MoneyUtil.getRoundedMoney(currencyWithDecimal);
		double myDouble = MoneyUtil.getRoundedMoney(myDoubleRounded * 100);
		int myInt = new Double(myDouble).intValue();
		String tempString = String.valueOf(myInt);
		return tempString;
	}

	/**
	 * getRequestMessageIdentifier.
	 * 
	 * @param responseMessage 
	 * 
	 * @return String
	 */
	protected String getRequestMessageIdentifier(String responseMessage) {

		String messageIdentifier = "";
		int partialExists = responseMessage.indexOf("PARTIALLY");

		if (responseMessage != null) {
			if (partialExists > 0) {
				messageIdentifier = CommonConstants.REDEMPTION_RESPONSE_PARTIAL;
				return messageIdentifier;
			} else if (responseMessage.substring(4, 7).equals("NMR")) {
				messageIdentifier = CommonConstants.SHARP_HEARTBEAT_RESPONSE;
				
			} else if (responseMessage.substring(4, 7).equals("NMQ")) {
				messageIdentifier = CommonConstants.SHARP_HEARTBEAT_REQUEST;

			} else if (responseMessage.substring(4, 7).equals("REJ")) {
				messageIdentifier = CommonConstants.REJECTION_RESPONSE;
				
			} else if (responseMessage.substring(37, 39).equals("15")) {			
				if (responseMessage.substring(4, 7).equals("GCR")) {
					messageIdentifier = CommonConstants.ACTIVATION_RELOAD_RESPONSE;
				} else if (responseMessage.substring(4, 7).equals("GCQ")) {
					messageIdentifier = CommonConstants.ACTIVATION_RELOAD_REQUEST;
				}
			} else if (responseMessage.substring(37, 39).equals("10")) {
				if (responseMessage.substring(4, 7).equals("GCR")) {
					messageIdentifier = CommonConstants.REDEMPTION_RESPONSE;
				} else if (responseMessage.substring(4, 7).equals("GCQ")) {
					messageIdentifier = CommonConstants.REDEMPTION_REQUEST;
				}
			} else if (responseMessage.substring(37, 39).equals("17")) {
				if (responseMessage.substring(4, 7).equals("GCR")) {
					messageIdentifier = CommonConstants.BALANCE_ENQUIRY_RESPONSE;
				} else if (responseMessage.substring(4, 7).equals("GCQ")) {
					messageIdentifier = CommonConstants.BALANCE_ENQUIRY_REQUEST;
				}
			} else if (responseMessage.substring(37, 39).equals("14")) {
				if (responseMessage.substring(4, 7).equals("GCR")) {
					messageIdentifier = CommonConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID_RESPONSE;
				} else if (responseMessage.substring(4, 7).equals("GCQ")) {
					messageIdentifier = CommonConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID;
				}

			} else if (responseMessage.substring(37, 39).equals("23")) {
				if (responseMessage.substring(4, 7).equals("GCR")) {
					messageIdentifier = CommonConstants.BALANCE_ADJUSTMENT_RELOAD_RESPONSE;
				} else if (responseMessage.substring(4, 7).equals("GCQ")) {
					messageIdentifier = CommonConstants.BALANCE_ADJUSTMENT_RELOAD_REQUEST;
				}

			} else if (responseMessage.substring(37, 39).equals("24")) {
				if (responseMessage.substring(4, 7).equals("GCR")) {
					messageIdentifier = CommonConstants.BALANCE_ADJUSTMENT_REDEEM_RESPONSE;
				} else if (responseMessage.substring(4, 7).equals("GCQ")) {
					messageIdentifier = CommonConstants.BALANCE_ADJUSTMENT_REDEEM_REQUEST;
				}

			}
		}
		return messageIdentifier;
	}

	/**
	 * getResponeMessageIdentifier.
	 * 
	 * @param responseMessage 
	 * 
	 * @return String
	 */
	protected String getResponeMessageIdentifier(String responseMessage) {

		String messageIdentifier = "";
		if (responseMessage != null) {
			if (responseMessage.substring(4, 7).equals("NMR")) {
				messageIdentifier = CommonConstants.SHARP_HEARTBEAT_RESPONSE;
			}
			if (responseMessage.substring(4, 7).equals("NMQ")) {
				messageIdentifier = CommonConstants.SHARP_HEARTBEAT_REQUEST;

			} else if (responseMessage.substring(37, 39).equals("15")) {
				if (responseMessage.substring(4, 7).equals("GCR")) {
					messageIdentifier = CommonConstants.ACTIVATION_RELOAD_RESPONSE;
				} else if (responseMessage.substring(4, 7).equals("GCQ")) {
					messageIdentifier = CommonConstants.ACTIVATION_RELOAD_REQUEST;
				}
			} else if (responseMessage.substring(37, 39).equals("10")) {
				if (responseMessage.substring(4, 7).equals("GCR")) {
					messageIdentifier = CommonConstants.REDEMPTION_RESPONSE;
				} else if (responseMessage.substring(4, 7).equals("GCQ")) {
					messageIdentifier = CommonConstants.REDEMPTION_REQUEST;
				}
			} else if (responseMessage.substring(37, 39).equals("17")) {
				if (responseMessage.substring(4, 7).equals("GCR")) {
					messageIdentifier = CommonConstants.BALANCE_ENQUIRY_RESPONSE;
				} else if (responseMessage.substring(4, 7).equals("GCQ")) {
					messageIdentifier = CommonConstants.BALANCE_ENQUIRY_REQUEST;
				}
			} else if (responseMessage.substring(37, 39).equals("14")) {
				if (responseMessage.substring(4, 7).equals("GCR")) {
					messageIdentifier = CommonConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID_RESPONSE;
				} else if (responseMessage.substring(4, 7).equals("GCQ")) {
					messageIdentifier = CommonConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID;
				}

			} else if (responseMessage.substring(37, 39).equals("23")) {
				if (responseMessage.substring(4, 7).equals("GCR")) {
					messageIdentifier = CommonConstants.BALANCE_ADJUSTMENT_RELOAD_RESPONSE;
				} else if (responseMessage.substring(4, 7).equals("GCQ")) {
					messageIdentifier = CommonConstants.BALANCE_ADJUSTMENT_RELOAD_REQUEST;
				}

			} else if (responseMessage.substring(37, 39).equals("24")) {
				if (responseMessage.substring(4, 7).equals("GCR")) {
					messageIdentifier = CommonConstants.BALANCE_ADJUSTMENT_REDEEM_RESPONSE;
				} else if (responseMessage.substring(4, 7).equals("GCQ")) {
					messageIdentifier = CommonConstants.BALANCE_ADJUSTMENT_REDEEM_REQUEST;
				}

			}
		}
		return messageIdentifier;
	}

	/**
	 * getTimeStamp.
	 * 
	 * @return String
	 */
	public String getTimeStamp() {

		Calendar calendar = new GregorianCalendar();
		int month = calendar.get(Calendar.MONTH) + 1;
		String date =
			checkTwoDigitFormat(String.valueOf(calendar.get(Calendar.YEAR)).substring(2)) 
			+ checkTwoDigitFormat(String.valueOf(month)) 
			+ checkTwoDigitFormat(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		String time =
			checkTwoDigitFormat(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))) 
			+ checkTwoDigitFormat(String.valueOf(calendar.get(Calendar.MINUTE)))
			+ checkTwoDigitFormat(String.valueOf(calendar.get(Calendar.SECOND)));
		return date + time;
	}

	/**
	 * prefixZero.
	 * 
	 * @param str 
	 * @param totalLength 
	 * 
	 * @return String
	 */
	protected String prefixZero(String str, int totalLength) {

		String stringLengthMyStr = str;
		if (str != null) {
			int stringLength = str.length();
			int toPrefixLength = totalLength - stringLength;
			if (toPrefixLength > 0) {
				while (toPrefixLength != 0) {
					stringLengthMyStr = "0" + stringLengthMyStr;
					toPrefixLength--;
				}
			}
		}
		return stringLengthMyStr;
	}

	/**
	 * processFooterData.
	 * 
	 * @param byteArrayOutputStream 
	 * 
	 * @return void
	 */
	public void processFooterData(ByteArrayOutputStream byteArrayOutputStream) {

		byteArrayOutputStream.write(END_DELIMITER);
	}

	/**
	 * processHeaderData.
	 * 
	 * @param isoRequestVO 
	 * @param isoMessagetemplate 
	 * @param byteArrayOutputStream 
	 * 
	 * @return void
	 * 
	 * @throws IOException 
	 */
	public void processHeaderData(IsoMessageVO isoRequestVO, IsoMessageTemplateVO isoMessagetemplate, ByteArrayOutputStream byteArrayOutputStream) throws IOException {

		HashMap<String, IsoMessageGenericRecordVO> isoMessageHash = isoMessagetemplate.getHash();

		byteArrayOutputStream.write(START_DELIMITER);
		IsoMessageGenericRecordVO startDelimiter2MessageGenericRecord = (IsoMessageGenericRecordVO) isoMessageHash.get("START_DELIMITER_2");
		byteArrayOutputStream.write(startDelimiter2MessageGenericRecord.getIsoMessageValue().getBytes());
		IsoMessageGenericRecordVO cardTypeMessageGenericRecord = (IsoMessageGenericRecordVO) isoMessageHash.get("CARD_TYPE");
		byteArrayOutputStream.write(cardTypeMessageGenericRecord.getIsoMessageValue().getBytes());
		IsoMessageGenericRecordVO mtiMessageGenericRecord = (IsoMessageGenericRecordVO) isoMessageHash.get("MTI");
		if (isoRequestVO.isResendRequest() == true) {
			String resentRequest = "1201";
			byteArrayOutputStream.write(resentRequest.getBytes());
		} else {
			byteArrayOutputStream.write(mtiMessageGenericRecord.getIsoMessageValue().getBytes());
		}
		byte [] bitmapByteArray = isoMessagetemplate.constructBitmapByteArray();
		if( bitmapByteArray.length != 8 ) {
			throw new IOException("bitmap must be exactly 8 bytes");
		}
		byteArrayOutputStream.write(bitmapByteArray);
	}

}
