package com.newco.marketplace.translator.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * BuyerSoTemplate entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.BuyerSoTemplate
 * @author MyEclipse Persistence Tools
 */

/**
 * @author mkhair
 *
 */
public class BuyerSoTemplateDAO extends JpaDaoSupport implements
		IBuyerSoTemplateDAO {
	// property constants
	public static final String TEMPLATE_NAME = "templateName";
	public static final String BUYER_ID = "buyerId";
	public static final String PRIMARY_SKILL_CATEGORY_ID = "primarySkillCategoryId";
	public static final String TEMPLATE_DATA = "templateData";

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
	 * BuyerSoTemplateDAO.save(entity);
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
	public void save(BuyerSoTemplate entity) {
		logger.info("saving BuyerSoTemplate instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

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
	 * BuyerSoTemplateDAO.delete(entity);
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
	public void delete(BuyerSoTemplate entity) {
		logger.info("deleting BuyerSoTemplate instance");
		try {
			entity = getJpaTemplate().getReference(BuyerSoTemplate.class,
					entity.getTemplateId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

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
	 * entity = BuyerSoTemplateDAO.update(entity);
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
	public BuyerSoTemplate update(BuyerSoTemplate entity) {
		logger.info("updating BuyerSoTemplate instance");
		try {
			BuyerSoTemplate result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public BuyerSoTemplate findById(Integer id) {
		logger.info("finding BuyerSoTemplate instance with id: " + id);
		try {
			BuyerSoTemplate instance = getJpaTemplate().find(
					BuyerSoTemplate.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all BuyerSoTemplate entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the BuyerSoTemplate property to query
	 * @param value
	 *            the property value to match
	 * @return List<BuyerSoTemplate> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSoTemplate> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding BuyerSoTemplate instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from BuyerSoTemplate model where model."
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

	public List<BuyerSoTemplate> findByTemplateName(Object templateName) {
		return findByProperty(TEMPLATE_NAME, templateName);
	}

	public List<BuyerSoTemplate> findByBuyerId(Object buyerId) {
		return findByProperty(BUYER_ID, buyerId);
	}

	public List<BuyerSoTemplate> findByPrimarySkillCategoryId(
			Object primarySkillCategoryId) {
		return findByProperty(PRIMARY_SKILL_CATEGORY_ID, primarySkillCategoryId);
	}

	public List<BuyerSoTemplate> findByTemplateData(Object templateData) {
		return findByProperty(TEMPLATE_DATA, templateData);
	}

	/**
	 * Find all BuyerSoTemplate entities.
	 * 
	 * @return List<BuyerSoTemplate> all BuyerSoTemplate entities
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerSoTemplate> findAll() {
		logger.info("finding all BuyerSoTemplate instances");
		try {
			final String queryString = "select model from BuyerSoTemplate model";
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

	public static IBuyerSoTemplateDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IBuyerSoTemplateDAO) ctx.getBean("BuyerSoTemplateDAO");
	}

	public BuyerSoTemplate findByPrimarySkillCategoryIdAndBuyerID(final Object primarySkillCategoryId, final Integer buyerID) {
		try {
			final String queryString = "select model from BuyerSoTemplate model where primarySkillCategoryId = :primarySkillCategoryId and buyer_id = :buyerID";
			return (BuyerSoTemplate) getJpaTemplate().execute(new JpaCallback() {
				public BuyerSoTemplate doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("primarySkillCategoryId", primarySkillCategoryId);
					query.setParameter("buyerID", buyerID);
					return (BuyerSoTemplate) query.getSingleResult();
				}
			});
		} catch (RuntimeException re) {
			logger.error("Finding template failed", re);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.dao.IBuyerSoTemplateDAO#findBySkuAndBuyerID(java.lang.String, java.lang.Integer)
	 */
	public BuyerSoTemplate findBySkuAndBuyerID(final String sku, final Integer buyerID, final String specialtyCode) {
		try {
			final String queryString = "select template from BuyerSoTemplate template, BuyerSkuTaskAssoc skuTask " +
					"where template.templateId = skuTask.templateId and skuTask.buyerId = :buyerID and skuTask.sku = :sku and " +
					"skuTask.specialtyCode = :specialtyCode group by skuTask.templateId";
			return (BuyerSoTemplate) getJpaTemplate().execute(new JpaCallback() {
				public BuyerSoTemplate doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("sku", sku);
					query.setParameter("buyerID", buyerID);
					query.setParameter("specialtyCode", specialtyCode);
					return (BuyerSoTemplate) query.getSingleResult();
				}
			});
		} catch (RuntimeException re) {
			logger.error("Finding template failed", re);
		}
		return null;
	}
}