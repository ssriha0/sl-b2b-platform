/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.servicelive.routing.tiered.services;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO;
import com.newco.marketplace.business.iBusiness.routing.tiered.SoTierRoutingHistoryBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.spn.ISPNetworkBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.dto.vo.spn.SPNetTierReleaseVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.servicelive.routing.tiered.schedulermanager.TierRoutingSchedulerManager;
import com.servicelive.routing.tiered.vo.SPNTierEventInfoVO;
import com.servicelive.routing.tiered.vo.SPNTieredVO;
import com.servicelive.routing.tiered.vo.TierRouteServiceOrderVO;

/**
 *
 * @author hoza
 */
public class TierRouteController {
    private TierRoutingSchedulerManager scheduler;
    private IRouteOrderGroupBO routeOrderGroupBO;
    private IServiceOrderBO serviceOrderBO;
    private ISPNetworkBO spnetworkBO;
    private IBuyerFeatureSetBO buyerFeatureSetBO;
    private IOrderGroupBO orderGroupBO;
    private SoTierRoutingHistoryBO tierRoutingHistoryBO;
    private ServiceOrderDao serviceOrderDAO;
    private Logger logger = Logger.getLogger(TierRouteController.class);

    public TierRoutingSchedulerManager getScheduler() {
        return scheduler;
    }

    public void setScheduler(TierRoutingSchedulerManager scheduler) {
        this.scheduler = scheduler;
    }
   
    /**
     * This method check of the status of SO it the SO is IN DARFT status or not any other status 
     * might be ignored and no further routing should happen. This may be the extra check, but this 
     * would certainly help in integrating of threaded execution of the SO and tier
     * 
     * @param orderVO
     * @return
     * @throws Exception
     */
    public boolean isSOEligibleForNextTier(TierRouteServiceOrderVO orderVO)
			throws Exception {
		boolean isEligible = false;

		try {
			if (orderVO.isGrouped()) {
				return isSOGroupEligibleForNextTier(orderVO);
			} else {
				ServiceOrder so = serviceOrderBO.getServiceOrder(orderVO
						.getOrderId());
				if (so != null) {
					orderVO.setSpnId(so.getSpnId());
					orderVO.setBuyerId(so.getBuyer().getBuyerId());
					if (so.getWfStateId() == OrderConstants.DRAFT_STATUS
							|| so.getWfStateId() == OrderConstants.ROUTED_STATUS) {
						isEligible = true;
					}
				}
			}
		} catch (Exception e) {
			String msg = "error occurred in isSOEligibleForNextTier due to "
					+ e.getMessage();
			throw new BusinessServiceException(msg, e);
		}
		return isEligible;

	}

    /**
     * consider group is eligible for Tier routing if all the individual orders are in 
     * Either DRAFT /ROUTED status
     * @param orderVO
     * @return
     * @throws Exception
     */
    private boolean isSOGroupEligibleForNextTier(TierRouteServiceOrderVO orderVO) throws Exception {
    	boolean isEligible = false;
    	int childOrdersCount = 0;
    	int eligibleOrderCount =0;
    	try{
    		List<ServiceOrderSearchResultsVO> childOrdersList = orderGroupBO.getServiceOrdersVOForGroup(orderVO.getOrderId());
    		if(childOrdersList!= null && childOrdersList.size()>0){
    			childOrdersCount = childOrdersList.size();
    			for(ServiceOrderSearchResultsVO eachChildOrder : childOrdersList){
    				orderVO.setBuyerId(eachChildOrder.getBuyerID());
    				orderVO.setSpnId(eachChildOrder.getSpnId());
    				if(eachChildOrder.getSoStatus() == OrderConstants.DRAFT_STATUS 
        					|| eachChildOrder.getSoStatus() == OrderConstants.ROUTED_STATUS){
        				eligibleOrderCount++;
        	    	}else{
        	    		isEligible = false;
        	    		break;
        	    	}
    			}
    			if(childOrdersCount == eligibleOrderCount){
    				isEligible = true;
    			}
     		}
    		orderVO.setGroupedOrders(childOrdersList);
    		
    	}catch(Exception e){
    		String msg = "error occurred in isGroupEligibleForNextTier due to "+ e.getMessage();
    		logger.error(msg);
    		throw new BusinessServiceException(msg,e);
    	}
		return isEligible;
	}

	/**
     * This method returns what tier SO should be routed and next fire time
     * @param TierRouteServiceOrderVO orderVO
     * @return SPNTierEventInfoVO
     */
    protected SPNTierEventInfoVO getNextTierOfOrder(TierRouteServiceOrderVO orderVo)
			throws Exception {

		if (orderVo.getCurrentTierId() == null
				|| orderVo.getCurrentTierId().intValue() == 0) {
			SPNTierEventInfoVO currentTierEventInfo = spnetworkBO.getSpnetSOTierEventInfo(orderVo);
			if (currentTierEventInfo != null) {
				orderVo.setCurrentTierId(currentTierEventInfo.getCurrentTierId());
			}
		}
		SPNTierEventInfoVO spnTierEventInfo = getTierInfo(orderVo);
		if (orderVo.isGrouped()) {
			spnTierEventInfo.setGroupOrderId(orderVo.getOrderId());
		} else {
			spnTierEventInfo.setSoId(orderVo.getOrderId());
		}
		return spnTierEventInfo;
	}

	/**
	 * @param orderVo
	 * @return
	 * @throws BusinessServiceException
	 */
	private SPNTierEventInfoVO getTierInfo(TierRouteServiceOrderVO orderVo)
			throws BusinessServiceException {
		SPNTierEventInfoVO spnTierEventInfo = new SPNTierEventInfoVO();
		SPNTieredVO nextTierVO = spnetworkBO.getSPNAdminNextTierInfo(orderVo);
		if (nextTierVO == null && hasSpnOverflow(orderVo)) {
			spnTierEventInfo.setCurrentTierId(OrderConstants.OVERFLOW);
			spnTierEventInfo.setNextTierId(null);
			spnTierEventInfo.setNextFireTime(null);
		} else if(nextTierVO != null){
				spnTierEventInfo.setCurrentTierId(nextTierVO.getTierId());
				if (nextTierVO.getNextTierId() != null) {
					spnTierEventInfo.setNextTierId(nextTierVO.getNextTierId());
					spnTierEventInfo.setNextFireTime(getNextFireTime(nextTierVO));
				} else if (hasSpnOverflow(orderVo)) {
					spnTierEventInfo.setNextTierId(OrderConstants.OVERFLOW);
					spnTierEventInfo.setNextFireTime(getNextFireTime(nextTierVO));
				}
		}
		return spnTierEventInfo;
	}

	/**
	 * @param orderVo
	 * @return
	 * @throws BusinessServiceException
	 */
	private boolean hasSpnOverflow(TierRouteServiceOrderVO orderVo)
			throws BusinessServiceException {
		boolean isOverflowExists = false;
					
		SPNetHeaderVO spnetHeaderVO = spnetworkBO.getSPNetHeaderInfo(orderVo.getSpnId());
		isOverflowExists = spnetHeaderVO!= null && spnetHeaderVO.getIsMPOverflow()? 
				                     spnetHeaderVO.getIsMPOverflow().booleanValue() : false;
		return isOverflowExists;
	}
    	
    	
    	

	private Date getNextFireTime(SPNTieredVO nextTierVO) {
		Date nextFireTime = new Date();
		Integer nextFireDays = nextTierVO.getTierWaitDays();
		Integer nextFireMinutes = nextTierVO.getTierWaitMinutes();
		Integer nextFireHours = nextTierVO.getTierWaitHours();

		if(nextFireDays!= null){
			nextFireTime = DateUtils.addDays(nextFireTime, nextFireDays);
		}
		if(nextFireHours!= null){
			nextFireTime = DateUtils.addHours(nextFireTime, nextFireHours);
		}
		if(nextFireMinutes!= null){
			nextFireTime = DateUtils.addMinutes(nextFireTime, nextFireMinutes);
		}
		return nextFireTime;
	}
  

    public Boolean route(String orderId, Boolean isGrouped, Integer reasonId)
			throws BusinessServiceException {
		Boolean result = Boolean.FALSE;
		boolean restartTierRouting = false;
		try {
			TierRouteServiceOrderVO orderVo = new TierRouteServiceOrderVO(
					orderId, isGrouped, reasonId);
			// if Restarting, remove persisted spnet Tier info for given orderId
			if (orderVo.getReasonId().equals(SPNConstants.TR_REASON_ID_RESTART_TIER_ROUTING)) {
				removeTierEventInfo(orderVo);
				restartTierRouting = true;
			}
			SPNTierEventInfoVO soRouteTierInfoVO = null;
			Integer numOfRoutedProviders = new Integer(0);
			int tierToBeRouted = 0;
			do {
				logger.info("Tier routing started for orderId :" + orderId
						+ "for reason " + orderVo.getReasonId());

				if (isSOEligibleForNextTier(orderVo)) {
					SecurityContext securityContext = ServiceOrderUtil
							.getSecurityContextForBuyer(orderVo.getBuyerId());
					soRouteTierInfoVO = getNextTierOfOrder(orderVo);
					tierToBeRouted = soRouteTierInfoVO.getCurrentTierId() != null ? soRouteTierInfoVO.getCurrentTierId().intValue(): 0;
					if (tierToBeRouted > 0) {
						orderVo.setTierToBeRouted(tierToBeRouted);
						/* log tier route history before routing */
						logTierRouteHistory(orderVo);
						numOfRoutedProviders = routeOrder(orderVo,
								tierToBeRouted, securityContext);

						// routing is done (irrespective of success/ failure) set currentTier as Tier with
						orderVo.setCurrentTierId(tierToBeRouted);
						if (soRouteTierInfoVO.getNextTierId() == null) {
							tierToBeRouted = 0;
						}
						postRoutingProcess(restartTierRouting, orderVo,	soRouteTierInfoVO, 
								numOfRoutedProviders, tierToBeRouted, securityContext);
					}
				}
			} while (0 == numOfRoutedProviders.intValue() && tierToBeRouted > 0);
			result = Boolean.TRUE;

		} catch (Exception e) {
			logger.error("Error occurred in TierRouteController.route due to "
					+ e.getMessage());
			throw new BusinessServiceException("error occured", e);
		}

		return result;
	}

	/**
	 * @param restartTierRouting
	 * @param orderVo
	 * @param soRouteTierInfoVO
	 * @param numOfRoutedProviders
	 * @param tierToBeRouted
	 * @param securityContext
	 */
	private void postRoutingProcess(boolean restartTierRouting,
			TierRouteServiceOrderVO orderVo,
			SPNTierEventInfoVO soRouteTierInfoVO, Integer numOfRoutedProviders,
			int tierToBeRouted, SecurityContext securityContext) {
		try{
		      if(null != numOfRoutedProviders && numOfRoutedProviders.intValue() > 0 ){
		           //Update spnInfo here
		           soRouteTierInfoVO.setCurrentFireTime(new Date());
		           spnetworkBO.persistSOTierEventInformation(soRouteTierInfoVO, orderVo.isGrouped());
		           /* update tier route history after routing */
		           updateTierRouteHistory(orderVo); 
		           if(soRouteTierInfoVO != null && soRouteTierInfoVO.getNextFireTime() != null ){
		        		scheduler.scheduleJob(soRouteTierInfoVO, orderVo.isGrouped(),SPNConstants.TR_REASON_ID_ADVANCE_RELEASE_TIME_ELAPSED);
				   }
	
		      }else{
		    	   if(tierToBeRouted == 0){
		    		  /* set NO PROVIDERS sub status if order in DRAFT status */
		    		   setNoProvidersSubStatus(orderVo.getOrderId(),orderVo.isGrouped(),restartTierRouting,securityContext);
		           }
		    	   orderVo.setReasonId(SPNConstants.TR_REASON_ID_NO_PROVIDERS);
		      }
		  
		  }catch(Exception e){
			  logger.error("EXCEPTION IN TIERED ROUTE JOB route method" + e.toString());
		   /* if something fails after routing successfully, add note , better way to alert to be handled*/
		   addNoteToNotifyFailure(orderVo);
		 }
	}

	/**
	 * @param orderVo
	 * @param tierToBeRouted
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 */
	private Integer routeOrder(TierRouteServiceOrderVO orderVo,
			int tierToBeRouted, SecurityContext securityContext)
			throws com.newco.marketplace.exception.BusinessServiceException {
		Integer numOfRoutedProviders = new Integer(0);
		  if(!orderVo.isGrouped()){
			  ServiceOrderSearchResultsVO serviceOrderVO = new ServiceOrderSearchResultsVO();
		      serviceOrderVO.setSoId(orderVo.getOrderId());
		      numOfRoutedProviders =  routeOrderGroupBO.routeIndividualOrder(serviceOrderVO, tierToBeRouted );
		  }else if(orderVo.isGrouped()){
			  List<ServiceOrderSearchResultsVO> grpOrdersList = orderVo.getGroupedOrders();
			  numOfRoutedProviders =  routeOrderGroupBO.processRouteOrderGroup(grpOrdersList, tierToBeRouted, securityContext);
		  }
		return numOfRoutedProviders;
	}
    
    private void addNoteToNotifyFailure(TierRouteServiceOrderVO orderVo) {
	    try{
	    	String noteMessage = "Error occurred after Tier routed successfully, may not be scheduled for next tier";
	    	if(orderVo.isGrouped()){
	    		List<ServiceOrderSearchResultsVO> childOrdersList = orderGroupBO.getServiceOrdersVOForGroup(orderVo.getOrderId());
	    		if(childOrdersList!= null && childOrdersList.size()>0){
	    			for(ServiceOrderSearchResultsVO eachOrderInfo : childOrdersList){
	    				addNoteToSO(eachOrderInfo.getSoId(), noteMessage);
	    			}
	    		}
	    	}else{
	    		addNoteToSO(orderVo.getOrderId(), noteMessage);
	    	}
	    	
	    } catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	private void addNoteToSO(String soId, String noteMessage) throws Exception {
		try {	
			ServiceOrder so = serviceOrderBO.getServiceOrder(soId);
			ServiceOrderNote note = new ServiceOrderNote();
			note.setCreatedByName(so.getBuyer().getUserName());
			note.setCreatedDate(new Date());
			note.setNote(noteMessage);
			note.setRoleId(new Integer(OrderConstants.BUYER_ROLEID));
			note.setEntityId(so.getBuyerResourceId());
			note.setSoId(so.getSoId());
			note.setNoteTypeId(new Integer(3));
			note.setPrivateId(1);
//			RandomGUID random = new RandomGUID();

			//note.setNoteId(random.generateGUID().longValue());
			serviceOrderDAO.insertNote(note);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void removeTierEventInfo(TierRouteServiceOrderVO orderVo) throws Exception{
    	spnetworkBO.deleteSOTierEventInformation(orderVo.getOrderId(), orderVo.isGrouped());
		
	}

	/* update tier route history record with given tierId & routeStatusInd for given so*/
    /**
     * @param orderVo
     */
    private void updateTierRouteHistory(TierRouteServiceOrderVO orderVo) {
    	try{
	    	if (!orderVo.getCurrentTierId().equals(OrderConstants.OVERFLOW)) {
				if (!orderVo.isGrouped()) {
					tierRoutingHistoryBO.updateRoutingStatusInd(orderVo.getOrderId(), orderVo.getCurrentTierId(), true);
				} else {
					List<ServiceOrderSearchResultsVO> childOrdersList = orderGroupBO
							.getServiceOrdersVOForGroup(orderVo.getOrderId());
					if (childOrdersList != null && childOrdersList.size() > 0) {
						for (ServiceOrderSearchResultsVO eachOrderInfo : childOrdersList) {
							tierRoutingHistoryBO.updateRoutingStatusInd(eachOrderInfo.getSoId(), orderVo.getCurrentTierId(), true);
						}
					}
				}
			}
    	}catch(Exception e){
    		logger.error("Error occurred in TierRouteController.updateTierRouteHistory due to " + e.getMessage() );
    	}	
	}

    /**
     * insert tier route history record with given tierId & routeStatusInd for given so
     * 
	 * @param orderVo
	 */
	private void logTierRouteHistory(TierRouteServiceOrderVO orderVo){
    	try{
	    	if (!orderVo.getTierToBeRouted().equals(OrderConstants.OVERFLOW)) {
				if (!orderVo.isGrouped()) {
					tierRoutingHistoryBO.createSoTierRoutingHist(orderVo
							.getOrderId(), orderVo.getTierToBeRouted(), orderVo.getReasonId(), false);
				} else {
					List<ServiceOrderSearchResultsVO> childOrdersList = orderGroupBO
							.getServiceOrdersVOForGroup(orderVo.getOrderId());
					if (childOrdersList != null && childOrdersList.size() > 0) {
						for (ServiceOrderSearchResultsVO eachOrderInfo : childOrdersList) {
							tierRoutingHistoryBO.createSoTierRoutingHist(
									eachOrderInfo.getSoId(), orderVo.getTierToBeRouted(), orderVo.getReasonId(), false);
						}
					}
				}
			}
    	}catch(Exception e){
    		logger.error("Error occurred in TierRouteController.logTierRouteHistory due to " + e.getMessage() );
    	}
 		
	}

	private void setNoProvidersSubStatus(String orderId, Boolean isGrouped,
			boolean restartTierRouting, SecurityContext securityContext) throws Exception{
    	if(!isGrouped){
    		ServiceOrder so = serviceOrderBO.getServiceOrder(orderId);
    		if(so.getWfStateId().equals(OrderConstants.DRAFT_STATUS)){
    			serviceOrderBO.updateSOSubStatus(orderId, OrderConstants.NO_PROVIDER_SUBSTATUS, securityContext);
    		} // if its restart Tier Routing,and no Providers found,set substatus irrespective of status
    		else if(restartTierRouting){
    			serviceOrderBO.updateSOSubStatus(orderId, OrderConstants.NO_PROVIDER_SUBSTATUS, securityContext);
    		}
		}else {
			// get the child orders, if they r in draft status, set each order sub status to NO PROVIDERS
			List<ServiceOrderSearchResultsVO> childOrdersList = orderGroupBO.getServiceOrdersVOForGroup(orderId);
    		if(childOrdersList!= null && childOrdersList.size()>0){
    			for(ServiceOrderSearchResultsVO eachOrderInfo : childOrdersList){
    				if(eachOrderInfo.getSoStatus().equals(OrderConstants.DRAFT_STATUS)){
    					serviceOrderBO.updateSOSubStatus(eachOrderInfo.getSoId(), OrderConstants.NO_PROVIDER_SUBSTATUS, securityContext);
    				}// if its restart Tier Routing,and no Providers found,set substatus irrespective of status
    				else if(restartTierRouting){
    	    			serviceOrderBO.updateSOSubStatus(orderId, OrderConstants.NO_PROVIDER_SUBSTATUS, securityContext);
    	    		}
    			}
    		}
		}
		
	}

	private ServiceOrderSearchResultsVO getServiceOrderSearchResultsVO(String orderId, Boolean isGrouped) throws Exception {
    	//TODO Implement me....
    	return new ServiceOrderSearchResultsVO();
    }
    
    /**
     * if Buyer has TIER Route Feature and if any tiers for given spn 
     * @param spnId
     * @param buyerId
     * @return
     */
    public boolean isTierRoutingRequried(Integer spnId, Integer buyerId){
    	boolean isTRRequired = false;
    	if(buyerFeatureSetBO.validateFeature(buyerId, BuyerFeatureConstants.TIER_ROUTE)){
    		try{
    			List<SPNetTierReleaseVO> spnTierList = spnetworkBO.getTiersForSpn(spnId);
    			if(spnTierList!= null && spnTierList.size() > 0){
    				isTRRequired = true;
    			}
    		}catch(Exception e){
    			String msg = "error caught in isTierRoutingRequried due to " + e.getMessage();
    			logger.error(msg);
    		}
    		
    	}
    	return isTRRequired;
    	
    }


	public ISPNetworkBO getSpnetworkBO() {
		return spnetworkBO;
	}

	public void setSpnetworkBO(ISPNetworkBO spnetworkBO) {
		this.spnetworkBO = spnetworkBO;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IRouteOrderGroupBO getRouteOrderGroupBO() {
		return routeOrderGroupBO;
	}

	public void setRouteOrderGroupBO(IRouteOrderGroupBO routeOrderGroupBO) {
		this.routeOrderGroupBO = routeOrderGroupBO;
	}

	public IOrderGroupBO getOrderGroupBO() {
		return orderGroupBO;
	}

	public void setOrderGroupBO(IOrderGroupBO orderGroupBO) {
		this.orderGroupBO = orderGroupBO;
	}

	public SoTierRoutingHistoryBO getTierRoutingHistoryBO() {
		return tierRoutingHistoryBO;
	}

	public void setTierRoutingHistoryBO(SoTierRoutingHistoryBO tierRoutingHistoryBO) {
		this.tierRoutingHistoryBO = tierRoutingHistoryBO;
	}

	public ServiceOrderDao getServiceOrderDAO() {
		return serviceOrderDAO;
	}

	public void setServiceOrderDAO(ServiceOrderDao serviceOrderDAO) {
		this.serviceOrderDAO = serviceOrderDAO;
	}


}
