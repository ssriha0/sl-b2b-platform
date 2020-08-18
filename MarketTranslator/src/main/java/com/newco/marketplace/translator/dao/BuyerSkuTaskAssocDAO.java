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
 * BuyerSkuTaskAssoc entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.BuyerSkuTaskAssocMT
 * @author MyEclipse Persistence Tools
 */

public class BuyerSkuTaskAssocDAO extends JpaDaoSupport implements
		IBuyerSkuTaskAssocDAO {
	// property constants
	public static final String SKU = "sku";
	public static final String NODE_ID = "nodeId";
	public static final String SERVICE_TYPE_TEMPLATE_ID = "serviceTypeTemplateId";

	/**
	 * Perform an initial save of a previously unsaved BuyerSkuTaskAssoc entity.
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
	 * BuyerSkuTaskAssocDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuTaskAssoc entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerSkuTaskAssocMT entity) {
		logger.info("saving BuyerSkuTaskAssoc instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent BuyerSkuTaskAssoc entity. This operation must be
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
	 * BuyerSkuTaskAssocDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuTaskAssoc entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerSkuTaskAssocMT entity) {
		logger.info("deleting BuyerSkuTaskAssoc instance");
		try {
			entity = getJpaTemplate().getReference(BuyerSkuTaskAssocMT.class,
					entity.getSkuTaskId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved BuyerSkuTaskAssoc entity and return it or a
	 * copy of it to the sender. A copy of the BuyerSkuTaskAssoc entity
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
	 * entity = BuyerSkuTaskAssocDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuTaskAssoc entity to update
	 * @returns BuyerSkuTaskAssoc the persisted BuyerSkuTaskAssoc entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerSkuTaskAssocMT update(BuyerSkuTaskAssocMT entity) {
		logger.info("updating BuyerSkuTaskAssoc instance");
		try {
			BuyerSkuTaskAssocMT result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public BuyerSkuTaskAssocMT findById(Integer id) {
		logger.info("finding BuyerSkuTaskAssoc instance with id: " + id);
		try {
			BuyerSkuTaskAssocMT instance = getJpaTemplate().find(
					BuyerSkuTaskAssocMT.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all BuyerSkuTaskAssoc entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerSkuTaskAssoc property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerSkuTaskAssoc> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSkuTaskAssocMT> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding BuyerSkuTaskAssoc instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from BuyerSkuTaskAssocMT model where model."
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

	public List<BuyerSkuTaskAssocMT> findBySku(Object sku) {
		return findByProperty(SKU, sku);
	}

	public List<BuyerSkuTaskAssocMT> findByNodeId(Object nodeId) {
		return findByProperty(NODE_ID, nodeId);
	}

	public List<BuyerSkuTaskAssocMT> findByServiceTypeTemplateId(
			Object serviceTypeTemplateId) {
		return findByProperty(SERVICE_TYPE_TEMPLATE_ID, serviceTypeTemplateId);
	}

	/**
	 * Find all BuyerSkuTaskAssoc entities.
	 * 
	 * @return List<BuyerSkuTaskAssoc> all BuyerSkuTaskAssoc entities
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSkuTaskAssocMT> findAll() {
		logger.info("finding all BuyerSkuTaskAssoc instances");
		try {
			final String queryString = "select model from BuyerSkuTaskAssocMT model";
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

	public static IBuyerSkuTaskAssocDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IBuyerSkuTaskAssocDAO) ctx.getBean("BuyerSkuTaskAssocDAO");
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.dao.IBuyerSkuTaskAssocDAO#findBySkuAndSpecialtyCode(java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSkuTaskAssocMT> findBySkuAndSpecialtyCode(final Object sku,
			final Object specialtyCode) {
		logger.info("finding all BuyerSkuTaskAssoc instances for specific sku and specialty code");
		try {
			final String queryString = "select model from BuyerSkuTaskAssocMT model where model.sku"
					+ "= :sku and model.specialtyCode = :specialtyCode";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("sku", sku);
					query.setParameter("specialtyCode", specialtyCode);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("findBySkuAndSpecialtyCode failed", re);
			throw re;
		}
	}
}