package com.servicelive.wallet.creditcard;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.common.BusinessServiceException;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.properties.ApplicationProperties;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.wallet.creditcard.dao.ICreditCardDao;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

// TODO: Auto-generated Javadoc
/**
 * Class HSAuthServiceCreditCardBO.
 */
public  class HSAuthServiceCreditCardBO implements IHSAuthServiceCreditCardBO {

	/** logger. */
	private static final Logger LOGGER = Logger.getLogger(HSAuthServiceCreditCardBO.class.getName());

	/** applicationProperties. */
	private ApplicationProperties applicationProperties;

	/** creditCardAuthUrl. */
	private String creditCardAuthUrl;
	
	/** creditCardDao. */
	protected ICreditCardDao creditCardDao;
 
	/** slStoreNo. */
	private String slStoreNo;

	/** slStoreNoWOZero. */
	private String slStoreNoWOZero;
 
	/** Minimum auth amount. */
	private String minAuthAmount;
	
	//PCI Vault
	private HSAuthServiceClient hsAuthServiceClient; 
	
	/** creditCardAuthHeader. */
	private String creditCardAuthHeader;
	
	/** creditCardRefundUrl. */
	private String creditCardRefundUrl;
	
	/** creditCardRefundHeader. */
	private String creditCardRefundHeader;
	
	/**
	 * Method to authorize Credit card while adding credit card and while adding fund
	 * @param cc 
	 * @param userName 
	 * @return SLCreditCardVO
	 * @throws SLBusinessServiceException 
	 */
	public SLCreditCardVO authorizeHSSTransaction(SLCreditCardVO cc,String userName) throws SLBusinessServiceException {
		SLCreditCardVO response = null;
		SLCreditCardVO details= null;
		HSSResponse hssResp = null;
		String xml =null;
		boolean updatable = false;
		try{
		// the response to return to caller
		 response = SLCreditCardVO.copy(cc); 
		// the details to be persisted
		 details = SLCreditCardVO.copy(cc);
		}catch (Exception e) {
			unhandledException(cc, e);
			throw new SLBusinessServiceException("Failed to invoke authorization service: "+ e);
		}
		details.setTransIdentifier(CommonConstants.AUTHORIZATION);
		details.setTransactionAmount(CommonConstants.TRANS_AMOUNT);
		if (cc.isDollarAuth()){
			cc.setHsTransAmount(getMinAuthAmount()+CommonConstants.TRANS_AMOUNT_CARD_ADD);
			details.setTransIdentifier(CommonConstants.VALIDATION);
		}
		//Method to format the amount last two places for decimal.
		else{
			String transAmount = CommonConstants.TRANS_AMOUNT_CARD_ADD;
			if(null!= cc.getTransactionAmount()){
				transAmount = String.format("%.2f", cc.getTransactionAmount());
				if(StringUtils.contains(transAmount, CommonConstants.DECIMAL_POINT)){
					transAmount = StringUtils.remove(transAmount, '.');
				}
			}//Check to make sure the amount is Numeric
			if(StringUtils.isNumeric(transAmount)){
				cc.setHsTransAmount(transAmount);
			}
			
		}

		try {

			// 1. create the request object
			CreditAuthRequest hssReq = createHSSAuthRequest(cc);
			try {
				// 2. invoke the authorization web service
				hssResp = invokeAuthRequest(hssReq,userName);
			} catch (Exception e) {
				// handle ws failures
				handleAuthException(cc, e);
				throw e;
			}
			 if(null!= hssResp){
					if (isAuthorized(hssResp)) {
						// 3. if authroized, update the vo's
						updateAuthResponse(response, hssResp);
						//check token or masked account no is null or not
						if(!cc.isDollarAuth() && (StringUtils.isBlank(details.getMaskedAccount()) || StringUtils.isBlank(details.getToken()))){
							updatable =true;
						}

						setAuthRespDetails(details, hssResp,userName);
						details.setAuthorized(true);
					} else {
						// 4. if not authorized, update the vo's
						updateNoAuthResponse(response, hssResp);
						setAuthRespDetails(details, hssResp,userName);
						details.setAuthorized(false);
					}
					if(null!=hssResp && StringUtils.isNotBlank(hssResp.getTransAmt())){
						String hsTransAmt = getTrnsAmount(hssResp,cc.isDollarAuth());
						details.setTransAmount(hsTransAmt);
					}
					// 5. serialize vo to XML
					try{
					   xml = serializeAuthResponseToXml(hssResp);
					}catch (Exception e) {
						LOGGER.error("Exception in serialization"+ e);
						throw  new SLBusinessServiceException("Couldn't serialize auth response");
					}
					if (StringUtils.isNotBlank(xml)) {
						// 6. persist the vo
						details.setResponse(xml);
						long respId = saveHSSAuthDetails(details);
						response.setRespId(respId);
						//If the existing credit card is not tokenized,update token and masked account no
						if(updatable){
							updateMaskedAccountNoToken(details);
						}
						
					} else { 
						throw  new SLBusinessServiceException("Couldn't serialize auth response");
					}
			 }
		 }catch (Exception e) {
			unhandledException(cc,e);
			throw new SLBusinessServiceException("Unhandled exception", e);
		}
		
		return response;
		
	}

	
	
	/**
	 * Method to authorize Credit card while refunding fund
	 * @param cc 
	 * @param userName 
	 * @return SLCreditCardVO
	 * @throws SLBusinessServiceException 
	 */
	public SLCreditCardVO refundHSTransaction(SLCreditCardVO cc,String userName) throws SLBusinessServiceException {
		SLCreditCardVO response = null;
		SLCreditCardVO details= null;
		CreditRefundResponse creditRefundResponse = null;
		String xml =null;
		
		try{
		// the response to return to caller
		 response = SLCreditCardVO.copy(cc); 
		// the details to be persisted
		 details = SLCreditCardVO.copy(cc);
		}catch (Exception e) {
			unhandledException(cc, e);
			throw new SLBusinessServiceException("Failed to invoke refund service: "+ e);
		}
		details.setTransIdentifier(CommonConstants.AUTHORIZATION);
		details.setTransactionAmount(CommonConstants.TRANS_AMOUNT);

			String transAmount = CommonConstants.TRANS_AMOUNT_CARD_ADD;
			if(null!= cc.getTransactionAmount()){
				transAmount = String.format("%.2f", cc.getTransactionAmount());
				if(StringUtils.contains(transAmount, CommonConstants.DECIMAL_POINT)){
					transAmount = StringUtils.remove(transAmount, '.');

			}//Check to make sure the amount is Numeric
			if(StringUtils.isNumeric(transAmount)){
				cc.setHsTransAmount(transAmount);
			}
			
		}

		try {

			
			CreditRefundRequest creditRefundRequest = createHSRefundRequest(cc);
			try {
				// invoke the HS refund  web service
				creditRefundResponse = invokeRefundRequest(creditRefundRequest,userName);
			} catch (Exception e) {
				
				handleRefundException(cc, e);
				throw e;
			}
			 if(null!= creditRefundResponse){
					if (isAuthorized(creditRefundResponse)) {
						
						updateAuthResponse(response, creditRefundResponse);
						

						setRefundRespDetails(details, creditRefundResponse,userName);
						details.setAuthorized(true);
					} else {
						// if refund is not authorized, update the vo's
						setNoRefunDetails(response, creditRefundResponse);
						setRefundRespDetails(details, creditRefundResponse,userName);
						details.setAuthorized(false);
					}
					if(null!=creditRefundResponse && StringUtils.isNotBlank(creditRefundResponse.getTransAmt())){
						String hsTransAmt = getTrnsAmount(creditRefundResponse,cc.isDollarAuth());
						details.setTransAmount(hsTransAmt);
					}
					// serialize vo to XML
					try{
					   xml = serializeRefundResponseToXml(creditRefundResponse);
					}catch (Exception e) {
						LOGGER.error("Exception in serialization"+ e);
						throw  new SLBusinessServiceException("Couldn't serialize HS refund response");
					}
					if (StringUtils.isNotBlank(xml)) {
						// 6. persist the vo
						details.setResponse(xml);
						long respId = saveHSSAuthDetails(details);
						response.setRespId(respId);
						
						
					} else { 
						throw  new SLBusinessServiceException("Couldn't serialize auth response");
					}
			 }
		 }catch (Exception e) {
			 unhandledRefundException(cc,e);
			throw new SLBusinessServiceException("Unhandled exception", e);
		}
		
		return response;
		
	}

	

	/**Method to get credit card address
	 * getCreditCardAddress.
	 * 
	 * @param cc 
	 * 
	 * @return String
	 */
	protected String getCreditCardAddress(SLCreditCardVO cc) {

		String address = "";
		if (StringUtils.isNotBlank(cc.getBillingAddress1())) {
			address = cc.getBillingAddress1().trim();
		}
		if (StringUtils.isNotBlank(cc.getBillingAddress2())) {
			address = address + " " + cc.getBillingAddress2().trim();
		}
		if (address.length() > CommonConstants.CC_ADDRESS_MAX_LENGTH) {
			address = address.substring(0, CommonConstants.CC_ADDRESS_MAX_LENGTH);
		}
		return address;
	}

	private CreditRefundRequest createHSRefundRequest(SLCreditCardVO cc) throws SLBusinessServiceException {
		CreditRefundRequest hsRefundRequest=null;
		String accountNoToken ="";
		String maskedAccNo ="";
		try{
			hsRefundRequest = new CreditRefundRequest();
		String StringTimeStamp = setTimeStampHss();
		hsRefundRequest.setInetBased(CommonConstants.INETBASED);
		hsRefundRequest.setTransAmt(cc.getHsTransAmount());
		hsRefundRequest.setExpirationDate(cc.getExpireDate());
	   
		if(StringUtils.isNotBlank(cc.getMaskedAccount())){
			maskedAccNo = cc.getMaskedAccount();
			hsRefundRequest.setMaskedAcctNo(maskedAccNo);
		}
		  if(StringUtils.isNotBlank(cc.getToken())){
				accountNoToken = cc.getToken();
				hsRefundRequest.setAcctNo(accountNoToken);
				
		  }
		hsRefundRequest.setTransTs(StringTimeStamp);
		hsRefundRequest.setTermId(getSlStoreNo()+CommonConstants.HSS_STORE_NUM);
		hsRefundRequest.setZipCode(cc.getZipcode());
		}catch (Exception e) {
			LOGGER.error("Exception in creating HS web service request"+ e);
			throw new SLBusinessServiceException(e);
		}
		
		loggingCC(cc);
		
        return hsRefundRequest;
	}
	
	private CreditRefundResponse invokeRefundRequest(CreditRefundRequest creditRefundRequest, String userName) throws Exception {
		int statusCode = 0;
		String requestXml = null;
		String responseXml = null;
		CreditRefundResponse creditRefundResponse = null;
		HomeServiceResponseVO homeServiceResponseVO = new HomeServiceResponseVO();
		requestXml = convertReqObjectToXMLString(creditRefundRequest,CreditRefundRequest.class);
		try{
		LOGGER.info("homeServiceClient start-->");
		LOGGER.info("HSRefundurl:"+getCreditCardRefundUrl()+" HSRefundHeader:"+getCreditCardRefundHeader(userName));

		//time out
		long hsStart = System.currentTimeMillis();
		homeServiceResponseVO = hsAuthServiceClient.createResponseFromClient(requestXml,getCreditCardRefundUrl(),getCreditCardRefundHeader(userName));
		long hsEnd = System.currentTimeMillis();
		LOGGER.info("Inside invokeAuthRequest..>>" +
        		"Time Taken for Home service  >>"+(hsEnd-hsStart));
		
		LOGGER.info("homeServiceClient end-->");
		}catch (Exception e) {
			LOGGER.error("Exception in invoking HS Refund  Web Service:"+ e.getMessage());
			throw new BusinessServiceException("Exception in invoking HS Refund Web Service");
		}
		if(null!=homeServiceResponseVO){
			responseXml = homeServiceResponseVO.getResponseXml();
			statusCode = homeServiceResponseVO.getStatusCode();
			LOGGER.info("HomeService:responseXml:"+responseXml);
			LOGGER.info("HomeService:statusCode:"+statusCode);
			//If Status is Ok, deserialize the response xml
			if (200 == statusCode && StringUtils.isNotBlank(responseXml)){
				try{
					creditRefundResponse =(CreditRefundResponse) deserializeRefundResponse(responseXml,CreditRefundResponse.class);
					LOGGER.info("HS Refund  responses--");
					LOGGER.info(creditRefundResponse.getAddlResponseData());
					LOGGER.info(creditRefundResponse.getMaskedAccount());
					LOGGER.info(creditRefundResponse.getToken());
					LOGGER.info(creditRefundResponse.getAjbKey());
				}catch (Exception e) {
					LOGGER.error("Exception in deserization of HS refund response"+ e.getMessage());
			   }
			}
		}
		return creditRefundResponse;
	}
	/** Method to get url
	 * getCreditCardAuthUrl.
	 * 
	 * @return String
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public String getCreditCardAuthUrl() throws SLBusinessServiceException {

		creditCardAuthUrl = initPropertyValue(CommonConstants.CREDIT_CARD_HS_AUTH_URL, creditCardAuthUrl);
		return creditCardAuthUrl;
	}

	/** Method to get header
	 * getCreditCardAuthHeader.
	 * @param userName 
	 * 
	 * @return String
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public String getCreditCardAuthHeader(String userName) throws SLBusinessServiceException {
        creditCardAuthHeader = initPropertyValue(CommonConstants.CREDIT_CARD_HS_AUTH_HEADER, creditCardAuthHeader);
		return (creditCardAuthHeader + CommonConstants.CREDIT_CARD_HDR_END+userName);
	}
	
	/** Method to get getSlStoreNo
	 * getSlStoreNo.
	 * 
	 * @return String
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public String getSlStoreNo() throws SLBusinessServiceException {

		slStoreNo = initPropertyValue(CommonConstants.SL_STORE_NO, slStoreNo);
		return slStoreNo;
	}

	/** Method to get getSlStoreNoWOZero
	 * getSlStoreNoWOZero.
	 * 
	 * @return String
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public String getSlStoreNoWOZero() throws SLBusinessServiceException {

		slStoreNoWOZero = initPropertyValue(CommonConstants.SL_STORE_NO_WO_ZERO, slStoreNoWOZero);
		return slStoreNoWOZero;
	}
	
	/**	Method to get getMinAuthAmount
	 * 
	 * @return <code>String</code>
	 * @throws SLBusinessServiceException 
	 */
	public String getMinAuthAmount() throws SLBusinessServiceException {
		minAuthAmount = initPropertyValue(CommonConstants.MIN_AUTH_TRANS_AMT, minAuthAmount);
		return minAuthAmount;
	}


	/** Method to handleAuthException
	 * handleAuthException.
	 * 
	 * @param cc 
	 * @param e 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	protected void handleAuthException(SLCreditCardVO cc, Exception e) throws SLBusinessServiceException {

		LOGGER.error("creditCardAuthUrl = [" + getCreditCardAuthUrl() + "]\r\nCreditCardBO-->authorizeCard()-->Exception-->" + e.getMessage(), e);
		LOGGER.info("Credit Card Details---");
		LOGGER.info("Username= " + cc.getUserName());
		LOGGER.info("Amount= " + cc.getHsTransAmount());
		LOGGER.info("Exp date= " + cc.getExpireDate());
		LOGGER.info("Address= " + (cc.getBillingAddress1() != null ? cc.getBillingAddress1().trim() + " " : "")
			+ (cc.getBillingAddress2() != null ? cc.getBillingAddress2().trim() : ""));
		LOGGER.info("Zip code= " + cc.getZipcode());
		throw new SLBusinessServiceException("Failed to invoke authorization service: " + getCreditCardAuthUrl(), e);
	}

	protected void handleRefundException(SLCreditCardVO cc, Exception e) throws SLBusinessServiceException {

		LOGGER.error("creditCardAuthUrl = [" + getCreditCardRefundUrl() + "]\r\nHSRefundCreditcardBO-->refundHSTransaction()-->Exception-->" + e.getMessage(), e);
		LOGGER.info("Credit Card Details---");
		LOGGER.info("Username= " + cc.getUserName());
		LOGGER.info("Amount= " + cc.getHsTransAmount());
		LOGGER.info("Exp date= " + cc.getExpireDate());
		LOGGER.info("Address= " + (cc.getBillingAddress1() != null ? cc.getBillingAddress1().trim() + " " : "")
			+ (cc.getBillingAddress2() != null ? cc.getBillingAddress2().trim() : ""));
		LOGGER.info("Zip code= " + cc.getZipcode());
		throw new SLBusinessServiceException("Failed to invoke authorization service: " + getCreditCardRefundUrl(), e);
	}
	protected void unhandledRefundException(SLCreditCardVO cc, Throwable t) {
        LOGGER.error("creditCardAuthUrl = [" + creditCardRefundUrl + "]\r\nHSRefundCreditcardBO-->refundHSTransaction()-->Exception-->" + t.getMessage(), t);
		LOGGER.info("Credit Card Details---");
		LOGGER.info("Username= "+ cc.getUserName());
		LOGGER.info("Amount= "+cc.getTransactionAmount());
		LOGGER.info("Exp date= "+cc.getExpireDate());
		LOGGER.info("Address= "+ cc.getBillingAddress1() + " " +cc.getBillingAddress2());
		LOGGER.info("Zip code= "+ cc.getZipcode());
		cc.setAuthorized(false);
	}
	/** Method to initialize property value
	 * initPropertyValue.
	 * 
	 * @param key 
	 * @param value 
	 * 
	 * @return String
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private String initPropertyValue(String key, String value) throws SLBusinessServiceException {

		try {
			return value == null ? applicationProperties.getPropertyValue(key) : value;
		} catch (DataNotFoundException e) {
			throw new SLBusinessServiceException(e);
		}
	}


	/** Method to save auth details
	 * saveAuthDetails.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	protected void saveAuthDetails(SLCreditCardVO cc) throws SLBusinessServiceException {

		try {
			Timestamp dateTime = new Timestamp(System.currentTimeMillis());
			cc.setResponseDate(dateTime);
			creditCardDao.saveAuthorizationResponse(cc);
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException("Unable to save the authorization response", e);
		}
	}

	/**method to persist details in account_hs_auth_resp
	 * saveHSSAuthDetails.
	 * 
	 * @param cc 
	 * 
	 * @return long
	 * 
	 * @throws SLBusinessServiceException 
	 */
	protected long saveHSSAuthDetails(SLCreditCardVO cc) throws SLBusinessServiceException {
		long respId = 0;
		try {
			Timestamp dateTime = new Timestamp(System.currentTimeMillis());
			cc.setResponseDate(dateTime);
			respId = creditCardDao.saveHSSAuthorizationResponse(cc);
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException("Unable to save the authorization response", e);
		}
		return respId;
	}
	/**
	 * @param details
	 * @throws SLBusinessServiceException 
	 */
	protected void updateMaskedAccountNoToken(SLCreditCardVO details) throws SLBusinessServiceException {
		try{
			creditCardDao.updateMaskedAccountNoToken(details);
		}catch (Exception e) {
			throw new SLBusinessServiceException("Unable to update token and masked account no", e);
		}
	}
	/**
	 * setApplicationProperties.
	 * 
	 * @param applicationProperties 
	 * 
	 * @return void
	 */
	public void setApplicationProperties(ApplicationProperties applicationProperties) {

		this.applicationProperties = applicationProperties;
	}

	/**
	 * setCreditCardDao.
	 * 
	 * @param creditCardDao 
	 * 
	 * @return void
	 */
	public void setCreditCardDao(ICreditCardDao creditCardDao) {

		this.creditCardDao = creditCardDao;
	}

	/** Method to handle unhandled exception
	 * unhandledException.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	protected void unhandledException(SLCreditCardVO cc, Throwable t) {
        LOGGER.error("creditCardAuthUrl = [" + creditCardAuthUrl + "]\r\nCreditCardBO-->authorizeCard()-->Exception-->" + t.getMessage(), t);
		LOGGER.info("Credit Card Details---");
		LOGGER.info("Username= "+ cc.getUserName());
		LOGGER.info("Amount= "+cc.getTransactionAmount());
		LOGGER.info("Exp date= "+cc.getExpireDate());
		LOGGER.info("Address= "+ cc.getBillingAddress1() + " " +cc.getBillingAddress2());
		LOGGER.info("Zip code= "+ cc.getZipcode());
		cc.setAuthorized(false);
	}

	/** method to set auth resp details
	 * setAuthRespDetails
	 * 
	 * @param cc 
	 * @param resp 
	 * @param userName 
	 * 
	 * @return void
	 */
	protected void setAuthRespDetails(SLCreditCardVO cc, HSSResponse resp, String userName) {

		if(null!=resp){
			cc.setHsTransAmount(resp.getTransAmt());
			cc.setResponseCode(resp.getResponseCode());
			cc.setResponseMessage(resp.getResponseMessage());
			cc.setAddlResponseData(resp.getAddlResponseData());
			cc.setAuthCd(resp.getAuthCd());
			cc.setToken(resp.getToken());
			cc.setMaskedAccount(resp.getMaskedAccount());
			cc.setAjbKey(resp.getAjbKey());
			cc.setUserName(userName);
		}
	}

	
	/**Method to Set transaction amount in ##.# format
	 * @param resp
	 * @param dollarAuth
	 * @return
	 */
	private String getTrnsAmount(HSSResponse resp, boolean dollarAuth){
		Double amt = 0.0;
		Double amtDouble = 0.0;
		String decimal="00";
		String value =null;
		String amtString = null;
	      if(StringUtils.isNotBlank(resp.getTransAmt())){ 
	    	  if(dollarAuth){
				amt = Double.parseDouble(resp.getTransAmt());
				amtDouble = amt/100;
				amtString = String.format("%.2f", amtDouble);
			  }else{
				int length = resp.getTransAmt().length();
				value = resp.getTransAmt().substring(0, length-2);
				decimal = resp.getTransAmt().substring(length-2);
				amtString = value + CommonConstants.DECIMAL_POINTS + decimal;
			}
	      }
		
		return amtString;
	}

	private void loggingCC(SLCreditCardVO cc) throws SLBusinessServiceException {
		LOGGER.info("creditCardAuthUrl = " + getCreditCardAuthUrl());
		LOGGER.info("---Credit Card Details Info---");
		LOGGER.info("Username= "+ cc.getUserName());
		LOGGER.info("Address= "+ cc.getBillingAddress1() + " " +cc.getBillingAddress2());
		LOGGER.info("---Credit Card Request---");
		LOGGER.info("CardType= "+ cc.getCardTypeId());
		LOGGER.info("StoreID= "+ getSlStoreNoWOZero());
		LOGGER.info("ExpirationDate= "+cc.getExpireDate());
		LOGGER.info("TransactionAmount= "+cc.getTransactionAmount());
		//logger.info("CardNumber= "+ cc.getCardNo());
		LOGGER.info("ZipCode= "+ cc.getZipcode());
		LOGGER.info("CID= "+ cc.getCardVerificationNo()); 
		LOGGER.info("StreetAddress= "+ getCreditCardAddress(cc));
	}
	
	
	/**Method to create request object
	 * createHSSAuthRequest.
	 * 
	 * @param cc 
	 * 
	 * @return CreditAuthRequest
	 * 
	 * @throws SLBusinessServiceException 
	 */
	protected CreditAuthRequest createHSSAuthRequest(SLCreditCardVO cc) throws SLBusinessServiceException {
		CreditAuthRequest hssRequest=null;
		String accountNoToken ="";
		String maskedAccNo ="";
		try{
			hssRequest = new CreditAuthRequest();
		String StringTimeStamp = setTimeStampHss();
		hssRequest.setInetBased(CommonConstants.INETBASED);
		hssRequest.setTransAmt(cc.getHsTransAmount());
		hssRequest.setExpirationDate(cc.getExpireDate());
	    if(!cc.isDollarAuth() && StringUtils.isNotBlank(cc.getToken())){
			accountNoToken = cc.getToken();
		}else{
			accountNoToken = cc.getCardNo();
		}
		if(StringUtils.isNotBlank(cc.getMaskedAccount())){
			maskedAccNo = cc.getMaskedAccount();
			hssRequest.setMaskedAcctNo(maskedAccNo);
		}
		hssRequest.setAcctNo(accountNoToken);
		hssRequest.setTransTs(StringTimeStamp);
		hssRequest.setTermId(getSlStoreNo()+CommonConstants.HSS_STORE_NUM);
		hssRequest.setZipCode(cc.getZipcode());
		}catch (Exception e) {
			LOGGER.error("Exception in creating HS web service request"+ e);
			throw new SLBusinessServiceException(e);
		}
		//Logging the HS Auth request
		loggingCC(cc);
		
        return hssRequest;
	}
	
	
	/** method to set time stamp of HS request in YYMMDDhhmmss format
	 * invokeAuthRequest.
	 * 
	 * @param hssRequest 
	 * @param userName 
	 * 
	 * @return HSSResponse
	 * 
	 * @throws Exception 
	 */
	private String setTimeStampHss(){
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String t = formatter.format(date);
		String newString = StringUtils.remove(t, '-');
		newString = StringUtils.remove(newString, ':');
		newString = StringUtils.remove(newString, ' ');
		String finalString = newString.substring(2);
		return finalString;
	}
	
	/**Method to invoke HS authorization service by simple rest client
	 * invokeAuthRequest.
	 * 
	 * @param hssRequest 
	 * @param userName 
	 * 
	 * @return HSSResponse
	 * 
	 * @throws Exception 
	 */
	protected HSSResponse invokeAuthRequest(CreditAuthRequest hssRequest, String userName) throws Exception {
		int statusCode = 0;
		String requestXml = null;
		String responseXml = null;
		HSSResponse hSSResponse = null;
		HomeServiceResponseVO homeServiceResponseVO = new HomeServiceResponseVO();
		requestXml = convertReqObjectToXMLString(hssRequest,CreditAuthRequest.class);
		try{
		LOGGER.info("homeServiceClient start-->");
		LOGGER.info("HSSurl:"+getCreditCardAuthUrl()+" HSSHEader:"+getCreditCardAuthHeader(userName));

		//time out
		long hsStart = System.currentTimeMillis();
		homeServiceResponseVO = hsAuthServiceClient.createResponseFromClient(requestXml,getCreditCardAuthUrl(),getCreditCardAuthHeader(userName));
		long hsEnd = System.currentTimeMillis();
		LOGGER.info("Inside invokeAuthRequest..>>" +
        		"Time Taken for Home service  >>"+(hsEnd-hsStart));
		
		LOGGER.info("homeServiceClient end-->");
		}catch (Exception e) {
			LOGGER.error("Exception in invoking HSS Web Service:"+ e.getMessage());
			throw new BusinessServiceException("Exception in invoking HSS Web Service");
		}
		if(null!=homeServiceResponseVO){
			responseXml = homeServiceResponseVO.getResponseXml();
			statusCode = homeServiceResponseVO.getStatusCode();
			LOGGER.info("HomeService:responseXml:"+responseXml);
			LOGGER.info("HomeService:statusCode:"+statusCode);
			//If Status is Ok, deserialize the response xml
			if (200 == statusCode && StringUtils.isNotBlank(responseXml)){
				try{
					hSSResponse =(HSSResponse) deserializeLpnResponse(responseXml,HSSResponse.class);
					LOGGER.info("HSS responses--");
					LOGGER.info(hSSResponse.getAddlResponseData());
					LOGGER.info(hSSResponse.getMaskedAccount());
					LOGGER.info(hSSResponse.getToken());
					LOGGER.info(hSSResponse.getAjbKey());
				}catch (Exception e) {
					LOGGER.error("Exception in deserization of hss response"+ e.getMessage());
			   }
			}
		}
		return hSSResponse;
	}
	
	
	

	// Common methods to convert Object to String and String to Object
		/**
		 * call this method to convert request Object to XML string
		 * 
		 * @param request
		 * @param classz
		 * @return XML String
		 */
		protected String convertReqObjectToXMLString(Object request, Class<?> classz) {
			XStream xstream = this.getXstream(classz);
			return xstream.toXML(request).toString();
		}
		
		/** method to get xtream
		 * 
		 * @param classz
		 * @return
		 */
		protected XStream getXstream(Class<?> classz) {
			XStream xstream = new XStream(new DomDriver());
			xstream.registerConverter(new ResultConvertor());
			xstream.registerConverter(new ErrorConvertor());
			xstream.registerConverter(new DateConverter(
					CommonConstants.DATE_FORMAT_YYYY_MM_DD, new String[0]));
			xstream.addDefaultImplementation(java.sql.Date.class,
					java.util.Date.class);
			xstream.processAnnotations(classz);
			return xstream;
		}

		/**
		 * Mapping the response xml to SendMessageResponse class
		 * 
		 * @param request
		 * @param classz
		 * @return XML String
		 */
		public Object deserializeLpnResponse(String responseXml,Class<?> classz) {
			XStream xstreamResponse = new XStream(new DomDriver());
			xstreamResponse.processAnnotations(classz);
			HSSResponse response = (HSSResponse) xstreamResponse.fromXML(responseXml);
			return response;
		}
		
		public Object deserializeRefundResponse(String responseXml,Class<?> classz) {
			XStream xstreamResponse = new XStream(new DomDriver());
			xstreamResponse.processAnnotations(classz);
			CreditRefundResponse response = (CreditRefundResponse) xstreamResponse.fromXML(responseXml);
			return response;
		}
		
		/**Only if validation for  ResponseCode,ResponseMessage,addlResponseData,authCd get passed, card should be authorized
		 * isAuthorized.
		 * 
		 * @param resp 
		 * 
		 * @return boolean
		 */
		protected boolean isAuthorized(HSSResponse resp) {
			boolean flag = false;
			if(null!=resp){
				if((CommonConstants.HSS_RESPONSE_CODE.equalsIgnoreCase(resp.getResponseCode()))&&
						(CommonConstants.HSS_RESPONSE_MESSAGE.equalsIgnoreCase(resp.getResponseMessage()))&&
								(CommonConstants.HSS_AUTH_CD.equalsIgnoreCase(resp.getAuthCd()))&&
								(CommonConstants.HSS_APPROVED.equalsIgnoreCase(resp.getAddlResponseData()))){
					flag = true;
				}
			}
			return flag;
		}
		
		/** update auth resp if credit card is authorized
		 * updateAuthResponse.
		 * 
		 * @param cc 
		 * @param resp 
		 * 
		 * @return void
		 */
		protected void updateAuthResponse(SLCreditCardVO cc, HSSResponse resp) {

			cc.setAuthorized(true);
			if(null!=resp){
			  cc.setAuthorizationCode(resp.getResponseCode());	
			  cc.setApprovalCode(resp.getApprovalCd());
			  cc.setMaskedAccount(resp.getMaskedAccount());
			  cc.setToken(resp.getToken());
			}
		}
		
		/** update auth resp if credit card is not authorized
		 * updateNoAuthResponse.
		 * 
		 * @param cc 
		 * @param resp 
		 * 
		 * @return void
		 * 
		 * @throws SLBusinessServiceException 
		 */
		protected void updateNoAuthResponse(SLCreditCardVO cc, HSSResponse resp) {

			cc.setAuthorized(false);
			if(null!=resp){
				cc.setAuthorizationCode(resp.getResponseCode());
				cc.setApprovalCode(resp.getApprovalCd());
			    cc.setMaskedAccount(resp.getMaskedAccount());
			    cc.setToken(resp.getToken());
			}
		}
		
		/** method to serailize auth response to xml
		 * serializeAuthResponseToXml.
		 * 
		 * @param resp 
		 * 
		 * @return String
		 * 
		 */
		
		/** Method to get url
		 * getCreditCardRefundUrl.
		 * 
		 * @return String
		 * 
		 * @throws SLBusinessServiceException 
		 */
		public String getCreditCardRefundUrl() throws SLBusinessServiceException {

			creditCardRefundUrl = initPropertyValue(CommonConstants.CREDIT_CARD_HS_REFUND_URL, creditCardRefundUrl);
			return creditCardRefundUrl;
		}

		/** Method to get header
		 * getCreditCardRefundHeader.
		 * @param userName 
		 * 
		 * @return String
		 * 
		 * @throws SLBusinessServiceException 
		 */
		public String getCreditCardRefundHeader(String userName) throws SLBusinessServiceException {
			creditCardRefundHeader = initPropertyValue(CommonConstants.CREDIT_CARD_HS_REFUND_HEADER, creditCardRefundHeader);
			return (creditCardRefundHeader + CommonConstants.CREDIT_CARD_HDR_END+userName);
		}
		
		
		
		/**Only if validation for  ResponseCode,ResponseMessage,addlResponseData,authCd get passed, card should be authorized
		 * isAuthorized.
		 * 
		 * @param resp 
		 * 
		 * @return boolean
		 */
		protected boolean isAuthorized(CreditRefundResponse resp) {
			boolean flag = false;
			if(null!=resp){
				if((CommonConstants.HSS_RESPONSE_CODE.equalsIgnoreCase(resp.getResponseCode()))&&
						(CommonConstants.HSS_RESPONSE_MESSAGE.equalsIgnoreCase(resp.getResponseMessage()))&&
								(CommonConstants.HSS_AUTH_CD.equalsIgnoreCase(resp.getAuthCd()))&&
								(CommonConstants.HSS_APPROVED.equalsIgnoreCase(resp.getAddlResponseData()))){
					flag = true;
				}
			}
			return flag;
		}
		
		/** update auth resp if credit card is authorized
		 * updateAuthResponse.
		 * 
		 * @param cc 
		 * @param resp 
		 * 
		 * @return void
		 */
		protected void updateAuthResponse(SLCreditCardVO cc, CreditRefundResponse resp) {

			cc.setAuthorized(true);
			if(null!=resp){
			  cc.setAuthorizationCode(resp.getResponseCode());	
			  cc.setApprovalCode(resp.getApprovalCd());
			  cc.setMaskedAccount(resp.getMaskedAccount());
			  cc.setToken(resp.getToken());
			}
		}
		
		protected String serializeRefundResponseToXml(CreditRefundResponse resp) throws SLBusinessServiceException{
			XStream xstream = new XStream();
            xstream.alias("CreditRefundResponse", HSSResponse.class);
			String xmlResponse = xstream.toXML(resp);
			return xmlResponse;
		}
		
		protected void setNoRefunDetails(SLCreditCardVO cc, CreditRefundResponse resp) {

			cc.setAuthorized(false);
			if(null!=resp){
				cc.setAuthorizationCode(resp.getResponseCode());
				cc.setApprovalCode(resp.getApprovalCd());
			    cc.setMaskedAccount(resp.getMaskedAccount());
			    cc.setToken(resp.getToken());
			}
		}
		
		protected void setRefundRespDetails(SLCreditCardVO cc, CreditRefundResponse resp, String userName) {

			if(null!=resp){
				cc.setHsTransAmount(resp.getTransAmt());
				cc.setResponseCode(resp.getResponseCode());
				cc.setResponseMessage(resp.getResponseMessage());
				cc.setAddlResponseData(resp.getAddlResponseData());
				cc.setAuthCd(resp.getAuthCd());
				cc.setToken(resp.getToken());
				cc.setMaskedAccount(resp.getMaskedAccount());
				cc.setAjbKey(resp.getAjbKey());
				cc.setUserName(userName);
			}
		}

		
		/**Method to Set transaction amount in ##.# format
		 * @param resp
		 * @param dollarAuth
		 * @return
		 */
		private String getTrnsAmount(CreditRefundResponse resp, boolean dollarAuth){
			Double amt = 0.0;
			Double amtDouble = 0.0;
			String decimal="00";
			String value =null;
			String amtString = null;
		      if(StringUtils.isNotBlank(resp.getTransAmt())){ 
		    	  if(dollarAuth){
					amt = Double.parseDouble(resp.getTransAmt());
					amtDouble = amt/100;
					amtString = String.format("%.2f", amtDouble);
				  }else{
					int length = resp.getTransAmt().length();
					value = resp.getTransAmt().substring(0, length-2);
					decimal = resp.getTransAmt().substring(length-2);
					amtString = value + CommonConstants.DECIMAL_POINTS + decimal;
				}
		      }
			
			return amtString;
		}


		protected void updateNoAuthResponse(SLCreditCardVO cc, CreditRefundResponse resp) {

			cc.setAuthorized(false);
			if(null!=resp){
				cc.setAuthorizationCode(resp.getResponseCode());
				cc.setApprovalCode(resp.getApprovalCd());
			    cc.setMaskedAccount(resp.getMaskedAccount());
			    cc.setToken(resp.getToken());
			}
		}
		
		
		
		
		protected String serializeAuthResponseToXml(HSSResponse resp) throws SLBusinessServiceException{
			XStream xstream = new XStream();
            xstream.alias("CreditAuthResponse", HSSResponse.class);
			String xmlResponse = xstream.toXML(resp);
			return xmlResponse;
		}


		public HSAuthServiceClient getHsAuthServiceClient() {
			return hsAuthServiceClient;
		}


		public void setHsAuthServiceClient(HSAuthServiceClient hsAuthServiceClient) {
			this.hsAuthServiceClient = hsAuthServiceClient;
		}

		
	
	
}
