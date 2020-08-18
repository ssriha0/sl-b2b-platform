package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for BuyerSoTemplateDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBuyerSoTemplateDAO {
	/**
	 * Perform an initial save of a previously unsaved BuyerSoTemplate entity.
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
	 * IBuyerSoTemplateDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSoTemplate entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerSoTemplate entity);

	/**
	 * Delete a persistent BuyerSoTemplate entity. This operation must be
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
	 * IBuyerSoTemplateDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSoTemplate entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerSoTemplate entity);

	/**
	 * Persist a previously saved BuyerSoTemplate entity and return it or a copy
	 * of it to the sender. A copy of the BuyerSoTemplate entity parameter is
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
	 * entity = IBuyerSoTemplateDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            BuyerSoTemplate entity to update
	 * @returns BuyerSoTemplate the persisted BuyerSoTemplate entity instance,
	 *          may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerSoTemplate update(BuyerSoTemplate entity);

	public BuyerSoTemplate findById(Integer id);

	/**
	 * Find all BuyerSoTemplate entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerSoTemplate property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerSoTemplate> found by query
	 */
	public List<BuyerSoTemplate> findByProperty(String propertyName,
			Object value);

	public List<BuyerSoTemplate> findByTemplateName(Object templateName);

	public List<BuyerSoTemplate> findByBuyerId(Object buyerId);

	public List<BuyerSoTemplate> findByPrimarySkillCategoryId(
			Object primarySkillCategoryId);

	public BuyerSoTemplate findByPrimarySkillCategoryIdAndBuyerID(Object primarySkillCategoryId, Integer buyerID);
	
	public List<BuyerSoTemplate> findByTemplateData(Object templateData);

	/**
	 * Find all BuyerSoTemplate entities.
	 * 
	 * @return List<BuyerSoTemplate> all BuyerSoTemplate entities
	 */
	public List<BuyerSoTemplate> findAll();
	
	/**
	 * Finds the BuyerSOTemplate by sku, buyer ID, SpecialtyCode
	 * 
	 * @param sku
	 * @param buyerID
	 * @param specialtyCode
	 * @return BuyerSoTemplate
	 */
	public BuyerSoTemplate findBySkuAndBuyerID(String sku, Integer buyerID, String specialtyCode);
}