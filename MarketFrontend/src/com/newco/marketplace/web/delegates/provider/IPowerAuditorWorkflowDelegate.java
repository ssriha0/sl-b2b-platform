/**
 *
 */
package com.newco.marketplace.web.delegates.provider;

import java.util.List;

import com.newco.marketplace.dto.vo.audit.AuditHistoryVO;
import com.newco.marketplace.dto.vo.audit.AuditNotesVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchResultVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchVO;
import com.newco.marketplace.vo.provider.PowerAuditorVendorInfoVO;

/**
 * @author ccarle5
 *
 */
public interface IPowerAuditorWorkflowDelegate {

	public List<LookupVO> getPrimaryIndustries() throws DelegateException;
	public List<LookupVO> getCredentialTypes() throws DelegateException;
	public List<LookupVO> getCredentialCategoryByType(Integer typeId) throws DelegateException;
	public List<LookupVO> getResourceCredentialCategoryByType(Integer typeId) throws DelegateException;
	public List<LookupVO> getResourceCredentialTypes() throws DelegateException;
	public List<PowerAuditorSearchResultVO> getAuditableItemsCount(PowerAuditorSearchVO searchVo) throws DelegateException;
	public List<AuditHistoryVO> getAuditHistoryForVendor(AuditHistoryVO historyVO) throws DelegateException;
	public List<AuditNotesVO> getAuditNotesForVendor(AuditNotesVO notesVo) throws DelegateException;
	public AuditVO getNextAuditFromQueue(PowerAuditorSearchVO searchVo) throws DelegateException;
	public PowerAuditorVendorInfoVO getVendorInfo(Integer vendorId) throws DelegateException;
	/**
	 * method to put an etry for an audit task with start time
	 * @param auditTimeVo
	 * @return
	 * @throws DelegateException
	 */
	public AuditTimeVO saveAuditTime(AuditTimeVO auditTimeVo) ;
	/**
	 * method to update the audit task with end time
	 * @param auditTimeVo
	 * @return
	 * @throws DelegateException
	 */
	public AuditTimeVO updateAuditTime(AuditTimeVO auditTimeVo) ;
	/**
	 * method to fetch the audit task id for corresponding resource cred id and vendor cred id
	 * @param id
	 * @param userInd
	 * @param credId
	 * @param auditLinkId
	 * @return
	 * @throws DelegateException
	 */
	public Integer getAuditTaskId(String id,boolean userInd,Integer credId,Integer auditLinkId);
	
	/** @description SL-20645->Checking for valid firm id
	 * @param firmId
	 * @return
	 * @throws DelegateException
	 */
	public Integer getValidFirmId(String firmId);
	public Integer getVendorCredentialId(Integer id, Integer credTypeId,Integer credCategoryId,Integer auditTaskId);
	public Integer getResourceCredentialId(Integer id, Integer credTypeId,Integer credCategoryId,Integer auditTaskId);
}
