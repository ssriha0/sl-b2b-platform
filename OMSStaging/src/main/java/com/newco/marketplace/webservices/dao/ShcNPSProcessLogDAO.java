package com.newco.marketplace.webservices.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * Data access object (DAO) for domain model class ShcNPSProcessLog.
 * @see .ShcNPSProcessLog
  * @author MyEclipse Persistence Tools 
 */
public class ShcNPSProcessLogDAO extends JpaDaoSupport implements IShcNPSProcessLogDAO {

	//property constants
	public static final String FILE_NAME = "fileName";
	public static final String MODIFIED_BY = "modifiedBy";

    public void save(ShcNPSProcessLog entity) {
		logger.info("saving ShcNPSProcessLog instance");
        try {
        	getJpaTemplate().persist(entity);
        	logger.info("save successful");
        } catch (RuntimeException re) {
    		logger.error("save failed", re);
            throw re;
        }
    }
    
    public void delete(ShcNPSProcessLog entity) {
		logger.info("deleting ShcNPSProcessLog instance");
        try {
        	entity = getJpaTemplate().getReference(ShcNPSProcessLog.class, entity.getNpsProcessId());
        	getJpaTemplate().remove(entity);
        	logger.info("delete successful");
        } catch (RuntimeException re) {
    		logger.error("delete failed", re);
            throw re;
        }
    }
    
    public ShcNPSProcessLog update(ShcNPSProcessLog entity) {
		logger.info("updating ShcNPSProcessLog instance");
        try {
        	ShcNPSProcessLog result = getJpaTemplate().merge(entity);
        	logger.info("update successful");
            return result;
        } catch (RuntimeException re) {
    		logger.error("update failed", re);
            throw re;
        }
    }
    
    public ShcNPSProcessLog findById( Integer id) {
		logger.info("finding ShcNPSProcessLog instance with id: " + id);
        try {
        	ShcNPSProcessLog instance = getJpaTemplate().find(ShcNPSProcessLog.class, id);
        	return instance;
        } catch (RuntimeException re) {
    		logger.error("find failed", re);
            throw re;
        }
    }
    
    public List<ShcNPSProcessLog> findByProperty(String propertyName, final Object value) {
		logger.info("finding ShcNPSProcessLog instance with property: " + propertyName + ", value: " + value);
		try {
			final String queryString = "select model from ShcNPSProcessLog model where model." + propertyName + "= :propertyValue";
		    @SuppressWarnings("unchecked")
			List<ShcNPSProcessLog> npsProcessLogs = getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("propertyValue", value);
					return query.getResultList();
				}
			});
			return npsProcessLogs;
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List<ShcNPSProcessLog> findByFileName(Object fileName) {
		return findByProperty(FILE_NAME, fileName);
	}
	
	public List<ShcNPSProcessLog> findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List<ShcNPSProcessLog> findAll() {
		logger.info("finding all ShcNPSProcessLog instances");
		try {
			final String queryString = "select model from ShcNPSProcessLog model";
			@SuppressWarnings("unchecked")
			List<ShcNPSProcessLog> npsProcessLogs = getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em) throws PersistenceException {
					Query query = em.createQuery(queryString);
					return query.getResultList();
				}
			});
			return npsProcessLogs;
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
	
	public static IShcNPSProcessLogDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (IShcNPSProcessLogDAO) ctx.getBean("ShcNPSProcessLogDAO");
	}
}