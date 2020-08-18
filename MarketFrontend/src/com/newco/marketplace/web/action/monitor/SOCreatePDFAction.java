package com.newco.marketplace.web.action.monitor;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;

import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.15 $ $Author: rgurra0 $ $Date: 2008/05/30 19:02:48 $
 */

/*
 * Maintenance History
 * $Log: SOCreatePDFAction.java,v $
 * Revision 1.15  2008/05/30 19:02:48  rgurra0
 * fix security check for view SO as PDF
 *
 * Revision 1.14  2008/05/28 18:26:16  glacy
 * Have comment
 *
 * Revision 1.13  2008/04/26 01:13:45  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.11.4.1  2008/04/23 11:41:31  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.12  2008/04/23 05:19:33  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.11  2008/02/27 22:27:41  mhaye05
 * cleaned up imports and logger
 *
 * Revision 1.10  2007/12/17 23:00:59  slazar2
 * IE Fix
 *
 * Revision 1.9  2007/12/06 21:18:31  rambewa
 * *** empty log message ***
 *
 * Revision 1.8  2007/12/06 20:31:35  rambewa
 * *** empty log message ***
 *
 * Revision 1.7  2007/11/14 21:58:52  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */
public class SOCreatePDFAction extends SLBaseAction implements Preparable

{

	private static final long serialVersionUID = 445901639992757944L;
	private static final Logger logger = Logger.getLogger("SOCreatePDFAction");	

	private ISOMonitorDelegate serviceOrderDelegate;

	public SOCreatePDFAction(ISOMonitorDelegate delegate) {
		this.serviceOrderDelegate = delegate;
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

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String soId = null;

		if(getParameter("soID") != null)
		{
			soId = getParameter("soID");
		}
		//SL-19820
		/*else
			soId= (String) getSession().getAttribute(
					OrderConstants.SO_ID);
			
		/*
		 * We need to look at the vendor_id , and buyer_id for this service order
		 * if admin than this is ok
		 */
		boolean isAssociated = false;
		
		if( _commonCriteria.getRoleId() == OrderConstants.NEWCO_ADMIN_ROLEID){
			isAssociated = true;
		}else{
			isAssociated = serviceOrderDelegate.isAssociatedToViewSOAsPDF(soId, _commonCriteria.getRoleId(), _commonCriteria.getCompanyId());		
		}
				
		
		ServletOutputStream out = getResponse().getOutputStream();
		
		if(isAssociated == true){
			baos = serviceOrderDelegate.getPDF(soId);
			int size = 0;
			if (baos != null) {
				System.out.print("this is the file " + baos.size());
				size = baos.size();
	
			getResponse().setContentType("application/pdf");
			getResponse().setContentLength(size);
			getResponse().setHeader("Expires", "0");
			getResponse().setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
			getResponse().setHeader("Pragma", "public");
			baos.writeTo(out);
			}
			else{
				out.println("<html><body><h3>Service Order cannot be displayed at this moment</h3></body><html>");
			}
		}else{
			logger.error("User is not authorized to view requested SO UserId -->" + _commonCriteria.getCompanyId() + "  Role Type -->"
					          + _commonCriteria.getRoleType() + "  trying to view SO Id -->"+  soId );
			out.println("<html><body><h3>You are not authorized to view the requested Service Order</h3></body><html>");
			
		}
			
		out.flush();

		return SUCCESS;
	}

	public ISOMonitorDelegate getServiceOrderDelegate() {
		return serviceOrderDelegate;
	}

	public void setServiceOrderDelegate(ISOMonitorDelegate serviceOrderDelegate) {
		this.serviceOrderDelegate = serviceOrderDelegate;
	}

}
