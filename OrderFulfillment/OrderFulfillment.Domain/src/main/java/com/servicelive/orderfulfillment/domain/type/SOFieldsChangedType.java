package com.servicelive.orderfulfillment.domain.type;

public enum SOFieldsChangedType {

	PRIMARY_SKILL_CATEGORY("Primary skill category"),
	SERVICE_LOCATION("Service location"),
	SERVICE_LOCATION_ZIP_CHANGED("Service location zip code"),
	SERVICE_CONTACT("Service contact"),
	SCHEDULE("Schedule"),
	SCHEDULE_SLOTS("ScheduleSlots"),
	TASKS_ADDED("Tasks added"),
	TASKS_DELETED("Tasks removed"),
	ADDON_ADDED("Add-on added"),
	PARTS("Parts"),
	CUSTOM_REF("Custom reference"),
	PROVIDER_INSTRUCTIONS("Provider instruction"),
	SO_DESCRIPTION("Description"),
	SO_TITLE("Title"),
	SO_UNIQUE_ID("SO Unique ID"),
	SPEND_LIMIT_LABOR_CHANGED("Spend limit labor"),
	SPEND_LIMIT_PARTS_CHANGED("Spend limit part"), 
	BUYER_TERMS_COND("Buyer's Term and Conditions"),
	ROUTED_RESOURCES("Routed resources"),
	BUYER_ASSOCIATE_CONTACT("Buyer associate contact"),
	BUYER_ASSOCIATE_LOCATION("Buyer associate location"),
	BUYER_SUPPORT_CONTACT("Buyer support contact"),
	BUYER_SUPPORT_LOCATION("Buyer support location"), 
	END_USER_CONTACT("End user contact"),
	VENDOR_RESOURCE_CONTACT("Vendor resources contact"),
	VENDOR_RESOURCE_LOCATION("Vendor resources location"),
	PROVIDER_SERVICE_CONFIRM_IND("Provider service confirm indicator"),
	SHARE_CONTACT_IND("Share contact indicator"),
	PRICE_MODEL("Price model"),
	SEALED_BID_ORDER_IND("Sealed bid order indicator"),
	SEQUENCE_NUMBER_CHANGED("SequenceNumber Changed"),
	ISREPOST("isRepost"),
	TIER_ROUTED_RESOURCES("Tier Routed resources"),
	TIER_ROUTE_IND("Tier Route Indicator"),
	NON_FUNDED_IND("Non Funded Indicator"),
	PERF_SCORE("Performance Score"),
	TIER_CHANGE("Tier Change"),
	SCOPE_CHANGE("Scope Change"),
	WF_SUBSTATUS("Service Order Substatus");
    private String description;

    private SOFieldsChangedType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
