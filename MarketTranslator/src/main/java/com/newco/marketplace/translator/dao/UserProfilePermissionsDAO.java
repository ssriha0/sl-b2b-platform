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
 * UserProfilePermissions entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.UserProfilePermissions
 * @author MyEclipse Persistence Tools
 */

public class UserProfilePermissionsDAO extends JpaDaoSupport implements
		IUserProfilePermissionsDAO {
	// property constants
	public static final String MODIFIED_BY = "modifiedBy";

	/**
	 * Perform an initial save of a previously unsaved UserProfilePermissions
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
	 * UserProfilePermissionsDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfilePermissions entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(UserProfilePermissions entity) {
		logger.info("saving UserProfilePermissions instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent UserProfilePermissions entity. This operation must be
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
	 * UserProfilePermissionsDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfilePermissions entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(UserProfilePermissions entity) {
		logger.info("deleting UserProfilePermissions instance");
		try {
			entity = getJpaTemplate().getReference(
					UserProfilePermissions.class, entity.getId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved UserProfilePermissions entity and return it or
	 * a copy of it to the sender. A copy of the UserProfilePermissions entity
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
	 * entity = UserProfilePermissionsDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfilePermissions entity to update
	 * @returns UserProfilePermissions the persisted UserProfilePermissions
	 *          entity instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public UserProfilePermissions update(UserProfilePermissions entity) {
		logger.info("updating UserProfilePermissions instance");
		try {
			UserProfilePermissions result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public UserProfilePermissions findById(UserProfilePermissionsId id) {
		logger.info("finding UserProfilePermissions instance with id: " + id);
		try {
			UserProfilePermissions instance = getJpaTemplate().find(
					UserProfilePermissions.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all UserProfilePermissions entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the UserProfilePermissions property to query
	 * @param value
	 *            the property value to match
	 * @return List<UserProfilePermissions> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<UserProfilePermissions> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding UserProfilePermissions instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from UserProfilePermissions model where model."
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

	public List<UserProfilePermissions> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/**
	 * Find all UserProfilePermissions entities.
	 * 
	 * @return List<UserProfilePermissions> all UserProfilePermissions entities
	 */
	@SuppressWarnings("unchecked")
	public List<UserProfilePermissions> findAll() {
		logger.info("finding all UserProfilePermissions instances");
		try {
			final String queryString = "select model from UserProfilePermissions model";
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

	public static IUserProfilePermissionsDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IUserProfilePermissionsDAO) ctx
				.getBean("UserProfilePermissionsDAO");
	}
}