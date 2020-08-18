package com.servicelive.spn.common.detached.counterbucket;

import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.detached.SpnMonitorCountVO;

public class InvitedProviderFirm extends GenericBucket {

	public InvitedProviderFirm() {
		super(BucketType.PROVIDER_FIRM);
	}

	@Override
	public void handleThisBucket(SpnMonitorCountVO vo) {
		String providerFirmSpnState = vo.getProviderFirmSpnState();
		if(!providerFirmSpnState.equals(SPNBackendConstants.WF_STATUS_PF_INVITED_TO_SPN)) {
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
