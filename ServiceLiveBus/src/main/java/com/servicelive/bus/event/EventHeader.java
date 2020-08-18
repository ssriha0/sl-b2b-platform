package com.servicelive.bus.event;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * An enum that defines all possible values allowed for event header.
 * User: Mustafa Motiwala
 * Date: Mar 26, 2010
 * Time: 4:39:18 PM
 */
@XmlType
@XmlEnum(String.class)
public enum EventHeader {
    SERVICE_ORDER_ID,
    GROUP_SERVICE_ORDER_ID,
    BUYER_RESOURCE_ID,
    BUYER_COMPANY_ID,
    PROVIDER_COMPANY_ID,
    PROVIDER_RESOURCE_ID,
    EVENT_TYPE,
    SUBSTATUS_ACTION,
    SUBSTATUS_REASON,
    ORDER_CREATION_TYPE;
}
