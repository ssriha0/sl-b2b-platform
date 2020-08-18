/**
 * 
 */
package com.newco.marketplace.business.businessImpl.serviceorder;

import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderOutFileTrackingBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderOutFileTrackingVO;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderOutFileTrackingDao;
import com.sears.os.business.ABaseBO;

/**
 * @author hoza
 *
 */
public class ServiceOrderOutFileTrackingBO extends ABaseBO implements
		IServiceOrderOutFileTrackingBO {
	
	private IServiceOrderOutFileTrackingDao serviceOrderOutFileTrackingDAO;
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderOutFileTrackingBO#getSOOutFileTrackingInfo(java.lang.String)
	 */
	
	public ServiceOrderOutFileTrackingVO getSOOutFileTrackingInfo(String soId)
			throws Exception {
		return serviceOrderOutFileTrackingDAO.getSOOutFileTrackingInfo(soId);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderOutFileTrackingBO#insertTrackingRecord(com.newco.marketplace.dto.vo.serviceorder.ServiceOrderOutFileTrackingVO)
	 */
	
	public Boolean insertTrackingRecord(ServiceOrderOutFileTrackingVO trackingVO)
			throws Exception {
		return serviceOrderOutFileTrackingDAO.insert(trackingVO);
	}
	

	/**
	 * @return the serviceOrderOutFileTrackingDAO
	 */
	public IServiceOrderOutFileTrackingDao getServiceOrderOutFileTrackingDAO() {
		return serviceOrderOutFileTrackingDAO;
	}

	/**
	 * @param serviceOrderOutFileTrackingDAO the serviceOrderOutFileTrackingDAO to set
	 */
	public void setServiceOrderOutFileTrackingDAO(
			IServiceOrderOutFileTrackingDao serviceOrderOutFileTrackingDAO) {
		this.serviceOrderOutFileTrackingDAO = serviceOrderOutFileTrackingDAO;
	}

}
