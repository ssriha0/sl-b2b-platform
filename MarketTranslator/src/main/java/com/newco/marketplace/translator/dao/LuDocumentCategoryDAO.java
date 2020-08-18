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
 * LuDocumentCategory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.LuDocumentCategory
 * @author MyEclipse Persistence Tools
 */

public class LuDocumentCategoryDAO extends JpaDaoSupport implements
		ILuDocumentCategoryDAO {
	// property constants
	public static final String TYPE = "type";
	public static final String DESCR = "descr";
	public static final String SORT_ORDER = "sortOrder";

	/**
	 * Perform an initial save of a previously unsaved LuDocumentCategory
	 * entity. All subsequent persist actions of this entity should use the
	 * #update() method. This operation must be performed within the a database
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
	 * LuDocumentCategoryDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuDocumentCategory entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LuDocumentCategory entity) {
		logger.info("saving LuDocumentCategory instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent LuDocumentCategory entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object) EntityManager#delete}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * LuDocumentCategoryDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuDocumentCategory entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LuDocumentCategory entity) {
		logger.info("deleting LuDocumentCategory instance");
		try {
			entity = getJpaTemplate().getReference(LuDocumentCategory.class,
					entity.getDocCategoryId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved LuDocumentCategory entity and return it or a
	 * copy of it to the sender. A copy of the LuDocumentCategory entity
	 * parameter is returned when the JPA persistence mechanism has not
	 * previously been tracking the updated entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently saved to the persistence store, i.e., database. This
	 * method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = LuDocumentCategoryDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuDocumentCategory entity to update
	 * @returns LuDocumentCategory the persisted LuDocumentCategory entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LuDocumentCategory update(LuDocumentCategory entity) {
		logger.info("updating LuDocumentCategory instance");
		try {
			LuDocumentCategory result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public LuDocumentCategory findById(Integer id) {
		logger.info("finding LuDocumentCategory instance with id: " + id);
		try {
			LuDocumentCategory instance = getJpaTemplate().find(
					LuDocumentCategory.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all LuDocumentCategory entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LuDocumentCategory property to query
	 * @param value
	 *            the property value to match
	 * @return List<LuDocumentCategory> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<LuDocumentCategory> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding LuDocumentCategory instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from LuDocumentCategory model where model."
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

	public List<LuDocumentCategory> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<LuDocumentCategory> findByDescr(Object descr) {
		return findByProperty(DESCR, descr);
	}

	public List<LuDocumentCategory> findBySortOrder(Object sortOrder) {
		return findByProperty(SORT_ORDER, sortOrder);
	}

	/**
	 * Find all LuDocumentCategory entities.
	 * 
	 * @return List<LuDocumentCategory> all LuDocumentCategory entities
	 */
	@SuppressWarnings("unchecked")
	public List<LuDocumentCategory> findAll() {
		logger.info("finding all LuDocumentCategory instances");
		try {
			final String queryString = "select model from LuDocumentCategory model";
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

	public static ILuDocumentCategoryDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ILuDocumentCategoryDAO) ctx.getBean("LuDocumentCategoryDAO");
	}
}