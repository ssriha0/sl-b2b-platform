package com.servicelive.orderfulfillment.decision;
import java.util.ArrayList;
import java.util.List;
import  org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.command.UpdateSubStatusCmd;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
public class ManualReviewCheck extends AbstractServiceOrderDecision {

	private static final long serialVersionUID = 7258373364727565809L;
	private static final Logger logger = Logger.getLogger(ManualReviewCheck.class);

	/*
	 * Checks if MANUAL_REVIEW feature set is on.If on sos will be placed in "draft" status for review by buyer agent
	 */
	@SuppressWarnings("unchecked")
	public String decide(OpenExecution execution) {
		logger.info("Inside ManualReviewCheck");

		ServiceOrder so = getServiceOrder(execution);
		List<SOCustomReference> customReferences = new ArrayList<SOCustomReference>();
		customReferences = so.getCustomReferences();
		if (customReferences != null && !customReferences.isEmpty()) {
			for(SOCustomReference customRef:customReferences){
				if(null !=customRef.getBuyerRefTypeName() && customRef.getBuyerRefTypeName().equalsIgnoreCase(SOCustomReference.MANUAL_REVIEW)){
					String refValue=customRef.getBuyerRefValue();
					if(null!=refValue && !(StringUtils.isBlank(refValue))){
						logger.info("Inside ReviewNeeded");
						return "ReviewNeeded";

					}
					else{
						logger.info("Inside NoReviewNeeded");

						return "NoReviewNeeded";
					}
				}
			}
		}
		return "NoReviewNeeded";
	}

}