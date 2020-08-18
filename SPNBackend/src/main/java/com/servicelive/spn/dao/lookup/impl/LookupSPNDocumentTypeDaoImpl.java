/**
 *
 */
package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupSPNDocumentType;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupSPNDocumentTypeDao;


/**
 * @author hoza
 * 
 */
@Transactional
public class LookupSPNDocumentTypeDaoImpl extends AbstractBaseDao implements LookupSPNDocumentTypeDao {
	
		
	@SuppressWarnings("unchecked")
	public List <LookupSPNDocumentType> findAll ( int... rowStartIdxAndCount) throws Exception {
		return ( List <LookupSPNDocumentType> )super.findAll("LookupSPNDocumentType", rowStartIdxAndCount);
	}

	public LookupSPNDocumentType findById(Integer id) throws Exception {
		return ( LookupSPNDocumentType) super.findById(LookupSPNDocumentType.class, id);
	}
	
	

	@SuppressWarnings("unchecked")
	public List<LookupSPNDocumentType> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception {
		return ( List<LookupSPNDocumentType>) super.findByProperty("LookupSPNDocumentType",propertyName,value, rowStartIdxAndCount);
	}
	
}