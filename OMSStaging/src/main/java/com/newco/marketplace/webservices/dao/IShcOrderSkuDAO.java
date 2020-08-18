package com.newco.marketplace.webservices.dao;

import java.util.List;

/**
 * Interface for ShcOrderSkuDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IShcOrderSkuDAO {
	/**
	 * Perform an initial save of a previously unsaved ShcOrderSku entity. All
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
	 * IShcOrderSkuDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrderSku entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ShcOrderSku entity);

	/**
	 * Delete a persistent ShcOrderSku entity. This operation must be performed
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
	 * IShcOrderSkuDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrderSku entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ShcOrderSku entity);

	/**
	 * Persist a previously saved ShcOrderSku entity and return it or a copy of
	 * it to the sender. A copy of the ShcOrderSku entity parameter is returned
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
	 * entity = IShcOrderSkuDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcOrderSku entity to update
	 * @returns ShcOrderSku the persisted ShcOrderSku entity instance, may not
	 *          be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ShcOrderSku update(ShcOrderSku entity);

	public ShcOrderSku findById(Integer id);

	/**
	 * Find all ShcOrderSku entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ShcOrderSku property to query
	 * @param value
	 *            the property value to match
	 * @return List<ShcOrderSku> found by query
	 */
	public List<ShcOrderSku> findByProperty(String propertyName, Object value);

	public List<ShcOrderSku> findBySku(Object sku);
	
	public List<ShcOrderSku> findByAddOnInd(Object addOnInd);

	public List<ShcOrderSku> findByInitialBidPrice(Object initialBidPrice);

	public List<ShcOrderSku> findByPriceRatio(Object priceRatio);

	public List<ShcOrderSku> findByFinalPrice(Object finalPrice);

	public List<ShcOrderSku> findByRetailPrice(Object retailPrice);

	public List<ShcOrderSku> findBySellingPrice(Object sellingPrice);
	
	public List<ShcOrderSku> findByServiceFee(Object serviceFee);

	public List<ShcOrderSku> findByChargeCode(Object chargeCode);

	public List<ShcOrderSku> findByCoverage(Object coverage);

	public List<ShcOrderSku> findByType(Object type);

	public List<ShcOrderSku> findByPermitSkuInd(Object permitSkuInd);

	public List<ShcOrderSku> findByModifiedBy(Object modifiedBy);

	public List<ShcOrderSku> findByShcOrder(Object shcOrder);
	
	public List<ShcOrderSku> findByQty(Object qty);
	/**
	 * Find all ShcOrderSku entities.
	 * 
	 * @return List<ShcOrderSku> all ShcOrderSku entities
	 */
	public List<ShcOrderSku> findAll();
}