package com.newco.marketplace.vo.ordermanagement.so;

import com.sears.os.vo.SerializableBaseVO;

public class SOScope extends SerializableBaseVO{
	private String soId;
	private String scope;
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
}