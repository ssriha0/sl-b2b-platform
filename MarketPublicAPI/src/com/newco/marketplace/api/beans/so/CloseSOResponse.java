package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.common.BaseResponse;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XSD(name="closeSOResponse.xsd", path="/resources/schemas/so/")
@XStreamAlias("closeSOResponse")	           
public class CloseSOResponse extends BaseResponse {
	
	public CloseSOResponse() {
		super();
	}
	
	public CloseSOResponse (Results results) {
		super(results);
	}	
}
