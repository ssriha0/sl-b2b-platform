package com.servicelive.memberOffer.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.persistence.dao.memberOffer.MemberOfferDao;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.dto.vo.memberOffer.MemberOfferDetailsVO;
import com.newco.marketplace.dto.vo.memberOffer.MemberOfferVO;
import com.newco.marketplace.dto.vo.memberOffer.MemberOffersCriteriaVO;
import com.servicelive.memberOffer.services.MemberOfferService;


public class MemberOfferServiceImpl implements MemberOfferService{
	private static final Logger logger = Logger.getLogger(MemberOfferServiceImpl.class);
	private MemberOfferDao memberOfferDao;
	private static final int SORT_CREATED_DATE_DESC = 1;
	private static final int SORT_CREATED_DATE_ASC = 2;
	private static final int SORT_COMPANY_NAME_ASC = 3;
	private static final int SORT_COMPANY_NAME_DESC = 4;
	private static final int SORT_CLICK_COUNT_DESC = 5;
	private static final String CREATED_DATE = "createdDate";
	private static final String COMPANY_NAME = "companyName";
	private static final String CLICK_COUNT = "clickCount DESC ,createdDate";
	private static final String ORDER_ASC = "ASC";
	private static final String ORDER_DESC = "DESC";
	
	/**
	 * This method returns the offer list after applying the criteria given in  criteriaVO
	 * @param criteriaVO
	 * @return memberOfferList
	 */
	
	public List<MemberOfferVO> getFilteredOffers(
			MemberOffersCriteriaVO criteriaVO)throws BusinessServiceException {
		List<MemberOfferVO> offerDetails = new ArrayList<MemberOfferVO>();
		try{
		if (null != criteriaVO) {
			
			// set start and end index for pagination
			int startPageIndex = ((criteriaVO.getCurrentPageNo()-1)* criteriaVO.getPerPageOfferCount());
			criteriaVO.setStartPageIndex(startPageIndex);
			criteriaVO.setEndPageIndex(criteriaVO.getPerPageOfferCount());
			
			// set sort column and sort order
			if (criteriaVO.getSortCriteria() == SORT_CREATED_DATE_DESC) {
				criteriaVO.setSortColumn(CREATED_DATE);
				criteriaVO.setSortOrder(ORDER_DESC);
			} else if (criteriaVO.getSortCriteria() == SORT_CREATED_DATE_ASC) {
				criteriaVO.setSortColumn(CREATED_DATE);
				criteriaVO.setSortOrder(ORDER_ASC);
			} else if (criteriaVO.getSortCriteria() == SORT_COMPANY_NAME_ASC) {
				criteriaVO.setSortColumn(COMPANY_NAME);
				criteriaVO.setSortOrder(ORDER_ASC);
			} else if (criteriaVO.getSortCriteria() == SORT_COMPANY_NAME_DESC) {
				criteriaVO.setSortColumn(COMPANY_NAME);
				criteriaVO.setSortOrder(ORDER_DESC);
			} else if (criteriaVO.getSortCriteria() == SORT_CLICK_COUNT_DESC) {
				criteriaVO.setSortColumn(CLICK_COUNT);
				criteriaVO.setSortOrder(ORDER_DESC);
			}
			offerDetails = memberOfferDao.getFilteredOffers(criteriaVO);
		}
		}catch (DataServiceException dse) {
				logger.error("Exception in getting the offer details due to " + dse.getMessage(), dse);
				throw new BusinessServiceException("Exception in getting the buyer so templates due to " + dse.getMessage(), dse);
			}
		return offerDetails;
	}
	
	/**
	 * fetches the  details of  the selected member offer
	 * @return MemberOfferDetailsVO
	 */
	public MemberOfferDetailsVO getMemberOfferDetails(int memberOfferId)
			throws BusinessServiceException {
		MemberOfferDetailsVO detailsVO = new MemberOfferDetailsVO();
		try {
			if (StringUtils.isNotEmpty(Integer.toString(memberOfferId))) {
				detailsVO = memberOfferDao.getMemberOfferDetails(memberOfferId);
			}
		} catch (DataServiceException dse) {
			logger.error("Exception in getting the memberOfferDetais due to"
					+ dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting the VOobject due to"
							+ dse.getMessage(), dse);
		}
		return detailsVO;
	}

	/**
	 * updates the view count of the member offer
	 */
	public void updateViewCount(int memberOfferId,int providerId)
			throws BusinessServiceException {
		try {
			memberOfferDao.updateViewCount(memberOfferId, providerId);
		} catch (DataServiceException dse) {
			logger.error(
					"Exception in updating viewcount due to" + dse.getMessage(),
					dse);
			throw new BusinessServiceException(
					"Exception in updating viewcount due to" + dse.getMessage(),
					dse);

		}
	}
	
	/**
	 * This method identifies the offer of the day.It selects the offer whose deal_of_day_ind is 1.
	 * If none is marked as 1 then returns the offer with highest click count.
	 * @return dealOfDay
	 */
	public MemberOfferVO getDealOfTheDay()throws BusinessServiceException{
		MemberOfferVO dealOfDay = new MemberOfferVO();
		try{
			dealOfDay = memberOfferDao.getDealOfDayUsingDodInd();
			if(null == dealOfDay)
			dealOfDay = memberOfferDao.getDealOfTheDay();
		}catch (DataServiceException dse) {
			logger.error("Exception in getting the offer details due to " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in getting deal of the day due to " + dse.getMessage(), dse);
		}
		
		return dealOfDay;
	}
	
	/**
	 * This method gives the total count of member offers.
	 * @return pageCount
	 */
	public Integer getTotalOfferCount()throws BusinessServiceException{
		Integer pageCount;
		try{
			pageCount = memberOfferDao.getTotalOfferCount();
		}catch (DataServiceException dse) {
			logger.error("Exception in getting the offer details due to " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in getting total page count due to " + dse.getMessage(), dse);
		}
		return pageCount;
	}

	public MemberOfferDao getMemberOfferDao() {
		return memberOfferDao;
	}

	public void setMemberOfferDao(MemberOfferDao memberOfferDao) {
		this.memberOfferDao = memberOfferDao;
	}
	
	
}