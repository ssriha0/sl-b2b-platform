package com.newco.marketplace.business.iBusiness.dashboard;

import com.newco.marketplace.dto.vo.dashboard.SODashboardVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * @author zizrale
 *
 */
public interface IDashboardDisplayBO {
	/**
	 * @param entityId
	 * @param resourceId
	 * @param entityTypeId
	 * @return SODashboardVO
	 * @throws BusinessServiceException 
	 */
	public SODashboardVO getDashBoardWidgetDetails(String entityId,String resourceId, Integer entityTypeId, 
			SurveyRatingsVO vo, boolean manageSoFlag,boolean leadFlag) 
		throws BusinessServiceException;

	public SODashboardVO getApprovedUnapprovedCountsAndFirmStatus(Integer vendorId)
		throws BusinessServiceException;
	//SLT-2235
	public boolean isLegalNoticeChecked(Integer vendorId)throws BusinessServiceException;
}
