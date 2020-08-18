package com.servicelive.spn.services.workflow;


/**
 * 
 * @author svanloon
 *
 */
public class SendNotificationTask extends SPNProviderFirmTask {

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param modifiedBy
	 * @param state
	 * @param comment
	 */
	public SendNotificationTask(Integer spnId, Integer providerFirmId, String modifiedBy, String state, String comment) {
		super(spnId, providerFirmId, modifiedBy, state, comment);
		setActionMapperForTask(modifiedBy, state);
	}
}
