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
 * Document entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.DocumentMT
 * @author MyEclipse Persistence Tools
 */

public class DocumentDAO extends JpaDaoSupport implements IDocumentDAO {
	// property constants
	public static final String DOC_CATEGORY_ID = "docCategoryId";
	public static final String DESCR = "descr";
	public static final String TITLE = "title";
	public static final String FILE_NAME = "fileName";
	public static final String FORMAT = "format";
	public static final String SOURCE = "source";
	public static final String KEYWORDS = "keywords";
	public static final String DOCUMENT = "document";
	public static final String VENDOR_ID = "vendorId";
	public static final String ROLE_ID = "roleId";
	public static final String ENTITY_ID = "entityId";
	public static final String DELETE_IND = "deleteInd";
	public static final String DOC_URL = "docUrl";
	public static final String DOC_PATH = "docPath";
	public static final String DOC_SIZE = "docSize";

	/**
	 * Perform an initial save of a previously unsaved Document entity. All
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
	 * DocumentDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Document entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(DocumentMT entity) {
		logger.info("saving Document instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent Document entity. This operation must be performed
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
	 * DocumentDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Document entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(DocumentMT entity) {
		logger.info("deleting Document instance");
		try {
			entity = getJpaTemplate().getReference(DocumentMT.class,
					entity.getDocumentId());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved Document entity and return it or a copy of it
	 * to the sender. A copy of the Document entity parameter is returned when
	 * the JPA persistence mechanism has not previously been tracking the
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
	 * entity = DocumentDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            Document entity to update
	 * @returns Document the persisted Document entity instance, may not be the
	 *          same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public DocumentMT update(DocumentMT entity) {
		logger.info("updating Document instance");
		try {
			DocumentMT result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public DocumentMT findById(Integer id) {
		logger.info("finding Document instance with id: " + id);
		try {
			DocumentMT instance = getJpaTemplate().find(DocumentMT.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all Document entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Document property to query
	 * @param value
	 *            the property value to match
	 * @return List<Document> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<DocumentMT> findByProperty(String propertyName, final Object value) {
		logger.info("finding Document instance with property: " + propertyName
				+ ", value: " + value);
		try {
			final String queryString = "select model from DocumentMT model where model."
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

	public List<DocumentMT> findByDocCategoryId(Object docCategoryId) {
		return findByProperty(DOC_CATEGORY_ID, docCategoryId);
	}

	public List<DocumentMT> findByDescr(Object descr) {
		return findByProperty(DESCR, descr);
	}

	public List<DocumentMT> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<DocumentMT> findByFileName(Object fileName) {
		return findByProperty(FILE_NAME, fileName);
	}

	public List<DocumentMT> findByFormat(Object format) {
		return findByProperty(FORMAT, format);
	}

	public List<DocumentMT> findBySource(Object source) {
		return findByProperty(SOURCE, source);
	}

	public List<DocumentMT> findByKeywords(Object keywords) {
		return findByProperty(KEYWORDS, keywords);
	}

	public List<DocumentMT> findByDocument(Object document) {
		return findByProperty(DOCUMENT, document);
	}

	public List<DocumentMT> findByVendorId(Object vendorId) {
		return findByProperty(VENDOR_ID, vendorId);
	}

	public List<DocumentMT> findByRoleId(Object roleId) {
		return findByProperty(ROLE_ID, roleId);
	}

	public List<DocumentMT> findByEntityId(Object entityId) {
		return findByProperty(ENTITY_ID, entityId);
	}

	public List<DocumentMT> findByDeleteInd(Object deleteInd) {
		return findByProperty(DELETE_IND, deleteInd);
	}

	public List<DocumentMT> findByDocUrl(Object docUrl) {
		return findByProperty(DOC_URL, docUrl);
	}

	public List<DocumentMT> findByDocPath(Object docPath) {
		return findByProperty(DOC_PATH, docPath);
	}

	public List<DocumentMT> findByDocSize(Object docSize) {
		return findByProperty(DOC_SIZE, docSize);
	}

	/**
	 * Find all Document entities.
	 * 
	 * @return List<Document> all Document entities
	 */
	@SuppressWarnings("unchecked")
	public List<DocumentMT> findAll() {
		logger.info("finding all Document instances");
		try {
			final String queryString = "select model from DocumentMT model";
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

	public static IDocumentDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IDocumentDAO) ctx.getBean("DocumentDAO");
	}
}