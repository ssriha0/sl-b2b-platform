package com.servicelive.orderfulfillment.notification;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTaskAddresses;
import com.servicelive.orderfulfillment.notification.address.AddressCodeSet;

public interface INotificationTaskAddressesResolver {
    public ServiceOrderNotificationTaskAddresses resolveAddresses(AddressCodeSet recipientCodeSet, NotificationInfo notificationInfo);
}
