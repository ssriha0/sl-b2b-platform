package com.newco.marketplace.persistence.iDao.so;

import com.newco.marketplace.exception.core.DataServiceException;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.3 $ $Author: glacy $ $Date: 2008/04/26 00:40:31 $
 */

/*
 * Maintenance History
 * $Log: SOPostProcessDao.java,v $
 * Revision 1.3  2008/04/26 00:40:31  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.1.24.1  2008/04/23 11:42:21  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.2  2008/04/23 05:02:15  hravi
 * Reverting to build 247.
 *
 * Revision 1.1  2008/01/08 18:27:48  mhaye05
 * Initial Check In
 *
 */
public interface SOPostProcessDao {
	
	/**
	 * Returns the concrete class name that implements the desired business logic for the given
	 * buyer and action.  If one is not found NULL is returned.
	 * @param buyerId
	 * @param actionId
	 * @return
	 * @throws DataServiceException
	 */
	public String getConcreteClassName (Integer buyerId, String actionId) throws DataServiceException;
}
