package com.servicelive.wallet.remoteservice.jms;

import java.io.ByteArrayInputStream;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.ejb.support.AbstractMessageDrivenBean;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.common.NotEnoughMoneyException;
import com.servicelive.wallet.remoteservice.dao.IWalletMessageDao;
import com.servicelive.wallet.remoteservice.vo.MessageResultVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.vo.WalletMessageVO;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;
import com.servicelive.wallet.serviceinterface.vo.WalletMessageVO.MethodName;


public class WalletQueueServiceBean extends AbstractMessageDrivenBean 
implements javax.ejb.MessageDrivenBean, javax.jms.MessageListener {

	private static final long serialVersionUID = 267666215497057405L;

	private static final String BEAN_FACTORY_NAME = "Wallet.RemoteService.EJB";

	/** logger. */
	private static final Logger logger = Logger.getLogger(WalletQueueServiceBean.class);

	private IWalletBO wallet;
	private IWalletMessageDao walletMessageDao;
	
	@Override
	protected void onEjbCreate() {
		wallet = (IWalletBO)this.getBeanFactory().getBean("wallet");
		walletMessageDao = (IWalletMessageDao) this.getBeanFactory().getBean("walletMessageDao");
	}

	@Override
	public void setMessageDrivenContext(MessageDrivenContext context)
			throws EJBException {
		super.setMessageDrivenContext(context);
        this.setBeanFactoryLocator(ContextSingletonBeanFactoryLocator
                .getInstance());
        this.setBeanFactoryLocatorKey(BEAN_FACTORY_NAME);
	}

	public void onMessage(Message message) {
		logger.debug("Wallet Service MDB received a message");
		WalletMessageVO walletMessage = null;
		try {
			if(message instanceof TextMessage){
				TextMessage textMessage = (TextMessage) message;
				walletMessage = deserializeWalletRequest(textMessage.getText());
				WalletResponseVO response = dispatchRequest(walletMessage);
				if(response.isError())
					saveMessageProcessingResult(walletMessage.getMessageId(), false, response.getErrorMessages().toString(), null);
				else
					saveMessageProcessingResult(walletMessage.getMessageId(), true, null, getTransactionId(response));
			}
			else {
				throw new JMSException("Expected message to be instance of TextMessage but got " + message.getClass().getName());
			}
		} catch (JMSException e) {
			logger.error("Failed to retrieve message... Re-deliver.",e);
			throw new EJBException(e);
		}catch (NotEnoughMoneyException neme){
			try {
				saveMessageProcessingResult(walletMessage.getMessageId(), false, neme.getMessage(), null);
				logger.info("There was not enough money in the wallet to perform operation " + walletMessage.getMethod());
			} catch (Exception e) {
				logger.error("Failed to write error message in the database",e);
				throw new EJBException(e);
			}
		}catch (SLBusinessServiceException e) {
			logger.error("Failed to handle message... Re-deliver.",e);
			throw new EJBException(e);
		} catch (Exception e) {
			logger.error("Failed to handle message... Re-deliver.",e);
			throw new EJBException(e);
		}	
	}
	
	private WalletMessageVO deserializeWalletRequest( String textMessage )throws SLBusinessServiceException {
		WalletMessageVO message = null;
		try {
			JAXBContext ctx = JAXBContext.newInstance(WalletMessageVO.class);
			Unmarshaller unMarshaller = ctx.createUnmarshaller();
			message = (WalletMessageVO) unMarshaller.unmarshal(new ByteArrayInputStream(textMessage.getBytes()));
		} catch (JAXBException e) {
			logger.error("Error occurred while de-serializing Wallet Request",e);
			throw new SLBusinessServiceException("Error occurred while de-serializing Wallet Request", e);
		}
		
		return message;
	}

    private Long getTransactionId(WalletResponseVO response){
        if(null != response.getProviderTransactionId()) return response.getProviderTransactionId();
        if(null != response.getBuyerTransactionId()) return response.getBuyerTransactionId();
        return response.getTransactionId();
    }

	private void saveMessageProcessingResult(String messageId, boolean result, String errorMessage, Long transactionId) throws Exception{
		MessageResultVO message = new MessageResultVO();
		message.setMessageId(messageId);
		message.setErrorMessage(errorMessage);
		message.setResult(result);
		message.setTransactionId(transactionId);
		walletMessageDao.insertMessage(message);
	}
	
	private WalletResponseVO dispatchRequest(WalletMessageVO message) throws SLBusinessServiceException, NotEnoughMoneyException {

		MethodName method = message.getMethod();
		WalletVO request = message.getRequest();
        logger.info("Wallet Response Bean received a message " + message.toString());
        
        logger.info("WalletQueueServiceBean = " + wallet + " AND method = " + method); 
        
		switch(method){
		case ADMIN_CREDIT_TO_BUYER:
			return wallet.adminCreditToBuyer(request); 
		case ADMIN_CREDIT_TO_PROVIDER:
			return wallet.adminCreditToProvider(request); 
		case ADMIN_DEBIT_FROM_BUYER:
			return wallet.adminDebitFromBuyer(request); 
		case ADMIN_DEBIT_FROM_PROVIDER:
			return wallet.adminDebitFromProvider(request); 
		case CANCEL_SERVICE_ORDER:
			return wallet.cancelServiceOrder(request); 
		case CANCEL_SERVICE_ORDER_WITHOUT_PENALTY:
			return wallet.cancelServiceOrderWithoutPenalty(request); 
		case CLOSE_SERVICE_ORDER:
			return wallet.closeServiceOrder(request); 
		case DECREASE_PROJECT_SPEND_LIMIT:
			return wallet.decreaseProjectSpendLimit(request); 
		case DEPOSIT_BUYER_FUNDS_WITH_CASH:
			return wallet.depositBuyerFundsWithCash(request); 
		case DEPOSIT_BUYER_FUNDS_WITH_CREDIT_CARD:
			return wallet.depositBuyerFundsWithCreditCard(request); 
		case DEPOSIT_BUYER_FUNDS_WITH_INSTANT_ACH:
			return wallet.depositBuyerFundsWithInstantACH(request); 
		case DEPOSIT_OPERATION_FUNDS:
			return wallet.depositOperationFunds(request); 
		case INCREASE_PROJECT_SPEND_LIMIT:
			return wallet.increaseProjectSpendLimit(request); 
		case INCREASE_PROJECT_SPEND_COMPLETION:
			return wallet.increaseProjectSpendCompletion(request);
		case POST_SERVICE_ORDER:
			return wallet.postServiceOrder(request); 
		case VOID_SERVICE_ORDER:
			return wallet.voidServiceOrder(request); 
		case WITHDRAW_BUYER_CASH_FUNDS:
			return wallet.withdrawBuyerCashFunds(request); 
		case WITHDRAW_BUYER_CREDIT_CARD_FUNDS:
			return wallet.withdrawBuyerCreditCardFunds(request); 
		case WITHDRAW_OPERATION_FUNDS:
			return wallet.withdrawOperationFunds(request); 
		case WITHDRAW_PROVIDER_FUNDS:
			return wallet.withdrawProviderFunds(request); 
		case WITHDRAW_PROVIDER_FUNDS_REVERSAL:
			return wallet.withdrawProviderFundsReversal(request); 
		default:
			throw new SLBusinessServiceException(method.name() + " is not supported.");
		}
	}


}
