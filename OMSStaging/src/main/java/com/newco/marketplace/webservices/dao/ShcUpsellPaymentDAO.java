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
 * ShcUpsellPayment entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.ShcUpsellPayment
 * @author MyEclipse Persistence Tools
 */

public class ShcUpsellPaymentDAO extends JpaDaoSupport implements
		IShcUpsellPaymentDAO {
	// property constants
	public static final String PAYMENT_METHOD_IND = "paymentMethodInd";
	public static final String PAYMENT_ACC_NO = "paymentAccNo";
	public static final String AUTH_NO = "authNo";
	public static final String SEC_PAYMENT_METHOD_IND = "secPaymentMethodInd";
	public static final String SEC_PAYMENT_ACC_NO = "secPaymentAccNo";
	public static final String SEC_AUTH_NO = "secAuthNo";
	public static final String AMOUNT_COLLECTED = "amountCollected";
	public static final String PRI_AMOUNT_COLLECTED = "priAmountCollected";
	public static final String SEC_AMOUNT_COLLECTED = "secAmountCollected";
	public static final String MODIFIED_BY = "modifiedBy";

	/**
	 * Perform an initial save of a previously unsaved ShcUpsellPayment entity.
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
	 * ShcUpsellPaymentDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcUpsellPayment entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ShcUpsellPayment entity) {
		logger.info("saving ShcUpsellPayment instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent ShcUpsellPayment entity. This operation must be
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
	 * ShcUpsellPaymentDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcUpsellPayment entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ShcUpsellPayment entity) {
		logger.info("deleting ShcUpsellPayment instance");
		try {
			entity = getJpaTemplate().getReference(ShcUpsellPayment.class,
					entity.getShcUpsellPaymentId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved ShcUpsellPayment entity and return it or a
	 * copy of it to the sender. A copy of the ShcUpsellPayment entity parameter
	 * is returned when the JPA persistence mechanism has not previously been
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
	 * entity = ShcUpsellPaymentDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcUpsellPayment entity to update
	 * @returns ShcUpsellPayment the persisted ShcUpsellPayment entity instance,
	 *          may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ShcUpsellPayment update(ShcUpsellPayment entity) {
		logger.info("updating ShcUpsellPayment instance");
		try {
			ShcUpsellPayment result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public ShcUpsellPayment findById(Integer id) {
		logger.info("finding ShcUpsellPayment instance with id: " + id);
		try {
			ShcUpsellPayment instance = getJpaTemplate().find(
					ShcUpsellPayment.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all ShcUpsellPayment entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ShcUpsellPayment property to query
	 * @param value
	 *            the property value to match
	 * @return List<ShcUpsellPayment> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<ShcUpsellPayment> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding ShcUpsellPayment instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from ShcUpsellPayment model where model."
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

	public List<ShcUpsellPayment> findByPaymentMethodInd(Object paymentMethodInd) {
		return findByProperty(PAYMENT_METHOD_IND, paymentMethodInd);
	}

	public List<ShcUpsellPayment> findByPaymentAccNo(Object paymentAccNo) {
		return findByProperty(PAYMENT_ACC_NO, paymentAccNo);
	}

	public List<ShcUpsellPayment> findByAuthNo(Object authNo) {
		return findByProperty(AUTH_NO, authNo);
	}

	public List<ShcUpsellPayment> findBySecPaymentMethodInd(
			Object secPaymentMethodInd) {
		return findByProperty(SEC_PAYMENT_METHOD_IND, secPaymentMethodInd);
	}

	public List<ShcUpsellPayment> findBySecPaymentAccNo(Object secPaymentAccNo) {
		return findByProperty(SEC_PAYMENT_ACC_NO, secPaymentAccNo);
	}

	public List<ShcUpsellPayment> findBySecAuthNo(Object secAuthNo) {
		return findByProperty(SEC_AUTH_NO, secAuthNo);
	}

	public List<ShcUpsellPayment> findByAmountCollected(Object amountCollected) {
		return findByProperty(AMOUNT_COLLECTED, amountCollected);
	}

	public List<ShcUpsellPayment> findByPriAmountCollected(
			Object priAmountCollected) {
		return findByProperty(PRI_AMOUNT_COLLECTED, priAmountCollected);
	}

	public List<ShcUpsellPayment> findBySecAmountCollected(
			Object secAmountCollected) {
		return findByProperty(SEC_AMOUNT_COLLECTED, secAmountCollected);
	}

	public List<ShcUpsellPayment> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/**
	 * Find all ShcUpsellPayment entities.
	 * 
	 * @return List<ShcUpsellPayment> all ShcUpsellPayment entities
	 */
	@SuppressWarnings("unchecked")
	public List<ShcUpsellPayment> findAll() {
		logger.info("finding all ShcUpsellPayment instances");
		try {
			final String queryString = "select model from ShcUpsellPayment model";
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

	public static IShcUpsellPaymentDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IShcUpsellPaymentDAO) ctx.getBean("ShcUpsellPaymentDAO");
	}
}