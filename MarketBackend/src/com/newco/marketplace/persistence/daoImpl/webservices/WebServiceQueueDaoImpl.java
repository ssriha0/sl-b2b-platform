package com.newco.marketplace.persistence.daoImpl.webservices;

import java.util.List;

import com.newco.marketplace.dto.vo.webservices.WSPayloadVO;
import com.newco.marketplace.persistence.iDao.webservices.WebServiceQueueDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class WebServiceQueueDaoImpl extends ABaseImplDao implements WebServiceQueueDao {

	public List<WSPayloadVO> getNext10Payloads() {
		return queryForList("webserviceQueue.query_next10", new WSPayloadVO());
	}

	public WSPayloadVO getPayload(WSPayloadVO vo) {
		return (WSPayloadVO) queryForObject("webserviceQueue.query_queue_by_id", vo);
	}
	
	public void deletePayload(WSPayloadVO vo) {
		delete("webserviceQueue.delete_by_queueID", vo);
	}

	public void insertPayload(WSPayloadVO vo) {
		insert("webserviceQueue.insert", vo);
	}

}
