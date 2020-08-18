package com.newco.marketplace.webservices.dao;

import java.util.List;

/**
 * Interface for ShcMerchandiseDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IShcMerchandiseDAO {
	/**
	 * Perform an initial save of a previously unsaved ShcMerchandise entity.
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
	 * IShcMerchandiseDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcMerchandise entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ShcMerchandise entity);

	/**
	 * Delete a persistent ShcMerchandise entity. This operation must be
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
	 * IShcMerchandiseDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcMerchandise entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ShcMerchandise entity);

	/**
	 * Persist a previously saved ShcMerchandise entity and return it or a copy
	 * of it to the sender. A copy of the ShcMerchandise entity parameter is
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
	 * entity = IShcMerchandiseDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ShcMerchandise entity to update
	 * @returns ShcMerchandise the persisted ShcMerchandise entity instance, may
	 *          not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ShcMerchandise update(ShcMerchandise entity);

	public ShcMerchandise findById(Integer id);

	/**
	 * Find all ShcMerchandise entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ShcMerchandise property to query
	 * @param value
	 *            the property value to match
	 * @return List<ShcMerchandise> found by query
	 */
	public List<ShcMerchandise> findByProperty(String propertyName, Object value);

	public List<ShcMerchandise> findByItemNo(Object itemNo);

	public List<ShcMerchandise> findByCode(Object code);

	public List<ShcMerchandise> findByDescription(Object description);

	public List<ShcMerchandise> findBySpecialty(Object specialty);

	public List<ShcMerchandise> findBySerialNumber(Object serialNumber);

	public List<ShcMerchandise> findByDivisionCode(Object divisionCode);

	public List<ShcMerchandise> findByBrand(Object brand);

	public List<ShcMerchandise> findByModel(Object model);

	public List<ShcMerchandise> findByModifiedBy(Object modifiedBy);
	
	public List<ShcMerchandise> findByShcOrderId(final Integer shcOrderId); 

	/**
	 * Find all ShcMerchandise entities.
	 * 
	 * @return List<ShcMerchandise> all ShcMerchandise entities
	 */
	public List<ShcMerchandise> findAll();
}