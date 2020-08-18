/**
 * 
 */
package com.servicelive.orderfulfillment.command;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOScheduleHistory;
import com.servicelive.orderfulfillment.domain.SOScheduleStatus;
import com.servicelive.orderfulfillment.domain.ServiceOrder;


/**
 * @author Infosys
 *
 */
public class ClearRecallOrderDetails extends SOCommand {

	public void execute(Map<String, Object> processVariables) {
		ServiceOrder serviceOrder = getServiceOrder(processVariables);
		/** Remove Recall Order Details saved in so_work_flow controlls*/
		SOWorkflowControls sOWorkflowControls = serviceOrder.getSOWorkflowControls();
		if(null!= sOWorkflowControls ){
			logger.info("going to clear the so_wrkflowcntrl_table");
			sOWorkflowControls.setWarrantyProviderFirm(null);
			sOWorkflowControls.setOriginalSoId(null);
			serviceOrder.setSOWorkflowControls(sOWorkflowControls);
		    serviceOrderDao.update(serviceOrder);
	    }
	  }
}
