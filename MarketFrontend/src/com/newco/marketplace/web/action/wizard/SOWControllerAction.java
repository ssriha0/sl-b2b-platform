package com.newco.marketplace.web.action.wizard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.BuyerSkuVO;
import com.newco.marketplace.dto.vo.InsuranceRatingsLookupVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBFilterVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNetTierReleaseVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.web.action.base.SLWizardBaseAction;
import com.newco.marketplace.web.delegates.IOrderGroupDelegate;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.delegates.ISOMonitorDelegate;
import com.newco.marketplace.web.dto.CheckboxDTO;
import com.newco.marketplace.web.dto.SOWProviderSearchDTO;
import com.newco.marketplace.web.dto.SOWProvidersTabDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.TabNavigationDTO;
import com.newco.marketplace.web.security.OrderAssociationPage;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.orderfulfillment.client.OFHelper;


/**
 * $Revision: 1.55 $ $Author: gjacks8 $ $Date: 2008/05/29 19:40:31 $
 */

@OrderAssociationPage
public class SOWControllerAction extends SLWizardBaseAction implements Preparable
{
    /**
     *
     */
    private static final long serialVersionUID = 5940069966251369324L;
    private static final Logger logger = Logger.getLogger(SOWControllerAction.class.getName());
   
    private IOrderGroupDelegate orderGroupDelegate;
    private ISODetailsDelegate soDetailsManager;
    private ISOMonitorDelegate soMonitorDelegate;
    private IBuyerFeatureSetBO buyerFeatureSetBO;
    private OFHelper orderFulfillmentHelper;
    //Actions are always prototype so this is fine
    private String soID = null;
    private static final String JSON = "json";
    List<SPNetTierReleaseVO> routingPrioritiesList;
   
    //Sl-19820
    String workFlowDisplayTab;
    //Sl-21070
    String isBeingEdited;
   
    public String getWorkFlowDisplayTab() {
        return workFlowDisplayTab;
    }

    public void setWorkFlowDisplayTab(String workFlowDisplayTab) {
        this.workFlowDisplayTab = workFlowDisplayTab;
    }

    public String getIsBeingEdited() {
		return isBeingEdited;
	}

	public void setIsBeingEdited(String isBeingEdited) {
		this.isBeingEdited = isBeingEdited;
	}
	
    public void prepare() throws Exception
    {
        createCommonServiceOrderCriteria();   
        populateLookup();
        String displayTab=getParameter("displayTab");
        if(displayTab!=null) {
            //SL-19820
            //This is used while returning to WFM
            //getSession().setAttribute(Constants.SESSION.WORKFLOW_DISPLAY_TAB, displayTab);
            this.workFlowDisplayTab = displayTab;
        }
        //SL-19820
        //TODO :??
        if (getSession().getAttribute("displayTab") == null){
            //getSession().setAttribute("displayTab", displayTab);
        	setAttribute("displayTab", displayTab);
        }
        
        //Added for Incident 4148358(Sl-19820)
        soID = getParameter("soId");
        getSession().setAttribute(OrderConstants.EDITED_SO_ID, soID);

    }   

    public ArrayList<LookupVO> getLanguages() {
        return (ArrayList<LookupVO>)getSession().getServletContext().getAttribute("languages");
    }
   
    // EXECUTE METHODS SHOULD BE CONSIDERED AS AN ENTRY POINT
    public String execute() throws Exception
    {
       
        Map<String,Object> sessionMap = ActionContext.getContext().getSession();
        //LA...Commenting out for now...will use after 12/15
        //Remove the showReviewBtn var and only add when it is needed
        //sessionMap.remove("showReviewBtn");
        populateSPNListForBuyer();
        String action = null;
        String role="buyer";

        action = getParameter("action");
       
        String removeWFMFromSession = getParameter("removeFromSession");
        if(removeWFMFromSession != null && removeWFMFromSession.equalsIgnoreCase("WFM") && getSession().getAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR) != null) {
            getSession().removeAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR);
        }
       
        //SL-19820
        /*if(StringUtils.isBlank(action))
            action = (String)getSession().getAttribute("action");*/

        soID = getParameter("soId");
        setAttribute(OrderConstants.SO_ID, soID);
        String groupId = (String) getSession().getAttribute(OrderConstants.GROUP_ID+"_"+this.soID);
        setAttribute(OrderConstants.GROUP_ID,groupId);
        //Sl-19820
        /*if(StringUtils.isBlank(soID))
            soID = (String)getSession().getAttribute(OrderConstants.SO_ID);*/

          //SL-19820 added for available providers filter .		
		setAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA,
				getSession().getAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA + "_"+ this.soID));
		
        SOWSessionFacility sowSessionFacility  = SOWSessionFacility.getInstance();
       
        boolean isSLAdmin = get_commonCriteria().getSecurityContext().isSlAdminInd();
       
//        if (isSLAdmin){
//            //The roleId must be buyer or provider
//            Integer roleId = get_commonCriteria().getSecurityContext().getRoleId();
//           
//            if (!roleId.equals(OrderConstants.BUYER_ROLEID) && (!roleId.equals(OrderConstants.PROVIDER_ROLEID))){
//                return "adminDashboard";
//            }
//           
//        }
        if (get_commonCriteria().getSecurityContext().getRoleId().equals(OrderConstants.SIMPLE_BUYER_ROLEID)){

            if (action != null && action.equals("copy")) {
                //SL-19820 : TODO check what parameters need
                //to be passed in the xml to the redirect action
                return "redirectSimpleBuyerProviderSearchCopy";
            }
            else {
                logger.debug("Invoking setFlagOnServiceOrder from SOWControllerAction" );
                sowSessionFacility.setFlagOnServiceOrder(soID, OrderConstants.SO_VIEW_MODE_FLAG, fetchDelegate, get_commonCriteria().getSecurityContext());
                //setFlagOnServiceOrder
                //SL-19820 : TODO check what parameters need
                //to be passed in the xml to the redirect action
                return "redirectSimpleBuyerProviderSearch";
            }
        }
        else if (get_commonCriteria().getSecurityContext().getRoleId().equals(OrderConstants.BUYER_ROLEID)){
            sessionMap.put("roleType",OrderConstants.BUYER_ROLEID);
        }else{
            sessionMap.put("roleType",OrderConstants.PROVIDER_ROLEID);
        }
       
        if(action != null && action.equals("create"))
        {
            if(getSession().getAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR) != null){
                getSession().removeAttribute(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR);
            }
            if(getSession().getAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT) != null){
                getSession().removeAttribute(Constants.SESSION.CAME_FROM_ORDER_MANAGEMENT);
            }
            SOWSessionFacility.getInstance().exitSOW();
            //SL-19820
            //sessionMap.put(THE_SERVICE_ORDER, null);
            setAttribute(THE_SERVICE_ORDER, null);
            //Removing the sku selected by user for the next service order SL-17504
            getSession().removeAttribute("buyerSkuNameForSkuList");
            //SL-19820
            //getSession().removeAttribute("attachmentsList");
            //getSession().removeAttribute("documentVOList");
            //getSession().removeAttribute("skuIndicatorForDocAttach");
            getSession().removeAttribute("labourPriceForSku");
            getSession().removeAttribute("labourForSkuCheck");
            //getSession().removeAttribute("mainServiceCategoryId");
            getSession().removeAttribute("performanceScore");
            //Remove of remove attribute from session SL-17504
            ServiceOrder so = sowSessionFacility.createBareBonesServiceOrder(isoWizardPersistDelegate);
           
            //Session flag to indicate that the user is within the Service Order Wizard
            //SL-19820 
            //sessionMap.put(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR, true);
            sessionMap.put(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR+"_"+so.getSoId(), true);
            //SL-19820
            /*sessionMap.put(OrderConstants.SO_ID, so.getSoId());
            sessionMap.put("actionType", action);
            sessionMap.put("entryTab", "draft");*/
           
			//SL-19820
			//sessionMap.put(OrderConstants.SO_ID, so.getSoId());
			setAttribute(OrderConstants.SO_ID, so.getSoId());
            sessionMap.put("actionType_"+so.getSoId(), action);
            sessionMap.put("entryTab_"+so.getSoId(), "draft");
           
            //SL-19820 setting into request
            setAttribute("actionType", action);
            setAttribute("entryTab", "draft");
           
        	//SL-19820
			//setCurrentSOStatusCodeInSession(null);
			setCurrentSOStatusCodeInRequest(null);
			getSession().setAttribute(OrderConstants.SOW_SERVICE_ORDER_STATUS_SOID+"_"+this.soID, null);
			
            sessionMap.remove(GROUP_ID);
            sessionMap.remove(SOW_SO_ID_LIST);
            //SL-19820
            sessionMap.remove(GROUP_ID+"_"+this.soID);
            sessionMap.remove(SOW_SO_ID_LIST+"_"+this.soID);
            
        }
        else if( (action != null && action.equals("edit")) && !StringUtils.isBlank(soID))
        {
           
            //To Do handle it for SL-19820
            // Check if this is a child order
            checkIfOrderGroup(soID);

            //Session flag to indicate that the user is within the Service Order Wizard
            //SL-19820
            //sessionMap.put(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR, true);
            sessionMap.put(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR+"_"+soID, true);
            if (org.apache.commons.lang.StringUtils.isNotEmpty(getParameter("soId"))) {
                //SL-19820
                //sessionMap.put(OrderConstants.SO_ID, getParameter("soId"));
                setAttribute(OrderConstants.SO_ID, getParameter("soId"));
            }
            TabNavigationDTO tabNavigationDTO = _createNavPoint(null,
                      OrderConstants.SOW_SOW_TAB,
                      OrderConstants.SOW_EDIT_MODE, //Same as SOW_EDIT_MODE?
                      OrderConstants.SOW_STARTPOINT_SOM );
            //SL-19820
            //sessionMap.put("actionType", action);
            sessionMap.put("actionType_"+soID, action);
            //SL-19820 setting into request
            setAttribute("actionType", action);
            try {
                //SL-19820
                SOWSessionFacility.getInstance().evaluateSOWBean(null, tabNavigationDTO, fetchDelegate, get_commonCriteria().getSecurityContext(), soID);
                // Set the default value for filter
                setProviderSearchTabDTO(sessionMap);
            } catch (BusinessServiceException e) {
                logger.info("Caught Exception and ignoring",e);
            }   
           
            if(getParameter("tab") != null)
            {
            	//SL-19820 appending with so_id and set into request
                //sessionMap.put("entryTab", getParameter("tab"));
            	 sessionMap.put("entryTab_"+soID, getParameter("tab"));
            	 setAttribute("entryTab", getParameter("tab"));
            }
           
            String fromPage = getParameter("manageDocsFromSOM");
            if (fromPage != null){
                if (fromPage.equalsIgnoreCase("fromDocumentSOMWidget")){
                    getRequest().setAttribute("theTab", "dijit_layout_LinkPane_0");
                    getRequest().setAttribute("theAnchor", "documents_top");
                }
            }

        }
        else if((action != null && action.equals("copy")) && (soID != null )){
           
            //The following will be used as a flag in the front-end to control the display of zip-modal.
            //The flag will be set to true once the entered zip code is validated (ref: SOWAjaxAction.validateZipCode()) 
        	//SL-19820
            //getSession().setAttribute("startCopy", "false");
        	
        	setAttribute("startCopy", "false");
           
            // First call the create BareBonesServiceOrder   
            logger.debug("Invoking getStartPointAndInvalidate()" );
            sowSessionFacility.getStartPointAndInvalidate(fetchDelegate, get_commonCriteria().getSecurityContext());
            //Session flag to indicate that the user is within the Service Order Wizard
            //SL-19820
            // sessionMap.put(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR, true);
           
            ServiceOrder so = sowSessionFacility.createBareBonesServiceOrder(isoWizardPersistDelegate);
            sessionMap.put(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR+"_"+so.getSoId(), true);
            getSession().setAttribute("startCopy_"+so.getSoId(), "false");
           
            // Set status to draft
            so.setStatus(OrderConstants.DRAFTED_STRING);
            //Sl-19820
            //setCurrentSOStatusCodeInSession(OrderConstants.DRAFT_STATUS);
            setCurrentSOStatusCodeInRequest(null);
            //Sl-19820
            getSession().setAttribute(OrderConstants.SOW_SERVICE_ORDER_STATUS_SOID+"_"+so.getSoId(), null);
            so.setSubStatus(null);
           
            //SL-19820
            //sessionMap.put(OrderConstants.SO_ID, getParameter("soId"));
            setAttribute(OrderConstants.SO_ID, getParameter("soId"));
           
            // Clear out session variables relating to Order Groups
            sessionMap.remove(GROUP_ID);
            sessionMap.remove(SOW_SO_ID_LIST);
            //SL-19820
            sessionMap.remove(GROUP_ID+"_"+so.getSoId());
            sessionMap.remove(SOW_SO_ID_LIST+"_"+so.getSoId());
            
            TabNavigationDTO tabNavigationDTO = _createNavPoint(null,
                      OrderConstants.SOW_SOW_TAB,
                      OrderConstants.SOW_EDIT_MODE, //Same as SOW_EDIT_MODE?
                      OrderConstants.SOW_STARTPOINT_SOM );
            
            //SL-19820
            //sessionMap.put("actionType", action);
            sessionMap.put("actionType_"+so.getSoId(), action);
            setAttribute("actionType", action);
            
            try {
                SOWSessionFacility.getInstance().evaluateSOWBeanForCopy(null, tabNavigationDTO,
                        fetchDelegate,isoWizardPersistDelegate,so.getSoId(),
                        _commonCriteria.getVendBuyerResId(),_commonCriteria.getCompanyId(), get_commonCriteria().getSecurityContext());
                //SL-19820
                //sessionMap.put(OrderConstants.SO_ID, so.getSoId());
                setAttribute(OrderConstants.SO_ID, so.getSoId());
                soID = so.getSoId();
                // Set the default value for filter
                setProviderSearchTabDTO(sessionMap);
            } catch (BusinessServiceException e) {
                logger.info("Caught Exception and ignoring",e);
            }   
           
            if(getParameter("tab") != null)
            {
            	//SL-19820
                //sessionMap.put("entryTab", getParameter("tab"));
            	sessionMap.put("entryTab_"+(String)getAttribute(OrderConstants.SO_ID), getParameter("tab"));
            	setAttribute("entryTab", getParameter("tab"));
            }
        }
        //SL-19820 : CAME_FROM_WORKFLOW_MONITOR will be obtained as request param
        //if(sessionMap.get(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR) != null) {
        if(StringUtils.isNotBlank(getParameter(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR))) {
        	sessionMap.put(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR+"_"+soID, getParameter(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR));
            if(StringUtils.isBlank(getParameter("defaultTab"))) {
                PBFilterVO filter;
                if(soID == null) {
                	//SL-19820
                    //String groupID = (String)sessionMap.get(OrderConstants.GROUP_ID);
                	String groupID = (String)getAttribute(OrderConstants.GROUP_ID);
                    filter = soDetailsManager.getDestinationTabForSO(groupID);
                    if(groupID == null) {
                        logger.error("Error condition: soId & groupId are both null, this will cause an error condition. action: " + action);
                    }
                } else {
                    filter = soDetailsManager.getDestinationTabForSO(soID);
                }
                String destinationSubTab = "";
                if(filter == null) {
                    destinationSubTab = "";
                    logger.error("Error condition: soId: " + soID + ", groupID: " + (String)getAttribute(OrderConstants.GROUP_ID)
                            + ", user's action: " + action);
                }else {
                    destinationSubTab = filter.getDestinationSubTab();
                }
                _setTabs(role, destinationSubTab);
            } else {
                _setTabs(role, getParameter("defaultTab"));
            }
        } else {
            _setTabs(role, getParameter("defaultTab"));
        }
        /* To populate the session with the values needed for the Order Express Menu tab in SOW(Edit Mode)*/
        if(action!= null && action.equals("edit")){

            String soId = getParameter("soId");   
            //SL-19820
            //String groupId = (String)getSession().getAttribute(OrderConstants.GROUP_ID);
            groupId = (String) getAttribute(OrderConstants.GROUP_ID);
            /*For Group SO's*/       
            if (StringUtils.isNotEmpty(groupId))
            {
                OrderGroupVO orderGroupVO = soMonitorDelegate.getOrderGroupById(groupId);
                List<ServiceOrderSearchResultsVO> soList = soMonitorDelegate.getServiceOrdersForGroup(groupId);
               
                // Set extra OrderGroupVO widget attributes from order child SO
                Integer roleId = ((SecurityContext) getSession().getAttribute("SecurityContext")).getRoleId();
                ServiceOrderDTO serviceOrderDTO = ((ArrayList<ServiceOrderDTO>)ObjectMapper.convertVOToDTO(soList.subList(0, 1), roleId)).get(0);
                orderGroupVO.setStatus(serviceOrderDTO.getPrimaryStatus());
                orderGroupVO.setBuyerWidget(serviceOrderDTO.getBuyerWidget());
                orderGroupVO.setEndCustomerWidget(serviceOrderDTO.getEndCustomerWidget());
                orderGroupVO.setLocationWidget(serviceOrderDTO.getLocationWidget());
                orderGroupVO.setAppointmentDates(serviceOrderDTO.getAppointmentDates());
                orderGroupVO.setServiceWindow(serviceOrderDTO.getServiceWindow());
               
                // Set these session variables for use in QuickLinks and various tabs.
                //SL-19820
                /*setCurrentSOStatusCodeInSession(serviceOrderDTO.getStatus());
                getSession().setAttribute(OrderConstants.GROUP_ID, groupId);
                getSession().setAttribute(THE_GROUP_ORDER, orderGroupVO);
                getSession().setAttribute(OrderConstants.SO_CHILD_ID, soId);
                getSession().setAttribute(OrderConstants.SO_ID, null);
                getSession().setAttribute(THE_SERVICE_ORDER, null);*/
                
                setCurrentSOStatusCodeInRequest(serviceOrderDTO.getStatus());
                getSession().setAttribute(OrderConstants.SOW_SERVICE_ORDER_STATUS_SOID+"_"+soId, serviceOrderDTO.getStatus());
                //SL-19820
                getSession().setAttribute(OrderConstants.GROUP_ID+"_"+this.soID, groupId);
                setAttribute(OrderConstants.GROUP_ID, groupId);
                setAttribute(THE_GROUP_ORDER, orderGroupVO);
                setAttribute(OrderConstants.SO_CHILD_ID, soId);
                setAttribute(OrderConstants.SO_ID, null);
                setAttribute(THE_SERVICE_ORDER, null);
            }else if (StringUtils.isNotEmpty(soId))
            {
                /*For simple SO's*/
                ServiceOrderDTO theCurrentSO = null;
                //SL-19820 : Commenting code since accepted resource is out of scope in creation
               /* if(getSession().getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID)!=null) {
                    Integer resId = Integer.parseInt((String)getSession().getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID));
                    theCurrentSO = soDetailsManager.getServiceOrder(soId,  get_commonCriteria().getRoleId(), resId);
                }
                else{
                    theCurrentSO = soDetailsManager.getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
                }*/
                theCurrentSO = soDetailsManager.getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
                if(theCurrentSO != null){
                	//Sl-19820 setting into request
                    //getSession().setAttribute(THE_SERVICE_ORDER, theCurrentSO);
                    setAttribute(THE_SERVICE_ORDER, theCurrentSO);
                }else{
                	//Sl-19820 setting into request
                    //getSession().setAttribute(THE_SERVICE_ORDER, null);
                    setAttribute(THE_SERVICE_ORDER, null);
                }
                if(theCurrentSO != null && theCurrentSO.getStatus() != null)
                {    
                	//SL-19820  setting into request
                   // setCurrentSOStatusCodeInSession(theCurrentSO.getStatus());
                	setCurrentSOStatusCodeInRequest(theCurrentSO.getStatus());
                	//SL-19820  setting into session for using in SOW
                	getSession().setAttribute(OrderConstants.SOW_SERVICE_ORDER_STATUS_SOID+"_"+soId, theCurrentSO.getStatus());
                }
                else
                {    
                	//SL-19820  setting into request
                    //setCurrentSOStatusCodeInSession(null);
                    setCurrentSOStatusCodeInRequest(null);
                  //SL-19820  setting into session for using in SOW
                    getSession().setAttribute(OrderConstants.SOW_SERVICE_ORDER_STATUS_SOID+"_"+soId, null);
                }
                if(soId != null)
                {
                	//SL-19820  setting into request
                   // getSession().setAttribute(OrderConstants.SO_ID, soId);
                	setAttribute(OrderConstants.SO_ID, soId);
                }
                else
                {
                	//SL-19820  setting into request
                    //getSession().setAttribute(OrderConstants.SO_ID, null);
                	setAttribute(OrderConstants.SO_ID, null);
                }
            }           
        }//if
       
       
        /* To populate the session with the values needed for the Order Express Menu tab in SOW(Edit Mode)*/
        /* Commenting code as it is exact replica of above else block 
         * if(action!= null && action.equals("edit")){

            String soId = getParameter("soId");   
            String groupId = (String)getSession().getAttribute(OrderConstants.GROUP_ID);
            For Group SO's       
            if (StringUtils.isNotEmpty(groupId))
            {
                OrderGroupVO orderGroupVO = soMonitorDelegate.getOrderGroupById(groupId);
                List<ServiceOrderSearchResultsVO> soList = soMonitorDelegate.getServiceOrdersForGroup(groupId);
               
                // Set extra OrderGroupVO widget attributes from order child SO
                Integer roleId = ((SecurityContext) getSession().getAttribute("SecurityContext")).getRoleId();
                ServiceOrderDTO serviceOrderDTO = ((ArrayList<ServiceOrderDTO>)ObjectMapper.convertVOToDTO(soList.subList(0, 1), roleId)).get(0);
                orderGroupVO.setStatus(serviceOrderDTO.getPrimaryStatus());
                orderGroupVO.setBuyerWidget(serviceOrderDTO.getBuyerWidget());
                orderGroupVO.setEndCustomerWidget(serviceOrderDTO.getEndCustomerWidget());
                orderGroupVO.setLocationWidget(serviceOrderDTO.getLocationWidget());
                orderGroupVO.setAppointmentDates(serviceOrderDTO.getAppointmentDates());
                orderGroupVO.setServiceWindow(serviceOrderDTO.getServiceWindow());
               
                // Set these session variables for use in QuickLinks and various tabs.
                setCurrentSOStatusCodeInSession(serviceOrderDTO.getStatus());
                getSession().setAttribute(OrderConstants.GROUP_ID, groupId);
                getSession().setAttribute(THE_GROUP_ORDER, orderGroupVO);
                getSession().setAttribute(OrderConstants.SO_CHILD_ID, soId);
                getSession().setAttribute(OrderConstants.SO_ID, null);
                getSession().setAttribute(THE_SERVICE_ORDER, null);
            }else if (StringUtils.isNotEmpty(soId))
            {
                For simple SO's
                ServiceOrderDTO theCurrentSO = null;
                if(getSession().getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID)!=null) {
                    Integer resId = Integer.parseInt((String)getSession().getAttribute(Constants.SESSION.SOD_ROUTED_RES_ID));
                    theCurrentSO = soDetailsManager.getServiceOrder(soId,  get_commonCriteria().getRoleId(), resId);
                }
                else{
                    theCurrentSO = soDetailsManager.getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
                }
                if(theCurrentSO != null){
                    getSession().setAttribute(THE_SERVICE_ORDER, theCurrentSO);
                }else{
                    getSession().setAttribute(THE_SERVICE_ORDER, null);
                }
                if(theCurrentSO != null && theCurrentSO.getStatus() != null)
                {                   
                    setCurrentSOStatusCodeInSession(theCurrentSO.getStatus());
                }
                else
                {                   
                    setCurrentSOStatusCodeInSession(null);
                }
                if(soId != null)
                {
                    getSession().setAttribute(OrderConstants.SO_ID, soId);
                }
                else
                {
                    getSession().setAttribute(OrderConstants.SO_ID, null);
                }
            }           
        }*///if
       
        String showSaveAndAutoPost = "false";
        String showSoCreationSku="false";
       
        ServiceOrderDTO theCurrentSO = null;
        //SL-19820
        /*if (sessionMap.get(THE_SERVICE_ORDER) != null) {
            theCurrentSO = (ServiceOrderDTO) sessionMap.get(THE_SERVICE_ORDER);
        }*/
        if (getAttribute(THE_SERVICE_ORDER) != null) {
            theCurrentSO = (ServiceOrderDTO) getAttribute(THE_SERVICE_ORDER);
        }
       
        if (theCurrentSO != null && theCurrentSO.getStatus().equals(OrderConstants.DRAFT_STATUS)) {   
            
        	//SL-19820  setting into session for using in SOW
        	getSession().setAttribute(OrderConstants.SOW_SERVICE_ORDER_STATUS_SOID+"_"+this.soID, theCurrentSO.getStatus());
            boolean newSo = getOrderFulfillmentHelper().isNewSo(soID);
            if(newSo){
                int buyerId = get_commonCriteria().getSecurityContext().getCompanyId();
                Boolean routeFeatureAvailable = getBuyerFeatureSetBO().validateFeature(buyerId, BuyerFeatureConstants.AUTO_ROUTE);
                Boolean isSKUFeatureOn=getBuyerFeatureSetBO().validateFeature(buyerId,BuyerFeatureConstants.SO_CREATION_SKU);
                if (routeFeatureAvailable) {
                    showSaveAndAutoPost = "true";
                }
                if (isSKUFeatureOn) {
                    showSoCreationSku = "true";
                }
               
            }
           
        }
        if (theCurrentSO == null)
        {   int buyerId = get_commonCriteria().getSecurityContext().getCompanyId();
            Boolean isSKUFeatureOn=getBuyerFeatureSetBO().validateFeature(buyerId,BuyerFeatureConstants.SO_CREATION_SKU);
            if (isSKUFeatureOn) {
                showSoCreationSku = "true";
            }
            //SL-19820
            try{
            	ServiceOrder serviceOrder = soDetailsManager.getServiceOrderStatusAndCompletdDate(soID);
            	if(null!=serviceOrder && null != serviceOrder.getWfStateId()){
            		if(OrderConstants.DRAFT_STATUS == serviceOrder.getWfStateId().intValue()){
            			Boolean routeFeatureAvailable = getBuyerFeatureSetBO().validateFeature(buyerId, BuyerFeatureConstants.AUTO_ROUTE);
            			 if (routeFeatureAvailable) {
                             showSaveAndAutoPost = "true";
                         }
            		}
            	}
            }catch(Exception e){
            	logger.error("Error getting SO status in SOWControllerAction.execute() for soId : "+ soID);
            }
            
        }
         
        //SL-19820
        sessionMap.put("showSaveAndAutoPost_"+soID, showSaveAndAutoPost);
        sessionMap.put("showSoCreationSku", showSoCreationSku);
        return SUCCESS;
    }

    private void checkIfOrderGroup(String soId)
    {
        ServiceOrder serviceOrder = fetchDelegate.getServiceOrder(soID);
       
        if(serviceOrder != null)
        {
            String groupId = serviceOrder.getGroupId();
            getSession().removeAttribute("GID");
           
            if(!StringUtils.isBlank(groupId))
            {
                List<String> soIdList = new ArrayList<String>();
               
                //SL-19820
                //getSession().setAttribute(OrderConstants.GROUP_ID, groupId);
                //getSession().setAttribute("GID", groupId);
                getSession().setAttribute(OrderConstants.GROUP_ID+"_"+this.soID, groupId);
                setAttribute(OrderConstants.GROUP_ID, groupId);
                List<ServiceOrderSearchResultsVO> soList = orderGroupDelegate.getChildServiceOrders(groupId);
                for(ServiceOrderSearchResultsVO vo : soList)
                {
                    soIdList.add(vo.getSoId());
                }
                //SL-19820
                //getSession().setAttribute(OrderConstants.SOW_SO_ID_LIST, soIdList);
                //getSession().setAttribute(OrderConstants.SO_CHILD_ID, soId);
                getSession().setAttribute(OrderConstants.SOW_SO_ID_LIST+"_"+groupId, soIdList);
                setAttribute(OrderConstants.SOW_SO_ID_LIST, soIdList);
                setAttribute(OrderConstants.SO_CHILD_ID, soId);
            }
            else
            {
                getSession().removeAttribute(OrderConstants.GROUP_ID);
                //Sl-19820
                getSession().removeAttribute(OrderConstants.GROUP_ID+"_"+this.soID);
                getSession().removeAttribute(OrderConstants.SOW_SO_ID_LIST+"_"+groupId);
                
                getSession().removeAttribute(OrderConstants.SOW_SO_ID_LIST);
                getSession().removeAttribute(OrderConstants.SO_CHILD_ID);
            }
        }
        else
        {
            getSession().removeAttribute(OrderConstants.GROUP_ID);
            getSession().removeAttribute(OrderConstants.SOW_SO_ID_LIST);
            getSession().removeAttribute(OrderConstants.SO_CHILD_ID);
            getSession().removeAttribute("GID");
        }
       
    }
   
    private void  populateLookup()
    {
        if (getSession().getServletContext().getAttribute(ProviderConstants.CREDENTAILS_LIST) == null) {
               
            ArrayList<LookupVO> credentials = null;
            try {
                credentials = getLookupManager().getCredentials();
            } catch (BusinessServiceException e) {
                logger.info("Caught Exception and ignoring",e);
            }
            getSession().getServletContext().setAttribute(ProviderConstants.CREDENTAILS_LIST, credentials);               
        }
       
        if (getSession().getServletContext().getAttribute(ProviderConstants.LANGUAGES_LIST) == null) {
           
            ArrayList<LookupVO> languages = null;           
            try {
                languages = getLookupManager().getLanguages();
            } catch (BusinessServiceException e) {
                logger.info("Caught Exception and ignoring",e);
            }           
            getSession().getServletContext().setAttribute(ProviderConstants.LANGUAGES_LIST, languages);               
        }
        // get list of select provider network in COMPLETE status for given buyerID
        // populateSPNListForBuyer();
        // get minimum ratings list
        populateMinimumRatings();
        // get provider distance list
        populateProviderDistance();
        //get the insurance ratings list
        populateInsuranceRatings();
       
       
    }


    private void populateProviderDistance() {
        if(getSession().getServletContext().getAttribute(ProviderConstants.DISTANCE_LIST) == null){
            List<LookupVO> providerDistanceList = new ArrayList<LookupVO>();
            try{
                providerDistanceList = getLookupManager().getProviderDistanceList();
            } catch (BusinessServiceException e) {
                logger.info("Caught Exception while getting distance list and ignoring",e);
            }
            getSession().getServletContext().setAttribute(ProviderConstants.DISTANCE_LIST, providerDistanceList);
        }
       
    }

    private void populateMinimumRatings() {
        if(getSession().getServletContext().getAttribute(ProviderConstants.RATING_LIST) == null){
            List<LookupVO> minRatingsList = new ArrayList<LookupVO>();
            try{
                minRatingsList = getLookupManager().getMinimumRatings();
            } catch (BusinessServiceException e) {
                logger.info("Caught Exception while getting min ratings and ignoring",e);
            }
            getSession().getServletContext().setAttribute(ProviderConstants.RATING_LIST, minRatingsList);
        }
       
    }

    private void populateSPNListForBuyer() {
        if (ActionContext.getContext().getSession().get(ProviderConstants.SPN_LIST) != null) {
            ActionContext.getContext().getSession().remove(ProviderConstants.SPN_LIST);
        }
           
            List<SPNMonitorVO> spnlist = null;
            List<LookupVO> performanceLevelDropdownList = null;
            try {
                Integer buyerId = get_commonCriteria().getSecurityContext().getCompanyId();
                spnlist = getLookupManager().getSPNetList(buyerId);
                performanceLevelDropdownList = getLookupManager().getPerformanceLevelList();               
            } catch (BusinessServiceException e) {
                logger.info("Caught Exception while getting spn list and ignoring",e);
            }           
            ActionContext.getContext().getSession().put(ProviderConstants.SPN_LIST, spnlist);               
            ActionContext.getContext().getSession().put(ProviderConstants.PERFORMANCE_LEVEL_DROPDOWN_LIST, performanceLevelDropdownList);
            populatePerformanceLevelOptions();
           
    }
   
    private void populatePerformanceLevelOptions()
    {
        if(getRequest().getSession().getAttribute("perfLevelCheckboxes") == null)
        {
            List<CheckboxDTO> checkboxList = new ArrayList<CheckboxDTO>();
            List<LookupVO> perfLevels = (List<LookupVO>)ActionContext.getContext().getSession().get(ProviderConstants.PERFORMANCE_LEVEL_DROPDOWN_LIST);
            CheckboxDTO dto;
            for(LookupVO vo : perfLevels)
            {
                dto = new CheckboxDTO();
                dto.setLabel(vo.getDescr());
                dto.setValue(vo.getId());           
                dto.setSelected(false);
                checkboxList.add(dto);
            }
            getRequest().getSession().setAttribute("perfLevelCheckboxes", checkboxList);
        }
       
    }
    /**This method is used to populate the default search criteria(spnId).
     * @param sessionMap
     */
    private void setProviderSearchTabDTO(Map<String,Object> sessionMap){
        // Set the default value for filter
    	//SL-19820
    	String soId = (String) getAttribute(OrderConstants.SO_ID);
        if(sessionMap.get(ProviderConstants.PROVIDERS_FILTER_CRITERIA+"_"+soId) == null){
            SOWProviderSearchDTO providerSearchDto = new SOWProviderSearchDTO();
            SOWProvidersTabDTO modelScopeDTO =
            (SOWProvidersTabDTO)
                            SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PROVIDERS_TAB);
            List<SPNMonitorVO> spnList = (List<SPNMonitorVO>) ActionContext.getContext().getSession().get(ProviderConstants.SPN_LIST);
            if(modelScopeDTO.getSpnId()!=null){
            	//SL-19820
                //sessionMap.put(ProviderConstants.OLD_SPN_ID, modelScopeDTO.getSpnId().toString());
            	sessionMap.put(ProviderConstants.OLD_SPN_ID+"_"+soId, modelScopeDTO.getSpnId().toString());
            	setAttribute(ProviderConstants.OLD_SPN_ID, modelScopeDTO.getSpnId().toString());
                for(SPNMonitorVO spn : spnList){
                    if(spn.getSpnId() == modelScopeDTO.getSpnId()){
                        providerSearchDto.setSpn(modelScopeDTO.getSpnId());   
                        providerSearchDto.setPerformanceScore((null==modelScopeDTO.getPerformanceScore()) ? 0.00 : modelScopeDTO.getPerformanceScore());
                        providerSearchDto.setRoutingPriorityApplied((null==modelScopeDTO.getRoutingPriorityAppliedInd()) ? false : modelScopeDTO.getRoutingPriorityAppliedInd());
                        if(null!=modelScopeDTO.getPerformanceScore()){
                        	//Sl-19820
                        	//sessionMap.put(ProviderConstants.PERFORMANCE_SCORE, modelScopeDTO.getPerformanceScore().intValue());
                        	sessionMap.put(ProviderConstants.PERFORMANCE_SCORE+"_"+soId, modelScopeDTO.getPerformanceScore().intValue());
                        	setAttribute(ProviderConstants.PERFORMANCE_SCORE, modelScopeDTO.getPerformanceScore().intValue());
                        }
                        //sessionMap.put(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING, modelScopeDTO.getRoutingPriorityAppliedInd());
                        //sessionMap.put(ProviderConstants.PROVIDERS_FILTER_CRITERIA, providerSearchDto);
                        sessionMap.put(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING+"_"+soId, modelScopeDTO.getRoutingPriorityAppliedInd());
                        sessionMap.put(ProviderConstants.PROVIDERS_FILTER_CRITERIA+"_"+soId, providerSearchDto);
                        setAttribute(ProviderConstants.ROUTING_PRIORITY_APPLIED_FOR_FILTERING, modelScopeDTO.getRoutingPriorityAppliedInd());
                        setAttribute(ProviderConstants.PROVIDERS_FILTER_CRITERIA, providerSearchDto);
                        break;
                    }                       
                }               
            }
        }
    }
    /**This private method is used to populate the insurance ratings for provider search widget.
     *
     */
    private void populateInsuranceRatings() {
        if(getSession().getServletContext().getAttribute(ProviderConstants.GENERAL_LIABILITY_RATING_LIST) == null
                ||getSession().getServletContext().getAttribute(ProviderConstants.VEHICLE_LIABILITY_RATING_LIST) == null
                ||getSession().getServletContext().getAttribute(ProviderConstants.WORKERS_COMPENSATION_RATING_LIST) == null
                ||getSession().getServletContext().getAttribute(ProviderConstants.ADDITIONAL_INSURANCE_LIST) == null){
       
            InsuranceRatingsLookupVO insuranceLookupVO = new InsuranceRatingsLookupVO();
            try{
                insuranceLookupVO = getLookupManager().getInsuranceRatings();
            } catch (DelegateException e) {
                logger.info("Caught Exception while getting insurance ratings and ignoring",e);
            }
            getSession().getServletContext().setAttribute(ProviderConstants.GENERAL_LIABILITY_RATING_LIST, insuranceLookupVO.getGeneralLiabilityRatingList());
            getSession().getServletContext().setAttribute(ProviderConstants.VEHICLE_LIABILITY_RATING_LIST, insuranceLookupVO.getVehicleLiabilityRatingList());
            getSession().getServletContext().setAttribute(ProviderConstants.WORKERS_COMPENSATION_RATING_LIST, insuranceLookupVO.getWorkersCompensationRatingList());
            getSession().getServletContext().setAttribute(ProviderConstants.ADDITIONAL_INSURANCE_LIST, insuranceLookupVO.getAdditionalInsuranceRatingList());
        }
       
    }
   
    @SuppressWarnings("unused")
    public String fetchRoutingPriorities(){
        String spnId = getRequest().getParameter("spnId");
        routingPrioritiesList = getLookupManager().fetchRoutingPriorities(spnId);
        return JSON;
    }

    /**
     * SL-21070
     * Ajax method which queries so_hdr to check if the so is still edit locked by another user. 
     * If so is still edit locked by another user, do nothing, and the page will stay as it is with the error message already populated.
     * If so is unlocked on this call, then the message from the page disappears and usual action happens
     */
    public String isSOEdited(){
    	String soId = getParameter(OrderConstants.LOCK_EDIT_CURRENT_SO_REQ_VAR);   
    	int editLockInd = soDetailsManager.getLockEditInd(soId);
    	if(editLockInd == 1){
    		isBeingEdited = OrderConstants.TRUE;
    	}else{
    		isBeingEdited = OrderConstants.FALSE;
    		//remove the current session attribute
    		getSession().removeAttribute(OrderConstants.LOCK_EDIT_SESSION_VAR+soId);
    		//lock for current buyer agent
    		//SOWSessionFacility.getInstance().setFlagOnServiceOrder(soID, OrderConstants.SO_EDIT_MODE_FLAG, fetchDelegate, get_commonCriteria().getSecurityContext());
    	}
        return JSON;
    }
    
    public String getSoID() {
        return soID;
    }

    public void setSoID(String soID) {
        this.soID = soID;
    }

    public IOrderGroupDelegate getOrderGroupDelegate()
    {
        return orderGroupDelegate;
    }

    public void setOrderGroupDelegate(IOrderGroupDelegate orderGroupDelegate)
    {
        this.orderGroupDelegate = orderGroupDelegate;
    }

    public ISODetailsDelegate getSoDetailsManager() {
        return soDetailsManager;
    }

    public void setSoDetailsManager(ISODetailsDelegate soDetailsManager) {
        this.soDetailsManager = soDetailsManager;
    }

    public ISOMonitorDelegate getSoMonitorDelegate() {
        return soMonitorDelegate;
    }

    public void setSoMonitorDelegate(ISOMonitorDelegate soMonitorDelegate) {
        this.soMonitorDelegate = soMonitorDelegate;
    }

    public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
        this.buyerFeatureSetBO = buyerFeatureSetBO;
    }

    public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
        return buyerFeatureSetBO;
    }

    public OFHelper getOrderFulfillmentHelper() {
        return orderFulfillmentHelper;
    }

    public void setOrderFulfillmentHelper(OFHelper orderFulfillmentHelper) {
        this.orderFulfillmentHelper = orderFulfillmentHelper;
    }

    public List<SPNetTierReleaseVO> getRoutingPrioritiesList() {
        return routingPrioritiesList;
    }

    public void setRoutingPrioritiesList(
            List<SPNetTierReleaseVO> routingPrioritiesList) {
        this.routingPrioritiesList = routingPrioritiesList;
    }
   
   
}
/*
 * Maintenance History
 * $Log: SOWControllerAction.java,v $
 * Revision 1.55  2008/05/29 19:40:31  gjacks8
 * changes to allow service order copy
 *
 * Revision 1.54  2008/05/22 21:00:29  cgarc03
 * Merged from I21.
 *
 * Revision 1.53  2008/05/21 23:33:14  akashya
 * I21 Merged
 *
 * Revision 1.52.4.1  2008/05/15 15:55:09  gjacks8
 * added simple buyer to buyer check
 *
 * Revision 1.52  2008/05/01 02:26:25  pvarkey
 * Checking changes to fix issue with current balance not being displayed when buyer logs in and goes directly to the SOW to create an order from the dashboard. Iteration 20.pvarkey@
 *
 * Revision 1.51  2008/04/26 01:13:54  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.46.10.1  2008/04/01 22:04:32  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.47  2008/03/27 18:58:16  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.46.14.1  2008/03/25 20:08:48  mhaye05
 * code cleanup
 *
 * Revision 1.46  2008/02/19 22:35:01  glacy
 * powerbuyer claim fixes for SOW
 *
 * Revision 1.45  2008/02/14 23:44:57  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.44  2008/02/13 18:59:29  cgarc03
 * *** empty log message ***
 *
 * Revision 1.43  2008/02/13 16:16:31  cgarc03
 * TreeMapSortingComparator moved to it's own file.
 *
 * Revision 1.42  2008/02/08 23:40:06  langara
 * Sears00046767,Sears00046898,Sears00046730,Sears00047094,Sears00047102
 *
 * Revision 1.41  2008/02/08 20:40:58  cgarc03
 * execute() - Get 'soId' and 'action' from session if not passed in as an attribute.
 */