package com.newco.marketplace.web.delegatesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.document.DocumentBO;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationJMSService;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundNotificationVO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.InitialPriceDetailsVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoDocumentVO;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;
import com.newco.marketplace.web.utils.OFServiceOrderMapper;
import com.newco.marketplace.web.utils.OFUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ActionContext;
import com.sears.os.service.ServiceConstants;
import com.servicelive.activitylog.client.IActivityLogHelper;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.TierRouteProviders;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateOrderRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * 
 * $Revision: 1.21 $ $Author: schavda $ $Date: 2008/05/31 16:09:58 $
 *
 */
public class SOWizardPersistDelegateImpl implements ISOWizardPersistDelegate{

	private IServiceOrderBO serviceOrderBo = new ServiceOrderBO();
	private IDocumentBO documentBO = new DocumentBO();
    private IApplicationProperties applicationProperties;
	private IBuyerBO buyerBO;
	private OFServiceOrderMapper ofServiceOrderMapper;
    private IBuyerOutBoundNotificationService buyerOutBoundNotificationService;
    private IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService;
    private OFHelper orderFulfillmentHelper;
    
    private IActivityLogHelper activityLogHelper;

	private static final Logger logger = Logger.getLogger(SOWizardPersistDelegateImpl.class.getName());

    private String createOFServiceOrder(ServiceOrder serviceOrder, SecurityContext securityContext){
        String returnVal ="";
        try{
            CreateOrderRequest orderRequest = ofServiceOrderMapper.createOrderRequest(serviceOrder,securityContext);
    		//get buyer state
    		try {
    			ContactLocationVO buyerContact = buyerBO.getBuyerContactLocationVO(securityContext.getCompanyId());
    			orderRequest.setBuyerState(buyerContact.getBuyerPrimaryLocation().getState());
    		} catch (Exception e) {
    			logger.error("Error happened when trying to get the funding type for buyer", e);
    			throw new BusinessServiceException(e);
    		}
            OrderFulfillmentResponse response=orderFulfillmentHelper.createServiceOrder(orderRequest);
            if (response.isError()) {
                String errorMessage = response.getErrorMessage();
                logger.error("Error returned while trying to create ServiceOrder: " + errorMessage);
            } else {
                returnVal = response.getSoId();
            }
        }catch(BusinessServiceException bse){
            logger.error("Exception tyring to create ServiceOrder!",bse);
        }
        return returnVal;
    }
    public ProcessResponse insertSODocument(DocumentVO documentVO){

		ProcessResponse pr = new ProcessResponse();

		try{
			pr = documentBO.insertServiceOrderDocument(documentVO);
		}catch(Exception e){
			logger.info("Error inserting SO Document, ignoring exception",e);
		}
		return pr;			
	}
	
	public ProcessResponse deleteSODocument(DocumentVO documentVO){
		ProcessResponse pr = new ProcessResponse();
		try{
			Integer documentId  = documentVO.getDocumentId();
			Integer roleId = documentVO.getRoleId();
			Integer userId = documentVO.getEntityId();
			String soId = documentVO.getSoId();
			
			pr = documentBO.deleteServiceOrderDocument(documentId, roleId, userId, soId);
		
		}catch(Exception e){
			logger.info("Error deleting SO Document, ignoring exception",e);
		}

		return pr;
	}
	
	public ProcessResponse deleteSODocumentforTask(String soId){
		ProcessResponse pr = new ProcessResponse();
		try{
			
			
			pr = documentBO.deleteServiceOrderDocumentforTask(soId);
		
		}catch(Exception e){
			logger.info("Error deleting SO Document, ignoring exception",e);
		}

		return pr;
	}
	
	
	
	public String processCreateDraftSO(ServiceOrder serviceOrder){
        SecurityContext securityContext = getSecurityContext();
        if(isNewOFAllowed(securityContext)) {
            return createOFServiceOrder(serviceOrder, securityContext);
        }
        String soId = null;
        if (serviceOrder != null) {
            ProcessResponse processResponse = serviceOrderBo.doProcessCreatePreDraftSO(serviceOrder, getSecurityContext());
			soId = (String) processResponse.getObj();
			//associate Buyer Documents with SO
			int buyerId = securityContext.getCompanyId();
			int roleId = securityContext.getRoleId();
			int userId = securityContext.getVendBuyerResId();

			try {
				serviceOrderBo.associateBuyerDocumentsToSO(soId, buyerId, roleId, userId);
			} catch (BusinessServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return soId;
	}
	
	/*public String processUpdateDraftSO(ServiceOrder serviceOrder) throws BusinessServiceException{
		String soId =serviceOrder.getSoId();
        SecurityContext securityContext = getSecurityContext();
        if(orderFulfillmentHelper.isNewSo(soId)){
            OrderFulfillmentRequest ofRequest = ofServiceOrderMapper.editServiceOrder(serviceOrder,securityContext);
        	if (serviceOrder.getGroupLaborSpendLimit() != null && serviceOrder.getGroupLaborSpendLimit().doubleValue() > 0){
        		ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_GROUP_ORDER_LABOR_SPEND_LIMIT, serviceOrder.getGroupLaborSpendLimit().toString());
        	}
        	if (serviceOrder.getGroupPartsSpendLimit() != null && serviceOrder.getGroupPartsSpendLimit().doubleValue() > 0){
        		ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_GROUP_ORDER_PARTS_SPEND_LIMIT, serviceOrder.getGroupPartsSpendLimit().toString());
        	}
            OrderFulfillmentResponse ofResponse = orderFulfillmentHelper.runOrderFulfillmentProcess(soId, SignalType.EDIT_ORDER, ofRequest);
            if(ofResponse.isError()) throw new BusinessServiceException(ofResponse.getErrorMessage());
            else soId = ofResponse.getSoId();
        }else{
			ProcessResponse processResponse = 	serviceOrderBo.processUpdateDraftSO(serviceOrder,securityContext);
			soId = (String)processResponse.getObj();
		}
		return soId;
	}*/
	
	public String processUpdateDraftSO(ServiceOrder serviceOrder,boolean routingPriorityApplied) throws BusinessServiceException{
		String soId =serviceOrder.getSoId();
        SecurityContext securityContext = getSecurityContext();
        String modifiedByName ="";
        String modifiedUserId ="";
        String soGroupId=serviceOrder.getGroupId();
        List<String>soIdList=new ArrayList<String>();
        try{
        LoginCredentialVO roles = securityContext.getRoles();
        if(null!=roles && null!=roles.getFirstName()&& null!=roles.getLastName()){
                 modifiedByName = (roles.getFirstName()+" "+roles.getLastName());
        }
        if(securityContext.getVendBuyerResId()!=null){
        	modifiedUserId = securityContext.getVendBuyerResId().toString();
    	}
        }catch(Exception e){
        logger.error("Error while fetching buyer details");            
        	}
        if(orderFulfillmentHelper.isNewSo(soId)){
        	Map<String,Object> sessionMap = ActionContext.getContext().getSession();
        	String perfCriteriaLevel = (String)sessionMap.get(ProviderConstants.PERF_CRITERIA_LEVEL);
            OrderFulfillmentRequest ofRequest = ofServiceOrderMapper.editServiceOrder(serviceOrder,securityContext,perfCriteriaLevel);
        	if (serviceOrder.getGroupLaborSpendLimit() != null && serviceOrder.getGroupLaborSpendLimit().doubleValue() > 0){

        		ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_GROUP_ORDER_LABOR_SPEND_LIMIT, serviceOrder.getGroupLaborSpendLimit().toString());

        	}
        	if (serviceOrder.getGroupPartsSpendLimit() != null && serviceOrder.getGroupPartsSpendLimit().doubleValue() > 0){

        		ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_GROUP_ORDER_PARTS_SPEND_LIMIT, serviceOrder.getGroupPartsSpendLimit().toString());

        	}
        
        com.servicelive.orderfulfillment.domain.ServiceOrder so = (com.servicelive.orderfulfillment.domain.ServiceOrder)ofRequest.getElement();
        so.setModifiedByName(modifiedByName);
        //for frontend orders
		ofRequest.addMiscParameter(OrderfulfillmentConstants.POST_FROM_FRONTEND_ACTION,"true");	
        
        
        //SL-18007
        logger.info("isAutoPost::"+serviceOrder.isAutoPost());
        logger.info("isRouteFromFE::"+serviceOrder.isRouteFromFE());
        logger.info("isSaveAsDraftFE::"+serviceOrder.isSaveAsDraftFE());
        so.setSaveAsDraftFE(serviceOrder.isSaveAsDraftFE());
        if(serviceOrder.isAutoPost() || serviceOrder.isRouteFromFE()){
        	so.setPost(true);
        }else{
        	so.setPost(false);
        }
        ofRequest.setElement(so);
        if (modifiedByName != null && !modifiedByName.equals("")){
                ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME, modifiedByName);
        }
        if (modifiedUserId != null && !modifiedUserId.equals("")){
            ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID, modifiedUserId);
        }
        if(serviceOrder.isPostFromFE()){
        	ofRequest.addMiscParameter(OrderfulfillmentConstants.FE_POST_ORDER, "true");
        	ofRequest.addMiscParameter(OrderfulfillmentConstants.ISUPDATE, null);
        }else{
        	ofRequest.addMiscParameter(OrderfulfillmentConstants.FE_POST_ORDER, "false");
        }
        if(serviceOrder.isSaveAsDraft()){
        	ofRequest.addMiscParameter(OrderfulfillmentConstants.SAVE_AS_DRAFT, "true");
        }else{
        	ofRequest.addMiscParameter(OrderfulfillmentConstants.SAVE_AS_DRAFT, "false");
        }
        /*SPNetHeaderVO spnHdr = buyerBO.getSpnDetails(serviceOrder.getSpnId());
        String routingPriority = spnHdr.getPriorityStatus();*/
        if(null != serviceOrder.getSpnId() && !serviceOrder.getSpnId().equals(0) && !serviceOrder.getSpnId().equals(-1)){
        SPNetHeaderVO spnHdr = buyerBO.getSpnDetails(serviceOrder.getSpnId());
        	perfCriteriaLevel = spnHdr.getPerfCriteriaLevel();
        }
       	if(routingPriorityApplied){
       		ofRequest.addMiscParameter(OrderfulfillmentConstants.ROUTING_PRIORITY_IND,"true");
            	if(null!=sessionMap.get(ProviderConstants.OLD_SPN_ID)){
            	String oldSpnId = (String)sessionMap.get(ProviderConstants.OLD_SPN_ID);
            	logger.info("OLD_SPN_ID"+oldSpnId);
            	ofRequest.addMiscParameter(OrderfulfillmentConstants.OLD_SPN_ID,oldSpnId);
            	}
            	ofRequest.addMiscParameter(OrderfulfillmentConstants.PERF_CRITERIA_LEVEL,perfCriteriaLevel);
            }else{
            	ofRequest.addMiscParameter(OrderfulfillmentConstants.ROUTING_PRIORITY_IND,"false");
            }
       	Boolean routingBehaviour = ofServiceOrderMapper.getFeatureSetBO().validateFeature(serviceOrder.getBuyer().getBuyerId(), BuyerFeatureConstants.TIER_ROUTE);
        logger.info("routingBehaviour>>"+routingBehaviour);
       	if(routingPriorityApplied && routingBehaviour){
       		logger.info("Tier eligible>>");
        	ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_AUTO_ROUTING_BEHAVIOR, "Tier");
        }else{
        	logger.info("Not Tier eligible>>");
        	ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_AUTO_ROUTING_BEHAVIOR, "None");
        }
       	/*//reset tier parametes so that the order reroutes if edited in the middle of routing process
       	ofRequest.addMiscParameter(OrderfulfillmentConstants.PROVIDERS_IN_CURRENT_TIER, null);
       	ofRequest.addMiscParameter(OrderfulfillmentConstants.PROVIDERS_IN_PREVIOUS_TIERS, null);
       	ofRequest.addMiscParameter(OrderfulfillmentConstants.AUTO_ROUTING_TIER,null);*/
       	
       	
       	int searsBuyerId = OrderfulfillmentConstants.BUYER_ID;
		int buyerId=serviceOrder.getBuyer().getBuyerId();
		 HashMap<String, BuyerOutboundNotificationVO> buyerOutboundNotificationMap=null;
		//int buyerIdForcheck=serviceOrder.getBuyerId();
		if (buyerId == searsBuyerId) {
			// For grouped order,get all the child ids and set into a list
			if(serviceOrder.getNewSoIndicator().equals(false)) 
			{
					if (StringUtils.isNotBlank(soGroupId)) {
						soIdList = buyerOutBoundNotificationService
								.getSoIdsForGroup(soGroupId);
					} else {
						soIdList.add(soId);
					  }
					if(null!=soIdList&& soIdList.size()>0)
					{
						 // This will fetch BuyerOutboundNotificationVO and put into  a map against corresponding soId
				    buyerOutboundNotificationMap = buyerOutBoundNotificationService.getServiceOrderDetails(soIdList);
					}
			}
	}
		try
		{
		// to do repost if providers are edited through frontend
		
		List<Integer>providersList= buyerOutBoundNotificationService.getTierRoutedProviders(soId);
		List<Integer> newProviderList=new ArrayList<Integer>();
		boolean repost=false;
		if(null!=so.getTierRoutedResources() && so.getTierRoutedResources().size()>0)
		{
		for(TierRouteProviders tier : so.getTierRoutedResources()){
			newProviderList.add(tier.getProviderResourceId());
		}
		}
		if(null==providersList)
		{
			providersList=new ArrayList<Integer>();	
		}
		//changes in tier routed resources
				if(null!=providersList && null!=newProviderList)
				{
				for(Integer  oldData: providersList){
					if(!newProviderList.contains(oldData)){
						repost=true;
						break;
					}
				}
				for(Integer newData : newProviderList){
					if(!providersList.contains(newData)){
						repost=true;
						break;
					}
				}
				if(repost){
					logger.info("repost due to change provider list");
					}
				}
				
			if(!repost){
				logger.info("Checking for change in tasks");
				//changes in tasks
				List<ServiceOrderTask> existingTasks = serviceOrderBo.getActiveTasks(soId);
				List<ServiceOrderTask> newTaskList = new ArrayList<ServiceOrderTask>();
				List<SOTask> newTasks = so.getActiveTasks();
				for(SOTask soTask :newTasks){
					ServiceOrderTask orderTask = new ServiceOrderTask();
					orderTask.setSkillNodeId(soTask.getSkillNodeId());
					orderTask.setServiceTypeId(soTask.getServiceTypeId());
					orderTask.setServiceType(soTask.getServiceType());
					orderTask.setTaskName(soTask.getTaskName());
					orderTask.setTaskComments(soTask.getTaskComments());
					newTaskList.add(orderTask);

				}
				String newTasksString = "";
				for (ServiceOrderTask task : newTaskList) {
					newTasksString += task.getCompareString();
				}
				String existingTasksString = "";
				for (ServiceOrderTask ctask : existingTasks) {
					existingTasksString += ctask.getCompareString();
				}

				if (!newTasksString.equals(existingTasksString)) {
					List<ServiceOrderTask> newTasksValues = new ArrayList<ServiceOrderTask>();
					List<ServiceOrderTask> deletedTasks = new ArrayList<ServiceOrderTask>();
					if (newTaskList != null && !newTaskList.isEmpty()) {
						for (ServiceOrderTask task : newTaskList) {
							boolean found = false;
							for (ServiceOrderTask ctask : existingTasks) {
								if(ctask.getSkillNodeId() == null){
									// Newly created task with no skill node id, has value 0.
									//So setting Old task with the same before comparison.
									ctask.setSkillNodeId(0); 
								}
								if (task.getSkillNodeId().intValue() == ctask.getSkillNodeId().intValue()
										&& task.getServiceTypeId().intValue() == ctask.getServiceTypeId().intValue()
										&& task.getTaskName().equalsIgnoreCase(ctask.getTaskName())) {
									found = true;
									break;
								}
							}
							if (!found) {
								newTasksValues.add(task);
							}
						}
						for (ServiceOrderTask ctask : existingTasks) {
							if(ctask.getSkillNodeId() == null){
								// Newly created task with no skill node id, has value 0.
								//So setting Old task with the same before comparison.
								ctask.setSkillNodeId(0); 
							}
							boolean found = false;
							for (ServiceOrderTask task : newTaskList) {
								if (task.getSkillNodeId().intValue() == ctask.getSkillNodeId().intValue()
										&& task.getServiceTypeId().intValue() == ctask.getServiceTypeId().intValue()
										&& task.getTaskName().equalsIgnoreCase(ctask.getTaskName())) {
									found = true;
									break;
								}
							}
							if (!found) {
								deletedTasks.add(ctask);
							}
						}
					}
					if (newTasks != null && newTasks.size() > 0) {
						repost = true;
					}
					if (deletedTasks != null && deletedTasks.size() > 0) {
						repost = true;
					}
				}
				if(repost){
				logger.info("repost due to change in tasks");
				}
			}	
			if(!repost){
				InitialPriceDetailsVO priceVo =	serviceOrderBo.getInitialPrice(soId);
				logger.info("Initial labor price"+priceVo.getInitialLaborPrice());
				logger.info("Initial parts price"+priceVo.getInitialPartsPrice());
				logger.info("New labor price"+so.getSpendLimitLabor());
				logger.info("New parts price"+so.getSpendLimitLabor());
				if(null!=priceVo.getInitialLaborPrice() && null!=so.getSpendLimitLabor() && priceVo.getInitialLaborPrice().doubleValue() != so.getSpendLimitLabor().doubleValue())
				{
					repost = true;
				}
				else if(null!=priceVo.getInitialPartsPrice() && null!=so.getSpendLimitParts() && priceVo.getInitialPartsPrice().doubleValue() != so.getSpendLimitParts().doubleValue()){
					repost = true;
				}
				}
			
				if(repost){
					ofRequest.addMiscParameter(OrderfulfillmentConstants.REPOST_FOR_TIER_FRONTEND,"true");	
				}else{
					ofRequest.addMiscParameter(OrderfulfillmentConstants.REPOST_FOR_TIER_FRONTEND,"false");	
				}
				if(null!=providersList && providersList.size()>0){
					//delete the entry in db before persisting the new list of providers	
			        buyerBO.deleteTierEligibleProviders(so.getSoId());
				}
		}
		catch(Exception e)
		{
			logger.info("exception while calculation frontend repost"+e);
		}
		
            OrderFulfillmentResponse ofResponse = orderFulfillmentHelper.runOrderFulfillmentProcess(soId, SignalType.EDIT_ORDER, ofRequest);
            if(serviceOrder.isSkuTaskIndicator()==true)
            {
            if(!ofResponse.isError())
            {
            	Boolean checkEntryInSoWorkflowcontrol=false;
            	checkEntryInSoWorkflowcontrol=serviceOrderBo.fetchSkuIndicatorFromSoWorkFlowControl(soId);
            	if(checkEntryInSoWorkflowcontrol.equals(false)||checkEntryInSoWorkflowcontrol.equals(null))
            	{
					serviceOrderBo.updateSkuIndicator(soId);
            	}
            	
				}
			}
            if(ofResponse.isError()) throw new BusinessServiceException(ofResponse.getErrorMessage());
            else {
				soId = ofResponse.getSoId();
				/*
				 * This method will be called when So is edited and try to
				 * a)SaveAndAutoPost b)SaveAsDraft c)PostServiceOrder It checks
				 * so details against so details in DB and call web service if
				 * detais are edited.
				 */
				// 
				
				//int buyerIdForcheck=serviceOrder.getBuyerId();
				if (buyerId == searsBuyerId) {
					if(serviceOrder.getNewSoIndicator().equals(false)) 
					{
					if (null != soIdList && !soIdList.isEmpty()) {
						for (String soIds : soIdList) {
							BuyerOutboundNotificationVO comapreTo = buyerOutboundNotificationMap
									.get(soIds);
							
							if (serviceOrder.getServiceLocationTimeZone() == null) {
								serviceOrder.setServiceLocationTimeZone(so.getServiceLocationTimeZone());

							}
						    RequestMsgBody requestMsgBody = buyerOutBoundNotificationService
									.getNPSNotificationRequest(serviceOrder,
											comapreTo, modifiedUserId);
						    if(null!=requestMsgBody){
							          BuyerOutboundFailOverVO failoverVO = buyerOutBoundNotificationService .callService(requestMsgBody, soIds);
									   if(null!=failoverVO){
							          buyerOutBoundNotificationJMSService.callJMSService(failoverVO);
									   } 
									}
						}

					}

				}
				}
			}
        }else{
			ProcessResponse processResponse = 	serviceOrderBo.processUpdateDraftSO(serviceOrder,securityContext);

			soId = (String)processResponse.getObj();
		}
		return soId;
} 
	
	public void processRouteSimpleBuyerSO(ServiceOrder so) throws BusinessServiceException{
		if (so != null)
		{
			SecurityContext securityContext = getSecurityContext();
			List<Integer> routedResourceIds = new ArrayList<Integer>();
			for (VendorResource vo : so.getProviders()) {
				routedResourceIds.add(vo.getResourceId());
			}
			
			if((securityContext.getAccountID()==null || securityContext.getAccountID()==0) && so.getSimpleBuyerAccountId()!=null)
				securityContext.setAccountID(so.getSimpleBuyerAccountId());
			
			ProcessResponse processResponse = 	serviceOrderBo.processRouteSO(so.getBuyer().getBuyerId(), so.getSoId(), routedResourceIds, so.getFundingAmountCC(), securityContext);
			if (processResponse.getCode().equals(ServiceConstants.USER_ERROR_RC) || processResponse.getCode().equals(ServiceConstants.SYSTEM_ERROR_RC)) {
				List<String> al = processResponse.getMessages();
				String msg = "";
				for (int r=0;r<al.size();r++) {
					String str = (String)al.get(r);
					msg = msg+"\n"+str;
				}
				
				throw new BusinessServiceException(msg);
			}
		}
	}
	
	public void processReRouteSimpleBuyerSO(ServiceOrder so) throws BusinessServiceException{
		if (so != null)
		{
			SecurityContext securityContext = getSecurityContext();
			List<Integer> routedResourceIds = new ArrayList<Integer>();
			for (VendorResource vo : so.getProviders()) {
				routedResourceIds.add(vo.getResourceId());
			}
			
			if((securityContext.getAccountID()==null || securityContext.getAccountID()==0) && so.getSimpleBuyerAccountId()!=null)
				securityContext.setAccountID(so.getSimpleBuyerAccountId());
			
			ProcessResponse processResponse = 	serviceOrderBo.processReRouteSO(so.getBuyer().getBuyerId(), so.getSoId(), routedResourceIds, so.getFundingAmountCC(), securityContext);
			if (processResponse.getCode().equals(ServiceConstants.USER_ERROR_RC) || processResponse.getCode().equals(ServiceConstants.SYSTEM_ERROR_RC)) {
				List<String> al = processResponse.getMessages();
				String msg = "";
				for (int r=0;r<al.size();r++) {
					String str = (String)al.get(r);
					msg = msg+"\n"+str;
				}
				
				throw new BusinessServiceException(msg);
			}
		}
	}
	
	public String processReRouteSO(String soId, Integer buyerId) throws BusinessServiceException{
		
		if (soId!=null)
		{
            SecurityContext securityContext = getSecurityContext();
            if(orderFulfillmentHelper.isNewSo(soId)){
                //not sure if we need this for reroute since edit order takes care of all the processing
                //processRouteSOWithOF(soId, securityContext);
            }else{
                ProcessResponse processResponse = 	serviceOrderBo.processReRouteSO(buyerId, soId,false,securityContext);
                if (processResponse.getCode().equals(ServiceConstants.USER_ERROR_RC) || processResponse.getCode().equals(ServiceConstants.SYSTEM_ERROR_RC)) {
                    List<String> al = processResponse.getMessages();
                    String msg = "";
                    for (int r=0;r<al.size();r++) {
                        String str = (String)al.get(r);
                        msg = msg+"\n"+str;
                    }

                    throw new BusinessServiceException(msg);
                }
            }
            activityLogHelper.markBidsFromProviderAsExpired(soId, null);
		}
		return soId;
	}

    private void processRouteSOWithOF(String soId, SecurityContext securityContext, boolean postFromFE, boolean routingPriorityApplied) throws BusinessServiceException {
        OrderFulfillmentRequest ofRequest = OFUtils.newPostOrderRequest(securityContext);
        List<Parameter> parameters = new ArrayList<Parameter>();
        if(postFromFE){
        	parameters.add(new Parameter(OrderfulfillmentConstants.FE_POST_ORDER,"true"));
        }else{
        	parameters.add(new Parameter(OrderfulfillmentConstants.FE_POST_ORDER,"false"));
        }
        //using a new process variable so as to enable routing through frontend white edit-post
        	parameters.add(new Parameter(OrderfulfillmentConstants.FE_POST,"true"));
	
        /*if(routingPriorityApplied){
        	parameters.add(new Parameter(OrderfulfillmentConstants.ROUTING_PRIORITY_IND,"true"));
        	Map<String,Object> sessionMap = ActionContext.getContext().getSession();
        	if(null!=sessionMap.get(ProviderConstants.OLD_SPN_ID)){
        	String oldSpnId = (String)sessionMap.get(ProviderConstants.OLD_SPN_ID);
        	logger.info("OLD_SPN_ID"+oldSpnId);
        	parameters.add(new Parameter(OrderfulfillmentConstants.OLD_SPN_ID,oldSpnId));
        	}
        	String perfCriteriaLevel = (String)sessionMap.get(ProviderConstants.PERF_CRITERIA_LEVEL);
        	parameters.add(new Parameter(OrderfulfillmentConstants.PERF_CRITERIA_LEVEL,perfCriteriaLevel));
        }else{
        	parameters.add(new Parameter(OrderfulfillmentConstants.ROUTING_PRIORITY_IND,"false"));
        }*/
		ofRequest.setMiscParameters(parameters);
        OrderFulfillmentResponse ofResponse = orderFulfillmentHelper.runOrderFulfillmentProcess(soId, SignalType.POST_ORDER, ofRequest);
        if(ofResponse.isError())throw new BusinessServiceException(ofResponse.getErrorMessage());
    }

	public String processRouteSO(String soId, Integer buyerId, boolean postFromFE, boolean routingPriorityApplied) throws BusinessServiceException{
        SecurityContext securityContext = getSecurityContext();
        if(orderFulfillmentHelper.isNewSo(soId)){
            processRouteSOWithOF(soId, securityContext, postFromFE, routingPriorityApplied);
            //call method to check updation to call webservice
        }else{
			ProcessResponse processResponse = 	serviceOrderBo.processRouteSO(buyerId, soId,securityContext);
			if (processResponse.getCode().equals(ServiceConstants.USER_ERROR_RC) || processResponse.getCode().equals(ServiceConstants.SYSTEM_ERROR_RC)) {
				List<String> al = processResponse.getMessages();
				String msg = "";
				for (int r=0;r<al.size();r++) {
					String str = (String)al.get(r);
					msg = msg+"\n"+str;
				}
				throw new BusinessServiceException(msg);
			}
		}
		return soId;
	}
	
	public void autoPostSO(String soId) throws BusinessServiceException {
		SecurityContext securityContext = getSecurityContext();
		logger.info("inside autoPostSO");
        if(orderFulfillmentHelper.isNewSo(soId)){
        	logger.info("of on:::");
        	// Do not call autopost the signal is not available (such as for an RI, non-CAR order)
        	SignalType autoPostSignal = SignalType.AUTO_POST_ORDER;
			if (!orderFulfillmentHelper.isSignalAvailable(soId, autoPostSignal)) {
				logger.info("Not sending AUTO_POST_ORDER signal for order " + soId + " because the signal is not available.");
				return;
			}
			OrderFulfillmentRequest ofRequest = OFUtils.newPostOrderRequest(securityContext);
			 List<Parameter> parameters = new ArrayList<Parameter>();
				parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_AUTO_POST,"isAutoPost"));
				parameters.add(new Parameter(OrderfulfillmentConstants.FE_POST_ORDER,"false"));
				logger.info("parameters::"+parameters);
				ofRequest.setMiscParameters(parameters);
	        OrderFulfillmentResponse ofResponse = orderFulfillmentHelper.runOrderFulfillmentProcess(soId, autoPostSignal, ofRequest);
	        if(ofResponse.isError()){
	        	throw new BusinessServiceException(ofResponse.getErrorMessage());
	        	}else{
	      //call method to check updation to call webservice
	        	}
        } else {
        	serviceOrderBo.updateSOSubStatus(soId, OrderConstants.READY_FOR_POSTING_SUBSTATUS, securityContext);
        }
	}
	
	private SecurityContext getSecurityContext(){
		Map<String,Object> sessionMap = ActionContext.getContext().getSession();
		SecurityContext securityContext = null;
		if(sessionMap != null){
			securityContext = (SecurityContext)sessionMap.get(Constants.SESSION.SECURITY_CONTEXT);
		}
		return securityContext;
	}
	
	public Buyer getBuyerAttrFromBuyerId(Integer buyerId) throws BusinessServiceException{
		Buyer buyer = null;
		System.out.println("buyerId " +buyerId);
		if (buyerId!=null)
		{
			buyer = serviceOrderBo.getBuyerAttrFromBuyerId(buyerId);		
		}
		return buyer;
	}
	public FundingVO checkBuyerFundsForIncreasedSpendLimit(ServiceOrder serviceOrder, Integer buyerId)
			throws BusinessServiceException {
		FundingVO fundingVO = new FundingVO();
		if (serviceOrder!=null)
		{
			fundingVO = serviceOrderBo.checkBuyerFundsForIncreasedSpendLimit(serviceOrder, buyerId);
		}
		
		return fundingVO;
	}
	
	public Map getTaskPrice(Integer taskId) throws BusinessServiceException, DataServiceException {
		return serviceOrderBo.getTaskPrice(taskId);
	}	
	
	public IServiceOrderBO getServiceOrderBo() {
		return serviceOrderBo;
	}

	public void setServiceOrderBo(IServiceOrderBO serviceOrderBo) {
		this.serviceOrderBo = serviceOrderBo;
	}

	public IDocumentBO getDocumentBO() {
		return documentBO;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

    public void setApplicationProperties(IApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public void setOrderFulfillmentHelper(OFHelper helperOrderFulfillment) {
        this.orderFulfillmentHelper = helperOrderFulfillment;
    }


    public boolean isNewOFAllowed(SecurityContext securityContext){
	    boolean returnVal = false;
        if(orderFulfillmentHelper.isOFEnabled()){
            if(orderFulfillmentHelper.isBuyerAllowed(securityContext.getRoleId())){
                try{
                    returnVal = StringUtils.equals("1",applicationProperties.getPropertyFromDB(Constants.OrderFulfillment.USE_NEW_ORDER_FULFILLMENT_PROCESS));
                }catch(DataNotFoundException ex){
                    String msg = String.format("Exception while trying to read Application Property(%1$s)",Constants.OrderFulfillment.USE_NEW_ORDER_FULFILLMENT_PROCESS);
                    logger.warn(msg,ex);
                    returnVal = false;
                }
            }
        }
	    return returnVal;
	}
    
    public void insertSODocuments(SoDocumentVO soDocVO)
    {
    	try{
			documentBO.insertSODocuments(soDocVO);
		}catch(Exception e){
			logger.info("Error inserting SO Document, ignoring exception",e);
		}
    }
    
	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}
	public void setOfServiceOrderMapper(OFServiceOrderMapper ofServiceOrderMapper) {
		this.ofServiceOrderMapper = ofServiceOrderMapper;
	}
	public IActivityLogHelper getActivityLogHelper() {
		return activityLogHelper;
	}
	public void setActivityLogHelper(IActivityLogHelper activityLogHelper) {
		this.activityLogHelper = activityLogHelper;
	}
	public IBuyerOutBoundNotificationService getBuyerOutBoundNotificationService() {
		return buyerOutBoundNotificationService;
	}
	public void setBuyerOutBoundNotificationService(
			IBuyerOutBoundNotificationService buyerOutBoundNotificationService) {
		this.buyerOutBoundNotificationService = buyerOutBoundNotificationService;
	}
	public IBuyerOutBoundNotificationJMSService getBuyerOutBoundNotificationJMSService() {
		return buyerOutBoundNotificationJMSService;
	}
	public void setBuyerOutBoundNotificationJMSService(
			IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService) {
		this.buyerOutBoundNotificationJMSService = buyerOutBoundNotificationJMSService;
	}
}
/*
 * Maintenance History
 * $Log: SOWizardPersistDelegateImpl.java,v $
 * Revision 1.21  2008/05/31 16:09:58  schavda
 * processRouteSO -- new method for fundingAmountCC -- only for simple buyer CC funding
 *
 * Revision 1.20  2008/05/23 04:54:03  mhaye05
 * fix to make sure we save providers for simple buyer posts
 *
 * Revision 1.19  2008/04/25 21:02:59  glacy
 * Shyam: Merged SL_DE_2008_04_30 branch to HEAD.
 *
 * Revision 1.18  2008/04/23 05:19:43  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.17.12.1  2008/04/22 19:27:53  sgopin2
 * Sears00051136
 *
 * Revision 1.17  2008/03/27 18:58:04  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.16.18.1  2008/03/10 21:58:03  mhaye05
 * added logic to check the buyer's  max spend limit per so
 *
 */
