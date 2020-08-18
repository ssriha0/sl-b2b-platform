/**
 * 
 */
package com.newco.marketplace.api.services.so;

import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.CloseSORequest;
import com.newco.marketplace.api.beans.so.CloseSOResponse;
import com.newco.marketplace.api.beans.so.ReleaseSORequest;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

/**
 * @author ndixit
 *
 */
@Namespace("http://www.servicelive.com/namespaces/closeso")
@APIRequestClass(CloseSORequest.class)
@APIResponseClass(CloseSOResponse.class)
public class SOReleaseService extends BaseService{
	private IServiceOrderBO serviceOrderBO;
	private Logger logger = Logger.getLogger(SOReleaseService.class);
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		Results results = null;
		ReleaseSORequest request  = (ReleaseSORequest) apiVO.getRequestFromPostPut();
		Map<String,String> requestMap = apiVO.getRequestFromGetDelete();
		Integer resourceId = apiVO.getProviderResourceId();
		String soId = requestMap.get(OrderConstants.SO_ID);
		SecurityContext securityContext = super.getSecurityContext(resourceId);
		
		if(soId != null)
		{
			ProcessResponse success = null;
			if (request.getStatusId().intValue() == OrderConstants.ACCEPTED_STATUS) {
				try {
					success = serviceOrderBO
							.releaseServiceOrderInAccepted(soId,
									OrderConstants.RELEASE_SO_REASON_CODE, request.getComment(),
									resourceId, securityContext);
				} catch (BusinessServiceException bse) {
					logger.error("Requested release unsuccessful", bse);
				}
				if (success.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {
					serviceOrderBO.releaseSOProviderAlert(soId,
							securityContext);
				}
			} else if (request.getStatusId().intValue() == OrderConstants.ACTIVE_STATUS) {
				try {
					success = serviceOrderBO
							.releaseServiceOrderInActive(soId, 
									OrderConstants.RELEASE_SO_REASON_CODE,
									request.getComment(), 
									resourceId,
									securityContext);
				} catch (BusinessServiceException bse) {
					logger.error("Requested release unsuccessful", bse);
				}
			} else if (request.getStatusId().intValue() == OrderConstants.PROBLEM_STATUS) {
				try {
					success = serviceOrderBO
							.releaseServiceOrderInProblem(soId,
									OrderConstants.RELEASE_SO_REASON_CODE,
									request.getComment(),
									resourceId,
									securityContext);
				} catch (BusinessServiceException bse) {
					logger.error("Requested release unsuccessful", bse);
				}

			}
		}
		return new CloseSOResponse(results);		
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
}
