package com.newco.marketplace.translator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Buyer
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.BuyerMT
 * @author MyEclipse Persistence Tools
 */

public class BuyerDAO extends JpaDaoSupport implements IBuyerDAO {
	// property constants
	public static final String POSTING_FEE = "postingFee";
	public static final String CANCELLATION_FEE = "cancellationFee";
	public static final String BUSINESS_NAME = "businessName";
	public static final String BUS_PHONE_NO = "busPhoneNo";
	public static final String BUS_FAX_NO = "busFaxNo";
	public static final String BUYER_SOURCE_ID = "buyerSourceId";
	public static final String CONTACT_ID = "contactId";
	public static final String USER_NAME = "userName";
	public static final String PRI_LOCN_ID = "priLocnId";
	public static final String BILL_LOCN_ID = "billLocnId";
	public static final String DEFAULT_ACCESS_FEE_ID = "defaultAccessFeeId";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String BUSINESS_TYPE_ID = "businessTypeId";
	public static final String PRIMARY_INDUSTRY_ID = "primaryIndustryId";
	public static final String COMPANY_SIZE_ID = "companySizeId";
	public static final String SALES_VOLUME_ID = "salesVolumeId";
	public static final String WEB_ADDRESS = "webAddress";
	public static final String PROMO_CD = "promoCd";
	public static final String TOTAL_SO_COMPLETED = "totalSoCompleted";
	public static final String AGGREGATE_RATING_COUNT = "aggregateRatingCount";
	public static final String AGGREGATE_RATING_SCORE = "aggregateRatingScore";
	public static final String TERMS_COND_IND = "termsCondInd";
	public static final String TERMS_COND_ID = "termsCondId";

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
	 * BuyerDAO.save(entity);
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
	public void save(BuyerMT entity) {
		logger.info("saving Buyer instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

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
	 * BuyerDAO.delete(entity);
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
	public void delete(BuyerMT entity) {
		logger.info("deleting Buyer instance");
		try {
			entity = getJpaTemplate().getReference(BuyerMT.class,
					entity.getBuyerId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

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
	 * entity = BuyerDAO.update(entity);
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
	public BuyerMT update(BuyerMT entity) {
		logger.info("updating Buyer instance");
		try {
			BuyerMT result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public BuyerMT findById(Integer id) {
		logger.info("finding Buyer instance with id: " + id);
		try {
			BuyerMT instance = getJpaTemplate().find(BuyerMT.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all Buyer entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Buyer property to query
	 * @param value
	 *            the property value to match
	 * @return List<Buyer> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerMT> findByProperty(String propertyName, final Object value) {
		logger.info("finding Buyer instance with property: " + propertyName
				+ ", value: " + value);
		try {
			final String queryString = "select model from BuyerMT model where model."
					+ propertyName + "= :propertyValue";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("propertyValue", value);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	public List<BuyerMT> findByPostingFee(Object postingFee) {
		return findByProperty(POSTING_FEE, postingFee);
	}

	public List<BuyerMT> findByCancellationFee(Object cancellationFee) {
		return findByProperty(CANCELLATION_FEE, cancellationFee);
	}

	public List<BuyerMT> findByBusinessName(Object businessName) {
		return findByProperty(BUSINESS_NAME, businessName);
	}

	public List<BuyerMT> findByBusPhoneNo(Object busPhoneNo) {
		return findByProperty(BUS_PHONE_NO, busPhoneNo);
	}

	public List<BuyerMT> findByBusFaxNo(Object busFaxNo) {
		return findByProperty(BUS_FAX_NO, busFaxNo);
	}

	public List<BuyerMT> findByBuyerSourceId(Object buyerSourceId) {
		return findByProperty(BUYER_SOURCE_ID, buyerSourceId);
	}

	public List<BuyerMT> findByContactId(Object contactId) {
		return findByProperty(CONTACT_ID, contactId);
	}

	public List<BuyerMT> findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List<BuyerMT> findByPriLocnId(Object priLocnId) {
		return findByProperty(PRI_LOCN_ID, priLocnId);
	}

	public List<BuyerMT> findByBillLocnId(Object billLocnId) {
		return findByProperty(BILL_LOCN_ID, billLocnId);
	}

	public List<BuyerMT> findByDefaultAccessFeeId(Object defaultAccessFeeId) {
		return findByProperty(DEFAULT_ACCESS_FEE_ID, defaultAccessFeeId);
	}

	public List<BuyerMT> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List<BuyerMT> findByBusinessTypeId(Object businessTypeId) {
		return findByProperty(BUSINESS_TYPE_ID, businessTypeId);
	}

	public List<BuyerMT> findByPrimaryIndustryId(Object primaryIndustryId) {
		return findByProperty(PRIMARY_INDUSTRY_ID, primaryIndustryId);
	}

	public List<BuyerMT> findByCompanySizeId(Object companySizeId) {
		return findByProperty(COMPANY_SIZE_ID, companySizeId);
	}

	public List<BuyerMT> findBySalesVolumeId(Object salesVolumeId) {
		return findByProperty(SALES_VOLUME_ID, salesVolumeId);
	}

	public List<BuyerMT> findByWebAddress(Object webAddress) {
		return findByProperty(WEB_ADDRESS, webAddress);
	}

	public List<BuyerMT> findByPromoCd(Object promoCd) {
		return findByProperty(PROMO_CD, promoCd);
	}

	public List<BuyerMT> findByTotalSoCompleted(Object totalSoCompleted) {
		return findByProperty(TOTAL_SO_COMPLETED, totalSoCompleted);
	}

	public List<BuyerMT> findByAggregateRatingCount(Object aggregateRatingCount) {
		return findByProperty(AGGREGATE_RATING_COUNT, aggregateRatingCount);
	}

	public List<BuyerMT> findByAggregateRatingScore(Object aggregateRatingScore) {
		return findByProperty(AGGREGATE_RATING_SCORE, aggregateRatingScore);
	}

	public List<BuyerMT> findByTermsCondInd(Object termsCondInd) {
		return findByProperty(TERMS_COND_IND, termsCondInd);
	}

	public List<BuyerMT> findByTermsCondId(Object termsCondId) {
		return findByProperty(TERMS_COND_ID, termsCondId);
	}

	/**
	 * Find all Buyer entities.
	 * 
	 * @return List<Buyer> all Buyer entities
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerMT> findAll() {
		logger.info("finding all Buyer instances");
		try {
			final String queryString = "select model from BuyerMT model";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	
	public static IBuyerDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IBuyerDAO) ctx.getBean("BuyerDAO");
	}
}