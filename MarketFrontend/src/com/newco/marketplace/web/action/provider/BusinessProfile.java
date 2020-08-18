package com.newco.marketplace.web.action.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.ABaseCriteriaHandler;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.ExploreMktPlace.BPSearchCriteria;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.PaginationVO;
import com.newco.marketplace.web.delegates.provider.IProviderProfilePagesDelegate;
import com.newco.marketplace.web.dto.BPSearchDTO;

public class BusinessProfile {

	private static final Logger logger = Logger.getLogger(BusinessProfile.class
			.getName());

	public static void setDefaultBPCriteria() {
		BPCriteriaHandlerFacility facility = BPCriteriaHandlerFacility
				.getInstance();
		PagingCriteria pc = new PagingCriteria();
		SortCriteria sc = new SortCriteria();
		BPSearchCriteria searchCriteria = new BPSearchCriteria();
		facility.initFilterFacility(sc, pc, searchCriteria);
	}

	public static BPCriteriaHandlerFacility loadBPCriteria() {
		BPCriteriaHandlerFacility criteriaHandler = BPCriteriaHandlerFacility
				.getInstance();
		return criteriaHandler;
	}

	public static BPSearchCriteria handleBPSearchCriteria(
			BPCriteriaHandlerFacility facility, BPSearchDTO bpSearchDto) {

		BPSearchCriteria searchCriteria = null;
		String flName = bpSearchDto.getFlname();
		String userId = bpSearchDto.getUserId();

		// logger.debug("flName = " + flName);
		// logger.debug("userId = " + userId);
		if (null != facility) {
			searchCriteria = facility.getBPSearchCriteria();
			if (null != searchCriteria) {
				if (StringUtils.isNotEmpty(flName)) {
					searchCriteria.setFlName(flName);
				}
				if (StringUtils.isNotEmpty(userId)
						&& StringUtils.isNumeric(userId)) {
					searchCriteria.setUserId(Integer.parseInt(userId));
				}
				facility.setBPSearchCriteria(searchCriteria);
			}
		}
		return searchCriteria;
	}

	public static PaginationVO getPaginationVO(Integer vendorId,
			BPCriteriaHandlerFacility criteriaHandler, boolean isError,
			IProviderProfilePagesDelegate providerProfilePagesDelegate)
			throws DataServiceException {

		logger.debug("----Start of BusinessProfile.getCompleteSearchList----");
		PaginationVO paginationVO = null;

		ABaseCriteriaHandler aBaseCriteriaHandler = new ABaseCriteriaHandler();

		CriteriaMap criteriaMap = criteriaHandler.getCriteria();
		int totalRecordCount = 0 ;
//		isError ? 0 : providerProfilePagesDelegate.getTotalRecordCount(vendorId, criteriaMap);
		logger.info("totalRecordCount ========= " + totalRecordCount);

		PagingCriteria pagingCriteria = (PagingCriteria) criteriaMap
				.get(OrderConstants.PAGING_CRITERIA_KEY);

		if (pagingCriteria != null) {
			paginationVO = aBaseCriteriaHandler._getPaginationDetail(
					totalRecordCount, pagingCriteria.getPageSize(),
					pagingCriteria.getStartIndex(),
					pagingCriteria.getEndIndex());
		}

		logger.debug("----End of BusinessProfile.getCompleteSearchList----");

		return paginationVO;
	}
}
