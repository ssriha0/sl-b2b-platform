package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for UserProfilePasswordDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IUserProfilePasswordDAO {
	/**
	 * Perform an initial save of a previously unsaved UserProfilePassword
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
	 * IUserProfilePasswordDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfilePassword entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(UserProfilePassword entity);

	/**
	 * Delete a persistent UserProfilePassword entity. This operation must be
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
	 * IUserProfilePasswordDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfilePassword entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(UserProfilePassword entity);

	/**
	 * Persist a previously saved UserProfilePassword entity and return it or a
	 * copy of it to the sender. A copy of the UserProfilePassword entity
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
	 * entity = IUserProfilePasswordDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfilePassword entity to update
	 * @returns UserProfilePassword the persisted UserProfilePassword entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public UserProfilePassword update(UserProfilePassword entity);

	public UserProfilePassword findById(Integer id);

	/**
	 * Find all UserProfilePassword entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the UserProfilePassword property to query
	 * @param value
	 *            the property value to match
	 * @return List<UserProfilePassword> found by query
	 */
	public List<UserProfilePassword> findByProperty(String propertyName,
			Object value);

	public List<UserProfilePassword> findByUserName(Object userName);

	public List<UserProfilePassword> findByPassword(Object password);

	public List<UserProfilePassword> findByModifiedBy(Object modifiedBy);

	/**
	 * Find all UserProfilePassword entities.
	 * 
	 * @return List<UserProfilePassword> all UserProfilePassword entities
	 */
	public List<UserProfilePassword> findAll();
}