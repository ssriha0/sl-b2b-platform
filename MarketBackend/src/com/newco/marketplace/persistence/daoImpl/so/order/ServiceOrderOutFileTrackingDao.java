/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.so.order;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderOutFileTrackingVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderOutFileTrackingDao;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author hoza
 *
 */
public class ServiceOrderOutFileTrackingDao extends ABaseImplDao implements
		IServiceOrderOutFileTrackingDao {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.IServiceOrderOutFileTrackingDao#getSOOutFileTrackingInfo(java.lang.String)
	 */

	public ServiceOrderOutFileTrackingVO getSOOutFileTrackingInfo(String soId)
			throws DataServiceException {
		ServiceOrderOutFileTrackingVO request = new ServiceOrderOutFileTrackingVO();
		request.setSoId(soId);
		//this might throw Null Pointer catch it dude
		List<ServiceOrderOutFileTrackingVO> list = (List<ServiceOrderOutFileTrackingVO>)queryForList("so.getSoOutFileTracking.query", request);
		if(list != null && list.size() > 0 ) {
			//get the first one 
			return (ServiceOrderOutFileTrackingVO)list.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.IServiceOrderOutFileTrackingDao#insert(com.newco.marketplace.dto.vo.serviceorder.ServiceOrderOutFileTrackingVO)
	 */

	public Boolean insert(ServiceOrderOutFileTrackingVO trackingVO)
			throws DataServiceException {
		Boolean result = Boolean.FALSE;
		try{
			Object o = insert("so.insertSoOutFileTracking", trackingVO);
			if(o != null) { result = Boolean.TRUE; }
		}catch(Exception ex){
			logger.error("Error occured while inserting tracking VO ",ex);
			throw new DataServiceException("Error occured while inserting selected counter offer reasons list in ServiceOrderDaoImpl.insertReasonCdForCounterOffer()",ex);
		}
		return result;
	}
	
}
