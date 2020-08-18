package com.newco.marketplace.business.iBusiness.serviceorder;

import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History
 * $Log: ISOReschedulePostProcess.java,v $
 * Revision 1.5  2008/04/26 00:40:06  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.3.22.1  2008/04/23 11:41:13  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.4  2008/04/23 05:16:53  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.3  2008/01/08 21:18:35  mhaye05
 * updated return type
 *
 * Revision 1.2  2008/01/08 19:21:52  mhaye05
 * added buyer id to input parameters
 *
 * Revision 1.1  2008/01/08 18:27:50  mhaye05
 * Initial Check In
 *
 */
public interface ISOReschedulePostProcess {

	/**
	 * @param serviceOrderId
	 * @param buyerId
	 * @throws BusinessServiceException
	 */
	public void execute (String serviceOrderId, Integer buyerId) throws BusinessServiceException;
}
