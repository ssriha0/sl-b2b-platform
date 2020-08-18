package com.servicelive.marketplatform.dao;

import com.servicelive.marketplatform.provider.domain.SkillNode;

import java.util.List;

public interface ISkillTreeDao extends INotificationEntityDao {

	public SkillNode findById(Long skillTreeId);
    public List<SkillNode> getAllAvailableProviderSkills();
}
