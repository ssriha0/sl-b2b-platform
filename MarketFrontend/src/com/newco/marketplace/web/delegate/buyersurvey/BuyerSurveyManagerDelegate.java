package com.newco.marketplace.web.delegate.buyersurvey;

import com.newco.marketplace.dto.vo.buyersurvey.BuyerLogo;
import com.newco.marketplace.dto.vo.buyersurvey.BuyerSurveyDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface BuyerSurveyManagerDelegate {

	BuyerSurveyDTO getEmailConfiguration(int buyerId);

	void saveBuyerConfiguration(BuyerSurveyDTO buyerSurveyDTO) throws BusinessServiceException;

	BuyerLogo getBuyerLogoProperties();

	String getEmailTemplate(BuyerSurveyDTO buyerSurveyDTO, String eventName);
}
