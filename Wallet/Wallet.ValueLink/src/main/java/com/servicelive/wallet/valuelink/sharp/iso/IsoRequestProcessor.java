package com.servicelive.wallet.valuelink.sharp.iso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.valuelink.sharp.iso.dao.IIsoMessageTemplateDao;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageGenericRecordVO;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageTemplateVO;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageVO;

// TODO: Auto-generated Javadoc
/**
 * Class IsoRequestProcessor.
 */
public class IsoRequestProcessor extends IsoMessageProcessor implements IIsoRequestProcessor {

	/** logger. */
	private static final Logger logger = Logger.getLogger(IsoRequestProcessor.class);

	/** isoMessageTemplateDao. */
	private IIsoMessageTemplateDao isoMessageTemplateDao;

	/** sharpDeviceNo. */
	private String sharpDeviceNo;

	/**
	 * getIsoMessageTemplateDao.
	 * 
	 * @return IIsoMessageTemplateDao
	 */
	public IIsoMessageTemplateDao getIsoMessageTemplateDao() {

		return isoMessageTemplateDao;
	}

	/**
	 * getSharpDeviceNo.
	 * 
	 * @return String
	 */
	public String getSharpDeviceNo() {

		return sharpDeviceNo;
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
			p11IsoMessageGenericRecord.setIsoMessageValue(prefixZero(message.getStanId(), p11IsoMessageGenericRecord.getDataLength()));
		}
	}

	/**
	 * handleP12.
	 * 
	 * @param hashMap 
	 * 
	 * @return void
	 */
	private void handleP12(HashMap<String, IsoMessageGenericRecordVO> hashMap) {

		IsoMessageGenericRecordVO p12IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P12");
		if (p12IsoMessageGenericRecord != null) {
			p12IsoMessageGenericRecord.setIsoMessageValue(getTimeStamp());
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
			Long lPan = message.getPrimaryAccountNumber();
			if (lPan != null) {
				String strPan = lPan.toString();
				p2IsoMessageGenericRecord.setIsoMessageValue(strPan);
			} else {
				p2IsoMessageGenericRecord.setIsoMessageValue(CommonConstants.SHARP_DUMMY_PAN_NUMBER);
			}
		}
	}

	/**
	 * handleP3.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP3(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p3IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P3");
		if (p3IsoMessageGenericRecord != null) {
			// TO DO
		}
	}

	/*
	 * private void handleP22(HashMap<String, IsoMessageGenericRecord>
	 * hashMap,IsoRequestVO message){
	 * 
	 * IsoMessageGenericRecord p22IsoMessageGenericRecord =
	 * (IsoMessageGenericRecord)hashMap.get("P22"); if
	 * (p22IsoMessageGenericRecord!=null) { // TO DO } }
	 */
	/**
	 * handleP37.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP37(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p37IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P37");
		if (p37IsoMessageGenericRecord != null) {
			String str = message.getRetrievalRefId();
			if (str != null) {
				p37IsoMessageGenericRecord.setIsoMessageValue(prefixZero(str, p37IsoMessageGenericRecord.getDataLength()));
			} else {
				p37IsoMessageGenericRecord.setIsoMessageValue(prefixZero("", p37IsoMessageGenericRecord.getDataLength()));
			}
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
		if (p38IsoMessageGenericRecord != null && message.getAuthorizationId() != null) {
			p38IsoMessageGenericRecord.setIsoMessageValue(prefixZero(message.getAuthorizationId(), p38IsoMessageGenericRecord.getDataLength()));
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
			if (message.getActionCode() != null) {
				p39IsoMessageGenericRecord.setIsoMessageValue(message.getActionCode());
			}

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
			double currencyWithDecimal = message.getTransAmount();
			String currencyWithoutDecimal = getCurrenyWithoutDecimal(currencyWithDecimal);
			p4IsoMessageGenericRecord.setIsoMessageValue(currencyWithoutDecimal);
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
			String slStoreNo = message.getStoreNo();
			//String deviceNo = getSharpDeviceNo();
			String deviceNo = message.getStoreDeviceNumber();
			if(StringUtils.isBlank(deviceNo))
				deviceNo="000";
			String sharpRegisterNo = slStoreNo + deviceNo;
			logger.debug("IsoRequestProcessor-->handleP41()-->device No-->" + sharpRegisterNo);

			p41IsoMessageGenericRecord.setIsoMessageValue(sharpRegisterNo);
			// Template loads the value. Dont do anything here unless you want
			// to handle the store-device number specifically
		}
	}	

	/**
	 * handleP44.
	 * 
	 * @param hashMap 
	 * 
	 * @return void
	 */
	private void handleP44(HashMap<String, IsoMessageGenericRecordVO> hashMap) {

		IsoMessageGenericRecordVO p44IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P44");
		if (p44IsoMessageGenericRecord != null) {
			p44IsoMessageGenericRecord.setIsoMessageValue(prefixZero("", p44IsoMessageGenericRecord.getDataLength()));

		}
	}

	/**
	 * handleP48.
	 * 
	 * @param hashMap 
	 * 
	 * @return void
	 */
	private void handleP48(HashMap<String, IsoMessageGenericRecordVO> hashMap) {

		IsoMessageGenericRecordVO p48IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P48");
		if (p48IsoMessageGenericRecord != null) {
			p48IsoMessageGenericRecord.setIsoMessageValue("0000" + "SM");
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
			p54IsoMessageGenericRecord.setIsoMessageValue(prefixZero("", p54IsoMessageGenericRecord.getDataLength()));
		}
	}

	/**
	 * handleP56.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP56(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p56IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P56");
		if (p56IsoMessageGenericRecord != null) {
			// Req fields
			// N4 - Original message type (MTI), e.g. 1100
			// N6 - Original System Audit Trace Number (P-11).
			// N12 - Original Date and Time, Local Transaction (P-12); format
			// should be MMDDhhmmss.
			// Optional
			// N4 - Original Transaction Code
			// N4 - Original Transaction Number
			// N5 - Original store number
			// N3 - Original Register number

			String p56 = "";
			p56 = p56 + ((IsoMessageGenericRecordVO) hashMap.get("MTI")).getIsoMessageValue();
			p56 = p56 + ((IsoMessageGenericRecordVO) hashMap.get("P11")).getIsoMessageValue();
			p56 = p56 + message.getTimeStamp();
			// p56=
			// p56+((IsoMessageGenericRecord)hashMap.get("P3")).getIsoMessageValue().substring(0,2);
			p56 = p56 + "10  "; // Only Redemption Request is to be void
			String str = prefixZero(message.getReferenceNo(), 4);
			int length = str.length();
			int lastFourth = length - 4;
			p56 = p56 + str.substring(lastFourth, length);
			p56 = p56 + ((IsoMessageGenericRecordVO) hashMap.get("P41")).getIsoMessageValue().substring(0, 5);
			p56 = p56 + "000";
			p56IsoMessageGenericRecord.setIsoMessageValue(p56);
		}
	}

	/**
	 * handleP63.
	 * 
	 * @param hashMap 
	 * @param message 
	 * 
	 * @return void
	 */
	private void handleP63(HashMap<String, IsoMessageGenericRecordVO> hashMap, IsoMessageVO message) {

		IsoMessageGenericRecordVO p63IsoMessageGenericRecord = (IsoMessageGenericRecordVO) hashMap.get("P63");
		if (p63IsoMessageGenericRecord != null) {
			String p63 = "";
			/* Fulfillment Group Number */
			if (message.getFullfillmentGroupId() != null) {
				p63 = p63 + CommonConstants.SHARP_P63_FULFILLMENT_GROUP + prefixZero(message.getFullfillmentGroupId().toString(), 15);
			}

			/* Promo Code */
			if (message.getMessageTypeId() != null && CommonConstants.MESSAGE_TYPE_ACTIVATION == message.getMessageTypeId().intValue() && message.getPromoCode() != null) {
				p63 = p63 + CommonConstants.SHARP_P63_PROMO_CODE + prefixZero(message.getPromoCode(), 8);
			}

			/* Ledger Account Number */
			if (message.getFullfillmentEntryId() != null) {
				p63 = p63 + CommonConstants.SHARP_P63_LEDGER_ACCOUNT + prefixZero(message.getFullfillmentEntryId().toString(), 16);
			}

			/* Account Number */
			if (message.getMessageTypeId() != null && CommonConstants.MESSAGE_TYPE_ACTIVATION == message.getMessageTypeId().intValue() && message.getPromoCode() != null) {
				if (message.getLedgerEntityId() != null) {
					p63 = p63 + CommonConstants.SHARP_P63_ACCOUNT_NUMBER + prefixZero(message.getLedgerEntityId().toString(), 16);
				}
			}

			p63IsoMessageGenericRecord.setIsoMessageValue(p63);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.sharp.iso.IIsoRequestProcessor#processRequest(com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageVO)
	 */
	public byte[] processRequest(IsoMessageVO message) throws UnknownMessageTypeException, StringParseException, DataServiceException, Exception {

		if ((message != null && message.getFullfillmentEntryId() != null && message.getMessageIdentifier() != null)
			|| (message != null && message.getMessageIdentifier() != null && CommonConstants.SHARP_HEARTBEAT_REQUEST.equals(message.getMessageIdentifier()))) {
			IsoMessageTemplateVO isoMessagetemplate = (IsoMessageTemplateVO) isoMessageTemplateDao.getIsoMessageTemplate(message.getMessageIdentifier());
			processSpecificData(message, isoMessagetemplate);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			processHeaderData(message, isoMessagetemplate, byteArrayOutputStream);
			byte[] bmessage = isoMessagetemplate.formatValue();
			byteArrayOutputStream.write(bmessage);
			processFooterData(byteArrayOutputStream);

			logger.info("balance inquiry : SHARP (" + message.getMessageIdentifier() + ") Message to be sent: " + byteArrayOutputStream.toString());
			logger.info("balance inquiry : message.getFullfillmentEntryId()" + message.getFullfillmentEntryId());
			
			return byteArrayOutputStream.toByteArray();
		} else {
			return null;
		}
	}

	/*
	 * This method processes specific data elements that are not handled by the
	 * template @param message @param isoMessagetemplate
	 */
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.sharp.iso.IIsoRequestProcessor#processSpecificData(com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageVO, com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageTemplateVO)
	 */
	public void processSpecificData(IsoMessageVO message, IsoMessageTemplateVO isoMessagetemplate) {

		HashMap<String, IsoMessageGenericRecordVO> hashMap = isoMessagetemplate.getHash();

		handleP2(hashMap, message);
		handleP3(hashMap, message);
		handleP4(hashMap, message);
		handleP11(hashMap, message);
		handleP12(hashMap);
		handleP37(hashMap, message);
		handleP38(hashMap, message);
		handleP39(hashMap, message);
		handleP41(hashMap, message);
		handleP44(hashMap);
		handleP48(hashMap);
		handleP54(hashMap, message);
		handleP56(hashMap, message);
		handleP63(hashMap, message);
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

	/**
	 * setSharpDeviceNo.
	 * 
	 * @param sharpDeviceNo 
	 * 
	 * @return void
	 */
	public void setSharpDeviceNo(String sharpDeviceNo) {

		this.sharpDeviceNo = sharpDeviceNo;
	}

}
