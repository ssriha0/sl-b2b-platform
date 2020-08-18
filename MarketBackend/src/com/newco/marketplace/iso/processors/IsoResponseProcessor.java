package com.newco.marketplace.iso.processors;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.iso.IIsoResponseProcessor;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.dto.vo.iso.IsoMessageGenericRecord;
import com.newco.marketplace.dto.vo.iso.IsoMessageTemplateVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.StringParseException;
import com.newco.marketplace.interfaces.FullfillmentConstants;
import com.newco.marketplace.iso.sharp.simulator.SharpSimulator;
import com.newco.marketplace.persistence.iDao.iso.IsoMessageTemplateDao;

/**
 * @author Siva
 * ISoResponseProcessor is the translator to convert the incoming message to FullfillmentEntryVO object
 */
		
public class IsoResponseProcessor extends IsoMessageProcessor implements IIsoResponseProcessor{
private IsoMessageTemplateDao isoMessageTemplateDao ;
private static final Logger logger = Logger.getLogger(IsoResponseProcessor.class);
/**
 * This method is the interface to the caller to convert the text message to VO 
 * @param responeMessage
 */
public FullfillmentEntryVO processResponse(byte[] responseByteMessage) throws StringParseException{
		String responseMessage = new String (responseByteMessage);  
		IsoMessageTemplateVO isoMessageTemplateVO = loadResponseMessage(responseMessage);
		FullfillmentEntryVO fullfillmentEntryVO = new FullfillmentEntryVO();
		processSpecificData(fullfillmentEntryVO, isoMessageTemplateVO);
		logger.debug("Response Processed.");
	
	return fullfillmentEntryVO;
}

/*
 * This method processes specific data elements that are not handled by the template   
 * @param fullfillmentEntryVO
 * @param isoMessagetemplate
 */

public void processSpecificData(FullfillmentEntryVO fullfillmentEntryVO,IsoMessageTemplateVO isoMessagetemplate) throws StringParseException{
	constructFulfillmentEntry(fullfillmentEntryVO, isoMessagetemplate);
}

/**
 * This method constructs the specific elements of FullfillmentEntryVO
 * 
 * @param fullfillmentEntryVO
 * @param isoMessageTemplateVO
 * @return FullfillmentEntryVO
 */
private FullfillmentEntryVO constructFulfillmentEntry(FullfillmentEntryVO fullfillmentEntryVO,IsoMessageTemplateVO isoMessageTemplateVO )
					throws StringParseException{
	HashMap<String, IsoMessageGenericRecord> hashMap = isoMessageTemplateVO.getHash();
	handleP2(hashMap, fullfillmentEntryVO);
	handleP4(hashMap, fullfillmentEntryVO);
	handleP5(hashMap, fullfillmentEntryVO);
	handleP11(hashMap, fullfillmentEntryVO);
	handleP12(hashMap, fullfillmentEntryVO);
	handleP38(hashMap, fullfillmentEntryVO);
	handleP39(hashMap, fullfillmentEntryVO);
	handleP41(hashMap, fullfillmentEntryVO);
	handleP44(hashMap, fullfillmentEntryVO);
	handleP54(hashMap, fullfillmentEntryVO);
	handleP63(hashMap, fullfillmentEntryVO);	
	
	return fullfillmentEntryVO;
}


private void handleP11(HashMap<String, IsoMessageGenericRecord> hashMap, FullfillmentEntryVO fullfillmentEntryVO){
IsoMessageGenericRecord p11IsoMessageGenericRecord = (IsoMessageGenericRecord)hashMap.get("P11");
if (p11IsoMessageGenericRecord!=null) {
	String stan = p11IsoMessageGenericRecord.getIsoMessageValue();
	fullfillmentEntryVO.setStanId(stan);
}
}

private void handleP12(HashMap<String, IsoMessageGenericRecord> hashMap, FullfillmentEntryVO fullfillmentEntryVO){
	IsoMessageGenericRecord p12IsoMessageGenericRecord = (IsoMessageGenericRecord)hashMap.get("P12");
	if (p12IsoMessageGenericRecord!=null) {
		fullfillmentEntryVO.setTimeStamp(p12IsoMessageGenericRecord.getIsoMessageValue());
	}
}

private void handleP63(HashMap<String, IsoMessageGenericRecord> hashMap, FullfillmentEntryVO fullfillmentEntryVO) throws StringParseException{
IsoMessageGenericRecord p63IsoMessageGenericRecord = (IsoMessageGenericRecord)hashMap.get("P63");
if (p63IsoMessageGenericRecord!=null) {
	parseP63(fullfillmentEntryVO,p63IsoMessageGenericRecord.getIsoMessageValue());
}
}
private void handleP2(HashMap<String, IsoMessageGenericRecord> hashMap, FullfillmentEntryVO fullfillmentEntryVO){
IsoMessageGenericRecord p2IsoMessageGenericRecord = (IsoMessageGenericRecord)hashMap.get("P2");
if(p2IsoMessageGenericRecord!=null){
	fullfillmentEntryVO.setPrimaryAccountNumber(new Long(p2IsoMessageGenericRecord.getIsoMessageValue()));
}
}
private void handleP4(HashMap<String, IsoMessageGenericRecord> hashMap, FullfillmentEntryVO fullfillmentEntryVO){
IsoMessageGenericRecord p4IsoMessageGenericRecord = (IsoMessageGenericRecord)hashMap.get("P4");
if(p4IsoMessageGenericRecord!=null){
Double d = new Double(p4IsoMessageGenericRecord.getIsoMessageValue());
fullfillmentEntryVO.setTransAmount(d/100);
}
}
	private void handleP5(HashMap<String, IsoMessageGenericRecord> hashMap, FullfillmentEntryVO fullfillmentEntryVO){
		IsoMessageGenericRecord p5IsoMessageGenericRecord = (IsoMessageGenericRecord)hashMap.get("P5");
		if(p5IsoMessageGenericRecord!=null){
			Double d = new Double(p5IsoMessageGenericRecord.getIsoMessageValue());
			fullfillmentEntryVO.setPartiallyAppvdAmount(d/100);
		}
	}
private void handleP38(HashMap<String, IsoMessageGenericRecord> hashMap, FullfillmentEntryVO fullfillmentEntryVO){
IsoMessageGenericRecord p38IsoMessageGenericRecord = (IsoMessageGenericRecord)hashMap.get("P38");
if(p38IsoMessageGenericRecord!=null) {
fullfillmentEntryVO.setAuthorizationId(p38IsoMessageGenericRecord.getIsoMessageValue());
}
}
private void handleP39(HashMap<String, IsoMessageGenericRecord> hashMap, FullfillmentEntryVO fullfillmentEntryVO){

IsoMessageGenericRecord p39IsoMessageGenericRecord = (IsoMessageGenericRecord)hashMap.get("P39");
if(p39IsoMessageGenericRecord!=null) {
	fullfillmentEntryVO.setActionCode(p39IsoMessageGenericRecord.getIsoMessageValue());
}
}
private void handleP41(HashMap<String, IsoMessageGenericRecord> hashMap, FullfillmentEntryVO fullfillmentEntryVO){

IsoMessageGenericRecord p41IsoMessageGenericRecord = (IsoMessageGenericRecord)hashMap.get("P41");
if(p41IsoMessageGenericRecord!=null) {
fullfillmentEntryVO.setStoreDeviceNumber(p41IsoMessageGenericRecord.getIsoMessageValue());
}
}
private void handleP44(HashMap<String, IsoMessageGenericRecord> hashMap, FullfillmentEntryVO fullfillmentEntryVO){

IsoMessageGenericRecord p44IsoMessageGenericRecord = (IsoMessageGenericRecord)hashMap.get("P44");
if(p44IsoMessageGenericRecord!=null) {
	fullfillmentEntryVO.setAdditionalResponse(p44IsoMessageGenericRecord.getIsoMessageValue());
}
}
private void handleP54(HashMap<String, IsoMessageGenericRecord> hashMap, FullfillmentEntryVO fullfillmentEntryVO){

IsoMessageGenericRecord p54IsoMessageGenericRecord = (IsoMessageGenericRecord)hashMap.get("P54");
if(p54IsoMessageGenericRecord!=null) {
	fullfillmentEntryVO.setAdditionalAmount(p54IsoMessageGenericRecord.getIsoMessageValue());
}
}

/**
 * This method constructs fullfillmentEntryVO from P-63 field. P-63 has the following
 * structure:
 * PCNNNNNNNNANNNNNNNNNNNNNNNNLANNNNNNNNNNNNNNNFGNNNNNNNNNNNNNNN
 * where PC stands for Promo Code
 * AN stands for Account Number
 * LA stands for Ledger Account Number and 
 * the N's are numeric values right justified with leading 0's
 * 
 * @param fullfillmentEntryVO
 * @param string
 */
private void parseP63(FullfillmentEntryVO fullfillmentEntryVO,String string )throws StringParseException {
	logger.info(string);
	checkP63(string);
	while(!string.equals("")) {
	String firstTwo = string.substring(0, 2);
	if (firstTwo.equals(FullfillmentConstants.SHARP_P63_PROMO_CODE)) {
		String promoCode = string.substring(2, 10);
		fullfillmentEntryVO.setPromoCode(promoCode);
		string = string.substring(10);
	}
	else if(firstTwo.equals(FullfillmentConstants.SHARP_P63_ACCOUNT_NUMBER)) {
		String ledgerEntityId = string.substring(2, 18);
		fullfillmentEntryVO.setLedgerEntityId(new Long(ledgerEntityId));
		string = string.substring(18);

	}
	else if(firstTwo.equals(FullfillmentConstants.SHARP_P63_LEDGER_ACCOUNT)){
		String ledgerAccount = string.substring(2, 18);
		fullfillmentEntryVO.setFullfillmentEntryId(new Long(ledgerAccount));
		string = string.substring(18);
	}
	else if(firstTwo.equals(FullfillmentConstants.SHARP_P63_FULFILLMENT_GROUP)){
		String fulfillmentGroupId = string.substring(2, 17);
		fullfillmentEntryVO.setFullfillmentGroupId(new Long(fulfillmentGroupId));
		string = string.substring(17);
	}
	}
}

private boolean checkP63(String string) throws StringParseException{
	String ss = string.substring(0, 2);
	boolean flag = true;
	if (ss.equals(FullfillmentConstants.SHARP_P63_PROMO_CODE) || ss.equals(FullfillmentConstants.SHARP_P63_ACCOUNT_NUMBER) ||
			ss.equals(FullfillmentConstants.SHARP_P63_LEDGER_ACCOUNT ) ||ss.equals(FullfillmentConstants.SHARP_P63_FULFILLMENT_GROUP))  {
	// Don't do anything
	}
	else
	{
		flag = false;
		throw new StringParseException("Unexpected P63 Format");
	}
	return flag;
	
	
}
/**
 * This method received the incoming message and loads to an object structure with the help of 
 * a defined template. 
 *  
 * @param responseMessage
 * @return
 */
private IsoMessageTemplateVO loadResponseMessage(String responseMessage){
	IsoMessageTemplateVO isoMessageTemplate =null;
	boolean b54Missing = false;
	int i54RemoveIndex = 0;
	try {
		//Find out the response type
		logger.info("loadResponseMessage-->responseMessage()-->" + responseMessage);
		String messageIdentifier = getRequestMessageIdentifier(responseMessage);
		if(messageIdentifier==null || messageIdentifier.equals(""))
			logger.info("**************** MESSAGE IDENTIFER IS EMPTY **********************" + messageIdentifier);
		isoMessageTemplate = (IsoMessageTemplateVO)isoMessageTemplateDao.getIsoMessageTemplate(messageIdentifier);
		isoMessageTemplate.sort();
		ArrayList<IsoMessageGenericRecord> al = (ArrayList<IsoMessageGenericRecord>)isoMessageTemplate.getIsoMessageGenericRecordList();
		int startInd = 0;
		int endInd = 0;
		for(int i=0;i<al.size();i++){
			IsoMessageGenericRecord isoMessageGenericRecord = (IsoMessageGenericRecord)al.get(i);
			if("P54".equals(isoMessageGenericRecord.getIsoDataElement()) && b54Missing){
				i54RemoveIndex = i;
				continue;
			}
			int length = isoMessageGenericRecord.getDataLength();
			String format = isoMessageGenericRecord.getIsoFormatTypeDescr();
			
			
			if(format.equals("LLVAR") || format.equals("LLLVAR")) 
			{
				int[] data = getFormatTypedData (responseMessage,format,startInd );
				startInd = data[0];
				length = data[1];
			}
			endInd=startInd+length;
			String temp = responseMessage.substring(startInd, endInd);
			startInd=endInd;
			isoMessageGenericRecord.setIsoMessageValue(temp);

			if("P39".equals(isoMessageGenericRecord.getIsoDataElement())){
				//Map<String, Object> returnMap = fullfillmentDao.getActionCodeId(temp);
				//status = (Boolean)returnMap.get("status");
				if("000".equals(temp) || "001".equals(temp) || "002".equals(temp)|| "080".equals(temp)|| "081".equals(temp) || "082".equals(temp)){
					b54Missing = false;
				}
				else{
					b54Missing = true;
				}
				
			}
			
		}	
		if(i54RemoveIndex != 0){
			al.remove(i54RemoveIndex);
		}
		
		
	}catch(DataServiceException dae){

        logger.info("Exception in loadResponseMessage" + responseMessage);
        logger.error("Exception in loadResponseMessage" , dae);
	}
	
	return isoMessageTemplate;
}


/**
 * This method parses the message to find out the starting indicator and the 
 * length of the string 
 * @param responseMessage
 * @param formatType
 * @param startIndex
 * @return int[] - returns two int - Start Indicator and the length of the string 
 */
private int[] getFormatTypedData(String responseMessage, String formatType, int startIndex){
	int startInd = 0;
	int endInd = 0;
	int data[] = new int[2];
	if (formatType.equals("LLVAR"))
	{
		endInd = startIndex+2;
		startInd = startIndex+2;
	}
	else if (formatType.equals("LLLVAR")) 
	{
		endInd = startIndex+3;
		startInd = startIndex+3;
		
	}
	int length = new Integer(responseMessage.substring(startIndex, endInd)).intValue();
	data[0] = startInd;
	data[1] = length;
	return data;

}

public static void main(String k[]){
	
	try {
		
		ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();
		IsoResponseProcessor isoMessageProcessor  = (IsoResponseProcessor)applicationContext.getBean("isoResponseProcessor");
		SharpSimulator ss = new SharpSimulator();
		byte b[]=ss.respondNMR();
		isoMessageProcessor.processResponse(b);
	} catch (Exception e) {
		e.printStackTrace();
	}

}

public IsoMessageTemplateDao getIsoMessageTemplateDao() {
	return isoMessageTemplateDao;
}

public void setIsoMessageTemplateDao(IsoMessageTemplateDao isoMessageTemplateDao) {
	this.isoMessageTemplateDao = isoMessageTemplateDao;
}

}
