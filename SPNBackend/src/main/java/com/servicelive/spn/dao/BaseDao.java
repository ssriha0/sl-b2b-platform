/**
 *
 */
package com.servicelive.spn.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author hoza
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
