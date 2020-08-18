package com.newco.marketplace.business.buyersurvey;

import com.newco.marketplace.dto.vo.buyersurvey.BuyerLogo;
import com.newco.marketplace.dto.vo.buyersurvey.BuyerSurveyDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface BuyerSurveyManagerBO {

	BuyerSurveyDTO getEmailConfiguration(int buyerId) throws BusinessServiceException;

	void saveBuyerConfiguration(BuyerSurveyDTO buyerSurveyDTO) throws BusinessServiceException;

	BuyerLogo getBuyerLogoProperties() throws BusinessServiceException;

	String getEmailTemplate(BuyerSurveyDTO buyerSurveyDTO, String eventName) throws BusinessServiceException;
}
