package com.newco.match.rank.service;

import java.util.List;

import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;

public interface IMatchCriteriaService {

	void calculateAndSaveMatchingCriteria(D2CPortalAPIVORequest d2cPortalAPIVO, List<D2CProviderAPIVO> firmDetailsListVo) throws Exception;

}
