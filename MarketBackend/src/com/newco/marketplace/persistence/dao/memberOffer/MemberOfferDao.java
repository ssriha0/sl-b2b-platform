package com.newco.marketplace.persistence.dao.memberOffer;
import java.util.List;


import com.newco.marketplace.dto.vo.memberOffer.MemberOfferDetailsVO;
import com.newco.marketplace.dto.vo.memberOffer.MemberOfferVO;
import com.newco.marketplace.dto.vo.memberOffer.MemberOffersCriteriaVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface MemberOfferDao {
	public List<MemberOfferVO> getFilteredOffers(MemberOffersCriteriaVO criteriaVO) throws DataServiceException;
	public MemberOfferDetailsVO getMemberOfferDetails(int memberOfferId) throws DataServiceException;
	public void updateViewCount(int memberOfferId, int providerId)throws DataServiceException;
	public MemberOfferVO getDealOfTheDay()throws DataServiceException;
	public MemberOfferVO getDealOfDayUsingDodInd()throws DataServiceException;
	public Integer getTotalOfferCount()throws DataServiceException;
}
