package com.newco.marketplace.web.action.details;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.exception.AssignOrderException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.servicelive.ordermanagement.services.OrderManagementService;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;

public class SODetailsAssignSOAction extends SLDetailsBaseAction{

	private static final long serialVersionUID = 1L;
	private static final Integer CONTACT_TYPE_ID_PRIMARY = 10;
	private static final Integer ENTITY_TYPE_ID_PROVIDER = 20;
	private static final String ASSIGN_PROVIDER_LIST = "assignProviderList";
	private static final String ASSIGNED_SO_ID = "assignProvSoId";
	private static final Logger LOGGER = Logger.getLogger(SODetailsAssignSOAction.class);
	private OrderManagementService managementService;
	//Sl-19820
	private ISODetailsDelegate detailsDelegate;
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

	/**
	 * Controller for displaying the Assign SO Pop up in SOD.
	 * */
	public String loadAssignServiceOrder(){
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
		String firmId = soContxt.getCompanyId().toString();
		//SL-19820
		//Getting from request
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soId =getParameter("soId");
		try{
			//SL-19820
			//Getting from request
			//ServiceOrderDTO serviceOrderDTO = getCurrentServiceOrderFromSession();
			ServiceOrderDTO serviceOrderDTO =null;
			serviceOrderDTO=detailsDelegate.getServiceOrder(soId, OrderConstants.PROVIDER_ROLEID, null);
			if(null!=serviceOrderDTO){
			setAttribute("serviceTime",formatServiceTime(serviceOrderDTO));
			setAttribute("serviceLocation", serviceOrderDTO.getLocationWidget());
			setAttribute("soId",soId);
			setAttribute("soTitle", serviceOrderDTO.getTitle());
			}
			List<ProviderResultVO> providers = managementService.getEligibleProviders(firmId, soId);
			if(null != providers && providers.size() > 0){
//				Sl-19820
//				getSession().setAttribute(ASSIGN_PROVIDER_LIST, providers);
//				getSession().setAttribute(ASSIGNED_SO_ID, soId);
//				getSession().setAttribute(VENDOR_ID, firmId);
				setAttribute(ASSIGN_PROVIDER_LIST, providers);
				setAttribute(ASSIGNED_SO_ID, soId);
				setAttribute(VENDOR_ID, firmId);
			}else{
				LOGGER.info("No eligible providers!");
			}
		}catch (Exception exc) {
			LOGGER.error(exc.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * Controller for Service Order Assigning.
	 * Sets error/success message in a variable "msg"!
	 * **/
	public String saveAssignServiceOrder(){
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
		String firmId = securityContext.getCompanyId().toString();
		//SL-19820
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		String soId=getRequest().getParameter("soId");
		this.soId=soId;
		String resource  = getRequest().getParameter("provider");
		int resourceId = null == resource ? 0 : Integer.valueOf(resource);
		List<ProviderResultVO> providers = managementService.getEligibleProviders(firmId, soId);
		String selectedResourceName = "";
		boolean validRecourse = Boolean.FALSE;
		if(null != providers && providers.size() > 0){
			for(ProviderResultVO providerResultVO : providers){
				if(providerResultVO.getResourceId() == resourceId){
					selectedResourceName = providerResultVO.getProviderFirstName()+" "+providerResultVO.getProviderLastName();
					validRecourse = Boolean.TRUE;
					break;
				}
			}
		}
		if(!validRecourse){
			LOGGER.info("Invalid resource id");
			return SUCCESS;
		}
		//setting so logging object
		SoLoggingVo soLoggingVO = new SoLoggingVo();
		Contact soContact = getsoContact(resourceId, soId);
		//SL-19820 
		//Passing soId to the method
		mapSOLogging(soLoggingVO,soContact, securityContext, resourceId,soId);
		
		//setting notes object
		ServiceOrderNote soNote = new ServiceOrderNote();
		mapSONote(soNote, soLoggingVO, securityContext);
		
		try {
			managementService.assignProvider(resourceId, soId, soLoggingVO, soNote,soContact);
			
			//SL-19820
			//getSession().removeAttribute(ASSIGN_PROVIDER_LIST);		
			//getSession().setAttribute(Constants.SESSION.SOD_MSG,"Service order is assigned to "+selectedResourceName);
			getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soId,"Service order is assigned to "+selectedResourceName);
		} catch (AssignOrderException exc) {
			//LOGGER.error(exc.getMessage());
			//SL-19820
			getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soId,exc.getMessage());
		} catch (DataServiceException exc) {
			LOGGER.error(exc.getMessage());
			//SL-19820
			getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soId,exc.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * Populates ServiceOrderNote object using pre populated SoLoggingVo.
	 * This method hard codes  Note type ID = 2 and PrivateId = 0 of ServiceOrderNote.
	 * @param soNote: {@link ServiceOrderNote} : The target to be populated.
	 * @param soLoggingVO : {@link SoLoggingVo} : The source.
	 * @return {@link Void}
	 * */
	private void mapSONote(ServiceOrderNote soNote, SoLoggingVo soLoggingVO, SecurityContext context) {
		soNote.setSoId(soLoggingVO.getServiceOrderNo());
		soNote.setCreatedDate(soLoggingVO.getCreatedDate());
		soNote.setSubject(Constants.ASSIGNMENT_NOTE_SUBJECT);
		soNote.setRoleId(soLoggingVO.getRoleId());
		soNote.setNote(soLoggingVO.getComment());
		soNote.setCreatedByName(context.getRoles().getLastName()+", "+context.getRoles().getFirstName());
		soNote.setModifiedBy(soLoggingVO.getModifiedBy());
		soNote.setModifiedDate(soLoggingVO.getModifiedDate());
		soNote.setNoteTypeId(Integer.valueOf(2));
		soNote.setEntityId(soLoggingVO.getEntityId());
		soNote.setPrivateId(Integer.valueOf(0));
	}

	/**
	 * Method which gets the Contact instance of the provider.
	 * @param firmId
	 * @param resourceId : Resource Id of the provider
	 * @param soId 
	 * @return {@link Contact}
	 * */
	private Contact getsoContact(Integer resourceId, String soId) {
		//ArrayList<Contact> routedContactList = managementService.getRoutedResources(soId,resourceId);
		Contact soContact = managementService.getRoutedResources(soId,resourceId);
	/*	if(null != routedContactList && null != resourceId){
			for(Contact contact : routedContactList){
				if(contact != null && contact.getResourceId().equals(resourceId)){
					soContact = contact;
					break;
				}
			}
		} */
		soContact.setSoId(soId);
		soContact.setEntityTypeId(ENTITY_TYPE_ID_PROVIDER);
		soContact.setContactTypeId(CONTACT_TYPE_ID_PRIMARY);
		soContact.setEntityId(resourceId);
		soContact.setCreatedDate(new Timestamp(new Date().getTime()));
		soContact.setModifiedDate(new Timestamp(new Date().getTime()));
		return soContact;
	}

	/**
	 * Populates soLoggingVO object using pre populated soContact
	 * @param soLoggingVO: {@link SoLoggingVo} : The target to be populated.
	 * @param soContact : {@link Contact} : The source.
	 * @param securityContext {@link SecurityContext}
	 * @param resourceId : The resource Id for whom SO to be assigned.
	 * @return {@link Void}
	 * */
	private void mapSOLogging(SoLoggingVo soLoggingVO, Contact soContact, SecurityContext securityContext, Integer resourceId,String soId) {
		
		Integer role = securityContext.getRoleId();
		Integer entityId = securityContext.getVendBuyerResId();
		String userFirstName =  securityContext.getRoles().getFirstName();
		String userLastName =  securityContext.getRoles().getLastName();
		//Sl-19820
		//Removing SoId fetch from session
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		soLoggingVO.setServiceOrderNo(soId);
		soLoggingVO.setOldValue(null);
		soLoggingVO.setNewValue(resourceId.toString());
		if(null != soContact){
			soLoggingVO.setFirstName(soContact.getFirstName().trim());
			soLoggingVO.setLastName(soContact.getLastName().trim());
			soLoggingVO.setEmail(soContact.getEmail().trim());
			soLoggingVO.setContactId(soContact.getContactId());
			soLoggingVO.setComment("Has been assigned to " + soContact.getFirstName() +" " + soContact.getLastName() + 
				"(" +  soLoggingVO.getNewValue() + ")");
		}else{
			soLoggingVO.setComment("Has been assigned to " +  soLoggingVO.getNewValue());
		}
		soLoggingVO.setCreatedDate(Calendar.getInstance().getTime());
		soLoggingVO.setModifiedDate(Calendar.getInstance().getTime());
		soLoggingVO.setCreatedByName(userFirstName +" "+userLastName);
		soLoggingVO.setModifiedBy(securityContext.getUsername());
		soLoggingVO.setRoleId(role);
		soLoggingVO.setEntityId(entityId);
		soLoggingVO.setValueName(Constants.ASSIGNMENT_NOTE_SUBJECT);
		soLoggingVO.setActionId(Integer.valueOf(Constants.SO_ACTION.SERVICE_ORDER_ASSIGN));
	}
	
	/**
	 * This method will format Service Time using the {@link Schedule} in the format
	 * required for displaying Assign Provider. 
	 * @param schedule {@link Schedule}
	 * @return Service Start time - End time
	 * eg: 4/15/13 - 4/20/13 at 7:30 AM - 12:00 PM (EST)
	 **/
	private String formatServiceTime(ServiceOrderDTO serviceOrderDTO) {
		StringBuilder serviceDate = new StringBuilder("");
		StringBuilder serviceTime = new StringBuilder("");
		if(null != serviceOrderDTO){
			Timestamp serviceTimeStart = serviceOrderDTO.getServiceDate1();
			Timestamp serviceTimeEnd = serviceOrderDTO.getServiceDate2();
			if(null != serviceTimeStart){
				String startArr [] = serviceTimeStart.toString().split(" ");
				String startDate = 	getTimeStampFromStr(startArr[0],"yyyy-MM-dd", "MM/dd/yy");
				//String startTime = getTimeStampFromStr(startArr[1],"HH:mm:ss", "hh:mm aa");
				serviceDate.append(startDate);
				serviceTime.append(serviceOrderDTO.getServiceTimeStart());
				if(null != serviceTimeEnd){
					String endArr [] = serviceTimeEnd.toString().split(" ");
					String endDate = "";
					//String endTime = "";
					endDate = getTimeStampFromStr(endArr[0],"yyyy-MM-dd", "MM/dd/yy");
					//endTime = getTimeStampFromStr(endArr[1],"HH:mm:ss", "hh:mm aa");
					serviceTime.append(" - ").append(serviceOrderDTO.getServiceTimeEnd());
					serviceDate.append(" - ").append(endDate).append(" at ").append(serviceTime);
				}else{
					serviceDate.append(" at ").append(serviceTime);
				}
				serviceDate.append(" (").append(serviceOrderDTO.getServiceLocationTimeZone()).append(")"); //TODO
			}
		}
		return serviceDate.toString();
	}

	private String getTimeStampFromStr(String date, String format, String requiredFormat){
		if(StringUtils.isBlank(date)){
			return "";
		}
		String strDate = date;
		Date dt1 = stringToDate(strDate, format);
		DateFormat formatter = new SimpleDateFormat(requiredFormat);
		String strFotrmated = "";
		strFotrmated = formatter.format(dt1);
		return strFotrmated;
	}
	
	private Date stringToDate(String strDate, String format) {
		DateFormat formatter;
		Date date = null;
		formatter = new SimpleDateFormat(format);
		try {
			date = (Date) formatter.parse(strDate);
		} catch (ParseException e) {
			LOGGER.error(e);
		}
		return date;
	}
	
	public OrderManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(OrderManagementService managementService) {
		this.managementService = managementService;
	}

}
