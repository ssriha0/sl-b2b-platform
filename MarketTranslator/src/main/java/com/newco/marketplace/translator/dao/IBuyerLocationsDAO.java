package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for BuyerLocationsDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBuyerLocationsDAO {
	/**
	 * Perform an initial save of a previously unsaved BuyerLocations entity.
	 * All subsequent persist actions of this entity should use the #update()
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
	 * IBuyerLocationsDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerLocations entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerLocations entity);

	/**
	 * Delete a persistent BuyerLocations entity. This operation must be
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
	 * IBuyerLocationsDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerLocations entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerLocations entity);

	/**
	 * Persist a previously saved BuyerLocations entity and return it or a copy
	 * of it to the sender. A copy of the BuyerLocations entity parameter is
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
	 * entity = IBuyerLocationsDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerLocations entity to update
	 * @returns BuyerLocations the persisted BuyerLocations entity instance, may
	 *          not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerLocations update(BuyerLocations entity);

	public BuyerLocations findById(BuyerLocationsId id);

	/**
	 * Find all BuyerLocations entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerLocations property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerLocations> found by query
	 */
	public List<BuyerLocations> findByProperty(String propertyName, Object value);

	public List<BuyerLocations> findByModifiedBy(Object modifiedBy);

	/**
	 * Find all BuyerLocations entities.
	 * 
	 * @return List<BuyerLocations> all BuyerLocations entities
	 */
	public List<BuyerLocations> findAll();
}