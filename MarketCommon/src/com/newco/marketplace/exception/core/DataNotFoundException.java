package com.newco.marketplace.exception.core;

import com.newco.marketplace.exception.DataServiceException;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.6 $ $Author: glacy $ $Date: 2008/04/26 00:51:54 $
 */

/*
 * Maintenance History
 * $Log: DataNotFoundException.java,v $
 * Revision 1.6  2008/04/26 00:51:54  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.3.12.1  2008/04/01 22:01:30  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.4  2008/03/27 18:57:34  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.3.16.1  2008/03/25 20:23:09  mhaye05
 * code cleanup
 *
 * Revision 1.3  2008/02/14 23:44:16  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.2.12.1  2008/02/08 02:33:26  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.2  2007/12/10 19:22:01  mhaye05
 * added javadoc
 *
 */
public class DataNotFoundException extends DataServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String message, Exception cause) {
		super(message, cause);
	}

	public DataNotFoundException(String message) {
		super(message);
	}

}
