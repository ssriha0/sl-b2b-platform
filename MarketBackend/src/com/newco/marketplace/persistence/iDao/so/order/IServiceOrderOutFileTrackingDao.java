/**
 * 
 */
package com.newco.marketplace.persistence.iDao.so.order;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderOutFileTrackingVO;
import com.newco.marketplace.exception.DataServiceException;

/**
 * @author hoza
 *
 */
public interface IServiceOrderOutFileTrackingDao {
	public  Boolean insert(ServiceOrderOutFileTrackingVO trackingVO) throws  DataServiceException;
	public ServiceOrderOutFileTrackingVO getSOOutFileTrackingInfo(String soId) throws DataServiceException;
}
