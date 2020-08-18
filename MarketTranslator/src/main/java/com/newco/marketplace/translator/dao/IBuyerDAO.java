package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for BuyerDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBuyerDAO {
	/**
	 * Perform an initial save of a previously unsaved Buyer entity. All
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
	 * IBuyerDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Buyer entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(BuyerMT entity);

	/**
	 * Delete a persistent Buyer entity. This operation must be performed within
	 * the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the
	 * {@link javax.persistence.EntityManager#remove(Object) EntityManager#delete}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * IBuyerDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Buyer entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(BuyerMT entity);

	/**
	 * Persist a previously saved Buyer entity and return it or a copy of it to
	 * the sender. A copy of the Buyer entity parameter is returned when the JPA
	 * persistence mechanism has not previously been tracking the updated
	 * entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = IBuyerDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Buyer entity to update
	 * @returns Buyer the persisted Buyer entity instance, may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public BuyerMT update(BuyerMT entity);

	public BuyerMT findById(Integer id);

	/**
	 * Find all Buyer entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Buyer property to query
	 * @param value
	 *            the property value to match
	 * @return List<Buyer> found by query
	 */
	public List<BuyerMT> findByProperty(String propertyName, Object value);

	public List<BuyerMT> findByPostingFee(Object postingFee);

	public List<BuyerMT> findByCancellationFee(Object cancellationFee);

	public List<BuyerMT> findByBusinessName(Object businessName);

	public List<BuyerMT> findByBusPhoneNo(Object busPhoneNo);

	public List<BuyerMT> findByBusFaxNo(Object busFaxNo);

	public List<BuyerMT> findByBuyerSourceId(Object buyerSourceId);

	public List<BuyerMT> findByContactId(Object contactId);

	public List<BuyerMT> findByUserName(Object userName);

	public List<BuyerMT> findByPriLocnId(Object priLocnId);

	public List<BuyerMT> findByBillLocnId(Object billLocnId);

	public List<BuyerMT> findByDefaultAccessFeeId(Object defaultAccessFeeId);

	public List<BuyerMT> findByModifiedBy(Object modifiedBy);

	public List<BuyerMT> findByBusinessTypeId(Object businessTypeId);

	public List<BuyerMT> findByPrimaryIndustryId(Object primaryIndustryId);

	public List<BuyerMT> findByCompanySizeId(Object companySizeId);

	public List<BuyerMT> findBySalesVolumeId(Object salesVolumeId);

	public List<BuyerMT> findByWebAddress(Object webAddress);

	public List<BuyerMT> findByPromoCd(Object promoCd);

	public List<BuyerMT> findByTotalSoCompleted(Object totalSoCompleted);

	public List<BuyerMT> findByAggregateRatingCount(Object aggregateRatingCount);

	public List<BuyerMT> findByAggregateRatingScore(Object aggregateRatingScore);

	public List<BuyerMT> findByTermsCondInd(Object termsCondInd);

	public List<BuyerMT> findByTermsCondId(Object termsCondId);

	/**
	 * Find all Buyer entities.
	 * 
	 * @return List<Buyer> all Buyer entities
	 */
	public List<BuyerMT> findAll();
}