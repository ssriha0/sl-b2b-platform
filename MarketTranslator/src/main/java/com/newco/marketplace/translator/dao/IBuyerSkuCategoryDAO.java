package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for BuyerSkuCategoryDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBuyerSkuCategoryDAO {
	/**
	 * Perform an initial save of a previously unsaved BuyerSkuCategory entity.
	 * All subsequent persist actions of this entity should use the #update()
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
	 * IBuyerSkuCategoryDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuCategory entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerSkuCategory entity);

	/**
	 * Delete a persistent BuyerSkuCategory entity. This operation must be
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
	 * IBuyerSkuCategoryDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuCategory entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerSkuCategory entity);

	/**
	 * Persist a previously saved BuyerSkuCategory entity and return it or a
	 * copy of it to the sender. A copy of the BuyerSkuCategory entity parameter
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
	 * entity = IBuyerSkuCategoryDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuCategory entity to update
	 * @return BuyerSkuCategory the persisted BuyerSkuCategory entity instance,
	 *         may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerSkuCategory update(BuyerSkuCategory entity);

	public BuyerSkuCategory findById(Integer id);

	/**
	 * Find all BuyerSkuCategory entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerSkuCategory property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerSkuCategory> found by query
	 */
	public List<BuyerSkuCategory> findByProperty(String propertyName,
			Object value);

	public List<BuyerSkuCategory> findByCategoryName(Object categoryName);

	public List<BuyerSkuCategory> findByCategoryDescr(Object categoryDescr);

	public List<BuyerSkuCategory> findByCategoryId(Object categoryId);
	/**
	 * Find all BuyerSkuCategory entities.
	 * 
	 * @return List<BuyerSkuCategory> all BuyerSkuCategory entities
	 */
	public List<BuyerSkuCategory> findAll();
}