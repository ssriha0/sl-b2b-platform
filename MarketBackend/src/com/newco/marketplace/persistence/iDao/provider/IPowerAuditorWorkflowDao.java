/**
 *
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.dto.vo.audit.AuditHistoryVO;
import com.newco.marketplace.dto.vo.audit.AuditNotesVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchResultVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchVO;
import com.newco.marketplace.vo.provider.PowerAuditorVendorInfoVO;

/**
 * @author hoza
 *
 */
public interface IPowerAuditorWorkflowDao {
	public List<PowerAuditorSearchResultVO> searchQueueCounts(PowerAuditorSearchVO searchCriteria) throws DBException;
	public List<AuditHistoryVO> getAuditHistory(AuditHistoryVO historyvo) throws DBException;
	public List<AuditNotesVO> getAuditNotes(AuditNotesVO notesvo) throws DBException;
	public AuditVO getNextAuditFromQueue(PowerAuditorSearchVO searchCriteria) throws DBException;
	public PowerAuditorVendorInfoVO getVendorInfo(Integer vendorId) throws DBException;
	/**
	 * method to put an etry for an audit task with start time
	 * @param auditTimeVo
	 * @return
	 * @throws DelegateException
	 */
	public AuditTimeVO saveAuditTime(AuditTimeVO auditTimeVo) throws Exception;
	/**
	 * method to update the audit task with end time
	 * @param auditTimeVo
	 * @return
	 * @throws DelegateException
	 */
	public AuditTimeVO updateAuditTime(AuditTimeVO auditTimeVo)throws Exception;
	/**
	 * method to fetch the count of audit tasks for the new reason codes under approved status
	 * @param searchVo
	 * @return
	 * @throws DBException
	 */
	public List<PowerAuditorSearchResultVO> searchReasonCdQueueCounts(PowerAuditorSearchVO searchVo)throws DBException;
	/**
	 *  method to fetch the details of audit tasks for the new reason codes under approved status
	 * @param searchVo
	 * @return
	 * @throws DBException
	 */
	public AuditVO getNextAuditFromReasonCdQueue(PowerAuditorSearchVO searchVo)throws DBException;
	/**
	 * method to fetch the audit task id for corresponding resource cred id and vendor cred id
	 * @param id
	 * @param userInd
	 * @param credId
	 * @param auditLinkId
	 * @return
	 * @throws DelegateException
	 */
	public Integer getAuditTaskId(String id, boolean userInd,Integer credId, Integer auditLinkId)throws Exception;
	

	/** @description SL-20645->Checking for valid firm id
	 * @param firmId
	 * @return
	 * @throws DBException
	 */
	public Integer getValidFirmId(String firmId)throws Exception;

	public Integer getVendorCredentialId(Integer id, Integer credTypeId,Integer credCategoryId,Integer auditTaskId)throws Exception;
	
	public Integer getResourceCredentialId(Integer id, Integer credTypeId,Integer credCategoryId,Integer auditTaskId)throws Exception;
	
}
