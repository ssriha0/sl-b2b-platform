package com.newco.marketplace.webservices.dao;

import java.sql.Timestamp;
import java.util.List;

/**
 * Interface for NpsAuditOrderMessagesDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface INpsAuditOrderMessagesDAO {
	/**
	 * Perform an initial save of a previously unsaved NpsAuditOrderMessages
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
	 * INpsAuditOrderMessagesDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditOrderMessages entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(NpsAuditOrderMessages entity);

	/**
	 * Delete a persistent NpsAuditOrderMessages entity. This operation must be
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
	 * INpsAuditOrderMessagesDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditOrderMessages entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(NpsAuditOrderMessages entity);

	/**
	 * Persist a previously saved NpsAuditOrderMessages entity and return it or
	 * a copy of it to the sender. A copy of the NpsAuditOrderMessages entity
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
	 * entity = INpsAuditOrderMessagesDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditOrderMessages entity to update
	 * @return NpsAuditOrderMessages the persisted NpsAuditOrderMessages entity
	 *         instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public NpsAuditOrderMessages update(NpsAuditOrderMessages entity);

	public NpsAuditOrderMessages findById(Integer id);

	/**
	 * Find all NpsAuditOrderMessages entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the NpsAuditOrderMessages property to query
	 * @param value
	 *            the property value to match
	 * @return List<NpsAuditOrderMessages> found by query
	 */
	public List<NpsAuditOrderMessages> findByProperty(String propertyName,
			Object value);

	/**
	 * Find all NpsAuditOrderMessages entities.
	 * 
	 * @return List<NpsAuditOrderMessages> all NpsAuditOrderMessages entities
	 */
	public List<NpsAuditOrderMessages> findAll();
}