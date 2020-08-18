package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for BuyerSkuTaskAssocDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBuyerSkuTaskAssocDAO {
	/**
	 * Perform an initial save of a previously unsaved BuyerSkuTaskAssoc entity.
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
	 * IBuyerSkuTaskAssocDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuTaskAssoc entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerSkuTaskAssocMT entity);

	/**
	 * Delete a persistent BuyerSkuTaskAssoc entity. This operation must be
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
	 * IBuyerSkuTaskAssocDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuTaskAssoc entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerSkuTaskAssocMT entity);

	/**
	 * Persist a previously saved BuyerSkuTaskAssoc entity and return it or a
	 * copy of it to the sender. A copy of the BuyerSkuTaskAssoc entity
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
	 * entity = IBuyerSkuTaskAssocDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSkuTaskAssoc entity to update
	 * @returns BuyerSkuTaskAssoc the persisted BuyerSkuTaskAssoc entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerSkuTaskAssocMT update(BuyerSkuTaskAssocMT entity);

	public BuyerSkuTaskAssocMT findById(Integer id);

	/**
	 * Find all BuyerSkuTaskAssoc entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerSkuTaskAssoc property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerSkuTaskAssoc> found by query
	 */
	public List<BuyerSkuTaskAssocMT> findByProperty(String propertyName,
			Object value);

	public List<BuyerSkuTaskAssocMT> findBySku(Object sku);
	
	/**
	 * Find all BuyerSkuTaskAssoc entities with specific sku and specialty code.
	 * 
	 * @param sku
	 * @param specialtyCode
	 * @return List<BuyerSkuTaskAssoc> found by query
	 */
	public List<BuyerSkuTaskAssocMT> findBySkuAndSpecialtyCode(Object sku, Object specialtyCode);

	public List<BuyerSkuTaskAssocMT> findByNodeId(Object nodeId);

	public List<BuyerSkuTaskAssocMT> findByServiceTypeTemplateId(
			Object serviceTypeTemplateId);
	
	

	/**
	 * Find all BuyerSkuTaskAssoc entities.
	 * 
	 * @return List<BuyerSkuTaskAssoc> all BuyerSkuTaskAssoc entities
	 */
	public List<BuyerSkuTaskAssocMT> findAll();
}