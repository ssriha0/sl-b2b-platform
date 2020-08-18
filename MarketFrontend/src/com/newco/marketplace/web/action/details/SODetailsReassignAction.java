package com.newco.marketplace.web.action.details;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.SOContactDTO;
import com.newco.marketplace.web.dto.SOLoggingDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;
import com.newco.marketplace.web.dto.ServiceOrderDTO;

public class SODetailsReassignAction  extends SLDetailsBaseAction
implements Preparable, ModelDriven { 
	
	private static final Logger logger = Logger
		.getLogger("SODetailsRequestRescheduleAction");
	private ISODetailsDelegate soDetailsManager;
	private ArrayList contactSelect;
	private String reassignReason;
	private ArrayList contactList;
	//Sl-19820
	String soId;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public SODetailsReassignAction(ISODetailsDelegate delegate) {
	this.soDetailsManager = delegate;
	}

	public ArrayList getContactSelect() {
		return contactSelect;
	}

	public String getReassignReason() {
		return reassignReason;
	}

	public void setReassignReason(String reassignReason) {
		this.reassignReason = reassignReason;
	}

	public void setContactSelect(ArrayList contactSelect) {
		this.contactSelect = contactSelect;
	}
	public List getRoutedResources()
	{
		//Sl-19820
		//String soID = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String soID =(String) getParameter("hiddenSoId");
		List<SOContactDTO> contactList = null;
		try {
			contactList = soDetailsManager.getRoutedResources(soID);
			setAttribute("contactList", contactList);
		}catch(Exception e){
			}
		return contactList;
	}
	

	public String saveReassignSO() throws BusinessServiceException
	{
		//SL-19820
		//String soID = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String soID =(String) getParameter("hiddenSoId");
		this.soId = soID;
		setAttribute(OrderConstants.SO_ID, soID);
		
		Integer currentRoutedResourceId = 0;
		String firstName = null;
		String lastName = null;
		String currentResourceFirstName = null;
		String currentResourceLastName = null;
		String email = null;
		String newResourceId = null;
		SOContactDTO soContactDTO = null;
		SOContactDTO soCurrentContactDTO = null;
		boolean isError = false;
		String result = null;
		ProcessResponse procResp = new ProcessResponse();
		//Sl-19820
		ServiceOrderDTO serviceOrderDTO = null;		
		try {
			serviceOrderDTO = soDetailsManager.getServiceOrder(soID,
					OrderConstants.PROVIDER_ROLEID, null);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			throw new BusinessServiceException("Data Access exception");
		}
		/*if (this.getCurrentServiceOrderFromSession().getProviderContact().getIndividualID() != null) {
			currentRoutedResourceId = Integer.parseInt(this.getCurrentServiceOrderFromSession().getProviderContact().getIndividualID());
			firstName=this.getCurrentServiceOrderFromSession().getProviderContact().getIndividualName();
			StringTokenizer str=new StringTokenizer(firstName," ");
			lastName=str.nextToken();
			if (lastName != null && lastName.endsWith(",")) {
				currentResourceLastName=lastName.substring(0, lastName.length()-1);
			}
			currentResourceFirstName=str.nextToken();
		}*/
		
		//Added for sl-19820
		if(serviceOrderDTO != null)
 {
			if (serviceOrderDTO.getProviderContact().getIndividualID() != null) {
				currentRoutedResourceId = Integer.parseInt(serviceOrderDTO
						.getProviderContact().getIndividualID());
				firstName = serviceOrderDTO.getProviderContact()
						.getIndividualName();
				StringTokenizer str = new StringTokenizer(firstName, " ");
				lastName = str.nextToken();
				if (lastName != null && lastName.endsWith(",")) {
					currentResourceLastName = lastName.substring(0,
							lastName.length() - 1);
				}
				currentResourceFirstName = str.nextToken();
			}

			setAttribute(THE_SERVICE_ORDER, serviceOrderDTO);
		}

		ArrayList selected =  getContactSelect();
		if(selected!=null)
		{
			newResourceId = (String) selected.get(0);
		}
		//sl-19820 commenting code
		//ArrayList contactList = (ArrayList) getSession().getAttribute("contactList");
		 ArrayList contactList = (ArrayList) getRoutedResources();
		if(contactList!=null && newResourceId!=null)
		{
			for(int i=0; i<contactList.size(); i++)
			{
				soContactDTO = (SOContactDTO) contactList.get(i);
				if(soContactDTO != null && soContactDTO.getResourceId()== Integer.parseInt(newResourceId))
					break;
			}
		}
		if(contactList!=null && currentRoutedResourceId!=null)
		{
			for(int i=0; i<contactList.size(); i++)
			{
				soCurrentContactDTO = (SOContactDTO) contactList.get(i);
				if(soCurrentContactDTO != null && soCurrentContactDTO.getResourceId()== currentRoutedResourceId)
					break;
			}
		}
		String reassignReason = getReassignReason();
		if (StringUtils.isEmpty(reassignReason)) {
			result = "Please enter a reason for reassigning the service order";
			isError = true;
		} 
		if (selected == null) {
			result = "Please select a resource to reassign the service order to";
			isError = true;
		}
		SOLoggingDTO soLoggingDTO = new SOLoggingDTO();
		if (!isError) {
			Integer role = ((SecurityContext) getSession().getAttribute(
			"SecurityContext")).getRoleId();
			Integer entityId = ((SecurityContext) getSession().getAttribute(
			"SecurityContext")).getVendBuyerResId();
			String userName =  ((SecurityContext) getSession().getAttribute(
			"SecurityContext")).getUsername();			
			String userFirstName=  ((SecurityContext) getSession().getAttribute(
			"SecurityContext")).getRoles().getFirstName();
			String userLastName =  ((SecurityContext) getSession().getAttribute(
			"SecurityContext")).getRoles().getLastName();
			soLoggingDTO.setServiceOrderNo(soID);
			soLoggingDTO.setFirstName(soContactDTO.getFirstName());
			soLoggingDTO.setLastName(soContactDTO.getLastName());
			soLoggingDTO.setEmail(soContactDTO.getEmail());
			soLoggingDTO.setContactId(Integer.parseInt(newResourceId));
			soLoggingDTO.setOldValue(currentRoutedResourceId.toString());
			soLoggingDTO.setNewValue(newResourceId);
			soLoggingDTO.setComment("Has been reassigned from " + currentResourceFirstName + " " + currentResourceLastName+ "(" + soLoggingDTO.getOldValue() + ") to " + soContactDTO.getFirstName() +" " + soContactDTO.getLastName() + 
					"(" +  soLoggingDTO.getNewValue() + "). Reason for Reassigning is: " + reassignReason);
			soLoggingDTO.setCreatedDate(Calendar.getInstance().getTime());
			soLoggingDTO.setModifiedDate(Calendar.getInstance().getTime());
			soLoggingDTO.setCreatedByName(userFirstName+" "+userLastName);
			soLoggingDTO.setModifiedBy(userFirstName+" "+userLastName);
			soLoggingDTO.setRoleId(role);
			soLoggingDTO.setEntityId(entityId);
			soLoggingDTO.setValueName(Constants.REASSIGNMENT_NOTE_SUBJECT);
			soLoggingDTO.setActionId(Integer.parseInt(Constants.SO_ACTION.SERVICE_ORDER_REASSIGN));
			procResp = soDetailsManager.saveReassignSO(soLoggingDTO, reassignReason);
			if (!procResp.getCode().equals(ServiceConstants.VALID_RC)) {
				this.setReturnURL("/serviceOrderMonitor.action");
				this.setErrorMessage(procResp.getMessages().get(0));
				return ERROR;
			} else {
				//Sl-19820
				//getSession().setAttribute(Constants.SESSION.SOD_MSG, procResp.getMessages().get(0));
				getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soID, procResp.getMessages().get(0));
				return SUCCESS;
			}
		}

		this.setReturnURL("/serviceOrderMonitor.action");
		this.setErrorMessage(result);
		return ERROR;
	}


public void prepare() throws Exception {
	// TODO Auto-generated method stub
	
}

public Object getModel() {
	// TODO Auto-generated method stub
	return null;
}

}

