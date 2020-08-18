package com.servicelive.wallet.remoteclient;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.serviceinterface.vo.WalletMessageVO;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;
import com.servicelive.wallet.serviceinterface.vo.WalletMessageVO.MethodName;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;

public class WalletBO implements IWalletBO {

	private String walletRestUrl;
	private Client client = Client.create();
	private Logger logger = Logger.getLogger(WalletBO.class);

	public WalletBO(){
		client.addFilter(new LoggingFilter(java.util.logging.Logger.getLogger(WalletBO.class.getName())));
	}

	public WalletResponseVO adminCreditToBuyer(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.ADMIN_CREDIT_TO_BUYER, request);
	}

	public WalletResponseVO withdrawBuyerdebitReversal(WalletVO request)
			throws SLBusinessServiceException {

		return this.sendWalletRequest(MethodName.WITHDRAW_BUYER_DEBIT_REVERSAL, request);
	}

	public WalletResponseVO adminCreditToProvider(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.ADMIN_CREDIT_TO_PROVIDER, request);
	}

	public WalletResponseVO adminDebitFromBuyer(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.ADMIN_DEBIT_FROM_BUYER, request);
	}

	public WalletResponseVO adminEscheatmentFromBuyer(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.ADMIN_ESCHEATMENT_FROM_BUYER, request);
	}

	public WalletResponseVO adminDebitFromProvider(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.ADMIN_DEBIT_FROM_PROVIDER, request);
	}

	public WalletResponseVO adminEscheatmentFromProvider(WalletVO request)
			throws SLBusinessServiceException {

		return this.sendWalletRequest(MethodName.ADMIN_ESCHEATMENT_FROM_PROVIDER, request);
	}

	public WalletResponseVO cancelServiceOrder(WalletVO request)
			throws SLBusinessServiceException {

		return this.sendWalletRequest(MethodName.CANCEL_SERVICE_ORDER, request);
	}

	public WalletResponseVO cancelServiceOrderWithoutPenalty(WalletVO request)
			throws SLBusinessServiceException {

		return this.sendWalletRequest(MethodName.CANCEL_SERVICE_ORDER_WITHOUT_PENALTY, request);
	}

	public boolean checkValueLinkReconciledIndicator(String soId)
			throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/valueLink/%1$s", soId));
		return ((Boolean) responseVO.getResult()).booleanValue();
	}

	public boolean isACHTransPending(String soId) 
			throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/valueLinkAch/%1$s", soId));
		return ((Boolean) responseVO.getResult()).booleanValue();

	}

	public boolean hasPreviousAddOn(String soId) 
			throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/valueLinkAddOn/%1$s", soId));
		return ((Boolean) responseVO.getResult()).booleanValue();

	}

	public WalletResponseVO closeServiceOrder(WalletVO request)
			throws SLBusinessServiceException {

		return this.sendWalletRequest(MethodName.CLOSE_SERVICE_ORDER, request);
	}


	public Map<String, Long> createValueLinkWithNewAmount(
			String fulfillmentEntryId, Double newAmount, String comments,
			String userName) throws SLBusinessServiceException {
		throw new SLBusinessServiceException("createValueLinkWithNewAmount Method is not implemented");
	}

	public WalletResponseVO decreaseProjectSpendLimit(WalletVO request)
			throws SLBusinessServiceException {

		return this.sendWalletRequest(MethodName.DECREASE_PROJECT_SPEND_LIMIT, request);
	}

	public void depositBuyerFundsAtValueLink(WalletVO request)
			throws SLBusinessServiceException {
		throw new SLBusinessServiceException("depositBuyerFundsAtValueLink Method is not implemented");
	}

	public WalletResponseVO depositBuyerFundsWithCash(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.DEPOSIT_BUYER_FUNDS_WITH_CASH, request);
	}

	public WalletResponseVO depositBuyerFundsWithCreditCard(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.DEPOSIT_BUYER_FUNDS_WITH_CREDIT_CARD, request);
	}

	public WalletResponseVO depositBuyerFundsWithInstantACH(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.DEPOSIT_BUYER_FUNDS_WITH_INSTANT_ACH, request);
	}

	public WalletResponseVO depositOperationFunds(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.DEPOSIT_OPERATION_FUNDS, request);
	}

	public WalletResponseVO getWalletMessageResult(String messageId) throws SLBusinessServiceException {
		WebResource resource = client.resource(walletRestUrl);
		WalletResponseVO responseVO = resource.path(String.format("/wallet/messageResult/%1$s", messageId))
				.accept(MediaType.APPLICATION_XML_TYPE).get(WalletResponseVO.class);
		return responseVO;
	}

	public WalletResponseVO getCreditCardInformation(Long accountId) throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/creditCard/%1$d", accountId));
		return responseVO;
	}

	public void depositSLOperationFundsAtValueLink(WalletVO request)
			throws SLBusinessServiceException {
		throw new SLBusinessServiceException("depositSLOperationFundsAtValueLink Method is not implemented");
	}

	public double getBuyerAvailableBalance(long entityId)
			throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/availableBalance/%1$d", Long.valueOf(entityId)));
		return ((Double) responseVO.getResult()).doubleValue();
	}


	//SL-21117: Revenue Pull Code change starts

	public List <String> getPermittedUsers() throws BusinessServiceException{

		throw new BusinessServiceException("getPermittedUsers Method is not implemented");	
	}

	public double getAvailableBalanceForRevenuePull() throws SLBusinessServiceException {

		throw new SLBusinessServiceException("getAvailableBalanceForRevenuePull Method is not implemented");
	}


	public boolean getAvailableDateCheckForRevenuePull(Date calendarOnDate) throws SLBusinessServiceException {

		throw new SLBusinessServiceException("getAvailableDateCheckForRevenuePull Method is not implemented");
	}

	public void insertEntryForRevenuePull(double amount,Date revenuePullDate,String note,String user) throws SLBusinessServiceException {

		throw new SLBusinessServiceException("insertEntryForRevenuePull Method is not implemented");
	}

	public List <String> getPermittedUsersEmail() throws BusinessServiceException{

		throw new BusinessServiceException("getPermittedUsersEmail Method is not implemented");	
	}

	//Code change ends

	public long getAccountId(long entityId)
			throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/accountId/%1$d", Long.valueOf(entityId)));
		return ((Long) responseVO.getResult()).longValue();

	}
	public double getBuyerCurrentBalance(long entityId)
			throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/currentBalance/%1$d", String.valueOf(entityId)));
		return ((Double) responseVO.getResult()).doubleValue();
	}

	public double getBuyerTotalDeposit(Long buyerId) throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/buyerBalance/%1$d", buyerId));
		return ((Double) responseVO.getResult()).doubleValue();
	}

	public double getCompletedSOLedgerAmount(long vendorId) throws SLBusinessServiceException
	{
		WalletResponseVO responseVO = callRestService(String.format("/wallet/providerCompltedSoBalance/%1$d", vendorId));
		return ((Double) responseVO.getResult()).doubleValue();
	}

	public boolean isBuyerAutoFunded(Long buyerId) throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/buyer/%1$d/autoFunded", buyerId));
		return ((Boolean) responseVO.getResult()).booleanValue();
	}

	public double getCurrentSpendingLimit(String serviceOrderId) throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/spendingLimit/%1$s", serviceOrderId));
		return ((Double) responseVO.getResult()).doubleValue();
	}

	public double getProviderBalance(long entityId) throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/providerBalance/%1$d", Long.valueOf(entityId)));
		return ((Double) responseVO.getResult()).doubleValue();
	}

	public double getSLOperationBalance() throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService("/wallet/opsBalance");
		return ((Double) responseVO.getResult()).doubleValue();
	}

	public List<ValueLinkEntryVO> getValueLinkEntries(String[] valueLinkEntryId, Boolean groupId)
			throws SLBusinessServiceException {
		throw new SLBusinessServiceException("getValueLinkEntries Method is not implemented");
	}

	public WalletResponseVO increaseProjectSpendLimit(WalletVO request)
			throws SLBusinessServiceException {

		return this.sendWalletRequest(MethodName.INCREASE_PROJECT_SPEND_LIMIT, request);
	}

	public WalletResponseVO postServiceOrder(WalletVO request)
			throws SLBusinessServiceException {

		return this.sendWalletRequest(MethodName.POST_SERVICE_ORDER, request);
	}

	public List<ValueLinkEntryVO> processGroupResend(
			String[] fulfillmentGroupIds, String comments, String userName)
					throws SLBusinessServiceException {
		throw new SLBusinessServiceException("processGroupResend Method is not implemented");
	}

	public Map<String, Long> reverseValueLinkTransaction(String[] valueLinkIds,
			String comments, String userName) throws SLBusinessServiceException {
		throw new SLBusinessServiceException("reverseValueLinkTransaction Method is not implemented");
	}

	public WalletResponseVO voidServiceOrder(WalletVO request)
			throws SLBusinessServiceException {

		return this.sendWalletRequest(MethodName.VOID_SERVICE_ORDER, request);
	}


	public WalletResponseVO increaseProjectSpendCompletion(WalletVO request) throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.INCREASE_PROJECT_SPEND_COMPLETION, request);
	}

	public WalletResponseVO withdrawBuyerCashFunds(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.WITHDRAW_BUYER_CASH_FUNDS, request);
	}

	public WalletResponseVO withdrawBuyerCreditCardFunds(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.WITHDRAW_BUYER_CREDIT_CARD_FUNDS, request);
	}

	public WalletResponseVO withdrawOperationFunds(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.WITHDRAW_OPERATION_FUNDS, request);
	}

	public WalletResponseVO withdrawProviderFunds(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.WITHDRAW_PROVIDER_FUNDS, request);
	}

	public WalletResponseVO withdrawProviderFundsReversal(WalletVO request)
			throws SLBusinessServiceException {
		return this.sendWalletRequest(MethodName.WITHDRAW_PROVIDER_FUNDS_REVERSAL, request);
	}


	public WalletResponseVO activateBuyer(WalletVO request)
			throws SLBusinessServiceException {
		throw new SLBusinessServiceException("reverseValueLinkTransaction Method is not implemented");
	}

	public ReceiptVO getTransactionReceipt(Long entityId, Integer entityTypeId,
			LedgerEntryType entryType, String serviceOrderId) throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/receipt/%1$d/%2$d/%3$d/%4$s", entityId, entityTypeId, entryType.getId(), serviceOrderId));
		logger.info("responseVO: "+responseVO);
		logger.info("responseVO.getResult(): "+responseVO.getResult());
		return (ReceiptVO) responseVO.getResult();
	}

	public Double getTransactionAmount(Long transactionId) throws SLBusinessServiceException {
		WalletResponseVO responseVO = callRestService(String.format("/wallet/transactionAmount/%1$d", transactionId));
		return (Double) responseVO.getResult();
	}

	/// Rest call
	private WalletResponseVO callRestService(String path) throws SLBusinessServiceException{
		WebResource resource = client.resource(walletRestUrl);
		WalletResponseVO responseVO = resource.path(path)
				.accept(MediaType.APPLICATION_XML_TYPE).get(WalletResponseVO.class);
		if (responseVO.isError()){
			throw new SLBusinessServiceException(responseVO.getErrorMessages());
		}

		return responseVO;
	}

	///  JMS IMPLEMENTATION
	private WalletResponseVO sendWalletRequest( MethodName method, WalletVO request ) {
		WalletResponseVO response = new WalletResponseVO();
		try{
			WalletMessageVO message = new WalletMessageVO(method, request);
			logger.info("Sending a message to wallet " + message.toString());            
			String textMessage = serializeWalletRequest(message);
			String jmsMessageID = sendTextMessage(textMessage);
			response.setMessageId(message.getMessageId());
			response.setJmsMessageId( jmsMessageID );
		}catch(Exception e){
			logger.error("Error occured while sending Wallet Request",e);
			response.addErrorMessage(e.getMessage());
		}
		return response;
	}

	private String serializeWalletRequest( WalletMessageVO message ) throws SLBusinessServiceException {

		try {
			JAXBContext ctx = JAXBContext.newInstance(WalletMessageVO.class);
			Marshaller marshaller = ctx.createMarshaller();
			OutputStream outputStream = new ByteArrayOutputStream();
			marshaller.marshal(message, outputStream);
			return outputStream.toString();
		} catch (JAXBException e) {
			logger.error("Error occured while serializing Wallet Request",e);
			throw new SLBusinessServiceException("Error occured while deserializing Wallet Request", e);
		}
	}

	private String sendTextMessage(String message) throws JMSException {
		WalletMessageCreator wmc = new WalletMessageCreator(message);
		this.jmsTemplate.send( wmc );
		logger.info("Sent wallet message with JMS ID: " + wmc.getJMSMessageID());
		return wmc.getJMSMessageID();
	}
	class WalletMessageCreator implements MessageCreator {
		private TextMessage jmsMessage; // need to store a reference to the message
		// because the JMSMessageID cannot be retrieved until
		// after the message is sent
		private String message;
		public String getJMSMessageID() throws JMSException {
			return jmsMessage.getJMSMessageID();
		}
		public WalletMessageCreator( String message ) {
			this.message = message;
		}
		public Message createMessage(Session session) throws JMSException {
			jmsMessage = session.createTextMessage(message);
			return jmsMessage;
		}
	}

	private JmsTemplate jmsTemplate;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setWalletRestUrl(String walletRestUrl) {
		this.walletRestUrl = walletRestUrl;
	}

	public WalletResponseVO authCCForDollarNoCVV(WalletVO request)
			throws SLBusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean sendBalanceInquiryMessage(Integer vendorId)
			throws SLBusinessServiceException {
		throw new UnsupportedOperationException("Method not implemented on the client.");
	}

	public String getLedgerEntryNonce(long busTransId) 
			throws SLBusinessServiceException {
		throw new UnsupportedOperationException("Method not implemented on the client.");
	}
	public String getApplicationFlagForHSWebService(String hsWebserviceAppKey)
			throws SLBusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}
}
