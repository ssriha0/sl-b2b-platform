package com.servicelive.spn.common.detached.counterbucket;

import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.detached.SpnMonitorCountVO;

public class InvitedServiceProvider extends GenericBucket {

	public InvitedServiceProvider() {
		super(BucketType.SERVICE_PROVIDER);
	}

	@Override
	public void handleThisBucket(SpnMonitorCountVO vo) {
		String providerFirmSpnState = vo.getProviderFirmSpnState();
		if(!providerFirmSpnState.equals(SPNBackendConstants.WF_STATUS_PF_INVITED_TO_SPN)) {
			return;
		}
		Integer key = vo.getServiceProviderId();
		getMap().put(key, vo);
	}
	
	/**
	 * 
	 */
	public void process(SpnMonitorCountVO vo) {
		handleThisBucket(vo);
	}

}
