package com.servicelive.spn.services.workflow;


/**
 * 
 * @author svanloon
 *
 */
public class ApproveTask extends SPNProviderFirmTask {

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param modifiedBy
	 * @param state 
	 */
	public ApproveTask(Integer spnId, Integer providerFirmId, String modifiedBy, String state) {
		super(spnId, providerFirmId, modifiedBy, state);
		setActionMapperForTask(modifiedBy, state);
	}


}
