package com.newco.marketplace.dto.vo.fullfillment;

import com.sears.os.vo.SerializableBaseVO;

public class VLHeartbeatVO extends SerializableBaseVO{
	private String sharpInd;
	private String valuelinkInd;

	public String getSharpInd() {
		return sharpInd;
	}

	public void setSharpInd(String sharpInd) {
		this.sharpInd = sharpInd;
	}

	public String getValuelinkInd() {
		return valuelinkInd;
	}

	public void setValuelinkInd(String valuelinkInd) {
		this.valuelinkInd = valuelinkInd;
	}
}
