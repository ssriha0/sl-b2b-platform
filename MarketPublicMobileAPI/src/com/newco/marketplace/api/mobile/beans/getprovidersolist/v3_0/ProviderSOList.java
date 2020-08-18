package com.newco.marketplace.api.mobile.beans.getprovidersolist.v3_0;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.vo.mobile.ProviderSearchSO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("serviceOrderList")
@XmlRootElement(name = "serviceOrderList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderSOList {
	
	@XStreamAlias("totalSOcount")
	@XmlElement(name="totalSOcount")
	private Integer totalSOcount;
	
	@XStreamAlias("serviceOrder")
	@XmlElement(name="serviceOrder")
	@XStreamImplicit(itemFieldName="serviceOrder")
	private List<ProviderSearchSO> providerSearchSO;
	
	

	public List<ProviderSearchSO> getProviderSearchSO() {
		return providerSearchSO;
	}

	public void setProviderSearchSO(List<ProviderSearchSO> providerSearchSO) {
		this.providerSearchSO = providerSearchSO;
	}

	public Integer getTotalSOcount() {
		return totalSOcount;
	}

	public void setTotalSOcount(Integer totalSOcount) {
		this.totalSOcount = totalSOcount;
	}

}
