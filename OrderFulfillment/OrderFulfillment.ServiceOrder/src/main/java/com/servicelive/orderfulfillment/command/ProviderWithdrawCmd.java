package com.servicelive.orderfulfillment.command;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import com.servicelive.domain.pendingcancel.PendingCancelHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;


public class ProviderWithdrawCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {


		ServiceOrder so = getServiceOrder(processVariables);
		PendingCancelHistory pendingCancelProviderHistory = serviceOrderDao
				.getPendingCancelCriteria(OrderfulfillmentConstants.PROVER_ROLE_ID, so.getSoId());
		PendingCancelHistory pendingCancelHistory = new PendingCancelHistory();
		if(null!=pendingCancelProviderHistory){
		pendingCancelHistory.setComments(pendingCancelProviderHistory.getComments());
		pendingCancelHistory.setPrice(pendingCancelProviderHistory.getPrice());
		pendingCancelHistory.setWfStateId(pendingCancelProviderHistory.getPendingCancelHistoryId());
		}		
		pendingCancelHistory.setRoleId(OrderfulfillmentConstants.PROVER_ROLE_ID);
		pendingCancelHistory.setSoId(so.getSoId());		
		pendingCancelHistory.setWithdrawFlag(true);

		Object adminUserName = processVariables.get(OrderfulfillmentConstants.PVKEY_ADMIN_USER_NAME);
		if (adminUserName != null){
			pendingCancelHistory.setAdminUserName(adminUserName.toString());
		}

		Object adminResourceId = processVariables.get(OrderfulfillmentConstants.PVKEY_ADMIN_USER_ID);
		if (adminResourceId != null && !adminResourceId.toString().equals("")){
			pendingCancelHistory.setAdminResourceId(Integer.parseInt(adminResourceId.toString()));
		}

		Object userName = processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME);
		if (userName != null){
			pendingCancelHistory.setModifiedBy(userName.toString());
		}

		Object userId = processVariables.get(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID);
		if (userId != null && !userId.toString().equals("")){
			pendingCancelHistory.setUserId(Integer.parseInt(userId.toString()));
		}
		//In order to update the Service Order Sub Status when provider withdraws, we are checking the last buyer action.
		PendingCancelHistory pendingCancelBuyerHistory = serviceOrderDao
		.getPendingCancelHistory(OrderfulfillmentConstants.BUYER_ROLE_ID, so.getSoId());
		if(null!=pendingCancelBuyerHistory && null!=pendingCancelBuyerHistory.getWfStateId() && pendingCancelBuyerHistory.getWfStateId().equals(OrderfulfillmentConstants.PENDING_RESPONSE))       
		{
			processVariables.put(OrderfulfillmentConstants.PREVIOUS_SUBSTATUS,"pendingResponse");	
		}else{
			processVariables.put(OrderfulfillmentConstants.PREVIOUS_SUBSTATUS,"noSubstatus");
		}
		processVariables.put("SOID",so.getSoId());
		
		PendingCancelHistory pendingCancelWithdrawHistory=serviceOrderDao.getPendingCancelHistory(OrderfulfillmentConstants.PROVER_ROLE_ID, so.getSoId());
		if(null!=pendingCancelWithdrawHistory && null!=pendingCancelWithdrawHistory.getPrice())
		{
			NumberFormat formatter = new DecimalFormat("###0.00");
			String cancelProviderPrice=formatter.format(pendingCancelWithdrawHistory.getPrice());
			processVariables.put("withdrawAmount",cancelProviderPrice)	;
		}
		
		serviceOrderDao.pendingCancelUpdation(pendingCancelHistory);
		
		
		
	}

}
