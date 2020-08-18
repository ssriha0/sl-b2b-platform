/**
 *
 */
package com.newco.marketplace.business.iBusiness.provider;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.audit.AuditHistoryVO;
import com.newco.marketplace.dto.vo.audit.AuditNotesVO;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchResultVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchVO;
import com.newco.marketplace.vo.provider.PowerAuditorVendorInfoVO;

/**
 * @author hoza
 *
 */
public interface IPowerAuditorWorkflowBO {

	public List<LookupVO> getPrimaryIndustries() throws Exception;
	public List<LookupVO> getCredentialTypes() throws Exception;
	public Map<Integer,List<LookupVO>> getCredentialCategoryMap() throws Exception;
	public List<LookupVO> getResourceCredentialTypes() throws Exception;
	public Map<Integer,List<LookupVO>> getResourceCredentialCategoryMap() throws Exception;
	public List<LookupVO> getCredentialCategoryByType(Integer typeId) throws Exception;
	public List<LookupVO> getResourceCredentialCategoryByType(Integer typeId) throws Exception;
	public List<PowerAuditorSearchResultVO> getAuditableItemsCount(PowerAuditorSearchVO searchVo) throws Exception;
	public List<AuditHistoryVO> getAuditHistoryForVendor(AuditHistoryVO vendorId) throws Exception;
	public List<AuditNotesVO> getAuditNotesForVendor(AuditNotesVO vendorId) throws Exception;
	public AuditVO getNextAuditFromQueue(PowerAuditorSearchVO searchVo) throws Exception;
	public PowerAuditorVendorInfoVO getVendorInfo(Integer vendorId) throws Exception;
	/**
	 * method to put an entry for an audit task with start time
	 * @param auditTimeVo
	 * @return
	 * @throws DelegateException
	 */
	public AuditTimeVO saveAuditTime(AuditTimeVO auditTimeVo)throws Exception;
	/**
	 * method to update the audit task with end time
	 * @param auditTimeVo
	 * @return
	 * @throws DelegateException
	 */
	public AuditTimeVO updateAuditTime(AuditTimeVO auditTimeVo)throws Exception;
	/**
	 * method to fetch the audit task id for corresponding resource cred id and vendor cred id
	 * @param id
	 * @param userInd
	 * @param credId
	 * @param auditLinkId
	 * @return
	 * @throws DelegateException
	 */
	public Integer getAuditTaskId(String id,boolean userInd, Integer credId, Integer auditLinkId)throws Exception;
	
	
	/**@description SL-20645->Checking for valid firm id
	 * @param firmId
	 * @return
	 * @throws Exception 
	 */
	public Integer getValidFirmId(String firmId) throws Exception;

	/**@description Checking for vendor credential id
	 * @param id
	 * @param credTypeId
	 * @param credCategoryId
	 * @return
	 * @throws Exception 
	 */
	public Integer getVendorCredentialId(Integer id, Integer credTypeId,Integer credCategoryId,Integer auditTaskId) throws Exception;
	
	public Integer getResourceCredentialId(Integer id, Integer credTypeId,Integer credCategoryId,Integer auditTaskId) throws Exception;
	
}
