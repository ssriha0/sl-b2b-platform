package com.servicelive.marketplatform.serviceinterface;

import java.util.List;

import com.servicelive.domain.common.ApplicationFlags;
import com.servicelive.domain.common.ApplicationProperties;
import com.servicelive.domain.common.InHomeOutBoundMessages;
import com.servicelive.domain.common.Contact;

public interface IMarketPlatformCommonLookupBO {
    Contact lookupContactInfo(Integer contactId);
    String lookupTimeZoneForZip(String zip);
    List<ApplicationProperties> getApplicationProperties();
    List<ApplicationFlags> getApplicationFlags();
	List<InHomeOutBoundMessages> getOutBoundStatusMessages();
}
