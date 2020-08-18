package com.newco.marketplace.webservices.dao;

import java.util.List;

/**
 * Interface for ShcNPSProcessLogDAO.
 * @author MyEclipse Persistence Tools
 */

public interface IShcNPSProcessLogDAO {
	
	/**
	 Perform an initial save of a previously unsaved ShcNPSProcessLog entity. 
	 All subsequent persist actions of this entity should use the #update() method.
	 This operation must be performed within the a database transaction context for the entity's data to be permanently saved to the persistence store, i.e., database. 
	 This method uses the {@link javax.persistence.EntityManager#persist(Object) EntityManager#persist} operation.
	 	 <p>
	 User-managed Spring transaction example:
	 	 	 
	 * <pre> 
	 *   TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
	 *   IShcNPSProcessLogDAO.save(entity);
	 *   txManager.commit(txn);
	 * </pre>
	 @see <a href = "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring container-managed transaction examples</a>
	   @param entity ShcNPSProcessLog entity to persist
	  @throws RuntimeException when the operation fails
	 */
    public void save(ShcNPSProcessLog entity);
    
    /**
	 Delete a persistent ShcNPSProcessLog entity.
	  This operation must be performed 
	 within the a database transaction context for the entity's data to be
	 permanently deleted from the persistence store, i.e., database. 
	 This method uses the {@link javax.persistence.EntityManager#remove(Object) EntityManager#delete} operation.
	 	 <p>
	 User-managed Spring transaction example:
	 	 	 
	 * <pre> 
	 *   TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
	 *   IShcNPSProcessLogDAO.delete(entity);
	 *   txManager.commit(txn);
	 *   entity = null;
	 * </pre>
	 @see <a href = "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring container-managed transaction examples</a>
	   @param entity ShcNPSProcessLog entity to delete
	 @throws RuntimeException when the operation fails
	 */
    public void delete(ShcNPSProcessLog entity);
    
   /**
	 Persist a previously saved ShcNPSProcessLog entity and return it or a copy of it to the sender. 
	 A copy of the ShcNPSProcessLog entity parameter is returned when the JPA persistence mechanism has not previously been tracking the updated entity. 
	 This operation must be performed within the a database transaction context for the entity's data to be permanently saved to the persistence
	 store, i.e., database. This method uses the {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge} operation.
	 	 <p>
	 User-managed Spring transaction example:
	 	 	 
	 * <pre> 
	 *   TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
	 *   entity = IShcNPSProcessLogDAO.update(entity);
	 *   txManager.commit(txn);
	 * </pre>
	 @see <a href = "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring container-managed transaction examples</a>
	   @param entity ShcNPSProcessLog entity to update
	 @returns ShcNPSProcessLog the persisted ShcNPSProcessLog entity instance, may not be the same
	 @throws RuntimeException if the operation fails
	 */
	public ShcNPSProcessLog update(ShcNPSProcessLog entity);
	
	public ShcNPSProcessLog findById( Integer id);
	
	 /**
	   * Find all ShcNPSProcessLog entities with a specific property value.  
	   *
	   *@param propertyName the name of the ShcNPSProcessLog property to query
	   *@param value the property value to match
	   *@return List<ShcNPSProcessLog> found by query
	   */
	public List<ShcNPSProcessLog> findByProperty(String propertyName, Object value);
	
	public List<ShcNPSProcessLog> findByFileName(Object fileName);
	
	public List<ShcNPSProcessLog> findByModifiedBy(Object modifiedBy);
	
	/**
	 * Find all ShcNPSProcessLog entities.
	  	  @return List<ShcNPSProcessLog> all ShcNPSProcessLog entities
	 */
	public List<ShcNPSProcessLog> findAll();	
}