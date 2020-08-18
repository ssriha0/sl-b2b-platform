package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * User: Mustafa Motiwala
 * Date: Mar 18, 2010
 * Time: 11:57:35 AM
 */
public class ServiceOrderDraftStateCheck extends AbstractServiceOrderDecision  {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -1685517934179070613L;

	public String decide(OpenExecution openExecution) {
        ServiceOrder so = getServiceOrder(openExecution);
        Boolean createWithOutTasks = (Boolean)openExecution.getVariable(ProcessVariableUtil.CREATE_WITHOUT_TASKS);
        if(null != createWithOutTasks && createWithOutTasks)
        	return "forward";
        else if((so.getTasks().isEmpty() || so.getPrimarySkillCatId() == null)) return "wait";
        else return "forward";
    }
}
