package com.servicelive.spn.common.detached.counterbucket;

import java.util.HashMap;
import java.util.Map;

import com.servicelive.domain.spn.detached.ProviderMatchingCountsVO;

public class BucketTranslator {

	public static Map<BucketType, ProviderMatchingCountsVO> translate(SpnBucket spnBucket) {
		Map<BucketType, ProviderMatchingCountsVO> result = new HashMap<BucketType, ProviderMatchingCountsVO>();
		for(GenericBucket genericBucket:spnBucket.getBuckets()) {
			ProviderMatchingCountsVO providerMatchingCountsVO = new ProviderMatchingCountsVO();
			providerMatchingCountsVO.setProviderCounts(0L);
			providerMatchingCountsVO.setProviderFirmCounts(0L);
			BucketType key = genericBucket.getBucketType();
			for (GenericBucket b:genericBucket.getBuckets()) {
				BucketType bt = b.getBucketType();
				if(bt.equals(BucketType.PROVIDER_FIRM)) {
					providerMatchingCountsVO.setProviderFirmCounts(b.getCount().longValue());
				} else if(bt.equals(BucketType.SERVICE_PROVIDER)) {
					providerMatchingCountsVO.setProviderCounts(b.getCount().longValue());
				}
			}
			result.put(key, providerMatchingCountsVO);
		}
		return result;
	}
}
