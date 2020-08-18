
package com.newco.marketplace.web.action.wizard.additionalinfo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuBuyerRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLWizardBaseAction;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.SOWAdditionalInfoTabDTO;
import com.newco.marketplace.web.dto.SOWAltBuyerContactDTO;
import com.newco.marketplace.web.dto.SOWBrandingInfoDTO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.SOWCustomRefDTO;
import com.newco.marketplace.web.dto.SOWPhoneDTO;
import com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO;
import com.newco.marketplace.web.dto.TabNavigationDTO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.utils.ObjectMapperWizard;
import com.newco.marketplace.web.utils.SOClaimedFacility;
import com.newco.marketplace.web.validator.sow.LookupHash;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.75 $ $Author: akashya $ $Date: 2008/05/21 23:33:03 $
 * $Revision: 1.76 $ $Author: agupt02 $ $Date: 2008/06/24 23:33:03 $ 
 *
 */
/**
 * @author Gireesh_Thadayil
 *
 */
public class SOWAdditionalInfoAction extends SLWizardBaseAction implements
		Preparable, ModelDriven<SOWAdditionalInfoTabDTO> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7074508530235555713L;

	private SOWAdditionalInfoTabDTO sowAdditionalInfoTabDTO = new SOWAdditionalInfoTabDTO();
	
	private ArrayList <LookupVO> phoneTypes; // phone dropdown
	


	private static final Logger logger = Logger
			.getLogger(SOWAdditionalInfoAction.class);

	private ArrayList<LuBuyerRefVO> buyerRef = new ArrayList<LuBuyerRefVO>();
	
	private String previous;
	private String next;
	
	//SL-19820
	String soID;

	public SOWAdditionalInfoAction(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
		setFetchDelegate(fetchDelegate);

	}

	public void prepare() throws Exception {
		//SL-19820
		//getRequest().getSession().setAttribute("previousTab","tab2");
		setAttribute("previousTab","tab2");
		String soId = getParameter(OrderConstants.SO_ID);
        setAttribute(OrderConstants.SO_ID,soId);
        this.soID = soId;
        
        String groupId = (String) getSession().getAttribute(OrderConstants.GROUP_ID+"_"+soId);
        setAttribute(OrderConstants.GROUP_ID,groupId);
        
		createCommonServiceOrderCriteria();
		loadBuyerRefLookUP(getModel().getCustomRefs());
		initBuyerAndBuyerSupportContactInfo();
		
		initPrimaryAndAlternateLocationContactInfo();
		
		//SL- 19820		
		String entryTab = (String)getSession().getAttribute("entryTab_"+soId);
		setAttribute("entryTab", entryTab);
		
		Integer status=(Integer) getSession().getAttribute(OrderConstants.SOW_SERVICE_ORDER_STATUS_SOID+"_"+soId);
        setCurrentSOStatusCodeInRequest(status);
        //SL-20527 Setting Spend Limit labor with out considering deleted task
      	setBuyerDocumentLogo();
		
	}
	
	private void initBuyerAndBuyerSupportContactInfo() throws Exception{		
		if(null == sowAdditionalInfoTabDTO.getAltBuyerContacts()){
		//Get the Contact Details of the Buyer Resource (who created the Service Order)
			//SoId is not present in session for grouped orders. So getting from the Service Order object.
			//String soId = ((ServiceOrderWizardBean)getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY)).getSoId();
			String soId = (String) getAttribute(OrderConstants.SO_ID);
			ContactLocationVO cVO = fetchDelegate.getBuyerResContLocDetailsForSO(soId);
			if(null == cVO){
				Integer contactId = get_commonCriteria().getSecurityContext().getBuyerAdminContactId();
				if(null == contactId){
					contactId = get_commonCriteria().getSecurityContext().getRoles().getContactId();
				}
				cVO = fetchDelegate.getBuyerResContLocDetailsForContactId(contactId);
			}else if (null != cVO){
				// Check if location id is present
				LocationVO locationVo =  cVO.getBuyerPrimaryLocation();
				if(null == locationVo){
					// Get the location of the admin and set it to the ContactLocationVO.
					Integer buyerAdminId = get_commonCriteria().getSecurityContext().getCompanyId();
					ContactLocationVO adminContact = fetchDelegate.getBuyerContLocDetails(buyerAdminId);
					if(null!= adminContact){
						LocationVO adminLocation = adminContact.getBuyerPrimaryLocation();
						if(null!=adminLocation){
							cVO.setBuyerPrimaryLocation(adminLocation);
						}
					}						
				}				
			}
			
		
		SOWContactLocationDTO altBuyerSupportLocationContact = ObjectMapperWizard.convertVOtoSOWContactLocationDTO(cVO);
		List<SOWAltBuyerContactDTO> altBuyerContacts = ObjectMapperWizard.convertVOtoAltBuyerContacts(cVO);
		
		//SL-19820
		sowAdditionalInfoTabDTO = (SOWAdditionalInfoTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
		sowAdditionalInfoTabDTO.setAltBuyerContacts(altBuyerContacts);
		sowAdditionalInfoTabDTO.setAltBuyerSupportLocationContact(altBuyerSupportLocationContact);		
		sowAdditionalInfoTabDTO.setCompanyId(get_commonCriteria().getCompanyId());
	}
	}
	

	// AN ENTRY POINT, no execute()
	public String createEntryPoint() throws Exception {
		//SL-19820
		//getRequest().getSession().setAttribute("prevTab","tab2");
		setAttribute("prevTab","tab2");
		// initPrimaryAndAlternateLocationContactInfo();
		return SUCCESS;
	}
	
	public String setDtoForTab() throws IOException{
		String TabStatus="tabIcon ";
		SOWAdditionalInfoTabDTO sOWAdditionalInfoTabDTO = getModel();


		TabNavigationDTO tabNav = _createNavPoint(
				OrderConstants.SOW_PREVIOUS_ACTION,
				OrderConstants.SOW_ADDITIONAL_INFO_TAB,
				OrderConstants.SOW_EDIT_MODE, "SOM");
		//setCustomRef();
		setCustomReferenceValues(sOWAdditionalInfoTabDTO.getBuyerRefs());
		setBuyerRef(buyerRef);
		try {
			SOWSessionFacility.getInstance().evaluateSOWBean(sOWAdditionalInfoTabDTO, tabNav);
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
		
		
		sOWAdditionalInfoTabDTO.validate();
		if (sOWAdditionalInfoTabDTO.getErrors().size() > 0){
			TabStatus="tabIcon error";
		}
		else if (sOWAdditionalInfoTabDTO.getWarnings().size() > 0){
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

	public String viewBrandingLogo() {
		try {
			Integer documentId = getModel().getLogodocumentId();
			SOWBrandingInfoDTO brandingInfoDTO = fetchDelegate
					.retrieveBuyerDocumentByDocumentId(new Integer(
							documentId));
			StringTokenizer fileName = new StringTokenizer(brandingInfoDTO
					.getLogoId(), ".");
			String extension = "";
			while (fileName.hasMoreTokens()) {
				extension = fileName.nextToken();
			}
			if (extension.equals("gif")) {
				getResponse().setContentType("image/gif");
			}else if(extension.equals("jpeg")|| extension.equals("jpg")){
				getResponse().setContentType("image/jpeg");
	    	}else if(extension.equals("png")){
	    		getResponse().setContentType("image/png");
	    	}else {
				getResponse().setContentType("text/html");
			}
			String header = "attachment;filename=\""
					+ brandingInfoDTO.getLogoId() + "\"";
			getResponse().setHeader("Expires", "0");
			getResponse().setHeader("Content-Disposition", header);
			InputStream in = new ByteArrayInputStream(brandingInfoDTO
					.getBlobBytes());// FiletoByteArray.getBytesFromFile(logoFile);
			ServletOutputStream outs = getResponse().getOutputStream();
			getResponse().setHeader("Cache-Control",
			"must-revalidate, post-check=0, pre-check=0");
			getResponse().setHeader("Pragma", "public");
			int bit = 256;
			while ((bit) >= 0) {
				bit = in.read();
				outs.write(bit);
			}
			outs.flush();
			outs.close();
			in.close();
			} catch (Exception e) {
		}
		setDefaultTab( LookupHash.getMyActionName( OrderConstants.SOW_ADDITIONAL_INFO_TAB) );
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	public String applyBrandingLogo() throws Exception {
		SOWSessionFacility.getInstance();
		Integer documentId = getModel().getLogodocumentId();
		//SL-19820
		//Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		//String soId = (String)sessionMap.get(OrderConstants.SO_ID);
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		if(null!=soId){
			fetchDelegate.applyBrandingLogo(soId, documentId);
		}
		//This parameter is added to prevent overridding of logo applied by the user
		setDefaultTab( LookupHash.getMyActionName( OrderConstants.SOW_ADDITIONAL_INFO_TAB) );
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}
	
	/**@Description : This method will update logo from model to the service order on click of naxt or save as Draft
	 * @throws Exception
	 */
	public void setBrandingLogo() throws Exception {
		Integer documentId = getModel().getLogodocumentId();
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		Integer existingLogoId = fetchDelegate.getLogoDocumentId(soId);
		if(null== existingLogoId && null!= documentId){
			fetchDelegate.applyBrandingLogo(soId, documentId);
		}
	}

	private void initPrimaryAndAlternateLocationContactInfo(){
		if (sowAdditionalInfoTabDTO == null)
			return;

		// Location Contact Info from SOWScopeOfWorkTabDTO
		//SL-19820
		SOWScopeOfWorkTabDTO sowDTO = (SOWScopeOfWorkTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
		
		sowAdditionalInfoTabDTO = (SOWAdditionalInfoTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_ADDITIONAL_INFO_TAB);

		setAttribute("numPhones", 0); // Set as default		
		if (sowAdditionalInfoTabDTO != null)
		{
			if (sowDTO.getServiceLocationContact() != null)
			{
				sowAdditionalInfoTabDTO.setServiceLocationContact(sowDTO.getServiceLocationContact());
				if (sowDTO.getServiceLocationContact().getPhones() != null)
					setAttribute("numPhones", sowDTO.getServiceLocationContact().getPhones().size());
			}
		}
		
		if(sowAdditionalInfoTabDTO.getAlternateLocationContact().getPhones() == null || 
					sowAdditionalInfoTabDTO.getAlternateLocationContact().getPhones().size() == 0){
			createPhoneNumbers();
		}
				
		// Phone Dropdown lists
		try
		{
			phoneTypes = fetchDelegate.getPhoneTypes();
		}
		catch(Exception e)
		{
			logger.info("Caught Exception and ignoring",e);
		}
	}

	public String createAndRoute() throws Exception {
		return null;
	}

	public String editEntryPoint() throws Exception {
		return null;
	}

	public String next() throws Exception {
		
		// 1. NAME OF THE ACTION (NEXT OR PREVIOUS)
		// 2. WHAT TAB OR YOU ON sow ETC..
		// 3. WHAT MODE ARE YOU IN CREATE OR EDIT
		// 4. WHERE DID YOU START FROM IN THE APPLICATION
		
		getRequest().setAttribute("previous","tab2");
		getRequest().setAttribute("next","tab3");
		TabNavigationDTO tabNav = _createNavPoint(
										OrderConstants.SOW_NEXT_ACTION,
										OrderConstants.SOW_ADDITIONAL_INFO_TAB,
										OrderConstants.SOW_EDIT_MODE, "SOM");
		
		SOWAdditionalInfoTabDTO sOWAdditionalInfoTabDTO = getModel();
		setCustomReferenceValues(getModel().getBuyerRefs());
		SOWSessionFacility.getInstance().evaluateSOWBean(sOWAdditionalInfoTabDTO, tabNav);
		setDefaultTab(SOWSessionFacility.getInstance().getGoingToTab());
		
		
		
		setSelectedAltBuyerContact(sOWAdditionalInfoTabDTO);

//		 If no errors, Clear out the text field
		if((sowAdditionalInfoTabDTO.getErrors() != null && sowAdditionalInfoTabDTO.getErrors().size() == 0)	|| sowAdditionalInfoTabDTO.getErrors() == null)
		{
			sowAdditionalInfoTabDTO.setRefTypeValue("");
			sowAdditionalInfoTabDTO.setRefTypeId(-1);
			
		}

		String temp = SOWSessionFacility.getInstance().getGoingToTab();
		if (temp == "Additional Info"){
			this.setNext("tab2");
		}
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	
	private void setSelectedAltBuyerContact(SOWAdditionalInfoTabDTO sOWAdditionalInfoTabDTO)
	{
		if(sOWAdditionalInfoTabDTO.isAltBuyerSupportLocationContactFlg())
		{
			//Get the Contact Details of the Buyer Resource (who created the Service Order)
			ContactLocationVO cVO = fetchDelegate.getBuyerResContLocDetails(get_commonCriteria().getCompanyId(), 
					get_commonCriteria().getVendBuyerResId()); //passing buyer id and buyer resource id
			List<SOWAltBuyerContactDTO> altBuyerContacts = ObjectMapperWizard.convertVOtoAltBuyerContacts(cVO);
			Integer selectedId=null;
			if(sOWAdditionalInfoTabDTO.getSelectedAltBuyerContact() != null)
				selectedId =  sOWAdditionalInfoTabDTO.getSelectedAltBuyerContact().getContactId();
			
			if(selectedId != null)
			{
				for(SOWAltBuyerContactDTO contact : altBuyerContacts)
				{
					if(contact.getContactId().intValue() == selectedId.intValue())
					{
						sOWAdditionalInfoTabDTO.setSelectedAltBuyerContact(contact);
						break;
					}
				}
			}
		}
		
	}
	
	public String previous() throws Exception {
		
		// 1. NAME OF THE ACTION (NEXT OR PREVIOUS)
		// 2. WHAT TAB OR YOU ON sow ETC..
		// 3. WHAT MODE ARE YOU IN CREATE OR EDIT
		// 4. WHERE DID YOU START FROM IN THE APPLICATION
		getRequest().setAttribute("previous","tab2");
		TabNavigationDTO tabNav = _createNavPoint(
										OrderConstants.SOW_PREVIOUS_ACTION,
										OrderConstants.SOW_ADDITIONAL_INFO_TAB,
										OrderConstants.SOW_EDIT_MODE, "SOM");
		SOWAdditionalInfoTabDTO sOWAdditionalInfoTabDTO = getModel();
		setCustomReferenceValues(sOWAdditionalInfoTabDTO.getBuyerRefs());

		SOWSessionFacility.getInstance().evaluateSOWBean(sOWAdditionalInfoTabDTO, tabNav);
		setDefaultTab(SOWSessionFacility.getInstance().getGoingToTab());
//		 If no errors, Clear out the text field
		if((sowAdditionalInfoTabDTO.getErrors() != null 
				&& sowAdditionalInfoTabDTO.getErrors().size() == 0)
				|| sowAdditionalInfoTabDTO.getErrors() == null){
			sowAdditionalInfoTabDTO.setRefTypeValue("");
			sowAdditionalInfoTabDTO.setRefTypeId(-1);
		}
		String temp = SOWSessionFacility.getInstance().getGoingToTab();
		if (temp == "Additional Info"){
			this.setNext("tab2");
		}
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	public String saveAsDraft() throws Exception {
		// 1. NAME OF THE ACTION (NEXT OR PREVIOUS)
		// 2. WHAT TAB OR YOU ON sow ETC..
		// 3. WHAT MODE ARE YOU IN CREATE OR EDIT
		// 4. WHERE DID YOU START FROM IN THE APPLICATION
		String returnValue = null;
		TabNavigationDTO tabNav = _createNavPoint(
						OrderConstants.SOW_SAVE_AS_DRAFT_ACTION,
						OrderConstants.SOW_ADDITIONAL_INFO_TAB,
						OrderConstants.SOW_EDIT_MODE, "SOM");

		SOWAdditionalInfoTabDTO dto = getModel();
		setCustomReferenceValues(dto.getBuyerRefs());		
		if (dto == null) {
			dto = new SOWAdditionalInfoTabDTO();
		}
		sowAdditionalInfoTabDTO.setRequiredRefTypeFieldsValidation(true);
		SOWContactLocationDTO cdto = dto.getServiceLocationContact();
		if(dto.getAlternateLocationContact()!=null)
			dto.setAltServiceLocationContactFlg(true);
       
		soPricePopulation();
		//SL-20527 : Setting SpendLimt labor and permit price
		setSpendLimitLabor();
		
		SOWSessionFacility.getInstance().evaluateAndSaveSOWBean(
					dto, tabNav, isoWizardPersistDelegate, get_commonCriteria(), orderGroupDelegate);
		
		String str = SOWSessionFacility.getInstance().getGoingToTab();
		if (str!=null && str.equalsIgnoreCase(OrderConstants.SOW_EXIT_SAVE_AS_DRAFT))
		{
			//SL-19820
			//String currentSO = (String)getSession().getAttribute( OrderConstants.SO_ID);
			//SL-21355 - Code change to save the buyer logo associated with the primary SKU 
			setBrandingLogo();
			invalidateAndReturn(fetchDelegate);
			Map sessionMap = ActionContext.getContext().getSession();
			//SL-19820
			//if(new SOClaimedFacility().isWorkflowTheStartingPoint(sessionMap))
			if(new SOClaimedFacility().isWorkflowTheStartingPoint(sessionMap, soID))
			{
				sessionMap.remove(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR+"_"+soId);
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
//		 If no errors, Clear out the text field
		if((sowAdditionalInfoTabDTO.getErrors() != null 
				&& sowAdditionalInfoTabDTO.getErrors().size() == 0)
				|| sowAdditionalInfoTabDTO.getErrors() == null){
			sowAdditionalInfoTabDTO.setRefTypeValue("");
			sowAdditionalInfoTabDTO.setRefTypeId(-1);
		}
		return returnValue;
	}

	public String removeCustomRef() {

		TabNavigationDTO tabNav = _createNavPoint(
				OrderConstants.SOW_POST_SO_ACTION,
				OrderConstants.SOW_ADDITIONAL_INFO_TAB,
				OrderConstants.SOW_CREATE_MODE, "SOM");

		SOWAdditionalInfoTabDTO SOWAddInfoTabDTO = new SOWAdditionalInfoTabDTO();

		SOWAddInfoTabDTO = getModel();

		SOWCustomRefDTO customRefDTO = new SOWCustomRefDTO();
		Integer refTypeId = (Integer.parseInt(getParameter("refTypeId")));
		customRefDTO.setRefTypeId(refTypeId);
		customRefDTO.setRefValue(getParameter("refValue"));

		for (int i = 0; i < SOWAddInfoTabDTO.getCustomRefs().size(); i++) {

			if (refTypeId.equals(SOWAddInfoTabDTO.getCustomRefs().get(i)
					.getRefTypeId())) {
				SOWAddInfoTabDTO.getCustomRefs().remove(i);
			}
		}

		try {
			SOWSessionFacility sowSessionFacility = SOWSessionFacility
					.getInstance();
			sowSessionFacility.evaluateSOWBean(SOWAddInfoTabDTO, tabNav);
			String goingTotab = sowSessionFacility.getGoingToTab();
			this.setDefaultTab(goingTotab);
		} catch (Exception e) {
			logger
					.error("Error in SOWAdditionalInformationAction.RemoveCustomRef"
							+ e);
		}
		// If no errors, Clear out the text field
		if((sowAdditionalInfoTabDTO.getErrors() != null 
				&& sowAdditionalInfoTabDTO.getErrors().size() == 0)
				|| sowAdditionalInfoTabDTO.getErrors() == null){
			sowAdditionalInfoTabDTO.setRefTypeValue("");
			sowAdditionalInfoTabDTO.setRefTypeId(-1);
		}
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	private void setCustomReferenceValues(List<LuBuyerRefVO> buyerRefList){
		List<SOWCustomRefDTO> customRefVals = new ArrayList<SOWCustomRefDTO>();
		for (LuBuyerRefVO buyRef : buyerRefList) {
			
			if (StringUtils.isNotBlank(buyRef.getReferenceValue())){
				SOWCustomRefDTO newCustomRef = new SOWCustomRefDTO();
				newCustomRef.setRefTypeId(buyRef.getRefTypeId());		
				newCustomRef.setRefValue(buyRef.getReferenceValue());
				newCustomRef.setRefType(buyRef.getRefType());
				customRefVals.add(newCustomRef);
			}
		}
		sowAdditionalInfoTabDTO.setCustomRefs(customRefVals);
	}


	private void loadBuyerRefLookUP(List<SOWCustomRefDTO> customRefVals) {
		sowAdditionalInfoTabDTO = (SOWAdditionalInfoTabDTO) SOWSessionFacility 
			.getInstance() .getTabDTO(OrderConstants.SOW_ADDITIONAL_INFO_TAB); 
		try {
			String buyerId = get_commonCriteria().getSecurityContext().getCompanyId()+ "";
			buyerRef = getFetchDelegate().getBuyerRef(buyerId);
			for (LuBuyerRefVO buyRef : buyerRef) {
				for (SOWCustomRefDTO customRefDTO : customRefVals) {
					if (buyRef.getRefTypeId().equals(customRefDTO.getRefTypeId()) ){
						
						buyRef.setReferenceValue(customRefDTO.getRefValue());
					}
					
				}
			}
		     sowAdditionalInfoTabDTO.setBuyerRefs(buyerRef);
		     
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in SOWAdditionalInformationAction.loadBuyerRefLookUP while getting lookup values" + e);
		}
	}

	public SOWAdditionalInfoTabDTO getModel() {

		sowAdditionalInfoTabDTO = (SOWAdditionalInfoTabDTO) SOWSessionFacility
				.getInstance()
				.getTabDTO(OrderConstants.SOW_ADDITIONAL_INFO_TAB);

		if (sowAdditionalInfoTabDTO != null) {
			try {
				sowAdditionalInfoTabDTO.setBrandingInfoList(fetchDelegate
						.retrieveBuyerDocumentsByBuyerIdAndCategory(
								get_commonCriteria().getCompanyId(),
								new Integer(
										Constants.DocumentTypes.CATEGORY.LOGO),
								get_commonCriteria().getRoleId(),
								get_commonCriteria().getCompanyId()));
				// initPrimaryAndAlternateLocationContactInfo();
			} catch (BusinessServiceException e) {
				logger.info("Caught Exception and ignoring",e);
			}
			return sowAdditionalInfoTabDTO;
		} else {
			try {
				sowAdditionalInfoTabDTO.setBrandingInfoList(fetchDelegate
						.retrieveBuyerDocumentsByBuyerIdAndCategory(
								get_commonCriteria().getCompanyId(),
								new Integer(
										Constants.DocumentTypes.CATEGORY.LOGO),
								get_commonCriteria().getRoleId(),
								get_commonCriteria().getCompanyId()));
			} catch (BusinessServiceException e) {
				logger.info("Caught Exception and ignoring",e);
			}
		}
		
		return sowAdditionalInfoTabDTO;
	}

	/**@Description : Method will set buyer logo from primary sku in request as well as to the model
	 * 
	 */
	private void setBuyerDocumentLogo() {
		SOWScopeOfWorkTabDTO sowScopeOfWork =(SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
		sowAdditionalInfoTabDTO = (SOWAdditionalInfoTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
		if(null!= sowScopeOfWork && null!=sowScopeOfWork.getSkus() 
				&& !sowScopeOfWork.getSkus().isEmpty() && null!= sowScopeOfWork.getSkus().get(0)){
			    Integer buyerDocumentLogo = sowScopeOfWork.getSkus().get(0).getBuyerDocumentLogo();
			    getRequest().setAttribute("logodocumentId", buyerDocumentLogo);
			    sowAdditionalInfoTabDTO.setLogodocumentId(buyerDocumentLogo);
			    
		}
	}

	public void setModel(SOWAdditionalInfoTabDTO dto) {

		this.sowAdditionalInfoTabDTO = dto;
	}

	public ArrayList<LuBuyerRefVO> getBuyerRef() {
		return buyerRef;
	}

	public void setBuyerRef(ArrayList<LuBuyerRefVO> buyerRef) {
		this.buyerRef = buyerRef;
	}

	public ISOWizardFetchDelegate getFetchDelegate() {
		return fetchDelegate;
	}

	public void setFetchDelegate(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
	}

	public SOWAdditionalInfoTabDTO getSowAdditionalInfoTabDTO() {
		return getModel();
	}

	public void setSowAdditionalInfoTabDTO(
			SOWAdditionalInfoTabDTO sowAdditionalInfoTabDTO) {
		this.sowAdditionalInfoTabDTO = sowAdditionalInfoTabDTO;
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

	public ArrayList<LookupVO> getPhoneTypes() {
		return phoneTypes;
	}

	public void setPhoneTypes(ArrayList<LookupVO> phoneTypes) {
		this.phoneTypes = phoneTypes;
	}

	private void createPhoneNumbers(){
			
			SOWAdditionalInfoTabDTO sowAdditionalInfoTabDTO = 	(SOWAdditionalInfoTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
			
			//The scopeOfWorkDTO2 will be in the session facility at this point since the calling program
			//sets the dto in the session facility prior to calling this method
			
			if(sowAdditionalInfoTabDTO.getAlternateLocationContact().getPhones().size() == 0 ){
	
					ArrayList <SOWPhoneDTO> phones = new ArrayList<SOWPhoneDTO>();
					for (int i = 1; i < 4; i++) {
							SOWPhoneDTO addPhoneType = new SOWPhoneDTO();
							//This will add PhoneTypes of 1 and 2 to the DTO
							//1--> Phone
							//2--> Alternate
							//3-->fax
							//LMA...This needs to be set so that the Phone,Alternate Phone and fax  fields
							//will show up on the jsp for creating a service order
							//If the user did not enter any information but saves a draft,
							//then I need to put in a check in the entry point for modification of a draft
							//to check what is populated...Figure out which phoneTypes are filled out 
							//(only check for phone types 1 or 2)...whichever one is missing then,
							//create a phoneType in the dto for that so that the Phone and Alterate fields
							//display.
	
							addPhoneType.setPhoneType(i);
							phones.add(addPhoneType);
					}
					sowAdditionalInfoTabDTO.getAlternateLocationContact().setPhones(phones);	
	
			}
		}

	public String getSoID() {
		return soID;
	}

	public void setSoID(String soID) {
		this.soID = soID;
	}

}



