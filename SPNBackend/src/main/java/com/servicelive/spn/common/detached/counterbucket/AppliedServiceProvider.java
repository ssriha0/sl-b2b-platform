package com.servicelive.spn.common.detached.counterbucket;

import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.detached.SpnMonitorCountVO;

public class AppliedServiceProvider extends GenericBucket {

	public AppliedServiceProvider() {
		super(BucketType.SERVICE_PROVIDER);
	}

	@Override
	public void handleThisBucket(SpnMonitorCountVO vo) {
		String providerFirmSpnState = vo.getProviderFirmSpnState();
		// Code Added for Jira SL-19384
		//Added Comparison code for 'Membership Under Review' status
		if(!providerFirmSpnState.equals(SPNBackendConstants.WF_STATUS_PF_SPN_APPLICANT) && !providerFirmSpnState.equals(SPNBackendConstants.WF_STATUS_PF_SPN_REAPPLICANT) && !providerFirmSpnState.equals(SPNBackendConstants.WF_STATUS_PF_SPN_MEMBERSHIP_UNDER_REVIEW)) {
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
