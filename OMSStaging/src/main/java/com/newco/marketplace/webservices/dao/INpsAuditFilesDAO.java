package com.newco.marketplace.webservices.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Interface for NpsAuditFilesDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface INpsAuditFilesDAO {
	/**
	 * Perform an initial save of a previously unsaved NpsAuditFiles entity. All
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
	 * INpsAuditFilesDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditFiles entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(NpsAuditFiles entity);

	/**
	 * Delete a persistent NpsAuditFiles entity. This operation must be
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
	 * INpsAuditFilesDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditFiles entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(NpsAuditFiles entity);

	/**
	 * Persist a previously saved NpsAuditFiles entity and return it or a copy
	 * of it to the sender. A copy of the NpsAuditFiles entity parameter is
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
	 * entity = INpsAuditFilesDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            NpsAuditFiles entity to update
	 * @return NpsAuditFiles the persisted NpsAuditFiles entity instance, may
	 *         not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public NpsAuditFiles update(NpsAuditFiles entity);

	public NpsAuditFiles findById(Integer id);

	/**
	 * Find all NpsAuditFiles entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the NpsAuditFiles property to query
	 * @param value
	 *            the property value to match
	 * @return List<NpsAuditFiles> found by query
	 */
	public List<NpsAuditFiles> findByProperty(String propertyName, Object value);

	public List<NpsAuditFiles> findByFileName(Object fileName);

	public List<NpsAuditFiles> findByNumSuccess(Object numSuccess);

	public List<NpsAuditFiles> findByNumFailure(Object numFailure);

	/**
	 * Find all NpsAuditFiles entities.
	 * 
	 * @return List<NpsAuditFiles> all NpsAuditFiles entities
	 */
	public List<NpsAuditFiles> findAll();
}