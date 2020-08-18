package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for ApplicationPropertiesDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IApplicationPropertiesDAO {
	/**
	 * Perform an initial save of a previously unsaved ApplicationProperties
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
	 * IApplicationPropertiesDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ApplicationProperties entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ApplicationPropertiesMT entity);

	/**
	 * Delete a persistent ApplicationProperties entity. This operation must be
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
	 * IApplicationPropertiesDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ApplicationProperties entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ApplicationPropertiesMT entity);

	/**
	 * Persist a previously saved ApplicationProperties entity and return it or
	 * a copy of it to the sender. A copy of the ApplicationProperties entity
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
	 * entity = IApplicationPropertiesDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ApplicationProperties entity to update
	 * @returns ApplicationProperties the persisted ApplicationProperties entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ApplicationPropertiesMT update(ApplicationPropertiesMT entity);

	public ApplicationPropertiesMT findById(String id);

	/**
	 * Find all ApplicationProperties entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ApplicationProperties property to query
	 * @param value
	 *            the property value to match
	 * @return List<ApplicationProperties> found by query
	 */
	public List<ApplicationPropertiesMT> findByProperty(String propertyName,
			Object value);

	public List<ApplicationPropertiesMT> findByAppValue(Object appValue);
	
	public ApplicationPropertiesMT findByAppKey(String appKey);

	public List<ApplicationPropertiesMT> findByAppDesc(Object appDesc);

	public List<ApplicationPropertiesMT> findByModifiedBy(Object modifiedBy);

	/**
	 * Find all ApplicationProperties entities.
	 * 
	 * @return List<ApplicationProperties> all ApplicationProperties entities
	 */
	public List<ApplicationPropertiesMT> findAll();
}