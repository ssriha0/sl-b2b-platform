package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for LuDocumentCategoryDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ILuDocumentCategoryDAO {
	/**
	 * Perform an initial save of a previously unsaved LuDocumentCategory
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
	 * ILuDocumentCategoryDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuDocumentCategory entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LuDocumentCategory entity);

	/**
	 * Delete a persistent LuDocumentCategory entity. This operation must be
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
	 * ILuDocumentCategoryDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuDocumentCategory entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LuDocumentCategory entity);

	/**
	 * Persist a previously saved LuDocumentCategory entity and return it or a
	 * copy of it to the sender. A copy of the LuDocumentCategory entity
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
	 * entity = ILuDocumentCategoryDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuDocumentCategory entity to update
	 * @returns LuDocumentCategory the persisted LuDocumentCategory entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LuDocumentCategory update(LuDocumentCategory entity);

	public LuDocumentCategory findById(Integer id);

	/**
	 * Find all LuDocumentCategory entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LuDocumentCategory property to query
	 * @param value
	 *            the property value to match
	 * @return List<LuDocumentCategory> found by query
	 */
	public List<LuDocumentCategory> findByProperty(String propertyName,
			Object value);

	public List<LuDocumentCategory> findByType(Object type);

	public List<LuDocumentCategory> findByDescr(Object descr);

	public List<LuDocumentCategory> findBySortOrder(Object sortOrder);

	/**
	 * Find all LuDocumentCategory entities.
	 * 
	 * @return List<LuDocumentCategory> all LuDocumentCategory entities
	 */
	public List<LuDocumentCategory> findAll();
}