package com.newco.marketplace.web.utils;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
/**
 * 
 * @author rjosep8 
 *
 */
public class SecurityChecker { 
    
    private static final Logger logger = Logger.getLogger(SecurityChecker.class);
    ResourceBundle resBundle = ResourceBundle.getBundle("/resources/properties/servicelive");
    String charac=resBundle.getString("invalid_Characters");       
    /**
     * Description: To sanitize the parameters
     * @param nextView      
     * @return the nextView
     */
    public String securityCheck(String nextView) {                 
           
            try{                           
                    Pattern pattern = Pattern.compile(charac,Pattern.CASE_INSENSITIVE);            
                    Matcher matcher=pattern.matcher(nextView);                     
                    nextView=matcher.replaceAll("");                                       
            }
            catch (Exception e) {
                    logger.error("SecurityChecker.java::securityCheck::Exception occured",e);
            }              
            return nextView;                       
            }
   
    public String fileNameCheck(String nextView){
            charac = resBundle.getString("invalid_file_characters");
            return securityCheck(nextView);
    }
           
    }
