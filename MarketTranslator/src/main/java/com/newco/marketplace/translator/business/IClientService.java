package com.newco.marketplace.translator.business;

import com.newco.marketplace.translator.dao.Client;
import com.newco.marketplace.translator.dao.WarrantyContract;

public interface IClientService {
	
	public Client getClient(String clientName);
	
	public WarrantyContract getWarrantyContract(Client client, String contractNumber);

}
