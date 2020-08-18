package com.newco.marketplace.api.beans.fundingsources;

import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author ndixit
 * POJO for credit cards.
 */
@XStreamAlias("creditCards")
public class CreditCards {
	@OptionalParam
	@XStreamImplicit(itemFieldName="creditCard")
	private List<CreditCardAccount> creditCardList;

	public List<CreditCardAccount> getCreditCardList() {
		return creditCardList;
	}

	public void setCreditCardList(List<CreditCardAccount> creditCardList) {
		this.creditCardList = creditCardList;
	}

}
