package com.newco.match.rank.service;

import java.util.List;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;

public interface IRankMetricsBO {

	String getProviderByRankCriteria(List<D2CProviderAPIVO> firmDetailsListVo, String buyerId);
	
}
