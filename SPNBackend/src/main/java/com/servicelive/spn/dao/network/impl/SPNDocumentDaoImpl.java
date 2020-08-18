/**
 * 
 */
package com.servicelive.spn.dao.network.impl;

import java.util.List;

import com.servicelive.domain.spn.network.SPNDocument;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.network.SPNDocumentDao;

/**
 * @author hoza
 *
 */
public class SPNDocumentDaoImpl extends AbstractBaseDao implements
		SPNDocumentDao {

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNDocumentDao#delete(com.servicelive.domain.spn.network.SPNDocument)
	 */
	public void delete(SPNDocument entity) throws Exception {
		super.delete(entity);

	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNDocumentDao#findById(java.lang.Integer)
	 */
	public SPNDocument findById(Integer id) throws Exception {
		return (SPNDocument)super.findById(SPNDocument.class, id);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNDocumentDao#findByProperty(java.lang.String, java.lang.Object, int[])
	 */
	@SuppressWarnings("unchecked")
	public List<SPNDocument> findByProperty(String propertyName, Object value,
			int... rowStartIdxAndCount) throws Exception {
		return ( List<SPNDocument>) super.findByProperty("SPNDocument",propertyName,value, rowStartIdxAndCount);
	}
	
	

}
