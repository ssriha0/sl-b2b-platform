/**
 *
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.dto.vo.audit.AuditHistoryVO;
import com.newco.marketplace.dto.vo.audit.AuditNotesVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.persistence.iDao.provider.IPowerAuditorWorkflowDao;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchResultVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchVO;
import com.newco.marketplace.vo.provider.PowerAuditorVendorInfoVO;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author hoza
 *
 */
public class PowerAuditorWorkflowDaoImpl extends ABaseImplDao implements
		IPowerAuditorWorkflowDao {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IPowerAuditorworkflowDao#searchQueueCounts(com.newco.marketplace.vo.provider.PowerAuditorSearchVO)
	 */
	public List<PowerAuditorSearchResultVO> searchQueueCounts(
			PowerAuditorSearchVO searchCriteria) throws DBException {
			return (List<PowerAuditorSearchResultVO> )this.queryForList("powerauditor.counts.query", searchCriteria);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IPowerAuditorWorkflowDao#getAuditHistory(java.lang.Integer)
	 */
	public List<AuditHistoryVO> getAuditHistory(AuditHistoryVO vendorId)
			throws DBException {
		return (List<AuditHistoryVO> )this.queryForList("vendor_audit_history.query", vendorId);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IPowerAuditorWorkflowDao#getAuditNotes(java.lang.Integer)
	 */
	public List<AuditNotesVO> getAuditNotes(AuditNotesVO vendorId)
			throws DBException {
		return (List<AuditNotesVO> )this.queryForList("vendor_audit_notes.query", vendorId);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IPowerAuditorWorkflowDao#getNextAuditFromQueue(com.newco.marketplace.vo.provider.PowerAuditorSearchVO)
	 */
	public AuditVO getNextAuditFromQueue(PowerAuditorSearchVO searchCriteria)
			throws DBException {
		return (AuditVO) this.queryForObject("powerauditor.fetchnext.credential.query", searchCriteria);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IPowerAuditorWorkflowDao#getVendorInfo(java.lang.Integer)
	 */
	public PowerAuditorVendorInfoVO getVendorInfo(Integer vendorId)
			throws DBException {
		return (PowerAuditorVendorInfoVO) this.queryForObject("vendor_info_for_audit.query", vendorId);
	}
	/**
	 * method to put an etry for an audit task with start time
	 * @param auditTimeVo
	 * @return
	 * @throws DelegateException
	 */
	public AuditTimeVO saveAuditTime(AuditTimeVO auditTimeVo)
			throws Exception {
		
		Integer auditTimeLoggingId=(Integer)this.insert("insertAuditLicInsTime.insert",
				auditTimeVo);
		
		if(null!=auditTimeLoggingId)
		{
			auditTimeVo.setAuditTimeLoggingId(auditTimeLoggingId);
		}
		
		return auditTimeVo;
	}
	/**
	 * method to update the audit task with end time
	 * @param auditTimeVo
	 * @return
	 * @throws DelegateException
	 */
	
	public AuditTimeVO updateAuditTime(AuditTimeVO auditTimeVo)
			throws Exception {
		
		Integer updated=(Integer)this.insert("updateAuditLicInsTime.update",
				auditTimeVo);
		
		if(null!=updated)
		{
			auditTimeVo.setUpdatedInd(updated);
		}
		
		return auditTimeVo;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IPowerAuditorWorkflowDao#searchReasonCdQueueCounts(com.newco.marketplace.vo.provider.PowerAuditorSearchVO)
	 */
	public List<PowerAuditorSearchResultVO> searchReasonCdQueueCounts(PowerAuditorSearchVO searchVo) throws DBException {
		return (List<PowerAuditorSearchResultVO> )this.queryForList("powerauditor.reasoncd.counts.query", searchVo);
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IPowerAuditorWorkflowDao#getNextAuditFromReasonCdQueue(com.newco.marketplace.vo.provider.PowerAuditorSearchVO)
	 */
	public AuditVO getNextAuditFromReasonCdQueue(PowerAuditorSearchVO searchVo)throws DBException {
		return (AuditVO) this.queryForObject("powerauditor.reasoncd.fetchnext.credential.query", searchVo);
	}
	/**
	 * method to fetch the audit task id for corresponding resource cred id and vendor cred id
	 * @param id
	 * @param userInd
	 * @param credId
	 * @param auditLinkId
	 * @return
	 * @throws DelegateException
	 */

	public Integer getAuditTaskId(String id, boolean userInd,Integer credId,Integer auditLinkId)
			throws Exception {
		Integer auditTaskId = null;
		HashMap<String,Object> params = new HashMap<String,Object>();
		if(userInd){
			params.put("resourceId", id);
			params.put("resourceCredId", credId);
			params.put("auditLinkId", auditLinkId);
			auditTaskId = (Integer)queryForObject("startTime.provider.auditTaskId", params);
		}
		else{
			params.put("vendorId", id);
			params.put("vendorCredId", credId);
			params.put("auditLinkId", auditLinkId);
			auditTaskId = (Integer)queryForObject("startTime.vendor.auditTaskId", params);
		}		
		
		 return auditTaskId;
	}

	
	/**@description SL-20645->Checking for valid firm id
	 * @param firmId
	 * @return
	 * @throws DBException
	 */
	public Integer getValidFirmId(String firmId)
			throws Exception {
			Integer validFirmIdCount = null;
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("firmId", firmId);
			validFirmIdCount = (Integer)queryForObject("valid.firmId.auditTask", params);
		
		 return validFirmIdCount;
	}

	public Integer getVendorCredentialId(Integer id, Integer credTypeId,Integer credCategoryId,Integer auditTaskId)
			throws Exception {
			Integer credentialId = null;
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("id", id);
			params.put("credTypeId", credTypeId);
			params.put("credCategoryId", credCategoryId);
			params.put("auditTaskId", auditTaskId);
		
			credentialId = (Integer)queryForObject("credential.id.auditTask.vendor", params);
		 return credentialId;
	}
	
	public Integer getResourceCredentialId(Integer id, Integer credTypeId,Integer credCategoryId,Integer auditTaskId)
			throws Exception {
			Integer credentialId = null;
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("id", id);
			params.put("credTypeId", credTypeId);
			params.put("credCategoryId", credCategoryId);
			params.put("auditTaskId", auditTaskId);
			credentialId = (Integer)queryForObject("credential.id.auditTask.resource", params);
		
		 return credentialId;
	}
	
}
