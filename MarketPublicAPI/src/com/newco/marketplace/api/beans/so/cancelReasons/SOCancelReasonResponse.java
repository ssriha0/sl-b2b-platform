package com.newco.marketplace.api.beans.so.cancelReasons;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.common.BaseResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="soCancelReasonResponse.xsd", path="/resources/schemas/so/")
@XStreamAlias("soCancelReasonResponse")
public class SOCancelReasonResponse extends BaseResponse{
	
	@XStreamAlias("cancelReasons")
	private CancelReasons cancelReasons;

	public SOCancelReasonResponse() {
		super();
	}
	
	public SOCancelReasonResponse(Results results) {
		super(results);
	}

	public CancelReasons getCancelReasons() {
		return cancelReasons;
	}

	public void setCancelReasons(CancelReasons cancelReasons) {
		this.cancelReasons = cancelReasons;
	}
		
}
