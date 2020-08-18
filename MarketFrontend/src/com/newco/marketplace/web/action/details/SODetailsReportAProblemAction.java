package com.newco.marketplace.web.action.details;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.SOProblemDTO;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;

/**
 * $Revision: 1.21 $ $Author: glacy $ $Date: 2008/04/26 01:13:47 $
 */

/*
 * Maintenance History
 * $Log: SODetailsReportAProblemAction.java,v $
 * Revision 1.21  2008/04/26 01:13:47  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.18.28.1  2008/04/01 22:04:00  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.19  2008/03/27 18:57:51  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.18.32.1  2008/03/25 20:08:48  mhaye05
 * code cleanup
 *
 * Revision 1.18  2007/12/13 23:53:24  mhaye05
 * replaced hard coded strings with constants
 *
 * Revision 1.17  2007/12/07 20:15:47  usawant
 * Increase spend limit and server side validation messages display changes
 *
 * Revision 1.16  2007/11/14 21:58:51  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */
public class SODetailsReportAProblemAction extends SLDetailsBaseAction implements Preparable, ServiceConstants, OrderConstants, ModelDriven<SOProblemDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7084244849848698964L;
	private static final Logger logger = Logger.getLogger(SODetailsReportAProblemAction.class.getName());
	static private String SOM_ACTION = "/serviceOrderMonitor.action";
	private ISODetailsDelegate detailsDelegate;
	private SOProblemDTO soPbDto = new SOProblemDTO();

	//Sl-19820
	String soId;
	
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public ISODetailsDelegate getDetailsDelegate() {
		return detailsDelegate;
	}

	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
		this.detailsDelegate = detailsDelegate;
	}


	public SODetailsReportAProblemAction(ISODetailsDelegate delegate) {
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
		ArrayList arrPbLookup = null;
		// sort the "Type of Problem" list alphabetically while reporting a problem - modified method call to invoke the new query.
	    arrPbLookup=(ArrayList)getDetailsDelegate().queryListSoProblemStatus();
	    getSession().setAttribute(Constants.SESSION.SOD_SO_STATUS_LIST, arrPbLookup);
	    //SL-19820
	    String soId = getParameter("soId");
	    setAttribute(OrderConstants.SO_ID, soId);
	    this.soId = soId;
	    String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
	    getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
	}

	public String execute() throws Exception {
		return SUCCESS;
	}

	public String submitProblem() throws Exception {
		logger.debug("----Start of SODetailsReportAProblemAction.submitProblem----");
		
		String strResponse = "";
		String strMessage = "";
		String strErrorMessage = "";
		String strErrorCode = "";
		String strSoId = "";
		int intSubStatusId = 0;
		String strPbType = "";
		String strComment = "";
		int intEntityId = 0;
		int intRoleType = 0;
		String strLoggedInUser = "";
		String strPbDesc = "";
		String defaultSelectedTab="";
		
		try{

			ServiceOrdersCriteria context = get_commonCriteria();
			soPbDto = (SOProblemDTO)getModel();
			logger.debug("soPbDTO : " + soPbDto.toString());
			//SL-19820
			strSoId = getAttribute(OrderConstants.SO_ID).toString();
			logger.debug("strSoId : " + strSoId);
			strPbType = soPbDto.getPbType();
			intSubStatusId = (!(strPbType.equalsIgnoreCase("")))?Integer.parseInt(strPbType):0;
			logger.debug("intSubStatusId :" + intSubStatusId);
			strComment = soPbDto.getPbComment();
			intEntityId = context.getSecurityContext().getCompanyId();
			logger.debug("intEntityId : " + intEntityId);
			intRoleType = context.getSecurityContext().getRoleId();
			logger.debug("intRoleType in action: " + intRoleType);
			strPbDesc =  soPbDto.getPbDesc();
			logger.debug("strPbDesc in action: " + strPbDesc);
			strLoggedInUser = context.getFName() + " " + context.getLName() ;
			logger.debug("strLoggedInUser in action: " + strLoggedInUser);
			soPbDto.validate("Problem");
			
			if (soPbDto.getErrors().size() > 0){
				strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
				defaultSelectedTab=SODetailsUtils.ID_REPORT_A_PROBLEM;
				strErrorMessage = soPbDto.getErrors().get(0).getMsg();
				strErrorCode = USER_ERROR_RC;
			}
			else
			{
				strMessage = getDetailsDelegate().reportProblem(strSoId, intSubStatusId, strComment, intEntityId, intRoleType, strPbDesc, strLoggedInUser);
				strErrorCode = strMessage.substring(0,2);
				strErrorMessage = strMessage.substring(2,strMessage.length());
				if (strErrorCode.equalsIgnoreCase(VALID_RC)){
					//Add SO Note
					addSONote(strSoId, strComment, strPbDesc);
					strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
					defaultSelectedTab=SODetailsUtils.ID_ISSUE_RESOLUTION;
					//SL-19820
					//setCurrentSOStatusCodeInSession(OrderConstants.PROBLEM_STATUS);
					setCurrentSOStatusCodeInRequest(OrderConstants.PROBLEM_STATUS);
					//messages.add("error",new ActionMessage(strErrorMessage));
				}
				else
				{	
					strResponse = ERROR;
					this.setReturnURL(SOM_ACTION);
					this.setErrorMessage(strErrorMessage);
				}
			}
			//SL-19820
			/*getSession().setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
			getSession().setAttribute(Constants.SESSION.SOD_MSG_CD,strErrorCode);*/
			getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+this.soId, strErrorMessage);
			setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
			getSession().setAttribute(Constants.SESSION.SOD_MSG_CD+"_"+this.soId,strErrorCode);
			setAttribute(Constants.SESSION.SOD_MSG_CD,strErrorCode);
			
			logger.debug("strResponse : " + strResponse);
			//Redisplay the tabs
		    logger.debug("defaultSelectedTab : " + defaultSelectedTab);
		    this.setDefaultTab( defaultSelectedTab );
  	} catch (BusinessServiceException e) {
		logger.info("Exception in reporting the problem on the service order: ", e);
		}
		logger.debug("----End of SODetailsReportAProblemAction.submitProblem----");

		return strResponse;
	}
	private void addSONote(String strSoId, String strComment, String strPbDesc) throws BusinessServiceException {
		logger.debug("----Start of SODetailsReportAProblemAction.addSONote----");
		ServiceOrderNoteDTO soNoteDTO = new ServiceOrderNoteDTO();
		soNoteDTO.setNoteTypeId(SOConstants.GENERAL_NOTE);
		soNoteDTO.setRadioSelection(SOConstants.PUBLIC_NOTE.toString());
		soNoteDTO.setSubject(strPbDesc);
		String strNoteMessage =strComment;
		if (null != strNoteMessage && strNoteMessage.length() > OrderConstants.SO_NOTE_MESSAGE_MAX_LENGTH) {
			strNoteMessage = strNoteMessage.substring(0, OrderConstants.SO_NOTE_MESSAGE_MAX_LENGTH);
		}
		soNoteDTO.setMessage(strNoteMessage);
		soNoteDTO.setSoId(strSoId);
		LoginCredentialVO lvRoles = get_commonCriteria().getSecurityContext().getRoles();
		Integer resourceId = get_commonCriteria().getVendBuyerResId();
		if(get_commonCriteria().getSecurityContext().isSlAdminInd()){
			resourceId = get_commonCriteria().getSecurityContext().getAdminResId(); 
			lvRoles.setRoleId(get_commonCriteria().getSecurityContext().getAdminRoleId());
		}
		// Resource Id is set as Entity Id
		soNoteDTO.setEntityId(resourceId);
		soNoteDTO.setEmailTobeSent(false);
		detailsDelegate.serviceOrderAddNote(lvRoles, soNoteDTO, resourceId);
		logger.debug("----End of SODetailsReportAProblemAction.addSONote----");
	}

	public void setModel(SOProblemDTO x){
		soPbDto = x;
	}


	public SOProblemDTO getModel() {
		return soPbDto;
	}
}
