package com.newco.marketplace.web.action.details;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.SoChangeDetailsDTO;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.16 $ $Author: akashya $ $Date: 2008/05/21 23:33:10 $
 */

/*
 * Maintenance History
 * $Log: SODetailsOrderHistoryAction.java,v $
 * Revision 1.16  2008/05/21 23:33:10  akashya
 * I21 Merged
 *
 * Revision 1.15.6.1  2008/05/16 12:16:28  pjoy0
 * Fixed as per Sears00051266 and Sears00051228: Time zone Issues
 *
 * Revision 1.15  2008/04/26 01:13:48  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.12.12.1  2008/04/01 22:04:01  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.13  2008/03/27 18:57:51  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.12.16.1  2008/03/25 20:08:48  mhaye05
 * code cleanup
 *
 * Revision 1.12  2008/02/14 23:44:56  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.11  2008/02/11 23:25:13  hravi
 * added time zone CST
 *
 * Revision 1.10  2008/02/05 21:34:30  usawant
 * Removed Modeldriven as it was not used, as part of Struts2 optimization
 *
 * Revision 1.9  2007/11/14 21:58:50  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */
public class SODetailsOrderHistoryAction extends SLDetailsBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2244714831562190481L;
	private static final Logger logger = Logger.getLogger(SODetailsOrderHistoryAction.class.getName());
	private ISODetailsDelegate detailsDelegate;

	public ISODetailsDelegate getDetailsDelegate() {
		return detailsDelegate;
	}

	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
		this.detailsDelegate = detailsDelegate;
	}


	public SODetailsOrderHistoryAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
	}

	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception {
		
		createCommonServiceOrderCriteria();
	}

	public String execute() throws Exception {
		
	
		// This line should be next to last on all the detail tabs
		populateDTO();
		
		
		return SUCCESS;
	}
	
	private void populateDTO()
	{
		//SL-19820
		//String soID = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soID = getParameter("soId");
		String resId = getParameter("resId");
		String date; 
		 ArrayList<SoChangeDetailsDTO> hdDtoList = null;
		try {
			hdDtoList = detailsDelegate.getSOLogs(soID);
			int size = hdDtoList.size();
			for(int i=0;i<size;i++){
				date = hdDtoList.get(i).getCreatedDate();
				date = date + " ("+OrderConstants.SERVICELIVE_ZONE+")";
				hdDtoList.get(i).setCreatedDate(date);
			}
			setAttribute("hdDtoList", hdDtoList);
			setAttribute("SERVICE_ORDER_ID", soID);
			setAttribute("routedResourceId", resId);
			String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
			getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
			setAttribute(Constants.SESSION.SOD_MSG, msg);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
	}
}
