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
 * BuyerReferenceType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.BuyerReferenceTypeMT
 * @author MyEclipse Persistence Tools
 */

public class BuyerReferenceTypeDAO extends JpaDaoSupport implements
		IBuyerReferenceTypeDAO {
	// property constants
	public static final String REF_TYPE = "refType";
	public static final String REF_DESCR = "refDescr";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String SORT_ORDER = "sortOrder";
	public static final String ACTIVE_IND = "activeInd";

	/**
	 * Perform an initial save of a previously unsaved BuyerReferenceType
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
	 * BuyerReferenceTypeDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerReferenceType entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerReferenceTypeMT entity) {
		logger.info("saving BuyerReferenceType instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent BuyerReferenceType entity. This operation must be
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
	 * BuyerReferenceTypeDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerReferenceType entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerReferenceTypeMT entity) {
		logger.info("deleting BuyerReferenceType instance");
		try {
			entity = getJpaTemplate().getReference(BuyerReferenceTypeMT.class,
					entity.getBuyerRefTypeId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved BuyerReferenceType entity and return it or a
	 * copy of it to the sender. A copy of the BuyerReferenceType entity
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
	 * entity = BuyerReferenceTypeDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerReferenceType entity to update
	 * @returns BuyerReferenceType the persisted BuyerReferenceType entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerReferenceTypeMT update(BuyerReferenceTypeMT entity) {
		logger.info("updating BuyerReferenceType instance");
		try {
			BuyerReferenceTypeMT result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public BuyerReferenceTypeMT findById(Integer id) {
		logger.info("finding BuyerReferenceType instance with id: " + id);
		try {
			BuyerReferenceTypeMT instance = getJpaTemplate().find(
					BuyerReferenceTypeMT.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all BuyerReferenceType entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerReferenceType property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerReferenceType> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerReferenceTypeMT> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding BuyerReferenceType instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from BuyerReferenceTypeMT model where model."
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

	public List<BuyerReferenceTypeMT> findByRefType(Object refType) {
		return findByProperty(REF_TYPE, refType);
	}

	public List<BuyerReferenceTypeMT> findByRefDescr(Object refDescr) {
		return findByProperty(REF_DESCR, refDescr);
	}

	public List<BuyerReferenceTypeMT> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List<BuyerReferenceTypeMT> findBySortOrder(Object sortOrder) {
		return findByProperty(SORT_ORDER, sortOrder);
	}

	public List<BuyerReferenceTypeMT> findByActiveInd(Object activeInd) {
		return findByProperty(ACTIVE_IND, activeInd);
	}

	/**
	 * Find all BuyerReferenceType entities.
	 * 
	 * @return List<BuyerReferenceType> all BuyerReferenceType entities
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerReferenceTypeMT> findAll() {
		logger.info("finding all BuyerReferenceType instances");
		try {
			final String queryString = "select model from BuyerReferenceTypeMT model";
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

	public static IBuyerReferenceTypeDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IBuyerReferenceTypeDAO) ctx.getBean("BuyerReferenceTypeDAO");
	}
}