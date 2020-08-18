package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for BuyerSkuDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBuyerSkuDAO {
	/**
	 * Perform an initial save of a previously unsaved BuyerSku entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
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
	 * IBuyerSkuDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSku entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerSku entity);

	/**
	 * Delete a persistent BuyerSku entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * IBuyerSkuDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSku entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerSku entity);

	/**
	 * Persist a previously saved BuyerSku entity and return it or a copy of it
	 * to the sender. A copy of the BuyerSku entity parameter is returned when
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
	 * entity = IBuyerSkuDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSku entity to update
	 * @return BuyerSku the persisted BuyerSku entity instance, may not be the
	 *         same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerSku update(BuyerSku entity);

	public BuyerSku findById(Integer id);

	/**
	 * Find all BuyerSku entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerSku property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerSku> found by query
	 */
	public List<BuyerSku> findByProperty(String propertyName, Object value);

	public List<BuyerSku> findBySku(Object sku);

	public List<BuyerSku> findByPriceType(Object priceType);

	public List<BuyerSku> findByRetailPrice(Object retailPrice);

	public List<BuyerSku> findByBidPrice(Object bidPrice);

	public List<BuyerSku> findByBillingPrice(Object billingPrice);

	public List<BuyerSku> findByBidMargin(Object bidMargin);

	public List<BuyerSku> findByBillingMargin(Object billingMargin);

	public List<BuyerSku> findByBidPriceSchema(Object bidPriceSchema);

	public List<BuyerSku> findByBillingPriceSchema(Object billingPriceSchema);

	public List<BuyerSku> findBySkuDescription(Object skuDescription);

	public List<BuyerSku> findByModifiedBy(Object modifiedBy);

	public List<BuyerSku> findByOrderitemType(Object orderitemType);

	/**
	 * Find all BuyerSku entities.
	 * 
	 * @return List<BuyerSku> all BuyerSku entities
	 */
	public List<BuyerSku> findAll();
	
	public BuyerSku findBySkuAndBuyerID(final String sku, final Integer buyerID);
	
	/* Get the add ons for given sku category (speciality code)*/
	public List<BuyerSku> findAddOnsBySkuCategory(final BuyerSkuCategory skuCategory);
	
}