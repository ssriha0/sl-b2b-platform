package com.newco.marketplace.api.services.buyerEventAck;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.buyerEventCallback.BuyerEventCallbackAckRequest;
import com.newco.marketplace.api.beans.buyerEventCallback.BuyerEventCallbackAckResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.business.iBusiness.buyerEventCallback.IBuyerEventCallbackBO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

import org.apache.log4j.Logger;

import java.math.BigInteger;

@APIResponseClass(BuyerEventCallbackAckResponse.class)
@APIRequestClass(BuyerEventCallbackAckRequest.class)
public class BuyerEventAcknowledgementService extends BaseService {

    private IBuyerEventCallbackBO buyerEventCallbackBO;
    private XStreamUtility conversionUtility;

    private static final Logger logger = Logger
            .getLogger(BuyerEventAcknowledgementService.class);

    /**
     * public constructor
     */
    public BuyerEventAcknowledgementService() {
        super(PublicAPIConstant.BUYER_EVENT_ACK_API_DETAIL_REQUEST_XSD, PublicAPIConstant.BUYER_EVENT_ACK_API_DETAILS_XSD_RESP,
                PublicAPIConstant.BUYER_EVENT_ACK_API_DETAILS_RESPONSE_NAMESPACE,
                PublicAPIConstant.BuyerCallback_RESOURCES_SCHEMAS,
                PublicAPIConstant.BUYER_EVENT_ACK_API_DETAILS_RESPONSE_SCHEMALOCATION,
                BuyerEventCallbackAckRequest.class, BuyerEventCallbackAckResponse.class);
    }

    @Override
    public IAPIResponse execute(APIRequestVO apiVO) {

        logger.info("Entering into method : BuyerEventAcknowledgementService.execute()");
        BuyerEventCallbackAckResponse response = new BuyerEventCallbackAckResponse();
        BuyerCallbackNotificationVO acknowledgement = new BuyerCallbackNotificationVO();
        BuyerEventCallbackAckRequest buyerEventCallbackAckRequest=new BuyerEventCallbackAckRequest();
        acknowledgement.setNotificationId(Integer.parseInt(apiVO.getProperties().get("NOTIFICATIONID").toString()));
        //Result result = new Result();
        String buyerCallbackAckRequest = apiVO.getProperties().get("RESULT").toString();
        buyerEventCallbackAckRequest = conversionUtility.getResultObjectBuyerEventCallbackAck(buyerCallbackAckRequest);
        String status = buyerEventCallbackAckRequest.getHttpResult().getCode() != null && buyerEventCallbackAckRequest.getHttpResult().getCode().equals("200") ? ResultsCode.SUCCESS.name() : ResultsCode.FAILURE.name();
        acknowledgement.setStatus(status);
        acknowledgement.setResponse(buyerCallbackAckRequest);
        acknowledgement.setException(status.equalsIgnoreCase(ResultsCode.SUCCESS.name()) ? null : buyerEventCallbackAckRequest.getHttpResult().getMessage());
        try {
            buyerEventCallbackBO.updateBuyerNotification(acknowledgement);
            response.setResults(Results.getSuccess(ResultsCode.SUCCESS
                    .getMessage()));
        } catch (BusinessServiceException e) {
            logger.error("Error occured while updating the ack of callback api");
            e.printStackTrace();
            response.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getCode(), ResultsCode.INTERNAL_SERVER_ERROR.getMessage()));
        }
        return response;
    }

    public IBuyerEventCallbackBO getBuyerEventCallbackBO() {
		return buyerEventCallbackBO;
	}

	public void setBuyerEventCallbackBO(IBuyerEventCallbackBO buyerEventCallbackBO) {
		this.buyerEventCallbackBO = buyerEventCallbackBO;
	}

	public XStreamUtility getConversionUtility() {
        return conversionUtility;
    }

    public void setConversionUtility(XStreamUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }
}
