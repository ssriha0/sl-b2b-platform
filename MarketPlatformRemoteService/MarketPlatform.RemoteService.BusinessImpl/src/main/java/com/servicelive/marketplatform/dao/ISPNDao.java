package com.servicelive.marketplatform.dao;

import com.servicelive.domain.spn.network.SimpleSPNTierReleaseInfo;

import java.util.List;

public interface ISPNDao {
    List<SimpleSPNTierReleaseInfo> retrieveTierReleaseInfoWithSPNAndStartingTier(Integer spnId, Integer startingTier);
    List<SimpleSPNTierReleaseInfo> retrieveAllAvailableTierReleaseInfo();
}
