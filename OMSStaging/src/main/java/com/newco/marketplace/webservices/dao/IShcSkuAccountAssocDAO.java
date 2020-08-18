package com.newco.marketplace.webservices.dao;

import java.util.List;

/**
 * Interface for ShcSkuAccountAssocDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IShcSkuAccountAssocDAO {
	/**
	 * Perform an initial save of a previously unsaved ShcSkuAccountAssoc
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
	 * IShcSkuAccountAssocDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcSkuAccountAssoc entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ShcSkuAccountAssoc entity);

	/**
	 * Delete a persistent ShcSkuAccountAssoc entity. This operation must be
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
	 * IShcSkuAccountAssocDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcSkuAccountAssoc entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ShcSkuAccountAssoc entity);

	/**
	 * Persist a previously saved ShcSkuAccountAssoc entity and return it or a
	 * copy of it to the sender. A copy of the ShcSkuAccountAssoc entity
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
	 * entity = IShcSkuAccountAssocDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcSkuAccountAssoc entity to update
	 * @returns ShcSkuAccountAssoc the persisted ShcSkuAccountAssoc entity
	 *          instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ShcSkuAccountAssoc update(ShcSkuAccountAssoc entity);

	public ShcSkuAccountAssoc findById(String id);

	/**
	 * Find all ShcSkuAccountAssoc entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ShcSkuAccountAssoc property to query
	 * @param value
	 *            the property value to match
	 * @return List<ShcSkuAccountAssoc> found by query
	 */
	public List<ShcSkuAccountAssoc> findByProperty(String propertyName,
			Object value);

	public List<ShcSkuAccountAssoc> findByGlAccount(Object glAccount);

	public List<ShcSkuAccountAssoc> findByGlDivision(Object glDivision);

	public List<ShcSkuAccountAssoc> findByGlCategory(Object glCategory);

	public List<ShcSkuAccountAssoc> findByModifiedBy(Object modifiedBy);

	/**
	 * Find all ShcSkuAccountAssoc entities.
	 * 
	 * @return List<ShcSkuAccountAssoc> all ShcSkuAccountAssoc entities
	 */
	public List<ShcSkuAccountAssoc> findAll();
}