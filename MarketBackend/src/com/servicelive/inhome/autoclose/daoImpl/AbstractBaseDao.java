package com.servicelive.inhome.autoclose.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 *
 */
public abstract class AbstractBaseDao implements BaseDao {
	protected final Log logger  =  LogFactory.getLog(this.getClass());

	private EntityManager em;
	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.BaseDao#getEntityManager()
	 */
	public EntityManager getEntityManager() {
		return em;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.BaseDao#setEntityManager(javax.persistence.EntityManager)
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}



	/**
	 * 
	 * @param tableName
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return Collection
	 * @throws RuntimeException
	 */
	public java.util.List<?> findByProperty(String tableName ,String propertyName, Object value,int... rowStartIdxAndCount)  throws RuntimeException {

			final String queryString = "select model from " + tableName + " model where model."
					+ propertyName + "= :propertyValue";
			Query query = getEntityManager().createQuery(
					queryString);
			query.setParameter("propertyValue", value);
			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			return query.getResultList();
	}


	/**
	 * 
	 * @param clazz
	 * @param id
	 * @return Object
	 * @throws RuntimeException
	 */
	public Object findById(Class<?> clazz,Object id)  throws RuntimeException {
			return getEntityManager().find(clazz, id);
	}

	/**
	 * 
	 * @param tableName
	 * @param rowStartIdxAndCount
	 * @return Collection
	 * @throws RuntimeException
	 */
	public List<?> findAll(String tableName, int... rowStartIdxAndCount)
		throws RuntimeException {
			
			final String queryString = "select model from " + tableName + " model " ;
			Query query = getEntityManager().createQuery(queryString);

			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			return query.getResultList();
	}
	
	/**
	 * 
	 * @param tableName
	 * @param orderByProp
	 * @param rowStartIdxAndCount
	 * @return Collection
	 * @throws RuntimeException
	 */
	public List<?> findAllOrderByDesc(String tableName, String orderByProp, int... rowStartIdxAndCount)
		throws RuntimeException {
			
			final String queryString = "select model from " + tableName + " model order by " + orderByProp ;
			Query query = getEntityManager().createQuery(queryString);

			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			return query.getResultList();
	}

	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void save(Object entity) throws Exception {
			getEntityManager().persist(entity);
	}

	/**
	 * 
	 * @param entity
	 * @return Object
	 * @throws Exception
	 */
	public Object update(Object entity) throws Exception {
		return getEntityManager().merge(entity);
	}

	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void delete(Object entity) throws Exception {
		getEntityManager().remove(entity);
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.BaseDao#refresh(java.lang.Object)
	 */
	public void refresh(Object entity) throws Exception {
		getEntityManager().refresh(entity);
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void flush() throws Exception {
		getEntityManager().flush();
	}
}
