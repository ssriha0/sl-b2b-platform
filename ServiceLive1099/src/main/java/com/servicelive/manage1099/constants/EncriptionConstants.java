package com.servicelive.manage1099.constants;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.manage1099.db.DBAccess;

public interface EncriptionConstants {
	
	// ENCRYPTION_KEY is not used right now, as base64EncodingString is declared as constant.
	// ENCRYPTION_KEY is the key value in applicationproperties table in database which stores base64EncodingString value.
	
	// base64EncodingString value should come from database query. need to update the code.
	public static final String ENCRYPTION_KEY = "enKey";
	// The key value is in column app_value in table application_properties, in supplier db.
	public static final String ENCRYPTION_KEY_COLUMN  ="app_value";
	
	// The value is read back from database and re-assigned when used.
	//public static String base64EncodingString = "P3Q6GUCryVD51h5JOo0WMQ==";
	
	
}
