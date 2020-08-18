package com.servicelive.marketplatform.dao;

import com.servicelive.domain.common.SimpleZipTimeZone;

public class GeoCodeDao extends BaseEntityDao implements IGeoCodeDao {
    public SimpleZipTimeZone findById(String zip) {
        return entityMgr.find(SimpleZipTimeZone.class, zip);
    }
}
