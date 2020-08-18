package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for UserProfilePermissionsDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IUserProfilePermissionsDAO {
	/**
	 * Perform an initial save of a previously unsaved UserProfilePermissions
	 * entity. All subsequent persist actions of this entity should use the
	 * #update() method. This operation must be performed within the a database
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
	 * IUserProfilePermissionsDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfilePermissions entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(UserProfilePermissions entity);

	/**
	 * Delete a persistent UserProfilePermissions entity. This operation must be
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
	 * IUserProfilePermissionsDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfilePermissions entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(UserProfilePermissions entity);

	/**
	 * Persist a previously saved UserProfilePermissions entity and return it or
	 * a copy of it to the sender. A copy of the UserProfilePermissions entity
	 * parameter is returned when the JPA persistence mechanism has not
	 * previously been tracking the updated entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently saved to the persistence store, i.e., database. This
	 * method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = IUserProfilePermissionsDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfilePermissions entity to update
	 * @returns UserProfilePermissions the persisted UserProfilePermissions
	 *          entity instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public UserProfilePermissions update(UserProfilePermissions entity);

	public UserProfilePermissions findById(UserProfilePermissionsId id);

	/**
	 * Find all UserProfilePermissions entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the UserProfilePermissions property to query
	 * @param value
	 *            the property value to match
	 * @return List<UserProfilePermissions> found by query
	 */
	public List<UserProfilePermissions> findByProperty(String propertyName,
			Object value);

	public List<UserProfilePermissions> findByModifiedBy(Object modifiedBy);

	/**
	 * Find all UserProfilePermissions entities.
	 * 
	 * @return List<UserProfilePermissions> all UserProfilePermissions entities
	 */
	public List<UserProfilePermissions> findAll();
}