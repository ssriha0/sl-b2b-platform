package com.newco.marketplace.persistence.iDao.webservices;

import java.util.List;

import com.newco.marketplace.dto.vo.webservices.WSPayloadVO;

public interface WebServiceQueueDao {

	WSPayloadVO getPayload(WSPayloadVO vo);
	
	List<WSPayloadVO> getNext10Payloads();
	
	void deletePayload(WSPayloadVO vo);
	
	void insertPayload(WSPayloadVO vo);
	
}
