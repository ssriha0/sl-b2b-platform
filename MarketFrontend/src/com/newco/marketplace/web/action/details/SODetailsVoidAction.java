package com.newco.marketplace.web.action.details;

import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.SOCancelDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;

/**
 * $Revision: 1.10 $ $Author: glacy $ $Date: 2008/04/26 01:13:47 $
 */

/*
 * Maintenance History
 * $Log: SODetailsVoidAction.java,v $
 * Revision 1.10  2008/04/26 01:13:47  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.7.28.1  2008/04/01 22:04:00  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.8  2008/03/27 18:57:51  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.7.32.1  2008/03/25 20:08:48  mhaye05
 * code cleanup
 *
 * Revision 1.7  2007/12/13 23:53:22  mhaye05
 * replaced hard coded strings with constants
 *
 * Revision 1.6  2007/12/03 17:35:02  iullah2
 * fix for merge build - delete package com.newco.marketplace.business.Utils and organize imports on src
 *
 * Revision 1.5  2007/11/14 21:58:50  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */
public class SODetailsVoidAction extends SLDetailsBaseAction implements Preparable, ServiceConstants, OrderConstants, ModelDriven<SOCancelDTO> {

    private static final Logger logger = Logger
            .getLogger(SODetailsVoidAction.class);

    private static final long serialVersionUID = 1L;
    static private String SOM_ACTION = "/serviceOrderMonitor.action";
    private SOCancelDTO soCancelDto = new SOCancelDTO();
    private ISODetailsDelegate detailsDelegate;
	
	public ISODetailsDelegate getDetailsDelegate() {
		return detailsDelegate;
	}

	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
		this.detailsDelegate = detailsDelegate;
	}


	public SODetailsVoidAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
	}

    public String execute() throws Exception
    {
      logger.debug("----Start of SODetailsVoidAction.execute----");	
      ServiceOrdersCriteria context = get_commonCriteria();
      String strErrorCode = "";
	  String strErrorMessage = "";
	  String strResponse = "";
	  String soId = "";
	  ProcessResponse prResp = null;
	  SOCancelDTO soCancelDto= new SOCancelDTO();
	  try{
	  soCancelDto.setBuyerId(context.getCompanyId());
	  soId = getSession().getAttribute(OrderConstants.SO_ID).toString();
	  	
	  soCancelDto.setSoId(soId);
	  logger.debug("soCancelDto : " + soCancelDto.toString());
	  prResp = getDetailsDelegate().serviceOrderVoid(soCancelDto);
	  strErrorCode = prResp.getCode();
	  strErrorMessage = prResp.getMessages().get(0);
	  if (strErrorCode.equalsIgnoreCase(SYSTEM_ERROR_RC))
	  {	
		//Go to common error page in case of business logic failure error or fatal error
		strResponse = ERROR; 
		this.setReturnURL(SOM_ACTION);
		this.setErrorMessage(strErrorMessage);
	}else 
	{
		//if success return to SOD
		strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
		setCurrentSOStatusCodeInSession(OrderConstants.VOIDED_STATUS);
	}
	logger.debug("strErrorMessage : " + strErrorMessage);	
	getSession().setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
	logger.debug("strResponse : " + strResponse);
	} catch (BusinessServiceException e) {
		logger.info("Exception in cancelling the SO: ", e);
	}
    logger.debug("----End of SODetailsVoidAction.execute----");
	return strResponse;
    }

    public void prepare() throws Exception {
    	createCommonServiceOrderCriteria();
    }

	public void setModel(SOCancelDTO x){
		soCancelDto = x;
	}


	public SOCancelDTO getModel() {
		return soCancelDto;
	}

}
