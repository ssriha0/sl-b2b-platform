package com.servicelive.marketplatform.dao;

import com.servicelive.domain.common.SimpleZipTimeZone;

public interface IGeoCodeDao {
    SimpleZipTimeZone findById(String zip);
}
