package com.servicelive.spn.common.detached.counterbucket;

import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.detached.SpnMonitorCountVO;

public class AppliedProviderFirm extends GenericBucket {

	public AppliedProviderFirm() {
		super(BucketType.PROVIDER_FIRM);
	}

	@Override
	public void handleThisBucket(SpnMonitorCountVO vo) {
		String providerFirmSpnState = vo.getProviderFirmSpnState();
		// Code Added for Jira SL-19384
		//Added Comparison code for 'Membership Under Review' status
		if(!providerFirmSpnState.equals(SPNBackendConstants.WF_STATUS_PF_SPN_APPLICANT) && !providerFirmSpnState.equals(SPNBackendConstants.WF_STATUS_PF_SPN_REAPPLICANT) && !providerFirmSpnState.equals(SPNBackendConstants.WF_STATUS_PF_SPN_MEMBERSHIP_UNDER_REVIEW)) {
			return;
		}
		Integer providerFirmId = vo.getProviderFirmId();
		getMap().put(providerFirmId, vo);
		
	}
	
	/**
	 * 
	 */
	public void process(SpnMonitorCountVO vo) {
		handleThisBucket(vo);
	}

}
