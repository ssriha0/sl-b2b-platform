package com.servicelive.orderfulfillment.notification.address;

public enum AddressCode {
    A (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.NONE},
            "Admins"
    ),
    AB (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.NONE},
            "All Buyers"
    ),
    ALL_RVR     (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchRoutedProviderContacts},
            "All Routed Vendor Resources for the Service Order"
    ),
    ALL_NEW_RVR (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchRoutedNewlyProviderContacts},
            "All Newly added Routed Vendor Resources for the Service Order"
    ),
    ALL_RVR_REL (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.NONE},
            "All Routed Vendor Resources for Release Service Order"
    ),
    AP          (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.NONE},
            "All Providers"
    ),
    ARP         (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchRoutedNonAcceptedProvider},
            "All Routed Providers other than accepted and rejected"
    ),
    AV          (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchAcceptedVendorContact},
            "Accepted Vendor (aka Provider or Vendor)"
    ),
    AVR         (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchAcceptedVendorContact},
            "Accepted Vendor Resource (aka Technician or Resource)"
    ),
    B           (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchBuyerEmail},
            "Buyer"
    ),
    BA          (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchBuyerAdmin, AddressResolverTaskCode.FetchSOCreatorEmail},
            "Buyer Admin and SO Creator for SO Communication"
    ),
    BC          (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.NONE},
            "Buyer Support Contact. Uses buyer_contact_id from so_hdr table"
    ),
    BC_AVR      (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchBuyerAdmin, AddressResolverTaskCode.FetchSOCreatorEmail, AddressResolverTaskCode.FetchAcceptedVendorContact},
            "Buyer Admin,SO Creator and Accepted Vendor Resource for SO Communication"
    ),
    BA_PA          (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchBuyerAdmin, AddressResolverTaskCode.FetchProviderAdmin},
            "Buyer Admin  & Provider Admin"
    ),
    BP          (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchBuyerEmail, AddressResolverTaskCode.FetchAcceptedVendorContact},
            "Buyer & Provider"
    ),
    B_OR_P      (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.NONE},
            "Buyer or Provider"
    ),
    C_ASSURANT  (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchAssurantFtpDestination},
            "Assurant client"
    ),
    C_ASSURANT_INCIDENT_CANCEL_REQUEST(
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchAssurantEmail},
            "ImageMicro, SL who recieve Assurant Cancellation Request"
    ),
    LP          (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.NONE},
            "Logged in Provider"
    ),
    N           (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.NONE},
            ""
    ),
    PA          (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchProviderAdmin},
            "Provider Admin"
    ),
    SL          (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchServiceLiveEmail},
            "Service Live"
    ),
    SL_SUPPORT  (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.FetchServiceLiveSupportEmail},
            "Service Live Support"
    ),
    X           (
            new AddressResolverTaskCode[]{AddressResolverTaskCode.NONE},
            "Contextual, Ex Notes - Whoever created a note will be the sender and either a buyer or provider will"
    )
    ;


    private AddressResolverTaskCode[] recipientResolverTaskCodes;
    @SuppressWarnings("unused")
	private String description;

    AddressCode(AddressResolverTaskCode[] recipientResolverTaskCodes, String description) {
        this.recipientResolverTaskCodes = recipientResolverTaskCodes;
        this.description = description;
    }

    public AddressResolverTaskCode[] getRecipientResolverTaskCodes() {
        return recipientResolverTaskCodes;
    }
}
