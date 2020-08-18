package com.servicelive.wallet.valuelink;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.wallet.common.IIdentifierDao;
import com.servicelive.wallet.common.WalletConstants;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.valuelink.dao.IValueLinkQueueDao;
import com.servicelive.wallet.valuelink.sharp.ISharpCallback;
import com.servicelive.wallet.valuelink.sharp.ISharpGateway;
import com.servicelive.wallet.valuelink.sharp.iso.IIsoRequestProcessor;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageVO;
import com.servicelive.wallet.valuelink.sharp.socket.bo.SharpSocketBo;
import com.servicelive.wallet.valuelink.socket.SocketContainer;
import com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO;
// TODO: Auto-generated Javadoc
/**
 * Class ValueLinkRequestHandlerBO.
 */
public class ValueLinkRequestHandlerBO implements IValueLinkRequestHandlerBO {

	/** logger. */
	private static final Logger logger = Logger.getLogger(ValueLinkRequestHandlerBO.class);

	/** isoRequestProcessor. */
	private IIsoRequestProcessor isoRequestProcessor;

	/** sharpGateway. */
	private ISharpGateway sharpGateway;

	/** valueLinkQueueDao. */
	private IValueLinkQueueDao valueLinkQueueDao;
	
	/** applicationProperties. */
	private IApplicationProperties applicationProperties;

	private IIdentifierDao identifierDao;
	
	private SharpSocketBo sharpSocketBo;
	
	private ISharpCallback valueLinkResponseHandlerBO;
	
	/**
	 * constructIsoMessage.
	 * 
	 * @param entry 
	 * 
	 * @return IsoMessageVO
	 * 
	 * @throws Exception 
	 */
	private IsoMessageVO constructIsoMessage(ValueLinkEntryVO entry) throws Exception {

		IsoMessageVO message = new IsoMessageVO();
		message.setStoreNo(this.applicationProperties.getPropertyValue(
			CommonConstants.SL_STORE_NO) );
		
		message.setFullfillmentEntryId(entry.getFullfillmentEntryId());
		message.setFullfillmentGroupId(entry.getFullfillmentGroupId());
		message.setLedgerEntityId(entry.getLedgerEntityId());
		message.setStanId(entry.getStanId());
		message.setMessageTypeId(entry.getMessageTypeId());

		if( entry.getMessageTypeId() != null ) {
			String messageIdentifier = IsoMessageVO.getMessageIdentifier(entry.getMessageTypeId());
			message.setMessageIdentifier(messageIdentifier);
		}
		String queueName = sharpGateway.getReplyTo().getQueueName();
		String queueNameSubStr = "";
		if(queueName!=null)
		{
			int replyIdx = queueName.indexOf("?");
			logger.info("queueName:= "+ queueName);
			queueNameSubStr = replyIdx != -1 ? queueName.substring(
					replyIdx - 2, replyIdx) : queueName.substring(queueName
					.length() - 2);
		}
		message.setStoreDeviceNumber("0"+queueNameSubStr);
		message.setPrimaryAccountNumber(entry.getPrimaryAccountNumber());
		message.setPromoCode(entry.getPromoCode());
		message.setReferenceNo(entry.getReferenceNo());
		message.setTransAmount(entry.getTransAmount());
		message.setTimeStamp(entry.getTimeStamp());
		message.setAuthorizationId(entry.getAuthorizationId());
		message.setAdditionalResponse(entry.getAdditionalResponse());
		message.setPartiallyAppvdAmount(entry.getPartiallyAppvdAmount());
		message.setRetrievalRefId(entry.getRetrievalRefId());
		return message;
	}

	private IsoMessageVO constructHeartbeatMessage() throws Exception {
		IsoMessageVO message = new IsoMessageVO();
		message.setStoreNo(this.applicationProperties.getPropertyValue(
				CommonConstants.SL_STORE_NO) );
		message.setFullfillmentEntryId(1234L);
		String queueName = sharpGateway.getReplyTo().getQueueName();
		String queueNameSubStr = "";
		if(queueName!=null)
		{
			int replyIdx = queueName.indexOf("?");
			logger.info("queueName:= "+ queueName);
			queueNameSubStr = replyIdx != -1 ? queueName.substring(
					replyIdx - 2, replyIdx) : queueName.substring(queueName
					.length() - 2);
		}
		message.setStoreDeviceNumber("0"+queueNameSubStr);
		message.setMessageIdentifier(CommonConstants.SHARP_HEARTBEAT_REQUEST);
		String stanId = identifierDao.getNextIdentifier(CommonConstants.STAN_ID).toString();
		message.setStanId(stanId);
		return message;
	}
	
	/**
	 * constructQueueEntry.
	 * 
	 * @param message 
	 * @param bmsg 
	 * 
	 * @return ValueLinkQueueEntryVO
	 */
	private ValueLinkQueueEntryVO constructQueueEntry(IsoMessageVO message, byte[] bmsg) {

		ValueLinkQueueEntryVO queueEntry = new ValueLinkQueueEntryVO();
		queueEntry.setFulfillmentEntryId(message.getFullfillmentEntryId());
		queueEntry.setFulfillmentGroupId(message.getFullfillmentGroupId());
		queueEntry.setPrimaryAccountNumber(message.getPrimaryAccountNumber());
		queueEntry.setSendCount(0);
		queueEntry.setQueueStatus(ValueLinkQueueEntryVO.QueueStatus.WAITING);
		queueEntry.setMessageTypeId(message.getMessageTypeId());
		queueEntry.setMessage(bmsg);
		return queueEntry;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestHandlerBO#createValueLinkMessages()
	 */
	public void createValueLinkMessages() throws SLBusinessServiceException {

		try {
			// retrieve entries ready to be enqueued
			// 1. Entry was produced by new queue architecture
			// 2. No entries with same PAN in queue
			// 3. No entries with same Group ID in queue
			// 4. Ordered by sort_order (ascending)
			List<ValueLinkEntryVO> entries = valueLinkQueueDao.getValueLinkEntriesReadyForQueue();

			logger.info("Found " + entries.size() + " entries ready for queue.");
			
			HashSet<Long> pans = new HashSet<Long>();
			HashSet<Long> groups = new HashSet<Long>();
			HashSet<Long> orginatingIds = new HashSet<Long>();
			Set<Long> feIdHold = new HashSet<Long>();

			for (ValueLinkEntryVO entry : entries) {
				
				// if we retrieve more than one entry with the same PAN or GROUP,
				// then we should only insert the first one we come to and then
				// skip the others
				if (entry.getFundingTypeId() == WalletConstants.SHC_FUNDING_TYPE
						&& !groups.contains(entry.getFullfillmentGroupId())
						&& !pans.contains(entry.getPrimaryAccountNumber())) {

						// construct the ISO message
						IsoMessageVO message = constructIsoMessage(entry);

						// convert it to ISO format (byte[])
						byte[] bmsg = isoRequestProcessor.processRequest(message);
						
						// construct the queue entry
						ValueLinkQueueEntryVO queueEntry = constructQueueEntry(message, bmsg);

					logger.info("Creating entry "+ queueEntry.getFulfillmentEntryId());

						// enqueue the message
						valueLinkQueueDao.createQueueEntry(queueEntry);
				} else if (!orginatingIds.contains(entry.getOriginatingEntityId())
						&& !groups.contains(entry.getFullfillmentGroupId())) {
					// construct the ISO message
					IsoMessageVO message = constructIsoMessage(entry);

					// convert it to ISO format (byte[])
					byte[] bmsg = isoRequestProcessor.processRequest(message);

					// construct the queue entry
					ValueLinkQueueEntryVO queueEntry = constructQueueEntry(message, bmsg);

					logger.info("Creating entry " + queueEntry.getFulfillmentEntryId());

					// enqueue the message
					valueLinkQueueDao.createQueueEntry(queueEntry);
                    }

				// keep track of pans and groups, so we only process one for each
				pans.add(entry.getPrimaryAccountNumber());
				groups.add(entry.getFullfillmentGroupId());
				orginatingIds.add(entry.getOriginatingEntityId());
			}
		} catch (Exception e) {
			throw new SLBusinessServiceException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.IValueLinkRequestHandlerBO#sendValueLinkMessages()
	 */
	public void sendValueLinkMessages(boolean useIPSocket) throws SLBusinessServiceException {

		List<ValueLinkQueueEntryVO> entries = valueLinkQueueDao.getQueueEntriesByStatus(ValueLinkQueueEntryVO.QueueStatus.WAITING);
		logger.info("Found " + entries.size() + " entries ready to send.");
		try{
			for (ValueLinkQueueEntryVO entry : entries) {
				logger.info("Normal financial transaction :Sending entry " + entry.getFulfillmentEntryId() );
				
				logger.info("Normal financial transaction : entry.getMessageTypeId()" + entry.getMessageTypeId());
				logger.info("Normal financial transaction : entry.getMessage()" + entry.getMessage());
				
				 byte[] bytes=entry.getMessage();
				 byte[] response = null;
				 String s = new String(bytes);
				 logger.info("SENDING REQUEST "+s);
				 if(!useIPSocket)
						sharpGateway.sendMessage(entry.getMessage());
					else{
						logger.info("using IP Sockets");
						response = sharpSocketBo.sendToSharp(entry.getMessage());
					}
				//sharpGateway.sendMessage(entry.getMessage());
				entry.setQueueStatus(ValueLinkQueueEntryVO.QueueStatus.SENT);
				valueLinkQueueDao.updateQueueEntry(entry);
				valueLinkResponseHandlerBO.handleMessage(response);
			}
		}catch (DataNotFoundException e) {
			throw new SLBusinessServiceException(e);
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SLBusinessServiceException(e);
		}
	}
	
	public void sendHeartbeatMessage(boolean useIPSocket) throws SLBusinessServiceException {
		
		try {
			logger.info("Sending heartbeat message.");
			IsoMessageVO message = this.constructHeartbeatMessage();
			byte [] bytesMessage = this.isoRequestProcessor.processRequest(message);
			System.out.println("sending heartbeat ISO message: "+bytesMessage);
			if(!useIPSocket)
				sharpGateway.sendMessage(bytesMessage);
			else{
				valueLinkResponseHandlerBO.handleMessage(sharpSocketBo.sendToSharp(bytesMessage));
			}
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			throw new SLBusinessServiceException(e);
		} catch (DataServiceException e) {
			e.printStackTrace();
			throw new SLBusinessServiceException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SLBusinessServiceException(e);
		}
	}

		
	public void sendHeartbeatMessage(SocketContainer socket) throws SLBusinessServiceException {
		try {
			IsoMessageVO message = this.constructHeartbeatMessage();
			byte [] bytesMessage = this.isoRequestProcessor.processRequest(message);
		
			valueLinkResponseHandlerBO.handleMessage(sharpSocketBo.sendToSharp(socket, bytesMessage));
		} catch (DataNotFoundException e) {
			throw new SLBusinessServiceException(e);
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e);
		} catch (Exception e) {
			throw new SLBusinessServiceException(e);
		}
	}
	
	/**
	 * setIsoRequestProcessor.
	 * 
	 * @param isoRequestProcessor 
	 * 
	 * @return void
	 */
	public void setIsoRequestProcessor(IIsoRequestProcessor isoRequestProcessor) {

		this.isoRequestProcessor = isoRequestProcessor;
	}

	/**
	 * setSharpGateway.
	 * 
	 * @param sharpGateway 
	 * 
	 * @return void
	 */
	public void setSharpGateway(ISharpGateway sharpGateway) {

		this.sharpGateway = sharpGateway;
	}

	/**
	 * setValueLinkQueueDao.
	 * 
	 * @param valueLinkQueue 
	 * 
	 * @return void
	 */
	public void setValueLinkQueueDao(IValueLinkQueueDao valueLinkQueue) {

		this.valueLinkQueueDao = valueLinkQueue;
	}

	
	/**
	 * setApplicationProperties.
	 * 
	 * @param applicationProperties 
	 * 
	 * @return void
	 */
	public void setApplicationProperties(IApplicationProperties applicationProperties) {
	
		this.applicationProperties = applicationProperties;
	}

	public void setIdentifierDao(IIdentifierDao identifierDao) {
		this.identifierDao = identifierDao;
	}

	public SharpSocketBo getSharpSocketBo() {
		return sharpSocketBo;
	}

	public void setSharpSocketBo(SharpSocketBo sharpSocketBo) {
		this.sharpSocketBo = sharpSocketBo;
	}

	public ISharpCallback getValueLinkResponseHandlerBO() {
		return valueLinkResponseHandlerBO;
	}

	public void setValueLinkResponseHandlerBO(
			ISharpCallback valueLinkResponseHandlerBO) {
		this.valueLinkResponseHandlerBO = valueLinkResponseHandlerBO;
	}
}
