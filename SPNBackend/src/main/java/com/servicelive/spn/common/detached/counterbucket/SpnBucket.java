package com.servicelive.spn.common.detached.counterbucket;

import com.servicelive.spn.common.detached.SpnMonitorCountVO;

public class SpnBucket extends GenericBucket {

	private Integer spnId;

	/**
	 * 
	 * @param spnId
	 */
	public SpnBucket(Integer spnId) {
		super(BucketType.SPN);
		this.spnId = spnId;
	}

	@Override
	public void process(SpnMonitorCountVO vo) {
		Integer pSpnId = vo.getSpnId();
		if(!pSpnId.equals(spnId)) {
			return;
		}

		super.process(vo);
	}

	@Override
	public void handleThisBucket(SpnMonitorCountVO vo) {
		// this bucket doesn't really need a count
		return;
	}
}
