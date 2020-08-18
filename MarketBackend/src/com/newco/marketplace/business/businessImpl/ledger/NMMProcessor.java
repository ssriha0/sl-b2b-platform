package com.newco.marketplace.business.businessImpl.ledger;

import org.apache.log4j.Logger;


public class NMMProcessor implements Runnable {
	private static final Logger logger = Logger.getLogger(NMMProcessor.class);
	public void run() {
		//should be moved to new Value link processing
		logger.info("NMM Not Implemented Message");
//		throw new NotImplementedException();
	}
	
/*	private static IIsoRequestProcessor isoRequestProcessor;
	private static IIsoResponseProcessor isoResponseProcessor;
	private static IFullfillmentDao fullfillmentDao;
	private static LookupDao lookupDao;
	private static ISpringJMSFacility jmsFacility;	

	public static void main(String[] args) {
		
		
		try {
			ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();
			jmsFacility = (SpringJMSFacility)applicationContext.getBean("jmsFacility");
			isoRequestProcessor = (IIsoRequestProcessor)applicationContext.getBean("isoRequestProcessor");
			isoResponseProcessor = (IIsoResponseProcessor)applicationContext.getBean("isoResponseProcessor");
			fullfillmentDao = (IFullfillmentDao)applicationContext.getBean("fullfillmentDao");
			lookupDao = (LookupDao)applicationContext.getBean("lookupDao");
			M2();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void M2() throws Exception{
		FullfillmentEntryVO feVO = new FullfillmentEntryVO();
		
		byte[] bytes = null;
		try{
			feVO.setMessageIdentifier(FullfillmentConstants.SHARP_HEARTBEAT_REQUEST);
			Integer nextSTAN = lookupDao.getNextIdentifier(FullfillmentConstants.STAN_ID);
			feVO.setStanId(nextSTAN.toString());
			bytes = isoRequestProcessor.processRequest(feVO);
			jmsFacility.sendNMMMesage(bytes);
			fullfillmentDao.createHeartBeatMessage(feVO.getStanId());
			M3(feVO.getStanId());
		}catch(Exception e){
			logger.error("Error occured NMMProcessor ---> M2", e);
			throw e;
		}
	}
	
	public static void M3(String heartBeatId) throws Exception {
		Message msg;
		BytesMessage bytesMsg;
		FullfillmentHelper futil = new FullfillmentHelper();
		FullfillmentEntryVO feVO = null;
		while (true) {
			try {
				msg = jmsFacility.ReceiveNMM();
				if (msg == null) {
					logger.debug("Received Null NMM Response Message");
					fullfillmentDao.updateSharpVLStatus(
							FullfillmentConstants.SHARP_SYSTEM, "N");
					Thread.sleep(30000);
				} else if (msg instanceof BytesMessage) {
					bytesMsg = (BytesMessage) msg;
					feVO = isoResponseProcessor.processResponse(futil
							.getBytesFromBytesMessage(bytesMsg));
					byte[] bytes = futil.getBytesFromBytesMessage(bytesMsg);
					logger.info("Recieved NMM Response and Going to Log Response in table" + new String(bytes));
					fullfillmentDao.insertNMMResponseLogging(feVO.getFullfillmentEntryId(), new String(bytes));
					if (new Integer(feVO.getStanId()).intValue() == new Integer(
							heartBeatId).intValue()) {
						fullfillmentDao.updateSharpVLStatus(
								FullfillmentConstants.SHARP_SYSTEM, "Y");
						logger.debug("NMM Response Message Received");
					} else {
						continue;
					}

					Thread.sleep(300000);
				}
				fullfillmentDao.deleteHeartBeatMessage(heartBeatId);
				M2();
			} catch (Exception e) {
				logger.error("Error occured NMMProcessor ---> M3", e);
				Thread.sleep(300000);				
			}

		}
	}


	public void run() {
		try {
			logger.debug("NMMProcessor-->run()-->START");
			ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();
			jmsFacility = (SpringJMSFacility)applicationContext.getBean("jmsFacility");
			isoRequestProcessor = (IIsoRequestProcessor)applicationContext.getBean("isoRequestProcessor");
			isoResponseProcessor = (IIsoResponseProcessor)applicationContext.getBean("isoResponseProcessor");
			fullfillmentDao = (IFullfillmentDao)applicationContext.getBean("fullfillmentDao");
			lookupDao = (LookupDao)applicationContext.getBean("lookupDao");
			M2();
			
		} 
		catch (Exception e) {
			logger.error("NMMProcessor-->run()-->EXCEPTION-->", e);
		}
	}
*/
	

}
