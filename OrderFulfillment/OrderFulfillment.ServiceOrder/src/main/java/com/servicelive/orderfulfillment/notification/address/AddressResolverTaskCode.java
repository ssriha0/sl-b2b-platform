package com.servicelive.orderfulfillment.notification.address;

public enum AddressResolverTaskCode {
    NONE,
    FetchBuyerEmail,
    FetchSOCreatorEmail,
    FetchRoutedProviderContacts,
    FetchAcceptedVendorContact,
    FetchBuyerAdmin,
    FetchProviderAdmin,
    FetchAssurantFtpDestination,
    FetchAssurantEmail,
    FetchServiceLiveEmail, 
    FetchRoutedNonAcceptedProvider,
    FetchServiceLiveSupportEmail,
    FetchRoutedNewlyProviderContacts;
}
