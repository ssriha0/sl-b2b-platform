package com.servicelive.orderfulfillment.signal;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Sep 13, 2010
 * Time: 11:33:35 AM
 */
public class UnPostSignal extends Signal {
    
    @Override
	protected void update(SOElement soe, ServiceOrder so)  {
		logger.debug("Inside UnPostSignal.process()");
        //remove all provider resources
        for(RoutedProvider rp : so.getRoutedResources()){
            serviceOrderDao.delete(rp);
        }
        so.getRoutedResources().clear();
        serviceOrderDao.update(so);
	}
    @Override
	public void accessMiscParams(Map<String, Serializable> miscParams, Identification id, ServiceOrder so) {	
    	logger.info("EditSignal setting IS_CURRENTLY_POSTED for SOID: "+ so.getSoId());
		if(OFConstants.POSTED_STATUS.equals(so.getWfStateId())){
			miscParams.put(ProcessVariableUtil.IS_CURRENTLY_POSTED, true);
		}
		else{
			miscParams.put(ProcessVariableUtil.IS_CURRENTLY_POSTED, false);
		}
	}


}
