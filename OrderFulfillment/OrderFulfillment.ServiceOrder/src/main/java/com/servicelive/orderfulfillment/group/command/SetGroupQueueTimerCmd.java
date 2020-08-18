package com.servicelive.orderfulfillment.group.command;

import java.util.Calendar;
import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;

public class SetGroupQueueTimerCmd extends SOGroupCmd {
    QuickLookupCollection quickLookupCollection;

	@Override
	protected void handleGroup(SOGroup soGroup,
			Map<String, Object> processVariables) {
        ServiceOrder so = soGroup.getFirstServiceOrder();
		handleServiceOrder(so, processVariables);
	}

	@Override
	protected void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
		Calendar expirationTime = Calendar.getInstance();
        Boolean useHoldTime = (Boolean) processVariables.get(ProcessVariableUtil.USE_HOLD_TIME_FOR_QUEUE);

        //check if the order has been posted before and if so than set the expiration to five seconds
        if(null != useHoldTime && useHoldTime.booleanValue() == false){
            //expirationTime.add(Calendar.SECOND, 1); //no need for this since we never output the seconds it is only hours and minutes
        } else {//use the hold time configuration to set the expiration time
            Calendar serviceStartDate = so.getServiceStartDateTimeCalendar();
            Calendar createdDate = Calendar.getInstance();
            createdDate.setTime(so.getCreatedDate());

            //get the difference between created and service date
            long diff = serviceStartDate.getTimeInMillis() - createdDate.getTimeInMillis();

            //convert to days difference
            int daysDiff = (int) (diff / 86400000);
            int delayInMinutes = quickLookupCollection.getBuyerHoldTimeLookup().getHoldTime(so.getBuyerId(), daysDiff);

            expirationTime.add(Calendar.MINUTE, delayInMinutes);
        }
		processVariables.put(ProcessVariableUtil.GROUP_QUEUE_EXPIRATION, ProcessVariableUtil.convertToJPDLDueDate(expirationTime));
	}

	public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}


}
