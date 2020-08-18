package com.servicelive.orderfulfillment.signal;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;


/**
 * @author Mustafa Motiwala
 *
 */
public class RequestRescheduleSignal extends RescheduleSignal {

	private static  final Logger logger = Logger.getLogger(RequestRescheduleSignal.class);
    @Override
	protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
    	logger.info("Entering validate method of RequestRescheduleSignal");
		List<String> returnVal = new ArrayList<String>();
		 //Modified for JIRA SL-19291
        //altered to allow edit of reschedule request
		String schedule = getName();
		Integer roleId = 0;
		if (schedule.equalsIgnoreCase(OFConstants.PROVIDER_RESCHEDULE)) {
			roleId = OrderfulfillmentConstants.PROVIDER_ROLEID;
		} else if (schedule.equalsIgnoreCase(OFConstants.BUYER_RESCHEDULE)) {
			roleId = OrderfulfillmentConstants.BUYER_ROLE_ID;
		}

		if (null != soTarget.getReschedule()) {
			logger.info("RoleId based on signal called:"+roleId);
			logger.info("RoleId from DB:"+soTarget.getRescheduleRole());

			if (!soTarget.getRescheduleRole().equals(roleId)) {
				returnVal
						.add("A Reschedule request already pending. You may have one reschedule request at a time.");
			}
		}
		return returnVal;
	}	

    @Override
    protected void reschedule(SOSchedule requestedSchedule, ServiceOrder so) {
        so.setReschedule(requestedSchedule);
        String schedule=getName();
        
        if(schedule.equalsIgnoreCase(OFConstants.PROVIDER_RESCHEDULE)){
        	String reason=requestedSchedule.getReason();
        	if(null!=reason&&!reason.equalsIgnoreCase("")){
        		so.setWfSubStatusId(Integer.parseInt(reason));
        	}   
        }
        //SL-18893 
    	//To clear the subStatus in case of problem status.
        if(null != so.getWfStateId() && 170 == so.getWfStateId().intValue()){
        	so.setWfSubStatusId(null);
        }
        //SL-19291 Inserting the new reschedule data to buyer preferred details columns in so_workflow_controls 
        //when buyer requests reschedule.
        if(schedule.equalsIgnoreCase(OFConstants.BUYER_RESCHEDULE)){
        	so.getSOWorkflowControls().setBuyerSchedule(requestedSchedule);
        	//SL-18893 
        	//To clear the subStatus as soon as RescheduleRequest is placed by the buyer.
            so.setWfSubStatusId(null);
        }
     
    }
}
