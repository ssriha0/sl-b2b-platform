package com.servicelive.orderfulfillment.domain.type;

public enum ProblemType {
    PrimarySkuNotConfigured("Error getting Main Sku", "Error getting Main Sku: primary sku is not configured."),
    MissingTemplate("CDT01", "Missing Template"),
    TaskTranslationProblem("Error pricing Service Order", "Service Order has SKUs that could not be mapped to Skill Nodes. The order could not be priced."),
    SpecialtySkuMismatch("Error pricing Service Order", "The sku does not belong to the given specialty code."),
    
    MissingStoreNumberForPricing("Error getting store code for Service Order", "The store code could not be found. The order could not be priced."),
    MissingZipForPricing("Error getting service location for Service Order", "The service location could not be found. The order could not be priced."),
    MissingBuyerId("CDE41", "Buyer id required"),
    MissingFirstName("CDW05", "First name is required."),
    MissingLastName("CDW06", "Last name is required."),
    MissingPhone("CDW11", "Atleast One phone required for posting."),
    BusinessNameLengthExceeds100("CDE03", "Business Name should not exceed 100 characters."),
    FirstNameLengthExceeds50("CDE04", "First name should not exceed 50 characters."),
    LastNameLengthExceeds50("CDE05", "Last name should exceed 50 Characters."),
    EmailLengthExceeds255("CDE9", "Email should not exceed 255 characters."),
    EmailNotValid("CDE10", "Email address is not valid."),
    UnknownPhoneType("CDE12", "Select a Valid Phone Type"),
    UnknownPhoneClass("Unknown Phone Class", "Please enter a valid Phone Class"),
    MissingContactPhone("CDE16", "please enter a contact phone no"),
    InvalidPrimaryPhone("CDE11", "Please enter a valid 10 digit Phone number [3 digits - 3 digits - 4 digits]."),
    InvalidAlternatePhone("CDE13", "Please enter a valid 10 digit  alternate Phone number [3 digits - 3 digits - 4 digits]."),
    InvalidFaxNumber("CDE15", "Please enter a valid 10 digit  Fax number [3 digits - 3 digits - 4 digits]."),
    MissingContactLocation("CDE30", "Service contact cannot be empty,atleast zip required"),
    MissingStreetName("CDW04", "Street Name is required."),
    MissingCity("CDW08", "City is required."),
    MissingStateLoc("CDE27", "Please select a state."),
    StreetLengthExceeds100("CDE06", "Street1 should not exceed 100 characters."),
    Street2LengthExceeds30("CDW07", "Street2 should not exceed 30 characters."),
    CityLengthExceeds30("CDE07", "City name cannot exceed 30 characters."),
    InvalidUSZip("CDE08", "Zip code validation failed, a valid zipcode is required"),
    MissingServiceDates("CDW18", "Please check the service date"),
    InvalidStartEndDates("CDE24", "End date should be grater than or equal to start date"),
    StartDatePast("CDE23", "Start date and time should be greater or equal to Current date and time."),
    MissingTaskName("CDW09", "Task name is empty"),
    MissingTaskCategory("Missing Task Category", "Task category is empty"),
    MissingSkillSelection("CDW16", "Selection of skill is required."),
    MissingSubCatSelection("CDE34", "A sub category must be selected."),
    EmptyTaskList("CDE31", "At least one task is required."),
    TaskCommentsExceeds5000("CDE32", "Task comment should not exceed 5000 characters"),
    //Sl-20728
    TaskCommentsExceeds10000("CDE32", "Task comment should not exceed 10000 rich text format characters"),
    TaskNameExceeds255("CDW19", "Task name cannot exceed 255 characters."),
    MissingPartManufacturer("PRT01", "manufacturer not set in part"),
    MissingPartModelNumber("PRT02", "model not set in part"),
    MissingPartReferenceId("PTE01", "Part Reference ID is required"),
    NoRetailPrice("CDE40", "Retail Price cannot be less than $1.00"),
    MissingOrderTitle("CDE20", "Please enter a Title for this service order (**Required**)."),
    OrderTitleLengthExceeds255("CDE35", "Title cannot exceed 255 character"),
    ProviderInstructionsLengthExceeds5000("CDE21", "Special instruction should not exceed 5000 characters."),
    //Sl-20728
    ProviderInstructionsLengthExceeds10000("CDE21", "Special instruction should not exceed 10000 rich text format characters."),
    DeliveryTaskNotSet("Delivery task not set", "Could not set delivery task. Check service template type for primary task node."),
    CannotSpecifyTaskServiceType("Cannot determine Service Type", "Could not find the service type for the task."),
    CannotFindTimeZone("Cannot find timezone", "Cannot find timezone because either zipcode is not present or invalid.");

    private String code;
    private String msg;

    ProblemType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
