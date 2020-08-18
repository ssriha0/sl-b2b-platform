package com.newco.marketplace.business.businessImpl.serviceorder;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.dto.vo.serviceorder.ABaseRequestDispatcher;
import com.newco.marketplace.persistence.iDao.webservices.WebServiceQueueDao;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.4 $ $Author: glacy $ $Date: 2008/04/26 00:40:24 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class HPSOPostProcessing {

	private final static Logger logger = Logger
	.getLogger(HPSOPostProcessing.class.getName());
	
	private WebServiceQueueDao payloadDao;
	
	protected void initPayloadDao() {
		Object beanFacility = null;
		try {
			beanFacility = MPSpringLoaderPlugIn.getCtx().getBean( ABaseRequestDispatcher.WSPAYLOAD_DAO );
			setPayloadDao((WebServiceQueueDao) beanFacility);
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}
	
	public WebServiceQueueDao getPayloadDao() {
		if (payloadDao == null) {
			initPayloadDao();
		}
		return payloadDao;
	}

	public void setPayloadDao(WebServiceQueueDao payloadDao) {
		this.payloadDao = payloadDao;
	}
}
/*
 * Maintenance History
 * $Log: HPSOPostProcessing.java,v $
 * Revision 1.4  2008/04/26 00:40:24  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.2.20.1  2008/04/23 11:42:20  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.3  2008/04/23 05:01:49  hravi
 * Reverting to build 247.
 *
 * Revision 1.2  2008/01/24 15:47:25  gjacks8
 * changes made for ws queue
 *
 * Revision 1.1  2008/01/08 20:19:25  mhaye05
 * Initial Check In
 *
 */