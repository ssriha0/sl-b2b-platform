package com.newco.marketplace.persistence.iDao.provider;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.vo.provider.AuditVO;

/**
 * @author KSudhanshu
 *
 */
public interface IAuditDao extends AuditStatesInterface{

    public AuditVO getResourceAudit(AuditVO auditVO)
            throws DBException;

    public String getResourceBackgroundCheckStatus(AuditVO auditVO)
            throws DBException;

    public String getStatusByTaskId(AuditVO auditVO)
            throws DBException;

    public String getStatusByVendorId(AuditVO auditVO)
            throws DBException;

    public String getStatusResourceCredential(AuditVO auditVO)
            throws DBException;

    public String getStatusVendorCredential(AuditVO auditVO)
            throws DBException;

    public String getStatusVendorHeader(AuditVO auditVO)
            throws DBException;

    public String getStatusVendorResource(AuditVO auditVO)
            throws DBException;

    public AuditVO getVendorAudit(AuditVO auditVO)
            throws DBException;

    public String getVendorBackgroundCheckStatus(AuditVO auditVO)
            throws DBException;

    public String getVendorComplianceStatus(int vendorId)
            throws DBException;

    public int getVendorIdByResourceId(int resourceId)
            throws DBException;

    public AuditVO insert(AuditVO auditVO) throws DBException;

    public int submitForVendorCompliance(int vendorId)
            throws DBException;

    public int updateByResourceId(AuditVO auditVO)
            throws DBException;

    public int updateByVendorId(AuditVO auditVO) throws DBException;

    public int updateResourceBackgroundCheckStatus(AuditVO auditVO)
            throws DBException;

    public int updateStatusByResourceId(AuditVO auditVO)
            throws DBException;

    public int updateStatusByTaskId(AuditVO auditVO)
            throws DBException;

    public int updateStatusByVendorId(AuditVO auditVO)
            throws DBException;

    public int updateStatusResourceCredential(AuditVO auditVO)
            throws DBException;

    public int updateStatusVendorCredential(AuditVO auditVO)
            throws DBException;
    public int updateStatusVendorCredentialById(AuditVO auditVO)
            throws DBException;

    public int updateStatusVendorHeader(AuditVO auditVO)
            throws DBException;

    public int updateStatusVendorResource(AuditVO auditVO)
            throws DBException;
    
    public int insertReasonCdForResource(AuditVO auditVO)
            throws DBException;
    
    public int deleteReasonCdForResource(AuditVO auditVO)
            throws DBException;

    public Integer getVendorIdByUserName (String userName) throws DBException;
    
    public int setAuditPendingStatusForVendorHdr (int vendorId) throws DBException;    

    public int unsetAuditPendingStatusForVendorHdr (int vendorId) throws DBException;

    public int getAuditPendingStatusForVendorHdr (int vendorId) throws DBException;
    
    public int updateStateReasonCdForResource(AuditVO auditVO) throws DBException;
    
    public AuditVO queryWfStateReasonCd(AuditVO auditVO) throws DBException;

    public AuditVO queryWfStateReasonCdTM(AuditVO auditVO) throws DBException;

    public int updateBackGroundCheckByResourceId(AuditVO auditVO)
			throws DBException;
    public String getReasonCodeDescription(AuditVO auditVO) throws DBException;
}//AudioDao
