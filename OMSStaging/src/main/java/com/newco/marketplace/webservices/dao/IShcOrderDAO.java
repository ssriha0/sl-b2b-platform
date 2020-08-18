package com.newco.marketplace.webservices.dao;

import java.util.List;

/**
 * Interface for ShcOrderDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IShcOrderDAO {
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
	 * IShcOrderDAO.save(entity);
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
	public void save(ShcOrder entity);

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
	 * IShcOrderDAO.delete(entity);
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
	public void delete(ShcOrder entity);

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
	 * entity = IShcOrderDAO.update(entity);
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
	public ShcOrder update(ShcOrder entity);

	public ShcOrder findById(Integer id);

	/**
	 * Find all ShcOrder entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ShcOrder property to query
	 * @param value
	 *            the property value to match
	 * @return List<ShcOrder> found by query
	 */
	public List<ShcOrder> findByProperty(String propertyName, Object value);

	public List<ShcOrder> findByOrderNo(Object orderNo);

	public List<ShcOrder> findByUnitNo(Object unitNo);

	public List<ShcOrder> findByStoreNo(Object storeNo);

	public List<ShcOrder> findBySoId(Object soId);

	public List<ShcOrder> findByProcessedInd(Object processedInd);

	public List<ShcOrder> findByGlProcessId(Object glProcessId);

	public List<ShcOrder> findByModifiedBy(Object modifiedBy);
	
	/**
	 * Get the list of ShcOrder by nps status
	 * 
	 * @param npsStatus
	 * @return
	 */
	public List<ShcOrder> findByNpsStatus(Object npsStatus);

	/**
	 * Find all ShcOrder entities.
	 * 
	 * @return List<ShcOrder> all ShcOrder entities
	 */
	public List<ShcOrder> findAll();
	
	public ShcOrder findByOrderNoAndUnitNo(final String orderNo, final String unitNo);

	public List<ShcOrder> findAllByUnitAndOrderNumbers(final List<String> unitAndOrderNumbers);
	
	public Integer updateShcOrder(final String soId, final Integer orderId) ; 
	
	/**
	 * Updates the nps status for Shc Order
	 * @param status
	 * @param orderId
	 * @return
	 */
	public Integer updateShcOrderNpsStatus(final String npsStatus, final Integer shcOrderId) ;
	
	/**
	 * Find all ShcOrder entities having wfStateId CLOSED and processId NULL
	 * 
	 * @return List<ShcOrder> 
	 */
	public List<ShcOrder> findClosedUnprocessedOrders(final int npsOrderToClose);

	public ShcOrder findLatestByUnitAndOrderNumbersWithTestSuffix(final String unitAndOrderNumber,
			final String testSuffix);
}
