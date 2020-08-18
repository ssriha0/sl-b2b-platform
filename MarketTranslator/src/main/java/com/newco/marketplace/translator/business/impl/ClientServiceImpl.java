package com.newco.marketplace.translator.business.impl;

import org.apache.log4j.Logger;

import com.newco.marketplace.translator.business.IClientService;
import com.newco.marketplace.translator.dao.Client;
import com.newco.marketplace.translator.dao.IClientDAO;
import com.newco.marketplace.translator.dao.IWarrantyContractDAO;
import com.newco.marketplace.translator.dao.WarrantyContract;

public class ClientServiceImpl implements IClientService {
	
	private static final Logger logger = Logger.getLogger(ClientServiceImpl.class);
	private IClientDAO clientDAO;
	private IWarrantyContractDAO warrantyDAO;

	public Client getClient(String clientName) {
		try {
			return clientDAO.findByClientName(clientName);
		}
		catch (Exception e) {
			logger.error("Error finding client by name", e);
		}
		return null;
	}
	
	public WarrantyContract getWarrantyContract(Client client, String contractNumber) {
		try {
			return warrantyDAO.findByClientAndContract(client, contractNumber);
		}
		catch (Exception e) {
			logger.error("Error finding warranty by client and contract", e);
		}
		return null;
	}

	public IClientDAO getClientDAO() {
		return clientDAO;
	}

	public void setClientDAO(IClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	public IWarrantyContractDAO getWarrantyDAO() {
		return warrantyDAO;
	}

	public void setWarrantyDAO(IWarrantyContractDAO warrantyDAO) {
		this.warrantyDAO = warrantyDAO;
	}

}
