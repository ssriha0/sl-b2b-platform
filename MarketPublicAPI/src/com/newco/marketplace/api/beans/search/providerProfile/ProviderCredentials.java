/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.search.types.ProviderCredential;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing all information of 
 * the Credentials 
 * @author Infosys
 *
 */
@XStreamAlias("credentials")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderCredentials {
	@OptionalParam
	@XStreamAlias("count")   
	@XStreamAsAttribute()   
	private Integer count;
	
	@OptionalParam
	@XStreamAlias("companyCount")   
	@XStreamAsAttribute()   
	private Integer companyCount;
	 
	@OptionalParam
	@XStreamImplicit(itemFieldName="credential")
	private List<ProviderCredentialItem> credentialList;

	public ProviderCredentials() {
	
	}

	public ProviderCredentials(ProviderSearchResponseVO providerSearchResponseVO) {
		this.credentialList = new ArrayList<ProviderCredentialItem>();
		this.count = providerSearchResponseVO.getCredentialTotal();
		this.companyCount = providerSearchResponseVO.getCredentialTotalCompany();
		List<ProviderCredential> proCredentialList = providerSearchResponseVO
		.getLicenses();
		if((null!=proCredentialList)) {
			for (ProviderCredential providerCredential : proCredentialList) {
				ProviderCredentialItem credential = new ProviderCredentialItem(providerCredential);						
				this.credentialList.add(credential);
			}
		}
	}
	
	public List<ProviderCredentialItem> getCredentialList() {
		return credentialList;
	}

	public void setCredentialList(List<ProviderCredentialItem> credentialList) {
		this.credentialList = credentialList;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getCompanyCount() {
		return companyCount;
	}

	public void setCompanyCount(Integer companyCount) {
		this.companyCount = companyCount;
	}
}
