package com.servicelive.marketplatform.service;

import com.servicelive.domain.common.ApplicationFlags;
import com.servicelive.domain.common.ApplicationProperties;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.common.InHomeOutBoundMessages;
import com.servicelive.domain.common.SimpleZipTimeZone;
import com.servicelive.marketplatform.dao.IApplicationPropertyDao;
import com.servicelive.marketplatform.dao.IContactDao;
import com.servicelive.marketplatform.dao.IGeoCodeDao;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformCommonLookupBO;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MarketPlatformCommonLookupBO implements IMarketPlatformCommonLookupBO {
    protected final Logger logger = Logger.getLogger(getClass());

    IContactDao contactDao;
    IGeoCodeDao geoCodeDao;
    IApplicationPropertyDao appPropDao;

    @Transactional(readOnly = true)
    public Contact lookupContactInfo(Integer contactId) {
        return contactDao.findById(contactId);
    }

    @Transactional(readOnly = true)
    public String lookupTimeZoneForZip(String zip) {
        SimpleZipTimeZone simpleZipTimeZone;
        try {
            simpleZipTimeZone = geoCodeDao.findById(zip);
        } catch (Exception e) {
            logger.error("Unable to lookup timezone for zip - " + zip, e);
            return null;
        }
        return (simpleZipTimeZone != null) ? simpleZipTimeZone.getTimeZoneId() : null;
    }

    @Transactional(readOnly = true)
    public List<ApplicationProperties> getApplicationProperties() {
        return appPropDao.getAllApplicationProperties();
    }
    @Transactional(readOnly = true)
    public List<ApplicationFlags> getApplicationFlags() {
        return appPropDao.getAllApplicationFlags();
    }
    @Transactional(readOnly = true)
    public List<InHomeOutBoundMessages> getOutBoundStatusMessages() {
    	return appPropDao.getOutBoundStatusMessages();
	}
    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setContactDao(IContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public void setGeoCodeDao(IGeoCodeDao geoCodeDao) {
        this.geoCodeDao = geoCodeDao;
    }

    public void setAppPropDao(IApplicationPropertyDao appPropDao) {
        this.appPropDao = appPropDao;
    }

	

}
