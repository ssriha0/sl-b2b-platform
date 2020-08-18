/**
 *
 */
package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupLanguage;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupLanguageDao;

/**
 * @author hoza
 * 
 */
@Transactional
public class LookupLanguageDaoImpl extends AbstractBaseDao implements LookupLanguageDao {

	public List<LookupLanguage> getLanguages() throws Exception
	{
		List<LookupLanguage> languages = findAll();
		return languages;
	}
	
	public LookupLanguage getLanguageFromId(Integer id) throws Exception
	{
		LookupLanguage language = findById(id);
		return language;
	}
	
	@SuppressWarnings("unchecked")
	public List <LookupLanguage> findAll ( int... rowStartIdxAndCount) throws Exception {
		List <LookupLanguage> orderList = (List <LookupLanguage>)super.findAllOrderByDesc("LookupLanguage", "descr", rowStartIdxAndCount);
		return orderList;
	}

	public LookupLanguage findById(Integer id) throws Exception {
		return ( LookupLanguage) super.findById(LookupLanguage.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<LookupLanguage> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception {
		return ( List<LookupLanguage>) super.findByProperty("LookupLanguage",propertyName,value, rowStartIdxAndCount);
	}
	
}