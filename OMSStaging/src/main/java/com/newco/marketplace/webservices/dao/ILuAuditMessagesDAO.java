package com.newco.marketplace.webservices.dao;

import java.util.List;
import java.util.Set;

/**
 * Interface for LuAuditMessagesDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ILuAuditMessagesDAO {
	/**
	 * Perform an initial save of a previously unsaved LuAuditMessages entity.
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
	 * ILuAuditMessagesDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuAuditMessages entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LuAuditMessages entity);

	/**
	 * Delete a persistent LuAuditMessages entity. This operation must be
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
	 * ILuAuditMessagesDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuAuditMessages entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LuAuditMessages entity);

	/**
	 * Persist a previously saved LuAuditMessages entity and return it or a copy
	 * of it to the sender. A copy of the LuAuditMessages entity parameter is
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
	 * entity = ILuAuditMessagesDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuAuditMessages entity to update
	 * @return LuAuditMessages the persisted LuAuditMessages entity instance,
	 *         may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LuAuditMessages update(LuAuditMessages entity);

	public LuAuditMessages findById(Integer id);

	/**
	 * Find all LuAuditMessages entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LuAuditMessages property to query
	 * @param value
	 *            the property value to match
	 * @return List<LuAuditMessages> found by query
	 */
	public List<LuAuditMessages> findByProperty(String propertyName,
			Object value);

	public List<LuAuditMessages> findByMessage(Object message);

	public List<LuAuditMessages> findByWorkInstruction(Object workInstruction);

	public List<LuAuditMessages> findByNpsStatus(Object npsStatus);

	public List<LuAuditMessages> findBySuccessInd(Object successInd);

	public List<LuAuditMessages> findByReportable(Object reportable);

	/**
	 * Find all LuAuditMessages entities.
	 * 
	 * @return List<LuAuditMessages> all LuAuditMessages entities
	 */
	public List<LuAuditMessages> findAll();
	
	/**
	 * find all LUAuditMessages entities for given message and npsStatus
	 * @param message
	 * @param npsStatus
	 * @return List<LuAuditMessages>
	 */
	public List<LuAuditMessages> findByMessageAndNpsStatus(String message, String npsStatus);
}