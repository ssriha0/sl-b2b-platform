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
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;
import com.newco.marketplace.web.dto.SimpleServiceOrderWizardBean;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDescribeAndScheduleDTO;
import com.newco.marketplace.web.dto.simple.SBLocationFormDTO;
import com.newco.marketplace.web.utils.SSoWSessionFacility;
import com.opensymphony.xwork2.ActionContext;

public abstract class SBLocationAction extends SLBaseAction {

	protected SBLocationFormDTO model = new SBLocationFormDTO();
	private ICreateServiceOrderDelegate createServiceOrderDelegate;
	protected static final Logger logger = Logger
			.getLogger("SBPanelSelectLocationAction");

	public SBLocationAction() {
		super();
	}

	/**
	 * Description:  Retrieves buyer resource info for the Edit Account screen
	 * @param buyerResId
	 * @return <code>CreateServiceOrderEditAccountDTO</code>
	 * @throws Exception
	 */
	public List<LocationVO> getBuyerResourceLocationInfo(Integer buyerResId)
			throws Exception {
		List<LocationVO> locDTOs = new ArrayList<LocationVO>();
		try {
			locDTOs = createServiceOrderDelegate
					.loadLocationsByResourceId(buyerResId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return locDTOs;
	}

	public String addNewLocation() throws Exception {
		getModel().clearAllErrors();

		// Validate		
		getModel().validateNewLocation();

		LocationVO newLoc = getModel().getNewLocation();

		if (getModel().getErrors().size() == 0) {
			createServiceOrderDelegate.saveLocation(newLoc,
					get_commonCriteria().getVendBuyerResId());
			
			// Clear out form after saving
			getModel().setNewLocation(new LocationVO());
		}

		getModel().getLocations().addAll(
				getBuyerResourceLocationInfo(get_commonCriteria()
						.getVendBuyerResId()));

		
		return "display_page";
	}

	/**
	 * Description:
	 * @return <code>boolean</code>
	 * @throws BusinessServiceException
	 */
	public boolean persistSelectedLocationToSession()
			throws BusinessServiceException {
		// See if we have a selected location from the radio buttons
		String selectedLocId_str = (String) getParameter("existingAddr");
		if (getModel().hasSelectedLocation(selectedLocId_str) == false) {
			return false;
		}

		// Set the selected location ID in the model in 2 places
		Integer selectedLocId_int = Integer.parseInt(selectedLocId_str);
		if (selectedLocId_int != null)
			getModel().setSelectedLocationId(selectedLocId_int);

		LocationVO selectedLoc = null;
		for (LocationVO vo : getModel().getLocations()) {
			if (vo.getLocnId().intValue() == selectedLocId_int) {
				vo.setDefaultLocn(1);
				selectedLoc = vo; // Save this for use later
			} else {
				vo.setDefaultLocn(0);
			}
		}
		
	
		// Save Selected Location to session
		try {
			SSoWSessionFacility.getInstance().evaluateSSOWBeanState(getModel());
		} catch (Exception e) {
		}

		// We need to save some info for the next page Describe and Schedule.
		// Get instance of describeScheduleDTO
		CreateServiceOrderDescribeAndScheduleDTO describeScheduleDTO = (CreateServiceOrderDescribeAndScheduleDTO) SSoWSessionFacility
				.getInstance().getTabDTO(
						OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);

		//Set location Info into DTO then  put back DTO into session
		if (selectedLoc != null && describeScheduleDTO != null) {
			describeScheduleDTO.setLocationName(selectedLoc.getLocName());
			describeScheduleDTO.setStreet1(selectedLoc.getStreet1());
			describeScheduleDTO.setStreet2(selectedLoc.getStreet2());
			describeScheduleDTO.setStateCd(selectedLoc.getState());
			describeScheduleDTO.setZip(selectedLoc.getZip());
			describeScheduleDTO.setFromSelectLocationPage(true);

			// Persist to session		
			persistDtoToSsoSession(describeScheduleDTO,
					OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);
		}

		return true; // success, no validation errors.
	}

	/**
	 * Description: Utility to save the passed in DTO and Key to the
	 * <code>SimpleServiceOrderWizardBean</code>
	 * @param describeScheduleDTO
	 * @param dtoKey
	 */
	protected void persistDtoToSsoSession(SOWBaseTabDTO describeScheduleDTO,
			String dtoKey) {
		Map<String, Object> sessionMap = (Map<String, Object>) ActionContext
				.getContext().getSession();
		SimpleServiceOrderWizardBean serviceOrderWizardBean;
		serviceOrderWizardBean = (SimpleServiceOrderWizardBean) sessionMap
				.get(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY);
		if (serviceOrderWizardBean == null)
			return;

		HashMap<String, Object> soDTOs = serviceOrderWizardBean.getTabDTOs();

		soDTOs.put(dtoKey, describeScheduleDTO);
		sessionMap.put(OrderConstants.SIMPLE_SERVICE_ORDER_WIZARD_KEY,
				serviceOrderWizardBean);
	}

	public SBLocationFormDTO getModel() {
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