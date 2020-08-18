package com.newco.marketplace.api.mobile.common;

import java.util.ArrayList;

import com.newco.marketplace.api.utils.utility.CommonUtility;

public enum ResultsCode {		
		SUCCESS("0000", "0000.success"),
		FAILURE("0006", "0006.failure"),
		INVALIDXML_ERROR_CODE("0001", "0001.validation.error"),
		AUTHENTICATION_ERROR_CODE("0002", "0002.validation.error"),
		AUTHORIZATION_ERROR_CODE("0003", "0003.validation.error"),
		INTERNAL_SERVER_ERROR("0004", "0004.server.error"),
		INVALID_SO_ID("0005", "0005.invalid.so"),
		INVALID_RESOURCE_ID("0006", "0006.invalid_provider"),
		WARNING("0009", "0009.warning"),
		INVALID_SO_PROVIDER_ASSOCIATION("0007", "0007.invalid.association"),
		INVALID_OR_MISSING_REQUIRED_PARAMS("0007", "0007.validation.error"),
		NOT_AUTHORISED_BUYER_RESOURCEID("0007", "0709.serviceorder.general.error"),
		NOT_AUTHORISED_BUYER_ID("0007", "0711.serviceorder.general.error"),
		NOT_AUTHORISED_PROVIDER_RESOURCEID("0007", "0710.serviceorder.general.error"),
		NOT_AUTHORISED_PROVIDER("0007", "0713.serviceorder.general.error"),
		INVALID_OPTIONAL_PARAMS("0008", "0008.validation.error"),
		INVALID_OR_MISSING_PARAM("0007","0018.invalid.missing.error"),
		PROFILE_IMAGE_API_ERROR("0007","0007.provider.profile.image.api.error"),
		PROFILE_IMAGE_UPLOAD_API_ERROR("0008","0008.provider.profile.image.upload.api.error"),
		IMVALID_MOBILE_NO("0009","0009.provider.profile.mobileNo.invalid"),
		
	//Generic Validation errors for mobile API
		INVALID_SO("0012","0012.invalidsoid.error"),
		INVALID_PROVIDER("0013","0013.invalidprovider.error"),
		PROVIDER_SO_MISMATCH("0014","0014.sonotasscosiatedtoprovider.error"),
	
	// for Mobile Time on Site API
		TIMEONSITE_INVALID_SO("0401", "0401.mobile.timeonsite.invalid.so"),
		TIMEONSITE_INVALID_SO_STATUS("0402", "0402.mobile.timeonsite.invalid.sostatus"),
		TIMEONSITE_INVALID_PROVIDERID("0403", "0403.mobile.timeonsite.invalid.providerid"),
		TIMEONSITE_NO_ARRIVALTIME("0404", "0404.mobile.timeonsite.no.arrivaltime"),
		TIMEONSITE_INVALID_DEPARTURETIME("0405", "0405.mobile.timeonsite.invalid.departuretime"),
		TIMEONSITE_INVALID_ARRIVALTIME("0406", "0406.mobile.timeonsite.invalid.arrivaltime"),
		TIMEONSITE_INVALID_EVENTTYPE("0407", "0407.mobile.timeonsite.invalid.event"),
		TIMEONSITE_INVALID_LATITUDE("0408", "0408.mobile.timeonsite.invalid.latitude"),
		TIMEONSITE_INVALID_LONGITUDE("0409", "0409.mobile.timeonsite.invalid.longitude"),
		
	// For mobile Time on site API v2.0	
		TIMEONSITE_INVALID_TRIP("0410", "0410.mobile.timeonsite.invalid.tripNo"),
		TIMEONSITE_INVALID_REASONCODE("0411", "0411.mobile.timeonsite.invalid.reasonCode"),
		TIMEONSITE_INVALID_CHECKIN_DATE("0412","0412.mobile.timeonsite.invalid.checkInDate"),
		TIMEONSITE_INVALID_CHECKOUT_DATE("0413","0413.mobile.timeonsite.invalid.checkOutDate"),
		
	// For mobile revisit needed API v2.0 (ERROR CODE STARTING FROM 0450)
		REVISIT_NEEDED_TRIP("0450", "0450.mobile.sorevisitneeded.invalid.tripNo"),
		REVISIT_NEEDED_APPT_DATE_TIME("0451", "0451.mobile.sorevisitneeded.invalid.nextApptDateOrTime"),
		REVISIT_NEEDED_APPT_DATE_TIME_PAST("0452", "0452.mobile.sorevisitneeded.incorrect.nextApptDateOrTime"),
		REVISIT_NEEDED_TIME_WINDOW("0453", "0453.mobile.sorevisitneeded.invalid.timeWindow"),

	//For Login Provider API
		BLANK_USERNAME_PROVIDER("0100","0100.login.provider.blank.username"),
		BLANK_PASSWORD_PROVIDER("0101","0101.login.provider.blank.password"),
		INVALID_USERNAME_PASSWORD_PROVIDER("0102","0102.login.provider.invalid.username.password"),
		LOGIN_ATTEMPTS_EXCEEDED_PROVIDER("0104","0104.login.provider.login.attempts.exceeded"),
		NOT_PROVIDER("0103","0103.login.provider.notaprovider"),
        BLANK_USERNAME_PASSWORD_PROVIDER("0105","0105.login.provider.blank.password.username"),
        UNABLE_TO_PROCESS_REQUEST("0106","0106.login.provider.unable.process.request"),
        NO_PERMISSION_TO_LOGIN("0108","0108.login.provider.unable.login.nopermission"),
        //For UploadDocument Validation
        INVALID_SERVICE_ORDER_STATUS("0800","0800.provider.upload.document.invalidStatus"),
        INVALID_SERVICE_ORDER_PROVIDER_ASSOC("0801","0801.provider.upload.document.invalidServiceOrderProviderAssoc"),
        INVALID_FILE_FORMAT("0803","0803.provider.upload.document.invalidFileFormat"),
        INVALID_FILE_SIZE("0804","0804.provider.upload.document.invalidFileSize"),
        INVALID_PROVIDER_ID("0805","0805.provider.upload.document.invalidProviderId"),
        INVALID_SERVICE_ORDER("0806","0806.provider.upload.document.invalidServiceOrder"),
        DOCUMENT_UPLOAD_SUCCESS("0000","0807.provider.upload.document.success"),
        INVALID_FILE("0808","0808.provider.upload.document.invalidFile"),
        DOC_EXISTS("0809","0809.provider.document.exists"),
        // CC-1113 Changes
        DOC_UPLOAD_ERROR("0810","0810.provider.document.upload.error"),
        DOC_PROCESSING_ERROR("0811","0811.provider.document.upload.processing.error"),
        DOC_USER_AUTH_ERROR("0812","0812.provider.document.upload.auth.error"),
        SO_DOC_SIZE_EXCEEDED("0813","0813.provider.document.size.exceeded.error"),
        
        //For Update Time Window
        
    	UPDATE_APPT_TIME_SUCCESS("0000","0000.update.time.success"),	
    	UPDATE_APPT_TIME_FAILURE("0600","0600.update.time.failure"),
    	TIME_WINDOW_MISMATCH("0604","0604.timewindow.mismatch"),
    	//INVALID_ETA("0605","0605.invalid.eta"),
    	INVALID_STATE("0605","0605.invalid.state"),
    	INVALID_TIME("0606","0606.invalid.time"),
    	INVALID_CUST_CONFIRMED("0607","0607.invalid.customer.confirmed"),
    	ETA_NOT_REQ("0608","0608.eta.not.req"),
    	INVALID_CUST_NA_REASON_CODE("0609","0609.invalid.customer.nareasocode"),
    	TOKEN_EXPIRED("1001","1001.token.expired"),
    	
    	//app version validation resultcodes
    	INVALID_APP_VERSION("1001","1001.mobile.app.version.validation"),
    	NOT_LATEST_APP_VERSION("1002","1002.mobile.app.version.validation"),
    	LATEST_APP_VERSION("1003","1003.mobile.app.version.validation"),
    	
    	//app version validation resultcodes
    	INVALID_APP_VERSION_FOR_UPDATE("1001","1001.update.mobile.app.version.validation"),
    	INVALID_APP_OS_FOR_UPDATE("1002","1002.update.mobile.app.os.validation"),
    	APP_VERSION_UPDATE_SUCCESS("1003","1003.update.mobile.app.version.success"),
    	APP_VERSION_UPDATE_FAILURE("1004","1004.update.mobile.app.version.failure"),
    	
    	//For AddNotes API
        INVALID_PRO("0703","0703.addNote.provider.invalidprovider"),
        INVALID_SOID("0702","0702.addNote.provider.invalidsoid"),
        SO_NOT_ASSOCIATED("0701","0701.addNote.provider.sonotasscosiatedtoprovider"),
        ADD_NOTE_FAILED("0700","0700.addNote.provider.addnotefailed"),
        ADD_NOTE_SUCCESS("0000","0000.addNote.provider.addnotesuccess"),
        ADD_NOTE_CC_SUBJECT("0704","0704.addNote.provider.creditcardpresentsubject"),
        ADD_NOTE_CC_MESSAGE("0705","0705.addNote.provider.creditcardpresentmessage"),
        
       //For Create New Password API
        
        CREATE_NEW_PASSWORD_FAILED("0880","0880.create.new.passwordfailed"),
        INVALID_USERID("0881","0881.createNewPassword.invalidUserId"),
        INVALID_PASSWORD("0882","0882.createNewPassword.invalidPassword"),
        INVALID_CONFIRM_PASSWORD("0883","0883.createNewPassword.invalidConfirmPassword"),
        INVALID_SECURITY_QUESTION("0884","0884.createNewPassword.invalidSecurityQuestion"),
        INVALID_SECURITY_ANSWER("0885","0885.createNewPassword.invalidSecurityAnswer"),
        INVALID_PASSWORD_AND_CONFIRMPASSWORD("0886","0886.createNewPassword.invalid.password.confirmpassword"),
        INVALID_PASSWORD_CANNOT_BESAME("0887","0887.createNewPassword.password.cannotBeSame"),
        INVALID_CURRENT_PASSWORD("0888","0888.createNewPassword.invalidCurrentPassword"),
        INVALID_PASSWORD_LENGTH("0889","0889.createNewPassword.invalidPasswordLength"),
        INVALID_PASSWORD_CRITERIA("0890","0890.createNewPassword.invalidPasswordCriteria"),
        CREATE_NEW_PASSWORD_SUCCESS("0000","0000.create.new.passwordsuccess"),

       
      
    	//Provider SO Search
    	PROVIDER_SO_SEARCH_NO_MATCH("0200", "0200.providersosearch.nomatch"),
    	PROVIDER_SO_SEARCH_INVALID_PROVIDER("0201", "0201.providersosearch.invalidprovider"),
    	PROVIDER_SO_SEARCH_INVALID_STATUS("0202", "0202.providersosearch.invalidstatus"),
    	PROVIDER_SO_SEARCH_INVALID_PAGE_NUMBER("0203", "0203.providersosearch.invalidpageno"),
    	PROVIDER_SO_SEARCH_INVALID_PAGE_SIZE("0204", "0204.providersosearch.invalidpagesize"),
    	PROVIDER_SO_SEARCH_FAILURE("0205", "0205.providersosearch.failure"),   	
		//get recieved orders
		RECIEVED_ORDERS_NO_SO("0200","0200.recieved.orders.no.so"),
		RECIEVED_ORDERS_PAGENO_NOT_EXIST("0201","0201.recieved.orders.page.not.exist"),
    	//For UPDATE SO COMPLETION
    	PROVIDER_INCOMPLETE_W9_REGISTRATION("0500", "0500.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_ERROR1("0501", "0501.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_ERROR2("0502", "0502.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_ERROR3("0503", "0503.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_ERROR4("0504", "0504.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_ERROR5("0505", "0505.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_CC_ERRORS("0505","0505.ccvalidation.error"),    	
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR1("0506", "0506.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR2("0507", "0507.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR3("0508", "0508.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR4("0509", "0509.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR5("0510", "0510.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR6("0511", "0511.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR7("0512", "0512.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR8("0513", "0513.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR9("0514", "0514.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR10("0515", "0515.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR11("0516", "0516.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR12("0517", "0517.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR13("0518", "0518.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR14("0519", "0519.validation.error"),
    	UPDATE_SO_COMPLETION_PERMITS_ERROR1("0520", "0520.validation.error"),
    	UPDATE_SO_COMPLETION_PERMITS_ERROR2("0521", "0521.validation.error"),
    	UPDATE_SO_COMPLETION_PERMITS_ADDON_TOTAL_INVALID("0522", "0522.validation.error"),
    	UPDATE_SO_COMPLETION_PERMITS_ADDON_CC_ERRORS("0505","0505.ccvalidation.error"),
    	UPDATE_SO_COMPLETION_RES_COMMENTS_INVALID("0523", "0523.validation.error"),
    	UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR1("0524", "0524.validation.error"),
    	UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR2("0525", "0525.validation.error"),
    	UPDATE_SO_GENERAL_INFO_ERROR1("0526", "0526.validation.error"),
    	UPDATE_SO_GENERAL_INFO_ERROR2("0527", "0527.validation.error"),
    	UPDATE_SO_GENERAL_INFO_ERROR3("0528", "0528.validation.error"),
    	UPDATE_SO_GENERAL_INFO_ERROR4("0529", "0529.validation.error"),
    	UPDATE_SO_GENERAL_INFO_ERROR5("0530", "0530.validation.error"),
    	UPDATE_SO_GENERAL_INFO_ERROR6("0531", "0531.validation.error"),
    	UPDATE_SO_GENERAL_INFO_ERROR7("0532", "0532.validation.error"),
    	UPDATE_SO_GENERAL_INFO_ERROR8("0533", "0533.validation.error"),
    	UPDATE_SO_GENERAL_INFO_ERROR9("0534", "0534.validation.error"),
    	UPDATE_SO_GENERAL_INFO_ERROR10("0535", "0535.validation.error"),
    	UPDATE_SO_PARTS_INFO_ERROR1("0536", "0536.validation.error"),
    	UPDATE_SO_PARTS_INFO_ERROR2("0537", "0537.validation.error"),
    	UPDATE_SO_PARTS_INFO_ERROR3("0538", "0538.validation.error"),
    	UPDATE_SO_CANCELLATION_REASON_INVALID("0539", "0539.validation.error"),
    	UPDATE_SO_REVISIT_REASON_INVALID("0540", "0540.validation.error"),
    	AUTOCLOSE_SO_SUCCESS("0541", "0541.processing.success"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR15("0542", "0542.validation.error"),
    	CANCELLED_SO_SUCCESS("0543", "0543.processing.success"),
    	AUTOCLOSE_COMPLETESO_SUCCESS("0544", "0544.processing.success"),
    	COMPLETESO_FAILURE("0545", "0545.processing.failure"),
    	COMPLETESO_SUCCESS("0546", "0546.processing.success"),
    	UPDATE_SO_COMPLETION_NOT_ASSIGNED_SO("0547", "0547.validation.error"),
    	UPDATE_SO_COMPLETION_PARTS_MAX_PRICE_EXCEEDED("0548", "0548.validation.error"),
    	UPDATE_SO_COMPLETION_TASKS_INCOMPLETE("0549", "0549.validation.error"),
    	
    	UPDATE_SO_COMPLETION_ADDON_SUCCESS("0555", "0555.processing.success"),
    	UPDATE_SO_COMPLETION_PERMIT_SUCCESS("0556", "0556.processing.success"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENT_SUCCESS("0557", "0557.processing.success"),
    	UPDATE_SO_COMPLETION_PRICING_SUCCESS("0558", "0558.processing.success"),
    	
    	UPDATE_SO_COMPLETION_ADDON_FAILURE("0559", "0559.updation.error"),
    	UPDATE_SO_COMPLETION_PERMIT_FAILURE("0560", "0560.updation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENT_FAILURE("0561", "0561.updation.error"),
    	UPDATE_SO_COMPLETION_PRICING_FAILURE("0562", "0562.updation.error"),
    	UPDATE_SO_COMPLETION_INCORRECT_REQUEST("0563", "0563.updation.error"),
    	UPDATE_SO_COMPLETION_NOT_ACTIVE_ORDER("0564", "0564.updation.error"),
    	UPDATE_SO_COMPLETION_CANCELLED("0565", "0565.updation.success"),
    	UPDATE_SO_COMPLETION_PENDING_CLAIM("0566", "0566.updation.success"),
    	UPDATE_SO_COMPLETION_PARTIAL("0567", "0567.updation.success"),
    	UPDATE_SO_COMPLETION_DATA_UPDATE_SUCCESS("0568", "0568.updation.success"),
    	UPDATE_SO_COMPLETION_DATA_UPDATE_FAILURE("0569", "0569.updation.failure"),
    	UPDATE_SO_COMPLETION_FAILURE("0570", "0570.updation.failure"),
    	UPDATE_SO_COMPLETION_PRICING_LABOR_FAILURE("0571", "0571.updation.failure"),
    	UPDATE_SO_COMPLETION_PRICING_PARTS_FAILURE("0572", "0572.updation.failure"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENTS_ERROR16("0573", "0573.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENT_VALIDATION_ERROR("0574", "0574.addonpayment.valiation.error"),
    	UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR1("0575", "0575.document.valiation.error"),
    	UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR2("0576", "0576.document.valiation.error"),
    	UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR3("0577", "0577.document.valiation.error"),
    	UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR4("0578", "0578.document.valiation.error"),
    	UPDATE_SO_COMPLETION_DOCUMENT_VALIDATION_ERROR5("0585", "0585.document.valiation.error"),
    	UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR3("0579", "0579.referece.valiation.error"),
    	UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR4("0580", "0580.referece.valiation.error"),
    	UPDATE_SO_COMPLETION_TASKS_ERROR1("0581", "0581.tasks.valiation.error"),
    	UPDATE_SO_COMPLETION_CUSTOM_REFERENCES_ERROR5("0582", "0582.referece.valiation.error"),
    	UPDATE_SO_COMPLETION_PERMITS_ERROR3("0583", "0583.validation.error"),
    	UPDATE_SO_COMPLETION_INVALID_CARRIER("0584", "0584.validation.error"),
    	UPDATE_SO_COMPLETION_ADDON_PAYMENT_INVALID_TOKENIZE_RESPONSE("0586", "0586.addonpayment.valiation.error"),

      //Get So Details Mobile
    	RETRIEVE_RESULT_CODE_SUCCESS("0000","0000.serviceorderdetails.get.result"),
    	NOT_AUTHORISED_PROVIDER_RESOURCEID_FOUND("0300", "0300.serviceorderdetails.general.error"),
	    SERVICE_ORDER_NOT_EXIST("0301","0301.serviceorderdetails.get.error"),
	    SO_SEARCH_INVALID_PROVIDER("0302", "0302.serviceorderdetails.invalidprovider.error"),
	    SO_INVALID_FILTER("0303", "0303.serviceorderdetails.invalidfilter.error"),
	  //Download Document Mobile
	    DOWNLOAD_DOC_SO_PROVIDER_NOT_ASSOCIATED("0900","0900.document.download.serviceorder.provider.notassociated.error"),
	    DOWNLOAD_DOC_SO_DOC_NOT_ASSOCIATED("0901","0901.document.download.serviceorder.document.notassociated.error"),
        DOWNLOAD_DOC_INVALID_SO("0902","0902.document.download.invalidserviceorder.error"),
	    DOWNLOAD_DOC_INVALID_PROVIDER("0903","0903.document.download.invalidprovider.error"),
	    DOWNLOAD_DOC_INVALID_DOCUMENT("0904","0904.document.download.invaliddocument.error"),
		DOWNLOAD_DOC_NOT_AUTHORIZED_TO_VIEW("0905","0905.document.download.notauthorizedtoview.error"),
		//AddSoProviderpart 
		INVALID_SO_STATUS("0303","0906.addsoproviderpart.invalid.so.status"),
		INVALID_BUYER("0907","0907.addsoproviderpart.invalid.buyer"),
		PART_ADDED_SUCCESSFULLY("0908","0908.addsoproviderpart.part.added.success"),
		PART_NOT_ASSOCIATED_SO("0908","0908.addsoproviderpart.part.not.associated"),
		INVALID_TRIP_NUMBER("0302","0909.addsoproviderpart.invalid.trip.number"),
		PART_NOT_ASSOCIATED_SO_OR_DELETED("0304","0910.addsoproviderpart.part.not.associated.or.deleted"),
		INVALID_DOCUMENT("0916","0916.addInvoicepart.invalid.document"),
		INVALID_SO_DOCUMENT("0917","0917.addInvoicepart.invalid.so.document"),
		INVALID_PART_STATUS("0919","0919.addsoproviderpart.invalid.part.status"),
		UPDATE_SO_COMPLETION_PARTS_MAX_RETAIL_PRICE_VALIDATION("0920","0920.addsoproviderpart.retail.price.validation.error"),
	    //AddInvoiceSOProviderPart V2
	    UNABLE_TO_PROCESS_THE_REQUEST("0911","0911.Add.Invoice.unable.process.request"),
	 	UPDATE_SO_INVOICE_PARTS_INFO_ERROR1("0912", "0912.validation.error"),
	 	UPDATE_SO_INVOICE_PARTS_INFO_ERROR2("0913", "0913.validation.error"),
	 	UPDATE_SO_INVOICE_PARTS_INFO_ERROR3("0914", "0914.validation.error"),
		UPDATE_SO_INVOICE_PARTS_INVALID_PART_ID("0915", "0915.validation.error"),
		UPDATE_SO_INVOICE_PARTS_STATUS_ERROR("0918", "0918.status.validation.error"),
		UPDATE_SO_INVOICE_PARTS_COVERAGE_ERROR("0918", "0918.coverage.validation.error"),
		UPDATE_SO_INVOICE_PARTS_SOURCE_ERROR("0918", "0918.source.validation.error"),
		UPDATE_SO_INVOICE_PARTS_PART_NUMBER_ERROR("0918", "0918.partNumber.validation.error"),
		UPDATE_SO_INVOICE_PARTS_PART_NAME_ERROR("0918", "0918.partName.validation.error"),
		UPDATE_SO_INVOICE_PARTS_INVOICE_NUMBER_ERROR("0918", "0918.invoiceNumber.validation.error"),
		UPDATE_SO_INVOICE_PARTS_UNIT_COST_ERROR("0918", "0918.unitCost.validation.error"),
		UPDATE_SO_INVOICE_PARTS_RETAIL_PRICE_ERROR("0918", "0918.retailPrice.validation.error"),
		UPDATE_SO_INVOICE_PARTS_QTY_ERROR("0918", "0918.qty.validation.error"),
		UPDATE_SO_INVOICE_PARTS_DIV_NO_ERROR("0918", "0918.divNo.validation.error"),
		UPDATE_SO_INVOICE_PARTS_SOURCE_NO_ERROR("0918", "0918.sourceNo.validation.error"),
		//For Login Provider API V2.0
		INVLID_APP_VERSION("0107","0107.login.provider.invalid.appversion"),
		// Success feedback logging
		SUCCESSFULL_APP_FEEDBACK("2001","2001.mobile.feedback.success"),
		//R14.0 mobile API phase two
		SEARCH_VALIDATION_MAXRESULTSET_ERROR_CODE("3001","3001.serviceorder.search.result.set.validation.error"),
		SUCCES_OF_CALL("00","3006.success"),
		//R14.0 Reject SO API
		REJECT_SO_FAILED("2401","2401.serviceorder.reject.failure"),
		REJECT_INVALID_REASON_CODE("2403","2403.serviceorder.reason.code.error"),
		REJECT_RESOURCE_ID_REQUIRED("2404","2404.serviceorder.resource.id.warning"),
		REJECT_SUCCESS("2400","2400.serviceorder.reject.success"),
		REJECT_RESOURCE_ID_INVALID("2402","2402.serviceorder.provider.error"),
		REJECT_COMMENT_REQUIRED("2405","2405.serviceorder.reject.comment.error"),
		//Assign API
		ASSIGN_SO_SUCCESS("3001","3001.serviceorder.assign.provider.success"),
		ASSIGN_SO_STATE_ERROR("3002","3002.assign.serviceorder.provider.error"),
		ASSIGN_SO_PROVIDER_ERROR("3003","3003.assign.serviceorder.provider.error"),
		ASSIGN_SO_ERROR_ALREADY_ASSIGNED("3004","3004.assign.serviceorder.provider.error"),
		ASSIGN_SO_PROVIDER_NOTMATCH("3005","3005.assign.serviceorder.provider.error"),
	    RESOURCE_NOT_UNDER_FIRM("3007","3007.assign.serviceorder.provider.error"),
		// For mobile so accept API v2.0 (ERROR CODE STARTING FROM 0450)
		SO_ACCEPT_SUCCESS("1050", "1050.mobile.soaccept.success"),
		SO_ACCEPT_INVALID_PROVIDER_ID("1051", "1051.mobile.soaccept.invalid.providerId"),
		SO_ACCEPT_PROVIDER_ID("1052", "1052.mobile.soaccept.providerId"),
		SO_ACCEPT_IN_EDIT_MODE("1053", "1053.mobile.soaccept.editMode"),
		SO_ACCEPT_NOT_IN_ACCEPTABLE_STATE("1054", "1054.mobile.soaccept.notAcceptableState"),
		//Create Counter Offer
		SO_CONDITIONAL_OFFER_INVALID_START_DATE("3006","1910.serviceorder.create.offer.error"),
		SO_CONDITIONAL_OFFER_INVALID_END_DATE("3007","1911.serviceorder.create.offer.error"),
		SO_CONDITIONAL_OFFER_INVALID_OFFER_EXPIRE_DATE("3008","1912.serviceorder.create.offer.error"),
		SO_CONDITIONAL_OFFER_INVALID_SPEND_LIMIT("3009","1913.serviceorder.create.offer.error"),
		SO_CONDITIONAL_START_DATE_OR_SPEND_LIMIT_REQUIRED("3010","1902.serviceorder.create.offer.error"),
		SO_CONDITIONAL_OFFER_NEGATIVE_SPEND_LIMIT("3011","1903.serviceorder.create.offer.error"),
		SO_CONDITIONAL_EXPIRATION_DATE_REQUIRED("3012","1904.serviceorder.create.offer.error"),
		SO_CONDITIONAL_OFFER_STATUS_NOTMATCH("3013","1905.serviceorder.create.offer.error"),
		SO_CONDITIONAL_OFFER_PROVIDER_NOTMATCH("3014","1906.serviceorder.create.offer.error"),
		SO_CONDITIONAL_OFFER_EXISTS("3015","1914.serviceorder.create.offer.error"),
		SO_CONDITIONAL_OFFER_PROVIDE_START_DATE("3016","1907.serviceorder.create.offer.error"),
		SO_CONDITIONAL_CAR_SO("3017","1908.serviceorder.create.offer.error"),
		SO_CONDITIONAL_COUNTER_OFFER_NOT_ALLOWED_FOR_NON_FUNDED("3018","1909.serviceorder.create.offer.error"),
		COUNTER_OFFER_SUBMITTED("0000","1001.serviceorder.offer.result"),
		COUNTER_OFFER_ERROR("3019","1002.serviceorder.offer.error"),
		SO_CONDITIONAL_SPEND_LIMIT_VALUE_SMALL("3040","3040.serviceorder.create.offer.error"),
		SO_CONDITIONAL_OFFER_START_DATE_PAST("3041","3041.serviceorder.create.offer.error"),
		SO_CONDITIONAL_OFFER_END_DATE_PAST("3042","3042.serviceorder.create.offer.error"),
		SO_CONDITIONAL_OFFER_END_DATE_PAST_THAN_START_DATE("3043","3043.serviceorder.create.offer.error"),
		SO_CONDITIONAL_OFFER_INVALID_ROLE("3044","3044.serviceorder.counteroffer.invalidRole.error"),
		//Withdraw counter offer
		SO_WITHDRAW_OFFER_STATUS_NOTMATCH("3020","1914.serviceorder.withdraw.offer.error"),
		SO_WITHDRAW_NO_COUNTER_OFFER("3021","1915.serviceorder.withdraw.offer.error"),
		SO_WITHDRAW_OFFER_PROVIDER_NOTMATCH("3022","1916.serviceorder.withdraw.offer.error"),
		COUNTER_OFFER_WITHDRAWN("0000","1001.serviceorder.withdraw.offer.result"),
		COUNTER_OFFER_WITHDRAWN_ERROR("3023","1002.serviceorder.withdraw.offer.error"),
		//Release
		SO_RELEASE_SUCCESS("2050", "2050.mobile.sorelease.success"),
		SO_RELEASE_FAILED("2051","2051.mobile.sorelease.failure"),
		SO_RELEASE_INVALID_RELEASE_CODE("2052","2052.mobile.sorelease.invalid.reason"),
		// get SORouteList API
		NO_PROVIDERS_AVAILABLE("3001","3001.no.routed.providers"),
		//Reassign Mobile API
		SO_REASSIGN_SUCCESS("3050","3050.reassign.serviceorder.provider.success"),
		//SO_REASSIGN_COMMENTS_EMPTY("3051","3051.reassign.serviceorder.provider.error"),
		SO_REASSIGN_WRONG_RESOURCE("3052","3052.reassign.serviceorder.provider.error"),
		SO_RELEASE_ERROR_PROBLEMSTATUS("3053","so.release.problemstatus.error"),
		SO_ALREADY_ASSIGN_ERROR("3054","3054.reassign.serviceorder.provider.error"),
		//Forget Username Password
		FORGET_UNAME_PWD_EMAIL_BLANK_INVALID_EMAIL("3101","3101.forget.uname.password.email.required.error"),
		FORGET_UNAME_PWD_USERNAME_BLANK("3102","3102.forget.uname.password.username.required.error"),
		FORGET_UNAME_PWD_NO_USER_EXISTS("3103","3103.forget.uname.password.emailLogin.error"),
		FORGET_UNAME_PWD_LOCKED_USER("3104","3104.forget.uname.password.user.account.locked"),
		FORGET_UNAME_PWD_NO_SEC_QN("3105","3105.forget.uname.password.no.security.question"),
		FORGET_UNAME_PWD_USERID_BLANK("3106","3106.forget.uname.userId.no.exists.error"),
		INVALID_SECRET_QUESTION("3107","3107.invalid.secret.question"),
		EMPTY_SECRET_QUESTION_ANSWER("3108","3108.invalid.secret.question.answer"),
		RESET_PASSWORD_EMAIL_SEND_ERROR("3109","3109.reset.password.email.send.error"),
		FORGET_UNAME_EMAIL_SEND_ERROR("3109","3109.reset.uname.email.send.error"),
		COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP("3110","3110.count.exceed.max.verification.count"),
		INVALID_SECRET_QUESTION_ANSWER("3111","3111.invalid.secret.question.answer"),
		FORGET_UNAME_PWD_PHONE_NUMBER_BLANK_INVALID("3112","3112.forget.uname.password.phoneno.required.error"),
		FORGET_UNAME_PWD_ZIP_CODE_BLANK_INVALID("3113","3113.forget.uname.password.zip.required.error"),
		FORGET_UNAME_PWD_COMPANY_NAME_BLANK_INVALID("3114","3114.forget.uname.password.companyname.required.error"),
		RESET_PASSWORD_EMAIL_SEND_SUCCESS("3115","3115.reset.password.email.send.success"),
		RESET_USERNAME_EMAIL_SEND_SUCCESS("3116","3116.reset.username.email.send.success"),
		FORGET_UNAME_PWD_USERID_REQUIRED("3117","3117.forget.uname.password.userid.required.error"),
		FORGET_UNAME_PWD_RESET_EMAIL_ERROR("3119","3119.forget.uname.password.reset.password.email.error"),
		FORGET_UNAME_PWD_SECURITY_QN("3120","3120.forget.uname.password.security.question.success"),
		FORGET_UNAME_PWD_MULTIPLE_USERS("3121","3121.forget.uname.password.multiple.users.success"),
		
		// Mobile bid request
		SO_BID_SUCCESS("1075", "1075.mobile.sobid.success"),
		SO_BID_LABOR_PRICE_REQUIRED("1076", "1076.mobile.sobid.laborPrice.reqd"),
		SO_BID_FIXED_INVALID_PARAMS("0007", "0710.serviceorder.general.error"),
		SO_BID_INVALID_EXPIRATION_DATE("1078", "1078.mobile.sobid.invalid.expirationDate"),
		SO_BID_INVALID_LABOR_PRICE("1079", "1079.mobile.sobid.invalid.laborPrice"),
		SO_BID_PROVIDER_NOT_ROUTED("1080", "1080.mobile.sobid.notRoutedToProvider"),
		SO_BID_HOURLY_RATE_REQUIRED("1081", "1081.mobile.sobid.hourlyRate.reqd"),
		SO_BID_ACTIVITY_LOG_FAILED("1082", "1082.mobile.sobid.activityLogFailed"),
		SO_BID_INVALID_HOURLY_RATE("4015", "4015.mobile.sobid.invalid.hourlyRate"),
		SO_BID_INVALID_LABOR_HOURS("4016", "4016.mobile.sobid.invalid.laborHours"),
		SO_BID_HOURLY_RATE_INVALID_PARAMS("0007", "0710.serviceorder.general.error"),
		SO_BID_NEGATIVE_PARTS_PRICE("4017", "4017.mobile.sobid.negativePartsPrice"),
		BID_EXPIRATION_PAST_ERROR("4018", "4018.mobile.sobid.expirationDate.past"),
		SO_BID_RANGE_DATES_REQUIRED("4019", "4019.mobile.sobid.range.dates.reqd"),
		SO_BID_RANGE_INVALID_PARAMS("0007", "0710.serviceorder.general.error"),
		START_DATE_PAST_ERROR("4020", "0111.serviceorder.create.error"),
		INVALID_START_DATE_GREATER_END_DATE("4021", "0112.serviceorder.create.error"),
		SO_BID_SPECIFIC_DATES_REQUIRED("1077", "1077.mobile.sobid.specific.reqd"),
		SO_BID_SPECIFIC_INVALID_PARAMS("0007", "0710.serviceorder.general.error"),
		INVALID_START_TIME_GREATER_END_TIME("3016", "0507.serviceorder.post.error"),
		SO_BID_INVALID_RESOURCE_ID("4022", "4022.mobile.sobid.invalid.bidResourceId"),
		
		RESPOND_RESCHEDULE_NOT_ALLOWED("1083","1083.mobile.respond.to.reschedule"),
		RESPOND_RESCHEDULE_SUCCESS("1084","1084.mobile.respond.to.reschedule.success"),
		// Update Service Order Flag
		UPDATE_SO_FLAG("1083","1083.update.serviceorder.flag.success"),
		// Submit Reschedule API messages
		RESCHEDULE_WRONG_START_DATE("3055","3055.submit.reschedule.error"),
		RESCHEDULE_WRONG_END_DATE("3055","3055.submit.reschedule.error"),
		RESCHEDULE_REQUEST_ACCEPTED("3057","3057.submit.reschedule.success"),
		RESCHEDULE_REQUEST_PROCESSED("3058","3058.submit.reschedule.success"),
		RESCHEDULE_REQUEST_REJECTED("3059","3059.submit.reschedule.error"),
		RESCHEDULE_WF_STATE_INVALID("3061","3061.submit.reschedule.invalid.wfstate.error"),
		RESCHEDULE_END_DATE_ABSENT("3062","3062.submit.reschedule.enddate.absent.error"),
		// Pre Call
		INVALID_CUST_UNAVILABLE_REASON("4001","4001.invalid.cust.unavailable.reason.error"),
		SERVICE_ORDER_NOT_READY_FOR_ACTION("4002","4002.invalid.so.for.action"),
		SERVICE_ORDER_NOT_ASSIGNE_BUT_TODAY("4003","4003.invalid.so.assignment"),
		SERVICE_ORDER_NOT_CONFIRMED_BUT_TODAY("4004","4004.invalid.so.schedule.confirm"),
		//Get ReasonCodes API
		INVALID_REASONCODE_TYPE("4005","4005.invalid.reasoncode.type"),
		PERMISSION_ERROR("4010","4010.invalid.provider.permission"),
		// Delete Filter API
		DELETE_FILTER_FILTER_ID_NO_INVALID_FILTERID_ERROR("3050","3050.delete.filter.no.invalid.filterId.error"),
		DELETE_FILTER_NO_FILTER_ERROR("3051","3051.delete.filter.noFilter.error"),
		DELETE_FILTER_SUCCESS("0000","0000.delete.filter.success"), 
		// Search API
		INVALID_PAGE_NUMBER ("3005","3005.invalid.page.no"),
		APPOINTMENT_SEARCH_ERROR ("3006","3006.invalid.appoint.error"),
		APPOINTMENT_SEARCH_VALUE_ERROR ("3007","3007.invalid.appoint.error"),
		//Save filter API
		SAVE_FILTER_SUCCESS("0000","0000.save.filter.success"),
		SAVE_FILTER_NAME_EXISTS_ERROR("3052","3052.save.filter.filterName.exists"),
		SAVE_FILTER_CRITERIA_MANDATORY_ERROR("3053","3053.save.filter.no.filter.criteria"),
		SAVE_FILTER_INVALID_DATE("3054","3054.save.filter.invalid.date"),
		//Get Filter
		GET_FILTER_NO_FILTERS_TO_FETCH("3060","3060.get.filter.no.filter"),
		INVALID_ROLE("4011","4011.invalid.role") ,
		
		//Mobile v3.1 API's
		V3_1_INVALID_SERVICE_ORDER_STATUS("3121","3121.invalid.so.status"),
		v3_1_INVALID_REASON_CODE("3120","3120.invalid.reason.code"),
		V3_1_RESOLVE_PBM_COMMENT_MANDATORY("3122","3122.resolve.pbm.comment.mandatory"),
		V3_1_REPORT_PROBLEM_TRANSISTION_FAILED("3124","3124.reportProblem.transition.failed"),
		V3_1_RESOLVE_PROBLEM_TRANSISTION_FAILED("3125","3125.resolveProblem.transition.failed"),
		V3_1_REPORT_PBM_SUCCESS_MSG("0000","0000.reportProblem.success"),
		V3_1_RESOLVE_PBM_SUCCESS_MSG("0000","0000.resolveProblem.success"),
		//Mobile 3.1 Release API
		RELEASE_COMMENT_MANDATORY("3123","3123.error.comment.mandatory"),
		RELEASE_SO_SUCCESS("0000","0000.mobile.sorelease.success"),
		V3_1_INVALID_RELEASE_REASON("3133","3133.error.invalid.reason"),
		//View Company Profile API
		COMPANY_PROFILE_SUCCESS("0000","0000.mobile.profile.success"),
		
		//Get Team Members API
		NO_MANAGE_TEAM_PERMISSION("3131","3131.mobile.team.members.no.manage.team.permission"),
		NO_REGISTERED_PROVIDERS("3132","3132.mobile.team.members.no.providers"),
		GET_TEAM_MEMBERS_SUCCESS("0000","0000.mobile.team.member.success"),
		
		GET_CALENDAR_EVENT_SUCCESS("0000","0000.mobile.calendar.event.success"),
		GET_CALENDAR_EVENT_ERROR("0001","0001.mobile.calendar.event.delete.error"),
		
		//Update Time Window API
		v3_1_INVALID_START_TIME("3101","3101.invalid.start.time"),
		v3_1_INVALID_END_TIME("3102","3102.invalid.end.time"),
		v3_1_INVALID_ETA("3103","3103.invalid.eta"),
		v3_1_END_TIME_MANDATORY("3104","3104.end.time.mandatory"),
		v3_1_TIME_WINDOW_MISMATCH("3105","3105.time.mismatch"),
		v3_1_TIME_WINDOW_SUCCESS_MSG("0000","0000.updateTimeWindow.success"),
		v3_1_START_TIME_GREATER_THAN_END_TIME("3016", "0507.serviceorder.post.error"),
		
		//Pre Call API
		V3_1_INVALID_SCHEDULE_STATUS("3112","3112.invalid.schedule.status"),
		V3_1_INVALID_SERVICE_ORDER_STATUS_PRE_CALL("3113","3113.invalid.so.status"),
		V3_1_INVALID_SERVICE_ORDER_STATUS_CONFIRM_APPT("3114","3114.invalid.so.status"),
		V3_1_CUSTOMER_NOT_AVAILABLE_REASON_MANDATORY("3111","3111.customer.not.available.reason.code.mandatory"),
		V3_1_APPOINTMENT_DATE_NOT_3_DAY("3115","3115.appointment.date.not.3.day"),
		V3_1_APPOINTMENT_DATE_TODAY_UNASSIGN("3116","3116.appointment.date.today.unassign"),
		V3_1_CUSTOMER_RESPONSE_MANDATORY("3117","3117.customer.response.mandatory"),
		V3_1_PRE_CALL_SUCCESS_MSG("0000","0000.precall.success"),
		V3_1_CONFIRM_APPT_SUCCESS_MSG("0000","0000.confirmAppt.success"),
		
		INVALID_ESTIMATE_ID("0008","4012.invalid.estimation.id.error"),
		INVALID_ESTIMATE_PRICE("0009","4014.invalid.estimation.price.error"),
		ADD_EDIT_ESTIMATE("0000","4013.add.edit_success.message"),
		NO_SKU_AVAILABLE("4021","4021.buyer.skus.not.available.error"),
		NO_ESTIMATE_CHANGE("4015","4015.no.estimation.change.error"),
		INVALID_SO_STATE("4016","4016.invalid.so.state"),
		
		//availableSlotsForTechTalk Service
		GET_AVAILABLE_TIME_SLOTS_DATE_FORMAT_ERROR("3901","3901.invalid.date.format"),
		START_DATE_AFTER_END_DATE("3902","3902.invalid.start.end.date"),

		SKU_NOT_AVAILABLE_FOR_BUYER("3903","3903.sku.not.available.for.buyer");

		
	private String message;
	private String code;
	
	private ResultsCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * Method for getting the internationalized message value for a 
	 * particular property code. This in turn calls CommonUtility.getMessage(message).
	 * 
	 * @return String
	 */
	public String getMessage() {
		if (message == null)
			return null;
		String value = CommonUtility.getMessage(message);
		if (value == null)
			value = message;
		return value;
	}
	
	/**
	 * Method for getting the internationalized message value for a property 
	 * code. This in turn calls CommonUtils.getMessage(String message, ArrayList<Object>).
	 * 
	 * @param replaceArguments
	 * @return String
	 */
	public String getMessage(ArrayList<Object> replaceArguments) {
		return CommonUtility.getMessage(message, replaceArguments);
	}
	
	public String getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		return code;
	}
}
