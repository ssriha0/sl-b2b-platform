package com.newco.marketplace.iso.processors;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.iso.IIsoRequestProcessor;
import com.newco.marketplace.business.iBusiness.iso.IMessageProducerHelper;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.dto.vo.iso.IsoMessageGenericRecord;
import com.newco.marketplace.dto.vo.iso.IsoMessageTemplateVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.StringParseException;
import com.newco.marketplace.exception.UnknownMessageTypeException;
import com.newco.marketplace.interfaces.FullfillmentConstants;
import com.newco.marketplace.persistence.iDao.iso.IsoMessageTemplateDao;
import com.newco.marketplace.persistence.iDao.ledger.IFullfillmentDao;
import com.newco.marketplace.util.PropertiesUtils;

/**
 * 
 * @author Siva The IsoRequestProcessor is a translator between SL system and
 *         IBM MQ/SHARP system.
 */
public class IsoRequestProcessor extends IsoMessageProcessor implements
		IIsoRequestProcessor {
	private IsoMessageTemplateDao isoMessageTemplateDao;
	private IFullfillmentDao fullfillmentDao;
	private String sharpDeviceNo;
	private static final Logger logger = Logger
			.getLogger(IsoRequestProcessor.class);

	/**
	 * This method is the interface to the caller to convert the VO to text
	 * message that will get dropped in the Queue
	 * 
	 * @param fullfillmentEntryVO
	 * @throws UnknownMessageTypeException
	 * @throws StringParseException
	 * @throws DataServiceException
	 */
	public byte[] processRequest(FullfillmentEntryVO fullfillmentEntryVO)
			throws UnknownMessageTypeException, StringParseException,
			DataServiceException, Exception {
		// ApplicationContext applicationContext =
		// MPSpringLoaderPlugIn.getCtx();
		// ApplicationContext applicationContext = new
		// ClassPathXmlApplicationContext("resources/spring/applicationContext.xml");
		// IFullfillmentDao fullfillmentDao =
		// (IFullfillmentDao)applicationContext.getBean("fullfillmentDao");
		if ((fullfillmentEntryVO != null
				&& fullfillmentEntryVO.getFullfillmentEntryId() != null && fullfillmentEntryVO
				.getMessageIdentifier() != null)
				|| (fullfillmentEntryVO != null
						&& fullfillmentEntryVO.getMessageIdentifier() != null && FullfillmentConstants.SHARP_HEARTBEAT_REQUEST
						.equals(fullfillmentEntryVO.getMessageIdentifier()))) {
			IsoMessageTemplateVO isoMessagetemplate = (IsoMessageTemplateVO) isoMessageTemplateDao
					.getIsoMessageTemplate(fullfillmentEntryVO
							.getMessageIdentifier());
			processSpecificData(fullfillmentEntryVO, isoMessagetemplate);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			processHeaderData(fullfillmentEntryVO, isoMessagetemplate,
					byteArrayOutputStream);
			byte[] message = isoMessagetemplate.formatValue();
			byteArrayOutputStream.write(message);
			processFooterData(byteArrayOutputStream);
			if (fullfillmentEntryVO != null
					&& fullfillmentEntryVO.getFullfillmentEntryId() != null)
				fullfillmentDao.insertProcessRequestLogging(fullfillmentEntryVO
						.getFullfillmentEntryId(), byteArrayOutputStream
						.toString());
			logger.debug("SHARP (" + fullfillmentEntryVO.getMessageIdentifier()
					+ ") Message to be sent: "
					+ byteArrayOutputStream.toString());
			// File f =new File("c:/iso.txt");
			// BaseFileUtil b = new BaseFileUtil();
			// b.writeStringToFile(f, byteArrayOutputStream.toString());
			return byteArrayOutputStream.toByteArray();
		} else {
			return null;
		}
	}

	/*
	 * This method processes specific data elements that are not handled by the
	 * template @param fullfillmentEntryVO @param isoMessagetemplate
	 */
	public void processSpecificData(FullfillmentEntryVO fullfillmentEntryVO,
			IsoMessageTemplateVO isoMessagetemplate) {
		HashMap<String, IsoMessageGenericRecord> hashMap = isoMessagetemplate
				.getHash();

		handleP2(hashMap, fullfillmentEntryVO);
		handleP3(hashMap, fullfillmentEntryVO);
		handleP4(hashMap, fullfillmentEntryVO);
		handleP11(hashMap, fullfillmentEntryVO);
		handleP12(hashMap, fullfillmentEntryVO);
		handleP37(hashMap, fullfillmentEntryVO);
		handleP38(hashMap, fullfillmentEntryVO);
		handleP39(hashMap, fullfillmentEntryVO);
		handleP41(hashMap, fullfillmentEntryVO);
		handleP44(hashMap, fullfillmentEntryVO);
		handleP48(hashMap, fullfillmentEntryVO);
		handleP54(hashMap, fullfillmentEntryVO);
		handleP56(hashMap, fullfillmentEntryVO);
		handleP63(hashMap, fullfillmentEntryVO);
	}

	private void handleP2(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {
		IsoMessageGenericRecord p2IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P2");
		if (p2IsoMessageGenericRecord != null) {
			Long lPan = fullfillmentEntryVO.getPrimaryAccountNumber();
			if (lPan != null) {
				String strPan = lPan.toString();
				p2IsoMessageGenericRecord.setIsoMessageValue(strPan);
			} else {
				p2IsoMessageGenericRecord
						.setIsoMessageValue(FullfillmentConstants.SHARP_DUMMY_PAN_NUMBER);
			}
		}
	}

	private void handleP3(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {
		IsoMessageGenericRecord p3IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P3");
		if (p3IsoMessageGenericRecord != null) {
			// TO DO
		}
	}

	private void handleP4(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {
		IsoMessageGenericRecord p4IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P4");
		if (p4IsoMessageGenericRecord != null) {
			double currencyWithDecimal = fullfillmentEntryVO.getTransAmount();
			String currencyWithoutDecimal = getCurrenyWithoutDecimal(currencyWithDecimal);
			p4IsoMessageGenericRecord
					.setIsoMessageValue(currencyWithoutDecimal);
		}
	}

	private void handleP11(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {
		IsoMessageGenericRecord p11IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P11");
		if (p11IsoMessageGenericRecord != null) {
			p11IsoMessageGenericRecord.setIsoMessageValue(prefixZero(
					fullfillmentEntryVO.getStanId(), p11IsoMessageGenericRecord
							.getDataLength()));
		}
	}

	private void handleP12(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {
		IsoMessageGenericRecord p12IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P12");
		if (p12IsoMessageGenericRecord != null) {
			p12IsoMessageGenericRecord.setIsoMessageValue(getTimeStamp());
		}
	}

	/*
	 * private void handleP22(HashMap<String, IsoMessageGenericRecord>
	 * hashMap,FullfillmentEntryVO fullfillmentEntryVO){
	 * 
	 * IsoMessageGenericRecord p22IsoMessageGenericRecord =
	 * (IsoMessageGenericRecord)hashMap.get("P22"); if
	 * (p22IsoMessageGenericRecord!=null) { // TO DO } }
	 */
	private void handleP37(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {

		IsoMessageGenericRecord p37IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P37");
		if (p37IsoMessageGenericRecord != null) {
			String str = fullfillmentEntryVO.getRetrievalRefId();
			if (str != null) {
				p37IsoMessageGenericRecord.setIsoMessageValue(prefixZero(str,
						p37IsoMessageGenericRecord.getDataLength()));
			} else {
				p37IsoMessageGenericRecord.setIsoMessageValue(prefixZero("",
						p37IsoMessageGenericRecord.getDataLength()));
			}
		}
	}

	private void handleP38(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {

		IsoMessageGenericRecord p38IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P38");
		if (p38IsoMessageGenericRecord != null
				&& fullfillmentEntryVO.getAuthorizationId() != null) {
			p38IsoMessageGenericRecord.setIsoMessageValue(prefixZero(
					fullfillmentEntryVO.getAuthorizationId(),
					p38IsoMessageGenericRecord.getDataLength()));
		}

	}

	private void handleP39(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {

		IsoMessageGenericRecord p39IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P39");
		if (p39IsoMessageGenericRecord != null) {
			if (fullfillmentEntryVO.getActionCode() != null) {
				p39IsoMessageGenericRecord
						.setIsoMessageValue(fullfillmentEntryVO.getActionCode());
			}

		}
	}

	private void handleP41(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {

		IsoMessageGenericRecord p41IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P41");
		if (p41IsoMessageGenericRecord != null) {
			String slStoreNo = PropertiesUtils
					.getPropertyValue(Constants.AppPropConstants.SL_STORE_NO);
			String deviceNo = getSharpDeviceNo();
			String sharpRegisterNo = slStoreNo + deviceNo;
			logger.debug("IsoRequestProcessor-->handleP41()-->device No-->"
					+ sharpRegisterNo);

			p41IsoMessageGenericRecord.setIsoMessageValue(sharpRegisterNo);
			// Template loads the value. Dont do anything here unless you want
			// to handle the store-device number specifically
		}
	}

	private void handleP44(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {

		IsoMessageGenericRecord p44IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P44");
		if (p44IsoMessageGenericRecord != null) {
			p44IsoMessageGenericRecord.setIsoMessageValue(prefixZero("",
					p44IsoMessageGenericRecord.getDataLength()));

		}
	}

	private void handleP48(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {

		IsoMessageGenericRecord p48IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P48");
		if (p48IsoMessageGenericRecord != null) {
			p48IsoMessageGenericRecord.setIsoMessageValue("0000" + "SM");
		}
	}

	private void handleP54(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {

		IsoMessageGenericRecord p54IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P54");
		if (p54IsoMessageGenericRecord != null) {
			p54IsoMessageGenericRecord.setIsoMessageValue(prefixZero("",
					p54IsoMessageGenericRecord.getDataLength()));
		}
	}

	private void handleP56(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {

		IsoMessageGenericRecord p56IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P56");
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
			p56 = p56
					+ ((IsoMessageGenericRecord) hashMap.get("MTI"))
							.getIsoMessageValue();
			p56 = p56
					+ ((IsoMessageGenericRecord) hashMap.get("P11"))
							.getIsoMessageValue();
			p56 = p56 + fullfillmentEntryVO.getTimeStamp();
			// p56=
			// p56+((IsoMessageGenericRecord)hashMap.get("P3")).getIsoMessageValue().substring(0,2);
			p56 = p56 + "10  "; // Only Redemption Request is to be void
			String str = fullfillmentEntryVO.getReferenceNo();
			int length = str.length();
			int lastFourth = length - 4;
			p56 = p56 + str.substring(lastFourth, length);
			p56 = p56
					+ ((IsoMessageGenericRecord) hashMap.get("P41"))
							.getIsoMessageValue().substring(0, 5);
			p56 = p56 + "000";
			p56IsoMessageGenericRecord.setIsoMessageValue(p56);
		}
	}

	private void handleP63(HashMap<String, IsoMessageGenericRecord> hashMap,
			FullfillmentEntryVO fullfillmentEntryVO) {
		IsoMessageGenericRecord p63IsoMessageGenericRecord = (IsoMessageGenericRecord) hashMap
				.get("P63");
		if (p63IsoMessageGenericRecord != null) {
			String p63 = "";
			/* Fulfillment Group Number */
			if (fullfillmentEntryVO.getFullfillmentGroupId() != null) {
				p63 = p63
						+ FullfillmentConstants.SHARP_P63_FULFILLMENT_GROUP
						+ prefixZero(fullfillmentEntryVO
								.getFullfillmentGroupId().toString(), 15);
			}

			/* Promo Code */
			if (fullfillmentEntryVO.getMessageTypeId() != null
					&& FullfillmentConstants.MESSAGE_TYPE_ACTIVATION == fullfillmentEntryVO
							.getMessageTypeId().intValue()
					&& fullfillmentEntryVO.getPromoCode() != null) {
				p63 = p63 + FullfillmentConstants.SHARP_P63_PROMO_CODE
						+ prefixZero(fullfillmentEntryVO.getPromoCode(), 8);
			}

			/* Ledger Account Number */
			if (fullfillmentEntryVO.getFullfillmentEntryId() != null) {
				p63 = p63
						+ FullfillmentConstants.SHARP_P63_LEDGER_ACCOUNT
						+ prefixZero(fullfillmentEntryVO
								.getFullfillmentEntryId().toString(), 16);
			}

			/* Account Number */
			if (fullfillmentEntryVO.getMessageTypeId() != null
					&& FullfillmentConstants.MESSAGE_TYPE_ACTIVATION == fullfillmentEntryVO
							.getMessageTypeId().intValue()
					&& fullfillmentEntryVO.getPromoCode() != null) {
				if (fullfillmentEntryVO.getLedgerEntityId() != null) {
					p63 = p63
							+ FullfillmentConstants.SHARP_P63_ACCOUNT_NUMBER
							+ prefixZero(fullfillmentEntryVO
									.getLedgerEntityId().toString(), 16);
				}
			}

			p63IsoMessageGenericRecord.setIsoMessageValue(p63);
		}
	}

	public static void main(String k[]) {
		try {
			ApplicationContext applicationContext = MPSpringLoaderPlugIn
					.getCtx();
			IsoRequestProcessor isoMessageProcessor = (IsoRequestProcessor) applicationContext
					.getBean("isoRequestProcessor");
			FullfillmentEntryVO f = new FullfillmentEntryVO();
			f
					.setMessageIdentifier(FullfillmentConstants.REDEMPTION_REQUEST_PARTIAL_APPROVAL_VOID);
			f.setLedgerEntityId(new Long("123456789123"));
			f.setFullfillmentEntryId(new Long("987654321"));
			f.setFullfillmentGroupId(new Long("987654321"));
			f.setPromoCode("1234");
			f.setTransAmount(10.00);
			f.setPrimaryAccountNumber(7777007859334732L);
			f.setStanId("3456");
			byte myBytes[] = isoMessageProcessor.processRequest(f);
			// MessageProducerHelper msgHelper = new MessageProducerHelper();
			IMessageProducerHelper msgHelper = (IMessageProducerHelper) applicationContext
					.getBean("messageProducerHelper");
			msgHelper.sendMessageToSharp(myBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public IsoMessageTemplateDao getIsoMessageTemplateDao() {
		return isoMessageTemplateDao;
	}

	public void setIsoMessageTemplateDao(
			IsoMessageTemplateDao isoMessageTemplateDao) {
		this.isoMessageTemplateDao = isoMessageTemplateDao;
	}

	public String getSharpDeviceNo() {
		return sharpDeviceNo;
	}

	public void setSharpDeviceNo(String sharpDeviceNo) {
		this.sharpDeviceNo = sharpDeviceNo;
	}

	public IFullfillmentDao getFullfillmentDao() {
		return fullfillmentDao;
	}

	public void setFullfillmentDao(IFullfillmentDao fullfillmentDao) {
		this.fullfillmentDao = fullfillmentDao;
	}

}
