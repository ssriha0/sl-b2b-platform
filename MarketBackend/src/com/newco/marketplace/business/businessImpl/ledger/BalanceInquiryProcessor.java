package com.newco.marketplace.business.businessImpl.ledger;

import org.apache.commons.lang.NotImplementedException;

import com.newco.marketplace.business.iBusiness.ledger.IBalanceInquiryProcessor;


public class BalanceInquiryProcessor implements IBalanceInquiryProcessor {

	public void kickStartBalanceInquiryMsgs() {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	public void run() {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}
/*
	
	public void run() {
		kickStartBalanceInquiryMsgs();
	}

	private static final Logger logger = Logger.getLogger(BalanceInquiryProcessor.class.getName());
	
	private ISpringJMSFacility jmsFacility;
	private IIsoRequestProcessor isoRequestProcessor;
	private IIsoResponseProcessor isoResponseProcessor;
	private IFullfillmentDao fullfillmentDao;
	private LookupDao lookupDao;
	public static int counter =0;
	
	public void kickStartBalanceInquiryMsgs(){
		logger.info("----> Entered into sendBalanceInquiryMsgs Method <---------");
		FullfillmentEntryVO fullfillmentEntryVO = new FullfillmentEntryVO();
		byte[] bytes = null;
		try{
			fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.BALANCE_ENQUIRY_REQUEST);
			fullfillmentEntryVO.setTransAmount(0.0);
			//fullfillmentEntryVO.setPrimaryAccountNumber(7777007861331306L);
			//fullfillmentEntryVO.setPrimaryAccountNumber(6047829759323538L);
			Long heartBeatAccountId = fullfillmentDao.getSLAccountForHeartBeat();
			fullfillmentEntryVO.setPrimaryAccountNumber(heartBeatAccountId);
			Integer nextSTAN = lookupDao.getNextIdentifier(FullfillmentConstants.STAN_ID);
			fullfillmentEntryVO.setStanId(nextSTAN.toString());
			fullfillmentEntryVO.setFullfillmentGroupId(1234L);
			fullfillmentEntryVO.setFullfillmentEntryId(1234L);
			bytes = isoRequestProcessor.processRequest(fullfillmentEntryVO);
			boolean go = false;
			synchronized(this){
				if(counter==0)
				{
					counter = 1;
					go = true;
				}
			}
			if(go){
				jmsFacility.sendBalanceMesage(bytes); 
				ListenForBalanceEnquiryMsgs(fullfillmentEntryVO.getStanId());
			}
		}catch (Exception e) {
			logger.error("Error occured while sending Balance Enquiry messages", e);
		}
	}
	
	private void sendBalanceInquiryMsgs(){
		logger.info("----> Entered into sendBalanceInquiryMsgs Method <---------");
		FullfillmentEntryVO fullfillmentEntryVO = new FullfillmentEntryVO();
		byte[] bytes = null;
		try{
			fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.BALANCE_ENQUIRY_REQUEST);
			fullfillmentEntryVO.setTransAmount(0.0);
			//fullfillmentEntryVO.setPrimaryAccountNumber(7777007861331306L);
			//fullfillmentEntryVO.setPrimaryAccountNumber(6047829759323538L);
			Long heartBeatAccountId = fullfillmentDao.getSLAccountForHeartBeat();
			fullfillmentEntryVO.setPrimaryAccountNumber(heartBeatAccountId);
			Integer nextSTAN = lookupDao.getNextIdentifier(FullfillmentConstants.STAN_ID);
			fullfillmentEntryVO.setStanId(nextSTAN.toString());
			fullfillmentEntryVO.setFullfillmentGroupId(1234L);
			fullfillmentEntryVO.setFullfillmentEntryId(1234L);
			bytes = isoRequestProcessor.processRequest(fullfillmentEntryVO);
			ListenForBalanceEnquiryMsgs(fullfillmentEntryVO.getStanId());
		}catch (Exception e) {
			logger.error("Error occured while sending Balance Enquiry messages", e);
		}
	}
	
	private void ListenForBalanceEnquiryMsgs(String stanId) throws Exception{
		
		Message msg;
		BytesMessage bytesMsg;
		FullfillmentHelper futil = new FullfillmentHelper();
		FullfillmentEntryVO feVO = null;
		VLHeartbeatVO vlHeartbeatVO = null;
		try{
				vlHeartbeatVO = fullfillmentDao.getValuelinkStatuses();
				if(vlHeartbeatVO.getValuelinkInd().equalsIgnoreCase("Y")){
					break;
				}
				msg = jmsFacility.ReceiveBalance();
				if(msg == null){
					vlHeartbeatVO = fullfillmentDao.getValuelinkStatuses();
					if(null != vlHeartbeatVO.getSharpInd() && vlHeartbeatVO.getSharpInd().trim().equalsIgnoreCase("N")
							&& null != vlHeartbeatVO.getValuelinkInd() && vlHeartbeatVO.getValuelinkInd().equalsIgnoreCase("N")){
						logger.info("Received Null Balance Enquiry Response Message");
						Thread.sleep(20000);
						sendBalanceInquiryMsgs();
					}
				}else if(msg instanceof BytesMessage){
					
					bytesMsg = (BytesMessage)msg;
					feVO = isoResponseProcessor.processResponse(futil.getBytesFromBytesMessage(bytesMsg));
					logger.info("Balance Enquiry Response Message Received" + new String(futil.getBytesFromBytesMessage(bytesMsg)));
					fullfillmentDao.insertBalanceInquiryLogging(feVO.getFullfillmentEntryId(), new String(futil.getBytesFromBytesMessage(bytesMsg)));
					//if(new Integer(feVO.getStanId()).intValue() == new Integer(stanId).intValue()){
						String actionCode = feVO.getActionCode();
						if(FullfillmentConstants.RESPONSE_ACTION_CODE_AUTHORIZER_UNAVAILABLE.equals(actionCode)){
							sendBalanceInquiryMsgs();
						}
						else{
							fullfillmentDao.updateSharpVLStatus(FullfillmentConstants.VL_SYSTEM, "Y");
							synchronized(this){
								counter = 0;
							}
						}
						
//					}else{
//						sendBalanceInquiryMsgs();
//					}
				}	
		}catch(Exception e){
			logger.error("Error occured Balance Enquiry Processor ---> ListenForBalanceEnquiryMsgs", e);
			throw e;
		}
	}
	public ISpringJMSFacility getJmsFacility() {
		return jmsFacility;
	}
	public void setJmsFacility(ISpringJMSFacility jmsFacility) {
		this.jmsFacility = jmsFacility;
	}
	public IIsoRequestProcessor getIsoRequestProcessor() {
		return isoRequestProcessor;
	}
	public void setIsoRequestProcessor(IIsoRequestProcessor isoRequestProcessor) {
		this.isoRequestProcessor = isoRequestProcessor;
	}

	public IIsoResponseProcessor getIsoResponseProcessor() {
		return isoResponseProcessor;
	}

	public void setIsoResponseProcessor(IIsoResponseProcessor isoResponseProcessor) {
		this.isoResponseProcessor = isoResponseProcessor;
	}

	public IFullfillmentDao getFullfillmentDao() {
		return fullfillmentDao;
	}

	public void setFullfillmentDao(IFullfillmentDao fullfillmentDao) {
		this.fullfillmentDao = fullfillmentDao;
	}

	public LookupDao getLookupDao() {
		return lookupDao;
	}

	public void setLookupDao(LookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}*/
}
