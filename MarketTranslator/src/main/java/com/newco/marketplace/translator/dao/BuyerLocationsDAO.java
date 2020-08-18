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
 * BuyerLocations entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.BuyerLocations
 * @author MyEclipse Persistence Tools
 */

public class BuyerLocationsDAO extends JpaDaoSupport implements
		IBuyerLocationsDAO {
	// property constants
	public static final String MODIFIED_BY = "modifiedBy";

	/**
	 * Perform an initial save of a previously unsaved BuyerLocations entity.
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
	 * BuyerLocationsDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerLocations entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerLocations entity) {
		logger.info("saving BuyerLocations instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent BuyerLocations entity. This operation must be
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
	 * BuyerLocationsDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerLocations entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerLocations entity) {
		logger.info("deleting BuyerLocations instance");
		try {
			entity = getJpaTemplate().getReference(BuyerLocations.class,
					entity.getId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved BuyerLocations entity and return it or a copy
	 * of it to the sender. A copy of the BuyerLocations entity parameter is
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
	 * entity = BuyerLocationsDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerLocations entity to update
	 * @returns BuyerLocations the persisted BuyerLocations entity instance, may
	 *          not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerLocations update(BuyerLocations entity) {
		logger.info("updating BuyerLocations instance");
		try {
			BuyerLocations result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public BuyerLocations findById(BuyerLocationsId id) {
		logger.info("finding BuyerLocations instance with id: " + id);
		try {
			BuyerLocations instance = getJpaTemplate().find(
					BuyerLocations.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all BuyerLocations entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerLocations property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerLocations> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerLocations> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding BuyerLocations instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from BuyerLocations model where model."
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

	public List<BuyerLocations> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/**
	 * Find all BuyerLocations entities.
	 * 
	 * @return List<BuyerLocations> all BuyerLocations entities
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerLocations> findAll() {
		logger.info("finding all BuyerLocations instances");
		try {
			final String queryString = "select model from BuyerLocations model";
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

	public static IBuyerLocationsDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IBuyerLocationsDAO) ctx.getBean("BuyerLocationsDAO");
	}
}