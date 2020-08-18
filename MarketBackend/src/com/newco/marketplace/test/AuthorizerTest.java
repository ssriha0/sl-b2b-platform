package com.newco.marketplace.test;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.iso.IIsoRequestProcessor;
import com.newco.marketplace.business.iBusiness.ledger.IBalanceInquiryProcessor;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.exception.StringParseException;
import com.newco.marketplace.exception.UnknownMessageTypeException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.FullfillmentConstants;
import com.newco.marketplace.persistence.iDao.ledger.IFullfillmentDao;
import com.newco.spring.jms.ISpringJMSFacility;
import com.newco.spring.jms.SpringJMSFacility;


public class AuthorizerTest extends TestCase{

	private IFullfillmentDao fullfillmentDao; 
	private IIsoRequestProcessor isoRequestProcessor;
	private ISpringJMSFacility jmsFacility;
	private IBalanceInquiryProcessor balanceInquiryProcessor;	
	public void testAuthorizer(){
		
		System.out.println("processResponseAuthorizerUnavailable-->ERRO AUTHORIZER UNAVAILABLE - PROCESSING...");
		ApplicationContext appContext = MPSpringLoaderPlugIn.getCtx();
		fullfillmentDao = (IFullfillmentDao)appContext.getBean("fullfillmentDao");
		jmsFacility = (SpringJMSFacility)appContext.getBean("jmsFacility");
		isoRequestProcessor = (IIsoRequestProcessor)appContext.getBean("isoRequestProcessor");
		balanceInquiryProcessor = (IBalanceInquiryProcessor)appContext.getBean("balanceEnqProcessor");
		FullfillmentEntryVO fullfillmentEntryVO = null;
		try {
			fullfillmentEntryVO = getFullfillmentMessageDetail(224636174L);
		} catch (BusinessServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fullfillmentDao.updateSharpVLStatus(FullfillmentConstants.VL_SYSTEM, "N");
		try {
			byte[] messageReversed = isoRequestProcessor.processRequest(fullfillmentEntryVO);
			jmsFacility.sendLocalMesage(messageReversed,new Long(0));
		} catch (UnknownMessageTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StringParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (com.newco.marketplace.exception.DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		balanceInquiryProcessor.kickStartBalanceInquiryMsgs();		
	}
	
	public FullfillmentEntryVO getFullfillmentMessageDetail(Long fullfillmentEntryId) throws BusinessServiceException{
		FullfillmentEntryVO fullfillmentEntryVO = null;
		Long nextFullfillmentEntryId = null;
		try{
			System.out.println("FullfillmentTransactionBO-->getFullfillmentMessageDetail()-->START");
			fullfillmentEntryVO = fullfillmentDao.getFullfillmentMessageDetail(fullfillmentEntryId);
			populateMessageIdentifier(fullfillmentEntryVO);
			if(fullfillmentEntryVO != null){
				nextFullfillmentEntryId = fullfillmentDao.getNextFullfillmentEntryId(fullfillmentEntryVO);
				if(nextFullfillmentEntryId != null){
					fullfillmentEntryVO.setNextFullfillmentEntryId(nextFullfillmentEntryId);
				}
			}
			
		}
		catch (DataServiceException dae) {
			System.out.println("getFullfillmentMessageDetail-->DataServiceException-->");
			throw new BusinessServiceException("getFullfillmentMessageDetail-->EXCEPTION-->", dae);
		} catch (Exception e) {
			System.out.println("getFullfillmentMessageDetail-->Exception-->");
			throw new BusinessServiceException("getFullfillmentMessageDetail-->EXCEPTION-->", e);
		}
		return fullfillmentEntryVO;
	}	
	
	private void populateMessageIdentifier(FullfillmentEntryVO fullfillmentEntryVO) throws Exception {
		if(fullfillmentEntryVO.getMessageTypeId() != null){
			if(FullfillmentConstants.MESSAGE_TYPE_ACTIVATION == fullfillmentEntryVO.getMessageTypeId().intValue()){
				fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.ACTIVATION_RELOAD_REQUEST);
			}
			else if(FullfillmentConstants.MESSAGE_TYPE_RELOAD == fullfillmentEntryVO.getMessageTypeId().intValue()){
				fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.ACTIVATION_RELOAD_REQUEST);
			}
			else if(FullfillmentConstants.MESSAGE_TYPE_REDEMPTION == fullfillmentEntryVO.getMessageTypeId().intValue()){
				fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.REDEMPTION_REQUEST);
			}
			else if(FullfillmentConstants.MESSAGE_TYPE_BALANCE_ENQ == fullfillmentEntryVO.getMessageTypeId().intValue()){
				fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.BALANCE_ENQUIRY_REQUEST);
			}
			else if(FullfillmentConstants.MESSAGE_TYPE_HEARTBEAT == fullfillmentEntryVO.getMessageTypeId().intValue()){
				fullfillmentEntryVO.setMessageIdentifier(FullfillmentConstants.SHARP_HEARTBEAT_REQUEST);
			}
		}
	} 	
}


