/**
 *
 */
package com.newco.marketplace.business.businessImpl.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO;
import com.newco.marketplace.dto.vo.audit.AuditHistoryVO;
import com.newco.marketplace.dto.vo.audit.AuditNotesVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.persistence.iDao.audit.AuditDao;
import com.newco.marketplace.persistence.iDao.provider.ILoginDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.IPowerAuditorWorkflowDao;
import com.newco.marketplace.vo.audit.AuditTimeVO;
import com.newco.marketplace.vo.audit.AuditVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchResultVO;
import com.newco.marketplace.vo.provider.PowerAuditorSearchVO;
import com.newco.marketplace.vo.provider.PowerAuditorVendorInfoVO;

/**
 * @author hoza
 *
 */
public class PowerAuditorWorkflowBOImpl extends ABaseBO implements
		IPowerAuditorWorkflowBO {

	private ILookupDAO lookupDao;
	private IPowerAuditorWorkflowDao powerAuditorwrokflowDao;
	private AuditDao auditDao;
	private ILoginDao iLoginDao;
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO#getPrimaryIndustries()
	 */
	public List<LookupVO> getPrimaryIndustries() throws Exception {
		return lookupDao.loadPrimaryIndustry();
	}
	/**
	 * @return the lookupDao
	 */
	public ILookupDAO getLookupDao() {
		return lookupDao;
	}
	/**
	 * @param lookupDao the lookupDao to set
	 */
	public void setLookupDao(ILookupDAO lookupDao) {
		this.lookupDao = lookupDao;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO#getCredentialCategoryMap()
	 */
	public Map<Integer, List<LookupVO>> getCredentialCategoryMap()
			throws Exception {
		Map<Integer,List<LookupVO>> result = new HashMap<Integer,List<LookupVO>>();
		List<LookupVO> categories = lookupDao.getAllCredentailCategories();
		for(LookupVO vo : categories) {
			Integer typeid = new Integer(vo.getType());
			if(!result.containsKey(typeid)){
				result.put(typeid, new ArrayList<LookupVO>());
			}
			result.get(typeid).add(vo);
		}

		return result;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO#getCredentialTypes()
	 */
	public List<LookupVO> getCredentialTypes() throws Exception {
		return lookupDao.getCredentialTypes();
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO#getAuditableItemsCount(com.newco.marketplace.vo.provider.PowerAuditorSearchVO)
	 */
	public List<PowerAuditorSearchResultVO> getAuditableItemsCount(
			PowerAuditorSearchVO searchVo) throws Exception {

		searchVo.setExcludedQueueNames(getExcludedQueues());
		searchVo.setStickyQueueNames(getStickyQueues());
		searchVo.setPrimaryQueueNames(getPrimaryQueues());
		List<PowerAuditorSearchResultVO> result = powerAuditorwrokflowDao.searchQueueCounts(searchVo);
		updatePrimaryQueues(result,searchVo);
		List<PowerAuditorSearchResultVO> reasonCdResult = powerAuditorwrokflowDao.searchReasonCdQueueCounts(searchVo);
		for(PowerAuditorSearchResultVO auditorSearchResultVO : reasonCdResult){
			result.add(auditorSearchResultVO);
		}			
		updateStickyGuys(result,searchVo);
		
		return result;
	}

	private void updateStickyGuys(List<PowerAuditorSearchResultVO> result,PowerAuditorSearchVO searchVo){
		for(String queueName : searchVo.getStickyQueueNames()) {
			if(!isQueueNameAvailable(result,queueName)){
				result.add(getStickyQueueResult(queueName));
			}

		}
	}
	
	private void updatePrimaryQueues(List<PowerAuditorSearchResultVO> result,PowerAuditorSearchVO searchVo){
		for(String queueName : searchVo.getPrimaryQueueNames()) {
			if(!isQueueNameAvailable(result,queueName)){
				result.add(getStickyQueueResult(queueName));
			}

		}
	}

	private PowerAuditorSearchResultVO getStickyQueueResult(String queueName){
		PowerAuditorSearchResultVO  vo = new PowerAuditorSearchResultVO();
			vo.setAuditableItems(0L);
			vo.setAverageAge(0d);
			vo.setWorkflowQueueName(queueName);
		return vo;
	}

	private boolean isQueueNameAvailable(List<PowerAuditorSearchResultVO> resultList,String queueName) {
		boolean result = false;
		for(PowerAuditorSearchResultVO  vo : resultList) {
			if(vo.getWorkflowQueueName().equals(queueName)){
				result = true;
			}
		}
		return result;
	}

	private List<String> getStickyQueues() {
		List<String> stcikyQueues = new ArrayList<String>();
		stcikyQueues.add(AuditStatesInterface.VENDOR_CREDENTIAL);
		stcikyQueues.add(AuditStatesInterface.RESOURCE_CREDENTIAL);
		stcikyQueues.add(AuditStatesInterface.TEN_DAY_EXCEPTION);
		stcikyQueues.add(AuditStatesInterface.ENDORSEMENT);
		stcikyQueues.add(AuditStatesInterface.CANCELLATION_NOTICE);
		return stcikyQueues;
	}
	private List<String> getPrimaryQueues() {
		List<String> primaryQueues = new ArrayList<String>();
		primaryQueues.add(AuditStatesInterface.VENDOR_CREDENTIAL);
		primaryQueues.add(AuditStatesInterface.RESOURCE_CREDENTIAL);
		return primaryQueues;
	}
	
	private List<String> getExcludedQueues(){
		List<String> excludedQueues = new ArrayList<String>();
		excludedQueues.add(AuditStatesInterface.VENDOR_BACKGROUND_CHECK);
		excludedQueues.add(AuditStatesInterface.RESOURCE);
		excludedQueues.add(AuditStatesInterface.VENDOR);
		excludedQueues.add(AuditStatesInterface.RESOURCE_BACKGROUND_CHECK);
		return excludedQueues;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO#getAuditHistoryForVendor(java.lang.Integer)
	 */
	public List<AuditHistoryVO> getAuditHistoryForVendor(AuditHistoryVO historyVO)
			throws Exception {
		return powerAuditorwrokflowDao.getAuditHistory(historyVO);
	}
	/*private PowerAuditorSearchVO buildSearchCriteria(PowerAuditorSearchVO searchVo) {
		if(searchVo != null) {
			searchVo.setPrimaryIndustry(1);

		}else
			return null;

	}*/
	private List<PowerAuditorSearchResultVO> getDummyAudiatableItems() {
		List<PowerAuditorSearchResultVO> list = new ArrayList<PowerAuditorSearchResultVO>();
		PowerAuditorSearchResultVO vo = new PowerAuditorSearchResultVO();
		vo.setWorkflowQueueName("Company Credential");
		vo.setAuditableItems(new Long(1542));
		vo.setAverageAge(177.6223);
		list.add(vo);
		vo = new PowerAuditorSearchResultVO();
		vo.setWorkflowQueueName("Team Member Credential");
		vo.setAuditableItems(new Long(311));
		vo.setAverageAge(158.8238);
		list.add(vo);
		vo = new PowerAuditorSearchResultVO();
		vo.setWorkflowQueueName("Team Member Background Check");
		vo.setAuditableItems(new Long(12329));
		vo.setAverageAge(107.5419);
		list.add(vo);



		return list;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO#getCredentialCategoryByType()
	 */
	public List<LookupVO> getCredentialCategoryByType(Integer typeId) throws Exception {
		return lookupDao.getCredentailCategoriesByType(typeId);
	}
	/**
	 * @return the powerAuditorwrokflowDao
	 */
	public IPowerAuditorWorkflowDao getPowerAuditorwrokflowDao() {
		return powerAuditorwrokflowDao;
	}
	/**
	 * @param powerAuditorwrokflowDao the powerAuditorwrokflowDao to set
	 */
	public void setPowerAuditorwrokflowDao(
			IPowerAuditorWorkflowDao powerAuditorwrokflowDao) {
		this.powerAuditorwrokflowDao = powerAuditorwrokflowDao;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO#getAuditNotesForVendor(java.lang.Integer)
	 */
	public List<AuditNotesVO> getAuditNotesForVendor(AuditNotesVO notesVo)
			throws Exception {
		return powerAuditorwrokflowDao.getAuditNotes(notesVo);
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO#getNextAuditFromQueue(com.newco.marketplace.vo.provider.PowerAuditorSearchVO)
	 */
	public AuditVO getNextAuditFromQueue(PowerAuditorSearchVO searchVo)
			throws Exception {
		AuditVO vo = null;
		if(searchVo.getWorkFlowQueue().equalsIgnoreCase(AuditStatesInterface.TEN_DAY_EXCEPTION)||
				searchVo.getWorkFlowQueue().equalsIgnoreCase(AuditStatesInterface.ENDORSEMENT)||
				searchVo.getWorkFlowQueue().equalsIgnoreCase(AuditStatesInterface.CANCELLATION_NOTICE)){
			vo = powerAuditorwrokflowDao.getNextAuditFromReasonCdQueue(searchVo);
		}
		else{
			vo = powerAuditorwrokflowDao.getNextAuditFromQueue(searchVo);
		}
		if(vo != null) {
			vo.setReviewedDate(new Date());
			auditDao.updateReviewDate(vo);
		}
		return vo;
	}
	/**
	 * @return the auditDao
	 */
	public AuditDao getAuditDao() {
		return auditDao;
	}
	/**
	 * @param auditDao the auditDao to set
	 */
	public void setAuditDao(AuditDao auditDao) {
		this.auditDao = auditDao;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO#getVendorInfo(java.lang.Integer)
	 */
	public PowerAuditorVendorInfoVO getVendorInfo(Integer vendorId)
			throws Exception {
		return powerAuditorwrokflowDao.getVendorInfo(vendorId);
	}
	public ILoginDao getiLoginDao() {
		return iLoginDao;
	}
	public void setiLoginDao(ILoginDao iLoginDao) {
		this.iLoginDao = iLoginDao;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO#getResourceCredentialCategoryByType(java.lang.Integer)
	 */
	public List<LookupVO> getResourceCredentialCategoryByType(Integer typeId)
			throws Exception {

		return lookupDao.getResourceCredentailCategoriesByType(typeId);
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO#getResourceCredentialCategoryMap()
	 */
	public Map<Integer, List<LookupVO>> getResourceCredentialCategoryMap()
			throws Exception {
		Map<Integer,List<LookupVO>> result = new HashMap<Integer,List<LookupVO>>();
		List<LookupVO> categories = lookupDao.getAllResourceCredentailCategories();
		for(LookupVO vo : categories) {
			Integer typeid = new Integer(vo.getType());
			if(!result.containsKey(typeid)){
				result.put(typeid, new ArrayList<LookupVO>());
			}
			result.get(typeid).add(vo);
		}

		return result;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IPowerAuditorWorkflowBO#getResourceCredentialTypes()
	 */
	public List<LookupVO> getResourceCredentialTypes() throws Exception {
		return lookupDao.getResourceCredentialTypes();
	}
	/**
	 * method to put an etry for an audit task with start time
	 * @param auditTimeVo
	 * @return
	 * @throws DelegateException
	 */
	
	public AuditTimeVO saveAuditTime(AuditTimeVO auditTimeVo) throws Exception {
		Integer agentId =null;
		if(StringUtils.isNotBlank(auditTimeVo.getAgentName())){
		 agentId= iLoginDao.retrieveResourceIDByUserName(auditTimeVo.getAgentName());
		}
		if(null != agentId){
		auditTimeVo.setAgentId(agentId);
		}
		AuditTimeVO vo = powerAuditorwrokflowDao.saveAuditTime(auditTimeVo);
		return vo;
		
	}
	/**
	 * method to update the audit task with end time
	 * @param auditTimeVo
	 * @return
	 * @throws DelegateException
	 */
	public AuditTimeVO updateAuditTime(AuditTimeVO auditTimeVo) throws Exception {
		AuditTimeVO vo = powerAuditorwrokflowDao.updateAuditTime(auditTimeVo);
		return vo;
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
	public Integer getAuditTaskId(String id, boolean userInd,Integer credId, Integer auditLinkId)throws Exception {
		Integer auditTaskId = powerAuditorwrokflowDao.getAuditTaskId(id,userInd,credId,auditLinkId);
		return auditTaskId;
	}
	
	//SL-20645->Checking for valid firm id
	public Integer getValidFirmId(String firmId)throws Exception {
		Integer validFirmIdCount = powerAuditorwrokflowDao.getValidFirmId(firmId);
		return validFirmIdCount;
	}

	public Integer getVendorCredentialId(Integer id, Integer credTypeId,Integer credCategoryId,Integer auditTaskId)throws Exception {
		
		Integer credentialId = powerAuditorwrokflowDao.getVendorCredentialId(id,credTypeId,credCategoryId,auditTaskId);
		return credentialId;
	}

public Integer getResourceCredentialId(Integer id, Integer credTypeId,Integer credCategoryId,Integer auditTaskId)throws Exception {
		
		Integer credentialId = powerAuditorwrokflowDao.getResourceCredentialId(id,credTypeId,credCategoryId,auditTaskId);
		return credentialId;
	}
	
}
