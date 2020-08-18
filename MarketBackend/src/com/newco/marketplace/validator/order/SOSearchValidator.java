package com.newco.marketplace.validator.order;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;


public class SOSearchValidator extends ABaseValidator implements OrderConstants{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2529697370268560572L;
	private static final Logger logger = Logger.getLogger(SOSearchValidator.class.getName());
	
	public ValidatorResponse validate(Integer searchType, String searchValue) throws Exception {
		
		ValidatorResponse resp = new ValidatorResponse(VALID_RC, VALID_RC);
		
		logger.debug("Validating SO Search parameters "+searchType+" and "+searchValue);
		
		if(StringUtils.isEmpty(searchValue))
		{
			setError(resp,SEARCH_BY_VALUE_REQUIRED );
			return resp;
		}
		
		boolean isTrue = false;
		switch(searchType.intValue())
		{
			case 1:	isTrue = isNumeric(searchValue);
					if(!isTrue)
					{
						setError(resp,SEARCH_BY_PHONE_NUMBER_VALUE_NOT_VALID );
						return resp;
					}
					if(searchValue.trim().length() > 10 || searchValue.trim().length() < 10)
					{
						setError(resp,SEARCH_BY_PHONE_NUMBER_LENGTH_NOT_VALID );
						return resp;
					}
					break;
			case 2:	isTrue = isNumeric(searchValue);
					if(!isTrue)
					{
						setError(resp,SEARCH_BY_ZIP_CODE_VALUE_NOT_VALID );
						return resp;
					}
					if(searchValue.trim().length() > 5 || searchValue.trim().length() < 5)
					{
						setError(resp,SEARCH_BY_ZIP_CODE_LENGTH_NOT_VALID );
						return resp;
					}
					break;
			case 3: // TODO:: Validate SO ID
					// resp = new CancelSOValidator().validate(searchValue);
										
					break;
			case 4: isTrue = isAlphabetic(searchValue);
					if(!isTrue)
					{
						setError(resp,SEARCH_BY_END_USER_NAME_VALUE_NOT_VALID );
						return resp;
					}
					break;
			case 5: isTrue = isNumeric(searchValue);
					if(!isTrue)
					{
						setError(resp,SEARCH_BY_TECHNICIAN_VALUE_NOT_VALID );
						return resp;
					}
					break;
			case 6: isTrue = isAlphabetic(searchValue);
					if(!isTrue)
					{
						setError(resp,SEARCH_BY_TECHNICIAN_NAME_VALUE_NOT_VALID );
						return resp;
					}
					break;
			case 8: isTrue = isNumeric(searchValue);
					if(!isTrue)
					{
						setError(resp,SEARCH_BY_PROVIDER_FIRM_ID_NOT_VALID);
						return resp;
					}
					break;		
			default: 
					break;
		}
					
		return resp;
	}
	
	
	public ValidatorResponse validatePbFilterId(Integer pbfilterId)
	{
		
		ValidatorResponse resp = new ValidatorResponse(VALID_RC, VALID_RC);
	  if(pbfilterId ==null)
	  {
		  setError(resp,FILTER_ID_IS_NOT_SET);
			return resp;
	  }
		
	 return resp;
	}
	
		
	
	
	private boolean isNumeric(String searchValue)
	{
		searchValue = searchValue.trim();

		for (int i = 0; i < searchValue.length(); i++) {
			if (!Character.isDigit(searchValue.charAt(i)))
				return false;
		}
		return true;
	}
		
	private boolean isAlphabetic(String searchValue)
	{
		searchValue = searchValue.trim();
		
		for (int i = 0; i < searchValue.length(); i++) {
			if (!Character.isLetter(searchValue.charAt(i)) && !Character.isSpaceChar(searchValue.charAt(i)))
				return false;
		}
		return true;
	}
}
