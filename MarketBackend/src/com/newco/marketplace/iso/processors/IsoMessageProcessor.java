package com.newco.marketplace.iso.processors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.dto.vo.iso.IsoMessageGenericRecord;
import com.newco.marketplace.dto.vo.iso.IsoMessageTemplateVO;
import com.newco.marketplace.interfaces.FullfillmentConstants;
import com.newco.marketplace.util.Bitmapper;
import com.newco.marketplace.utils.MoneyUtil;

public abstract class IsoMessageProcessor {
	private  final int END_DELIMITER = 0x03;
	private  final int START_DELIMITER = 0x02;
	
	public void processHeaderData(FullfillmentEntryVO fullfillmentEntryVO, IsoMessageTemplateVO isoMessagetemplate, ByteArrayOutputStream byteArrayOutputStream) throws IOException{
		HashMap<String, IsoMessageGenericRecord> isoMessageHash = isoMessagetemplate.getHash();
		
	/*	IsoMessageGenericRecord startDelimiter1MessageGenericRecord = (IsoMessageGenericRecord)isoMessageHash.get("START_DELIMITER_1");
		startDelimiter1MessageGenericRecord.setIsoByteValue(END_DELIMITER );
		IsoMessageGenericRecord endDelimiter1MessageGenericRecord = (IsoMessageGenericRecord)isoMessageHash.get("END_DELIMITER");
		endDelimiter1MessageGenericRecord.setIsoByteValue(endDelimiter1MessageGenericRecord.getIsoMessageValue().getBytes());
	*/
		byteArrayOutputStream.write(START_DELIMITER);
		IsoMessageGenericRecord startDelimiter2MessageGenericRecord = (IsoMessageGenericRecord)isoMessageHash.get("START_DELIMITER_2");
		byteArrayOutputStream.write(startDelimiter2MessageGenericRecord.getIsoMessageValue().getBytes());
		IsoMessageGenericRecord cardTypeMessageGenericRecord = (IsoMessageGenericRecord)isoMessageHash.get("CARD_TYPE");
		byteArrayOutputStream.write(cardTypeMessageGenericRecord.getIsoMessageValue().getBytes());
		IsoMessageGenericRecord mtiMessageGenericRecord = (IsoMessageGenericRecord)isoMessageHash.get("MTI");
		if(fullfillmentEntryVO.isResendRequest()==true) {
			String resentRequest = "1201";
			byteArrayOutputStream.write(resentRequest.getBytes());
		}
		else{
			byteArrayOutputStream.write(mtiMessageGenericRecord.getIsoMessageValue().getBytes());
		}
		String bitmapStr = isoMessagetemplate.constructBitmap();
		//byte[] byteVal = Bitmapper.getBinaryBitmap(bitmapStr,byteArrayOutputStream);
		String byteVal = Bitmapper.getHexValueFromBits(bitmapStr,byteArrayOutputStream);
		
		//bitmapMessageGenericRecord.setIsoByteValue(byteVal);
	//	byteArrayOutputStream.write(byteVal);
		
		
	}
	
	public void processFooterData(ByteArrayOutputStream byteArrayOutputStream){
		
		byteArrayOutputStream.write(END_DELIMITER);
	}
	public String getCurrenyWithoutDecimal(double currencyWithDecimal){

		double myDoubleRounded = MoneyUtil.getRoundedMoney(currencyWithDecimal);
		double myDouble = MoneyUtil.getRoundedMoney(myDoubleRounded  * 100);
		int myInt = new Double(myDouble).intValue();
		String tempString = new Integer(myInt).toString()+"";
		//tempString = tempString.substring(0,tempString.indexOf("."));
		return tempString;
	}

	protected String prefixZero(String str, int totalLength){
		String stringLengthMyStr = str;
		if (str!=null) {
		int stringLength = str.length();
		int toPrefixLength = totalLength - stringLength;
		if(toPrefixLength>0){
			while (toPrefixLength!=0){
				stringLengthMyStr="0"+stringLengthMyStr;
				toPrefixLength--;
			}
		}
		}
		return stringLengthMyStr;
	}

	
	public String getTimeStamp(){
	Calendar calendar = new GregorianCalendar();
	 int month = calendar.get(Calendar.MONTH)+1;
 	 String date = checkTwoDigitFormat( (""+calendar.get(Calendar.YEAR)).substring(2) )+checkTwoDigitFormat(""+month)+
 	 							checkTwoDigitFormat(""+calendar.get(Calendar.DAY_OF_MONTH));
 	  String time = checkTwoDigitFormat(""+calendar.get(Calendar.HOUR_OF_DAY))+checkTwoDigitFormat(""+calendar.get(Calendar.MINUTE))+
 	  							checkTwoDigitFormat(""+calendar.get(Calendar.SECOND));
	return date+time;
	}
	
	protected String getRequestMessageIdentifier(String responseMessage){
		String messageIdentifier ="";
		int partialExists = responseMessage.indexOf("PARTIALLY");

		if (responseMessage!=null) {
			if (partialExists>0 ) {
				messageIdentifier = FullfillmentConstants.REDEMPTION_RESPONSE_PARTIAL;
				return messageIdentifier;	
			}
			if (responseMessage.substring(4,7).equals("NMR")) {
					messageIdentifier=FullfillmentConstants.SHARP_HEARTBEAT_RESPONSE;
			}
			if (responseMessage.substring(4,7).equals("NMQ")) {
				messageIdentifier=FullfillmentConstants.SHARP_HEARTBEAT_REQUEST;
				
			}
			else if(responseMessage.substring(37,39).equals("15")) {
				if (responseMessage.substring(4,7).equals("GCR")) {
					messageIdentifier=FullfillmentConstants.ACTIVATION_RELOAD_RESPONSE;
				}
				else if (responseMessage.substring(4,7).equals("GCQ")) {
					messageIdentifier=FullfillmentConstants.ACTIVATION_RELOAD_REQUEST;
				}
			}
			else if(responseMessage.substring(37,39).equals("10"))
			{
				if (responseMessage.substring(4,7).equals("GCR")) {
					messageIdentifier=FullfillmentConstants.REDEMPTION_RESPONSE;
				}
				else if (responseMessage.substring(4,7).equals("GCQ")) {
					messageIdentifier=FullfillmentConstants.REDEMPTION_REQUEST;
				}
			}
			else if (responseMessage.substring(37,39).equals("17"))
			{
				if (responseMessage.substring(4,7).equals("GCR")) {
					messageIdentifier=FullfillmentConstants.BALANCE_ENQUIRY_RESPONSE;
				}
				else if (responseMessage.substring(4,7).equals("GCQ")) {
					messageIdentifier=FullfillmentConstants.BALANCE_ENQUIRY_REQUEST;
				}			
			}
			else if(responseMessage.substring(37,39).equals("14"))
			{
				if (responseMessage.substring(4,7).equals("GCR")) {
					messageIdentifier=FullfillmentConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID_RESPONSE;
				}
				else if (responseMessage.substring(4,7).equals("GCQ")) {
					messageIdentifier=FullfillmentConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID;
				}			
				
			}
			else if(responseMessage.substring(37,39).equals("23"))
			{
				if (responseMessage.substring(4,7).equals("GCR")) {
					messageIdentifier=FullfillmentConstants.BALANCE_ADJUSTMENT_RELOAD_RESPONSE;
				}
				else if (responseMessage.substring(4,7).equals("GCQ")) {
					messageIdentifier=FullfillmentConstants.BALANCE_ADJUSTMENT_RELOAD_REQUEST;
				}			
				
			}
			else if(responseMessage.substring(37,39).equals("24"))
			{
				if (responseMessage.substring(4,7).equals("GCR")) {
					messageIdentifier=FullfillmentConstants.BALANCE_ADJUSTMENT_REDEEM_RESPONSE;
				}
				else if (responseMessage.substring(4,7).equals("GCQ")) {
					messageIdentifier=FullfillmentConstants.BALANCE_ADJUSTMENT_REDEEM_REQUEST;
				}			
				
			}
		}
		return messageIdentifier;
	}
	
	protected String getResponeMessageIdentifier(String responseMessage){
		String messageIdentifier ="";
		if (responseMessage!=null) {
			if (responseMessage.substring(4,7).equals("NMR")) {
					messageIdentifier=FullfillmentConstants.SHARP_HEARTBEAT_RESPONSE;
			}
			if (responseMessage.substring(4,7).equals("NMQ")) {
				messageIdentifier=FullfillmentConstants.SHARP_HEARTBEAT_REQUEST;
				
			}
			else if(responseMessage.substring(37,39).equals("15")) {
				if (responseMessage.substring(4,7).equals("GCR")) {
					messageIdentifier=FullfillmentConstants.ACTIVATION_RELOAD_RESPONSE;
				}
				else if (responseMessage.substring(4,7).equals("GCQ")) {
					messageIdentifier=FullfillmentConstants.ACTIVATION_RELOAD_REQUEST;
				}
			}
			else if(responseMessage.substring(37,39).equals("10"))
			{
				if (responseMessage.substring(4,7).equals("GCR")) {
					messageIdentifier=FullfillmentConstants.REDEMPTION_RESPONSE;
				}
				else if (responseMessage.substring(4,7).equals("GCQ")) {
					messageIdentifier=FullfillmentConstants.REDEMPTION_REQUEST;
				}
			}
			else if (responseMessage.substring(37,39).equals("17"))
			{
				if (responseMessage.substring(4,7).equals("GCR")) {
					messageIdentifier=FullfillmentConstants.BALANCE_ENQUIRY_RESPONSE;
				}
				else if (responseMessage.substring(4,7).equals("GCQ")) {
					messageIdentifier=FullfillmentConstants.BALANCE_ENQUIRY_REQUEST;
				}			
			}
			else if(responseMessage.substring(37,39).equals("14"))
			{
				if (responseMessage.substring(4,7).equals("GCR")) {
					messageIdentifier=FullfillmentConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID_RESPONSE;
				}
				else if (responseMessage.substring(4,7).equals("GCQ")) {
					messageIdentifier=FullfillmentConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID;
				}			
				
			}
			else if(responseMessage.substring(37,39).equals("23"))
			{
				if (responseMessage.substring(4,7).equals("GCR")) {
					messageIdentifier=FullfillmentConstants.BALANCE_ADJUSTMENT_RELOAD_RESPONSE;
				}
				else if (responseMessage.substring(4,7).equals("GCQ")) {
					messageIdentifier=FullfillmentConstants.BALANCE_ADJUSTMENT_RELOAD_REQUEST;
				}			
				
			}
			else if(responseMessage.substring(37,39).equals("24"))
			{
				if (responseMessage.substring(4,7).equals("GCR")) {
					messageIdentifier=FullfillmentConstants.BALANCE_ADJUSTMENT_REDEEM_RESPONSE;
				}
				else if (responseMessage.substring(4,7).equals("GCQ")) {
					messageIdentifier=FullfillmentConstants.BALANCE_ADJUSTMENT_REDEEM_REQUEST;
				}			
				
			}
		}
		return messageIdentifier;
	}
	private String checkTwoDigitFormat(String input){
		 
		if (input.length()!=2) {
			 input="0"+input;
			 }

		return input;
	}
}
