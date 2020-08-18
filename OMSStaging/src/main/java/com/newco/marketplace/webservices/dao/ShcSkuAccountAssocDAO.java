package com.newco.marketplace.webservices.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * ShcSkuAccountAssoc entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.ShcSkuAccountAssoc
 * @author MyEclipse Persistence Tools
 */

public class ShcSkuAccountAssocDAO extends JpaDaoSupport implements
		IShcSkuAccountAssocDAO {
	// property constants
	public static final String GL_ACCOUNT = "glAccount";
	public static final String GL_DIVISION = "glDivision";
	public static final String GL_CATEGORY = "glCategory";
	public static final String MODIFIED_BY = "modifiedBy";

	/**
	 * Perform an initial save of a previously unsaved ShcSkuAccountAssoc
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
	 * ShcSkuAccountAssocDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcSkuAccountAssoc entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ShcSkuAccountAssoc entity) {
		logger.info("saving ShcSkuAccountAssoc instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent ShcSkuAccountAssoc entity. This operation must be
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
	 * ShcSkuAccountAssocDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcSkuAccountAssoc entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ShcSkuAccountAssoc entity) {
		logger.info("deleting ShcSkuAccountAssoc instance");
		try {
			entity = getJpaTemplate().getReference(ShcSkuAccountAssoc.class,
					entity.getSku());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved ShcSkuAccountAssoc entity and return it or a
	 * copy of it to the sender. A copy of the ShcSkuAccountAssoc entity
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
	 * entity = ShcSkuAccountAssocDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcSkuAccountAssoc entity to update
	 * @returns ShcSkuAccountAssoc the persisted ShcSkuAccountAssoc entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ShcSkuAccountAssoc update(ShcSkuAccountAssoc entity) {
		logger.info("updating ShcSkuAccountAssoc instance");
		try {
			ShcSkuAccountAssoc result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public ShcSkuAccountAssoc findById(String id) {
		logger.info("finding ShcSkuAccountAssoc instance with id: " + id);
		try {
			ShcSkuAccountAssoc instance = getJpaTemplate().find(
					ShcSkuAccountAssoc.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all ShcSkuAccountAssoc entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ShcSkuAccountAssoc property to query
	 * @param value
	 *            the property value to match
	 * @return List<ShcSkuAccountAssoc> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<ShcSkuAccountAssoc> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding ShcSkuAccountAssoc instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from ShcSkuAccountAssoc model where model."
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

	public List<ShcSkuAccountAssoc> findByGlAccount(Object glAccount) {
		return findByProperty(GL_ACCOUNT, glAccount);
	}

	public List<ShcSkuAccountAssoc> findByGlDivision(Object glDivision) {
		return findByProperty(GL_DIVISION, glDivision);
	}

	public List<ShcSkuAccountAssoc> findByGlCategory(Object glCategory) {
		return findByProperty(GL_CATEGORY, glCategory);
	}

	public List<ShcSkuAccountAssoc> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/**
	 * Find all ShcSkuAccountAssoc entities.
	 * 
	 * @return List<ShcSkuAccountAssoc> all ShcSkuAccountAssoc entities
	 */
	@SuppressWarnings("unchecked")
	public List<ShcSkuAccountAssoc> findAll() {
		logger.info("finding all ShcSkuAccountAssoc instances");
		try {
			final String queryString = "select model from ShcSkuAccountAssoc model";
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

	public static IShcSkuAccountAssocDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IShcSkuAccountAssocDAO) ctx.getBean("ShcSkuAccountAssocDAO");
	}
}