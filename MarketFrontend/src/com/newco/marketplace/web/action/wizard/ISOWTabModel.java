package com.newco.marketplace.web.action.wizard;

import com.newco.marketplace.web.dto.SOWBaseTabDTO;

public interface ISOWTabModel
{
	public SOWBaseTabDTO getTabById(String soId);
	
	public void setTab(SOWBaseTabDTO tab);
}
