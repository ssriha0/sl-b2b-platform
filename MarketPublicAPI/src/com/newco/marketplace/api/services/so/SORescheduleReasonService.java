package com.newco.marketplace.api.services.so;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.serviceOrderDetail.GetServiceOrdersResponse;
import com.newco.marketplace.api.beans.so.post.SOPostFirmRequest;
import com.newco.marketplace.api.beans.so.post.SOPostFirmResponse;
import com.newco.marketplace.api.beans.so.rescheduleReasons.RescheduleReason;
import com.newco.marketplace.api.beans.so.rescheduleReasons.RescheduleReasons;
import com.newco.marketplace.api.beans.so.rescheduleReasons.SORescheduleReasonResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.orderfulfillment.client.OFHelper;

@APIResponseClass(SORescheduleReasonResponse.class)
public class SORescheduleReasonService extends BaseService {
	
	private static final Logger logger = Logger.getLogger(SORejectService.class);
	private IServiceOrderBO serviceOrderBO;
    private OFHelper ofHelper = new OFHelper();
	
    /**
     * public constructor
     */
	public SORescheduleReasonService() {
		super(null, PublicAPIConstant.GET_SO_RESCHEDULE_REASON_XSD_RESP,
				PublicAPIConstant.SO_RESCHEDULE_REASON_RESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS,
				PublicAPIConstant.SO_RESCHEDULE_REASON_RESPONSE_SCHEMALOCATION, null,
				SORescheduleReasonResponse.class);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering into method : SORescheduleReasonService.execute()");
		ArrayList<LookupVO> rescheduleReasons = null;
		SORescheduleReasonResponse rescheduleReasonsresponse = new SORescheduleReasonResponse();
		Results resultCodes = new Results();
		try {
			rescheduleReasons = serviceOrderBO.getRescheduleReasonCodes(OrderConstants.BUYER_ROLEID);
		} catch (BusinessServiceException e) {
			logger.error("Error Occured...");
			e.printStackTrace();
			resultCodes = Results.getError(
					ResultsCode.GENERIC_ERROR.getMessage(),ResultsCode.FAILURE.getCode());
			rescheduleReasonsresponse.setResults(resultCodes);
			return rescheduleReasonsresponse;
		} catch (Exception e) {
			logger.error("Error Occured...");
			e.printStackTrace();
			resultCodes = Results.getError(
					ResultsCode.GENERIC_ERROR.getMessage(),ResultsCode.FAILURE.getCode());
			rescheduleReasonsresponse.setResults(resultCodes);
			return rescheduleReasonsresponse;
		}

		if(null == rescheduleReasons || 0 >= rescheduleReasons.size()){
			logger.info("Empty reschedule reason codes");
		} else {
			RescheduleReason reason;
			ArrayList reasons = new ArrayList();
			RescheduleReasons reschedReasons = new RescheduleReasons();
			for(LookupVO reasonCodeVO : rescheduleReasons){
					reason = new RescheduleReason(reasonCodeVO.getId(), reasonCodeVO.getType());
					reasons.add(reason);
		     }
			reschedReasons.setRescheduleReason(reasons);
			rescheduleReasonsresponse.setRescheduleReasons(reschedReasons);
		}
		rescheduleReasonsresponse.setResults(Results.getSuccess(
				ResultsCode.SUCCESS.getMessage()));
		
		return rescheduleReasonsresponse;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}
	
}
