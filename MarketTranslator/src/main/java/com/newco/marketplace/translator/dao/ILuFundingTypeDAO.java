package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for LuFundingTypeDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ILuFundingTypeDAO {
	/**
	 * Perform an initial save of a previously unsaved LuFundingType entity. All
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
	 * ILuFundingTypeDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuFundingType entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LuFundingType entity);

	/**
	 * Delete a persistent LuFundingType entity. This operation must be
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
	 * ILuFundingTypeDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuFundingType entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LuFundingType entity);

	/**
	 * Persist a previously saved LuFundingType entity and return it or a copy
	 * of it to the sender. A copy of the LuFundingType entity parameter is
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
	 * entity = ILuFundingTypeDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuFundingType entity to update
	 * @returns LuFundingType the persisted LuFundingType entity instance, may
	 *          not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LuFundingType update(LuFundingType entity);

	public LuFundingType findById(Integer id);

	/**
	 * Find all LuFundingType entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LuFundingType property to query
	 * @param value
	 *            the property value to match
	 * @return List<LuFundingType> found by query
	 */
	public List<LuFundingType> findByProperty(String propertyName, Object value);

	public List<LuFundingType> findByType(Object type);

	public List<LuFundingType> findByDescr(Object descr);

	public List<LuFundingType> findBySortOrder(Object sortOrder);

	/**
	 * Find all LuFundingType entities.
	 * 
	 * @return List<LuFundingType> all LuFundingType entities
	 */
	public List<LuFundingType> findAll();
}