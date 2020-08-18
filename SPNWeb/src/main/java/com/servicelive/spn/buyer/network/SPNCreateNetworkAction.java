package com.servicelive.spn.buyer.network;

import static com.servicelive.spn.common.SPNBackendConstants.DOC_CATEGORY_ID_FOR_SPN;
import static com.servicelive.spn.common.SPNBackendConstants.WF_ENTITY_NETWORK;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SPN_DOC_EDITED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SPN_EDITED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SPN_EDITED_WITHOUT_STATUS_CHANGE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_SPN_INCOMPLETE;
import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN;
import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN_AS_BUYER;
import static com.servicelive.spn.constants.SPNActionConstants.RESULT_GLOBAL_RECOVERABLE_EXCEPTION;
import static com.servicelive.spn.constants.SPNActionConstants.ROLE_ID_PROVIDER;
import static com.servicelive.spn.constants.SPNActionConstants.SPN_ID_PARAM;
import static com.servicelive.spn.constants.SPNActionConstants.SPN_NETWORKMODEL_IN_REQUEST;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.common.Document;
import com.servicelive.domain.lookup.LookupSPNDocumentType;
import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.domain.lookup.LookupSkills;
import com.servicelive.domain.spn.detached.ApprovalModel;
import com.servicelive.domain.spn.network.SPNBuyer;
import com.servicelive.domain.spn.network.SPNDocument;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.buyer.common.SPNCreateAction;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.services.LookupService;
import com.servicelive.spn.services.providermatch.ApprovalCriteriaHelper;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 
 * 
 *
 */
@Validation
public class SPNCreateNetworkAction extends SPNCreateAction
implements ModelDriven<SPNCreateNetworkModel>
{

	private static final long serialVersionUID = -2373625036264742599L;
	private SPNCreateNetworkModel model = new SPNCreateNetworkModel();
	private String  spnId;

	public String getSpnId() {
		return spnId;
	}

	public void setSpnId(String spnId) {
		this.spnId = spnId;
	}

	@Override
	public String display()
	{
		User loggedInUser = getLoggedInUser();
		
		if (loggedInUser == null)
		{
			return NOT_LOGGED_IN;
		}
		else if (loggedInUser.getRole() == null || loggedInUser.getRole().getId() == null || loggedInUser.getRole().getId().intValue() == ROLE_ID_PROVIDER.intValue())
		{
			return NOT_LOGGED_IN_AS_BUYER;
		}
		
		Integer spnid = getNetworkIdfromRequest();
		Integer buyerId2 = loggedInUser.getUserId();
		model.setBuyerId(buyerId2);
		//R11.0 SL_19387 Setting the background check feature set
		if(buyerId2 != null){
			model.setRecertificationBuyerFeatureInd(createSPNServices.getBuyerFeatureSet(buyerId2,"BACKGROUND_CHECK_RECERTIFICATION"));
		}
		if(spnid != null ) {
		//this is edit sceanrio
			try {
				SPNHeader spnheader = createSPNServices.getNetwork(spnid);
				if(spnheader == null) throw new Exception("SPNID provided is not available");
				model.setSpnHeader(spnheader);
				model.setExceptionRequired(spnheader.getExceptionsInd());
				model.setCriteriaLevel(spnheader.getCriteriaLevel());
				model.setCriteriaTimeframe(spnheader.getCriteriaTimeframe());
				model.setRoutingPriorityStatus(spnheader.getRoutingPriorityStatus());
				model.setMarketPlaceOverFlow(spnheader.getMarketPlaceOverFlow());
				ApprovalModel approvalItms = ApprovalCriteriaHelper.getApprovalModelFromCriteria(spnheader.getApprovalCriterias());
				model.setApprovalItems(approvalItms);
				model.setApprovalItems(getDocumentList(spnheader, model.getApprovalItems()));
				model.setHasCampaign(Boolean.valueOf(createSPNServices.hasCampaign(spnid)));
				getRequest().getSession().setAttribute("SPNApprovalItems", approvalItms);
			} catch (Exception e) {
				logger.error(e);
				return RESULT_GLOBAL_RECOVERABLE_EXCEPTION;
				
			}
		}
		else { 
			//check last ..
			if(this.vf != null && vf.equalsIgnoreCase("true")){
				if(ActionContext.getContext().getSession().get(SPN_NETWORKMODEL_IN_REQUEST)  != null) {
					SPNCreateNetworkModel mymodel =  (SPNCreateNetworkModel)ActionContext.getContext().getSession().get(SPN_NETWORKMODEL_IN_REQUEST);
					this.setActionErrors(mymodel.getActionErrors());
					this.setFieldErrors(mymodel.getFieldErrors());
					//this.model = mymodel;
					this.model.setSpnHeader(mymodel.getSpnHeader());
					this.model.setApprovalItems(mymodel.getApprovalItems());
					this.model.setLookupsVO(mymodel.getLookupsVO());
					this.model.setUploadDocData(mymodel.getUploadDocData());
					try{
					this.model.setHasCampaign(Boolean.valueOf(createSPNServices.hasCampaign(mymodel.getSpnHeader().getSpnId())));
					}catch(Exception e){
						logger.error(e);
						return RESULT_GLOBAL_RECOVERABLE_EXCEPTION;
					}
				}
			}else {
				//Remove any object
				if(ActionContext.getContext().getSession().get(SPN_NETWORKMODEL_IN_REQUEST)  != null) {
					ActionContext.getContext().getSession().remove(SPN_NETWORKMODEL_IN_REQUEST);
				}
			}
		}
	
		
		initDropdowns();
		
		initTabDisplay();
		 
		return SUCCESS;
	}
	
	@Override
	protected void initDropdowns() {
		super.initDropdowns();
		try {
		     Collection<SPNDocument> docCollectionList=createSPNServices.getSPNDocumentsForBuyer(buyerId).values();
	         //added for sorting..sorting after converting as arrayList,assign & set to collection
		     ArrayList<SPNDocument>docArrayList=new ArrayList<SPNDocument>( docCollectionList);
			 Collections.sort(docArrayList, new Comparator<SPNDocument>() {
             public int compare(SPNDocument o1, SPNDocument o2) {
					if(null!=o1.getDocument() && null != o2.getDocument()){
						if(null==o1.getDocument().getTitle() && null !=o2.getDocument().getTitle()){
	                		return 1;
	                	}else if(null!=o1.getDocument().getTitle() && null ==o2.getDocument().getTitle()){
	                		return -1;
	                	}else if(null==o1.getDocument().getTitle() && null ==o2.getDocument().getTitle()){
	                		return 0;
	                	}else{
	                		return o1.getDocument().getTitle().trim().compareToIgnoreCase(o2.getDocument().getTitle().trim());
	                	}
					}else if(null==o1.getDocument() && null != o2.getDocument()){
						return 1;
					}else if(null!=o1.getDocument() && null == o2.getDocument()){
						return -1;
					}else{
						return 0;
					}
				}
			});
			 docCollectionList=docArrayList;
			model.getLookupsVO().setAllDocuments(docCollectionList);
			
		} catch (Exception e) {
			//logger.error("Exception in sorting map");
			//logger.error(e.getMessage());
			model.getLookupsVO().setAllDocuments(null);
		}
	}
	


	private Integer  getNetworkIdfromRequest() {
		
			String paramvalue = getRequest().getParameter(SPN_ID_PARAM);
			if(paramvalue == null) {
				 if( getRequest().getAttribute(SPN_ID_PARAM) != null) {
					 
					 try {
						 	return  (Integer)  getRequest().getAttribute(SPN_ID_PARAM);
						} catch (Exception e) {
							logger.error("Non Number supplied as SPN ID",e);
						}
					 
				 }
			}
			else {
				try {
					return new Integer ( paramvalue.trim());
				} catch (NumberFormatException e) {
					logger.error("Non Number supplied as SPN ID",e);
				}
			}
		
		return null;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String checkEffectiveDateAjax()
	{
		Integer validEffectiveDate = Integer.valueOf(0);
		Date effectiveDate = model.getEffectiveDate();
		Date minEffectiveDate = CalendarUtil.addDays(CalendarUtil.getTodayAtMidnight(), 0);
		
		if (effectiveDate != null)
		{
			if (effectiveDate.after(minEffectiveDate) || effectiveDate.equals(minEffectiveDate))
			{
				validEffectiveDate = Integer.valueOf(1);
			}
		}
		
		getRequest().setAttribute("validEffectiveDate", validEffectiveDate);
		return SUCCESS;
	}
	
	public String checkNetworkEditableAjax()
	{
		Integer canEditNetwork = Integer.valueOf(1);
		Integer spnId = model.getSpnHeader().getSpnId();
		Date effectiveDate = null;
		try {
			SPNHeader aliasHeader = createSPNServices.findAlias(spnId);
			if (aliasHeader != null && aliasHeader.getEffectiveDate() != null )
			{
				Date today = CalendarUtil.getTodayAtMidnight();
				if (today.before(aliasHeader.getEffectiveDate()))
				{
					canEditNetwork = Integer.valueOf(0);
					effectiveDate = aliasHeader.getEffectiveDate();
				}
			}
			
		} catch (Exception e) {
			logger.error("Find alias failed",e);
		}
		
		getRequest().setAttribute("canEditNetwork", canEditNetwork);
		getRequest().setAttribute("effectiveDate", effectiveDate);
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * @return String
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public String saveAndDone()
	{
		User loggedInUser = getLoggedInUser();
        Integer spnIdReq = null;

		model = getModel();
		if (loggedInUser == null)
		{
			return NOT_LOGGED_IN;
		}
		else if (loggedInUser.getRole() == null || loggedInUser.getRole().getId() == null || loggedInUser.getRole().getId().intValue() == ROLE_ID_PROVIDER.intValue())
		{
			return NOT_LOGGED_IN_AS_BUYER;
		}
		String message=null;
		Integer isMemberStatusAffected=Integer.valueOf(0);
		Boolean auditRequired=Boolean.FALSE;
		Integer buyerId2 = loggedInUser.getUserId();
		model.setBuyerId(buyerId2);		
		model.getSpnHeader().setModifiedBy(getCurrentBuyerResourceUserName());
		model.getSpnHeader().setContactPhone(stripPhoneDash(model.getSpnHeader().getContactPhone()));
		
		//Set the approval criteria
		approvalCriteriaMapper.setApprovalModel(model.getApprovalItems());
		approvalCriteriaMapper.setUserName(getCurrentBuyerResourceUserName());
		model.getSpnHeader().setApprovalCriterias(
				approvalCriteriaMapper.mapSPNApprovalModelToApprovalCriterias());
		//model.getSpnHeader().setApprovalCriterias(getSPNApprovalCriteria());
		model.getSpnHeader().setSpnDocuments(getSPNDocumentsFromSave());
		
		model.getSpnHeader().setExceptionsInd(false);
		
		Boolean isEditFlow = Boolean.FALSE;
		if(model.getSpnHeader().getSpnId() != null) {
			isEditFlow = Boolean.TRUE;
			if((model.getInitialExpInd()!=null && model.getExceptionRequired()!=null)&&(model.getInitialExpInd().booleanValue()== model.getExceptionRequired().booleanValue())){
				model.getSpnHeader().setExceptionsInd(model.getInitialExpInd());
			}
			try{
				if(createSPNServices.hasCampaign(model.getSpnHeader().getSpnId())){
					isMemberStatusAffected=model.getMemberStatusAffected().intValue();
					if(model.getAuditRequired()!=null){
						auditRequired=model.getAuditRequired();
					}
					message=createSuccessMessage(isMemberStatusAffected);
				}
				if(model.getExceptionRequired()&& createSPNServices.hasExceptions(model.getSpnHeader().getSpnId())){
					model.getSpnHeader().setExceptionsInd(true);
				}
			}
			catch (Exception e) {
				logger.error(e);
				throw new RuntimeException();
			}
		}
        try {
        	//do not add the buyer if the buyer is already available
        	boolean buyerAvailable = false;
        	List<SPNBuyer> spnBuyers = new ArrayList<SPNBuyer>();
        	if(model.getSpnHeader().getSpnId() != null && model.getBuyerId() != null && model.getBuyerId().intValue() > 0 ) {
        		
        		spnBuyers = spnBuyerServices.getListForSPN(model.getSpnHeader());
        	}
        	
        	for(SPNBuyer b : spnBuyers) {
        		if(b.getBuyerId().getBuyerId().intValue() ==  model.getBuyerId().intValue()){
        			buyerAvailable = true;
        			break;
        		}
        	}
        	
        	if(buyerAvailable == false) {
        		Buyer buyer = getBuyerServices().getBuyerForId(model.getBuyerId());
        		SPNBuyer spnbuyer = new SPNBuyer();
        		spnbuyer.setModifiedBy(getCurrentBuyerResourceUserName());
        		spnbuyer.setBuyerId(buyer);
        		model.getSpnHeader().getBuyer().add(spnbuyer);
        	}
        	
		} catch (Exception e1) {
			logger.error("Could not retrive Buyer ", e1);
			throw new RuntimeException();
		}

        try {
			
			
			Boolean isDocumentEdited = isDocumentEdited();
			SPNHeader spnHdr = model.getSpnHeader();
			spnHdr.setCriteriaLevel(StringUtils.isBlank(spnHdr.getCriteriaLevel()) ? "N/A" : spnHdr.getCriteriaLevel());
			spnHdr.setCriteriaTimeframe(StringUtils.isBlank(spnHdr.getCriteriaTimeframe()) ? "N/A" : spnHdr.getCriteriaTimeframe());
			spnHdr.setRoutingPriorityStatus(StringUtils.isBlank(spnHdr.getRoutingPriorityStatus()) ? "N/A" : spnHdr.getRoutingPriorityStatus());
			SPNHeader hdr = createSPNServices.saveSPN(model.getSpnHeader(), isDocumentEdited, isMemberStatusAffected, auditRequired);
			Integer spnId = hdr.getSpnId();// model.getSpnHeader().getSpnId();
			spnIdReq = spnId;
			String username = getCurrentBuyerResourceUserName();
			//step 1  of edit network 
			if(isEditFlow.booleanValue()){
				boolean hasCampaignExists = createSPNServices.hasCampaign(spnId);
				if(hasCampaignExists) {
					if(isMemberStatusAffected==1){
						if(isDocumentEdited != null && isDocumentEdited.booleanValue()) {
							// set status = SPN DOC EDITED 
							spnWorkflowSerivce.signal(WF_ENTITY_NETWORK, WF_STATUS_SPN_DOC_EDITED, spnId, username);
						} else {
							// set status = SPN EDITED
							spnWorkflowSerivce.signal(WF_ENTITY_NETWORK, WF_STATUS_SPN_EDITED, spnId, username);
						}
					} else {
					String saveComments =  model.getSpnHeader().getComments();
					spnWorkflowSerivce.saveWorkflowHistory(WF_ENTITY_NETWORK, WF_STATUS_SPN_EDITED_WITHOUT_STATUS_CHANGE , spnId, username, saveComments);
					}
				}
			} else {
				spnWorkflowSerivce.signal(WF_ENTITY_NETWORK,WF_STATUS_SPN_INCOMPLETE,spnId,username );
			}
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException();
		}
		
	
		try {
			// code to handle SL-18018 route to exceptions tab 
			if(model.getExceptionRequired()){
				getRequest().setAttribute("expIncludedInd", "true");
				getRequest().setAttribute("spnId", spnIdReq);
				if(spnIdReq != null){
					this.spnId = spnIdReq.toString();
				}
				return "exceptions_tab";
			}
			else if(this.isRoutingTierAccessibleByCurrentUser().booleanValue() && isEditFlow.booleanValue() == false) {
				return "routing_tiers_tab";
			}

			ServletActionContext.getRequest().getSession().setAttribute("SpnMessage", message);
			return "spn_monitor_tab";
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException();
	}
	}
	
	private Boolean isDocumentEdited()
	{
		if (model.getUploadDocData().getUploadDocFlag() != null && model.getUploadDocData().getUploadDocFlag().intValue() == 1 )
		{
			List<Integer> uploadTypeList = model.getUploadDocData().getUploadDocTypeList();
			if(uploadTypeList!=null){
				for(Integer doctype:uploadTypeList){
					  if(doctype==2||doctype==3){
						  return Boolean.TRUE;
					  }					  
				  }
			}
		}

		return Boolean.FALSE;
	}
	
	private String stripPhoneDash(String pPhone)
	{
		String phone = pPhone;
		if (phone != null)
		{
			phone = phone.replace("-","");
		}
		
		return phone;
	}

	private List<SPNDocument> getSPNDocumentsFromSave()
	{
		List<SPNDocument> spnDocumentList = new ArrayList<SPNDocument>();
		List<Integer> uploadedDocIdList = model.getUploadDocData().getUploadDocIdList();
		
		if (uploadedDocIdList == null)
		{
			return spnDocumentList;
		}
		
		for (int x = 0; x < uploadedDocIdList.size(); x++)
		{
			Integer docId = uploadedDocIdList.get(x);
			Integer docTypeId = null;
			String docDesc = null;
			String docTitle = null;
			
			if (model.getUploadDocData().getUploadDocTypeList() != null && model.getUploadDocData().getUploadDocTypeList().size() >= x)
			{
				docTypeId = model.getUploadDocData().getUploadDocTypeList().get(x);
			}
			if (model.getUploadDocData().getUploadDocDescList() != null && model.getUploadDocData().getUploadDocDescList().size() >= x)
			{
				docDesc = model.getUploadDocData().getUploadDocDescList().get(x);
			}
			if (model.getUploadDocData().getUploadDocTitleList() != null && model.getUploadDocData().getUploadDocTitleList().size() >= x)
			{
				docTitle = model.getUploadDocData().getUploadDocTitleList().get(x);
			}
			
			if (docTypeId == null || docDesc == null || docTitle == null)
			{
				continue;
			}
			
			SPNDocument spnDocument = new SPNDocument();
			Document document = new Document();
			document.setCreatedDate(CalendarUtil.getNow());
			document.setModifiedDate(CalendarUtil.getNow());
			document.setDescr(docDesc);
			document.setTitle(docTitle);
			document.setDocumentId(docId);
			document.setDocCategoryId(DOC_CATEGORY_ID_FOR_SPN);
			
			 
			spnDocument.setDocument(document);
			LookupSPNDocumentType docTypeObj = new LookupSPNDocumentType();
			docTypeObj.setId(docTypeId);
			spnDocument.setDocTypeId(docTypeObj);
			spnDocument.setModifiedBy(getCurrentBuyerResourceUserName());
			spnDocumentList.add(spnDocument);
		}
		
		return spnDocumentList;
	}
	

	
	@Override
	public void prepare() throws Exception
	{
		buyerId = getCurrentBuyerId();
		super.setModel(getModel());
	}

	/**
	 * 
	 * @return String
	 * @throws Exception 
	 */
	public String viewNetworkDetailsAjax() throws Exception
	{
		Integer networkId = getNetworkIdfromRequest();
		User loggedInUser = getLoggedInUser();
		
		if (loggedInUser == null)
		{
			return NOT_LOGGED_IN;
		}
		else if (loggedInUser.getRole() == null || loggedInUser.getRole().getId() == null || loggedInUser.getRole().getId().intValue() == ROLE_ID_PROVIDER.intValue())
		{
			return NOT_LOGGED_IN_AS_BUYER;
		}
		Integer buyerId = loggedInUser.getUserId();
		//R11.0 SL_19387 Setting the background check feature set
		if(buyerId != null){
			model.setRecertificationBuyerFeatureInd(createSPNServices.getBuyerFeatureSet(buyerId,"BACKGROUND_CHECK_RECERTIFICATION"));
		}
		
		return super.displayTabNetworkDetailsAjax(networkId.intValue());
	}
	
	public String viewRoutingTiersAjax() throws Exception
	{
		Integer networkId = getNetworkIdfromRequest();
		
		return super.displayTabRoutingTiersAjax(networkId.intValue());
	}
	
	/**
	 * 
	 * @param pApprovalItems
	 * @param luService
	 */
	public static void initViewServicesAndSkills(ApprovalModel pApprovalItems, LookupService luService)
	{
		ApprovalModel approvalItems2 = pApprovalItems;
		List<String> servicesAndSkills = new ArrayList<String>();
		if (approvalItems2 == null)
		{
			approvalItems2 = new ApprovalModel();
			
		}
		
		List<Integer> selectedSkills = approvalItems2.getSelectedMainServices();
		for (int x = 0; x < selectedSkills.size(); x++)
		{
			LookupSkills skill = luService.getSkillFromSkillId(selectedSkills.get(x));
			String servicesAndSkill = skill.getDescription();
			
			String appendSkills = "";
			List<Integer> selectedServices = approvalItems2.getSelectedSkills();
			List<LookupServiceType> servicesForSkill = luService.getServicesFromSkillId(skill.getId());
			for (int y = 0; y < servicesForSkill.size(); y++)
			{
				if (selectedServices.contains(servicesForSkill.get(y).getId()))
				{
					if (!"".equals(appendSkills))
					{
						appendSkills = appendSkills + ", ";
					}
					appendSkills = appendSkills + servicesForSkill.get(y).getDescription();
				}
			}
			if (!"".equals(appendSkills))
			{
				servicesAndSkill = servicesAndSkill + " > " + appendSkills;
			}
			servicesAndSkills.add(servicesAndSkill);
		}
		
		approvalItems2.setViewServicesAndSkills(servicesAndSkills);
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void validate() {
		super.validate();
		boolean addRequestAttrib = false;
		
		validateNameAndGeneralInformationPanel();
		//validateMinimumCompletedOrders();
		validateInsurance(getModel().getApprovalItems());
		
		// In case validation fails store model to 
		if(getActionErrors().size() > 0 ) {
			addRequestAttrib = true;
			model.getActionErrors().addAll(getActionErrors());
		}
		// do something for Filed Error
		if(getFieldErrors().size() > 0 ) {
			addRequestAttrib = true;
			// is this correct way ???  Sh0ould I be doing deep copy ??
			model.setFieldErrors(getFieldErrors());
		}
		if(addRequestAttrib) {
			ActionContext.getContext().getSession().put(SPN_NETWORKMODEL_IN_REQUEST,model );
		}
		//In case of no errors remoe any request attribute available
		else {
			if(ActionContext.getContext().getSession().get(SPN_NETWORKMODEL_IN_REQUEST)  != null) {
				ActionContext.getContext().getSession().remove(SPN_NETWORKMODEL_IN_REQUEST); 
			}
		}
		
		
	}

	// Handle s:checkboxlists, which do not have existing validators
	private void validateNameAndGeneralInformationPanel()
	{
		SPNCreateNetworkModel tmpModel = getModel();
		if(tmpModel == null)
			return;
		
		if (model.getSpnHeader().getSpnId() == null && createSPNServices.isSpnNameExistForBuyer(buyerId, tmpModel.getSpnHeader().getSpnName())) {
			addFieldError("spnHeader.spnName", this.getText("errors.netwrok.name.duplicated"));
		}
		
		if(tmpModel.getApprovalItems() != null)
		{
			if(tmpModel.getApprovalItems().getSelectedMainServices().size() == 0)
			{
				addFieldError("approvalItems.selectedMainServices", this.getText("errors.common.mainservice.required"));
			}
			if(tmpModel.getApprovalItems().getSelectedSkills().size() == 0)
			{
				addFieldError("approvalItems.selectedSkills", this.getText("errors.common.skills.required"));
			}
		}
		
	}
	
	/*
	 * Method to check whether the changes made to the SPN affect membership status or not*/
	@Transactional ( propagation = Propagation.REQUIRED)
	 public String checkMembershipStatusAffectAjax() {
		model = getModel();
		ApprovalModel approvalCriteriaNew = model.getApprovalItems();
		ApprovalModel approvalCriteriaOld = (ApprovalModel) getRequest()
				.getSession().getAttribute("SPNApprovalItems");
		Integer isMemberShipAffected = Integer.valueOf(0);
		Boolean isAuditRequired = false;
		if (approvalCriteriaOld != null) {
			if (isDocumentEdited() == true) {
				isMemberShipAffected = Integer.valueOf(1);
				if(model.getUploadDocData().getUploadDocTypeList().contains(3)){
					isAuditRequired=Boolean.TRUE;
				}
			}

			if (approvalCriteriaOld.getSelectedMinimumRating() == null) {
				if (approvalCriteriaNew.getSelectedMinimumRating().intValue() != 0) {
					isMemberShipAffected = 1;
				}
			} else {
				if (approvalCriteriaNew.getSelectedMinimumRating().intValue() > approvalCriteriaOld
						.getSelectedMinimumRating().intValue()) {
					isMemberShipAffected = 1;
				}
			}

			if (approvalCriteriaNew.getVehicleLiabilitySelected() == true) {
				if (approvalCriteriaOld.getVehicleLiabilitySelected() == null) {
					isMemberShipAffected = Integer.valueOf(1);
				} else {
					if (Integer.parseInt(approvalCriteriaNew
							.getVehicleLiabilityAmt()) > Integer
							.parseInt(approvalCriteriaOld
									.getVehicleLiabilityAmt())) {
						isMemberShipAffected = Integer.valueOf(1);
					} else if (approvalCriteriaNew
							.getVehicleLiabilityVerified() == true
							&& approvalCriteriaOld
									.getVehicleLiabilityVerified() == null) {
						isMemberShipAffected = Integer.valueOf(1);
					}
				}
			}
			if (approvalCriteriaNew.getCommercialGeneralLiabilitySelected() == true) {
				if (approvalCriteriaOld.getCommercialGeneralLiabilitySelected() == null) {
					isMemberShipAffected = Integer.valueOf(1);
				} else {
					if (Integer.parseInt(approvalCriteriaNew
							.getCommercialGeneralLiabilityAmt()) > Integer
							.parseInt(approvalCriteriaOld
									.getCommercialGeneralLiabilityAmt())) {
						isMemberShipAffected = Integer.valueOf(1);
					} else if (approvalCriteriaNew
							.getCommercialGeneralLiabilityVerified() == true
							&& approvalCriteriaOld
									.getCommercialGeneralLiabilityVerified() == null) {
						isMemberShipAffected = Integer.valueOf(1);
					}
				}
			}
			if (approvalCriteriaNew.getWorkersCompensationSelected() == true) {
				if (approvalCriteriaOld.getWorkersCompensationSelected() == null) {
					isMemberShipAffected = Integer.valueOf(1);
				} else {
					if (approvalCriteriaNew.getWorkersCompensationVerified() == true
							&& approvalCriteriaOld
									.getWorkersCompensationVerified() == null) {
						isMemberShipAffected = Integer.valueOf(1);
					}
				}
			}

			if (approvalCriteriaNew.getSelectedLanguages().size() > approvalCriteriaOld
					.getSelectedLanguages().size()) {
				isMemberShipAffected = Integer.valueOf(1);
			} else if (approvalCriteriaNew.getSelectedLanguages().size() <= approvalCriteriaOld
					.getSelectedLanguages().size()
					&& approvalCriteriaOld.getSelectedLanguages().size() != 0) {
				for (Integer language : approvalCriteriaNew
						.getSelectedLanguages()) {
					if (!approvalCriteriaOld.getSelectedLanguages().contains(
							language)) {
						isMemberShipAffected = Integer.valueOf(1);
					}
				}
			}

			if (!StringUtils.isEmpty(approvalCriteriaNew
					.getMinimumCompletedServiceOrders())) {
				if (approvalCriteriaOld.getMinimumCompletedServiceOrders() == null) {
					isMemberShipAffected = Integer.valueOf(1);
				} else {
					if (Integer.parseInt(approvalCriteriaNew
							.getMinimumCompletedServiceOrders()) > Integer
							.parseInt(approvalCriteriaOld
									.getMinimumCompletedServiceOrders()))
						isMemberShipAffected = 1;
				}
			}

			if (approvalCriteriaNew.getMeetingRequired() == true
					&& approvalCriteriaOld.getMeetingRequired() == null){
				isMemberShipAffected = Integer.valueOf(1);
				isAuditRequired = Boolean.TRUE;
			}
				

			if (approvalCriteriaNew.getSelectedMainServices().size() > approvalCriteriaOld
					.getSelectedMainServices().size())
				isMemberShipAffected = Integer.valueOf(1);
			else if (approvalCriteriaNew.getSelectedMainServices().size() <= approvalCriteriaOld
					.getSelectedMainServices().size()
					&& approvalCriteriaOld.getSelectedMainServices().size() != 0) {
				for (Integer services : approvalCriteriaNew
						.getSelectedMainServices()) {
					if (!approvalCriteriaOld.getSelectedMainServices()
							.contains(services)) {
						isMemberShipAffected = Integer.valueOf(1);
					}
				}
			}
			if (approvalCriteriaNew.getSelectedSkills().size() > approvalCriteriaOld
					.getSelectedSkills().size())
				isMemberShipAffected = Integer.valueOf(1);
			else if (approvalCriteriaNew.getSelectedSkills().size() <= approvalCriteriaOld
					.getSelectedSkills().size()
					&& approvalCriteriaOld.getSelectedSkills().size() != 0) {
				for (Integer skills : approvalCriteriaNew.getSelectedSkills()) {
					if (!approvalCriteriaOld.getSelectedSkills().contains(
							skills)) {
						isMemberShipAffected = Integer.valueOf(1);
					}
				}
			}
			if (approvalCriteriaNew.getSelectedSubServices1().size() > approvalCriteriaOld
					.getSelectedSubServices1().size())
				isMemberShipAffected = Integer.valueOf(1);
			else if (approvalCriteriaNew.getSelectedSubServices1().size() <= approvalCriteriaOld
					.getSelectedSubServices1().size()
					&& approvalCriteriaOld.getSelectedSubServices1().size() != 0) {
				for (Integer services : approvalCriteriaNew
						.getSelectedSubServices1()) {
					if (!approvalCriteriaOld.getSelectedSubServices1()
							.contains(services)) {
						isMemberShipAffected = Integer.valueOf(1);
					}
				}
			}
			if (approvalCriteriaNew.getSelectedSubServices2().size() > approvalCriteriaOld
					.getSelectedSubServices2().size())
				isMemberShipAffected = Integer.valueOf(1);
			else if (approvalCriteriaNew.getSelectedSubServices2().size() <= approvalCriteriaOld
					.getSelectedSubServices2().size()
					&& approvalCriteriaOld.getSelectedSubServices2().size() != 0) {
				for (Integer services : approvalCriteriaNew
						.getSelectedSubServices2()) {
					if (!approvalCriteriaOld.getSelectedSubServices2()
							.contains(services)) {
						isMemberShipAffected = Integer.valueOf(1);
					}
				}
			}
			if (approvalCriteriaNew.getSelectedResCredCategories().size() > approvalCriteriaOld
					.getSelectedResCredCategories().size())
				isMemberShipAffected = Integer.valueOf(1);
			else if (approvalCriteriaNew.getSelectedResCredCategories().size() <= approvalCriteriaOld
					.getSelectedResCredCategories().size()
					&& approvalCriteriaOld.getSelectedResCredCategories()
							.size() != 0) {
				for (Integer resCreds : approvalCriteriaNew
						.getSelectedResCredCategories()) {
					if (!approvalCriteriaOld.getSelectedResCredCategories()
							.contains(resCreds)) {
						isMemberShipAffected = Integer.valueOf(1);
					}
				}
			}
			if (approvalCriteriaNew.getSelectedResCredTypes().size() > approvalCriteriaOld
					.getSelectedResCredTypes().size())
				isMemberShipAffected = Integer.valueOf(1);
			else if (approvalCriteriaNew.getSelectedResCredTypes().size() <= approvalCriteriaOld
					.getSelectedResCredTypes().size()
					&& approvalCriteriaOld.getSelectedResCredTypes().size() != 0) {
				for (Integer resCredTypes : approvalCriteriaNew
						.getSelectedResCredTypes()) {
					if (!approvalCriteriaOld.getSelectedResCredTypes()
							.contains(resCredTypes)) {
						isMemberShipAffected = Integer.valueOf(1);
					}
				}
			}
			if (approvalCriteriaNew.getSelectedVendorCredCategories().size() > approvalCriteriaOld
					.getSelectedVendorCredCategories().size())
				isMemberShipAffected = Integer.valueOf(1);
			else if (approvalCriteriaNew.getSelectedVendorCredCategories()
					.size() <= approvalCriteriaOld
					.getSelectedVendorCredCategories().size()
					&& approvalCriteriaOld.getSelectedVendorCredCategories()
							.size() != 0) {
				for (Integer vendorCreds : approvalCriteriaNew
						.getSelectedVendorCredCategories()) {
					if (!approvalCriteriaOld.getSelectedVendorCredCategories()
							.contains(vendorCreds)) {
						isMemberShipAffected = Integer.valueOf(1);
					}
				}
			}
			if (approvalCriteriaNew.getSelectedVendorCredTypes().size() > approvalCriteriaOld
					.getSelectedVendorCredTypes().size())
				isMemberShipAffected = Integer.valueOf(1);
			
			//R11_0 CR-20289
			if(approvalCriteriaOld.getRecertificationInd() == null){
				if (null!= approvalCriteriaNew.getRecertificationInd() && approvalCriteriaNew.getRecertificationInd() == true){
					isMemberShipAffected = Integer.valueOf(1);
			}
          }
		} else if (approvalCriteriaNew.getSelectedVendorCredTypes().size() <= approvalCriteriaOld
				.getSelectedVendorCredTypes().size()
				&& approvalCriteriaOld.getSelectedVendorCredTypes().size() != 0) {
			for (Integer venCredTypes : approvalCriteriaNew
					.getSelectedVendorCredTypes()) {
				if (!approvalCriteriaOld.getSelectedVendorCredTypes().contains(
						venCredTypes)) {
					isMemberShipAffected = Integer.valueOf(1);
				}
			}
		}
		getRequest().setAttribute("isMemberShipAffected", isMemberShipAffected);
		getRequest().setAttribute("isAuditRequired", isAuditRequired);
		return SUCCESS;
	}

	public String createSuccessMessage(Integer isMembershipAffected){
		String message = null;
		if (isMembershipAffected.intValue() == 1) {
			DateFormat date=new SimpleDateFormat("MM/dd/yyyy");
			String effectiveDate = date.format(model.getSpnHeader()
					.getEffectiveDate());
			message = "The changes to <b>"
					+ model.getSpnHeader().getSpnName()
					+ "</b> have been <b>saved</b> and will become effective on <b>"
					+ effectiveDate + "</b>.";
		} else {
			message = "The changes to <b>"
					+ model.getSpnHeader().getSpnName()
					+ "</b> have been <b>saved</b> and do <b>not</b> affect membership status.";
		}
		return message;
	}

	/**
	 * @return the model
	 */
	public SPNCreateNetworkModel getModel() {
		return model;
	}

}