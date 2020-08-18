package com.servicelive.service.dataTokenization;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.tokenization.log.Log;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;


/**
 * Class HSAuthServiceCreditCardBO.
 */
public  class HSAuthServiceCreditCardBO  {

	/** logger. */
	private static final Logger LOGGER = Logger.getLogger(HSAuthServiceCreditCardBO.class.getName());

	/** creditCardAuthUrl. */
	private String creditCardAuthUrl;
	

	//PCI Vault
	private HSAuthServiceClient hsAuthServiceClient; 
	
	/** creditCardAuthHeader. */
	private String creditCardAuthHeader;
	


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.ICreditCardBO#authorizeCardTransaction(com.servicelive.wallet.creditcard.vo.CreditCardVO)
	 */
	/** Method to authorize Credit card while adding credit card and while adding fund
	 * authorizeHSSTransaction.
	 * 
	 * @param cc 
	 * 
	 * @return SLCreditCardVO
	 */
	public TokenizeResponse authorizeHSSTransaction(String creditCardNumber,String userName,String creditCardAuthUrl,String creditCardAuthHeader,String slStoreNo) throws Exception {

		
		TokenizeResponse hssResp = new TokenizeResponse();
		try { 
			// 1. create the request object
			TokenizeRequest hssReq = createHSSAuthRequest(creditCardNumber,slStoreNo);
			
			try {
				// 2. invoke the authorization web service
				//long timetaken=System.currentTimeMillis();
				hssResp = invokeAuthRequest(hssReq,userName,creditCardAuthUrl,creditCardAuthHeader);
				//System.out.println("Time taken to hit the service");
				//System.out.println(System.currentTimeMillis()-timetaken);
			} catch (Exception e) {
				// handle ws failures
				handleAuthException(creditCardNumber, e);
				throw e;
			}
			
			//  serialize vo to XML
			String xml = serializeAuthResponseToXml(hssResp);
			if (xml == null) {
				throw  new Exception("Couldn't serialize auth response");
			} 
		 }catch (Exception e) {
			//LOGGER.error("creditCardAuthUrl = [" + getCreditCardAuthUrl() + "]\r\nCreditCardBO-->authorizeCard()-->Exception-->" + e.getMessage(), e);
			LOGGER.info("Credit Card Details----");
			
			unhandledException(creditCardNumber,e);
			throw new Exception(e); 
		}
		
		return hssResp;
		
	}

	

	/** Method to get url
	 * getCreditCardAuthUrl.
	 * 
	 * @return String
	 *  
	 * @throws Exception 
	 */
	public String getCreditCardAuthUrl() throws Exception {

		creditCardAuthUrl = initPropertyValue(CommonConstants.CREDIT_CARD_HS_AUTH_URL, creditCardAuthUrl);
		return creditCardAuthUrl;
	}

	/** Method to get header
	 * getCreditCardAuthHeader.
	 * @param userName 
	 * 
	 * @return String
	 * 
	 * @throws Exception 
	 */
	public String getCreditCardAuthHeader(String userName) throws Exception {
        creditCardAuthHeader = initPropertyValue(CommonConstants.CREDIT_CARD_HS_AUTH_HEADER, creditCardAuthHeader);
		return (creditCardAuthHeader + CommonConstants.CREDIT_CARD_HDR_END+userName);
	}
	

	/** Method to handleAuthException
	 * handleAuthException.
	 * 
	 * @param cc 
	 * @param e 
	 * 
	 * @return void
	 * 
	 * @throws Exception 
	 */
	protected void handleAuthException(String creditCardNumber, Exception e) throws Exception {

		LOGGER.error("creditCardAuthUrl = [" + getCreditCardAuthUrl() + "]\r\nCreditCardBO-->authorizeCard()-->Exception-->" + e.getMessage(), e);
		LOGGER.info("Credit Card Details---");
		
		throw new Exception(e);
	}

	/** Method to initialize property value
	 * initPropertyValue.
	 * 
	 * @param key 
	 * @param value 
	 * 
	 * @return String
	 * 
	 * @throws Exception 
	 */
	private String initPropertyValue(String key, String value) throws Exception {

		return "null";
	}


	
	
	/** Method to handle unhandled exception
	 * unhandledException.
	 * 
	 * @param cc 
	 * 
	 * @return void
	 */
	protected void unhandledException(String creditCardNumber, Throwable t) {

		LOGGER.error("creditCardAuthUrl = [" + creditCardAuthUrl + "]\r\nCreditCardBO-->authorizeCard()-->Exception-->" + t.getMessage(), t);
		LOGGER.info("Credit Card Details---");
		
		
		
	}


	
	
	/**Method to create request object
	 * createHSSAuthRequest.
	 * 
	 * @param cc 
	 * 
	 * @return CreditAuthRequest
	 * 
	 * @throws Exception 
	 */
	protected TokenizeRequest createHSSAuthRequest(String accountNo,String slStoreNo) throws Exception {
		TokenizeRequest hssRequest = new TokenizeRequest();
		hssRequest.setAcctNo(accountNo);
		hssRequest.setTermId(slStoreNo+CommonConstants.HSS_STORE_NUM);
        return hssRequest;
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
	protected TokenizeResponse invokeAuthRequest(TokenizeRequest hssRequest, String userName,String creditCardAuthUrl,String creditCardAuthHeader) throws Exception {
		int statusCode = 0;
		String requestXml = null;
		String responseXml = null; 
		TokenizeResponse hSSResponse = null;
		HomeServiceResponseVO homeServiceResponseVO = new HomeServiceResponseVO();
		requestXml = convertReqObjectToXMLString(hssRequest,TokenizeRequest.class);
		try{
		LOGGER.info("homeServiceClient start-->");
		LOGGER.info("homeServiceClient request-->"+requestXml);
		LOGGER.info("HSSurl:"+getCreditCardAuthUrl()+" HSSHEader:"+creditCardAuthHeader);
		hsAuthServiceClient=new HSAuthServiceClient();
		Log.writeLog( Level.INFO, "TermId in the request:"+ hssRequest.getTermId());
		homeServiceResponseVO = hsAuthServiceClient.createResponseFromClient(requestXml,creditCardAuthUrl,creditCardAuthHeader);
		LOGGER.info("homeServiceClient end-->");
		}catch (Exception e) {
			LOGGER.error("Exception in invoking HSS Web Service:"+ e.getMessage());
			throw new Exception(e);
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
			TokenizeResponse response = (TokenizeResponse) xstreamResponse.fromXML(responseXml);
			return response;
		}
		
		/**Only if validation for  ResponseCode,ResponseMessage,addlResponseData,authCd get passed, card should be authorized
		 * isAuthorized.
		 * 
		 * @param resp 
		 * 
		 * @return boolean
		 */
		protected boolean isAuthorized(TokenizeResponse resp) {
			boolean flag = false;
			if(null!=resp){
				if((CommonConstants.HSS_RESPONSE_CODE.equalsIgnoreCase(resp.getResponseCode()))&&
						(CommonConstants.HSS_RESPONSE_MESSAGE.equalsIgnoreCase(resp.getResponseMessage()))){
					flag = true;
				}
			}
			return flag;
		}
		
		
		
		
		/** method to serailize auth response to xml
		 * serializeAuthResponseToXml.
		 * 
		 * @param resp 
		 * 
		 * @return String
		 * 
		 */
		protected String serializeAuthResponseToXml(TokenizeResponse resp) {

			XStream xstream = new XStream();

			xstream.alias("CreditAuthResponse", TokenizeResponse.class);
			String xmlResponse = xstream.toXML(resp);
			if(null!= resp){
				LOGGER.info("CreditAuthResponse-->ResponseCode=" + resp.getResponseCode());
				LOGGER.info("CreditAuthResponse-->ResponseMessage=" + resp.getResponseMessage());
				LOGGER.info("CreditAuthResponse-->token=" + resp.getToken());
				LOGGER.info("CreditAuthResponse-->maskedAccount=" + resp.getMaskedAccount());
			}
			return xmlResponse;
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

		public HSAuthServiceClient getHsAuthServiceClient() {
			return hsAuthServiceClient;
		}


		public void setHsAuthServiceClient(HSAuthServiceClient hsAuthServiceClient) {
			this.hsAuthServiceClient = hsAuthServiceClient;
		}

		
	
	
}
