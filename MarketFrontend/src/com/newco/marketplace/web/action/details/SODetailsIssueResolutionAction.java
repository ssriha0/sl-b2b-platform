package com.newco.marketplace.web.action.details;

import java.sql.Timestamp;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.DelegateException;
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
 * $Revision: 1.23 $ $Author: glacy $ $Date: 2008/04/26 01:13:48 $
 */

/*
 * Maintenance History
 * $Log: SODetailsIssueResolutionAction.java,v $
 * Revision 1.23  2008/04/26 01:13:48  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.20.24.1  2008/04/01 22:04:00  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.21  2008/03/27 18:57:51  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.20.28.1  2008/03/25 18:29:52  mhaye05
 * code cleanup
 *
 * Revision 1.20  2008/01/04 19:20:43  usawant
 * Fix for defect Sears00044619 : Added a function Split which will be used to split the comment content when there is no space in the content and does the wrapping of content.
 *
 * Revision 1.19  2007/12/13 23:53:23  mhaye05
 * replaced hard coded strings with constants
 *
 * Revision 1.18  2007/12/07 20:15:47  usawant
 * Increase spend limit and server side validation messages display changes
 *
 * Revision 1.17  2007/11/14 21:58:51  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */
public class SODetailsIssueResolutionAction extends SLDetailsBaseAction implements Preparable,ServiceConstants, OrderConstants, ModelDriven {

	private static final long serialVersionUID = 10002;// arbitrary number to get rid
	static private String SOM_ACTION = "/serviceOrderMonitor.action";
	private static final Logger logger = Logger
    .getLogger("SODetailsIssueResolutionAction");

	public SODetailsIssueResolutionAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
	}
	
	private SOProblemDTO soPbDto = new SOProblemDTO();
	
	//SL-19820
	String soId;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception {
		logger.debug("----Start of SODetailsIssueResolutionAction.prepare----");
		//SL-19820
		//String soId = getSession().getAttribute(OrderConstants.SO_ID).toString();
		String soId = getParameter("soId");
		setAttribute(SO_ID, soId);
		this.soId = soId;
		
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		
		String msgCd = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG_CD+"_"+soId);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG_CD+"_"+soId);
		setAttribute(Constants.SESSION.SOD_MSG_CD, msgCd);
		
		String strPbComment = "";
		logger.debug("soId : " + soId);
		createCommonServiceOrderCriteria();
		ProblemResolutionSoVO prbResolutionVO = null;
		//SL-19820
		if (getSession().getAttribute(Constants.SESSION.SOD_PRB_RES_SO+"_"+this.soId) == null){
			prbResolutionVO = (ProblemResolutionSoVO)getDetailsDelegate().getProblemDesc(soId);
			if (prbResolutionVO != null){
				logger.debug("prbResolutionVO value: " + prbResolutionVO.toString());
				strPbComment = prbResolutionVO.getPbComment();
				System.out.println("strPbComment : " + strPbComment);
				//Need this only when there are no spaces in the comment and display overflows
				if (strPbComment.indexOf(" ") < 0){
					strPbComment = split(strPbComment, 70);
					prbResolutionVO.setPbComment(strPbComment);
					System.out.println("strPbComment after splitting: " + strPbComment);
				}
			}	
			else
				logger.debug("prbResolutionVO value is null");
			logger.debug("----End of SODetailsIssueResolutionAction.prepare----");
			//SL-19820
			//getSession().setAttribute(Constants.SESSION.SOD_PRB_RES_SO, prbResolutionVO);
			getSession().setAttribute(Constants.SESSION.SOD_PRB_RES_SO+"_"+this.soId, prbResolutionVO);
			setAttribute(Constants.SESSION.SOD_PRB_RES_SO, prbResolutionVO);
		}
	}
	
	public static String split(String longName, int length) {
        if (longName == null) {
            return "<Unnamed>";
        }

        if (longName.length() <= length) {
            return longName;
        }

        StringBuffer results = new StringBuffer();

        // The third argument is true, which means return the delimiters
        // as part of the tokens.
        StringTokenizer tokenizer = new StringTokenizer(longName,
        		System.getProperty("line.separator"), true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            int mark = 0;

            while (mark < (token.length() - length)) {
                // We look for the space from the end of the first length
                // characters.  If we find one, then we use that
                // as the place to insert a newline.
                int lastSpaceIndex = token.substring(mark, mark + length)
                        .lastIndexOf(" ");

                if (lastSpaceIndex < 0) {
                    // No space found, just insert a new line after length
                    results.append(token.substring(mark, mark + length)
                            + System.getProperty("line.separator"));
                    mark += length;
                } else {
                    results.append(token.substring(mark, mark + lastSpaceIndex)
                            + System.getProperty("line.separator"));
                    mark += (lastSpaceIndex + 1);
                }
            }

            results.append(token.substring(mark));
        }

        return results.toString();
    }

	
	public String execute() throws Exception {
		return SUCCESS;
	}



	public String submitResolution() throws Exception {
		logger.debug("----Start of SODetailsIssueResolutionAction.submitResolution----");
		String strResponse = "";
		String strMessage = "";
		String strErrorCode = "";
		String strSoId = "";
		String strComment = "";
		int intEntityId = 0;
		int intRoleType = 0;
		String defaultSelectedTab="";
		String strErrorMessage = "";
		String strPbDesc = "";
		String strLoggedInUser = "";
		String strPbDetails = "";
		ServiceOrder serviceOrder = null;
		Timestamp completedDate = null;
		Integer wfStateId = null;
		
		ProblemResolutionSoVO prbResolutionVO = null;
		try{
			logger.debug("soPbDTO : " + soPbDto.toString());
			//SL-19820
			//strSoId =getSession().getAttribute(OrderConstants.SO_ID).toString();
			strSoId = getAttribute(OrderConstants.SO_ID).toString();
			logger.debug("strSoId : " + strSoId);
			strComment = soPbDto.getResComment();
			ServiceOrdersCriteria context = get_commonCriteria();
			intEntityId = context.getSecurityContext().getCompanyId();
			logger.debug("intEntityId : " + intEntityId);;
			intRoleType = context.getSecurityContext().getRoleId();
			logger.debug("intRoleType : " + intRoleType);
			strLoggedInUser = context.getFName() + " " + context.getLName();
		
			soPbDto.validate("Resolution");
			
			if (soPbDto.getErrors().size() > 0){
				strResponse = GOTO_COMMON_DETAILS_CONTROLLER;
				defaultSelectedTab=SODetailsUtils.ID_ISSUE_RESOLUTION;
				strErrorMessage = soPbDto.getErrors().get(0).getMsg();
			}else {
				//SL-19820
				//prbResolutionVO = (ProblemResolutionSoVO)getSession().getAttribute(Constants.SESSION.SOD_PRB_RES_SO);
				prbResolutionVO = (ProblemResolutionSoVO)getSession().getAttribute(Constants.SESSION.SOD_PRB_RES_SO+"_"+this.soId);
				if (prbResolutionVO != null){
					strPbDesc = prbResolutionVO.getSubStatusDesc();
					strPbDetails = prbResolutionVO.getPbComment();
				}
				strMessage = getDetailsDelegate().reportResolution(strSoId,0,strComment, intEntityId, intRoleType, strPbDesc, strPbDetails, strLoggedInUser);
				logger.debug("strMessage : " + strMessage);
				strErrorCode = strMessage.substring(0,2);

				if (strErrorCode.equalsIgnoreCase(VALID_RC)){
					// Add SO Note
					addSONote(strSoId, strComment);
					//Fetching the completed date for the SO
					serviceOrder = getDetailsDelegate().getServiceOrderStatusAndCompletdDate(strSoId);
					completedDate = serviceOrder.getCompletedDate();
					wfStateId = serviceOrder.getWfStateId();
					
					strResponse = GOTO_COMMON_DETAILS_CONTROLLER;;
					strErrorMessage = RESOLUTION_ADD_SUCCESS;
					
					if((wfStateId == ACTIVE_STATUS)&&(null!=completedDate)&&(intRoleType==PROVIDER_ROLEID)){
						defaultSelectedTab=SODetailsUtils.ID_COMPLETE_FOR_PAYMENT;
					}else{
						defaultSelectedTab=SODetailsUtils.ID_SUMMARY;
					}
					
					if (wfStateId == ACCEPTED_STATUS || wfStateId == ACTIVE_STATUS) {
						//SL-19820
						//setCurrentSOStatusCodeInSession(wfStateId);
						setCurrentSOStatusCodeInRequest(wfStateId);
					}
					else{
						strErrorMessage = OrderConstants.LAST_STATUS_NULL;
						this.setReturnURL(SOM_ACTION);
						this.setErrorMessage(strErrorMessage);
						}
					//SL-19820
					//getSession().removeAttribute(Constants.SESSION.SOD_PRB_RES_SO);
					getSession().removeAttribute(Constants.SESSION.SOD_PRB_RES_SO+"_"+this.soId);
				}
				else
				{
					strResponse = ERROR;
					strErrorMessage = strMessage.substring(2,strMessage.length());
					this.setReturnURL(SOM_ACTION);
					this.setErrorMessage(strErrorMessage);
				}
			}
			//SL-19820
			//getSession().setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
			getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+this.soId, strErrorMessage);
			setAttribute(Constants.SESSION.SOD_MSG, strErrorMessage);
			//Redisplay the tabs
		    logger.debug("defaultSelectedTab : " + defaultSelectedTab);
			this.setDefaultTab( defaultSelectedTab );

		} catch (BusinessServiceException e) {
			logger.info("Exception in issuing the resolution on the service order: ", e);
		}catch(DelegateException ex){
			logger.info("Exception in issuing the resolution on the service order: ", ex);
		}
		logger.debug("----End of SODetailsIssueResolutionAction.submitResolution----");
		return strResponse;
	}
	private void addSONote(String strSoId, String strComment) throws BusinessServiceException {
		logger.debug("----Start of SODetailsIssueResolutionAction.addSONote----");
		ServiceOrderNoteDTO soNoteDTO = new ServiceOrderNoteDTO();
		soNoteDTO.setNoteTypeId(SOConstants.GENERAL_NOTE);
		soNoteDTO.setRadioSelection(SOConstants.PUBLIC_NOTE.toString());
		soNoteDTO.setSubject(Constants.SESSION.SOD_ISSUE_RESOLUTION_NOTES_SUBJECT);
		String strNoteMessage = strComment;
		if (null != strNoteMessage && strNoteMessage.length() > OrderConstants.SO_NOTE_MESSAGE_MAX_LENGTH) {
			strNoteMessage = strNoteMessage.substring(0, OrderConstants.SO_NOTE_MESSAGE_MAX_LENGTH);
		}
		soNoteDTO.setMessage(strNoteMessage);
		soNoteDTO.setSoId(strSoId);
		LoginCredentialVO lvRoles = get_commonCriteria().getSecurityContext()
				.getRoles();
		Integer resourceId = get_commonCriteria().getVendBuyerResId();
		if (get_commonCriteria().getSecurityContext().isSlAdminInd()) {
			resourceId = get_commonCriteria().getSecurityContext()
					.getAdminResId();
			lvRoles.setRoleId(get_commonCriteria().getSecurityContext()
					.getAdminRoleId());
		}
		// Resource Id is set as Entity Id
		soNoteDTO.setEntityId(resourceId);
		detailsDelegate.serviceOrderAddNote(lvRoles, soNoteDTO, resourceId);
		logger.debug("----End of SODetailsIssueResolutionAction.addSONote----");
	}
	public ActionErrors validateResolution(SOProblemDTO soPbDto) throws Exception {
		logger.debug("----Start of SODetailsIssueResolutionAction.validateResolution----");
		ActionErrors errors = new ActionErrors();
		String strMessage = "";
		logger.debug("resComment : " + soPbDto.getResComment());
		if (soPbDto.getResComment().equalsIgnoreCase("")){
			strMessage = OrderConstants.ENTER_RESCOMMENT;
			logger.debug("strMessage in validateResolution: " + strMessage);
			errors.add("error",new ActionMessage(strMessage));
		}
		logger.debug("----End of SODetailsIssueResolutionAction.validateResolution----");
		return errors;
	}

	public void setModel(Object x){
		soPbDto = (SOProblemDTO) x;
	}


	public Object getModel() {
		return soPbDto;
	}

}
