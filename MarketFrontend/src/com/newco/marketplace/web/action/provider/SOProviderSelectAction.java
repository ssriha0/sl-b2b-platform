package com.newco.marketplace.web.action.provider;

import org.apache.log4j.Logger;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;

public class SOProviderSelectAction extends SLSimpleBaseAction {
	
	private Logger logger = Logger.getLogger(SOProviderSelectAction.class);
	
	public String execute() {
		String zip = getZip();
		String resourceID = getResourceID();
		if (resourceID != null) {
			try {
				int i = Integer.parseInt(resourceID);
			}
			catch (NumberFormatException nfe) {
				logger.error("Error parsing resourceID", nfe);
				return ERROR;
			}
		}
		if (zip != null && resourceID != null) {
			logger.debug("Putting zip:" + zip + " resourceID:" + resourceID);
			//place in session
			getSession().setAttribute(SSOW_ZIP, zip);
			getSession().setAttribute(SSOW_RESOURCE_ID, resourceID);
			//handle error
			return SUCCESS;
		}
		return ERROR;
	}
	
	private String getZip() {
		return getParameter(SSOW_ZIP);
	}
	
	private String getResourceID() {
		return getParameter(SSOW_RESOURCE_ID);
	}

}
