package com.newco.marketplace.web.action.calendarPortal;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.persistence.daoImpl.externalcalendar.ExternalCalendarDAOImpl;
import com.newco.marketplace.vo.calendarPortal.ExternalCalendarDTO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ICalendarPortalDelegate;
import com.newco.marketplace.web.dto.d2cproviderportal.CalendarPortalResponseDto;
import com.opensymphony.xwork2.ActionContext;

import flexjson.JSONSerializer;

/**
 * 
 * This class is to handle page loading for Calendar Portal and to handle ajax
 * calls from Calendar Portal
 * 
 * @author rranja1
 * 
 */
public class CalendarPortalAction extends SLBaseAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CalendarPortalAction.class.getName());

	// json serializer to convert Object into json
	// call toJson method
	private final JSONSerializer jsonSerializer = new JSONSerializer().exclude("*.class");

	// Json response root element for ajax calls
	private String jsonString = "";

	// delegate instance
	private final ICalendarPortalDelegate calendarPortalDelegate;
	private ExternalCalendarDTO externalCalendarDTO;

	public CalendarPortalAction(ICalendarPortalDelegate calendarPortalDelegate) {
		this.calendarPortalDelegate = calendarPortalDelegate;
	}

	/**
	 * This method is called on page init to check logged in user info
	 * 
	 * @return
	 */
	public String initPage() {
		logger.info("start initPage of CalendarPortalAction");

		String returnAction = SUCCESS;

		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		SecurityContext securityContext = (SecurityContext) ServletActionContext.getRequest().getSession().getAttribute(SECURITY_KEY);

		boolean isSLAdmin = securityContext.isSlAdminInd();
		boolean isProviderAdmin = securityContext.isPrimaryInd();
		boolean isDispatchInd = securityContext.isDispatchInd();

		logger.info("Vendor id: " + vendorIdStr + " SLAdmin: " + isSLAdmin + " Provider Admin: " + isProviderAdmin + " Dispatcher Ind: "
				+ isDispatchInd);
		if (null != vendorIdStr && vendorIdStr.trim().length() > 0 && (isDispatchInd || isSLAdmin)) {
			returnAction = SUCCESS;
		} else {
			returnAction = ERROR;
			logger.warn("vendor id is null or not a dispatcher. redirecting to error page from CalendarPortalAction.");
		}

		logger.debug("returnAction: " + returnAction);

		logger.info("end initPage of CalendarPortalAction");
		return returnAction;
	}

	public String getVenderEvents() {
		logger.info("entering getVenderEvents of CalendarPortalAction");

		String returnString = SUCCESS;

		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		SecurityContext securityContext = (SecurityContext) ServletActionContext.getRequest().getSession().getAttribute(SECURITY_KEY);

		boolean isSLAdmin = securityContext.isSlAdminInd();
		boolean isProviderAdmin = securityContext.isPrimaryInd();
		boolean isDispatchInd = securityContext.isDispatchInd();

		logger.info("Vendor id: " + vendorIdStr + " SLAdmin: " + isSLAdmin + " Provider Admin: " + isProviderAdmin + " Dispatcher Ind: "
				+ isDispatchInd);
		if (null != vendorIdStr && vendorIdStr.trim().length() > 0 && (isDispatchInd || isSLAdmin)) {

			String json = null;
			try {
				json = IOUtils.toString(getRequest().getReader());

				@SuppressWarnings("deprecation")
				JSONObject obj = JSONObject.fromString(json);
				Date startDate = new Date((Long) obj.get("startDate"));
				Date endDate = new Date((Long) obj.get("endDate"));
				CalendarPortalResponseDto response = calendarPortalDelegate.getVendorEvents(vendorIdStr, startDate, endDate);

				if (null != response.getError()) {
					logger.error("problem in the response of calendarPortalDelegate: " + response.getError().getErrorMessage());
					jsonString = "Error!";
					returnString = ERROR;
				} else {
					response.setEditable(isDispatchInd);
					jsonString = toJSON(response);
				}
			} catch (IOException exception) {
				logger.error("problem while io operations getVenderEvents", exception);
				jsonString = "Error!";
				returnString = ERROR;
			} catch (Exception exception) {
				logger.error("exception in getVenderEvents", exception);
				jsonString = "Error!";
				returnString = ERROR;
			}
		} else {
			logger.warn("Vendor id is not present in session or not a dispatcher.");
			jsonString = "Error!";
			returnString = ERROR;
		}

		logger.info("exiting getVenderEvents of CalendarPortalAction");
		return returnString;
	}

	/**
	 * perform sl service order actions
	 * 
	 * @return
	 */
	public String acceptSO() {
		logger.info("entering acceptSO of CalendarPortalAction");

		String returnString = SUCCESS;

		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		SecurityContext securityContext = (SecurityContext) ServletActionContext.getRequest().getSession().getAttribute(SECURITY_KEY);

		boolean isSLAdmin = securityContext.isSlAdminInd();
		boolean isProviderAdmin = securityContext.isPrimaryInd();
		boolean isDispatchInd = securityContext.isDispatchInd();

		logger.info("Vendor id: " + vendorIdStr + " SLAdmin: " + isSLAdmin + " Provider Admin: " + isProviderAdmin + " Dispatcher Ind: "
				+ isDispatchInd);
		if (null != vendorIdStr && vendorIdStr.trim().length() > 0 && isDispatchInd) {

			String json = null;
			try {
				json = IOUtils.toString(getRequest().getReader());

				@SuppressWarnings("deprecation")
				JSONObject obj = JSONObject.fromString(json);
				String soId = (String) obj.get("soId");
				Integer routedResourceId = (Integer) obj.get("routedResourceId");
				Integer companyID = securityContext.getCompanyId();
				
				Long startDate = (Long) obj.get("startDate");
				String startTime = (String) obj.get("startTime");
				Long endDate = (Long) obj.get("endDate");
				String endTime = (String) obj.get("endTime");

				ServiceDatetimeSlot selectedSlot = new ServiceDatetimeSlot();
				selectedSlot.setServiceStartDate(new Timestamp(startDate));
				selectedSlot.setServiceStartTime(startTime);
				selectedSlot.setServiceEndDate(new Timestamp(endDate));
				selectedSlot.setServiceEndTime(endTime);

				CalendarPortalResponseDto response = calendarPortalDelegate.acceptSO(soId, routedResourceId, companyID, selectedSlot);

				if (null != response.getError()) {
					logger.error("problem in the response of calendarPortalDelegate: " + response.getError().getErrorMessage());
					jsonString = "Error!";
					returnString = ERROR;
				} else {
					jsonString = toJSON(response);
				}
			} catch (IOException exception) {
				logger.error("problem while io operations acceptSO", exception);
				jsonString = "Error!";
				returnString = ERROR;
			} catch (Exception exception) {
				logger.error("exception in acceptSO", exception);
				jsonString = "Error!";
				returnString = ERROR;
			}
		} else {
			logger.warn("Vendor id is not present in session or not a dispatcher.");
			jsonString = "Error!";
			returnString = ERROR;
		}

		logger.info("exiting acceptSO of CalendarPortalAction");
		return returnString;
	}

	public String rejectSO() {
		logger.info("entering rejectSO of CalendarPortalAction");

		String returnString = SUCCESS;

		String vendorIdStr = (String) ActionContext.getContext().getSession().get(VENDOR_ID);
		SecurityContext securityContext = (SecurityContext) ServletActionContext.getRequest().getSession().getAttribute(SECURITY_KEY);

		boolean isSLAdmin = securityContext.isSlAdminInd();
		boolean isProviderAdmin = securityContext.isPrimaryInd();
		boolean isDispatchInd = securityContext.isDispatchInd();

		logger.info("Vendor id: " + vendorIdStr + " SLAdmin: " + isSLAdmin + " Provider Admin: " + isProviderAdmin + " Dispatcher Ind: "
				+ isDispatchInd);
		if (null != vendorIdStr && vendorIdStr.trim().length() > 0 && isDispatchInd) {

			String json = null;
			try {
				json = IOUtils.toString(getRequest().getReader());

				@SuppressWarnings("deprecation")
				JSONObject obj = JSONObject.fromString(json);
				String soId = (String) obj.get("soId");
				Integer routedResourceId = (Integer) obj.get("routedResourceId");
				Integer rejectReasonId = (Integer) obj.get("rejectReasonId");
				String rejectReasonComment = (String) obj.get("rejectReasonComment");

				CalendarPortalResponseDto response = calendarPortalDelegate.rejectSO(soId, routedResourceId, rejectReasonId,
						rejectReasonComment, securityContext.getUsername());

				if (null != response.getError()) {
					logger.error("problem in the response of calendarPortalDelegate: " + response.getError().getErrorMessage());
					jsonString = "Error!";
					returnString = ERROR;
				} else {
					jsonString = toJSON(response);
				}
			} catch (IOException exception) {
				logger.error("problem while io operations rejectSO", exception);
				jsonString = "Error!";
				returnString = ERROR;
			} catch (Exception exception) {
				logger.error("exception in rejectSO", exception);
				jsonString = "Error!";
				returnString = ERROR;
			}
		} else {
			logger.warn("Vendor id is not present in session or not a dispatcher.");
			jsonString = "Error!";
			returnString = ERROR;
		}

		logger.info("exiting rejectSO of CalendarPortalAction");
		return returnString;
	}

	/**
	 * Ajax ping to this action class
	 */
	public void pingAJAX() {
		jsonString = "{'response':'success'}";
	}

	/**
	 * Method to
	 * 
	 * @return
	 */
	public String doExternalCalIntegration() {
			SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
		if(securityContext == null || securityContext.getVendBuyerResId() == null
				|| securityContext.getCompanyId() == null){
			logger.warn("vendorid or resourceid is null. redirecting to error page from CalendarPortalAction.");
			return ERROR;
		}
			int personId = securityContext.getVendBuyerResId();
			logger.info(personId);
			int firmId = securityContext.getCompanyId();
			logger.info(firmId);
		if(ActionContext.getContext().getParameters().containsKey("code")){
			String contextPath = getRequest().getScheme() + "://" + getRequest().getServerName()
			        + ":" + getRequest().getServerPort() + getRequest().getContextPath() + "/";
			logger.info(contextPath);
			String[] codes = (String[]) (ActionContext.getContext().getParameters().get("code"));
			String oAuthCode = codes[0];
			logger.info("Lets persist oAuth code: " + oAuthCode);
			externalCalendarDTO = calendarPortalDelegate.saveOrUpdateExternalCalendarDetail(firmId, personId, oAuthCode,contextPath);
		}else{
			externalCalendarDTO = calendarPortalDelegate.getExternalCalendarDetial(personId);
		}
		return SUCCESS;
	}

	/**
	 * Method to convert object into JSON string
	 * 
	 * @param obj
	 * @return
	 */
	private String toJSON(Object obj) {
		return jsonSerializer.deepSerialize(obj);
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public ExternalCalendarDTO getExternalCalendarDTO() {
		return externalCalendarDTO;
	}

	public void setExternalCalendarDTO(ExternalCalendarDTO externalCalendarDTO) {
		this.externalCalendarDTO = externalCalendarDTO;
	}
}