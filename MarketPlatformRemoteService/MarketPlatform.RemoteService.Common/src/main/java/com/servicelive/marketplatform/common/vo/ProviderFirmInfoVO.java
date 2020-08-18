package com.servicelive.marketplatform.common.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement()
public class ProviderFirmInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

    private Integer spnId;
    private Integer providerFirmId;
    
    
    public Integer getSpnId() {
		return spnId;
	}
    @XmlElement
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	public Integer getProviderFirmId() {
		return providerFirmId;
	}
	@XmlElement
	public void setProviderFirmId(Integer providerFirmId) {
		this.providerFirmId = providerFirmId;
	}

}

