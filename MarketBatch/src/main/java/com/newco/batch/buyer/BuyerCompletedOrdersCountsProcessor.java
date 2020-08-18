package com.newco.batch.buyer;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.exception.BusinessServiceException;

public class BuyerCompletedOrdersCountsProcessor {
	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

	private IBuyerBO buyerBO;
	
	public void execute() throws BusinessServiceException {
		buyerBO.updateBuyerCompletedOrdersCount();
	}
}
