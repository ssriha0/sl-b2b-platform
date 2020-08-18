package com.newco.marketplace.webservices.dao;

import java.util.List;

/**
 * Interface for NpsAuditOrdersDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface INpsAuditOrdersDAO {
	/**
	 * Perform an initial save of a previously unsaved NpsAuditOrders entity.
	 * All subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
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
	 * INpsAuditOrdersDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditOrders entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(NpsAuditOrders entity);

	/**
	 * Delete a persistent NpsAuditOrders entity. This operation must be
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
	 * INpsAuditOrdersDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditOrders entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(NpsAuditOrders entity);

	/**
	 * Persist a previously saved NpsAuditOrders entity and return it or a copy
	 * of it to the sender. A copy of the NpsAuditOrders entity parameter is
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
	 * entity = INpsAuditOrdersDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditOrders entity to update
	 * @return NpsAuditOrders the persisted NpsAuditOrders entity instance, may
	 *         not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public NpsAuditOrders update(NpsAuditOrders entity);

	public NpsAuditOrders findById(Integer id);

	/**
	 * Find all NpsAuditOrders entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the NpsAuditOrders property to query
	 * @param value
	 *            the property value to match
	 * @return List<NpsAuditOrders> found by query
	 */
	public List<NpsAuditOrders> findByProperty(String propertyName, Object value);

	public List<NpsAuditOrders> findByProcessId(Object processId);

	public List<NpsAuditOrders> findByReturnCode(Object returnCode);

	public List<NpsAuditOrders> findByStagingOrderId(Object stagingOrderId);

	/**
	 * Find all NpsAuditOrders entities.
	 * 
	 * @return List<NpsAuditOrders> all NpsAuditOrders entities
	 */
	public List<NpsAuditOrders> findAll();
}