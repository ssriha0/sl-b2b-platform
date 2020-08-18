/**
 * 
 */
package com.servicelive.spn.services.providermatch;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.sears.os.dao.impl.ABaseImplDao;
import com.servicelive.domain.spn.campaign.CampaignApprovalCriteria;
import com.servicelive.domain.spn.detached.ApprovalModel;
import com.servicelive.domain.spn.detached.ProviderFirmVO;
import com.servicelive.domain.spn.detached.ProviderMatchingCountsVO;
import com.servicelive.domain.spn.network.SPNApprovalCriteria;
import com.servicelive.spn.common.detached.CampaignInvitationVO;
import com.servicelive.spn.common.detached.CredentialsCriteriaVO;
import com.servicelive.spn.common.detached.MemberMaintenanceCriteriaVO;
import com.servicelive.spn.common.detached.MemberMaintenanceDetailsVO;
import com.servicelive.spn.common.detached.MemberMaintenanceProviderFirmVO;
import com.servicelive.spn.common.detached.MemberMaintenanceServiceProviderVO;
import com.servicelive.spn.common.detached.ProviderMatchApprovalCriteriaVO;
import com.servicelive.spn.common.detached.SpnDetailsVO;
import com.servicelive.spn.services.BaseServices;
import com.servicelive.spn.common.detached.DuplicateFirmsVO;
import com.servicelive.spn.common.detached.DuplicateProviderVO;

/**
 * @author hoza
 *
 */
public class ProviderMatchForApprovalServices extends ABaseImplDao {
	

	/**
	 * 
	 * @param criteriaVO 
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ProviderMatchingCountsVO> getProvidersCountVOsForApprovalCriteria(ProviderMatchApprovalCriteriaVO criteriaVO) throws Exception {
		return getSqlMapClient().queryForList("network.provider.match.getProviderCountsForApprovalCriteriaRefactored", criteriaVO);

	}

	/**
	 * 
	 * @param criteriaVO
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ProviderMatchingCountsVO> getProvidersCountVOsForInvitationCriteria(ProviderMatchApprovalCriteriaVO criteriaVO) throws Exception {
		return getSqlMapClient().queryForList("network.provider.match.getProviderCountsForInvitationCriteria", criteriaVO);

	}

	/**
	 * 
	 * @param criteriaVO
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ProviderFirmVO> getProviderFirmVOsForCampaign(ProviderMatchApprovalCriteriaVO criteriaVO) throws Exception {
		return getSqlMapClient().queryForList("network.provider.match.getProviderAdminsForCampaignEmailsRefactored", criteriaVO);

	}

	/**
	 * 
	 * @param spnCampaignMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ProviderFirmVO> getProviderFirmVOsForSpecificInviteFirmCampaign(Map spnCampaignMap) throws Exception {
		return getSqlMapClient().queryForList("network.provider.match.getProviderAdminsForSpecificFirmCampaignEmails", spnCampaignMap);
	}

	/**
	 * 
	 * @param spnId
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<SPNApprovalCriteria> getApprovalCriteriaForSPN(Integer spnId) throws Exception {
		return getSqlMapClient().queryForList("network.provider.match.getApprovalCriteriaForSPN", spnId);
	}

	/**
	 * 
	 * @param campaignId
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CampaignApprovalCriteria> getApprovalCriteriaForCampaign(Integer campaignId) throws Exception {
		return getSqlMapClient().queryForList("network.provider.match.getApprovalCriteriaForCampaign", campaignId);
	}

	/**
	 * 
	 * @param spnId
	 * @return ApprovalModel
	 * @throws Exception
	 */
	public ApprovalModel getApprovalModelForSPN(Integer spnId) throws Exception {
		ApprovalModel model = ApprovalCriteriaHelper.getApprovalModelFromCriteria(this.getApprovalCriteriaForSPN(spnId));
		return model;
	}

	/**
	 * 
	 * @param campaignId
	 * @return ApprovalModel
	 * @throws Exception
	 */
	public ApprovalModel getApprovalModelForCampaign(Integer campaignId) throws Exception {
		ApprovalModel model = ApprovalCriteriaHelper.getApprovalModelFromCriteria(this.getApprovalCriteriaForCampaign(campaignId));
		return model;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@SuppressWarnings("unused")
	protected void handleDates(Object entity) {
		// do nothing
	}

	/**
	 * 
	 * @param spnId
	 * @return ProviderMatchingCountsVO
	 * @throws Exception
	 */
	public ProviderMatchingCountsVO getProviderCountsForSPN(Integer spnId) throws Exception {
		ApprovalModel approvalModel = getApprovalModelForSPN(spnId);
		List<ProviderMatchingCountsVO> list = getProvidersCountVOsForApprovalCriteria(ApprovalCriteriaHelper.getCriteriaVOFromModel(approvalModel));
		return (list != null && list.size() > 0) ? list.get(0) : new ProviderMatchingCountsVO();
	}

	/**
	 * 
	 * @param campaignId
	 * @return ProviderMatchingCountsVO
	 * @throws Exception
	 */
	public ProviderMatchingCountsVO getProviderCountsForCampaign(Integer campaignId) throws Exception {
		ApprovalModel approvalModel = getApprovalModelForCampaign(campaignId);
		List<ProviderMatchingCountsVO> list = getProvidersCountVOsForApprovalCriteria(ApprovalCriteriaHelper.getCriteriaVOFromModel(approvalModel));
		return (list != null && list.size() > 0) ? list.get(0) : new ProviderMatchingCountsVO();
	}

	/**
	 * 
	 * @param invitationVO
	 * @return List
	 * @throws Exception
	 */
	public List<ProviderFirmVO> getListOfAdminsForCampaignInvitation(CampaignInvitationVO invitationVO) throws Exception {
		ApprovalModel approvalModel = getApprovalModelForCampaign(invitationVO.getCampaignId());
		ProviderMatchApprovalCriteriaVO criteriaVO = ApprovalCriteriaHelper.getCriteriaVOFromModel(approvalModel);
		criteriaVO.setCampaignId(invitationVO.getCampaignId());
		criteriaVO.setSpnId(invitationVO.getSpnId());
		criteriaVO.setIsAdminSearch(Boolean.TRUE);
		List<ProviderFirmVO> list = getProviderFirmVOsForCampaign(criteriaVO);
		return (list != null && list.size() > 0) ? list : new ArrayList<ProviderFirmVO>();
	}

	public List<ProviderFirmVO> getListOfAdminsForSpecificInviteFirmInvitation(Integer spnId, Integer campaignId) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("spnId", spnId);
		map.put("campaignId", campaignId);
		List<ProviderFirmVO> list = getProviderFirmVOsForSpecificInviteFirmCampaign(map);
		return (list != null && list.size() > 0) ? list : new ArrayList<ProviderFirmVO>();
	}

	/**
	 * @param spnId
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MemberMaintenanceProviderFirmVO> getCompliantFirmsForMemberMaintenance(Integer spnId,ProviderMatchApprovalCriteriaVO criteriaVO) throws Exception {
		criteriaVO.setSpnId(spnId);
		/*logger.info("calculating old compliance");
		List<MemberMaintenanceProviderFirmVO> result1 = getSqlMapClient().queryForList("network.provider.match.getCompliantFirmsForMemberMaintenanceOld", criteriaVO);
		if(null!=result1 && result1.size()>0)
		{
		logger.info("firmOldcompliance for spn ="+spnId+" "+result1.size());
		
		}
		else
		{
			logger.info("firmOldcompliance for spn is 0 for "+spnId);
	
		}*/
		List<MemberMaintenanceProviderFirmVO> result = getSqlMapClient().queryForList("network.provider.match.getCompliantFirmsForMemberMaintenance", criteriaVO);
		
		if(null!=result && result.size()>0)
		{
		logger.info("firmNewcompliance for spn ="+spnId+" "+result.size());
		
		}
		else
		{
			logger.info("firmNewcompliance for spn is 0 for "+spnId);
	
		}
		
		return result;
	}

	/**
	 * @param spnId
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MemberMaintenanceProviderFirmVO> getOutOfCompliantAndMemberProfileFirms(Integer spnId) throws Exception {
		List<MemberMaintenanceProviderFirmVO> result = getSqlMapClient().queryForList("network.provider.match.getOutOfCompliantAndMemberProfileFirmsForSpn", spnId);
		return result;
	}

	/**
	 * @param spnId
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MemberMaintenanceServiceProviderVO> getCompliantServiceProviderForMemberMaintenance(MemberMaintenanceCriteriaVO mmCriteriaVO,ProviderMatchApprovalCriteriaVO criteriaVO) throws Exception {
		if (mmCriteriaVO.getSpnId() == null) {
			//System.out.println("##### the spn should not be null in ProviderMatchForApprovalServices.getCompliantServiceProviderForMemberMaintenance");
			return new ArrayList<MemberMaintenanceServiceProviderVO>();
		}
		criteriaVO.setSpnId(mmCriteriaVO.getSpnId());
		criteriaVO.setSpecificProviderFirmId(mmCriteriaVO.getProviderFirmId());
//		logger.info("calculating old provider compliance");
//		List<MemberMaintenanceServiceProviderVO> result1 = getSqlMapClient().queryForList("network.provider.match.getCompliantServiceProviderForMemberMaintenanceOld", criteriaVO);
//		if(null!=result1 && result1.size()>0)
//		{
//		logger.info("providerOldcompliance for spn ="+mmCriteriaVO.getSpnId()+" "+result1.size());
//		/*for(int i=0;i<result1.size();i++)
//		{
//			logger.info(" provider Id  "+result.get(i).getServiceProviderId());	
//		}*/
//		}
//		else
//		{
//			logger.info("providerOldcompliance for spn is 0 for "+mmCriteriaVO.getSpnId());
//	
//		}
		List<MemberMaintenanceServiceProviderVO> result = getSqlMapClient().queryForList("network.provider.match.getCompliantServiceProviderForMemberMaintenance", criteriaVO);
		
		if(null!=result && result.size()>0)
		{
		logger.info("providerNewcompliance for spn ="+mmCriteriaVO.getSpnId()+" "+result.size());
		/*for(int i=0;i<result.size();i++)
		{
			logger.info(" provider Id  "+result.get(i).getServiceProviderId());	
		}*/
		}
		else
		{
			logger.info("providerNewcompliance for spn is 0 for "+mmCriteriaVO.getSpnId());
	
		}
		return result;
	}

	/**
	 * @param spnId
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MemberMaintenanceServiceProviderVO> getOutOfCompliantAndApprovedServiceProvider(MemberMaintenanceCriteriaVO mmCriteriaVO) throws Exception {
		List<MemberMaintenanceServiceProviderVO> result = getSqlMapClient().queryForList("network.provider.match.getOutOfCompliantAndApprovedServiceProvider", mmCriteriaVO);
		return result;
	}

	/**
	 * 
	 * @return List
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getAllSpnList() throws Exception {
		List<Integer> list = getSqlMapClient().queryForList("network.provider.match.getAllSpnList", null);
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteDuplicateMembersOfSpns(MemberMaintenanceCriteriaVO mmCriteriaVO) throws Exception {
		int result=0;
		Integer originalSPNId = (Integer)getSqlMapClient().queryForObject("network.provider.match.findAliasSPN", mmCriteriaVO.getSpnId());
		if(null!=originalSPNId){
			Integer aliasSPNId = mmCriteriaVO.getSpnId();
			//delete all the duplicate provider ids who are members of original and alias SPNs
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("originalSPNId", originalSPNId);
			map.put("aliasSPNId", aliasSPNId);
			logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: before delete of providers");
			int count = getSqlMapClient().delete("network.provider.match.deleteDuplicateProvidersOfAliasSpns", map);
			logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: after delete of providers: "+count);
			
			//Find the distinct provider firm ids of alias SPN from spnet_provider_firm_state table
			List<DuplicateFirmsVO> aliasSPNPFIds= new ArrayList<DuplicateFirmsVO>();
			aliasSPNPFIds = (ArrayList<DuplicateFirmsVO>)getSqlMapClient().queryForList("network.provider.match.findFirmIdsofAliasSPN", map);
			logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: list of aliasSPNPFIds: "+aliasSPNPFIds);
			
			//Find the distinct provider firm ids of alias SPN from spnet_service_provider_state table
			List<DuplicateFirmsVO> providerFirmIds = new ArrayList<DuplicateFirmsVO>();
			providerFirmIds = (ArrayList<DuplicateFirmsVO>)getSqlMapClient().queryForList("network.provider.match.findFirmIdsofAliasSPNusingSSPS", map);
			logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: list of providerFirmIds: "+providerFirmIds);
			
			//Find the member provider firms from original SPN
			List<DuplicateFirmsVO> providerFirmIdsFromOriginalSPN = new ArrayList<DuplicateFirmsVO>();
			providerFirmIdsFromOriginalSPN = (ArrayList<DuplicateFirmsVO>)getSqlMapClient().queryForList("network.provider.match.findFirmIdsofOriginalSPN", map);
			logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: list of originalProviderFirmIds: "+providerFirmIdsFromOriginalSPN);
			
			//if there are no member firms in the original SPN don't delete anyone from the alias SPN
			if(null!=providerFirmIdsFromOriginalSPN && !(providerFirmIdsFromOriginalSPN.isEmpty()) ){
				
				if(null!=aliasSPNPFIds && !(aliasSPNPFIds.isEmpty())){
					//VO for removing firms
					DuplicateFirmsVO removalVO = new DuplicateFirmsVO();
					removalVO.setAliasSPNId(aliasSPNId);
					List<DuplicateFirmsVO> copyAliasSPNPFIds= new ArrayList<DuplicateFirmsVO>(aliasSPNPFIds);
					
					if(null==providerFirmIds || providerFirmIds.isEmpty()){
						//delete the firms from the alias SPN
						List<Integer> ids = new ArrayList<Integer>();
						//Before deleting them, need to check whether they are members of original SPN or not
						if(null!=providerFirmIdsFromOriginalSPN && !(providerFirmIdsFromOriginalSPN.isEmpty()) ){
							for(DuplicateFirmsVO pfId : aliasSPNPFIds){
								for(DuplicateFirmsVO pfOrigSPN : providerFirmIdsFromOriginalSPN){
									//checking if the firm to be removed is already a member of original SPN
									if(pfId.getProviderFirmId().equals(pfOrigSPN.getProviderFirmId())){
										ids.add(pfId.getProviderFirmId());									
									}								
								}							
							}
							removalVO.setToBeRemovedFirmsList(ids);
							logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: list of providerFirmIds before first del: "+aliasSPNPFIds);
							int deletedCount1 = getSqlMapClient().delete("network.provider.match.deleteDuplicatefirmsOfAliasSpns", removalVO);
							logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: list of providerFirmIds after first del: " +deletedCount1);						
						}//else don't delete any member as they are members of alias SPN without any approved service providers
						
					}else{
						logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: before for loop: ");
						//deleting all the firms whose service providers were already deleted and are members of original SPN
						for(DuplicateFirmsVO provFirmId : aliasSPNPFIds){
							logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: inside loop1: "+provFirmId.getProviderFirmId());	
							for(DuplicateFirmsVO pfId : providerFirmIds){
								logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: inside loop2: "+pfId.getProviderFirmId());
								//Remove the firms who has service providers in the alias SPN
								if(provFirmId.getProviderFirmId().equals(pfId.getProviderFirmId())){
									copyAliasSPNPFIds.remove(provFirmId);
								}
							}
						}
						logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: after for loop: ");
						//delete the firms from the alias SPN after checking whether they are members of original SPN
						if(null!=copyAliasSPNPFIds && !(copyAliasSPNPFIds.isEmpty())){
							
							List<Integer> ids1 = new ArrayList<Integer>();							
							if(null!=providerFirmIdsFromOriginalSPN && !(providerFirmIdsFromOriginalSPN.isEmpty()) ){
								for(DuplicateFirmsVO pfId : copyAliasSPNPFIds){
									for(DuplicateFirmsVO pfOrigSPN : providerFirmIdsFromOriginalSPN){
										//checking if the firm to be removed is already a member of original SPN
										if(pfId.getProviderFirmId().equals(pfOrigSPN.getProviderFirmId())){
											logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: list of copyAliasSPNPFIds: "+pfId.getProviderFirmId());
											ids1.add(pfId.getProviderFirmId());	
										}
									}								
								}
								removalVO.setToBeRemovedFirmsList(ids1);
								logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: list of providerFirmIds before sec del: "+copyAliasSPNPFIds);
								int deletedCount1 = getSqlMapClient().delete("network.provider.match.deleteDuplicatefirmsOfAliasSpns", removalVO);
								logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: list of providerFirmIds after sec del: " +deletedCount1);
							}//else don't delete from alias SPN as there are they are not members in original SPN				
						}										
					}				
				}
				//Now deleting firms is in OOC state in Alias SPN but is a member of original SPN. In this case we are giving priority to the
				//status of original SPN as he is a member.
				//int deletedCount2=getSqlMapClient().delete("network.provider.match.deleteDuplicateOOCfirmsOfAliasSpns", map);
				//logger.info("Inside deleteDuplicateMembersOfSpns of providerMatchForApprovalServices: list of providerFirmIds after last del: " +deletedCount2);
			}			
		}
		return result;
		//return getSqlMapClient().delete("network.provider.match.deleteDuplicateMembersOfSpns", mmCriteriaVO);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void  deleteProviderCredentialStatus(Integer spnId)  {
		try
		{
		 getSqlMapClient().delete("network.provider.match.deleteProviderCredentialStatus",spnId);
		}
		catch(Exception e)
		{
			logger.info(e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteFirmCredentialStatus(Integer spnId)  {
		try
		{
		 getSqlMapClient().delete("network.provider.match.deleteFirmCredentialStatus",spnId);
		}
		catch(Exception e)
		{
			logger.info(e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteFirmCredentialStatusWorkersComp(Integer spnId)  {
		try
		{
		 getSqlMapClient().delete("network.provider.match.deleteFirmCredentialStatusWorkersComp",spnId);
		}
		catch(Exception e)
		{
			logger.info(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getFirmCredentialDetails(CredentialsCriteriaVO credentialsCriteriaVO) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getFirmCredentialDetails", credentialsCriteriaVO);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e);

			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getFirmCredentialDetailsOutofCompliant(CredentialsCriteriaVO credentialsCriteriaVO) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getFirmCredentialDetailsOutofCompliant", credentialsCriteriaVO);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e);

			return null;
		}
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getProviderCredentialDetails(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getProviderCredentialDetails", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getProviderCredentialDetailsOutOfCompliant(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getProviderCredentialDetailsOutOfCompliant", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getPreviousFirmCredentialDetails(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getPreviousFirmCredentialDetails", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getPreviousFirmCredentialDetailsOutOfCompliant(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getPreviousFirmCredentialDetailsOutOfCompliant", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getPreviousFirmCredentialDetailsForCriteria(Integer luSpnApprovalCriteriaId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getPreviousFirmCredentialDetailsForCriteria", luSpnApprovalCriteriaId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getFirmCredentialExceptions(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getFirmCredentialExceptions", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getResourceCredentialExceptions(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getResourceCredentialExceptions", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return null;
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getPreviousProviderCredentialDetails(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getPreviousProviderCredentialDetails", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getPreviousProviderCredentialDetailsOutOfCompliant(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getPreviousProviderCredentialDetailsOutOfCompliant", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getPreviousProviderCredentialDetailsForCriteria(Integer luSpnApprovalCriteriaId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getPreviousProviderCredentialDetailsForCriteria", luSpnApprovalCriteriaId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getPreviousProviderCredDetailsForCriteriaForSpn(MemberMaintenanceCriteriaVO memberMaintenanceCriteriaVO) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getPreviousProviderCredDetailsForCriteriaForSpn", memberMaintenanceCriteriaVO);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getPreviousFirmCredDetailsForCriteriaForSpn(MemberMaintenanceCriteriaVO memberMaintenanceCriteriaVO) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getPreviousFirmCredDetailsForCriteriaForSpn", memberMaintenanceCriteriaVO);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer getPreviousFirmCredentialDetailsCount() throws Exception {
		try
		{
			Integer count = (Integer) getSqlMapClient().queryForObject("network.provider.match.getPreviousFirmCredentialDetailsCount",null);
		return count;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer getPreviousProviderCredentialDetailsCount() throws Exception {
		try
		{
		Integer count = (Integer) getSqlMapClient().queryForObject("network.provider.match.getPreviousProviderCredentialDetailsCount", null);
		return count;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return null;
		}
	}
	

	public  void insertFirmCredentialStatus(List<MemberMaintenanceDetailsVO> firmCredentialList) throws Exception {
		//splitBatchInsert("network.provider.match.firmCredentialStatusInsert", firmCredentialList);
		long start1=System.currentTimeMillis();
		splitInsert("network.provider.match.firmCredentialStatusSplitInsert", firmCredentialList);
		long end1=System.currentTimeMillis();
		logger.info(" time to insert firm credential status " +(start1-end1));

	}
	
	public  void insertProviderCredentialStatus(List<MemberMaintenanceDetailsVO> firmCredentialList) throws Exception {
		//splitBatchInsert("network.provider.match.providerCredentialStatusInsert", firmCredentialList);
		long start1=System.currentTimeMillis();
		splitInsert("network.provider.match.providerCredentialStatusSplitInsert", firmCredentialList);
		long end1=System.currentTimeMillis();
		logger.info(" time to insert provider credential status " +(start1-end1));

		
	}
	
	
	
	
	private void splitBatchInsert(String query, List<MemberMaintenanceDetailsVO> firmCredentialList) throws Exception{
		try{
			int noOfRecords = (firmCredentialList==null?0:firmCredentialList.size());
			int noOfIter = 0;
			int count =10000;
			if(noOfRecords>0){
				noOfIter = noOfRecords/count;
				if(noOfIter==0){
					count = noOfRecords;
					noOfIter =1;
				}
			}
			int loopCount = 0;
			while(noOfIter!=0){
				int endIndex = (loopCount+1)*count;
				if(noOfIter==1){
					endIndex = firmCredentialList.size();
				}
				List<MemberMaintenanceDetailsVO> firms = firmCredentialList.subList(loopCount*count, endIndex);
				batchInsert(query, firms);
				
				loopCount++;
				noOfIter--;
			}
		}catch (Exception dse){
			
		}
	}
	private void splitInsert(String query, List<MemberMaintenanceDetailsVO> firmCredentialList) throws Exception{
		try{
			int noOfRecords = (firmCredentialList==null?0:firmCredentialList.size());
			int noOfIter = 0;
			int count =500;
			if(noOfRecords>0){
				noOfIter = noOfRecords/count;
				if(noOfIter==0){
					count = noOfRecords;
					noOfIter =1;
				}
			}
			int loopCount = 0;
			while(noOfIter!=0){
				int endIndex = (loopCount+1)*count;
				if(noOfIter==1){
					endIndex = firmCredentialList.size();
				}
				List<MemberMaintenanceDetailsVO> firms = firmCredentialList.subList(loopCount*count, endIndex);
				insert(query, firms);
				
				loopCount++;
				noOfIter--;
			}
		}catch (Exception dse){
			logger.info(dse);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getMinimumRatingDetails(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getMinimumRatingDetails", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getCompletedSoDetails(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getCompletedSoDetails", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getResourceLanguages(Integer spnId) throws Exception {
		try{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getResourceLanguages", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

			return null;
		}
	}
	
	
	/* R11.0 SL-19387*/	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getProviderBackgroundInformation(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getProviderBackgroundInformation", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateFirmCredentialStatusForSpnApproved(List<Integer> complianceIds) throws Exception {
		try{
		 getSqlMapClient().update("network.provider.match.updateFirmCredentialStatusForSpnApproved", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateFirmCredentialStatusForOutOfCompliance(List<Integer> complianceIds) throws Exception {
		try{
		 getSqlMapClient().update("network.provider.match.updateFirmCredentialStatusForOutOfCompliance", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateFirmCredentialStatusForCompliantDuetoException(List<Integer> complianceIds) throws Exception {
		try{
		 getSqlMapClient().update("network.provider.match.updateFirmCredentialStatusForCompliantDuetoException", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateProviderCredentialStatusForSpnApproved(List<Integer> complianceIds) throws Exception {
		try{
		 getSqlMapClient().update("network.provider.match.updateProviderCredentialStatusForSpnApproved", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateProviderCredentialStatusForOutOfCompliance(List<Integer> complianceIds) throws Exception {
		try{
		 getSqlMapClient().update("network.provider.match.updateProviderCredentialStatusForOutOfCompliance", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateProviderCredentialStatusForCompliantDuetoException(List<Integer> complianceIds) throws Exception {
		try{
		 getSqlMapClient().update("network.provider.match.updateProviderCredentialStatusForCompliantDuetoException", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
	
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void deletePreviousFirmCredentialStatus(List<Integer> complianceIds) throws Exception {
		try{
		 getSqlMapClient().delete("network.provider.match.deletePreviousFirmCredentialStatus", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void deletePreviousProviderCredentialStatus(List<Integer> complianceIds) throws Exception {
		try{
		 getSqlMapClient().delete("network.provider.match.deletePreviousProviderCredentialStatus", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getMainServices(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getMainServices", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getCategory(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getCategory", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getSubCategory(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getSubCategory", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return null;
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public HashMap<Integer, String> getResourceForMainServices(List<Integer> nodeIds) throws Exception {
		
		try
		{
		HashMap<Integer, String> processMap = new HashMap<Integer, String>();
		Map dataMap = new HashMap();
		dataMap.put("nodeIds", nodeIds);
		processMap = (HashMap<Integer, String>) getSqlMapClient().queryForMap("network.provider.match.getResourceForMainServices", dataMap,"resourceId","nodeId");
		return processMap;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return null;
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public HashMap<Integer, String> getResourceForCategory(List<Integer> nodeIds) throws Exception {
		try
		{
			HashMap<Integer, String> processMap = new HashMap<Integer, String>();
			Map dataMap = new HashMap();
			dataMap.put("nodeIds", nodeIds);
			processMap = (HashMap<Integer, String>) getSqlMapClient().queryForMap("network.provider.match.getResourceForCategory", dataMap,"resourceId","nodeId");
			return processMap;
			
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getProviderCredentialDetailsCredType(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getProviderCredentialDetailsCredType", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return null;
		}
	}
	
	/*public Map<String, String> getResourceActivityStatus(String resourceId)
			throws Exception {
		Map result = null;
		try {
			ActivityRegistry activityRegistry = new ActivityRegistry();
			if (resourceId != null){
				activityRegistry.setActLinkKey(Integer.parseInt(resourceId));
				activityRegistry.setActLinkKeyType("Resource");
				result = getSqlMapClient().queryForMap(
					"activity_registry.getProviderStatus", activityRegistry,
					"act_name", "status");
			}
			else{
				throw new Exception("Baddness Got a null resourceID");
			}
		} catch (Exception ex) {
			logger
					.info("[ActivityRegistryDaoImpl.getResourceActivityStatus - Exception] "
							+ ex.getStackTrace());
			ex.printStackTrace();
		}
		return result;
	}*/
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getCriteriaValueNameForNodeIds(List<Integer> nodeIds) throws Exception {

		HashMap processMap = new HashMap();
		try {
			Map dataMap = new HashMap();
			dataMap.put("nodeIds", nodeIds);
			processMap = (HashMap<String, String>) getSqlMapClient().queryForMap("network.provider.match.getCriteriaValueNameForNodeIds", dataMap,"nodeId","nodeName");
			return processMap;
		} catch (Exception e) {
			logger.info(e.getMessage());

		    return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getCriteriaValueNameForTemplateIds(List<Integer> templateIds) throws Exception {

		HashMap<String, String> processMap = new HashMap<String, String>();
		try {
			Map dataMap = new HashMap();
			dataMap.put("templateIds", templateIds);
			processMap = (HashMap<String, String>) getSqlMapClient().queryForMap("network.provider.match.getCriteriaValueNameForTemplateIds", dataMap,"templateId","valueName");
			return processMap;
		} catch (Exception e) {
			logger.info(e.getMessage());

		    return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<MemberMaintenanceDetailsVO> getCredTypesforCategoryId(List<Integer> credCategoryIds) throws Exception {

		List<MemberMaintenanceDetailsVO> list=new ArrayList<MemberMaintenanceDetailsVO>();
		try {
			Map dataMap = new HashMap();
			dataMap.put("credCategoryIds", credCategoryIds);
			list =  getSqlMapClient().
					queryForList("network.provider.match.getCredTypesforCategoryId", dataMap);
			return list;
		} catch (Exception e) {
			logger.info(e.getMessage());

		    return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getMainCredTypesforCategoryId(List<Integer> credCategoryIds)  {

		List<Integer> list=new ArrayList<Integer>();
		try {
			Map dataMap = new HashMap();
			dataMap.put("credCategoryIds", credCategoryIds);
			list =  getSqlMapClient().
					queryForList("network.provider.match.getMainCredTypesforCategoryId", dataMap);
			return list;
		} catch (Exception e) {
			logger.info(e.getMessage());

		    return null;
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Integer> getMainCredTypesforProviderCategoryId(List<Integer> credCategoryIds)  {

		List<Integer> list=new ArrayList<Integer>();
		try {
			Map dataMap = new HashMap();
			dataMap.put("credCategoryIds", credCategoryIds);
			list =  getSqlMapClient().
					queryForList("network.provider.match.getMainCredTypesforProviderCategoryId", dataMap);
			return list;
		} catch (Exception e) {
			logger.info(e.getMessage());

		    return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getCriteriaValueNameForLanguageIds(List<Integer> languageIds) throws Exception {

		HashMap<String, String> processMap = new HashMap<String, String>();
		try {
			Map dataMap = new HashMap();
			dataMap.put("languageIds", languageIds);
			processMap = (HashMap<String, String>) getSqlMapClient().queryForMap("network.provider.match.getCriteriaValueNameForLanguageIds", dataMap,"languageId","valueName");
			return processMap;
		} catch (Exception e) {
			logger.info(e.getMessage());

		    return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getSkills(Integer spnId) throws Exception {
		try
		{
		List<MemberMaintenanceDetailsVO> list = getSqlMapClient().queryForList("network.provider.match.getSkills", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public HashMap<Integer, String> getResourceHavingSkills(List<Integer> templateIds,Integer spnId) throws Exception {
		try
		{
			HashMap<Integer, String> processMap = new HashMap<Integer, String>();
			Map dataMap = new HashMap();
			dataMap.put("templateIds", templateIds);
			dataMap.put("spnId", spnId);
			processMap = (HashMap<Integer, String>) getSqlMapClient().queryForMap("network.provider.match.getResourceHavingSkills", dataMap,"resourceId","nodeId");
			return processMap;
			
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public HashMap<Integer, String> getResourceForLanguageCriteria(List<Integer> languageIds) throws Exception {
		try
		{
			
			HashMap<Integer, String> processMap = new HashMap<Integer, String>();
			Map dataMap = new HashMap();
			dataMap.put("languageIds", languageIds);
			processMap = (HashMap<Integer, String>) getSqlMapClient().queryForMap("network.provider.match.getResourceForLanguageCriteria", dataMap,"resourceId","languageId");

		return processMap;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public HashMap<Integer, String> getPreviousCriteriaOfAllSpn() throws Exception {
		try
		{
	
		HashMap<Integer, String> processMap = new HashMap<Integer, String>();
		processMap = (HashMap<Integer, String>) getSqlMapClient().queryForMap("network.provider.match.getPreviousCriteriaOfAllSpn", null,"spnId","criteriaIds");

		return processMap;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return null;
		}
	}
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getCommercialLiabilityAmount(Integer spnId) throws Exception {
		List<MemberMaintenanceDetailsVO> list =new ArrayList<MemberMaintenanceDetailsVO>();

		try
		{
			list= getSqlMapClient().queryForList("network.provider.match.commercialGeneralLiabilityAmtBD", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return list;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> getSpnetFirms(Integer spnId) throws Exception {
		List<Integer> list =new ArrayList<Integer>();

		try
		{
		// list = getSqlMapClient().queryForList("network.provider.match.getSpnetFirms", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return list;
		}
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberMaintenanceDetailsVO> getVehicleLiabilityAmount(Integer spnId) throws Exception {
		List<MemberMaintenanceDetailsVO> list =new ArrayList<MemberMaintenanceDetailsVO>();

		try
		{
		 list = getSqlMapClient().queryForList("network.provider.match.vehicleLiabilityAmtBD", spnId);
		return list;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

	    return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateFirmExceptionAppliedGrace(List<Integer> complianceIds)  {
		try{
		 getSqlMapClient().update("network.provider.match.updateFirmExceptionAppliedGrace", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateFirmExceptionAppliedState(List<Integer> complianceIds)  {
		try{
		 getSqlMapClient().update("network.provider.match.updateFirmExceptionAppliedState", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateFirmExceptionAppliedCombined(List<Integer> complianceIds)  {
		try{
		 getSqlMapClient().update("network.provider.match.updateFirmExceptionAppliedCombined", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateFirmExceptionAppliedNull(List<Integer> complianceIds)  {
		try{
		 getSqlMapClient().update("network.provider.match.updateFirmExceptionAppliedNull", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateProviderExceptionAppliedGrace(List<Integer> complianceIds)  {
		try{
		 getSqlMapClient().update("network.provider.match.updateProviderExceptionAppliedGrace", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateProviderExceptionAppliedState(List<Integer> complianceIds)  {
		try{
		 getSqlMapClient().update("network.provider.match.updateProviderExceptionAppliedState", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateProviderExceptionAppliedCombined(List<Integer> complianceIds)  {
		try{
		 getSqlMapClient().update("network.provider.match.updateProviderExceptionAppliedCombined", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updateProviderExceptionAppliedNull(List<Integer> complianceIds)  {
		try{
		 getSqlMapClient().update("network.provider.match.updateProviderExceptionAppliedNull", complianceIds);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

		}
	}

	public void evaluteProviderAndFirmNetworkOverridedInfo() {
		try {
			getSqlMapClient().update("network.provider.match.evaluteFirmNetworkOverridedInfo", null);
			getSqlMapClient().update("network.provider.match.evaluteProviderNetworkOverridedInfo", null);
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void updateVendorActivityRegistryForBackgroundCheck() {
		try {
			getSqlMapClient().update("network.provider.match.updateVendorActivityRegistryForBackgroundCheckAsCompleted", null);
			getSqlMapClient().update("network.provider.match.updateVendorActivityRegistryForBackgroundCheckAsWarning", null);
		} catch(Exception e) {
			logger.info("error in updating updateVendorActivityRegistryForBackgroundCheck",e);
		}
	}
	
	// get background check grace period
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer getBackgroundCheckGracePeriod() throws Exception {
		try
		{
		Integer gracePeriod = (Integer) getSqlMapClient().queryForObject("network.provider.match.getBackgroundCheckGracePeriod", null);
		return gracePeriod;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());

			return null;
		}
	}
	
	
}
