/**
 * 
 */
package com.servicelive.orderfulfillment.decision;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author Infosys
 *
 */
public class ProviderForInhomeAutoAcceptanceEligibilityCheck extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProviderForInhomeAutoAcceptanceEligibilityCheck.class);
	public String decide(OpenExecution execution) {
		ServiceOrder serviceOrder = getServiceOrder(execution);
		List<RoutedProvider>  eligibleProvider = new ArrayList<RoutedProvider>();
		List<RoutedProvider>  providerVO = serviceOrder.getRoutedResources();
		if(null!=providerVO && providerVO.size()> 0){
		    Integer vendorId = serviceOrder.getInhomeAcceptedFirm() != null ? serviceOrder.getInhomeAcceptedFirm().intValue() : null;
			    if(null!= vendorId){
			    	for(RoutedProvider routedProvider:providerVO){
			    		if(null!= routedProvider.getVendorId() && vendorId.equals(routedProvider.getVendorId())){
			    			eligibleProvider.add(routedProvider);
			    		}
			    	}
			    }
			    if(!eligibleProvider.isEmpty()){
			    	return "YES";
			    }else{
			    	execution.setVariable(ProcessVariableUtil.REPEAT_REPAIR_NOTE,"noEligibleProviderFound");
		            return "NO";
			    }
			    
		 }else{
		   logger.info("No Eligible Provider found for the order");
		   execution.setVariable(ProcessVariableUtil.REPEAT_REPAIR_NOTE,"noEligibleProviderFound");
		   return "NO";  
		 }
	}
}
