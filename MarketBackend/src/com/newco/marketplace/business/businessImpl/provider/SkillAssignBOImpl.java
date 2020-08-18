package com.newco.marketplace.business.businessImpl.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.business.iBusiness.provider.ISkillAssignBO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeIdsVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.ILuActivityDao;
import com.newco.marketplace.persistence.iDao.provider.IMarketplaceSearchDao;
import com.newco.marketplace.persistence.iDao.provider.ISkillAssignDao;
import com.newco.marketplace.util.ActivityCheckList;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.provider.ActivityRegistry;
import com.newco.marketplace.vo.provider.CheckedSkillsVO;
import com.newco.marketplace.vo.provider.LanguageVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.LuActivity;
import com.newco.marketplace.vo.provider.ServiceTypesVO;
import com.newco.marketplace.vo.provider.SkillAssignRequest;
import com.newco.marketplace.vo.provider.SkillAssignResponse;
import com.newco.marketplace.vo.provider.SkillAssignVO;
import com.newco.marketplace.vo.provider.SkillCompletionVO;
import com.newco.marketplace.vo.provider.SkillNodeVO;
import com.newco.marketplace.vo.provider.SkillServiceTypeVO;



/**
 * 
 * $Revision: 1.11 $ $Author: glacy $ $Date: 2008/04/26 00:40:26 $
 *
 */
public class SkillAssignBOImpl extends ABaseBO implements ISkillAssignBO{  
	
	

	private ISkillAssignDao iSkillAssignDao; 
	private IMarketplaceSearchDao iMarketplaceSearchDao;
	private ILuActivityDao iLuActivityDao;
	private IActivityRegistryDao iActivityRegistryDao;
	private ILookupDAO iLookupDao;
	
	public SkillAssignBOImpl(ISkillAssignDao iSkillAssignDao,
			IMarketplaceSearchDao iMarketplaceSearchDao,
			ILuActivityDao iLuActivityDao,
			IActivityRegistryDao iActivityRegistryDao,
			ILookupDAO iLookupDao){
		this.iSkillAssignDao = iSkillAssignDao;
		this.iMarketplaceSearchDao = iMarketplaceSearchDao;
		this.iLuActivityDao = iLuActivityDao;
		this.iActivityRegistryDao =iActivityRegistryDao;
		this.iLookupDao = iLookupDao;
	}
	/**
	 * @param assignRequest  
	 * @return SkillAssignResponse
	 * @throws BusinessServiceException */
	public SkillAssignResponse assignSkills(SkillAssignVO  assignRequest) throws BusinessServiceException {
			
			SkillAssignVO assignVO = new SkillAssignVO();
		  	LanguageVO languageVO= new LanguageVO();
	        assignVO.setResourceId(assignRequest.getResourceId());
	        assignVO.setModifiedBy(assignRequest.getModifiedBy());
	        java.util.Date today = new java.util.Date();
	        java.sql.Date now = new java.sql.Date(today.getTime());
	        assignVO.setCreatedDate(now);
	        assignVO.setModifiedDate(now);
	        languageVO.setCreatedDate(now);
	        languageVO.setModifiedDate(now);
	        ArrayList<SkillServiceTypeVO> skillServTypeArray = assignRequest.getSkillServiceList();
	        SkillAssignResponse skillResponse = new SkillAssignResponse();
	        
	        try{
	            Set uniqueSet = new HashSet();
	            SkillAssignVO tempSkillAssVO = null;
	            Hashtable tempMap= new Hashtable();
	            int  resourceSkillid=0;
	            
	            //Update Language
	            deleteLanguage(assignRequest.getLanguageList(), languageVO);
	            updateLanguage(assignRequest.getLanguageList(), languageVO);
	            
	        	for (int i = 0; i < skillServTypeArray.size(); i++){
	        		assignVO.setNodeId(skillServTypeArray.get(i).getSkillNodeId());
	        		if(skillServTypeArray.get(i).getServiceTypeId() != -1){ //means its coming from the grid, and not the first page
	        			assignVO.setSkillTypeId(skillServTypeArray.get(i).getServiceTypeId());
	        			assignVO.setRootNode(false);
	        			assignVO.setRootNodeId(assignRequest.getRootNodeId());
	        			
	        		}else{
	        			// root node indicates that its from the first page, not the grid
	        			assignVO.setRootNode(true);
	        		}
	        		Long tempId=new Long(assignVO.getNodeId());
		            //save the skill assingnments
	                if(uniqueSet.add(tempId)){
	                	tempSkillAssVO = iSkillAssignDao.assign(assignVO);
	                	resourceSkillid=tempSkillAssVO.getResourceSkillId();
	                	tempMap.put(tempId+"",new Integer(resourceSkillid));
	                	
	                }else{
	                	resourceSkillid=((Integer)tempMap.get(tempId+"")).intValue();
	                }
		            if(skillServTypeArray.get(i).getServiceTypeId() != -1){ 
		            	//means its coming from the grid, and not the first page
		            	assignVO.setResourceSkillId(resourceSkillid);
		            	iSkillAssignDao.assignServiceTypes(assignVO);
		            	tempSkillAssVO=null;
		            }
	        	}
	            uniqueSet=null; 
	            tempMap=null;
	        	if (assignRequest.getSkillServiceListToDelete() != null){
	        		// delete all the old values
	        		deleteOldChecked(assignRequest.getSkillServiceListToDelete(), assignRequest.getResourceId());
	        	}
	        	
	            skillResponse.setErrorId(0);
	            skillResponse.setMessage("The update was successful");
	            
	        }catch (DBException ex) {
	    		logger.error("DB Exception @SkillAssignBOImpl.assignSkills() due to"+ ex.getMessage());
	    		throw new BusinessServiceException(ex.getMessage());
	    		} catch (Exception ex) {
	    		ex.printStackTrace();
	    		logger.error("General Exception @SkillAssignBOImpl.assignSkills() due to"+ ex.getMessage());
	    throw new BusinessServiceException(
	    		"General Exception @SkillAssignBOImpl.assignSkills() due to "+ ex.getMessage());
	    }
    	
        return skillResponse;
	}
	
	private void deleteOldChecked(ArrayList<SkillServiceTypeVO> toDelete, long resourceId) throws BusinessServiceException {
	 
		SkillAssignVO assignVO = new SkillAssignVO();
		assignVO.setResourceId(resourceId);
		try {
			for (int i=0; i<toDelete.size(); i++){
				SkillServiceTypeVO nodeToDelete = toDelete.get(i);
				if(nodeToDelete.getServiceTypeId() != 0){ // has service types - comes from the grid
					assignVO.setNodeId(nodeToDelete.getSkillNodeId());
					assignVO.setSkillTypeId(nodeToDelete.getServiceTypeId());
					SkillAssignVO tempSkillAssVO = iSkillAssignDao.getResourceSkillId(assignVO);
						assignVO.setResourceSkillId(tempSkillAssVO.getResourceSkillId());
						assignVO.setSkillServiceTypeId(tempSkillAssVO.getSkillServiceTypeId());
						iSkillAssignDao.deleteOldSkills(assignVO);
						List tempList= iSkillAssignDao.getResourceSkillIdsList(assignVO);

						if(tempList==null || tempList.size()==0){
							iSkillAssignDao.deleteOldSkillsNodes(assignVO);
						}
					
					
				} else { 
					// general skills
					assignVO.setNodeId(nodeToDelete.getSkillNodeId());
					// delete all the guys where parent id = this node
					deleteMyChildren(nodeToDelete.getSkillNodeId(), resourceId);
					// then delete the parents
					iSkillAssignDao.deleteOldGeneralSkills(assignVO);
				}
			}
		} catch (DBException ex) {
    		logger
    		.info("DB Exception @SkillAssignBOImpl.deleteOldChecked() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(ex.getMessage());
    		} catch (Exception ex) {
    		ex.printStackTrace();
    		logger
    		.info("General Exception @SkillAssignBOImpl.deleteOldChecked() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(
    		"General Exception @SkillAssignBOImpl.deleteOldChecked() due to "+ ex.getMessage());
    		}
    	 
	}

	private void deleteMyChildren(int nodeId, long resourceId){
		 
		SkillAssignVO assignVO = new SkillAssignVO();
		//Set up checkedSkillsVO (resourceId, rootNodeId)
		CheckedSkillsVO checkedSkills = new CheckedSkillsVO();
		checkedSkills.setRootNodeId(nodeId);
		checkedSkills.setResourceId(resourceId);
		
		try {
			//getCheckedSkills, expect checkedSkillsVO back
			List<CheckedSkillsVO> chkdSkills = iSkillAssignDao.getCheckedSkills(checkedSkills);
			//put info in SkillAssignVO
			for(int i=0; i<chkdSkills.size(); i++){
				CheckedSkillsVO checkedSkill = chkdSkills.get(i);
				assignVO.setResourceId(resourceId);
				assignVO.setNodeId(checkedSkill.getNodeId());
				assignVO.setSkillTypeId(checkedSkill.getServiceTypeId());
				// do delete like above for the grid for all items returned
				SkillAssignVO tempSkillAssVO = iSkillAssignDao.getResourceSkillId(assignVO);
				assignVO.setResourceSkillId(tempSkillAssVO.getResourceSkillId());
				assignVO.setSkillServiceTypeId(tempSkillAssVO.getSkillServiceTypeId());
				iSkillAssignDao.deleteOldSkills(assignVO);
				List tempList= iSkillAssignDao.getResourceSkillIdsList(assignVO);
				if(tempList== null||tempList.size()==0){
					iSkillAssignDao.deleteOldSkillsNodes(assignVO);
				}
			}
		} catch (DBException e) {
			logger.error("DB Exception @SkillAssignBOImpl.deleteOldChecked() due to"+e.getStackTrace());
		}
		 
	}
	
	/**
	 * @param assignRequest
	 * @return SkillAssignResponse
	 * @throws BusinessServiceException
	 */
	public SkillAssignResponse getSkills(SkillAssignVO assignRequest)throws BusinessServiceException {
		
		SkillAssignResponse skillResponse = new SkillAssignResponse(); 
		SkillNodeVO skillNodeReq = new SkillNodeVO();
		skillNodeReq.setNodeId(assignRequest.getNodeId());
		// Get the name of the parent node for display
		try{
			SkillNodeVO tempSkillNode = new SkillNodeVO();
			tempSkillNode = iSkillAssignDao.getSkillNodeNameById(skillNodeReq);
			skillNodeReq.setNodeName(tempSkillNode.getNodeName());
			skillNodeReq.setLevel(1);
		}catch (Exception ex) {
    		logger.info("General Exception @SkillAssignBOImpl.getSkills() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(
    		"General Exception @SkillAssignBOImpl.getSkills() due to "+ ex.getMessage());
    }
		
		// Get all the tree children elements and assign to the response object
		ArrayList treeChildren = new ArrayList();
		try{
			drillDownTree(skillNodeReq, treeChildren);
		}catch (Exception ex) {
    		ex.printStackTrace();
    		logger.info("General Exception @SkillAssignBOImpl.getSkills() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(
    		"General Exception @SkillAssignBOImpl.getSkills() due to "
    				+ ex.getMessage());
		}
		
		// loop through and assign service types to each skill node 
		//(the are the same for each part of the given tree)
		for (int i=0; i<treeChildren.size(); i++){
			SkillNodeVO node = (SkillNodeVO)treeChildren.get(i);
			ArrayList<ServiceTypesVO> serviceTypes = loadServiceTypes(skillNodeReq, iSkillAssignDao);
			node.setServiceTypes(serviceTypes);
		}
		ArrayList<ServiceTypesVO> serviceTypes = loadServiceTypes(skillNodeReq, iSkillAssignDao);
		skillResponse.setServiceList(serviceTypes);
		skillResponse.setSkillTreeList(treeChildren);
		
		return skillResponse;
	}

	public List<SkillNodeVO> getSkillsForResource(Integer resourceId) throws BusinessServiceException{
		ArrayList<SkillNodeVO> skillTree = new ArrayList<SkillNodeVO>();
		try{
			skillTree = (ArrayList<SkillNodeVO>)iSkillAssignDao.getSkillsForResource(resourceId);			
		}catch (DBException ex) {
    		logger
    		.info("DB Exception @SkillAssignBOImpl.getSkillsForResource() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(ex.getMessage());
    		} catch (Exception ex) {
    		ex.printStackTrace();
    		logger
    		.info("General Exception @SkillAssignBOImpl.getSkillsForResource() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(
    		"General Exception @SkillAssignBOImpl.getSkillsForResource() due to "
    				+ ex.getMessage());
    }
    	 
		return skillTree;
	}
	private ArrayList<ServiceTypesVO> loadServiceTypes(SkillNodeVO skillNodeReq, ISkillAssignDao iSkillAssignDao) throws BusinessServiceException {
		 
		ArrayList<ServiceTypesVO> serviceTypes = new ArrayList<ServiceTypesVO>();
		try{
			serviceTypes = (ArrayList<ServiceTypesVO>)iSkillAssignDao.getServiceTypes(skillNodeReq);			
		}catch (DBException ex) {
    		logger
    		.info("DB Exception @SkillAssignBOImpl.loadServiceTypes() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(ex.getMessage());
    		} catch (Exception ex) {
    		ex.printStackTrace();
    		logger
    		.info("General Exception @SkillAssignBOImpl.loadServiceTypes() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(
    		"General Exception @SkillAssignBOImpl.loadServiceTypes() due to "
    				+ ex.getMessage());
    }
    	 
		return serviceTypes;
	}
	
	/**
	 * @param checkedSkills
	 * @return ArrayList of checkedSkillsVO
	 * @throws BusinessServiceException
	 */
	public ArrayList<CheckedSkillsVO> getCheckedSkills(CheckedSkillsVO checkedSkills) throws BusinessServiceException{
	
		ArrayList<CheckedSkillsVO> checkedSkillsVO = new ArrayList<CheckedSkillsVO>();
		try{
			if (checkedSkills.getRootNodeId() >= 0) {
				checkedSkillsVO = (ArrayList<CheckedSkillsVO>)iSkillAssignDao.getCheckedSkills(checkedSkills);
			} else {
				checkedSkillsVO = (ArrayList<CheckedSkillsVO>)iSkillAssignDao.getCheckedGeneralSkills(checkedSkills);
			}
		}catch (DBException ex) {
    		logger
    		.info("DB Exception @SkillAssignBOImpl.getCheckedSkills() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(ex.getMessage());
    		} catch (Exception ex) {
    		ex.printStackTrace();
    		logger.info("General Exception @SkillAssignBOImpl.getCheckedSkills() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(
    		"General Exception @SkillAssignBOImpl.getCheckedSkills() due to "
    				+ ex.getMessage());
    	}
    	
		return checkedSkillsVO;
	}
	
	/**
	 * @param assignRequest
	 * @return SkillAssignResponse
	 * @throws BusinessServiceException
	 */
	
	public SkillAssignResponse getGeneralSkills(SkillAssignRequest assignRequest)throws BusinessServiceException{
		 	
		SkillAssignResponse skillResponse = new SkillAssignResponse();
		// Get all the tree root elements and assign to the response object
		ArrayList generalSkills = new ArrayList();
		ArrayList<LookupVO> lookuplangList = new ArrayList<LookupVO>();
		ArrayList<LanguageVO> languageList = new ArrayList<LanguageVO>();
		ArrayList<LanguageVO> checkedlangList = new ArrayList<LanguageVO>();
		ArrayList<String> serviceProvList = new ArrayList<String>();
		LanguageVO languageVO = new LanguageVO();
		SkillAssignVO skill = new SkillAssignVO();
		skill.setResourceId(assignRequest.getResourceId());
		languageVO.setResourceId(assignRequest.getResourceId());
		SkillAssignVO skillDbVo = new SkillAssignVO();
		try{
			//Get language 
			lookuplangList = (ArrayList)iLookupDao.loadLanguages();
			checkedlangList = getCheckedLanguages(languageVO);
			languageList = alignAvailableLanguages(lookuplangList, checkedlangList);
			if(!languageList.isEmpty()){
				logger.info("-------------language List--------"+languageList.size());
			}
			generalSkills = (ArrayList)iSkillAssignDao.getGeneralSkills();
			
		}catch (DBException ex) {
    		logger
    		.info("DB Exception @SkillAssignBOImpl.getGeneralSkills() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(ex.getMessage());
    		} catch (Exception ex) {
    		ex.printStackTrace();
    		logger
    		.info("General Exception @SkillAssignBOImpl.getGeneralSkills() due to"
    				+ ex.getMessage());
    throw new BusinessServiceException(
    		"General Exception @SkillAssignBOImpl.getGeneralSkills() due to "
    				+ ex.getMessage());
    }
		skillResponse.setSkillTreeList(generalSkills);
		skillResponse.setLanguageList(languageList);
		// Get the name of the resource
		skill.setResourceId(assignRequest.getResourceId());
		try{
			skill=iSkillAssignDao.getResourceName(skill);
		}catch (DBException ex) {
    		logger
    		.info("DB Exception @SkillAssignBOImpl.getGeneralSkills() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(ex.getMessage());
    		} catch (Exception ex) {
    		ex.printStackTrace();
    		logger
    		.info("General Exception @SkillAssignBOImpl.getGeneralSkills() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(
    		"General Exception @SkillAssignBOImpl.getGeneralSkills() due to "
    				+ ex.getMessage());
    	}
		skillResponse.setResourceName(skill.getResourceName());
	 
		return skillResponse;
	}

	/**
	 * @param skillComplete
	 * @return SkillCompleteVO
	 * @throws BusinessServiceException
	 */
	public boolean getSkillsComplete(SkillCompletionVO skillComplete) throws BusinessServiceException {
		
		boolean isComplete = false;
		ArrayList<SkillCompletionVO> skillsCompletedList = null;
		try{
			skillsCompletedList =  (ArrayList<SkillCompletionVO>)iSkillAssignDao.getSkillsComplete(skillComplete);
		}catch (DBException ex) {
    			logger.info("DB Exception @SkillAssignBOImpl.getSkillsComplete() due to"
    				+ ex.getMessage());
    			throw new BusinessServiceException(ex.getMessage());
    		} catch (Exception ex) {
	    		logger.info("General Exception @SkillAssignBOImpl.getSkillsComplete() due to"
	    				+ ex.getMessage());
	    		throw new BusinessServiceException(
	    		"General Exception @SkillAssignBOImpl.getSkillsComplete() due to "
	    				+ ex.getMessage());
    		}
		int parentIds[] = new int[skillsCompletedList.size()];
		int rootNodeIds[] = new int[skillsCompletedList.size()];
		int parentCount = 0;
		int rootCount = 0;
		// get a list of parents
		// get a list of unique children
		for (int i=0; i<skillsCompletedList.size(); i++){
			SkillCompletionVO skillVO = skillsCompletedList.get(i);
			if(skillVO.getRootNodeId() == 0){
				parentIds[parentCount] = skillVO.getNodeId();
				parentCount++;
			}else{
				rootNodeIds[rootCount] = skillVO.getRootNodeId();
				rootCount++;
			}
		}
		
		//compare - if second list contains all the parents, isComplete = true
		for(int i=0; i<parentIds.length; i++){
			boolean found = false;
			for(int j=0; j<rootNodeIds.length; j++){
				if(parentIds[i] == rootNodeIds[j]){
					found = true;
					break;
				}
			}
			if (!found){
				isComplete = false;
				break;
			}else{
				isComplete = true;
			}
		}
		
		return isComplete;
	}
	
	private List getChildren(SkillNodeVO node) throws BusinessServiceException {
		
		try{
			return iSkillAssignDao.getChildren(node);
		}catch (DBException ex) {
    		logger.info("DB Exception @SkillAssignBOImpl.getChildren() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(ex.getMessage());
    		} catch (Exception ex) {
    		
    		logger.info("General Exception @SkillAssignBOImpl.getChildren() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(
    		"General Exception @SkillAssignBOImpl.getChildren() due to "
    				+ ex.getMessage());
    		}
    	
	}
	
	private void drillDownTree(SkillNodeVO node, List nodes) throws BusinessServiceException {
		
		nodes.add(node);
		List children = null;
		
		try {
			children = getChildren(node);
		} catch (BusinessServiceException bse) {
			throw bse;
		}
		
		if (children == null || children.size() == 0)
			return;
		
		for (int i = 0; i < children.size(); i++) {
			SkillNodeVO childNode = (SkillNodeVO)children.get(i);
			drillDownTree(childNode, nodes);
		}
	
		return;
	}
	
	/**
	 * 
	 * @param activityLinkKey
	 * @param activityName
	 * @param actLinkKeyType
	 * @param optionalVendorId
	 * @return
	 */
	public ActivityCheckList updateActivity(Long activityLinkKey, String activityName, String actLinkKeyType, Long optionalVendorId)  throws BusinessServiceException{
		 
		int actLinkKey = activityLinkKey.intValue();
		ActivityRegistry ar = new ActivityRegistry();
		LuActivity luActivity = new LuActivity();
		luActivity.setActName(activityName);
		luActivity.setActLinkKeyType(actLinkKeyType);
		try {
			luActivity = iLuActivityDao.query(luActivity);
		} catch (DBException ex) {
    		logger.info("DB Exception @SkillAssignBOImpl.getInsuranceType() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(ex.getMessage());
    		}
		ar.setActLinkKey(actLinkKey);
		ar.setActCompleted(1);
		ar.setActivityId(luActivity.getActivityId());
		try{
			//Commenting this to get values only from insurance selection thingy but not from old
			//logic which was build for uploading POLICY and turning status to green
			if (luActivity.getActivityId() == 7){
				//do nothing
			}
			else {
				iActivityRegistryDao.insert(ar);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	 
		return retrieveActivityList(actLinkKey, optionalVendorId);
	}
	
	/**
	 * 
	 * @param activityLinkKey
	 * @param optionalVendorId
	 * @return
	 */
	public ActivityCheckList retrieveActivityList(int activityLinkKey, Long optionalVendorId)throws BusinessServiceException {
		 
		ActivityCheckList acl = new ActivityCheckList();
		ActivityCheckList resourceAcl = new ActivityCheckList();
		resourceAcl = retrieveActivityListIndividuals(activityLinkKey);
		if(optionalVendorId != null)
		{
			ActivityCheckList aclVendor = new ActivityCheckList();
			aclVendor = retrieveActivityListIndividuals(optionalVendorId.intValue());
			resourceAcl.setBusinessInformationAllowed(aclVendor.getIsBusinessInformationAllowed());
			resourceAcl.setFinancialAllowed(aclVendor.getIsFinancialAllowed());
			resourceAcl.setWarrantyAllowed(aclVendor.getIsWarrantyAllowed());
			resourceAcl.setCredentialsAllowed(aclVendor.getIsCredentialsAllowed());
			resourceAcl.setTermsAndConditionsAllowed(aclVendor.getIsTermsAndConditionsAllowed());
			resourceAcl.setInsuranceAllowed(aclVendor.getIsInsuranceAllowed());
			resourceAcl.setW9Allowed(aclVendor.getIsW9Allowed());
			resourceAcl.setTeamMemberAllowed(aclVendor.getIsTeamMemberAllowed());
			acl = resourceAcl;
		}
		else
		{
			acl.setCoverageAreaAllowed(false);
			acl.setQualificationsAllowed(false);
			acl.setScheduleAllowed(false);
			acl.setSkillsAllowed(false);
			acl.setTeamMemberCredentialsAllowed(false);
		}
		 
		return acl;
	}
	
	/**
	 * 
	 * @param activityLinkKey
	 * @return
	 */
	public ActivityCheckList retrieveActivityListIndividuals(int activityLinkKey) {
		 
		ActivityCheckList acl = new ActivityCheckList();
		ActivityRegistry ar = new ActivityRegistry();
		ar.setActLinkKey(activityLinkKey);
		try{

			List activityRegistryList = iActivityRegistryDao.queryList(ar);
			List luActivityList = iLuActivityDao.queryList(null);

			LuActivity luActivity = null;

			for (int z = 0; z < activityRegistryList.size(); z++) {
				ActivityRegistry activityRegistry = (ActivityRegistry) activityRegistryList
						.get(z);

				if (activityRegistry.getActCompleted() == 1) {
					for (int y = 0; y < luActivityList.size(); y++) {

						luActivity = (LuActivity) luActivityList.get(y);

						if (luActivity.getActivityId() == activityRegistry.getActivityId())
						{

							break;
						}
					}
					if (luActivity.getActName().equals(MPConstants.SKILLS))
						acl.setSkillsAllowed(true);

				}

			}
		}catch (DBException ex) {
    		logger
    		.info("DB Exception @SkillAssignBOImpl.retrieveActivityList due to"
    				+ ex.getMessage());
    		
    		}
		 
		return acl;
	}

	private void  updateLanguage(ArrayList<LanguageVO> languageList, LanguageVO languageVO){
	 
		if(languageList != null)
		{
			for(int i=0; i<languageList.size(); i++)
			{	
				languageVO.setResourceId(languageList.get(i).getResourceId());
				languageVO.setLanguageId(languageList.get(i).getLanguageId());
				try {
					int result = iSkillAssignDao.insertLanguage(languageVO);
				} catch (DBException e) {
					logger.info("Caught Exception and ignoring",e);
				}
			}	
		}
		 
	}
	
	
	private void  deleteLanguage(ArrayList<LanguageVO> languageList, LanguageVO languageVO){
		
		if(languageList != null)
		{
			for(int i=0; i<languageList.size(); i++)
			{	
				languageVO.setResourceId(languageList.get(i).getResourceId());
				languageVO.setLanguageId(languageList.get(i).getLanguageId());
				try {
					iSkillAssignDao.deleteOldLanguages(languageVO);
				} catch (DBException e) {
					logger.info("Caught Exception and ignoring",e);
				}
			}	
		}
		
	}
	
	public ArrayList<LanguageVO> getCheckedLanguages(LanguageVO languageVO) throws BusinessServiceException{
	
		ArrayList<LanguageVO> checkedLangVOList = new ArrayList<LanguageVO>();
		try{
			checkedLangVOList = (ArrayList)iSkillAssignDao.getcheckedLanguages(languageVO);
		}catch (DBException ex) {
    		logger
    		.info("DB Exception @SkillAssignBOImpl.getInsuranceType() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(ex.getMessage());
    		} catch (Exception ex) {
    		ex.printStackTrace();
    		logger.info("General Exception @SkillAssignBOImpl.getCheckedLanguages() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(
    		"General Exception @SkillAssignBOImpl.getCheckedLanguages() due to "
    				+ ex.getMessage());
    		}
    	
		return checkedLangVOList;
	}
	
	
	private ArrayList alignAvailableLanguages(ArrayList<LookupVO> languages, ArrayList<LanguageVO> checked) {
		 
		ArrayList languageList = new ArrayList();
		for (int i=0; i<languages.size(); i++) {
			LanguageVO  languageVO = new LanguageVO();
			LookupVO lang = (LookupVO)languages.get(i);
			languageVO.setActive(false);
			languageVO.setDescr(lang.getDescr());
			languageVO.setLanguageType(lang.getType());
			languageVO.setLanguageId(Long.parseLong(lang.getId()));
			for (int j=0; j<checked.size(); j++) {
				LanguageVO chkd = (LanguageVO)checked.get(j);
				if(Long.parseLong(lang.getId()) == chkd.getLanguageId()) {
					languageVO.setActive(true);
				}
			}
			languageList.add(languageVO);
		}
		 
		return languageList;
	}
	
	public SkillAssignResponse updateSkillsActivity(SkillAssignVO  assignRequest) throws BusinessServiceException {
		 
		SkillAssignResponse skillResponse = new SkillAssignResponse(); 
		String resourceId = Long.toString(assignRequest.getResourceId());
		try {
			iActivityRegistryDao.updateResourceActivityStatus(resourceId, ActivityRegistryConstants.RESOURCE_SKILLS);
		} catch (DBException ex) {
    		logger
    		.info("DB Exception @SkillAssignBOImpl.updateSkillsActivity() due to"
    				+ ex.getMessage());
    		throw new BusinessServiceException(ex.getMessage());
    		} catch (Exception ex) {
    		ex.printStackTrace();
    		logger
    		.info("General Exception @SkillAssignBOImpl.updateSkillsActivity() due to"
    				+ ex.getMessage());
    throw new BusinessServiceException(
    		"General Exception @SkillAssignBOImpl.updateSkillsActivity() due to "
    				+ ex.getMessage());
    }
    	 
		return skillResponse;
	}
	
	
	public List<CheckedSkillsVO> getAllCheckedSkills(Integer resourceId)
	throws BusinessServiceException {
		List <CheckedSkillsVO> checkedSkillsVO = new ArrayList<CheckedSkillsVO>();
		try{
			
				checkedSkillsVO = (ArrayList<CheckedSkillsVO>)iSkillAssignDao.getALLCheckedSkillsForResource(resourceId);
			
		}catch (DBException ex) {
			logger
			.info("DB Exception @SkillAssignBOImpl.getCheckedSkills() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
			} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignBOImpl.getCheckedSkills() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(
			"General Exception @SkillAssignBOImpl.getCheckedSkills() due to "
					+ ex.getMessage());
		}
		
		return checkedSkillsVO;
	}
	public Map<Integer, List<SkillNodeVO>> getSkillTree(List<Integer> rootNodeIds)
			throws BusinessServiceException {
		List <SkillNodeVO> skillsList = new ArrayList<SkillNodeVO>();
		List<ServiceTypesVO> rootServiceTypes = new ArrayList<ServiceTypesVO>();
		Map <Integer, List<SkillNodeVO>> skillMap = new HashMap<Integer,List<SkillNodeVO>>();
		try{
			SkillNodeIdsVO requestVo = new SkillNodeIdsVO();
			requestVo.setSkillNodeIds((ArrayList)rootNodeIds);
			skillsList = (ArrayList<SkillNodeVO>)iSkillAssignDao.getSkillTree(requestVo);
			rootServiceTypes  = iSkillAssignDao.getServiceTypeTree(requestVo);
			skillMap = getSkillTreeMap(skillsList,rootNodeIds,rootServiceTypes);
			
		}catch (DBException ex) {
			logger
			.info("DB Exception @SkillAssignBOImpl.getCheckedSkills() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
			} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignBOImpl.getCheckedSkills() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(
			"General Exception @SkillAssignBOImpl.getCheckedSkills() due to "
					+ ex.getMessage());
		}
		
		return skillMap;
	}
	
	private Map <Integer, List<SkillNodeVO >> getSkillTreeMap(List<SkillNodeVO> resultNodes,List<Integer> rootNodeIds, List<ServiceTypesVO> rootServiceTypes)throws BusinessServiceException {
		
		
		Map<Integer, List<SkillNodeVO> >rootNodeMap = new HashMap<Integer,List<SkillNodeVO>>();
		Map<Integer, List<ServiceTypesVO> >serviceTyperootNodeMap = new HashMap<Integer, List<ServiceTypesVO> >();
		for(ServiceTypesVO vo : rootServiceTypes ) {
			//create the root Node Map for first time
			if(vo.getRootNodeId() != null && !serviceTyperootNodeMap.containsKey(vo.getRootNodeId())){
				serviceTyperootNodeMap.put(vo.getRootNodeId(),new ArrayList<ServiceTypesVO>());
			}
			serviceTyperootNodeMap.get(vo.getRootNodeId()).add(vo);
		}
		
		
		for(Integer id : rootNodeIds){
			//create the root Node Map for first time
			rootNodeMap.put(id,new ArrayList<SkillNodeVO>());
			
			Iterator itr = resultNodes.iterator();
			//build parent node map 
			while(itr.hasNext()){
				SkillNodeVO vo = (SkillNodeVO) itr.next();
				if(vo.getRootNodeId().intValue() == id) {
					rootNodeMap.get(id).add(vo);
				}
			}
		}
		
		return getbuiltSkillTree(rootNodeMap,serviceTyperootNodeMap);
	}
	
	
	private Map<Integer, List<SkillNodeVO> > getbuiltSkillTree(Map<Integer, List<SkillNodeVO> >rootNodeMap,Map<Integer, List<ServiceTypesVO> >serviceTyperootNodeMap) throws BusinessServiceException{
		Map <Integer, List<SkillNodeVO >> skillMap = new HashMap<Integer,List<SkillNodeVO>>();
		for (Integer key : rootNodeMap.keySet()) {
			List<SkillNodeVO> skillList = rootNodeMap.get(key);
			List<SkillNodeVO> resultSkillList = new ArrayList<SkillNodeVO>();
			List<ServiceTypesVO> rootServiceTypes =serviceTyperootNodeMap.get(key);
			skillMap.put(key, resultSkillList);
			for(SkillNodeVO skill : skillList){
				try{
				skill.setServiceTypes((ArrayList)getClonedServiceTypes(rootServiceTypes));
				}catch(Exception e) {
					throw new BusinessServiceException(e);
				}
				drillDownSkillTree(skill,resultSkillList,skillList);
			}
		}
		return skillMap;
	}
	
	private List<ServiceTypesVO> getClonedServiceTypes(List<ServiceTypesVO> rootServiceTypes) throws Exception{
		List<ServiceTypesVO> cloneList = new ArrayList<ServiceTypesVO>();
		for(ServiceTypesVO vo : rootServiceTypes ) {
			ServiceTypesVO clone =  new ServiceTypesVO();
			PropertyUtils.copyProperties(clone,vo);
			
			cloneList.add(clone);
		}
		return cloneList;
	}
	
	private List<SkillNodeVO> getSkillChildern(SkillNodeVO questionVo, List <SkillNodeVO> suppliedVOs ) {
		List<SkillNodeVO> children = new ArrayList<SkillNodeVO>();
		for(SkillNodeVO vo : suppliedVOs) {
			if(vo.getParentNodeId() == questionVo.getNodeId()){
				children.add(vo);
			}
		}
		return children;
	}
	private void drillDownSkillTree(SkillNodeVO questionnode, List resultnodes, List <SkillNodeVO> suppliedVOs) throws BusinessServiceException {
		if(! resultnodes.contains(questionnode)) resultnodes.add(questionnode);
		List<SkillNodeVO> children = null;
		children = getSkillChildern(questionnode,suppliedVOs);
		if (children == null || children.size() == 0)
			return;
		for (int i = 0; i < children.size(); i++) {
			SkillNodeVO childNode = children.get(i);
			drillDownSkillTree(childNode, resultnodes,suppliedVOs);
		}
	
		return;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.ISkillAssignBO#getServiceTypeTree(java.util.List)
	 */
	public List<ServiceTypesVO> getServiceTypeTree(List<Integer> rootNodeIds)
			throws BusinessServiceException {
		List <ServiceTypesVO> serviceTypes = new ArrayList<ServiceTypesVO>();
		
		try{
			SkillNodeIdsVO requestVo = new SkillNodeIdsVO();
			requestVo.setSkillNodeIds((ArrayList)rootNodeIds);
			serviceTypes = (ArrayList<ServiceTypesVO>)iSkillAssignDao.getServiceTypeTree(requestVo);
			
			
		}catch (DBException ex) {
			logger
			.info("DB Exception @SkillAssignBOImpl.getCheckedSkills() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
			} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignBOImpl.getCheckedSkills() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(
			"General Exception @SkillAssignBOImpl.getCheckedSkills() due to "
					+ ex.getMessage());
		}
		
		return serviceTypes;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<ServiceTypesVO> getServiceTypes(Integer mainCategoryId) throws BusinessServiceException, DBException {
		ArrayList<ServiceTypesVO> serviceTypes = new ArrayList<ServiceTypesVO>();
		SkillNodeVO skillNodeReq = new SkillNodeVO();
		skillNodeReq.setNodeId(mainCategoryId);

        //SkillAssignDAO assignDAO = (SkillAssignDAO) MPSpringLoaderPlugIn.getCtx().getBean("SkillAssignDAO");

		try{
			serviceTypes = (ArrayList<ServiceTypesVO>)iSkillAssignDao.getServiceTypes(skillNodeReq);			
		}catch(DataAccessException dae){
			logger.info("[DataAccessException] " + StackTraceHelper.getStackTrace(dae));
			throw new BusinessServiceException("Unable to retrieve the service types from the database.", dae);
		}
		return serviceTypes;
	}
	/* 
	 * method which returns root node Id for a particular node Id
	 */
	public Integer getRootNodeId(Integer nodeId)throws BusinessServiceException {
		Integer rootNodeId;
		
		try{
		
			rootNodeId = iSkillAssignDao.getRootNodeId(nodeId);
			
			
		}catch (DBException ex) {
			logger.info("DB Exception @SkillAssignBOImpl.getRootNodeId() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
			} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @SkillAssignBOImpl.getRootNodeId() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(
			"General Exception @SkillAssignBOImpl.getRootNodeId() due to "
					+ ex.getMessage());
		}
		
		return rootNodeId;
	}
	
}
