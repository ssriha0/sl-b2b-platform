package com.servicelive.spn.dao.provider.impl;

import java.util.List;

import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.provider.ProviderFirmDao;
import com.servicelive.domain.provider.ProviderFirm;

public class ProviderFirmDaoImpl extends AbstractBaseDao implements ProviderFirmDao
{

	public ProviderFirm findById(Integer id) throws Exception
	{
		return (ProviderFirm) super.findById(ProviderFirm.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ProviderFirm> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) throws Exception
	{
		return ( List<ProviderFirm> )super.findByProperty("ProviderFirm", propertyName, value, rowStartIdxAndCount);
	}


}
