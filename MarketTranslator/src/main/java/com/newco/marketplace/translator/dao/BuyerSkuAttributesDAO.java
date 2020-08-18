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
 * BuyerSkuAttributes entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.BuyerSkuAttributes
 * @author MyEclipse Persistence Tools
 */

public class BuyerSkuAttributesDAO extends JpaDaoSupport implements
		IBuyerSkuAttributesDAO {
	// property constants

	/**
	 * Perform an initial save of a previously unsaved BuyerSkuAttributes
	 * entity. All subsequent persist actions of this entity should use the
	 * #update() method. This operation must be performed within the a database
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
	 * BuyerSkuAttributesDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuAttributes entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerSkuAttributes entity) {
		logger.info("saving BuyerSkuAttributes instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent BuyerSkuAttributes entity. This operation must be
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
	 * BuyerSkuAttributesDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuAttributes entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerSkuAttributes entity) {
		logger.info("deleting BuyerSkuAttributes instance");
		try {
			entity = getJpaTemplate().getReference(BuyerSkuAttributes.class,
					entity.getId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved BuyerSkuAttributes entity and return it or a
	 * copy of it to the sender. A copy of the BuyerSkuAttributes entity
	 * parameter is returned when the JPA persistence mechanism has not
	 * previously been tracking the updated entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently saved to the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#merge(Object)
	 * EntityManager#merge} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = BuyerSkuAttributesDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuAttributes entity to update
	 * @return BuyerSkuAttributes the persisted BuyerSkuAttributes entity
	 *         instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerSkuAttributes update(BuyerSkuAttributes entity) {
		logger.info("updating BuyerSkuAttributes instance");
		try {
			BuyerSkuAttributes result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public BuyerSkuAttributes findById(BuyerSkuAttributesId id) {
		logger.info("finding BuyerSkuAttributes instance with id: " + id);
		try {
			BuyerSkuAttributes instance = getJpaTemplate().find(
					BuyerSkuAttributes.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all BuyerSkuAttributes entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerSkuAttributes property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerSkuAttributes> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSkuAttributes> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding BuyerSkuAttributes instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from BuyerSkuAttributes model where model."
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
	 * Find all BuyerSkuAttributes entities.
	 * 
	 * @return List<BuyerSkuAttributes> all BuyerSkuAttributes entities
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSkuAttributes> findAll() {
		logger.info("finding all BuyerSkuAttributes instances");
		try {
			final String queryString = "select model from BuyerSkuAttributes model";
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

	public static IBuyerSkuAttributesDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IBuyerSkuAttributesDAO) ctx.getBean("BuyerSkuAttributesDAO");
	}
}