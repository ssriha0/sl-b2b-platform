package com.newco.marketplace.business.businessImpl.serviceorder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.serviceorder.ISOCancelPostProcess;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOClosePostProcess;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOPostProcessingFactory;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOReschedulePostProcess;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.SOPostProcessDao;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.7 $ $Author: glacy $ $Date: 2008/04/26 00:40:24 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class SOPostProcessingFactoryImpl implements ISOPostProcessingFactory{

	private static final Logger logger = Logger.getLogger(SOPostProcessingFactoryImpl.class.getName());
	protected SOPostProcessDao postProcDAO;
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.ISOPostProcessingFactory#getSOCloseProcess(java.lang.Integer)
	 */
	public ISOClosePostProcess getSOCloseProcess(Integer buyerId) {
		
		String className = this.getClassName(buyerId, Constants.SO_ACTION.SERVICE_ORDER_CLOSED);
		
		ISOClosePostProcess toReturn = null;
		
		if (StringUtils.isNotBlank(className)) {
			try {
				toReturn = (ISOClosePostProcess)Class.forName(className).newInstance();
			} catch (InstantiationException e) {
				logger.error(e.getMessage(),e);
				toReturn = null;
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(),e);
				toReturn = null;
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(),e);
				toReturn = null;
			}
		}
		
		return toReturn;
	}
	
	public ISOCancelPostProcess getSOCancelProcess(Integer buyerId, String className) {
		
		String absoluteClassName = this.getClassName(buyerId, className);
		
		ISOCancelPostProcess toReturn = null;
		
		if (StringUtils.isNotBlank(absoluteClassName)) {
			try {
				toReturn = (ISOCancelPostProcess)Class.forName(absoluteClassName).newInstance();
			} catch (InstantiationException e) {
				logger.error(e.getMessage(),e);
				toReturn = null;
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(),e);
				toReturn = null;
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(),e);
				toReturn = null;
			}
		}
		
		return toReturn;
	}
	

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.ISOPostProcessingFactory#getSORescheduleProcess(java.lang.Integer)
	 */
	public ISOReschedulePostProcess getSORescheduleProcess(Integer buyerId) {
		
		String className = this.getClassName(buyerId, Constants.SO_ACTION.SERVICE_ORDER_RESCHEDULED);
		ISOReschedulePostProcess toReturn = null;
		
		if (StringUtils.isNotBlank(className)) {
			try {
				toReturn = (ISOReschedulePostProcess)Class.forName(className).newInstance();
			} catch (InstantiationException e) {
				logger.error(e.getMessage(),e);
				toReturn = null;
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(),e);
				toReturn = null;
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(),e);
				toReturn = null;
			}
		}
		
		return toReturn;
	}

	/**
	 * Helper method to retrieve the class name from the DAO
	 * @param buyerId
	 * @param actionId
	 * @return
	 */
	protected String getClassName (Integer buyerId, String actionId) {
		String className = null;
		
		try {
			className = postProcDAO.getConcreteClassName(buyerId, actionId);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		
		return className;
	}
	
	public SOPostProcessDao getPostProcDAO() {
		return postProcDAO;
	}

	public void setPostProcDAO(SOPostProcessDao postProcDAO) {
		this.postProcDAO = postProcDAO;
	}

}
/*
 * Maintenance History
 * $Log: SOPostProcessingFactoryImpl.java,v $
 * Revision 1.7  2008/04/26 00:40:24  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.4.12.1  2008/04/01 21:52:59  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.5  2008/03/27 18:58:35  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.4.16.1  2008/03/25 17:01:22  mhaye05
 * code cleanup
 *
 * Revision 1.4  2008/01/31 21:55:00  gjacks8
 * new outbound jar, again
 *
 * Revision 1.3  2008/01/24 22:08:43  gjacks8
 * cleaned testing code
 *
 * Revision 1.2  2008/01/24 15:47:25  gjacks8
 * changes made for ws queue
 *
 * Revision 1.1  2008/01/08 18:27:48  mhaye05
 * Initial Check In
 *
 */