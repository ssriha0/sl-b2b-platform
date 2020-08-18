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
 * UserRoles entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.UserRoles
 * @author MyEclipse Persistence Tools
 */

public class UserRolesDAO extends JpaDaoSupport implements IUserRolesDAO {
	// property constants
	public static final String ROLE_NAME = "roleName";
	public static final String ROLE_DESCR = "roleDescr";
	public static final String INTERNAL_ROLE = "internalRole";

	/**
	 * Perform an initial save of a previously unsaved UserRoles entity. All
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
	 * UserRolesDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserRoles entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(UserRoles entity) {
		logger.info("saving UserRoles instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent UserRoles entity. This operation must be performed
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
	 * UserRolesDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserRoles entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(UserRoles entity) {
		logger.info("deleting UserRoles instance");
		try {
			entity = getJpaTemplate().getReference(UserRoles.class,
					entity.getRoleId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved UserRoles entity and return it or a copy of it
	 * to the sender. A copy of the UserRoles entity parameter is returned when
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
	 * entity = UserRolesDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserRoles entity to update
	 * @returns UserRoles the persisted UserRoles entity instance, may not be
	 *          the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public UserRoles update(UserRoles entity) {
		logger.info("updating UserRoles instance");
		try {
			UserRoles result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public UserRoles findById(Integer id) {
		logger.info("finding UserRoles instance with id: " + id);
		try {
			UserRoles instance = getJpaTemplate().find(UserRoles.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all UserRoles entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the UserRoles property to query
	 * @param value
	 *            the property value to match
	 * @return List<UserRoles> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<UserRoles> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding UserRoles instance with property: " + propertyName
				+ ", value: " + value);
		try {
			final String queryString = "select model from UserRoles model where model."
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

	public List<UserRoles> findByRoleName(Object roleName) {
		return findByProperty(ROLE_NAME, roleName);
	}

	public List<UserRoles> findByRoleDescr(Object roleDescr) {
		return findByProperty(ROLE_DESCR, roleDescr);
	}

	public List<UserRoles> findByInternalRole(Object internalRole) {
		return findByProperty(INTERNAL_ROLE, internalRole);
	}

	/**
	 * Find all UserRoles entities.
	 * 
	 * @return List<UserRoles> all UserRoles entities
	 */
	@SuppressWarnings("unchecked")
	public List<UserRoles> findAll() {
		logger.info("finding all UserRoles instances");
		try {
			final String queryString = "select model from UserRoles model";
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

	public static IUserRolesDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IUserRolesDAO) ctx.getBean("UserRolesDAO");
	}
}