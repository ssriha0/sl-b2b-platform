package com.servicelive.spn.common.detached.counterbucket;

import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.detached.SpnMonitorCountVO;

public class ApplicationIncompleteProviderFirm extends GenericBucket {

	public ApplicationIncompleteProviderFirm() {
		super(BucketType.PROVIDER_FIRM);
	}

	@Override
	public void handleThisBucket(SpnMonitorCountVO vo) {
		String providerFirmSpnState = vo.getProviderFirmSpnState();
		if(!providerFirmSpnState.equals(SPNBackendConstants.WF_STATUS_PF_APPLICANT_INCOMPLETE)) {
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
