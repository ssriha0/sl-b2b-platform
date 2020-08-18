package com.newco.marketplace.persistence.iDao.so.buyerPII;

import com.newco.marketplace.dto.vo.buyer.BuyerPIIVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface IBuyerPIIDao {

	public void saveBuyerPII(BuyerPIIVO buyerPII) throws DataServiceException;
}
