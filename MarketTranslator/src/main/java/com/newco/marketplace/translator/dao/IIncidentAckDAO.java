package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for IncidentAckDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IIncidentAckDAO {
	/**
	 * Perform an initial save of a previously unsaved IncidentAck entity. All
	 * subsequent persist actions of this entity should use the #update()
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
	 * IIncidentAckDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            IncidentAck entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(IncidentAck entity);

	/**
	 * Delete a persistent IncidentAck entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * IIncidentAckDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            IncidentAck entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(IncidentAck entity);

	/**
	 * Persist a previously saved IncidentAck entity and return it or a copy of
	 * it to the sender. A copy of the IncidentAck entity parameter is returned
	 * when the JPA persistence mechanism has not previously been tracking the
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
	 * entity = IIncidentAckDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            IncidentAck entity to update
	 * @return IncidentAck the persisted IncidentAck entity instance, may not be
	 *         the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public IncidentAck update(IncidentAck entity);

	public IncidentAck findById(Integer id);

	/**
	 * Find all IncidentAck entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the IncidentAck property to query
	 * @param value
	 *            the property value to match
	 * @return List<IncidentAck> found by query
	 */
	public List<IncidentAck> findByProperty(String propertyName, Object value);

	public List<IncidentAck> findByAckFileName(Object ackFileName);

	public List<IncidentAck> findByIncidentStatus(Object incidentStatus);

	public List<IncidentAck> findByModifiedBy(Object modifiedBy);

	/**
	 * Find all IncidentAck entities.
	 * 
	 * @return List<IncidentAck> all IncidentAck entities
	 */
	public List<IncidentAck> findAll();
}