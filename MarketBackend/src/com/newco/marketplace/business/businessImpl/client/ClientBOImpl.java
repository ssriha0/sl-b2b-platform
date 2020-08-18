package com.newco.marketplace.business.businessImpl.client;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.client.IClientBO;
import com.newco.marketplace.persistence.iDao.client.IClientInvoiceDAO;

public class ClientBOImpl implements IClientBO {
	
	private static final Logger logger = Logger.getLogger(ClientBOImpl.class);
	
	private IClientInvoiceDAO clientInvoiceDAO;
	
	public Integer getClientIdForName(String clientName) {
		return clientInvoiceDAO.getClientIDForName(clientName);
	}
	
	
	public boolean isValidClient(String clientId, String clientName){
		boolean isValidClient = false;
		Integer clientIdToCompare = getClientIdForName(clientName);
		if(clientIdToCompare!= null){
			isValidClient = clientIdToCompare.toString().equals(clientId); 
		}
		return isValidClient;
	}

	public IClientInvoiceDAO getClientInvoiceDAO() {
		return clientInvoiceDAO;
	}

	public void setClientInvoiceDAO(IClientInvoiceDAO clientInvoiceDAO) {
		this.clientInvoiceDAO = clientInvoiceDAO;
	}
	

}
