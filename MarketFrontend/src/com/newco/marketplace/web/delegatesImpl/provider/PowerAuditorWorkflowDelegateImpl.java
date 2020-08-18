/**
 *
 */
package com.newco.marketplace.web.delegatesImpl.provider;

import java.util.List;

import org.apache.log4j.Logger;


import com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO;
import com.newco.marketplace.dto.vo.audit.AuditHistoryVO;
import com.newco.marketplace.dto.vo.audit.AuditNotesVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchResultVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchVO;
import com.newco.marketplace.vo.provider.PowerAuditorVendorInfoVO;
import com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate;

/**
 * @author ccarle5
 *
 */
public class PowerAuditorWorkflowDelegateImpl implements
		IPowerAuditorWorkflowDelegate {
	private IPowerAuditorWorkflowBO powerAuditorWorkflowBO;
	private static final Logger localLogger = Logger.getLogger(PowerAuditorWorkflowDelegateImpl.class.getName());

	public List<LookupVO> getPrimaryIndustries() throws DelegateException
	{
		List<LookupVO> primaryIndustries;

		try {
			primaryIndustries = powerAuditorWorkflowBO.getPrimaryIndustries();
		}
		catch (Exception e) {
			localLogger.error("Delegate could not retrive info for Primary Industries", e);
			throw new DelegateException("Delegate could not retrive info for Primary Industries, exception: ",e);
		}


		return primaryIndustries;
	}

	public List<LookupVO> getCredentialTypes() throws DelegateException
	{
		List<LookupVO> credentialTypes;

		try {
			credentialTypes = powerAuditorWorkflowBO.getCredentialTypes();
		}
		catch (Exception e) {
			localLogger.error("Delegate could not retrive info for Credential Types", e);
			throw new DelegateException("Delegate could not retrive info for Credential Types, exception: ",e);
		}


		return credentialTypes;
	}

	public List<LookupVO> getCredentialCategoryByType(Integer typeId) throws DelegateException
	{
		List<LookupVO> credentialCategory;

		try {
			credentialCategory = powerAuditorWorkflowBO.getCredentialCategoryByType(typeId);
		}
		catch (Exception e) {
			localLogger.error("Delegate could not retrive info for Credential Category", e);
			throw new DelegateException("Delegate could not retrive info for Credential Category, exception: ",e);
		}


		return credentialCategory;
	}
	
	public List<LookupVO> getResourceCredentialTypes() throws DelegateException
	{
		List<LookupVO> resourceCredentialTypes;

		try {
			resourceCredentialTypes = powerAuditorWorkflowBO.getResourceCredentialTypes();
		}
		catch (Exception e) {
			localLogger.error("Delegate could not retrive info for Resource Credential Types", e);
			throw new DelegateException("Delegate could not retrive info for Resource Credential Types, exception: ",e);
		}


		return resourceCredentialTypes;
	}
	
	public List<LookupVO> getResourceCredentialCategoryByType(Integer typeId) throws DelegateException
	{
		List<LookupVO> resourceCredentialCategory;

		try {
			resourceCredentialCategory = powerAuditorWorkflowBO.getResourceCredentialCategoryByType(typeId);
		}
		catch (Exception e) {
			localLogger.error("Delegate could not retrive info for Resource Credential Category", e);
			throw new DelegateException("Delegate could not retrive info for Resource Credential Category, exception: ",e);
		}


		return resourceCredentialCategory;
	}

	public List<PowerAuditorSearchResultVO> getAuditableItemsCount(PowerAuditorSearchVO searchVo) throws DelegateException
	{
		List<PowerAuditorSearchResultVO> powerAuditorSearchResult;

		try {
			powerAuditorSearchResult = powerAuditorWorkflowBO.getAuditableItemsCount(searchVo);
		}
		catch (Exception e) {
			localLogger.error("Delegate could not retrive info for Power Auditor Search Result", e);
			throw new DelegateException("Delegate could not retrive info for Power Auditor Search Result, exception: ",e);
		}


		return powerAuditorSearchResult;
	}

	public List<AuditHistoryVO> getAuditHistoryForVendor(AuditHistoryVO historyVO) throws DelegateException
	{
		List<AuditHistoryVO> auditHistoryVO;

		try {
			auditHistoryVO = powerAuditorWorkflowBO.getAuditHistoryForVendor(historyVO);
		}
		catch (Exception e) {
			localLogger.error("Delegate could not retrive info for AuditHistoryForVendor", e);
			throw new DelegateException("Delegate could not retrive info for AuditHistoryForVendor, exception: ",e);
		}

		return auditHistoryVO;
	}

	public List<AuditNotesVO> getAuditNotesForVendor(AuditNotesVO notesVo) throws DelegateException
	{
		List<AuditNotesVO> auditNotesVO;

		try {
			auditNotesVO = powerAuditorWorkflowBO.getAuditNotesForVendor(notesVo);
		}
		catch (Exception e) {
			localLogger.error("Delegate could not retrive info for AuditNotesForVendor", e);
			throw new DelegateException("Delegate could not retrive info for AuditNotesForVendor, exception: ",e);
		}

		return auditNotesVO;

	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.provider.IPowerAuditorWorkflowDelegate#getNextAuditFromQueue(com.newco.marketplace.vo.provider.PowerAuditorSearchVO)
	 */
	public AuditVO getNextAuditFromQueue(PowerAuditorSearchVO searchVo)
			throws DelegateException {
		// TODO Auto-generated method stub
		AuditVO vo = new AuditVO();
		 try {
			vo = powerAuditorWorkflowBO.getNextAuditFromQueue(searchVo);
		} catch (Exception e) {
			localLogger.error("Delegate could not retrive info for AuditNotesForVendor", e);
			throw new DelegateException("Delegate could not retrive info for AuditNotesForVendor, exception: ",e);
		}
		 return vo;
	}
	
	public PowerAuditorVendorInfoVO getVendorInfo(Integer vendorId) throws DelegateException {
		PowerAuditorVendorInfoVO vendVO = new PowerAuditorVendorInfoVO();
		try
		{
			vendVO = powerAuditorWorkflowBO.getVendorInfo(vendorId);
		}
		catch (Exception e)
		{
			localLogger.error("Delegate could not retrive info for getVendorInfo", e);
			throw new DelegateException("Delegate could not retrive info for getVendorInfo, exception: ",e);
		}
		
		return vendVO;		
	}
	/**
	 * method to put an etry for an audit task with start time
	 * @param auditTimeVo
	 * @return
	 * @throws DelegateException
	 */
	
	public AuditTimeVO saveAuditTime(AuditTimeVO auditTimeVo){
		// TODO Auto-generated method stub
			AuditTimeVO vo = new AuditTimeVO();
				try {
					vo = powerAuditorWorkflowBO.saveAuditTime(auditTimeVo);
				} catch (Exception e) {
					localLogger.error("Delegate could not retrive info for saveAuditTime", e);
					e.printStackTrace();
					
				}
				return vo;
	}
	/**
	 * method to update the audit task with end time
	 * @param auditTimeV
	 * @return
	 * @throws DelegateException
	 */
	
	public AuditTimeVO updateAuditTime(AuditTimeVO auditTimeVo){
		// TODO Auto-generated method stub
			AuditTimeVO vo = new AuditTimeVO();
				try {
					vo = powerAuditorWorkflowBO.updateAuditTime(auditTimeVo);
				} catch (Exception e) {
					localLogger.error("Delegate could not retrive info for updateAuditTime", e);
					e.printStackTrace();
				}
				return vo;
	}
	
	/**
	 * @return the powerAuditorWorkflowBO
	 */
	public IPowerAuditorWorkflowBO getPowerAuditorWorkflowBO() {
		return powerAuditorWorkflowBO;
	}

	/**
	 * @param powerAuditorWorkflowBO the powerAuditorWorkflowBO to set
	 */
	public void setPowerAuditorWorkflowBO(
			IPowerAuditorWorkflowBO powerAuditorWorkflowBO) {
		this.powerAuditorWorkflowBO = powerAuditorWorkflowBO;
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

	public Integer getAuditTaskId(String id,boolean userInd, Integer credId,Integer auditLinkId)  {
		
		Integer  auditTaskId = null;
		try {
			auditTaskId = powerAuditorWorkflowBO.getAuditTaskId(id,userInd,credId,auditLinkId);
		} catch (Exception e) {
			localLogger.error("Delegate could not retrive info for getAuditTaskId", e);
			e.printStackTrace();
		}
		return auditTaskId;
	}
	
	/** @description SL-20645->Checking for valid firm id
	 * @param firmId
	 * @return
	 * @throws DelegateException
	 */
	public Integer getValidFirmId(String firmId) {
		
		Integer  validFirmIdCount = null;
		try {
			validFirmIdCount = powerAuditorWorkflowBO.getValidFirmId(firmId);
		} catch (Exception e) {
			localLogger.error("Delegate could not retrive info for getValidFirmId", e);
			e.printStackTrace();
		}
		return validFirmIdCount;
	}

	public Integer getVendorCredentialId(Integer id, Integer credTypeId,Integer credCategoryId,Integer auditTaskId){
		Integer credId = 0;
		try {
			credId = powerAuditorWorkflowBO.getVendorCredentialId(id,credTypeId,credCategoryId,auditTaskId);
		} catch (Exception e) {
			localLogger.error("Delegate could not retrive info for getVendorCredentialId", e);
			e.printStackTrace();
		}
		return credId;
	}
	
	public Integer getResourceCredentialId(Integer id, Integer credTypeId,Integer credCategoryId,Integer auditTaskId){
		Integer credId = 0;
		try {
			credId = powerAuditorWorkflowBO.getResourceCredentialId(id,credTypeId,credCategoryId,auditTaskId);
		} catch (Exception e) {
			localLogger.error("Delegate could not retrive info for getResourceCredentialId", e);
			e.printStackTrace();
		}
		return credId;
	}
}
