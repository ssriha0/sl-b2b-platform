/**
 *
 */
package com.servicelive.spn.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupCompanySize;
import com.servicelive.domain.lookup.LookupLanguage;
import com.servicelive.domain.lookup.LookupMarket;
import com.servicelive.domain.lookup.LookupMinimumRating;
import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.domain.lookup.LookupPrimaryIndustry;
import com.servicelive.domain.lookup.LookupResourceCredentialCategory;
import com.servicelive.domain.lookup.LookupResourceCredentialType;
import com.servicelive.domain.lookup.LookupSPNApprovalCriteria;
import com.servicelive.domain.lookup.LookupSPNDocumentState;
import com.servicelive.domain.lookup.LookupSPNDocumentType;
import com.servicelive.domain.lookup.LookupSPNMeetAndGreetState;
import com.servicelive.domain.lookup.LookupSPNStatusActionMapper;
import com.servicelive.domain.lookup.LookupSPNStatusOverrideReason;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.lookup.LookupSalesVolume;
import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.domain.lookup.LookupSkills;
import com.servicelive.domain.lookup.LookupStates;
import com.servicelive.domain.lookup.LookupVendorCredentialCategory;
import com.servicelive.domain.lookup.LookupVendorCredentialType;
import com.servicelive.domain.spn.detached.LookupVO;
import com.servicelive.domain.spn.network.SPNExclusionsVO;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.dao.lookup.LookupCompanySizeDao;
import com.servicelive.spn.dao.lookup.LookupLanguageDao;
import com.servicelive.spn.dao.lookup.LookupMarketDao;
import com.servicelive.spn.dao.lookup.LookupMinimumRatingDao;
import com.servicelive.spn.dao.lookup.LookupPerformaceLevelDao;
import com.servicelive.spn.dao.lookup.LookupPrimaryIndustryDao;
import com.servicelive.spn.dao.lookup.LookupResourceCredentialCategoryDao;
import com.servicelive.spn.dao.lookup.LookupResourceCredentialTypeDao;
import com.servicelive.spn.dao.lookup.LookupSPNApprovalCriteriaDao;
import com.servicelive.spn.dao.lookup.LookupSPNDocumentStateDao;
import com.servicelive.spn.dao.lookup.LookupSPNDocumentTypeDao;
import com.servicelive.spn.dao.lookup.LookupSPNMeetAndGreetStateDao;
import com.servicelive.spn.dao.lookup.LookupSPNStatusActionMapperDao;
import com.servicelive.spn.dao.lookup.LookupSPNStatusOverrideReasonDao;
import com.servicelive.spn.dao.lookup.LookupSPNWorkFlowStateDao;
import com.servicelive.spn.dao.lookup.LookupSalesVolumeDao;
import com.servicelive.spn.dao.lookup.LookupServiceTypeDao;
import com.servicelive.spn.dao.lookup.LookupSkillsDao;
import com.servicelive.spn.dao.lookup.LookupStatesDao;
import com.servicelive.spn.dao.lookup.LookupVendorCredentialCategoryDao;
import com.servicelive.spn.dao.lookup.LookupVendorCredentialTypeDao;

/**
 * @author ccarle5
 *
 */
@Transactional
@Service
public class LookupService extends BaseServices{

	private LookupSkillsDao lookupSkillsDao;
	private LookupServiceTypeDao lookupServiceTypeDao;
	private LookupLanguageDao lookupLanguageDao;
	private LookupSPNApprovalCriteriaDao lookupSPNApprovalCriteriaDao;
	private LookupStatesDao lookupStatesDao;
	private LookupMinimumRatingDao lookupMinimRatingDao;
	private LookupCompanySizeDao lookupCompanySizeDao;
	private LookupSalesVolumeDao lookupSalesVolumeDao;
	private LookupMarketDao lookupMarketDao;
	private LookupResourceCredentialTypeDao lookupResourceCredentialTypeDao;
	private LookupResourceCredentialCategoryDao lookupResourceCredentialCategoryDao;
	private LookupVendorCredentialTypeDao lookupVendorCredentialTypeDao;
	private LookupVendorCredentialCategoryDao lookupVendorCredentialCategoryDao;
	private LookupSPNDocumentStateDao lookupSPNDocumentStateDao;
	private LookupSPNMeetAndGreetStateDao lookupSPNMeetAndGreetStateDao;
	private LookupSPNDocumentTypeDao lookupSPNDocumentTypeDao;
	private LookupSPNWorkFlowStateDao lookupSPNWorkflowStateDao;
	private LookupPerformaceLevelDao lookupPerformanceLevelsDao;
	private LookupSPNStatusActionMapperDao lookupSPNStatusActionMapperDao;
	private LookupSPNStatusOverrideReasonDao lookupSPNStatusOverrideReasonDao;
	//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
	//Inject the PrimaryIndustryDAO to call the getPrimaryIndustry() method 
	//which fetches the values from DB
	private LookupPrimaryIndustryDao lookupPrimaryIndustryDao;
	/**
	 * 
	 * @param skillId
	 * @return LookupSkills
	 */
	public LookupSkills getSkillFromSkillId(Integer skillId)
	{
		try {
			LookupSkills skill = lookupSkillsDao.getSkillNodeForId(skillId);
			return skill;
		} catch (Exception e) {
			logger.error("Error LookupService.getSkillFromSkillId", e);
		}
		return null;
	}
	
	/**
	 * Gets a map of LookupSkills and LookupServiceType for the specified service types
	 * @param selectedSkillIds
	 * @return Map
	 */
	public Map<LookupSkills, Set<LookupServiceType>> getMapOfServiceAndSkills(List<Integer> selectedSkillIds){
		Map<LookupSkills, Set<LookupServiceType>> serviceAndSkills = new LinkedHashMap<LookupSkills, Set<LookupServiceType>>();
	
		try {
			if (selectedSkillIds != null && selectedSkillIds.size() > 0) {
				List<LookupServiceType> selectedSkills = lookupServiceTypeDao.getListFromSkillIds(selectedSkillIds);
				for (LookupServiceType currentSkill : selectedSkills) {
					LookupSkills currentService = currentSkill.getSkillNodeId();
					if (serviceAndSkills.containsKey(currentService)) {
						Set<LookupServiceType> skillsForCurrentService = serviceAndSkills.get(currentService);
						skillsForCurrentService.add(currentSkill);
						serviceAndSkills.put(currentService, skillsForCurrentService);
					}
					else{
						Set<LookupServiceType> skillsForCurrentService = new HashSet<LookupServiceType>();
						skillsForCurrentService.add(currentSkill);
						serviceAndSkills.put(currentService, skillsForCurrentService);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error finding  map of service and skills", e);
		}
		return serviceAndSkills;
	}

	/**
	 * 
	 * @return List
	 */
	public List<LookupSkills> getSkillsMainSkills()
	{
		try {
			List<LookupSkills> mainSkills = lookupSkillsDao.getRootNodes();
		    Collections.sort(mainSkills,new Comparator<LookupSkills>() {
				public int compare(LookupSkills o1, LookupSkills o2) {
					if(null==o1.getDescription() && null !=o2.getDescription()){
                		return 1;
                	}else if(null!=o1.getDescription() && null ==o2.getDescription()){
                		return -1;
                	}else if(null==o1.getDescription() && null ==o2.getDescription()){
                		return 0;
                	}else{
                		return o1.getDescription().trim().compareToIgnoreCase(o2.getDescription().trim());
                	}
				}
			});
			return mainSkills;
		} catch (Exception e) {
			logger.error("Error LookupService.getSkillsMainSkills", e);
		}
		return null;
	}

	/**
	 * 
	 * @param parentNodeId
	 * @return List
	 */
	public List<LookupSkills> getSkillsSubCategoriesFromParent(Integer parentNodeId)
	{
		try {
			List<LookupSkills> subCategories = lookupSkillsDao
					.getSubCategoriesFromParentNodeId(parentNodeId);
			//added for sorting subcategories
			Collections.sort(subCategories,new Comparator<LookupSkills>() {
            public int compare(LookupSkills o1, LookupSkills o2) {
	            	if(null==o1.getDescription() && null !=o2.getDescription()){
	            		return 1;
	            	}else if(null!=o1.getDescription() && null ==o2.getDescription()){
	            		return -1;
	            	}else if(null==o1.getDescription() && null ==o2.getDescription()){
	            		return 0;
	            	}else{
	            		return o1.getDescription().trim().compareToIgnoreCase(o2.getDescription().trim());
	            	}
				}
			});
			return subCategories;
		} catch (Exception e) {
			logger.error("Error LookupService.getSkillsSubCategoriesFromParent", e);
		}
		return null;
	}

	/**
	 * 
	 * @param serviceTypeId
	 * @return LookupServiceType
	 */
	public LookupServiceType getServiceTypeFromServiceTypeId(Integer serviceTypeId)
	{
		try {
			LookupServiceType serviceType = lookupServiceTypeDao.getServiceTypeFromId(serviceTypeId);
			return serviceType;
		} catch (Exception e) {
			logger.error("Error LookupService.getServiceTypeFromServiceTypeId", e);
		}

		return null;
	}

	/**
	 * 
	 * @param skillId
	 * @return List
	 */
	public List<LookupServiceType> getServicesFromSkillId(Integer skillId)
	{
		try {
			LookupSkills skill = new LookupSkills();
			skill.setId(skillId);
			List<LookupServiceType> serviceTypeList = lookupServiceTypeDao.getServiceTypeFromSkillNodeId(skill);
			return serviceTypeList;
		} catch (Exception e) {
			logger.error("Error LookupService.getServicesFromSkillId", e);
		}

		return null;
	}
	
	/**
	 *
	 * @param skillIds
	 * @return List
	 */
	public List<LookupVO> getSkillsConcatinatedWithServices(List<Integer> skillIds)
	{
		Integer skillId;
		LookupSkills skill;
		List<LookupServiceType> serviceTypes;
		LookupServiceType serviceType;
		//String customId;
		String customDescription;
		List<LookupVO> skillsWithServices = new ArrayList<LookupVO>();

		for(int x = 0; x < skillIds.size(); x++)
		{
			skillId = skillIds.get(x);
			skill = getSkillFromSkillId(skillId);
			serviceTypes = skill.getServiceTypes();

			for (int y = 0; y < serviceTypes.size(); y++)
			{
				serviceType = serviceTypes.get(y);
				//customId = skill.getId() + "_" + serviceType.getId();
				//customId = "" + serviceType.getId();
				customDescription = skill.getDescription() + " > " + serviceType.getDescription();
				LookupVO skillWithService = new LookupVO();
				skillWithService.setId(serviceType.getId());
				skillWithService.setDescription(customDescription);

				skillsWithServices.add(skillWithService);
			}
		}
       //add code for sorting
		Collections.sort(skillsWithServices,new Comparator<LookupVO>() {
                 public int compare(LookupVO o1, LookupVO o2) {
                	 if(null==o1.getDescription() && null !=o2.getDescription()){
     	          		return 1;
     	          	}else if(null!=o1.getDescription() && null ==o2.getDescription()){
     	          		return -1;
     	          	}else if(null==o1.getDescription() && null ==o2.getDescription()){
     	          		return 0;
     	          	}else{
     	          		return o1.getDescription().toString().trim().compareToIgnoreCase(o2.getDescription().toString().trim());
     	          	}
				 
			}
		});
		return skillsWithServices;
	}
	
	/**
	 * Gets the resource credential categories for the specified types
	 * @param credTypeIds
	 * @return List
	 */
	public List<LookupVO> getResourceCredCatgoriesConcatinatedWithTypes(List<Integer> credTypeIds)
	{
		List<LookupVO> credTypesWithCategories = 
			new ArrayList<LookupVO>();
		for (Integer credTypeId : credTypeIds) {
			try {
				LookupResourceCredentialType credType = lookupResourceCredentialTypeDao.findById(credTypeId);
				for (LookupResourceCredentialCategory credCategory : credType.getCredentialCategories()) {
					LookupVO categoriesWithSkills = new LookupVO();
					String customDescription = credType.getDescription() + " > " + credCategory.getDescription();
					categoriesWithSkills.setId(credCategory.getId());
					categoriesWithSkills.setDescription(customDescription);
					credTypesWithCategories.add(categoriesWithSkills);
				}
			} catch (Exception e) {
				logger.error("Error finding resource credential categories.", e);
			}
			
		}
		//added for sorting
		Collections.sort( credTypesWithCategories,new Comparator<LookupVO>() {
        public int compare(LookupVO o1, LookupVO o2) {
	        	if(null==o1.getDescription() && null !=o2.getDescription()){
	          		return 1;
	          	}else if(null!=o1.getDescription() && null ==o2.getDescription()){
	          		return -1;
	          	}else if(null==o1.getDescription() && null ==o2.getDescription()){
	          		return 0;
	          	}else{
	          		return o1.getDescription().toString().trim().compareToIgnoreCase(o2.getDescription().toString().trim());
	          	}
			}
		});
		
		return credTypesWithCategories;
	}
	
	/**
	 * Gets the vendor credential categories for the specified types
	 * @param credTypeIds
	 * @return List
	 */
	public List<LookupVO> getVendorCredCatgoriesConcatinatedWithTypes(List<Integer> credTypeIds)
	{
		List<LookupVO> credTypesWithCategories = 
			new ArrayList<LookupVO>();
		for (Integer credTypeId : credTypeIds) {
			try {
				LookupVendorCredentialType credType = lookupVendorCredentialTypeDao.findById(credTypeId);
				for (LookupVendorCredentialCategory credCategory : credType.getCredentialCategories()) {
					LookupVO categoriesWithSkills = new LookupVO();
					String customDescription = credType.getDescription() + " > " + credCategory.getDescription();
					categoriesWithSkills.setId(credCategory.getId());
					categoriesWithSkills.setDescription(customDescription);
					credTypesWithCategories.add(categoriesWithSkills);
				}
			} catch (Exception e) {
				logger.error("Error finding vendor credential categories.", e);
			}
			
		}
		//code for sorting
		Collections.sort(credTypesWithCategories,new Comparator<LookupVO>() {
        public int compare(LookupVO o1, LookupVO o2) {
	        	if(null==o1.getDescription() && null !=o2.getDescription()){
	        		return 1;
	        	}else if(null!=o1.getDescription() && null ==o2.getDescription()){
	        		return -1;
	        	}else if(null==o1.getDescription() && null ==o2.getDescription()){
	        		return 0;
	        	}else{
	        		return o1.getDescription().toString().trim().compareToIgnoreCase(o2.getDescription().toString().trim());
	        	}
			}
		});
	
		return credTypesWithCategories;
	}

	
	/**
	 * 
	 * @param credTypeIds
	 * @param credCatIds
	 * @return List
	 */
	public List<LookupVO> getSelectedVendorCredCatgoriesConcatinatedWithTypes(List<Integer> credTypeIds,List<Integer> credCatIds){
		
		List<LookupVO> lookupVOList = new ArrayList<LookupVO>();
		List<LookupVendorCredentialCategory> credentialList = getAllVendorCredentialCategories();
		for(Integer criteriaId : credTypeIds){					

			String credentialDescr = getVendorCredentialDescription(criteriaId, credentialList);
			LookupVO credentialVO = new LookupVO();
			credentialVO.setDescription(credentialDescr);
			credentialVO.setId(criteriaId);
						
			lookupVOList.add(credentialVO);
		}
		List<LookupVO> credWithNoChildSelected = getParentCredWithNoChildSelected(credTypeIds,credCatIds,credentialList);
		if(credWithNoChildSelected.size() > 0){
			lookupVOList.addAll(credWithNoChildSelected);
		}
		
		return lookupVOList;
			
	}
	
	private List<LookupVO>  getParentCredWithNoChildSelected(List<Integer> credTypeIds,
			List<Integer> credCatIds,
			List<LookupVendorCredentialCategory> credentialList) {
		
		List<LookupVO> credentialCatWithNoChildList = new ArrayList<LookupVO>();
		
		for(Integer selectedCredId : credCatIds){
			boolean noChildCredSelected = true;
			for(Integer criteriaId : credTypeIds){	
				for(LookupVendorCredentialCategory cred : credentialList ){
					if(cred.getId().intValue() == criteriaId.intValue()){
						Integer credParentId = cred.getCredentialCategory().getId();
						if(selectedCredId.intValue() == credParentId.intValue()){
								noChildCredSelected = false;
						}
					}
				}
			}
			if(noChildCredSelected){
				LookupVO credentialCatWithNoChild = new LookupVO();
				List<LookupVendorCredentialType> credentialTypeList = getAllVendorCredentialTypes();
				for(LookupVendorCredentialType credentialType: credentialTypeList){
					if(credentialType.getId().intValue() == selectedCredId.intValue()){
						credentialCatWithNoChild.setDescription(credentialType.getDescription());
						credentialCatWithNoChild.setId(selectedCredId);
						break;
					}
				}
				
				credentialCatWithNoChildList.add(credentialCatWithNoChild);
			}
			
		}
		return credentialCatWithNoChildList;
	}

	/**
	 * 
	 * @param credTypeIds
	 * @param credCatIds
	 * @return List
	 */
	public List<LookupVO> getSelectedResourceCredCatgoriesConcatinatedWithTypes(List<Integer> credTypeIds,List<Integer> credCatIds){
		List<LookupVO> lookupVOList = new ArrayList<LookupVO>();
		List<LookupResourceCredentialCategory> credentialList = getAllResourceCredentialCategories();
		for(Integer criteriaId : credTypeIds){					

				String credentialDescr = getResCredentialDescription(criteriaId, credentialList);
				LookupVO credentialVO = new LookupVO();
				credentialVO.setDescription(credentialDescr);
				credentialVO.setId(criteriaId);
				
				lookupVOList.add(credentialVO);
				
			}
		List<LookupVO> credWithNoChildSelected = getParentResCredWithNoChildSelected(credTypeIds,credCatIds,credentialList);
		if(credWithNoChildSelected.size() > 0){
			lookupVOList.addAll(credWithNoChildSelected);
		}
		
		return lookupVOList;
			
	}
	
	private List<LookupVO>  getParentResCredWithNoChildSelected(List<Integer> credTypeIds,
			List<Integer> credCatIds,
			List<LookupResourceCredentialCategory> credentialList) {
		
		List<LookupVO> credentialCatWithNoChildList = new ArrayList<LookupVO>();
		
		for(Integer selectedCredId : credCatIds){
			boolean noChildCredSelected = true;
			for(Integer criteriaId : credTypeIds){	
				for(LookupResourceCredentialCategory cred : credentialList ){
					if(cred.getId().intValue() == criteriaId.intValue()){
						Integer credParentId = cred.getCredentialCategory().getId();
						if(selectedCredId.intValue() == credParentId.intValue()){
								noChildCredSelected = false;
						}
					}
				}
			}
			if(noChildCredSelected){
				LookupVO credentialCatWithNoChild = new LookupVO();
				List<LookupResourceCredentialType> credentialTypeList = getAllResourceCredentialTypes();
				for(LookupResourceCredentialType credentialType: credentialTypeList){
					if(credentialType.getId().intValue() == selectedCredId.intValue()){
						credentialCatWithNoChild.setDescription(credentialType.getDescription());
						credentialCatWithNoChild.setId(selectedCredId);
						break;
					}
				}
				
				credentialCatWithNoChildList.add(credentialCatWithNoChild);
			}
			
		}
		return credentialCatWithNoChildList;
	}

	
	private String getVendorCredentialDescription(Integer criteriaId,
			List<LookupVendorCredentialCategory> credentialList) {
		
		String marketDescr = "";
		String parentDescr = "";
		
		for(LookupVendorCredentialCategory market : credentialList ){
			if(market.getId().intValue() == criteriaId.intValue()){
				marketDescr = market.getDescription();
				parentDescr = market.getCredentialCategory().getDescription();
				marketDescr = parentDescr + " >" +marketDescr + " " ;
			}
		}
		return marketDescr;
		
	}	
	
	private String getResCredentialDescription(Integer criteriaId,
			List<LookupResourceCredentialCategory> credentialList) {
		
		String marketDescr = "";
		String parentDescr = ""; 
		
		for(LookupResourceCredentialCategory cred : credentialList ){
			if(cred.getId().intValue() == criteriaId.intValue()){
				marketDescr = cred.getDescription();
				parentDescr = cred.getCredentialCategory().getDescription();
				marketDescr = parentDescr + " >" +marketDescr + " " ;
			}
		}
		return marketDescr;
		
	}
	
	/**
	 * @param vendorCredCategoriesWithTypes
	 * @param networkId
	 * @param credentialType
	 * @return List<SPNExclusionsVO>
	 * 
	 * method to fetch exceptions (SL-18018)
	 * 
	 */
	public List<SPNExclusionsVO> getExclusionsForCredentials(
			List<LookupVO> credCategoriesWithTypes, int networkId,
			String credentialType) {
		try{
		List<SPNExclusionsVO> exclusions1 = new ArrayList<SPNExclusionsVO>();
		List<SPNExclusionsVO> exclusions2 = new ArrayList<SPNExclusionsVO>();
		List<Integer> typeIds = new ArrayList<Integer>();
		List<Integer> categoryIds = new ArrayList<Integer>();
		for(LookupVO lookupVO : credCategoriesWithTypes){
			if(lookupVO!=null){
				if(null != lookupVO.getDescription()){
					String desc = (String) lookupVO.getDescription();
					if(desc.contains(">")){
						categoryIds.add((Integer)lookupVO.getId());
					}
					else{
						typeIds.add((Integer)lookupVO.getId());
					}
				}
			}
		}
		//for vendor credentials
		if("vendor".equals(credentialType)){
			// for credentials with type only
			if(typeIds.size()>0){
				exclusions1 = lookupVendorCredentialCategoryDao.getExclusionsForCredentialTypes(typeIds,networkId);
			}
			// for credentials with type and category
			if(categoryIds.size()>0){
				exclusions2 = lookupVendorCredentialCategoryDao.getExclusionsForCredentialCategories(categoryIds,networkId);
			}
		}
		//for resource credentials
		else if("resource".equals(credentialType)){
			// for credentials with type only
			if(typeIds.size()>0){
				exclusions1 = lookupResourceCredentialCategoryDao.getExclusionsForCredentialTypes(typeIds,networkId);
			}
			// for credentials with type and category
			if(categoryIds.size()>0){
				exclusions2 = lookupResourceCredentialCategoryDao.getExclusionsForCredentialCategories(categoryIds,networkId);
			}
		}
		//adding both credentials list with only type and with category
		if(exclusions1!=null){
			exclusions1.addAll(exclusions2);
			return exclusions1;
		}
		else{
			return exclusions2;
		}
		}
		catch(Exception e){
			logger.info(e.getMessage());
			return null;
		}
	}
	
	
	
	/**
	 * 
	 * @return List
	 */
	public List<LookupLanguage> getLanguages()
	{
		try {
			List<LookupLanguage> languages = lookupLanguageDao.getLanguages();
			return languages;
		} catch (Exception e) {
			logger.error("Error LookupService.getLanguages", e);
		}

		return null;
	}
	
	/**
	 * Get the list of all states
	 * @return A List of LookupStates
	 */
	public List<LookupStates> findStatesNotBlackedOut()
	{
		try {
			List<LookupStates> states = lookupStatesDao.findStatesNotBlackedOut();
			return states;
		} catch (Exception e) {
			logger.error("Error finding the list of states.", e);
			return null;
		}
	}

	/**
	 * 
	 * @param id
	 * @return LookupLanguage
	 */
	public LookupLanguage getLanguageFromId(Integer id)
	{
		try {
			LookupLanguage language = lookupLanguageDao.getLanguageFromId(id);
			return language;
		} catch (Exception e) {
			logger.error("Error LookupService.getLanguageFromId", e);
		}

		return null;
	}

	/**
	 * 
	 * @param propertyname
	 * @return LookupSPNApprovalCriteria
	 */
	public LookupSPNApprovalCriteria getApprovalCriteriaForDescription(String propertyname) {
		try {
			List <LookupSPNApprovalCriteria> criterias =  this.getLookupSPNApprovalCriteriaDao().getApprovalCriteriaForDescription(propertyname);
			if(criterias != null && criterias.size() > 0 ) {
				return criterias.get(0);
			}
		} catch (Exception e) {
			logger.error("Error finding approval criteria from description", e);
		}
		return null;
	}
	
	
	/**
	 * Gets a map of all the approval criterias where key is criteria description and value is the {@link LookupSPNApprovalCriteria}
	 * @return A map of approval criterias
	 */
	public Map<String,LookupSPNApprovalCriteria> getAllApprovalCriteriasMap() {
		try {
			List <LookupSPNApprovalCriteria> approvalCriterias =  this.getLookupSPNApprovalCriteriaDao().findAll();
			Map<String,LookupSPNApprovalCriteria> approvalCriteriasMap = new Hashtable<String,LookupSPNApprovalCriteria>();
			for (LookupSPNApprovalCriteria lookupSPNApprovalCriteria : approvalCriterias) {
				approvalCriteriasMap.put(lookupSPNApprovalCriteria.getDescription().toString().trim(), lookupSPNApprovalCriteria);
			}
			return approvalCriteriasMap;
			
			
		} catch (Exception e) {
			logger.error("Error finding all the approval criterias", e);
		}
		return null;
	}
	
	/**
	 * 
	 * @return List
	 */
	public List<LookupSPNDocumentType> getDocumentTypes()
	{
		try {
			List<LookupSPNDocumentType> documentTypes = lookupSPNDocumentTypeDao.findAll();
             return documentTypes;
		} catch (Exception e) {
			logger.error("Error LookupService.getDocumentTypes", e);
		}
		
		return null;
	}
	/**
	 * 
	 * @param docTypeId
	 * @return LookupSPNDocumentType
	 */
	public LookupSPNDocumentType getDocumentTypeFromTypeId(Integer docTypeId)
	{
		try {
			LookupSPNDocumentType documentType = lookupSPNDocumentTypeDao.findById(docTypeId);
			return documentType;
		} catch (Exception e) {
			logger.error("Error LookupService.getDocumentTypeFromTypeId", e);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @return List
	 */
	public List<LookupMinimumRating> getAllMimimumRating(){
		try {
			List<LookupMinimumRating> ratings = lookupMinimRatingDao.findAll();
			return ratings;
		} catch (Exception e) {
			logger.error("Error LookupService.getAllMimimumRating", e);
		}
		
		return null;
	}
	
	/**
	 * Get the list of all sales volume
	 * @return A List of LookupSalesVolume
	 */
	public List<LookupSalesVolume> getAllSalesVolumes()
	{
		try {
			List<LookupSalesVolume> salesVolumes = lookupSalesVolumeDao.getAllSalesVolumes();
			return salesVolumes;
		} catch (Exception e) {
			logger.error("Error finding the list of sales volumes.", e);
			return null;
		}
	}
	
	/**
	 * Get the list of all company sizes
	 * @return A List of LookupCompanySize
	 */
	public List<LookupCompanySize> getAllCompanySizes()
	{
		try {
			List<LookupCompanySize> companySizes = lookupCompanySizeDao.getAllCompanySizes();
			return companySizes;
		} catch (Exception e) {
			logger.error("Error finding the list of company sizes.", e);
			return null;
		}
	}
	
	/**
	 * 
	 * @return List
	 */
	public List<LookupMarket> getAllMarkets()
	{
		try
		{
			List<LookupMarket> results = lookupMarketDao.getMarkets();
			return results;
		}
		catch (Exception e)
		{
			logger.error("Error LookupService.getAllMarkets", e);
		}

		return null;
	}
	/**
	 * 
	 * @return List
	 */
	public List<LookupResourceCredentialType> getAllResourceCredentialTypes()
	{
		try
		{
			List<LookupResourceCredentialType> results = lookupResourceCredentialTypeDao.findAll();
			//added for sorting
			Collections.sort(results,new Comparator<LookupResourceCredentialType>() {
            public int compare(LookupResourceCredentialType o1,
			LookupResourceCredentialType o2) {
	            	if(null==o1.getDescription() && null !=o2.getDescription()){
	            		return 1;
	            	}else if(null!=o1.getDescription() && null ==o2.getDescription()){
	            		return -1;
	            	}else if(null==o1.getDescription() && null ==o2.getDescription()){
	            		return 0;
	            	}else{
	            		return o1.getDescription().trim().compareToIgnoreCase(o2.getDescription().trim());
	            	}
				}
			});
			return results;
		}
		catch (Exception e)
		{
			logger.error("Error LookupService.getAllResourceCredentialTypes", e);
		}

		return null;
	}

	/**
	 * 
	 * @return List
	 */
	public List<LookupResourceCredentialCategory> getAllResourceCredentialCategories()
	{
		try
		{
			List<LookupResourceCredentialCategory> results = lookupResourceCredentialCategoryDao.findAll();
			return results;
		}
		catch (Exception e)
		{
			logger.error("Error LookupService.getAllResourceCredentialCategories", e);
		}

		return null;
	}

	/**
	 * 
	 * @return List
	 */
	public List<LookupVendorCredentialType> getAllVendorCredentialTypes()
	{
		try
		{
			List<LookupVendorCredentialType> results = lookupVendorCredentialTypeDao.findAllbyRemovingInsurance();
			Collections.sort(results,new Comparator<LookupVendorCredentialType>() {
                   public int compare(LookupVendorCredentialType o1, LookupVendorCredentialType o2) {
                	   if(null==o1.getDescription() && null !=o2.getDescription()){
                   		return 1;
	                   	}else if(null!=o1.getDescription() && null ==o2.getDescription()){
	                   		return -1;
	                   	}else if(null==o1.getDescription() && null ==o2.getDescription()){
	                   		return 0;
	                   	}else{
	                   		return o1.getDescription().trim().compareToIgnoreCase(o2.getDescription().trim());
	                   	}
				}
			});
			return results;
		}
		catch (Exception e)
		{
			logger.error("Error LookupService.getAllResourceCredentialTypes", e);
		}

		return null;
	}

	/**
	 * 
	 * @return List
	 */
	public List<LookupVendorCredentialCategory> getAllVendorCredentialCategories()
	{
		try
		{
			List<LookupVendorCredentialCategory> results = lookupVendorCredentialCategoryDao.findAll();
			return results;
		}
		catch (Exception e)
		{
			logger.error("Error LookupService.getAllResourceCredentialCategories", e);
		}

		return null;
	}
	/**
	 * 
	 * @return List
	 */
	public List<LookupSPNDocumentState> getActionableSPNDocumentStates()
	{
		try
		{
			return lookupSPNDocumentStateDao.getActionableSPNDocumentStates();
		}
		catch(Exception e)
		{
			logger.error("Error LookupService.getAllSPNDocumentStates", e);
		}
		return null;
	}
	/**
	 * 
	 * @return List
	 */
	public List<LookupSPNMeetAndGreetState> getAllSPNMeetAndGreetStates()
	{
		try
		{
			return lookupSPNMeetAndGreetStateDao.findAll();
		}
		catch(Exception e)
		{
			logger.error("Error LookupService.getAllSPNMeetAndGreetStates", e);
		}
		return null;
	}
	/**
	 * get list of districts
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LookupVO> getALLDistricts() throws Exception {
		try{
			return getSqlMapClient().queryForList("lookup.districts", null);
		}catch(Exception e){
			logger.error("Error LookupService.getALLDistricts due to "+ e.getMessage(), e);
			throw e;
		}
	}
	
	/**
	 * List of workflow states for ProviderFirm
	 * @return
	 * @throws Exception
	 */
	public List<LookupSPNWorkflowState> getPFMembershipStatusList() throws Exception {
		try{
			return lookupSPNWorkflowStateDao.getWorkflowStatesForGroupType(SPNBackendConstants.PF_WF_STATE_GROUP_TYPE);
		}catch(Exception e){
			logger.error("Error LookupService.getPFMembershipStatusList due to "+ e.getMessage(), e);
			throw e;
		}
	}
	
	public List<LookupPerformanceLevel> getAllPerformanceLevels() throws Exception {
		try{
			
			List<LookupPerformanceLevel> sorted =lookupPerformanceLevelsDao.getAllPerformancelevels();
			if(sorted.size()> 0 ) Collections.reverse(sorted);
			return sorted;
		}catch(Exception e){
			logger.error("Error LookupService.getAllperformance due to "+ e.getMessage(), e);
			throw e;
		}
	}
	/**
	 * R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
	 * Method returns the primary industry list
	 * @return List<LookupPrimaryIndustry>
	 */
	public List<LookupPrimaryIndustry> getPrimaryIndustry()
	{
		try {
			List<LookupPrimaryIndustry> primaryIndustry = lookupPrimaryIndustryDao.getPrimaryIndustry();
			return primaryIndustry;
		} catch (Exception e) {
			logger.error("Error LookupService.getPrimaryIndustry", e);
		}

		return null;
	}
	
	
	/**
	 * 
	 * @param wfState
	 * @return  List<LookupSPNStatusOverrideReason>
	 */
	public List<LookupSPNStatusOverrideReason> findByWfState(String wfState, String currentWfState) {
		return lookupSPNStatusOverrideReasonDao.findByWfState(wfState, currentWfState);
	}
	
	// SL-12300 : Method to find the previous network status for a given spn id and provider firm id from spnet_provider_firm_network_override table.
	public String findLastFirmNetworkStatus(Integer spnId, Integer providerFirmId) {
		return lookupSPNStatusOverrideReasonDao.findLastFirmNetworkStatus(spnId, providerFirmId);
	}
	
	// SL-12300 : Method to find the previous network status for a given spn id and provider resource id from spnet_provider_network_override table.
	public String findLastProviderNetworkStatus(Integer spnId, Integer providerResourceId) {
		return lookupSPNStatusOverrideReasonDao.findLastProviderNetworkStatus(spnId, providerResourceId);
	}


	/**
	 * 
	 * @param modifiedBy
	 * @param wfState
	 * @param actionType
	 * @return LookupSPNStatusActionMapper
	 */
	public LookupSPNStatusActionMapper findByModifierStateAndActionType(String modifiedBy, String wfState, String actionType) {
		return lookupSPNStatusActionMapperDao.findByModifierStateAndActionType(modifiedBy, wfState, actionType);
	}

	/////////// GETTERS & SETTERS ///////////////

	protected LookupSkillsDao getLookupSkillsDao() {
		return lookupSkillsDao;
	}

	/**
	 * 
	 * @param lookupSkillsDao
	 */
	public void setLookupSkillsDao(LookupSkillsDao lookupSkillsDao) {
		this.lookupSkillsDao = lookupSkillsDao;
	}

	protected LookupServiceTypeDao getLookupServiceTypeDao() {
		return lookupServiceTypeDao;
	}

	/**
	 * 
	 * @param lookupServiceTypeDao
	 */
	public void setLookupServiceTypeDao(LookupServiceTypeDao lookupServiceTypeDao) {
		this.lookupServiceTypeDao = lookupServiceTypeDao;
	}

	protected LookupLanguageDao getLookupLanguageDao() {
		return lookupLanguageDao;
	}

	/**
	 * 
	 * @param lookupLanguageDao
	 */
	public void setLookupLanguageDao(LookupLanguageDao lookupLanguageDao) {
		this.lookupLanguageDao = lookupLanguageDao;
	}

	/**
	 * @return the lookupSPNApprovalCriteriaDao
	 */
	protected LookupSPNApprovalCriteriaDao getLookupSPNApprovalCriteriaDao() {
		return lookupSPNApprovalCriteriaDao;
	}

	/**
	 * @param lookupSPNApprovalCriteriaDao the lookupSPNApprovalCriteriaDao to set
	 */
	public void setLookupSPNApprovalCriteriaDao(
			LookupSPNApprovalCriteriaDao lookupSPNApprovalCriteriaDao) {
		this.lookupSPNApprovalCriteriaDao = lookupSPNApprovalCriteriaDao;
	}

	/**
	 * 
	 * @return LookupSPNDocumentTypeDao
	 */
	public LookupSPNDocumentTypeDao getLookupSPNDocumentTypeDao() {
		return lookupSPNDocumentTypeDao;
	}

	/**
	 * 
	 * @param lookupSPNDocumentTypeDao
	 */
	public void setLookupSPNDocumentTypeDao(LookupSPNDocumentTypeDao lookupSPNDocumentTypeDao) {
		this.lookupSPNDocumentTypeDao = lookupSPNDocumentTypeDao;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
		
	}

	/**
	 * @return the lookupStatesDao
	 */
	public LookupStatesDao getLookupStatesDao() {
		return lookupStatesDao;
	}

	/**
	 * @param lookupStatesDao the lookupStatesDao to set
	 */
	public void setLookupStatesDao(LookupStatesDao lookupStatesDao) {
		this.lookupStatesDao = lookupStatesDao;
	}

	/**
	 * @return the lookupMinimRatingDao
	 */
	public LookupMinimumRatingDao getLookupMinimRatingDao() {
		return lookupMinimRatingDao;
	}

	/**
	 * @param lookupMinimRatingDao the lookupMinimRatingDao to set
	 */
	public void setLookupMinimRatingDao(LookupMinimumRatingDao lookupMinimRatingDao) {
		this.lookupMinimRatingDao = lookupMinimRatingDao;
	}
	
	/**
	 * @return the lookupCompanySizeDao
	 */
	public LookupCompanySizeDao getLookupCompanySizeDao() {
		return lookupCompanySizeDao;
	}

	/**
	 * @param lookupCompanySizeDao the lookupCompanySizeDao to set
	 */
	public void setLookupCompanySizeDao(LookupCompanySizeDao lookupCompanySizeDao) {
		this.lookupCompanySizeDao = lookupCompanySizeDao;
	}

	/**
	 * @return the lookupSalesVolumeDao
	 */
	public LookupSalesVolumeDao getLookupSalesVolumeDao() {
		return lookupSalesVolumeDao;
	}

	/**
	 * @param lookupSalesVolumeDao the lookupSalesVolumeDao to set
	 */
	public void setLookupSalesVolumeDao(LookupSalesVolumeDao lookupSalesVolumeDao) {
		this.lookupSalesVolumeDao = lookupSalesVolumeDao;
	}

	/**
	 * 
	 * @return LookupMarketDao
	 */
	public LookupMarketDao getLookupMarketDao()
	{
		return lookupMarketDao;
	}

	/**
	 * 
	 * @param lookupMarketDao
	 */
	public void setLookupMarketDao(LookupMarketDao lookupMarketDao)
	{
		this.lookupMarketDao = lookupMarketDao;
	}

	/**
	 * 
	 * @return LookupResourceCredentialTypeDao
	 */
	public LookupResourceCredentialTypeDao getLookupResourceCredentialTypeDao()
	{
		return lookupResourceCredentialTypeDao;
	}

	/**
	 * 
	 * @param lookupResourceCredentialTypeDao
	 */
	public void setLookupResourceCredentialTypeDao(LookupResourceCredentialTypeDao lookupResourceCredentialTypeDao)
	{
		this.lookupResourceCredentialTypeDao = lookupResourceCredentialTypeDao;
	}

	/**
	 * 
	 * @return LookupResourceCredentialCategoryDao
	 */
	public LookupResourceCredentialCategoryDao getLookupResourceCredentialCategoryDao()
	{
		return lookupResourceCredentialCategoryDao;
	}

	/**
	 * 
	 * @param lookupResourceCredentialCategoryDao
	 */
	public void setLookupResourceCredentialCategoryDao(LookupResourceCredentialCategoryDao lookupResourceCredentialCategoryDao)
	{
		this.lookupResourceCredentialCategoryDao = lookupResourceCredentialCategoryDao;
	}

	/**
	 * 
	 * @return LookupVendorCredentialTypeDao
	 */
	public LookupVendorCredentialTypeDao getLookupVendorCredentialTypeDao()
	{
		return lookupVendorCredentialTypeDao;
	}

	/**
	 * 
	 * @param lookupVendorCredentialTypeDao
	 */
	public void setLookupVendorCredentialTypeDao(LookupVendorCredentialTypeDao lookupVendorCredentialTypeDao)
	{
		this.lookupVendorCredentialTypeDao = lookupVendorCredentialTypeDao;
	}

	/**
	 * 
	 * @return LookupVendorCredentialCategoryDao
	 */
	public LookupVendorCredentialCategoryDao getLookupVendorCredentialCategoryDao()
	{
		return lookupVendorCredentialCategoryDao;
	}

	/**
	 * 
	 * @param lookupVendorCredentialCategoryDao
	 */
	public void setLookupVendorCredentialCategoryDao(LookupVendorCredentialCategoryDao lookupVendorCredentialCategoryDao)
	{
		this.lookupVendorCredentialCategoryDao = lookupVendorCredentialCategoryDao;
	}

	/**
	 * 
	 * @param lookupSPNDocumentStateDao
	 */
	public void setLookupSPNDocumentStateDao(LookupSPNDocumentStateDao lookupSPNDocumentStateDao)
	{
		this.lookupSPNDocumentStateDao = lookupSPNDocumentStateDao;
	}

	/**
	 * @return the lookupSPNMeetAndGreetStateDao
	 */
	public LookupSPNMeetAndGreetStateDao getLookupSPNMeetAndGreetStateDao() {
		return lookupSPNMeetAndGreetStateDao;
	}

	/**
	 * @param lookupSPNMeetAndGreetStateDao the lookupSPNMeetAndGreetStateDao to set
	 */
	public void setLookupSPNMeetAndGreetStateDao(
			LookupSPNMeetAndGreetStateDao lookupSPNMeetAndGreetStateDao) {
		this.lookupSPNMeetAndGreetStateDao = lookupSPNMeetAndGreetStateDao;
	}

	/**
	 * @return the lookupSPNDocumentStateDao
	 */
	public LookupSPNDocumentStateDao getLookupSPNDocumentStateDao() {
		return lookupSPNDocumentStateDao;
	}

	/**
	 * @return the lookupSPNWorkflowStateDao
	 */
	public LookupSPNWorkFlowStateDao getLookupSPNWorkflowStateDao() {
		return lookupSPNWorkflowStateDao;
	}

	/**
	 * @param lookupSPNWorkflowStateDao the lookupSPNWorkflowStateDao to set
	 */
	public void setLookupSPNWorkflowStateDao(
			LookupSPNWorkFlowStateDao lookupSPNWorkflowStateDao) {
		this.lookupSPNWorkflowStateDao = lookupSPNWorkflowStateDao;
	}

	

	/**
	 * @param lookupPerformanceLevelsDao the lookupPerformanceLevelsDao to set
	 */
	public void setLookupPerformanceLevelsDao(
			LookupPerformaceLevelDao lookupPerformanceLevelsDao) {
		this.lookupPerformanceLevelsDao = lookupPerformanceLevelsDao;
	}

	/**
	 * @param lookupSPNStatusActionMapperDao the lookupSPNStatusActionMapperDao to set
	 */
	public void setLookupSPNStatusActionMapperDao(
			LookupSPNStatusActionMapperDao lookupSPNStatusActionMapperDao) {
		this.lookupSPNStatusActionMapperDao = lookupSPNStatusActionMapperDao;
	}

	/**
	 * @param lookupSPNStatusOverrideReasonDao the lookupSPNStatusOverrideReasonDao to set
	 */
	public void setLookupSPNStatusOverrideReasonDao(
			LookupSPNStatusOverrideReasonDao lookupSPNStatusOverrideReasonDao) {
		this.lookupSPNStatusOverrideReasonDao = lookupSPNStatusOverrideReasonDao;
	}

	/**
	 * @return the lookupPrimaryIndustryDao
	 */
	public LookupPrimaryIndustryDao getLookupPrimaryIndustryDao() {
		return lookupPrimaryIndustryDao;
	}

	/**
	 * @param lookupPrimaryIndustryDao the lookupPrimaryIndustryDao to set
	 */
	public void setLookupPrimaryIndustryDao(
			LookupPrimaryIndustryDao lookupPrimaryIndustryDao) {
		this.lookupPrimaryIndustryDao = lookupPrimaryIndustryDao;
	}


}
