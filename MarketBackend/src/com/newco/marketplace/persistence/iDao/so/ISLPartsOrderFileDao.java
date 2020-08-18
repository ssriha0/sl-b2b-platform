package com.newco.marketplace.persistence.iDao.so;

import java.util.List;

import com.newco.marketplace.dto.vo.so.order.SLPartsOrderFileVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface ISLPartsOrderFileDao {
	public List<SLPartsOrderFileVO> getPartsOrderRecords(Integer buyerId) throws DataServiceException;
}
