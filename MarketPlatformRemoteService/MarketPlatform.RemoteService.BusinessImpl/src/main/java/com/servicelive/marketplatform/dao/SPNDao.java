package com.servicelive.marketplatform.dao;

import com.servicelive.domain.spn.network.SimpleSPNTierReleaseInfo;

import javax.persistence.Query;
import java.util.List;

public class SPNDao extends BaseEntityDao implements ISPNDao {
    public List<SimpleSPNTierReleaseInfo> retrieveTierReleaseInfoWithSPNAndStartingTier(Integer spnId, Integer startingTier) {
        Query q = entityMgr.createQuery("SELECT tierReleaseInfo FROM SimpleSPNTierReleaseInfo tierReleaseInfo WHERE tierReleaseInfo.spnTierReleasePK.spnId.spnId = :spnId AND tierReleaseInfo.spnTierReleasePK.tierId >= :tierId ORDER BY tierReleaseInfo.spnTierReleasePK.tierId");
        q.setParameter("spnId", spnId);
        q.setParameter("tierId", startingTier);
        List result = q.getResultList();
        if (result.size() > 0) {
            return (List<SimpleSPNTierReleaseInfo>) result;
        }
        return null;
    }

    public List<SimpleSPNTierReleaseInfo> retrieveAllAvailableTierReleaseInfo() {
        Query q = entityMgr.createQuery("SELECT tierReleaseInfo FROM SimpleSPNTierReleaseInfo tierReleaseInfo");
        return (List<SimpleSPNTierReleaseInfo>) q.getResultList();
    }
}
