package com.servicelive.spn.services.interfaces;

import java.util.List;

import com.servicelive.domain.spn.network.SPNUploadedDocumentState;

public interface IUploadedDocumentStateService
{

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @return List
	 */
	public abstract List<SPNUploadedDocumentState> findBy(Integer spnId, Integer providerFirmId);

	/**
	 * 
	 * @param docState
	 */
	public abstract void update(SPNUploadedDocumentState docState);

}