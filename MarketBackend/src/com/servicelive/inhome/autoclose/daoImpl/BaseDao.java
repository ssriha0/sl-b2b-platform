package com.servicelive.inhome.autoclose.daoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 *
 */
public interface BaseDao {
	/**
	 * 
	 * @param em
	 */
	 @PersistenceContext
	public void setEntityManager(EntityManager em) ;

	 /**
	  * 
	  * @return EntityManager
	  */
	public EntityManager getEntityManager() ;
	
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void  refresh(Object entity) throws Exception ;

}
