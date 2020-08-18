package com.newco.marketplace.web.action.wizard.review;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.constants.Constants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LuBuyerRefVO;
import com.newco.marketplace.dto.vo.TermsAndConditionsVO;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSubStatusVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.web.action.base.SLWizardBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.IFinanceManagerDelegate;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.delegates.IOrderGroupDelegate;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOContactDTO;
import com.newco.marketplace.web.dto.SOPartsDTO;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.SOWAdditionalInfoTabDTO;
import com.newco.marketplace.web.dto.SOWAltBuyerContactDTO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.SOWCustomRefDTO;
import com.newco.marketplace.web.dto.SOWPartDTO;
import com.newco.marketplace.web.dto.SOWPartsTabDTO;
import com.newco.marketplace.web.dto.SOWPricingTabDTO;
import com.newco.marketplace.web.dto.SOWProviderDTO;
import com.newco.marketplace.web.dto.SOWProvidersTabDTO;
import com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO;
import com.newco.marketplace.web.dto.SOWSelBuyerRefDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderWizardBean;
import com.newco.marketplace.web.dto.TabNavigationDTO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.utils.ObjectMapperWizard;
import com.newco.marketplace.web.utils.SOClaimedFacility;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class SOWReviewAction extends SLWizardBaseAction implements Preparable, ModelDriven<ServiceOrderDTO>{

	private static final long serialVersionUID = -3837126560934484072L;
	private static final Logger logger = Logger.getLogger(SOWReviewAction.class.getName());
	
	IFinanceManagerDelegate financeManagerDelegate;
	IOrderGroupDelegate orderGroupDelegate;
	
	private ServiceOrderDTO reviewDTO = new ServiceOrderDTO();
	private ILookupDelegate lookupManager;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	private IRelayServiceNotification relayNotificationService;
	private Integer subStatusIdChanged;
	private String previous;
	private String next;
	
	// The following is for handling Order Groups
	private Double ogLaborSpendLimit = 0.0;
	private Double ogPartsSpendLimit = 0.0;
	
	//SL-19820
	String soID;

	public Integer getSubStatusIdChanged() {
		return subStatusIdChanged;
	}

	/**
	 * @param subStatusIdChanged the subStatusIdChanged to set
	 */
	public void setSubStatusIdChanged(Integer subStatusIdChanged) {
		this.subStatusIdChanged = subStatusIdChanged;
	}


	public ISODetailsDelegate getDetailsDelegate() {
		return detailsDelegate;
	}



	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate) {
		this.detailsDelegate = detailsDelegate;
	}

	public String setDtoForTab() throws IOException{
		String TabStatus="tabIcon ";
		AjaxResultsDTO actionResults = new AjaxResultsDTO();
		actionResults.setActionState(1);
		actionResults.setResultMessage(SUCCESS);
		actionResults.setAddtionalInfo1(TabStatus);
		
		// Response output
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		String responseStr = actionResults.toXml();
		return NONE; 
	}

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		
		//SL-19820
		String soId = getParameter(SO_ID);
		setAttribute(SO_ID, soId);
		this.soID = soId;
		String groupId = (String) getSession().getAttribute(OrderConstants.GROUP_ID+"_"+soId);
        setAttribute(OrderConstants.GROUP_ID,groupId);
        
        String entryTab = (String)getSession().getAttribute("entryTab_"+soId);
		setAttribute("entryTab", entryTab);
		
		Integer status=(Integer) getSession().getAttribute(OrderConstants.SOW_SERVICE_ORDER_STATUS_SOID+"_"+soId);
		setCurrentSOStatusCodeInRequest(status);
        
        setAttribute("showSaveAndAutoPost", getSession().getAttribute("showSaveAndAutoPost_"+this.soID));
		
		maps();
		//SL-20527 : Setting SpendLimt labor and permit price
		setSpendLimitLabor();
		populateServiceOrderDTO();
		
		setAllowBidOrders();
	}

	public SOWReviewAction(ISOWizardFetchDelegate fetchDelegate,INotificationService notificationService) {
		this.fetchDelegate = fetchDelegate;
		this.notificationService=notificationService;
	}

	public String createEntryPoint() throws Exception {

		return SUCCESS;
	}

	private void populateServiceOrderDTO()throws BusinessServiceException,Exception {
		populatePanelGeneralInfo();
		populatePanelScopeOfWork();
		populatePanelContactInfo();
		populatePanelManageDocsAndPhotos();
		populatePanelParts();
		populatePanelProviders();
		populatePanelPricing();
		populatePanelTermsAndConditions();
		
		//SL-19820
		//reviewDTO.setMyReviewErrors((ArrayList<IError>)getSession().getAttribute("reviewErrors"));
		reviewDTO.setMyReviewErrors((ArrayList<IError>)getSession().getAttribute("reviewErrors_"+soID));
		getSession().setAttribute("reviewErrors_"+soID,null);
		getSession().setAttribute("reviewErrors",null);
		setAttribute("reviewDTO", reviewDTO);
		getRequest().setAttribute("previous","tab6");
		getRequest().setAttribute("next","tab5");
	
	}
	/**
	 * This method is to set the Buyer Contact and Support Location Contact details
	 * to the SOWAdditionalInfoTabDTO
	 * @param sowAdditionalInfoTabDTO
	 * @throws DelegateException 
	 */
	private void initBuyerAndBuyerSupportContactInfo(SOWAdditionalInfoTabDTO sowAdditionalInfoTabDTO) throws Exception{	 
		// Get the Contact Details of the Buyer Resource (who created the Service Order)
		if(null == sowAdditionalInfoTabDTO.getAltBuyerContacts()){
			//SL-19820
			//String soId = ((ServiceOrderWizardBean)getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY)).getSoId();
			String soId = (String) getAttribute(OrderConstants.SO_ID);
			ContactLocationVO contactVO = fetchDelegate.getBuyerResContLocDetailsForSO(soId);
			if(null == contactVO){
				Integer contactId = get_commonCriteria().getSecurityContext().getBuyerAdminContactId();
				if(null == contactId){
					contactId = get_commonCriteria().getSecurityContext().getRoles().getContactId();
				}
				contactVO = fetchDelegate.getBuyerResContLocDetailsForContactId(contactId);
			}else if (null != contactVO){
				// Check if location id is present
				
				LocationVO locationVo =  contactVO.getBuyerPrimaryLocation();
				if(null == locationVo){
					// Get the location of the admin and set it to the ContactLocationVO.
					Integer buyerAdminId = get_commonCriteria().getSecurityContext().getCompanyId();
					ContactLocationVO adminContact = fetchDelegate.getBuyerContLocDetails(buyerAdminId);
					if(null!= adminContact){
						LocationVO adminLocation = adminContact.getBuyerPrimaryLocation();
						if(null!=adminLocation){
							contactVO.setBuyerPrimaryLocation(adminLocation);
						}
					}
				}
			}
			SOWContactLocationDTO altBuyerSupportLocationContact = ObjectMapperWizard.convertVOtoSOWContactLocationDTO(contactVO);
			List<SOWAltBuyerContactDTO> altBuyerContacts = ObjectMapperWizard.convertVOtoAltBuyerContacts(contactVO);
				
			// Set the Buyer Contact and Support Location Contact details
			sowAdditionalInfoTabDTO = (SOWAdditionalInfoTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
			sowAdditionalInfoTabDTO.setAltBuyerContacts(altBuyerContacts);
			sowAdditionalInfoTabDTO.setAltBuyerSupportLocationContact(altBuyerSupportLocationContact);		
			sowAdditionalInfoTabDTO.setCompanyId(get_commonCriteria().getCompanyId());
		}
	}
	
	/**
	 * This method is to set the Service Location Contact details to the SOWAdditionalInfoTabDTO
	 * @param sowAdditionalInfoTabDTO
	 */
	private void initServiceLocationContactInfo(SOWAdditionalInfoTabDTO sowAdditionalInfoTabDTO){
		if (sowAdditionalInfoTabDTO == null){
			return;
		}else if(null == sowAdditionalInfoTabDTO.getServiceLocationContact().getFirstName()){
			// Set the Location Contact Info from SOWScopeOfWorkTabDTO
			SOWScopeOfWorkTabDTO sowDTO = (SOWScopeOfWorkTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);		
			sowAdditionalInfoTabDTO = (SOWAdditionalInfoTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
			if ( null  != sowAdditionalInfoTabDTO)
			{
				if ( null != sowDTO.getServiceLocationContact())
				{				
				sowAdditionalInfoTabDTO.setServiceLocationContact(sowDTO.getServiceLocationContact());				
				}
			}
		}
	}
	private void populatePanelGeneralInfo()throws BusinessServiceException {
		//SL-19820
		//String soId = ((ServiceOrderWizardBean)getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY)).getSoId();
		getSession().setAttribute(OrderConstants.SO_ID,soId);
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		setAttribute(OrderConstants.SO_ID,soId);
		HashMap<String, Object> dtos = fetchDelegate.getServiceOrderDTOs(soId, OrderConstants.BUYER_ROLEID);
		ServiceOrderDTO soDTO = (ServiceOrderDTO)dtos.get(soId);
		
		reviewDTO.setId(soId);
		SOWScopeOfWorkTabDTO scopeOfWorkDto = (SOWScopeOfWorkTabDTO) SOWSessionFacility
		.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
		if(null != scopeOfWorkDto )
		{
			reviewDTO.setTitle(scopeOfWorkDto.getTitle());
			reviewDTO.setOverview(scopeOfWorkDto.getOverview());
			reviewDTO.setBuyersTerms(scopeOfWorkDto.getBuyerTandC());
			reviewDTO.setSpecialInstructions(scopeOfWorkDto.getSpecialInstructions());
			  if(scopeOfWorkDto.getServiceDate1()!=null && scopeOfWorkDto.getServiceDate1().trim().length() > 1 && scopeOfWorkDto.getStartTime()!=null)
			  {	
		    	
				if(scopeOfWorkDto.getServiceDate2()!=null && scopeOfWorkDto.getServiceDate2().trim().length() > 1 && scopeOfWorkDto.getEndTime()!=null) 
				{
					if(StringUtils.isNotEmpty(scopeOfWorkDto.getServiceDate1()) && StringUtils.isNotEmpty(scopeOfWorkDto.getServiceDate2())){
						Date appointDate1 = Timestamp.valueOf(scopeOfWorkDto.getServiceDate1()+" 00:00:00.0");
						Date appointDate2 = Timestamp.valueOf(scopeOfWorkDto.getServiceDate2()+" 00:00:00.0");					
						reviewDTO.setAppointmentDates(DateUtils.getFormatedDate(appointDate1, "MMM d, yyyy")
								+" - "+ DateUtils.getFormatedDate(appointDate2, "MMM d, yyyy"));
					}
					if(!scopeOfWorkDto.getStartTime().equals("[HH:MM]") && !scopeOfWorkDto.getEndTime().equals("[HH:MM]")){
						reviewDTO.setServiceWindow(scopeOfWorkDto.getStartTime()+" - "+scopeOfWorkDto.getEndTime());
					}
					
				} 
				else
				{
					if(StringUtils.isNotEmpty(scopeOfWorkDto.getServiceDate1())){
						Date appointDate1 = Timestamp.valueOf(scopeOfWorkDto.getServiceDate1()+" 00:00:00.0");
						reviewDTO.setAppointmentDates(DateUtils.getFormatedDate(appointDate1, "MMM d, yyyy"));
					}
					if(!scopeOfWorkDto.getStartTime().equals("[HH:MM]")){
						reviewDTO.setServiceWindow(scopeOfWorkDto.getStartTime());
					}
				}
			  }
			
			
			Integer serviceWindowComment = scopeOfWorkDto.getConfirmServiceTime();
			if(serviceWindowComment != null && serviceWindowComment == 1)
				reviewDTO.setServiceWindowComment(OrderConstants.PROVIDER_CONFIRM_SERVICE_TIME_YES);
			else
				reviewDTO.setServiceWindowComment(OrderConstants.PROVIDER_CONFIRM_SERVICE_TIME_NO);
			reviewDTO.setContinuationOrderID(scopeOfWorkDto.getContinuationReasonId()+"");
			reviewDTO.setContinuationReason(scopeOfWorkDto.getContinuationReasonDesc());
		}
			
		// Get what we need from the AdditionalInfoDTO
		SOWAdditionalInfoTabDTO addInfoDto = (SOWAdditionalInfoTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
		if(null != addInfoDto)
		{
			ArrayList<LuBuyerRefVO> buyerRefList = (ArrayList<LuBuyerRefVO>) addInfoDto.getBuyerRefs(); 
			if(buyerRefList == null ||buyerRefList.isEmpty()){ 
				String buyerId = get_commonCriteria().getSecurityContext().getCompanyId().toString(); 
				buyerRefList = getFetchDelegate().getBuyerRef(buyerId); 
			} 
			if(buyerRefList != null && addInfoDto.getCustomRefs() != null)
			{
				for(LuBuyerRefVO buyerRef :buyerRefList) 
				  {
					//SL-19050
					//Code added to display custom reference based on the new attribute 'Display field if no value'
					int ind=0;
					for(SOWCustomRefDTO selbuyerDto : addInfoDto.getCustomRefs())
	                    {
				            if (selbuyerDto.getRefTypeId().equals(buyerRef.getRefTypeId()))
				            {   
				            	SOWSelBuyerRefDTO selBuyerdef=new SOWSelBuyerRefDTO(); 		
				            	selBuyerdef.setRefType((buyerRef.getRefType()).toUpperCase());
				            	selBuyerdef.setRefValue(selbuyerDto.getRefValue());
				            	reviewDTO.getSelByerRefDTO().add(selBuyerdef);
				            	ind=1;
				            	break;
				            } 
	                    } 
					
					   if((ind==0) && (null!=buyerRef.getDisplayNoValue()) && (buyerRef.getDisplayNoValue().intValue()==1) && (StringUtils.isBlank(buyerRef.getReferenceValue())))
			            {
						   	SOWSelBuyerRefDTO selBuyerdef=new SOWSelBuyerRefDTO(); 		
			            	selBuyerdef.setRefType((buyerRef.getRefType()).toUpperCase());
			            	selBuyerdef.setRefValue("");
			            	reviewDTO.getSelByerRefDTO().add(selBuyerdef);
			            }
	               }
				}

			
			Integer statusId = -1;
			
			statusId = addInfoDto.getWfStateId();
			
			if (statusId == null || statusId.equals(100)){
				//When the statusId == null then we know that the user
				//is creating an SO and did not save it as draft yet
				statusId = OrderConstants.DRAFT_STATUS;
				
				getSubStatusDetails(statusId, _commonCriteria.getRoleId());
				reviewDTO.setShowSubStatusChange(true);
			}else{
				reviewDTO.setShowSubStatusChange(false);
			}
			//END
			
			 //get the substatus id from the fetch
			SOWAdditionalInfoTabDTO fetchAddtlDTO = (SOWAdditionalInfoTabDTO)dtos.get("addnlInfo");
			Integer subStatusId=null;

			if (fetchAddtlDTO != null){
				subStatusId = fetchAddtlDTO.getSubStatusId();
			}
			
			reviewDTO.setSubStatus(subStatusId);
			//SL 18418 Changes for CAR routed Orders the substatus is not set as Exclusive
			//Calling method when the so is in posted state
			if(null!=addInfoDto.getWfStateId() && addInfoDto.getWfStateId()==110)
			{
				String methodOfRouting=detailsDelegate.getMethodOfRouting(soId);
			     if(null!=methodOfRouting)
			     {
			    	 reviewDTO.setMethodOfRouting(methodOfRouting);
			     }
			}
			reviewDTO.setStatusString(addInfoDto.getStatusString());
			reviewDTO.setLogodocumentId(addInfoDto.getLogodocumentId());
		}
		
	
		
		if(scopeOfWorkDto.getCreatedDateString() != null){
			Date createdDate = Timestamp.valueOf(scopeOfWorkDto.getCreatedDateString());
			reviewDTO.setCreatedDateString(DateUtils.getFormatedDate(createdDate, "MMM d, yyyy hh:mm aaa"));
		}
        if(scopeOfWorkDto.getModifiedDate() != null){
        	Date modifiedDate = Timestamp.valueOf(scopeOfWorkDto.getModifiedDate());
        	reviewDTO.setModifiedDate(DateUtils.getFormatedDate(modifiedDate, "MMM d, yyyy hh:mm aaa"));
        }	
	}
	
	
	@Override
	protected void getSubStatusDetails(Integer statusId, Integer roleId) {
		//LMA...Sears00044851
		ArrayList<ServiceOrderStatusVO> statusSubStatuses = detailsDelegate.getSubStatusDetails(statusId, roleId);
		List<ServiceOrderSubStatusVO> subStatuses = null;

		for(ServiceOrderStatusVO statusVO : statusSubStatuses){
			if(statusId == statusVO.getStatusId()){
				subStatuses = statusVO.getServiceOrderSubStatusVO();
				break;
			}
				
		}
		setAttribute("SOStatusSubStatuses", subStatuses);
}
	

	private void populatePanelScopeOfWork()
	{
		// Use these maps to collect lists with no duplicates.
		HashMap<Integer, Integer> categoryArray = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> subCategoryArray = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> skillArray = new HashMap<Integer, Integer>();
		
		String title = "";
		String categories = "";
		String subCategories = "";
		String skills = "";
		SOWScopeOfWorkTabDTO scopeOfWorkDto = (SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance().getTabDTO(
				OrderConstants.SOW_SOW_TAB);
		
		// Loop thru all the tasks and string together a set of
		// categories, subcategories, and skills, no duplicates.
		if(!(scopeOfWorkDto.getIsEditMode()) && ((scopeOfWorkDto.getServiceOrderSkuIndicator().equals(true))||
				(null != scopeOfWorkDto.getServiceOrderTypeIndicator() && (scopeOfWorkDto.getServiceOrderTypeIndicator().equals("SoUsingSku")))))
		{
			if (scopeOfWorkDto.getSkus() != null && scopeOfWorkDto.getSkus().size() > 0)
		{
			for (SOTaskDTO taskDto : scopeOfWorkDto.getSkus())
			{
				if (taskDto.getTaskName() != null)
					title += taskDto.getTaskName();

				if (!arrayContainsId(categoryArray, taskDto.getCategoryId()))
				{
					if (!categories.equals(""))
						categories += ", ";

					categories += taskDto.getCategory();
				}

				if (!arrayContainsId(subCategoryArray, taskDto.getSubCategoryId()))
				{
					if (!subCategories.equals(""))
						subCategories += ", ";

					if(null!=taskDto.getSubCategory())	
					subCategories += taskDto.getSubCategory();
				}

				if (!arrayContainsId(skillArray, taskDto.getSkillId()))
				{
					if (!skills.equals(""))
						skills += ", ";

					skills += taskDto.getSkill();
				}

				taskDto.setTitle(taskDto.getTaskName() + " - " + scopeOfWorkDto.getMainServiceCategoryName());
			}
			reviewDTO.setCategoriesRequired(categories);
			reviewDTO.setSubcategoriesRequired(subCategories);
			reviewDTO.setSkillsRequired(skills);
			reviewDTO.setNumberOfTasks(scopeOfWorkDto.getSkus().size());
			ArrayList<SOTaskDTO> skuList = scopeOfWorkDto.getSkus();
			//Integer seqNum = 1;
			for(SOTaskDTO skus:skuList){
				skus.setTaskType(0);
				//skus.setSequenceNumber(seqNum);
				//seqNum = seqNum+1;
			}
			reviewDTO.setTaskList(skuList);
		}
		
		}
		
		//Populating the sku list if it is a so created using sku
		else
		{
			if (scopeOfWorkDto.getTasks() != null && scopeOfWorkDto.getTasks().size() > 0)
			{
				for (SOTaskDTO taskDto : scopeOfWorkDto.getTasks())
				{
					if (taskDto.getTaskName() != null)
						title += taskDto.getTaskName();

					if (!arrayContainsId(categoryArray, taskDto.getCategoryId()))
					{
						if (!categories.equals(""))
							categories += ", ";

						categories += taskDto.getCategory();
					}

					if (!arrayContainsId(subCategoryArray, taskDto.getSubCategoryId()))
					{
						if (!subCategories.equals(""))
							subCategories += ", ";
						
						if(null!=taskDto.getSubCategory())	
						subCategories += taskDto.getSubCategory();
					}

					if (!arrayContainsId(skillArray, taskDto.getSkillId()))
					{
						if (!skills.equals(""))
							skills += ", ";

						skills += taskDto.getSkill();
					}

					taskDto.setTitle(taskDto.getTaskName() + " - " + scopeOfWorkDto.getMainServiceCategoryName());
				}
				reviewDTO.setCategoriesRequired(categories);
				reviewDTO.setSubcategoriesRequired(subCategories);
				reviewDTO.setSkillsRequired(skills);
				reviewDTO.setNumberOfTasks(scopeOfWorkDto.getTasks().size());
				ArrayList<SOTaskDTO> taskList = scopeOfWorkDto.getTasks();
				if(!(scopeOfWorkDto.getIsEditMode())){
				//Integer seqNum = 1;
				for(SOTaskDTO skus:taskList){
					skus.setTaskType(0);
					//skus.setSequenceNumber(seqNum);
					//seqNum = seqNum+1;
				}
				reviewDTO.setTaskList(taskList);
				}else{
					for(SOTaskDTO skus:taskList){
						if(skus.getTaskType()==null){
							skus.setTaskType(0);
						}
				}
				reviewDTO.setTaskList(taskList);
				}
				
			}	
		}
	
		// Scope of Work - Location
		reviewDTO.setLocationContact(new SOContactDTO(scopeOfWorkDto.getServiceLocationContact()));
		reviewDTO.setLocationNotes(scopeOfWorkDto.getServiceLocationContact().getServiceLocationNote());
		reviewDTO.setMainServiceCategory(scopeOfWorkDto.getMainServiceCategoryName());
		reviewDTO.setMainServiceCategoryId(scopeOfWorkDto.getMainServiceCategoryId());
		
	}
	boolean arrayContainsId(HashMap<Integer, Integer> map, Integer id)
	{		
		if(id == null)
			return false;
		boolean exists = map.get(id) != null;
		
		if(exists == false)
			map.put(id, id);
		
		return exists;			
	}
	
	
	private void populatePanelContactInfo()throws Exception
	{
		
		SOWPricingTabDTO pricingTabDto = (SOWPricingTabDTO) SOWSessionFacility
		.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);
		
		boolean shareContactInd = pricingTabDto.isShareContactInd();
		reviewDTO.setShareContactInd(shareContactInd);
		
		SOWAdditionalInfoTabDTO addInfoDto = (SOWAdditionalInfoTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
		// to get the buyer contact info and service location info
		initBuyerAndBuyerSupportContactInfo(addInfoDto);
		initServiceLocationContactInfo(addInfoDto);
		if(null != addInfoDto)
		{
			
			// provider contact
			if (null != addInfoDto.getServiceLocationContact())
			{
				SOWContactLocationDTO serviceLocationContact = addInfoDto.getServiceLocationContact();
				SOContactDTO providerContact = new  SOContactDTO(serviceLocationContact);
				reviewDTO.setProviderContact(providerContact);
				}
			
			// locationAlternateContact
			if(addInfoDto.isAltServiceLocationContactFlg()){
        			if (null != addInfoDto.getAlternateLocationContact())
        			{
        				SOWContactLocationDTO altContactLocationDto = addInfoDto.getAlternateLocationContact();
        				SOContactDTO locationAlternateContact = new SOContactDTO(altContactLocationDto);
					
        				reviewDTO.setLocationAlternateContact(locationAlternateContact);
					}					
			     }
			
			
		
			// buyer contact
			if (null != addInfoDto.getAltBuyerSupportLocationContact())
			{
				SOWContactLocationDTO altBuyerSupportLocationContact = addInfoDto.getAltBuyerSupportLocationContact();
				SOContactDTO buyerContact = new  SOContactDTO(altBuyerSupportLocationContact);
				reviewDTO.setBuyerContact(buyerContact);
			}
			
			if(addInfoDto.getCompanyId()!=null && addInfoDto.getCompanyId()>0)
			  reviewDTO.setCompanyID(addInfoDto.getCompanyId()+"");
						
			
		    // buyer Support Contact
			if(addInfoDto.isAltBuyerSupportLocationContactFlg()){
				if (null != addInfoDto.getSelectedAltBuyerContact())
				{
					SOWAltBuyerContactDTO selectedAltBuyerContact =  addInfoDto.getSelectedAltBuyerContact();
					SOContactDTO buyerSupportContact = new SOContactDTO();
					if( selectedAltBuyerContact!= null)
					{
						String individualName = selectedAltBuyerContact.getFirstName() + " " + selectedAltBuyerContact.getLastName();
						if(null!=selectedAltBuyerContact.getResourceId()) 
							individualName=individualName+" (User Id#"+selectedAltBuyerContact.getResourceId()+")";
						logger.info("Review Tab: Selected Alternate Buyer Contact:"+individualName);
						buyerSupportContact.setIndividualName(individualName);
						Integer resourceId = selectedAltBuyerContact.getResourceId();
						if(resourceId != null)
							buyerSupportContact.setIndividualID(resourceId + "");
						buyerSupportContact.setResourceId(resourceId);
						//buyerSupportContact.setResourceId(selectedAltBuyerContact.getEntityId());
						buyerSupportContact.setPhoneHome(selectedAltBuyerContact.getHomeNo());
						buyerSupportContact.setPhoneMobile(selectedAltBuyerContact.getCellNo());
						buyerSupportContact.setPhoneAlternate(selectedAltBuyerContact.getPhoneNo());
						buyerSupportContact.setStreetAddress(selectedAltBuyerContact.getAddress1());
						buyerSupportContact.setStreetAddress2(selectedAltBuyerContact.getAddress2());
						if(StringUtils.isNotEmpty(selectedAltBuyerContact.getFaxNo()))
						buyerSupportContact.setFax(selectedAltBuyerContact.getFaxNo());
						buyerSupportContact.setEmail(selectedAltBuyerContact.getEmail());
						
						ContactLocationVO contactLocationVO = fetchDelegate.getBuyerResContLocDetailsForContactId(selectedAltBuyerContact.getContactId());
						
						if (null != contactLocationVO
								&& null != contactLocationVO
										.getBuyerPrimaryLocation()) {
							if (null == buyerSupportContact.getStreetAddress() && null!=contactLocationVO
									.getBuyerPrimaryLocation()
									.getStreet1())
								buyerSupportContact
										.setStreetAddress(contactLocationVO
												.getBuyerPrimaryLocation()
												.getStreet1());
							
							
							if (null == buyerSupportContact.getStreetAddress2() && null!=contactLocationVO
									.getBuyerPrimaryLocation()
									.getStreet2())
								buyerSupportContact
										.setStreetAddress2(contactLocationVO
												.getBuyerPrimaryLocation()
												.getStreet2());

							StringBuffer cityStateZip = new StringBuffer("");
							
							if (null == selectedAltBuyerContact.getCity() && null!=contactLocationVO
									.getBuyerPrimaryLocation().getCity())
								
								cityStateZip.append( contactLocationVO
										.getBuyerPrimaryLocation().getCity().toString());

							if (null == selectedAltBuyerContact.getState()&& null!=contactLocationVO
									.getBuyerPrimaryLocation()
									.getState())
								cityStateZip.append(" "
										+ contactLocationVO
												.getBuyerPrimaryLocation()
												.getState());

							if (null == selectedAltBuyerContact.getZip()&& null!=contactLocationVO
									.getBuyerPrimaryLocation()
									.getZip())
								cityStateZip.append(" "
										+ contactLocationVO
												.getBuyerPrimaryLocation()
												.getZip());

							if (null == selectedAltBuyerContact.getZip4()&& null!=contactLocationVO
									.getBuyerPrimaryLocation()
									.getZip4())
								cityStateZip.append(" "
										+ contactLocationVO
												.getBuyerPrimaryLocation()
												.getZip4());

							if (null != cityStateZip)
								buyerSupportContact
										.setCityStateZip(cityStateZip
												.toString());
						}
					}
					
					reviewDTO.setBuyerSupportContact(buyerSupportContact);
				}	
 			}
		}
	}

	private void populatePanelManageDocsAndPhotos()
	{
		SOWScopeOfWorkTabDTO tabDTO =(SOWScopeOfWorkTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
		
		
		reviewDTO.setDocumentsList(tabDTO.getDocuments());
		
	}

	private void populatePanelParts() {
		SOWPartsTabDTO partsTabDto =(SOWPartsTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PARTS_TAB);
		
		try {
			if (partsTabDto != null) {

				if (null != partsTabDto.getParts()
						&& partsTabDto.getParts().size() > 0)
				{
					
					for(SOWPartDTO dto : partsTabDto.getParts())
					{
						reviewDTO.getPartsList().add(new SOPartsDTO(dto));
					}
				}
				validateParts(partsTabDto);
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
	}
	/**
	 * This method sets the indicator depending on whether parts are present or not in the SO.
	 * @param partsTabDto 
	 * 
	 */
	private void validateParts(SOWPartsTabDTO partsTabDto){
		int partsIndicator=0;
		SOWPartDTO partsDto;
		if (partsTabDto.getParts() != null){
			if(partsTabDto.getParts().size()==1){
				partsDto = partsTabDto.getParts().get(0);
				if(StringUtils.isNotBlank(partsDto.getManufacturer())&&StringUtils.isNotBlank(partsDto.getModelNumber())&& partsTabDto.getPartsSuppliedBy().equals(OrderConstants.SOW_SOW_BUYER_PROVIDES_PART)){
					partsIndicator = 1;	
				}
			}else if (partsTabDto.getParts().size()>1&&partsTabDto.getPartsSuppliedBy().equals(OrderConstants.SOW_SOW_BUYER_PROVIDES_PART)){
				partsIndicator = 1;
			}
			
			setAttribute("partsIndicatorSOW",partsIndicator);
		}
		
	}

	private void populatePanelProviders()
	{
		SOWProvidersTabDTO provDTO = (SOWProvidersTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PROVIDERS_TAB);
		
		if (null == provDTO.getProviders() || provDTO.getProviders().isEmpty()){
			//SL-19820
			//provDTO=(SOWProvidersTabDTO) getRequest().getSession().getAttribute("ProvidersList");
			provDTO=(SOWProvidersTabDTO) getRequest().getSession().getAttribute("ProvidersList_"+(String)getAttribute(SO_ID));
		}
		if(provDTO == null)
			return;
		
		if(reviewDTO.getProvidersList() == null)
			reviewDTO.setProvidersList(new ArrayList<SOWProviderDTO>());
		
		for(SOWProviderDTO provider : provDTO.getProviders())
		{
			if(provider.getIsChecked())
			{
				reviewDTO.getProvidersList().add(provider);
				//To show the provider error message when providers with No Insurance Details are selected
				if(	(null == provider.getGenLiabilityInsAmt() || provider.getGenLiabilityInsAmt() <=  0.0) ||
						(null == provider.getWorkCompInsAmt() || provider.getWorkCompInsAmt() <= 0.0)    ||
						(null == provider.getVehLiabilityInsAmt() || provider.getVehLiabilityInsAmt() <=  0.0)
				 ){
						reviewDTO.setInsPresent(Boolean.FALSE);
				}
			}			
		}
		
	}

	private void populatePanelPricing() {
		
		SOWPricingTabDTO pricingTabDto = (SOWPricingTabDTO) SOWSessionFacility
				.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);
		SOWPartsTabDTO partsTabDto = (SOWPartsTabDTO) SOWSessionFacility
		.getInstance().getTabDTO(OrderConstants.SOW_PARTS_TAB);
		
		//set local formatting
		Locale locale = Locale.getDefault();
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		SOWPartsTabDTO tabDTO =(SOWPartsTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PARTS_TAB);

		if (pricingTabDto != null && tabDTO != null)
		{
			double postingFee = pricingTabDto.getPostingFee();
			double TotalAmountDue = 0.0;
			double tempLaborSpendLimit = 0.0;
			double tempPartsSpendLimit = 0.0;
			double tempPermitsSpendLimit = 0.0;

			if( !StringUtils.isBlank(pricingTabDto.getLaborSpendLimit()) )			
				tempLaborSpendLimit = com.newco.marketplace.web.utils.SLStringUtils.getMonetaryNumber(pricingTabDto.getLaborSpendLimit());

			tempLaborSpendLimit = Double.valueOf(tempLaborSpendLimit) != null ? tempLaborSpendLimit : 0.00;

			if( !StringUtils.isBlank(pricingTabDto.getPartsSpendLimit()) )
				tempPartsSpendLimit = com.newco.marketplace.web.utils.SLStringUtils.getMonetaryNumber(pricingTabDto.getPartsSpendLimit());
				

			tempPartsSpendLimit = Double.valueOf(tempPartsSpendLimit) != null ? tempPartsSpendLimit : 0.00;
			
						
			TotalAmountDue = tempLaborSpendLimit + tempPartsSpendLimit + postingFee;
			
			if(pricingTabDto.getPermitPrepaidPrice() != null ) 			
				tempPermitsSpendLimit = com.newco.marketplace.web.utils.SLStringUtils.getMonetaryNumber(pricingTabDto.getPermitPrepaidPrice().toString());

			tempPermitsSpendLimit = Double.valueOf(tempPermitsSpendLimit) != null ? tempPermitsSpendLimit : 0.00;
			
			String orderType = "";
			if (pricingTabDto.getOrderType().equals(Constants.PriceModel.ZERO_PRICE_BID)) {
				orderType = "Bid Request";
				reviewDTO.setShowPrices(false);
			} else {
				orderType = "Name Your Price (fixed)";
				reviewDTO.setShowPrices(true);
			}
			//SL-19820
			ServiceOrderDTO soDTO = null;
			//ServiceOrderDTO soDTO = (ServiceOrderDTO)sessionMap.get("THE_SERVICE_ORDER");
			try{
				soDTO = detailsDelegate.getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
				setAttribute(THE_SERVICE_ORDER, soDTO);
					
			}catch(Exception e){
				System.err.println("Exception while fetching service order object");
			}
			if(null != soDTO){
				reviewDTO.setPriceType(soDTO.getPriceType());
			}else{
				if(buyerFeatureSetBO.validateFeature(get_commonCriteria().getCompanyId(), BuyerFeatureConstants.TASK_LEVEL)){
					reviewDTO.setPriceType(TASK_LEVEL_PRICING);
				}else{
					reviewDTO.setPriceType(SO_LEVEL_PRICING);
				}
			}
			reviewDTO.setPartsSpendLimit(tempPartsSpendLimit);
			reviewDTO.setLaborSpendLimit(tempLaborSpendLimit);
			reviewDTO.setPricingType(orderType);
			reviewDTO.setAccessFee(nf.format(postingFee));
			reviewDTO.setTotalAmountDue(nf.format(TotalAmountDue) + "");
			reviewDTO.setPartsSuppliedBy(tabDTO.getPartsSuppliedBy());
			reviewDTO.setPrePaidPermitPrice(tempPermitsSpendLimit);
			reviewDTO.setBuyerID(pricingTabDto.getBuyerId());
			// Child Orders of Order Group
			//SL-19820
			//String groupId = (String)getSession().getAttribute(GROUP_ID);
			String groupId = (String) getAttribute(GROUP_ID);
			if(!StringUtils.isBlank(groupId))
			{
				Double laborSpendLimit = 0.0;
				Double partsSpendLimit = 0.0;
				if(pricingTabDto.getLaborSpendLimit() != null)
					laborSpendLimit = pricingTabDto.getOgLaborSpendLimit();
				
				if(pricingTabDto.getPartsSpendLimit() != null)
					partsSpendLimit = pricingTabDto.getOgPartsSpendLimit();
				
				setOgLaborSpendLimit(laborSpendLimit);
				setOgPartsSpendLimit(partsSpendLimit);
			}
			
		}

	}
	
	
	
	private void populatePanelTermsAndConditions() {
		TermsAndConditionsVO termsAndConditionsVO = null;
		try {
			termsAndConditionsVO = getLookupManager().getTermsConditionsContent(SOConstants.BUYER_SO_AGREEMENT);			
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		
		reviewDTO.setPleaseVerify("Please verify your acceptance of ServiceLive's Terms & Conditions. You will be able to post this service order only upon acceptance.");
		reviewDTO.setTermsAndConditions(termsAndConditionsVO.getTermsCondContent());
		reviewDTO.setTermsAndConditionsId(termsAndConditionsVO.getTermsCondId());
		
	}



	public String createAndRoute() throws Exception {
		logger.info("inside createAndRoute");
		getRequest().setAttribute("previous","tab6");
		getRequest().setAttribute("next","tab6");
				
		
		TabNavigationDTO tabNav = _createNavPoint(
				OrderConstants.SOW_POST_SO_ACTION,
				OrderConstants.SOW_REVIEW_TAB, OrderConstants.SOW_EDIT_MODE,
				"SOW");
		reviewDTO.setTheResourceBundle(getTexts("servicelive"));
		reviewDTO.setTryingToPost(true);
		reviewDTO.setRouteFromFE(true);
		
		String returnValue = GOTO_COMMON_WIZARD_CONTROLLER;
		String sessionIdStr = (String) getRequest().getSession().getAttribute("sessionId");
		Double sessionIdDbl = new Double(0.0);
		if (sessionIdStr != null && StringUtils.isNotBlank(sessionIdStr)) {
			sessionIdDbl = Double.parseDouble(sessionIdStr);
		}
		if (reviewDTO.getSessionId() != sessionIdDbl.doubleValue()) {
			getRequest().getSession().setAttribute("sessionId", reviewDTO.getSessionId() + "");
			soPricePopulation();
			//SL-20527 : Setting SpendLimt labor and permit price
			setSpendLimitLabor();
			boolean gotoReviewTab = SOWSessionFacility.getInstance().evaluateAndPostSOWBean(reviewDTO, getIsoWizardPersistDelegate(), financeManagerDelegate, orderGroupDelegate, tabNav, get_commonCriteria(), fetchDelegate, relayNotificationService);
			reviewDTO.setTryingToPost(false);
			ArrayList<IError> myErrors = (ArrayList<IError>) reviewDTO.getErrors();
			//SL-19820
			//getSession().setAttribute("reviewErrors", myErrors);
			getSession().setAttribute("reviewErrors_"+soID, myErrors);
			if (gotoReviewTab == true) {
				setDefaultTab(SOWSessionFacility.getInstance().getGoingToTab());
				// FIXME: Shall this be .equals()?
				if (SOWSessionFacility.getInstance().getGoingToTab() == "Pricing") {
					setDefaultTab("Review");
				}
				return GOTO_COMMON_WIZARD_CONTROLLER;
			} else {
				//SL-19820
				//String currentSO = (String) getSession().getAttribute(OrderConstants.SO_ID);
				String currentSO = (String) getAttribute(OrderConstants.SO_ID);
				//SL-21355 - Code change to save the buyer logo associated with the primary SKU 
				setBrandingLogo();
				invalidateAndReturn(getFetchDelegate());
				Map sessionMap = ActionContext.getContext().getSession();
				
				if (new SOClaimedFacility().isWorkflowTheStartingPoint(sessionMap, currentSO)) {
					returnValue = OrderConstants.WORKFLOW_STARTINGPOINT;
				} else {
					returnValue = OrderConstants.SOW_STARTPOINT_SOM + "Posted";
				}
				
				// If this buyer is allowed to post bid orders, then turn on the feature
				// to allow communication between buyer and provider for this order.
				if (reviewDTO.getCompanyID() != null) {
					Integer buyerId = Integer.parseInt(reviewDTO.getCompanyID());
					if (getDetailsDelegate().validateFeature(buyerId, "ALLOW_BID_ORDERS")) {
						getDetailsDelegate().setServiceOrderFeature(currentSO, OrderConstants.ALLOW_COMMUNICATION);
					}
				}
				
			}
			
		}
		return returnValue;
	}

	public String saveAndAutoPost() throws Exception {
		logger.info("inside saveAndAutoPost");
		getRequest().setAttribute("previous","tab6");
		getRequest().setAttribute("next","tab6");
				
		
		TabNavigationDTO tabNav = _createNavPoint(
				OrderConstants.SOW_SAVE_AS_DRAFT_ACTION,
				OrderConstants.SOW_REVIEW_TAB, OrderConstants.SOW_EDIT_MODE,
				"SOW");
		reviewDTO.setTheResourceBundle(getTexts("servicelive"));
		reviewDTO.setTryingToPost(false);

	
		String returnValue = GOTO_COMMON_WIZARD_CONTROLLER;
		String sessionIdStr = (String) getRequest().getSession().getAttribute("sessionId");
		Double sessionIdDbl = new Double(0.0);
		if (sessionIdStr != null && StringUtils.isNotBlank(sessionIdStr)) {
			sessionIdDbl = Double.parseDouble(sessionIdStr);
		}
		if (reviewDTO.getSessionId() != sessionIdDbl.doubleValue()) {
			logger.info("inside 111");
			getRequest().getSession().setAttribute("sessionId", reviewDTO.getSessionId() + "");
		    soPricePopulation();
			//SL-20527 : Setting SpendLimt labor and permit price
			setSpendLimitLabor();
			logger.info("inside 222");
			boolean gotoReviewTab = SOWSessionFacility.getInstance().evaluateAndSaveAndAutoPostSOWBean(reviewDTO, tabNav, isoWizardPersistDelegate, get_commonCriteria(), orderGroupDelegate);
			reviewDTO.setTryingToPost(false);
			ArrayList<IError> myErrors = (ArrayList<IError>) reviewDTO.getErrors();
			//SL-19820
			//getSession().setAttribute("reviewErrors", myErrors);
			getSession().setAttribute("reviewErrors_"+soID, myErrors);
			if (gotoReviewTab == true) {
				setDefaultTab(SOWSessionFacility.getInstance().getGoingToTab());
				// FIXME: Shall this be .equals()?
				if (SOWSessionFacility.getInstance().getGoingToTab() == "Pricing") {
					setDefaultTab("Review");
				}
				return GOTO_COMMON_WIZARD_CONTROLLER;
			} else {
				//SL-19820
				//String currentSO = (String) getSession().getAttribute(OrderConstants.SO_ID);
				String soId = (String) getAttribute(OrderConstants.SO_ID);
				//SL-21355 - Code change to save the buyer logo associated with the primary SKU 
				setBrandingLogo();
				invalidateAndReturn(getFetchDelegate());
				Map sessionMap = ActionContext.getContext().getSession();
	
				if (new SOClaimedFacility().isWorkflowTheStartingPoint(sessionMap, soId)) {
					returnValue = OrderConstants.WORKFLOW_STARTINGPOINT;
				} else {
					returnValue = OrderConstants.SOW_STARTPOINT_SOM + "Posted";
				}
	
			}
			
		}
		return returnValue;
	}

	
	
	
	public String editEntryPoint() throws Exception {

	return null;
	}


	public String previous() throws Exception {
		getRequest().setAttribute("previous","tab6");
		getRequest().setAttribute("next","tab5");
		TabNavigationDTO tabNav = _createNavPoint(
				OrderConstants.SOW_PREVIOUS_ACTION,
				OrderConstants.SOW_REVIEW_TAB, OrderConstants.SOW_EDIT_MODE,
				"SOW");

		ServiceOrderDTO reviewDTO = getModel();
		if (reviewDTO == null) {
			reviewDTO = new ServiceOrderDTO();
		}
		reviewDTO.setTheResourceBundle(getTexts("servicelive"));

		SOWSessionFacility sowSessionFacility = SOWSessionFacility
				.getInstance();
		sowSessionFacility.evaluateSOWBean(reviewDTO, tabNav);

		String goingTotab = sowSessionFacility.getGoingToTab();
		this.setDefaultTab(goingTotab);
		if (goingTotab == "Review"){
			this.setNext("tab6");
		}
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}
	
	public String updateSubStatus(){
		Integer subStatusIdChanged = getSubStatusIdChanged();
		Integer statusId = getWfStatusId();
		try{
			updateSOSubStatus(subStatusIdChanged, reviewDTO.getId(),statusId);
			this.setDefaultTab(SOConstants.REVIEW);
		}catch(Exception e){
			logger.info("Caught Exception and ignoring",e);
		}
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	private Integer getWfStatusId() {
		SOWAdditionalInfoTabDTO addInfoDto = (SOWAdditionalInfoTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
		Integer statusId = -1;
		statusId = addInfoDto.getWfStateId();
		return statusId;
	}

	public String saveAsDraft() throws Exception {
		//1. NAME OF THE ACTION (NEXT OR PREVIOUS)
		//2. WHAT TAB OR YOU ON sow ETC..
		//3. WHAT MODE ARE YOU IN CREATE OR EDIT
		//4. WHERE DID YOU START FROM IN THE APPLICATION
		String returnValue = null;
		
		TabNavigationDTO tabNav = _createNavPoint(OrderConstants.SOW_SAVE_AS_DRAFT_ACTION,
												  OrderConstants.SOW_REVIEW_TAB,
												  OrderConstants.SOW_EDIT_MODE, "SOM" );
		ServiceOrderDTO sowReviewDTO = getModel();
		soPricePopulation();
		//SL-20527 : Setting SpendLimt labor and permit price
		setSpendLimitLabor();
		
		SOWSessionFacility.getInstance().evaluateAndSaveSOWBean(sowReviewDTO, tabNav, isoWizardPersistDelegate, get_commonCriteria(), orderGroupDelegate);
		
		String str = SOWSessionFacility.getInstance().getGoingToTab();
		if (str!=null && str.equalsIgnoreCase(OrderConstants.SOW_EXIT_SAVE_AS_DRAFT))
		{
			//SL-19820
			//String currentSO = (String)getSession().getAttribute( OrderConstants.SO_ID);
			String soId = (String) getAttribute( OrderConstants.SO_ID);
			//SL-21355 - Code change to save the buyer logo associated with the primary SKU 
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
	}

	public String saveAsTemplate() throws Exception {
		ServiceOrderDTO sowReviewDTO = getModel();
		
		fetchDelegate.saveServiceOrderAsTemplate(_commonCriteria.getCompanyId(),sowReviewDTO);
		
		invalidateAndReturn(fetchDelegate);
				
		return SOW_STARTPOINT_SOM;
	}
	
	private void setAllowBidOrders() {
		try{

			//Getting BuyerId
			Integer buyerId = null;
			Map<String, Object> sessionMap = ActionContext.getContext().getSession();
			SecurityContext securityCntxt = (SecurityContext) sessionMap.get("SecurityContext");
			SOWPricingTabDTO pricingTabDto = (SOWPricingTabDTO) SOWSessionFacility
			.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);
			boolean sealedBidInd = pricingTabDto.isSealedBidInd();
			
			if (securityCntxt != null) {
				buyerId = securityCntxt.getCompanyId();
			}

			if(buyerId != null)
			{
				if (buyerFeatureSetBO.validateFeature(buyerId, "ALLOW_BID_ORDERS")) {
					reviewDTO.setAllowBidOrders(true);
				}
				if (buyerFeatureSetBO.validateFeature(buyerId, "ALLOW_SEALED_BID_ORDERS")) {
					reviewDTO.setAllowSealedBidOrders(true);
			}
			
				reviewDTO.setSealedBidInd(sealedBidInd);
			}
			
		} catch (Exception ex) {
			ex.getStackTrace();
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
	
	public ServiceOrderDTO getModel() {

		return reviewDTO;
}

	public ServiceOrderDTO getReviewDTO() {
		return reviewDTO;
	}

	public void setReviewDTO(ServiceOrderDTO reviewDTO)
	{
		this.reviewDTO = reviewDTO;
	}

	public ILookupDelegate getLookupManager() {
		return lookupManager;
	}

	public void setLookupManager(ILookupDelegate lookupManager) {
		this.lookupManager = lookupManager;
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

	public IFinanceManagerDelegate getFinanceManagerDelegate() {
		return financeManagerDelegate;
	}

	public void setFinanceManagerDelegate(
			IFinanceManagerDelegate financeManagerDelegate) {
		this.financeManagerDelegate = financeManagerDelegate;
	}

	public IOrderGroupDelegate getOrderGroupDelegate()
	{
		return orderGroupDelegate;
	}

	public void setOrderGroupDelegate(IOrderGroupDelegate orderGroupDelegate)
	{
		this.orderGroupDelegate = orderGroupDelegate;
	}

	public Double getOgLaborSpendLimit()
	{
		return ogLaborSpendLimit;
	}

	public void setOgLaborSpendLimit(Double ogLaborSpendLimit)
	{
		this.ogLaborSpendLimit = ogLaborSpendLimit;
	}

	public Double getOgPartsSpendLimit()
	{
		return ogPartsSpendLimit;
	}

	public void setOgPartsSpendLimit(Double ogPartsSpendLimit)
	{
		this.ogPartsSpendLimit = ogPartsSpendLimit;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}
	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public String getSoID() {
		return soID;
	}

	public void setSoID(String soID) {
		this.soID = soID;
	}

	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}
}
