package com.newco.marketplace.webservices.dao;

import java.util.List;

/**
 * Interface for SpecialtyAddOnDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ISpecialtyAddOnDAO {
	/**
	 * Perform an initial save of a previously unsaved SpecialtyAddOn entity.
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
	 * ISpecialtyAddOnDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            SpecialtyAddOn entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(SpecialtyAddOn entity);

	/**
	 * Delete a persistent SpecialtyAddOn entity. This operation must be
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
	 * ISpecialtyAddOnDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            SpecialtyAddOn entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(SpecialtyAddOn entity);

	/**
	 * Persist a previously saved SpecialtyAddOn entity and return it or a copy
	 * of it to the sender. A copy of the SpecialtyAddOn entity parameter is
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
	 * entity = ISpecialtyAddOnDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            SpecialtyAddOn entity to update
	 * @returns SpecialtyAddOn the persisted SpecialtyAddOn entity instance, may
	 *          not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public SpecialtyAddOn update(SpecialtyAddOn entity);

	public SpecialtyAddOn findById(Integer id);

	/**
	 * Find all SpecialtyAddOn entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the SpecialtyAddOn property to query
	 * @param value
	 *            the property value to match
	 * @return List<SpecialtyAddOn> found by query
	 */
	public List<SpecialtyAddOn> findByProperty(String propertyName, Object value);

	public List<SpecialtyAddOn> findBySpecialtyCode(Object specialtyCode);

	public List<SpecialtyAddOn> findBySpecialtyDescription(
			Object specialtyDescription);

	public List<SpecialtyAddOn> findByMajorHeadingCode(Object majorHeadingCode);

	public List<SpecialtyAddOn> findByMajorHeadingDescription(
			Object majorHeadingDescription);

	public List<SpecialtyAddOn> findBySubHeadingCode(Object subHeadingCode);

	public List<SpecialtyAddOn> findBySubHeadingDescription(
			Object subHeadingDescription);

	public List<SpecialtyAddOn> findByClassificationCode(
			Object classificationCode);

	public List<SpecialtyAddOn> findByClassificationDescription(
			Object classificationDescription);

	public List<SpecialtyAddOn> findByCoverage(Object coverage);

	public List<SpecialtyAddOn> findByCoverageDescription(
			Object coverageDescription);

	public List<SpecialtyAddOn> findByJobCodeSuffix(Object jobCodeSuffix);

	public List<SpecialtyAddOn> findByStockNumber(Object stockNumber);

	public List<SpecialtyAddOn> findByJobCodeDescripton(Object jobCodeDescripton);

	public List<SpecialtyAddOn> findByLongDescription(Object longDescription);

	public List<SpecialtyAddOn> findByInclusionDescription(
			Object inclusionDescription);

	public List<SpecialtyAddOn> findBySequence(Object sequence);

	public List<SpecialtyAddOn> findByContractorCostType(
			Object contractorCostType);

	public List<SpecialtyAddOn> findByContractorCostTypeDescription(
			Object contractorCostTypeDescription);

	public List<SpecialtyAddOn> findByDispatchDaysOut(Object dispatchDaysOut);

	public List<SpecialtyAddOn> findByModifiedBy(Object modifiedBy);
	
	public SpecialtyAddOn findBySpecialtyCodeAndStockNumber( String specialtyCode, String stockNumber );
	public List<SpecialtyAddOn> findAddOnsWithMiscBySpecialtyCode( String specialtyCode, String divisionCode );
	
	public List<SpecialtyAddOn> findByMarkUpPercentage(Object markUpPercentage);

	/**
	 * Find all SpecialtyAddOn entities.
	 * 
	 * @return List<SpecialtyAddOn> all SpecialtyAddOn entities
	 */
	public List<SpecialtyAddOn> findAll();
}
