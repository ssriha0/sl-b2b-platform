package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

/**
 * @author zizrale
 *
 */
public class CredentialParameterBean extends RatingParameterBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7936632286680900559L;
	private Integer credentialId;
	private Integer credentialTypeId;
	
	public Integer getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(Integer credentialId) {
		this.credentialId = credentialId;
	}

	public Integer getCredentialTypeId() {
		return credentialTypeId;
	}

	public void setCredentialTypeId(Integer credentialTypeId) {
		this.credentialTypeId = credentialTypeId;
	}

}
