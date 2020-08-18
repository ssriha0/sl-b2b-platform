package com.servicelive.spn.common.detached.counterbucket;

import com.servicelive.spn.common.detached.SpnMonitorCountVO;

public class CompositeBucket extends GenericBucket {
	
	public CompositeBucket(BucketType bucketType) {
		super(bucketType);
	}

	@Override
	public void handleThisBucket(SpnMonitorCountVO vo) {
		// do nothing just let process do it's work.
		return;
	}
}
