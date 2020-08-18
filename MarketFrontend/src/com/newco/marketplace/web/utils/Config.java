package com.newco.marketplace.web.utils;


import java.io.IOException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.newco.marketplace.web.constants.SOConstants;
/*
 *  Config.java
 *
 *  Created on December 12, 2005, 3:55 PM
 *
 *  To change this template, choose Tools | Template Manager
 *  and open the template in the editor.
 */
/**
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/04/26 01:13:46 $
 */
public class Config {
	
	private static final Logger logger = Logger.getLogger(Config.class.getName());
    private static Properties props = new Properties();
    static {
        init();
    }
    
    /**
     * Creates a new instance of Config
     */
    private Config() { }
    
    
    /**
     *  Gets the serverLocation attribute of the Config class
     *
     * @return    The serverLocation value
     */
    public static String getServerLocation() {
        return props.getProperty("SERVER_LOCATION");
    }
    
    
    /**
     *  Gets the prop attribute of the Config class
     *
     * @param  propName  Description of the Parameter
     * @return           The prop value
     */
    public static String getProp(final String propName) {
        if (props.containsKey(propName)) {
            return props.getProperty(propName);
        }
        return null;
    }
    
    public static ResourceBundle getResouceBundle ()
    {
    	ResourceBundle newBundle = null;
    	try {
			
    		newBundle =	new PropertyResourceBundle(  Config.class.getClassLoader()
			                .getResourceAsStream(SOConstants.COMMON_CONFIG) );
    		return newBundle;
		} catch (IOException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return null;
    	
    }
        
    
    
    /**
     *  Gets the prop attribute of the Config class
     *
     * @param  propName      Description of the Parameter
     * @param  createObject  Description of the Parameter
     * @return               The prop value
     */
    public static Object getProp(final String propName, boolean createObject) {
        if (props.containsKey(propName)) {
            String clazz = props.getProperty(propName);
            
            try {
                if(createObject)
                {
                    return Class.forName(clazz).newInstance();
                }
                else
                {
                    return Class.forName(clazz);
                }
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    
   
    /**
     *  Description of the Method
     */
    private static void init() {
        try {
            props.load(
                    Config.class.getClassLoader()
                    .getResourceAsStream(SOConstants.COMMON_CONFIG)
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     *  Description of the Method
     *
     * @param  args           Description of the Parameter
     * @exception  Exception  Description of the Exception
     */
    public static void main(String args[]) throws Exception {
       //Object test = Config.getProp("MainService_Category_Validation");
        Object test = Config.getResouceBundle().getString("MainService_Category_Validation");
        System.out.println(test);
    }
}


