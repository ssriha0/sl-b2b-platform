package com.newco.marketplace.webservices.dao;

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
 * ShcOrderTransaction entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.ShcOrderTransaction
 * @author MyEclipse Persistence Tools
 */

public class ShcOrderTransactionDAO extends JpaDaoSupport implements
		IShcOrderTransactionDAO {
	// property constants
	public static final String TRANSACTION_TYPE = "transactionType";
	public static final String XML_FRAGMENT = "xmlFragment";
	public static final String INPUT_FILE_NAME = "inputFileName";
	public static final String MODIFIED_BY = "modifiedBy";

	/**
	 * Perform an initial save of a previously unsaved ShcOrderTransaction
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
	 * ShcOrderTransactionDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrderTransaction entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ShcOrderTransaction entity) {
		logger.info("saving ShcOrderTransaction instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent ShcOrderTransaction entity. This operation must be
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
	 * ShcOrderTransactionDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrderTransaction entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ShcOrderTransaction entity) {
		logger.info("deleting ShcOrderTransaction instance");
		try {
			entity = getJpaTemplate().getReference(ShcOrderTransaction.class,
					entity.getShcOrderTransactionId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved ShcOrderTransaction entity and return it or a
	 * copy of it to the sender. A copy of the ShcOrderTransaction entity
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
	 * entity = ShcOrderTransactionDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrderTransaction entity to update
	 * @returns ShcOrderTransaction the persisted ShcOrderTransaction entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ShcOrderTransaction update(ShcOrderTransaction entity) {
		logger.info("updating ShcOrderTransaction instance");
		try {
			ShcOrderTransaction result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public ShcOrderTransaction findById(Integer id) {
		logger.info("finding ShcOrderTransaction instance with id: " + id);
		try {
			ShcOrderTransaction instance = getJpaTemplate().find(
					ShcOrderTransaction.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all ShcOrderTransaction entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ShcOrderTransaction property to query
	 * @param value
	 *            the property value to match
	 * @return List<ShcOrderTransaction> found by query
	 */
	public List<ShcOrderTransaction> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding ShcOrderTransaction instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from ShcOrderTransaction model where model."
					+ propertyName + "= :propertyValue";
			@SuppressWarnings("unchecked")
			List<ShcOrderTransaction> transactions = getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("propertyValue", value);
					return query.getResultList();
				}
			});
			return transactions;
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ShcOrderTransaction> findByTransactionType(
			Object transactionType) {
		return findByProperty(TRANSACTION_TYPE, transactionType);
	}

	public List<ShcOrderTransaction> findByXmlFragment(Object xmlFragment) {
		return findByProperty(XML_FRAGMENT, xmlFragment);
	}

	public List<ShcOrderTransaction> findByInputFileName(Object inputFileName) {
		return findByProperty(INPUT_FILE_NAME, inputFileName);
	}

	public List<ShcOrderTransaction> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/**
	 * Find all ShcOrderTransaction entities.
	 * 
	 * @return List<ShcOrderTransaction> all ShcOrderTransaction entities
	 */
	public List<ShcOrderTransaction> findAll() {
		logger.info("finding all ShcOrderTransaction instances");
		try {
			final String queryString = "select model from ShcOrderTransaction model";
			@SuppressWarnings("unchecked")
			List<ShcOrderTransaction> transactions = getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					return query.getResultList();
				}
			});
			return transactions;
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	/**
	 * Find all ShcOrderTransaction entities for a given orderid.
	 * 
	 * @return List<ShcOrderTransaction> all ShcOrderTransaction entities
	 */
	public List<ShcOrderTransaction> findByShcOrderId(final Integer shcOrderId) {
		logger.info("finding ShcOrderTransaction instance with OrderId:" + shcOrderId);
		try {
			final String queryString = "select model from ShcOrderTransaction model where model.shcOrder.shcOrderId" 
			 						+ "= :shcOrderId";
			@SuppressWarnings("unchecked")
			List<ShcOrderTransaction> transactions = getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("shcOrderId", shcOrderId);					
					return query.getResultList();
				}
			});
			return transactions;
		}
		catch(EmptyResultDataAccessException ex){
			logger.info("No ShcOrder entiry found for OrderId:" + shcOrderId );
			return null;
		
		}
		catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}
	public static IShcOrderTransactionDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IShcOrderTransactionDAO) ctx.getBean("ShcOrderTransactionDAO");
	}
}