package com.servicelive.orderfulfillment.decision;

import java.util.List;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.IWalletGateway;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Apr 15, 2010
 * Time: 5:03:14 PM
 */
public class SpendLimitChanged extends AbstractServiceOrderDecision {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5758858102666017230L;
	private IWalletGateway walletGateway;
	private Logger logger = Logger.getLogger(getClass());
    /**
     * the name of the selected outgoing transition
     */
	@SuppressWarnings("unchecked")
    public String decide(OpenExecution execution) {
    	List<SOFieldsChangedType> changes = (List<SOFieldsChangedType>) execution.getVariable(ProcessVariableUtil.SERVICE_ORDER_CHANGES);
    	ServiceOrder serviceOrder = getServiceOrder(execution);
    	String isUpdate = (String)execution.getVariable(ProcessVariableUtil.ISUPDATE);
        double projectBalance = walletGateway.getCurrentSpendingLimit(serviceOrder.getSoId());
        if (projectBalance > serviceOrder.getTotalSpendLimit().doubleValue()){
            return "decrease";
        }else if (projectBalance < serviceOrder.getTotalSpendLimit().doubleValue()){
            return "increase";
        }
        else if(changes !=null && changes.size()>0){
        if (isUpdate!=null && isUpdate.equals("true") && (serviceOrder.getWfStateId()==100||serviceOrder.getWfStateId()==110)
        		&&(changes.contains(SOFieldsChangedType.TASKS_ADDED)||changes.contains(SOFieldsChangedType.TASKS_DELETED)
        		||changes.contains(SOFieldsChangedType.SCHEDULE)||changes.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY)
        		||changes.contains(SOFieldsChangedType.SERVICE_LOCATION_ZIP_CHANGED)
				|| changes.contains(SOFieldsChangedType.SCHEDULE))){
        	logger.info("WF_State_id in repost of SpendLimitChanged:" +serviceOrder.getWfStateId());
        	return "repost";
        }
        }
        return "noChange";
    }
    
	public void setGateWay(IWalletGateway gateWay) {
		this.walletGateway = gateWay;
	}
}
