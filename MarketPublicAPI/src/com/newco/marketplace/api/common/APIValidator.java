package com.newco.marketplace.api.common;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.CreditCardConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;

/**
 * This is not a spring bean. Don't even try to make it.
 * @author Shekhar Nirkhe
 *
 */
public class APIValidator {
	
	private ILookupBO lookupBO;
	private static APIValidator validator;
	private Logger logger = Logger.getLogger(APIValidator.class);
	
	/**
	 * Method to validate zip code
	 * @param zip
	 * @return boolean
	 * @throws DataServiceException
	 */
	private APIValidator() {
		lookupBO = (ILookupBO)loadBeanFromContext("lookupBO");
	}
	
	public static APIValidator getInstance() {
		if (validator == null)
			validator = createInstance();
		return validator;
	}
	
	private static synchronized APIValidator createInstance()  {
		if (validator == null)
			validator = new APIValidator();
		return validator;
	}
	
	
	public boolean verifyZipCode(String zip) throws DataServiceException {
		try {
			 if(StringUtils.isEmpty(zip)) {
				 return true;
			 }
			 if (lookupBO.checkIFZipExists(zip) != null) {
				 return true;
			 }
		} catch(DataServiceException e) {
			logger.error("zip code validation failed for zip: " + zip + ", error message: " 
					+ e.getMessage());
			throw e;
		}
		return false;
	}
	
	public boolean validateSO(String soId,APIRequestVO apiVO) throws APIValidatationException {
	  boolean bool = validateSORegrep(soId);	
	  if (bool) {
		  //SL-15642: Added validation for Group Order ID.
		  /*If 'groupind' is passed as get parameter and if its value is '1' 
		   * the soId is considered as group id.*/
		  String groupInd = "";
		  //checking whether request type is get/delete
		  if(apiVO.getRequestType().toString().equals(RequestType.Get.toString()) || 
				  apiVO.getRequestType().toString().equals(RequestType.Delete.toString())){
			  Map<String, String> requestMap = apiVO.getRequestFromGetDelete();
			  if(null != requestMap && requestMap.containsKey(PublicAPIConstant.GROUP_IND_PARAM)){
					groupInd = requestMap.get(PublicAPIConstant.GROUP_IND_PARAM);
			  }
		  }
		  //checking whether request type is post/put
		  else if(apiVO.getRequestType().toString().equals(RequestType.Put.toString()) || 
				  apiVO.getRequestType().toString().equals(RequestType.Post.toString())){
			  if(null != apiVO.getProperties()){
				  Map<String, Object> reqMap = (Map<String, Object>)apiVO.getProperties().get(PublicAPIConstant.REQUEST_MAP);
				  if(null != reqMap && reqMap.containsKey(PublicAPIConstant.GROUP_IND_PARAM)){
						groupInd = reqMap.get(PublicAPIConstant.GROUP_IND_PARAM).toString();
				  } 
			  }
		  }
		 
		  com.newco.marketplace.dto.vo.serviceorder.ServiceOrder soObj = null; 
		  if(StringUtils.isNotBlank(groupInd) && PublicAPIConstant.GROUPED_SO_IND.equalsIgnoreCase(groupInd)){
			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder so = BaseService.getGroupedSO(soId);
			 if(null != so){
				 soObj = so;
			 }//Else soObl will be null by default
		  }else{
			  soObj = BaseService.getSO(soId);
		  }
		  if (soObj == null) {			 
			  String msg = "SO Id not found";
			  throw new APIValidatationException(msg);
		  }
		  else if(null!=apiVO.getBuyerIdInteger()&&!apiVO.getBuyerIdInteger().equals(soObj.getBuyer().getBuyerId()))
		  {
			  String errorCode = ResultsCode.NOT_AUTHORISED_BUYER_ID.getCode();
			  String msg = "Buyer not authorised to view the SO Details";
			  throw new APIValidatationException(errorCode, msg);
			  
		  } else if(null!=apiVO.getProviderIdInteger()){
			  int stateId=soObj.getWfStateId();
			  //SL-15642 : For provider Expired status is also unauthorized.
			  if(stateId==OrderConstants.DRAFT_STATUS ||stateId==OrderConstants.EXPIRED_STATUS){
				  String errorCode = ResultsCode.NOT_AUTHORISED_BUYER_ID.getCode();
				  String msg = "Provider not authorised to view the SO Details";
				  throw new APIValidatationException(errorCode, msg);
			  }else if(stateId==OrderConstants.ROUTED_STATUS){
				  boolean isAuthorizedProvider=false;
				  Iterator<RoutedProvider> iterator = soObj.getRoutedResources().iterator();
				  while ( iterator.hasNext() ){
				      if(iterator.next().getVendorId().toString().equalsIgnoreCase(apiVO.getProviderId()))
				    	  {
				    	  isAuthorizedProvider=true;
				    	  break;
				    	  }
				  }
				  if(!isAuthorizedProvider){
					  String errorCode = ResultsCode.NOT_AUTHORISED_BUYER_ID.getCode();
					  String msg = "Provider not authorised to view the SO Details";
					  throw new APIValidatationException(errorCode, msg);
				  }
				  
			  }else if(stateId==OrderConstants.ACCEPTED_STATUS||stateId==OrderConstants.CLOSED_STATUS||
					  stateId==OrderConstants.CANCELLED_STATUS||stateId==OrderConstants.ACTIVE_STATUS||stateId==OrderConstants.COMPLETED_STATUS
					  ||stateId==OrderConstants.PROBLEM_STATUS){
				  if(!(soObj.getAcceptedVendorId().equals(apiVO.getProviderIdInteger()))){
					  String errorCode = ResultsCode.NOT_AUTHORISED_BUYER_ID.getCode();
					  String msg = "Provider not authorised to view the SO Details";
					  throw new APIValidatationException(errorCode, msg);
					  
				  }
			  }
		  }
		  		  
	  } else {
		  String errorCode = ResultsCode.INVALID_OR_MISSING_PARAM.getCode();
		  String msg = "Invalid SO Id";
		  throw new APIValidatationException(errorCode, msg);
	  }
	  
	  return true;
	}
	
	public boolean validateSOGroup(String groupId,APIRequestVO apiVO) throws APIValidatationException {
		  boolean bool = validateSORegrep(groupId);	
		  if (bool) {
			  com.newco.marketplace.dto.vo.serviceorder.ServiceOrder soObj = BaseService.getSO(groupId);
			  if (soObj == null) {			 
				  String msg = "SO Id not found";
				  throw new APIValidatationException(msg);
			  }
			  else if(null!=apiVO.getBuyerIdInteger()&&!apiVO.getBuyerIdInteger().equals(soObj.getBuyer().getBuyerId()))
			  {
				  String errorCode = ResultsCode.NOT_AUTHORISED_BUYER_ID.getCode();
				  String msg = "Buyer not authorised to view the SO Details";
				  throw new APIValidatationException(errorCode, msg);
				  
			  } else if(null!=apiVO.getProviderIdInteger()){
				  int stateId=soObj.getWfStateId();
			  
				  if(stateId==OrderConstants.DRAFT_STATUS){
					  String errorCode = ResultsCode.NOT_AUTHORISED_BUYER_ID.getCode();
					  String msg = "Provider not authorised to view the SO Details";
					  throw new APIValidatationException(errorCode, msg);
				  }else if(stateId==OrderConstants.ROUTED_STATUS){
					  boolean isAuthorizedProvider=false;
					  Iterator<RoutedProvider> iterator = soObj.getRoutedResources().iterator();
					  while ( iterator.hasNext() ){
					      if(iterator.next().getVendorId().toString().equalsIgnoreCase(apiVO.getProviderId()))
					    	  {
					    	  isAuthorizedProvider=true;
					    	  break;
					    	  }
					  }
					  if(!isAuthorizedProvider){
						  String errorCode = ResultsCode.NOT_AUTHORISED_BUYER_ID.getCode();
						  String msg = "Provider not authorised to view the SO Details";
						  throw new APIValidatationException(errorCode, msg);
					  }
					  
				  }else if(stateId==OrderConstants.ACCEPTED_STATUS||stateId==OrderConstants.EXPIRED_STATUS||stateId==OrderConstants.CLOSED_STATUS||
						  stateId==OrderConstants.CANCELLED_STATUS||stateId==OrderConstants.ACTIVE_STATUS||stateId==OrderConstants.COMPLETED_STATUS
						  ||stateId==OrderConstants.PROBLEM_STATUS){
					  if(!(soObj.getAcceptedVendorId().equals(apiVO.getProviderIdInteger()))){
						  String errorCode = ResultsCode.NOT_AUTHORISED_BUYER_ID.getCode();
						  String msg = "Provider not authorised to view the SO Details";
						  throw new APIValidatationException(errorCode, msg);
						  
					  }
				  }
			  }
			  		  
		  } else {
			  String errorCode = ResultsCode.INVALID_OR_MISSING_PARAM.getCode();
			  String msg = "Invalid SO Id";
			  throw new APIValidatationException(errorCode, msg);
		  }
		  
		  return true;
		}
	
	private boolean validateSORegrep(String so) {
		if (so == null) return false;
		Pattern p = Pattern.compile("\\d{3}-\\d{4}-\\d{4}-\\d{2}");		
		Matcher m = p.matcher(so);
		boolean b = m.matches();
		return b;	
	}
	
	protected Object loadBeanFromContext(String beanName) {		
		return MPSpringLoaderPlugIn.getCtx().getBean(beanName);
		
	}
	
	public boolean validateEntity(APIRequestVO apiVO) throws APIValidatationException {
		if(null!= apiVO.getProviderFirmId())
		{
			 Integer firmId = APIRequestVO.StringToInt(apiVO.getProviderFirmId());
			 if(null!=firmId)
			 {
				 boolean firmExist=BaseService.isProviderExist(firmId);
				 if(firmExist == false)
				 {
					 String errorString = ResultsCode.INVALID_FIRMID.getMessage();
					 String errorCode = ResultsCode.INVALID_FIRMID.getCode();
					 throw new APIValidatationException (errorCode, errorString);
				 }
			 } 
		}
		
		if (validateBuyer(apiVO) == true) {
			return validateProvider(apiVO);
		} else {
			return false;
		}
	}
	
	public boolean validateBuyer(APIRequestVO apiVO) throws APIValidatationException {
		Integer entityId  = apiVO.getBuyerIdInteger();
		if (entityId != null) {
			validateAndVerifyEntityId(entityId, "BUYER");			
			Integer resourceId = apiVO.getBuyerResourceIdInteger();	
			
			if (resourceId != null) {
				validateAndVerifyResourceId(resourceId, "BUYER");
				validateBuyerLink(apiVO);
			}
		}
		return true;
	}
	
	public boolean validateProvider(APIRequestVO apiVO) throws APIValidatationException {
		Integer entityId  = apiVO.getProviderIdInteger();
		Integer resourceID = apiVO.getProviderResourceId();
		if (entityId != null) {
			validateAndVerifyEntityId(entityId, "PROVIDER");			
			Integer resourceId = apiVO.getProviderResourceId();	
			
			if (resourceId != null) {
				validateAndVerifyResourceId(resourceId, "PROVIDER");
				validateProviderLink(apiVO);
			}
		}
		if (resourceID != null) {
			validateAndVerifyResourceId(resourceID, "PROVIDER");
			
		}
		return true;
	}
	
	
	protected boolean validateAndVerifyEntityId(Integer entityId, String type) throws APIValidatationException {	
		if (entityId != null) {		// It could be null as it might not be applicable for some URLs.	
			if (entityId == -1) {
				String errorCode = ResultsCode.INVALID_OR_MISSING_PARAM.getCode();
				throw new APIValidatationException(errorCode, "Invalid " +  type + " Id");		
			}

			boolean flag = false;
			if (type.equalsIgnoreCase("BUYER")) {
				flag = BaseService.isBuyerExist(entityId);
			} else {
				flag = BaseService.isProviderExist(entityId);
			}

			if (flag == false) {
				throw new APIValidatationException(type + " Id does not exist");		
			}		
		}

		return true;
	}
	
	
	protected boolean validateAndVerifyResourceId(Integer resourceId, String type) throws APIValidatationException {		
		if (resourceId != null) { // It could be null as it might not be applicable for some URLs.
			if (resourceId == -1) {
				String errorString = "Invalid " + type +" Resource Id";
				String errorCode = ResultsCode.INVALID_OR_MISSING_PARAM.getCode();
				throw new APIValidatationException (errorCode, errorString);
			}

			SecurityContext obj = null;
			if (type.equalsIgnoreCase("BUYER")) {
				obj = BaseService.getSecurityContextForBuyer(resourceId);
			} else {
				obj = BaseService.getSecurityContextForVendor(resourceId);
			}

			if (obj == null) {
				throw new APIValidatationException(type + " Resource Id does not exist");		
			}
		}
		return true;
	}
	
	protected boolean validateBuyerLink(APIRequestVO apiVO) throws APIValidatationException {
		Integer receivedBuyerResourceId = apiVO.getBuyerResourceIdInteger();
		SecurityContext securityContext = BaseService.getSecurityContextForBuyer(apiVO.getBuyerResourceIdInteger());
		Integer buyerId = securityContext.getCompanyId();	
		Integer receivedBuyerId =  apiVO.getBuyerIdInteger();
		
		if (receivedBuyerId.intValue() != buyerId.intValue()) {
			String errorString  = "Buyer Id " + receivedBuyerId + " is not associated with resource " 
						+  receivedBuyerResourceId;
			String errorCode = ResultsCode.INVALIDXML_ERROR_CODE.getCode();
			throw new APIValidatationException (errorCode, errorString);			
		}		
		return true;
	}
	

	
	protected boolean validateProviderLink(APIRequestVO apiVO) throws APIValidatationException {
		Integer receivedProviderResourceId = apiVO.getProviderResourceId();
		SecurityContext securityContext = BaseService.getSecurityContextForVendor(receivedProviderResourceId);
		Integer buyerId = securityContext.getCompanyId();	
		Integer receivedProviderId =  apiVO.getProviderIdInteger();
		
		if (receivedProviderId.intValue() != buyerId.intValue()) {
			String errorString  = "Provider Id " + receivedProviderId + " is not associated with resource " 
						+  receivedProviderResourceId;
			String errorCode = ResultsCode.INVALIDXML_ERROR_CODE.getCode();
			throw new APIValidatationException (errorCode, errorString);			
		}		
		return true;
	}
	
	
	public boolean validateCreditCard(CreditCardVO creditCardVO) throws APIValidatationException {
		String errorString, errorCode;

		boolean formatError = false;

		if (StringUtils.isBlank(creditCardVO.getCardHolderName())) {
			errorString = ResultsCode.CARDHOLDERNAME_EMPTY.getMessage() ;
			errorCode = ResultsCode.CARDHOLDERNAME_EMPTY.getCode();
			throw new APIValidatationException(errorCode, errorString);

		} else if (creditCardVO.getCardHolderName().length() > 75) {
			errorString = ResultsCode.CARDHOLDERNAME_TOO_LONG.getMessage();
			errorCode = ResultsCode.CARDHOLDERNAME_TOO_LONG.getCode();
			throw new APIValidatationException(errorCode, errorString);			
		}

		if(StringUtils.isBlank(creditCardVO.getCardNo())){
			errorString = ResultsCode.CARDNUMBER_EMPTY.getMessage();
			errorCode = ResultsCode.CARDNUMBER_EMPTY.getCode();
			throw new APIValidatationException(errorCode, errorString);	
		}


		checkCardId(creditCardVO);		
		checkBillingAddress(creditCardVO.getBillingAddress1());


		if (StringUtils.isBlank(creditCardVO.getBillingCity())) {
			errorString = ResultsCode.BILLING_CITY_EMPTY_ERROR.getMessage();
			errorCode = ResultsCode.BILLING_CITY_EMPTY_ERROR.getCode();
			throw new APIValidatationException(errorCode, errorString);			
		}

		if (creditCardVO.getBillingCity().length() > 30) {
			errorString = ResultsCode.BILLING_CITY_LONG_ERROR.getMessage();
			errorCode = ResultsCode.BILLING_CITY_LONG_ERROR.getCode();
			throw new APIValidatationException(errorCode, errorString);		
		}

		if (StringUtils.isBlank(creditCardVO.getBillingState())) {
			errorString = ResultsCode.BILLING_STATE_EMPTY_ERROR.getMessage();
			errorCode = ResultsCode.BILLING_STATE_EMPTY_ERROR.getCode();
			throw new APIValidatationException(errorCode, errorString);		
		}

		if (StringUtils.isNotBlank(creditCardVO.getZipcode())) {
			boolean valResult = validateUSZIP(creditCardVO.getZipcode());		
			
			if (valResult == false) {
				errorString = ResultsCode.ZIP_CODE_INVALID.getMessage();
				errorCode = ResultsCode.ZIP_CODE_INVALID.getCode();
				throw new APIValidatationException(errorCode, errorString);		
			}
		} else {
			errorString = ResultsCode.ZIP_CODE_EMPTY_ERROR.getMessage();
			errorCode = ResultsCode.ZIP_CODE_EMPTY_ERROR.getCode();
			throw new APIValidatationException(errorCode, errorString);		
		}


		int zipCheck = LocationUtils.checkIfZipAndStateValid(creditCardVO.getZipcode(),creditCardVO.getBillingState());
		switch (zipCheck) {
		case Constants.LocationConstants.ZIP_NOT_VALID:
			errorString = ResultsCode.ZIP_CODE_INVALID.getMessage();
			errorCode = ResultsCode.ZIP_CODE_INVALID.getCode();
			throw new APIValidatationException(errorCode, errorString);		
		case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
			errorString = ResultsCode.ZIP_STATE_NO_MATCH.getMessage();
			errorCode = ResultsCode.ZIP_STATE_NO_MATCH.getCode();
			throw new APIValidatationException(errorCode, errorString);		
		}

		return true;
	}
	
	public boolean validateUSZIP(String zip) {
		if (zip == null)
			return false;
		boolean valResult = false;
		String numPattern = "(\\d{5})";
		valResult = zip.matches(numPattern);
		return valResult;
	}

	private void checkBillingAddress(String address) throws APIValidatationException {
		int billLength = 0;

		if (StringUtils.isNotBlank(address)) {
			billLength = address.length();
		} 

		if (billLength < 1) {
			String errorString = ResultsCode.BILLING_ADDRESS_EMPTY.getMessage();
			String errorCode = ResultsCode.BILLING_ADDRESS_EMPTY.getCode();
			throw new APIValidatationException(errorCode, errorString);			

		}

		if (billLength > 30) {
			String errorString = ResultsCode.BILLING_ADDRESS_LENGTH.getMessage();
			String errorCode = ResultsCode.BILLING_ADDRESS_LENGTH.getCode();
			throw new APIValidatationException(errorCode, errorString);			
		}
	}


	private void checkCardId(CreditCardVO creditCardVO) throws APIValidatationException {		
		CreditCardType cardId = CreditCardType.getCardType(creditCardVO.getCardTypeId());

		switch (cardId) {
		case VISA:
			if ((creditCardVO.getCardNo().length() != 16) || Integer.parseInt(creditCardVO.getCardNo().substring(0, 1)) != 4) {
				String errorString = ResultsCode.CREDIT_CARD_INVALID.getMessage();
				String errorCode = ResultsCode.CREDIT_CARD_INVALID.getCode();
				throw new APIValidatationException(errorCode, errorString);		
			}
			break;
		case MASTER_CARD:
			if (creditCardVO.getCardNo().length() != 16
					|| Integer.parseInt(creditCardVO.getCardNo().substring(0, 2)) < 51
					|| Integer.parseInt(creditCardVO.getCardNo().substring(0, 2)) > 55
					|| checkForSearsMasterCard(creditCardVO)) {
				String errorString = ResultsCode.CREDIT_CARD_INVALID.getMessage();
				String errorCode = ResultsCode.CREDIT_CARD_INVALID.getCode();
				throw new APIValidatationException(errorCode, errorString);	
			}
			break;
		case SEARS_MASTER_CARD:
			if (!checkForSearsMasterCard(creditCardVO)) {
				String errorString = ResultsCode.CREDIT_CARD_INVALID.getMessage();
				String errorCode = ResultsCode.CREDIT_CARD_INVALID.getCode();
				throw new APIValidatationException(errorCode, errorString);
			}
			break;
			//SLT-2591 and SLT-2592: Disable Amex
		/*case AMEX:
			if (creditCardVO.getCardNo().length() != 15
					|| (Integer.parseInt(creditCardVO.getCardNo().substring(0, 2)) != 34 && Integer
							.parseInt(creditCardVO.getCardNo().substring(0, 2)) != 37)) {
				String errorString = ResultsCode.CREDIT_CARD_INVALID.getMessage();
				String errorCode = ResultsCode.CREDIT_CARD_INVALID.getCode();
				throw new APIValidatationException(errorCode, errorString);					
			}
			break;*/
		default:
			if (creditCardVO.getCardNo().length() != 16
					&& creditCardVO.getCardNo().length() != 13) {
				String errorString = ResultsCode.CREDIT_CARD_INVALID.getMessage();
				String errorCode = ResultsCode.CREDIT_CARD_INVALID.getCode();
				throw new APIValidatationException(errorCode, errorString);	
			}

		}

	}
	
	private boolean checkForSearsMasterCard(CreditCardVO creditCardVO) {
		if (creditCardVO.getCardNo().length()== 16 )//Sears Master card has card type id of 4
		{
			String searsMasterCardInitial6digits[] = CreditCardConstants.SEARS_MASTER_CARD_BIN_RANGE;
			String cardInitialSixDigits = creditCardVO.getCardNo().substring(0, 6);
			for (int i = 0; i < searsMasterCardInitial6digits.length;i++)
			{
				if (StringUtils.equals(cardInitialSixDigits,searsMasterCardInitial6digits[i]))
				{
					return true;
				}
			}
		}
		return false;
	}
}
