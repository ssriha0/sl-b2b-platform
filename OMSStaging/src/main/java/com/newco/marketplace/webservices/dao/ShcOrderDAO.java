package com.newco.marketplace.webservices.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.newco.marketplace.webservices.util.StagingConstants;


/**
 * A data access object (DAO) providing persistence and search support for
 * ShcOrder entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.ShcOrder
 * @author MyEclipse Persistence Tools
 */

public class ShcOrderDAO extends JpaDaoSupport implements IShcOrderDAO {
	// property constants
	public static final String ORDER_NO = "orderNo";
	public static final String UNIT_NO = "unitNo";
	public static final String STORE_NO = "storeNo";
	public static final String SO_ID = "soId";
	public static final String PROCESSED_IND = "processedInd";
	public static final String GL_PROCESS_ID = "glProcessId";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String COMPLETED_DATE = "completedDate";
	public static final String ROUTED_DATE = "routedDate";
	public static final String RESOLUTION_DESCR = "resolutionDescr";
	public static final String NPS_STATUS = "npsStatus";
	

	/**
	 * Perform an initial save of a previously unsaved ShcOrder entity. All
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
	 * ShcOrderDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrder entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ShcOrder entity) {
		logger.info("saving ShcOrder instance:");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful:");
		} catch (RuntimeException re) {
			logger.error("save failed:");
			throw re;
		}
	}

	/**
	 * Delete a persistent ShcOrder entity. This operation must be performed
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
	 * ShcOrderDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrder entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ShcOrder entity) {
		logger.info("deleting ShcOrder instance:");
		try {
			entity = getJpaTemplate().getReference(ShcOrder.class,
					entity.getShcOrderId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved ShcOrder entity and return it or a copy of it
	 * to the sender. A copy of the ShcOrder entity parameter is returned when
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
	 * entity = ShcOrderDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrder entity to update
	 * @returns ShcOrder the persisted ShcOrder entity instance, may not be the
	 *          same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ShcOrder update(ShcOrder entity) {
		logger.info("updating ShcOrder instance");
		try {
			ShcOrder result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public ShcOrder findById(Integer id) {
		if (logger.isInfoEnabled())
		  logger.info("finding ShcOrder instance with id: " + id);
		try {
			ShcOrder instance = getJpaTemplate().find(ShcOrder.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all ShcOrder entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ShcOrder property to query
	 * @param value
	 *            the property value to match
	 * @return List<ShcOrder> found by query
	 */
	public List<ShcOrder> findByProperty(String propertyName, final Object value) {
		if (logger.isInfoEnabled()) {
			logger.info("finding ShcOrder instance with property: " + propertyName
					+ ", value: " + value);
		}
		
		try {
			final String queryString = "select model from ShcOrder model where model."
					+ propertyName + "= :propertyValue";
			@SuppressWarnings("unchecked")
			List<ShcOrder> orders = getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("propertyValue", value);
					return query.getResultList();
				}
			});
			return orders;
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ShcOrder> findByOrderNo(Object orderNo) {
		return findByProperty(ORDER_NO, orderNo);
	}

	public List<ShcOrder> findByUnitNo(Object unitNo) {
		return findByProperty(UNIT_NO, unitNo);
	}

	public List<ShcOrder> findByStoreNo(Object storeNo) {
		return findByProperty(STORE_NO, storeNo);
	}

	public List<ShcOrder> findBySoId(Object soId) {
		return findByProperty(SO_ID, soId);
	}

	public List<ShcOrder> findByProcessedInd(Object processedInd) {
		return findByProperty(PROCESSED_IND, processedInd);
	}

	public List<ShcOrder> findByGlProcessId(Object glProcessId) {
		return findByProperty(GL_PROCESS_ID, glProcessId);
	}

	public List<ShcOrder> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}
	
	public List<ShcOrder> findByCompletedDate(Object completedDate) {
		return findByProperty(COMPLETED_DATE, completedDate);
	}
	
	public List<ShcOrder> findByRoutedDate(Object routedDate) {
		return findByProperty(ROUTED_DATE, routedDate);
	}
	
	public List<ShcOrder> findByResolutionDescr(Object resolutionDescr) {
		return findByProperty(RESOLUTION_DESCR, resolutionDescr);
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.webservices.dao.IShcOrderDAO#findByNpsStatus(java.lang.Object)
	 */
	public List<ShcOrder> findByNpsStatus(Object npsStatus) {
		return findByProperty(NPS_STATUS, npsStatus);
	}

	/**
	 * Find all ShcOrder entities.
	 * 
	 * @return List<ShcOrder> all ShcOrder entities
	 */
	public List<ShcOrder> findAll() {
		logger.info("finding all ShcOrder instances");
		try {
			final String queryString = "select model from ShcOrder model";
			@SuppressWarnings("unchecked")
			List<ShcOrder> orders = getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					return query.getResultList();
				}
			});
			return orders;
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	/**
	 * Update so_id in ShcOrder Table	 
	 * @return Integer
	 */
	public Integer updateShcOrder(final String soId, final Integer shcOrderId) {
		if (logger.isInfoEnabled())
		  logger.info("updating ShcOrder instance with order number:" + shcOrderId );
		try {
			final String queryString = "update ShcOrder model set model.soId" 
			 						+ "= :soId where model.shcOrderId = :shcOrderId";
				return (Integer) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("soId", soId);
					query.setParameter("shcOrderId", shcOrderId);
					return Integer.valueOf(query.executeUpdate());
				}
			});   
		}
		catch(EmptyResultDataAccessException ex){
			logger.info("No ShcOrder entity found for order orderId:" + shcOrderId );
			return null;
		
		}
		catch (RuntimeException re) {
			logger.error("updating soId in shc_order failed", re);
			throw re;
		}
	}
	
	public ShcOrder findByOrderNoAndUnitNo(final String orderNo, final String unitNo) {
		if (logger.isInfoEnabled())
		  logger.info("finding ShcOrder instance with order number:" + orderNo + " and unit number: " + unitNo);
		try {
			final String queryString = "select model from ShcOrder model where model.orderNo" 
			 						+ "= :orderNo and model.unitNo = :unitNo";
			return (ShcOrder) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("orderNo", orderNo);
					query.setParameter("unitNo", unitNo);
					return query.getSingleResult();
				}
			});
		} catch(EmptyResultDataAccessException ex){
			logger.info("No ShcOrder entity found for order number:" + orderNo + " and unit number: " + unitNo);
			return null;
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List<ShcOrder> findAllByUnitAndOrderNumbers(final List<String> unitAndOrderNumbers) {
		StringBuilder sb = new StringBuilder(unitAndOrderNumbers.size() * 20);
		if (logger.isInfoEnabled()) {
			for (String s : unitAndOrderNumbers) sb.append(s).append(",");
			if (sb.length() > 0) sb.delete(sb.length()-1, sb.length());
			logger.info("finding all ShcOrder instances with unit and order numbers:" + sb.toString());
		}
		try {
			final String queryString =
				"select model from ShcOrder model " +
				"where model.shcUnitOrderNumber IN (:unitAndOrderNumbers) AND model.soId is not null";
			
			@SuppressWarnings("unchecked")
			List<ShcOrder> resultList = (List<ShcOrder>) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("unitAndOrderNumbers", unitAndOrderNumbers);
					return query.getResultList();
				}
			});
			return resultList;
		} catch(EmptyResultDataAccessException ex){
			if (sb.length() <= 0) {
				for (String s : unitAndOrderNumbers) sb.append(s).append(", ");
				if (sb.length() > 2) sb.delete(sb.length()-2, sb.length()); // remove trailing ", "
			}
			logger.info("No ShcOrder entities found for unit and order numbers: " + sb.toString());
			return Collections.emptyList();
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	public ShcOrder findLatestByUnitAndOrderNumbersWithTestSuffix(final String unitAndOrderNumber, final String testSuffix) {
		if (logger.isInfoEnabled()) {
			  logger.info("finding all ShcOrder instances with unit and order number:" + unitAndOrderNumber + " and testSuffx: " + testSuffix);
		}
		try {
			final String queryString =
				"select model from ShcOrder model " +
				"where concat(model.unitNo, model.orderNo) like :unitAndOrderNumber" +
				"order by model.shcOrderId desc";
			
			ShcOrder result = (ShcOrder) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("unitAndOrderNumber", unitAndOrderNumber + testSuffix + "%");
					query.setMaxResults(1);
					return query.getSingleResult();
				}
			});
			return result;
		} catch(EmptyResultDataAccessException ex){
			logger.info("No ShcOrder entities found for unit and order numbers: " + unitAndOrderNumber + " and testSuffix: " + testSuffix);
			return null;
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * TO ENABLE SL-11193, UNCOMMENT THE THREE LINES 
	 * 
	 * Find all closed ShcOrder entities which are not yet processed.
	 * 
	 * @return List<ShcOrder> 
	 */
	public List<ShcOrder> findClosedUnprocessedOrders(final int npsOrderToClose) {
		
		logger.info("finding unprocessed closed orders i.e. wf_state_id = 180 and nps_status is AT");	
		try {
			final String queryString = "select model from ShcOrder model where model.wfStateId = :wfStateId and model.npsStatus = :npsStatus order by model.npsProcessId, model.shcOrderId";
			@SuppressWarnings("unchecked")
			List<ShcOrder> orders = getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("wfStateId", StagingConstants.CLOSED_STATE);
					query.setParameter("npsStatus", StagingConstants.ASSIGNED_TECH);
					query.setMaxResults(npsOrderToClose);
					return query.getResultList();
				}
			});
			return orders;
		} catch (RuntimeException re) {
			logger.error("findByProcessIdAndStateId failed", re);
			throw re;
		}
	}
	
	public static IShcOrderDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IShcOrderDAO) ctx.getBean("ShcOrderDAO");
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.webservices.dao.IShcOrderDAO#updateShcOrderNpsStatus(java.lang.String, java.lang.Integer)
	 */
	public Integer updateShcOrderNpsStatus(final String npsStatus, final Integer shcOrderId) {
		if (logger.isInfoEnabled())
			  logger.info("updating ShcOrder instance " + shcOrderId +  " with nps status:" + npsStatus );
			try {
				final String queryString = "update ShcOrder model set model.npsStatus" 
				 						+ "= :npsStatus where model.shcOrderId = :shcOrderId";
					return (Integer) getJpaTemplate().execute(new JpaCallback() {
					public Object doInJpa(EntityManager em) throws PersistenceException {
						Query query = em.createQuery(queryString);
						query.setParameter("npsStatus", npsStatus);
						query.setParameter("shcOrderId", shcOrderId);
						return Integer.valueOf(query.executeUpdate());
					}
				});   
			}
			catch(EmptyResultDataAccessException ex){
				logger.info("No ShcOrder entity found for order orderId:" + shcOrderId );
				return null;
			
			}
			catch (RuntimeException re) {
				logger.error("updating nps status in shc_order failed", re);
				throw re;
			}
	}
}