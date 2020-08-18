package com.newco.marketplace.tokenize;

import javax.crypto.SecretKey;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.util.Cryptography128;
import com.servicelive.common.BusinessServiceException;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.properties.ApplicationProperties;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

// TODO: Auto-generated Javadoc
/**
 * Class HSTokenizeServiceCreditCardBO.
 */
public  class HSTokenizeServiceCreditCardBO implements IHSTokenizeServiceCreditCardBO {

	/** logger. */
	private static final Logger LOGGER = Logger.getLogger(HSTokenizeServiceCreditCardBO.class.getName());

	/** applicationProperties. */
	private ApplicationProperties applicationProperties;

	/** creditCardAuthUrl. */
	private String creditCardAuthUrl;
	
	/** hsTokenServiceClient */
	private HSTokenServiceClient hsTokenServiceClient; 
	
	/** creditCardAuthHeader. */
	private String creditCardAuthHeader;
	
	/** slStoreNo. */
	private String slStoreNo;
	
	/**Cryptography128  */
	private Cryptography128 cryptography128;
	
	/**IVendorResourceDao */
	private IVendorResourceDao vendorResourceDao;
	
	/**
	 * Method to tokenize an account number
	 * 
	 * @param accNum
	 * @param userName
	 * @return TokenizeResponse
	 */
	public TokenizeResponse tokenizeHSSTransaction(String accNum,String userName) throws SLBusinessServiceException {
	
		LOGGER.info("inside tokenizeHSSTransaction");
		TokenizeResponse hssResp = null;
		String xml =null;
		if(null!= accNum){
		try{
			TokenizeRequest hssReq = createTokenizeRequest(accNum);
			try{
				hssResp = invokeAuthRequest(hssReq,userName);
			}catch (Exception e) {
				handleAuthException(e);
				throw e;
			}
			if(null!= hssResp){
				try{
					   xml = serializeAuthResponseToXml(hssResp);
					   if (StringUtils.isNotBlank(xml)) {
						   hssResp.setResponseXML(xml);
					   }
					}catch (Exception e) {
						LOGGER.error("Exception in serialization"+ e);
						throw  new SLBusinessServiceException("Couldn't serialize auth response");
					}
			}
			
		 }catch (Exception e) {
			 throw new SLBusinessServiceException("Unhandled exception", e);
		 	}
		}
		return hssResp;
		
	}
	/**
	 * Method to tokenize an account number
	 * 
	 * @param accNum
	 * @param userName
	 * @return TokenizeResponse
	 * @throws BusinessServiceException 
	 */
	public HsTokenizeResponse tokenizeHSSTransaction(String soId,String accountNo,Long resourceId) throws Exception {
	    LOGGER.info("inside tokenizeHSSTransaction from close Event");
		TokenizeResponse hssResp = null;
		HsTokenizeResponse response =null;
		String xml = null;
		String userName = null;
		String decryptAccountNumber =null;
		if(null!= accountNo){
			//Decrypting credit card Number 
			decryptAccountNumber = decryptCreditcardNo(accountNo);
			userName = getResourceUserName(resourceId);
		    try{
			   TokenizeRequest hssReq = createTokenizeRequest(decryptAccountNumber);
			   try{
				    hssResp = invokeAuthRequest(hssReq,userName);
			      }catch (Exception e) {
			       e.printStackTrace();
			      }
		         if(null!= hssResp){
				      try{
					   xml = serializeAuthResponseToXml(hssResp);
					   if (StringUtils.isNotBlank(xml)) {
						   response = mapResponse(hssResp,soId,xml);
						   updateSoAdditionalPayement(response);
						   updateSoAdditionalPaymentHistory(response);
					     }
					   }catch (Exception e) {
						LOGGER.error("Exception in serialization"+ e);
						e.printStackTrace();
			        }
		         }
			
		    }catch (Exception e) {
		    	e.printStackTrace();
		 	}
		}
		return response;
		
	}
	
	
	/**
	 * @param response
	 */
	private void updateSoAdditionalPayement(HsTokenizeResponse response) {
		try{
		  if(StringUtils.isNotBlank(response.getMaskedAccountNo()) && StringUtils.isNotBlank(response.getToken())){
			  vendorResourceDao.updateSoAdditionalPayement(response.getMaskedAccountNo(),response.getToken(),response.getSoId());
		   }
		}catch (Exception e) {
			LOGGER.info("Exception in updating so_additional payment history with response"+ e.getMessage());
			e.printStackTrace();
		}
		
	}
	/**
	 * @param response
	 */
	private void updateSoAdditionalPaymentHistory(HsTokenizeResponse response) {
		try{
		  if(StringUtils.isNotBlank(response.getMaskedAccountNo()) && StringUtils.isNotBlank(response.getToken())){
			vendorResourceDao.updateSoAdditionalPayementHistory(response.getMaskedAccountNo(),response.getToken(),response.getSoId(),response.getResponseXml());
		  }
		}catch (Exception e) {
			LOGGER.info("Exception in updating so_additional payment with token and masked Account No"+ e.getMessage());
			e.printStackTrace();
		}
		
	}
	private HsTokenizeResponse mapResponse(TokenizeResponse hssResp, String soId, String xml) {
		HsTokenizeResponse response = new HsTokenizeResponse();
		response.setSoId(soId);
		response.setResponseXml(xml);
		if(StringUtils.isNotBlank(hssResp.getMaskedAccount())){
			response.setMaskedAccountNo(hssResp.getMaskedAccount());
		}
		if(StringUtils.isNotBlank(hssResp.getToken())){
			response.setToken(hssResp.getToken());
		}
		
		return response;
	}
	private String getResourceUserName(Long resourceId) throws BusinessServiceException {
		String userName =null;
		String resourceIdS = null;
		if(null!= resourceId){
			resourceIdS = resourceId.toString();
		}
		try{
			userName = vendorResourceDao.getUserName(resourceIdS);
		}catch (Exception e) {
			LOGGER.info("Exception in getting userName of the resource"+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return userName;
	}
	/**
	 * @param accountNo
	 * @return
	 */
	private String decryptCreditcardNo(String accountNo) {
		String creditCardNo = null;
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
		cryptographyVO.setInput(accountNo);
		try {
			SecretKey secret = cryptography128.generateSecretKey(cryptographyVO);
			cryptographyVO = cryptography128.decryptWithSecretKey(cryptographyVO, secret);
			creditCardNo = cryptographyVO.getResponse();
			// To be removed Later
			LOGGER.info("Credit Card Number decrypted:" + creditCardNo);
		} catch (Exception e) {
			LOGGER.info("Exception in decrypting credit card Number");
			e.printStackTrace();
		}
		return creditCardNo;
	}
	/**
	 * Method to request object for tokenization
	 * 
	 * @param accNum
	 * @return TokenizeRequest
	 */
	private TokenizeRequest createTokenizeRequest(String accNum) throws SLBusinessServiceException{
		TokenizeRequest tokenizeRequest = null;
		try{
			tokenizeRequest = new TokenizeRequest();
			tokenizeRequest.setAcctNo(accNum);
			tokenizeRequest.setTermId(getSlStoreNo()+CommonConstants.HSS_STORE_NUM);
			
		}catch (Exception e) {
			LOGGER.error("Exception in creating HS Tokenize service request"+ e);
			throw new SLBusinessServiceException(e);
		}
		return tokenizeRequest;
	}
	
	/**
	 * Method to invoke the tokenize web service
	 * 
	 * @param hssRequest
	 * @param userName
	 * @return TokenizeResponse
	 */
	private TokenizeResponse invokeAuthRequest(TokenizeRequest hssRequest,String userName)throws Exception{
		int statusCode = 0;
		String requestXml = null;
		String responseXml = null;
		TokenizeResponse hSSResponse = null;
		HomeServiceResponseVO homeServiceResponseVO = new HomeServiceResponseVO();
		requestXml = convertReqObjectToXMLString(hssRequest,TokenizeRequest.class);
		try{
			LOGGER.info("homeServiceClient start-->");
			LOGGER.info("homeServiceClient request: TermId >"+ hssRequest.getTermId());
			LOGGER.info("HSSurl:"+getCreditCardAuthUrl()+" HSSHeader:"+getCreditCardAuthHeader(userName));
			//time out
			long hsStart = System.currentTimeMillis();
			homeServiceResponseVO = hsTokenServiceClient.createResponseFromClient(requestXml,getCreditCardAuthUrl(),getCreditCardAuthHeader(userName));
			long hsEnd = System.currentTimeMillis();
			LOGGER.info("Inside invokeAuthRequest..>>" +
	        		"Time Taken for Tokenize Home service  >>"+(hsEnd-hsStart));
			
		}catch (Exception e) {
			LOGGER.error("Exception in invoking HSS Tokenize Service:"+ e.getMessage());
			throw new BusinessServiceException("Exception in invoking HSS Tokenize Service");
		}
		if(null!=homeServiceResponseVO){
			responseXml = homeServiceResponseVO.getResponseXml();
			statusCode = homeServiceResponseVO.getStatusCode();
			LOGGER.info("HomeService:responseXml:"+responseXml);
			LOGGER.info("HomeService:statusCode:"+statusCode);
			//If Status is Ok, deserialize the response xml
			if (200 == statusCode && StringUtils.isNotBlank(responseXml)){
				try{
					hSSResponse =(TokenizeResponse) deserializeLpnResponse(responseXml,TokenizeResponse.class);
					LOGGER.info("HSS responses--");
					LOGGER.info(hSSResponse.getMaskedAccount());
					LOGGER.info(hSSResponse.getToken());
					LOGGER.info(hSSResponse.getResponseMessage());
				}catch (Exception e) {
					LOGGER.error("Exception in deserization of hss response"+ e.getMessage());
			   }
			}
		}
		return hSSResponse;
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
		TokenizeResponse response = (TokenizeResponse) xstreamResponse.fromXML(responseXml);
		return response;
	}
	
	/** Method to get url
	 * getCreditCardAuthUrl.
	 * 
	 * @return String
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public String getCreditCardAuthUrl() throws SLBusinessServiceException {

		creditCardAuthUrl = initPropertyValue(MPConstants.CREDIT_CARD_HS_TOKENIZE_URL, creditCardAuthUrl);
		return creditCardAuthUrl;
	}
	
	/**
	 * @return
	 * @throws SLBusinessServiceException
	 */
	public String getSlStoreNo() throws SLBusinessServiceException {
        slStoreNo = initPropertyValue(CommonConstants.SL_STORE_NO, slStoreNo);
		return slStoreNo;
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
        creditCardAuthHeader = initPropertyValue(MPConstants.CREDIT_CARD_HS_AUTH_HEADER, creditCardAuthHeader);
		return (creditCardAuthHeader + MPConstants.CREDIT_CARD_HDR_END+userName);
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
				MPConstants.DATE_FORMAT_YYYY_MM_DD, new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class,
				java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}

	
	/** method to serailize auth response to xml
	 * serializeAuthResponseToXml.
	 * 
	 * @param resp 
	 * 
	 * @return String
	 * 
	 */
	protected String serializeAuthResponseToXml(TokenizeResponse resp) throws SLBusinessServiceException{
		XStream xstream = new XStream();
        xstream.alias("TokenizeResponse", TokenizeResponse.class);
		String xmlResponse = xstream.toXML(resp);
		return xmlResponse;
	}
	
	/** Method to handleAuthException
	 * handleAuthException.
	 * 
	 * @param e 
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	protected void handleAuthException(Exception e) throws SLBusinessServiceException {

		LOGGER.error("creditCardAuthUrl = [" + getCreditCardAuthUrl() + "]\r\nCreditCardBO-->authorizeCard()-->Exception-->" + e.getMessage(), e);
		throw new SLBusinessServiceException("Failed to invoke tokenize authorization service: " + getCreditCardAuthUrl(), e);
	}
	
	/**Method to validate tokenize response
	 * isAuthorized.
	 * 
	 * @param resp 
	 * 
	 * @return boolean
	 */
	protected boolean isAuthorized(TokenizeResponse resp) {
		boolean flag = false;
		if(null!=resp){
			if((MPConstants.HSS_RESPONSE_CODE.equalsIgnoreCase(resp.getResponseCode()))&&
					(MPConstants.HSS_RESPONSE_MESSAGE.equalsIgnoreCase(resp.getResponseMessage()))){
				flag = true;
			}
		}
		return flag;
	}
	
	public ApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	public HSTokenServiceClient getHsTokenServiceClient() {
		return hsTokenServiceClient;
	}

	public void setHsTokenServiceClient(HSTokenServiceClient hsTokenServiceClient) {
		this.hsTokenServiceClient = hsTokenServiceClient;
	}
	public Cryptography128 getCryptography128() {
		return cryptography128;
	}
	public void setCryptography128(Cryptography128 cryptography128) {
		this.cryptography128 = cryptography128;
	}
	public IVendorResourceDao getVendorResourceDao() {
		return vendorResourceDao;
	}
	public void setVendorResourceDao(IVendorResourceDao vendorResourceDao) {
		this.vendorResourceDao = vendorResourceDao;
	}
	

	
	
}
