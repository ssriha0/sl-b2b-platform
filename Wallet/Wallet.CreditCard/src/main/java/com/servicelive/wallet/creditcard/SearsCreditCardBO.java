package com.servicelive.wallet.creditcard;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sears.services.creditauth.domain.CreditAuthRequest;
import com.sears.services.creditauth.domain.CreditAuthResponse;
import com.sears.services.creditauth.domain.SearsCreditAuthRequest;
import com.sears.services.creditauth.domain.SearsCreditAuthResponse;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;
import com.thoughtworks.xstream.XStream;

// TODO: Auto-generated Javadoc
/**
 * Class SearsCreditCardBO.
 */
public class SearsCreditCardBO extends CreditCardBO {

	/** logger. */
	private static final Logger logger = Logger.getLogger(SearsCreditCardBO.class.getName());

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.CreditCardBO#createAuthRequest(com.servicelive.wallet.creditcard.vo.CreditCardVO)
	 */
	@Override
	protected SearsCreditAuthRequest createAuthRequest(SLCreditCardVO cc) throws SLBusinessServiceException {

		SearsCreditAuthRequest searsRequest = new SearsCreditAuthRequest();
		if(cc.getCardVerificationNo() != null){
			searsRequest.setCIDCVV2(cc.getCardVerificationNo());
		}else{
			searsRequest.setCIDCVV2("");
		}
		searsRequest.setTransactionAmount(cc.getTransactionAmount());	
		searsRequest.setCardType(String.valueOf(cc.getCardTypeId()));
		searsRequest.setStoreID(getSlStoreNo());
		searsRequest.setExpirationDate(cc.getExpireDate());
		searsRequest.setCardNumber(cc.getCardNo());
		searsRequest.setTrack2AVSData(StringUtils.rightPad(cc.getZipcode(), 9) + getCreditCardAddress(cc));
		searsRequest.setDivision(CommonConstants.DIVISION);

		return searsRequest;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.CreditCardBO#invokeAuthRequest(com.sears.services.creditauth.domain.CreditAuthRequest)
	 */
	@Override
	protected CreditAuthResponse invokeAuthRequest(CreditAuthRequest req) throws Exception {

		return this.getAuthServiceBinding().authorize(((SearsCreditAuthRequest) req));
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.CreditCardBO#isAuthorized(com.sears.services.creditauth.domain.CreditAuthResponse)
	 */
	@Override
	protected boolean isAuthorized(CreditAuthResponse resp) {

		return (resp.getAuthorizationCode() != null && !("").equals(resp.getAuthorizationCode().trim()));
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.CreditCardBO#serializeAuthResponseToXml(com.sears.services.creditauth.domain.CreditAuthResponse)
	 */
	@Override
	protected String serializeAuthResponseToXml(CreditAuthResponse resp) {

		XStream xstream = new XStream();

		xstream.alias("SearsCreditAuthResponse", SearsCreditAuthResponse.class);
		xstream.omitField(CreditAuthResponse.class, "cardNumber");
		String xmlResponse = xstream.toXML(resp);

		logger.debug("SearsRESPONSE-->TransactionID=" + resp.getTransactionID());
		logger.debug("SearsRESPONSE-->AuthAmount=" + resp.getAuthAmount());
		logger.debug("SearsRESPONSE-->AuthorizationCode=" + resp.getAuthorizationCode());
		logger.debug("SearsRESPONSE-->ANSIRespCode/KPAS=" + resp.getANSIRespCode());
		logger.debug("SearsRESPONSE-->CIDRespCode=" + resp.getCIDRespCode());

		return xmlResponse;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.CreditCardBO#updateNoAuthResponse(com.servicelive.wallet.creditcard.vo.CreditCardVO, com.sears.services.creditauth.domain.CreditAuthResponse)
	 */
	@Override
	protected void updateNoAuthResponse(SLCreditCardVO cc, CreditAuthResponse resp) {

		cc.setAuthorized(false);
		cc.setAnsiResponseCode("91");
	}
}
