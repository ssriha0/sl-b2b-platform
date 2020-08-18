package com.servicelive.spn.common.detached.counterbucket;

import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.detached.SpnMonitorCountVO;

public class ApplicationIncompleteServiceProvider extends GenericBucket {

	public ApplicationIncompleteServiceProvider() {
		super(BucketType.SERVICE_PROVIDER);
	}

	@Override
	public void handleThisBucket(SpnMonitorCountVO vo) {
		String providerFirmSpnState = vo.getProviderFirmSpnState();
		if(!providerFirmSpnState.equals(SPNBackendConstants.WF_STATUS_PF_APPLICANT_INCOMPLETE)) {
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