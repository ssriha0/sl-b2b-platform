/**
 * 
 */
package com.newco.marketplace.business.iBusiness.ledger;

import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * @author schavda
 *
 */
public interface IGLProcessor {

	public boolean writeGLFeed(java.util.Date date1, java.util.Date date2) throws BusinessServiceException;
}
