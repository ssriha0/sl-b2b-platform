/**
 * 
 */
package com.newco.marketplace.business.businessImpl.survey;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.newco.marketplace.business.iBusiness.survey.IAnalyticalSurveyRatingBO;
import com.newco.marketplace.dto.vo.survey.BuyerRating;
import com.newco.marketplace.dto.vo.survey.EntityRating;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.persistence.iDao.survey.ExtendedSurveyDAO;

/**
 * @author rkumari
 *
 */
public class AnalyticalSurveyRatingBOImpl implements IAnalyticalSurveyRatingBO{
	
	private static final Logger logger = Logger.getLogger(AnalyticalSurveyRatingBOImpl.class
            .getName());

    private ExtendedSurveyDAO extendedSurveyDAO;


	@Override
	public void updateBuyerGivenRating(String endDate) throws BusinessServiceException {
		logger.info("Updating BuyerRating : Start");
		List<BuyerRating> surveyBuyerRatings = new ArrayList<>();
		try {
			List<BuyerRating> hdrBuyerRatings = extendedSurveyDAO.fetchSoDetails(endDate);
			if (!CollectionUtils.isEmpty(hdrBuyerRatings)) {
				for (BuyerRating hdr : hdrBuyerRatings) {
					if (CollectionUtils.isEmpty(extendedSurveyDAO.getBuyerRatingExt(hdr))) {
						surveyBuyerRatings.add(hdr);
					} else {
						extendedSurveyDAO.updateBuyerRatingTotalSOCount(hdr);
					}
				}
				if (!CollectionUtils.isEmpty(surveyBuyerRatings)) {
					extendedSurveyDAO.insertSurveyBuyerRatings(surveyBuyerRatings);
				}
				calulateBuyerGivenAggregateCount();
			}
			List<BuyerRating> surveyResponseRatings = getSurveyResponseRatings(endDate);
			if (!CollectionUtils.isEmpty(surveyResponseRatings)) {
				for (BuyerRating survey : surveyResponseRatings) {
					if (CollectionUtils.isEmpty(extendedSurveyDAO.getBuyerRatingExt(survey))) {
						Integer id = extendedSurveyDAO.getSurveyBuyerRatingId(survey);
						if (id != null) {
							survey.setId(id);
							extendedSurveyDAO.insertBuyerRatingExt(survey);
						}
					} else {
						extendedSurveyDAO.updateBuyerRatingSurveyDetails(survey);
					}
				}
				calulateBuyerGivenAggregateRating();
			}
		} catch (DataServiceException e) {
			logger.error("DataServiceException while updating BuyerRating :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
		logger.info("Updating BuyerRating : End");
	}
	
	private List<BuyerRating> getSurveyResponseRatings(String endDate) throws DataServiceException{
		List<BuyerRating> result = new ArrayList<>();
		result.addAll(extendedSurveyDAO.fetchCSATSurveyResponseRatings(endDate));
		result.addAll(extendedSurveyDAO.fetchNPSSurveyResponseRatings(endDate));
		return result;
	}

	@Override
	public void updateEntityRating(String endDate) throws BusinessServiceException {
		try{
			getBuyerRatings(endDate);
			getProvierRatings(endDate);
			getFirmRatings(endDate);	
		}catch (Exception e){
			logger.error(e);
			throw new BusinessServiceException(e);
		}
	}
	
	private void getFirmRatings(String endDate) throws BusinessServiceException{
		logger.debug("start of getFirmRatings() of AnalyticalSurveyRatingBOImpl");	
		List<EntityRating> subList = null;
        try {
        	subList = extendedSurveyDAO.getDeltaFrimCSATScore(endDate);
            updateEntityRating(subList);
            
            subList = extendedSurveyDAO.getDeltaFrimCSATCurrentScore(endDate);
            updateEntityRating(subList);
            
            subList = extendedSurveyDAO.getDeltaFrimNPSScore(endDate);     
            updateEntityRating(subList);
            
            subList = extendedSurveyDAO.getDeltaFrimNPSCurrentScore(endDate);   
            updateEntityRating(subList);            
           
        } catch (DataServiceException e) {            
            throw new BusinessServiceException(e);
        }
		logger.info(" end of getFirmRatings() of AnalyticalSurveyRatingBOImpl");
		
	}

	private void getProvierRatings(String endDate) throws BusinessServiceException{
		logger.debug("start of getProvierRatings() of AnalyticalSurveyRatingBOImpl");
		List<EntityRating> subList = null;
		try {			
	    	subList = extendedSurveyDAO.getDeltaProviderCSATScore(endDate);
	    	updateEntityRating(subList);
	    	
	    	subList = extendedSurveyDAO.getDeltaProviderCSATCurrentScore(endDate);
	    	updateEntityRating(subList);
	    	
	    	subList = extendedSurveyDAO.getDeltaProviderNPSScore(endDate);
	    	updateEntityRating(subList);
	    	
	    	subList = extendedSurveyDAO.getDeltaProviderNPSCurrentScore(endDate);
	    	updateEntityRating(subList);  
	    	  
    	} catch (DataServiceException e) {				
    		logger.error(e);
			throw new BusinessServiceException(e);
		}catch(Exception e){
			logger.error(e);
			throw new BusinessServiceException(e);
		}
		logger.info(" end of getProvierRatings() of AnalyticalSurveyRatingBOImpl");
		
	}

	private void getBuyerRatings(String endDate) throws BusinessServiceException {		
		logger.info(" start of getBuyerRatings() of AnalyticalSurveyRatingBOImpl"); 
		List<EntityRating> buyerList = null;
	    try {
	    	buyerList = extendedSurveyDAO.getDeltaBuyerCSATScore(endDate);
	    	updateEntityRating(buyerList);
	    	
	    	buyerList = extendedSurveyDAO.getDeltaBuyerCSATCurrentScore(endDate);
	    	updateEntityRating(buyerList); 	
	    	  
    	} catch (DataServiceException e) {				
    		logger.error(e);
			throw new BusinessServiceException(e);
		}catch(Exception e){
			logger.error(e);
			throw new BusinessServiceException(e);
		}
	    logger.info(" end of getBuyerRatings() of AnalyticalSurveyRatingBOImpl");
	}	
	
	private void updateEntityRating(List<EntityRating> list) throws BusinessServiceException{
		try {
			for(EntityRating ele: list){
				EntityRating oldEntity = extendedSurveyDAO.getEntityRating(ele.getEntityId(), ele.getEntityTypeId(),
						ele.getScoreType(), ele.getScoreTimeLineType());
				
				if (oldEntity != null){
					//update
					Integer newCount;
					Double newScore;
					if (SurveyConstants.CURRENTSCORE.equals(oldEntity.getScoreTimeLineType())){
						newCount = ele.getRatingCount();
						newScore = ele.getRatingScore();
						
					}else{						
						newCount = ele.getRatingCount() + oldEntity.getRatingCount();
						if(oldEntity.getRatingScore()!= null){
							newScore = (oldEntity.getRatingCount() * oldEntity.getRatingScore()
									+ ele.getRatingCount() * ele.getRatingScore()) / newCount;
						}else{
							newScore = ele.getRatingScore();
						}
						
					}
					oldEntity.setRatingScore(newScore);
					oldEntity.setRatingCount(newCount);	
					oldEntity.setModifiedBy("updateSurveyRatingBatch");
					extendedSurveyDAO.updateSurveyScoreEntityRating(oldEntity);
					
				}else{
					//insert 1 row	
					ele.setModifiedBy("updateSurveyRatingBatch");
					extendedSurveyDAO.saveEntityRating(ele);
				}	
			}	
		}  catch (DataServiceException e) {				
    		logger.error(e);
			throw new BusinessServiceException(e);
		}catch(Exception e){
			logger.error(e);
			throw new BusinessServiceException(e);
		}
	}

	public ExtendedSurveyDAO getExtendedSurveyDAO() {
		return extendedSurveyDAO;
	}

	public void setExtendedSurveyDAO(ExtendedSurveyDAO extendedSurveyDAO) {
		this.extendedSurveyDAO = extendedSurveyDAO;
	}

	private void calulateBuyerGivenAggregateCount() throws BusinessServiceException {
		try {
			logger.info("Start calulateBuyerGivenAggregateCount");
			List<BuyerRating> buyerRatings = extendedSurveyDAO.calulateTotalSOforBuyer();
			if (!CollectionUtils.isEmpty(buyerRatings)) {
				List<BuyerRating> surveyBuyerRatings = new ArrayList<>();
				for (BuyerRating rating : buyerRatings) {
					Integer id = extendedSurveyDAO.getSurveyBuyerRatingId(rating);
					if (id != null && id > 0) {
						extendedSurveyDAO.replaceBuyerRatingTotalSOCount(rating);
					} else {
						surveyBuyerRatings.add(rating);
					}
				}
				if (!CollectionUtils.isEmpty(surveyBuyerRatings)) {
					extendedSurveyDAO.insertSurveyBuyerRatings(surveyBuyerRatings);
				}
			}
			logger.info("End calulateBuyerGivenAggregateCount");
		} catch (DataServiceException e) {
			logger.error("DataServiceException while calulateBuyerGivenAggregateCount :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}

	private void calulateBuyerGivenAggregateRating() throws BusinessServiceException {
		try {
			logger.info("Start calulateBuyerGivenAggregateRating");
			List<BuyerRating> buyerRatingExts = extendedSurveyDAO.calulateTotalRatingsforBuyer();
			if (!CollectionUtils.isEmpty(buyerRatingExts)) {
				for (BuyerRating rating : buyerRatingExts) {
					if (CollectionUtils.isEmpty(extendedSurveyDAO.getBuyerRatingExt(rating))) {
						Integer id = extendedSurveyDAO.getSurveyBuyerRatingId(rating);
						if (id != null) {
							rating.setId(id);
							extendedSurveyDAO.insertBuyerRatingExt(rating);
						}
					} else {
						extendedSurveyDAO.replaceBuyerRatingSurveyDetails(rating);
					}
				}
			}
			logger.info("End calulateBuyerGivenAggregateRating");
		} catch (DataServiceException e) {
			logger.error("DataServiceException while calulating BuyerGivenAggregateRating :" + e.getMessage());
			throw new BusinessServiceException(e);
		}
	}
}
