package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOCancellation;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.jbpm.TransientVariable;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class UpdateSOCancellationCmd extends SOCommand {

	@Override
	public void execute(Map<String, Object> processVariables) {
		
		ServiceOrder so = getServiceOrder(processVariables);
		String action = SOCommandArgHelper.extractStringArg(processVariables, 1);
		SOCancellation soCancellation = new SOCancellation();
		String amount = (String) processVariables.get(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT);
		Double cancelAmt = 0.0;
		if(null!=amount && !"".equals(amount)){
			cancelAmt = Double.parseDouble(amount);
		}
		soCancellation.setCancelAmt(cancelAmt);
		String reasonCode = (String)processVariables.get(OrderfulfillmentConstants.PVKEY_CANCELLATION_REASON_CODE);
		Integer code = null;
		if(null!=reasonCode){
			code = Integer.parseInt(reasonCode);
		}
		soCancellation.setCancelReasonCode(code);
		soCancellation.setCancelComments((String) processVariables.get(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT));
		if(null != processVariables.get(ProcessVariableUtil.PVKEY_IDENTIFICATION)){
			Identification id = (Identification) ((TransientVariable)processVariables.get(ProcessVariableUtil.PVKEY_IDENTIFICATION)).getObject(); 
			soCancellation.setModifiedBy(id.getUsername());
			logger.info("setusername");

		}
		soCancellation.setPreviousState(so.getLastStatusId());
		Boolean payInd = null;
		String payment = (String)processVariables.get(OrderfulfillmentConstants.PVKEY_PAY_PROVIDER_IND);
		String providerAck=(String)processVariables.get(OrderfulfillmentConstants.PVKEY_CANCELLATION_PROVIDER_ACKNOWLEDGEMENT_IND);
		if(null!=payment && !"".equals(payment)){
			payInd = Boolean.parseBoolean(payment);
		}
		if(null!=providerAck && !"".equals(providerAck) && providerAck.equalsIgnoreCase("false")){
			payInd = false;
		}
		soCancellation.setPaymentApproveInd(payInd);
		if(action!=null && action.equals("CANCEL")){
			soCancellation.setCancelInd(true);
		}else{
			soCancellation.setCancelInd(false);
		}
		soCancellation.setServiceOrder(so);
		serviceOrderDao.save(soCancellation);
		
	}
}