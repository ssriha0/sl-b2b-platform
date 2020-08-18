package com.servicelive.memberOffer.services;

import java.util.List;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.dto.vo.memberOffer.MemberOfferDetailsVO;
import com.newco.marketplace.dto.vo.memberOffer.MemberOfferVO;
import com.newco.marketplace.dto.vo.memberOffer.MemberOffersCriteriaVO;


public interface MemberOfferService {
	
	public List<MemberOfferVO> getFilteredOffers(MemberOffersCriteriaVO criteriaVO)throws BusinessServiceException;
	public MemberOfferDetailsVO getMemberOfferDetails(int memberOfferId)throws BusinessServiceException;
    public void updateViewCount(int memberOfferId, int providerId)throws BusinessServiceException;
    public MemberOfferVO getDealOfTheDay()throws BusinessServiceException;
    public Integer getTotalOfferCount()throws BusinessServiceException;
}