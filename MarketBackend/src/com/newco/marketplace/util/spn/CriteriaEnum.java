/**
 * 
 */
package com.newco.marketplace.util.spn;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.newco.marketplace.interfaces.SPNConstants;

/**
 * @author hoza
 *
 */
public enum CriteriaEnum { 
	 /** */ENUM_CRITERIA_MAIN_SERVICES 					(SPNConstants.CRITERIA_MAIN_SERVICES ),              
	 /** */ENUM_CRITERIA_SKILLS							(SPNConstants.CRITERIA_SKILLS ),                        
	 /** */ENUM_CRITERIA_CATEGORY						(SPNConstants.CRITERIA_CATEGORY ),                     
	 /** */ENUM_CRITERIA_SUB_CATEGORY 					(SPNConstants.CRITERIA_SUB_CATEGORY),                  
	 /** */ENUM_CRITERIA_MINIMUM_RATING					(SPNConstants.CRITERIA_MINIMUM_RATING),                
	 /** */ENUM_CRITERIA_LANGUAGE						(SPNConstants.CRITERIA_LANGUAGE),                      
	 /** */ENUM_CRITERIA_MINIMUM_SO_COMPLETED         	(SPNConstants.CRITERIA_MINIMUM_SO_COMPLETED ),         
	 /** */ENUM_CRITERIA_AUTO_LIABILITY_AMT           	(SPNConstants.CRITERIA_AUTO_LIABILITY_AMT ),           
	 /** */ENUM_CRITERIA_AUTO_LIABILITY_VERIFIED      	(SPNConstants.CRITERIA_AUTO_LIABILITY_VERIFIED),       
	 /** */ENUM_CRITERIA_WC_LIABILITY_VERIFIED        	(SPNConstants.CRITERIA_WC_LIABILITY_VERIFIED),         
	 /** */ENUM_CRITERIA_COMMERCIAL_LIABILITY_AMT     	(SPNConstants.CRITERIA_COMMERCIAL_LIABILITY_AMT),      
	 /** */ENUM_CRITERIA_COMMERCIAL_LIABILITY_VERIFIED	(SPNConstants.CRITERIA_COMMERCIAL_LIABILITY_VERIFIED ),
	 /** */ENUM_CRITERIA_COMPANY_CRED                 	(SPNConstants.CRITERIA_COMPANY_CRED ),                 
	 /** */ENUM_CRITERIA_COMPANY_CRED_CATEGORY        	(SPNConstants.CRITERIA_COMPANY_CRED_CATEGORY),         
	 /** */ENUM_CRITERIA_COMPANY_CRED_VERIFIED        	(SPNConstants.CRITERIA_COMPANY_CRED_VERIFIED),         
	 /** */ENUM_CRITERIA_SP_CRED                      	(SPNConstants.CRITERIA_SP_CRED),                       
	 /** */ENUM_CRITERIA_SP_CRED_CATEGORY             	(SPNConstants.CRITERIA_SP_CRED_CATEGORY ),             
	 /** */ENUM_CRITERIA_SP_CRED_VERIFIED             	(SPNConstants.CRITERIA_SP_CRED_VERIFIED),              
	 /** */ENUM_CRITERIA_MEETING_REQUIRED             	(SPNConstants.CRITERIA_MEETING_REQUIRED),              
	 /** */ENUM_CRITERIA_MARKET                       	(SPNConstants.CRITERIA_MARKET),                        
	 /** */ENUM_CRITERIA_STATE                        	(SPNConstants.CRITERIA_STATE),                         
	 /** */ENUM_CRITERIA_COMPANY_SIZE                 	(SPNConstants.CRITERIA_COMPANY_SIZE),                  
	 /** */ENUM_CRITERIA_SALES_VOLUME                 	(SPNConstants.CRITERIA_SALES_VOLUME),     
	 /** */ENUM_CRITERIA_MARKET_ALL                     (SPNConstants.CRITERIA_MARKET_ALL),                        
	 /** */ENUM_CRITERIA_STATE_ALL                      (SPNConstants.CRITERIA_STATE_ALL),     
	 /** */ENUM_CRITERIA_MINIMUM_RATING_NOTRATED		(SPNConstants.CRITERIA_MINIMUM_RATING_NOTRATED),
	 /** */ENUM_CRITERIA_NONE							("NONE"); 
	 
	 
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
