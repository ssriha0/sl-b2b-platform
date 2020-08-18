package com.servicelive.spn.dao.provider;

import java.util.List;

import com.servicelive.spn.dao.BaseDao;
import com.servicelive.domain.provider.ProviderFirm;

public interface ProviderFirmDao extends BaseDao
{
	public ProviderFirm findById(Integer   id) throws Exception;
	public List<ProviderFirm> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;

}
