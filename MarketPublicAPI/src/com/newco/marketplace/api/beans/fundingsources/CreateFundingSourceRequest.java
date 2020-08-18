package com.newco.marketplace.api.beans.fundingsources;

import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.api.beans.so.Identification;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author ndixit
 * POJO for createFundingSource request XSD.
 *
 */
@XStreamAlias("createfundingSourceRequest")
public class CreateFundingSourceRequest{
	
	@XStreamAlias("accountSourceType")
	private AccountSourceType accountSourceType;
	
	
	@XStreamAlias("typeOfSource")
	private String typeOfSource;
	
	public String getTypeOfSource() {
		return typeOfSource;
	}

	public void setTypeOfSource(String typeOfSource) {
		this.typeOfSource = typeOfSource;
	}

	public AccountSourceType getAccountSourceType() {
		return accountSourceType;
	}

	public void setAccountSourceType(AccountSourceType accountSourceType) {
		this.accountSourceType = accountSourceType;
	}

}
