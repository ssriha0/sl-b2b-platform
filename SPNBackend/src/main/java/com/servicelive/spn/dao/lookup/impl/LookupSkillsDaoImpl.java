/**
 *
 */
package com.servicelive.spn.dao.lookup.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupSkills;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupSkillsDao;

/**
 * @author hoza
 *
 */
@Transactional
public class LookupSkillsDaoImpl extends AbstractBaseDao implements LookupSkillsDao {

	/*
	 * (non-Javadoc)
	 *
	 * @see com.servicelive.spn.dao.lookup.LookupSkillsDao#getSkillNodeForId(java.lang.Integer)
	 */
	public LookupSkills getSkillNodeForId(Integer nodeId) throws Exception {
		LookupSkills skillNode = findById(nodeId);
		return skillNode;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.servicelive.spn.dao.lookup.LookupSkillsDao#getRootNodes()
	 */
	public List<LookupSkills> getRootNodes() throws Exception {
		List<LookupSkills> results = findByProperty("isRootNode", Boolean.TRUE);
		return results;
	}

	public List<LookupSkills> getSubCategoriesFromParentNodeId(Integer parentNodeId)  throws Exception{
		List<LookupSkills> results = findByProperty("parentNodeId", parentNodeId);

		return results;
	}

	@SuppressWarnings("unchecked")
	public List <LookupSkills> findAll ( int... rowStartIdxAndCount) throws Exception {
		return ( List <LookupSkills> )super.findAll("LookupSkills", rowStartIdxAndCount);
	}

	public LookupSkills findById(Integer id)  throws Exception {
		return ( LookupSkills) super.findById(LookupSkills.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<LookupSkills> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception {
		return ( List<LookupSkills>) super.findByProperty("LookupSkills",propertyName,value, rowStartIdxAndCount);
	}
	@SuppressWarnings("unchecked")
	public List<LookupSkills> findAllSkillsByList (Set<Integer> nodeIds )  throws Exception {
		Query query = getEntityManager().createQuery("from LookupSkills o where o.id in (:nodeIds)");
		query.setParameter("nodeIds", nodeIds);
		return  query.getResultList();
		
	}


}
