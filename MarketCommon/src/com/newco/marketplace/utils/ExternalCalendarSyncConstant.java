package com.newco.marketplace.utils;

public class ExternalCalendarSyncConstant {

	public static final int JOB_SUCCESS = 1;
	public static final String SPACE 	= " ";
	
	
	/***** Keys for application properties table  ****/
	public class CRONOFY_PROPERTIES{
		public static final String CRONOFY_BASE_URL 				= "cronofy_base_url";
		public static final String CRONOFY_BASE_URL_V1 				= "cronofy_base_url_v1";
		public static final String CRONOFY_GET_EVENTS_API 			= "cronofy_get_event_api";
		public static final String CRONOFY_POST_EVNET_API			= "cronofy_post_event_api";
		public static final String CRONOFY_POST_OAUTH_API 			= "cronofy_post_oauth_api"; 
		public static final String CRONOFY_CLIENT_ID 				= "cronofy_client_id";
		public static final String CRONOFY_CLIENT_SECRET 			= "cronofy_client_secret";
		public static final String CRONOFY_GRANT_TYPE_AUTH			= "cronofy_grant_type_auth";
		public static final String CRONOFY_GRANT_TYPE_REFRESH 		= "cronofy_grant_type_refresh";
		public static final String CRONOFY_REDIRECT_URI				= "cronofy_redirect_uri";
		public static final String CRONOFY_DATE_FORMAT				= "cronofy_date_format";
		public static final String CRONOFY_TIME_ZONE				= "cronofy_time_zone";
		public static final String CRONOFY_TOKEN_TYPE				= "cronofy_token_type";
		public static final String CRONOFY_SYNC_PERIOD				= "cronofy_sync_period";
		public static final String CRONOFY_INCLUDE_MANAGED			= "cronofy_include_managed";
	}
	


	/***** Parameter keys for Cronofy apis   ****/
	public class CRONOFY_PARAMS {
		public static final String CLIENT_ID	 				= "client_id";
		public static final String CLIENT_SECRET 				= "client_secret";
		public static final String CODE							= "code";
		public static final String REFRESH_TOKEN				= "refresh_token";
		public static final String GRANT_TYPE					= "grant_type";
		public static final String GRANT_TYPE_AUTH_CODE			= "authorization_code";
		public static final String GRANT_TYPE_REFRESH_TOKEN		= "refresh_token";
		public static final String REDIRECT_URI					= "redirect_uri";
		public static final String TZ_ID						= "tzid";
		public static final String FROM							= "from";
		public static final String TO							= "to";
		public static final String INCLUDE_MANAGED				= "include_managed";
	}
}
