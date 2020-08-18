/*
 * SkillAssignDAOImpl        1.0     2007/05/18
 */
package com.newco.marketplace.persistence.daoImpl.skillTree;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.skillTree.CheckedSkillsVO;
import com.newco.marketplace.dto.vo.skillTree.SkillAssignVO;
import com.newco.marketplace.dto.vo.skillTree.SkillCompletionVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.persistence.iDao.skillTree.SkillAssignDAO;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * SkillAssign DAO Implementation
 * 
 * @version
 * @author zizrale
 *
 */
public class SkillAssignDAOImpl extends ABaseImplDao implements SkillAssignDAO
{
	public SkillAssignVO assign(SkillAssignVO skillVO) throws DataAccessException{
		Integer id = null;

        try{
            id  = (Integer)insert("skillAssign.insert", skillVO);
        }catch (Exception e){
            e.printStackTrace();
        }
        skillVO.setResourceSkillId(id.intValue());
        return skillVO;
    }

	public void assignServiceTypes(SkillAssignVO skillVO) throws DataAccessException{
		insert("servTypeAssign.insert", skillVO);
	}
	
	public List getChildren(SkillNodeVO treeRequestVO) throws DataAccessException{
		return queryForList("getChildrenForNode.query", treeRequestVO);
	}
	
	public List getServiceTypes(SkillNodeVO requestVO) throws DataAccessException{
		return queryForList("getServiceTypes.query", requestVO);
	}
	
	public List getGeneralSkills() throws DataAccessException{
		return queryForList("getGeneralSkills.query", null);
	}
	
	public SkillAssignVO getResourceName(SkillAssignVO skillVO) throws DataAccessException{
		return (SkillAssignVO)queryForObject("getResourceName.query", skillVO);
	}
	
	public List getCheckedGeneralSkills(CheckedSkillsVO checkedVO) throws DataAccessException{
		return queryForList("getCheckedGeneralSkills.query", checkedVO);
	}
	
	public List getCheckedSkills(CheckedSkillsVO checkedVO) throws DataAccessException{
		return queryForList("getCheckedSkills.query", checkedVO);
	}
	
	public void deleteOldGeneralSkills(SkillAssignVO skillVO) throws DataAccessException {
		delete("removeOldChecksGeneral.delete", skillVO);
	}
	
	public void deleteOldSkills(SkillAssignVO skillVO) throws DataAccessException{
		delete("removeOldChecks.delete", skillVO);
	}
	
	public void deleteOldSkillsNodes(SkillAssignVO skillVO) throws DataAccessException{
		delete("removeOldChecksNodes.delete", skillVO);
	}
	
	public SkillAssignVO getResourceSkillId(SkillAssignVO skillVO) throws DataAccessException{
		return (SkillAssignVO)queryForObject("getResourceSkillId.query", skillVO);
	}
	
	public List getSkillsComplete(SkillCompletionVO skillCompleteVO) throws DataAccessException{
		return queryForList("getCompletion.query", skillCompleteVO);
	}
}