package com.newco.marketplace.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.interfaces.OrderConstants;

public class LocationUtils {
	
	public static final Logger logger = Logger.getLogger(LocationUtils.class);
	
	public static int checkIfZipAndStateValid(String zip,String state){		
		LocationVO locationVo = null;
		int result = Constants.LocationConstants.ZIP_VALID;
		try {
			if(zip != null && zip.trim().length() == 5) {
				ILookupBO lookupBO = (ILookupBO)MPSpringLoaderPlugIn.getCtx().getBean("lookupBO");
				locationVo = lookupBO.checkIFZipExists(zip);

				// No return, invalid
				if(locationVo == null && zip != null){
					result = Constants.LocationConstants.ZIP_NOT_VALID;
				}
				// Length of zip is not 5, invalid
				if(locationVo != null && 
						(locationVo.getZip() == null || locationVo.getZip().length() != 5)){
					result = Constants.LocationConstants.ZIP_NOT_VALID;
				}
				// If state returned does not match passed in state, invalid
				if(StringUtils.isNotEmpty(state) && !state.equals("-1")  
						&& locationVo != null && locationVo.getState() != null
						&& !locationVo.getState().equalsIgnoreCase(state)){
					result = Constants.LocationConstants.ZIP_STATE_NO_MATCH;
				}						
			}
		} catch (Exception e) {
			logger.info("Unable to validate state and zip code",e);
			result = Constants.LocationConstants.ZIP_NOT_VALID;
		}	
		return result;
	}
	
	
	public static String getTimeZone(String zip){		
		String timeZone = OrderConstants.EST_ZONE;
		try {
			if(zip != null && zip.trim().length() == 5) {
				ILookupBO lookupBO = (ILookupBO)MPSpringLoaderPlugIn.getCtx().getBean("lookupBO");
				timeZone = lookupBO.getZipTimezone(zip);	
			}
		} catch (Exception e) {
			logger.info("Unable to validate state and zip code",e);
		}	
		return timeZone;
	}
}
