/**
 * 
 */
package com.servicelive.integrationtest.util;

/**
 * Allowed Configuraiton value for Configuration.properties found in ${b2c.config.home} directory.
 * @author mustafa
 *
 */
public enum ConfigKeys {
	CONFIG_FILE("integrationtest.config.file"),
	ASSURANT_ESB_INBOX_LOCATION("esb.inbox.assurant"),
	HSR_ESB_INBOX_LOCATION("esb.inbox.hsr"),
	OMS_ESB_INBOX_LOCATION("esb.inbox.oms"),
    OUTPUT_ROOT_FOLDER("output.rootfolder")
    ;

    private String keyName;
    ConfigKeys(String key){
        keyName=key;
    }
    
    public String getKeyName(){
        return keyName;
    }
}
