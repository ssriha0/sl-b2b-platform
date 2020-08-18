package com.newco.marketplace.web.action.details;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.web.constants.SOConstants;

/**
 * SODetailsSessionCleanup is responsible for removing any objects
 * put into the Session while the user is in the SOD
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.5 $ $Author: glacy $ $Date: 2008/05/02 21:23:21 $
 */


// This was created for Details but needs to be genericized for all entry points into major modules.
// Similar functionality class in SOWSessionFacility. Ideally, merge this with SOWSessionFacility.
// Comments by Doug/Carlos
public class SODetailsSessionCleanup {
	
	private static SODetailsSessionCleanup instance = new SODetailsSessionCleanup();
	
	private SODetailsSessionCleanup() {}
	
	public static SODetailsSessionCleanup getInstance () {
		if (null == instance) {
			instance = new SODetailsSessionCleanup();
		}
		return instance;
	}

	public void cleanUpSession() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		session.removeAttribute(Constants.SESSION.SOD_MSG);
		session.removeAttribute(Constants.SESSION.SOD_MSG_CD);
		session.removeAttribute(Constants.SESSION.SOD_SO_CLOSED_DTO);
		session.removeAttribute(Constants.SESSION.SOD_SO_COMPLETE_DTO);
		session.removeAttribute(Constants.SESSION.SOD_SHIP_CAR);
		session.removeAttribute(Constants.SESSION.SOD_INC_SO_DTO);
		session.removeAttribute(Constants.SESSION.SOD_ERR_LIST);
		session.removeAttribute(Constants.SESSION.SOD_PRB_RES_SO);
		session.removeAttribute(Constants.SESSION.SOD_NOTE_DTO);
		session.removeAttribute(Constants.SESSION.SOD_RSLT_BUY_TO_PROV);
		session.removeAttribute(Constants.SESSION.SOD_RSLT_PROV_TO_BUY);
		session.removeAttribute(Constants.SESSION.SOD_SO_STATUS_LIST);
		session.removeAttribute(Constants.SESSION.SOD_ROUTED_RES_ID);
		session.removeAttribute(SOConstants.REFETCH_SERVICE_ORDER);
		session.removeAttribute(SOConstants.THE_SERVICE_ORDER_STATUS_CODE);
		session.removeAttribute(SOConstants.DETAILS_ERROR_MSG);
		session.removeAttribute("spnBuilderFormInfo");
		  session.removeAttribute(Constants.SESSION.DOCS_IN_CURRENTVISIT_LIST);
		// need to clean this up since we are passing this info in the request
		// now - causing an issue when user clicks on SOM link in the nav
		// bar while being in SOD
		//session.removeAttribute("displayTab");
	}
}
