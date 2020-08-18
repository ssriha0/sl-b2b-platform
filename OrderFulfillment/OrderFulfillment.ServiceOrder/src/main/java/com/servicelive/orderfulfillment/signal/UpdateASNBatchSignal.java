package com.servicelive.orderfulfillment.signal;

import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.LegacySOSubStatus;

public class UpdateASNBatchSignal extends UpdateBatchSignal {

	protected void updateScope(ServiceOrder source, ServiceOrder target, boolean primaryCategoryChanged, boolean tasksChanged) {
		target.setWfSubStatusId(LegacySOSubStatus.RESEARCH.getId());
		serviceOrderDao.update(target);
		super.updateScope(source, target, primaryCategoryChanged, tasksChanged);
	}

    @Override
    protected void updatePricing(ServiceOrder source, ServiceOrder target){
         //We do not update price since price is not part of the file
    }

    @Override
    protected void updateServiceLocation(SOLocation source, ServiceOrder so, boolean zipChanged){
        //For assurant we do not update the zip code at all
        if(zipChanged && null != so.getServiceLocation()){
            source.setZip(so.getServiceLocation().getZip()); //make the zip code same so super class does not change it
        }
        super.updateServiceLocation(source, so, false); //also make the zip changed flag false since we are not changing it
    }

    @Override
    protected void updateParts(ServiceOrder source, ServiceOrder so){
        //we only update parts if the order is not routed to providers
        if(null != so.getRoutedResources() && so.getRoutedResources().size() > 0) return;
        super.updateParts(source, so);
    }
}
