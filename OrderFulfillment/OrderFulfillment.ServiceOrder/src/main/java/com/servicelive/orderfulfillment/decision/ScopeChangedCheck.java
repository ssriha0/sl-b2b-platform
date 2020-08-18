package com.servicelive.orderfulfillment.decision;

import java.util.List;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;

public class ScopeChangedCheck extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6089068930818611942L;

	@SuppressWarnings("unchecked")
	public String decide(OpenExecution execution) {
		List<SOFieldsChangedType> changes = (List<SOFieldsChangedType>) execution.getVariable(ProcessVariableUtil.SERVICE_ORDER_CHANGES);
		if (changes.contains(SOFieldsChangedType.TASKS_ADDED) 
				|| changes.contains(SOFieldsChangedType.TASKS_DELETED)){
			return "true";
		}
		return "false";
	}

}
