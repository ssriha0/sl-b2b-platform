package com.newco.marketplace.persistence.iDao.audit;

import java.util.List;

import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.hi.provider.ApproveFirmsVO;
import com.newco.marketplace.vo.hi.provider.ApproveProvidersVO;

public interface AuditDao {

    public AuditVO getResourceAudit(AuditVO auditVO)
            throws DataServiceException;

    public String getResourceBackgroundCheckStatus(AuditVO auditVO)
            throws DataServiceException;

    public String getStatusByResourceId(AuditVO auditVO)
            throws DataServiceException;

    public String getStatusByTaskId(AuditVO auditVO)
            throws DataServiceException;

    public String getStatusByVendorId(AuditVO auditVO)
            throws DataServiceException;

    public String getStatusResourceCredential(AuditVO auditVO)
            throws DataServiceException;

    public String getStatusVendorCredential(AuditVO auditVO)
            throws DataServiceException;

    public String getStatusVendorResource(AuditVO auditVO)
            throws DataServiceException;

    public AuditVO getVendorAudit(AuditVO auditVO)
            throws DataServiceException;

    public String getVendorComplianceStatus(int vendorId)
            throws DataServiceException;

    public int getVendorIdByResourceId(int resourceId)
            throws DataServiceException;

    public AuditVO insert(AuditVO auditVO) throws DataServiceException;

    public int submitForVendorCompliance(int vendorId)
            throws DataServiceException;

    public int updateByResourceId(AuditVO auditVO)
            throws DataServiceException;

    public int updateByVendorId(AuditVO auditVO) throws DataServiceException;

    public int updateResourceBackgroundCheckStatus(AuditVO auditVO)
            throws DataServiceException;

    public int updateStatusByResourceId(AuditVO auditVO)
            throws DataServiceException;

    public int updateStatusByTaskId(AuditVO auditVO)
            throws DataServiceException;

    public int updateStatusByVendorId(AuditVO auditVO)
            throws DataServiceException;

    public int updateStatusResourceCredential(AuditVO auditVO)
            throws DataServiceException;

    public int updateStatusVendorCredential(AuditVO auditVO)
            throws DataServiceException;

    public int updateStatusVendorHeader(AuditVO auditVO)
            throws DataServiceException;

    public int updateStatusVendorResource(AuditVO auditVO)
            throws DataServiceException;

    public int insertReasonCdForResource(AuditVO auditVO)
            throws DataServiceException;

    public int deleteReasonCdForResource(AuditVO auditVO)
            throws DataServiceException;

    public Integer getVendorIdByUserName (String userName) throws DataServiceException;

    public int setAuditPendingStatusForVendorHdr (int vendorId) throws DataServiceException;

    public int unsetAuditPendingStatusForVendorHdr (int vendorId) throws DataServiceException;

    public int getAuditPendingStatusForVendorHdr (int vendorId) throws DataServiceException;

    public int updateStateReasonCdForResource(AuditVO auditVO) throws DataServiceException;

    public AuditVO queryWfStateReasonCd(AuditVO auditVO) throws DataServiceException;

    public AuditVO queryWfStateReasonCdTM(AuditVO auditVO) throws DataServiceException;

    public int updateBackGroundCheckByResourceId(AuditVO auditVO) throws DataServiceException;

    public int updateReviewDate(AuditVO auditVO) throws DataServiceException;

	/**@description: Fetching reasonCodes id for the given status.
	 * @param approveFirmsVO
	 * @return ApproveFirmsVO
	 * @throws DataServiceException
	 */
	public ApproveFirmsVO getReasonCodesForStatus(ApproveFirmsVO approveFirmsVO)throws DataServiceException;

	/**@description: Fetching reasonCodes id for the given status.
	 * @param approveProvidersVO
	 * @return ApproveProvidersVO
	 * @throws DataServiceException
	 */
	public ApproveProvidersVO getProviderReasonCodesForStatus(ApproveProvidersVO approveProvidersVO)throws DataServiceException;
	/**
	 * R16_0: SL-21003 method added to fetch the reason code of a resource credential
	 */
	public String getReasonCdResourceCredential(AuditVO auditVO)
            throws DataServiceException;
	/**
	 * R16_0: SL-21003 method added to fetch the reason code of a vendor credential
	 */
    public String getReasonCdVendorCredential(AuditVO auditVO)
            throws DataServiceException;

}//AudioDao
