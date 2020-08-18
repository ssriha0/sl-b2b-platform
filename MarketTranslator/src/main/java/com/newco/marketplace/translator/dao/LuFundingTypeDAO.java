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
 * LuFundingType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.LuFundingType
 * @author MyEclipse Persistence Tools
 */

public class LuFundingTypeDAO extends JpaDaoSupport implements
		ILuFundingTypeDAO {
	// property constants
	public static final String TYPE = "type";
	public static final String DESCR = "descr";
	public static final String SORT_ORDER = "sortOrder";

	/**
	 * Perform an initial save of a previously unsaved LuFundingType entity. All
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
	 * LuFundingTypeDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuFundingType entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LuFundingType entity) {
		logger.info("saving LuFundingType instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent LuFundingType entity. This operation must be
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
	 * LuFundingTypeDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuFundingType entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LuFundingType entity) {
		logger.info("deleting LuFundingType instance");
		try {
			entity = getJpaTemplate().getReference(LuFundingType.class,
					entity.getFundingTypeId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved LuFundingType entity and return it or a copy
	 * of it to the sender. A copy of the LuFundingType entity parameter is
	 * returned when the JPA persistence mechanism has not previously been
	 * tracking the updated entity. This operation must be performed within the
	 * a database transaction context for the entity's data to be permanently
	 * saved to the persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = LuFundingTypeDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuFundingType entity to update
	 * @returns LuFundingType the persisted LuFundingType entity instance, may
	 *          not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LuFundingType update(LuFundingType entity) {
		logger.info("updating LuFundingType instance");
		try {
			LuFundingType result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public LuFundingType findById(Integer id) {
		logger.info("finding LuFundingType instance with id: " + id);
		try {
			LuFundingType instance = getJpaTemplate().find(LuFundingType.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all LuFundingType entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LuFundingType property to query
	 * @param value
	 *            the property value to match
	 * @return List<LuFundingType> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<LuFundingType> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding LuFundingType instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from LuFundingType model where model."
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

	public List<LuFundingType> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<LuFundingType> findByDescr(Object descr) {
		return findByProperty(DESCR, descr);
	}

	public List<LuFundingType> findBySortOrder(Object sortOrder) {
		return findByProperty(SORT_ORDER, sortOrder);
	}

	/**
	 * Find all LuFundingType entities.
	 * 
	 * @return List<LuFundingType> all LuFundingType entities
	 */
	@SuppressWarnings("unchecked")
	public List<LuFundingType> findAll() {
		logger.info("finding all LuFundingType instances");
		try {
			final String queryString = "select model from LuFundingType model";
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

	public static ILuFundingTypeDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ILuFundingTypeDAO) ctx.getBean("LuFundingTypeDAO");
	}
}