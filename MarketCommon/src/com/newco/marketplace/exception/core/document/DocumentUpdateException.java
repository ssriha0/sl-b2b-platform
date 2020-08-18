package com.newco.marketplace.exception.core.document;

import com.newco.marketplace.exception.core.DataServiceException;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.4 $ $Author: glacy $ $Date: 2008/04/26 00:51:56 $
 */

/*
 * Maintenance History
 * $Log: DocumentUpdateException.java,v $
 * Revision 1.4  2008/04/26 00:51:56  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.2.12.1  2008/04/23 11:42:04  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.3  2008/04/23 05:17:43  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.2  2008/02/14 23:44:15  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.1.16.1  2008/02/08 02:33:24  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.1  2007/11/30 18:49:27  mhaye05
 * Initial Check In
 *
 */
public class DocumentUpdateException extends DataServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DocumentUpdateException(String message, Exception cause) {
		super(message, cause);
	}

	public DocumentUpdateException(String message) {
		super(message);
	}

}
