package com.newco.marketplace.dto.vo.serviceorder;

import com.newco.marketplace.webservices.base.CommonVO;

public class ServiceOrderFeatureVO extends CommonVO {

	private static final long serialVersionUID = 2750786338505527813L;
	
	private String soId;
	private String feature;
	
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getSoId() {
		return soId;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getFeature() {
		return feature;
	}

}
