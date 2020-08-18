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
 * ShcMerchandise entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.ShcMerchandise
 * @author MyEclipse Persistence Tools
 */

public class ShcMerchandiseDAO extends JpaDaoSupport implements
		IShcMerchandiseDAO {
	// property constants
	public static final String ITEM_NO = "itemNo";
	public static final String CODE = "code";
	public static final String DESCRIPTION = "description";
	public static final String SPECIALTY = "specialty";
	public static final String SERIAL_NUMBER = "serialNumber";
	public static final String DIVISION_CODE = "divisionCode";
	public static final String BRAND = "brand";
	public static final String MODEL = "model";
	public static final String MODIFIED_BY = "modifiedBy";

	/**
	 * Perform an initial save of a previously unsaved ShcMerchandise entity.
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
	 * ShcMerchandiseDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcMerchandise entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ShcMerchandise entity) {
		logger.info("saving ShcMerchandise instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent ShcMerchandise entity. This operation must be
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
	 * ShcMerchandiseDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcMerchandise entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ShcMerchandise entity) {
		logger.info("deleting ShcMerchandise instance");
		try {
			entity = getJpaTemplate().getReference(ShcMerchandise.class,
					entity.getShcMerchandiseId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved ShcMerchandise entity and return it or a copy
	 * of it to the sender. A copy of the ShcMerchandise entity parameter is
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
	 * entity = ShcMerchandiseDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcMerchandise entity to update
	 * @returns ShcMerchandise the persisted ShcMerchandise entity instance, may
	 *          not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ShcMerchandise update(ShcMerchandise entity) {
		logger.info("updating ShcMerchandise instance");
		try {
			ShcMerchandise result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public ShcMerchandise findById(Integer id) {
		logger.info("finding ShcMerchandise instance with id: " + id);
		try {
			ShcMerchandise instance = getJpaTemplate().find(
					ShcMerchandise.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all ShcMerchandise entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ShcMerchandise property to query
	 * @param value
	 *            the property value to match
	 * @return List<ShcMerchandise> found by query
	 */
	public List<ShcMerchandise> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding ShcMerchandise instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from ShcMerchandise model where model."
					+ propertyName + "= :propertyValue";
			@SuppressWarnings("unchecked")
			List<ShcMerchandise> merchandises = getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("propertyValue", value);
					return query.getResultList();
				}
			});
			return merchandises;
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ShcMerchandise> findByItemNo(Object itemNo) {
		return findByProperty(ITEM_NO, itemNo);
	}

	public List<ShcMerchandise> findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List<ShcMerchandise> findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List<ShcMerchandise> findBySpecialty(Object specialty) {
		return findByProperty(SPECIALTY, specialty);
	}

	public List<ShcMerchandise> findBySerialNumber(Object serialNumber) {
		return findByProperty(SERIAL_NUMBER, serialNumber);
	}

	public List<ShcMerchandise> findByDivisionCode(Object divisionCode) {
		return findByProperty(DIVISION_CODE, divisionCode);
	}

	public List<ShcMerchandise> findByBrand(Object brand) {
		return findByProperty(BRAND, brand);
	}

	public List<ShcMerchandise> findByModel(Object model) {
		return findByProperty(MODEL, model);
	}

	public List<ShcMerchandise> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/**
	 * Find all ShcMerchandise entities.
	 * 
	 * @return List<ShcMerchandise> all ShcMerchandise entities
	 */
	public List<ShcMerchandise> findAll() {
		logger.info("finding all ShcMerchandise instances");
		try {
			final String queryString = "select model from ShcMerchandise model";
			@SuppressWarnings("unchecked")
			List<ShcMerchandise> merchandises = getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					return query.getResultList();
				}
			});
			return merchandises;
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	/**
	 * Find all ShcMerchandise entities for a given orderid.
	 * 
	 * @return List<ShcMerchandise> all ShcMerchandise entities
	 */
	public List<ShcMerchandise> findByShcOrderId(final Integer shcOrderId) {
		logger.info("finding ShcMerchandise instance with OrderId:"	+ shcOrderId);

		try {
			final String queryString = "select model from ShcMerchandise model where model.shcOrder.shcOrderId"
					+"= :shcOrderId";
			@SuppressWarnings("unchecked")
			List<ShcMerchandise> merchandises = getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
				throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("shcOrderId", shcOrderId);
					return query.getResultList();
				}
			});
			return merchandises;
		}
		catch (EmptyResultDataAccessException ex) {
			logger.info("No ShcOrder entiry found for OrderId:" + shcOrderId);
			return null;
		}
		catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}


	 

	public static IShcMerchandiseDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IShcMerchandiseDAO) ctx.getBean("ShcMerchandiseDAO");
	}
}