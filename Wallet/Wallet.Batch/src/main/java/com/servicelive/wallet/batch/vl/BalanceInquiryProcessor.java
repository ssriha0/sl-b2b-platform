package com.servicelive.wallet.batch.vl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.lookup.dao.IAccountDao;
import com.servicelive.wallet.batch.BaseProcessor;
import com.servicelive.wallet.common.IIdentifierDao;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.valuelink.sharp.ISharpGateway;
import com.servicelive.wallet.valuelink.sharp.iso.IIsoRequestProcessor;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageVO;

// TODO: Auto-generated Javadoc
/**
 * The Class BalanceInquiryProcessor.
 */
public class BalanceInquiryProcessor extends BaseProcessor {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(BalanceInquiryProcessor.class.getName());

	/** requestProcessor. */
	private IIsoRequestProcessor requestProcessor;

	private ISharpGateway sharpGateway;
	
	private IAccountDao accountDao;

	/** identifierDao. */
	private IIdentifierDao identifierDao;

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.IProcessor#process()
	 */
	public void process() throws SLBusinessServiceException {

		logger.info("----> Entered into sendBalanceInquiryMsgs Method <---------");

		try {
			List<Long> v1v2AccountList = accountDao.getV1V2AccountsForBalanceEnquiry();

			if (v1v2AccountList != null) {
				for (Long primaryNumber : v1v2AccountList) {
					byte [] message = createBalanceInquiryMessage(primaryNumber.longValue());
					logger.info("Sending balance inquiry for account # " + primaryNumber );
					logger.info("{" + message + "}");
					sendBalanceInquiryMessage(message);
					Thread.sleep(2000);
				}
			}
		} catch (Exception e) {
			logger.error("Error occured while sending Balance Enquiry messages", e);
		}
	}
	
	/**
	 * getSTANId.
	 * 
	 * @return String
	 */
	private String getSTANId() {

		Long nextSTAN = identifierDao.getNextIdentifier(CommonConstants.STAN_ID);
		return nextSTAN.toString();
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.IValueLinkBO#balanceInquiryMessage(long)
	 */
	public byte[] createBalanceInquiryMessage(long primaryAccountNumber) throws SLBusinessServiceException {

		try {
			IsoMessageVO entry = new IsoMessageVO();

			entry.setStoreNo(this.applicationProperties.getPropertyValue(
				CommonConstants.SL_STORE_NO) );
			
			entry.setMessageTypeId((long)CommonConstants.MESSAGE_TYPE_BALANCE_ENQ);
			entry.setMessageIdentifier(CommonConstants.BALANCE_ENQUIRY_REQUEST);
			entry.setPrimaryAccountNumber(primaryAccountNumber);
			entry.setStanId(getSTANId());
			entry.setFullfillmentGroupId(2345L);
			entry.setFullfillmentEntryId(2345L);
			entry.setTransAmount(0.0d);

			byte[] message = requestProcessor.processRequest(entry);
			return message;
		} catch (Exception e) {
			logger.error("balanceInquiryMessage", e);
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}
	
	protected void sendBalanceInquiryMessage( byte [] message ) throws SLBusinessServiceException {
		
		try {
			
			sharpGateway.sendMessage(message);
			
		} catch (Exception e) {
			logger.error("balanceInquiryMessage", e);
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}
	
	public boolean sendBalanceInquiryMessage(Integer vendorId) throws SLBusinessServiceException
	{
		try
		{
			ValueLinkEntryVO fullfillmentEntryVO = new ValueLinkEntryVO();
			fullfillmentEntryVO.setMessageIdentifier(CommonConstants.BALANCE_ENQUIRY_REQUEST);
			fullfillmentEntryVO.setTransAmount(0.0);
			List<String> v1v2AccountList = accountDao.getV1V2AccountsForBalanceEnquiry(vendorId);
			if(v1v2AccountList==null || v1v2AccountList.size()==0)
			{
				return false;
			}
			else
			{
				for (int i = 0; i < v1v2AccountList.size(); i++) {
					if(StringUtils.isNotBlank(v1v2AccountList.get(i)))
					{
						IsoMessageVO entry = new IsoMessageVO();
		
						entry.setStoreNo(this.applicationProperties.getPropertyValue(
							CommonConstants.SL_STORE_NO) );
						
						entry.setMessageTypeId((long)CommonConstants.MESSAGE_TYPE_BALANCE_ENQ);
						entry.setMessageIdentifier(CommonConstants.BALANCE_ENQUIRY_REQUEST);
						entry.setPrimaryAccountNumber(Long.parseLong(v1v2AccountList.get(i)));
						entry.setStanId(getSTANId());
						entry.setFullfillmentGroupId(2345L);
						entry.setFullfillmentEntryId(2345L);
						entry.setTransAmount(0.0d);
						byte[] message = requestProcessor.processRequest(entry);
						sharpGateway.sendMessage(message);
						Thread.sleep(100);
					}
			}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new SLBusinessServiceException(e);
		}
		return true;
	}

	public void setSharpGateway(ISharpGateway sharpGateway) {
		this.sharpGateway = sharpGateway;
	}
	
	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	/**
	 * setRequestProcessor.
	 * 
	 * @param requestProcessor 
	 * 
	 * @return void
	 */
	public void setRequestProcessor(IIsoRequestProcessor requestProcessor) {

		this.requestProcessor = requestProcessor;
	}

	/**
	 * setIdentifierDao.
	 * 
	 * @param identifierDao 
	 * 
	 * @return void
	 */
	public void setIdentifierDao(IIdentifierDao identifierDao) {

		this.identifierDao = identifierDao;
	}

}
