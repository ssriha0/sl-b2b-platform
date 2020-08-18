package com.newco.marketplace.web.action.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;
import com.newco.marketplace.web.dto.SimpleServiceOrderWizardBean;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDescribeAndScheduleDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderFindProvidersDTO;
import com.newco.marketplace.web.dto.simple.SBLocationFormDTO;
import com.newco.marketplace.web.utils.SSoWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class SBSelectLocationAction extends SLBaseAction 
				implements Preparable, ModelDriven<SBLocationFormDTO>
{
	
	private static final long serialVersionUID = 8878196496268919097L;
	private SBLocationFormDTO model = new SBLocationFormDTO();
	private ICreateServiceOrderDelegate createServiceOrderDelegate;
	private static final Logger logger = Logger.getLogger("SBPanelSelectLocationAction");

	

	public SBSelectLocationAction()
	{
	}
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
		
		
		try {
			model = (SBLocationFormDTO) SSoWSessionFacility
					.getInstance().getTabDTO(
							OrderConstants.SSO_SELECT_LOCATION_DTO);
			String editOrCreateMode = (String) SSoWSessionFacility
					.getInstance().getApplicationMode();
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring", e);
		}
	}
	

	public String displayPanel() throws Exception
	{
		
		return "display_panel";
	}

	public String displayPage() throws Exception
	{
		Boolean isLoggedIn = (Boolean)getSession().getAttribute(SOConstants.IS_LOGGED_IN);
		if (!isLoggedIn.booleanValue()) {
			return "dashboard";
		}
		
		
		
		// Set the locations associated with this buyer
		getModel().getLocations().clear();
		getModel().getLocations().addAll(getBuyerResourceLocationInfo(get_commonCriteria().getVendBuyerResId()));
		getRequest().setAttribute("SELECT_LOCATION_PAGE", true);
		return "display_page";
	}

	
	
	/**
	 * Description:  Retrieves buyer resource info for the Edit Account screen
	 * @param buyerResId
	 * @return <code>CreateServiceOrderEditAccountDTO</code>
	 * @throws Exception
	 */
	private List<LocationVO> getBuyerResourceLocationInfo(Integer buyerResId) throws Exception
	{
		List<LocationVO> locDTOs = new ArrayList<LocationVO>();
		try{
			locDTOs = createServiceOrderDelegate.loadLocationsByResourceId(buyerResId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return locDTOs;
	}
	

	public String addNewLocation() throws Exception
	{
		getModel().clearAllErrors();
		
		// Validate		
		getModel().validateNewLocation();
		
		LocationVO newLoc = getModel().getNewLocation();		
		
		
		if(getModel().getErrors().size() == 0)
		{
			createServiceOrderDelegate.saveLocation(newLoc, get_commonCriteria().getVendBuyerResId());
			clearNewLocValuesInForm();
		}

		getModel().getLocations().clear();
		getModel().getLocations().addAll(getBuyerResourceLocationInfo(get_commonCriteria().getVendBuyerResId()));
		
		return "display_page";
	}

	private void clearNewLocValuesInForm()
	{
		if(getModel() == null)
			return;
		
		
		getModel().setNewLocation(new LocationVO());
	}
	
	
	public String deleteLocation() throws Exception
	{
		Integer locId = 111;
		
		createServiceOrderDelegate.deleteLocationByLocationId(locId);
		return "display_page";
	}
	
	
	
	
	public String next() throws Exception
	{		
		
		boolean validationOK = persistSelectedLocationToSession();
		
		if(validationOK)		
			return "next_success";
		else
			return "next_fail";
	}
	
	private boolean persistSelectedLocationToSession() throws BusinessServiceException
	{
		// See if we have a selected location from the radio buttons
		String selectedLocId_str = (String)getParameter("existingAddr");
		if(getModel().hasSelectedLocation(selectedLocId_str) == false)
		{
			getRequest().setAttribute("validateLocation", "validateLocation");
			return false;
		}
		
		// Set the selected location ID in the model in 2 places
		Integer selectedLocId_int = Integer.parseInt(selectedLocId_str);
		if(selectedLocId_int != null)
			getModel().setSelectedLocationId(selectedLocId_int);
		
		LocationVO selectedLoc=null;
		for(LocationVO vo : getModel().getLocations())
		{
			if(vo.getLocnId().intValue() == selectedLocId_int)
			{
				vo.setDefaultLocn(1);
				selectedLoc = vo; // Save this for use later
			}
			else
			{
				vo.setDefaultLocn(0);
			}
		}

		// Save Selected Location to session
		try
		{
			SSoWSessionFacility.getInstance().evaluateSSOWBeanState(getModel());
		}
		catch(Exception e)
		{			
		}
		
		
		// We need to save some info for the next page Describe and Schedule.
		// Get instance of describeScheduleDTO
		CreateServiceOrderDescribeAndScheduleDTO
		 describeScheduleDTO = 
				(CreateServiceOrderDescribeAndScheduleDTO)
							SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);

		CreateServiceOrderFindProvidersDTO findProvidersDTO = 
			(CreateServiceOrderFindProvidersDTO)
						SSoWSessionFacility.getInstance().getTabDTO(OrderConstants.SSO_FIND_PROVIDERS_DTO);

		
        //Set location Info into DTO then  put back DTO into session
		if(selectedLoc != null && describeScheduleDTO != null)
		{
			describeScheduleDTO.setLocationName(selectedLoc.getLocName());
			describeScheduleDTO.setStreet1(selectedLoc.getStreet1());
			describeScheduleDTO.setStreet2(selectedLoc.getStreet2());
			describeScheduleDTO.setCity(selectedLoc.getCity());
			describeScheduleDTO.setStateCd(selectedLoc.getState());
			describeScheduleDTO.setZip(selectedLoc.getZip());
			describeScheduleDTO.setFromSelectLocationPage(true);
			findProvidersDTO.setState(selectedLoc.getState());
			findProvidersDTO.setZip(selectedLoc.getZip());
		
			// Persist to session		
	        persistDtoToSsoSession(describeScheduleDTO,OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);
	        persistDtoToSsoSession(findProvidersDTO,OrderConstants.SSO_FIND_PROVIDERS_DTO);
		}
		
		//We also need to put selected location into the Find Providers Page.

		
		return true; // success, no validation errors.
	}

	private void persistDtoToSsoSession(
			SOWBaseTabDTO describeScheduleDTO, String dtoKey) {
		Map<String, Object> sessionMap = (Map<String, Object>)ActionContext.getContext().getSession();
        SimpleServiceOrderWizardBean serviceOrderWizardBean;
        serviceOrderWizardBean = (SimpleServiceOrderWizardBean)sessionMap.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);        
        if(serviceOrderWizardBean == null)
        	return;
        
        HashMap<String, Object> soDTOs = serviceOrderWizardBean.getTabDTOs();

        soDTOs.put(dtoKey, describeScheduleDTO);
        sessionMap.put(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY, serviceOrderWizardBean);
	}

	
	public SBLocationFormDTO getModel()
	{
		return model;
	}

	public void setModel(SBLocationFormDTO model) {
		this.model = model;
	}

	public ICreateServiceOrderDelegate getCreateServiceOrderDelegate() {
		return createServiceOrderDelegate;
	}

	public void setCreateServiceOrderDelegate(
			ICreateServiceOrderDelegate createServiceOrderDelegate) {
		this.createServiceOrderDelegate = createServiceOrderDelegate;
	}

	
}
