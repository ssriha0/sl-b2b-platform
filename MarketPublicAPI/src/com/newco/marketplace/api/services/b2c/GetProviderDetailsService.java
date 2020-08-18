/**
 * 
 */
package com.newco.marketplace.api.services.b2c;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.b2c.ProviderDetailsResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.business.iBusiness.b2c.IB2CGenericBO;

/**
 * @author Infosys
 * 
 */
@Namespace("http://www.servicelive.com/namespaces/getProviderDetails")
@APIResponseClass(ProviderDetailsResponse.class)
public class GetProviderDetailsService extends BaseService {

	private static Logger logger = Logger.getLogger(GetProviderDetailsService.class);
	
	private B2CRequestValidator b2CRequestValidator;
	private IB2CGenericBO b2CGenericBO;
	private B2CRequestResponseMapper b2CRequestResponseMapper;
	

	public GetProviderDetailsService() {
		super();
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}

	public B2CRequestValidator getB2CRequestValidator() {
		return b2CRequestValidator;
	}

	public void setB2CRequestValidator(B2CRequestValidator b2cRequestValidator) {
		b2CRequestValidator = b2cRequestValidator;
	}

	public IB2CGenericBO getB2CGenericBO() {
		return b2CGenericBO;
	}

	public void setB2CGenericBO(IB2CGenericBO b2cGenericBO) {
		b2CGenericBO = b2cGenericBO;
	}

	public B2CRequestResponseMapper getB2CRequestResponseMapper() {
		return b2CRequestResponseMapper;
	}

	public void setB2CRequestResponseMapper(
			B2CRequestResponseMapper b2cRequestResponseMapper) {
		b2CRequestResponseMapper = b2cRequestResponseMapper;
	}

}
