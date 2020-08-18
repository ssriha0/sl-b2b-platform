package com.newco.marketplace.webservices.dao;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * LuAuditMessages entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.webservices.dao.LuAuditMessages
 * @author MyEclipse Persistence Tools
 */

public class LuAuditMessagesDAO extends JpaDaoSupport implements
		ILuAuditMessagesDAO {
	// property constants
	public static final String MESSAGE = "message";
	public static final String WORK_INSTRUCTION = "workInstruction";
	public static final String NPS_STATUS = "npsStatus";
	public static final String SUCCESS_IND = "successInd";
	public static final String REPORTABLE = "reportable";

	/**
	 * Perform an initial save of a previously unsaved LuAuditMessages entity.
	 * All subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * LuAuditMessagesDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuAuditMessages entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(LuAuditMessages entity) {
		logger.info("saving LuAuditMessages instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent LuAuditMessages entity. This operation must be
	 * performed within the a database transaction context for the entity's data
	 * to be permanently deleted from the persistence store, i.e., database.
	 * This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * LuAuditMessagesDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuAuditMessages entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(LuAuditMessages entity) {
		logger.info("deleting LuAuditMessages instance");
		try {
			entity = getJpaTemplate().getReference(LuAuditMessages.class,
					entity.getAuditMessageId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved LuAuditMessages entity and return it or a copy
	 * of it to the sender. A copy of the LuAuditMessages entity parameter is
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
	 * entity = LuAuditMessagesDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            LuAuditMessages entity to update
	 * @return LuAuditMessages the persisted LuAuditMessages entity instance,
	 *         may not be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public LuAuditMessages update(LuAuditMessages entity) {
		logger.info("updating LuAuditMessages instance");
		try {
			LuAuditMessages result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public LuAuditMessages findById(Integer id) {
		logger.info("finding LuAuditMessages instance with id: " + id);
		try {
			LuAuditMessages instance = getJpaTemplate().find(
					LuAuditMessages.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all LuAuditMessages entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the LuAuditMessages property to query
	 * @param value
	 *            the property value to match
	 * @return List<LuAuditMessages> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<LuAuditMessages> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding LuAuditMessages instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from LuAuditMessages model where model."
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

	public List<LuAuditMessages> findByMessage(Object message) {
		return findByProperty(MESSAGE, message);
	}

	public List<LuAuditMessages> findByWorkInstruction(Object workInstruction) {
		return findByProperty(WORK_INSTRUCTION, workInstruction);
	}

	public List<LuAuditMessages> findByNpsStatus(Object npsStatus) {
		return findByProperty(NPS_STATUS, npsStatus);
	}

	public List<LuAuditMessages> findBySuccessInd(Object successInd) {
		return findByProperty(SUCCESS_IND, successInd);
	}

	public List<LuAuditMessages> findByReportable(Object reportable) {
		return findByProperty(REPORTABLE, reportable);
	}

	/**
	 * Find all LuAuditMessages entities.
	 * 
	 * @return List<LuAuditMessages> all LuAuditMessages entities
	 */
	@SuppressWarnings("unchecked")
	public List<LuAuditMessages> findAll() {
		logger.info("finding all LuAuditMessages instances");
		try {
			final String queryString = "select model from LuAuditMessages model";
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

	public static ILuAuditMessagesDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ILuAuditMessagesDAO) ctx.getBean("LuAuditMessagesDAO");
	}
	
	@SuppressWarnings("unchecked")
	public List<LuAuditMessages> findByMessageAndNpsStatus(final String  message, final String npsStatus){
		logger.info("finding AuditMessages for given message and nps status");
		try{
			final String querystring = "select model from LuAuditMessages model where model.message "
											+ "= :messageValue and model.npsStatus" + "= :npsStatusValue";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em) 
				       throws PersistenceException {
					Query query = em.createQuery(querystring);
					query.setParameter("messageValue", message);
					query.setParameter("npsStatusValue", npsStatus);
					return  query.getResultList();
				}
			});
			
		}catch(RuntimeException re){
			logger.error("findByMessageAndNpsStatus failed", re);
			throw re;
		}
	}
}