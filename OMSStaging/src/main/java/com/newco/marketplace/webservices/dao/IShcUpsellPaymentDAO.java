package com.newco.marketplace.webservices.dao;

import java.util.List;

/**
 * Interface for ShcUpsellPaymentDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IShcUpsellPaymentDAO {
	/**
	 * Perform an initial save of a previously unsaved ShcUpsellPayment entity.
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
	 * IShcUpsellPaymentDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcUpsellPayment entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ShcUpsellPayment entity);

	/**
	 * Delete a persistent ShcUpsellPayment entity. This operation must be
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
	 * IShcUpsellPaymentDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcUpsellPayment entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ShcUpsellPayment entity);

	/**
	 * Persist a previously saved ShcUpsellPayment entity and return it or a
	 * copy of it to the sender. A copy of the ShcUpsellPayment entity parameter
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
	 * entity = IShcUpsellPaymentDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcUpsellPayment entity to update
	 * @returns ShcUpsellPayment the persisted ShcUpsellPayment entity instance,
	 *          may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ShcUpsellPayment update(ShcUpsellPayment entity);

	public ShcUpsellPayment findById(Integer id);

	/**
	 * Find all ShcUpsellPayment entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ShcUpsellPayment property to query
	 * @param value
	 *            the property value to match
	 * @return List<ShcUpsellPayment> found by query
	 */
	public List<ShcUpsellPayment> findByProperty(String propertyName,
			Object value);

	public List<ShcUpsellPayment> findByPaymentMethodInd(Object paymentMethodInd);

	public List<ShcUpsellPayment> findByPaymentAccNo(Object paymentAccNo);

	public List<ShcUpsellPayment> findByAuthNo(Object authNo);

	public List<ShcUpsellPayment> findBySecPaymentMethodInd(
			Object secPaymentMethodInd);

	public List<ShcUpsellPayment> findBySecPaymentAccNo(Object secPaymentAccNo);

	public List<ShcUpsellPayment> findBySecAuthNo(Object secAuthNo);

	public List<ShcUpsellPayment> findByAmountCollected(Object amountCollected);

	public List<ShcUpsellPayment> findByPriAmountCollected(
			Object priAmountCollected);

	public List<ShcUpsellPayment> findBySecAmountCollected(
			Object secAmountCollected);

	public List<ShcUpsellPayment> findByModifiedBy(Object modifiedBy);

	/**
	 * Find all ShcUpsellPayment entities.
	 * 
	 * @return List<ShcUpsellPayment> all ShcUpsellPayment entities
	 */
	public List<ShcUpsellPayment> findAll();
}