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
 * UserProfile entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.UserProfile
 * @author MyEclipse Persistence Tools
 */

public class UserProfileDAO extends JpaDaoSupport implements IUserProfileDAO {
	// property constants
	public static final String VENDOR_ID = "vendorId";
	public static final String PASSWORD = "password";
	public static final String QUESTION_ID = "questionId";
	public static final String ANSWER_TXT = "answerTxt";
	public static final String CONTACT_ID = "contactId";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String GENERATED_PASSWORD_IND = "generatedPasswordInd";
	public static final String LOCKED_IND = "lockedInd";
	public static final String LOGIN_ATTEMPT_COUNT = "loginAttemptCount";
	public static final String USER_NAME = "userName";

	/**
	 * Perform an initial save of a previously unsaved UserProfile entity. All
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
	 * UserProfileDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfile entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(UserProfile entity) {
		logger.info("saving UserProfile instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent UserProfile entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
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
	 * UserProfileDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfile entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(UserProfile entity) {
		logger.info("deleting UserProfile instance");
		try {
			entity = getJpaTemplate().getReference(UserProfile.class,
					entity.getUserName());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved UserProfile entity and return it or a copy of
	 * it to the sender. A copy of the UserProfile entity parameter is returned
	 * when the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
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
	 * entity = UserProfileDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            UserProfile entity to update
	 * @returns UserProfile the persisted UserProfile entity instance, may not
	 *          be the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public UserProfile update(UserProfile entity) {
		logger.info("updating UserProfile instance");
		try {
			UserProfile result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public UserProfile findById(String id) {
		logger.info("finding UserProfile instance with id: " + id);
		try {
			UserProfile instance = getJpaTemplate().find(UserProfile.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}
	
	public UserProfile findByUserNameAndRoleID(final String userName, final Integer roleID) {
		try {
			final String queryString = "select model from UserProfile model where user_name = :userName and role_id = :roleID";
			return (UserProfile) getJpaTemplate().execute(new JpaCallback() {
				public UserProfile doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("userName", userName);
					query.setParameter("roleID", roleID);
					return (UserProfile) query.getSingleResult();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * Find all UserProfile entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the UserProfile property to query
	 * @param value
	 *            the property value to match
	 * @return List<UserProfile> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<UserProfile> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding UserProfile instance using property name and value");
		try {
			final String queryString = "select model from UserProfile model where model."
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

	public List<UserProfile> findByVendorId(Object vendorId) {
		return findByProperty(VENDOR_ID, vendorId);
	}

	public List<UserProfile> findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List<UserProfile> findByQuestionId(Object questionId) {
		return findByProperty(QUESTION_ID, questionId);
	}

	public List<UserProfile> findByAnswerTxt(Object answerTxt) {
		return findByProperty(ANSWER_TXT, answerTxt);
	}

	public List<UserProfile> findByContactId(Object contactId) {
		return findByProperty(CONTACT_ID, contactId);
	}

	public List<UserProfile> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List<UserProfile> findByGeneratedPasswordInd(
			Object generatedPasswordInd) {
		return findByProperty(GENERATED_PASSWORD_IND, generatedPasswordInd);
	}

	public List<UserProfile> findByLockedInd(Object lockedInd) {
		return findByProperty(LOCKED_IND, lockedInd);
	}

	public List<UserProfile> findByLoginAttemptCount(Object loginAttemptCount) {
		return findByProperty(LOGIN_ATTEMPT_COUNT, loginAttemptCount);
	}

	/**
	 * Find all UserProfile entities.
	 * 
	 * @return List<UserProfile> all UserProfile entities
	 */
	@SuppressWarnings("unchecked")
	public List<UserProfile> findAll() {
		logger.info("finding all UserProfile instances");
		try {
			final String queryString = "select model from UserProfile model";
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

	public static IUserProfileDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IUserProfileDAO) ctx.getBean("UserProfileDAO");
	}

}