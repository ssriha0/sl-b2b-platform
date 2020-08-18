package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for UserProfileDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IUserProfileDAO {
	/**
	 * Perform an initial save of a previously unsaved UserProfile entity. All
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
	 * IUserProfileDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfile entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(UserProfile entity);

	/**
	 * Delete a persistent UserProfile entity. This operation must be performed
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
	 * IUserProfileDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfile entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(UserProfile entity);

	/**
	 * Persist a previously saved UserProfile entity and return it or a copy of
	 * it to the sender. A copy of the UserProfile entity parameter is returned
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
	 * entity = IUserProfileDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfile entity to update
	 * @returns UserProfile the persisted UserProfile entity instance, may not
	 *          be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public UserProfile update(UserProfile entity);

	public UserProfile findById(String id);

	/**
	 * Find all UserProfile entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the UserProfile property to query
	 * @param value
	 *            the property value to match
	 * @return List<UserProfile> found by query
	 */
	public List<UserProfile> findByProperty(String propertyName, Object value);

	public List<UserProfile> findByVendorId(Object vendorId);

	public List<UserProfile> findByPassword(Object password);

	public List<UserProfile> findByQuestionId(Object questionId);

	public List<UserProfile> findByAnswerTxt(Object answerTxt);

	public List<UserProfile> findByContactId(Object contactId);

	public List<UserProfile> findByModifiedBy(Object modifiedBy);

	public List<UserProfile> findByGeneratedPasswordInd(
			Object generatedPasswordInd);

	public List<UserProfile> findByLockedInd(Object lockedInd);

	public List<UserProfile> findByLoginAttemptCount(Object loginAttemptCount);

	/**
	 * Find all UserProfile entities.
	 * 
	 * @return List<UserProfile> all UserProfile entities
	 */
	public List<UserProfile> findAll();
	
	public UserProfile findByUserNameAndRoleID(String userName, Integer roleID);
}