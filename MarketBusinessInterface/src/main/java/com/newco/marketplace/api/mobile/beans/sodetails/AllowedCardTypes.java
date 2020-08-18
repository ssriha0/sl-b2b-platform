package com.newco.marketplace.api.mobile.beans.sodetails;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for AllowedCardTypes.
 * @author Infosys
 *
 */

@XStreamAlias("allowedCardTypes")
public class AllowedCardTypes {
	
	@XStreamImplicit(itemFieldName = "card")
	private List<Card> card;

	/**
	 * @return the card
	 */
	public List<Card> getCard() {
		return card;
	}

	/**
	 * @param card the card to set
	 */
	public void setCard(List<Card> card) {
		this.card = card;
	}

	
}
