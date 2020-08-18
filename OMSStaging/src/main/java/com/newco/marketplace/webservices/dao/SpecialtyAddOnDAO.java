package com.newco.marketplace.webservices.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;


/**
 * A data access object (DAO) providing persistence and search support for
 * SpecialtyAddOn entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.SpecialtyAddOn
 * @author MyEclipse Persistence Tools
 */

public class SpecialtyAddOnDAO extends JpaDaoSupport implements
		ISpecialtyAddOnDAO {
	// property constants
	public static final String SPECIALTY_CODE = "specialtyCode";
	public static final String SPECIALTY_DESCRIPTION = "specialtyDescription";
	public static final String MAJOR_HEADING_CODE = "majorHeadingCode";
	public static final String MAJOR_HEADING_DESCRIPTION = "majorHeadingDescription";
	public static final String SUB_HEADING_CODE = "subHeadingCode";
	public static final String SUB_HEADING_DESCRIPTION = "subHeadingDescription";
	public static final String CLASSIFICATION_CODE = "classificationCode";
	public static final String CLASSIFICATION_DESCRIPTION = "classificationDescription";
	public static final String COVERAGE = "coverage";
	public static final String COVERAGE_DESCRIPTION = "coverageDescription";
	public static final String JOB_CODE_SUFFIX = "jobCodeSuffix";
	public static final String STOCK_NUMBER = "stockNumber";
	public static final String JOB_CODE_DESCRIPTON = "jobCodeDescripton";
	public static final String LONG_DESCRIPTION = "longDescription";
	public static final String INCLUSION_DESCRIPTION = "inclusionDescription";
	public static final String SEQUENCE = "sequence";
	public static final String CONTRACTOR_COST_TYPE = "contractorCostType";
	public static final String CONTRACTOR_COST_TYPE_DESCRIPTION = "contractorCostTypeDescription";
	public static final String DISPATCH_DAYS_OUT = "dispatchDaysOut";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String MARK_UP_PERCENTAGE = "markUpPercentage";

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
	 * SpecialtyAddOnDAO.save(entity);
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
	public void save(SpecialtyAddOn entity) {
		logger.info("saving SpecialtyAddOn instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

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
	 * SpecialtyAddOnDAO.delete(entity);
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
	public void delete(SpecialtyAddOn entity) {
		logger.info("deleting SpecialtyAddOn instance");
		try {
			entity = getJpaTemplate().getReference(SpecialtyAddOn.class,
					entity.getSpecialtyAddOnId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

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
	 * entity = SpecialtyAddOnDAO.update(entity);
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
	public SpecialtyAddOn update(SpecialtyAddOn entity) {
		logger.info("updating SpecialtyAddOn instance");
		try {
			SpecialtyAddOn result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public SpecialtyAddOn findById(Integer id) {
		logger.info("finding SpecialtyAddOn instance with id: " + id);
		try {
			SpecialtyAddOn instance = getJpaTemplate().find(
					SpecialtyAddOn.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all SpecialtyAddOn entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the SpecialtyAddOn property to query
	 * @param value
	 *            the property value to match
	 * @return List<SpecialtyAddOn> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<SpecialtyAddOn> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding SpecialtyAddOn instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from SpecialtyAddOn model where model."
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

	public List<SpecialtyAddOn> findBySpecialtyCode(Object specialtyCode) {
		return findByProperty(SPECIALTY_CODE, specialtyCode);
	}

	public List<SpecialtyAddOn> findBySpecialtyDescription(
			Object specialtyDescription) {
		return findByProperty(SPECIALTY_DESCRIPTION, specialtyDescription);
	}

	public List<SpecialtyAddOn> findByMajorHeadingCode(Object majorHeadingCode) {
		return findByProperty(MAJOR_HEADING_CODE, majorHeadingCode);
	}

	public List<SpecialtyAddOn> findByMajorHeadingDescription(
			Object majorHeadingDescription) {
		return findByProperty(MAJOR_HEADING_DESCRIPTION,
				majorHeadingDescription);
	}

	public List<SpecialtyAddOn> findBySubHeadingCode(Object subHeadingCode) {
		return findByProperty(SUB_HEADING_CODE, subHeadingCode);
	}

	public List<SpecialtyAddOn> findBySubHeadingDescription(
			Object subHeadingDescription) {
		return findByProperty(SUB_HEADING_DESCRIPTION, subHeadingDescription);
	}

	public List<SpecialtyAddOn> findByClassificationCode(
			Object classificationCode) {
		return findByProperty(CLASSIFICATION_CODE, classificationCode);
	}

	public List<SpecialtyAddOn> findByClassificationDescription(
			Object classificationDescription) {
		return findByProperty(CLASSIFICATION_DESCRIPTION,
				classificationDescription);
	}

	public List<SpecialtyAddOn> findByCoverage(Object coverage) {
		return findByProperty(COVERAGE, coverage);
	}

	public List<SpecialtyAddOn> findByCoverageDescription(
			Object coverageDescription) {
		return findByProperty(COVERAGE_DESCRIPTION, coverageDescription);
	}

	public List<SpecialtyAddOn> findByJobCodeSuffix(Object jobCodeSuffix) {
		return findByProperty(JOB_CODE_SUFFIX, jobCodeSuffix);
	}

	public List<SpecialtyAddOn> findByStockNumber(Object stockNumber) {
		return findByProperty(STOCK_NUMBER, stockNumber);
	}

	public List<SpecialtyAddOn> findByJobCodeDescripton(Object jobCodeDescripton) {
		return findByProperty(JOB_CODE_DESCRIPTON, jobCodeDescripton);
	}

	public List<SpecialtyAddOn> findByLongDescription(Object longDescription) {
		return findByProperty(LONG_DESCRIPTION, longDescription);
	}

	public List<SpecialtyAddOn> findByInclusionDescription(
			Object inclusionDescription) {
		return findByProperty(INCLUSION_DESCRIPTION, inclusionDescription);
	}

	public List<SpecialtyAddOn> findBySequence(Object sequence) {
		return findByProperty(SEQUENCE, sequence);
	}

	public List<SpecialtyAddOn> findByContractorCostType(
			Object contractorCostType) {
		return findByProperty(CONTRACTOR_COST_TYPE, contractorCostType);
	}

	public List<SpecialtyAddOn> findByContractorCostTypeDescription(
			Object contractorCostTypeDescription) {
		return findByProperty(CONTRACTOR_COST_TYPE_DESCRIPTION,
				contractorCostTypeDescription);
	}

	public List<SpecialtyAddOn> findByDispatchDaysOut(Object dispatchDaysOut) {
		return findByProperty(DISPATCH_DAYS_OUT, dispatchDaysOut);
	}

	public List<SpecialtyAddOn> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}
	
	public List<SpecialtyAddOn> findByMarkUpPercentage(Object markUpPercentage) {
		return findByProperty(MARK_UP_PERCENTAGE, markUpPercentage);
	}

	/**
	 * Find all SpecialtyAddOn entities.
	 * 
	 * @return List<SpecialtyAddOn> all SpecialtyAddOn entities
	 */
	@SuppressWarnings("unchecked")
	public List<SpecialtyAddOn> findAll() {
		logger.info("finding all SpecialtyAddOn instances");
		try {
			final String queryString = "select model from SpecialtyAddOn model";
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
	public SpecialtyAddOn findBySpecialtyCodeAndStockNumber(	final String specialtyCode,
																final String stockNumber )
	{
		logger.info("finding SpecialtyAddOn instance with stockNumber: " + stockNumber
				+ " and specialtyCode: " + specialtyCode);
		try {
			final String queryString = "SELECT specialtyAddOn FROM SpecialtyAddOn specialtyAddOn "
					+ "WHERE specialtyAddOn.stockNumber = :stockNumber "
					+ "AND specialtyAddOn.specialtyCode = :specialtyCode ";
			return (SpecialtyAddOn) getJpaTemplate().execute(new JpaCallback() {
				public SpecialtyAddOn doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("stockNumber", stockNumber);
					query.setParameter("specialtyCode", specialtyCode);
					return (SpecialtyAddOn) query.getSingleResult();
				}
			});
		} catch (RuntimeException re) {
			logger.error("Finding SpecialtyAddOn failed", re);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<SpecialtyAddOn> findAddOnsWithMiscBySpecialtyCode( 	final String specialtyCode, 
																	final String divisionCode )
	{
		logger.info("finding SpecialtyAddOn instance with specialtyCode: " + specialtyCode
				+ " and one miscillaneous: " + divisionCode);
		try {
			final String queryString = "SELECT specialtyAddOn FROM SpecialtyAddOn specialtyAddOn "
				+ "WHERE specialtyAddOn.specialtyCode = :specialtyCode AND classification_code = 'A' "
				+ "OR specialtyAddOn.specialtyCode = :specialtyCode AND classification_code = 'M' "
					+ "AND stock_number LIKE :divisionCode ";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("specialtyCode", specialtyCode);
					query.setParameter("divisionCode", "%"+divisionCode);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("Finding SpecialtyAddOn failed", re);
		}
		return null;
	}

	public static ISpecialtyAddOnDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ISpecialtyAddOnDAO) ctx.getBean("SpecialtyAddOnDAO");
	}
}
