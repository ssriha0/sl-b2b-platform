package com.newco.marketplace.api.services.so.v1_1;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.servicelive.orderfulfillment.client.OFHelper;

public abstract class SOBaseService extends BaseService {
	private Logger logger = Logger.getLogger(SOBaseService.class);
	protected OFHelper ofHelper = new OFHelper();
	
	public SOBaseService(String requestXsd, String responseXsd,
			String namespace, String resourceSchema, String schemaLocation,
			Class<?> requestClass, Class<?> responseClass) {
		super(requestXsd, responseXsd, namespace, resourceSchema,
				schemaLocation, requestClass, responseClass);
	}

	public SOBaseService() {
		super();
	}

	/**
	 * Legacy code to be eventually supplanted by executeOrderFulfillmentService().
	 */
	protected abstract IAPIResponse executeLegacyService(APIRequestVO apiVO);
	
	protected abstract IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO);
	
	/**
	 * This method dispatches the post Service order request.
	 * 
	 * @param apiVO
	 * @return IAPIResponse
	 */
	public IAPIResponse execute(APIRequestVO apiVO) {
		String strSvcOrdrId = apiVO.getSOId();
		if (isOrderFulfillmentServiceOrder(strSvcOrdrId)) {
			return executeOrderFulfillmentService(apiVO);
		}
		return executeLegacyService(apiVO);
	}
	
	////////////////////////////////////////////////////////////////////////////
	//  HELPER METHODS
	//////////////////////////////////////////////////////////////////////////
	
	protected boolean isOrderFulfillmentServiceOrder(String strSvcOrdrId) {
		return ofHelper.isNewSo(strSvcOrdrId);
	}
	
	protected SecurityContext getSecCtxtForBuyerAdmin(int buyerId) {
		SecurityContext securityContext = null;
		try {
			securityContext = getSecurityContextForBuyerAdmin(buyerId);
		} catch (NumberFormatException nme) {
			logger.error(this.getClass().getName() + ".execute(): Number Format Exception occurred for resourceId: "
					+ buyerId, nme);
		} catch (Exception exception) {
			logger.error(this.getClass().getName() + ".execute():  Exception occurred while accessing security context with resourceId: "
					+ buyerId, exception);
		}
		return securityContext;
	}

    /**
     * @return the ofHelper
     */
    public OFHelper getOfHelper() {
        return ofHelper;
    }

    /**
     * @param ofHelper the ofHelper to set
     */
    public void setOfHelper(OFHelper ofHelper) {
        this.ofHelper = ofHelper;
    }
}