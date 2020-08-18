package com.newco.marketplace.api.services.so;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.cancelReasons.CancelReason;
import com.newco.marketplace.api.beans.so.cancelReasons.CancelReasons;
import com.newco.marketplace.api.beans.so.cancelReasons.SOCancelReasonResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.domain.reasoncodemgr.ReasonCode;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.reasoncode.services.ManageReasonCodeService;

@APIResponseClass(SOCancelReasonResponse.class)
public class SOCancelReasonService extends BaseService {

	private static final Logger logger = Logger
			.getLogger(SOCancelReasonService.class);
	private ManageReasonCodeService manageReasonCodeService;
	private OFHelper ofHelper = new OFHelper();

	/**
	 * public constructor
	 */
	public SOCancelReasonService() {
		super(null, PublicAPIConstant.GET_SO_CANCEL_REASON_XSD_RESP,
				PublicAPIConstant.SO_CANCEL_REASON_RESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS,
				PublicAPIConstant.SO_CANCEL_REASON_RESPONSE_SCHEMALOCATION,
				null, SOCancelReasonResponse.class);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {

		logger.info("Entering into method : SOCancelReasonService.execute()");
		List<ReasonCode> cancelReasonsResp = null;
		Integer buyerId = apiVO.getBuyerIdInteger();
		SOCancelReasonResponse cancelReasonsresponse = new SOCancelReasonResponse();
		Results resultCodes = new Results();
		cancelReasonsResp = manageReasonCodeService.getAllCancelReasonCodes(buyerId, OrderConstants.TYPE_CANCEL);
		if (null == cancelReasonsResp || 0 >= cancelReasonsResp.size()) {
			logger.info("Empty cancel reason codes");
		} else {
			CancelReason reason;
			ArrayList reasons = new ArrayList();
			CancelReasons cancelReasons = new CancelReasons();
			for (ReasonCode reasonCode : cancelReasonsResp) {
				reason = new CancelReason(reasonCode.getReasonCodeId(),
						reasonCode.getReasonCode());
				reasons.add(reason);
			}
			cancelReasons.setCancelReason(reasons);
			cancelReasonsresponse.setCancelReasons(cancelReasons);
		}
		cancelReasonsresponse.setResults(Results.getSuccess(ResultsCode.SUCCESS
				.getMessage()));

		return cancelReasonsresponse;
	}

	public ManageReasonCodeService getManageReasonCodeService() {
		return manageReasonCodeService;
	}

	public void setManageReasonCodeService(
			ManageReasonCodeService manageReasonCodeService) {
		this.manageReasonCodeService = manageReasonCodeService;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

}
