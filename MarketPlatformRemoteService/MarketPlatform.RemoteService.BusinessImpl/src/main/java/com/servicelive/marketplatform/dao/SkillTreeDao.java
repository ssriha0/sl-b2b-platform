package com.servicelive.marketplatform.dao;

import com.servicelive.marketplatform.provider.domain.SkillNode;

import javax.persistence.Query;
import java.util.List;

public class SkillTreeDao extends BaseEntityDao implements ISkillTreeDao {

	public SkillNode findById(Long skillTreeId) {
		return entityMgr.find(SkillNode.class, skillTreeId);
	}

    public List<SkillNode> getAllAvailableProviderSkills() {
        Query q = entityMgr.createQuery("SELECT skills FROM SkillNode skills");
        return (List<SkillNode>) q.getResultList();
    }

}
