/**
 *
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.LeadVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.hi.provider.ApproveFirmsVO;
import com.newco.marketplace.vo.hi.provider.ApproveProvidersVO;
import com.newco.marketplace.vo.hi.provider.BackgroundCheckHistoryProviderVO;
import com.newco.marketplace.vo.hi.provider.BackgroundCheckProviderVO;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorResource;
/**
 * @author sahmad7
 */
public interface IVendorHdrDao
{
    public int update(VendorHdr vendorHdr)throws DBException;
	public int updateLicenseAndInsuranceInd(VendorHdr vendorHdr) throws DBException;

    public int updateFM(VendorHdr vendorHdr) throws DBException ;
    public VendorHdr query(VendorHdr vendorHdr)throws DBException;

	public Integer queryForBuyer(Integer vendorId) throws DBException;
	public Buyer queryForBuyer1(String vendorId ) throws DBException;
	public int updateFMBuyer(Buyer buyer)throws DBException;

    public VendorHdr insert(VendorHdr vendorHdr)throws DBException;
    public List queryList(VendorHdr vo) throws DBException;
    public int updateQTnC(VendorHdr vendorHdr) throws DBException;
    public int updateAuditClaimedBy(VendorHdr vendorHdr) throws DBException;
    public VendorHdr queryAuditClaimedBy(VendorHdr vendorHdr) throws DBException;
    public void updateWFStateId(VendorHdr vendorHdr) throws DBException;
    public int UpdateDbInsuranceSelection(VendorHdr vendorHdr) throws DBException;
    public String getAnnualSalesVolume(int vendorId)throws DBException;
    public List<VendorResource> getResourceAndStatus(Integer vendorId) throws DBException;
    public String getStatusForResource(Integer resourceId) throws DBException;
    public String getVendorCurrentStatus(Integer vendorId) throws DBException;
    public Integer getVendorWFStateId(int vendorId) throws DBException;
    public List<VendorHdr> queryCompaniesReadyForApproval() throws DBException;
    public int updateEIN(VendorHdr vendorHdr) throws DBException;
    public Integer getWCIInd(Integer vendorId) throws DBException;
    public Integer updateCBGLInsurance(CredentialProfile credentialProfile)throws DBException;
    public Integer updateWCInsurance(CredentialProfile credentialProfile)throws DBException;
    public Integer updateVLInsurance(CredentialProfile credentialProfile)throws DBException;
    public VendorHdr getInsuranceIndicators(Integer vendorId) throws DBException;
    /**
	 * Description: Retrieves the Company Name for the given vendorId
	 * @param Integer vendorId
	 * @return String Company Name
	 */
    public String getCompanyName(Integer vendorId)throws DBException;
    
    public HashMap getLeadSummaryCountsProvider(Integer vendorId)
			throws DBException;
    public HashMap getStaleInfoCount(Integer vendorId,String staleAfter)
			throws DBException;
    public HashMap getLeadSummaryCountsBuyer(Integer buyerId)
			throws DBException;
    
    public List<LeadVO> getDateForMatchedStatus(Integer vendorId)
			throws DBException;
    
    
    public Integer getStaleInfo(Integer vendorId,String staleAfter)throws DBException;
    // SL-19667 get background check status count
    public HashMap getBcCount(Integer vendorIdr)
			throws DBException;
    
	/**@Description: getting firmType for HI provider Firm
	 * @param approveFirmsVOList
	 * @return
	 * @throws DataServiceException
	 */
	public List<Integer> getVendorType(List<ApproveFirmsVO> approveFirmsVOList)throws DataServiceException;
	
	/**
	 * @param firmStatus
	 * @return
	 * @throws DataServiceException
	 */
	public Integer getWfStateIdForStatus(String firmStatus)throws DataServiceException;
	/**
	 * @param adminResourceId
	 * @return
	 * @throws DataServiceException
	 */
	public String getAdminUserName(Integer adminResourceId)throws DataServiceException;
	/**
	 * @param firmId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean checkIfEligibleForStatusChange(Integer firmId)throws DataServiceException;
	/**@Description: getting ResourceType for HI provider Firm
	 * @param approveProvidersVOList
	 * @return
	 * @throws DataServiceException
	 */
	public List<Integer> getResourceType(List<ApproveProvidersVO> approveProvidersVOList)throws DataServiceException;
    
	/**
	 * @param firmId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean checkIfProviderEligibleForStatusChange(Integer providerId)throws DataServiceException;
	
	/**
	 * @param providerId
	 * @return
	 * @throws DBException
	 */
	public Integer getProviderWFStateId(int providerId) throws DBException;
	
	/**
	 * @param status
	 * @return
	 * @throws DataServiceException
	 */
	public Integer getProviderWfStateIdForStatus(String status)throws DataServiceException;
	/**
	 * @param providerId
	 * @return
	 * @throws DBException
	 */
	public Integer getVendorIdForResource(int providerId) throws DataServiceException;
	/**
	 * @param providerId
	 * @return
	 * @throws DBException
	 */
	public ApproveProvidersVO getBackgroundCheckDataForProvider(int providerId)throws DataServiceException;
	/**
	 * @param backgroundCheckProviderVO
	 * @return
	 * @throws DBException
	 */
	public void updateBackgroundCheckStatus(BackgroundCheckProviderVO backgroundCheckProviderVO)throws DataServiceException;
	/**
	 * @param providerId
	 * @return
	 * @throws DBException
	 */
	public void updateBackgroundCheckStatusInVendorResource(int providerId)throws DataServiceException;
	/**
	 * @param backgroundCheckHistoryProviderVO
	 * @return
	 * @throws DBException
	 */
	public void updateSlProBackgroundHistory(BackgroundCheckHistoryProviderVO backgroundCheckHistoryProviderVO)throws DataServiceException;
	
	/**
	 * @param provider
	 * @throws DataServiceException
	 */
	public void updateBackgroundCheckStatusInVendorResource(ApproveProvidersVO provider)throws DataServiceException;
	
	/**
	 * @param vendorId
	 * @return
	 * @throws DataServiceException
	 */
	public Integer getApprovedmembers(Integer vendorId)throws DataServiceException;
	//Added for D2C provider
	public String buyerSkuPrimaryIndustry(Integer vendorId) throws DataServiceException;
	//SLT-2235
	public boolean isLegalNoticeChecked(Integer vendorId)throws DataServiceException;
}
