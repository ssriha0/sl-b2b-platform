package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for BuyerRetailPriceDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBuyerRetailPriceDAO {
	/**
	 * Perform an initial save of a previously unsaved BuyerRetailPrice entity.
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
	 * IBuyerRetailPriceDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerRetailPrice entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerRetailPrice entity);

	/**
	 * Delete a persistent BuyerRetailPrice entity. This operation must be
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
	 * IBuyerRetailPriceDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerRetailPrice entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerRetailPrice entity);

	/**
	 * Persist a previously saved BuyerRetailPrice entity and return it or a
	 * copy of it to the sender. A copy of the BuyerRetailPrice entity parameter
	 * is returned when the JPA persistence mechanism has not previously been
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
	 * entity = IBuyerRetailPriceDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerRetailPrice entity to update
	 * @returns BuyerRetailPrice the persisted BuyerRetailPrice entity instance,
	 *          may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerRetailPrice update(BuyerRetailPrice entity);

	public BuyerRetailPrice findById(Integer id);

	/**
	 * Find all BuyerRetailPrice entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerRetailPrice property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerRetailPrice> found by query
	 */
	public List<BuyerRetailPrice> findByProperty(String propertyName,
			Object value);

	public List<BuyerRetailPrice> findByStoreNo(Object storeNo);

	public List<BuyerRetailPrice> findBySku(Object sku);

	public List<BuyerRetailPrice> findByRetailPrice(Object retailPrice);

	public List<BuyerRetailPrice> findByModifiedBy(Object modifiedBy);

	/**
	 * Find all BuyerRetailPrice entities.
	 * 
	 * @return List<BuyerRetailPrice> all BuyerRetailPrice entities
	 */
	public List<BuyerRetailPrice> findAll();
	
	/**
	 *  Find all BuyerRetailPrice entity with a specific sku and store value
	 * 
	 * @param storeNo
	 * @param sku
	 * @param buyerID
	 * @return BuyerRetailPrice for the specific sku and store value
	 * * @throws Exception when the system cannot find the retail price for sku and store number
	 */
	public BuyerRetailPrice findByStoreNoAndSKU(final String storeNo, final String sku, final Integer buyerID, final String defaultStoreNo) throws Exception;
}