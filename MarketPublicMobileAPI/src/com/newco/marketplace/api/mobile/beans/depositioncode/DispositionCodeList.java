package com.newco.marketplace.api.mobile.beans.depositioncode;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("dispositionCodeList")
@XmlAccessorType(XmlAccessType.FIELD)
public class DispositionCodeList {
	@XStreamImplicit(itemFieldName = "dispositionCodeDetail")
	private List<DispositionCodeDetail> dispositionCodeDetail;

	public List<DispositionCodeDetail> getDispositionCodeDetail() {
		return dispositionCodeDetail;
	}

	public void setDispositionCodeDetail(
			List<DispositionCodeDetail> dispositionCodeDetail) {
		this.dispositionCodeDetail = dispositionCodeDetail;
	}

	
}