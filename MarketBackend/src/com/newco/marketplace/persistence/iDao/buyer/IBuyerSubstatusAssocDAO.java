package com.newco.marketplace.persistence.iDao.buyer;

import java.util.List;

import com.newco.marketplace.dto.vo.buyer.BuyerSubstatusAssocVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface IBuyerSubstatusAssocDAO {

	List<BuyerSubstatusAssocVO> getBuyerStatus(Integer buyerID, Integer statusId, Integer substatus, String buyerSubstatus) throws DataServiceException;

	public BuyerSubstatusAssocVO getBuyerSubstatusAssoc(Integer buyerSubStatusAssocId)throws DataServiceException;
}
