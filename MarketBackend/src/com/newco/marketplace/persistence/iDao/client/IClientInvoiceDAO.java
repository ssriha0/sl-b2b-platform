package com.newco.marketplace.persistence.iDao.client;

import com.newco.marketplace.dto.vo.client.ClientInvoiceOrderVO;

public interface IClientInvoiceDAO {
	
	public void saveClientInvoice(ClientInvoiceOrderVO clientInvoiceOrderVO);
	
	public Integer getClientIDForName(String name);

	public ClientInvoiceOrderVO getClientInvoiceForSoID(String soID);
	/**
	 * get the invoice record for that incident for the sku
	 * @param incidentId
	 * @param skuId
	 * @return
	 */
	public ClientInvoiceOrderVO getClientInvoiceForIncident(String incidentId, int skuId);

}
