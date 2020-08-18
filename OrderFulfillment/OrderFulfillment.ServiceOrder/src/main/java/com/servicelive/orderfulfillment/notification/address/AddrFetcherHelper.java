package com.servicelive.orderfulfillment.notification.address;

import org.apache.commons.lang.StringUtils;

import com.servicelive.domain.common.Contact;
import com.servicelive.orderfulfillment.notification.enumerations.NotificationType;

public class AddrFetcherHelper {
    public static String resolveDestAddrForContactInformation(Contact contactInformation, NotificationType notificationType) {
        String destAddress = StringUtils.EMPTY;

        if (contactInformation != null) {
            switch (notificationType) {
                case EMAIL:
                    destAddress = contactInformation.getEmail() != null ? contactInformation.getEmail() : StringUtils.EMPTY;
                    break;
                case SMS:
                    destAddress = contactInformation.getSmsNo() != null ? contactInformation.getMobileNo() : StringUtils.EMPTY;
                    break;
                case PUSH:
                    destAddress = contactInformation.getSmsNo() != null ? contactInformation.getEmail() : StringUtils.EMPTY;
                    break;
            }
        }
        return destAddress;
    }
}