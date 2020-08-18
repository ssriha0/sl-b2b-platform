package com.newco.marketplace.web.delegates;

import com.newco.marketplace.dto.vo.dashboard.SODashboardVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.leadprofile.LeadProfileDetailsVO;
 
/**
 * @author z
 *
 */
public interface ISODashBoardDelegate {
	/**
	 * @param entityId
	 * @param resourceId
	 * @param entityTypeId
	 * @return SODashboardVO
	 */
	public SODashboardVO getDashBoardWidgetDetails(String entityId,String resourceId, Integer entityTypeId, SurveyRatingsVO vo,boolean manageSOFlag,boolean leadFlag);
	
	/**
	 * @param entityId
	 * @param resourceId
	 * @param entityTypeId
	 * @return SODashboardVO
	 */
	public SODashboardVO getDashBoardWidgetDetailsCache(String entityId, String resourceId, Integer entityTypeId);

	public SODashboardVO getApprovedUnapprovedCountsAndFirmStatus(Integer vendorId);
	/**
	 * @param vendorId
	 * @return boolean
	 */	
	public boolean isIncompleteProvider( Integer vendorId);
	
	public Integer showMemberOffers( Integer vendorId);
	
	/**
	 * @param vendorId
	 * @return
	 */
	public Integer showLeadsTCIndicator( Integer vendorId);
	
	/**checks whether provider has any un-archived CAR rules
	 * @param vendorId
	 * @return int
	 */
	public int getUnarchivedCARRulesCount(Integer vendorId);

	/**checks whether provider has any active pending CAR rules
	 * @param vendorId
	 * @return int
	 */
	public int getActivePendingCARRulesCount(Integer vendorId);

	/**checks whether provider has Manage Business Profile Permission
	 * @param resourceId
	 * @return int
	 */
	public int getPermission(Integer resourceId);

	/**checks whether vendor has Leads accnt
	 * @param vendorId
	 * @return int
	 */
	public Integer showLeadsSignUp(Integer vendorId);
	//SL-19293 New T&C- START
	//update the new T&C indicator after user clicks on Agree button
	public void updateNewTandC(Integer vendorId, String userName);
	//SL-19293 New T&C- END

	/**Description:Checking non funded feature set is enabled for the buyer
	 * @param companyId
	 * @return
	 */
	public boolean isNonFundedBuyer(String companyId);
	//Added for D2C provider permission link in Nav.
	String buyerSkuPrimaryIndustry(Integer vendorIds);
	//SLT-2235:
	public boolean isLegalNoticeChecked(Integer vendorId) throws BusinessServiceException;
}
