package com.newco.marketplace.web.action.details;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.RescheduleDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;

/**
 * $Revision: 1.11 $ $Author: glacy $ $Date: 2008/04/26 01:13:46 $
 */

/*
 * Maintenance History 
 * $Log: SODetailsRequestRescheduleAction.java,v $
 * Revision 1.11  2008/04/26 01:13:46  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.9.28.1  2008/04/23 11:41:34  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.10  2008/04/23 05:19:31  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.9  2007/12/13 23:53:22  mhaye05
 * replaced hard coded strings with constants
 *
 * Revision 1.8  2007/12/12 00:49:34  akashya *** empty log message ***
 * 
 * Revision 1.7 2007/12/06 23:12:24 mhaye05 refactored requestReschedule()
 * 
 * Revision 1.6 2007/11/20 23:17:01 zizrale serial versino uid change
 * 
 * Revision 1.5 2007/11/15 23:07:37 zizrale cleanup, using proc response, more
 * validation
 * 
 * Revision 1.4 2007/11/14 21:58:50 mhaye05 changed reference to SOW_SO_ID or
 * THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 * 
 */
public class SODetailsRequestRescheduleAction extends SLDetailsBaseAction
		implements Preparable, ModelDriven {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2078166121944367019L;
	private static final Logger logger = Logger
			.getLogger("SODetailsRequestRescheduleAction");
	private ISODetailsDelegate soDetailsManager;
	private RescheduleDTO resched = new RescheduleDTO();

	public SODetailsRequestRescheduleAction(ISODetailsDelegate delegate) {
		this.soDetailsManager = delegate;
	}
	
	//SL-19820
	String soId;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String requestReschedule() {
		logger
				.info("Entering the SODetailsRequestRescheduleAction.requestReschedule()");
		Long start = System.currentTimeMillis();
		String result = null;

		//SL-19820
		//String soID = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String soID = getParameter("soId");
		this.soId = soID;
        setAttribute(SO_ID, soId);
		int roleId = get_commonCriteria().getRoleId().intValue();
		Integer companyId = get_commonCriteria().getCompanyId();

		boolean isError = false;

		if (soID != null) {

			RescheduleDTO reschedule = (RescheduleDTO) getModel();				
			String newStartDate = reschedule.getNewStartDate();
			String newEndDate = reschedule.getNewEndDate();
			
			
			String comments= reschedule.getRescheduleComments();

			Timestamp newStartDateTS = null;
			Timestamp newEndDateTS = null;
			if (StringUtils.isNotEmpty(newStartDate)) {
				newStartDateTS = Timestamp.valueOf(newStartDate + " 00:00:00");
			} else {
				result = RESCHEDULE_START_DATE_REQUIRED;
				isError = true;
			}
			if (StringUtils.isNotEmpty(newEndDate)) {
				newEndDateTS = Timestamp.valueOf(newEndDate + " 00:00:00");
			}

			if (reschedule.getRangeOfDates().equalsIgnoreCase("1")) { // 1 =
																		// ranged
				if (StringUtils.isEmpty(newEndDate)) {
					result = RESCHEDULE_END_DATE_REQUIRED;
					isError = true;
				}
			} else {
				// check start date in future
				if (newStartDateTS == null) {
					result = RESCHEDULE_START_DATE_REQUIRED;
					isError = true;
				}else if (StringUtils.isBlank(comments)){
					result = RESCHEDULE_COMMENTS_REQUIRED;
					isError = true;
				}				
				reschedule.setNewEndDate(StringUtils.EMPTY);
				reschedule.setNewEndTime(StringUtils.EMPTY);
			}
			if(StringUtils.isNotEmpty(newStartDate)
					&& StringUtils.isNotEmpty(newEndDate)&& StringUtils.isBlank(comments) ){
				result =RESCHEDULE_COMMENTS_REQUIRED;
				isError = true;
			}
			if (!isError) {
			try {
				Long start1 = System.currentTimeMillis();
				ServiceOrderDTO serviceOrder = soDetailsManager.getServiceOrder(soID, roleId, companyId);
				Long end1 = System.currentTimeMillis();
				logger.info("Time taken for soDetailsManager.getServiceOrder"+(end1-start1));
				Long start2 = System.currentTimeMillis();
				ProcessResponse procRespValidate = reschedule.validate(serviceOrder,reschedule.getRangeOfDates());
				Long end2 = System.currentTimeMillis();
				logger.info("Time taken for reschedule.validate"+(end2-start2));
				if (!procRespValidate.getCode().equals(ServiceConstants.VALID_RC)) {
					result = procRespValidate.getMessages().get(0);
					isError=true;
				}
			
			} catch (DataServiceException e) {
				logger.error("Error in rescheduling action");
				result = "Error in Rescheduling";
				isError=true;
			}
			}
			
			if (!isError) {
				reschedule.setSoId(soID);
				reschedule.setRequestorRole(roleId);
				reschedule.setCompanyId(companyId);
				Long start3 = System.currentTimeMillis();
				ProcessResponse procResp = soDetailsManager
						.requestRescheduleSO(reschedule);
				Long end3 = System.currentTimeMillis();
				logger.info("Time taken for soDetailsManager.requestRescheduleSO"+(end3-start3));
				if (!procResp.getCode().equals(ServiceConstants.VALID_RC)) {
					//SL-19820
					/*getSession().setAttribute(Constants.SESSION.SOD_RESCHEDULE_MSG,procResp.getMessages().get(0));
					getSession().setAttribute(OrderConstants.RESCHEDULE_DTO, reschedule);*/
					getSession().setAttribute(Constants.SESSION.SOD_RESCHEDULE_MSG+"_"+soID,procResp.getMessages().get(0));
					getSession().setAttribute(OrderConstants.RESCHEDULE_DTO+"_"+soID, reschedule);
					Long end = System.currentTimeMillis();
					logger.info("Total Time taken to reschedule in SODetailsRequestRescheduleAction"+(end-start));
					return SUCCESS;
				} else {
					//SL-19820
					//getSession().setAttribute(Constants.SESSION.SOD_MSG, procResp.getMessages().get(0));
					getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soID, procResp.getMessages().get(0));
					Long end = System.currentTimeMillis();
					logger.info("Total Time taken to reschedule in SODetailsRequestRescheduleAction"+(end-start));
					return SUCCESS;
				}
			}else{
				//SL-19820
				/*getSession().setAttribute(Constants.SESSION.SOD_RESCHEDULE_MSG,	result);
				getSession().setAttribute(OrderConstants.RESCHEDULE_DTO, reschedule);*/
				getSession().setAttribute(Constants.SESSION.SOD_RESCHEDULE_MSG+"_"+soID, result);
				getSession().setAttribute(OrderConstants.RESCHEDULE_DTO+"_"+soID, reschedule);
				Long end = System.currentTimeMillis();
				logger.info("Total Time taken to reschedule in SODetailsRequestRescheduleAction"+(end-start));
				return SUCCESS;
			}
		}

		this.setReturnURL("/serviceOrderMonitor.action");
		this.setErrorMessage(result);
		Long end = System.currentTimeMillis();
		logger.info("Total Time taken to reschedule in SODetailsRequestRescheduleAction"+(end-start));
		return ERROR;
	}

	/**
	 * Method to validate the reschedule request
	 * @return
	 */
	public String validateRequestReschedule() {
		logger
				.info("Entering the SODetailsRequestRescheduleAction.requestReschedule()");
				
        String result = null;

        //SL-19820
  		//String soID = (String) getSession().getAttribute(OrderConstants.SO_ID);
        String soID = (String) getParameter("soId");
        this.soId = soID;
        setAttribute(SO_ID, soId);
  		int roleId = get_commonCriteria().getRoleId().intValue();
  		Integer companyId = get_commonCriteria().getCompanyId();

  		boolean isError = false;

  		if (soID != null) {

  			RescheduleDTO reschedule = new RescheduleDTO();				
			String newStartDate = getParameter("newStartDate");
  			String newEndDate = getParameter("newEndDate");
  			String rangeOfDates=getParameter("rangeOfDates");
  			String comments= getParameter("rescheduleComments");
  			String reasonCode=getParameter("reasonCode");
  			reschedule.setNewStartDate(newStartDate);
  			reschedule.setNewEndDate(newEndDate);
  			reschedule.setRangeOfDates(rangeOfDates);
  			reschedule.setNewStartTime(getParameter("newStartTime"));
  			reschedule.setNewEndTime(getParameter("newEndTime"));
  			Timestamp newStartDateTS = null;
  			Timestamp newEndDateTS = null;
  			if (StringUtils.isNotEmpty(newStartDate)) {
  				newStartDateTS = Timestamp.valueOf(newStartDate + " 00:00:00");
  			} else {
  				result = RESCHEDULE_START_DATE_REQUIRED;
  				isError = true;
  			}
  			if (!isError && StringUtils.isNotEmpty(newEndDate)) {
  				newEndDateTS = Timestamp.valueOf(newEndDate + " 00:00:00");
  			}
  			//If 1 then a date range is selected
  			if (getParameter("rangeOfDates").equalsIgnoreCase("1")) {	 // 1 =
  																		// ranged
  				if (!isError && StringUtils.isEmpty(newEndDate)) {
  					result = RESCHEDULE_END_DATE_REQUIRED;
  					isError = true;
  				}
  			} else {
  				// check start date in future
  				if (newStartDateTS == null) {
  					result = RESCHEDULE_START_DATE_REQUIRED;
  					isError = true;
  				}else if (!isError && StringUtils.isBlank(comments)){
  					result = RESCHEDULE_COMMENTS_REQUIRED;
  					isError = true;
  				}				
  				reschedule.setNewEndDate(StringUtils.EMPTY);
  				reschedule.setNewEndTime(StringUtils.EMPTY);
  			}
  			//Check if comments are entered
  			if(!isError && StringUtils.isNotEmpty(newStartDate)
  					&& StringUtils.isNotEmpty(newEndDate)&& StringUtils.isBlank(comments)) {
  				result = RESCHEDULE_COMMENTS_REQUIRED;
  				isError = true;
  			}
  			// SL-19240 removing the roleId check
  			else if ((!isError) && (StringUtils.isBlank(reasonCode)||reasonCode.equalsIgnoreCase("-1"))){
  					//Check if reschedule reason code is selected
  					result = RESCHEDULE_REASON_CODE_REQUIRED;
  					isError = true;
  			}
  			if (!isError) {
  			try {
  				ServiceOrderDTO serviceOrder = soDetailsManager.getServiceOrder(soID, roleId, companyId);			
  				ProcessResponse procRespValidate = reschedule.validate(serviceOrder,getParameter("rangeOfDates"));
  				if (!procRespValidate.getCode().equals(ServiceConstants.VALID_RC)) {
  					result = procRespValidate.getMessages().get(0);
  					isError=true;
  				}
  				
  			} catch (DataServiceException e) {
  				logger.error("Error in rescheduling action");
  				result = "Error in Rescheduling";
  				isError=true;
  			}
  			}
 			
  		}
 		
		try{
			if (isError) {
				setAttribute("errorText",result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ERROR;
	}

	public String cancelRequestReschedule() {
		logger
				.info("Entering the SODetailsRequestRescheduleAction.cancelRequestReschedule()");

		String result = null;

		//SL-19820
		//String soID = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String soID = getParameter("soId");
		this.soId = soID;
		setAttribute(SO_ID, soId);
		int roleId = get_commonCriteria().getRoleId().intValue();
		boolean isError = false;

		if (soID != null) {
			RescheduleDTO reschedule = (RescheduleDTO) getModel();

			if (!isError) {
				reschedule.setSoId(soID);
				reschedule.setRequestorRole(roleId);
				reschedule.setCompanyId(get_commonCriteria().getCompanyId());
				ProcessResponse success = soDetailsManager
						.cancelRescheduleRequest(reschedule);

				if (!success.getCode().equals(ServiceConstants.VALID_RC)) {
					this.setReturnURL("/serviceOrderMonitor.action");
					this.setErrorMessage(success.getMessages().get(0));
					return ERROR;
				} else {
					//SL-19820
					/*getSession().setAttribute(Constants.SESSION.SOD_MSG,
									"Reschedule request has been successfully canceled.");*/
					getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soID,
							"Reschedule request has been successfully canceled.");
					setAttribute(Constants.SESSION.SOD_MSG,	"Reschedule request has been successfully canceled.");
					return SUCCESS;
				}
			}
		}

		this.setReturnURL("/serviceOrderMonitor.action");
		this.setErrorMessage(result);
		return ERROR;
	}

	public String acceptReschedule() {
		logger
				.info("Entering the SODetailsRequestRescheduleAction.acceptReschedule()");
		return respondToReschedule(true);
	}

	public String declineReschedule() {
		logger
				.info("Entering the SODetailsRequestRescheduleAction.declineReschedule()");
		return respondToReschedule(false);
	}

	private String respondToReschedule(boolean accept) {
		String result = null;

		//SL-19820
		//String soID = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String soID = getParameter("soId");
		this.soId = soID;
		setAttribute(SO_ID, soId);
		int roleId = get_commonCriteria().getRoleId().intValue();
		boolean isError = false;

		if (soID != null) {
			RescheduleDTO reschedule = (RescheduleDTO) getModel();

			if (!isError) {
				reschedule.setSoId(soID);
				reschedule.setRequestorRole(roleId);
				reschedule.setRescheduleAccepted(accept);
				reschedule.setCompanyId(get_commonCriteria().getCompanyId());

				ProcessResponse success = soDetailsManager
						.respondToRescheduleRequest(reschedule);

				if (!success.getCode().equals(ServiceConstants.VALID_RC)) {
					this.setReturnURL("/serviceOrderMonitor.action");
					this.setErrorMessage(success.getMessages().get(0));
					return ERROR;
				} else {
					//SL-19820
					/*getSession().setAttribute(Constants.SESSION.SOD_MSG,
									"You have successfully replied to the reschedule request.");*/
					getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soID,
								"You have successfully replied to the reschedule request.");
					setAttribute(Constants.SESSION.SOD_MSG,	"You have successfully replied to the reschedule request.");
					return SUCCESS;
				}
			}
		}

		this.setReturnURL("/serviceOrderMonitor.action");
		this.setErrorMessage(result);
		return ERROR;
	}

	public void prepare() throws Exception {
		logger.info("Entering Prepare of SODetailsRequestRescheduleAction");
		createCommonServiceOrderCriteria();

	}

	public Object getModel() {
		return resched;
	}

	public void setModel(Object x) {
		resched = (RescheduleDTO) x;
	}

}