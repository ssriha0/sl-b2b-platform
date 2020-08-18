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
 * UserProfileRole entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.UserProfileRole
 * @author MyEclipse Persistence Tools
 */

public class UserProfileRoleDAO extends JpaDaoSupport implements
		IUserProfileRoleDAO {
	// property constants

	/**
	 * Perform an initial save of a previously unsaved UserProfileRole entity.
	 * All subsequent persist actions of this entity should use the #update()
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
	 * UserProfileRoleDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfileRole entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(UserProfileRole entity) {
		logger.info("saving UserProfileRole instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent UserProfileRole entity. This operation must be
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
	 * UserProfileRoleDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfileRole entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(UserProfileRole entity) {
		logger.info("deleting UserProfileRole instance");
		try {
			entity = getJpaTemplate().getReference(UserProfileRole.class,
					entity.getId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved UserProfileRole entity and return it or a copy
	 * of it to the sender. A copy of the UserProfileRole entity parameter is
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
	 * entity = UserProfileRoleDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfileRole entity to update
	 * @returns UserProfileRole the persisted UserProfileRole entity instance,
	 *          may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public UserProfileRole update(UserProfileRole entity) {
		logger.info("updating UserProfileRole instance");
		try {
			UserProfileRole result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public UserProfileRole findById(UserProfileRoleId id) {
		logger.info("finding UserProfileRole instance with id: " + id);
		try {
			UserProfileRole instance = getJpaTemplate().find(
					UserProfileRole.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all UserProfileRole entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the UserProfileRole property to query
	 * @param value
	 *            the property value to match
	 * @return List<UserProfileRole> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<UserProfileRole> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding UserProfileRole instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from UserProfileRole model where model."
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

	/**
	 * Find all UserProfileRole entities.
	 * 
	 * @return List<UserProfileRole> all UserProfileRole entities
	 */
	@SuppressWarnings("unchecked")
	public List<UserProfileRole> findAll() {
		logger.info("finding all UserProfileRole instances");
		try {
			final String queryString = "select model from UserProfileRole model";
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

	public static IUserProfileRoleDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IUserProfileRoleDAO) ctx.getBean("UserProfileRoleDAO");
	}
}