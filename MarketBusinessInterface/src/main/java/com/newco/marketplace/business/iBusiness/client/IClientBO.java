package com.newco.marketplace.business.iBusiness.client;

public interface IClientBO {

	public Integer getClientIdForName(String clientName);

	public boolean isValidClient(String clientId, String clientName);
}
