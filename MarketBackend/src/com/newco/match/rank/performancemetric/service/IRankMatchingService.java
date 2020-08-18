package com.newco.match.rank.performancemetric.service;

import java.util.List;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;

public interface IRankMatchingService {

	String getProviderByRankMatchingMetric(List<D2CProviderAPIVO> firmDetailsListVo, String buyerId);

}
