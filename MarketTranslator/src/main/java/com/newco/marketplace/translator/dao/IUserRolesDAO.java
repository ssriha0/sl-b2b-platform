package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for UserRolesDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IUserRolesDAO {
	/**
	 * Perform an initial save of a previously unsaved UserRoles entity. All
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
	 * IUserRolesDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserRoles entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(UserRoles entity);

	/**
	 * Delete a persistent UserRoles entity. This operation must be performed
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
	 * IUserRolesDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserRoles entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(UserRoles entity);

	/**
	 * Persist a previously saved UserRoles entity and return it or a copy of it
	 * to the sender. A copy of the UserRoles entity parameter is returned when
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
	 * entity = IUserRolesDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserRoles entity to update
	 * @returns UserRoles the persisted UserRoles entity instance, may not be
	 *          the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public UserRoles update(UserRoles entity);

	public UserRoles findById(Integer id);

	/**
	 * Find all UserRoles entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the UserRoles property to query
	 * @param value
	 *            the property value to match
	 * @return List<UserRoles> found by query
	 */
	public List<UserRoles> findByProperty(String propertyName, Object value);

	public List<UserRoles> findByRoleName(Object roleName);

	public List<UserRoles> findByRoleDescr(Object roleDescr);

	public List<UserRoles> findByInternalRole(Object internalRole);

	/**
	 * Find all UserRoles entities.
	 * 
	 * @return List<UserRoles> all UserRoles entities
	 */
	public List<UserRoles> findAll();
}