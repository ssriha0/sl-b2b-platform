package com.servicelive.orderfulfillment.group.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.orderfulfillment.ServiceOrderBO;
import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;

public abstract class GroupSignalSOCmd extends SOGroupCmd {

	private ServiceOrderBO serviceOrderBO;
	protected List<String> groupProcessKeys = new ArrayList<String>();

	public GroupSignalSOCmd(){
		groupProcessKeys.add(ProcessVariableUtil.PVKEY_GROUP_ID);
		groupProcessKeys.add(ProcessVariableUtil.PVKEY_SERVICE_ORDER_IDS_FOR_GROUP_PROCESS);
	}
	
    protected void sendSignalToSOProcess(SOGroup soGroup, SignalType signalType, SOElement soElement, Map<String, Serializable> processVariables){
		Identification callerId = getIdentification(soGroup.getFirstServiceOrder(), signalType);

		for (ServiceOrder so : soGroup.getServiceOrders())
			serviceOrderBO.processOrderSignal(so.getSoId(), signalType, callerId, soElement, processVariables);
	}
	
	protected void sendSignalToSOProcess(ServiceOrder so, SignalType signalType, SOElement soElement, Map<String, Serializable> processVariables){
		Identification callerId = getIdentification(so, signalType);
		serviceOrderBO.processOrderSignal(so.getSoId(), signalType, callerId, soElement, processVariables);
	}
	
	public void setServiceOrderBO(ServiceOrderBO serviceOrderBO){
		this.serviceOrderBO = serviceOrderBO;
	}
	
	public Identification getIdentification(ServiceOrder so, SignalType signalType){
		Identification id = new Identification();
        id.setUsername(OFConstants.SL_ADMIN_USERNAME);
        id.setRoleId(OFConstants.SL_ADMIN_ROLE_ID);
		id.setEntityType(EntityType.SLADMIN);
		return id;
	}

	protected Map<String, Serializable> getProcessVariableForSendEmail(boolean sendEmail){
		Map<String, Serializable> processVariable = new HashMap<String, Serializable>();
		if (!sendEmail){
			processVariable.put(OrderfulfillmentConstants.PVKEY_SEND_EMAIL, Boolean.FALSE);
		}
		
		return processVariable;
	}

    protected void clearEffectiveServiceOrder(Map<String, Object> processVariables){
        //clear out the effective service order
        processVariables.put(ProcessVariableUtil.PVKEY_EFFECTIVE_SERVICE_ORDER, null);
    }

    protected ServiceOrder getEffectiveServiceOrder(Map<String, Object> processVariables){
        if ( processVariables.get(ProcessVariableUtil.PVKEY_EFFECTIVE_SERVICE_ORDER) == null){
            throw new ServiceOrderException("Need to know which service order was effected by this transition");
        }
        String effectedSOId = (String) processVariables.get(ProcessVariableUtil.PVKEY_EFFECTIVE_SERVICE_ORDER);
        ServiceOrder eSO = serviceOrderDao.getServiceOrder(effectedSOId);
        return eSO;
    }

    protected boolean sendSpendLimitChange(Map<String, Object> processVariables) {
        String spendLimit = SOCommandArgHelper.extractStringArg(processVariables, 1);
        return spendLimit.equals("spendLimit");
    }

    protected boolean sendPostSignal(Map<String, Object> processVariables){
        String post = SOCommandArgHelper.extractStringArg(processVariables, 1);
        return post.equals("post");
    }

}
