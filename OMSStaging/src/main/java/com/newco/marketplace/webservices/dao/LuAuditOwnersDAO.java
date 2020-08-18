package com.newco.marketplace.webservices.dao;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * LuAuditOwners entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.LuAuditOwners
 * @author MyEclipse Persistence Tools
 */

public class LuAuditOwnersDAO extends JpaDaoSupport implements
		ILuAuditOwnersDAO {
	// property constants
	public static final String OWNER_NAME = "ownerName";
	public static final String EMAIL_IDS = "emailIds";

	/**
	 * Perform an initial save of a previously unsaved LuAuditOwners entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * LuAuditOwnersDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuAuditOwners entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LuAuditOwners entity) {
		logger.info("saving LuAuditOwners instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent LuAuditOwners entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * LuAuditOwnersDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuAuditOwners entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LuAuditOwners entity) {
		logger.info("deleting LuAuditOwners instance");
		try {
			entity = getJpaTemplate().getReference(LuAuditOwners.class,
					entity.getAuditOwnerId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved LuAuditOwners entity and return it or a copy
	 * of it to the sender. A copy of the LuAuditOwners entity parameter is
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
	 * entity = LuAuditOwnersDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuAuditOwners entity to update
	 * @return LuAuditOwners the persisted LuAuditOwners entity instance, may
	 *         not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LuAuditOwners update(LuAuditOwners entity) {
		logger.info("updating LuAuditOwners instance");
		try {
			LuAuditOwners result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public LuAuditOwners findById(Integer id) {
		logger.info("finding LuAuditOwners instance with id: " + id);
		try {
			LuAuditOwners instance = getJpaTemplate().find(LuAuditOwners.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all LuAuditOwners entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LuAuditOwners property to query
	 * @param value
	 *            the property value to match
	 * @return List<LuAuditOwners> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<LuAuditOwners> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding LuAuditOwners instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from LuAuditOwners model where model."
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

	public List<LuAuditOwners> findByOwnerName(Object ownerName) {
		return findByProperty(OWNER_NAME, ownerName);
	}

	public List<LuAuditOwners> findByEmailIds(Object emailIds) {
		return findByProperty(EMAIL_IDS, emailIds);
	}

	/**
	 * Find all LuAuditOwners entities.
	 * 
	 * @return List<LuAuditOwners> all LuAuditOwners entities
	 */
	@SuppressWarnings("unchecked")
	public List<LuAuditOwners> findAll() {
		logger.info("finding all LuAuditOwners instances");
		try {
			final String queryString = "select model from LuAuditOwners model";
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

	public static ILuAuditOwnersDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ILuAuditOwnersDAO) ctx.getBean("LuAuditOwnersDAO");
	}
}