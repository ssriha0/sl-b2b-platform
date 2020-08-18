package com.servicelive.orderfulfillment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import com.servicelive.orderfulfillment.domain.*;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;

import com.servicelive.orderfulfillment.group.GroupPricingUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.common.TransitionNotAvailableException;
import com.servicelive.orderfulfillment.group.signal.GroupSignal;
import com.servicelive.orderfulfillment.group.signal.IGroupSignal;
import com.servicelive.orderfulfillment.jbpm.TransitionContext;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.ordertype.ServiceOrderType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.signal.ISignal;
import com.servicelive.orderfulfillment.signal.ProcessSignalStep;
import com.servicelive.orderfulfillment.signal.Signal;

public class ServiceOrderGroupBO extends BaseServiceOrderBO {

	private static final BigDecimal MINIMUM_TOTAL_SPEND_LIMIT_FOR_AUTO_POSTING = PricingUtil.ONE_SCALED_WITH_4;
	private List<SignalType> groupSignals;
	private Logger logger = Logger.getLogger(getClass());
	private QuickLookupCollection quickLookupCollection;
	private GroupPricingUtil groupPricingUtil;

	private final String processName = "orderGroupProcess";

	private void addServiceOrderToGroup(String groupId, String groupProcessId,	ServiceOrder serviceOrder, boolean saveSearchData) {
		SOGroup soGroup = serviceOrderDao.getSOGroup(groupId);
		soGroup.addServiceOrder(serviceOrder);

		serviceOrderDao.save(soGroup);
		if(saveSearchData)
			saveServiceOrderSearchData(serviceOrder, groupProcessId, soGroup.getGroupId(), true);
	}

	private ServiceOrderProcess createNewGroupOrderWF(ServiceOrder serviceOrder, boolean useHoldTime, Integer buyerRoleId){
		return createNewGroupOrderWF(serviceOrder, useHoldTime, buyerRoleId, true);
	}

	private ServiceOrderProcess createNewGroupOrderWF(ServiceOrder serviceOrder, boolean useHoldTime, Integer buyerRoleId, boolean makeGroupingSearchable){

		if (!doesServiceOrderHaveNecessaryComponent(serviceOrder)){
			return null;
		}

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ProcessVariableUtil.PVKEY_SVC_ORDER_TYPE, ServiceOrderType.getServiceOrderType(serviceOrder.getBuyerId(), buyerRoleId).getSoTypeName());
		variables.put(ProcessVariableUtil.PVKEY_SERVICE_ORDER_IDS_FOR_GROUP_PROCESS, serviceOrder.getSoId());
		variables.put(ProcessVariableUtil.PVKEY_NUMBER_OF_SERVICE_ORDER, 1);
		variables.put(ProcessVariableUtil.USE_HOLD_TIME_FOR_QUEUE, useHoldTime);

		logger.debug("adding new group workflow");
		String groupProcessId = workflowManager.createNewWFInstance(processName, variables);

		return saveServiceOrderSearchData(serviceOrder, groupProcessId, null, makeGroupingSearchable);
	}
	private String createOrderGroup(List<ServiceOrderProcess> soProcesses, ServiceOrder so){
		return createOrderGroup(soProcesses, so, true, true);
	}

	private String createOrderGroup(List<ServiceOrderProcess> soProcesses, ServiceOrder so, boolean saveSearchData, boolean addRoutedProviders) {
		SOGroup soGroup = new SOGroup();
		soGroup.setGroupServiceDate(so.getSchedule().getServiceDate1());
		soGroup.setGroupServiceTime(so.getSchedule().getServiceTimeStart());
		soGroup.setGroupTitle("Grouped Order - " + so.getSowTitle());
		soGroup.addServiceOrder(so);
		//also add other service orders
		for(ServiceOrderProcess soProcess : soProcesses){
			soGroup.addServiceOrder(serviceOrderDao.getServiceOrder(soProcess.getSoId()));
		}

		//check if other orders have providers and if so add the providers to group
		ServiceOrder otherSO = serviceOrderDao.getServiceOrder(soProcesses.get(0).getSoId());
		if(null != otherSO.getRoutedResources() && otherSO.getRoutedResources().size() > 0 && addRoutedProviders){
			Set<GroupRoutedProvider> groupRoutedProviders = new HashSet<GroupRoutedProvider>();
			for(RoutedProvider rp : otherSO.getRoutedResources()){
				GroupRoutedProvider grp = new GroupRoutedProvider();
				grp.setProviderResourceId(rp.getProviderResourceId());
				grp.setVendorId(rp.getVendorId());
				grp.setRoutedDate(new Date());
				groupRoutedProviders.add(grp);
			}
			soGroup.setGroupRoutedProviders(groupRoutedProviders);
		}

		logger.debug("Creating new so group record and associating all the orders to it");
		serviceOrderDao.save(soGroup);

		//add the soProcess for the current service Order
		if(saveSearchData)
			soProcesses.add(saveServiceOrderSearchData(so, soProcesses.get(0).getGroupProcessId(), soGroup.getGroupId(), true));
		else {
			ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(so.getSoId());
			sop.setGroupProcessId(soProcesses.get(0).getGroupProcessId());
			soProcesses.add(sop);
		}

		//add group id to all so processes
		for(ServiceOrderProcess soProcess : soProcesses){
			soProcess.setGroupId(soGroup.getGroupId());
			serviceOrderProcessDao.update(soProcess);
		}
		return soGroup.getGroupId();
	}

	/**
	 * This method is called after the ServiceOrder creation is called
	 * This method will search if there are other order that has same criteria as this service order
	 * if found it will 
	 * 1. Get the group workflow and join that workflow
	 * 2. Create a new order workflow
	 * 3. Update the group workflow Id in the so_process_map
	 * @param callerId
	 * @param serviceOrder
	 * @return
	 */
	public void createServiceOrderGroup(Identification callerId, ServiceOrder serviceOrder){
		createServiceOrderGroup(callerId, serviceOrder, true);
	}

	public void createServiceOrderGroupWithoutSearch(Identification callerId, ServiceOrder serviceOrder){
		createNewGroupOrderWF(serviceOrder, false, callerId.getRoleId(), false);
	}

	private void createServiceOrderGroup(Identification callerId, ServiceOrder serviceOrder, boolean useHoldTime){
		if (!isOrderEligibleForGrouping(serviceOrder)){
			logger.info("inside isOrderEligibleForGrouping");
			return; //return if service order is not complete
		}


		try{
			logger.debug("Service order is eligible for grouping");
			//Check if there is group workflow that matches criteria
			List<ServiceOrderProcess> soProcesses = getGroupProcessIdForMatchingOrder(serviceOrder);
			logger.debug("Looking for the match in the database " + soProcesses.size());
			if (soProcesses == null || soProcesses.size() == 0){
				logger.info("R16_0:SL-20638 : inside Create NEw oRder WF: process map size is 0");
			}
			if (soProcesses == null || soProcesses.size() == 0
					|| (soProcesses.size() > 0 && !groupPricingUtil.orderIsEligibleForGrouping(soProcesses, serviceOrder, true))){
				// inside create new order WF
				logger.info("R16_0:SL-20638 : inside Create NEw oRder WF");
				createNewGroupOrderWF(serviceOrder, useHoldTime, callerId.getRoleId());
			} else {
				
				if (soProcesses.get(0).getGroupId() == null){
					logger.info("createOrderGroup*******))"+serviceOrder.getSoId());

					createOrderGroup(soProcesses, serviceOrder);
				}else {
					logger.info("inside addServiceOrderToGroup*******))"+serviceOrder.getSoId());
					addServiceOrderToGroup(soProcesses.get(0).getGroupId(), soProcesses.get(0).getGroupProcessId(), serviceOrder, true);
				}
				Map<String,Serializable> miscParams = new HashMap<String, Serializable>();
				miscParams.put(ProcessVariableUtil.PVKEY_GROUP_ID, soProcesses.get(0).getGroupId());
				//Make sure that we update the number of service order in group
				SOGroup sog = serviceOrderDao.getSOGroup(soProcesses.get(0).getGroupId());
				miscParams.put(ProcessVariableUtil.PVKEY_NUMBER_OF_SERVICE_ORDER, String.valueOf(sog.getServiceOrders().size()));
				logger.info("invoking ADD_ORDER_TO_GROUP***)))"+serviceOrder.getSoId());
				processGroupSignal(soProcesses.get(0).getGroupId(), SignalType.ADD_ORDER_TO_GROUP, callerId, null, miscParams);
			}
		}		
		catch (ServiceOrderException soe) {
			soe.addError(">> Error happened while either creating new group workflow or adding an order to existing workflow");
			throw soe;
		}
		catch (Exception e)	{
			ServiceOrderException soe = new ServiceOrderException();
			soe.addError(">> Error happened while either creating new group workflow or adding an order to existing workflow");
			soe.addError(">> General exception. Message:"+e.getMessage()+" Cause:"+e.getCause());
			logger.error(">> General exception stack trace:", e);
			logger.error(">> End of stack trace:");
			throw soe;
		}
	}

	private boolean doesBuyerSupportsGrouping(Long buyerId){
		BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
		if (!buyerFeatureLookup.isInitialized()) {
			throw new ServiceOrderException("Unable to lookup buyer feature for grouping. BuyerFeatureLookup not initialized.");
		}
		return buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.ORDER_GROUPING, buyerId);
	}

	private boolean isNotCARServiceOrder(String soId){
		ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(soId);
		return !sop.isCarOrder();
	}

	private boolean doesServiceOrderHaveNecessaryComponent(ServiceOrder serviceOrder) {
		if (serviceOrder.getBuyerId() != null &&
				serviceOrder.getPrimarySkillCatId() != null &&
				serviceOrder.getServiceLocation() != null &&
				serviceOrder.getSchedule() != null) return true;
		return false;
	}

	private boolean doesServiceOrderHaveValidationWarnings(ServiceOrder serviceOrder){
		return (serviceOrder.getValidationHolder()==null)? false : serviceOrder.getValidationHolder().hasWarnings();
	}

	/*private boolean hasProviders(ServiceOrder serviceOrder){
		logger.info("Inside new method hasProviders to check for providers available");
		boolean hasProviders = false;
		try{	
			if(null != serviceOrder.getRoutedResources() && serviceOrder.getRoutedResources().size()>0){
				logger.info("Service order routed resources list size :"+serviceOrder.getRoutedResources().size());
				hasProviders=true;
			}		
		}
		catch(Exception e){
			logger.info("Exception ocured in the hasProviders() method"+e.getMessage());
		}
		return hasProviders;
	}*/
	private List<ServiceOrderProcess> getGroupProcessIdForMatchingOrder(ServiceOrder serviceOrder){
		return serviceOrderProcessDao.findLikeServiceOrders(serviceOrder);
	}

	private boolean isOrderEligibleForGrouping(ServiceOrder serviceOrder){
		logger.info("inside isOrderEligibleForGrouping : added new condition to check for providers");
		return (doesBuyerSupportsGrouping(serviceOrder.getBuyerId()) 
				&& isNotCARServiceOrder(serviceOrder.getSoId()) 
				&& doesServiceOrderHaveNecessaryComponent(serviceOrder)
				&& !doesServiceOrderHaveValidationWarnings(serviceOrder)
				&& doesServiceOrderMeetingPricingRequirements(serviceOrder)
				&& !(serviceOrder.isJobCodeMismatch()));
	}

	private boolean doesServiceOrderMeetingPricingRequirements(ServiceOrder serviceOrder) {		
		BigDecimal amount = serviceOrder.getTotalSpendLimit();
		return amount != null && amount.compareTo(MINIMUM_TOTAL_SPEND_LIMIT_FOR_AUTO_POSTING) >= 0;
	}

	public void processGroupSignal(
			String groupId, 
			SignalType signalType,
			Identification callerId,
			SOElement soElement,
			Map<String,Serializable> miscParams){
		if (!groupSignals.contains(signalType)){
			return; //group does not know how to handle this signal
		}

		String pss="";
		try {

			// get group order from the DB
			pss = ProcessSignalStep.PSS_GETSO;
			SOGroup sog = serviceOrderDao.getSOGroup(groupId);
			if (sog==null) 
				throw new ServiceOrderException("Group Order record isn't found in the database");
			ServiceOrder firstServiceOrder = sog.getFirstServiceOrder();

			// get process record from the database
			pss = ProcessSignalStep.PSS_GETPROCESSRECORD;
			List<ServiceOrderProcess> soProcesses = serviceOrderProcessDao.getProcessMapByGroupIdWithLock(groupId);
			ServiceOrderProcess sop = soProcesses.get(0);

			// get process id for the group order
			pss = ProcessSignalStep.PSS_GETWFID;
			String pid = sop.getGroupProcessId();
			if (pid == null) 
				throw new ServiceOrderException("Process id isn't found for the order");

			// get signal bean from the map of signals
			pss = ProcessSignalStep.PSS_GETSIGNAL;
			IGroupSignal signalObj = (IGroupSignal) GroupSignal.getSignal(signalType, sop.getSoType(), soElement);
			if (signalObj==null) 
				throw new ServiceOrderException("Signal wasn't found in the signal map");


			// is the caller authorized to send this signal?
			// Authorization logic depends on user type and id from Identification and
			// ServiceOrder because many rules use order info. I.e., ROUTED_PROVIDER, ACCEPTED_PROVIDER, BUYER, ...
			pss = ProcessSignalStep.PSS_AUTHORIZE;
			signalObj.authorize(callerId, firstServiceOrder);

			// can this signal be processed? ask jBPM 
			pss = ProcessSignalStep.PSS_TRANSITIONCHECK;
			String transitionName = signalObj.getTransitionName();
			if (signalObj.isStateDependent()){
				if (!workflowManager.isTransitionAvailable(pid, transitionName))
					throw new TransitionNotAvailableException("Order workflow is in a state where "+	signalObj.getName()+" signal is prohibited");
			}

			// process signal
			logger.debug("[processGroupSignal]>>> "+signalType.name()+" for groupId "+groupId+". Calling signal process method");
			pss = ProcessSignalStep.PSS_SIGNALPROCESS;
			//the process method calls validate internally. If validation fails an ServiceOrderException should be thrown.
			signalObj.process(soElement, sog, callerId, miscParams);

			// now it's time to transition jBPM
			// this will fire all commands that are associated with the transition
			logger.debug("[processGroupSignal]>>> "+signalType.name()+" for groupId "+groupId+". Calling jBPM transition");
			pss = ProcessSignalStep.PSS_WFTRANSITION;
			if (signalObj.isStateDependent())
				workflowManager.takeTransition(new TransitionContext(pid, transitionName, miscParams));

			// save group order
			logger.debug("[processGroupSignal]>>> "+signalType.name()+" for groupId "+groupId+". Saving SO");
			pss = ProcessSignalStep.PSS_SAVESO;
			serviceOrderDao.update(sog);
			serviceOrderProcessDao.unlockByGroupId(groupId);//unlock group process so others can use it
		}
		catch (ServiceOrderException soe) {
			soe.addError(">> "+signalType.name()+" signal processing failed performing "+pss+". groupId="+groupId);
			throw soe;
		}
		catch (Exception e)	{
			ServiceOrderException soe = new ServiceOrderException();
			soe.addError(">> "+signalType.name()+" signal processing failed performing "+pss+". groupId="+groupId);
			soe.addError(">> General exception. Message:"+e.getMessage()+" Cause:"+e.getCause());
			logger.error(">> General exception stack trace:", e);
			logger.error(">> End of stack trace:");
			throw soe;
		}
	}

	public void processOrderSignal(String soId, SignalType signalType, Identification callerId, Map<String, Serializable> miscParams) {
		if (!groupSignals.contains(signalType)){
			return; //group does not know how to handle this signal
		}
		try{		
			//check if service order supports grouping and has group workflow
			ServiceOrder so = serviceOrderDao.getServiceOrder(soId);
			if (!this.doesBuyerSupportsGrouping(so.getBuyerId())){
				return; //buyer does not support grouping
			}
			ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(soId);
			if (StringUtils.isEmpty(sop.getGroupProcessId())){
				if (signalType == SignalType.EDIT_ORDER
						|| signalType == SignalType.PROVIDER_RELEASE_ORDER){
					//this order was does not have group process id
					//call create method
					//this will happen if the order was created with minimal information
					this.createServiceOrderGroup(callerId, so, false);
					return;
				}else {
					return; //order does not have group process This could happen with UPDATE_BATCH_ORDER and CANCEL_ORDER
				}
			}
			//it is possible to get the POST_ORDER when the order is grouped
			//this happens at least when the order is copied and it gets grouped with its twin
			//We will just ignore the Post and return
			if (signalType == SignalType.POST_ORDER 
					&& so.getSoGroup() != null){
				//throw new ServiceOrderException("Service order that is grouped should not get the post order signal");
				return;
			}

			String pid = sop.getGroupProcessId();
			ISignal signalObj = Signal.getSignal(signalType, sop.getSoType(), null);
			if (signalObj==null) 
				throw new ServiceOrderException("Signal wasn't found in the signal map");

			String transitionName = signalObj.getTransitionName();			
			if (miscParams == null)miscParams = new HashMap<String, Serializable>();
			miscParams.put(ProcessVariableUtil.PVKEY_EFFECTIVE_SERVICE_ORDER, soId);
			if (signalType == SignalType.UPDATE_BATCH_ORDER){
				miscParams.put(ProcessVariableUtil.PVKEY_IS_UPDATE_BATCH_GROUP,"true");
				miscParams.put(ProcessVariableUtil.PVKEY_UPDATED_SERVICE_ORDER, soId);
				logger.info("Upadted so"+soId);
			}
			if (signalObj.isStateDependent())
				workflowManager.takeTransition(new TransitionContext(pid, transitionName, miscParams ));

			//if the signal was edit order than check if the workflow has ended
			//if so call createServiceOrderGroup method
			if (signalType == SignalType.EDIT_ORDER){
				logger.debug("Signal is EDIT_ORDER --- checking if the workflow is done");
				if (!workflowManager.processExists(pid)){
					logger.info("Signal is EDIT_ORDER --- good workflow has finished we are creating new workflow");
					createServiceOrderGroup(callerId, so);
				}
			} else if (signalType == SignalType.UPDATE_BATCH_ORDER){
				//check if the process id is null out in the sop 
				//if it is than call create new group workflow            	
				Integer condRuleId = (Integer)miscParams.get("condAutoRouteRuleId");
				logger.info("condRuleId in ServiceOrderGroupBO: "+condRuleId);
				logger.debug("Signal is UPDATE_BATCH_ORDER --- checking if the order is kicked out of group workflow");
				sop = serviceOrderProcessDao.getServiceOrderProcess(soId);
				if (StringUtils.isEmpty(sop.getGroupProcessId()) && (condRuleId==null)){
					logger.info("Signal is UPDATE_BATCH_ORDER --- we are creating new workflow for this order");
					createServiceOrderGroup(callerId, so, false);       		      		
				}
			}
		}catch(TransitionNotAvailableException tnae){
			//we should ignore this exception because it is possible in many cases that there is no transition
			//for example START_PROCESS is only available when we add new order and group workflow is in Post state
			//for the REMOVE_ALL_ORDERS_FROM_GROUP we want to throw an error
			//because we do not want the rest of the un-grouping to proceed
			if (signalType == SignalType.REMOVE_ALL_ORDERS_FROM_GROUP){
				throw tnae;
			}
		}catch(ServiceOrderException soe){
			soe.addError(">> "+signalType.name()+" signal processing failed during group operation - soId="+soId);
			throw soe;
		}
		catch (Exception e)	{
			ServiceOrderException soe = new ServiceOrderException();
			soe.addError(">> "+signalType.name()+" signal processing failed - soId="+soId);
			soe.addError(">> General exception. Message:"+e.getMessage()+" Cause:"+e.getCause());
			logger.error(">> General exception stack trace:", e);
			logger.error(">> End of stack trace:");
			throw soe;
		}
	}

	private ServiceOrderProcess saveServiceOrderSearchData(ServiceOrder serviceOrder, String groupProcessId, String soGroupId, boolean makeGroupingSearchable){

		ServiceOrderProcess soProcess = serviceOrderProcessDao.getServiceOrderProcess(serviceOrder.getSoId());
		soProcess.setBuyerId(serviceOrder.getBuyerId());
		soProcess.setGroupProcessId(groupProcessId);
		logger.info("R16_0:SL-20638 : soId: "+ serviceOrder.getSoId());
		if(null != groupProcessId){
			logger.info("R16_0:SL-20638 : soGroupProcessId: "+ groupProcessId);
		}
		soProcess.setPrimarySkillCatId(serviceOrder.getPrimarySkillCatId());
		soProcess.setCity(serviceOrder.getServiceLocation().getCity());
		soProcess.setServiceDate1(serviceOrder.getSchedule().getServiceDate1());
		soProcess.setState(serviceOrder.getServiceLocation().getState());
		soProcess.setStreet1(serviceOrder.getServiceLocation().getStreet1());
		soProcess.setStreet2(serviceOrder.getServiceLocation().getStreet2());
		soProcess.setZip(serviceOrder.getServiceLocation().getZip());
		soProcess.setGroupingSearchable(makeGroupingSearchable);
		if(null != soGroupId)soProcess.setGroupId(soGroupId);

		logger.debug("Saving order group details to order process map");
		serviceOrderProcessDao.update(soProcess);

		return soProcess;
	}

	public void setGroupSignals(List<SignalType> groupSignals){
		this.groupSignals = groupSignals;
	}

	public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}

	public void markGroupNotSearchable(String soGroupId) {
		serviceOrderProcessDao.markGroupNotSearchable(soGroupId);
	}

	public SOGroup getGroup(String soGroupId) {
		return serviceOrderDao.getSOGroup(soGroupId);
	}

	public String addOrdersToGroup(String groupId, String firstSoId, List<String> soIds, Identification callerId) {
		//get so process map for the service order whose group process we are going to attach other orders to
		List<ServiceOrderProcess> soProcesses = new ArrayList<ServiceOrderProcess>();
		if(!StringUtils.isBlank(groupId)){
			SOGroup group = serviceOrderDao.getSOGroup(groupId);
			soProcesses.add(serviceOrderProcessDao.getServiceOrderProcess(group.getFirstServiceOrder().getSoId()));
		} else {
			soProcesses.add(serviceOrderProcessDao.getServiceOrderProcess(firstSoId));
		}
		int i = 0;
		for(String soId : soIds){
			ServiceOrder so = serviceOrderDao.getServiceOrder(soId);
			if(i == 0 && StringUtils.isBlank(groupId)){
				groupId = createOrderGroup(soProcesses, so, false, false);
			} else {
				addServiceOrderToGroup(soProcesses.get(0).getGroupId(), soProcesses.get(0).getGroupProcessId(), so, true);
			}
			i++;
		}

		Map<String,Serializable> miscParams = new HashMap<String, Serializable>();
		miscParams.put(ProcessVariableUtil.PVKEY_GROUP_ID, soProcesses.get(0).getGroupId());
		//Make sure that we update the number of service order in group
		SOGroup sog = serviceOrderDao.getSOGroup(soProcesses.get(0).getGroupId());
		miscParams.put(ProcessVariableUtil.PVKEY_NUMBER_OF_SERVICE_ORDER, String.valueOf(sog.getServiceOrders().size()));
		processGroupSignal(soProcesses.get(0).getGroupId(), SignalType.ADD_ORDERS_TO_GROUP, callerId, null, miscParams);

		return groupId;
	}


	public boolean hasGroupProcess(String soId) {
		ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(soId);
		return !StringUtils.isBlank(sop.getGroupProcessId());
	}

	public void setGroupPricingUtil(GroupPricingUtil groupPricingUtil) {
		this.groupPricingUtil = groupPricingUtil;
	}
}
