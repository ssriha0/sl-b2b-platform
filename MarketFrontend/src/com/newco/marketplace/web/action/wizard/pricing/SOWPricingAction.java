package com.newco.marketplace.web.action.wizard.pricing;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLWizardBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.SOWPricingTabDTO;
import com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.TabNavigationDTO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.utils.SOClaimedFacility;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * $Revision: 1.36 $ $Author: nsanzer $ $Date: 2008/06/10 17:32:47 $
 *
 */
public class SOWPricingAction extends SLWizardBaseAction implements Preparable, ModelDriven<SOWPricingTabDTO>
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SOWPricingAction.class);
	private SOWPricingTabDTO sowPricingTabDTO = new SOWPricingTabDTO();
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	private IBuyerBO buyerBO;
	
	private HashMap<Integer, String> pricingRadioOptions = new HashMap<Integer, String>();
	private HashMap<Integer, String> paymentRadioOptions = new HashMap<Integer, String>();
	private String previous;
	private String next;
	
	//SL-19820
	private ISODetailsDelegate soDetailsManager;
	String soID;

	public void prepare() throws Exception
	{
		//Check the session to see if the application is currently in the service order wizard workflow
		/* Sl-19820
		 * if (getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR == null )
		{*/
		if (getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR+"_"+getParameter(SO_ID)) == null )
		{
			throw new Exception("Invalid entry point into Service Order Wizard.");

		}

		//set the login credential information in the HTTPSession
		super.createCommonServiceOrderCriteria();
		
		//SL-19820
		String soId = getParameter(SO_ID);
		setAttribute(SO_ID, soId);
		String selectedSkuCategory = getParameter("skuCategory");
        setAttribute("skuCategoryHidden",selectedSkuCategory);
		this.soID = soId;
		
		String groupId = (String) getSession().getAttribute(OrderConstants.GROUP_ID+"_"+soId);
        setAttribute(OrderConstants.GROUP_ID,groupId);

		sowPricingTabDTO = (SOWPricingTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);

		//When Pricing Action gets executed, it means that the user has been to the Pricing tab.
		sowPricingTabDTO.setTabVisited(true);
		boolean taskLevelPrice = false;
		boolean isNonFunded=false;
		
		Buyer buyer = buyerBO.getBuyer(get_commonCriteria().getCompanyId());
		sowPricingTabDTO.setCancellationFee(buyer.getCancellationFee());

		setShowPartsSpendLimit();
		/*//SL-20527 : Setting SpendLimt labor and permit price
		setSpendLimitLabor();*/
		setShowBillingEstimate();
		
		setAllowBidOrders();
		
		//SL-19820
		//Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		ServiceOrderDTO soDTO = null;
		//ServiceOrderDTO soDTO = (ServiceOrderDTO)sessionMap.get("THE_SERVICE_ORDER");
		try{
			soDTO = soDetailsManager.getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
			setAttribute(THE_SERVICE_ORDER, soDTO);
				
		}catch(Exception e){
			System.err.println("Exception while fetching service order object");
		}
		if (null != soDTO) {
			Integer status = soDTO.getStatus();
			//if SO is in edit mode, get the non funded indicator from SO object.
			isNonFunded=soDTO.getNonFundedInd();
			if(OrderConstants.TASK_LEVEL_PRICING.equals(soDTO.getPriceType())){
				taskLevelPrice = true;
			}
			if (status != null && !status.equals(100)) {
				sowPricingTabDTO.setDraftOrder(false);
			}
		}else {
			//THE_SERVICE_ORDER == null means SOW is in create mode
			taskLevelPrice = buyerFeatureSetBO.validateFeature(get_commonCriteria().getCompanyId(), BuyerFeatureConstants.TASK_LEVEL);
			//if SO is in create mode, check the non funded feature from buyer feature set.
			isNonFunded=buyerFeatureSetBO.validateFeature(get_commonCriteria().getCompanyId(), BuyerFeatureConstants.NON_FUNDED);
		}
		
		sowPricingTabDTO.setNonFundedInd(isNonFunded);
		if(isNonFunded)
		{
			sowPricingTabDTO.setAllowBidOrders(false);
		}
		setAttribute("taskLevelPrice", taskLevelPrice);
		
		//SL-19820
		String entryTab = (String)getSession().getAttribute("entryTab_"+soId);
		setAttribute("entryTab", entryTab);
		
        Integer status=(Integer) getSession().getAttribute(OrderConstants.SOW_SERVICE_ORDER_STATUS_SOID+"_"+soId);
        setCurrentSOStatusCodeInRequest(status);
		
	}//end method prepare()


	public String setDtoForTab() throws IOException{
		String TabStatus="tabIcon ";
		SOWPricingTabDTO sOWPricingTabDTO = getModel();
		sOWPricingTabDTO.validate();
		if (sOWPricingTabDTO.getErrors().size() > 0){
			TabStatus="tabIcon error";
		}
		else if (sOWPricingTabDTO.getWarnings().size() > 0){
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
		response.getWriter().write(responseStr);

		return NONE;
	}

	private void setShowPartsSpendLimit()
	{
		/*
		boolean showPartsSpendLimit = false;
		SOWPartsTabDTO partsTabDto =(SOWPartsTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PARTS_TAB);
		if(partsTabDto != null && partsTabDto.getParts() != null)
		{
			if(partsTabDto.getParts().size() > 0)
				showPartsSpendLimit = true;
		}
		setAttribute("showPartsSpendLimit", showPartsSpendLimit);
		*/

		//Always show parts spend limit
		setAttribute("showPartsSpendLimit", true);
	}

	private void setShowBillingEstimate() throws Exception
	{
		try{
			boolean showBillingEstimate = false;

			//Getting BuyerId
			Integer buyerId = null;
			Map<String, Object> sessionMap = ActionContext.getContext().getSession();
			SecurityContext securityCntxt = (SecurityContext) sessionMap.get("SecurityContext");
			if (securityCntxt != null) {
				buyerId = securityCntxt.getCompanyId();
			}
			setAttribute("buyerId", buyerId);

			//Getting funding_type_id for the buyer
			Buyer buyer = isoWizardPersistDelegate.getBuyerAttrFromBuyerId(buyerId);
			if(buyer != null)
			{
				System.out.println("Funding_type_Id:" +buyer.getFundingTypeId());
				if(buyer.getFundingTypeId() == 20)
					showBillingEstimate = true;
			}
			setAttribute("showBillingEstimate", showBillingEstimate);

			//Set posting fee for display on the review screen.
			sowPricingTabDTO.setPostingFee(buyer.getPostingFee());
			System.out.println("showBillingEstimate " +showBillingEstimate);
		} catch (Exception ex) {
			ex.getStackTrace();
		}
	}
	
	private void setAllowBidOrders() {
		try{
			boolean allowBidOrders = false;
			boolean sealedBidOrders = false;

			//Getting BuyerId
			Integer buyerId = null;
			Map<String, Object> sessionMap = ActionContext.getContext().getSession();
			SecurityContext securityCntxt = (SecurityContext) sessionMap.get("SecurityContext");
			if (securityCntxt != null) {
				buyerId = securityCntxt.getCompanyId();
			}

			if(buyerId != null)
			{
				if (buyerFeatureSetBO.validateFeature(buyerId, "ALLOW_BID_ORDERS")) {
					allowBidOrders = true;
				}
				if(buyerFeatureSetBO.validateFeature(buyerId, "ALLOW_SEALED_BID_ORDERS")){
					sealedBidOrders = true;
			}
			}
			sowPricingTabDTO.setAllowBidOrders(allowBidOrders);
			sowPricingTabDTO.setSealedBidOrder(sealedBidOrders);
			
		} catch (Exception ex) {
			ex.getStackTrace();
		}
	}

	public SOWPricingAction(ISOWizardFetchDelegate fetchDelegate ) {
		this.fetchDelegate = fetchDelegate;
	}

	// AN ENTRY POINT, no execute()
	public String createEntryPoint() throws Exception {
		//SL-20527: Method to calculate spend limit incase of deleted task is considered for pricing
		return SUCCESS;
	}

	private void setupOrderGroupData()
	{
		// Child Orders of Order Group
		String groupId = (String)getSession().getAttribute(GROUP_ID);
		if(!StringUtils.isBlank(groupId))
		{
			/*OrderGroupVO ogVO = getOrderGroupDelegate().getOrderGroupByGroupId(groupId);
			sowPricingTabDTO = getModel();
			
			// Get the current spend limits for labor and parts
			Double currentSpendLimitLabor = ogVO.getFinalSpendLimitLabor();
			Double currentSpendLimitParts = ogVO.getFinalSpendLimitParts();			
			if(currentSpendLimitLabor == null)
			{
				currentSpendLimitLabor = ogVO.getDiscountedSpendLimitLabor();
			}			
			if(currentSpendLimitParts == null)
			{
				currentSpendLimitParts = ogVO.getDiscountedSpendLimitParts();
			}*/
			
			// Save the original value for use upon save
			//sowPricingTabDTO.setOgCurrentLaborSpendLimit(currentSpendLimitLabor);
			//sowPricingTabDTO.setOgCurrentPartsSpendLimit(currentSpendLimitParts);			
			
			// Init the value to be displayed on the tab/panel
			//sowPricingTabDTO.setOgLaborSpendLimit(currentSpendLimitLabor);
			//sowPricingTabDTO.setOgPartsSpendLimit(currentSpendLimitParts);
		}
	}

	public String createAndRoute() throws Exception {
		return null;
	}


	public String editEntryPoint() throws Exception {
		return null;
	}

	public String next() throws Exception {
		getRequest().setAttribute("previous","tab5");
		getRequest().setAttribute("next","tab6");
		TabNavigationDTO tabNav = _createNavPoint(OrderConstants.SOW_NEXT_ACTION,
				OrderConstants.SOW_PRICING_TAB,
				OrderConstants.SOW_EDIT_MODE,
					"SOW" );

		SOWPricingTabDTO sOWPricingTabDTO = getModel();
		//Due to formatting issues resulting from comma seperators in the .jsp number formatter,
		//the comma seperators must be stripped from the labor and parts spending limits
//		if (org.apache.commons.lang.StringUtils.isBlank(sowPricingTabDTO.getStrLaborSpendLimit()))
//		{
//			sowPricingTabDTO.setLaborSpendLimit(0.0);
//		}
//		Double templaborLimit =  StringUtils.getMonetaryNumber(sowPricingTabDTO.getStrLaborSpendLimit());
//		if(StringUtils.IsParsableNumber(sowPricingTabDTO.getStrLaborSpendLimit()))
//		{
//			sowPricingTabDTO.setLaborSpendLimit(Double.valueOf(sowPricingTabDTO.getStrLaborSpendLimit()));
//		}
//
//		if(StringUtils.IsParsableNumber(sowPricingTabDTO.getStrPartsSpendLimit()))
//		{
//			sowPricingTabDTO.setPartsSpendLimit(Double.valueOf(sowPricingTabDTO.getPartsSpendLimit()));
//		}

		sowPricingTabDTO.setBillingEstimate(sowPricingTabDTO.getBillingEstimate());

		SOWSessionFacility.getInstance().evaluateSOWBean(sOWPricingTabDTO, tabNav);
		this.setDefaultTab(SOWSessionFacility.getInstance().getGoingToTab());
		String goingTotab = SOWSessionFacility.getInstance().getGoingToTab();
		if (goingTotab == "Pricing"){
			this.setNext("tab5");
		}
		
		
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	public String previous() throws Exception {
		getRequest().setAttribute("next","tab5");
		TabNavigationDTO tabNav = _createNavPoint(OrderConstants.SOW_PREVIOUS_ACTION,
				OrderConstants.SOW_PRICING_TAB,
				OrderConstants.SOW_EDIT_MODE,
					"SOW" );

		SOWPricingTabDTO sOWPricingTabDTO = getModel();
		//Due to formatting issues resulting from comma seperators in the .jsp number formatter,
		//the comma seperators must be stripped from the labor and parts spending limits

//		if(StringUtils.IsParsableNumber(sowPricingTabDTO.getStrLaborSpendLimit()))
//		{
//			sowPricingTabDTO.setLaborSpendLimit(Double.valueOf(sowPricingTabDTO.getStrLaborSpendLimit()));
//		}
//
//		if(StringUtils.IsParsableNumber(sowPricingTabDTO.getStrPartsSpendLimit()))
//		{
//			sowPricingTabDTO.setPartsSpendLimit(Double.valueOf(sowPricingTabDTO.getPartsSpendLimit()));
//		}


		sowPricingTabDTO.setBillingEstimate(sowPricingTabDTO.getBillingEstimate());

		SOWSessionFacility.getInstance().evaluateSOWBean(sOWPricingTabDTO, tabNav);
		this.setDefaultTab(SOWSessionFacility.getInstance().getGoingToTab());
		String goingTotab = SOWSessionFacility.getInstance().getGoingToTab();
		if (goingTotab == "Pricing"){
			this.setNext("tab5");
		}
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	public String saveAsDraft() throws Exception {
		String returnValue = null;
		TabNavigationDTO tabNav = _createNavPoint(OrderConstants.SOW_SAVE_AS_DRAFT_ACTION,
				OrderConstants.SOW_PRICING_TAB,
				OrderConstants.SOW_EDIT_MODE,
					"SOW" );

		sowPricingTabDTO = getModel();
		//Due to formatting issues resulting from comma seperators in the .jsp number formatter,
		//the comma seperators must be stripped from the labor and parts spending limits
//		if(StringUtils.IsParsableNumber(sowPricingTabDTO.getStrLaborSpendLimit()))
//		{
//			sowPricingTabDTO.setLaborSpendLimit(Double.valueOf(sowPricingTabDTO.getStrLaborSpendLimit()));
//		}
//
//		if(StringUtils.IsParsableNumber(sowPricingTabDTO.getStrPartsSpendLimit()))
//		{
//			sowPricingTabDTO.setPartsSpendLimit(Double.valueOf(sowPricingTabDTO.getPartsSpendLimit()));
//		}
//


		sowPricingTabDTO.setBillingEstimate(sowPricingTabDTO.getBillingEstimate());
		
		soPricePopulation();
		//SL-20527 : Setting SpendLimt labor and permit price
		setSpendLimitLabor();
		SOWSessionFacility.getInstance().evaluateAndSaveSOWBean(sowPricingTabDTO, tabNav, isoWizardPersistDelegate, get_commonCriteria(), orderGroupDelegate);

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
			//SL-19820
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
		{   this.setDefaultTab(str);
			returnValue = GOTO_COMMON_WIZARD_CONTROLLER;
		}


		return returnValue;
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
	


	public void setModel(SOWPricingTabDTO dto){
		sowPricingTabDTO = dto;
	}

	public SOWPricingTabDTO getModel() {
		SOWPricingTabDTO dto =
							(SOWPricingTabDTO)
										SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);

		if(dto != null){
			return dto;
		}
		return sowPricingTabDTO;
	}
	
	public SOWPricingTabDTO getSowPricingTabDTO() {
		return sowPricingTabDTO;
	}

	public void setSowPricingTabDTO(SOWPricingTabDTO sowPricingTabDTO) {
		this.sowPricingTabDTO = sowPricingTabDTO;
	}


	public HashMap<Integer, String> getPricingRadioOptions() {
		return pricingRadioOptions;
	}


	public void setPricingRadioOptions(HashMap<Integer, String> pricingRadioOptions) {
		this.pricingRadioOptions = pricingRadioOptions;
	}


	public HashMap<Integer, String> getPaymentRadioOptions() {
		return paymentRadioOptions;
	}


	public void setPaymentRadioOptions(HashMap<Integer, String> paymentRadioOptions) {
		this.paymentRadioOptions = paymentRadioOptions;
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


	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	





	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}




	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}


	public String getSoID() {
		return soID;
	}


	public void setSoID(String soID) {
		this.soID = soID;
	}


	public ISODetailsDelegate getSoDetailsManager() {
		return soDetailsManager;
	}


	public void setSoDetailsManager(ISODetailsDelegate soDetailsManager) {
		this.soDetailsManager = soDetailsManager;
	}
}

/*
 * Maintenance History
 * $Log: SOWPricingAction.java,v $
 * Revision 1.36  2008/06/10 17:32:47  nsanzer
 * Fix for posting fee being left blank on the SO review and summary screens.  Now retrieves posting fee from the buyer table, for the review screen and the so_hdr table for the summary screen.
 *
 * Revision 1.35  2008/04/25 21:03:00  glacy
 * Shyam: Merged SL_DE_2008_04_30 branch to HEAD.
 *
 * Revision 1.34  2008/04/25 16:43:22  sgopala
 * 52724 fix
 *
 * Revision 1.33  2008/04/23 07:04:42  glacy
 * Shyam: Re-merge of I19_FreeTab branch to HEAD.
 *
 *
 * Revision 1.30.6.1  2008/04/21 01:34:25  sgopala
 * free tab changes i 19.5
 *
 * Revision 1.32  2008/04/23 05:19:39  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.30.12.1  2008/04/22 19:21:39  sgopin2
 * Sears00051136
 *
 * Revision 1.30  2008/03/27 18:58:09  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.29.8.1  2008/03/10 21:58:03  mhaye05
 * added logic to check the buyer's  max spend limit per so
 *
 */
