package com.newco.marketplace.web.action.assurant;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.business.iBusiness.client.IClientInvoiceBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO;
import com.newco.marketplace.dto.vo.incident.IncidentEventVO;
import com.newco.marketplace.dto.vo.incident.IncidentPartVO;
import com.newco.marketplace.dto.vo.incident.IncidentResponseVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IAssurantIncidentEventTrackerDelegate;
import com.newco.marketplace.web.dto.assurant.IncidentEventTrackerDTO;
import com.newco.marketplace.web.utils.Config;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class AssurantIncidentEventTrackerAction extends SLBaseAction implements Preparable, ModelDriven<IncidentEventTrackerDTO>
{

	private static final long serialVersionUID = 0L;
	
	private IncidentEventTrackerDTO incidentEventTrackerDTO = new IncidentEventTrackerDTO();
	
	private IIncidentBO incidentBO;
	
	private  IClientInvoiceBO clientInvoiceBO;
	
	private IAssurantIncidentEventTrackerDelegate assurantIncidentEventTrackerDelegate;
	
	private static String REASON_REQUEST_PLEASE_SELECT="-1";
	
	
	public IIncidentBO getIncidentBO()
	{
		return incidentBO;
	}

	public void setIncidentBO(IIncidentBO incidentBO)
	{
		this.incidentBO = incidentBO;
	}

	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
		initDropdowns();		
	}

	public IncidentEventTrackerDTO getModel()
	{
		return incidentEventTrackerDTO;
	}
	
	public String displayPage() throws Exception
	{
		
		String clientIncidentID = getParameter("clientIncidentID");
		String soID = getParameter("soID");
		String monitorTab = getParameter("monitorTab");
		
		setAttribute("monitorTab", monitorTab);
		
		
		initEventsAndParts(clientIncidentID, soID);
		
		return SUCCESS;
	}
	
	private void initEventsAndParts(String clientIncidentID, String soID)
	{
		List<IncidentEventVO> incidentEvents = null;
		List<IncidentPartVO> incidentParts = null;
		
		try
		{
			incidentEvents = incidentBO.getIncidentEvents(clientIncidentID);
		}
		catch (BusinessServiceException e)
		{
			e.printStackTrace();
		}
		
		// Put some key IDs into model
		incidentEventTrackerDTO = getModel();
		incidentEventTrackerDTO.setClientIncidentID(clientIncidentID + "");
		incidentEventTrackerDTO.setSoID(soID);
		
		if(incidentEvents != null && !incidentEvents.isEmpty())
		{
			Integer incidentID = null;			
			incidentID = incidentEvents.get(0).getIncidentID();
			incidentEventTrackerDTO.setIncidentID(incidentID + "");
			try
			{
				if(incidentID != null)
				{
					incidentParts = incidentBO.getIncidentPartsByIncidentID(incidentID);
					setAttribute("incidentID", incidentID);
				}
				else
				{
					incidentParts = new ArrayList<IncidentPartVO>();
				}
			}
			catch (BusinessServiceException e)
			{
				e.printStackTrace();
			}
		}

		setAttribute("incidentEvents", incidentEvents);
		setAttribute("incidentParts", incidentParts);
		
	}
	
	private void initDropdowns()
	{
		ResourceBundle rb = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
		
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		list.add(new LabelValueBean("--Select a Reason Code--", REASON_REQUEST_PLEASE_SELECT));
		String optionValue = null;
		for(int incidentTrackerOption = 1;;++incidentTrackerOption) {
			try {
				optionValue = rb.getString("incident.tracker.action." + incidentTrackerOption);
			} catch (MissingResourceException missingResourceEx) {
				// Exit loop
				break;
			}
			list.add(new LabelValueBean(optionValue, String.valueOf(incidentTrackerOption)));
		}
		
		getModel().setDropdownList(list);
	}
	
	public String submitRequest() throws Exception
	{
		IncidentEventTrackerDTO model = getModel();
		if(model == null)
			return SUCCESS;

		// Get some parameters that we'll need to pass to backend call.
		String soID = getParameter("soID");
		String incidentID = getParameter("incidentID");
		String clientIncidentID = getParameter("clientIncidentID");
		
		initEventsAndParts(clientIncidentID, soID);

		// Check for properly filled out form.
		model.validate();
		if(model.getErrors() != null && model.getErrors().size() > 0)
		{
			return SUCCESS;
		}
		
		// Get the text for the action selected from the dropdown.  This will be used later.
		String subject ="";
		ResourceBundle rb = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
		subject = rb.getString("incident.tracker.action." + model.getDropdownSelection());
		
		// prepend the actionId so that the MarketESB's NotifyBuyer integration can map the ID to the assurantStatus
		// subject will now look like: "1)Original Subject" 
		subject = model.getDropdownSelection().trim() + ")" + subject;
		
		// Set data to do the Assurant Incident magic
		IncidentResponseVO incidentResponseVO = new IncidentResponseVO();		
		incidentResponseVO.setSoId(soID);
		if(StringUtils.isNotBlank(incidentID))
		{
			incidentResponseVO.setIncidentId(Integer.parseInt(incidentID));
		}		
		incidentResponseVO.setClientIncidentId(clientIncidentID);
		incidentResponseVO.setResourceId(get_commonCriteria().getVendBuyerResId());
		incidentResponseVO.setRoleId(get_commonCriteria().getRoleId());
		incidentResponseVO.setSubject(subject);
		incidentResponseVO.setAction(model.getDropdownSelection().trim());
		incidentResponseVO.setNoteDescription(model.getReasonText());
		incidentResponseVO.setNoteTypeId(OrderConstants.SO_NOTE_GENERAL_TYPE);
		String name = get_commonCriteria().getLName() + ","+ get_commonCriteria().getFName();
		incidentResponseVO.setCreatedByName(name);
		incidentResponseVO.setModifiedBy(get_commonCriteria().getTheUserName());
		incidentResponseVO.setEntityId(get_commonCriteria().getCompanyId());
		incidentResponseVO.setPrivateInd(OrderConstants.SO_NOTE_PUBLIC_ACCESS);
		
		assurantIncidentEventTrackerDelegate.notifyBuyer(incidentResponseVO, clientIncidentID, get_commonCriteria().getSecurityContext());
		
		if(model.getDropdownSelection().equals(String.valueOf(OrderConstants.INCIDENT_TRACKER_CANCEL_BY_PROVIDER.intValue()))  || 
				model.getDropdownSelection().equals(String.valueOf(OrderConstants.INCIDENT_TRACKER_CANCEL_BY_ASSURANT.intValue())) || 
				model.getDropdownSelection().equals(String.valueOf(OrderConstants.INCIDENT_TRACKER_SERVICE_DENIED.intValue()))) {
			clientInvoiceBO.invoiceIncidentEvent(soID, clientIncidentID, get_commonCriteria().getSecurityContext().getClientId());
		}
		
		setAttribute("successMsg", "Your response was successfully sent.");
		initDropdowns();
		model.setReasonText("");
		model.setDropdownSelection(null);
		
		return SUCCESS;
	}

	public ResourceBundle getTheResourceBundle() {
		return Config.getResouceBundle();
	}
	
	public IClientInvoiceBO getClientInvoiceBO() {
		return clientInvoiceBO;
	}

	public void setClientInvoiceBO(IClientInvoiceBO clientInvoiceBO) {
		this.clientInvoiceBO = clientInvoiceBO;
	}
	
	public void setAssurantIncidentEventTrackerDelegate(
			IAssurantIncidentEventTrackerDelegate assurantIncidentEventTrackerDelegate) {
		this.assurantIncidentEventTrackerDelegate = assurantIncidentEventTrackerDelegate;
	}

	public IAssurantIncidentEventTrackerDelegate getAssurantIncidentEventTrackerDelegate() {
		return assurantIncidentEventTrackerDelegate;
	}
	
	

}
