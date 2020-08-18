package com.newco.marketplace.business.techtalk;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.DepositionCodeDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.techtalk.ITechTalkPortalDAO;

public class TechTalkBuyerPortalBOImpl implements ITeclTalkBuyerPortalBO {

	private static final Logger logger = Logger
			.getLogger(TechTalkBuyerPortalBOImpl.class.getName());
	private ITechTalkPortalDAO techTalkDAO;

	public List<DepositionCodeDTO> getAllDipositionCodes()
			throws BusinessServiceException {
		List<DepositionCodeDTO> depositionCodes = null;
		try {
			logger.info("fetching all deposition codes");
			depositionCodes = techTalkDAO.fetchDispositionCode();
		} catch (DataServiceException e) {
			logger.error("Exceptino in fetching deposition code: " + e);
			throw new BusinessServiceException(e);
		}
		return depositionCodes;
	}

	public boolean insertOrUpdateDispositionCode(String soID,
			String dispositionCode) throws BusinessServiceException {
		try {
			logger.info("fetching all deposition codes");
			techTalkDAO.insertOrUpdateDispositionCode(dispositionCode, soID);
		} catch (DataServiceException e) {
			logger.error("Exceptino in fetching deposition code: " + e);
			throw new BusinessServiceException(e);
		}
		return true;
	}

	public String getDispositionCode(String soID)
			throws BusinessServiceException {
		String dispositionCode = null;
		try {
			logger.info("fetching deposition code for orderID: " + soID);
			dispositionCode = techTalkDAO.fetchDispositionCode(soID);
		} catch (DataServiceException e) {
			logger.error("Exceptino in fetching deposition code: " + e);
			throw new BusinessServiceException(e);
		}
		return dispositionCode;
	}

	public ITechTalkPortalDAO getTechTalkDAO() {
		return techTalkDAO;
	}

	public void setTechTalkDAO(ITechTalkPortalDAO techTalkDAO) {
		this.techTalkDAO = techTalkDAO;
	}
}
