package com.newco.marketplace.translator.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * SkillTree entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.SkillTree
 * @author MyEclipse Persistence Tools
 */

public class SkillTreeDAO extends JpaDaoSupport implements ISkillTreeDAO {
	// property constants
	public static final String LEVEL = "level";
	public static final String LEAF = "leaf";
	public static final String JOB_CLASSIFIER = "jobClassifier";
	public static final String ROOT = "root";
	public static final String NODE_NAME = "nodeName";
	public static final String DESCRIPTION_CONTENT = "descriptionContent";
	public static final String MATCH_WEIGHT_FACTOR = "matchWeightFactor";
	public static final String DEPTH_LIMIT = "depthLimit";
	public static final String PARENT_NODE = "parentNode";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String SORT_ORDER = "sortOrder";

	/**
	 * Perform an initial save of a previously unsaved SkillTree entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object) EntityManager#persist}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * SkillTreeDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            SkillTree entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(SkillTree entity) {
		logger.info("saving SkillTree instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent SkillTree entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the
	 * {@link javax.persistence.EntityManager#remove(Object) EntityManager#delete}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * SkillTreeDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            SkillTree entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(SkillTree entity) {
		logger.info("deleting SkillTree instance");
		try {
			entity = getJpaTemplate().getReference(SkillTree.class,
					entity.getNodeId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved SkillTree entity and return it or a copy of it
	 * to the sender. A copy of the SkillTree entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = SkillTreeDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            SkillTree entity to update
	 * @returns SkillTree the persisted SkillTree entity instance, may not be
	 *          the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public SkillTree update(SkillTree entity) {
		logger.info("updating SkillTree instance");
		try {
			SkillTree result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public SkillTree findById(Integer id) {
		logger.info("finding SkillTree instance with id: " + id);
		try {
			SkillTree instance = getJpaTemplate().find(SkillTree.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all SkillTree entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the SkillTree property to query
	 * @param value
	 *            the property value to match
	 * @return List<SkillTree> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<SkillTree> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding SkillTree instance with property: " + propertyName
				+ ", value: " + value);
		try {
			final String queryString = "select model from SkillTree model where model."
					+ propertyName + "= :propertyValue";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("propertyValue", value);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SkillTree> findByLevel(Object level) {
		return findByProperty(LEVEL, level);
	}

	public List<SkillTree> findByLeaf(Object leaf) {
		return findByProperty(LEAF, leaf);
	}

	public List<SkillTree> findByJobClassifier(Object jobClassifier) {
		return findByProperty(JOB_CLASSIFIER, jobClassifier);
	}

	public List<SkillTree> findByRoot(Object root) {
		return findByProperty(ROOT, root);
	}

	public List<SkillTree> findByNodeName(Object nodeName) {
		return findByProperty(NODE_NAME, nodeName);
	}

	public List<SkillTree> findByDescriptionContent(Object descriptionContent) {
		return findByProperty(DESCRIPTION_CONTENT, descriptionContent);
	}

	public List<SkillTree> findByMatchWeightFactor(Object matchWeightFactor) {
		return findByProperty(MATCH_WEIGHT_FACTOR, matchWeightFactor);
	}

	public List<SkillTree> findByDepthLimit(Object depthLimit) {
		return findByProperty(DEPTH_LIMIT, depthLimit);
	}

	public List<SkillTree> findByParentNode(Object parentNode) {
		return findByProperty(PARENT_NODE, parentNode);
	}

	public List<SkillTree> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List<SkillTree> findBySortOrder(Object sortOrder) {
		return findByProperty(SORT_ORDER, sortOrder);
	}

	/**
	 * Find all SkillTree entities.
	 * 
	 * @return List<SkillTree> all SkillTree entities
	 */
	@SuppressWarnings("unchecked")
	public List<SkillTree> findAll() {
		logger.info("finding all SkillTree instances");
		try {
			final String queryString = "select model from SkillTree model";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	public static ISkillTreeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ISkillTreeDAO) ctx.getBean("SkillTreeDAO");
	}
}