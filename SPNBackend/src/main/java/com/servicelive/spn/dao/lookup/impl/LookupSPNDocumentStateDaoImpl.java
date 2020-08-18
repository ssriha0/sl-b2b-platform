package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupSPNDocumentState;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupSPNDocumentStateDao;

/**
 * 
 * @author svanloon
 *
 */
public class LookupSPNDocumentStateDaoImpl extends AbstractBaseDao implements LookupSPNDocumentStateDao {

	@SuppressWarnings("unchecked")
	public List<LookupSPNDocumentState> getActionableSPNDocumentStates() throws Exception {
		return ( List<LookupSPNDocumentState>) super.findByProperty("LookupSPNDocumentState","isActionable",Boolean.TRUE);
	}

	public LookupSPNDocumentState getSPNDocumentStateFromId(String id) throws Exception
	{
		LookupSPNDocumentState state = findById(id);
		return state;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List <LookupSPNDocumentState> findAll ( int... rowStartIdxAndCount) throws Exception {
		return ( List <LookupSPNDocumentState> )super.findAll("LookupSPNDocumentState", rowStartIdxAndCount);
	}

	public LookupSPNDocumentState findById(String id) throws Exception {
		return ( LookupSPNDocumentState) super.findById(LookupSPNDocumentState.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<LookupSPNDocumentState> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception {
		return ( List<LookupSPNDocumentState>) super.findByProperty("LookupSPNDocumentState",propertyName,value, rowStartIdxAndCount);
	}
}
