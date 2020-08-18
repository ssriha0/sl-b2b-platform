package com.newco.marketplace.web.delegate.buyersurvey;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.buyersurvey.BuyerSurveyManagerBO;
import com.newco.marketplace.dto.vo.buyersurvey.BuyerLogo;
import com.newco.marketplace.dto.vo.buyersurvey.BuyerSurveyDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class BuyerSurveyManagerDelegateImpl implements BuyerSurveyManagerDelegate {

	private static final Logger logger = Logger.getLogger("BuyerSurveyManagerDelegateImpl");

	private BuyerSurveyManagerBO buyerSurveyBO;

	public BuyerSurveyManagerBO getBuyerSurveyBO() {
		return buyerSurveyBO;
	}

	public void setBuyerSurveyBO(BuyerSurveyManagerBO buyerSurveyBO) {
		this.buyerSurveyBO = buyerSurveyBO;
	}

	@Override
	public BuyerSurveyDTO getEmailConfiguration(int buyerId) {
		logger.info("Start BuyerSurveyManagerDelegateImpl::getEmailConfiguration");
		BuyerSurveyDTO buyerSurveyDTO = null;
		try {
			buyerSurveyDTO=buyerSurveyBO.getEmailConfiguration(buyerId);
		} catch (BusinessServiceException bse) {
			logger.error("Could not retrieve email Configuration from Database ", bse);
			bse.printStackTrace();
		}
		logger.info("End BuyerSurveyManagerDelegateImpl::getEmailConfiguration");
		return buyerSurveyDTO;
	}

	@Override
	public void saveBuyerConfiguration(BuyerSurveyDTO buyerSurveyDTO) throws BusinessServiceException {

		logger.info("Start BuyerSurveyManagerDelegateImpl::saveBuyerConfiguration");
		try {
			buyerSurveyBO.saveBuyerConfiguration(buyerSurveyDTO);
		} catch (BusinessServiceException bse) {
			logger.error("Could not save email Configuration in Database ", bse);
			bse.printStackTrace();
			throw new BusinessServiceException(bse);
		}
		logger.info("End BuyerSurveyManagerDelegateImpl::saveBuyerConfiguration");
	
		
	}

	@Override
	public BuyerLogo getBuyerLogoProperties() {
		logger.info("Start BuyerSurveyManagerDelegateImpl::getBuyerLogoProperties");
		BuyerLogo buyerLogo = null;
		try {
			buyerLogo=buyerSurveyBO.getBuyerLogoProperties();
		} catch (BusinessServiceException bse) {
			logger.error("Could not retrieve Logo Configuration from Database ", bse);
			bse.printStackTrace();
		}
		logger.info("End BuyerSurveyManagerDelegateImpl::getBuyerLogoProperties");
		return buyerLogo;
	
	}

	@Override
	public String getEmailTemplate(BuyerSurveyDTO buyerSurveyDTO, String eventName) {
		logger.info("Start BuyerSurveyManagerDelegateImpl::getEmailTemplate");
		String templateResponse = null;
		try {
			templateResponse = buyerSurveyBO.getEmailTemplate(buyerSurveyDTO, eventName);
		} catch (BusinessServiceException bse) {
			logger.error("Could not retrieve Email template from Database ", bse);
			bse.printStackTrace();
		}
		logger.info("End BuyerSurveyManagerDelegateImpl::getEmailTemplate");
		return templateResponse;

	}
}
