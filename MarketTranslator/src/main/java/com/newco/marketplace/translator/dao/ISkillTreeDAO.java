package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for SkillTreeDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface ISkillTreeDAO {
	/**
	 * Perform an initial save of a previously unsaved SkillTree entity. All
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
	 * ISkillTreeDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            SkillTree entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(SkillTree entity);

	/**
	 * Delete a persistent SkillTree entity. This operation must be performed
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
	 * ISkillTreeDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            SkillTree entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(SkillTree entity);

	/**
	 * Persist a previously saved SkillTree entity and return it or a copy of it
	 * to the sender. A copy of the SkillTree entity parameter is returned when
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
	 * entity = ISkillTreeDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            SkillTree entity to update
	 * @returns SkillTree the persisted SkillTree entity instance, may not be
	 *          the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public SkillTree update(SkillTree entity);

	public SkillTree findById(Integer id);

	/**
	 * Find all SkillTree entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the SkillTree property to query
	 * @param value
	 *            the property value to match
	 * @return List<SkillTree> found by query
	 */
	public List<SkillTree> findByProperty(String propertyName, Object value);

	public List<SkillTree> findByLevel(Object level);

	public List<SkillTree> findByLeaf(Object leaf);

	public List<SkillTree> findByJobClassifier(Object jobClassifier);

	public List<SkillTree> findByRoot(Object root);

	public List<SkillTree> findByNodeName(Object nodeName);

	public List<SkillTree> findByDescriptionContent(Object descriptionContent);

	public List<SkillTree> findByMatchWeightFactor(Object matchWeightFactor);

	public List<SkillTree> findByDepthLimit(Object depthLimit);

	public List<SkillTree> findByParentNode(Object parentNode);

	public List<SkillTree> findByModifiedBy(Object modifiedBy);

	public List<SkillTree> findBySortOrder(Object sortOrder);

	/**
	 * Find all SkillTree entities.
	 * 
	 * @return List<SkillTree> all SkillTree entities
	 */
	public List<SkillTree> findAll();
}