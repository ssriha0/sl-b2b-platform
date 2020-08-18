package com.newco.marketplace.webservices.dao;

import java.util.List;
import java.util.Set;

/**
 * Interface for LuAuditOwnersDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ILuAuditOwnersDAO {
	/**
	 * Perform an initial save of a previously unsaved LuAuditOwners entity. All
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
	 * ILuAuditOwnersDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuAuditOwners entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LuAuditOwners entity);

	/**
	 * Delete a persistent LuAuditOwners entity. This operation must be
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
	 * ILuAuditOwnersDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuAuditOwners entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LuAuditOwners entity);

	/**
	 * Persist a previously saved LuAuditOwners entity and return it or a copy
	 * of it to the sender. A copy of the LuAuditOwners entity parameter is
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
	 * entity = ILuAuditOwnersDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuAuditOwners entity to update
	 * @return LuAuditOwners the persisted LuAuditOwners entity instance, may
	 *         not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LuAuditOwners update(LuAuditOwners entity);

	public LuAuditOwners findById(Integer id);

	/**
	 * Find all LuAuditOwners entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LuAuditOwners property to query
	 * @param value
	 *            the property value to match
	 * @return List<LuAuditOwners> found by query
	 */
	public List<LuAuditOwners> findByProperty(String propertyName, Object value);

	public List<LuAuditOwners> findByOwnerName(Object ownerName);

	public List<LuAuditOwners> findByEmailIds(Object emailIds);

	/**
	 * Find all LuAuditOwners entities.
	 * 
	 * @return List<LuAuditOwners> all LuAuditOwners entities
	 */
	public List<LuAuditOwners> findAll();
}