package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for DocumentDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IDocumentDAO {
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
	 * IDocumentDAO.save(entity);
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
	public void save(DocumentMT entity);

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
	 * IDocumentDAO.delete(entity);
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
	public void delete(DocumentMT entity);

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
	 * entity = IDocumentDAO.update(entity);
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
	public DocumentMT update(DocumentMT entity);

	public DocumentMT findById(Integer id);

	/**
	 * Find all Document entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the Document property to query
	 * @param value
	 *            the property value to match
	 * @return List<Document> found by query
	 */
	public List<DocumentMT> findByProperty(String propertyName, Object value);

	public List<DocumentMT> findByDocCategoryId(Object docCategoryId);

	public List<DocumentMT> findByDescr(Object descr);

	public List<DocumentMT> findByTitle(Object title);

	public List<DocumentMT> findByFileName(Object fileName);

	public List<DocumentMT> findByFormat(Object format);

	public List<DocumentMT> findBySource(Object source);

	public List<DocumentMT> findByKeywords(Object keywords);

	public List<DocumentMT> findByDocument(Object document);

	public List<DocumentMT> findByVendorId(Object vendorId);

	public List<DocumentMT> findByRoleId(Object roleId);

	public List<DocumentMT> findByEntityId(Object entityId);

	public List<DocumentMT> findByDeleteInd(Object deleteInd);

	public List<DocumentMT> findByDocUrl(Object docUrl);

	public List<DocumentMT> findByDocPath(Object docPath);

	public List<DocumentMT> findByDocSize(Object docSize);

	/**
	 * Find all Document entities.
	 * 
	 * @return List<Document> all Document entities
	 */
	public List<DocumentMT> findAll();
}