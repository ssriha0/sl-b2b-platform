package com.newco.marketplace.remoteclient;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
public class WalletClient {
	
	/**
	 * This class is for invoking the wallet using client to fix the wallet dirty read issue 
	 */

	private String walletRestUrl;
	private Client client = Client.create();
	private Logger logger = Logger.getLogger(WalletClient.class);
	
	public WalletClient(){
		client.addFilter(new LoggingFilter(java.util.logging.Logger.getLogger(WalletClient.class.getName())));
	}

    //SL-20987 -Wallet Dirty Read --starts here
	/**
	 * @Description Method to call a rest service to withdraw provider funds
	 * @param request
	 * @return WalletResponseVO
	 * @throws SLBusinessServiceException
	 */
	public WalletResponseVO withdrawProviderFunds(WalletVO request)throws SLBusinessServiceException {
		
		//call rest service
		WalletResponseVO responseVO = callRestServiceForPost(String.format("/wallet/withdrawProviderFunds"), request);
		return (WalletResponseVO) responseVO;
	}
    
	public void setWalletRestUrl(String walletRestUrl) {
		this.walletRestUrl = walletRestUrl;
	}

	// SL-20987 Rest client call
	/**
	 * @Description Rest client call to invoke the withdraw for provider funds in the wallet 
	 * @param path
	 * @param request
	 * @return WalletResponseVO
	 * @throws SLBusinessServiceException
	 */
	private WalletResponseVO callRestServiceForPost(String path, WalletVO request) throws SLBusinessServiceException{
		WalletResponseVO responseVO=null;
		try{
			WebResource resource = client.resource(walletRestUrl);
			responseVO = resource.path(path).accept(MediaType.APPLICATION_XML_TYPE).post(WalletResponseVO.class, request);

		}
		catch (Exception e) {
			 throw new SLBusinessServiceException(CommonConstants.INTERNAL_SERVER_ERROR);
		}
		
		return responseVO;
	}
	//SL-20987 -ends here
	
}
