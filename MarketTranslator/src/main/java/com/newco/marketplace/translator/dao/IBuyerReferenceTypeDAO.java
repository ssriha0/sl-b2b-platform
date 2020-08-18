package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for BuyerReferenceTypeDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBuyerReferenceTypeDAO {
	/**
	 * Perform an initial save of a previously unsaved BuyerReferenceType
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
	 * IBuyerReferenceTypeDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerReferenceType entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerReferenceTypeMT entity);

	/**
	 * Delete a persistent BuyerReferenceType entity. This operation must be
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
	 * IBuyerReferenceTypeDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerReferenceType entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerReferenceTypeMT entity);

	/**
	 * Persist a previously saved BuyerReferenceType entity and return it or a
	 * copy of it to the sender. A copy of the BuyerReferenceType entity
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
	 * entity = IBuyerReferenceTypeDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerReferenceType entity to update
	 * @returns BuyerReferenceType the persisted BuyerReferenceType entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerReferenceTypeMT update(BuyerReferenceTypeMT entity);

	public BuyerReferenceTypeMT findById(Integer id);

	/**
	 * Find all BuyerReferenceType entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerReferenceType property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerReferenceType> found by query
	 */
	public List<BuyerReferenceTypeMT> findByProperty(String propertyName,
			Object value);

	public List<BuyerReferenceTypeMT> findByRefType(Object refType);

	public List<BuyerReferenceTypeMT> findByRefDescr(Object refDescr);

	public List<BuyerReferenceTypeMT> findByModifiedBy(Object modifiedBy);

	public List<BuyerReferenceTypeMT> findBySortOrder(Object sortOrder);

	public List<BuyerReferenceTypeMT> findByActiveInd(Object activeInd);

	/**
	 * Find all BuyerReferenceType entities.
	 * 
	 * @return List<BuyerReferenceType> all BuyerReferenceType entities
	 */
	public List<BuyerReferenceTypeMT> findAll();
}