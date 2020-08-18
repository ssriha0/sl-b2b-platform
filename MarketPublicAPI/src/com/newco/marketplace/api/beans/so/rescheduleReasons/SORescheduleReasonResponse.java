package com.newco.marketplace.api.beans.so.rescheduleReasons;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.common.BaseResponse;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XSD(name="soRescheduleReasonResponse.xsd", path="/resources/schemas/so/")
@XStreamAlias("soRescheduleReasonResponse")
public class SORescheduleReasonResponse extends BaseResponse{
	
		
	@XStreamAlias("rescheduleReasons")
	private RescheduleReasons rescheduleReasons;

	public SORescheduleReasonResponse() {
		super();
	}
	
	public SORescheduleReasonResponse (Results results) {
		super(results);
	}

	public RescheduleReasons getRescheduleReasons() {
		return rescheduleReasons;
	}


	public void setRescheduleReasons(RescheduleReasons rescheduleReasons) {
		this.rescheduleReasons = rescheduleReasons;
	}
}
