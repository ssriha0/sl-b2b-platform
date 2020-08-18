package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for LuZipMarketDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ILuZipMarketDAO {
	/**
	 * Perform an initial save of a previously unsaved LuZipMarket entity. All
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
	 * ILuZipMarketDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuZipMarket entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LuZipMarket entity);

	/**
	 * Delete a persistent LuZipMarket entity. This operation must be performed
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
	 * ILuZipMarketDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuZipMarket entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LuZipMarket entity);

	/**
	 * Persist a previously saved LuZipMarket entity and return it or a copy of
	 * it to the sender. A copy of the LuZipMarket entity parameter is returned
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
	 * entity = ILuZipMarketDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuZipMarket entity to update
	 * @returns LuZipMarket the persisted LuZipMarket entity instance, may not
	 *          be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LuZipMarket update(LuZipMarket entity);

	public LuZipMarket findById(String id);

	/**
	 * Find all LuZipMarket entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LuZipMarket property to query
	 * @param value
	 *            the property value to match
	 * @return List<LuZipMarket> found by query
	 */
	public List<LuZipMarket> findByProperty(String propertyName, Object value);

	public List<LuZipMarket> findByMarketId(Object marketId);

	/**
	 * Find all LuZipMarket entities.
	 * 
	 * @return List<LuZipMarket> all LuZipMarket entities
	 */
	public List<LuZipMarket> findAll();
}