package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for BuyerMarketAdjustmentDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBuyerMarketAdjustmentDAO {
	/**
	 * Perform an initial save of a previously unsaved BuyerMarketAdjustment
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
	 * IBuyerMarketAdjustmentDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerMarketAdjustment entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerMarketAdjustment entity);

	/**
	 * Delete a persistent BuyerMarketAdjustment entity. This operation must be
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
	 * IBuyerMarketAdjustmentDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerMarketAdjustment entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerMarketAdjustment entity);

	/**
	 * Persist a previously saved BuyerMarketAdjustment entity and return it or
	 * a copy of it to the sender. A copy of the BuyerMarketAdjustment entity
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
	 * entity = IBuyerMarketAdjustmentDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerMarketAdjustment entity to update
	 * @returns BuyerMarketAdjustment the persisted BuyerMarketAdjustment entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerMarketAdjustment update(BuyerMarketAdjustment entity);

	public BuyerMarketAdjustment findById(Integer id);

	/**
	 * Find all BuyerMarketAdjustment entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerMarketAdjustment property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerMarketAdjustment> found by query
	 */
	public List<BuyerMarketAdjustment> findByProperty(String propertyName,
			Object value);

	public List<BuyerMarketAdjustment> findByAdjustment(Object adjustment);

	public List<BuyerMarketAdjustment> findByModifiedBy(Object modifiedBy);

	/**
	 * Find all BuyerMarketAdjustment entities.
	 * 
	 * @return List<BuyerMarketAdjustment> all BuyerMarketAdjustment entities
	 */
	public List<BuyerMarketAdjustment> findAll();
}