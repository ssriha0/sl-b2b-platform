package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for LuMarketDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ILuMarketDAO {
	/**
	 * Perform an initial save of a previously unsaved LuMarket entity. All
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
	 * ILuMarketDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuMarket entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LuMarket entity);

	/**
	 * Delete a persistent LuMarket entity. This operation must be performed
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
	 * ILuMarketDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuMarket entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LuMarket entity);

	/**
	 * Persist a previously saved LuMarket entity and return it or a copy of it
	 * to the sender. A copy of the LuMarket entity parameter is returned when
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
	 * entity = ILuMarketDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuMarket entity to update
	 * @returns LuMarket the persisted LuMarket entity instance, may not be the
	 *          same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LuMarket update(LuMarket entity);

	public LuMarket findById(Integer id);

	/**
	 * Find all LuMarket entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LuMarket property to query
	 * @param value
	 *            the property value to match
	 * @return List<LuMarket> found by query
	 */
	public List<LuMarket> findByProperty(String propertyName, Object value);

	public List<LuMarket> findByMarketName(Object marketName);

	public List<LuMarket> findByModifiedBy(Object modifiedBy);

	/**
	 * Find all LuMarket entities.
	 * 
	 * @return List<LuMarket> all LuMarket entities
	 */
	public List<LuMarket> findAll();
}