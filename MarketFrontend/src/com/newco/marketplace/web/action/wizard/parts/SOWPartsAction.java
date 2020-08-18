package com.newco.marketplace.web.action.wizard.parts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.web.action.base.SLWizardBaseAction;
import com.newco.marketplace.web.action.wizard.ISOWAction;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.SOWPartDTO;
import com.newco.marketplace.web.dto.SOWPartsTabDTO;
import com.newco.marketplace.web.dto.SOWPhoneDTO;
import com.newco.marketplace.web.dto.SOWPricingTabDTO;
import com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO;
import com.newco.marketplace.web.dto.SOWTabStateVO;
import com.newco.marketplace.web.dto.ServiceOrderWizardBean;
import com.newco.marketplace.web.dto.TabNavigationDTO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.utils.SOClaimedFacility;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.45 $ $Author: sgopala $ $Date: 2008/04/25 16:42:44 $
 *
 */
public class SOWPartsAction extends SLWizardBaseAction implements Preparable,
		ISOWAction, ModelDriven<SOWPartsTabDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2158305483509107751L;
	private static final Logger logger = Logger.getLogger(SOWPartsAction.class.getName());
	private SOWPartsTabDTO partsTabDTO = new SOWPartsTabDTO();
	private String previous;
	private String next;
	
	//Sl-19820
	String soID;

	public String getSoID() {
		return soID;
	}

	public void setSoID(String soID) {
		this.soID = soID;
	}

	public void prepare() throws Exception {
		createSessionAttributes();
		
		//SL-19820
		String soId = getParameter(OrderConstants.SO_ID);
        setAttribute(OrderConstants.SO_ID,soId);
        this.soID = soId;
        
        String groupId = (String) getSession().getAttribute(OrderConstants.GROUP_ID+"_"+soId);
        setAttribute(OrderConstants.GROUP_ID,groupId);
        
        String actionType = (String)getSession().getAttribute("actionType_"+soId);
        setAttribute("actionType", actionType);
              
    	String entryTab = (String)getSession().getAttribute("entryTab_"+soId);
		setAttribute("entryTab", entryTab);
		
		Integer status=(Integer) getSession().getAttribute(OrderConstants.SOW_SERVICE_ORDER_STATUS_SOID+"_"+soId);
        setCurrentSOStatusCodeInRequest(status);
		
		populateLookup();
		createCommonServiceOrderCriteria();
		maps();
        /**SL-20884 : Setting partsSuppliedBy from template used 
		in service order creation using sku in partsTabDto */
		setPartsSuppliedBy();
		prepareParts();
		
	      //SL-19820 : TODO : Move these to a new method
        String addNewPartWarningMsg = (String) getSession().getAttribute("addNewPartWarningMsg_"+soId);
        if(StringUtils.isNotBlank(addNewPartWarningMsg)){
        	setAttribute("addNewPartWarningMsg", addNewPartWarningMsg);
        }
        getSession().removeAttribute("addNewPartWarningMsg_"+soId);
       /* //SL-20527 Setting Spend Limit labor with out considering deleted task
      	setSpendLimitLabor();*/
	}
	
	
	public String createEntryPoint() throws Exception
	{
		return SUCCESS;
	}

	private void prepareParts() {
		partsTabDTO = (SOWPartsTabDTO) SOWSessionFacility.getInstance()
				.getTabDTO(OrderConstants.SOW_PARTS_TAB);

		if (partsTabDTO == null)
			return;

		if (partsTabDTO.getParts() == null)
			return;

		if (partsTabDTO.getParts().size() == 0) {
			createFirstPart();
		}
		setPhoneNumbers();
		

		for (SOWPartDTO partDTO : partsTabDTO.getParts()) {
			if (partDTO != null){
				setupShippingStatusButtons(partDTO);
				setupPanelsExpandablity(partDTO);
				continue;
			}

			SOWContactLocationDTO contLocationDTO = partDTO
					.getPickupContactLocation();
			if (contLocationDTO == null)
				continue;

			List<SOWPhoneDTO> phoneDTOs = contLocationDTO.getPhones();
			if (phoneDTOs == null || phoneDTOs.size() == 0) {
				phoneDTOs = new ArrayList<SOWPhoneDTO>();
				SOWPhoneDTO phone1 = new SOWPhoneDTO();
				phoneDTOs.add(phone1);
				SOWPhoneDTO phone2 = new SOWPhoneDTO();
				phoneDTOs.add(phone2);
				contLocationDTO.setPhones(phoneDTOs);
			} else if (phoneDTOs.size() == 1) {
				SOWPhoneDTO phone2 = new SOWPhoneDTO();
				phoneDTOs.add(phone2);
				contLocationDTO.setPhones(phoneDTOs);
			}
			
		}
	}
	
	/**@Description : Method will set parts supplied by in the DTO
	 * 
	 */
	private void setPartsSuppliedBy() {
		SOWScopeOfWorkTabDTO sowScopeOfWork =(SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
		partsTabDTO = (SOWPartsTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PARTS_TAB);
		if(null!= sowScopeOfWork && null!=sowScopeOfWork.getSkus() 
				&& !sowScopeOfWork.getSkus().isEmpty() && null!= sowScopeOfWork.getSkus().get(0)){
			    Integer partsSuppliedBy = sowScopeOfWork.getSkus().get(0).getPartsSuppliedBy();
			    partsTabDTO.setPartsSuppliedBy(partsSuppliedBy.toString());
		}
	}

	private void setupShippingStatusButtons(SOWPartDTO partDTO) {
		partDTO.setEnablePartShipStatus(isEnableTracking(
				partDTO.getShippingCarrierId(), partDTO.getShippingTrackingNo()));
		partDTO.setEnablePartRetShipStatus(isEnableTracking(
				partDTO.getReturnCarrierId(), partDTO.getReturnTrackingNo()));
		partDTO.setEnableCorePartRetShipStatus(isEnableTracking(
				partDTO.getCoreReturnCarrierId(), partDTO.getCoreReturnTrackingNo()));
	}
	
	private Boolean isEnableTracking(Integer carrierId, String trackingNo){
		Boolean hasCarrierId = false;
		Boolean hasTrackingNo = false;
		//Check for Carrier ID
		if (carrierId!= null && (carrierId.equals(1) || carrierId.equals(2) || carrierId.equals(5))) {
			hasCarrierId = true;
		}
		//Check for Tracking Number
		if (StringUtils.isNotBlank(trackingNo)) {
			hasTrackingNo = true;
		}
		if (hasCarrierId && hasTrackingNo) {
			return true;
		}
		return false;
		
	}

	private void setupPanelsExpandablity(SOWPartDTO part) {
		if (part.isThereAnyMorePartInfo()) {
			part.setHasMorePartInfo(true);
		}
		if (part.isThereAnyPartPickupInfo()) {
			part.setHasPartPickUpInfo(true);
		}
		if (part.isThereAnyShippingInfo()) {
			part.setHasShippingInfo(true);
		}
		if (part.isThereAnyReturnShippingInfo()) {
			part.setHasPartReturnShippingInfo(true);
		}		
	}

	public SOWPartsAction(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
	}
	
	public String setDtoForTab() throws IOException, BusinessServiceException{
		String TabStatus="tabIcon ";
		TabNavigationDTO tabNav = _createNavPoint(
				OrderConstants.SOW_NEXT_ACTION, OrderConstants.SOW_PARTS_TAB,
				OrderConstants.SOW_EDIT_MODE, "SOW");
		SOWPartsTabDTO partsTabDTO = getModel();
		SOWSessionFacility sowSessionFacility = SOWSessionFacility.getInstance();
		sowSessionFacility.evaluateSOWBean(partsTabDTO, tabNav);
		
		if (partsTabDTO.getPartsSuppliedBy().equalsIgnoreCase("3") || partsTabDTO.getPartsSuppliedBy().equalsIgnoreCase("2")){
			TabStatus="tabIcon complete";
		}
		else{
			partsTabDTO.validate();
		}
		if (partsTabDTO.getErrors().size() > 0){
			TabStatus="tabIcon error";
		}
		else if (partsTabDTO.getWarnings().size() > 0){
			TabStatus="tabIcon incomplete";
		}
		else{
			TabStatus="tabIcon complete";
		}
		AjaxResultsDTO actionResults = new AjaxResultsDTO();
		actionResults.setActionState(1);
		actionResults.setResultMessage(SUCCESS);
		actionResults.setAddtionalInfo1(TabStatus);
		
		// Response output
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		String responseStr = actionResults.toXml();
		logger.info(responseStr);
		response.getWriter().write(responseStr);

		return NONE;
	}

	public String createAndRoute() throws Exception {
		return null;
	}

	public String editEntryPoint() throws Exception {
		return null;
	}

	public String next() throws Exception {

		getRequest().setAttribute("previous","tab3");
		getRequest().setAttribute("next","tab4");
		TabNavigationDTO tabNav = _createNavPoint(
				OrderConstants.SOW_NEXT_ACTION, OrderConstants.SOW_PARTS_TAB,
				OrderConstants.SOW_EDIT_MODE, "SOW");

		SOWPartsTabDTO partsTabDTO = getModel();

		SOWSessionFacility sowSessionFacility = SOWSessionFacility
				.getInstance();
		//Validate if the state and zip code match
		validateZipCode(partsTabDTO);
		//Validate if partsDescription is present
		//validatePartsDesc(partsTabDTO);
		//nullOutParts();
		
		if(!partsTabDTO.getPartsSuppliedBy().equals(OrderConstants.SOW_SOW_BUYER_PROVIDES_PART)){
			nullOutParts();
		}

		sowSessionFacility.evaluateSOWBean(partsTabDTO, tabNav);

		String goingTotab = sowSessionFacility.getGoingToTab();
		this.setDefaultTab(goingTotab);
		String temp = SOWSessionFacility.getInstance().getGoingToTab();
		if (temp == "Parts"){
			this.setNext("tab3");
		}
		return "gl_wizard_success";
	}
	
	
	
	private void nullOutParts(){

	SOWPricingTabDTO pricingTabDto = (SOWPricingTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);
	SOWPartsTabDTO partsTabDto = (SOWPartsTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PARTS_TAB);

	//SL-19820
	//ServiceOrderWizardBean bean = SOWSessionFacility.getInstance().getSOWBean();
	ServiceOrderWizardBean bean = SOWSessionFacility.getInstance().getSOWBean((String)getAttribute(SO_ID));
	SOWTabStateVO vo;
	vo = (SOWTabStateVO) bean.getTabStateDTOs().get(
			OrderConstants.SOW_PARTS_TAB);
	
	if(pricingTabDto !=null && partsTabDto != null && partsTabDto !=null && partsTabDto.getPartsSuppliedBy()!= null && !partsTabDto.getPartsSuppliedBy().equals("1")){
		partsTabDto.setParts(null);
		if(vo!=null){
			vo.setTabErrorState("");
			vo.setTabWarningState("");
			vo.setTabShowIcon(true);
		}
	}else{
		if(vo!=null){
			vo.setTabShowIcon(true);
		}
	}
}

	public String previous() throws Exception {
		getRequest().setAttribute("previous","tab3");
		TabNavigationDTO tabNav = _createNavPoint(
				OrderConstants.SOW_PREVIOUS_ACTION,
				OrderConstants.SOW_PARTS_TAB, OrderConstants.SOW_EDIT_MODE,
				"SOW");

		SOWPartsTabDTO partsTabDTO = getModel();

//		 Validate if the state and zip code match
		validateZipCode(partsTabDTO);	
		
		SOWSessionFacility sowSessionFacility = SOWSessionFacility
				.getInstance();
		sowSessionFacility.evaluateSOWBean(partsTabDTO, tabNav);

		String goingTotab = sowSessionFacility.getGoingToTab();
		this.setDefaultTab(goingTotab);
		String temp = SOWSessionFacility.getInstance().getGoingToTab();
		if (temp == "Parts"){
			this.setNext("tab3");
		}
		return "gl_wizard_success";
	}

	public String saveAsDraft() throws Exception {
		String returnValue = null;
		TabNavigationDTO tabNav = _createNavPoint(
				OrderConstants.SOW_SAVE_AS_DRAFT_ACTION,
				OrderConstants.SOW_PARTS_TAB, OrderConstants.SOW_EDIT_MODE,
				"SOW");

		partsTabDTO = getModel();

//		 Validate if the state and zip code match 
		validateZipCode(partsTabDTO);
		//SL-20527 : Setting SpendLimt labor and permit price
		setSpendLimitLabor();		
		soPricePopulation();
		SOWSessionFacility.getInstance().evaluateAndSaveSOWBean(partsTabDTO,
				tabNav, isoWizardPersistDelegate, get_commonCriteria(), orderGroupDelegate);

		String str = SOWSessionFacility.getInstance().getGoingToTab();
		if (str!=null && str.equalsIgnoreCase(OrderConstants.SOW_EXIT_SAVE_AS_DRAFT))
		{
			//SL-19820
			//String currentSO = (String)getSession().getAttribute( OrderConstants.SO_ID);
			String soId = (String) getAttribute( OrderConstants.SO_ID);
			//SL-21355 : Saving logo from model to the service order.
			setBrandingLogo();
			invalidateAndReturn(fetchDelegate);
			Map sessionMap = ActionContext.getContext().getSession();
			if(new SOClaimedFacility().isWorkflowTheStartingPoint(sessionMap, soId))
			{
			  returnValue = OrderConstants.WORKFLOW_STARTINGPOINT;
			}
			else
			{
			  returnValue = OrderConstants.SOW_STARTPOINT_SOM;
			}
		}
		else
		{   
			this.setDefaultTab(str);
			returnValue = GOTO_COMMON_WIZARD_CONTROLLER;
		}
		return returnValue;
		// this.setDefaultTab(SOWSessionFacility.getInstance().getGoingToTab());

		// return GOTO_COMMON_WIZARD_CONTROLLER;
	}
	
	private void validatePartsDesc(SOWPartsTabDTO partsTabDTO){
		for(int i=0;i<partsTabDTO.getParts().size();i++){
			SOWPartDTO partDto = partsTabDTO.getParts().get(i);
			if(StringUtils.isBlank(partDto.getPartDesc()))
			{
				partsTabDTO.getErrors().add(new SOWError("Part " + (i+1) + " --> " +"Part Description -->", 
						partsTabDTO.getTheResourceBundle().getString("parts.description.blank"), OrderConstants.SOW_TAB_ERROR));
			}
		}
	}

	private void validateZipCode(SOWPartsTabDTO partsTabDTO){
		partsTabDTO.setErrors(new ArrayList<IError>());
		if(partsTabDTO != null && partsTabDTO.getParts() != null){
			for(int i=0;i<partsTabDTO.getParts().size();i++){
				SOWPartDTO partDto = partsTabDTO.getParts().get(i);		
				SOWContactLocationDTO contLocDto = partDto.getPickupContactLocation();
				if(partDto != null && contLocDto != null){
					int zipCheck = LocationUtils.checkIfZipAndStateValid(contLocDto.getZip(),contLocDto.getState());
					switch (zipCheck) {
						case Constants.LocationConstants.ZIP_NOT_VALID:
							partsTabDTO.getErrors().add(new SOWError("Part " + (i+1) + " --> " +"Zip", 
									partsTabDTO.getTheResourceBundle().getString("Zip_Not_Valid"), OrderConstants.SOW_TAB_ERROR));
							break;
						case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
							partsTabDTO.getErrors().add(new SOWError("Part " + (i+1) + " --> " +"Zip", 
									partsTabDTO.getTheResourceBundle().getString("Zip_State_No_Match"), OrderConstants.SOW_TAB_ERROR));
							break;
					}
					
				}
			}
		}
	}
	
	private void createFirstPart() {

		SOWPartDTO part = new SOWPartDTO();
		part.setProviderBringparts(true);
		SOWContactLocationDTO location = new SOWContactLocationDTO();
		List<SOWPhoneDTO> phones = new ArrayList();
		SOWPhoneDTO phone1 = new SOWPhoneDTO();
		phones.add(phone1);
		SOWPhoneDTO phone2 = new SOWPhoneDTO();
		phones.add(phone2);
		location.setPhones(phones);
		part.setPickupContactLocation(location);
		partsTabDTO = (SOWPartsTabDTO) SOWSessionFacility.getInstance()
				.getTabDTO(OrderConstants.SOW_PARTS_TAB);
		partsTabDTO.addPart(part);
	}

	public String deletePart() throws Exception {
		TabNavigationDTO tabNav = _createNavPoint(null,
				OrderConstants.SOW_PARTS_TAB, OrderConstants.SOW_EDIT_MODE,
				"SOW");

		partsTabDTO = getModel();

		List<SOWPartDTO> parts = partsTabDTO.getParts();

		if(parts.size() > 1)
			parts.remove(partsTabDTO.getDeletePartIndex());
		partsTabDTO.setParts(parts);

		partsTabDTO.setTheResourceBundle(getTexts("servicelive"));
		SOWSessionFacility.getInstance().evaluateSOWBean(partsTabDTO, tabNav);
		this.setDefaultTab(OrderConstants.SOW_PARTS_TAB);
		partsTabDTO.setAddNewPart(false);
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}
	
	public String addNewPart() throws Exception {

		TabNavigationDTO tabNav = _createNavPoint(null,
				OrderConstants.SOW_PARTS_TAB, OrderConstants.SOW_EDIT_MODE,
				"SOW");

		partsTabDTO = getModel();

		partsTabDTO.setTheResourceBundle(getTexts("servicelive"));

		SOWSessionFacility.getInstance().evaluateSOWBean(partsTabDTO, tabNav);
		ArrayList<SOWPartDTO> parts = new ArrayList<SOWPartDTO>();
		parts = (ArrayList<SOWPartDTO>) partsTabDTO.getParts();

		/* create empty part with it's chold objects and add in the list */
		SOWPartDTO part = new SOWPartDTO();
		part.setQuantity("1");
		part.setProviderBringparts(true);
		SOWContactLocationDTO location = new SOWContactLocationDTO();
		List<SOWPhoneDTO> phones = new ArrayList();
		SOWPhoneDTO phone1 = new SOWPhoneDTO();
		phones.add(phone1);
		SOWPhoneDTO phone2 = new SOWPhoneDTO();
		phones.add(phone2);
		location.setPhones(phones);
		part.setPickupContactLocation(location);

		parts.add(part);
		SOWPartsTabDTO currentDTO = (SOWPartsTabDTO) SOWSessionFacility
				.getInstance().getTabDTO(OrderConstants.SOW_PARTS_TAB);
		partsTabDTO.setParts(parts);
		partsTabDTO.setAddNewPart(true);
		this.setDefaultTab(OrderConstants.SOW_PARTS_TAB);

		String addNewPartWarningMsg = partsTabDTO.getTheResourceBundle()!= null ? 
				                                       partsTabDTO.getTheResourceBundle().getString("Add_New_Part_Msg"):"";
		//SL-19820
		//getSession().setAttribute("addNewPartWarningMsg", addNewPartWarningMsg);
		getSession().setAttribute("addNewPartWarningMsg_"+(String)getAttribute(SO_ID), addNewPartWarningMsg);
		setAttribute("addNewPartWarningMsg", addNewPartWarningMsg);

		return GOTO_COMMON_WIZARD_CONTROLLER;

	}
	private void setPhoneNumbers(){
		if (partsTabDTO.getParts() != null) {
			SOWPhoneDTO p1 = new SOWPhoneDTO();
			SOWPhoneDTO p2 = new SOWPhoneDTO();
			for (int i = 0; i < partsTabDTO.getParts().size(); i++) {
				if(partsTabDTO.getParts().get(i)
						.getPickupContactLocation()!=null){
				List<SOWPhoneDTO> checkPhones = partsTabDTO.getParts().get(i)
						.getPickupContactLocation().getPhones();
				if (checkPhones.size() == 0) {
			
					partsTabDTO.getParts().get(i).getPickupContactLocation().getPhones().add(p1);
					partsTabDTO.getParts().get(i).getPickupContactLocation().getPhones().add(p2);
				}else
				if (checkPhones.size() == 1){
					
					if(checkPhones.get(0).getPhoneType()==1){
					partsTabDTO.getParts().get(i).getPickupContactLocation().getPhones().add(p2);
					}else
						if(checkPhones.get(0).getPhoneType()==2){
							partsTabDTO.getParts().get(i).getPickupContactLocation().getPhones().add(0,p1);
					}
				}
				}else{
					SOWContactLocationDTO tempContLoc = new SOWContactLocationDTO();
					tempContLoc.getPhones().add(p1);
					tempContLoc.getPhones().add(p2);
					partsTabDTO.getParts().get(i).setPickupContactLocation(tempContLoc);
					
				}
			}

		}
	}
	private void populateLookup() {

		if (getSession().getServletContext().getAttribute("phoneTypes") == null) {
			try {
				ArrayList<LookupVO> phoneTypes = getFetchDelegate()
						.getPhoneTypes();
				getSession().getServletContext().setAttribute("phoneTypes",
						phoneTypes);

			} catch (DataServiceException e) {
				logger.info("Caught Exception and ignoring",e);
			}
		}
		if (getSession().getServletContext().getAttribute("shippingCarrier") == null) {
			try {
				ArrayList<LookupVO> shippingCarrier = getFetchDelegate()
						.getShippingCarrier();
				getSession().getServletContext().setAttribute(
						"shippingCarrier", shippingCarrier);

			} catch (DataServiceException e) {
				logger.info("Caught Exception and ignoring",e);
			}
		}
		if (getSession().getServletContext().getAttribute("partStatus") == null) {
			try {
				ArrayList<LookupVO> partStatus = getFetchDelegate()
						.getPartStatus();
				getSession().getServletContext().setAttribute(
						"partStatus", partStatus);

			} catch (DataServiceException e) {
				logger.info("Caught Exception and ignoring",e);
			}
		}
	}
    
	/**@Description : Update the logo document against the service order
	 * @throws Exception
	 */
	public void setBrandingLogo() throws Exception {
		Integer buyerDocumentLogo =null;
		SOWScopeOfWorkTabDTO sowScopeOfWorkDTO = (SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
		if(null!= sowScopeOfWorkDTO && null!=sowScopeOfWorkDTO.getSkus() 
				&& !sowScopeOfWorkDTO.getSkus().isEmpty() && null!= sowScopeOfWorkDTO.getSkus().get(0)){
			    buyerDocumentLogo = sowScopeOfWorkDTO.getSkus().get(0).getBuyerDocumentLogo();
		}
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		Integer existingLogoId = fetchDelegate.getLogoDocumentId(soId);
		if(null== existingLogoId && null!= buyerDocumentLogo){
			fetchDelegate.applyBrandingLogo(soId, buyerDocumentLogo);
		}
	}
	
	public SOWPartsTabDTO getModel() {
		partsTabDTO = (SOWPartsTabDTO) SOWSessionFacility.getInstance()
				.getTabDTO(OrderConstants.SOW_PARTS_TAB);

		return partsTabDTO;

	}

	public void setModel(SOWPartsTabDTO partsTabDTO) {
		this.partsTabDTO = partsTabDTO;
	}
	
	public String getPrevious() {
		return previous;
	}


	public void setPrevious(String previous) {
		this.previous = previous;
	}


	public String getNext() {
		return next;
	}


	public void setNext(String next) {
		this.next = next;
	}

}
