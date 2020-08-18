package com.newco.marketplace.business.businessImpl.skillassign;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.dto.request.skillTree.SkillAssignRequest;
import com.newco.marketplace.dto.response.skillTree.SkillAssignResponse;
import com.newco.marketplace.dto.vo.skillTree.CheckedSkillsVO;
import com.newco.marketplace.dto.vo.skillTree.ServiceTypesVO;
import com.newco.marketplace.dto.vo.skillTree.SkillAssignVO;
import com.newco.marketplace.dto.vo.skillTree.SkillCompletionVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.dto.vo.skillTree.SkillServiceTypeVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.skillTree.MarketplaceSearchDAO;
import com.newco.marketplace.persistence.iDao.skillTree.SkillAssignDAO;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.business.ABaseBO;

/**
 * @author zizrale
 *
 */
public class SkillAssignBusinessBean extends ABaseBO{
	
        SkillAssignDAO assignDAO = null;
        
	/**
	 * @param assignRequest  
	 * @return SkillAssignResponse
	 * @throws BusinessServiceException */
	public SkillAssignResponse assignSkills(SkillAssignRequest assignRequest) throws BusinessServiceException {
		  	SkillAssignVO assignVO = new SkillAssignVO();
	        
	        assignVO.setResourceId(assignRequest.getResourceId());
	        assignVO.setModifiedBy(assignRequest.getModifiedBy());
	      
	        java.util.Date today = new java.util.Date();
	        java.sql.Date now = new java.sql.Date(today.getTime());
	        assignVO.setCreatedDate(now);
	        assignVO.setModifiedDate(now);
	        ArrayList<SkillServiceTypeVO> skillServTypeArray = (ArrayList<SkillServiceTypeVO>) assignRequest.getSkillServiceArray();
	        SkillAssignResponse skillResponse = new SkillAssignResponse();
	        
	        try{
	            // find the DAO
	            SkillAssignDAO assignDAO = (SkillAssignDAO) MPSpringLoaderPlugIn.getCtx().getBean("SkillAssignDAO");
	            
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
		            SkillAssignVO tempSkillAssVO = null;
		            //save the skill assingnments
		            tempSkillAssVO = assignDAO.assign(assignVO);
		            if(skillServTypeArray.get(i).getServiceTypeId() != -1){ //means its coming from the grid, and not the first page
		            	assignVO.setResourceSkillId(tempSkillAssVO.getResourceSkillId());
		            	assignDAO.assignServiceTypes(assignVO);
		            }
	        	}
	        	
	        	if (assignRequest.getSkillServiceArrayToDelete() != null){
	        		// delete all the old values
	        		deleteOldChecked(assignRequest.getSkillServiceArrayToDelete(), assignRequest.getResourceId());
	        	}
	        	
	            skillResponse.setErrorId(0);
	            skillResponse.setMessage("The update was successful");
	            
	        }catch (DataAccessException dae){
	        	logger.info("[DataAccessException] " + StackTraceHelper.getStackTrace(dae));
	        	throw new BusinessServiceException("Unable to assign skills to a resource.", dae);
	        }
	        
	        return skillResponse;
	}
	
	private void deleteOldChecked(ArrayList<SkillServiceTypeVO> toDelete, long resourceId) throws BusinessServiceException {
		SkillAssignVO assignVO = new SkillAssignVO();
        
		assignVO.setResourceId(resourceId);
		
		SkillAssignDAO assignDAO = (SkillAssignDAO) MPSpringLoaderPlugIn.getCtx().getBean("SkillAssignDAO");
		
		for (int i=0; i<toDelete.size(); i++){
			SkillServiceTypeVO nodeToDelete = toDelete.get(i);
			if(nodeToDelete.getServiceTypeId() != 0){ // has service types - comes from the grid
				assignVO.setNodeId(nodeToDelete.getSkillNodeId());
				assignVO.setSkillTypeId(nodeToDelete.getServiceTypeId());
				SkillAssignVO tempSkillAssVO = assignDAO.getResourceSkillId(assignVO);
				assignVO.setResourceSkillId(tempSkillAssVO.getResourceSkillId());
				assignDAO.deleteOldSkills(assignVO);
				assignDAO.deleteOldSkillsNodes(assignVO);
			} else { // general skills
				assignVO.setNodeId(nodeToDelete.getSkillNodeId());
				// delete all the guys where parent id = this node
				deleteMyChildren(nodeToDelete.getSkillNodeId(), resourceId);
				// then delete the parents
				assignDAO.deleteOldGeneralSkills(assignVO);
				
			}
		}
	}

	private void deleteMyChildren(int nodeId, long resourceId){
		SkillAssignDAO assignDAO = (SkillAssignDAO) MPSpringLoaderPlugIn.getCtx().getBean("SkillAssignDAO");
		SkillAssignVO assignVO = new SkillAssignVO();
		
		//Set up checkedSkillsVO (resourceId, rootNodeId)
		CheckedSkillsVO checkedSkills = new CheckedSkillsVO();
		checkedSkills.setRootNodeId(nodeId);
		checkedSkills.setResourceId(resourceId);
		
		//getCheckedSkills, expect checkedSkillsVO back
		List<CheckedSkillsVO> chkdSkills = assignDAO.getCheckedSkills(checkedSkills);
		
		//put info in SkillAssignVO
		for(int i=0; i<chkdSkills.size(); i++){
			CheckedSkillsVO checkedSkill = chkdSkills.get(i);
			assignVO.setResourceId(resourceId);
			assignVO.setNodeId(checkedSkill.getNodeId());
			assignVO.setSkillTypeId(checkedSkill.getServiceTypeId());
			// do delete like above for the grid for all items returned
			SkillAssignVO tempSkillAssVO = assignDAO.getResourceSkillId(assignVO);
			assignVO.setResourceSkillId(tempSkillAssVO.getResourceSkillId());
			assignDAO.deleteOldSkills(assignVO);
			assignDAO.deleteOldSkillsNodes(assignVO);
		}
	}
	
	/**
	 * @param assignRequest
	 * @return SkillAssignResponse
	 * @throws BusinessServiceException
	 */
	public SkillAssignResponse getSkills(SkillAssignRequest assignRequest)throws BusinessServiceException {
		SkillAssignResponse skillResponse = new SkillAssignResponse();
		
		SkillNodeVO skillNodeReq = new SkillNodeVO();
		skillNodeReq.setNodeId(assignRequest.getNodeId());
		// Get the name of the parent node for display
		try{
			SkillNodeVO tempSkillNode = new SkillNodeVO();
			MarketplaceSearchDAO searchDAO = (MarketplaceSearchDAO)MPSpringLoaderPlugIn.getCtx().getBean("MarketplaceSearchDao");
			tempSkillNode = searchDAO.getNodeNameById(skillNodeReq);
			skillNodeReq.setNodeName(tempSkillNode.getNodeName());
			skillNodeReq.setLevel(1);
		}catch(DataAccessException dae){
			logger.info("[DataAccessException] " + StackTraceHelper.getStackTrace(dae));
			throw new BusinessServiceException("Unable to retrieve node name from the database.", dae);
		}
		
		// Get all the tree children elements and assign to the response object
		ArrayList treeChildren = new ArrayList();
		try{
			drillDownTree(skillNodeReq, treeChildren);
		}catch(DataAccessException dae){
			logger.info("[DataAccessException] " + StackTraceHelper.getStackTrace(dae));
			throw new BusinessServiceException("Unable to retrieve the skill tree from the database.", dae);
		}
		
		
		// Now get all the service types and assign them to the response object
		SkillAssignDAO assignDAO = (SkillAssignDAO)MPSpringLoaderPlugIn.getCtx().getBean("SkillAssignDAO");

		// loop through and assign service types to each skill node 
		//(the are the same for each part of the given tree)
		for (int i=0; i<treeChildren.size(); i++){
			SkillNodeVO node = (SkillNodeVO)treeChildren.get(i);
			ArrayList<ServiceTypesVO> serviceTypes = loadServiceTypes(skillNodeReq, assignDAO);
			node.setServiceTypes(serviceTypes);
		}
		
		ArrayList<ServiceTypesVO> serviceTypes = loadServiceTypes(skillNodeReq, assignDAO);
		skillResponse.setServiceList(serviceTypes);
		
		skillResponse.setSkillTreeList(treeChildren);
		
		return skillResponse;
	}

	private ArrayList<ServiceTypesVO> loadServiceTypes(SkillNodeVO skillNodeReq, SkillAssignDAO assignDAO) throws BusinessServiceException {
		ArrayList<ServiceTypesVO> serviceTypes = new ArrayList<ServiceTypesVO>();
		
		try{
			serviceTypes = (ArrayList<ServiceTypesVO>)assignDAO.getServiceTypes(skillNodeReq);			
		}catch(DataAccessException dae){
			logger.info("[DataAccessException] " + StackTraceHelper.getStackTrace(dae));
			throw new BusinessServiceException("Unable to retrieve the service types from the database.", dae);
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
				checkedSkillsVO = (ArrayList<CheckedSkillsVO>)assignDAO.getCheckedSkills(checkedSkills);
			} else {
				checkedSkillsVO = (ArrayList<CheckedSkillsVO>)assignDAO.getCheckedGeneralSkills(checkedSkills);
			}
		}catch(DataAccessException dae){
			logger.info("[DataAccessException] " + StackTraceHelper.getStackTrace(dae));
			throw new BusinessServiceException("Unable to retrieve the service types from the database.", dae);
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
		try{
			generalSkills = (ArrayList)assignDAO.getGeneralSkills();
		}catch(DataAccessException dae){
			logger.info("[DataAccessException] " + StackTraceHelper.getStackTrace(dae));
			throw new BusinessServiceException("Unable to retrieve the skill tree from the database.", dae);
		}
		skillResponse.setSkillTreeList(generalSkills);
		
		// Get the name of the resource
		SkillAssignVO skill = new SkillAssignVO();
		skill.setResourceId(assignRequest.getResourceId());
		try{
			skill=assignDAO.getResourceName(skill);
		}catch(DataAccessException dae){
			logger.info("[DataAccessException] " + StackTraceHelper.getStackTrace(dae));
			throw new BusinessServiceException("Unable to retrieve the service types from the database.", dae);
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
			skillsCompletedList =  (ArrayList<SkillCompletionVO>)assignDAO.getSkillsComplete(skillComplete);
	
		}catch(DataAccessException dae){
			logger.info("[DataAccessException] " + StackTraceHelper.getStackTrace(dae));
			throw new BusinessServiceException("Unable to retrieve the skill tree from the database.", dae);
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
			return assignDAO.getChildren(node);
			
		}catch(DataAccessException dae){
			logger.info("[DataAccessException] " + StackTraceHelper.getStackTrace(dae));
			throw new BusinessServiceException("Unable to retrieve the skill tree from the database.", dae);
		}
	}
	
	private void drillDownTree(SkillNodeVO node, List nodes) throws BusinessServiceException {
		//System.out.println("Visting: " + node);
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

    public SkillAssignDAO getAssignDAO() {
    
        return assignDAO;
    }

    public void setAssignDAO(SkillAssignDAO assignDAO) {
    
        this.assignDAO = assignDAO;
    }
}
