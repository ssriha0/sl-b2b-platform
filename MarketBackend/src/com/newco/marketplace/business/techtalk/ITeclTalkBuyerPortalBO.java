package com.newco.marketplace.business.techtalk;

import java.util.List;

import com.newco.marketplace.api.beans.so.DepositionCodeDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface ITeclTalkBuyerPortalBO {

	String getDispositionCode(String soID) throws BusinessServiceException;

	boolean insertOrUpdateDispositionCode(String soID, String depositionCode)
			throws BusinessServiceException;

	List<DepositionCodeDTO> getAllDipositionCodes()
			throws BusinessServiceException;

}
