package com.newco.marketplace.persistence.daoImpl.memberOffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.dto.vo.memberOffer.MemberOfferDetailsVO;
import com.newco.marketplace.dto.vo.memberOffer.MemberOfferVO;
import com.newco.marketplace.dto.vo.memberOffer.MemberOffersCriteriaVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.memberOffer.MemberOfferDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class MemberOfferDaoImpl extends ABaseImplDao implements MemberOfferDao {
	
	/**
	 * Returns the filtered offers.
	 * @return List<MemberOfferVO>
	 */
	@SuppressWarnings("unchecked")
	public List<MemberOfferVO> getFilteredOffers (
			MemberOffersCriteriaVO criteriaVO)throws DataServiceException  {
		List<MemberOfferVO> offerDetails = new ArrayList<MemberOfferVO>();
		offerDetails = (List<MemberOfferVO>)queryForList("memberOffers.fetchAllOffers",criteriaVO);
		return offerDetails;
	}
	
	/**
	 * fetches the  details of  the selected member offer
	 * @return MemberOfferDetailsVO
	 */

	public MemberOfferDetailsVO getMemberOfferDetails(int memberOfferId)
			throws DataServiceException {
		MemberOfferDetailsVO detailsVO = new MemberOfferDetailsVO();
		detailsVO = (MemberOfferDetailsVO) queryForObject(
				"memberOffers.fetchSpecificDetails", memberOfferId);
		if (null != detailsVO) {
			return detailsVO;
		} else {
			return null;
		}

	}

	/**
	 * updates the view count of the member offer
	 */
	public void updateViewCount(int memberOfferId, int providerId)
			throws DataServiceException {
		HashMap<String, Integer> map = new HashMap<String,Integer>();
		map.put("memberOfferId", memberOfferId);
		map.put("providerId", providerId);
		Integer  clickCount= (Integer) queryForObject("checkcount.check",map);
		if (clickCount==null||clickCount==0){
		      insert("insertOrUpdateViewCount.insert", map);}
		else{
			  map.put("viewCount", clickCount+1);
			  update("insertOrUpdateViewCount.update",map);
		}
	       	  update("updateViewCount.update", memberOfferId);
            
		}
	
	/**
	 * It selects the offer whose deal_of_day_ind is 1.
	 * 
	 * @return dealOfDay
	 */
	public MemberOfferVO getDealOfDayUsingDodInd()throws DataServiceException{
		return (MemberOfferVO) queryForObject("memberOffers.fetchDealOfDayUsingDodInd",null);
	}
	
	/**
	 * returns the offer with highest click count.
	 * @return dealOfDay
	 */
	public MemberOfferVO getDealOfTheDay()throws DataServiceException{
		return (MemberOfferVO) queryForObject("memberOffers.fetchDealOfTheDay",null);
		
	}
	public Integer getTotalOfferCount()throws DataServiceException{
		return (Integer) queryForObject("memberOffers.getTotalOfferCount", null);
	}
	

}
