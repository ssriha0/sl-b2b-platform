package com.servicelive.bus.event.order;

/**
 * User: Mustafa Motiwala
 * Date: Mar 31, 2010
 * Time: 10:58:31 PM
 */
public enum OrderEventType {
    CREATED,
    POSTED,
    ACCEPTED,
    EXPIRED,
    ACTIVATED,
    COMPLETED,
    CLOSED,
    PROBLEM,
    CANCELLED,
    VOIDED,
    DELETED,
    SUBSTATUS_CHANGE,
    BUYER_NOTIFICATION,
    PARTS_SHIPPED,
    PENDINGCANCEL
}
