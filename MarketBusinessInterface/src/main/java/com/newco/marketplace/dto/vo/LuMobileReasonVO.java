package com.newco.marketplace.dto.vo;

import java.util.List;


import com.newco.marketplace.dto.vo.reasonCode.ReasonCodeVO;
import com.sears.os.vo.SerializableBaseVO;

public class LuMobileReasonVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8724673362543969676L;
	
	private int reasonType;
	private List<ReasonCodeVO> reasonCodes;
	
	public int getReasonType() {
		return reasonType;
	}
	public void setReasonType(int reasonType) {
		this.reasonType = reasonType;
	}
	public List<ReasonCodeVO> getReasonCodes() {
		return reasonCodes;
	}
	public void setReasonCodes(List<ReasonCodeVO> reasonCodes) {
		this.reasonCodes = reasonCodes;
	}
	
		
}
