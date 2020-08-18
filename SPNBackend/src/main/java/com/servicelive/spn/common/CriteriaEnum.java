/**
 * 
 */
package com.servicelive.spn.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hoza
 *
 */
public enum CriteriaEnum {
	 /** */ENUM_CRITERIA_MAIN_SERVICES 					(SPNBackendConstants.CRITERIA_MAIN_SERVICES ),              
	 /** */ENUM_CRITERIA_SKILLS							(SPNBackendConstants.CRITERIA_SKILLS ),                        
	 /** */ENUM_CRITERIA_CATEGORY						(SPNBackendConstants.CRITERIA_CATEGORY ),                     
	 /** */ENUM_CRITERIA_SUB_CATEGORY 					(SPNBackendConstants.CRITERIA_SUB_CATEGORY),                  
	 /** */ENUM_CRITERIA_MINIMUM_RATING					(SPNBackendConstants.CRITERIA_MINIMUM_RATING),                
	 /** */ENUM_CRITERIA_LANGUAGE						(SPNBackendConstants.CRITERIA_LANGUAGE),                      
	 /** */ENUM_CRITERIA_MINIMUM_SO_COMPLETED         	(SPNBackendConstants.CRITERIA_MINIMUM_SO_COMPLETED ),         
	 /** */ENUM_CRITERIA_AUTO_LIABILITY_AMT           	(SPNBackendConstants.CRITERIA_AUTO_LIABILITY_AMT ),           
	 /** */ENUM_CRITERIA_AUTO_LIABILITY_VERIFIED      	(SPNBackendConstants.CRITERIA_AUTO_LIABILITY_VERIFIED),       
	 /** */ENUM_CRITERIA_WC_LIABILITY_VERIFIED        	(SPNBackendConstants.CRITERIA_WC_LIABILITY_VERIFIED),         
	 /** */ENUM_CRITERIA_COMMERCIAL_LIABILITY_AMT     	(SPNBackendConstants.CRITERIA_COMMERCIAL_LIABILITY_AMT),      
	 /** */ENUM_CRITERIA_COMMERCIAL_LIABILITY_VERIFIED	(SPNBackendConstants.CRITERIA_COMMERCIAL_LIABILITY_VERIFIED ),
	 /** */ENUM_CRITERIA_COMPANY_CRED                 	(SPNBackendConstants.CRITERIA_COMPANY_CRED ),                 
	 /** */ENUM_CRITERIA_COMPANY_CRED_CATEGORY        	(SPNBackendConstants.CRITERIA_COMPANY_CRED_CATEGORY),         
	 /** */ENUM_CRITERIA_COMPANY_CRED_VERIFIED        	(SPNBackendConstants.CRITERIA_COMPANY_CRED_VERIFIED),         
	 /** */ENUM_CRITERIA_SP_CRED                      	(SPNBackendConstants.CRITERIA_SP_CRED),                       
	 /** */ENUM_CRITERIA_SP_CRED_CATEGORY             	(SPNBackendConstants.CRITERIA_SP_CRED_CATEGORY ),             
	 /** */ENUM_CRITERIA_SP_CRED_VERIFIED             	(SPNBackendConstants.CRITERIA_SP_CRED_VERIFIED),              
	 /** */ENUM_CRITERIA_MEETING_REQUIRED             	(SPNBackendConstants.CRITERIA_MEETING_REQUIRED),              
	 /** */ENUM_CRITERIA_MARKET                       	(SPNBackendConstants.CRITERIA_MARKET),                        
	 /** */ENUM_CRITERIA_STATE                        	(SPNBackendConstants.CRITERIA_STATE),                         
	 /** */ENUM_CRITERIA_COMPANY_SIZE                 	(SPNBackendConstants.CRITERIA_COMPANY_SIZE),                  
	 /** */ENUM_CRITERIA_SALES_VOLUME                 	(SPNBackendConstants.CRITERIA_SALES_VOLUME),     
	 /** */ENUM_CRITERIA_MARKET_ALL                     (SPNBackendConstants.CRITERIA_MARKET_ALL),                        
	 /** */ENUM_CRITERIA_STATE_ALL                      (SPNBackendConstants.CRITERIA_STATE_ALL),     
	 /** */ENUM_CRITERIA_MINIMUM_RATING_NOTRATED		(SPNBackendConstants.CRITERIA_MINIMUM_RATING_NOTRATED),
	 /** */ENUM_CRITERIA_WC_LIABILITY_SELECTED        	(SPNBackendConstants.CRITERIA_WC_LIABILITY_SELECTED), 
	 /** */ENUM_CRITERIA_NONE							("NONE"),
	 //R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
	 //Add new Enum attribute to hold the primary industry value for TRG_SPN_CAM_INV batch
	 /** */ENUM_CRITERIA_PRIMARY_INDUSTRY				(SPNBackendConstants.CRITERIA_PRIMARY_INDUSTRY),
	 /*R11.0* SL-19387/
	 /** */ENUM_CRITERIA_BACKGROUND_CHECK 				("Background check - 2 year recertification");
	 
	 
	 private static final Map<String,CriteriaEnum> lookup 
     = new HashMap<String,CriteriaEnum>();

	static {
	     for(CriteriaEnum c : EnumSet.allOf(CriteriaEnum.class))
	          lookup.put(c.getDescription(), c);
	}
	 
	 private final String criteriaDescription;
	 CriteriaEnum(String desc) {
	        this.criteriaDescription = desc;
	    }
	 /**
	  * 
	  * @return String
	  */
	 public String getDescription() {
		 return criteriaDescription;
	 }
	 /**
	  * 
	  * @param str
	  * @return CriteriaEnum
	  */
	 public static CriteriaEnum toCriteriaEnum(String str)
	    {
	        try {
	        	return lookup.get(str);
	        } 
	        catch (Exception ex) {
	        	return ENUM_CRITERIA_NONE;
	        }
	    }   
}
