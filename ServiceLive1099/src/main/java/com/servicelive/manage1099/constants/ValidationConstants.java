/**
 * 
 */
package com.servicelive.manage1099.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mjoshi1
 *
 */
public class ValidationConstants {
	
	public static String VALIDATE = "false";
	// The name testFile.txt is nothing but a default name, while testing from local
	// The actual file name and file is loaded from path in slproperties.properties.
	public static String VALIDATE_AGAINST_FILE = "testFile.txt";
	// At which loc is the vendor id in comma separated file.
	public static int VENDOR_ID_INDEX_IN_FILE= 1;
	// At which loc is the vendor id in comma separated file.
	public static int AMOUNT_INDEX_IN_FILE= 2;
	// This property is used to trouble shoot any issues reported, when vendors are reported as missing in the file generated.
	public static String MISSING_VENDORS = "";
	// The MISSING_VENDORS property contains all vendors as comma separated.
	// MISSING_VENDORS_LIST breaks them down as a list.
	public static List<String> MISSING_VENDORS_LIST = new ArrayList<String>();
}
