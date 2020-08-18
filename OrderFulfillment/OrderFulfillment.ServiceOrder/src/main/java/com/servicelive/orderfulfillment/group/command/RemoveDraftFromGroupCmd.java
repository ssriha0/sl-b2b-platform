package com.servicelive.orderfulfillment.group.command;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Sep 18, 2010
 * Time: 10:45:42 AM
 */
public class RemoveDraftFromGroupCmd extends GroupSignalSOCmd {
	@Override
	protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {

		logger.info("Inside cokmmand handleGroup : RemoveDraftFromGroupCmd"); 

		boolean statusDiffer = false;
		String isUpdate = "false";
		Integer groupStatus =OrderConstants.DRAFT_STATUS;
		if(null != processVariables.get(ProcessVariableUtil.PVKEY_IS_UPDATE_BATCH_GROUP)){
			isUpdate = (String)processVariables.get(ProcessVariableUtil.PVKEY_IS_UPDATE_BATCH_GROUP);
		}
		ServiceOrder eSO =null;
		if(null != soGroup){
			if(isUpdate.equals("true")){
				int size = soGroup.getServiceOrders().size();
				logger.info("the number of Sos in group:"+soGroup.getGroupId() +" size: "+size);
				String updatedSoId ="";
				if (null != processVariables
						.get(ProcessVariableUtil.PVKEY_UPDATED_SERVICE_ORDER)) {

					updatedSoId = (String) processVariables
							.get(ProcessVariableUtil.PVKEY_UPDATED_SERVICE_ORDER);
				}
				logger.info("SO in group with draft state:" +updatedSoId); 
				if(StringUtils.isNotBlank(updatedSoId)){
				eSO = serviceOrderDao.getServiceOrder(updatedSoId);
				
				logger.info("eSO status" +eSO.getWfStateId());
				}
				
				
				
				if(null != eSO ){
					for(ServiceOrder so :soGroup.getServiceOrders()){
						logger.info(" Service order :"+so.getSoId()+" and status: "+so.getWfStateId());

						if(!eSO.getWfStateId().equals(so.getWfStateId())){
							logger.info("status differnce exist");

							statusDiffer = true;
							groupStatus = so.getWfStateId();
							break;
						}
					}
					if(statusDiffer){
						//There are two orders in the group if we are removing one we need to delete the group record
						if (size == 2){ 
							logger.info("Removing all group details"); 

							String activeSoId = deleteGroup(soGroup, eSO.getSoId());
							//remove groupId from the process variable 
							//and put the serviceOrder id that is still active
							processVariables.put(ProcessVariableUtil.PVKEY_GROUP_ID, null);
							processVariables.put(ProcessVariableUtil.PVKEY_SERVICE_ORDER_IDS_FOR_GROUP_PROCESS, activeSoId);
							if(sendSpendLimitChange(processVariables)){
								//send spend limit changed signal to the remaining service order
								logger.info("Calling spend limit changed for so " + activeSoId);
								//ServiceOrder so = serviceOrderDao.getServiceOrder(activeSoId);
								//this.sendSignalToSOProcess(so, SignalType.SPEND_LIMIT_CHANGED, null, getProcessVariableForSendEmail(false));
							}
						}else{
							logger.info("Removing Single order grouping"); 

							for(ServiceOrder so : soGroup.getServiceOrders()){
								if (so.getSoId().equals(eSO.getSoId())){
									removeServiceOrderFromGroup(so, false);
								}
							} 			
						} 
					}

				}

			}else{
				// delete the whole group if it is having no providers (for order getting grouped in group queue)
				deleteGroup(soGroup);
			}
			processVariables.put(ProcessVariableUtil.PVKEY_ORDER_GROUP_STATUS, groupStatus);

		}
		processVariables.put(ProcessVariableUtil.PVKEY_IS_UPDATE_BATCH_GROUP,null);
		processVariables.put(ProcessVariableUtil.PVKEY_UPDATED_SERVICE_ORDER,null); 

	}

	/**
	 * @param soGroup
	 */
	private void deleteGroup(SOGroup soGroup) {

		for(ServiceOrder so : soGroup.getServiceOrders()){
			removeServiceOrderFromGroup(so);
		}
	}
	private void removeServiceOrderFromGroup(ServiceOrder so){

		logger.info("Inside cokmmand removeServiceOrderFromGroup"); 
		//delete the group from so
		so.setSoGroup(null);
		//updating soProcessMap
		ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(so.getSoId());
		sop.setGroupId(null);
		sop.setGroupProcessId(null);
		sop.setGroupingSearchable(false);
		serviceOrderDao.update(so);
		serviceOrderProcessDao.update(sop);

		logger.info("End cokmmand removeServiceOrderFromGroup"); 

	}
	@Override
	protected void handleServiceOrder(ServiceOrder so,Map<String, Object> processVariables) {

		logger.info("Inside cokmmand handleServiceOrder"); 
		removeServiceOrderFromGroup(so);

	}


	private void removeServiceOrderFromGroup(ServiceOrder so, boolean keepSearchable){
		if(null != so.getSoGroup()){
			//remove trip charge discount
			so.setSpendLimitLabor(so.getPrice().getOrigSpendLimitLabor());
			so.setSpendLimitParts(so.getPrice().getOrigSpendLimitParts());
			so.getPrice().reset();
			//delete the group from so
			so.setSoGroup(null);

			/*//**
			 * SL-18007 setting so price change history if there is a change in the
			 * so spend limit labour during batch update of service order from a group order.
			 */
			if (null != so.getSoPriceChangeHistory()) {

				logger.info("18007: so price change history contins: " + so.getSoPriceChangeHistory());

				SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();

				// set specific components before passing to the generic method
				newSOPriceChangeHistory.setAction(OFConstants.ORDER_REMOVED_FROM_GROUP);
				newSOPriceChangeHistory.setReasonComment(null);


				// call generic method to save so price change history			
				PricingUtil.addSOPriceChangeHistory(so, newSOPriceChangeHistory,so.getModifiedBy(), so.getModifiedByName());
			}
		}
		logger.info(">>inside removeServiceOrderFromGroup");
		//also update service order process map
		ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(so.getSoId());
		sop.setGroupId(null);
		if (!keepSearchable){
			// to make the SO not available for grouping in future
			sop.setGroupProcessId(null);
			sop.setGroupingSearchable(false);
		}
		serviceOrderProcessDao.update(sop);
	}


	private String deleteGroup(SOGroup soGroup, String effectedSOId){
		String activeSoId = null;
		for(ServiceOrder so : soGroup.getServiceOrders()){
			if (so.getSoId().equals(effectedSOId)){
				logger.info("deleteGroup soId:: "+effectedSOId);
				removeServiceOrderFromGroup(so, false);
			} else {
				removeServiceOrderFromGroup(so, true);
				activeSoId = so.getSoId();
			}
			serviceOrderDao.update(so);
		}
		serviceOrderDao.refresh(soGroup);
		serviceOrderDao.delete(soGroup);
		logger.info("deleteGroup final:: "+soGroup.getGroupId());

		return activeSoId;
	}

}
