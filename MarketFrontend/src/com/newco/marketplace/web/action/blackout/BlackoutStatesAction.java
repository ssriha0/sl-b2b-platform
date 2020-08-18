/**
 * 
 */
package com.newco.marketplace.web.action.blackout;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.delegates.IBlackoutStatesDelegate;

/**
 * @author groma
 *
 */
public class BlackoutStatesAction extends SLSimpleBaseAction {
	
	private List<String> blackoutStates;
	private IBlackoutStatesDelegate blackoutStatesDelegate;
	private static final Logger logger = Logger.getLogger(BlackoutStatesAction.class.getName());
	private static final long serialVersionUID = -1791530059244642916L;
	

	public BlackoutStatesAction(IBlackoutStatesDelegate blackoutStatesDelegate) {
		this.blackoutStatesDelegate = blackoutStatesDelegate;
	}
	
	public String execute() throws Exception {
		
		try {
			blackoutStates = blackoutStatesDelegate.getBlackoutStates();
		} catch(DelegateException ex) {
			logger.info("Exception occured while processing the request due to " + ex.getMessage());
			addActionError("Exception occured while processing the request due to " + ex.getMessage());
			throw ex;			
		}
		
		return SUCCESS;
	}

	public String getBlackoutStatesForJoinForm() throws Exception {
		
		try {
			blackoutStates = blackoutStatesDelegate.getBlackoutStates();
			getRequest().setAttribute("blackoutStates", blackoutStates);
		} catch (DelegateException ex) {
			logger.info("Exception occured while processing the request due to " + ex.getMessage());
			addActionError("Exception occured while processing the request due to " + ex.getMessage());
			throw ex;
		}
		return NONE;
	}
	
	public List<String> getBlackoutStates() {
		return blackoutStates;
	}

	public void setBlackoutStates(List<String> blackoutStates) {
		this.blackoutStates = blackoutStates;
	}
	
}
