/**
 * 
 */
package com.newco.marketplace.web.delegatesImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerRegistrationBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.web.delegates.IBlackoutStatesDelegate;

/**
 * @author groma
 *
 */
public class BlackoutStatesDelegateImpl implements IBlackoutStatesDelegate {
	
	private static final Logger logger = Logger.getLogger(BlackoutStatesDelegateImpl.class.getName());
	IBuyerRegistrationBO buyerRegistrationBO;
	
	public BlackoutStatesDelegateImpl(IBuyerRegistrationBO buyerRegistrationBO) {
		this.buyerRegistrationBO = buyerRegistrationBO;
	
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IBlackoutStatesDelegate#getBlackoutStates()
	 */
	public List<String> getBlackoutStates() throws DelegateException {
		
		List<String> blackoutStates = new ArrayList<String>();
		
		try {
			blackoutStates = buyerRegistrationBO.getBlackoutStates();
		} catch(BusinessServiceException e) {
			logger.debug("Exception thrown retrieving Blackout States", e);
			e.printStackTrace();
		}
		
		return blackoutStates;
	}

	public IBuyerRegistrationBO getBuyerRegistrationBO() {
		return buyerRegistrationBO;
	}

	public void setBuyerRegistrationBO(IBuyerRegistrationBO buyerRegistrationBO) {
		this.buyerRegistrationBO = buyerRegistrationBO;
	}

}
