package com.servicelive.wallet.creditcard;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sears.services.creditauth.domain.CreditAuthRequest;
import com.sears.services.creditauth.domain.CreditAuthResponse;
import com.sears.services.creditauth.domain.ThirdPartyCreditAuthRequest;
import com.sears.services.creditauth.domain.ThirdPartyCreditAuthResponse;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;
import com.thoughtworks.xstream.XStream;

// TODO: Auto-generated Javadoc
/**
 * Class ThirdPartyCreditCardBO.
 */
public class ThirdPartyCreditCardBO extends CreditCardBO {

	/** logger. */
	private static final Logger logger = Logger.getLogger(ThirdPartyCreditCardBO.class.getName());

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.CreditCardBO#createAuthRequest(com.servicelive.wallet.creditcard.vo.CreditCardVO)
	 */
	@Override
	protected ThirdPartyCreditAuthRequest createAuthRequest(SLCreditCardVO cc) throws SLBusinessServiceException {

		ThirdPartyCreditAuthRequest thirdpartyRequest = new ThirdPartyCreditAuthRequest();
		if(cc.getCardVerificationNo() != null){
			thirdpartyRequest.setCID(cc.getCardVerificationNo());
		}else{
			thirdpartyRequest.setCID("");
		}
		thirdpartyRequest.setTransactionAmount(cc.getTransactionAmount());	
		
		thirdpartyRequest.setCardType(String.valueOf(cc.getCardTypeId()));
		thirdpartyRequest.setStoreID(getSlStoreNoWOZero());
		thirdpartyRequest.setExpirationDate(cc.getExpireDate());
		thirdpartyRequest.setCardNumber(cc.getCardNo());
		thirdpartyRequest.setZipCode(cc.getZipcode());
		thirdpartyRequest.setStreetAddress(this.getCreditCardAddress(cc));

		return thirdpartyRequest;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.CreditCardBO#invokeAuthRequest(com.sears.services.creditauth.domain.CreditAuthRequest)
	 */
	@Override
	protected CreditAuthResponse invokeAuthRequest(CreditAuthRequest req) throws Exception {

		return this.getAuthServiceBinding().authorize((ThirdPartyCreditAuthRequest) req);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.CreditCardBO#isAuthorized(com.sears.services.creditauth.domain.CreditAuthResponse)
	 */
	@Override
	protected boolean isAuthorized(CreditAuthResponse resp) {

		return (resp.getANSIRespCode().equals("00") && !resp.getCIDRespCode().equals("N"));
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.CreditCardBO#serializeAuthResponseToXml(com.sears.services.creditauth.domain.CreditAuthResponse)
	 */
	@Override
	protected String serializeAuthResponseToXml(CreditAuthResponse resp) {

		XStream xstream = new XStream();

		xstream.alias("ThirdPartyCreditAuthResponse", ThirdPartyCreditAuthResponse.class);
		xstream.omitField(CreditAuthResponse.class, "cardNumber");
		String xmlResponse = xstream.toXML(resp);

		logger.debug("ThirdPartyRESPONSE-->TransIdentifier=" + ((ThirdPartyCreditAuthResponse) resp).getTransIdentifier());
		logger.debug("ThirdPartyRESPONSE-->TransactionID=" + resp.getTransactionID());
		logger.debug("ThirdPartyRESPONSE-->AuthAmount=" + resp.getAuthAmount());
		logger.debug("ThirdPartyRESPONSE-->AuthorizationCode=" + resp.getAuthorizationCode());
		logger.debug("ThirdPartyRESPONSE-->ANSIRespCode/KPAS=" + resp.getANSIRespCode());
		logger.debug("ThirdPartyRESPONSE-->CIDRespCode=" + resp.getCIDRespCode());

		return xmlResponse;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.CreditCardBO#updateNoAuthResponse(com.servicelive.wallet.creditcard.vo.CreditCardVO, com.sears.services.creditauth.domain.CreditAuthResponse)
	 */
	@Override
	protected void updateNoAuthResponse(SLCreditCardVO cc, CreditAuthResponse resp) throws SLBusinessServiceException {

		cc.setAuthorized(false);
		cc.setAuthorizationCode(resp.getAuthorizationCode());
		try {
			if (StringUtils.isBlank(cc.getAnsiResponseCode())) {
				cc.setAnsiResponseCode(creditCardDao.getCreditCardResponseDetails(resp.getANSIRespCode()));
			}
			if (StringUtils.isBlank(cc.getCidResponseCode())) {
				cc.setCidResponseCode(creditCardDao.getCreditCardResponseDetails(resp.getCIDRespCode()));
			}
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException("Failed to translate ANSI and CID response codes.", e);
		}

	}
}
