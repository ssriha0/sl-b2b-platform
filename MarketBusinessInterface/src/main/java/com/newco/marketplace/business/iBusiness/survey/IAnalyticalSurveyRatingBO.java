/**
 * 
 */
package com.newco.marketplace.business.iBusiness.survey;

import java.util.List;

import com.newco.marketplace.dto.vo.survey.BuyerRating;
import com.newco.marketplace.dto.vo.survey.EntityRating;
import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * @author rkumari
 *
 */
public interface IAnalyticalSurveyRatingBO {
	public void updateBuyerGivenRating(String endDate) throws BusinessServiceException;
	public void updateEntityRating(String endDate) throws BusinessServiceException;
}
