package com.newco.marketplace.translator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * ApplicationProperties entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.ApplicationPropertiesMT
 * @author MyEclipse Persistence Tools
 */

public class ApplicationPropertiesDAO extends JpaDaoSupport implements
		IApplicationPropertiesDAO {
	// property constants
	public static final String APP_KEY = "appKey";
	public static final String APP_VALUE = "appValue";
	public static final String APP_DESC = "appDesc";
	public static final String MODIFIED_BY = "modifiedBy";

	/**
	 * Perform an initial save of a previously unsaved ApplicationProperties
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
	 * ApplicationPropertiesDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ApplicationProperties entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ApplicationPropertiesMT entity) {
		logger.info("saving ApplicationProperties instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent ApplicationProperties entity. This operation must be
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
	 * ApplicationPropertiesDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ApplicationProperties entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ApplicationPropertiesMT entity) {
		logger.info("deleting ApplicationProperties instance");
		try {
			entity = getJpaTemplate().getReference(ApplicationPropertiesMT.class,
					entity.getAppKey());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved ApplicationProperties entity and return it or
	 * a copy of it to the sender. A copy of the ApplicationProperties entity
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
	 * entity = ApplicationPropertiesDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ApplicationProperties entity to update
	 * @returns ApplicationProperties the persisted ApplicationProperties entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ApplicationPropertiesMT update(ApplicationPropertiesMT entity) {
		logger.info("updating ApplicationProperties instance");
		try {
			ApplicationPropertiesMT result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public ApplicationPropertiesMT findById(String id) {
		logger.info("finding ApplicationProperties instance with id: " + id);
		try {
			ApplicationPropertiesMT instance = getJpaTemplate().find(
					ApplicationPropertiesMT.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all ApplicationProperties entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ApplicationProperties property to query
	 * @param value
	 *            the property value to match
	 * @return List<ApplicationProperties> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<ApplicationPropertiesMT> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding ApplicationProperties instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from ApplicationPropertiesMT model where model."
					+ propertyName + "= :propertyValue";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("propertyValue", value);
					return query.getResultList();
				}
			});
		} catch (EmptyResultDataAccessException e) {
			logger.warn(String.format("no records found when looking for property '%s' and value '%s'", propertyName, value.toString()));
			throw e;
		} catch (RuntimeException e) {
			logger.error("find by property name failed", e);
			throw e;
		}
	}
	
	public ApplicationPropertiesMT findByAppKey(final String appKey) {
		try {
			final String queryString = "select model from ApplicationPropertiesMT model where appKey = :appKey";
			return (ApplicationPropertiesMT) getJpaTemplate().execute(new JpaCallback() {
				public ApplicationPropertiesMT doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("appKey", appKey);
					return (ApplicationPropertiesMT) query.getSingleResult();
				}
			});
		} catch (EmptyResultDataAccessException e) {
			logger.warn(String.format("no records found when looking for appKey '%s'", appKey));
			throw e;
		}
		catch (RuntimeException e) {
			logger.error(String.format("find by appKey name failed for appKey '%s'", appKey), e);
			throw e;
		}
	}

	public List<ApplicationPropertiesMT> findByAppValue(Object appValue) {
		return findByProperty(APP_VALUE, appValue);
	}

	public List<ApplicationPropertiesMT> findByAppDesc(Object appDesc) {
		return findByProperty(APP_DESC, appDesc);
	}

	public List<ApplicationPropertiesMT> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/**
	 * Find all ApplicationProperties entities.
	 * 
	 * @return List<ApplicationProperties> all ApplicationProperties entities
	 */
	@SuppressWarnings("unchecked")
	public List<ApplicationPropertiesMT> findAll() {
		logger.info("finding all ApplicationProperties instances");
		try {
			final String queryString = "select model from ApplicationPropertiesMT model";
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

	public static IApplicationPropertiesDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IApplicationPropertiesDAO) ctx
				.getBean("ApplicationPropertiesDAO");
	}
}