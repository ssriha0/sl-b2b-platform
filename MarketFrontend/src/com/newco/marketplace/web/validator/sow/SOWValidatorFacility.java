package com.newco.marketplace.web.validator.sow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;

/**
 * $Revision: 1.11 $ $Author: glacy $ $Date: 2008/04/26 01:13:52 $
 */

/*
 * Maintenance History
 * $Log: SOWValidatorFacility.java,v $
 * Revision 1.11  2008/04/26 01:13:52  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.9.28.1  2008/04/23 11:41:49  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.10  2008/04/23 05:19:57  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.9  2007/11/28 15:49:57  spannee
 * review tab additions
 *
 * Revision 1.8  2007/11/26 15:33:43  bgangaj
 * fixed --> allowing to post if warnings exists
 *
 * Revision 1.7  2007/11/16 23:05:01  spannee
 * Post SO changes
 *
 * Revision 1.6  2007/11/14 21:58:53  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */
public class SOWValidatorFacility {

	private static SOWValidatorFacility _facility = new SOWValidatorFacility();
	
	private SOWValidatorFacility(){}
	
	public static SOWValidatorFacility getInstance(){
		if(_facility == null)
		{
			_facility = new SOWValidatorFacility();
		}
		return _facility;
	}
	
	public SOWBaseTabDTO validate(SOWBaseTabDTO sowTabDTO, String incomingMode, HashMap<String, Object> tabDTOs){
		if (incomingMode!=null)
		{ 
			if (incomingMode.equals(OrderConstants.SOW_EDIT_MODE))
			{
				Set set = tabDTOs.keySet();
				Iterator iterator =(Iterator) set.iterator();
				while (iterator.hasNext()){
					String key = (String)iterator.next();
					if(key != null)
					{
						//TODO it was crashing with class cast exception with string
						if (!key.equalsIgnoreCase(OrderConstants.SO_ID))  
						{
						SOWBaseTabDTO sowBaseTabDTO = (SOWBaseTabDTO)tabDTOs.get(key);
						if(null!=sowBaseTabDTO){
						sowBaseTabDTO.validate();
						}
					}
				}
				}
			}else
			{
				if (sowTabDTO!=null) {
					sowTabDTO.validate();
				}
				
			}
		
		}
		return sowTabDTO;
	}
	public boolean isReviewTabErrors(HashMap<String, Object> tabDTOs) {
		boolean flag = false;
		ServiceOrderDTO serviceOrderDTO = (ServiceOrderDTO)tabDTOs.get(OrderConstants.SOW_REVIEW_TAB);
		List list = serviceOrderDTO.getErrors();
		if (list!=null) {
			int r = list.size();
			if (r>0){
				flag= true;
			}
		}
		return flag;
	}
	
	public boolean isErrorsWarningExist(HashMap<String, Object> tabDTOs){
		boolean flag = false;
		if(tabDTOs!=null) {
			
			Set mySet = tabDTOs.keySet();
			Iterator iterator = mySet.iterator();
			
			while (iterator.hasNext()){
				String strKey = (String)iterator.next();
				SOWBaseTabDTO dto = (SOWBaseTabDTO)tabDTOs.get(strKey);
				if ( dto!=null ) {
					List errorList =  dto.getErrorsOnly();
					List warningList = dto.getWarningsOnly();
					if (errorList==null && warningList==null)
					{
						
					}
					else{
						if (errorList.size()>0 || warningList.size()>0){
							flag = true;
							break;
						}
					}
				}
			}
			
		}
		return flag;
		
	}
	
	public boolean isErrorsExist(HashMap<String, Object> tabDTOs){
		boolean flag = false;
		if(tabDTOs!=null) {
			Set mySet = tabDTOs.keySet();
			Iterator iterator = mySet.iterator();
			while (iterator.hasNext()){
				String strKey = (String)iterator.next();
				SOWBaseTabDTO dto = (SOWBaseTabDTO)tabDTOs.get(strKey);
				if ( dto!=null ) 
				{
					List errorList =  dto.getErrorsOnly();
					if (errorList!=null && errorList.size()>0)
					{
						flag = true;
						break;
					}
				}
			}
			
		}
		return flag;
	}
}
