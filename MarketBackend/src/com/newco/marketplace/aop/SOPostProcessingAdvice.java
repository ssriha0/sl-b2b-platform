package com.newco.marketplace.aop;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOCancelPostProcess;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOClosePostProcess;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOPostProcessingFactory;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOReschedulePostProcess;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProviderResponseVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.routing.tiered.schedulermanager.TierRoutingSchedulerManager;
import com.servicelive.routing.tiered.services.TierRouteController;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.6 $ $Author: glacy $ $Date: 2008/04/26 00:40:39 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class SOPostProcessingAdvice implements AfterReturningAdvice, MethodBeforeAdvice {

	private final static Logger logger = Logger.getLogger(SOPostProcessingAdvice.class.getName());
	private ISOPostProcessingFactory soPostProcessingFactory;
	private IServiceOrderBO serviceOrderBO;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	
	
	public void before(Method method, Object[] inputParameters, Object arg2) throws Throwable {
		if (StringUtils.equals(method.getName(), "deleteDraftSO")) {
			Integer buyerId = null;
			String soId = null;
			
			try {
				soId = (String)inputParameters[0];
			} catch (Exception e) {
				logger.warn("Parameters do not match - ignoring aop hook");
			}
			ServiceOrder so = serviceOrderBO.getServiceOrder(soId);
			buyerId = so.getBuyer().getBuyerId();
			Object[] inputParams = new Object[3];
			inputParams[0]=buyerId;
			inputParams[1]=inputParameters[0];
			inputParams[2]=inputParameters[1];

			if (null != buyerId && null != soId && null != so) {
				try {
					if (so.getLoctEditInd() == null || so.getLoctEditInd().compareTo(OrderConstants.SO_EDIT_MODE_FLAG) == 0) {
						if (so.getWfStateId().intValue() == OrderConstants.DRAFT_STATUS) {
							processAfterCancel(new ProcessResponse(ServiceConstants.VALID_RC, null), method, inputParams, arg2, Constants.SO_ACTION.SERVICE_ORDER_DELETE_DRAFT);
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void afterReturning(Object returnValue, Method method, Object[] inputParameters,
			Object arg3) throws Throwable {
		
		if( inputParameters == null )
		{
			logger.warn( "Input parameters <null> or empty before calling: "
				+ method.getName() );
		}
		if (StringUtils.equals(method.getName(),"respondToRescheduleRequest")) 
		{
			StringBuffer parms = new StringBuffer();
			for( Object o : inputParameters )
			{
				parms.append( o ).append( "," );
			}
			logger.info( "respondToRescheduleRequest: " + parms );
			processAfterReschedule(returnValue, method, inputParameters, arg3);
		} 
		else if (StringUtils.equals(method.getName(),"processCloseSO")) 
		{
			StringBuffer parms = new StringBuffer();
			for( Object o : inputParameters )
			{
				parms.append( o ).append( "," );
			}
			logger.info( "processCloseSO: " + parms );
			processAfterClose(returnValue, method, inputParameters, arg3);
		} 
		else if (StringUtils.equals(method.getName(),"processVoidSOForWS")) 
		{
			StringBuffer parms = new StringBuffer();
			for( Object o : inputParameters )
			{
				parms.append( o ).append( "," );
			}
			logger.info( "processVoidSOForWS: " + parms );
			processAfterCancel(returnValue, method, inputParameters, arg3, Constants.SO_ACTION.SERVICE_ORDER_VOID);
		} 
		else if (StringUtils.equals(method.getName(),"processCancelRequestInActive")) 
		{
			StringBuffer parms = new StringBuffer();
			for( Object o : inputParameters )
			{
				parms.append( o ).append( "," );
			}
			logger.info( "processCancelRequestInActive: " + parms );
			processAfterCancel(returnValue, method, inputParameters, arg3, Constants.SO_ACTION.CANCELLATION_REQUEST_TO_PROVIDER_IN_ACT_ST);
		} 
		else if (StringUtils.equals(method.getName(),"processCancelSOInAccepted")) 
		{
			StringBuffer parms = new StringBuffer();
			for( Object o : inputParameters )
			{
				parms.append( o ).append( "," );
			}
			logger.info( "processCancelSOInAccepted: " + parms );
			processAfterCancel(returnValue, method, inputParameters, arg3, Constants.SO_ACTION.SERVICE_ORDER_CANCELLED);
		}
		else if (StringUtils.equals(method.getName(),"processAcceptServiceOrder")) 
		{
			String soId = (String) inputParameters[0];
			logger.info("STOP TIER ROUTE SCHEDULER FOR Accept SOID : " + soId);
			TierRoutingSchedulerManager trSchedulerMgr = (TierRoutingSchedulerManager) MPSpringLoaderPlugIn.getCtx().getBean("tierroutingschedulermanager");
			trSchedulerMgr.stopAndRemoveJob(soId);
		}
		else if (StringUtils.equals(method.getName(),"processAcceptGroupOrder") ||
				StringUtils.equals(method.getName(),"acceptConditionalOfferForGroup") ||
				StringUtils.equals(method.getName(),"processUngroupOrderGrp") ) {
			String groupId = (String) inputParameters[0];
			logger.info("STOP TIER ROUTE SCHEDULER FOR Accept GroupID: " + groupId);
			TierRoutingSchedulerManager trSchedulerMgr = (TierRoutingSchedulerManager) MPSpringLoaderPlugIn.getCtx().getBean("tierroutingschedulermanager");
			trSchedulerMgr.stopAndRemoveJob(groupId);
		}
		else if (StringUtils.equals(method.getName(),"addToOrderGrp") || 
				          StringUtils.equals(method.getName(),"addToOrderGroup")) {
			String groupId = (String) inputParameters[1];
			List<String> childOrders = serviceOrderBO.getServiceOrderIDsForGroup(groupId);
			TierRoutingSchedulerManager trSchedulerMgr= (TierRoutingSchedulerManager) MPSpringLoaderPlugIn.getCtx().getBean("tierroutingschedulermanager");
			for(String childOrderSoId: childOrders){
				logger.info("STOP TIER ROUTE SCHEDULER FOR Each child SOID : " + childOrderSoId);
				trSchedulerMgr.stopAndRemoveJob(childOrderSoId);
			}
			logger.info("STOP TIER ROUTE SCHEDULER FOR Grp SOID after AddTOGroup: " + groupId);
			trSchedulerMgr.stopAndRemoveJob(groupId);
		}
		else if (StringUtils.equals(method.getName(),"processCreateConditionalOffer") || 
				          StringUtils.equals(method.getName(),"rejectServiceOrder")) {
			String soId = null;
			if (StringUtils.equals(method.getName(),"processCreateConditionalOffer")){
			    soId = (String) inputParameters[0];
			}
			if(StringUtils.equals(method.getName(),"rejectServiceOrder")){
				soId = (String) inputParameters[1];
			}
			ServiceOrder so = serviceOrderBO.getServiceOrder(soId);
			Integer buyerId = so.getBuyer().getBuyerId();
			boolean isGrouped = false;
			if(StringUtils.isNotBlank(so.getGroupId())){
				isGrouped = true;
			}
			/* process only if it is single order*/
			if(!isGrouped){
				processAfterCondOfferOrReject(soId, isGrouped, buyerId);
			}
			
		}else if (StringUtils.equals(method.getName(),"processCreateConditionalOfferForGroup")) {
			IOrderGroupBO orderGroupBO = (IOrderGroupBO) MPSpringLoaderPlugIn.getCtx().getBean("OrderGroupBOTarget");
			String groupId = (String) inputParameters[0];
			List<ServiceOrderSearchResultsVO> childOrdersList = orderGroupBO.getServiceOrdersVOForGroup(groupId);
    		Integer buyerId = childOrdersList.get(0).getBuyerID();
			processAfterCondOfferOrReject(groupId, true, buyerId);
			
		}else if (StringUtils.equals(method.getName(),"rejectGroupedOrder")) {
			IOrderGroupBO orderGroupBO = (IOrderGroupBO) MPSpringLoaderPlugIn.getCtx().getBean("OrderGroupBOTarget");
			String groupId = (String) inputParameters[1];
			List<ServiceOrderSearchResultsVO> childOrdersList = orderGroupBO.getServiceOrdersVOForGroup(groupId);
    		Integer buyerId = childOrdersList.get(0).getBuyerID();
			processAfterCondOfferOrReject(groupId, true, buyerId);
		}
		
		return;
	}

	private void processAfterCondOfferOrReject(String orderId, boolean isGrouped, Integer buyerId) {
		try{
			boolean isTierRouteEnabled = buyerFeatureSetBO.validateFeature(buyerId, BuyerFeatureConstants.TIER_ROUTE);
			if(isTierRouteEnabled){
				
				boolean isCondOfferOrReject = isAllProCondOfferOrReject(orderId, isGrouped);
			
				/* if it is single order */			
				if(isCondOfferOrReject && !isGrouped){
					TierRoutingSchedulerManager trSchedulerMgr = (TierRoutingSchedulerManager) MPSpringLoaderPlugIn.getCtx().getBean("tierroutingschedulermanager");
					trSchedulerMgr.stopAndRemoveJob(orderId);
					logger.info("STOP TIER ROUTE SCHEDULER FOR SOID after isCondOfferOrReject: " + orderId);
					TierRouteController trRouteController = (TierRouteController) MPSpringLoaderPlugIn.getCtx().getBean("trRouteController");
					trRouteController.route(orderId, isGrouped, SPNConstants.TR_REASON_ID_ALL_RESPONDED);
				}
				/* if it is grouped order */			
				if(isCondOfferOrReject && isGrouped){
					TierRoutingSchedulerManager trSchedulerMgr = (TierRoutingSchedulerManager) MPSpringLoaderPlugIn.getCtx().getBean("tierroutingschedulermanager");
					trSchedulerMgr.stopAndRemoveJob(orderId);
					logger.info("STOP TIER ROUTE SCHEDULER FOR GROUP SOID after isCondOfferOrReject: " + orderId);
					TierRouteController trRouteController = (TierRouteController) MPSpringLoaderPlugIn.getCtx().getBean("trRouteController");
					trRouteController.route(orderId, isGrouped, SPNConstants.TR_REASON_ID_ALL_RESPONDED);
				}
			}
		
		}catch(Exception e){
			logger.error("error occurred in processAfterCondOfferOrReject", e);
		}
		
	}

	private boolean isAllProCondOfferOrReject(String orderId, boolean isGrouped) throws Exception {
		boolean isAllProCondOfferOrReject = false;
		String soId = orderId;
		
		if(isGrouped){
			IOrderGroupBO orderGroupBO = (IOrderGroupBO) MPSpringLoaderPlugIn.getCtx().getBean("OrderGroupBOTarget");
			soId = orderGroupBO.getFirstSoIdForGroup(orderId);
			
		}
		
		List<RoutedProviderResponseVO> routedProResponseList = serviceOrderBO.getRoutedResourcesResponseInfo(soId);
		int routedProvidersCount = routedProResponseList!= null ?routedProResponseList.size() : 0;
		int rejectOrCondOfferCnt = 0;
		for(RoutedProviderResponseVO responseVO :routedProResponseList){
			if(responseVO.getResponseId()!= null){
				if( (responseVO.getResponseId().equals(OrderConstants.REJECTED) ) ||
						(responseVO.getResponseId().equals(OrderConstants.CONDITIONAL_OFFER)) ){
					rejectOrCondOfferCnt++;
				}
			}
			
		}
		if(routedProvidersCount == rejectOrCondOfferCnt){
			isAllProCondOfferOrReject = true;
		}
		return isAllProCondOfferOrReject;
	}

	/**
	 * @param returnValue
	 * @param method
	 * @param inputParameters
	 * @param arg3
	 * @throws Throwable
	 */
	protected void processAfterReschedule (Object returnValue, Method method, Object[] inputParameters,
			Object arg3) throws Throwable {
		
		Integer buyerId = (Integer)inputParameters[3];
		String soId = (String)inputParameters[0];
		boolean requestAccepted = ((Boolean)inputParameters[1]).booleanValue();
		
		if ( StringUtils.equals(((ProcessResponse)returnValue).getCode(),ServiceConstants.VALID_RC) &&
			 requestAccepted ){
			
			try {
				ServiceOrder so = serviceOrderBO.getServiceOrder(soId);
				ISOReschedulePostProcess soReschedulePostProcess = soPostProcessingFactory.getSORescheduleProcess(so.getBuyer().getBuyerId());
				
				// it is acceptable for there not be be a post process for this buyer.
				if (null != soReschedulePostProcess) {
					soReschedulePostProcess.execute(soId, buyerId);
				}
			} catch (BusinessServiceException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}

	/**
	 * @param returnValue
	 * @param method
	 * @param inputParameters
	 * @param arg3
	 * @throws Throwable
	 */
	protected void processAfterClose (Object returnValue, Method method, Object[] inputParameters,
			Object arg3) throws Throwable {
		
		Integer buyerId = (Integer)inputParameters[0];
		String soId = (String)inputParameters[1];
		
		if ( StringUtils.equals(((ProcessResponse)returnValue).getCode(),ServiceConstants.VALID_RC)){
			ISOClosePostProcess soClosePostProcess = soPostProcessingFactory.getSOCloseProcess(buyerId);
			
			// it is acceptable for there not be be a post process for this buyer.
			if (null != soClosePostProcess) {
				soClosePostProcess.execute(soId, buyerId);
			}
		}
	}
	
	/**
	 * for method to have AOp handling, last argument of method should be Security Context
	 * @param returnValue
	 * @param method
	 * @param inputParameters
	 * @param arg3
	 * @throws Throwable
	 */
	protected void processAfterCancel (Object returnValue, Method method, Object[] inputParameters,
			Object arg3, String className) throws Throwable {
		Integer buyerId = null;
		String soId = null;
		String actionFrom = "";
		
		try {
			buyerId = (Integer)inputParameters[0];
			soId = (String)inputParameters[1];
			SecurityContext securityContext = (SecurityContext)inputParameters[inputParameters.length - 1];
			actionFrom = securityContext.getActionSource();
		}
		catch (Exception e) {
			logger.warn("Parameters do not match - ignoring aop hook");
			return;
		}
		
		if(!StringUtils.isBlank(actionFrom))
			return;
		
		if (ServiceConstants.VALID_RC.equals(((ProcessResponse)returnValue).getCode())) {
			ISOCancelPostProcess soCancelPostProcess = soPostProcessingFactory.getSOCancelProcess(buyerId, className);
			
			// it is acceptable for there not be be a post process for this buyer.
			if (null != soCancelPostProcess) {
				if (arg3 instanceof ServiceOrder) {
					soCancelPostProcess.execute((ServiceOrder) arg3);
				}
				else {
					soCancelPostProcess.execute(soId, buyerId);
				}
			}
		}
	}
	
	public ISOPostProcessingFactory getSoPostProcessingFactory() {
		return soPostProcessingFactory;
	}

	public void setSoPostProcessingFactory(
			ISOPostProcessingFactory soPostProcessingFactory) {
		this.soPostProcessingFactory = soPostProcessingFactory;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

}
/*
 * Maintenance History
 * $Log: SOPostProcessingAdvice.java,v $
 * Revision 1.6  2008/04/26 00:40:39  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.4.22.1  2008/04/23 11:42:22  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.5  2008/04/23 05:01:34  hravi
 * Reverting to build 247.
 *
 * Revision 1.4  2008/01/17 14:07:41  mhaye05
 * fixed issue where processAfterClose was trying to convert a String to a boolean
 *
 * Revision 1.3  2008/01/09 17:11:20  mhaye05
 * now pulling buyer id from service order
 *
 * Revision 1.2  2008/01/08 19:21:51  mhaye05
 * added buyer id to input parameters
 *
 * Revision 1.1  2008/01/08 18:27:48  mhaye05
 * Initial Check In
 *
 */