package com.newco.marketplace.dto.vo.serviceorder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.request.ABaseServiceRequestVO;
public class ReasonCodeVO extends ABaseServiceRequestVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8217118395391612830L;
	private List<ReasonLookupVO> arrRejectReason = new ArrayList();
	
	
	
	public List<ReasonLookupVO> getArrRejectReason() {
		return arrRejectReason;
	}



	public void setArrRejectReason(List<ReasonLookupVO> arrRejectReason) {
		this.arrRejectReason = arrRejectReason;
	}



	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("Reject Reasons", arrRejectReason.toString())
			.toString();
	}
}
