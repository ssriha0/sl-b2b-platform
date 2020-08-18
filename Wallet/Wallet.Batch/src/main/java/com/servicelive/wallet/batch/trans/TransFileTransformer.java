package com.servicelive.wallet.batch.trans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.Cryptography;
import com.servicelive.common.util.Cryptography128;
import com.servicelive.wallet.batch.trans.vo.TransactionProcessLogVO;
import com.servicelive.wallet.batch.trans.vo.TransactionRecordVO;

// TODO: Auto-generated Javadoc
/**
 * The Class TransFileTransformer.
 */
public class TransFileTransformer {

	/** The cryptography. */
	private Cryptography cryptography;
	
	private Cryptography128 cryptography128;
	
	private static final Logger logger = Logger.getLogger(TransFileTransformer.class);

	/**
	 * Check two digit format.
	 * 
	 * @param input 
	 * 
	 * @return the string
	 */
	protected String checkTwoDigitFormat(String input) {

		if (input.length() != 2) {
			input = "0" + input;
		}

		return input;
	}

	/**
	 * Gets the amount.
	 * 
	 * @param amount 
	 * 
	 * @return the amount
	 */
	protected String getAmount(double amount) {

		String s = String.valueOf(amount * 100);
		s = s.substring(0, s.indexOf("."));
		return s;
	}

	/**
	 * Gets the cryptography.
	 * 
	 * @return the cryptography
	 */
	public Cryptography getCryptography() {

		return cryptography;
	}

	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	protected String getDate() {

		Calendar calendar = new GregorianCalendar();
		int month = calendar.get(Calendar.MONTH) + 1;
		String date = calendar.get(Calendar.YEAR) + "-" + checkTwoDigitFormat( String.valueOf(month) ) + "-" 
			+ checkTwoDigitFormat( String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		return date;

	}

	/**
	 * Gets the register number.
	 * 
	 * @return the register number
	 */
	private int getRegisterNumber() {

		return TransactionNumberManager.getInstance().getRegisterNumber();
	}

	/**
	 * Gets the signed amount.
	 * 
	 * @param amount 
	 * @param type 
	 * 
	 * @return the signed amount
	 */
	protected String getSignedAmount(String amount, String type) {

		if ("9".equals(type)) {
			return amount + " ";
		} else {
			return amount + "-";
		}

	}

	/**
	 * Gets the time.
	 * 
	 * @return the time
	 */
	protected String getTime() {

		Calendar calendar = new GregorianCalendar();
		String time =
			checkTwoDigitFormat(String.valueOf(calendar.get(Calendar.HOUR))) + ":" + checkTwoDigitFormat(String.valueOf(calendar.get(Calendar.MINUTE))) + ":"
				+ checkTwoDigitFormat(String.valueOf(calendar.get(Calendar.SECOND)));

		return time;

	}

	/**
	 * Gets the tran number.
	 * 
	 * @return the tran number
	 */
	private int getTranNumber() {

		return TransactionNumberManager.getInstance().getTranNumber();
	}

	/**
	 * Gets the transaction process log vo.
	 * 
	 * @param transRecordCount 
	 * @param fullPathFileName 
	 * @param transDepositTotal 
	 * @param transRefundTotal 
	 * 
	 * @return the transaction process log vo
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public TransactionProcessLogVO getTransactionProcessLogVO(int transRecordCount, String fullPathFileName, double transDepositTotal, double transRefundTotal)
		throws SLBusinessServiceException {

		try {
			TransactionProcessLogVO transactionProcessLogVO = new TransactionProcessLogVO();
			transactionProcessLogVO.setGeneratedServer(InetAddress.getLocalHost().getHostName() + "( " + (InetAddress.getLocalHost().getHostAddress()) + " )");
			transactionProcessLogVO.setGeneratedFileName(fullPathFileName);
			transactionProcessLogVO.setTransRecordCount(transRecordCount);
			transactionProcessLogVO.setTransDepositTotal(transDepositTotal);
			transactionProcessLogVO.setTransRefundTotal(transRefundTotal);
			transactionProcessLogVO.setCreatedDate(new Timestamp(new Date(System.currentTimeMillis()).getTime()));
			transactionProcessLogVO.setModifiedDate(new Timestamp(new Date(System.currentTimeMillis()).getTime()));

			return transactionProcessLogVO;
		} catch (UnknownHostException e) {
			throw new SLBusinessServiceException(e);
		}
	}

	/**
	 * Prefix zero.
	 * 
	 * @param str 
	 * @param totalLength 
	 * 
	 * @return the string
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
	 * Suffix space.
	 * 
	 * @param str 
	 * @param totalLength 
	 * 
	 * @return the string
	 */
	protected String suffixSpace(String str, int totalLength) {

		String stringLengthMyStr = str;
		if (str != null) {
			int stringLength = str.length();
			int toPrefixLength = totalLength - stringLength;
			if (toPrefixLength > 0) {
				while (toPrefixLength != 0) {
					stringLengthMyStr = stringLengthMyStr + " ";
					toPrefixLength--;
				}
			}
		}
		
		return stringLengthMyStr;
	}	

	/**
	 * Sets the cryptography.
	 * 
	 * @param cryptography the new cryptography
	 */
	public void setCryptography(Cryptography cryptography) {

		this.cryptography = cryptography;
	}

	/**
	 * Sets the type code2.
	 * 
	 * @param sb 
	 * @param type 
	 */
	private void setTypeCode2(StringBuffer sb, String type) {

		if ("9".equals(type)) {
			sb.append(CommonConstants.TYPE_CODE_2_SALE);
		} else {
			sb.append(CommonConstants.TYPE_CODE_2_RETURN);
		}

	}

	/**
	 * Transform header data.
	 * 
	 * @param transactionRecordDTO 
	 * @param slStoreNo 
	 * 
	 * @return the byte[]
	 * 
	 * @throws IOException 
	 */
	public byte[] transformHeaderData(TransactionRecordVO transactionRecordDTO, String slStoreNo) throws IOException {
		logger.info("inside transformHeaderData method");
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		StringBuffer sb = new StringBuffer();
		// 1. indicator
		// int startIndicator = 177;

		// String s = Integer.toString(0xB1, 16);

		// byteArrayOutputStream.write(0xB1);

		// 2. Segment Length
		// int segLength1 = 0x10;
		// int segLength2 = 0x0C;
		
		// SL-20853
		if(transactionRecordDTO.isPciVersion()){
			byteArrayOutputStream.write(0x10);//first character of the length ie.if 104C,then 0x10 and 0x4C
			byteArrayOutputStream.write(0x2C);
		
			// 3.Version Number only required for in store format
			//sb.append(CommonConstants.VERSION_NUMBER);
			//byteArrayOutputStream.write(0x06);
			//byteArrayOutputStream.write(0x00);
		}
		else{
			// 2. Segment Length
			byteArrayOutputStream.write(0x10);//first character of the length ie.100C
			byteArrayOutputStream.write(0x0C);
		}
		// 3. Store Number
		sb.append(slStoreNo);

		// Register Number
		sb.append(prefixZero(String.valueOf(getRegisterNumber()), 3));

		// 4. Transaction number
		sb.append(prefixZero(String.valueOf(getTranNumber()), 4));

		// 5. Transaction Date
		sb.append(getDate());

		// 6. Transaction Time
		sb.append(getTime());

		// 7. Credited Store Number
		sb.append(slStoreNo);

		// 8. Ringing Associate ID
		sb.append(CommonConstants.RINGING_ASSOCIATE_ID);

		// 9. Purchasing Customer ID
		sb.append(CommonConstants.PURCHASING_CUSTOMER_ID);

		// 10. Purchasing Address ID
		sb.append(CommonConstants.PURCHASING_ADDRESS_ID);
		// 11. Customer ID Status Code
		sb.append(CommonConstants.CUSTOMER_STATUS_ID_CODE);

		// 12. Transaction Source Code
		sb.append(CommonConstants.TRANSACTION_SOURCE_CODE);

		// 13. Transaction Type Code
		sb.append(CommonConstants.TRANSACTION_TYPE_CODE);

		// 14. Transaction Status Code
		sb.append(CommonConstants.TRANSACTION_STATUS_CODE);

		// 15. Transaction Error Code
		sb.append(CommonConstants.TRANSACTION_ERROR_CODE);

		// 16. Transaction Reason Code
		sb.append(CommonConstants.TRANSACTION_REASON_CODE);

		// 17. Transaction Total Discount
		sb.append(CommonConstants.TRANSACTION_TOTAL_DISCOUNT);

		// 18. Transaction Total tax
		sb.append(CommonConstants.TRANSACTION_TOTAL_TAX);

		// 19. Transaction Tax Code
		sb.append(CommonConstants.TRANSACTION_TAX_CODE);

		// 20. Transaction Total
		sb.append(getSignedAmount(prefixZero(getAmount(transactionRecordDTO.getAmount()), 7), transactionRecordDTO.getAchTransCodeId()));

		byteArrayOutputStream.write(sb.toString().getBytes());
		logger.info("output of transformHeaderData method"+sb.toString());
		// 21. Transaction Flags
		byteArrayOutputStream.write(00);
		byteArrayOutputStream.write(00);
		//SL-20853
		//Transaction Flags change to char(4)
		if(transactionRecordDTO.isPciVersion()){
			byteArrayOutputStream.write(00);
			byteArrayOutputStream.write(00);
		}
		
		// 22. End Of Segment Delimiter
		// byteArrayOutputStream.write(CommonConstants.LTRAN_SEGMENT_DELIMIITER);

		return byteArrayOutputStream.toString().getBytes();
	}

	/**
	 * Transform line item data.
	 * 
	 * @param transactionRecordDTO 
	 * 
	 * @return the byte[]
	 * 
	 * @throws IOException 
	 */
	public byte[] transformLineItemData(TransactionRecordVO transactionRecordDTO) throws IOException {
		logger.info("inside transformLineItemData");
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		StringBuffer sb = new StringBuffer();
		// 1. indicator

		// int startIndicator = 0xC1;
		// byteArrayOutputStream.write(0xC1);

		// 2. Segment Length
		byteArrayOutputStream.write(0x08);
		byteArrayOutputStream.write(0x4C);

		// 3. Selling Associate
		sb.append(CommonConstants.SELLING_ASSOCIATE);

		// 4. Division Number - TO DO
		sb.append(CommonConstants.DIVISION_NUMBER);

		// 5. Item Number - TO DO
		sb.append(CommonConstants.ITEM_NUMBER);

		// 6. SKU Number
		sb.append(CommonConstants.SKU_NUMBER);

		// 7. Misc Account Number
		sb.append(CommonConstants.MISC_ACCOUNT_NUMBER);

		// 8. Quantity
		sb.append(CommonConstants.QUANTITY);

		// 9. Selling Amount - TO DO
		sb.append(getSignedAmount(prefixZero(getAmount(transactionRecordDTO.getAmount()), 7), transactionRecordDTO.getAchTransCodeId()));

		byteArrayOutputStream.write(sb.toString().getBytes());

		// 10. Item Flags
		byteArrayOutputStream.write(0x00);
		byteArrayOutputStream.write(0x00);
		byteArrayOutputStream.write(0x00);
		byteArrayOutputStream.write(0x00);
		byteArrayOutputStream.write(0x00);
		byteArrayOutputStream.write(0x00);

		sb = new StringBuffer();

		// 11. Line Item Type Code 1
		sb.append(CommonConstants.TYPE_CODE_1);

		// 12. Line Item Type Code 2
		setTypeCode2(sb, transactionRecordDTO.getAchTransCodeId());

		// 13. Line Item Reason Code
		sb.append(CommonConstants.REASON_CODE);

		// 13 Line Item status Code
		sb.append(CommonConstants.STATUS_CODE);

		// 14. Line Item status reason Code
		sb.append(CommonConstants.STATUS_REASON_CODE);

		// 15. Promised Date TO DO
		sb.append(getDate());

		// 16. Regular Price
		sb.append(prefixZero(getAmount(transactionRecordDTO.getAmount()), 7));

		// 17. PLU Amount
		sb.append(prefixZero(getAmount(transactionRecordDTO.getAmount()), 7));

		// 18. PLU Amount Type Code
		sb.append(CommonConstants.PLU_AMOUNT_TYPE_CODE);

		// 19. Tax Amount
		sb.append(CommonConstants.TAX_AMOUNT);

		// 20. Tax Code
		sb.append(CommonConstants.TAX_CODE);
		byteArrayOutputStream.write(sb.toString().getBytes());

		// 21. End Of Segment Delimiter
		// byteArrayOutputStream.write(CommonConstants.LTRAN_SEGMENT_DELIMIITER);
		logger.info("Output of transformLineItemData"+sb.toString());
		return byteArrayOutputStream.toString().getBytes();

	}

	/**
	 * Transform payment data.
	 * 
	 * @param transactionRecordDTO 
	 * 
	 * @return the byte[]
	 * 
	 * @throws IOException 
	 */
	public byte[] transformPaymentData(TransactionRecordVO transactionRecordDTO) throws IOException {
		
		logger.info("Inside transformPaymentData method in TransFileTransformer");

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		StringBuffer sb = new StringBuffer();
		// 1. indicator
		// int startIndicator = 0xB5;
		// byteArrayOutputStream.write(0xB5);

		// 2. Segment Length
		// SL-20853
		if(transactionRecordDTO.isPciVersion()){
			byteArrayOutputStream.write(0x08);
			byteArrayOutputStream.write(0x7C);
		}
		else{
			byteArrayOutputStream.write(0x05);
			byteArrayOutputStream.write(0x0C);
		}
		// 3. Payment Method Type Code - TO DO
		sb.append(transactionRecordDTO.getCardTypeCode());

		// 4. Payment Method Amount- TO DO
		sb.append(getSignedAmount(prefixZero(getAmount(transactionRecordDTO.getAmount()), 7), transactionRecordDTO.getAchTransCodeId()));

		// 5. Payment Method Account Number - TO DO
		
		//Commenting code for SL-18789
		//String cardNumber = cryptography.decryptKey(transactionRecordDTO.getCreditCardNumber());
	
		//SL-20853
		if(transactionRecordDTO.isPciVersion()&& StringUtils.isNotBlank(transactionRecordDTO.getMaskedAccountNo())){
			sb.append(suffixSpace(transactionRecordDTO.getMaskedAccountNo(), 19));
		}
		else{
			String cardNumber = cryptography128.decryptKey128Bit(transactionRecordDTO.getCreditCardNumber());
			logger.info("Credit Card number"+cardNumber);
			sb.append(prefixZero(cardNumber, 16));
		}
		logger.info("Credit Card number after prefixing with Zero"+sb.toString());
		// 6. Payment Method Expiration Date - TO DO
		sb.append(transactionRecordDTO.getCardExpireDate());

		// 7. Payment Status Code
		sb.append(CommonConstants.PAYMENT_STATUS_CODE);

		// 8. Payment Method Auth Code - TO DO
		if (transactionRecordDTO.getAuthorizationCode() != null) {
			sb.append(prefixZero(transactionRecordDTO.getAuthorizationCode(), 6));
		} else {
			sb.append(prefixZero("", 6));
		}

		// 9. Payment Method Date Code
		sb.append(CommonConstants.PAYMENT_METHOD_DATE_CODE);

		// 10. Payment Method Date - TO DO
		sb.append(getDate());
		
		//SL-20853
		if(transactionRecordDTO.isPciVersion()){
			if(StringUtils.isNotBlank(transactionRecordDTO.getTokenId())){
				//11.Token ID char(16) Left Justified, Padded with Spaces.
				sb.append(suffixSpace(transactionRecordDTO.getTokenId(),16));
			}
			if(StringUtils.isNotBlank(transactionRecordDTO.getSettlementKey())){
				//12.Settlement Key char(18) Left Justified, Padded with Spaces.
				sb.append(suffixSpace(transactionRecordDTO.getSettlementKey(),18));
			}
		}
		
		byteArrayOutputStream.write(sb.toString().getBytes());
		logger.info("Output of transformPaymentData"+sb.toString());

		// 11. End Of Segment Delimiter
		// byteArrayOutputStream.write(CommonConstants.LTRAN_SEGMENT_DELIMIITER);

		return byteArrayOutputStream.toString().getBytes();

	}

	public Cryptography128 getCryptography128() {
		return cryptography128;
	}

	public void setCryptography128(Cryptography128 cryptography128) {
		this.cryptography128 = cryptography128;
	}

}
