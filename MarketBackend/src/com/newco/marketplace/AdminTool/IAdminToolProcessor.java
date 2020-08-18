package com.newco.marketplace.AdminTool;

public interface IAdminToolProcessor {
	public boolean processAdjustment(String[] args);
	public boolean sendFullfillmentGroup(Long fullfillmentEntryId);

}
