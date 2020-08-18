package com.newco.marketplace.api.common;

import java.util.ArrayList;

import com.newco.marketplace.api.utils.utility.CommonUtility;

public enum ResultsCode {		
	SUCCESS("0000", "0000.success"),
	INVALIDXML_ERROR_CODE("0001", "0001.validation.error"),
	AUTHENTICATION_ERROR_CODE("0002", "0002.validation.error"),
	AUTHORIZATION_ERROR_CODE("0003", "0003.validation.error"),
	INTERNAL_SERVER_ERROR("0004", "0004.server.error"),
	GENERIC_ERROR("0005", "0005.server.error"),
	BUYER_ID_DOES_NOT_EXIST("0005","3500.get.so.substaus.buyerid.does.not.exist"),
	INVALID_SERVICE_ORDER_STATUS("0007","3501.invalid.so.status.error"),
	FAILURE("0006", "0006.failure"),
	WARNING("0009", "0009.warning"),
	INVALID_OR_MISSING_REQUIRED_PARAMS("0007", "0007.validation.error"),
	NOT_AUTHORISED_BUYER_RESOURCEID("0007", "0709.serviceorder.general.error"),
	NOT_AUTHORISED_BUYER_ID("0007", "0711.serviceorder.general.error"),
	NOT_AUTHORISED_PROVIDER_RESOURCEID("0007", "0710.serviceorder.general.error"),
	NOT_AUTHORISED_PROVIDER_FIRM_ID("0007", "4911.serviceorder.provider.firm.auth.error"),
	INVALID_OPTIONAL_PARAMS("0008", "0008.validation.error"),
	INVALID_OR_MISSING_PARAM("0007","0018.invalid.missing.error"),
	// Provider Search
	PROVIDER_SEARCH_MULTIPLE_MATCH("0010", "0010.providersearch.multiplematch"),	
	PROVIDER_SEARCH_SPELLING_MISTAKE("0011", "0011.providersearch.spellingmatch"),	
	// Provider Search End
	//Provider get 
	PROVIDER_GET_DRAFT_STATE_MESSAGE("0007", "2001.serviceorder.providerGet.error"),	
	//Provider get End
	//Create Buyer Account
	CREATE_BUYER_USERNAME_ALREADY_EXISTS("0012","0012.create.buyer.account.username.already.exists"),
	CREATE_BUYER_INVALID_ZIPCODE_STATE("0013","0013.create.buyer.account.error"),
	//Create Buyer Account End
	
	//Funding Source
	CREATE_FUNDING_SOURCE_FAILED("0013", "0013.create.fundingsource"),
	GET_FUNDING_SOURCE_FAILED_DURING_RETRIVAL("0014", "0014.fundingsource.get.result"),
	INVALID_RESOURCE_ID("0007","0708.serviceorder.general.error"),
	INVALID_BUYER_ID("0007","0712.serviceorder.general.error"),
	DATA_INVALID_IN_DATE_ARGUEMENTS("0904","0904.serviceorder.get.error"),
	TOO_MANY_WALLET_HISTORY_RECORDS("0905","0905.wallet.get.error"),
	WALLET_CREDIT_PROMO_ERROR("0921","0921.wallet.get.error"),
	//CC Validation
	CARDHOLDERNAME_TOO_LONG("0906", "0906.cc.name.long"),
	CARDHOLDERNAME_EMPTY("0907", "0907.cc.name.empty"),
	CARDTYPEID_EMPTY("0908","0908.cc.typeid.empty"),
	CARDNUMBER_EMPTY("0909","0909.cc.number.empty"),
	CARDNUMBER_INVALID_LENGTH("0910","0910.cc.number.long"),
	CARDNUMBER_FORMAT_ERROR("0911","0911.cc.number.formaterror"),
	BILLING_CITY_EMPTY_ERROR("0006","0912.cc.billing.city.empty"),
	BILLING_CITY_LONG_ERROR("0006","0913.cc.billing.city.long"),
	BILLING_STATE_EMPTY_ERROR("0006","0914.cc.billing.state.empty"),
	ZIP_CODE_EMPTY_ERROR("0006","0915.cc.zip.empty"),
	ZIP_CODE_INVALID("0006","0916.cc.zip.invalid"),
	ZIP_STATE_NO_MATCH("0006","0917.cc.zip.state.not.match"),
	CREDIT_CARD_INVALID("0006","0918.cc.card.number.invalid"),
	BILLING_ADDRESS_EMPTY("0006","0919.cc.billing.address.empty"),
	BILLING_ADDRESS_LENGTH("0006","0920.cc.billing.address.long"),
	
	//Search SO
	SEARCH_VALIDATION_ZIP_ERROR_CODE("0016","0303.serviceorder.search.zip.validation.error"),
	SEARCH_VALIDATION_MAXRESULTSET_ERROR_CODE("0017","0304.serviceorder.search.result.set.validation.error"),
	SEARCH_VALIDATION_SOSTATUS_SET_ERROR_CODE("0018","0305.serviceorder.search.result.set.validation.error"),
	SEARCH_VALIDATION_PROVIDER_STATUS_SET_ERROR_CODE("0006","0306.serviceorder.search.result.set.validation.error"),
	SEARCH_NO_RECORDS("0006","0307.serviceorder.search.result"),
	// Search template 
	SEARCH_TEMPLATE_NO_RECORDS("0006","2601.serviceorder.search.template.error"),
	//Close SO
	
	//LOGININCOMPLETE_SL_PROFILE
	
	LOGIN_USER_NOT_VERIFIED("1000","2901.serviceorder.login.user.verified"),
	LOGIN_USER_ACCOUNT_LOCKED("1001","2902.userviceorder.login.user.account"),
	LOGIN_INCORRECT_USER("1002","2903.serviceorder.login.user.invalid"),
	LOGIN_BUYER_NOT_ASSOCIATED("1004","2904.serviceorder.login.user.associated"),
	
	
	SO_NOT_FOUND("0208", null),
	SO_CLOSE_ERROR("0212", null),
	
	//ReleaseSO
	SO_RELEASE_ERROR("0213", "0213.so.release.error"),
	SO_RELEASE_ERROR_PROBLEMSTATUS("0214", "so.release.problemstatus.error"),
	SO_RELEASE_ERROR_ACCEPTEDSTATUS("0215", "so.release.accceptedstatus.error"),
	SO_RELEASE_ERROR_ACTIVESTATUS("0216", "so.release.activestatus.error"),
	RELEASE_SUCCESSFUL("0000","0217.serviceorder.release.success"),
	//CreateSO
	DRAFT_CREATED("0000","0101.serviceorder.create.result"),
	DRAFT_NOT_CREATED("0006","0102.serviceorder.create.error"),
	SEARCH_UNSUCCESSFUL("0006","0302.serviceorder.search.error"),
	//CancelSO
	NOT_AUTHORISED("0007","1005.serviceorder.offer.error"),
	RETRIEVE_RESULT_CODE("0000","0201.serviceorder.get.result"),
	//AcceptSO
	ACCEPT_RESULT_CODE("0000","1501.serviceorder.get.result"),
	//Add Note
	NOTE_ADDED("0000","0802.serviceorder.addnote.result"),
	// Post SO
	SERVICE_ORDER_DOES_NOT_EXIST("0006","0202.serviceorder.get.error"),
	//Edit SO
	EDIT_SUCCESSFUL("0000","1301.serviceorder.edit.result"),
	EDIT_FAILURE("0006","1302.serviceorder.edit.error"),
	EDIT_ROUTE_NO_PROVIDERS("0006","1306.serviceorder.edit.error"),
	EDIT_INVALID_STATE("0006","1313.serviceorder.edit.error"),
	CANNOT_EDIT_SO("0006","1310.serviceorder.edit.error"),
	SERVICEORDER_NOT_FOUND("0006","0202.serviceorder.get.error"),
	//UPDATE SO
	UPDATE_INVALID_STATE("0006","4101.serviceorder.update.error"),
	UPDATE_FAILURE("0006","4106.serviceorder.update.error"),
	UPDATE_SUCCESSFUL("0000","4107.serviceorder.update.result"),
	//Post SO
	POST_INVALID_STATE("0006","0508.serviceorder.post.error"),
	BUYER_UPFUND_LIMIT_PER_TRANSACTION_ERROR("0006","0509.serviceorder.post.error"),
	//Document Upload
	DOCUMENT_MAPPED_FAILED("0006","01003.document.mapping.error"),
	DOCUMENT_CREATED_FAILED("0006","01002.document.upload.error"),
	//Reschedule
	ENDDATE_REQUIRED("0006","0603.serviceorder.reschedule.error"),
	COMMENT_REQUIRED("0006","0612.serviceorder.reschedule.error"),
	START_DATE_NOT_PRIOR("0006","0604.serviceorder.reschedule.error"),
	START_DATE_PAST("0006","0111.serviceorder.create.error"),
	INVALID_STATUS_TO_RESCHEDULE("0006","0605.serviceorder.reschedule.error"),
	RESCHEDULE_REQUEST_SUBMITTED("0000","0601.serviceorder.reschedule.result"),
	CANCEL_RESCHEDULE_REQUEST_SUBMITTED("0000","0606.serviceorder.reschedule.result"),
	// Time On Site
	ARRIVAL_DEPARTURE_TIME_VALIDATION("0006","0607.serviceorder.timeonsite.error"),
	ARRIVAL_CURRENT_TIME_VALIDATION("0006","0608.serviceorder.timeonsite.error"),
	DEPARTURE_CURRENT_TIME_VALIDATION("0006","0609.serviceorder.timeonsite.error"),
	ACCEPTED_DATE_TIME_VALIDATION("0006","0610.serviceorder.timeonsite.error"),
	INVALID_TIME_ONSITE_ID("0006","0611.serviceorder.timeonsite.invalid.visit.id"),
	// Accept offer
	COUNTER_OFFER_SUBMITTED("0000","1001.serviceorder.offer.result"),
	COUNTER_OFFER_ERROR("0006","1002.serviceorder.offer.error"),
	WITHDRAW_OFFER_SUBMITTED("0000","1006.serviceorder.offer.result"),
	WITHDRAW_OFFER_ERROR("0006","1007.serviceorder.offer.error"),
	// Get buyer account
	GET_BUYER_ACCOUNT_SUCCESSFUL("0000","1401.get.buyeraccount.result"),
	GET_BUYER_ACCOUNT_USER_DOES_NOT_EXIST("0006","1402.get.buyeraccount.error"),
	//SendAlert
	SEND_ALERT_SUCCESSFUL("0000","1201.alert.send.result"),
	SEND_ALERT_NOT_SUCCESSFUL("0006","1202.alert.send.result"),
	//DocumentDelete
	DOCUMENT_DELETE_SUCCESS("0000","1103.serviceorder.deletesodoc.result"),
	DOCUMENT_DELETE_FAILURE("0006","1104.serviceorder.deletesodoc.error"),
	DOCUMENT_DELETE_INPUT_ERROR("0006","1105.serviceorder.deletedoc.error"),
	//Resolve Issue on SO
	RESOLVE_SUCCESSFUL("0000","1401.serviceorder.resolve.success"),
	RESOLVE_FAILURE("0006","1402.serviceorder.resolve.failure"),
	RESOLVE_COMMENTS_EMPTY("0006","1403.serviceorder.resolve.comments.failure"),
	//Add Document to SO
	DOCUMENT_ADDED_TO_SO("0000","1101.serviceorder.addsodoc.result"),
	DOCUMENT_DELETE_FROM_SO_FAILURE("0006","1104.serviceorder.deletesodoc.error"),
	DOCUMENT_DELETE_FROM_SO_SUCCESS("0000","1103.serviceorder.deletesodoc.result"),
	//Reject SO
	REJECT_SUCCESSFUL("0000","1601.serviceorder.reject.success"),
	REJECT_FAILURE("0006","1602.serviceorder.reject.failure"),
	VENDOR_RESOURCE_ID_FAILURE("0006","1603.serviceorder.provider.error"),
	//Accept Reschedule Request
	ACCEPT_RESCEDULE_SUCCESS("0000","1701.serviceorder.acceptReschedule.result"),
	ACCEPT_RESCEDULE_NOT_AUTHORIZED("0006","1702.serviceorder.acceptReschedule.error"),
	ACCEPT_RESCEDULE_NOREQUEST("0006","1703.serviceorder.acceptReschedule.error"),
	ACCEPT_RESCEDULE_FAILURE("0006","1704.serviceorder.acceptReschedule.error"),
	//Reject Reschedule Request
	REJECT_RESCEDULE_SUCCESS("0000","1801.serviceorder.rejectReschedule.result"),
	REJECT_RESCEDULE_NOT_AUTHORIZED("0006","1802.serviceorder.rejectReschedule.error"),
	REJECT_RESCEDULE_NOREQUEST("0006","1803.serviceorder.rejectReschedule.error"),
	REJECT_RESCEDULE_FAILURE("0006","1804.serviceorder.rejectReschedule.error"),
	//Create Counter Offer
	SO_CONDITIONAL_START_DATE_OR_SPEND_LIMIT_REQUIRED("0006","1902.serviceorder.create.offer.error"),
	SO_CONDITIONAL_OFFER_NEGATIVE_SPEND_LIMIT("0006","1903.serviceorder.create.offer.error"),
	SO_CONDITIONAL_EXPIRATION_DATE_REQUIRED("0006","1904.serviceorder.create.offer.error"),
	SO_CONDITIONAL_OFFER_STATUS_NOTMATCH("0006","1905.serviceorder.create.offer.error"),
	SO_CONDITIONAL_OFFER_PROVIDER_NOTMATCH("0006","1906.serviceorder.create.offer.error"),
	SO_CONDITIONAL_OFFER_PROVIDE_START_DATE("0006","1907.serviceorder.create.offer.error"),
	SO_CONDITIONAL_CAR_SO("0006","1908.serviceorder.create.offer.error"),
	SO_WITHDRAW_OFFER_STATUS_NOTMATCH("0006","1909.serviceorder.withdraw.offer.error"),
	SO_WITHDRAW_NO_COUNTER_OFFER("0006","1910.serviceorder.withdraw.offer.error"),
	SO_CONDITIONAL_COUNTER_OFFER_NOT_ALLOWED_FOR_NON_FUNDED("0006","1911.serviceorder.create.offer.error"),
	//Report problem :provider
	REPORT_PROBLEM_PROVIDER_NOT_ASSOCIATED("0006","2101.serviceorder.provider.report.problem.error"),
	REPORT_PROBLEM_PROVIDER_FAILURE("0006","2102.serviceorder.provider.report.problem.error"),
	REPORT_PROBLEM_PROVIDER_NOT_APPROPRIATE_STATUS("0006","2103.serviceorder.provider.report.problem.error"),
	REPORT_PROBLEM_DESCRIPTION_EMPTY("0006","2106.serviceorder.provider.report.problem.error"),
	REPORT_PROBLEM_PROVIDER_SERVICEORDER_NULL("0006","2104.serviceorder.provider.report.problem.error"),
	REPORT_PROBLEM_PROVIDER_SUCCESS("0000","2105.serviceorder.provider.report.problem.result"),
	//Complete SO Response
	COMPLETE_SO_SUCCESS("0000","2201.serviceorder.compelteso.success"),
	COMPLETE_SO_PARTS_ERROR("0006","2202.serviceorder.compelteso.error"),
	COMPLETE_SO_LABOR_ERROR("0006","2203.serviceorder.compelteso.error"),
	//Reject Reschedule :provider
	REJECT_RESCHEDULE_PROVIDER_NOT_ASSOCIATED("0006","2301.serviceorder.provider.reject.reschedule.error"),
	//Rate Buyer
	RATE_BUYER_COMMENTS_EMPTY("0006","2401.serviceorder.rate.buyer.comments.empty"),
	//Reassign SO
	SO_REASSIGN_FIRSTNAME_EMPTY("0006","2501.serviceorder.reassign.firstname.empty"),
	SO_REASSIGN_LASTNAME_EMPTY("0006","2502.serviceorder.reassign.lastname.empty"),
	SO_REASSIGN_COMMENTS_EMPTY("0006","2503.serviceorder.reassign.comments.empty"),
	SO_REASSIGN_WRONG_RESOURCE("0006","2504.serviceorder.reassign.resource.error"),
	//Retrieve SOs
	SO_RETRIEVE_SOS_FOUND("0000","3101.serviceorder.retrieve.success"),
	SO_RETRIEVE_SOS_NOTFOUND("0006","3102.serviceorder.retrieve.failure"),
	//Retrieve SpendLimitIncrease
	SO_SPENDLIMIT_FETCHED("0000","5101.serviceorder.retrievespendlimit.success"),
	SO_SPENDLIMIT_NOT_RECORDED("0006","5102.serviceorder.retrievespendlimit.failure"),
	SO_SPENDLIMIT_INVALID_SPENDLIMIT_RESPONSE("0006","5103.serviceorder.retrievespendlimit.invalid.response"),
	SO_SPENDLIMIT_NOT_AVAILABLE("0006","5104.serviceorder.retrievespendlimit.empty"),
//GetSOPriceDetails
	RETRIEVE_PRICE_HISTORY_RESULT_CODE("0000","6101.serviceorder.get.price.history.result"),
	INVALID_SO_COUNT("0007","6102.serviceorder.get.price.invalid.so.count"),
	RETRIEVE_PRICE_HISTORY_RESULT_CODE_VALUE("0007","6103.serviceorder.get.price.history.result_1"),
	//SL-15642:assign/re-assign provider
	ASSIGN_SUCCESS("0000","6001.serviceorder.assign.provider.success"),
	ASSIGN_STATE_ERROR("0006","6002.serviceorder.assign.provider.error"),
	ASSIGN_PROVIDER_ERROR("0006","6003.serviceorder.assign.provider.error"),
	ASSIGN_ERROR_ALREADY_ASSIGNED("0006","6004.serviceorder.assign.provider.error"),
	SO_ACCEPT_FAILURE("0006","1504.serviceorder.accept.failure"),
	
//Create Ptovider Messages
	
	INVALID_LEGALBUSINESSNAME("0004","7001.validation.error"),
	INVALID_DBANAME("0004","7002.validation.error"),
	INVALID_WEBSITEADDRESS("0003","7003.validation.error"),
	MAINBUSINESSPHONE("0004","7004.validation.error"),
	MAINBUSINESSEXT_INVALID("0004","7051.validation.error"),
	INVALID_MAINBUSINESSEXTN("0005","7005.validation.error"),
	BUSINESSFAX("0004","7006.validation.error"),
	INVALID_BUSINESSSTREET("0004","7007.validation.error"),
	BUSINESSCITY("0004","7008.validation.error"),
	BUSINESSSTATE("0004","7009.validation.error"),
	BUSINESSZIP("0004","7010.validation.error"),
	BUSINESSZIP_INVALID("0004","7050.validation.error"),
	BUSINESSZIP_INVALID_LENGTH("0004","7082.validation.error"),
	BUSINESSAPART("0004","7011.validation.error"),
	MAILINGSTREET("0004","7012.validation.error"),
	MAILINGCITY("0004","7013.validation.error"),
	MAILINGSTATE("0004","7014.validation.error"),
	MAILINGZIP("0004","7015.validation.error"),
	MAILINGZIP_INVALID("0004","7052.validation.error"),
	MAILINGAPART("0004","7016.validation.error"),
	HOWDIDYOUHERE("0004","7017.validation.error"),
	PROMOTIONCODE("0004","7029.validation.error"),
	ROLEWITHINCOM("0004","7018.validation.error"),
	JOBTITLE("0004","7019.validation.error"),
	SERVICECALL("0004","7020.validation.error"),
	FIRSTNAME("0004","7021.validation.error"),
	LASTNAME("0004","7023.validation.error"),
	
	//SL 16934 Error code for zip code and referral code 
	//Athentication Api
	INVALID_USER_CREDENTIALS("0004","8000.validation.error"),
	INVALID_REFERRAL_CODE("0004","7049.validation.error"),
	INVALID_PROVIDER_FIRM_ID("0004","7050.validation.error"),
	INVALID_LEAD_EMAIL_ID("0004","7052.validation.error"),
	INVALID_LEAD_PHONE_NO("0004","7053.validation.error"),
	INVALID_LEAD_DAILY_LIMIT ("0004","7054.validation.error"),
	INVALID_LEAD_MONTHLY_LIMIT("0004","7055.validation.error"),
	INVALID_AVAILABLE_DAYS_WEEK("0004","7056.validation.error"),
	INVALID_FORMAT_AVAILABLE_DAYS_WEEK("0004","7063.validation.error"),
	INVALID_DELIVERY_OPTION("0004","7058.validation.error"),
	INVALID_LOCATION_TYPE("0004","7059.validation.error"),
	INVALID_LEAD_ELIGIBILITY("0004","7060.validation.error"),
	INVALID_PACKAGE_ID("0004","7064.validation.error"),
	INVALID_LEAD_MONTHLY_BUDGET("0004","7068.validation.error"),
	INVALID_EMAIL_ID("0004","7003.validation.error"),
	INVALID_COVERAGE_IN_MILES("0004","9000.validation.error"),
	INVALID_IS_LICENSING_IND("0004","9001.validation.error"),
	INVALID_LICENSING_STATE("0004","9002.validation.error"),
	
	//SL 16934 Billing 
	INVALID_PARTNER_ID("0004","7071.validation.error"),
	INVALID_BILLING_AMOUNT("0004","7072.validation.error"),
	INVALID_ORGANIZATION_TYPE("0004","7073.validation.error"),
	INVALID_DESCRIPTION("0004","7074.validation.error"),
	INVALID_EXPIRATION_MONTH("0004","7075.validation.error"),
	INVALID_EXPIRATION_YEAR("0004","7076.validation.error"),
	INVALID_CARD_NUMBER("0004","7077.validation.error"),
	LEAD_BILLING_SUCCESS("0000","7078.leads.billing.success"),
	LEAD_BILLING_FAILURE("0006","7081.leads.billing.failure"),
	INVALID_CARD_TYPE("0004","7079.validation.error"),
	INVALID_CCV("0004","7080.validation.error"),
	INVALID_URGENCY_SERVICES("0004","7069.validation.error"),
	
	INVALID_ZIP_CODE("0004","7048.validation.error"),
	EMAIL("0004","7024.validation.error"),
	CONEMAIL("0004","7025.validation.error"),
	ALTEREMAIL("0004","7026.validation.error"),
	USERNAME("0004","7028.validation.error"),
	PRIMARYINDUSTRY("0004","7029.validation.error"),
	PHONENUMBER("0004","7030.validation.error"),
	PHONENUMBERVALI("0004","7031.validation.error"),
	PHONENUMBERLENGTH("0004","7032.validation.error"),
	DUPLICATE_USERNAME("0004","7033.validation.error"),
	INVALID_MAILINGZIP("0004","7034.validation.error"),
	INVALID_BUSINESSZIP("0004","7035.validation.error"),
	INVALID_HOW_DID_YOU_HEAR("0004","7036.validation.error"),
	INVALID_PRIMARY_INDUSTRY("0004","7037.validation.error"),
	INVALID_USERNAME("0004","7039.validation.error"),
	FAXNUMBER("0004","7040.validation.error"),
	FAXNUMBER_INVALID("0004","7041.validation.error"),
	FAXNUMBER_INVALID_LENGTH("0004","7042.validation.error"),
	INVALID_ROLE_WITH_COM("0004","7038.validation.error"),
	INVALID_BUSINESS_STATE("0004","7043.validation.error"),
	INVALID_EMAIL("0004","7045.validation.error"),
	INVALID_ALTERNATE_EMAIL("0004","7046.validation.error"),
	INVALID_SERVICE_CALL("0004","7047.validation.error"),
	INVALID_MAILING_STATE("0004","7044.validation.error"),
	LEAD_REGISTRATION_SUCCESS("0000","7062.lead.success"), 
	
	DUPLICATE_USERNAME_IPR("4001","7070.validation.error"),
	
	LEAD_REGISTRATION_FAILED("0004","7065.lead.failed.lms"),
	INCOMPLETE_SL_PROFILE("0004","7066.lead.failed.sl.incomplete"),
	INCOMPLETE_FILTER_PROFILE("0004","7077.lead.failed.filter.incomplete"),
	
	//SL 16934 : Insert FilterSet API
	INVALID_FILTER_NAME("0004","8001.validation.error"), 
	INVALID_FILTER_PRICE("0004","8002.validation.error"), 
	INVALID_ACCEPTED_SOURCES("0004","8003.validation.error"), 
	INVALID_FORMAT_ACCEPTED_SOURCES("0004","8004.validation.error"), 
	INVALID_MATCH_PRIORITY("0004","8005.validation.error"), 
	INVALID_SERVICE_TIME("0004","8006.validation.error"), 
	INVALID_FORMAT_SERVICE_TIME("0004","8007.validation.error"), 
	INVALID_ACCEPT_MANUALLY_REVIEWED("0004","8008.validation.error"), 
	INVALID_ZIP_MODE("0004","8009.validation.error"), 
	INVALID_ZIP("0004","8010.validation.error"), 
	INVALID_FORMAT_ZIP("0004","8011.validation.error"), 
	INVALID_STATE("0004","8012.validation.error"), 
	INVALID_FORMAT_STATE("0004","8013.validation.error"), 
	INVALID_SKILL("0004","8014.validation.error"), 
	INVALID_FORMAT_SKILL("0004","8015.validation.error"), 
	INVALID_PARTNER("0004","8016.validation.error"), 
	FILTER_CREATION_SUCCESS("0000","8017.filter.success"),
	FILTER_CREATION_FAILURE("0004","8020.filter.failure"),
	FILTER_CREATION_COMP_FAILURE("0004","8025.filter.failure"),
	FILTER_CREATION_EXEC_FAILURE("0004","8026.filter.failure"),
	//SL 16934 : getProjectTypes API
	INVALID_VENDOR("0004","8018.validation.error"), 
	NO_PROJECTS_AVAILABLE("0004","8019.validation.error"),
	FILTER_AVAILABLE("0004","8020.validation.error"), 
	TERMS_CONDITIONS("0004","8021.validation.error"),

	//getleadproviderFirm API
	INVALID_ZIP_CODES("0001","6201.leads.getleadproviderfirm.custzipcode.error"),
	INVALID_ZIP_CODE_NON_US("0007","6207.leads.getleadproviderfirm.custzipcode.error"),
	UNABLE_TO_PROCESS("0002","6202.leads.getleadproviderfirm.unabletoprocess.error"),
	INVALID_PROJECT("0003","6203.leads.getleadproviderfirm.project.error"),
	UNMATCHED("0005","6205.leads.getleadproviderfirm.unmatched.error"),
	LEAD_CREATION_VIA_OF_FAILED("0006","6206.leads.getleadproviderfirm.of.creation.failed.error"),
	//postmember lead API
	INVALID_PROJECT1("0001","6202.serviceorder.create.error"),
	INVALID_ZIP_STATE("0001","6203.serviceorder.create.error"),
	INVALID_LEAD_ID("0003","6303.postlead.invalidleadid.error"),
	INVALID_LEAD_STATUS("0004","6304.postlead.invalidleadidStatus.error"),
	INVALID_LEAD_STATUS_FOR_POST("0004","6305.postlead.invalidleadidStatus.error"),
	INVALID_LEAD_STATUS_FOR_POST_V2("0005","6331.postlead.invalidleadidStatus.error"),
	NONLAUNCHZIP_LEAD_STATUS_FOR_POST("0005","6306.postlead.nonlaunchzip.warning"),
	FIRM_INVALID("0006","6332.postlead.firminvalid.error"),
	FIRM_NOT_POSTED("0007","6333.postlead.firmalnotposted.error"),
	FIRM_ALREADY_MATCHED("0008","6330.postlead.firmalreadymatched.error"),
	//Update Membership Info
		INVALID_LEAD_MEMBERSHIP_STATUS("0001","6319.leads.updateMembershipInfo.leadstatus.invalid"),
	//getAllleadsByFirmAPI
	LEADS_NOT_FOUND("0001","6204.leads.getAllLeadsByFirm.leadId.notfound"),
	//getInviduallLeadDetails API
	LEAD_NOT_FOUND("0002","6205.leads.getIndividuallead.leadnotfound.error"),	
	FIRM_NOT_FOUND("0004","6206.leads.getIndividuallead.firmId.notfound"),
	UNMATCHED1("0005","6207.leads.getIndividuallead.unmatched.error"),
	NOENTRY("0003","6208.leads.getIndividuallead.noentry.error"),
	//UpdateLeadStatusAPI
	LEAD_STATUS_NOT_FOUND("0006","6301.leads.updateLeadStatus.leadstatus.notfound"),
	FIRM_STATUS_NOT_FOUND("0007","6302.leads.updateLeadStatus.firmstatus.notfound"),
	FIRM_STATUS_EMPTY("0008","6303.leads.updateLeadStatus.frimstatus.empty"),
	LEAD_STATUS_EMPTY("0009","6304.leads.updateLeadStatus.leadstatus.empty"),
	LEAD_CANNOT_BE_UPDATED("0010","6317.leads.updateLeadStatus.leadstatus.invalid"),
	FIRM_STATUS_CANNOT_BE_UPDATED("0011","6318.leads.updateLeadStatus.firmstatus.invalid"),
	//AssignProviderAPI 
	INVALID_FIRM_ID("0004","6305.leads.assignProvider.firmid.notfound"),
	INVALID_RESOURCEID("0004","6306.leads.assignProvider.resourceid.notfound"),
	INVALID_FIRM_FOR_LEAD("0004","6309.leads.assignProvider.firmforlead.notfound"),
	ASSIGN_PROVIDER("0000","6307.leads.assignProvider.assignedprovider"),
	REASSIGN_PROVIDER("0000","6308.leads.assignProvider.reassignedprovider"),
	ASSIGN_SAME_PROVIDER("0000","6316.leads.assignProvider.reassignsameprovider"),
	//Lead ADD Note API
	EMPTY_LEAD_NOTE_CATEGORY("0006","6311.leads.addNote.empty.note.category"),
	EMPTY_LEAD_NOTE_BODY("0006","6312.leads.addNote.empty.note.body"),
	INVALID_LEAD_NOTE_CATEGORY("0006","6313.leads.addNote.invalid.note.category"),
	EMPTY_LEAD_NOTES("0006","6310.leads.addNote.empty.note"),
	INVALID_BUYER("0006","6314.leads.addNote.unmatched.buyer.error"),
	//complete Leads
	COMPLETE_LEADS("0000","6317.leads.completeLeads.completedLeads"),
	LEAD_STATUS_NOT_MATCHED("0006","6320.leads.completeLeads.LeadStatusNotMatched"),
	LEAD_STATUS_ALREADY_COMPLETED("0006","6325.leads.completeLeads.LeadStatusCompleted"),
	FIRM_STATUS_NOT_SCHEDULED("0006","6321.leads.completeLeads.FirmStatusNotScheduled"),
	INVALID_COMPLETED_DATETIME("0006","6322.leads.completeLeads.InvalidCompletedDate"),
	RESOURCE_ID_MISSING("0006","6323.leads.completeLeads.ResourceIdNotPresent"),
	INVALID_RESOURCE_ID_FOR_FIRM("0006","6324.leads.completeLeads.ResourceIdNotValid"),
	//Get Eligible providers API.
	NO_ELIGIBLE_PROVIDER_FOUND("0006","6315.leads.no.eligible.provider.found"),
	//update lead schedule
	LEAD_FIRM_NOT_MATCHING("0002","6318.leads.firm.not.matching"),
	
	INVALID_LEAD_STATE("0006","6319.leads.addNote.invalid.lead.status.error"),
	
	INVALID_LEAD_NOTE_ID("0006","6325.leads.addNote.invalid.note.id"),
	//schedule Appointment 
	SCHEDULE_SUCCESS("0001","6326.leads.scheduleAppointment.leadScheduled"),
	RESCHEDULE_SUCCESS("0002","6327.leads.scheduleAppointment.leadRescheduled"),
	SCHEDULE_INVALID_START_TIME("0003","6328.leads.scheduleAppointment.invalidScheduledStartTime"),
	SCHEDULE_INVALID_START_ENDTIME("0003","6329.leads.scheduleAppointment.invalidScheduledStartEndTime"),
    
	INVALID_BUYER_RESOURCE_ID("0006","6326.leads.addNote.invalid.buyer.resource.id"),
	
	INVALID_PROVIDER_RESOURCE_ID("0006","6327.leads.addNote.invalid.provider.resource.id"),
	
	INVALID_BUYER_RESOURCE("0006","6328.leads.addNote.invalid.buyer.resource"),
	
	INVALID_PROVIDER_RESOURCE("0006","6329.leads.addNote.invalid.provider.resource"),
    //GetleadFirmDetails API
	INVALID_LEAD_BUYER_ASSOCIATION("0006","6401.leads.getleadFirmDetails.invalid.leadbuyerAssociation"),
	//Download Signed Copy Webservice
	INVALID_SERVICE_ORDER("0100","6402.download.signed.copy.document.invalid.serviceorder"),
	INVALID_BUYER_ID_NEW("0101","6403.download.signed.copy.document.invalid.buyerId"),
	SERVICE_ORDER_NOT_ASSOCIATED("0102","6404.download.signed.copy.document.serviceorder.not.associated"),
	SIGNED_COPY_NOT_AVAILABLE("0103","6405.download.signed.copy.document.document.not.available"),
	DOCUMENT_SIZE_EXCEEDS_LIMIT("0104","6406.download.signed.copy.document.document.size.exceeds"),
	SO_COUNT_EXCEEDS_LIMIT("0105","6407.download.signed.copy.document.invalid.so.count"),
	INVALID_SO_STATUS("0106","6408.download.signed.copy.document.invalid.so.status"),
	SIGNED_COPY_NOT_AVAILABLE_ORDER("0103","6409.download.signed.copy.document.document.not.available.order"),
	SO_DOCUMENTS_NOT_AVAILABLE("0103","6410.download.so.documents.not.available"),
	INVALID_SO_DOCUMENT_TYPE("0105","6411.download.so.invalid.document.type"),
	SO_DOCUMENT_SIZE_EXCEEDS_LIMIT("0104","6412.download.so.document.size.exceeds"),
	// Post API validations
	NOT_APPROVED_PROVIDERS("0510","0510.serviceorder.post.error"),
	NO_ELIGIBLE_PROVIDERS("0511","0511.serviceorder.post.error"),
	INVALID_ROLE("4011","4011.invalid.role"),
	// Post to Firm V1.0 API validations
	POSTFIRM_INVALID_SO_STATE("1604","1604.serviceorder.post.soStatus.error"),
	POSTFIRM_NO_ELIGIBLE_PROVIDERS("1605","1605.serviceorder.post.noproviders.error"),
	POSTFIRM_INVALID_FIRM("1606","1606.serviceorder.post.invalidFirm.error"),
	POSTFIRM_BUYER_UPFUND_ERROR("1608","1608.serviceorder.post.buyer.transaction.limit.error"),
	POSTFIRM_FROM_DATETIME_ERROR_CODE("1607","1607.serviceorder.post.datetime.error"),
	POSTFIRM_FROM_DATE_ERROR_CODE("1607","1608.serviceorder.post.datetime.error"),
	POSTFIRM_FROM_TODATE_ERROR_CODE("1607","1609.serviceorder.post.datetime.error"),
	POSTFIRM_TIME_ERROR_CODE("1607","1611.serviceorder.postpost.datetime.error"),
	POSTFIRM_NOTELIGIBLE_FIRM("1612","1612.serviceorder.post.notEligibleFirm.error"),
	
	//Home Improvement API's
	
	CREATE_FIRM_SUCCESS("3000","3000.home.improvement.success.create.firm"),
	INVALID_PRIMARY_INDUSTRY_FIRM("3001","3001.home.improvement.invalid.primary.industry.firm"),
	INVALID_CREDENTIAL_TYPE_FIRM("3002","3002.home.improvement.invalid.credential.type.firm"),
	INVALID_BUSINESS_STATE_FIRM("3003","3003.home.improvement.invalid.business.state.firm"),
	INVALID_MAILING_STATE_FIRM("3004","3004.home.improvement.invalid.mailing.state.firm"),
	INVALID_CREDENTIAL_ISSUE_DATE_FIRM("3005","3005.home.improvement.invalid.credential.expiration.issue.date.firm"),
	INVALID_GENERAL_INSURANCE_EXPIRATION_DATE_FIRM("3006","3006.home.improvement.invalid.insurance.general.expiration.date.firm"),
	INVALID_W9_DATE_OF_BIRTH_FIRM("3007","3007.home.improvement.invalid.date.of.birth.w9.firm"),
	USERNAME_EXISTS_FIRM("3008","3008.home.improvement.username.exist.firm"),
	MISMATCH_CREDENTIAL_TYPE_CATEGORY_FIRM("3009","3009.home.improvement.mismatch.credential.type.category.firm"),
	UDPATE_FIRM_SUCCESS("3010","3010.home.improvement.success.update.firm"),
	INVALID_FIRMID("3011","3011.home.improvement.invalid.firm.id"),
	INVALID_BUSINESS_DATE_FIRM("3012","3012.home.improvement.invalid.business.date.firm"),
	INVALID_BUSINESSZIP_FIRM("3013","3013.home.improvement.invalid.business.zip.firm"),
	INVALID_MAILINGZIP_FIRM("3014","3014.home.improvement.invalid.mailing.zip.firm"),
	INVALID_W9_TAX_PAYER_ID_EIN_FIRM("3015","3015.home.improvement.invalid.tax.payer.id.ein.firm"),
	INVALID_W9_TAX_PAYER_ID_SSN_FIRM("3016","3016.home.improvement.invalid.tax.payer.id.ssn.firm"),
	INVALID_LICENSE_STATE_FIRM("3017","3017.home.improvement.invalid.license.cert.state.firm"),
	INVALID_GENERAL_LIABILITY_STATE_FIRM("3018","3018.home.improvement.invalid.general.liability.state.firm"),
	INVALID_VEHICLE_LIABILITY_STATE_FIRM("3019","3019.home.improvement.invalid.vehicle.liability.state.firm"),
	INVALID_WORKMANS_COMPENSATION_STATE_FIRM("3020","3020.home.improvement.invalid.workers.compensation.state.firm"),
	INVALID_STATEZIP_W9_FIRM("3021","3021.home.improvement.invalid.state.zip.w9.firm"),
	INVALID_STATE_W9_FIRM("3022","3022.home.improvement.invalid.state.w9.firm"),
	INVALID_STATE_WARRANTY_DRUG_FIRM("3023","3023.home.improvement.warranty.consider.drug.firm"),
	INVALID_STATE_WARRANTY_ETHICS_FIRM("3024","3024.home.improvement.warranty.consider.ethics.firm"),
	INVALID_STATE_WARRANTY_CITIZEN_FIRM("3025","3025.home.improvement.warranty.consider.citizenship.firm"),
	INVALID_STATE_WARRANTY_BADGES_FIRM("3026","3026.home.improvement.warranty.consider.badges.firm"),
	INVALID_TAXPAYER_ID_FIRM("3027","3027.home.improvement.invalid.taxpayer.id.firm"),
	MANDATORY_W9_DATE_OF_BIRTH_FIRM("3028","3028.home.improvement.mandatory.date.of.birth.w9.firm"),
	INVALID_LICENSE_VENDOR_CREDENTIAL_ID_FIRM("3029","3029.home.improvement.license.vendor.credential.not.found.firm"),
	INVALID_GENERAL_INSURANCE_EXPIRATION_DATE_PAST_FIRM("3030","3030.home.improvement.invalid.insurance.general.expiration.past.date.firm"),
	INVALID_VEHICLE_INSURANCE_EXPIRATION_DATE_FIRM("3031","3031.home.improvement.invalid.insurance.vehicle.expiration.date.firm"),
	INVALID_WORKMANS_INSURANCE_EXPIRATION_DATE_FIRM("3032","3032.home.improvement.invalid.insurance.workers.expiration.date.firm"),
	INVALID_VEHICLE_INSURANCE_EXPIRATION_DATE_PAST_FIRM("3033","3033.home.improvement.invalid.insurance.vehicle.expiration.past.date.firm"),
	INVALID_WORKMANS_INSURANCE_EXPIRATION_DATE_PAST_FIRM("3034","3034.home.improvement.invalid.insurance.workers.expiration.past.date.firm"),
	INVALID_GENERAL_LIABILITY_AMT("3035","3035.home.improvement.invalid.general.liability.amt"),
	INVALID_VEHICLE_LIABILITY_AMT("3036","3036.home.improvement.invalid.vehicle.liability.amt"),
	INVALID_WORKMAN_LIABILITY_AMT("3037","3037.home.improvement.invalid.workman.liability.amt"),
	INVALID_USER_NAME("3038","3038.home.improvement.invalid.username"),
	INVALIDE_FIRM_LICENSE("3039","3039.home.improvement.invalid.firm.license"),
	
	// Approve Firm API Start
	STATUS_CHANGE_PARTIAL_SUCCESS("3200","3200.home.improvement.success.partial"),
	INVALID_FIRM_ID_ALL("3201","3201.home.improvement.invalid.firm.all"),
	INVALID_HI_FIRM_ID("3202","3202.home.improvement.invalid.hi.firm"),
	INVALID_FIRM_STATUS("3203","3203.home.improvement.invalid.firm.status"),
	STATUS_CHANGE_SUCCESS_ALL("3204","3204.home.improvement.success.all"),
	STATUS_CHANGE_SUCCESS_IND("3205","3205.home.improvement.success.ind"),
	INVALID_CURRENT_STATUS("3206","3206.home.improvement.invalid.current.status"),
	REQUIRED_REASON_CODE("3207","3207.home.improvement.required.reasoncode"),
	INVALID_REASON_CODE("3208","3208.home.improvement.invalid.reasoncode"),
	INVALID_ADMIN_RESOURCE_ID("3209","3209.home.improvement.invalid.admin.resource"), 
	// Approve Firm API End
	//Create Provider Account
	INVALID_SSN("3103","3103.home.improvement.invalid.ssn"),
	INVALID_DISPATCH_STATE("3105","3105.home.improvement.invalid.dispatch.state"),
	INVALID_DISPATCH_STATE_ZIP("3101","3101.home.improvement.invalid.state.zip"),
	USER_NAME_EXISTS("3102","3102.home.improvement.username.exists"),
	INVALID_START_END_TIME("3107","3107.home.improvement.invalid.start.end.time"),
	INVALID_USER_NAME_LENGTH("3110","3110.home.improvement.invalid.username.length"),
	INVALID_SECONDARY_CONTACT("3104","3104.home.improvement.invalid.secondary.contact"),
	PROVIDER_SUCCESS("3100","3100.home.improvement.provider.success"), 
	INVALID_LICENSE_RESOURCE_CREDENTIAL_ID_PROVIDER("3108","3108.home.improvement.invalid.resource.credential.id"), 
	INVALID_LICENSE_RESOURCE_CREDENTIAL_NUMBER_PROVIDER("3106","3106.home.improvement.invalid.alpha.numeric"),
	INVALID_LICENSE_CREDENTIALS("3112","3112.home.improvement.enter.credentials"),
	INVALID_START_TIME_GREATER_END_TIME("3113","3113.home.improvement.invalid.start.time.greater.end.time"),
	
	//Update Provider API
	
	SUCCESS_UPDATE_PROVIDER("3150","3150.home.improvement.update.success.provider"),
	INCOMPLETE_UPDATE_PROVIDER_REQUEST("3109","3109.home.improvement.incomplete.request.update.provider"),
	DUPLICATE_WEEKDAYS("3111","3111.home.improvement.duplicate.weekname.availability"),
	
	
	//  AddProviderSkill API
	PROVIDER_SKILL_SUCCESS_ALL("3250","3250.home.improvement.success.provider.skill"),
	INVALID_PROVIDER_ID("3251","3251.home.improvement.invalid.provider.id"),
	INVALID_SKILLS("3252","3252.home.improvement.invalid.skills"),
	INVALID_SERVICE_TYPES("3253","3253.home.improvement.invalid.service.type"),
	SKILLS_ALREADY_IN_TABLE("3254","3254.home.improvement.skills.already_in_table"),
	
	// RemoveProviderSkill API
	PROVIDER_SKILL_REMOVE_ALL("3270","3270.home.improvement.remove.success.provider.skill"),
	NO_SKILLS_TO_FETCH("3271","3271.home.improvement.no.skills.for.provider"),
	// Approve Provider START
	INVALID_HI_PROVIDER_ID("3402","3402.home.improvement.invalid.hi.provider"),
	INVALID_PROVIDER_STATUS("3403","3403.home.improvement.invalid.provider.status"),
	INVALID_CURRENT_PROVIDER_STATUS("3406","3406.home.improvement.invalid.current.provider.status"),
	REQUIRED_PROVIDER_REASON_CODE("3407","3407.home.improvement.required.reasoncode.provider"),
	INVALID_PROVIDER_REASON_CODE("3408","3408.home.improvement.invalid.provider.reasoncode"),
	PROVIDER_STATUS_CHANGE_SUCCESS_IND("3405","3405.home.improvement.success.provider.ind"),
	PROVIDER_STATUS_CHANGE_PARTIAL_SUCCESS("3400","3400.home.improvement.success.provider.partial"),
	PROVIDER_STATUS_CHANGE_SUCCESS_ALL("3404","3404.home.improvement.success.provider.all"),
	INVALID_PROVIDER_ID_ALL("3401","3401.home.improvement.invalid.provider.all"),
	PROVIDER_INVALID_ADMIN_RESOURCE_ID("3409","3409.home.improvement.invalid.admin.provider.resource"),
	INVALID_PROVIDER_BG_STATUS("3410","3410.home.improvement.invalid.provider.bg.status"),
	//Approve Provider END;
	// Estimattion error 
	
	INVALID_ESTIMATION_ID_ERROR("4501","4501.serviceorder.estimate.error"),
	
	//B2C: Get Closed Orders for firm API
	CLOSEDORDERS_VALIDATION_COMPLETEDIN_ERROR_CODE("4502","4502.serviceorder.closedorders.result.set.validation.error"),
	CLOSEDORDERS_VALIDATION_NOOFORDERS_ERROR_CODE("4503","4503.serviceorder.closedorders.result.set.validation.error"),
	
	//B2C: Get Firm Details API
	FIRM_DETAIL_SUCCESS("0000","4506.firm.details.success"),
	INVALID_VENDOR_ID("0001","4504.invalid.firmid.error"),
	INVALID_RESPONSE_FILTER("0002","4505.invalid.response.filter.error"),
	
	//R16_1_1: SL-21270: Invalid SO Final Price
	INVALID_SO_FINAL_PRICE("4507","4507.invalid.so.final.price"),
	ADDON_CLOSE_ERROR("4508","4508.addon.close.error"),
	INVALID_WF_STATE("4509","4509.invalid.so.wfstate"),
	
		//R16_1_2: SL-21378 
	INVALID_CATEGORY_ID("4514","4514.search.providers.categid.error"),
	INVALID_SERVICE_TYPE("4515","4515.search.providers.servicetype.error"),
	INVALID_MAX_DISTANCE("4516","4516.search.providers.maxdistance.error"),
	INVALID_PAGE_SIZE("4517","4517.search.providers.pagesize.error"),
	INVALID_PAGE_NUM("4518","4518.search.providers.pagenum.error"),
	INVALID_MIN_RATING("4519","4519.search.providers.minrating.error"),
	INVALID_MAX_RATING("4520","4520.search.providers.maxrating.error"),
	
	//R16_2_1: SL-21376
	INVALID_SKU("4521","4521.search.providers.sku.error"),
	SKU_CATEGORY_MISMATCH("4522","4522.search.providers.sku.category.mismatch"),
	
	//Added For D2C
	NO_MATCHING_FOR_THE_REQUESTED_DATA("0002","5757.no.firm.is.available"),
	CONTENT_TYPE_CAN_NOT_BE_BLANK("0415","5758.content.type.is.blank"),
	ONLY_JSON_FORMAT_IS_ACCEPTED("0415","5758.only.json.format.accepted"),
	
	//SL-21308: Search Firms API
	SEARCH_FIRMS_SUCCESS("0000","4510.search.firms.success"),
	PROVIDE_START_DATE("4511", "4511.search.firms.date.error"),
	START_DATE_NOT_PRIOR_ERROR("4512","0604.serviceorder.reschedule.error"),
	START_DATE_PAST_ERROR("4513","0111.serviceorder.create.error"),
	
	INVALID_ESTIMATE_ID("4013","4013.invalid.estimation.id.error"),
	INVALID_ESTIMATE_PRICE("4014","4014.invalid.estimation.price.error"),
	ADD_EDIT_ESTIMATE("0000","0000.add.edit_success.message"),
	NO_ESTIMATE_CHANGE("4015","4015.no.estimation.change.error"),
	
	//availableSlotsForTechTalk Service
	GET_AVAILABLE_TIME_SLOTS_DATE_FORMAT_ERROR("3901","3901.invalid.date.format"),
	
	GET_DESPOSITION_CODES_SUCCESS("4901","4901.get.despositioncodes.success"),
	GET_DESPOSITION_CODES_ERROR("4902","4902.get.despositioncodes.error"),

	START_DATE_AFTER_END_DATE("3902","3902.invalid.start.end.date"),
	SKU_NOT_AVAILABLE_FOR_BUYER("3903","3903.sku.not.available.for.buyer"),
	
			
	
	FILE_LIMIT_EXCEEDED("4016","4016.file.limit.exceeded"),
	
	//buyerEventCallbackService
	GET_BUYER_EVENT_CALLBACK_SUCCESS("0000","4903.get.buyercallbackcodes.success"),
	GET_BUYER_EVENT_CALLBACK_ERROR("0005","4904.get.buyercallbackcodes.error"),
	//CSAT_NPS survey
	SAVE_SURVEY_SUCCESS("0000","4905.save.survey.success"),
	SAVE_SURVEY_ERROR("0005","4906.save.survey.error"),
	SAVE_SURVEY_INVALID_PARAMETER("0005","4907.save.survey.invalid.parameter.error"),
	SAVE_SURVEY_INVALID_KEY("0005","4908.save.survey.key.error"),
	SAVE_SURVEY_INVALID_RATING("0005","4909.save.survey.rating.error"),
	
	//SLT-4491
	PROVIDER_DOES_NOT_EXIST("0005","4910.provider.not.exist"),
	
	
	IDENTIFICATION_INVALID_RESOURCE_ERROR("0005","4912.identification.resource.invalid"),
	IDENTIFICATION_INVALID_PROVIDER_RESOURCE_ERROR("0005","4913.identification.provider.resource.invalid"),
	
	STATUS_IS_REQUIRED("0005","4914.status.is.required");
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
	
	public String toString() {
		return code;
	}
}
