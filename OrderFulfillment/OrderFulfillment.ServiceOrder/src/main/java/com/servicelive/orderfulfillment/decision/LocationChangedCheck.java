package com.servicelive.orderfulfillment.decision;

import java.util.List;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;

public class LocationChangedCheck extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7258373364727565809L;

	@SuppressWarnings("unchecked")
	public String decide(OpenExecution execution) {
		List<SOFieldsChangedType> changes = (List<SOFieldsChangedType>) execution.getVariable(ProcessVariableUtil.SERVICE_ORDER_CHANGES);
		if (changes.contains(SOFieldsChangedType.SERVICE_LOCATION)){
			return "true";
		}
		return "false";
	}

}
