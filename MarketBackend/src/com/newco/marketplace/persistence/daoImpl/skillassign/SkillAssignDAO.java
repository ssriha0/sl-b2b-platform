/*
 * SkillAssignDAO.java       1.0     2007/05/18
 */
package com.newco.marketplace.persistence.daoImpl.skillassign;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.skillTree.CheckedSkillsVO;
import com.newco.marketplace.dto.vo.skillTree.SkillAssignVO;
import com.newco.marketplace.dto.vo.skillTree.SkillCompletionVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;




/**
 * SkillAssign DAO interface
 * 
 * @version
 * @author zizrale
 *
 */
public interface SkillAssignDAO 
{
    /**
     * @param skillVO
     * @return SkillAssignVO
     * @throws DataAccessException
     */
    public SkillAssignVO assign(SkillAssignVO skillVO) throws DataAccessException;
    
    /**
     * @param skillVO
     * @throws DataAccessException
     */
    public void assignServiceTypes(SkillAssignVO skillVO) throws DataAccessException;
    /**
     * @param treeRequestVO
     * @return List of Skills
     * @throws DataAccessException
     */
    public List getChildren(SkillNodeVO treeRequestVO) throws DataAccessException;
    
    /**
     * @param requestVO
     * @return List of Service Types
     * @throws DataAccessException
     */
    public List getServiceTypes(SkillNodeVO requestVO) throws DataAccessException;
    
    /**
     * @return List of general skills
     * @throws DataAccessException
     */
    public List getGeneralSkills() throws DataAccessException;
    
    /**
     * @param skillVO
     * @return SkillAssignVO
     * @throws DataAccessException
     */
    public SkillAssignVO getResourceName(SkillAssignVO skillVO) throws DataAccessException;
    
    /**
     * @param checkedVO
     * @return List of CheckedSkillsVO - skills that are checked for this resourceId
     * @throws DataAccessException
     */
    public List getCheckedGeneralSkills(CheckedSkillsVO checkedVO) throws DataAccessException;
    
    /**
     * @param checkedVO
     * @return List of CheckedSkillsVO - skills that are checked for this resourceId
     * @throws DataAccessException
     */
    public List getCheckedSkills(CheckedSkillsVO checkedVO) throws DataAccessException;
    
    /**
     * @param skillVO
     * @throws DataAccessException
     */
    public void deleteOldGeneralSkills(SkillAssignVO skillVO) throws DataAccessException;
    
    /**
     * @param skillVO
     * @throws DataAccessException
     */
    public void deleteOldSkills(SkillAssignVO skillVO) throws DataAccessException;
    
    /**
     * @param skillVO
     * @throws DataAccessException
     */
    public void deleteOldSkillsNodes(SkillAssignVO skillVO) throws DataAccessException;
    
    /**
     * @param skillVO
     * @return SkillAssignVO
     * @throws DataAccessException
     */
    public SkillAssignVO getResourceSkillId(SkillAssignVO skillVO) throws DataAccessException;
    
    /**
     * @param skillCompleteVO
     * @return SkillCompletionVO
     * @throws DataAccessException
     */
    public List getSkillsComplete(SkillCompletionVO skillCompleteVO) throws DataAccessException;
}