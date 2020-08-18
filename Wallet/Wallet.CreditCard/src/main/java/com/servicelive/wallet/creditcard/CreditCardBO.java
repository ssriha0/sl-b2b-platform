package com.servicelive.wallet.creditcard;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sears.services.creditauth.CreditAuthServiceLocator;
import com.sears.services.creditauth.CreditAuthServiceSoapBindingStub;
import com.sears.services.creditauth.domain.CreditAuthRequest;
import com.sears.services.creditauth.domain.CreditAuthResponse;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.properties.ApplicationProperties;
import com.servicelive.common.util.StackTraceHelper;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.wallet.creditcard.dao.ICreditCardDao;

// TODO: Auto-generated Javadoc
/**
 * Class CreditCardBO.
 */
public abstract class CreditCardBO implements ICreditCardBO {

	/** logger. */
	private static final Logger logger = Logger.getLogger(CreditCardBO.class.getName());

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
	


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.ICreditCardBO#authorizeCardTransaction(com.servicelive.wallet.creditcard.vo.CreditCardVO)
	 */
	public SLCreditCardVO authorizeCardTransaction(SLCreditCardVO cc) throws SLBusinessServiceException {

		SLCreditCardVO response = SLCreditCardVO.copy(cc); // the response to return to caller
		SLCreditCardVO details = SLCreditCardVO.copy(cc); // the details to be persisted
		details.setTransIdentifier("Full Authorization");
		details.setTransactionAmount(0.0);
		if (cc.isDollarAuth()){
			cc.setTransactionAmount(Double.parseDouble(getMinAuthAmount()));
			details.setTransIdentifier("Validation");
		}
		//loggingCC(cc);

		try {

			// 1. create the request object
			CreditAuthRequest req = createAuthRequest(cc);
			CreditAuthResponse resp = null;
			try {
				// 2. invoke the authorization web service
				resp = invokeAuthRequest(req);
			} catch (Exception e) {
				// handle ws failures
				handleAuthException(cc, e);
				throw e;
			}
			if (isAuthorized(resp)) {
				// 3. if authroized, update the vo's
				updateAuthResponse(response, resp);
				setAuthRespDetails(details, resp);
				details.setAuthorized(true);
			} else {
				// 4. if not authorized, update the vo's
				updateNoAuthResponse(response, resp);
				setAuthRespDetails(details, resp);
				details.setAuthorized(false);
			}
			// 5. serialize vo to XML
			String xml = serializeAuthResponseToXml(resp);
			if (xml != null) {
				// 6. persist the vo
				details.setResponse(xml);
				details.setTransactionAmount(resp.getAuthAmount());
				saveAuthDetails(details);
			} else { 
				throw  new SLBusinessServiceException("Couldn't serialize auth response");
			}
		} catch (Exception e) {
			logger.error("creditCardAuthUrl = [" + getCreditCardAuthUrl() + "]\r\nCreditCardBO-->authorizeCard()-->Exception-->" + e.getMessage(), e);
			logger.info("Credit Card Details---");
			logger.info("Username= "+ cc.getUserName());
			logger.info("Amount= "+cc.getTransactionAmount());
			logger.info("Exp date= "+cc.getExpireDate());
			logger.info("Address= "+ cc.getBillingAddress1() + " " +cc.getBillingAddress2());
			logger.info("Zip code= "+ cc.getZipcode());
			unhandledException(cc,e);
			throw new SLBusinessServiceException("Unhandled exception", e);
		}

		return response;
	}

	/**
	 * createAuthRequest.
	 * 
	 * @param cc 
	 * 
	 * @return CreditAuthRequest
	 * 
	 * @throws SLBusinessServiceException 
	 */
	protected abstract CreditAuthRequest createAuthRequest(SLCreditCardVO cc) throws SLBusinessServiceException;

	/**
	 * getAuthServiceBinding.
	 * 
	 * @return CreditAuthServiceSoapBindingStub
	 * 
	 * @throws Exception 
	 */
	protected CreditAuthServiceSoapBindingStub getAuthServiceBinding() throws Exception {

		CreditAuthServiceLocator locator = new CreditAuthServiceLocator();
		locator.setCreditAuthServiceEndpointAddress(getCreditCardAuthUrl());

		CreditAuthServiceSoapBindingStub binding = null;
		binding = (CreditAuthServiceSoapBindingStub) locator.getCreditAuthService();

		return binding;
	}

	/**
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

	/**
	 * getCreditCardAuthUrl.
	 * 
	 * @return String
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public String getCreditCardAuthUrl() throws SLBusinessServiceException {

		creditCardAuthUrl = initPropertyValue(CommonConstants.CREDIT_CARD_AUTH_URL, creditCardAuthUrl);
		return creditCardAuthUrl;
	}

	/**
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

	/**
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
	
	/**	
	 * 
	 * @return <code>String</code>
	 * @throws SLBusinessServiceException 
	 */
	public String getMinAuthAmount() throws SLBusinessServiceException {
		minAuthAmount = initPropertyValue(CommonConstants.MIN_AUTH_TRANS_AMT, minAuthAmount);
		return minAuthAmount;
	}


	/**
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

		logger.error("creditCardAuthUrl = [" + getCreditCardAuthUrl() + "]\r\nCreditCardBO-->authorizeCard()-->Exception-->" + e.getMessage(), e);
		logger.info("Credit Card Details---");
		logger.info("Username= " + cc.getUserName());
		logger.info("Amount= " + cc.getTransactionAmount());
		logger.info("Exp date= " + cc.getExpireDate());
		logger.info("Address= " + (cc.getBillingAddress1() != null ? cc.getBillingAddress1().trim() + " " : "")
			+ (cc.getBillingAddress2() != null ? cc.getBillingAddress2().trim() : ""));
		logger.info("Zip code= " + cc.getZipcode());
		throw new SLBusinessServiceException("Failed to invoke authorization service: " + getCreditCardAuthUrl(), e);
	}

	/**
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

	/**
	 * invokeAuthRequest.
	 * 
	 * @param req 
	 * 
	 * @return CreditAuthResponse
	 * 
	 * @throws Exception 
	 */
	protected abstract CreditAuthResponse invokeAuthRequest(CreditAuthRequest req) throws Exception;

	/**
	 * isAuthorized.
	 * 
	 * @param resp 
	 * 
	 * @return boolean
	 */
	protected abstract boolean isAuthorized(CreditAuthResponse resp);

	/**
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

	/**
	 * serializeAuthResponseToXml.
	 * 
	 * @param resp 
	 * 
	 * @return String
	 */
	protected abstract String serializeAuthResponseToXml(CreditAuthResponse resp);

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

	/**
	 * unhandledException.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	protected void unhandledException(SLCreditCardVO cc, Throwable t) {

		logger.error("creditCardAuthUrl = [" + creditCardAuthUrl + "]\r\nCreditCardBO-->authorizeCard()-->Exception-->" + t.getMessage(), t);
		logger.info("Credit Card Details---");
		logger.info("Username= "+ cc.getUserName());
		logger.info("Amount= "+cc.getTransactionAmount());
		logger.info("Exp date= "+cc.getExpireDate());
		logger.info("Address= "+ cc.getBillingAddress1() + " " +cc.getBillingAddress2());
		logger.info("Zip code= "+ cc.getZipcode());
		
		cc.setAuthorized(false);
		cc.setAnsiResponseCode("91");
	}

	/**
	 * updateAuthResponse.
	 * 
	 * @param cc 
	 * @param resp 
	 * 
	 * @return void
	 */
	protected void updateAuthResponse(SLCreditCardVO cc, CreditAuthResponse resp) {

		cc.setAuthorized(true);
		cc.setAuthorizationCode(resp.getAuthorizationCode());
	}

	/**
	 * updateNoAuthDetails.
	 * 
	 * @param cc 
	 * @param resp 
	 * 
	 * @return void
	 */
	protected void setAuthRespDetails(SLCreditCardVO cc, CreditAuthResponse resp) {

		cc.setTransactionId(resp.getTransactionID());
		cc.setAuthorizationAmount(resp.getAuthAmount());
		cc.setAnsiResponseCode(resp.getANSIRespCode());
		cc.setCidResponseCode(resp.getCIDRespCode());
		cc.setAuthorizationCode(resp.getAuthorizationCode());		
		cc.setAddrResponseCode(resp.getAddrVerify());
	}

	/**
	 * updateNoAuthResponse.
	 * 
	 * @param cc 
	 * @param resp 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	protected abstract void updateNoAuthResponse(SLCreditCardVO cc, CreditAuthResponse resp) throws SLBusinessServiceException;

	private void loggingCC(SLCreditCardVO cc) throws SLBusinessServiceException {
		logger.info("creditCardAuthUrl = " + getCreditCardAuthUrl());
		logger.info("---Credit Card Details Info---");
		logger.info("Username= "+ cc.getUserName());
		logger.info("Address= "+ cc.getBillingAddress1() + " " +cc.getBillingAddress2());
		logger.info("-Credit Card Thirdparty Request---");
		logger.info("CardType= "+ cc.getCardTypeId());
		logger.info("StoreID= "+ getSlStoreNoWOZero());
		logger.info("ExpirationDate= "+cc.getExpireDate());
		logger.info("TransactionAmount= "+cc.getTransactionAmount());
		//logger.info("CardNumber= "+ cc.getCardNo());
		logger.info("ZipCode= "+ cc.getZipcode());
		logger.info("CID= "+ cc.getCardVerificationNo());
		logger.info("StreetAddress= "+ getCreditCardAddress(cc));
	}
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.ICreditCardBO#getApplicationFlag(java.lang.String)
	 */
	public String getApplicationFlag(String appKey) throws SLBusinessServiceException{
		String flagValue = null;
		try {
			flagValue=	creditCardDao.getApplicationFlag(appKey);
			logger.info("Inside getApplicationFlag to get the application flag value");
		} catch (Exception ex) {
			logger.info("[CreditCardDaoImpl.getApplicationFlag - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new SLBusinessServiceException("Error", ex);
		}
		return flagValue;
	}
}
