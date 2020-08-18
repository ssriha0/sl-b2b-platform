package com.servicelive.wallet.valuelink.sharp.iso;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.valuelink.sharp.iso.dao.IIsoMessageTemplateDao;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageGenericRecordVO;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageTemplateVO;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageVO;

// TODO: Auto-generated Javadoc
/**
 * Class IsoResponseProcessor.
 */
public class IsoResponseProcessor extends IsoMessageProcessor implements IIsoResponseProcessor {

	/** logger. */
	private static final Logger logger = Logger.getLogger(IsoResponseProcessor.class);

	/** isoMessageTemplateDao. */
	private IIsoMessageTemplateDao isoMessageTemplateDao;

	/**
	 * checkP63.
	 * 
	 * @param string 
	 * 
	 * @return boolean
	 * 
	 * @throws StringParseException 
	 */
	private boolean checkP63(String string) throws StringParseException {

		String ss = string.substring(0, 2);
		boolean flag = true;
		if (ss.equals(CommonConstants.SHARP_P63_PROMO_CODE) || ss.equals(CommonConstants.SHARP_P63_ACCOUNT_NUMBER) || ss.equals(CommonConstants.SHARP_P63_LEDGER_ACCOUNT)
			|| ss.equals(CommonConstants.SHARP_P63_FULFILLMENT_GROUP)) {
			// Don't do anything
		} else {
			flag = false;
			throw new StringParseException("Unexpected P63 Format");
		}
		return flag;

	}

	/*
	 * This method processes specific data elements that are not handled by the
	 * template @param message @param isoMessagetemplate
	 */

	/**
	 * constructFulfillmentEntry.
	 * 
	 * @param message 
	 * @param isoMessageTemplateVO 
	 * 
	 * @return IsoMessageVO
	 * 
	 * @throws StringParseException 
	 */
	private IsoMessageVO constructFulfillmentEntry(IsoMessageVO message, IsoMessageTemplateVO isoMessageTemplateVO) throws StringParseException {

		HashMap<String, IsoMessageGenericRecordVO> hashMap = isoMessageTemplateVO.getHash();
		handleP2(hashMap, message);
		handleP4(hashMap, message);
		handleP5(hashMap, message);
		handleP11(hashMap, message);
		handleP12(hashMap, message);
		handleP38(hashMap, message);
		handleP39(hashMap, message);
		handleP41(hashMap, message);
		handleP44(hashMap, message);
		handleP54(hashMap, message);
		handleP63(hashMap, message);

		return message;
	}

	/**
	 * getFormatTypedData.
	 * 
	 * @param responseMessage 
	 * @param formatType 
	 * @param startIndex 
	 * 
	 * @return int[]
	 */
	private int[] getFormatTypedData(String responseMessage, String formatType, int startIndex) {

		int startInd = 0;
		int endInd = 0;
		int data[] = new int[2];
		if ("LLVAR".equals(formatType)) {
			endInd = startIndex + 2;
			startInd = startIndex + 2;
		} else if ("LLLVAR".equals(formatType)) {
			endInd = startIndex + 3;
			startInd = startIndex + 3;

		}
		int length = Integer.valueOf(responseMessage.substring(startIndex, endInd));
		data[0] = startInd;
		data[1] = length;
		return data;

	}

	/**
	 * getIsoMessageTemplateDao.
	 * 
	 * @return IIsoMessageTemplateDao
	 */
	public IIsoMessageTemplateDao getIsoMessageTemplateDao() {

		return isoMessageTemplateDao;
	}

	/**
	 * handleP11.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP11(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p11IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P11");
		if (p11IsoMessageGenericRecord != null) {
			String stan = p11IsoMessageGenericRecord.getIsoMessageValue();
			message.setStanId(stan);
		}
	}

	/**
	 * handleP12.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP12(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p12IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P12");
		if (p12IsoMessageGenericRecord != null) {
			message.setTimeStamp(p12IsoMessageGenericRecord.getIsoMessageValue());
		}
	}

	/**
	 * handleP2.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP2(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p2IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P2");
		if (p2IsoMessageGenericRecord != null) {
			message.setPrimaryAccountNumber(new Long(p2IsoMessageGenericRecord.getIsoMessageValue()));
		}
	}

	/**
	 * handleP38.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP38(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p38IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P38");
		if (p38IsoMessageGenericRecord != null) {
			message.setAuthorizationId(p38IsoMessageGenericRecord.getIsoMessageValue());
		}
	}

	/**
	 * handleP39.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP39(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p39IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P39");
		if (p39IsoMessageGenericRecord != null) {
			message.setActionCode(p39IsoMessageGenericRecord.getIsoMessageValue());
		}
	}

	/**
	 * handleP4.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP4(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p4IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P4");
		if (p4IsoMessageGenericRecord != null) {
			try {
				Double d = new Double(p4IsoMessageGenericRecord.getIsoMessageValue());
				message.setTransAmount(d / 100);
			} catch( Throwable t ) {
				message.setTransAmount(0.0d);
			}
		}
	}

	/**
	 * handleP41.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP41(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p41IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P41");
		if (p41IsoMessageGenericRecord != null) {
			message.setStoreDeviceNumber(p41IsoMessageGenericRecord.getIsoMessageValue());
		}
	}

	/**
	 * handleP44.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP44(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p44IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P44");
		if (p44IsoMessageGenericRecord != null) {
			message.setAdditionalResponse(p44IsoMessageGenericRecord.getIsoMessageValue());
		}
	}

	/**
	 * handleP5.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP5(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p5IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P5");
		if (p5IsoMessageGenericRecord != null) {
			Double d = new Double(p5IsoMessageGenericRecord.getIsoMessageValue());
			message.setPartiallyAppvdAmount(d / 100);
		}
	}

	/**
	 * handleP54.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP54(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p54IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P54");
		if (p54IsoMessageGenericRecord != null) {
			message.setAdditionalAmount(p54IsoMessageGenericRecord.getIsoMessageValue());
		}
	}

	/**
	 * handleP63.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 * 
	 * @throws StringParseException 
	 */
	private void handleP63(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) throws StringParseException {

		IsoMessageGenericRecordVO p63IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P63");
		if (p63IsoMessageGenericRecord != null) {
			parseP63(message, p63IsoMessageGenericRecord.getIsoMessageValue());
		}
	}
	
	
	
	/**
	 * loadResponseMessage.
	 * 
	 * @param responseMessage 
	 * 
	 * @return IsoMessageTemplateVO
	 * @throws SLBusinessServiceException 
	 */
	private IsoMessageTemplateVO loadResponseMessage(IsoMessageVO message, byte[] responseByteMessage) throws SLBusinessServiceException {

		String responseMessage = new String(responseByteMessage);
		long bitmap = IsoBitmapSupport.getBitmapFromMessage(responseByteMessage);
		
		IsoMessageTemplateVO isoMessageTemplate = null;

		try {
			// Find out the response type
			logger.info("loadResponseMessage-->responseMessage()-->" + responseMessage);
			String messageIdentifier = getRequestMessageIdentifier(responseMessage);	
			if (messageIdentifier == null || messageIdentifier.equals("")) {
				logger.info("**************** MESSAGE IDENTIFER IS EMPTY **********************" + messageIdentifier);
				return null;
			}
			message.setMessageIdentifier(messageIdentifier);
			
			if( CommonConstants.REJECTION_RESPONSE.equals(messageIdentifier)) {
				logger.error("**************** MESSAGE REJECTED **********************" + messageIdentifier);
				logger.error(responseMessage);
				return null;
			}
			
			isoMessageTemplate = (IsoMessageTemplateVO) isoMessageTemplateDao.getIsoMessageTemplate(messageIdentifier);
			isoMessageTemplate.sort();
			ArrayList<IsoMessageGenericRecordVO> al = (ArrayList<IsoMessageGenericRecordVO>) isoMessageTemplate.getIsoMessageGenericRecordList();
			int startInd = 0;
			int endInd = 0;
			
			for (int i = 0; i < al.size(); i++) {
				
				IsoMessageGenericRecordVO isoMessageGenericRecord = (IsoMessageGenericRecordVO) al.get(i);
				String dataElement = isoMessageGenericRecord.getIsoDataElement();
				if( IsoBitmapSupport.isBitmapped(dataElement) && 
					!IsoBitmapSupport.isBitmappedElementPresent(dataElement, bitmap) ) {
					continue;
				}
				
				int length = isoMessageGenericRecord.getDataLength();
				String format = isoMessageGenericRecord.getIsoFormatTypeDescr();

				if (format.equals("LLVAR") || format.equals("LLLVAR")) {
					int[] data = getFormatTypedData(responseMessage, format, startInd);
					startInd = data[0];
					length = data[1];
				}
				endInd = startInd + length;
				String temp = responseMessage.substring(startInd, endInd);
				startInd = endInd;
				isoMessageGenericRecord.setIsoMessageValue(temp);
			}
			
		} catch (DataServiceException dae) {
			logger.info("Exception in loadResponseMessage" + responseMessage);
			logger.error("Exception in loadResponseMessage", dae);
			throw new SLBusinessServiceException(dae);
		}

		return isoMessageTemplate;
	}

	/**
	 * parseP63.
	 * 
	 * @param message 
	 * @param string 
	 * 
	 * @return void
	 * 
	 * @throws StringParseException 
	 */
	private void parseP63(IsoMessageVO message, String string) throws StringParseException {

		logger.info(string);
		checkP63(string);
		while (!string.equals("")) {
			String firstTwo = string.substring(0, 2);
			if (firstTwo.equals(CommonConstants.SHARP_P63_PROMO_CODE)) {
				String promoCode = string.substring(2, 10);
				message.setPromoCode(promoCode);
				string = string.substring(10);
			} else if (firstTwo.equals(CommonConstants.SHARP_P63_ACCOUNT_NUMBER)) {
				String ledgerEntityId = string.substring(2, 18);
				message.setLedgerEntityId(Long.valueOf(ledgerEntityId));
				string = string.substring(18);

			} else if (firstTwo.equals(CommonConstants.SHARP_P63_LEDGER_ACCOUNT)) {
				String ledgerAccount = string.substring(2, 18);
				message.setFullfillmentEntryId(Long.valueOf(ledgerAccount));
				string = string.substring(18);
			} else if (firstTwo.equals(CommonConstants.SHARP_P63_FULFILLMENT_GROUP)) {
				String fulfillmentGroupId = string.substring(2, 17);
				message.setFullfillmentGroupId(Long.valueOf(fulfillmentGroupId));
				string = string.substring(17);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.sharp.iso.IIsoResponseProcessor#processResponse(byte[])
	 */
	public IsoMessageVO processResponse(byte[] responseByteMessage) throws SLBusinessServiceException {
		
		IsoMessageVO message = new IsoMessageVO();
		IsoMessageTemplateVO isoMessageTemplateVO = loadResponseMessage(message, responseByteMessage);
		if( isoMessageTemplateVO != null ) {
			processSpecificData(message, isoMessageTemplateVO);
			logger.debug("Response Processed.");
		} else {
			return null;
		}

		return message;
	}

	/**
	 * processSpecificData.
	 * 
	 * @param message 
	 * @param isoMessagetemplate 
	 * 
	 * @return void
	 * @throws SLBusinessServiceException 
	 * 
	 * @throws StringParseException 
	 */
	public void processSpecificData(IsoMessageVO message, IsoMessageTemplateVO isoMessagetemplate) throws SLBusinessServiceException {

		try {
			constructFulfillmentEntry(message, isoMessagetemplate);
		} catch (StringParseException e) {
			throw new SLBusinessServiceException(e);
		}
	}

	/**
	 * setIsoMessageTemplateDao.
	 * 
	 * @param isoMessageTemplateDao 
	 * 
	 * @return void
	 */
	public void setIsoMessageTemplateDao(IIsoMessageTemplateDao isoMessageTemplateDao) {

		this.isoMessageTemplateDao = isoMessageTemplateDao;
	}

}
