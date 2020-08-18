package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.dto.vo.skillTree.SkillNodeIdsVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.CheckedSkillsVO;
import com.newco.marketplace.vo.provider.LanguageVO;
import com.newco.marketplace.vo.provider.ServiceTypesVO;
import com.newco.marketplace.vo.provider.SkillAssignVO;
import com.newco.marketplace.vo.provider.SkillCompletionVO;
import com.newco.marketplace.vo.provider.SkillNodeVO;

public interface ISkillAssignDao 
{

	public List getResourceSkillIdsList(SkillAssignVO skillVO) throws DBException;
    public SkillAssignVO assign(SkillAssignVO skillVO) throws DBException;
    public void assignServiceTypes(SkillAssignVO skillVO) throws DBException;
    public List getChildren(SkillNodeVO treeRequestVO) throws DBException;
    public List getServiceTypes(SkillNodeVO requestVO) throws DBException;
    public List getGeneralSkills() throws DBException;
    public SkillAssignVO getResourceName(SkillAssignVO skillVO) throws DBException;
    public List getCheckedGeneralSkills(CheckedSkillsVO checkedVO) throws DBException;
    public List getCheckedSkills(CheckedSkillsVO checkedVO) throws DBException;
    public void deleteOldGeneralSkills(SkillAssignVO skillVO) throws DBException;
    public void deleteOldSkills(SkillAssignVO skillVO) throws DBException;
    public void deleteOldSkillsNodes(SkillAssignVO skillVO) throws DBException;
    public SkillAssignVO getResourceSkillId(SkillAssignVO skillVO) throws DBException;
    public List getSkillsComplete(SkillCompletionVO skillCompleteVO) throws DBException;
    public int insertLanguage(LanguageVO  languageVO) throws DBException;
    public void deleteOldLanguages(LanguageVO  languageVO) throws DBException;
    public List getcheckedLanguages(LanguageVO  languageVO) throws DBException;
    public SkillNodeVO getSkillNodeNameById(SkillNodeVO node) throws DBException;
    public List getResourceLanguages(Integer resourceId) throws DBException;
    public List getResourceSkillCat(Integer resourceId) throws DBException;
    public List getSkillNodeName(Integer nodeId) throws DBException;
    public List <SkillNodeVO> getSkillsForResource(Integer resourceId) throws DBException;
    public List getALLCheckedSkillsForResource(Integer resourceId) throws DBException;
    public List <SkillNodeVO> getSkillTree(SkillNodeIdsVO nodeIds) throws DBException;
    public List <ServiceTypesVO> getServiceTypeTree(SkillNodeIdsVO nodeIds) throws DBException;
    
    public Integer getRootNodeId(Integer nodeId)throws DBException;
    public Integer loadProviderId(String providerId); 
	public List loadSkillsOfResource(String providerId) throws DBException;
	public SkillAssignVO getResourceSkillIdByNode(SkillAssignVO skillVO) throws DBException;
	public List<Integer> getChildServiceTypeIdList(SkillAssignVO skillVO) throws DBException;
	public List<Integer> getRootServiceTypeIdList(SkillAssignVO skillVO) throws DBException;
	public void deleteRootSkillServiceIdList(List<Integer> rootServiceTypeIdList)throws DBException;
	public SkillAssignVO fetchResourceSkillId(SkillAssignVO skillVO) throws DBException;
	public List<Integer> fetchResourceSkillEntry(SkillAssignVO skillVO) throws DBException;
	public void deleteRootNodeId(SkillAssignVO skillVO) throws DBException;
	/**
	 * @param skillVO
	 * @return
	 * @throws DBException
	 */
	public List<Integer> retrieveResourceSkillEntry(SkillAssignVO skillVO) throws DBException;
	/**
	 * 
	 * @param skillVO
	 * @return
	 * @throws DBException
	 */
	public SkillAssignVO getRootNodeResourceSkillId(SkillAssignVO skillVO) throws DBException;
	
	
	/**
	 * @param skillVO
	 * @throws DBException
	 */
	public void deleteOtherNodes(SkillAssignVO skillVO) throws DBException;
	
	
	/**
	 * @param skillVO
	 * @return
	 * @throws DBException
	 */
	public List<Integer> fetchResourceSkillServiceType(SkillAssignVO skillVO) throws DBException;

}
