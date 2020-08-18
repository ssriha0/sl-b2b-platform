package com.newco.marketplace.persistence.daoImpl.so;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.InOutVO;
import com.newco.marketplace.dto.vo.serviceorder.SOEventVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.ISOEventDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class SOEventDaoImpl extends ABaseImplDao implements ISOEventDao {

	public void insert(SOEventVO vo) {
		insert("soEvent.insert", vo);
	}

	public SOEventVO selectByID(SOEventVO vo) {
		return (SOEventVO) queryForObject("soEvent.query_event_by_id", vo);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.ISOEventDao#validateAndInsertArrival()
	 */
	public List<String> validateAndInsertArrival() throws DataServiceException {
		List list=null;
		int count=0;
/*		count=update("soEvent.step1", "1");
		count=count>0?update("soEvent.step1", "1"):0;
		count=count>0?update("soEvent.arrival.step2", null):0;
		count=count>0?update("soEvent.arrival.step3", null):0;
		count=count>0?update("soEvent.arrival.step4", null):0;
		list=count>0?queryForList("soEvent.step5", "a4"):null;
		count=count>0?delete("soEvent.step5", "a4"):0;*/
		return list;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.ISOEventDao#validateAndUpdateDeparture()
	 */
	public List<String> validateAndUpdateDeparture()
			throws DataServiceException {
		List list=null;
		int count=0;
/*		count=update("soEvent.step1", "1");
		count=count>0?update("soEvent.step1", "1"):0;
		count=count>0?update("soEvent.departure.step2", null):0;
		count=count>0?update("soEvent.departure.step3", null):0;
		count=count>0?update("soEvent.departure.step3.1", null):0;
		count=count>0?update("soEvent.departure.step4", null):0;
		list=count>0?queryForList("soEvent.step5", "d4"):null;
		count=count>0?delete("soEvent.step5", "d4"):0;*/
		return list;
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.ISOEventDao#select(com.newco.marketplace.dto.vo.serviceorder.SOEventVO)
	 */
	public List<SOEventVO> selectSOEventVO(InOutVO inOutVO)
			throws DataServiceException {
		return queryForList("soEvent.select",inOutVO);
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.ISOEventDao#updateSOEvent(com.newco.marketplace.dto.vo.serviceorder.InOutVO)
	 */
	public int updateSOEvent(InOutVO inOutVO) throws DataServiceException {
		return update("soEvent.update",inOutVO);
	}


}
