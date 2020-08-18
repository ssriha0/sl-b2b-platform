package com.newco.marketplace.persistence.dao.techtalk;

import java.util.List;

import com.newco.marketplace.api.beans.so.DepositionCodeDTO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface ITechTalkPortalDAO {
	
	String fetchDispositionCode(String orderID) throws DataServiceException;

	List<DepositionCodeDTO> fetchDispositionCode()throws DataServiceException;
	boolean insertOrUpdateDispositionCode(String depositionCode,String soID)throws DataServiceException;
}
