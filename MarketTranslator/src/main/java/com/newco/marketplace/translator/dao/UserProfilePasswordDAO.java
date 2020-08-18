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
 * UserProfilePassword entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.UserProfilePassword
 * @author MyEclipse Persistence Tools
 */

public class UserProfilePasswordDAO extends JpaDaoSupport implements
		IUserProfilePasswordDAO {
	// property constants
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String MODIFIED_BY = "modifiedBy";

	/**
	 * Perform an initial save of a previously unsaved UserProfilePassword
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
	 * UserProfilePasswordDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfilePassword entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(UserProfilePassword entity) {
		logger.info("saving UserProfilePassword instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent UserProfilePassword entity. This operation must be
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
	 * UserProfilePasswordDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfilePassword entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(UserProfilePassword entity) {
		logger.info("deleting UserProfilePassword instance");
		try {
			entity = getJpaTemplate().getReference(UserProfilePassword.class,
					entity.getUserPasswordId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved UserProfilePassword entity and return it or a
	 * copy of it to the sender. A copy of the UserProfilePassword entity
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
	 * entity = UserProfilePasswordDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfilePassword entity to update
	 * @returns UserProfilePassword the persisted UserProfilePassword entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public UserProfilePassword update(UserProfilePassword entity) {
		logger.info("updating UserProfilePassword instance");
		try {
			UserProfilePassword result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public UserProfilePassword findById(Integer id) {
		logger.info("finding UserProfilePassword instance with id: " + id);
		try {
			UserProfilePassword instance = getJpaTemplate().find(
					UserProfilePassword.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all UserProfilePassword entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the UserProfilePassword property to query
	 * @param value
	 *            the property value to match
	 * @return List<UserProfilePassword> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<UserProfilePassword> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding UserProfilePassword instance using property");
		try {
			final String queryString = "select model from UserProfilePassword model where model."
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

	public List<UserProfilePassword> findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List<UserProfilePassword> findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List<UserProfilePassword> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/**
	 * Find all UserProfilePassword entities.
	 * 
	 * @return List<UserProfilePassword> all UserProfilePassword entities
	 */
	@SuppressWarnings("unchecked")
	public List<UserProfilePassword> findAll() {
		logger.info("finding all UserProfilePassword instances");
		try {
			final String queryString = "select model from UserProfilePassword model";
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

	public static IUserProfilePasswordDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IUserProfilePasswordDAO) ctx.getBean("UserProfilePasswordDAO");
	}
}