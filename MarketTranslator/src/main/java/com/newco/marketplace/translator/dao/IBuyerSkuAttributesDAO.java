package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for BuyerSkuAttributesDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBuyerSkuAttributesDAO {
	/**
	 * Perform an initial save of a previously unsaved BuyerSkuAttributes
	 * entity. All subsequent persist actions of this entity should use the
	 * #update() method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * IBuyerSkuAttributesDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuAttributes entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerSkuAttributes entity);

	/**
	 * Delete a persistent BuyerSkuAttributes entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * IBuyerSkuAttributesDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuAttributes entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerSkuAttributes entity);

	/**
	 * Persist a previously saved BuyerSkuAttributes entity and return it or a
	 * copy of it to the sender. A copy of the BuyerSkuAttributes entity
	 * parameter is returned when the JPA persistence mechanism has not
	 * previously been tracking the updated entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently saved to the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#merge(Object)
	 * EntityManager#merge} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = IBuyerSkuAttributesDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuAttributes entity to update
	 * @return BuyerSkuAttributes the persisted BuyerSkuAttributes entity
	 *         instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerSkuAttributes update(BuyerSkuAttributes entity);

	public BuyerSkuAttributes findById(BuyerSkuAttributesId id);

	/**
	 * Find all BuyerSkuAttributes entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerSkuAttributes property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerSkuAttributes> found by query
	 */
	public List<BuyerSkuAttributes> findByProperty(String propertyName,
			Object value);

	/**
	 * Find all BuyerSkuAttributes entities.
	 * 
	 * @return List<BuyerSkuAttributes> all BuyerSkuAttributes entities
	 */
	public List<BuyerSkuAttributes> findAll();
}