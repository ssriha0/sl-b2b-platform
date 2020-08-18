package com.servicelive.wallet.remoteservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.servicelive.common.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.rest.RemoteServiceStatus;
import com.servicelive.wallet.remoteservice.dao.IWalletMessageDao;
import com.servicelive.wallet.remoteservice.vo.MessageResultVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

//
//purpose of this class is to map URLs, query string parameters and request body to calls
//to walletBO
//
@Path("/")
public class RestWalletBO {

	private IWalletBO walletBO;
	private IWalletMessageDao walletMessageDao;

    @GET
    @Path("/servicestatus")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public RemoteServiceStatus getRemoteServiceStatus() {
    	RemoteServiceStatus serviceStatus = new RemoteServiceStatus();
    	serviceStatus.setActive(true);
        return serviceStatus;
    }

    @GET
	@Path("/wallet/valueLink/{soId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO checkValueLinkReconciledIndicator(@PathParam("soId") String soId) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			boolean ind = walletBO.checkValueLinkReconciledIndicator(soId);
			response.setResult(new Boolean(ind));
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
    
    @GET
	@Path("/wallet/valueLinkAch/{soId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO isACHTransPending(@PathParam("soId") String soId) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			boolean ind = walletBO.isACHTransPending(soId);
			response.setResult(new Boolean(ind));
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

    
    @GET
	@Path("/wallet/valueLinkAddOn/{soId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO hasPreviousAddOn(@PathParam("soId") String soId) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			boolean ind = walletBO.hasPreviousAddOn(soId);
			response.setResult(new Boolean(ind));
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
    
    
    
    
	@GET
	@Path("/wallet/availableBalance/{entityId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO getBuyerAvailableBalance(@PathParam("entityId") long entityId) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			double amount = walletBO.getBuyerAvailableBalance(entityId);
			response.setResult(new Double(amount));
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	@GET
	@Path("/wallet/accountId/{entityId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO getAccountId(@PathParam("entityId") long entityId) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			long  accountId = walletBO.getAccountId(entityId);
			response.setResult(new Long(accountId));
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	@GET
	@Path("/wallet/providerCompltedSoBalance/{entityId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO getCompletedSOLedgerAmount(@PathParam("entityId") long entityId) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			double amount = walletBO.getCompletedSOLedgerAmount(entityId);
			response.setResult(new Double(amount));
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	
	
	@GET
	@Path("/wallet/currentBalance/{entityId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO getBuyerCurrentBalance(@PathParam("entityId") long entityId) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			double amount = walletBO.getBuyerCurrentBalance(entityId);
			response.setResult(new Double(amount));
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@GET
	@Path("/wallet/buyerBalance/{entityId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO getBuyerTotalDeposit(@PathParam("entityId") Long entityId) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			double amount = walletBO.getBuyerTotalDeposit(entityId);
			response.setResult(new Double(amount));
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@GET
	@Path("/wallet/spendingLimit/{serviceOrderId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO getCurrentSpendingLimit(@PathParam("serviceOrderId") String serviceOrderId) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			double amount = walletBO.getCurrentSpendingLimit(serviceOrderId);
			response.setResult(new Double(amount));
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@GET
	@Path("/wallet/providerBalance/{entityId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO getProviderBalance(@PathParam("entityId") long entityId) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			double amount = walletBO.getProviderBalance(entityId);
			response.setResult(new Double(amount));
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@GET
	@Path("/wallet/opsBalance")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO getSLOperationBalance() {
		WalletResponseVO response = new WalletResponseVO();
		try {
			double amount = walletBO.getSLOperationBalance();
			response.setResult(new Double(amount));
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@GET
	@Path("/wallet/messageResult/{messageId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO getMessageResult(@PathParam("messageId") String messageId){
		WalletResponseVO response = new WalletResponseVO();
		try {
			MessageResultVO walletMessage = walletMessageDao.getResult(messageId);
			if (walletMessage != null){
				response.setResult(walletMessage.getResult());
				if(!walletMessage.getResult())
					response.addErrorMessage(walletMessage.getErrorMessage());
				if (walletMessage.getTransactionId() != null)
					response.setTransactionId(walletMessage.getTransactionId());
			}
		} catch (DataServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@GET
	@Path("/wallet/receipt/{entityId}/{entityTypeId}/{ledgerRuleId}/{soId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO getTransactionReceipt(@PathParam("entityId") Long entityId,
						@PathParam("entityTypeId") Integer entityTypeId, @PathParam("ledgerRuleId") Integer ledgerRuleId,
						@PathParam("soId") String soId){
		WalletResponseVO response = new WalletResponseVO();
		try {
			ReceiptVO receipt = walletBO.getTransactionReceipt(entityId, entityTypeId, LedgerEntryType.fromId(ledgerRuleId), soId);
			response.setResult(receipt);
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	@GET
	@Path("/wallet/buyer/{buyerId}/autoFunded")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO isBuyerAutoFunded(@PathParam("buyerId") Long buyerId){
		WalletResponseVO response = new WalletResponseVO();
		try {
			response.setResult(walletBO.isBuyerAutoFunded(buyerId));
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	@GET
	@Path("/wallet/creditCard/{accountId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO getCreditCardInformation(@PathParam("accountId") long accountId) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			response = walletBO.getCreditCardInformation(accountId);
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	@GET
	@Path("/wallet/transactionAmount/{transactionId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO getTransactionAmount(@PathParam("transactionId") long transactionId) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			Double amount = walletBO.getTransactionAmount(transactionId);
			response.setResult(amount);
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}
	
	//SL-20987 starts here --
	
	@POST
	@Path("/wallet/withdrawProviderFunds")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public WalletResponseVO withdrawProviderFunds(WalletVO request) {
		WalletResponseVO response = new WalletResponseVO();
		try {
			response = walletBO.withdrawProviderFunds(request);
		} catch (SLBusinessServiceException e) {
			response.addErrorMessage(e.getMessage());
			e.printStackTrace();
		}
			return response;
		}
	
	//SL-20987 ends here --
	
	public void setWalletBO(IWalletBO walletBO) {
		this.walletBO = walletBO;
	}

	public void setWalletMessageDao(IWalletMessageDao walletMessageDao) {
		this.walletMessageDao = walletMessageDao;
	}

}
