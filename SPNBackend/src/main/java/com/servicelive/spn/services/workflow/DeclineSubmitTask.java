package com.servicelive.spn.services.workflow;



/**
 * 
 * @author svanloon
 *
 */
public class DeclineSubmitTask extends SPNProviderFirmTask {

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param modifiedBy
	 * @param comment
	 * @param state
	 */
	public DeclineSubmitTask(Integer spnId, Integer providerFirmId, String modifiedBy, String state, String comment) {
		super(spnId, providerFirmId, modifiedBy, state, comment);
		setActionMapperForTask(modifiedBy, state);
	}

	

}
