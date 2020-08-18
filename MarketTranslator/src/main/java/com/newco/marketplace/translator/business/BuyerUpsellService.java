package com.newco.marketplace.translator.business;


import com.newco.marketplace.webservices.dao.ShcOrder;

public interface BuyerUpsellService 
{
	public void processAddOnMerchandise( ShcOrder shcOrder, int buyerID );
}
