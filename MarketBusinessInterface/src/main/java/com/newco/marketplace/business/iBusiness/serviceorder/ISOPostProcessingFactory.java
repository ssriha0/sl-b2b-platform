package com.newco.marketplace.business.iBusiness.serviceorder;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History
 * $Log: ISOPostProcessingFactory.java,v $
 * Revision 1.3  2008/04/26 00:40:06  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.1.22.1  2008/04/23 11:41:13  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.2  2008/04/23 05:16:53  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.1  2008/01/08 18:27:50  mhaye05
 * Initial Check In
 *
 */
public interface ISOPostProcessingFactory {

	/**
	 * Returns a class that handles the Service Order Post Processing 
	 * when a service order is closed, for the buyer provided.  
	 * If one is not available then NULL is returned.
	 * @param buyerId
	 * @return
	 */
	public ISOClosePostProcess getSOCloseProcess(Integer buyerId);
	
	/**
	 * Returns a class that handles the Service Order Post Processing 
	 * when a request for rescheduling is requested, for the buyer provided.  
	 * If one is not available then NULL is returned.
	 * @param buyerId
	 * @return
	 */
	public ISOReschedulePostProcess getSORescheduleProcess(Integer buyerId);
	
	public ISOCancelPostProcess getSOCancelProcess(Integer buyerId, String className);
}
