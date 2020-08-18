package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupSPNStatusActionModifier;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupSPNStatusActionModifierDao;

public class LookupSPNStatusActionModifierDaoImpl extends AbstractBaseDao implements LookupSPNStatusActionModifierDao {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<LookupSPNStatusActionModifier> findAll(int... rowStartIdxAndCount) throws Exception {
		return (List<LookupSPNStatusActionModifier>) super.findAll("LookupSPNStatusActionModifier", rowStartIdxAndCount);
	}
}
